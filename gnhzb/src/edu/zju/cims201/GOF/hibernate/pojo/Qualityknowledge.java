// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;
import java.util.Date;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Qualityknowledge extends MetaKnowledge
	implements Knowledge, Serializable
{

	private String amount;
	private String creat_sso_code;
	private String appr_conc;
	private Date appr_date;
	private String appr_levelid;
	private String appr_levelnm;
	private String archnum;
	private String article;
	private String article_content;
	private String author;
	private String bill_num;
	private String bodycd;
	private String bug_classid1;
	private String bug_classid2;
	private String bug_classid3;
	private String bug_classid4;
	private String bug_classnm1;
	private String bug_classnm2;
	private String bug_classnm3;
	private String bug_classnm4;
	private String bug_degreeid;
	private String bug_degreenm;
	private String card_id;
	private String classid;
	private String classnm;
	private String collate;
	private String correct_effectid;
	private String correct_effectnm;
	private String correct_measure;
	private String correct_orgid;
	private String correct_orgnm;
	private String cost;
	private String createrid;
	private Date createtime;
	private String creaternm;
	private String data_levelid;
	private String data_levelnm;
	private String delsubsystem;
	private String dept_collate;
	private String design_orgid;
	private String design_orgnm;
	private String design_value;
	private Date detect_date;
	private String detect_orgid;
	private String detect_orgnm;
	private String detect_situs;
	private String dev_stageid;
	private String dev_stagenm;
	private String duty_orgid;
	private String duty_orgnm;
	private Date exptime;
	private String fact_value;
	private Date fill_date;
	private String hdept_collate;
	private String headcd;
	private String indepbatch;
	private String indepcd;
	private String indepid;
	private String indepnm;
	private String indepserial;
	private Date input_date;
	private String input_orgid;
	private String input_orgnm;

	public Qualityknowledge()
	{
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String s)
	{
		amount = s;
	}

	public String getCreat_sso_code()
	{
		return creat_sso_code;
	}

	public void setCreat_sso_code(String s)
	{
		creat_sso_code = s;
	}

	public String getAppr_conc()
	{
		return appr_conc;
	}

	public void setAppr_conc(String s)
	{
		appr_conc = s;
	}

	public Date getAppr_date()
	{
		return appr_date;
	}

	public void setAppr_date(Date date)
	{
		appr_date = date;
	}

	public String getAppr_levelid()
	{
		return appr_levelid;
	}

	public void setAppr_levelid(String s)
	{
		appr_levelid = s;
	}

	public String getAppr_levelnm()
	{
		return appr_levelnm;
	}

	public void setAppr_levelnm(String s)
	{
		appr_levelnm = s;
	}

	public String getArchnum()
	{
		return archnum;
	}

	public void setArchnum(String s)
	{
		archnum = s;
	}

	public String getArticle()
	{
		return article;
	}

	public void setArticle(String s)
	{
		article = s;
	}

	public String getArticle_content()
	{
		return article_content;
	}

	public void setArticle_content(String s)
	{
		article_content = s;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String s)
	{
		author = s;
	}

	public String getBill_num()
	{
		return bill_num;
	}

	public void setBill_num(String s)
	{
		bill_num = s;
	}

	public String getBodycd()
	{
		return bodycd;
	}

	public void setBodycd(String s)
	{
		bodycd = s;
	}

	public String getBug_classid1()
	{
		return bug_classid1;
	}

	public void setBug_classid1(String s)
	{
		bug_classid1 = s;
	}

	public String getBug_classid2()
	{
		return bug_classid2;
	}

	public void setBug_classid2(String s)
	{
		bug_classid2 = s;
	}

	public String getBug_classid3()
	{
		return bug_classid3;
	}

	public void setBug_classid3(String s)
	{
		bug_classid3 = s;
	}

	public String getBug_classid4()
	{
		return bug_classid4;
	}

	public void setBug_classid4(String s)
	{
		bug_classid4 = s;
	}

	public String getBug_classnm1()
	{
		return bug_classnm1;
	}

	public void setBug_classnm1(String s)
	{
		bug_classnm1 = s;
	}

	public String getBug_classnm2()
	{
		return bug_classnm2;
	}

	public void setBug_classnm2(String s)
	{
		bug_classnm2 = s;
	}

	public String getBug_classnm3()
	{
		return bug_classnm3;
	}

	public void setBug_classnm3(String s)
	{
		bug_classnm3 = s;
	}

	public String getBug_classnm4()
	{
		return bug_classnm4;
	}

	public void setBug_classnm4(String s)
	{
		bug_classnm4 = s;
	}

	public String getBug_degreeid()
	{
		return bug_degreeid;
	}

	public void setBug_degreeid(String s)
	{
		bug_degreeid = s;
	}

	public String getBug_degreenm()
	{
		return bug_degreenm;
	}

	public void setBug_degreenm(String s)
	{
		bug_degreenm = s;
	}

	public String getCard_id()
	{
		return card_id;
	}

	public void setCard_id(String s)
	{
		card_id = s;
	}

	public String getClassid()
	{
		return classid;
	}

	public void setClassid(String s)
	{
		classid = s;
	}

	public String getClassnm()
	{
		return classnm;
	}

	public void setClassnm(String s)
	{
		classnm = s;
	}

	public String getCollate()
	{
		return collate;
	}

	public void setCollate(String s)
	{
		collate = s;
	}

	public String getCorrect_effectid()
	{
		return correct_effectid;
	}

	public void setCorrect_effectid(String s)
	{
		correct_effectid = s;
	}

	public String getCorrect_effectnm()
	{
		return correct_effectnm;
	}

	public void setCorrect_effectnm(String s)
	{
		correct_effectnm = s;
	}

	public String getCorrect_measure()
	{
		return correct_measure;
	}

	public void setCorrect_measure(String s)
	{
		correct_measure = s;
	}

	public String getCorrect_orgid()
	{
		return correct_orgid;
	}

	public void setCorrect_orgid(String s)
	{
		correct_orgid = s;
	}

	public String getCorrect_orgnm()
	{
		return correct_orgnm;
	}

	public void setCorrect_orgnm(String s)
	{
		correct_orgnm = s;
	}

	public String getCost()
	{
		return cost;
	}

	public void setCost(String s)
	{
		cost = s;
	}

	public String getCreaterid()
	{
		return createrid;
	}

	public void setCreaterid(String s)
	{
		createrid = s;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Date date)
	{
		createtime = date;
	}

	public String getCreaternm()
	{
		return creaternm;
	}

	public void setCreaternm(String s)
	{
		creaternm = s;
	}

	public String getData_levelid()
	{
		return data_levelid;
	}

	public void setData_levelid(String s)
	{
		data_levelid = s;
	}

	public String getData_levelnm()
	{
		return data_levelnm;
	}

	public void setData_levelnm(String s)
	{
		data_levelnm = s;
	}

	public String getDelsubsystem()
	{
		return delsubsystem;
	}

	public void setDelsubsystem(String s)
	{
		delsubsystem = s;
	}

	public String getDept_collate()
	{
		return dept_collate;
	}

	public void setDept_collate(String s)
	{
		dept_collate = s;
	}

	public String getDesign_orgid()
	{
		return design_orgid;
	}

	public void setDesign_orgid(String s)
	{
		design_orgid = s;
	}

	public String getDesign_orgnm()
	{
		return design_orgnm;
	}

	public void setDesign_orgnm(String s)
	{
		design_orgnm = s;
	}

	public String getDesign_value()
	{
		return design_value;
	}

	public void setDesign_value(String s)
	{
		design_value = s;
	}

	public Date getDetect_date()
	{
		return detect_date;
	}

	public void setDetect_date(Date date)
	{
		detect_date = date;
	}

	public String getDetect_orgid()
	{
		return detect_orgid;
	}

	public void setDetect_orgid(String s)
	{
		detect_orgid = s;
	}

	public String getDetect_orgnm()
	{
		return detect_orgnm;
	}

	public void setDetect_orgnm(String s)
	{
		detect_orgnm = s;
	}

	public String getDetect_situs()
	{
		return detect_situs;
	}

	public void setDetect_situs(String s)
	{
		detect_situs = s;
	}

	public String getDev_stageid()
	{
		return dev_stageid;
	}

	public void setDev_stageid(String s)
	{
		dev_stageid = s;
	}

	public String getDev_stagenm()
	{
		return dev_stagenm;
	}

	public void setDev_stagenm(String s)
	{
		dev_stagenm = s;
	}

	public String getDuty_orgid()
	{
		return duty_orgid;
	}

	public void setDuty_orgid(String s)
	{
		duty_orgid = s;
	}

	public String getDuty_orgnm()
	{
		return duty_orgnm;
	}

	public void setDuty_orgnm(String s)
	{
		duty_orgnm = s;
	}

	public Date getExptime()
	{
		return exptime;
	}

	public void setExptime(Date date)
	{
		exptime = date;
	}

	public String getFact_value()
	{
		return fact_value;
	}

	public void setFact_value(String s)
	{
		fact_value = s;
	}

	public Date getFill_date()
	{
		return fill_date;
	}

	public void setFill_date(Date date)
	{
		fill_date = date;
	}

	public String getHdept_collate()
	{
		return hdept_collate;
	}

	public void setHdept_collate(String s)
	{
		hdept_collate = s;
	}

	public String getHeadcd()
	{
		return headcd;
	}

	public void setHeadcd(String s)
	{
		headcd = s;
	}

	public String getIndepbatch()
	{
		return indepbatch;
	}

	public void setIndepbatch(String s)
	{
		indepbatch = s;
	}

	public String getIndepcd()
	{
		return indepcd;
	}

	public void setIndepcd(String s)
	{
		indepcd = s;
	}

	public String getIndepid()
	{
		return indepid;
	}

	public void setIndepid(String s)
	{
		indepid = s;
	}

	public String getIndepnm()
	{
		return indepnm;
	}

	public void setIndepnm(String s)
	{
		indepnm = s;
	}

	public String getIndepserial()
	{
		return indepserial;
	}

	public void setIndepserial(String s)
	{
		indepserial = s;
	}

	public Date getInput_date()
	{
		return input_date;
	}

	public void setInput_date(Date date)
	{
		input_date = date;
	}

	public String getInput_orgid()
	{
		return input_orgid;
	}

	public void setInput_orgid(String s)
	{
		input_orgid = s;
	}

	public String getInput_orgnm()
	{
		return input_orgnm;
	}

	public void setInput_orgnm(String s)
	{
		input_orgnm = s;
	}
}
