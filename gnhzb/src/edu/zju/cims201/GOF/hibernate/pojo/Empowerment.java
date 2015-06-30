package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;
import java.util.Set;

public class Empowerment {
	
	
	private Long id;
	
	private Boolean isAdmin;
	
	private String name;
	
	private String description;
	
	private SystemUser creater;
	
	private TreeNode belongedNode;
	
	private Date empowermentDate;
	
	private Set<UserPrivilegeTriple> userPrivilegeTriples;
	
	private Set<RolePrivilegeTriple> rolePrivilegeTriples;
	
	private Set<AdminPrivilegeTriple> adminPrivilegeTriples;
	
	
	
	
	
	
	

	

	
	
	

	

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<AdminPrivilegeTriple> getAdminPrivilegeTriples() {
		return adminPrivilegeTriples;
	}

	public void setAdminPrivilegeTriples(
			Set<AdminPrivilegeTriple> adminPrivilegeTriples) {
		this.adminPrivilegeTriples = adminPrivilegeTriples;
	}

	public Set<RolePrivilegeTriple> getRolePrivilegeTriples() {
		return rolePrivilegeTriples;
	}

	public void setRolePrivilegeTriples(
			Set<RolePrivilegeTriple> rolePrivilegeTriples) {
		this.rolePrivilegeTriples = rolePrivilegeTriples;
	}

	public Set<UserPrivilegeTriple> getUserPrivilegeTriples() {
		return userPrivilegeTriples;
	}

	public void setUserPrivilegeTriples(
			Set<UserPrivilegeTriple> userPrivilegeTriples) {
		this.userPrivilegeTriples = userPrivilegeTriples;
	}

	public Date getEmpowermentDate() {
		return empowermentDate;
	}

	public void setEmpowermentDate(Date empowermentDate) {
		this.empowermentDate = empowermentDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TreeNode getBelongedNode() {
		return belongedNode;
	}

	public void setBelongedNode(TreeNode belongedNode) {
		this.belongedNode = belongedNode;
	}

	public SystemUser getCreater() {
		return creater;
	}

	public void setCreater(SystemUser creater) {
		this.creater = creater;
	}
	
	
	
	
	

	
	
	
	

}
