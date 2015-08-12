package edu.zju.cims201.GOF.hibernate.pojo.pdm;


public class PdmProjectValuePool {

	private Long id;
	private PdmProject project;
	private String value;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PdmProject getProject() {
		return project;
	}
	public void setProject(PdmProject project) {
		this.project = project;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
