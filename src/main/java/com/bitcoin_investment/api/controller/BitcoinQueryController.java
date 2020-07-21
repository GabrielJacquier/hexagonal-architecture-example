package com.bitcoin_investment.api.controller;

import com.bitcoin_investment.api.controller.response.BitcoinPriceResponse;
import com.bitcoin_investment.api.controller.response.BitcoinTotalAmountResponse;
import com.bitcoin_investment.core.query.repository.BitcoinQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/bitcoin")
public class BitcoinQueryController {

	@Autowired
	private BitcoinQueryRepository bitcoinQueryRepository;

	@GetMapping("/price")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BitcoinPriceResponse findCurrentPrices() {
		var pricesQueryResp = bitcoinQueryRepository.findCurrentPrice();
		return new BitcoinPriceResponse(pricesQueryResp.getBuyPrice(), pricesQueryResp.getSalePrice());
	}

	@GetMapping("/negotiated-total-amount")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BitcoinTotalAmountResponse findNegotiatedTotalAmount() {
		var amountQueryResp = bitcoinQueryRepository.findTotalBitcoinAmount();
		return new BitcoinTotalAmountResponse(amountQueryResp);
	}

}
