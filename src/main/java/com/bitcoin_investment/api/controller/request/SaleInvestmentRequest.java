package com.bitcoin_investment.api.controller.request;

public class SaleInvestmentRequest {

	private Double valueToRescue;

	public SaleInvestmentRequest() {}

	public SaleInvestmentRequest(Double valueToRescue) {
		this.valueToRescue = valueToRescue;
	}

	public Double getValueToRescue() {
		return valueToRescue;
	}
}
