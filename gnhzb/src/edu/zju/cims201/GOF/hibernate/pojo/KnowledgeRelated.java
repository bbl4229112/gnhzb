package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;;

public class KnowledgeRelated {
	//KnowledgeRelated对象实际存储的是一个figure。
	//该对象只包含一个字段即figureId，因为在前期的设计中，一个figure的figureId是唯一的。——不能这样。
	private Long id;
	private String xmlFigureId;
	private Long knowledgeId;
	private String type;
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getXmlFigureId() {
		return xmlFigureId;
	}
	public void setXmlFigureId(String xmlFigureId) {
		this.xmlFigureId = xmlFigureId;
	}
	public Long getKnowledgeId() {
		return knowledgeId;
	}
	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}	
	
}
