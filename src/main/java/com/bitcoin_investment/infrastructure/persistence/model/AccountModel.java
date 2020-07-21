package com.bitcoin_investment.infrastructure.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT")
public class AccountModel extends BaseModel {

	@Column(name = "NAME")
	@NotBlank
	@Size(max = 200)
	private String name;

	@Column(name = "EMAIL")
	@NotBlank
	@Size(max = 50)
	private String email;

	@Column(name = "PASSWORD")
	@NotBlank
	@Size(max = 200)
	private String password;

	@Column(name = "BALANCE")
	@NotNull
	private BigDecimal balance;

	@Column(name = "CREATED_ON")
	private LocalDateTime createdOn;

	@OneToMany(mappedBy = "accountModel", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<BitcoinInvestmentModel> investments;

	@Version
	private Integer version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public Set<BitcoinInvestmentModel> getInvestments() {
		return investments;
	}

	public void setInvestments(Set<BitcoinInvestmentModel> investments) {
		this.investments = investments;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
