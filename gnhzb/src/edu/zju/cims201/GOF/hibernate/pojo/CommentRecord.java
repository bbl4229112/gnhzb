package edu.zju.cims201.GOF.hibernate.pojo;

public class CommentRecord {
	
	
	private Long id;
	private Long viewCount;
    private Long commentCount;
    private Long downloadCount;
    private Long ratecount;
    private Float rate;
    private Knowledge knowledge;
	
	
	
	
	public Long getRatecount() {
		return ratecount;
	}
	public void setRatecount(Long ratecount) {
		this.ratecount = ratecount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	public Long getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	public Knowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}
	public Long getViewCount() {
		return viewCount;
	}
	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}


}
