package com.bitcoin_investment.api.authentication;

public class AccountAuthenticated {
	private String email;

	public AccountAuthenticated(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
