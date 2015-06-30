package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SmlUnitPool {
	private long id;
	private String unitCode;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
}
