// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Longtestt extends MetaKnowledge
	implements Knowledge, Serializable
{

	private String longtest;
	private String longtoclob;

	public Longtestt()
	{
	}

	public String getLongtest()
	{
		return longtest;
	}

	public void setLongtest(String s)
	{
		longtest = s;
	}

	public String getLongtoclob()
	{
		return longtoclob;
	}

	public void setLongtoclob(String s)
	{
		longtoclob = s;
	}
}
