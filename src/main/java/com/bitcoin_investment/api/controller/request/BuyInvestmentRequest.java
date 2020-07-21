package com.bitcoin_investment.api.controller.request;

public class BuyInvestmentRequest {

	private Double valueToInvest;

	public BuyInvestmentRequest() {}

	public BuyInvestmentRequest(Double valueToInvest) {
		this.valueToInvest = valueToInvest;
	}

	public Double getValueToInvest() {
		return valueToInvest;
	}
}
