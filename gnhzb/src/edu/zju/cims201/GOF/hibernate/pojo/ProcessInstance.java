package edu.zju.cims201.GOF.hibernate.pojo;

import java.sql.Date;

public class ProcessInstance {
	private int id;
	private String name;
	private ProcessTemplate processtemplate;
	private int carrierid;
	private Date starttime;
	private Date endtime;
	public ProcessInstance() {
		
	}


	public ProcessInstance(int id, String name, BaseModule module, int processid,
			ProcessInstance parent, ProcessTemplate processtemplate,Date starttime,Date endtime,int carrierid) {
		super();
		this.id = id;
		this.name = name;
		this.processtemplate=processtemplate;
		this.starttime=starttime;
		this.carrierid=carrierid;
		this.endtime=endtime;
	}
	public int getId() {
		return id;
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
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}


	public int getCarrierid() {
		return carrierid;
	}


	public void setCarrierid(int carrierid) {
		this.carrierid = carrierid;
	}


	public ProcessTemplate getProcesstemplate() {
		return processtemplate;
	}


	public void setProcesstemplate(ProcessTemplate processtemplate) {
		this.processtemplate = processtemplate;
	}


	public Date getEndtime() {
		return endtime;
	}


	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}


}
