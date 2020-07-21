package com.bitcoin_investment.core.command.ports.in;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BuyInvestmentCommand {

	@NotNull
	private BigDecimal valueToBuy;

	@NotBlank
	@Email
	private String accountEmail;

	public BuyInvestmentCommand(BigDecimal valueToBuy, String accountEmail) {
		this.valueToBuy = valueToBuy;
		this.accountEmail = accountEmail;
	}

	public BigDecimal getValueToBuy() {
		return valueToBuy;
	}

	public String getAccountEmail() {
		return accountEmail;
	}
}
