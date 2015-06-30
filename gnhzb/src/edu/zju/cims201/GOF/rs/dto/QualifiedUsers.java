package edu.zju.cims201.GOF.rs.dto;

import java.util.List;


/**
 * 该数据结构描述具有资格的审批人员
 * @author zd
 *
 */
public class QualifiedUsers {
	private POTreeNodeDTO domainNode;
	private List<UserDTO> userList;
	public POTreeNodeDTO getDomainNode() {
		return domainNode;
	}
	public void setDomainNode(POTreeNodeDTO domainNode) {
		this.domainNode = domainNode;
	}
	public List<UserDTO> getUserList() {
		return userList;
	}
	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}	
}
