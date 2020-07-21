package com.bitcoin_investment.core.command.service;

import com.bitcoin_investment.core.command.domain.entity.account.Account;
import com.bitcoin_investment.core.command.domain.entity.account.AccountPassword;
import com.bitcoin_investment.core.command.domain.entity.account.RealAmount;
import com.bitcoin_investment.core.command.ports.in.AccountCreateCommand;
import com.bitcoin_investment.core.command.ports.out.AccountCommandRepository;
import com.bitcoin_investment.core.command.service.event.AccountDepositDone;
import com.bitcoin_investment.core.command.ports.in.AccountCommandService;
import com.bitcoin_investment.core.command.ports.in.DepositCommand;
import com.bitcoin_investment.core.command.validator.AccountValidator;
import com.bitcoin_investment.core.command.validator.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {

	@Autowired
	private AccountCommandRepository accountCommandRepository;

	@Autowired
	private AccountValidator accountValidator;

	@Autowired
	private BaseValidator baseValidator;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Override
	public void createNewAccount(AccountCreateCommand accountCreateCommand) {
		baseValidator.validateCommand(accountCreateCommand);
		accountValidator.throwValidationIfAlreadyExistByEmail(accountCreateCommand.getEmail());

		var account = new Account(accountCreateCommand.getName(), accountCreateCommand.getEmail());
		var password = new AccountPassword(accountCreateCommand.getPassword());
		accountCommandRepository.create(account, password);
	}

	@Override
	public void doDeposit(DepositCommand depositCommand) {
		baseValidator.validateCommand(depositCommand);
		var account = accountValidator.findByEmailOrThrowValidation(depositCommand.getEmail());

		var realAmountToDeposit = new RealAmount(depositCommand.getValue());
		account.doDeposit(realAmountToDeposit);
		accountCommandRepository.update(account);
		eventPublisher.publishEvent(new AccountDepositDone(account.getId(), realAmountToDeposit));
	}

}
