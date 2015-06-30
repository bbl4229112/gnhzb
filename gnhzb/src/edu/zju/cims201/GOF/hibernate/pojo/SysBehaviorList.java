package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * SysBehaviorlist entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysBehaviorList implements java.io.Serializable {

	// Fields
	private Long id;
	private String behaviorname;
	private int behaviorpoint;
	private Boolean contributionBehavior;
	private String behaviorcname;
	private Set sysBehaviorlogs = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysBehaviorList() {
	}

	/** minimal constructor */
	public SysBehaviorList(String behaviorname) {
		this.behaviorname = behaviorname;
	}

	/** full constructor */
	public SysBehaviorList(String behaviorname, int behaviorpoint,
			Set sysBehaviorlogs) {
		this.behaviorname = behaviorname;
		this.behaviorpoint = behaviorpoint;
		this.sysBehaviorlogs = sysBehaviorlogs;
	}

	// Property accessors

	public String getBehaviorname() {
		return this.behaviorname;
	}

	public void setBehaviorname(String behaviorname) {
		this.behaviorname = behaviorname;
	}

	public int getBehaviorpoint() {
		return this.behaviorpoint;
	}

	public void setBehaviorpoint(int behaviorpoint) {
		this.behaviorpoint = behaviorpoint;
	}

	public Set getSysBehaviorlogs() {
		return this.sysBehaviorlogs;
	}

	public void setSysBehaviorlogs(Set sysBehaviorlogs) {
		this.sysBehaviorlogs = sysBehaviorlogs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setContributionBehavior(Boolean contributionBehavior){
		this.contributionBehavior = contributionBehavior;
	}
	
	public Boolean getContributionBehavior(){
		return contributionBehavior;
	}

	public String getBehaviorcname() {
		return behaviorcname;
	}

	public void setBehaviorcname(String behaviorcname) {
		this.behaviorcname = behaviorcname;
	}



}