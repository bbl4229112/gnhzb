package edu.zju.cims201.GOF.util.ontology;

import java.util.Iterator;
import java.util.List;

public class ClassProp {
	private String classURI=null; 
	private List<PropNode> props=null; 
	
	public ClassProp(String classURI,List<PropNode> props){
		this.classURI=classURI;
		this.props=props;
	}
	
	public String getClassURI(){
		return this.classURI;
	}
	public List<PropNode> getProps(){
		return this.props;
	}
	
	public String getJSP(){
		Iterator<PropNode> IT = props.iterator();
		 StringBuffer sbf = new StringBuffer();
		while(IT.hasNext()){
			PropNode propNode = IT.next();
			sbf.append("");
		}
		return sbf.toString();
	}
}
