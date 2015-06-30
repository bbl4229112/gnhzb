package edu.zju.cims201.GOF.springsecurity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.approvalFlow.ApprovalFlowService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeServiceImpl;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.Constants;


@Component
public class KnowledgeViewVoter implements  AccessDecisionVoter{
	
	private KnowledgeService knowledgeService;
	private UserService userService;
	
	private RequestHandler requestHandler;
	
	private BorrwerChecker borrwerChecker;
	
	
	private ApprovalFlowService approvalFlowService;
	
	private String knowledge_uploader="KNOWLEDGE_VIEW";
	
	
	

	public boolean supports(ConfigAttribute attribute) {
		String caString=attribute.getAttribute().trim();
        if ((caString != null) && caString.equals(getKnowledge_uploader())) {
            return true;
        }
        else {
            return false;
        }
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		//是否为知识上传者
		if(authentication.getName().equals(Constants.SUPERADMIN))
			return ACCESS_GRANTED;
		
		if(attributes==null){
			return ACCESS_ABSTAIN;
			
		}
		
		for (ConfigAttribute attribute : attributes) {
			if(supports(attribute)){
				
				requestHandler.setObject(object);
				requestHandler.init();
				
				if(requestHandler.isKnowledgeOperation()){
					String knowledgeID=requestHandler.getKnowledgeId();
					Long KID=Long.valueOf(knowledgeID);
					MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(KID);
					SystemUser uploader=knowledge.getUploader();
					SystemUser currentUser=this.userService.getUser();
					
					if(currentUser.getEmail().equals(uploader.getEmail()))
						return ACCESS_GRANTED;
					
					List<MetaKnowledge> neededApprovalKnowledges=this.approvalFlowService.listNeededApprovalKnowledge(currentUser);
					if(neededApprovalKnowledges.contains(knowledge))
						return ACCESS_GRANTED;
					
					if(this.borrwerChecker.check4lender(currentUser, knowledge))
						return ACCESS_GRANTED;
					
					if(this.borrwerChecker.check(currentUser, knowledge))
						return ACCESS_GRANTED;
					
					
					
				}else{
					return ACCESS_ABSTAIN;
				}
				
					
			}
		}
		
		
		
		
		return ACCESS_ABSTAIN;
	}

	public String getKnowledge_uploader() {
		return knowledge_uploader;
	}

	public void setKnowledge_uploader(String knowledge_uploader) {
		this.knowledge_uploader = knowledge_uploader;
	}

	public KnowledgeService getKnowledgeService() {
		return knowledgeService;
	}
	@Autowired
	public void setKnowledgeService(KnowledgeServiceImpl knowledgeService) {
		this.knowledgeService = knowledgeService;
	}

	public UserService getUserService() {
		return userService;
	}
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RequestHandler getRequestHandler() {
		return requestHandler;
	}
	@Autowired
	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	public ApprovalFlowService getApprovalFlowService() {
		return approvalFlowService;
	}
	@Autowired
	public void setApprovalFlowService(ApprovalFlowService approvalFlowService) {
		this.approvalFlowService = approvalFlowService;
	}

	public BorrwerChecker getBorrwerChecker() {
		return borrwerChecker;
	}
	@Autowired
	public void setBorrwerChecker(BorrwerChecker borrwerChecker) {
		this.borrwerChecker = borrwerChecker;
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	

}
