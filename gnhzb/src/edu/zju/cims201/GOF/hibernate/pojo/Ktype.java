package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ktype {
	
	
	
	private Long id;
	private String name;
	private String ktypeName;
	
	private String templatePath;
	private String templateXMLPath;
	
	private String className;
	private String tableName;
	
	private List<KtypeProperty> ktypeproperties=new ArrayList<KtypeProperty>();
	
	public List<KtypeProperty> getKtypeproperties() {
		return ktypeproperties;
	}
	public void setKtypeproperties(List<KtypeProperty> ktypeproperties) {
		this.ktypeproperties = ktypeproperties;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKtypeName() {
		return ktypeName;
	}
	public void setKtypeName(String ktypeName) {
		this.ktypeName = ktypeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getTemplateXMLPath() {
		return templateXMLPath;
	}
	public void setTemplateXMLPath(String templateXMLPath) {
		this.templateXMLPath = templateXMLPath;
	}
	
	

}
