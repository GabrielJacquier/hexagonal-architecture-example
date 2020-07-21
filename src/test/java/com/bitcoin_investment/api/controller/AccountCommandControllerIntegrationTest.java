package com.bitcoin_investment.api.controller;

import com.bitcoin_investment.ConfigurationDefaultTest;
import com.bitcoin_investment.api.controller.response.AccountBalanceResponse;
import com.bitcoin_investment.api.controller.request.AccountCreateRequest;
import com.bitcoin_investment.api.controller.request.DepositRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {ConfigurationDefaultTest.class})
@Sql(value = "/test_scripts/UPDATE_ACCOUNT_BALANCE_ZERO.sql")
public class AccountCommandControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void successWhenCreateValidNewAccount() {
		var request = new AccountCreateRequest("test", "email@email.com", "123change");
		ResponseEntity<Void> response = restTemplate.exchange("/api/account", HttpMethod.POST, new HttpEntity<>(request), Void.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	static Stream<Arguments> params_failWhenTryCreateNewAccountWithInvalidField() {
		return Stream.of(
				Arguments.of("name empty was not validated", new AccountCreateRequest("", "email@email.com", "123change")),
				Arguments.of("email empty was not validated", new AccountCreateRequest("test", "", "123change")),
				Arguments.of("password empty was not validated", new AccountCreateRequest("test", "email@email.com", "")),
				Arguments.of("name null was not validated", new AccountCreateRequest(null, "email@email.com", "123change")),
				Arguments.of("email null was not validated", new AccountCreateRequest("test", null, "123change")),
				Arguments.of("password null was not validated", new AccountCreateRequest("test", "email@email.com", null)),
				Arguments.of("email invalid was not validated", new AccountCreateRequest("test", "emamail.com", "123change"))
		);
	}

	@ParameterizedTest(name = "Run {index}, message: {0}")
	@MethodSource("params_failWhenTryCreateNewAccountWithInvalidField")
	public void failWhenTryCreateNewAccountWithInvalidField(String message, AccountCreateRequest request) {
		ResponseEntity<Void> response = restTemplate.exchange("/api/account", HttpMethod.POST, new HttpEntity<>(request), Void.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), message);
	}

	@Test
	public void successWhenTryToDoValidAccountDeposit() {
		ResponseEntity<Void> response = restTemplate.exchange("/api/account/deposit", HttpMethod.POST,
				new HttpEntity<>(new DepositRequest(12.90)), Void.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void failToDoAccountDepositWhenValueIsZero() {
		ResponseEntity<Void> response = restTemplate.exchange("/api/account/deposit", HttpMethod.POST,
				new HttpEntity<>(new DepositRequest(0D)), Void.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void failToDoAccountDepositWhenValueIsNegative() {
		ResponseEntity<Void> response = restTemplate.exchange("/api/account/deposit", HttpMethod.POST,
				new HttpEntity<>(new DepositRequest(-123D)), Void.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void successToDoAccountDepositWhenValueIsVeryBig() {
		ResponseEntity<Void> response = restTemplate.exchange("/api/account/deposit", HttpMethod.POST,
				new HttpEntity<>(new DepositRequest(Double.MAX_VALUE)), Void.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void successCheckValidAccountBalanceWhenDoMultipleDeposits() {
		restTemplate.exchange("/api/account/deposit", HttpMethod.POST,
				new HttpEntity<>(new DepositRequest(20.00)), Void.class);

		restTemplate.exchange("/api/account/deposit", HttpMethod.POST,
				new HttpEntity<>(new DepositRequest(20.00)), Void.class);

		restTemplate.exchange("/api/account/deposit", HttpMethod.POST,
				new HttpEntity<>(new DepositRequest(20.25)), Void.class);

		var response = restTemplate.getForEntity("/api/account/balance", AccountBalanceResponse.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(BigDecimal.valueOf(60.25), Objects.requireNonNull(response.getBody()).getBalance());
	}

}
