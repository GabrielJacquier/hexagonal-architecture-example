package com.bitcoin_investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BitcoinInvestmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitcoinInvestmentApplication.class, args);
	}

}
