// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Question extends MetaKnowledge
	implements Knowledge, Serializable
{

	private String questioncontent;
	private Long questionstatus;
	private String questionsupplement;

	public Question()
	{
	}

	public String getQuestioncontent()
	{
		return questioncontent;
	}

	public void setQuestioncontent(String s)
	{
		questioncontent = s;
	}

	public Long getQuestionstatus()
	{
		return questionstatus;
	}

	public void setQuestionstatus(Long long1)
	{
		questionstatus = long1;
	}

	public String getQuestionsupplement()
	{
		return questionsupplement;
	}

	public void setQuestionsupplement(String s)
	{
		questionsupplement = s;
	}
}
