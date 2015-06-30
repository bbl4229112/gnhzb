package edu.zju.cims201.GOF.hibernate.pojo.pdm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import edu.zju.cims201.GOF.hibernate.pojo.PdmModule;
import edu.zju.cims201.GOF.hibernate.pojo.PdmProcessTemplate;

public class PdmTask {

	
	private Long id;
	private String name;
	private String status;
	private Employee checkperson;
	private Employee carrier;
	private String mainFileName;
	private Date starttime;
	private PdmProcessTemplate pdmprocessTemplate;
	private PdmModule pdmModule;
	private Date endtime;
	private PdmProject pdmProject;
	private PdmTask parentPdmTask;
	private Set<PdmTask> subPdmTasks=new HashSet<PdmTask>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Employee getCheckperson() {
		return checkperson;
	}
	public void setCheckperson(Employee checkperson) {
		this.checkperson = checkperson;
	}
	
	public PdmModule getPdmModule() {
		return pdmModule;
	}
	public void setPdmModule(PdmModule pdmModule) {
		this.pdmModule = pdmModule;
	}
	public String getMainFileName() {
		return mainFileName;
	}
	public void setMainFileName(String mainFileName) {
		this.mainFileName = mainFileName;
	}
	public Employee getCarrier() {
		return carrier;
	}
	public void setCarrier(Employee carrier) {
		this.carrier = carrier;
	}
	public PdmProject getPdmProject() {
		return pdmProject;
	}
	public void setPdmProject(PdmProject pdmProject) {
		this.pdmProject = pdmProject;
	}
	public PdmTask getParentPdmTask() {
		return parentPdmTask;
	}
	public void setParentPdmTask(PdmTask parentPdmTask) {
		this.parentPdmTask = parentPdmTask;
	}
	public Set<PdmTask> getSubPdmTasks() {
		return subPdmTasks;
	}
	public void setSubPdmTasks(Set<PdmTask> subPdmTasks) {
		this.subPdmTasks = subPdmTasks;
	}
	public PdmProcessTemplate getPdmprocessTemplate() {
		return pdmprocessTemplate;
	}
	public void setPdmprocessTemplate(PdmProcessTemplate pdmprocessTemplate) {
		this.pdmprocessTemplate = pdmprocessTemplate;
	}

}
