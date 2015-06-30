package edu.zju.cims201.GOF.hibernate.pojo.pdm;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.sparql.algebra.op.OpReduced;
import com.sun.org.apache.bcel.internal.generic.NEW;

import edu.zju.cims201.GOF.hibernate.pojo.Empowerment;
import edu.zju.cims201.GOF.hibernate.pojo.Flow;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.InterestModel;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.UserKnowledgeTag;
import edu.zju.cims201.GOF.hibernate.pojo.UserPrivilegeTriple;

public class PrivilegeOperationRole {

	
	private Long id;
	private Privilege privilege=new Privilege();
	private OperationRoles operationRoles=new OperationRoles();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Privilege getPrivilege() {
		return privilege;
	}
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}
	public OperationRoles getOperationRoles() {
		return operationRoles;
	}
	public void setOperationRoles(OperationRoles operationRoles) {
		this.operationRoles = operationRoles;
	}

}
