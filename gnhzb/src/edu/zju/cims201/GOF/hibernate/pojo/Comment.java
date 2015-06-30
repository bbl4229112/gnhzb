package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class Comment implements Comparable<Comment>{
	
	
	
	private Long id;
	private MetaKnowledge knowledge;
	private Comment commented;
	private SystemUser commenter;
	private Set<Comment> comments=new HashSet<Comment>();
	private Set<Vote> votes=new HashSet<Vote>();
	private String content;
	private Date commmentTime;
	private Integer supportVoteCount;
	private Integer againstVoteCount;
	private Integer heat;
	private Integer isBest;//最佳答案
	
	
	//用来表示该节点是否是子节点
	private int tail;
	
	public SystemUser getCommenter() {
		return commenter;
	}
	public void setCommenter(SystemUser commenter) {
		this.commenter = commenter;
	}
	public Set<Vote> getVotes() {
		return votes;
	}
	public void setVotes(Set<Vote> votes) {
		this.votes = votes;
	}
	public Set<Comment> getComments() {
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	public Integer getAgainstVoteCount() {
		return againstVoteCount;
	}
	public void setAgainstVoteCount(Integer againstVoteCount) {
		this.againstVoteCount = againstVoteCount;
	}
	public Comment getCommented() {
		return commented;
	}
	public void setCommented(Comment commented) {
		this.commented = commented;
	}
	public Date getCommmentTime() {
		return commmentTime;
	}
	public void setCommmentTime(Date commmentTime) {
		this.commmentTime = commmentTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Integer getSupportVoteCount() {
		return supportVoteCount;
	}
	public void setSupportVoteCount(Integer supportVoteCount) {
		this.supportVoteCount = supportVoteCount;
	}
	public Integer getHeat() {
		return heat;
	}
	public void setHeat(Integer heat) {
		this.heat = heat;
	}
	public int getTail() {
		return tail;
	}
	public void setTail(int tail) {
		this.tail = tail;
	}
	public Integer getIsBest() {
		return isBest;
	}
	public void setIsBest(Integer isBest) {
		this.isBest = isBest;
	}
	public int compareTo(Comment o) {
	if(commmentTime.before(o.commmentTime))
		return 1;
	else
		return -1;
	}
}
