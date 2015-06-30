package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import edu.zju.cims201.GOF.hibernate.pojo.pdm.TaskTreeNode;

public class PdmProcessTemplate extends ProcessTemplate {
	private PdmModule module;
	private TaskTreeNode tasktreenode;
	private String input;
	private int  orderid;
	private String output;
	private Set<RelatedModel> relatedmodels=new HashSet<RelatedModel>();
	
	public TaskTreeNode getTasktreenode() {
		return tasktreenode;
	}


	public void setTasktreenode(TaskTreeNode tasktreenode) {
		this.tasktreenode = tasktreenode;
	}


	public String getInput() {
		return input;
	}


	public void setInput(String input) {
		this.input = input;
	}


	public String getOutput() {
		return output;
	}


	public void setOutput(String output) {
		this.output = output;
	}


	public PdmModule getModule() {
		return module;
	}


	public void setModule(PdmModule module) {
		this.module = module;
	}


	


	public Set<RelatedModel> getRelatedmodels() {
		return relatedmodels;
	}


	public void setRelatedmodels(Set<RelatedModel> relatedmodels) {
		this.relatedmodels = relatedmodels;
	}


	public int getOrderid() {
		return orderid;
	}


	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}




}
