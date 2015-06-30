// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;
import java.util.Date;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Paper extends MetaKnowledge
	implements Knowledge, Serializable
{

	private String tecfield;
	private Date pubdate;
	private String teccategory;

	public Paper()
	{
	}

	public String getTecfield()
	{
		return tecfield;
	}

	public void setTecfield(String s)
	{
		tecfield = s;
	}

	public Date getPubdate()
	{
		return pubdate;
	}

	public void setPubdate(Date date)
	{
		pubdate = date;
	}

	public String getTeccategory()
	{
		return teccategory;
	}

	public void setTeccategory(String s)
	{
		teccategory = s;
	}
}
