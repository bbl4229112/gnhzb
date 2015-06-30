package edu.zju.cims201.GOF.rs.dto;


public class PlatStructTreeDTO {
	private long id;
	private long platId;
	private long moduleId;
	private long partsCount;
	private String classCode;
	private String classText;

	private int leaf;
	private int sequence;
	private int onlyone;
	private int ismust;
	private String configStatusName;
	public long getId() {
		return id;
	}
	public long getPlatId() {
		return platId;
	}
	public long getModuleId() {
		return moduleId;
	}
	public String getClassCode() {
		return classCode;
	}
	public String getClassText() {
		return classText;
	}
	public int getLeaf() {
		return leaf;
	}
	public int getSequence() {
		return sequence;
	}
	public int getOnlyone() {
		return onlyone;
	}
	public int getIsmust() {
		return ismust;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setPlatId(long platId) {
		this.platId = platId;
	}
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public void setClassText(String classText) {
		this.classText = classText;
	}
	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public void setOnlyone(int onlyone) {
		this.onlyone = onlyone;
	}
	public void setIsmust(int ismust) {
		this.ismust = ismust;
	}
	public long getPartsCount() {
		return partsCount;
	}
	public void setPartsCount(long partsCount) {
		this.partsCount = partsCount;
	}
	public String getConfigStatusName() {
		return configStatusName;
	}
	public void setConfigStatusName(String configStatusName) {
		this.configStatusName = configStatusName;
	}
    
	
	
	
}
