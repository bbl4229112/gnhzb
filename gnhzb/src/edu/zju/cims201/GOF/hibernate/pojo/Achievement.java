// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;
import java.util.Date;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Achievement extends MetaKnowledge implements Knowledge,
		Serializable {

	private String provinceandcity;
	private String maturity;
	private String achievementlevel;
	private String achivlevel;
	private String investigators;
	private Date yearannounced;
	private String authenticatedepartment;
	private Date appraisalyear;
	private String registernumber;
	private Date registrationdate;
	private String industryname;
	private String contactunitname;
	private String address;
	private String linkman;
	private String postcode;

	public Achievement() {
	}

	public String getProvinceandcity() {
		return provinceandcity;
	}

	public void setProvinceandcity(String s) {
		provinceandcity = s;
	}

	public String getMaturity() {
		return maturity;
	}

	public void setMaturity(String s) {
		maturity = s;
	}

	public String getAchievementlevel() {
		return achievementlevel;
	}

	public void setAchievementlevel(String s) {
		achievementlevel = s;
	}

	public String getAchivlevel() {
		return achivlevel;
	}

	public void setAchivlevel(String s) {
		achivlevel = s;
	}

	public String getInvestigators() {
		return investigators;
	}

	public void setInvestigators(String s) {
		investigators = s;
	}

	public Date getYearannounced() {
		return yearannounced;
	}

	public void setYearannounced(Date date) {
		yearannounced = date;
	}

	public String getAuthenticatedepartment() {
		return authenticatedepartment;
	}

	public void setAuthenticatedepartment(String s) {
		authenticatedepartment = s;
	}

	public Date getAppraisalyear() {
		return appraisalyear;
	}

	public void setAppraisalyear(Date date) {
		appraisalyear = date;
	}

	public String getRegisternumber() {
		return registernumber;
	}

	public void setRegisternumber(String s) {
		registernumber = s;
	}

	public Date getRegistrationdate() {
		return registrationdate;
	}

	public void setRegistrationdate(Date date) {
		registrationdate = date;
	}

	public String getIndustryname() {
		return industryname;
	}

	public void setIndustryname(String s) {
		industryname = s;
	}

	public String getContactunitname() {
		return contactunitname;
	}

	public void setContactunitname(String s) {
		contactunitname = s;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String s) {
		address = s;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String s) {
		linkman = s;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String s) {
		postcode = s;
	}
}
