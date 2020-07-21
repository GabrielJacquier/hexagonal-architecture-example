package com.bitcoin_investment.api.controller;

import com.bitcoin_investment.ConfigurationDefaultTest;
import com.bitcoin_investment.api.controller.response.BitcoinInvestmentPositionResponse;
import com.bitcoin_investment.api.controller.request.BuyInvestmentRequest;
import com.bitcoin_investment.api.controller.request.DepositRequest;
import com.bitcoin_investment.api.controller.request.SaleInvestmentRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {ConfigurationDefaultTest.class})
@Sql(value = "/test_scripts/DELETE_BITCOIN_INVESTMENT.sql")
public class BitcoinCommandControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void successfulInvestmentDone() {
		restTemplate.exchange("/api/account/deposit", HttpMethod.POST,
				new HttpEntity<>(new DepositRequest(200.0)), Void.class);

		ResponseEntity<Void> response = restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.POST,
				new HttpEntity<>(new BuyInvestmentRequest(100.0)), Void.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Was not able to create a investment");
	}

	@Test
	public void failInvestAmountBiggerThanAvailableAccountBalanceValue() {
		restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.POST,
				new HttpEntity<>(new BuyInvestmentRequest(Double.MAX_VALUE)), Void.class);

		ResponseEntity<Void> response = restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.POST,
				new HttpEntity<>(new BuyInvestmentRequest(Double.MAX_VALUE)), Void.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "A investment was created without Account Balance sufficient");
	}

	@Test
	public void failInvestNegativeValue() {
		ResponseEntity<Void> response = restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.POST,
				new HttpEntity<>(new BuyInvestmentRequest(-10.0)), Void.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "A invalid negative investment was created");
	}

	@Test
	public void successfulDoThreeNewInvestments() {
		restTemplate.exchange("/api/account/deposit", HttpMethod.POST,
				new HttpEntity<>(new DepositRequest(1000.0)), Void.class);

		restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.POST,
				new HttpEntity<>(new BuyInvestmentRequest(200.0)), Void.class);

		restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.POST,
				new HttpEntity<>(new BuyInvestmentRequest(200.0)), Void.class);

		restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.POST,
				new HttpEntity<>(new BuyInvestmentRequest(200.0)), Void.class);

		ResponseEntity<List<BitcoinInvestmentPositionResponse>> response = restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.GET,
				null, new ParameterizedTypeReference<>() {});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(3, response.getBody().size());
	}

	@Test
	public void successfulCountOneInvestmentAfterInvestmentSell() {
		restTemplate.exchange("/api/account/deposit", HttpMethod.POST,
				new HttpEntity<>(new DepositRequest(1000.0)), Void.class);

		restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.POST,
				new HttpEntity<>(new BuyInvestmentRequest(200.0)), Void.class);

		restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.POST,
				new HttpEntity<>(new BuyInvestmentRequest(200.0)), Void.class);

		restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.POST,
				new HttpEntity<>(new BuyInvestmentRequest(200.0)), Void.class);

		restTemplate.exchange("/api/bitcoin/investment/sale", HttpMethod.POST,
				new HttpEntity<>(new SaleInvestmentRequest(450.0)), Void.class);

		ResponseEntity<List<BitcoinInvestmentPositionResponse>> response = restTemplate.exchange("/api/bitcoin/investment/purchase", HttpMethod.GET,
				null, new ParameterizedTypeReference<>() {});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

}
