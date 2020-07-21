package com.bitcoin_investment.core.command.ports.out;

import com.bitcoin_investment.core.command.domain.entity.account.AccountPassword;
import com.bitcoin_investment.core.command.domain.entity.account.Account;

import java.util.Optional;

public interface AccountCommandRepository {

	void create(Account account, AccountPassword password);

	void update(Account account);

	Optional<Account> findByEmail(String email);

	Optional<AccountPassword> findPasswordByEmail(String email);

}
