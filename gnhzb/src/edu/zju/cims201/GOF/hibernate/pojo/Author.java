package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Author {
	
	private Long id;
	private String authorName;
	private Set knowledges=new HashSet<MetaKnowledge>();
	
	
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set getKnowledges() {
		return knowledges;
	}
	public void setKnowledges(Set knowledges) {
		this.knowledges = knowledges;
	}
	
	

}
