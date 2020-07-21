package com.bitcoin_investment.core.command.validator.validation;

public class InvalidFieldApplicationValidation extends RuntimeException {
	private String field;

	public InvalidFieldApplicationValidation(String message, String field) {
		super(message);
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
