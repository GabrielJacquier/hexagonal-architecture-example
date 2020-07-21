package com.bitcoin_investment.core.command.domain.entity.account;

public class AccountId {
	private String value;

	protected AccountId(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
