package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;

public class Vote {
	
	
	
	private Long id;
	private SystemUser user;
	private Boolean isSupport;
	private Comment comment;
	private Date voteTime;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getIsSupport() {
		return isSupport;
	}
	public void setIsSupport(Boolean isSupport) {
		this.isSupport = isSupport;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public SystemUser getUser() {
		return user;
	}
	public void setUser(SystemUser user) {
		this.user = user;
	}
	public Date getVoteTime() {
		return voteTime;
	}
	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}

}
