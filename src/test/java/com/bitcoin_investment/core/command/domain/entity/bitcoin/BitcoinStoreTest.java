package com.bitcoin_investment.core.command.domain.entity.bitcoin;

import com.bitcoin_investment.core.command.domain.entity.account.RealAmount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BitcoinStoreTest {

	@Test
	public void successCalculateTwoInvestmentsToSale() {
		var bitcoinStore = new BitcoinStore(BigDecimal.valueOf(1002), BigDecimal.valueOf(1000));
		var investment = new BitcoinInvestment(BigDecimal.valueOf(100), BigDecimal.valueOf(0.2), BigDecimal.valueOf(500));
		var investment2 = new BitcoinInvestment(BigDecimal.valueOf(200), BigDecimal.valueOf(0.4), BigDecimal.valueOf(500));

		var plan = bitcoinStore.calculateRescuePlan(new RealAmount(BigDecimal.valueOf(300)), Arrays.asList(investment, investment2));
		assertEquals(2, plan.getInvestmentsToAccountSale().size(), "The investments quantity to sale is wrong");
	}

	@Test
	public void successCalculateOneInvestmentsToBuy() {
		var bitcoinStore = new BitcoinStore(BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
		var investment = new BitcoinInvestment(BigDecimal.valueOf(100), BigDecimal.valueOf(0.2), BigDecimal.valueOf(500));
		var investment2 = new BitcoinInvestment(BigDecimal.valueOf(200), BigDecimal.valueOf(0.4), BigDecimal.valueOf(500));

		var plan = bitcoinStore.calculateRescuePlan(new RealAmount(BigDecimal.valueOf(300)), Arrays.asList(investment, investment2));
		assertEquals(1, plan.getInvestmentsToAccountBuy().size(), "The investments quantity to buy is wrong");
	}

	@Test
	public void successRescueAmountFromTwoInvestments() {
		var bitcoinStore = new BitcoinStore(BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
		var investment = new BitcoinInvestment(BigDecimal.valueOf(100), BigDecimal.valueOf(0.2), BigDecimal.valueOf(500));
		var investment2 = new BitcoinInvestment(BigDecimal.valueOf(200), BigDecimal.valueOf(0.4), BigDecimal.valueOf(500));

		var plan = bitcoinStore.calculateRescuePlan(new RealAmount(BigDecimal.valueOf(300)), Arrays.asList(investment, investment2));
		assertEquals(BigDecimal.valueOf(150).doubleValue(), plan.getInvestmentsToAccountBuy().get(0).getInvestedAmount().doubleValue());
		assertEquals(BigDecimal.valueOf(0.3).doubleValue(), plan.getInvestmentsToAccountBuy().get(0).getBitcoinAmount().doubleValue());
	}

	static Stream<Arguments> successRemainingAmountToReinvestParam() {
		return Stream.of(
			Arguments.of(1000.50, 1000.50, 100.05, 0.2, 500.25, 10.07, 95.01),
			Arguments.of(1000.0, 1000.0, 100.25, 0.2, 500.0, 10.33, 95.08),
			Arguments.of(1000.0, 1000.0, 100.0, 0.2, 500.0, 10.33, 94.83)
		);
	}

	@ParameterizedTest(name = "Run {index} ")
	@MethodSource("successRemainingAmountToReinvestParam")
	public void successRemainingAmountToReinvest(
			Double bitcoinPriceSale,
			Double bitcoinPricePurchase,
			Double investedAmount,
			Double bitcoinAmount,
			Double bitcoinPrice,
			Double rescueValue,
			Double reinvestValue
	) {
		var bitcoinStore = new BitcoinStore(BigDecimal.valueOf(bitcoinPriceSale), BigDecimal.valueOf(bitcoinPricePurchase));
		var investment = new BitcoinInvestment(BigDecimal.valueOf(investedAmount), BigDecimal.valueOf(bitcoinAmount), BigDecimal.valueOf(bitcoinPrice));

		var plan = bitcoinStore.calculateRescuePlan(new RealAmount(BigDecimal.valueOf(rescueValue)), Arrays.asList(investment));
		assertEquals(BigDecimal.valueOf(reinvestValue).doubleValue(), plan.getInvestmentsToAccountBuy().get(0).getInvestedAmount().doubleValue());
	}

}
