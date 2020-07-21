package com.bitcoin_investment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

@TestConfiguration
public class ConfigurationDefaultTest {

    @Value("${authentication.access_token}")
    private String accessTokenValue;

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder().additionalInterceptors((outReq, bytes, clientHttpReqExec) -> {
            outReq.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenValue);
            return clientHttpReqExec.execute(outReq, bytes);
        });
    }

}
