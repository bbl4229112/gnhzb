package edu.zju.cims201.GOF.rs.dto;

public class TripleDTO {
	
	private Long roleUserID;
	
	private Long treeNodeID;
	
	private Long operationID;
	
	public TripleDTO(){
		
	}
	
	public TripleDTO(Long roleUserID, Long treeNodeID,Long operationID){
		this.roleUserID=roleUserID;
		this.treeNodeID=treeNodeID;
		this.operationID=operationID;
	}
	

	public Long getOperationID() {
		return operationID;
	}

	public void setOperationID(Long operationID) {
		this.operationID = operationID;
	}

	public Long getRoleUserID() {
		return roleUserID;
	}

	public void setRoleUserID(Long roleUserID) {
		this.roleUserID = roleUserID;
	}

	public Long getTreeNodeID() {
		return treeNodeID;
	}

	public void setTreeNodeID(Long treeNodeID) {
		this.treeNodeID = treeNodeID;
	}
	
	
	

}
