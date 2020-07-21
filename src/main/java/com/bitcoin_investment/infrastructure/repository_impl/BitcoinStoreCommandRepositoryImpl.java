package com.bitcoin_investment.infrastructure.repository_impl;

import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinStore;
import com.bitcoin_investment.core.command.ports.out.BitcoinStoreCommandRepository;
import com.bitcoin_investment.infrastructure.external.BitcoinApiService;
import com.bitcoin_investment.infrastructure.validation.BitcoinAPIConnectionErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class BitcoinStoreCommandRepositoryImpl implements BitcoinStoreCommandRepository {

	@Autowired
	private BitcoinApiService bitcoinApiService;

	@Override
	public BitcoinStore getStore() {
		return bitcoinApiService.getBitcoinPrices()
				.map(bitcoinPrices ->  new BitcoinStore(
						BigDecimal.valueOf(bitcoinPrices.getTicker().getSell()),
						BigDecimal.valueOf(bitcoinPrices.getTicker().getBuy())))
				.orElseThrow(BitcoinAPIConnectionErrorException::new);
	}

}
