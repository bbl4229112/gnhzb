package edu.zju.cims201.GOF.hibernate.pojo;




public class Nodecategory {
	private int id;
	private String name;
	
	public Nodecategory() {
		// TODO Auto-generated constructor stub
	}
	public Nodecategory(int id, String name
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
