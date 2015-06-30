package edu.zju.cims201.GOF.hibernate.pojo;


public class Ioflow {
	private int id;
	private String name;
	private int unitcost;	
	private int amount;
	private String unit;
	private String type;
	
	//CalculateResult,碳足迹计�?0130602--杨文�?
	//private Set<CalculateResult> calculateResult=new HashSet<CalculateResult>();
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUnitcost() {
		return unitcost;
	}
	public void setUnitcost(int unitcost) {
		this.unitcost = unitcost;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
