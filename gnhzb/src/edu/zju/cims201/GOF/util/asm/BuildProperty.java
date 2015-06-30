package edu.zju.cims201.GOF.util.asm;

public class BuildProperty {
	
	/*字段名称*/
	private String name;
	/*字段类型*/
	private String propertyType;
	
	public BuildProperty()
	{
		
	}
		
	public BuildProperty(String name,String propertyType)
	{
		this.name = name;
		this.propertyType = propertyType;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
	
}
