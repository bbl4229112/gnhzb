package edu.zju.cims201.GOF.rs.dto;

public class PlatformManageDTO {
	private long id;
	private long statusId;
	private String platName;
	private String beginDate;
	private String info;
	private String statusName;
	private String inputUserName;
	private String checkUserName;
	
	
	public long getId() {
		return id;
	}
	public String getPlatName() {
		return platName;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public String getInfo() {
		return info;
	}

	public String getInputUserName() {
		return inputUserName;
	}
	public String getCheckUserName() {
		return checkUserName;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setPlatName(String platName) {
		this.platName = platName;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}
	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	public long getStatusId() {
		return statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
	
	
}
