package com.bitcoin_investment.core.query.repository;

import com.bitcoin_investment.core.query.dto.BitcoinTotalAmountQueryResponse;
import com.bitcoin_investment.core.query.dto.BitcoinPriceQueryResponse;

public interface BitcoinQueryRepository {

	BitcoinPriceQueryResponse findCurrentPrice();

	BitcoinTotalAmountQueryResponse findTotalBitcoinAmount();

}
