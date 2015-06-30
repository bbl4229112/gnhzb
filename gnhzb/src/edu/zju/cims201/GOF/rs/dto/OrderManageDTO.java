package edu.zju.cims201.GOF.rs.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class OrderManageDTO {
	private long id;
	private long statusId;
	private String orderNumber;
	private String info;
	private String beginDate;
	private String endDate;
	private String mingxi;
	private String checkinfo;
	private String statusName;
	
	
	public long getId() {
		return id;
	}
	public long getStatusId() {
		return statusId;
	}

	public String getInfo() {
		return info;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getMingxi() {
		return mingxi;
	}
	public String getCheckinfo() {
		return checkinfo;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setMingxi(String mingxi) {
		this.mingxi = mingxi;
	}
	public void setCheckinfo(String checkinfo) {
		this.checkinfo = checkinfo;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	
	
}
