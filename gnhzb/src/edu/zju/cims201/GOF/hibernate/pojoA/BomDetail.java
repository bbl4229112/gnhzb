package edu.zju.cims201.GOF.hibernate.pojoA;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BomDetail {
	private long id;
	private PlatStructTree platStruct;
	private ClassificationTree moduleClass;
	private Part part;
	private int quantity;
	private Bom bom;
	
	private String partUuid;
	private String moduleUuid;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="plat_struct_id")
	public PlatStructTree getPlatStruct() {
		return platStruct;
	}
	public void setPlatStruct(PlatStructTree platStruct) {
		this.platStruct = platStruct;
	}
	@OneToOne
	@JoinColumn(name="module_class_id")
	public ClassificationTree getModuleClass() {
		return moduleClass;
	}
	
	public void setModuleClass(ClassificationTree moduleClass) {
		this.moduleClass = moduleClass;
	}

	@OneToOne
	@JoinColumn(name="part_id")
	public Part getPart() {
		return part;
	}
	public void setPart(Part part) {
		this.part = part;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@ManyToOne
	@JoinColumn(name="bom_id")
	public Bom getBom() {
		return bom;
	}
	public void setBom(Bom bom) {
		this.bom = bom;
	}
	public String getPartUuid() {
		return partUuid;
	}
	public void setPartUuid(String partUuid) {
		this.partUuid = partUuid;
	}
	public String getModuleUuid() {
		return moduleUuid;
	}
	public void setModuleUuid(String moduleUuid) {
		this.moduleUuid = moduleUuid;
	}
	
}
