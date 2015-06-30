package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;




public class Function {
	private int id;
	private String name;
	private String typee;
	public String getTypee() {
		return typee;
	}
	public void setTypee(String typee) {
		this.typee = typee;
	}
	
	
	
	public Function() {
		// TODO Auto-generated constructor stub
	}
	public Function(int id, String name, String typee){
		super();
		this.id = id;
		this.name = name;
		this.typee=typee;
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
