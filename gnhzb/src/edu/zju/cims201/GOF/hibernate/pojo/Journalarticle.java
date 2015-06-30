// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;
import java.util.Date;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Journalarticle extends MetaKnowledge implements Knowledge,
		Serializable {

	private String journaltitle;
	private Date dateofpublication;
	private String vol;
	private String no;
	private String pagerange;
	private String organization;
	private String authorintroduction;
	private String funding;
	private String refer;

	public Journalarticle() {
	}

	public String getJournaltitle() {
		return journaltitle;
	}

	public void setJournaltitle(String s) {
		journaltitle = s;
	}

	public Date getDateofpublication() {
		return dateofpublication;
	}

	public void setDateofpublication(Date date) {
		dateofpublication = date;
	}

	public String getVol() {
		return vol;
	}

	public void setVol(String s) {
		vol = s;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String s) {
		no = s;
	}

	public String getPagerange() {
		return pagerange;
	}

	public void setPagerange(String s) {
		pagerange = s;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String s) {
		organization = s;
	}

	public String getAuthorintroduction() {
		return authorintroduction;
	}

	public void setAuthorintroduction(String s) {
		authorintroduction = s;
	}

	public String getFunding() {
		return funding;
	}

	public void setFunding(String s) {
		funding = s;
	}

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

}
