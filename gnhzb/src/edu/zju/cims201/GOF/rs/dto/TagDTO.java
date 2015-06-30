package edu.zju.cims201.GOF.rs.dto;

import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.Property;

public class TagDTO {
	
	private Long id;
	private String tagName;
	private String tagType;
	private int tagnum;
	public int getTagnum() {
		return tagnum;
	}
	public void setTagnum(int tagnum) {
		this.tagnum = tagnum;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
}
