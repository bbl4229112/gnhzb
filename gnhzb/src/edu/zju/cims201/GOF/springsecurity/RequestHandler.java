 package edu.zju.cims201.GOF.springsecurity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import edu.zju.cims201.GOF.service.privilege.PrivilegeService;
import edu.zju.cims201.GOF.service.privilege.SpringSecurityService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.web.privilege.PrivilegeConstants;


/**
 * 本类负责匹配URL并从URL中得出相应操作所需的资源id以及操作名
 * @author zju
 *
 */


@Component
public class RequestHandler {
	
	
	private SessionFactory sessionFactory;


	private PrivilegeService privilegeService;

	private SpringSecurityService springSecurityService;
	
	private Object object;
	
	private String url;
	
	private Map<String, Object> requestMap;
	
	private static Pattern treeAdminPattern=Pattern.compile("(?<=^)((/tree/tree)|(/privilege/empowerment)).*(?=(\\.action))");
	
	private static Pattern treeAssignAdminPattern=Pattern.compile("(?<=^)(/privilege/tree-empowerment).*(?=(\\.action))");
	
	private static Pattern swfPattern=Pattern.compile("(?<=^)(/knowledge/viewfile!view).*(?=(\\.action))");
	
	private static Pattern attachPattern=Pattern.compile("(?<=^)(/knowledge/sourcefiledownload!downloadattach).*(?=(\\.action))");
	///knowledge/sourcefiledownload!downloadattach.action
	
	private static Pattern viewDownloadKnowledgePattern=Pattern.compile("(?<=^)((/knowledge/knowledge!show)|(/knowledge/viewfile!down)).*(?=(\\.action))");
	
//	private static Pattern viewDownloadKnowledgePattern=Pattern.compile("(?<=^)((/knowledge/knowledge!show)|(/knowledge/viewfile!view)).*(?=(\\.action))");
	
	
	
	
	
	/**
	 * 初始化,由于在spring容器中本类为单例，所有在每次使用时须注入新的URL，及requestMap
	 *
	 */
	
	public void init(){
		url = ((FilterInvocation) object).getRequestUrl();
		requestMap=((FilterInvocation) object).getRequest().getParameterMap();
	}
	
	
	/**
	 * 
	 * @return 树节点id
	 */
	
	public String getTreeNode(){
		if(!isTreeOperation())
			return null;
		String key="nodeId";
		Matcher matcher=treeAdminPattern.matcher(url);
		if(matcher.find()){
			String partUrl=matcher.group();
			if(partUrl.startsWith("/tree/tree"))
				key="id";
			//删不掉empowerment，加上
			if(partUrl.startsWith("/privilege/empowerment!delete")){
				key="currentEmpowerment";
				Object object=requestMap.get(key);
				if(object==null)
					return null;
				
				String ss[]=(String[])object;
				long currentEmpowermentId=Long.valueOf(ss[0]);
				long nodeId=this.privilegeService.getEmpowerment(currentEmpowermentId).getBelongedNode().getId();
				
				return String.valueOf(nodeId);
				
			}
			//删不掉empowerment，加上
				
		}
		
		Object object=requestMap.get(key);
		if(object==null)
			return null;
		
		String ss[]=(String[])object;
			
		return ss[0];
		
	}
	
	/**对于不同的url，得到知识id的方法也不同，代码可优化
	 * 
	 * @return 知识id
	 */
	
	public String getKnowledgeId(){
		if(!isKnowledgeOperation())
			return null;
		String knowledgeId;
		String key="id";
		
		
	//	System.out.println("url==+++++++++++++++++++++++++++"+url);
		
		Matcher matcher=viewDownloadKnowledgePattern.matcher(url);
		Matcher swfMatcher=swfPattern.matcher(url);
		Matcher attacheMatcher=attachPattern.matcher(url);
		if(matcher.find()){
		//	System.out.println("Macher");
			Object object=requestMap.get(key);
			if(object==null)
				return null;
			
			String ss[]=(String[])object;
			
			
			String methodName=matcher.group();
			if(methodName.endsWith("attach")){
				String attachIdString=ss[0];
				Long attachId=Long.valueOf(attachIdString);
				Connection connection=null;
				Session session=null;
				Long kid=null;
				try {
					
					session=sessionFactory.openSession();
					connection=session.connection();
					kid=springSecurityService.getKnowledgeID(attachId, connection);
				}catch (Exception e) {
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
				knowledgeId=String.valueOf(kid);
				return knowledgeId;
				
				
				
			}
			//System.out.println("knowledgeid===================="+ss[0]);
			return ss[0];	
		}
		else if(attacheMatcher.find())
		{
			
			Object object=requestMap.get(key);
			if(object==null)
				return null;
			
			String ss[]=(String[])object;
			String attachIdString=ss[0];
			Long attachId=Long.valueOf(attachIdString);
			Connection connection=null;
			Session session=null;
			Long kid=null;
			try {
				
				session=sessionFactory.openSession();
				connection=session.connection();
				kid=springSecurityService.getKnowledgeID(attachId, connection);
			}catch (Exception e) {
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
			knowledgeId=String.valueOf(kid);
			return knowledgeId;
			
			
			
		
			
		}
		else if(swfMatcher.find()){
		//	System.out.println("url==+++++++++++++++++++++++++++"+url);
		//	System.out.println("swfMacher");
			key="fileId";
			Object object=requestMap.get(key);
			if(object==null)
				return null;
			
			String ss[]=(String[])object;
			String partUrl=swfMatcher.group();
			String path="";
				//partUrl.substring(partUrl.lastIndexOf("fileId=")+7);
			path=ss[0];
			//path+=".swf";
			Connection connection=null;
			Session session=null;
			Long kid=null;
			try {
				
				session=sessionFactory.openSession();
				connection=session.connection();
				kid=springSecurityService.getKnowledgeID(path, connection);
				
			}catch (Exception e) {
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
			knowledgeId=String.valueOf(kid);
			return knowledgeId;
		}else{
			
			Object object=requestMap.get(key);
			if(object==null)
				return null;
			
			String ss[]=(String[])object;
			return ss[0];
		}
			
			
		
		
	}
	
	
	
	
	/**
	 * 
	 * @return 所需的操作名
	 */
	public String getOperationRight(){
		
		if(isTreeOperation()){
			if(treeAdminPattern.matcher(url).find()){
				return  PrivilegeConstants.ADMIN_ADMIN_NODE;
			}else if(treeAssignAdminPattern.matcher(url).find()){
				return  PrivilegeConstants.ADMIN_ASIGN_ADMINISTRATOR;
			}
		}else if(isKnowledgeOperation()){
			Matcher swfMatcher=swfPattern.matcher(url);
			if(swfMatcher.find()){
				String partUrl=swfMatcher.group();
				if(partUrl.startsWith("/knowledge/viewfile!vie"))
					return PrivilegeConstants.USER_VIEW_KNOWLEDGE;
			}
			Matcher attacheMatcher=attachPattern.matcher(url);
			if(attacheMatcher.find()){
				return PrivilegeConstants.USER_DOWNLOAD_KNOWLEDGE;
			}
			Matcher matcher=viewDownloadKnowledgePattern.matcher(url);
			if(matcher.find()){
				String partUrl=matcher.group();
				 if(partUrl.startsWith("/knowledge/viewfile!downlo")){
					return PrivilegeConstants.USER_DOWNLOAD_KNOWLEDGE;
				}
				 else
					 if(partUrl.startsWith("/knowledge/knowledge")){
					return PrivilegeConstants.USER_VIEW_KNOWLEDGE;
				}
//					 else if(partUrl.startsWith("/knowledge/viewfile!downlo")){
//					return PrivilegeConstants.USER_DOWNLOAD_KNOWLEDGE;
//				}
					
				
			}
			
			
			
		}
		
		return null;
	}
	
	/**
	 * 是否是树操作
	 * @return
	 */
	public boolean isTreeOperation(){
	
		if(treeAdminPattern.matcher(url).find()||treeAssignAdminPattern.matcher(url).find())
			return true;
		return false;
	}
	/**
	 * 是否是知识操作
	 * @return
	 */
	public boolean isKnowledgeOperation(){
		if(viewDownloadKnowledgePattern.matcher(url).find()||swfPattern.matcher(url).find()||attachPattern.matcher(url).find())
			return true;
		return false;
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


	public Object getObject() {
		return object;
	}


	public void setObject(Object object) {
		this.object = object;
	}


	public Map<String, Object> getRequestMap() {
		return requestMap;
	}


	public void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	@Autowired
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}
	
	
	
	
	
	
	

}
