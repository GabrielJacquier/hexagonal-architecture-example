package com.bitcoin_investment.api.controller.response;

import java.math.BigDecimal;

public class BitcoinPriceResponse {
	private BigDecimal buyPrice;
	private BigDecimal salePrice;

	public BitcoinPriceResponse(BigDecimal buyPrice, BigDecimal salePrice) {
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
