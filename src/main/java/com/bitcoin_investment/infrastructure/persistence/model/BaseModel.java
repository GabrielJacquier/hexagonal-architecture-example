package com.bitcoin_investment.infrastructure.persistence.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseModel {

	@Id
	@Column(name =  "ID")
	private String id;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BaseModel)) return false;
		BaseModel baseModel = (BaseModel) o;
		return Objects.equals(id, baseModel.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
