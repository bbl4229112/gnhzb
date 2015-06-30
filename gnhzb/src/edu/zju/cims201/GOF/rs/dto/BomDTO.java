package edu.zju.cims201.GOF.rs.dto;

public class BomDTO {
	
	private long id;
	private String bomName;
	private long orderId;
	private String orderName;
	private String platName;
	private String bomCreator;
	private String bomChecker;
	private String createTime;
	private long bomStatusId;
	private String bomStatus;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBomName() {
		return bomName;
	}
	public void setBomName(String bomName) {
		this.bomName = bomName;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getPlatName() {
		return platName;
	}
	public void setPlatName(String platName) {
		this.platName = platName;
	}
	public String getBomCreator() {
		return bomCreator;
	}
	public void setBomCreator(String bomCreator) {
		this.bomCreator = bomCreator;
	}
	public String getBomChecker() {
		return bomChecker;
	}
	public void setBomChecker(String bomChecker) {
		this.bomChecker = bomChecker;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public long getBomStatusId() {
		return bomStatusId;
	}
	public void setBomStatusId(long bomStatusId) {
		this.bomStatusId = bomStatusId;
	}
	public String getBomStatus() {
		return bomStatus;
	}
	public void setBomStatus(String bomStatus) {
		this.bomStatus = bomStatus;
	}
	
	
	
}
