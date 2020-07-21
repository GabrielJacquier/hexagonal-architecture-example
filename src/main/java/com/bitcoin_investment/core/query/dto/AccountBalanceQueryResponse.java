package com.bitcoin_investment.core.query.dto;

import java.math.BigDecimal;

public class AccountBalanceQueryResponse {
	private BigDecimal balance;

	public AccountBalanceQueryResponse(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalance() {
		return balance;
	}
}
