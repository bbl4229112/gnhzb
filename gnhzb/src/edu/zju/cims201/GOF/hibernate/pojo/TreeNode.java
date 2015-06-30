package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;



public class TreeNode {
	
	
	
	private Long id;
	private Long parentId;
	private Long orderId;
	private String code;
	private String nodeName;
	private String nodeDescription;
	private Set<TreeNode> subNodes=new HashSet<TreeNode>();
	
	private Set<Empowerment> empowerments=new HashSet<Empowerment>();
	
	private Set<AdminPrivilegeTriple> adminPrivilegeTriples=new HashSet<AdminPrivilegeTriple>();
	
	private Set<Expert> experts=new HashSet<Expert>();
	
	
	
	public Set<Expert> getExperts() {
		return experts;
	}
	public void setExperts(Set<Expert> experts) {
		this.experts = experts;
	}
	public Set<Empowerment> getEmpowerments() {
		return empowerments;
	}
	public void setEmpowerments(Set<Empowerment> empowerments) {
		this.empowerments = empowerments;
	}
	public Set<AdminPrivilegeTriple> getAdminPrivilegeTriples() {
		return adminPrivilegeTriples;
	}
	public void setAdminPrivilegeTriples(
			Set<AdminPrivilegeTriple> adminPrivilegeTriples) {
		this.adminPrivilegeTriples = adminPrivilegeTriples;
	}
	public Set<TreeNode> getSubNodes() {
		return subNodes;
	}
	public void setSubNodes(Set<TreeNode> subNodes) {
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
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
