package com.bitcoin_investment.infrastructure.repository_impl;

import com.bitcoin_investment.core.command.domain.entity.account.AccountPassword;
import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinInvestment;
import com.bitcoin_investment.infrastructure.persistence.AccountPasswordCryptService;
import com.bitcoin_investment.infrastructure.validation.PersistenceNotFoundErrorException;
import com.bitcoin_investment.core.command.domain.entity.account.Account;
import com.bitcoin_investment.core.command.ports.out.AccountCommandRepository;
import com.bitcoin_investment.infrastructure.persistence.AccountPersistence;
import com.bitcoin_investment.infrastructure.persistence.model.AccountModel;
import com.bitcoin_investment.infrastructure.persistence.model.BitcoinInvestmentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AccountCommandRepositoryImpl implements AccountCommandRepository {

	@Autowired
	private AccountPersistence accountPersistence;

	@Autowired
	private AccountPasswordCryptService accountPasswordCryptService;

	@Override
	@Transactional
	public void create(Account account, AccountPassword password) {
		AccountModel model = new AccountModel();
		model.setId(account.getId().getValue());
		model.setName(account.getName());
		model.setEmail(account.getEmail());
		model.setBalance(account.getBalance().getValue());
		model.setPassword(accountPasswordCryptService.encode(password.getValue()));
		model.setCreatedOn(account.getCreatedOn());

		var modelInvestments = account.getInvestments().stream().
				map(i -> this.mapToBitcoinInvestmentModel(i, model))
				.collect(Collectors.toSet());

		if (model.getInvestments() == null) {
			model.setInvestments(new HashSet<>());
		}

		model.getInvestments().clear();
		model.getInvestments().addAll(modelInvestments);
		accountPersistence.save(model);
	}

	@Override
	@Transactional
	public void update(Account account) {
		var accountModel = accountPersistence.findById(account.getId().getValue())
				.orElseThrow(PersistenceNotFoundErrorException::new);

		accountModel.setName(account.getName());
		accountModel.setEmail(account.getEmail());
		accountModel.setBalance(account.getBalance().getValue());
		accountModel.setVersion(account.getVersion());

		var modelInvestments = account.getInvestments().stream().
				map(i -> this.mapToBitcoinInvestmentModel(i, accountModel))
				.collect(Collectors.toSet());

		if (accountModel.getInvestments() == null) {
			accountModel.setInvestments(new HashSet<>());
		}

		accountModel.getInvestments().clear();
		accountModel.getInvestments().addAll(modelInvestments);
		accountPersistence.save(accountModel);
	}

	@Override
	@Transactional
	public Optional<Account> findByEmail(String email) {
		Optional<AccountModel> accountOptional = accountPersistence.findByEmail(email);

		return accountOptional.map(m -> new Account(
						m.getId(),
						m.getName(), m.getEmail(),
						m.getCreatedOn(),
						m.getBalance(), m.getInvestments().stream()
						.map(this::mapToBitcoinInvestment)
						.collect(Collectors.toSet()),
						m.getVersion()
				)
		);
	}

	@Override
	public Optional<AccountPassword> findPasswordByEmail(String email) {
		Optional<AccountModel> accountOptional = accountPersistence.findByEmail(email);
		return accountOptional.map(account -> new AccountPassword(account.getPassword()));
	}

	private BitcoinInvestment mapToBitcoinInvestment(BitcoinInvestmentModel investmentModel) {
		return new BitcoinInvestment(
				investmentModel.getId(),
				investmentModel.getInvestedAmount(),
				investmentModel.getBitcoinValueAtTheTimeOfPurchase(),
				investmentModel.getBitcoinAmount(),
				investmentModel.getStatus(),
				investmentModel.getPurchasedOn(),
				investmentModel.getSoldOn()
		);
	}

	private BitcoinInvestmentModel mapToBitcoinInvestmentModel(BitcoinInvestment investment, AccountModel accountModel) {
		var model = new BitcoinInvestmentModel();
		model.setId(investment.getId().getValue());
		model.setAccountModel(accountModel);
		model.setBitcoinAmount(investment.getBitcoinAmount());
		model.setBitcoinValueAtTheTimeOfPurchase(investment.getBitcoinValueAtTheTimeOfPurchase());
		model.setInvestedAmount(investment.getInvestedAmount());
		model.setStatus(investment.getStatus());
		model.setPurchasedOn(investment.getPurchasedOn());
		model.setSoldOn(investment.getSoldOn());
		return model;
	}

}
