package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * Tag entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Tag implements java.io.Serializable {

	// Fields

	private Long id;
	private String tagName;
	private String tagType;
	private Set userKnowledgeTags = new HashSet(0);

	// Constructors

	/** default constructor */
	public Tag() {
	}


	
	public Tag(String tagType,Set userKnowledgeTags) {
		this.tagType = tagType;
		this.userKnowledgeTags = userKnowledgeTags;
	}

	// Property accessors

	

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

	public Set getUserKnowledgeTags() {
		return userKnowledgeTags;
	}

	public void setUserKnowledgeTags(Set userKnowledgeTags) {
		this.userKnowledgeTags = userKnowledgeTags;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}

	



}