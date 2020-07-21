package com.bitcoin_investment.infrastructure.persistence;

import com.bitcoin_investment.infrastructure.persistence.model.AccountModel;

import java.util.Optional;

public interface AccountPersistence extends GenericPersistence<AccountModel> {

	Optional<AccountModel> findByEmail(String email);

	Optional<AccountModel> findById(String id);

}