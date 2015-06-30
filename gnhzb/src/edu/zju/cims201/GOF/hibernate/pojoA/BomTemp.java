package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BomTemp{
	
	private long id;
	private PlatformManage plat;
	private PlatStructTree platStruct;
	private ClassificationTree moduleClass;
	private String classCode;
	private String classText;
	private Part part;
	/**
	 * 规则的状态
	 * 1：必选
	 * 2：可选
	 * 3：排除
	 */
	private int status;
	private String info;
	private Part partSelected;
	private int quantity;
	private OrderManage order;
	/**
	 * 配置状态
	 * 1：未配置，未与其他已配置零件关联
	 * 2：未配置，已经与其他已配置零件关联（必选）
	 * 3：已配置
	 */
	private int config;
	
	public BomTemp(){}
	public BomTemp(PlatStructTree platStruct,ClassificationTree moduleClass,
			Part part,int quantity,OrderManage order){
		this.platStruct = platStruct;
		this.moduleClass = moduleClass;
		this.part = part;
		this.quantity = quantity;
		this.order =order;
	}
	
	public BomTemp(PlatStructTree platStruct,Part part,int quantity,OrderManage order){
		this.platStruct = platStruct;
		this.part = part;
		this.quantity = quantity;
		this.order =order;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="plat_id")
	public PlatformManage getPlat() {
		return plat;
	}
	public void setPlat(PlatformManage plat) {
		this.plat = plat;
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
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassText() {
		return classText;
	}
	
	public void setClassText(String classText) {
		this.classText = classText;
	}
	@OneToOne
	@JoinColumn(name="part_id")
	public Part getPart() {
		return part;
	}
	public void setPart(Part part) {
		this.part = part;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	@OneToOne
	@JoinColumn(name="part_selected_id")
	public Part getPartSelected() {
		return partSelected;
	}
	public void setPartSelected(Part partSelected) {
		this.partSelected = partSelected;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@ManyToOne
	@JoinColumn(name="order_id")
	public OrderManage getOrder() {
		return order;
	}
	public void setOrder(OrderManage order) {
		this.order = order;
	}
	public int getConfig() {
		return config;
	}
	public void setConfig(int config) {
		this.config = config;
	}

	
}
