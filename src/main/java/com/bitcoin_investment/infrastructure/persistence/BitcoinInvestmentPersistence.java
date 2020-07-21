package com.bitcoin_investment.infrastructure.persistence;

import com.bitcoin_investment.infrastructure.persistence.model.BitcoinInvestmentModel;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BitcoinInvestmentPersistence extends GenericPersistence<BitcoinInvestmentModel> {

	@Query("select sum(bim.bitcoinAmount) from BitcoinInvestmentModel bim " +
			"where bim.soldOn >= :initialDate and bim.soldOn <= :finalDate")
	BigDecimal sumBitcoinAmountBySoldOnBetween(LocalDateTime initialDate, LocalDateTime finalDate);

	@Query("select sum(bim.bitcoinAmount) from BitcoinInvestmentModel bim " +
			"where bim.purchasedOn >= :initialDate and bim.purchasedOn <= :finalDate")
	BigDecimal sumBitcoinAmountByPurchasedOnBetween(LocalDateTime initialDate, LocalDateTime finalDate);

}