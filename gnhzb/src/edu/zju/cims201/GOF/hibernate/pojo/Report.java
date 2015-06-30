// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;
import java.util.Date;

// Referenced classes of package edu.zju.cims201.GOF.hibernate.pojo:
//			MetaKnowledge, Knowledge

public class Report extends MetaKnowledge implements Knowledge, Serializable {

	private Date dateofreport;
	private String refer;

	public Report() {
	}

	public Date getDateofreport() {
		return dateofreport;
	}

	public void setDateofreport(Date date) {
		dateofreport = date;
	}

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

}
