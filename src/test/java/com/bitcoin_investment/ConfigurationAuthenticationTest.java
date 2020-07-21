package com.bitcoin_investment;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

@TestConfiguration
public class ConfigurationAuthenticationTest {

	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder().additionalInterceptors((outReq, bytes, clientHttpReqExec) -> {
			outReq.getHeaders().set(HttpHeaders.AUTHORIZATION, "Basic Yml0Y29pbi1pbnZlc3RtZW50czplZHV6el90ZXN0");
			return clientHttpReqExec.execute(outReq, bytes);
		});
	}

}
