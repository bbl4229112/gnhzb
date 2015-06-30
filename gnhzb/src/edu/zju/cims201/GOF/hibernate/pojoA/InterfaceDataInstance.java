package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Entity
public class InterfaceDataInstance {
	private long id;
	private InterfaceData interfaceData;
	private Part interfaceInstance2;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	@ManyToOne
	@JoinColumn(name="interface_data_id")
	public InterfaceData getInterfaceData() {
		return interfaceData;
	}
	@OneToOne
	@JoinColumn(name="interface_instance2_id")
	public Part getInterfaceInstance2() {
		return interfaceInstance2;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setInterfaceData(InterfaceData interfaceData) {
		this.interfaceData = interfaceData;
	}
	public void setInterfaceInstance2(Part interfaceInstance2) {
		this.interfaceInstance2 = interfaceInstance2;
	}
	
	
}
