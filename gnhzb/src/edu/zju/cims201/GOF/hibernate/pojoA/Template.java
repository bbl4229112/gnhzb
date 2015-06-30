package edu.zju.cims201.GOF.hibernate.pojoA;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Template{
	private long id;
	private String templateName;
	private String templateUuid;
	private String templateDetail;
	private Date createTime;
	private String xmlFilePath;
	private String xmlFileName;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateUuid() {
		return templateUuid;
	}
	public void setTemplateUuid(String templateUuid) {
		this.templateUuid = templateUuid;
	}
	public String getTemplateDetail() {
		return templateDetail;
	}
	public void setTemplateDetail(String templateDetail) {
		this.templateDetail = templateDetail;
	}
	
	@Temporal(TemporalType.TIMESTAMP) 
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getXmlFilePath() {
		return xmlFilePath;
	}
	public void setXmlFilePath(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	}
	public String getXmlFileName() {
		return xmlFileName;
	}
	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}
	
	
}
