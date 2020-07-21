package com.bitcoin_investment.api.controller;

import com.bitcoin_investment.api.authentication.AccountAuthenticated;
import com.bitcoin_investment.api.controller.response.AccountBalanceResponse;
import com.bitcoin_investment.api.controller.response.AccountTransactionResponse;
import com.bitcoin_investment.core.query.repository.AccountQueryRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/account")
public class AccountQueryController {

	@Autowired
	private AccountQueryRepository accountQueryRepository;

	@Autowired
	private AccountAuthenticated accountAuthenticated;

	@GetMapping("/balance")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AccountBalanceResponse getCurrentBalanceValue() {
		var queryResponse = accountQueryRepository.findBalanceByEmail(accountAuthenticated.getEmail());
		return new AccountBalanceResponse(queryResponse.getBalance());
	}

	@GetMapping("/transaction")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<AccountTransactionResponse> findTransaction(
		@ApiParam(value = "Initial Date (ex: 2020-01-01T00:00:00)")
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		@RequestParam(value = "initialDate") LocalDateTime initialDate,
		@ApiParam(value = "Final Date (ex: 2020-12-31T00:00:00)")
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		@RequestParam(value = "finalDate") LocalDateTime finalDate
	) {
		return accountQueryRepository
				.findTransactionsByEmail(accountAuthenticated.getEmail(), initialDate, finalDate)
				.stream()
				.map(AccountTransactionResponse::new)
				.collect(Collectors.toList());
	}

}
