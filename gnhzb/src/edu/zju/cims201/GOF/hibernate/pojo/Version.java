package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Version {
	

	private Long id;
	private Long pid;
	private String versionNumber;
	private MetaKnowledge knowledge;
	private Date versionTime;
	private List<Version> subVersions=new ArrayList<Version>();
	
	
	
	public List<Version> getSubVersions() {
		return subVersions;
	}
	public void setSubVersions(List<Version> subVersions) {
		this.subVersions = subVersions;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public MetaKnowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(MetaKnowledge knowledge) {
		this.knowledge = knowledge;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public Date getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(Date versionTime) {
		this.versionTime = versionTime;
	}
	

}
