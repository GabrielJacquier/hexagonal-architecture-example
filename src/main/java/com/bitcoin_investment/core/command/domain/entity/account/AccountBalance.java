package com.bitcoin_investment.core.command.domain.entity.account;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AccountBalance {
	private BigDecimal value;

	protected AccountBalance(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValue() {
		return value;
	}
}
