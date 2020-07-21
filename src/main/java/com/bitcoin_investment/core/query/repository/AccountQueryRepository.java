package com.bitcoin_investment.core.query.repository;

import com.bitcoin_investment.core.query.dto.AccountBalanceQueryResponse;
import com.bitcoin_investment.core.query.dto.AccountTransactionQueryResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountQueryRepository {

	AccountBalanceQueryResponse findBalanceByEmail(String email);

	List<AccountTransactionQueryResponse> findTransactionsByEmail(String email, LocalDateTime initialDate, LocalDateTime finalDate);

}
