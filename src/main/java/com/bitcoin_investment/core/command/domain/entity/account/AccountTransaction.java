package com.bitcoin_investment.core.command.domain.entity.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AccountTransaction {

	private AccountTransactionId id;
	private AccountId accountId;
	private BigDecimal value;
	private BigDecimal bitcoinSalePrice;
	private BigDecimal bitcoinPurchasePrice;
	private LocalDateTime madeOn;
	private AccountTransactionType type;

	public AccountTransaction(AccountId accountId, RealAmount amountValue, AccountTransactionType type) {
		this.id = new AccountTransactionId(UUID.randomUUID().toString().toUpperCase());
		this.accountId = accountId;
		this.value = amountValue.getValue();
		this.madeOn = LocalDateTime.now();
		this.type = type;
	}

	public AccountTransaction(AccountId accountId, RealAmount amountValue, BigDecimal bitcoinSalePrice, BigDecimal bitcoinPurchasePrice, AccountTransactionType type) {
		this(accountId, amountValue, type);
		this.bitcoinSalePrice = bitcoinSalePrice;
		this.bitcoinPurchasePrice = bitcoinPurchasePrice;
	}

	public AccountTransactionId getId() {
		return id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public LocalDateTime getMadeOn() {
		return madeOn;
	}

	public AccountTransactionType getType() {
		return type;
	}

	public AccountId getAccountId() {
		return accountId;
	}

	public BigDecimal getBitcoinSalePrice() {
		return bitcoinSalePrice;
	}

	public BigDecimal getBitcoinPurchasePrice() {
		return bitcoinPurchasePrice;
	}
}
