package edu.zju.cims201.GOF.rs.dto;

import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.util.asm.BuildProperty;

public class PropertyDTO extends BuildProperty implements java.io.Serializable,Comparable<PropertyDTO>{

	private Long id;
	private String and_or;
	private String vcomponent;	
	private String description;	
	private Integer length;	
	private Boolean isCommon;
	private Boolean isNotNull;
	private Boolean isVisible;
	private String valuelist;
	private Boolean searchable;
	private Object value;
	
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getValuelist() {
		return valuelist;
	}
	public void setValuelist(String valuelist) {
		this.valuelist = valuelist;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Boolean getIsCommon() {
		return isCommon;
	}
	public void setIsCommon(Boolean isCommon) {
		this.isCommon = isCommon;
	}
	public Boolean getIsNotNull() {
		return isNotNull;
	}
	public void setIsNotNull(Boolean isNotNull) {
		this.isNotNull = isNotNull;
	}
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public int compareTo(PropertyDTO o) {
		if(this.id>o.getId())
			return 1;
		return 0;
	}
	public String getVcomponent() {
		return vcomponent;
	}
	public void setVcomponent(String vcomponent) {
		this.vcomponent = vcomponent;
	}
	public String getAnd_or() {
		return and_or;
	}
	public void setAnd_or(String and_or) {
		this.and_or = and_or;
	}
	public Boolean getSearchable() {
		return searchable;
	}
	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}
}
