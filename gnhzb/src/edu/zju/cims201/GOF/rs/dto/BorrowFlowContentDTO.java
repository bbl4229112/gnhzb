package edu.zju.cims201.GOF.rs.dto;

import java.util.Date;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlowContent;

public class BorrowFlowContentDTO {
	
	private Long id;
	
	private Integer times;
	
	private boolean limited;
	
	private boolean download;
	private String borrowTime;
	
	public BorrowFlowContentDTO(){
		
	}
	
	public BorrowFlowContentDTO(BorrowFlowContent content){
		id=content.getId();
		times=content.getTimes();
		limited=content.isLimited();
		download=content.isDownload();
		if(null!=content.getBorrowTime())
			borrowTime=content.getBorrowTime().toLocaleString();
		
	}


	public String getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}

	public boolean isDownload() {
		return download;
	}

	public void setDownload(boolean download) {
		this.download = download;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isLimited() {
		return limited;
	}

	public void setLimited(boolean limited) {
		this.limited = limited;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
	
	
	

}
