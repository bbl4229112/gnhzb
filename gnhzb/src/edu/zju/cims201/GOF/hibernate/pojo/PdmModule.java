package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;





public class PdmModule extends BaseModule{

	private String processid;
	private int  isparent;
	private String levelid;
	private String parentlevelid;
	private PdmModule parent;
	private Set<PdmModule> pdmModules=new HashSet<PdmModule>();
	private Set<PdmProcessTemplate> pdmprocessTemplates=new HashSet<PdmProcessTemplate>();
	public String getLevelid() {
		return levelid;
	}

	public void setLevelid(String levelid) {
		this.levelid = levelid;
	}

	public String getParentlevelid() {
		return parentlevelid;
	}

	public void setParentlevelid(String parentlevelid) {
		this.parentlevelid = parentlevelid;
	}

	public int getIsparent() {
		return isparent;
	}

	public void setIsparent(int isparent) {
		this.isparent = isparent;
	}

	public PdmModule getParent() {
		return parent;
	}

	public void setParent(PdmModule parent) {
		this.parent = parent;
	}

	public Set<PdmModule> getPdmModules() {
		return pdmModules;
	}

	public void setPdmModules(Set<PdmModule> pdmModules) {
		this.pdmModules = pdmModules;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public Set<PdmProcessTemplate> getPdmprocessTemplates() {
		return pdmprocessTemplates;
	}

	public void setPdmprocessTemplates(Set<PdmProcessTemplate> pdmprocessTemplates) {
		this.pdmprocessTemplates = pdmprocessTemplates;
	}
}
