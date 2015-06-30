package edu.zju.cims201.GOF.test;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.RegexUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import edu.zju.cims201.GOF.web.privilege.PrivilegeConstants;

import junit.framework.TestCase;

public class UrlTest extends TestCase {
	
	
	
	private static Pattern viewDownloadKnowledgePattern=Pattern.compile("(?<=^)((/knowledge/knowledge!show)|(/knowledge/sourcefiledownload!down)).*(?=(\\.action))");
	
	private static Pattern treeAdminPattern=Pattern.compile("(?<=^)((/tree/tree)|(/privilege/empowerment)).*(?=(\\.action))");
	
	private static Pattern treeAssignAdminPattern=Pattern.compile("(?<=^)(/privilege/tree-empowerment).*(?=(\\.action))");
	private static Pattern swfPattern=Pattern.compile("(?<=^)(/sourcefile).*(?=(\\.swf))");
	
	private UrlMatcher urlMatcher = new RegexUrlPathMatcher();
	
	private String url;
	
	public boolean isTreeOperation(){
		
		Matcher matcher=treeAssignAdminPattern.matcher(url);
		if(matcher.find())
			System.out.println(matcher.group());
		
		
		if(treeAdminPattern.matcher(url).find()||treeAssignAdminPattern.matcher(url).find())
			return true;
		
		
		return false;
	}
	
	public String getTreeNode(){
		if(!isTreeOperation())
			return null;
		String key="nodeId";
		Matcher matcher=treeAdminPattern.matcher(url);
		if(matcher.find()){
			String partUrl=matcher.group();
			if(partUrl.startsWith("/tree/tree"))
				key="id";
		}
		return key;
		
	}
	
	public boolean isKnowledgeOperation(){
		if(viewDownloadKnowledgePattern.matcher(url).find()||swfPattern.matcher(url).find())
			return true;
		return false;
	}
	
	
	
	public String getOperationRight(){
		String operationRight=null;
		if(isTreeOperation()){
			if(treeAdminPattern.matcher(url).find()){
				operationRight=PrivilegeConstants.ADMIN_ADMIN_NODE;
			}else if(treeAssignAdminPattern.matcher(url).find()){
				operationRight=PrivilegeConstants.ADMIN_ASIGN_ADMINISTRATOR;
			}
		}else if(isKnowledgeOperation()){
			Matcher matcher=viewDownloadKnowledgePattern.matcher(url);
			Matcher swfMatcher=swfPattern.matcher(url);
			if(matcher.find()){
				String partUrl=matcher.group();
				if(partUrl.startsWith("/knowledge/knowledge")){
					operationRight=PrivilegeConstants.USER_VIEW_KNOWLEDGE;
				}else if(partUrl.startsWith("/knowledge/sourcefiledownload!download")){
					operationRight=PrivilegeConstants.USER_DOWNLOAD_KNOWLEDGE;
				}
					
				
			}else if(swfMatcher.find()){
				String partUrl=swfMatcher.group();
				if(partUrl.startsWith("/sourcefile"))
					operationRight="sss";
			}
			
			
			
		}
		
		return operationRight;
	}
	
	
	
	public void testUrl(){
		
		this.url="/sourcefile/3e9c43b9-1c34-4dd5-8318-3ca54f315d9c.swf";
		if(isKnowledgeOperation())
			System.out.println("sssssssss");
		
		System.out.println(getOperationRight());
		
		Matcher matcher=this.swfPattern.matcher(this.url);
		if(matcher.find()){
			String methodName=matcher.group();
			String path=methodName.substring(methodName.lastIndexOf("/")+1);
			System.out.println(path);
			
		}
		
	}
		
	
	
	
	

}
