package com.bitcoin_investment.core.command.domain.entity.bitcoin;

public class BitcoinInvestmentId {
	private String value;

	protected BitcoinInvestmentId(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
