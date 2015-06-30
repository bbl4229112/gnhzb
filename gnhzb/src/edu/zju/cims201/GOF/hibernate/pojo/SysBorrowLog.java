package edu.zju.cims201.GOF.hibernate.pojo;

public class SysBorrowLog {
	
	private Long id;
	
	private BorrowFlowContent content;
	
	private Integer times;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public BorrowFlowContent getContent() {
		return content;
	}
	public void setContent(BorrowFlowContent content) {
		this.content = content;
	}
	
	

}
