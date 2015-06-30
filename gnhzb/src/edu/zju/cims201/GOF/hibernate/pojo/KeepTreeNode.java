package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class KeepTreeNode  {

	
	
	
	private Long id;
	private Long parentId;
	private Long orderId;
	private Long knowledgeId;
	private String code;
	private String nodeName;
	private String nodeDescription;
	private Date keepTime;
	private String keepTreeNodeType;
	
	public String getKeepTreeNodeType() {
		return keepTreeNodeType;
	}
	public void setKeepTreeNodeType(String keepTreeNodeType) {
		this.keepTreeNodeType = keepTreeNodeType;
	}
	public Long getKnowledgeId() {
		return knowledgeId;
	}
	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	public Date getKeepTime() {
		return keepTime;
	}
	public void setKeepTime(Date keepTime) {
		this.keepTime = keepTime;
	}
	private Set<KeepTreeNode> subNodes=new HashSet<KeepTreeNode>();
	
	
	public Set<KeepTreeNode> getSubNodes() {
		return subNodes;
	}
	public void setSubNodes(Set<KeepTreeNode> subNodes) {
		this.subNodes = subNodes;
	}
	public Long getParentId() {
		return parentId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getNodeDescription() {
		return nodeDescription;
	}
	public void setNodeDescription(String nodeDescription) {
		this.nodeDescription = nodeDescription;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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
		final TreeNode other = (TreeNode) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}
	
	
	


	
}
