package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DomainTreeNode extends TreeNode {
	
	//private Integer id;
	
	//域树下挂知识
	private Set<MetaKnowledge> knowledges=new HashSet<MetaKnowledge>();
	
	private Set<RolePrivilegeTriple> rolePrivilegeTriples=new HashSet<RolePrivilegeTriple>();
	
	
	
	public Set<RolePrivilegeTriple> getRolePrivilegeTriples() {
		return rolePrivilegeTriples;
	}
	public void setRolePrivilegeTriples(
			Set<RolePrivilegeTriple> rolePrivilegeTriples) {
		this.rolePrivilegeTriples = rolePrivilegeTriples;
	}
	
	public Set<MetaKnowledge> getKnowledges() {
		return knowledges;
	}
	public void setKnowledges(Set<MetaKnowledge> knowledges) {
		this.knowledges = knowledges;
	}
	
	

	

}
