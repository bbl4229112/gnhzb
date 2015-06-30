package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Term {
	
	
	private Long id;
	private String termName;
	private Set<TermInterpretation> interpretations=new HashSet<TermInterpretation>();
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<TermInterpretation> getInterpretations() {
		return interpretations;
	}
	public void setInterpretations(Set<TermInterpretation> interpretations) {
		this.interpretations = interpretations;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}

}
