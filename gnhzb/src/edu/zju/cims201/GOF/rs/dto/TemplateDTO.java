package edu.zju.cims201.GOF.rs.dto;



public class TemplateDTO {
	private long id;
	private String templateName;
	private String templateDetail;
	private String createTime;

	
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
	public String getTemplateDetail() {
		return templateDetail;
	}
	public void setTemplateDetail(String templateDetail) {
		this.templateDetail = templateDetail;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
