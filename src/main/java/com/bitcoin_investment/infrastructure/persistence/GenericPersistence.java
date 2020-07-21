package com.bitcoin_investment.infrastructure.persistence;

import com.bitcoin_investment.infrastructure.persistence.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenericPersistence<T extends BaseModel> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

}
