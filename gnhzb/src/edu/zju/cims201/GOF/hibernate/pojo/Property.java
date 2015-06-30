package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.zju.cims201.GOF.util.asm.BuildProperty;


public class Property extends BuildProperty implements java.io.Serializable,Comparable<Property>{
	
	
	private Long id;
//	private String name;
	private String description;	
	private Integer length;	
	private Boolean isCommon;
	private Boolean isNotNull;
	private Boolean isVisible;
	private String vcomponent;
	private String listvalue;
	
	//用于转换listValues值为list类型, 此属性add by hebi @2011年5月1日 19:57:59 
	private List listvalues;
	
    //用于置入该属性的值,此属性add by hebi  @2011年5月1日 19:57:59  
    private Object value; 
//	private Set<KtypeProperty> ktypeproperties=new HashSet<KtypeProperty>();
	
	
	
	
	
//	public Set<KtypeProperty> getKtypeproperties() {
//		return ktypeproperties;
//	}
//	public void setKtypeproperties(Set<KtypeProperty> ktypeproperties) {
//		this.ktypeproperties = ktypeproperties;
//	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public List getListvalues() {
		return listvalues;
	}
	public void setListvalues(List listvalues) {
		this.listvalues = listvalues;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public String getVcomponent() {
		return vcomponent;
	}
	public void setVcomponent(String vcomponent) {
		this.vcomponent = vcomponent;
	}

	public int compareTo(Property o) {
		if(this.id>o.getId())
			return 1;
		return 0;
	}
	public String getListvalue() {
		return listvalue;
	}
	public void setListvalue(String listvalue) {
		this.listvalue = listvalue;
	}

	

}
