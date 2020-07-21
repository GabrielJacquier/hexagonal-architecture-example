package com.bitcoin_investment.core.query.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTransactionQueryResponse {
	private LocalDateTime date;
	private String type;
	private BigDecimal value;
	private BigDecimal bitcoinSalePrice;
	private BigDecimal bitcoinPurchasePrice;

	public AccountTransactionQueryResponse(LocalDateTime date, String type, BigDecimal value, BigDecimal bitcoinSalePrice, BigDecimal bitcoinPurchasePrice) {
		this.date = date;
		this.type = type;
		this.value = value;
		this.bitcoinSalePrice = bitcoinSalePrice;
		this.bitcoinPurchasePrice = bitcoinPurchasePrice;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public BigDecimal getBitcoinSalePrice() {
		return bitcoinSalePrice;
	}

	public String getType() {
		return type;
	}

	public BigDecimal getBitcoinPurchasePrice() {
		return bitcoinPurchasePrice;
	}
}
