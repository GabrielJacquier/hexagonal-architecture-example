package com.bitcoin_investment.api.controller.request;

public class DepositRequest {
	private Double value;

	public DepositRequest() {
	}

	public DepositRequest(Double value) {
		this.value = value;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
