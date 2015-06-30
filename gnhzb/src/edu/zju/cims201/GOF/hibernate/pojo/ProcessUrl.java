package edu.zju.cims201.GOF.hibernate.pojo;

import edu.zju.cims201.GOF.hibernate.pojo.pdm.LcaTask;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmTask;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.TaskTreeNode;





public class ProcessUrl {
	private int id;
	private PdmTask task;
	private TaskTreeNode node;
	private String url;
	private String operationpage;
	
    public ProcessUrl(){
    	
    }
	public ProcessUrl(int id, String url, String operationpage,PdmTask task,TaskTreeNode node){
		super();
		this.id = id;
		this.url = url;
		this.operationpage=operationpage;
		this.task=task;
		this.node=node;
	}
   
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOperationpage() {
		return operationpage;
	}

	public void setOperationpage(String operationpage) {
		this.operationpage = operationpage;
	}
	public PdmTask getTask() {
		return task;
	}
	public void setTask(PdmTask task) {
		this.task = task;
	}
	public TaskTreeNode getNode() {
		return node;
	}
	public void setNode(TaskTreeNode node) {
		this.node = node;
	}
	
	
}
