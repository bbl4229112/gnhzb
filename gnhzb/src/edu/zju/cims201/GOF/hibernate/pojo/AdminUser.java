package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;

public class AdminUser extends SystemUser {
	
	
	
	public AdminUser(){
		
	}
	public AdminUser(SystemUser user) {
		//super();
		this.setId(user.getId());
		this.setHobby(user.getHobby());
		this.setEmail(user.getEmail());
		this.setIntroduction(user.getIntroduction());
		this.setIsVisible(user.getIsVisible());
		this.setName(user.getName());
		this.setPassword(user.getPassword());
	}
	

	private Set<AdminPrivilegeTriple> adminPrivilegeTriples=new HashSet<AdminPrivilegeTriple>();

	public Set<AdminPrivilegeTriple> getAdminPrivilegeTriples() {
		return adminPrivilegeTriples;
	}

	public void setAdminPrivilegeTriples(
			Set<AdminPrivilegeTriple> adminPrivilegeTriples) {
		this.adminPrivilegeTriples = adminPrivilegeTriples;
	}

	
}
