package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;

public class Expert {
	
	private Long id;
	private SystemUser user;
	private Set<TreeNode> treeNodes = new HashSet<TreeNode>();	
	private Set<MetaKnowledge> unanswers = new HashSet<MetaKnowledge>();
	
	
	public Expert(Long id, SystemUser user, Set<TreeNode> treeNodes,
			Set<MetaKnowledge> unanswers) {
		super();
		this.id = id;
		this.user = user;
		this.treeNodes = treeNodes;
		this.unanswers = unanswers;
	}

	public Expert() {
		super();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public SystemUser getUser() {
		return user;
	}
	public void setUser(SystemUser user) {
		this.user = user;
	}
	public Set<TreeNode> getTreeNodes() {
		return treeNodes;
	}
	public void setTreeNodes(Set<TreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}
	public Set<MetaKnowledge> getUnanswers() {
		return unanswers;
	}
	public void setUnanswers(Set<MetaKnowledge> unanswers) {
		this.unanswers = unanswers;
	}
	
	

}
