package edu.zju.cims201.GOF.hibernate.pojo;




public class Nodetype {
	private int id;
	private String name;
	
	public Nodetype() {
		// TODO Auto-generated constructor stub
	}
	public Nodetype(int id, String name
			) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
