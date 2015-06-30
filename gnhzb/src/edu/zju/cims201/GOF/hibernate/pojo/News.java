// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class News extends MetaKnowledge implements Knowledge, Serializable {

	private byte[] newsbody;
	private String url;
	private String parenturl;
	private byte[] newscontent;
	private String publictime;

	public News() {
	}

	public byte[] getNewsbody() {
		return newsbody;
	}

	public void setNewsbody(byte[] newsbody) {
		this.newsbody = newsbody;
	}

	public byte[] getNewscontent() {
		return newscontent;
	}

	public void setNewscontent(byte[] newscontent) {
		this.newscontent = newscontent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String s) {
		url = s;
	}

	public String getParenturl() {
		return parenturl;
	}

	public void setParenturl(String s) {
		parenturl = s;
	}

	public String getPublictime() {
		return publictime;
	}

	public void setPublictime(String s) {
		publictime = s;
	}
}
