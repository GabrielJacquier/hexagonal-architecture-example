package com.bitcoin_investment.api.controller.response;

import com.bitcoin_investment.core.query.dto.BitcoinTotalAmountQueryResponse;

import java.math.BigDecimal;

public class BitcoinTotalAmountResponse {
	private BigDecimal purchaseAmount;
	private BigDecimal saleAmount;

	public BitcoinTotalAmountResponse(BitcoinTotalAmountQueryResponse amountQueryResponse) {
		this.purchaseAmount = amountQueryResponse.getPurchaseAmount();
		this.saleAmount = amountQueryResponse.getSaleAmount();
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
}
