package com.bitcoin_investment.core.command.domain.entity.account;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RealAmount {
	private BigDecimal value;

	public RealAmount(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValue() {
		return value.setScale(2, RoundingMode.DOWN);
	}
}
