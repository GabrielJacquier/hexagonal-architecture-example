package com.bitcoin_investment.core.command.service.event;

import com.bitcoin_investment.core.command.domain.entity.account.AccountId;
import com.bitcoin_investment.core.command.domain.entity.account.RealAmount;

public class AccountDepositDone {
	private AccountId accountId;
	private RealAmount valueDeposited;

	public AccountDepositDone(AccountId accountId, RealAmount valueDeposited) {
		this.accountId = accountId;
		this.valueDeposited = valueDeposited;
	}

	public AccountId getAccountId() {
		return accountId;
	}

	public RealAmount getValueDeposited() {
		return valueDeposited;
	}
}
