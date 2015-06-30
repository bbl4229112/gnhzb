package edu.zju.cims201.GOF.web.knowledge;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.service.approvalFlow.ApprovalFlowService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.JSONUtil;


@Namespace("/knowledge/approval")
public class ApprovalStatisticsAction extends ActionSupport implements ServletResponseAware{
	
	
	private int size;
	private int index;
	
	private HttpServletResponse response;
	
	private ApprovalFlowService approvalFlowService;
	
	private UserService userService;
	
	
	
	public String getUnApprovalKnowledges(){
		Page<MetaKnowledge> page=null;
		if(this.size==0)
			 page=new Page<MetaKnowledge>(10);
		else
			page=new Page<MetaKnowledge>(this.size);
		page.setPageNo(this.index+1);
		
		SystemUser user=this.userService.getUser();
		
		Page<MetaKnowledge> newpage=this.approvalFlowService.listUnApprovalKnowledge(user, page);
		
	
		writeJson(newpage);
		return null;
	}
	
	public String getPendingApprovalKnowledges(){
		Page<MetaKnowledge> page=null;
		if(this.size==0)
			 page=new Page<MetaKnowledge>(10);
		else
			page=new Page<MetaKnowledge>(this.size);
		page.setPageNo(this.index+1);
		
		SystemUser user=this.userService.getUser();
		
		Page<MetaKnowledge> newpage=this.approvalFlowService.listPendingApprovalKnowledge(user, page);
		
		
		writeJson(newpage);
		return null;
		
		
		
	}
	
	public String getPassApprovalKnowledges(){
		
		Page<MetaKnowledge> page=null;
		if(this.size==0)
			 page=new Page<MetaKnowledge>(10);
		else
			page=new Page<MetaKnowledge>(this.size);
		page.setPageNo(this.index+1);
		
		SystemUser user=this.userService.getUser();
		Page<MetaKnowledge> newpage=this.approvalFlowService.listApprovalPassedKnowledge(user, page);
		
		writeJson(newpage);
		return null;
		
	}
	
	public String getEndApprovalKnowledges(){
		
		Page<MetaKnowledge> page=null;
		if(this.size==0)
			 page=new Page<MetaKnowledge>(10);
		else
			page=new Page<MetaKnowledge>(this.size);
		page.setPageNo(this.index+1);
		
		SystemUser user=this.userService.getUser();
		Page<MetaKnowledge> newpage=this.approvalFlowService.listApprovalEndOrBlockedKnowledge(user, page);
		
		writeJson(newpage);
		return null;
		
	}
	
	public String getNeededApprovalKnowledges(){
		
		Page<MetaKnowledge> page=null;
		if(this.size==0)
			 page=new Page<MetaKnowledge>(10);
		else
			page=new Page<MetaKnowledge>(this.size);
		page.setPageNo(this.index+1);
		
		SystemUser user=this.userService.getUser();
		Page<MetaKnowledge> newpage=this.approvalFlowService.listNeededApprovalKnowledge(user, page);
		
		writeJson(newpage);
		return null;
		
	}
	
	public String getApprovaledKnowledges(){
		
		Page<MetaKnowledge> page=null;
		if(this.size==0)
			 page=new Page<MetaKnowledge>(10);
		else
			page=new Page<MetaKnowledge>(this.size);
		page.setPageNo(this.index+1);
		
		SystemUser user=this.userService.getUser();
		Page<MetaKnowledge> newpage=this.approvalFlowService.listApprovaledKnowledge(user, page);
		
		writeJson(newpage);
		return null;
		
	}
	
	private void writeJson(Page<MetaKnowledge> page){
		PageDTO pageDTO=new PageDTO();
		pageDTO.setTotal(page.getTotalCount());
		pageDTO.setPagesize(page.getPageSize());
		pageDTO.setTotalPage(page.getTotalPages());
		//pagedto.
		pageDTO.setData(new ArrayList());
		for(MetaKnowledge knowledge:page.getResult()){
			KnowledgeDTO dto=new KnowledgeDTO();
			
			dto.setId(knowledge.getId());
			dto.setTitleName(knowledge.getTitlename());
			
			dto.setUploadTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(knowledge.getUploadtime()));

			
			List<ObjectDTO> authorDtos=new ArrayList<ObjectDTO>();
			List<Author> authors=knowledge.getKauthors();
			for(Author author:authors){
				ObjectDTO objectDTO=new ObjectDTO();
				objectDTO.setId(author.getId());
				objectDTO.setName(author.getAuthorName());
				authorDtos.add(objectDTO);
				
			}
			dto.setKAuthors(authorDtos);
			
			List<ObjectDTO> keywordDtos=new ArrayList<ObjectDTO>();
			Set<Keyword> keywords=knowledge.getKeywords();
			for(Keyword keyword:keywords){
				ObjectDTO objectDTO=new ObjectDTO();
				objectDTO.setId(keyword.getId());
				objectDTO.setName(keyword.getKeywordName());
				keywordDtos.add(objectDTO);
				
			}
			System.out.println(knowledge);
			System.out.println(knowledge.getKnowledgetype().getId());
			System.out.println(knowledge.getKnowledgetype().getKnowledgeTypeName());

			ObjectDTO knowledgetypeDTO=new ObjectDTO(knowledge.getKnowledgetype().getId(),knowledge.getKnowledgetype().getKnowledgeTypeName());
	    	dto.setKnowledgetype(knowledgetypeDTO);
			dto.setKeywords(keywordDtos);
			
			pageDTO.getData().add(dto);
		}
		
		JSONUtil.write(response, pageDTO);
		
	}
	
	
	
	
	
	

	public void setServletResponse(HttpServletResponse response) {
		
		this.response = response;
		
		
	}




	public int getIndex() {
		return index;
	}




	public void setIndex(int index) {
		this.index = index;
	}




	public int getSize() {
		return size;
	}




	public void setSize(int size) {
		this.size = size;
	}




	public ApprovalFlowService getApprovalFlowService() {
		return approvalFlowService;
	}



	@Autowired
	public void setApprovalFlowService(ApprovalFlowService approvalFlowService) {
		this.approvalFlowService = approvalFlowService;
	}




	public UserService getUserService() {
		return userService;
	}



	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
	
	
	
	

}
