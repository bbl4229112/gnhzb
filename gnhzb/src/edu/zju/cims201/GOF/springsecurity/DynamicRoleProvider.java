package edu.zju.cims201.GOF.springsecurity;


import java.util.Collection;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import org.springframework.stereotype.Component;

import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;


/**
 * 本类负责提供操作某一资源所需的权限，此处资源包括knowledge和tree
 * @author zju
 *
 */


@Component
public class DynamicRoleProvider {
	
	
	
	
	private SQLTriplesProvider sqltriplesProvider;
	
	private RequestHandler requestHandler;
	
	
	
	
	/**
	 * 获取操作资源所需的权限，由requestHandler对象从object中获取资源id和操作名operationName
	 * @param object
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public Collection<ConfigAttribute> getAttributes(Object object)
	throws IllegalArgumentException {
		
		
		//guess object is a URL.
		//处理url，寻找相关对象
		
		requestHandler.setObject(object);
		requestHandler.init();
		
		
		
		if(requestHandler.isKnowledgeOperation()){//处理知识
			String knowledgeID=requestHandler.getKnowledgeId();
			
			String operationName=requestHandler.getOperationRight();
			
			Long KID=Long.valueOf(knowledgeID);
			Set<ConfigAttribute> attributes=this.sqltriplesProvider.getKnowledgeRelatedTriples(KID, operationName);
			
			
			
			return attributes;
		}else if(requestHandler.isTreeOperation()){
			
			//查找树权限
			String operationName=requestHandler.getOperationRight();
			String treeNodeString=requestHandler.getTreeNode();
			if(operationName==null||treeNodeString==null)
				return null;
			Long treeNodeID=Long.valueOf(treeNodeString);
			
			Set<ConfigAttribute> attributes=this.sqltriplesProvider.getTreeRelatedTriples(treeNodeID, operationName);
			
			return attributes;
		}
		
		return null;
		}





	public SQLTriplesProvider getSqltriplesProvider() {
		return sqltriplesProvider;
	}

	@Autowired
	public void setSqltriplesProvider(SQLTriplesProvider sqltriplesProvider) {
		this.sqltriplesProvider = sqltriplesProvider;
	}





	public RequestHandler getRequestHandler() {
		return requestHandler;
	}




	@Autowired
	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}





	
	
	
	
	
	
	
	

		



	
	
	
	
	

}
