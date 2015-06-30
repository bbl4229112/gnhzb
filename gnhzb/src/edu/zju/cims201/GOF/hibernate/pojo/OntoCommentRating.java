package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;

public class OntoCommentRating {
	
	
	
	
	private Long id;
	private SystemUser rater;
	private OntoComment ontocomment;
	private Float Score;
	private Date ratingTime;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public OntoComment getOntocomment() {
		return ontocomment;
	}
	public void setOntocomment(OntoComment ontocomment) {
		this.ontocomment = ontocomment;
	}
	public Date getRatingTime() {
		return ratingTime;
	}
	public void setRatingTime(Date ratingTime) {
		this.ratingTime = ratingTime;
	}
	public Float getScore() {
		return Score;
	}
	public void setScore(Float score) {
		Score = score;
	}
	public SystemUser getRater() {
		return rater;
	}
	public void setRater(SystemUser rater) {
		this.rater = rater;
	}


}
