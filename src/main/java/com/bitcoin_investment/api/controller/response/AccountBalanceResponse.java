package com.bitcoin_investment.api.controller.response;

import java.math.BigDecimal;

public class AccountBalanceResponse {
	private BigDecimal balance;

	public AccountBalanceResponse() {}

	public AccountBalanceResponse(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalance() {
		return balance;
	}
}