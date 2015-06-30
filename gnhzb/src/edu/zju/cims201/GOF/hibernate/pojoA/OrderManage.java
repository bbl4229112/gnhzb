package edu.zju.cims201.GOF.hibernate.pojoA;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class OrderManage {
	private long id;
	private String orderNumber;
	private String info;
	private Date beginDate;
	private Date endDate;
	private String mingxi;
	private String checkinfo;
	private Status status;
	
	private Set<ModuleConfigStatus> moduleConfigStatusList;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public String getInfo() {
		return info;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getMingxi() {
		return mingxi;
	}
	public String getCheckinfo() {
		return checkinfo;
	}
	@ManyToOne
	@JoinColumn(name="status_id")
	public Status getStatus() {
		return status;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setMingxi(String mingxi) {
		this.mingxi = mingxi;
	}
	public void setCheckinfo(String checkinfo) {
		this.checkinfo = checkinfo;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@OneToMany(mappedBy="order",cascade=CascadeType.ALL)
	public Set<ModuleConfigStatus> getModuleConfigStatusList() {
		return moduleConfigStatusList;
	}
	public void setModuleConfigStatusList(
			Set<ModuleConfigStatus> moduleConfigStatusList) {
		this.moduleConfigStatusList = moduleConfigStatusList;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
}
