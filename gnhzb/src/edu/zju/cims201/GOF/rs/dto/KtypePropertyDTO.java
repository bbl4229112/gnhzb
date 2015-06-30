package edu.zju.cims201.GOF.rs.dto;

import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.Property;

public class KtypePropertyDTO {
	
private Long id;
	
	private String ktype;
	private String property;
	private String showname;
	private boolean searchable;
	private String vcomponent;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKtype() {
		return ktype;
	}
	public void setKtype(String ktype) {
		this.ktype = ktype;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
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
	public String getVcomponent() {
		return vcomponent;
	}
	public void setVcomponent(String vcomponent) {
		this.vcomponent = vcomponent;
	}
	
}
