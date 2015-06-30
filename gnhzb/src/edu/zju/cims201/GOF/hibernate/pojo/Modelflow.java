package edu.zju.cims201.GOF.hibernate.pojo;



public class Modelflow {
	private int id;
	private Ioflow flow;
	private String type;
	private ProcessTemplate process;
	
	public Modelflow() {
		// TODO Auto-generated constructor stub
	}
	public Modelflow(int id, Ioflow flow, String type, ProcessTemplate process) {
		super();
		this.id = id;
		this.flow = flow;
		this.type = type;
		this.process = process;
	}
	public ProcessTemplate getProcess() {
		return process;
	}
	public void setProcess(ProcessTemplate process) {
		this.process = process;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Ioflow getFlow() {
		return flow;
	}
	public void setFlow(Ioflow flow) {
		this.flow = flow;
	}

	
}
