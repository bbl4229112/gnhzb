package edu.zju.cims201.GOF.rs.dto;

import java.awt.List;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import edu.zju.cims201.GOF.hibernate.pojo.Comment;

public class CommentDTO implements Comparable<CommentDTO>{
	private Long id;
	private Long knowledgeid;
	private CommentDTO commented;
	private Set<CommentDTO> commentdtos = new TreeSet<CommentDTO>();
	private String commenterName;
	private String commenterpicpath;
	private String content;
	private String commmentTime;
	private Integer supportVoteCount;
	private Integer againstVoteCount;
	private Integer heat;
	private Integer isBest;//最佳答案
    private Float rate;
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	public Set<CommentDTO> getCommentdtos() {
		return commentdtos;
	}
	public void setCommentdtos(Set<CommentDTO> commentdtos) {
		this.commentdtos = commentdtos;
	}

	public Integer getIsBest() {
		return isBest;
	}
	public void setIsBest(Integer isBest) {
		this.isBest = isBest;
	}
	public String getCommenterName() {
		return commenterName;
	}
	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getKnowledgeid() {
		return knowledgeid;
	}
	public void setKnowledgeid(Long knowledgeid) {
		this.knowledgeid = knowledgeid;
	}	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public String getCommmentTime() {
		return commmentTime;
	}
	public void setCommmentTime(String commmentTime) {
		this.commmentTime = commmentTime;
	}
	public Integer getSupportVoteCount() {
		return supportVoteCount;
	}
	public void setSupportVoteCount(Integer supportVoteCount) {
		this.supportVoteCount = supportVoteCount;
	}
	public Integer getAgainstVoteCount() {
		return againstVoteCount;
	}
	public void setAgainstVoteCount(Integer againstVoteCount) {
		this.againstVoteCount = againstVoteCount;
	}
	public Integer getHeat() {
		return heat;
	}
	public void setHeat(Integer heat) {
		this.heat = heat;
	}
	public CommentDTO getCommented() {
		return commented;
	}
	public void setCommented(CommentDTO commented) {
		this.commented = commented;
	}
	public String getCommenterpicpath() {
		return commenterpicpath;
	}
	public void setCommenterpicpath(String commenterpicpath) {
		this.commenterpicpath = commenterpicpath;
	}
	public int compareTo(CommentDTO o) {
		
		return commmentTime.compareTo(o.commmentTime);
		
	}	    
}
