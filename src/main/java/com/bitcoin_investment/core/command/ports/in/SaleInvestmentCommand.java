package com.bitcoin_investment.core.command.ports.in;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SaleInvestmentCommand {

	@NotNull
	private BigDecimal valueToRescue;

	@NotBlank
	@Email
	private String accountEmail;

	public SaleInvestmentCommand(BigDecimal valueToRescue, String accountEmail) {
		this.valueToRescue = valueToRescue;
		this.accountEmail = accountEmail;
	}

	public BigDecimal getValueToRescue() {
		return valueToRescue;
	}

	public String getAccountEmail() {
		return accountEmail;
	}
}
