package edu.zju.cims201.GOF.util.ontology;

public class Indiv {
	
	private String localname;
	private String nameSpace;
	
	public Indiv(String localname,String nameSpace ){
		this.localname=localname;
		this.nameSpace=nameSpace;
	}
	
	 public String getLocalname() {  
	        return localname;  
	}  
	public void setLocalname(String localname) {  
	        this.localname = localname;  
	    }
	
	 public String getNameSpace() {  
	        return nameSpace;  
	}  
	public void setNameSpace(String nameSpace) {  
	        this.nameSpace = nameSpace;  
	    }

}
