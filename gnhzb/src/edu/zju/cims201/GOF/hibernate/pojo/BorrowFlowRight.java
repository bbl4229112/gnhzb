package edu.zju.cims201.GOF.hibernate.pojo;

public class BorrowFlowRight {
	
	
	private Long id;
	private BorrowFlow borrowFlow;
	private OperationRight borrowFlowRight;
	
	
	public BorrowFlow getBorrowFlow() {
		return borrowFlow;
	}
	public void setBorrowFlow(BorrowFlow borrowFlow) {
		this.borrowFlow = borrowFlow;
	}
	public OperationRight getBorrowFlowRight() {
		return borrowFlowRight;
	}
	public void setBorrowFlowRight(OperationRight borrowFlowRight) {
		this.borrowFlowRight = borrowFlowRight;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
