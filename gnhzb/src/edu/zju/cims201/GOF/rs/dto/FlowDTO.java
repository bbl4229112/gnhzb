package edu.zju.cims201.GOF.rs.dto;

import java.util.ArrayList;
import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;

public class FlowDTO {
	
	private Long id;
	
	private String status;
	
	private List<FlowNodeDTO> nodes ;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FlowNodeDTO> getNodes() {
		return nodes;
	}

	public void setNodes(List<FlowNodeDTO> nodes) {
		this.nodes = nodes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	
	
	
	
	

}
