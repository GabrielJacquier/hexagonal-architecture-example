package com.bitcoin_investment.api.handler;

import com.bitcoin_investment.core.command.domain.validation.DomainValidation;
import com.bitcoin_investment.core.command.validator.validation.AlreadyExistApplicationValidation;
import com.bitcoin_investment.core.command.validator.validation.InvalidFieldApplicationValidation;
import com.bitcoin_investment.core.command.validator.validation.InvalidFieldsApplicationValidation;
import com.bitcoin_investment.core.command.validator.validation.NotFoundApplicationValidation;
import com.bitcoin_investment.infrastructure.validation.BitcoinAPIConnectionErrorException;
import com.bitcoin_investment.infrastructure.validation.PersistenceNotFoundErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationResponseHandler {
	private Logger logger = LoggerFactory.getLogger(ValidationResponseHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<FieldInvalidResponse> handleArgumentNotValid(MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		return fieldErrors.stream()
				.map(fieldError -> new FieldInvalidResponse(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
	}

	@ExceptionHandler(InvalidFieldsApplicationValidation.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<FieldInvalidResponse> handleInvalidFields(InvalidFieldsApplicationValidation ex) {
		return ex.getFields().stream().map(FieldInvalidResponse::new).collect(Collectors.toList());
	}

	@ExceptionHandler(InvalidFieldApplicationValidation.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public FieldInvalidResponse handleInvalidField(InvalidFieldApplicationValidation ex) {
		return new FieldInvalidResponse(ex.getField(), ex.getMessage());
	}

	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleInvalidNumberFormat(NumberFormatException ex) {
		logger.warn(ex.getMessage(), ex);
		return "Input value is invalid";
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleJsonParseError(HttpMessageNotReadableException ex) {
		logger.warn(ex.getMessage(), ex);
		return "Input value is invalid";
	}

	@ExceptionHandler(DomainValidation.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleDomainValidation(DomainValidation ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(AlreadyExistApplicationValidation.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public FieldInvalidResponse handleAlreadyExistValidation(AlreadyExistApplicationValidation ex) {
		return new FieldInvalidResponse(ex.getField(), ex.getMessage());
	}

	@ExceptionHandler(NotFoundApplicationValidation.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public FieldInvalidResponse handleNotFoundValidation(NotFoundApplicationValidation ex) {
		return new FieldInvalidResponse(ex.getField(), ex.getMessage());
	}

	@ExceptionHandler(PersistenceNotFoundErrorException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public String handleNotFoundPersistenceError(PersistenceNotFoundErrorException ex) {
		return "Sorry, it was not found some entity needed to realize this action, try authenticate and after to do the action again";
	}

	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public String handleOptimisticLocking(ObjectOptimisticLockingFailureException ex) {
		return "Operation error. Other update happened simultaneously with this action. Please try this action again. ";
	}

	@ExceptionHandler(BitcoinAPIConnectionErrorException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	@ResponseBody
	public String handleBitcoinConnectionError(BitcoinAPIConnectionErrorException ex) {
		return String.format("Sorry, it was not possible get information from {} system, try again later.", ex.getSystemTargetId());
	}

	@ExceptionHandler(ResourceAccessException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	@ResponseBody
	public String handleResourceAccessError(ResourceAccessException ex) {
		logger.error(ex.getMessage(), ex);
		return "Sorry, it was not possible process information when get data from other system, try again later.";
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handleServerError(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return "Sorry, a error has occurred, try the action again or contact our support.";
	}

}
