package edu.zju.cims201.GOF.rs.dto;

import java.util.Date;

public class OntoBuildDTO {
	
	private Long id;
	private String classname;
	private String ontoname;
	private boolean hasExplain;
	private String  creatername;
	public String  createremail;
	private String finalExplainCreatername;
	private String finalExplainCreateremail;
	private String finalExplain;
	private String creattime;
	private String explaintime;
	public Integer getOntocommentratecount() {
		return ontocommentratecount;
	}
	public void setOntocommentratecount(Integer ontocommentratecount) {
		this.ontocommentratecount = ontocommentratecount;
	}
	private Integer ontocommentcount;
	private Integer ontocommentratecount;
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getCreatername() {
		return creatername;
	}
	public void setCreatername(String creatername) {
		this.creatername = creatername;
	}
	public String getCreateremail() {
		return createremail;
	}
	public void setCreateremail(String createremail) {
		this.createremail = createremail;
	}
	public String getFinalExplainCreatername() {
		return finalExplainCreatername;
	}
	public void setFinalExplainCreatername(String finalExplainCreatername) {
		this.finalExplainCreatername = finalExplainCreatername;
	}
	public String getFinalExplainCreateremail() {
		return finalExplainCreateremail;
	}
	public void setFinalExplainCreateremail(String finalExplainCreateremail) {
		this.finalExplainCreateremail = finalExplainCreateremail;
	}
	public Integer getOntocommentcount() {
		return ontocommentcount;
	}
	public void setOntocommentcount(Integer ontocommentcount) {
		this.ontocommentcount = ontocommentcount;
	}
	public String getCreattime() {
		return creattime;
	}
	public void setCreattime(String creattime) {
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

	public boolean isHasExplain() {
		return hasExplain;
	}
	public void setHasExplain(boolean hasExplain) {
		this.hasExplain = hasExplain;
	}

	public String getFinalExplain() {
		return finalExplain;
	}
	public void setFinalExplain(String finalExplain) {
		this.finalExplain = finalExplain;
	}
	public String getExplaintime() {
		return explaintime;
	}
	public void setExplaintime(String explaintime) {
		this.explaintime = explaintime;
	}
	
	
	

}
