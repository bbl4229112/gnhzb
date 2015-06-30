package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;





public class BaseModule {
	private int id;
	private String name;
	private Date createdate;	
	private long createuserid;
	private Component component;
	private String note;
	private String moduledir;
	private String XmlFileName;
	private String moduleUUID;
	private int version;
	private Set<ProcessTemplate> processTemplates=new HashSet<ProcessTemplate>();
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	public int getId() {
		return id;
	}
	public Component getComponent() {
		return component;
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModuledir() {
		return moduledir;
	}
	public void setModuledir(String moduledir) {
		this.moduledir = moduledir;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	
	public String getXmlFileName() {
		return XmlFileName;
	}
	public void setXmlFileName(String xmlFileName) {
		XmlFileName = xmlFileName;
	}
	public String getModuleUUID() {
		return moduleUUID;
	}
	public void setModuleUUID(String moduleUUID) {
		this.moduleUUID = moduleUUID;
	}
	public Set<ProcessTemplate> getProcessTemplates() {
		return processTemplates;
	}
	public void setProcessTemplates(Set<ProcessTemplate> processTemplates) {
		this.processTemplates = processTemplates;
	}
	public long getCreateuserid() {
		return createuserid;
	}
	public void setCreateuserid(long createuserid) {
		this.createuserid = createuserid;
	}
	
	
}
