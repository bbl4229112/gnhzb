package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SmlCodePool {
	private long id;
	private String firstCode;
	private String codeName;
	private String information;
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public String getFirstCode() {
		return firstCode;
	}
	public String getCodeName() {
		return codeName;
	}
	public String getInformation() {
		return information;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	
}
