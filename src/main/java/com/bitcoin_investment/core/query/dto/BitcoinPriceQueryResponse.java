package com.bitcoin_investment.core.query.dto;

import java.math.BigDecimal;

public class BitcoinPriceQueryResponse {
	private BigDecimal buyPrice;
	private BigDecimal salePrice;

	public BitcoinPriceQueryResponse(BigDecimal buyPrice, BigDecimal salePrice) {
		this.buyPrice = buyPrice;
		this.salePrice = salePrice;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}
}
