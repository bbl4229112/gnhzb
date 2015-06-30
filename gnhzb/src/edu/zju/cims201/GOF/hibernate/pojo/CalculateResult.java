package edu.zju.cims201.GOF.hibernate.pojo; 


public class CalculateResult {
	private int id;
	private String userName;
	private int componentId;	
	private double stage1Emission;
	private double stage2Emission;
	private double stage3Emission;
	private double stage4Emission;
	private double stage5Emission;
	private double stage6Emission;
	private double finalResult;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getComponentId() {
		return componentId;
	}
	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}
	public double getStage1Emission() {
		return stage1Emission;
	}
	public void setStage1Emission(double stage1Emission) {
		this.stage1Emission = stage1Emission;
	}
	public double getStage2Emission() {
		return stage2Emission;
	}
	public void setStage2Emission(double stage2Emission) {
		this.stage2Emission = stage2Emission;
	}
	public double getStage3Emission() {
		return stage3Emission;
	}
	public void setStage3Emission(double stage3Emission) {
		this.stage3Emission = stage3Emission;
	}
	public double getStage4Emission() {
		return stage4Emission;
	}
	public void setStage4Emission(double stage4Emission) {
		this.stage4Emission = stage4Emission;
	}
	public double getStage5Emission() {
		return stage5Emission;
	}
	public void setStage5Emission(double stage5Emission) {
		this.stage5Emission = stage5Emission;
	}
	public double getStage6Emission() {
		return stage6Emission;
	}
	public void setStage6Emission(double stage6Emission) {
		this.stage6Emission = stage6Emission;
	}
	public double getFinalResult() {
		return finalResult;
	}
	public void setFinalResult(double finalResult) {
		this.finalResult = finalResult;
	}
	
	
}