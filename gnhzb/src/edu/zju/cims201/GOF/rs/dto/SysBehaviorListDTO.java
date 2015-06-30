package edu.zju.cims201.GOF.rs.dto;

public class SysBehaviorListDTO {

	private Long id;
	private String behaviorname;
	private int behaviorpoint;
	private Boolean contributionBehavior;
	private String behaviorcname;
	
	public SysBehaviorListDTO(){}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBehaviorname() {
		return behaviorname;
	}
	public void setBehaviorname(String behaviorname) {
		this.behaviorname = behaviorname;
	}
	public int getBehaviorpoint() {
		return behaviorpoint;
	}
	public void setBehaviorpoint(int behaviorpoint) {
		this.behaviorpoint = behaviorpoint;
	}
	public Boolean getContributionBehavior() {
		return contributionBehavior;
	}
	public void setContributionBehavior(Boolean contributionBehavior) {
		this.contributionBehavior = contributionBehavior;
	}
	public String getBehaviorcname() {
		return behaviorcname;
	}
	public void setBehaviorcname(String behaviorcname) {
		this.behaviorcname = behaviorcname;
	}
	
	
	
	
	
	
}
