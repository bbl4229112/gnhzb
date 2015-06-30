package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;




public class Node {
	private int id;
	private String name;
	private Nodetype nodetype;
	private String nodedrawtype;
	private String img;
	private String label;
	private Nodecategory nodecategory;
	private String description;
	private Set<Function> functions=new HashSet<Function>();
	public Node() {
		// TODO Auto-generated constructor stub
	}
	public Node(int id, String name, String nodedrawtype,String label, String description,Nodetype nodetype,Set<Function>functions,String img,Nodecategory nodecategory
			) {
		super();
		this.id = id;
		this.name = name;
		this.nodetype=nodetype;
		this.functions=functions;
		this.nodecategory=nodecategory;
		this.nodedrawtype=nodedrawtype;
		this.img=img;
		this.label=label;
		this.description=description;
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
	public Nodetype getNodetype() {
		return nodetype;
	}
	public void setNodetype(Nodetype nodetype) {
		this.nodetype = nodetype;
	}
	public Set<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Nodecategory getNodecategory() {
		return nodecategory;
	}
	public void setNodecategory(Nodecategory nodecategory) {
		this.nodecategory = nodecategory;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getNodedrawtype() {
		return nodedrawtype;
	}
	public void setNodedrawtype(String nodedrawtype) {
		this.nodedrawtype = nodedrawtype;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
