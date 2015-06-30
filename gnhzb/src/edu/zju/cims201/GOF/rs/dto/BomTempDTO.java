package edu.zju.cims201.GOF.rs.dto;

public class BomTempDTO{
	
	private long id;
	private long platId;
	private long platStructId;
	private long moduleClassId;
	private String classCode;
	private String classText;
	private long partId;
	private String partName;
	private String partNumber;
	/**
	 * 规则的状态
	 * 1：必选
	 * 2：可选
	 * 3：排除
	 */
	private int status;
	private String info;
	private long partSelectedId;
	private int quantity;
	private long orderId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPlatId() {
		return platId;
	}
	public void setPlatId(long platId) {
		this.platId = platId;
	}
	public long getPlatStructId() {
		return platStructId;
	}
	public void setPlatStructId(long platStructId) {
		this.platStructId = platStructId;
	}
	public long getModuleClassId() {
		return moduleClassId;
	}
	public void setModuleClassId(long moduleClassId) {
		this.moduleClassId = moduleClassId;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassText() {
		return classText;
	}
	public void setClassText(String classText) {
		this.classText = classText;
	}
	public long getPartId() {
		return partId;
	}
	public void setPartId(long partId) {
		this.partId = partId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public long getPartSelectedId() {
		return partSelectedId;
	}
	public void setPartSelectedId(long partSelectedId) {
		this.partSelectedId = partSelectedId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	
}
