package com.bitcoin_investment.core.command.service;

import com.bitcoin_investment.core.command.ports.in.LoginCommand;
import com.bitcoin_investment.core.command.ports.in.LoginCommandService;
import com.bitcoin_investment.core.command.ports.out.AccountPasswordService;
import com.bitcoin_investment.core.command.domain.entity.account.AccountPassword;
import com.bitcoin_investment.core.command.ports.out.AccountCommandRepository;
import com.bitcoin_investment.core.command.validator.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginCommandServiceImpl implements LoginCommandService {

	@Autowired
	private AccountCommandRepository accountCommandRepository;

	@Autowired
	private AccountPasswordService accountPasswordService;

	@Autowired
	private BaseValidator baseValidator;

	@Override
	public boolean isCredentialAuthentic(LoginCommand loginCommand) {
		baseValidator.validateCommand(loginCommand);
		Optional<AccountPassword> accountPassword = accountCommandRepository.findPasswordByEmail(loginCommand.getEmail());
		return accountPassword
				.map(pass -> accountPasswordService.matchesPasswords(loginCommand.getPassword(), pass.getValue()))
				.orElse(false);
	}

}
