package edu.zju.cims201.GOF.util.ontology;

public class IndivDTO {
	
	private String text;
	private String value;
	private String nameSpace;
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public IndivDTO(String localname,String nameSpace ){
		this.text=localname;
		this.value=localname;
		this.nameSpace=nameSpace;
	}
	

	
	 public String getNameSpace() {  
	        return nameSpace;  
	}  
	public void setNameSpace(String nameSpace) {  
	        this.nameSpace = nameSpace;  
	    }

}
