package edu.zju.cims201.GOF.rs.dto;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MdeIndexDTO implements Comparable<MdeIndexDTO>{
	private Long id;
	private Long parentId;
	private String code;
	private String nodeName;
	private String nodeDescription;
	private String nameForDetail;
	private List children;
	private Set<MdeIndexDTO> treenodedtos = new TreeSet<MdeIndexDTO>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeDescription() {
		return nodeDescription;
	}
	public void setNodeDescription(String nodeDescription) {
		this.nodeDescription = nodeDescription;
	}
	public String getNameForDetail() {
		return nameForDetail;
	}
	public void setNameForDetail(String nameForDetail) {
		this.nameForDetail = nameForDetail;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public Set<MdeIndexDTO> getTreenodedtos() {
		return treenodedtos;
	}
	public void setTreenodedtos(Set<MdeIndexDTO> treenodedtos) {
		this.treenodedtos = treenodedtos;
	}
	public int compareTo(MdeIndexDTO o) {
		// TODO Auto-generated method stub
		if(this.getId()==0||o.id==0)
			return 0;
		return this.id>o.id?1:-1;
	}
	
}
