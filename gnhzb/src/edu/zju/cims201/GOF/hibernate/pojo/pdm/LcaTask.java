package edu.zju.cims201.GOF.hibernate.pojo.pdm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import edu.zju.cims201.GOF.hibernate.pojo.LcaModule;
import edu.zju.cims201.GOF.hibernate.pojo.PdmModule;
import edu.zju.cims201.GOF.hibernate.pojo.ProcessTemplate;

public class LcaTask {

	
	private Long id;
	private String name;
	private String processid;
	private String status;
	private Employee checkperson;
	private Employee carrier;
	private String mainFileName;
	private Date starttime;
	private ProcessTemplate processTemplate;
	private PdmModule pdmModule;
	private LcaModule lcaModule;
	private Date endtime;
	private PdmProject pdmProject;
	private LcaTask parentLcaTask;
	private Set<LcaTask> subLcaTasks=new HashSet<LcaTask>();
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
	public ProcessTemplate getProcessTemplate() {
		return processTemplate;
	}
	public void setProcessTemplate(ProcessTemplate processTemplate) {
		this.processTemplate = processTemplate;
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
	
	public LcaModule getLcaModule() {
		return lcaModule;
	}
	public void setLcaModule(LcaModule lcaModule) {
		this.lcaModule = lcaModule;
	}
	public String getProcessid() {
		return processid;
	}
	public void setProcessid(String processid) {
		this.processid = processid;
	}
	
	public Set<LcaTask> getSubLcaTasks() {
		return subLcaTasks;
	}
	public void setSubLcaTasks(Set<LcaTask> subLcaTasks) {
		this.subLcaTasks = subLcaTasks;
	}
	public LcaTask getParentLcaTask() {
		return parentLcaTask;
	}
	public void setParentLcaTask(LcaTask parentLcaTask) {
		this.parentLcaTask = parentLcaTask;
	}
	
	

}
