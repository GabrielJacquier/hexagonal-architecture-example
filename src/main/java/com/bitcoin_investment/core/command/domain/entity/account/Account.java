package com.bitcoin_investment.core.command.domain.entity.account;

import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinInvestment;
import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinInvestmentStatus;
import com.bitcoin_investment.core.command.domain.validation.DomainValidation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Account {
	private AccountId id;
	private String name;
	private String email;
	private LocalDateTime createdOn;
	private AccountBalance balance;
	private Set<BitcoinInvestment> investments;
	private Integer version;

	public Account(String name, String email) {
		this.id = new AccountId(UUID.randomUUID().toString().toUpperCase());
		this.name = name;
		this.email = email;
		this.createdOn = LocalDateTime.now();
		this.balance = new AccountBalance(BigDecimal.valueOf(0));
		this.investments = new HashSet<>();
	}

	public Account(String id, String name, String email, LocalDateTime createdOn, BigDecimal balance, Set<BitcoinInvestment> investments, Integer version) {
		this.id = new AccountId(id);
		this.name = name;
		this.email = email;
		this.createdOn = createdOn;
		this.balance = new AccountBalance(balance);
		this.investments = investments;
		this.version = version;
	}

	public void doDeposit(RealAmount amount) {
		if (amount.getValue().doubleValue() == 0 || amount.getValue().doubleValue() < 0) {
			throw new DomainValidation("Deposit operation invalid. Value must be more than zero.");
		}
		this.balance = new AccountBalance(this.balance.getValue().add(amount.getValue()));
	}

	public void doWithdraw(RealAmount amount) {
		if (amount.getValue().doubleValue() == 0 || amount.getValue().doubleValue() < 0) {
			throw new DomainValidation("Withdraw operation invalid. Value must be more than zero.");
		}

		if (amount.getValue().compareTo(this.balance.getValue()) > 0) {
			throw new DomainValidation("Withdraw operation invalid. Value is bigger than available account balance.");
		}

		this.balance = new AccountBalance(this.balance.getValue().subtract(amount.getValue()));
	}

	public void purchaseInvestment(BitcoinInvestment investment) {
		investment.setStatus(BitcoinInvestmentStatus.PURCHASED);
		this.investments.add(investment);
	}

	public void saleInvestment(BitcoinInvestment investment) {
		investment.setStatus(BitcoinInvestmentStatus.SOLD);
		investment.setSoldOn(LocalDateTime.now());
		this.investments.add(investment);
	}

	public AccountId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Integer getVersion() {
		return version;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public AccountBalance getBalance() {
		return balance;
	}

	public List<BitcoinInvestment> getPurchasedInvestments() {
		return investments.stream()
				.filter(i -> i.getStatus().equals(BitcoinInvestmentStatus.PURCHASED))
				.collect(Collectors.toUnmodifiableList());
	}

	public List<BitcoinInvestment> getSortedPurchasedInvestments() {
		return investments.stream()
				.filter(i -> i.getStatus().equals(BitcoinInvestmentStatus.PURCHASED))
				.sorted(Comparator.comparing(BitcoinInvestment::getPurchasedOn))
				.collect(Collectors.toUnmodifiableList());
	}

	public List<BitcoinInvestment> getInvestments() {
		return investments.stream()
				.collect(Collectors.toUnmodifiableList());
	}

}
