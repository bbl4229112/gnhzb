package edu.zju.cims201.GOF.rs.dto;

import java.util.Date;

import edu.zju.cims201.GOF.hibernate.pojo.Flow;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;

public class FlowNodeDTO implements Comparable<FlowNodeDTO>{
	private Long id;
	private Long  initiatorId;
	private String initiatorName;

	private Long approverORLenderId;
	private String approverORLenderName;
	private String approverORLenderMail;
	private String approvalORBorrowOpinion;
	private String nodeStatus;
	private Date addTime;
	private Date approvalORBorrowTime;
	
	
	public String getApproverORLenderMail() {
		return approverORLenderMail;
	}

	public void setApproverORLenderMail(String approverORLenderMail) {
		this.approverORLenderMail = approverORLenderMail;
	}

	public FlowNodeDTO(){
		
	}
	
	public FlowNodeDTO(FlowNode flowNode){
		id=flowNode.getId();
		if(flowNode.getInitiator()!=null){
			initiatorId=flowNode.getInitiator().getId();
			initiatorName=flowNode.getInitiator().getName();
		}
		if(flowNode.getApproverORLender()!=null){
			approverORLenderId=flowNode.getApproverORLender().getId();
			approverORLenderName=flowNode.getApproverORLender().getName();
			approverORLenderMail=flowNode.getApproverORLender().getEmail();
		}
		approvalORBorrowOpinion=flowNode.getApprovalORBorrowOpinion();
		
		nodeStatus=flowNode.getNodeStatus();
		addTime=flowNode.getAddTime();
		approvalORBorrowTime=flowNode.getApprovalORBorrowTime();
		
		
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getInitiatorId() {
		return initiatorId;
	}
	public void setInitiatorId(Long initiatorId) {
		this.initiatorId = initiatorId;
	}
	public String getInitiatorName() {
		return initiatorName;
	}
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
	public Long getApproverORLenderId() {
		return approverORLenderId;
	}
	public void setApproverORLenderId(Long approverORLenderId) {
		this.approverORLenderId = approverORLenderId;
	}
	public String getApproverORLenderName() {
		return approverORLenderName;
	}
	public void setApproverORLenderName(String approverORLenderName) {
		this.approverORLenderName = approverORLenderName;
	}
	public String getApprovalORBorrowOpinion() {
		return approvalORBorrowOpinion;
	}
	public void setApprovalORBorrowOpinion(String approvalORBorrowOpinion) {
		this.approvalORBorrowOpinion = approvalORBorrowOpinion;
	}
	public String getNodeStatus() {
		return nodeStatus;
	}
	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getApprovalORBorrowTime() {
		return approvalORBorrowTime;
	}
	public void setApprovalORBorrowTime(Date approvalORBorrowTime) {
		this.approvalORBorrowTime = approvalORBorrowTime;
	}
	
	
	public int compareTo(FlowNodeDTO o) {
		
		return this.id>o.id?1:-1;
	}
	
	
}
