package edu.zju.cims201.GOF.service.privilege;

import java.sql.Connection;
import java.util.Set;

import edu.zju.cims201.GOF.rs.dto.TripleDTO;

/**
 * 提供动态授权相关的底层查询，均用sql
 * @author zju
 *
 */
public interface SpringSecurityService {
	
	
		
	/**
	 * 列出和知识相关的直接域节点填充在directCNodeIDS和directDNodeIDS中
	 */
	public void findDirectNodes(Long knowledgeID,Set<Long> directCNodeIDS,Set<Long> directDNodeIDS,Connection connection);
	
	
	/**
	 * 得到一定条件下的角色三元组：1、输入operationName，allcdNodeIDS；2、输入allrNodeIDS
	 */
	public Set<TripleDTO> getRoleTriples(String operationName,Set<Long> allcdNodeIDS,Set<Long> allrNodeIDS,Connection connection);
	
	
	/**
	 * 得到用户三元组：1、输入operationName和allNodeIDS，其中allNodeIDS为cdnode
	 */
	public Set<TripleDTO> getUserTriples(String operationName,Set<Long> allNodeIDS,Connection connection);
	
	
	/**
	 * 返回用户三元组
	 */
	public Set<TripleDTO> getUserTriples(Long userID,Connection connection);
	
	
	/**
	 * 列出用户直接属于的角色
	 */
	public Set<Long> listDirectRoleNodes(Long userID,Connection connection);
	
	
	/**
	 * 返回某一结合节点的所有父节点及其本身;
	 */
	public Set<Long> listParentNodes(Set<Long> directRoles,Connection connection);
	
	//+++++++++++++++++++++++++++++++++++以下管理树节点+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	/**
	 * 列出用户直接管理的树节点
	 */
	public Set<Long> listDirectAdminNodes(Long adminID,Connection connection);
	
	
	/**
	 * 列出用户管理的所有节点
	 */
	public Set<Long> listSubNodes(Set<Long> directNodes,Connection connection);
	
	public Set<TripleDTO> getAdminTriples(String operationName,Set<Long> allTreeNodeIDS,Long adminID,Set<Long> allAdminNodeIDS,Connection connection);
	
	public Long getKnowledgeID(Long attachId,Connection connection);
	
	public Long getKnowledgeID(String swfPath,Connection connection);
	

}
