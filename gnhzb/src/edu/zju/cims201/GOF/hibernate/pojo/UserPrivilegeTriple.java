package edu.zju.cims201.GOF.hibernate.pojo;


import java.util.HashSet;
import java.util.Set;

public class UserPrivilegeTriple {
	
	private Long id;
	private TreeNode cdTreeNode;
	private SystemUser user;
	private OperationRight operationRight;
	
	private Empowerment empowerment;
	
	
	
	
	
	public Empowerment getEmpowerment() {
		return empowerment;
	}
	public void setEmpowerment(Empowerment empowerment) {
		this.empowerment = empowerment;
	}
	
	
	public TreeNode getCdTreeNode() {
		return cdTreeNode;
	}
	public void setCdTreeNode(TreeNode cdTreeNode) {
		this.cdTreeNode = cdTreeNode;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public OperationRight getOperationRight() {
		return operationRight;
	}
	public void setOperationRight(OperationRight operationRight) {
		this.operationRight = operationRight;
	}
	
	public SystemUser getUser() {
		return user;
	}
	public void setUser(SystemUser user) {
		this.user = user;
	}

}
