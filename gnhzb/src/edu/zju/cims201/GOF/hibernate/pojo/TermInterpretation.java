package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;

public class TermInterpretation {
	
	private Long id;
	private SystemUser giver;
	private String termInterpretationType;
	private String interpretation;
	private Date giveTime;
	
	
	public Date getGiveTime() {
		return giveTime;
	}
	public void setGiveTime(Date giveTime) {
		this.giveTime = giveTime;
	}
	public SystemUser getGiver() {
		return giver;
	}
	public void setGiver(SystemUser giver) {
		this.giver = giver;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInterpretation() {
		return interpretation;
	}
	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}
	public String getTermInterpretationType() {
		return termInterpretationType;
	}
	public void setTermInterpretationType(String termInterpretationType) {
		this.termInterpretationType = termInterpretationType;
	}

}
