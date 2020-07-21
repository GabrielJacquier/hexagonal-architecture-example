package com.bitcoin_investment.core.command.service.event;

import com.bitcoin_investment.core.command.domain.entity.account.RealAmount;
import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinInvestment;
import com.bitcoin_investment.core.command.domain.entity.account.AccountId;

import java.math.BigDecimal;
import java.util.List;

public class AccountSaleDone {
	private AccountId accountId;
	private RealAmount valueRescue;
	private BigDecimal bitcoinSalePrice;
	private BigDecimal bitcoinPurchasePrice;
	private List<BitcoinInvestment> investmentsSold;

	public AccountSaleDone(AccountId accountId, RealAmount valueRescue, BigDecimal bitcoinSalePrice, BigDecimal bitcoinPurchasePrice, List<BitcoinInvestment> investmentsSold) {
		this.accountId = accountId;
		this.valueRescue = valueRescue;
		this.bitcoinSalePrice = bitcoinSalePrice;
		this.bitcoinPurchasePrice = bitcoinPurchasePrice;
		this.investmentsSold = investmentsSold;
	}

	public AccountId getAccountId() {
		return accountId;
	}

	public RealAmount getValueRescue() {
		return valueRescue;
	}

	public BigDecimal getBitcoinSalePrice() {
		return bitcoinSalePrice;
	}

	public BigDecimal getBitcoinPurchasePrice() {
		return bitcoinPurchasePrice;
	}

	public List<BitcoinInvestment> getInvestmentsSold() {
		return investmentsSold;
	}
}
