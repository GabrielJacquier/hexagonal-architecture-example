package com.bitcoin_investment.infrastructure.repository_impl;

import com.bitcoin_investment.core.query.dto.BitcoinTotalAmountQueryResponse;
import com.bitcoin_investment.infrastructure.persistence.BitcoinInvestmentPersistence;
import com.bitcoin_investment.core.query.dto.BitcoinPriceQueryResponse;
import com.bitcoin_investment.core.query.repository.BitcoinQueryRepository;
import com.bitcoin_investment.infrastructure.external.BitcoinApiService;
import com.bitcoin_investment.infrastructure.validation.BitcoinAPIConnectionErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class BitcoinQueryRepositoryImpl implements BitcoinQueryRepository {

	@Autowired
	private BitcoinApiService bitcoinApiService;

	@Autowired
	private BitcoinInvestmentPersistence bitcoinInvestmentPersistence;

	@Override
	public BitcoinPriceQueryResponse findCurrentPrice() {
		return bitcoinApiService.getBitcoinPrices()
				.map(bitcoinPrices -> new BitcoinPriceQueryResponse(
						BigDecimal.valueOf(bitcoinPrices.getTicker().getBuy()),
						BigDecimal.valueOf(bitcoinPrices.getTicker().getSell())))
				.orElseThrow(BitcoinAPIConnectionErrorException::new);
	}

	@Override
	public BitcoinTotalAmountQueryResponse findTotalBitcoinAmount() {
		var totalPurchaseSum = bitcoinInvestmentPersistence.sumBitcoinAmountByPurchasedOnBetween(
				LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));

		var totalSaleSum = bitcoinInvestmentPersistence.sumBitcoinAmountBySoldOnBetween(
				LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));

		return new BitcoinTotalAmountQueryResponse(
				totalPurchaseSum != null ? totalPurchaseSum : BigDecimal.ZERO,
				totalSaleSum != null ? totalSaleSum : BigDecimal.ZERO);
	}

}
