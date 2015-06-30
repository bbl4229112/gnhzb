package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;





public class LccModule extends BaseModule{
	
	private LccModule parent;
	private Component component=new Component();
	private Set<LccModule> lccModules=new HashSet<LccModule>();
	
	public Component getComponent() {
		return component;
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	public Set<LccModule> getLccModules() {
		return lccModules;
	}
	public void setLccModules(Set<LccModule> lccModules) {
		this.lccModules = lccModules;
	}
	public LccModule getParent() {
		return parent;
	}
	public void setParent(LccModule parent) {
		this.parent = parent;
	}


	
	
	
}
