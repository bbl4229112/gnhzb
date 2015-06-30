package edu.zju.cims201.GOF.springsecurity;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Component;

import edu.zju.cims201.GOF.rs.dto.TripleDTO;
import edu.zju.cims201.GOF.service.privilege.SpringSecurityService;

/**
 * 查询与资源及操作相关的三元组，
 * 以R_roleID_TreeNodeID_OperationID,U_userId_TreeNodeID_OperationID,A_adminId__TreeNodeID_OperationID格式返回
 * 均直接使用sql
 * @author zju
 *
 */
@Component
public class SQLTriplesProvider {
	
	private SessionFactory sessionFactory;
	
	private SpringSecurityService springSecurityService;
	
	/**
	 * 
	 * @param knowledgeId 知识id
	 * @param operationName操作名
	 * @return 与知识id、操作名相关的三元组
	 */
	public Set<ConfigAttribute> getKnowledgeRelatedTriples(Long knowledgeId,String operationName){
		
		
		Session session=null;
		Connection connection=null;
		Set<TripleDTO> userPrivilegeTriples=null;
		Set<TripleDTO> rolePrivilegeTriples=null;
		try {
			
			session=sessionFactory.openSession();
			connection=session.connection();
			Set<Long> directCNodeIDS=new HashSet<Long>();
			Set<Long> directDNodeIDS=new HashSet<Long>();
			//得到知识所属的直接节点，包括domain 和category，填充至directCNodeIDS，directCNodeIDS
			springSecurityService.findDirectNodes(knowledgeId, directCNodeIDS, directDNodeIDS, connection);
			
			//得到知识所属的父节点
			Set<Long> allCNodes=springSecurityService.listParentNodes(directCNodeIDS, connection);
			Set<Long> allDNodes=springSecurityService.listParentNodes(directDNodeIDS, connection);
			Set<Long> allNodes=new HashSet<Long>();
			for(Long node:allCNodes){
				allNodes.add(node);
			}
			for(Long node:allDNodes){
				allNodes.add(node);
			}
			
			//根据节点、操作名查询权限三元组
			userPrivilegeTriples=springSecurityService.getUserTriples(operationName, allNodes, connection);
			rolePrivilegeTriples=springSecurityService.getRoleTriples(operationName, allNodes, null, connection);
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				
			}
		}finally{
			try {
				connection.close();
				session.close();
			} catch (Exception e1) {
				
			}
		}
		
		if(userPrivilegeTriples.size()==0&&rolePrivilegeTriples.size()==0)
			return null;
		//转为所需格式
		Set<ConfigAttribute> attributes=new AuthorityStringMaker(userPrivilegeTriples,rolePrivilegeTriples).makeConfigAttribute();
		
		
		return attributes;
	}
	
	
	/**
	 * 得到和树节点、操作相关的三元组
	 * @param treeNodeId
	 * @param operationName
	 * @return 与树节点、操作名相关的三元组
	 */
	public Set<ConfigAttribute> getTreeRelatedTriples(Long treeNodeId,String operationName){
		
		
		Set<TripleDTO> adminPrivilegeTriples=null;
		Session session=null;
		Connection connection=null;
		try {
			
			session=sessionFactory.openSession();
			connection=session.connection();
			Set<Long> node=new HashSet<Long>();
			node.add(new Long(treeNodeId));
			Set<Long> allNodes=springSecurityService.listParentNodes(node, connection);
			adminPrivilegeTriples=springSecurityService.getAdminTriples(operationName, allNodes, null, null, connection);
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				
			}
		}finally{
			try {
				connection.close();
				session.close();
			} catch (Exception e1) {
				
			}
		}
		
		if(adminPrivilegeTriples.size()==0)
			return null;
		
		Set<ConfigAttribute> attributes=new AuthorityStringMaker(null,null,adminPrivilegeTriples).makeConfigAttribute();
		
		return attributes;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SpringSecurityService getSpringSecurityService() {
		return springSecurityService;
	}
	@Autowired
	public void setSpringSecurityService(SpringSecurityService springSecurityService) {
		this.springSecurityService = springSecurityService;
	}
	
	
	

}
