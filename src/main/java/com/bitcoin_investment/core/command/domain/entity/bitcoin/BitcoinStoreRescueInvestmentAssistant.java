package com.bitcoin_investment.core.command.domain.entity.bitcoin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BitcoinStoreRescueInvestmentAssistant {
	private BigDecimal amountToSale = BigDecimal.ZERO;
	private BigDecimal amountToRescue;
	private List<BitcoinInvestmentPosition> investmentsPositionsToSale = new ArrayList<>();

	public BitcoinStoreRescueInvestmentAssistant(BigDecimal amountToRescue) {
		this.amountToRescue = amountToRescue;
	}

	public void addIfNeedsMoreInvestment(BitcoinInvestmentPosition newInvestmentPosition) {
		if (hasNotSufficientInvestmentsToRescue()) {
			investmentsPositionsToSale.add(newInvestmentPosition);
			incrementAmountSale(newInvestmentPosition.getInvestmentTotalGrossValue());
		}
	}

	public void incrementAmountSale(BigDecimal value) {
		this.amountToSale = this.amountToSale.add(value);
	}

	public boolean hasNotSufficientInvestmentsToRescue() {
		return this.amountToSale.compareTo(amountToRescue) < 0;
	}

	public boolean isNecessaryToReinvest() {
		return this.amountToSale.compareTo(amountToRescue) > 0;
	}

	public List<BitcoinInvestmentPosition> getInvestmentsPositionsToSale() {
		return investmentsPositionsToSale;
	}

	public List<BitcoinInvestment> getInvestmentsToSale() {
		return investmentsPositionsToSale.stream()
				.map(BitcoinInvestmentPosition::getBitcoinInvestment)
				.collect(Collectors.toList());
	}

	public BitcoinInvestmentPosition lastInvestmentPositionToSale() {
		return investmentsPositionsToSale.get(investmentsPositionsToSale.size() - 1);
	}

	public BigDecimal getRescueValueFromLastInvestment() {
		return lastInvestmentPositionToSale().getInvestmentTotalGrossValue()
				.subtract(amountToSale.subtract(amountToRescue));
	}
}
