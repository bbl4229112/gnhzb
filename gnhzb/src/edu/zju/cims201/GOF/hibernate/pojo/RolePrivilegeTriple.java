package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RolePrivilegeTriple {
	
	
	private Long id;
	private TreeNode cdTreeNode;
	private TreeNode roleTreeNode;
	
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
	
	
	public TreeNode getRoleTreeNode() {
		return roleTreeNode;
	}
	public void setRoleTreeNode(TreeNode roleTreeNode) {
		this.roleTreeNode = roleTreeNode;
	}
	

}
