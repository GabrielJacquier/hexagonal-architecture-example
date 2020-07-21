package com.bitcoin_investment.core.command.ports.in;

import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinInvestmentPosition;

import java.util.List;

public interface InvestmentCommandService {

	void buyInvestment(BuyInvestmentCommand buyInvestmentCommand);

	void saleInvestment(SaleInvestmentCommand saleInvestmentCommand);

	List<BitcoinInvestmentPosition> calculateInvestmentsPosition(String accountEmail);

}
