package edu.zju.cims201.GOF.hibernate.pojoA;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class InterfaceModule {
	private long id;
	private ClassificationTree module;
	private String interfaceNumber;
	private String interfaceRelation;
	private String interfaceName;
	private String interfaceType;
	private ClassificationTree module2;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name="module_id")
	public ClassificationTree getModule() {
		return module;
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
	@ManyToOne
	@JoinColumn(name="module2_id")
	public ClassificationTree getModule2() {
		return module2;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setModule(ClassificationTree module) {
		this.module = module;
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
	public void setModule2(ClassificationTree module2) {
		this.module2 = module2;
	}
	
	
	
}
