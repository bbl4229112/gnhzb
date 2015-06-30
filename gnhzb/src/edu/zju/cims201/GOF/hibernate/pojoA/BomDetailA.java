package edu.zju.cims201.GOF.hibernate.pojoA;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BomDetailA {
	private long id;
	private ClassificationTreeA moduleClass;
	private PartA part;
	private int quantity;
	private BomA bom;
	
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
	
	@OneToOne
	@JoinColumn(name="module_class_id")
	public ClassificationTreeA getModuleClass() {
		return moduleClass;
	}
	
	public void setModuleClass(ClassificationTreeA moduleClass) {
		this.moduleClass = moduleClass;
	}

	@OneToOne
	@JoinColumn(name="part_id")
	public PartA getPart() {
		return part;
	}
	public void setPart(PartA part) {
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
	public BomA getBom() {
		return bom;
	}
	public void setBom(BomA bom) {
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
