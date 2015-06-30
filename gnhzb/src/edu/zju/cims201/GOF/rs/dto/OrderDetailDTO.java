package edu.zju.cims201.GOF.rs.dto;


public class OrderDetailDTO {
	
	private long id;
	private long orderId;
	private long demandId;
	private long demandValueId;
	private String demandName;
	private String demandMemo;
	private String demandValueName;
	private String demandValueMemo;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrderId() {
		return orderId;
	}
	public long getDemandId() {
		return demandId;
	}
	public long getDemandValueId() {
		return demandValueId;
	}
	public String getDemandName() {
		return demandName;
	}
	public String getDemandMemo() {
		return demandMemo;
	}
	public String getDemandValueName() {
		return demandValueName;
	}
	public String getDemandValueMemo() {
		return demandValueMemo;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public void setDemandId(long demandId) {
		this.demandId = demandId;
	}
	public void setDemandValueId(long demandValueId) {
		this.demandValueId = demandValueId;
	}
	public void setDemandName(String demandName) {
		this.demandName = demandName;
	}
	public void setDemandMemo(String demandMemo) {
		this.demandMemo = demandMemo;
	}
	public void setDemandValueName(String demandValueName) {
		this.demandValueName = demandValueName;
	}
	public void setDemandValueMemo(String demandValueMemo) {
		this.demandValueMemo = demandValueMemo;
	}

	
}
