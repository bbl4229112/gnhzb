package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.List;



public class Knowledgetype {
	

	
	private Long id;
	private String knowledgeTypeName;
//	private Knowledge knowledge;
   // private List<Ktype> ktypelist;
	
	
//	public List<Ktype> getKtypelist() {
//		return ktypelist;
//	}
//	public void setKtypelist(List<Ktype> ktypelist) {
//		this.ktypelist = ktypelist;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public Knowledge getKnowledge() {
//		return knowledge;
//	}
//	public void setKnowledge(Knowledge knowledge) {
//		this.knowledge = knowledge;
//	}
	public String getKnowledgeTypeName() {
		return knowledgeTypeName;
	}
	public void setKnowledgeTypeName(String knowledgeTypeName) {
		this.knowledgeTypeName = knowledgeTypeName;
	}


}
