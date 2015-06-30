package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DemandManage {
	
	
	private long id;
	private String demandName;
	private String demandMemo;
	
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public String getDemandName() {
		return demandName;
	}
	public String getDemandMemo() {
		return demandMemo;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setDemandName(String demandName) {
		this.demandName = demandName;
	}
	public void setDemandMemo(String demandMemo) {
		this.demandMemo = demandMemo;
	}
	
	
}
