package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DraftType {
	private Long id;
	private String typeName;
	private String typeCode;
	private String typeDes;
	private String typeSuffix;
	private int ismaster;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeDes() {
		return typeDes;
	}
	public void setTypeDes(String typeDes) {
		this.typeDes = typeDes;
	}
	public String getTypeSuffix() {
		return typeSuffix;
	}
	public void setTypeSuffix(String typeSuffix) {
		this.typeSuffix = typeSuffix;
	}
	public int getIsmaster() {
		return ismaster;
	}
	public void setIsmaster(int ismaster) {
		this.ismaster = ismaster;
	}
	
	
}
