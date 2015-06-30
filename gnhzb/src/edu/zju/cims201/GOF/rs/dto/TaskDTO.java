package edu.zju.cims201.GOF.rs.dto;

import edu.zju.cims201.GOF.hibernate.pojo.PdmModule;
import edu.zju.cims201.GOF.hibernate.pojo.ProcessTemplate;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.Employee;

public class TaskDTO {
	private Long id;
	private String name;
	private String status;
	private Employee checkperson;
	private String carriername;
	private String mainFileName;
	private String projectname;
	private String pjcreateusername;
	private String starttime;
	private ProcessTemplate processTemplate;
	private PdmModule pdmModule;
	private int lcamoduleid;
	private String endtime;
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
	public String getCarriername() {
		return carriername;
	}
	public void setCarriername(String carriername) {
		this.carriername = carriername;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getPjcreateusername() {
		return pjcreateusername;
	}
	public void setPjcreateusername(String pjcreateusername) {
		this.pjcreateusername = pjcreateusername;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getLcamoduleid() {
		return lcamoduleid;
	}
	public void setLcamoduleid(int lcamoduleid) {
		this.lcamoduleid = lcamoduleid;
	}

}
