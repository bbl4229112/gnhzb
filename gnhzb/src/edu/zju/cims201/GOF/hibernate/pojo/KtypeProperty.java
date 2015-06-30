package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;

public class KtypeProperty {
	
	
	
	private Long id;
	
	private Ktype ktype;
	private Property property;
	private String showname;
	private boolean searchable;
	private String vcomponent;
	private String listvalue;

	public String getVcomponent() {
		return vcomponent;
	}
	public void setVcomponent(String vcomponent) {
		this.vcomponent = vcomponent;
	}
	public String getListvalue() {
		return listvalue;
	}
	public void setListvalue(String listvalue) {
		this.listvalue = listvalue;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Ktype getKtype() {
		return ktype;
	}
	public void setKtype(Ktype ktype) {
		this.ktype = ktype;
	}
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	public String getShowname() {
		return showname;
	}
	public void setShowname(String showname) {
		this.showname = showname;
	}
	public boolean isSearchable() {
		return searchable;
	}
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	
	
	
	

}
