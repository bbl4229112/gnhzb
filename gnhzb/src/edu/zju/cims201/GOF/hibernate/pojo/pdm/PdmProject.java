package edu.zju.cims201.GOF.hibernate.pojo.pdm;

import java.util.Date;
import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojo.PdmModule;


public class PdmProject {
	
	private Long id;
	private String projectname;
	private String projectdetail;
    private String projectCode;
	private Employee creater;
	private Employee developer;
	private Employee builder;
	private Employee applier;
    private String mainFileName;
    private String xmlFileName;
    private PdmModule pdmModule;
	private Date creatTime;
	private Date startTime;
	private Date realStartTime;
	private Date finishTime;
	private Date realFinishtime;
	private String finishpercent;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getProjectdetail() {
		return projectdetail;
	}
	public void setProjectdetail(String projectdetail) {
		this.projectdetail = projectdetail;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getRealStartTime() {
		return realStartTime;
	}
	public void setRealStartTime(Date realStartTime) {
		this.realStartTime = realStartTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public Date getRealFinishtime() {
		return realFinishtime;
	}
	public void setRealFinishtime(Date realFinishtime) {
		this.realFinishtime = realFinishtime;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setMainFileName(String mainFileName) {
		this.mainFileName = mainFileName;
	}
	public String getMainFileName() {
		return mainFileName;
	}
	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}
	public String getXmlFileName() {
		return xmlFileName;
	}
	public Employee getCreater() {
		return creater;
	}
	public void setCreater(Employee creater) {
		this.creater = creater;
	}
	public Employee getDeveloper() {
		return developer;
	}
	public void setDeveloper(Employee developer) {
		this.developer = developer;
	}
	public Employee getBuilder() {
		return builder;
	}
	public void setBuilder(Employee builder) {
		this.builder = builder;
	}
	public Employee getApplier() {
		return applier;
	}
	public void setApplier(Employee applier) {
		this.applier = applier;
	}
	public PdmModule getPdmModule() {
		return pdmModule;
	}
	public void setPdmModule(PdmModule pdmModule) {
		this.pdmModule = pdmModule;
	}
	public String getFinishpercent() {
		return finishpercent;
	}
	public void setFinishpercent(String finishpercent) {
		this.finishpercent = finishpercent;
	}
}
