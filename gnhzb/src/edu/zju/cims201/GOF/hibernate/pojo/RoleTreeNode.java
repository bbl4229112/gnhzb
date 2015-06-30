package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RoleTreeNode extends TreeNode {
	
	
	//角色树下挂权限-角色对域或者分类树节点具有的权限集合
	private Set<RolePrivilegeTriple> rolePrivilegeTriples=new HashSet<RolePrivilegeTriple>();
	//角色树下挂人。
	private Set<SystemUser> users=new HashSet<SystemUser>();
	//以上描述了人对域或者分类树具有的权限集合。
	
	private Set<UserRoleAssociation> associations=new HashSet<UserRoleAssociation>();
	
	

	
	
	

	public Set<RolePrivilegeTriple> getRolePrivilegeTriples() {
		return rolePrivilegeTriples;
	}

	public void setRolePrivilegeTriples(Set<RolePrivilegeTriple> rolePrivilegeTriples) {
		this.rolePrivilegeTriples = rolePrivilegeTriples;
	}

	

	public Set<SystemUser> getUsers() {
		return users;
	}

	public void setUsers(Set<SystemUser> users) {
		this.users = users;
	}

	public Set<UserRoleAssociation> getAssociations() {
		return associations;
	}

	public void setAssociations(Set<UserRoleAssociation> associations) {
		this.associations = associations;
	}
	
	
	

	

	
	
	

}
