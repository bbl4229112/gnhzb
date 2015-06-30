package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;




public class BorrowFlowContent {
	
	
	private Long id;
	private BorrowFlow borrowFlow;
	private Date borrowTime;
	private Integer times;
	
	private boolean limited;
	
	private boolean download;
	
	
	
	
	
	
	
	public Date getBorrowTime() {
		return borrowTime;
	}
	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public BorrowFlow getBorrowFlow() {
		return borrowFlow;
	}
	public void setBorrowFlow(BorrowFlow borrowFlow) {
		this.borrowFlow = borrowFlow;
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
	public boolean isDownload() {
		return download;
	}
	public void setDownload(boolean download) {
		this.download = download;
	}
	
	
	

}
