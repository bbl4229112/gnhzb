package edu.zju.cims201.GOF.rs.dto;

public class ShowButtonDTO {
	
	private long approvalFlowId;
	private long borrowFlowId;
	
	private boolean isShow=false;

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public long getApprovalFlowId() {
		return approvalFlowId;
	}

	public void setApprovalFlowId(long approvalFlowId) {
		this.approvalFlowId = approvalFlowId;
	}

	public long getBorrowFlowId() {
		return borrowFlowId;
	}

	public void setBorrowFlowId(long borrowFlowId) {
		this.borrowFlowId = borrowFlowId;
	}
	
	
	
	

}
