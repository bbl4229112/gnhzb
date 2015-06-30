package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;

public class SysBehaviorLog {
	private Long id;
	private SystemUser user;
	private Date actionTime;
	private Long objectid;
	private String objectType;
	private  SysBehaviorList sysBehaviorList;
	
	public SysBehaviorLog() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SystemUser getUser() {
		return user;
	}
	public void setUser(SystemUser user) {
		this.user = user;
	}
	public Date getActionTime() {
		return actionTime;
	}
	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}
	public Long getObjectid() {
		return objectid;
	}
	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public SysBehaviorList getSysBehaviorList() {
		return sysBehaviorList;
	}
	public void setSysBehaviorList(SysBehaviorList sysBehaviorList) {
		this.sysBehaviorList = sysBehaviorList;
	}
		
}
