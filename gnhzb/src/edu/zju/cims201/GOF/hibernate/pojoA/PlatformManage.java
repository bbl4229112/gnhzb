package edu.zju.cims201.GOF.hibernate.pojoA;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
 

@Entity
public class PlatformManage {
	private long id;
	private String platName;
	private Date beginDate;
	private String info;
	private String checkinfo;
	private PlatformStatus status;
	
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public String getPlatName() {
		return platName;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	
	@ManyToOne
	@JoinColumn(name="status_id")
	public PlatformStatus getStatus() {
		return status;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setPlatName(String platName) {
		this.platName = platName;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public void setStatus(PlatformStatus status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getCheckinfo() {
		return checkinfo;
	}
	public void setCheckinfo(String checkinfo) {
		this.checkinfo = checkinfo;
	}
	
	
	
}
