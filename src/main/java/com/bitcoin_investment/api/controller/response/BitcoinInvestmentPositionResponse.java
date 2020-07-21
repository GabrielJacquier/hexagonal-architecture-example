package com.bitcoin_investment.api.controller.response;

import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinInvestmentPosition;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BitcoinInvestmentPositionResponse {
	private BigDecimal investedAmount;
	private BigDecimal bitcoinValueAtTheTimeOfPurchase;
	private BigDecimal bitcoinPriceVariationPercent;
	private BigDecimal investmentTotalGrossValue;
	private LocalDateTime createdOn;

	public BitcoinInvestmentPositionResponse(BitcoinInvestmentPosition bitcoinInvestmentPosition) {
		this.investedAmount = bitcoinInvestmentPosition.getBitcoinInvestment().getInvestedAmount();
		this.bitcoinValueAtTheTimeOfPurchase = bitcoinInvestmentPosition.getBitcoinInvestment().getBitcoinValueAtTheTimeOfPurchase();
		this.bitcoinPriceVariationPercent = bitcoinInvestmentPosition.getBitcoinPriceVariationPercent();
		this.investmentTotalGrossValue = bitcoinInvestmentPosition.getInvestmentTotalGrossValue();
		this.createdOn = bitcoinInvestmentPosition.getBitcoinInvestment().getPurchasedOn();
	}

	public BitcoinInvestmentPositionResponse() {}

	public BigDecimal getInvestedAmount() {
		return investedAmount;
	}

	public BigDecimal getBitcoinValueAtTheTimeOfPurchase() {
		return bitcoinValueAtTheTimeOfPurchase;
	}

	public BigDecimal getBitcoinPriceVariationPercent() {
		return bitcoinPriceVariationPercent;
	}

	public BigDecimal getInvestmentTotalGrossValue() {
		return investmentTotalGrossValue;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
}
