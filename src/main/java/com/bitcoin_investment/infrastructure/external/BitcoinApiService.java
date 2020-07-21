package com.bitcoin_investment.infrastructure.external;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class BitcoinApiService {

	public Optional<BitcoinInfoDTO> getBitcoinPrices() {
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		var response = restTemplate.getForEntity("https://www.mercadobitcoin.net/api/BTC/ticker/", BitcoinInfoDTO.class);
		return Optional.ofNullable(response.getBody());
	}

}
