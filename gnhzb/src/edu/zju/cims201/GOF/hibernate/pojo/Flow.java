package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Flow {
	
	
	
	private Long id;
	private MetaKnowledge knowledge;
	private SystemUser  initiator;
	private Date startTime;
	private String status;
	private Integer index;
	private List<FlowNode> flowNodes=new ArrayList<FlowNode>() ;
	
	
	
	
	
	
	
	public List<FlowNode> getFlowNodes() {
		return flowNodes;
	}
	public void setFlowNodes(List<FlowNode> flowNodes) {
		this.flowNodes = flowNodes;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public SystemUser getInitiator() {
		return initiator;
	}
	public void setInitiator(SystemUser initiator) {
		this.initiator = initiator;
	}
	public MetaKnowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(MetaKnowledge knowledge) {
		this.knowledge = knowledge;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
