package com.bitcoin_investment.core.command.domain.entity.account;

public class AccountTransactionId {
	private String value;

	protected AccountTransactionId(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
