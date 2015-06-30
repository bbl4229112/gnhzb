package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BomStatus{
	/**
	 * 1:正在审核
	 * 2:审核通过
	 * 3:审核不通过
	 * 
	 */
	private long id;
	private String statusName;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
