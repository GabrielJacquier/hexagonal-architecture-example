package com.bitcoin_investment.infrastructure.repository_impl;

import com.bitcoin_investment.core.query.dto.AccountBalanceQueryResponse;
import com.bitcoin_investment.core.query.dto.AccountTransactionQueryResponse;
import com.bitcoin_investment.core.query.repository.AccountQueryRepository;
import com.bitcoin_investment.infrastructure.persistence.AccountTransactionPersistence;
import com.bitcoin_investment.infrastructure.persistence.AccountPersistence;
import com.bitcoin_investment.infrastructure.validation.PersistenceNotFoundErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AccountQueryRepositoryImpl implements AccountQueryRepository {

	@Autowired
	private AccountPersistence accountPersistence;

	@Autowired
	private AccountTransactionPersistence accountTransactionPersistence;

	@Override
	public AccountBalanceQueryResponse findBalanceByEmail(String email) {
		var accountModel = accountPersistence.findByEmail(email)
				.orElseThrow(PersistenceNotFoundErrorException::new);

		return new AccountBalanceQueryResponse(accountModel.getBalance());
	}

	@Override
	public List<AccountTransactionQueryResponse> findTransactionsByEmail(String email, LocalDateTime initialDate, LocalDateTime finalDate) {
		var accountModel = accountPersistence.findByEmail(email)
				.orElseThrow(PersistenceNotFoundErrorException::new);

		return accountTransactionPersistence.findBy(accountModel.getId(), initialDate, finalDate)
				.stream().map(at -> new AccountTransactionQueryResponse(at.getCreatedOn(),
						at.getType().name(), at.getValue(),
						at.getBitcoinSalePrice(),
						at.getBitcoinPurchasePrice()))
				.collect(Collectors.toList());
	}

}
