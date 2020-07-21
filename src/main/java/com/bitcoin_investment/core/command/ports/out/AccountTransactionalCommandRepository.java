package com.bitcoin_investment.core.command.ports.out;

import com.bitcoin_investment.core.command.domain.entity.account.AccountTransaction;

public interface AccountTransactionalCommandRepository {

	void create(AccountTransaction accountTransaction);

}
