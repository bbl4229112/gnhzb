package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity 
public class SmlParameterPool {
	private long id;
	private String smlName;
	private String smlCode;
	private String DataType;
	private double maxValue;
	private double minValue;
	private String information;
	private String defaultValue;
	private SmlUnitPool unit;
	private ClassificationTree moduleBelong;
	private SmlCodePool codeBelong;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public String getSmlName() {
		return smlName;
	}
	public String getSmlCode() {
		return smlCode;
	}
	public String getDataType() {
		return DataType;
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
	@ManyToOne
	@JoinColumn(name="unit_id")
	public SmlUnitPool getUnit() {
		return unit;
	}
	@ManyToOne
	@JoinColumn(name="module_belong_id")
	public ClassificationTree getModuleBelong() {
		return moduleBelong;
	}
	@ManyToOne
	@JoinColumn(name="code_belong_id")
	public SmlCodePool getCodeBelong() {
		return codeBelong;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setSmlName(String smlName) {
		this.smlName = smlName;
	}
	public void setSmlCode(String smlCode) {
		this.smlCode = smlCode;
	}
	public void setDataType(String dataType) {
		DataType = dataType;
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
	public void setUnit(SmlUnitPool unit) {
		this.unit = unit;
	}
	public void setModuleBelong(ClassificationTree moduleBelong) {
		this.moduleBelong = moduleBelong;
	}
	public void setCodeBelong(SmlCodePool codeBelong) {
		this.codeBelong = codeBelong;
	}
}
