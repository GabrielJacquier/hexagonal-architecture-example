package com.bitcoin_investment.core.command.validator.validation;

public class NotFoundApplicationValidation extends RuntimeException {
	private String field;

	public NotFoundApplicationValidation(String field) {
		super("The entity does not exist");
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
