package edu.zju.cims201.GOF.rs.dto;

import java.util.Date;



public class UserKnowledgeTagDTO {
	private Long id;
	private String tag;
	private String tagerid;
	private String tagername;
	private Date tagTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTagerid() {
		return tagerid;
	}
	public void setTagerid(String tagerid) {
		this.tagerid = tagerid;
	}
	public String getTagername() {
		return tagername;
	}
	public void setTagername(String tagername) {
		this.tagername = tagername;
	}
	public Date getTagTime() {
		return tagTime;
	}
	public void setTagTime(Date tagTime) {
		this.tagTime = tagTime;
	}

}
