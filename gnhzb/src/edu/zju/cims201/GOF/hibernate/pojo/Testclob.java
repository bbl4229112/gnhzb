// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Testclob extends MetaKnowledge
	implements Knowledge, Serializable
{

	private String longtest;

	public Testclob()
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
}
