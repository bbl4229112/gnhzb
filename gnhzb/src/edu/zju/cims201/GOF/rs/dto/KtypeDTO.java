package edu.zju.cims201.GOF.rs.dto;

import java.util.List;

public class KtypeDTO {
	
	private Long id;
	private String name;
	private String ktypeName;
	
	private String templatePath;
	private String templateXMLPath;
	
	private String className;
	private String tableName;
	private List<PropertyDTO> Property;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKtypeName() {
		return ktypeName;
	}
	public void setKtypeName(String ktypeName) {
		this.ktypeName = ktypeName;
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
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<PropertyDTO> getProperty() {
		return Property;
	}
	public void setProperty(List<PropertyDTO> property) {
		Property = property;
	}
	
	
}
