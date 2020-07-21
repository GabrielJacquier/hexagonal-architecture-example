package com.bitcoin_investment.core.command.validator.validation;

public class AlreadyExistApplicationValidation extends RuntimeException {
	private String field;

	public AlreadyExistApplicationValidation(String field) {
		super("The entity already exist");
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
