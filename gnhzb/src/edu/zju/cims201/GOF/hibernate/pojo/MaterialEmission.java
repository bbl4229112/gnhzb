package edu.zju.cims201.GOF.hibernate.pojo;

public class MaterialEmission {
	private int id;
	private int materialId;
	private int componentId;
	private double emission;
	private double sum;
	
	private int userId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMaterialId() {
		return materialId;
	}
	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}
	public int getComponentId() {
		return componentId;
	}
	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}
	public double getEmission() {
		return emission;
	}
	public void setEmission(double emission) {
		this.emission = emission;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
}
