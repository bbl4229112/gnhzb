package edu.zju.cims201.GOF.rs.dto;

public class BomDetailDTO {
	
	private long bomId;
	private String moduleCode;
	private String moduleName;
	private long moduleId;
	private long partId;
	private String partName;
	private String partNumber;
	private int partCount;
	
	
	

	public long getBomId() {
		return bomId;
	}
	public void setBomId(long bomId) {
		this.bomId = bomId;
	}
	public long getPartId() {
		return partId;
	}
	public void setPartId(long partId) {
		this.partId = partId;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public int getPartCount() {
		return partCount;
	}
	public void setPartCount(int i) {
		this.partCount = i;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public long getModuleId() {
		return moduleId;
	}
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}
	
	
	
	
}
