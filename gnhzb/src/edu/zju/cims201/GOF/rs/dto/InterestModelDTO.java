package edu.zju.cims201.GOF.rs.dto;

import java.util.Date;
import java.util.List;

public class InterestModelDTO implements Comparable<InterestModelDTO> {
	
	private Long id;
	private Long interestItemId;
	private String interestItemName;
	private String interestItemType;
	private Integer counts;
	private List children;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getInterestItemId() {
		return interestItemId;
	}
	public void setInterestItemId(Long interestItemId) {
		this.interestItemId = interestItemId;
	}
	public String getInterestItemName() {
		return interestItemName;
	}
	public void setInterestItemName(String interestItemName) {
		this.interestItemName = interestItemName;
	}
	public String getInterestItemType() {
		return interestItemType;
	}
	public void setInterestItemType(String interestItemType) {
		this.interestItemType = interestItemType;
	}
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public int compareTo(InterestModelDTO o) {
		if(this.getId()==0||o.id==0)
			return 0;
		return this.id>o.id?1:-1;
	}

}
