// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;
import java.util.Date;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Thepaper extends MetaKnowledge
	implements Knowledge, Serializable
{

	private String authordepartment;
	private String tecfield;
	private String kfunction;
	private String fromsource;
	private Date pubdate;

	public Thepaper()
	{
	}

	public String getAuthordepartment()
	{
		return authordepartment;
	}

	public void setAuthordepartment(String s)
	{
		authordepartment = s;
	}

	public String getTecfield()
	{
		return tecfield;
	}

	public void setTecfield(String s)
	{
		tecfield = s;
	}

	public String getKfunction()
	{
		return kfunction;
	}

	public void setKfunction(String s)
	{
		kfunction = s;
	}

	public String getFromsource()
	{
		return fromsource;
	}

	public void setFromsource(String s)
	{
		fromsource = s;
	}

	public Date getPubdate()
	{
		return pubdate;
	}

	public void setPubdate(Date date)
	{
		pubdate = date;
	}
}
