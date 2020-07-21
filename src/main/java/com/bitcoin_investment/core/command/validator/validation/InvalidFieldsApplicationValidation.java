package com.bitcoin_investment.core.command.validator.validation;

import java.util.List;

public class InvalidFieldsApplicationValidation extends RuntimeException {
	private List<InvalidFieldApplicationValidation> fields;

	public InvalidFieldsApplicationValidation(String message, List<InvalidFieldApplicationValidation> fields) {
		super(message);
		this.fields = fields;
	}

	public InvalidFieldsApplicationValidation(List<InvalidFieldApplicationValidation> fields) {
		this.fields = fields;
	}

	public List<InvalidFieldApplicationValidation> getFields() {
		return fields;
	}
}
