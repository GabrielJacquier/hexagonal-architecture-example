package com.bitcoin_investment.api.controller;

import com.bitcoin_investment.api.authentication.AccountAuthenticated;
import com.bitcoin_investment.api.controller.request.AccountCreateRequest;
import com.bitcoin_investment.api.controller.request.DepositRequest;
import com.bitcoin_investment.core.command.ports.in.AccountCommandService;
import com.bitcoin_investment.core.command.ports.in.AccountCreateCommand;
import com.bitcoin_investment.core.command.ports.in.DepositCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/api/account")
public class AccountCommandController {

	@Autowired
	private AccountCommandService accountCreateCommandService;

	@Autowired
	private AccountAuthenticated accountAuthenticated;

	@PostMapping
	public ResponseEntity<Void> create(@RequestBody AccountCreateRequest createReq) {
		accountCreateCommandService.createNewAccount(new AccountCreateCommand(
				createReq.getName(),
				createReq.getEmail(),
				createReq.getPassword()));

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/deposit")
	public ResponseEntity<Void> doDeposit(@RequestBody DepositRequest depositRequest) {
		var depositCommand = new DepositCommand(BigDecimal.valueOf(depositRequest.getValue()),
				accountAuthenticated.getEmail());

		accountCreateCommandService.doDeposit(depositCommand);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
