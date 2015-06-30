package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;

public class HotWord {
	
	
	
	
	private Long id;
	private String wordName;
	private SystemUser Initiator;
	private Date time;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SystemUser getInitiator() {
		return Initiator;
	}
	public void setInitiator(SystemUser initiator) {
		Initiator = initiator;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getWordName() {
		return wordName;
	}
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

}
