package com.bitcoin_investment.api.controller;

import com.bitcoin_investment.ConfigurationAuthenticationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { ConfigurationAuthenticationTest.class })
public class AuthenticationProviderIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void successWhenTryAuthenticateWithValidCredentials() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "password");
		body.add("username", "gabriel@email.com");
		body.add("password", "123mudar");

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		ResponseEntity<Void> response = restTemplate.postForEntity("/oauth/token",
				requestEntity, Void.class);

		assertEquals(HttpStatus.OK, response.getStatusCode(), "Error to authenticate with valid credentials");
	}

}
