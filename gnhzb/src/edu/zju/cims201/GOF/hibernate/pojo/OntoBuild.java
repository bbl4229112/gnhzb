package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;

public class OntoBuild {
	
	private Long id;
	private String ontoname;
	private boolean hasExplain;
	public SystemUser Creater;
	public SystemUser finalExplainCreater;
	public String finalExplain;
	public Date creattime;
	public Date explaintime;
	public Date getCreattime() {
		return creattime;
	}
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getOntoname() {
		return ontoname;
	}
	public void setOntoname(String ontoname) {
		this.ontoname = ontoname;
	}
	public SystemUser getFinalExplainCreater() {
		return finalExplainCreater;
	}
	public void setFinalExplainCreater(SystemUser finalExplainCreater) {
		this.finalExplainCreater = finalExplainCreater;
	}
	public boolean isHasExplain() {
		return hasExplain;
	}
	public void setHasExplain(boolean hasExplain) {
		this.hasExplain = hasExplain;
	}
	public SystemUser getCreater() {
		return Creater;
	}
	public void setCreater(SystemUser creater) {
		Creater = creater;
	}
	public String getFinalExplain() {
		return finalExplain;
	}
	public void setFinalExplain(String finalExplain) {
		this.finalExplain = finalExplain;
	}
	public Date getExplaintime() {
		return explaintime;
	}
	public void setExplaintime(Date explaintime) {
		this.explaintime = explaintime;
	}
	
	
	

}
