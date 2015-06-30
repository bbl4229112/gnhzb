package edu.zju.cims201.GOF.rs.dto;


public class PartDTO {
	
	private long id;
	private String partNumber;
	private String partName;
	private String createTime;
	private String partBigVersion;
	private String partSmallVersion;
	private int isarranged;
	private String description;
	private String state;
	
	private long platStructId;
	private long orderId;
	private long count;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPartBigVersion() {
		return partBigVersion;
	}
	public void setPartBigVersion(String partBigVersion) {
		this.partBigVersion = partBigVersion;
	}
	public String getPartSmallVersion() {
		return partSmallVersion;
	}
	public void setPartSmallVersion(String partSmallVersion) {
		this.partSmallVersion = partSmallVersion;
	}
	public int getIsarranged() {
		return isarranged;
	}
	public void setIsarranged(int isarranged) {
		this.isarranged = isarranged;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getPlatStructId() {
		return platStructId;
	}
	public void setPlatStructId(long platStructId) {
		this.platStructId = platStructId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	

}
