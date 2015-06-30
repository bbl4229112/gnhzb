package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;

public class UserKnowledgeTag {
	
	private Long id;
	private SystemUser tager;
	private MetaKnowledge knowledge;
	private Tag tag;
	private Date tagTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SystemUser getTager() {
		return tager;
	}
	public void setTager(SystemUser tager) {
		this.tager = tager;
	}
	public MetaKnowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(MetaKnowledge knowledge) {
		this.knowledge = knowledge;
	}
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public Date getTagTime() {
		return tagTime;
	}
	public void setTagTime(Date tagTime) {
		this.tagTime = tagTime;
	}
	

}
