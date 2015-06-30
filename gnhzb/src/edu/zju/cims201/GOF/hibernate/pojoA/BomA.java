package edu.zju.cims201.GOF.hibernate.pojoA;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
public class BomA {
	private long id;
	private String bomName;
	private Date createTime;
	/**
	 * Bom的状态
	 * 1：正在审核
	 * 2：审核通过
	 * 3：审核不通过
	 */
	private BomStatus bomStatus;
	private String checkInfo;
	private String info;
//	private SystemUser creator;
//	private SystemUser checker;
	
	private List<BomDetailA> bomDetailList;
	
	private String uuid;
	/*
	 * 0,不需要同步
	 * 1，需要同步
	 */
	private int synFlag;
	
	@Id
	@GeneratedValue
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
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/*	@ManyToOne
	@JoinColumn(name="creator_id")
	public SystemUser getCreator() {
		return creator;
	}
	public void setCreator(SystemUser creator) {
		this.creator = creator;
	}
	@ManyToOne
	@JoinColumn(name="checker_id")
	public SystemUser getChecker() {
		return checker;
	}
	public void setChecker(SystemUser checker) {
		this.checker = checker;
	}*/
	@ManyToOne
	@JoinColumn(name="status_id")
	public BomStatus getBomStatus() {
		return bomStatus;
	}
	public void setBomStatus(BomStatus bomStatus) {
		this.bomStatus = bomStatus;
	}
	public String getCheckInfo() {
		return checkInfo;
	}
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}
	
	@OneToMany(mappedBy="bom",cascade = CascadeType.ALL)
	public List<BomDetailA> getBomDetailList() {
		return bomDetailList;
	}
	public void setBomDetailList(List<BomDetailA> bomDetailList) {
		this.bomDetailList = bomDetailList;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getSynFlag() {
		return synFlag;
	}
	public void setSynFlag(int synFlag) {
		this.synFlag = synFlag;
	}
	
}
