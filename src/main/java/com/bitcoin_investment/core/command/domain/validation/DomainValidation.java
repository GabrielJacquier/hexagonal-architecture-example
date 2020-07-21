package com.bitcoin_investment.core.command.domain.validation;

public class DomainValidation extends RuntimeException {

	public DomainValidation(String message) {
		super(message);
	}

}
