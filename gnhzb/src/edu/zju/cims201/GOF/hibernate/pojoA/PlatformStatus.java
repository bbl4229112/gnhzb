package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class PlatformStatus {
	private long id;
	private String statusName;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}
