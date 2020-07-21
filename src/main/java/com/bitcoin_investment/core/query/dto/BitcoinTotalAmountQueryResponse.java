package com.bitcoin_investment.core.query.dto;

import java.math.BigDecimal;

public class BitcoinTotalAmountQueryResponse {
	private BigDecimal purchaseAmount;
	private BigDecimal saleAmount;

	public BitcoinTotalAmountQueryResponse(BigDecimal purchaseAmount, BigDecimal saleAmount) {
		this.purchaseAmount = purchaseAmount;
		this.saleAmount = saleAmount;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
}
