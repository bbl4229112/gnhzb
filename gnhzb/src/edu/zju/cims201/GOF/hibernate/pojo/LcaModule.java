package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;





public class LcaModule extends BaseModule{

	private LcaModule parent;
	private Component component;
	private String StageXmlFileName;
	private String name;
	private Set<LcaModule> lcaModules=new HashSet<LcaModule>();

	public LcaModule getParent() {
		return parent;
	}
	public void setParent(LcaModule parent) {
		this.parent = parent;
	}


	public Component getComponent() {
		return component;
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	public Set<LcaModule> getLcaModules() {
		return lcaModules;
	}
	public void setLcaModules(Set<LcaModule> lcaModules) {
		this.lcaModules = lcaModules;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStageXmlFileName() {
		return StageXmlFileName;
	}
	public void setStageXmlFileName(String stageXmlFileName) {
		StageXmlFileName = stageXmlFileName;
	}

	
	
	
	
}
