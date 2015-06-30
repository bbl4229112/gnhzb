package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Keyword {
	
	private Long id;
	private String keywordName;
	private Set knowledges=new HashSet<MetaKnowledge>();
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	public Set getKnowledges() {
		return knowledges;
	}
	public void setKnowledges(Set knowledges) {
		this.knowledges = knowledges;
	}
	
	

}
