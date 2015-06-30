package edu.zju.cims201.GOF.rs.dto;

public class KnowledgeRelatedDTO {
	
	private Long id;
	private String xmlFigureId;
	private Long knowledgeId;
	private String titleName;
	private String uploader;
	private String uploadTime;
	private String knowledgeType;	
	private String knowledgeFilePath;
	
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
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getKnowledgeType() {
		return knowledgeType;
	}
	public void setKnowledgeType(String knowledgeType) {
		this.knowledgeType = knowledgeType;
	}
	public String getKnowledgeFilePath() {
		return knowledgeFilePath;
	}
	public void setKnowledgeFilePath(String knowledgeFilePath) {
		this.knowledgeFilePath = knowledgeFilePath;
	}	
	
}