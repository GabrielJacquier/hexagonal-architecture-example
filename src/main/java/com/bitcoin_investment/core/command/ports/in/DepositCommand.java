package com.bitcoin_investment.core.command.ports.in;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DepositCommand {

	@NotNull
	private BigDecimal value;

	@NotBlank
	@Email
	private String email;

	public DepositCommand(@NotNull BigDecimal value, @NotBlank @Email String email) {
		this.value = value;
		this.email = email;
	}

	public BigDecimal getValue() {
		return value;
	}

	public String getEmail() {
		return email;
	}
}
