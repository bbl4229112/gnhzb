package edu.zju.cims201.GOF.rs.dto;

public class InterfaceModuleDTO {
	private long id;
	private long moduleId;
	private long module2Id;
	private String interfaceNumber;
	private String interfaceRelation;
	private String interfaceName;
	private String interfaceType;
	private String  module2Name;
	public long getId() {
		return id;
	}
	public String getInterfaceNumber() {
		return interfaceNumber;
	}
	public String getInterfaceRelation() {
		return interfaceRelation;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public String getModule2Name() {
		return module2Name;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setInterfaceNumber(String interfaceNumber) {
		this.interfaceNumber = interfaceNumber;
	}
	public void setInterfaceRelation(String interfaceRelation) {
		this.interfaceRelation = interfaceRelation;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public void setModule2Name(String module2Name) {
		this.module2Name = module2Name;
	}
	public long getModuleId() {
		return moduleId;
	}
	public long getModule2Id() {
		return module2Id;
	}
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}
	public void setModule2Id(long module2Id) {
		this.module2Id = module2Id;
	}
	
}
