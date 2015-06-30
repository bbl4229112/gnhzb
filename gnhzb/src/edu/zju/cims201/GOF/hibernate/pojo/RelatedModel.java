package edu.zju.cims201.GOF.hibernate.pojo;

public class RelatedModel {
	private Long id;
	private PdmProcessTemplate pdmprocessTemplate;
	private Long  domainNodeId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getDomainNodeId() {
		return domainNodeId;
	}
	public void setDomainNodeId(Long domainNodeId) {
		this.domainNodeId = domainNodeId;
	}
	public PdmProcessTemplate getPdmprocessTemplate() {
		return pdmprocessTemplate;
	}
	public void setPdmprocessTemplate(PdmProcessTemplate pdmprocessTemplate) {
		this.pdmprocessTemplate = pdmprocessTemplate;
	}	
}
