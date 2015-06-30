package edu.zju.cims201.GOF.rs.dto;


public class SmlParameterPoolDTO {
	private long id;
	private long moduleId;
	private long smlCodeId;
	private long unitId;

	private String smlName;
	private String smlCode;
	private String dataType;
	private double maxValue;
	private double minValue;
	private String information;
	private String defaultValue;
	private String unit;
	private String moduleBelong;
	private String codeBelong;
	public long getId() {
		return id;
	}
	public long getModuleId() {
		return moduleId;
	}
	public long getSmlCodeId() {
		return smlCodeId;
	}
	public String getSmlName() {
		return smlName;
	}
	public String getSmlCode() {
		return smlCode;
	}
	public String getDataType() {
		return dataType;
	}
	public double getMaxValue() {
		return maxValue;
	}
	public double getMinValue() {
		return minValue;
	}
	public String getInformation() {
		return information;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public String getUnit() {
		return unit;
	}
	public String getModuleBelong() {
		return moduleBelong;
	}
	public String getCodeBelong() {
		return codeBelong;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}
	public void setSmlCodeId(long smlCodeId) {
		this.smlCodeId = smlCodeId;
	}
	public void setSmlName(String smlName) {
		this.smlName = smlName;
	}
	public void setSmlCode(String smlCode) {
		this.smlCode = smlCode;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setModuleBelong(String moduleBelong) {
		this.moduleBelong = moduleBelong;
	}
	public void setCodeBelong(String codeBelong) {
		this.codeBelong = codeBelong;
	}
	public long getUnitId() {
		return unitId;
	}
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	
}
