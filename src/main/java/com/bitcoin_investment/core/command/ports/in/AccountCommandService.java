package com.bitcoin_investment.core.command.ports.in;

public interface AccountCommandService {

	void createNewAccount(AccountCreateCommand accountCreateCommand);

	void doDeposit(DepositCommand depositCommand);

}
