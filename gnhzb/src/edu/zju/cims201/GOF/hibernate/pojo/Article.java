// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;
import java.util.Date;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Article extends MetaKnowledge implements Knowledge, Serializable {

	private Date dateofpublication;
	private byte[] articlecontent;

	public Article() {
	}

	public Date getDateofpublication() {
		return dateofpublication;
	}

	public void setDateofpublication(Date date) {
		dateofpublication = date;
	}

	public byte[] getArticlecontent() {
		return articlecontent;
	}

	public void setArticlecontent(byte[] articlecontent) {
		this.articlecontent = articlecontent;
	}

}
