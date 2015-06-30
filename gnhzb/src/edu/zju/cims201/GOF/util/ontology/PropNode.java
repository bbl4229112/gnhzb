package edu.zju.cims201.GOF.util.ontology;

import java.util.List;

import edu.zju.cims201.GOF.rs.dto.TreeNodeDTO;

public class PropNode {
	
	private String name;
	private String propURI;
	private String propValue;
	private String propType;
	private List<TreeNodeDTO> propRange;


	public List<TreeNodeDTO> getPropRange() {
		return propRange;
	}
	public void setPropRange(List<TreeNodeDTO> propRange) {
		this.propRange = propRange;
	}
	public void setName(String name){
		
		this.name=name;
	}
	public String getName(){
		return name;
	}
	
	public void setPropURI(String propURI){
		
		this.propURI=propURI;
	}
	public String getPropURI(){
		return propURI;
	}
	
	public void setPropValue(String propValue){
		
		this.propValue=propValue;
	}
	public String getPropValue(){
		return propValue;
	}
	
	public void setPropType(String propType){
		
		this.propType=propType;
	}
	public String getPropType(){
		return propType;
	}

}
