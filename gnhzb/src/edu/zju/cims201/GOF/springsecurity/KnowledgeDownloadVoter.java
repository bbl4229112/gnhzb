package edu.zju.cims201.GOF.springsecurity;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.approvalFlow.ApprovalFlowService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeServiceImpl;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.Constants;



@Component
public class KnowledgeDownloadVoter implements  AccessDecisionVoter{
	
	
	private KnowledgeService knowledgeService;
	private UserService userService;
	
	private RequestHandler requestHandler;
	
	private BorrwerChecker borrwerChecker;
	
	
	private ApprovalFlowService approvalFlowService;
	
	private String knowledge_download="KNOWLEDGE_DOWNLOAD";
	
	
	

	public boolean supports(ConfigAttribute attribute) {
		String caString=attribute.getAttribute().trim();
        if ((caString != null) && caString.equals(this.getKnowledge_download())) {
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
					SystemUser currentUser=this.userService.getUser();
					if(this.borrwerChecker.check4download(currentUser, knowledge))
						return ACCESS_GRANTED;
					
				}else{
					return ACCESS_ABSTAIN;
				}
				
					
			}
		}
		return ACCESS_ABSTAIN;
	}

	

	public String getKnowledge_download() {
		return knowledge_download;
	}

	public void setKnowledge_download(String knowledge_download) {
		this.knowledge_download = knowledge_download;
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
