package edu.zju.cims201.GOF.rs.dto;

import java.util.Date;

public class VoteDTO {
	private Long id;
	private String username;
	private Boolean isSupport;
	private String commentid;
	private Date voteTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getIsSupport() {
		return isSupport;
	}
	public void setIsSupport(Boolean isSupport) {
		this.isSupport = isSupport;
	}
	public String getCommentid() {
		return commentid;
	}
	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}
	public Date getVoteTime() {
		return voteTime;
	}
	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}
	
	

}
