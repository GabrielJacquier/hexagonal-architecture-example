package com.bitcoin_investment.core.command.validator;

import com.bitcoin_investment.core.command.domain.entity.account.Account;
import com.bitcoin_investment.core.command.ports.out.AccountCommandRepository;
import com.bitcoin_investment.core.command.validator.validation.AlreadyExistApplicationValidation;
import com.bitcoin_investment.core.command.validator.validation.NotFoundApplicationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountValidator {

	@Autowired
	private AccountCommandRepository accountCommandRepository;

	public void throwValidationIfAlreadyExistByEmail(String email) {
		var otherAccount = accountCommandRepository.findByEmail(email);
		if (otherAccount.isPresent()) {
			throw new AlreadyExistApplicationValidation(email);
		}
	}

	public Account findByEmailOrThrowValidation(String email) {
		var otherAccount = accountCommandRepository.findByEmail(email);
		return otherAccount.orElseThrow(() -> new NotFoundApplicationValidation(email));
	}

}
