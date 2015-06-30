// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Avidmknowledge extends MetaKnowledge
	implements Knowledge, Serializable
{

	private String avidmtype;
	private String avidmdocumentiid;
	private String avidmfileiid;
	private String avidmversioniid;
	private String avidmproductiid;
	private String avidmglobledocumentid;
	private String avidmhost;

	public Avidmknowledge()
	{
	}

	public String getAvidmtype()
	{
		return avidmtype;
	}

	public void setAvidmtype(String s)
	{
		avidmtype = s;
	}

	public String getAvidmdocumentiid()
	{
		return avidmdocumentiid;
	}

	public void setAvidmdocumentiid(String s)
	{
		avidmdocumentiid = s;
	}

	public String getAvidmfileiid()
	{
		return avidmfileiid;
	}

	public void setAvidmfileiid(String s)
	{
		avidmfileiid = s;
	}

	public String getAvidmversioniid()
	{
		return avidmversioniid;
	}

	public void setAvidmversioniid(String s)
	{
		avidmversioniid = s;
	}

	public String getAvidmproductiid()
	{
		return avidmproductiid;
	}

	public void setAvidmproductiid(String s)
	{
		avidmproductiid = s;
	}

	public String getAvidmglobledocumentid()
	{
		return avidmglobledocumentid;
	}

	public void setAvidmglobledocumentid(String s)
	{
		avidmglobledocumentid = s;
	}

	public String getAvidmhost()
	{
		return avidmhost;
	}

	public void setAvidmhost(String s)
	{
		avidmhost = s;
	}
}
