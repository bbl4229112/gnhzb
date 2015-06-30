package edu.zju.cims201.GOF.rs.dto;

import java.awt.List;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;

public class CommentRecordDTO {
	
	private Long id;
	private Long viewCount;
    private Long commentCount;
    private Long downloadCount;
    private Long ratecount;
   
	private Float rate;
    public CommentRecordDTO(){}
    public CommentRecordDTO(CommentRecord cr)
    {   
    	if(null!=cr){
    	this.commentCount=cr.getCommentCount();
		this.downloadCount=cr.getDownloadCount();
		this.rate=cr.getRate();
		this.viewCount=cr.getViewCount();
    	this.id=cr.getId();
    	this.ratecount=cr.getRatecount();}
    	else{
    		this.commentCount=new Long(0);
    		this.downloadCount=new Long(0);
    		this.rate=new Float(0);
    		this.viewCount=new Long(0);
    		this.ratecount=new Long(0);
        	//this.id=cr.getId();
    		
    	}
    	
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getViewCount() {
		return viewCount;
	}
	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
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
	 public Long getRatecount() {
			return ratecount;
		}
		public void setRatecount(Long ratecount) {
			this.ratecount = ratecount;
		}
	
}
