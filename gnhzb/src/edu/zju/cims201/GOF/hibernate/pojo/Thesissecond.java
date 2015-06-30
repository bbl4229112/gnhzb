// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Thesissecond extends MetaKnowledge
	implements Knowledge, Serializable
{

	private String danwei;

	public Thesissecond()
	{
	}

	public String getDanwei()
	{
		return danwei;
	}

	public void setDanwei(String s)
	{
		danwei = s;
	}
}
