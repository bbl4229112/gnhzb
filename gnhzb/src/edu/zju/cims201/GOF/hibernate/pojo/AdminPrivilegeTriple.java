package edu.zju.cims201.GOF.hibernate.pojo;

public class AdminPrivilegeTriple {
	
	private Long id;
	private TreeNode TreeNode;
	private AdminUser admin;
	private AdminOperationRight adminOperationRight;
	private Empowerment empowerment;
	
	
	public AdminUser getAdmin() {
		return admin;
	}
	public void setAdmin(AdminUser admin) {
		this.admin = admin;
	}
	public AdminOperationRight getAdminOperationRight() {
		return adminOperationRight;
	}
	public void setAdminOperationRight(AdminOperationRight adminOperationRight) {
		this.adminOperationRight = adminOperationRight;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TreeNode getTreeNode() {
		return TreeNode;
	}
	public void setTreeNode(TreeNode treeNode) {
		TreeNode = treeNode;
	}
	public Empowerment getEmpowerment() {
		return empowerment;
	}
	public void setEmpowerment(Empowerment empowerment) {
		this.empowerment = empowerment;
	}
	
	
	
	

}
