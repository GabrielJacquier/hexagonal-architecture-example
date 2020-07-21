package com.bitcoin_investment.core.command.domain.entity.bitcoin;

import com.bitcoin_investment.core.command.domain.entity.account.RealAmount;
import com.bitcoin_investment.core.command.domain.validation.DomainValidation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BitcoinStore {
	private BigDecimal sellPrice;
	private BigDecimal buyPrice;

	public BitcoinStore(BigDecimal sellPrice, BigDecimal buyPrice) {
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
	}

	public BitcoinInvestment sellInvestmentByRealAmount(RealAmount realAmount) {
		var bitcoinAmount = realAmount.getValue().divide(sellPrice, 20, RoundingMode.DOWN);
		return new BitcoinInvestment(realAmount.getValue(), bitcoinAmount, sellPrice);
	}

	public BitcoinRescuePlan calculateRescuePlan(RealAmount amountToRescue, List<BitcoinInvestment> investments) {
		if (investments == null || investments.isEmpty()) {
			throw new DomainValidation("The account does not have investments.");
		}

		if (amountToRescue == null || amountToRescue.getValue().compareTo(BigDecimal.ZERO) == 0) {
			throw new DomainValidation("It is not possible to rescue the value 0.");
		}

		var rescueInvestmentAssistant = new BitcoinStoreRescueInvestmentAssistant(amountToRescue.getValue());
		var orderedInvestmentPositions = calculateOrderedInvestmentPositions(investments);
		orderedInvestmentPositions.forEach(rescueInvestmentAssistant::addIfNeedsMoreInvestment);

		if (rescueInvestmentAssistant.hasNotSufficientInvestmentsToRescue()) {
			throw new DomainValidation("The account does not has investments sufficient to rescue this currency amount.");
		}

		var investmentsForAccountToBuy = new ArrayList<BitcoinInvestment>();
		if (rescueInvestmentAssistant.isNecessaryToReinvest()) {
			var lastInvestmentToSale = rescueInvestmentAssistant.lastInvestmentPositionToSale();
			var amountToRescueFromLastInvestment = rescueInvestmentAssistant.getRescueValueFromLastInvestment();
			var newInvestment = reinvestRemainingAmount(lastInvestmentToSale, amountToRescueFromLastInvestment);
			investmentsForAccountToBuy.add(newInvestment);
		}
		return new BitcoinRescuePlan(rescueInvestmentAssistant.getInvestmentsToSale(), investmentsForAccountToBuy);
	}

	private List<BitcoinInvestmentPosition> calculateOrderedInvestmentPositions(List<BitcoinInvestment> investments) {
		return new ArrayList<>(investments).stream()
				.sorted(Comparator.comparing(BitcoinInvestment::getPurchasedOn))
				.map(this::calculateInvestmentPosition)
				.collect(Collectors.toList());
	}

	private BitcoinInvestment reinvestRemainingAmount(BitcoinInvestmentPosition investmentPosition, BigDecimal amountToRescue) {
		var reinvestmentPercent = investmentPosition.getInvestmentTotalGrossValue().subtract(amountToRescue)
				.divide(investmentPosition.getInvestmentTotalGrossValue(), 20, RoundingMode.DOWN);
		var bitcoinAmountToReinvest = investmentPosition.getBitcoinInvestment().getBitcoinAmount().multiply(reinvestmentPercent);
		var realAmountToReinvest = investmentPosition.getBitcoinInvestment().getInvestedAmount()
				.multiply(reinvestmentPercent).setScale(2, RoundingMode.HALF_DOWN);
		return new BitcoinInvestment(realAmountToReinvest, bitcoinAmountToReinvest,
				investmentPosition.getBitcoinInvestment().getBitcoinValueAtTheTimeOfPurchase());
	}

	public BitcoinInvestmentPosition calculateInvestmentPosition(BitcoinInvestment investment) {
		var bitcoinPriceVariation = buyPrice.subtract(investment.getBitcoinValueAtTheTimeOfPurchase());
		var bitcoinPricePercentVariation = bitcoinPriceVariation.divide(investment.getBitcoinValueAtTheTimeOfPurchase(), 4, RoundingMode.DOWN);
		var investmentTotalGrossValue = investment.getInvestedAmount()
				.multiply(BigDecimal.ONE.add(bitcoinPricePercentVariation)).setScale(2, RoundingMode.DOWN);
		return new BitcoinInvestmentPosition(investment, bitcoinPricePercentVariation, investmentTotalGrossValue);
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
}
