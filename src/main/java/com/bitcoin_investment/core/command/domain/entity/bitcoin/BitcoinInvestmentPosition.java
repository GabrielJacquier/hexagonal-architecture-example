package com.bitcoin_investment.core.command.domain.entity.bitcoin;

import java.math.BigDecimal;

public class BitcoinInvestmentPosition {
	private BitcoinInvestment bitcoinInvestment;
	private BigDecimal bitcoinPriceVariationPercent;
	private BigDecimal investmentTotalGrossValue;

	public BitcoinInvestmentPosition(BitcoinInvestment bitcoinInvestment, BigDecimal bitcoinPriceVariationPercent, BigDecimal investmentTotalGrossValue) {
		this.bitcoinInvestment = bitcoinInvestment;
		this.bitcoinPriceVariationPercent = bitcoinPriceVariationPercent;
		this.investmentTotalGrossValue = investmentTotalGrossValue;
	}

	public BitcoinInvestment getBitcoinInvestment() {
		return bitcoinInvestment;
	}

	public BigDecimal getBitcoinPriceVariationPercent() {
		return bitcoinPriceVariationPercent;
	}

	public BigDecimal getInvestmentTotalGrossValue() {
		return investmentTotalGrossValue;
	}
}
