package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class OntoComment implements Comparable<OntoComment>{
	
	
	
	private Long id;
	private OntoBuild ontobuild;
	private OntoComment commented;
	private SystemUser commenter;
	private Set<OntoComment> comments=new HashSet<OntoComment>();
	private Set<Vote> votes=new HashSet<Vote>();
	private String content;
	private Date commmentTime;
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
	public Set<OntoComment> getComments() {
		return comments;
	}
	public void setComments(Set<OntoComment> comments) {
		this.comments = comments;
	}
	public Integer getAgainstVoteCount() {
		return againstVoteCount;
	}
	public void setAgainstVoteCount(Integer againstVoteCount) {
		this.againstVoteCount = againstVoteCount;
	}
	public OntoComment getCommented() {
		return commented;
	}
	public void setCommented(OntoComment commented) {
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

	public OntoBuild getOntobuild() {
		return ontobuild;
	}
	public void setOntobuild(OntoBuild ontobuild) {
		this.ontobuild = ontobuild;
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
	public int compareTo(OntoComment o) {
	if(commmentTime.before(o.commmentTime))
		return 1;
	else
		return -1;
	}
}
