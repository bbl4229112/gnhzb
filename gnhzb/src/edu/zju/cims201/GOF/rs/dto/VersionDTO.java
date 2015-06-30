package edu.zju.cims201.GOF.rs.dto;

import java.util.List;

public class VersionDTO implements Comparable<VersionDTO> {
	
	private Long id;
	private Long pid;
	private String versionNumber;
	private Long knowledgeID;
	private String knowledgeTitleName;
	private List children;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Long getKnowledgeID() {
		return knowledgeID;
	}

	public void setKnowledgeID(Long knowledgeID) {
		this.knowledgeID = knowledgeID;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public String getKnowledgeTitleName() {
		return knowledgeTitleName;
	}

	public void setKnowledgeTitleName(String knowledgeTitleName) {
		this.knowledgeTitleName = knowledgeTitleName;
	}

	public int compareTo(VersionDTO o) {
		if(this.getId()==0||o.id==0)
			return 0;
		return this.id>o.id?1:-1;
	}

}
