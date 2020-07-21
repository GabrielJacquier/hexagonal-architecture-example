package com.bitcoin_investment.api.controller.response;

import com.bitcoin_investment.core.query.dto.AccountTransactionQueryResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTransactionResponse {
	private LocalDateTime date;
	private String type;
	private BigDecimal value;
	private BigDecimal bitcoinSalePrice;
	private BigDecimal bitcoinPurchasePrice;

	public AccountTransactionResponse(AccountTransactionQueryResponse accountTransactionQueryResponse) {
		this.date = accountTransactionQueryResponse.getDate();
		this.type = accountTransactionQueryResponse.getType();
		this.value = accountTransactionQueryResponse.getValue();
		this.bitcoinSalePrice = accountTransactionQueryResponse.getBitcoinSalePrice();
		this.bitcoinPurchasePrice = accountTransactionQueryResponse.getBitcoinPurchasePrice();
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

	public BigDecimal getBitcoinPurchasePrice() {
		return bitcoinPurchasePrice;
	}

	public String getType() {
		return type;
	}
}
