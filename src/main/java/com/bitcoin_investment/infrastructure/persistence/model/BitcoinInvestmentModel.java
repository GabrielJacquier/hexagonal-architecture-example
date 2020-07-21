package com.bitcoin_investment.infrastructure.persistence.model;

import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinInvestmentStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "BITCOIN_INVESTMENT")
public class BitcoinInvestmentModel extends BaseModel {

	@JoinColumn(name = "ACCOUNT_ID")
	@ManyToOne
	@NotNull
	private AccountModel accountModel;

	@Column(name = "INVESTED_AMOUNT")
	@NotNull
	private BigDecimal investedAmount;

	@Column(name = "BITCOIN_VALUE_AT_THE_TIME_OF_PURCHASE")
	@NotNull
	private BigDecimal bitcoinValueAtTheTimeOfPurchase;

	@Column(name = "BITCOIN_AMOUNT")
	@NotNull
	private BigDecimal bitcoinAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	@NotNull
	private BitcoinInvestmentStatus status;

	@Column(name = "PURCHASED_ON")
	@NotNull
	private LocalDateTime purchasedOn;

	@Column(name = "SOLD_ON")
	private LocalDateTime soldOn;

	public AccountModel getAccountModel() {
		return accountModel;
	}

	public void setAccountModel(AccountModel accountModel) {
		this.accountModel = accountModel;
	}

	public BigDecimal getInvestedAmount() {
		return investedAmount;
	}

	public void setInvestedAmount(BigDecimal investedAmount) {
		this.investedAmount = investedAmount;
	}

	public BigDecimal getBitcoinValueAtTheTimeOfPurchase() {
		return bitcoinValueAtTheTimeOfPurchase;
	}

	public void setBitcoinValueAtTheTimeOfPurchase(BigDecimal bitcoinValueAtTheTimeOfPurchase) {
		this.bitcoinValueAtTheTimeOfPurchase = bitcoinValueAtTheTimeOfPurchase;
	}

	public BigDecimal getBitcoinAmount() {
		return bitcoinAmount;
	}

	public void setBitcoinAmount(BigDecimal bitcoinAmount) {
		this.bitcoinAmount = bitcoinAmount;
	}

	public LocalDateTime getPurchasedOn() {
		return purchasedOn;
	}

	public void setPurchasedOn(LocalDateTime purchasedOn) {
		this.purchasedOn = purchasedOn;
	}

	public BitcoinInvestmentStatus getStatus() {
		return status;
	}

	public void setStatus(BitcoinInvestmentStatus status) {
		this.status = status;
	}

	public LocalDateTime getSoldOn() {
		return soldOn;
	}

	public void setSoldOn(LocalDateTime soldOn) {
		this.soldOn = soldOn;
	}
}
