package edu.zju.cims201.GOF.hibernate.pojo;

public class UserRoleAssociation {
	
	private UserRolePK pk;
	
	private Long orderId;
	
//	private SystemUser user;
//	
//	private TreeNode roleNode;
	
	public UserRoleAssociation(){
		
	}
	
	public UserRoleAssociation(UserRolePK pk){
		this.pk=pk;
	}
	
	public UserRolePK getPk() {
		return pk;
	}
	public void setPk(UserRolePK pk) {
		this.pk = pk;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

//	public SystemUser getUser() {
//		return user;
//	}
//
//	public void setUser(SystemUser user) {
//		this.user = user;
//	}
//
//	public TreeNode getRoleNode() {
//		return roleNode;
//	}
//
//	public void setRoleNode(TreeNode roleNode) {
//		this.roleNode = roleNode;
//	}
	
	
	

}
