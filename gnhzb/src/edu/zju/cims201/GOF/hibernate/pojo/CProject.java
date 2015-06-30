package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;


public class CProject {
	
	private Long id;
	private String projectname;
	private String projectdetail;
    private String projectCode;
	private SystemUser creater;
    private String mainFileName;
    private String xmlFileName;
	private Date creatTime;
	private Date startTime;
	private Date realStartTime;
	private Date finishTime;
	private Date realFinishtime;
	private int datelimit;
	private String finishpercent;
	private String status;
	private BaseModule baseModule;
	private Boolean isVisible;
	private String projectUuid;
	

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
	public SystemUser getCreater() {
		return creater;
	}
	public void setCreater(SystemUser creater) {
		this.creater = creater;
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
	public int getDatelimit() {
		return datelimit;
	}
	public void setDatelimit(int datelimit) {
		this.datelimit = datelimit;
	}
	public String getFinishpercent() {
		return finishpercent;
	}
	public void setFinishpercent(String finishpercent) {
		this.finishpercent = finishpercent;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
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
	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}
	public String getProjectUuid() {
		return projectUuid;
	}
	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}
	public String getXmlFileName() {
		return xmlFileName;
	}
	public BaseModule getBaseModule() {
		return baseModule;
	}
	public void setBaseModule(BaseModule baseModule) {
		this.baseModule = baseModule;
	}


}
