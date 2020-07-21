package com.bitcoin_investment.infrastructure.persistence.model;

import com.bitcoin_investment.core.command.domain.entity.account.AccountTransactionType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACCOUNT_TRANSACTION")
public class AccountTransactionModel extends BaseModel {

	@Column(name = "TYPE")
	@Enumerated(value = EnumType.STRING)
	@NotNull
	private AccountTransactionType type;

	@JoinColumn(name = "ACCOUNT_ID")
	@ManyToOne
	@NotNull
	private AccountModel accountModel;

	@Column(name = "BITCOIN_SALE_PRICE")
	private BigDecimal bitcoinSalePrice;

	@Column(name = "BITCOIN_PURCHASE_PRICE")
	private BigDecimal bitcoinPurchasePrice;

	@Column(name = "VALUE")
	@NotNull
	private BigDecimal value;

	@Column(name = "CREATED_ON")
	@NotNull
	private LocalDateTime createdOn;

	public AccountTransactionType getType() {
		return type;
	}

	public void setType(AccountTransactionType type) {
		this.type = type;
	}

	public AccountModel getAccountModel() {
		return accountModel;
	}

	public void setAccountModel(AccountModel accountModel) {
		this.accountModel = accountModel;
	}

	public BigDecimal getBitcoinSalePrice() {
		return bitcoinSalePrice;
	}

	public void setBitcoinSalePrice(BigDecimal bitcoinSalePrice) {
		this.bitcoinSalePrice = bitcoinSalePrice;
	}

	public BigDecimal getBitcoinPurchasePrice() {
		return bitcoinPurchasePrice;
	}

	public void setBitcoinPurchasePrice(BigDecimal bitcoinPurchasePrice) {
		this.bitcoinPurchasePrice = bitcoinPurchasePrice;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
}
