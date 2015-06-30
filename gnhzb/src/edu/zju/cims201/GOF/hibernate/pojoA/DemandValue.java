package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DemandValue {
	private long id;
	private String demandValueName;
	private String demandValueMemo;
	private DemandManage demandManage;
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public String getDemandValueName() {
		return demandValueName;
	}
	public String getDemandValueMemo() {
		return demandValueMemo;
	}
	
	@ManyToOne
	@JoinColumn(name="demand_manage_id")
	public DemandManage getDemandManage() {
		return demandManage;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setDemandValueName(String demandValueName) {
		this.demandValueName = demandValueName;
	}
	public void setDemandValueMemo(String demandValueMemo) {
		this.demandValueMemo = demandValueMemo;
	}
	public void setDemandManage(DemandManage demandManage) {
		this.demandManage = demandManage;
	}
	
	
	
}
