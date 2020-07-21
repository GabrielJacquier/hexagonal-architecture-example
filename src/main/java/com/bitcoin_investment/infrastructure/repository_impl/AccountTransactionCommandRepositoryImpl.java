package com.bitcoin_investment.infrastructure.repository_impl;

import com.bitcoin_investment.core.command.ports.out.AccountTransactionalCommandRepository;
import com.bitcoin_investment.infrastructure.persistence.AccountTransactionPersistence;
import com.bitcoin_investment.core.command.domain.entity.account.AccountTransaction;
import com.bitcoin_investment.infrastructure.persistence.AccountPersistence;
import com.bitcoin_investment.infrastructure.persistence.model.AccountTransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountTransactionCommandRepositoryImpl implements AccountTransactionalCommandRepository {

	@Autowired
	private AccountTransactionPersistence accountTransactionPersistence;

	@Autowired
	private AccountPersistence accountPersistence;

	@Override
	public void create(AccountTransaction accountTransaction) {
		var transactionModel = new AccountTransactionModel();
		transactionModel.setId(accountTransaction.getId().getValue());
		transactionModel.setBitcoinSalePrice(accountTransaction.getBitcoinSalePrice());
		transactionModel.setBitcoinPurchasePrice(accountTransaction.getBitcoinPurchasePrice());
		transactionModel.setValue(accountTransaction.getValue());
		transactionModel.setAccountModel(accountPersistence.getOne(accountTransaction.getAccountId().getValue()));
		transactionModel.setCreatedOn(accountTransaction.getMadeOn());
		transactionModel.setType(accountTransaction.getType());
		accountTransactionPersistence.save(transactionModel);
	}

}
