package com.bitcoin_investment.core.command.service;

import com.bitcoin_investment.core.command.ports.out.AccountTransactionalCommandRepository;
import com.bitcoin_investment.core.command.service.event.AccountDepositDone;
import com.bitcoin_investment.core.command.domain.entity.account.AccountTransaction;
import com.bitcoin_investment.core.command.domain.entity.account.AccountTransactionType;
import com.bitcoin_investment.core.command.service.event.AccountPurchaseDone;
import com.bitcoin_investment.core.command.service.event.AccountSaleDone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AccountTransactionEventListenerService {
	private Logger logger = LoggerFactory.getLogger(AccountTransactionEventListenerService.class);

	@Autowired
	private AccountTransactionalCommandRepository accountTransactionalCommandRepository;

	@EventListener
	@Async
	public void createTransactionLogWhenNewDepositIsDone(AccountDepositDone accountDepositDone) {
		AccountTransaction transaction = new AccountTransaction(accountDepositDone.getAccountId(),
				accountDepositDone.getValueDeposited(), AccountTransactionType.DEPOSIT);

		accountTransactionalCommandRepository.create(transaction);
		logger.info("Deposit transaction log created.");
	}

	@EventListener
	@Async
	public void createTransactionLogWhenNewPurchaseIsDone(AccountPurchaseDone accountPurchaseDone) {
		AccountTransaction transaction = new AccountTransaction(accountPurchaseDone.getAccountId(),
				accountPurchaseDone.getValuePurchased(),
				accountPurchaseDone.getBitcoinSalePrice(),
				accountPurchaseDone.getBitcoinPurchasePrice(),
				AccountTransactionType.PURCHASE);

		accountTransactionalCommandRepository.create(transaction);
		logger.info("Purchase transaction log created.");
	}

	@EventListener
	@Async
	public void createTransactionLogWhenNewSaleIsDone(AccountSaleDone accountSaleDone) {
		AccountTransaction transaction = new AccountTransaction(accountSaleDone.getAccountId(),
				accountSaleDone.getValueRescue(),
				accountSaleDone.getBitcoinSalePrice(),
				accountSaleDone.getBitcoinPurchasePrice(),
				AccountTransactionType.SALE);

		accountTransactionalCommandRepository.create(transaction);
		logger.info("Sale transaction log created.");
	}

}
