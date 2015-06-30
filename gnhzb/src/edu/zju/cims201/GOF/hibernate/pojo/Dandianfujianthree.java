// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Dandianfujianthree extends MetaKnowledge
	implements Knowledge, Serializable
{

	private String productname;
	private String productnum;
	private String dandianmodel;
	private String dandianreason;
	private String dandianeffect;
	private String seriouslevel;
	private String frequency;
	private String designmeasures;
	private String recommendedmeasures;
	private String specnm;
	private String htmltext;

	public Dandianfujianthree()
	{
	}

	public String getProductname()
	{
		return productname;
	}

	public void setProductname(String s)
	{
		productname = s;
	}

	public String getProductnum()
	{
		return productnum;
	}

	public void setProductnum(String s)
	{
		productnum = s;
	}

	public String getDandianmodel()
	{
		return dandianmodel;
	}

	public void setDandianmodel(String s)
	{
		dandianmodel = s;
	}

	public String getDandianreason()
	{
		return dandianreason;
	}

	public void setDandianreason(String s)
	{
		dandianreason = s;
	}

	public String getDandianeffect()
	{
		return dandianeffect;
	}

	public void setDandianeffect(String s)
	{
		dandianeffect = s;
	}

	public String getSeriouslevel()
	{
		return seriouslevel;
	}

	public void setSeriouslevel(String s)
	{
		seriouslevel = s;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String s)
	{
		frequency = s;
	}

	public String getDesignmeasures()
	{
		return designmeasures;
	}

	public void setDesignmeasures(String s)
	{
		designmeasures = s;
	}

	public String getRecommendedmeasures()
	{
		return recommendedmeasures;
	}

	public void setRecommendedmeasures(String s)
	{
		recommendedmeasures = s;
	}

	public String getSpecnm()
	{
		return specnm;
	}

	public void setSpecnm(String s)
	{
		specnm = s;
	}

	public String getHtmltext()
	{
		return htmltext;
	}

	public void setHtmltext(String s)
	{
		htmltext = s;
	}
}
