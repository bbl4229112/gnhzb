package edu.zju.cims201.GOF.rs.dto;

import java.util.List;

public class InterfaceDataDTO {
	private long id;
	private long interfaceInstanceId;
	private String interfaceInstance2Id;
	private String interfaceInstanceNumber;
	private String interfaceInstance2Number;
	private String interfaceType;
	private String interfaceElement;
	private String interfaceParams;
	private String interfaceNumber;
	private List<InterfaceDataDTO> children;
	public long getId() {
		return id;
	}
	public long getInterfaceInstanceId() {
		return interfaceInstanceId;
	}
	public String getInterfaceInstance2Id() {
		return interfaceInstance2Id;
	}
	public String getInterfaceInstanceNumber() {
		return interfaceInstanceNumber;
	}
	public String getInterfaceInstance2Number() {
		return interfaceInstance2Number;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public String getInterfaceElement() {
		return interfaceElement;
	}
	public String getInterfaceParams() {
		return interfaceParams;
	}
	public String getInterfaceNumber() {
		return interfaceNumber;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setInterfaceInstanceId(long interfaceInstanceId) {
		this.interfaceInstanceId = interfaceInstanceId;
	}
	public void setInterfaceInstance2Id(String interfaceInstance2Id) {
		this.interfaceInstance2Id = interfaceInstance2Id;
	}
	public void setInterfaceInstanceNumber(String interfaceInstanceNumber) {
		this.interfaceInstanceNumber = interfaceInstanceNumber;
	}
	public void setInterfaceInstance2Number(String interfaceInstance2Number) {
		this.interfaceInstance2Number = interfaceInstance2Number;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public void setInterfaceElement(String interfaceElement) {
		this.interfaceElement = interfaceElement;
	}
	public void setInterfaceParams(String interfaceParams) {
		this.interfaceParams = interfaceParams;
	}
	public void setInterfaceNumber(String interfaceNumber) {
		this.interfaceNumber = interfaceNumber;
	}
	public List<InterfaceDataDTO> getChildren() {
		return children;
	}
	public void setChildren(List<InterfaceDataDTO> children) {
		this.children = children;
	}

	
	
	
}
