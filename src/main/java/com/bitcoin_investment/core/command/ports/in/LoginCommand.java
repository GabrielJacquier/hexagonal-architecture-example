package com.bitcoin_investment.core.command.ports.in;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginCommand {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String password;

	public LoginCommand(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
