package com.bitcoin_investment.api.controller;

import com.bitcoin_investment.api.authentication.AccountAuthenticated;
import com.bitcoin_investment.api.controller.request.BuyInvestmentRequest;
import com.bitcoin_investment.api.controller.request.SaleInvestmentRequest;
import com.bitcoin_investment.api.controller.response.BitcoinInvestmentPositionResponse;
import com.bitcoin_investment.core.command.ports.in.BuyInvestmentCommand;
import com.bitcoin_investment.core.command.ports.in.InvestmentCommandService;
import com.bitcoin_investment.core.command.ports.in.SaleInvestmentCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/bitcoin")
public class BitcoinCommandController {
	private Logger logger = LoggerFactory.getLogger(BitcoinCommandController.class);

	@Autowired
	private InvestmentCommandService investmentCommandService;

	@Autowired
	private AccountAuthenticated accountAuthenticated;

	@PostMapping("/investment/purchase")
	public ResponseEntity<Void> invest(@RequestBody BuyInvestmentRequest buyInvestmentRequest) {
		investmentCommandService.buyInvestment(new BuyInvestmentCommand(
				BigDecimal.valueOf(buyInvestmentRequest.getValueToInvest()), accountAuthenticated.getEmail()));

		logger.info("Investment Done");
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/investment/sale")
	public ResponseEntity<Void> saleInvestment(@RequestBody SaleInvestmentRequest saleInvestmentRequest) {
		investmentCommandService.saleInvestment(new SaleInvestmentCommand(BigDecimal.valueOf(
				saleInvestmentRequest.getValueToRescue()), accountAuthenticated.getEmail()));

		logger.info("Investment Sold");
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/investment/purchase")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<BitcoinInvestmentPositionResponse> findInvestmentsPositions() {
		var investmentsPositions = investmentCommandService.calculateInvestmentsPosition(accountAuthenticated.getEmail());
		return investmentsPositions.stream()
				.map(BitcoinInvestmentPositionResponse::new)
				.collect(Collectors.toList());
	}


}
