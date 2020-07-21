package com.bitcoin_investment.infrastructure.persistence;

import com.bitcoin_investment.infrastructure.persistence.model.AccountTransactionModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountTransactionPersistence extends GenericPersistence<AccountTransactionModel> {

	Optional<AccountTransactionModel> findById(String id);

	@Query("select t from AccountTransactionModel t join t.accountModel m where t.createdOn >= :initialDate and t.createdOn <= :finalDate and m.id = :accountId")
	List<AccountTransactionModel> findBy(@Param("accountId") String accountId,
										 @Param("initialDate") LocalDateTime initialDate,
										 @Param("finalDate") LocalDateTime finalDate);

}