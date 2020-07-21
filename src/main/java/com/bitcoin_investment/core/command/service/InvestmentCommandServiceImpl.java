package com.bitcoin_investment.core.command.service;

import com.bitcoin_investment.core.command.ports.out.BitcoinStoreCommandRepository;
import com.bitcoin_investment.core.command.domain.entity.account.RealAmount;
import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinInvestmentPosition;
import com.bitcoin_investment.core.command.ports.in.BuyInvestmentCommand;
import com.bitcoin_investment.core.command.ports.in.InvestmentCommandService;
import com.bitcoin_investment.core.command.ports.in.SaleInvestmentCommand;
import com.bitcoin_investment.core.command.ports.out.AccountCommandRepository;
import com.bitcoin_investment.core.command.service.event.AccountPurchaseDone;
import com.bitcoin_investment.core.command.service.event.AccountSaleDone;
import com.bitcoin_investment.core.command.validator.AccountValidator;
import com.bitcoin_investment.core.command.validator.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestmentCommandServiceImpl implements InvestmentCommandService {

	@Autowired
	private AccountValidator accountValidator;

	@Autowired
	private BaseValidator baseValidator;

	@Autowired
	private BitcoinStoreCommandRepository bitcoinStoreCommandRepository;

	@Autowired
	private AccountCommandRepository accountCommandRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Override
	public void buyInvestment(BuyInvestmentCommand buyInvestmentCommand) {
		baseValidator.validateCommand(buyInvestmentCommand);
		var bitcoinStore = bitcoinStoreCommandRepository.getStore();
		var account = accountValidator.findByEmailOrThrowValidation(buyInvestmentCommand.getAccountEmail());

		var realAmountToWithdraw = new RealAmount(buyInvestmentCommand.getValueToBuy());
		account.doWithdraw(realAmountToWithdraw);
		var newInvestment = bitcoinStore.sellInvestmentByRealAmount(realAmountToWithdraw);
		account.purchaseInvestment(newInvestment);

		accountCommandRepository.update(account);
		eventPublisher.publishEvent(new AccountPurchaseDone(account.getId(),
				realAmountToWithdraw,
				bitcoinStore.getSellPrice(),
				bitcoinStore.getBuyPrice(),
				newInvestment.getBitcoinAmount()));
	}

	@Override
	public void saleInvestment(SaleInvestmentCommand saleInvestmentCommand) {
		baseValidator.validateCommand(saleInvestmentCommand);
		var bitcoinStore = bitcoinStoreCommandRepository.getStore();
		var account = accountValidator.findByEmailOrThrowValidation(saleInvestmentCommand.getAccountEmail());
		var realAmountToRescue = new RealAmount(saleInvestmentCommand.getValueToRescue());
		var rescuePlan = bitcoinStore.calculateRescuePlan(realAmountToRescue, account.getPurchasedInvestments());

		rescuePlan.getInvestmentsToAccountSale()
				.forEach(account::saleInvestment);

		rescuePlan.getInvestmentsToAccountBuy()
				.forEach(account::purchaseInvestment);

		account.doDeposit(realAmountToRescue);
		accountCommandRepository.update(account);

		eventPublisher.publishEvent(new AccountSaleDone(account.getId(),
				realAmountToRescue,
				bitcoinStore.getSellPrice(),
				bitcoinStore.getBuyPrice(),
				rescuePlan.getInvestmentsToAccountSale()));
	}

	@Override
	public List<BitcoinInvestmentPosition> calculateInvestmentsPosition(String accountEmail) {
		var bitcoinStore = bitcoinStoreCommandRepository.getStore();
		var account = accountValidator.findByEmailOrThrowValidation(accountEmail);
		return account.getSortedPurchasedInvestments().stream()
				.map(bitcoinStore::calculateInvestmentPosition)
				.collect(Collectors.toList());
	}

}
