package com.bitcoin_investment.core.command.validator;

import com.bitcoin_investment.core.command.validator.validation.InvalidFieldApplicationValidation;
import com.bitcoin_investment.core.command.validator.validation.InvalidFieldsApplicationValidation;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import java.util.stream.Collectors;

@Service
public class BaseValidator {

	public <T> void validateCommand(T command) {
		var factory = Validation.buildDefaultValidatorFactory();
		var validator = factory.getValidator();
		var fieldsWithErrors = validator.validate(command);
		if (!fieldsWithErrors.isEmpty()) {
			var validationsDomain = fieldsWithErrors
					.stream().map(c -> new InvalidFieldApplicationValidation(c.getMessage(), c.getPropertyPath().toString()))
					.collect(Collectors.toList());

			throw new InvalidFieldsApplicationValidation(validationsDomain);
		}
	}

}
