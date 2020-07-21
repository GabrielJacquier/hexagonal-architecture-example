package com.bitcoin_investment.api.handler;

import com.bitcoin_investment.core.command.validator.validation.InvalidFieldApplicationValidation;

public class FieldInvalidResponse {
	private String fieldName;
	private String message;

	public FieldInvalidResponse(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}

	public FieldInvalidResponse(InvalidFieldApplicationValidation invalidFieldApplicationValidation) {
		this.fieldName = invalidFieldApplicationValidation.getField();
		this.message = invalidFieldApplicationValidation.getMessage();
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return message;
	}
}
