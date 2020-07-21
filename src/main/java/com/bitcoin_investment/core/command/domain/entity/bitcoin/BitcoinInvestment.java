package com.bitcoin_investment.core.command.domain.entity.bitcoin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class BitcoinInvestment {
	private BitcoinInvestmentId id;
	private BigDecimal investedAmount;
	private BigDecimal bitcoinValueAtTheTimeOfPurchase;
	private BigDecimal bitcoinAmount;
	private BitcoinInvestmentStatus status;
	private LocalDateTime purchasedOn;
	private LocalDateTime soldOn;

	public BitcoinInvestment(BigDecimal investedAmount, BigDecimal bitcoinAmount, BigDecimal bitcoinValueAtTheTimeOfPurchase) {
		this.id = new BitcoinInvestmentId(UUID.randomUUID().toString().toUpperCase());
		this.investedAmount = investedAmount;
		this.bitcoinValueAtTheTimeOfPurchase = bitcoinValueAtTheTimeOfPurchase;
		this.bitcoinAmount = bitcoinAmount;
		this.status = BitcoinInvestmentStatus.PURCHASED;
		this.purchasedOn = LocalDateTime.now();
	}

	public BitcoinInvestment(String id, BigDecimal investedAmount, BigDecimal bitcoinValueAtTheTimeOfPurchase,
							 BigDecimal bitcoinAmount, BitcoinInvestmentStatus status, LocalDateTime purchasedOn, LocalDateTime soldOn) {
		this.id = new BitcoinInvestmentId(id);
		this.investedAmount = investedAmount;
		this.bitcoinValueAtTheTimeOfPurchase = bitcoinValueAtTheTimeOfPurchase;
		this.bitcoinAmount = bitcoinAmount;
		this.status = status;
		this.purchasedOn = purchasedOn;
		this.soldOn = soldOn;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BitcoinInvestment)) return false;
		BitcoinInvestment that = (BitcoinInvestment) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public BitcoinInvestmentId getId() {
		return id;
	}

	public BigDecimal getInvestedAmount() {
		return investedAmount;
	}

	public BigDecimal getBitcoinValueAtTheTimeOfPurchase() {
		return bitcoinValueAtTheTimeOfPurchase;
	}

	public BigDecimal getBitcoinAmount() {
		return bitcoinAmount;
	}

	public LocalDateTime getPurchasedOn() {
		return purchasedOn;
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
