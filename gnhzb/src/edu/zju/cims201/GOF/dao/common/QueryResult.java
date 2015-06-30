package edu.zju.cims201.GOF.dao.common;

import java.util.List;

public class QueryResult<T> {
	
	private List<T> resultlist;
	private long totalrecord;
	private String currentRank;//用户排行用到
	
	public List<T> getResultlist() {
		return resultlist;
	}
	public void setResultlist(List<T> resultlist) {
		this.resultlist = resultlist;
	}
	public long getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
	}
	public String getCurrentRank() {
		return currentRank;
	}
	public void setCurrentRank(String currentRank) {
		this.currentRank = currentRank;
	}
	
	
	

}
