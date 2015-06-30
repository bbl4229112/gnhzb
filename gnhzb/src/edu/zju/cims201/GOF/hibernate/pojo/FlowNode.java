package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;

public class FlowNode {
	
	
	private Long id;
	private SystemUser  initiator;
	private Flow flow;
	private SystemUser approverORLender;
	private String approvalORBorrowOpinion;
	private String nodeStatus;
	private Date addTime;
	private Date approvalORBorrowTime;
	
	
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getApprovalORBorrowOpinion() {
		return approvalORBorrowOpinion;
	}
	public void setApprovalORBorrowOpinion(String approvalORBorrowOpinion) {
		this.approvalORBorrowOpinion = approvalORBorrowOpinion;
	}
	public Date getApprovalORBorrowTime() {
		return approvalORBorrowTime;
	}
	public void setApprovalORBorrowTime(Date approvalORBorrowTime) {
		this.approvalORBorrowTime = approvalORBorrowTime;
	}
	public SystemUser getApproverORLender() {
		return approverORLender;
	}
	public void setApproverORLender(SystemUser approverORLender) {
		this.approverORLender = approverORLender;
	}
	public Flow getFlow() {
		return flow;
	}
	public void setFlow(Flow flow) {
		this.flow = flow;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SystemUser getInitiator() {
		return initiator;
	}
	public void setInitiator(SystemUser initiator) {
		this.initiator = initiator;
	}
	public String getNodeStatus() {
		return nodeStatus;
	}
	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FlowNode other = (FlowNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
