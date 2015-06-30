package edu.zju.cims201.GOF.rs.dto;

import java.util.List;

import edu.zju.cims201.GOF.util.Constants;

public class PageDTO {
	
	private int firstindex;
	private long totalPage;
	private long total;
	private List data;
	private long pagesize;
	private String orderBy;
	private int kccounts;
	/**
	 * 返回每个页面的结果个数
	 * @return pagesize
	 * @author hebi
	 */
	public long getPagesize() {
		if(0==pagesize)
		{
			pagesize=Constants.rawPrepage;
		}
		return pagesize;
	}
	/**
	 * 设置每个页面的结果个数
	 * @return pagesize
	 * @author hebi
	 */
	public void setPagesize(long pagesize) {
		this.pagesize = pagesize;
	}
	/**
	 * 设置每个页面的结果
	 * @return data 页面的list结果
	 * @author hebi
	 */
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	/**
	 * 返回结果集总个数
	 * @return data 页面的list结果
	 * @author hebi
	 */
	public long getTotalPage() {
		if(null!=pagesize+""&&pagesize!=0)
		{
	    this.pagesize=Constants.rawPrepage;
		totalPage=	total/pagesize+1;
		}
		return totalPage;
	}
	/**
	 * 设置每个页面的结果
	 * @return data 页面的list结果
	 * @author hebi
	 */
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getFirstindex() {
		return firstindex;
	}
	public void setFirstindex(int firstindex) {
		this.firstindex = firstindex;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public int getKccounts() {
		return kccounts;
	}
	public void setKccounts(int kccounts) {
		this.kccounts = kccounts;
	}
	
}
