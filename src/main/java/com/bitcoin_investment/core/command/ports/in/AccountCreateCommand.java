package com.bitcoin_investment.core.command.ports.in;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AccountCreateCommand {

	@NotBlank
	@Size(max = 200)
	private String name;

	@NotBlank
	@Email
	@Size(max = 50)
	private String email;

	@NotBlank
	@Size(max = 30)
	private String password;

	public AccountCreateCommand(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public AccountCreateCommand() {}

	public String getName() {
		return name;
	}


	public String getEmail() {
		return email;
	}


	public String getPassword() {
		return password;
	}

}
