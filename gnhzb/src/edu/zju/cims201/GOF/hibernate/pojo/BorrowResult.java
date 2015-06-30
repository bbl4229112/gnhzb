package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.Date;

public class BorrowResult {
	
	
	private Long id;
	private SystemUser user;
	private MetaKnowledge knowledge;
	private ArrayList<OperationRight> borrowRights=new ArrayList<OperationRight>();
	private Date borrowTime;
	public ArrayList<OperationRight> getBorrowRights() {
		return borrowRights;
	}
	
	
	public void setBorrowRights(ArrayList<OperationRight> borrowRights) {
		this.borrowRights = borrowRights;
	}
	public Date getBorrowTime() {
		return borrowTime;
	}
	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public MetaKnowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(MetaKnowledge knowledge) {
		this.knowledge = knowledge;
	}
	public SystemUser getUser() {
		return user;
	}
	public void setUser(SystemUser user) {
		this.user = user;
	}

}
