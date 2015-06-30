package edu.zju.cims201.GOF.hibernate.pojo;



public class Tree {
	

	
	private Long id;
	private String attachmentName;
	private MetaKnowledge knowledge;
	
	
	
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MetaKnowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(MetaKnowledge knowledge) {
		this.knowledge = knowledge;
	}
}
