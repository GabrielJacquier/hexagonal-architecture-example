package com.bitcoin_investment.core.command.service.event;

import com.bitcoin_investment.core.command.domain.entity.account.AccountId;
import com.bitcoin_investment.core.command.domain.entity.account.RealAmount;

import java.math.BigDecimal;

public class AccountPurchaseDone {
	private AccountId accountId;
	private RealAmount valuePurchased;
	private BigDecimal bitcoinSalePrice;
	private BigDecimal bitcoinPurchasePrice;
	private BigDecimal bitcoinAmount;

	public AccountPurchaseDone(AccountId accountId, RealAmount valuePurchased, BigDecimal bitcoinSalePrice, BigDecimal bitcoinPurchasePrice, BigDecimal bitcoinAmount) {
		this.accountId = accountId;
		this.valuePurchased = valuePurchased;
		this.bitcoinSalePrice = bitcoinSalePrice;
		this.bitcoinPurchasePrice = bitcoinPurchasePrice;
		this.bitcoinAmount = bitcoinAmount;
	}

	public AccountId getAccountId() {
		return accountId;
	}

	public RealAmount getValuePurchased() {
		return valuePurchased;
	}

	public BigDecimal getBitcoinSalePrice() {
		return bitcoinSalePrice;
	}

	public BigDecimal getBitcoinAmount() {
		return bitcoinAmount;
	}

	public BigDecimal getBitcoinPurchasePrice() {
		return bitcoinPurchasePrice;
	}
}
