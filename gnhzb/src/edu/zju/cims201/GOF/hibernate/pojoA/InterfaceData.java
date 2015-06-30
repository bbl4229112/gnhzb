package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Entity
public class InterfaceData {
	private long id;
	private InterfaceModule interfaceModule;
	private String interfaceType;
	private String interfaceElement;
	private String interfaceParams;
	private String interfaceNumber;
	private Part interfaceInstance;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	@ManyToOne
	@JoinColumn(name="interface_module_id")
	public InterfaceModule getInterfaceModule() {
		return interfaceModule;
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
	@OneToOne
	@JoinColumn(name="interface_instance_id")
	public Part getInterfaceInstance() {
		return interfaceInstance;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setInterfaceModule(InterfaceModule interfaceModule) {
		this.interfaceModule = interfaceModule;
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
	public void setInterfaceInstance(Part interfaceInstance) {
		this.interfaceInstance = interfaceInstance;
	}
	
	
}
