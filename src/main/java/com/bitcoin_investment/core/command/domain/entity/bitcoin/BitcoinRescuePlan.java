package com.bitcoin_investment.core.command.domain.entity.bitcoin;

import java.util.List;

public class BitcoinRescuePlan {
	private List<BitcoinInvestment> investmentsToAccountSale;
	private List<BitcoinInvestment> investmentsToAccountBuy;

	public BitcoinRescuePlan(List<BitcoinInvestment> investmentsToAccountSale, List<BitcoinInvestment> investmentsToAccountBuy) {
		this.investmentsToAccountSale = investmentsToAccountSale;
		this.investmentsToAccountBuy = investmentsToAccountBuy;
	}

	public List<BitcoinInvestment> getInvestmentsToAccountSale() {
		return investmentsToAccountSale;
	}

	public List<BitcoinInvestment> getInvestmentsToAccountBuy() {
		return investmentsToAccountBuy;
	}
}
