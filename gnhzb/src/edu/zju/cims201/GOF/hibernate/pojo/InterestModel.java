package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class InterestModel {
	
	
	
	
	private Long id;
	private SystemUser user;
	private String interestItem;
	private String interestItemType;
	private Date createtime;
	private Integer unRead;
	private Integer isParent;//是否是首级订阅点
	
	private Set knowledges=new HashSet<MetaKnowledge>();
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInterestItem() {
		return interestItem;
	}
	public void setInterestItem(String interestItem) {
		this.interestItem = interestItem;
	}
	public String getInterestItemType() {
		return interestItemType;
	}
	public void setInterestItemType(String interestItemType) {
		this.interestItemType = interestItemType;
	}
	
	public Set getKnowledges() {
		return knowledges;
	}
	public void setKnowledges(Set knowledges) {
		this.knowledges = knowledges;
	}
	public SystemUser getUser() {
		return user;
	}
	public void setUser(SystemUser user) {
		this.user = user;
	}
	public Integer getUnRead() {
		return unRead;
	}
	public void setUnRead(Integer unRead) {
		this.unRead = unRead;
	}	
	
	public Integer getIsParent() {
		return isParent;
	}
	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


}
