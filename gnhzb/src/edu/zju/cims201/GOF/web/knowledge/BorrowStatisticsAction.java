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
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlow;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.BorrowFlowDTO;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowConstants;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.JSONUtil;




@Namespace("/knowledge/approval")
public class BorrowStatisticsAction extends ActionSupport implements ServletResponseAware{
	
	
	private int size;
	private int index;
	
	private HttpServletResponse response;
	
	private BorrowFlowService borrowFlowService;
	
	private UserService userService;
	
	//针对借阅者 借阅中
	public void listBuildingAndBorrowing4Initiator(){
		Page<BorrowFlow> page=null;
		if(this.size==0)
			 page=new Page<BorrowFlow>(10);
		else
			page=new Page<BorrowFlow>(this.size);
		page.setPageNo(this.index+1);
		
		SystemUser initiator=this.userService.getUser();
		
		Page<BorrowFlow> newPage = this.borrowFlowService.listBuildingAndBorrowingFlow4Initiator(initiator, page);
		writeJson(newPage);
	}
	
	
	
//	public void listBorrowing4Initiator(){
//		Page<BorrowFlow> page=null;
//		if(this.size==0)
//			 page=new Page<BorrowFlow>(10);
//		else
//			page=new Page<BorrowFlow>(this.size);
//		page.setPageNo(this.index+1);
//		
//		SystemUser initiator=this.userService.getUser();
//		
//		Page<BorrowFlow> newPage = this.borrowFlowService.listFlow4Initiator(initiator, page, BorrowFlowConstants.BORROWFLOW_STATUS_BORROWING);
//		writeJson(newPage);
//	}
	//针对借阅者 当前借到的
	public void listBorrowedSuccess4Initiator(){
		Page<BorrowFlow> page=null;
		if(this.size==0)
			 page=new Page<BorrowFlow>(10);
		else
			page=new Page<BorrowFlow>(this.size);
		page.setPageNo(this.index+1);
		
		SystemUser initiator=this.userService.getUser();
		
		Page<BorrowFlow> newPage = this.borrowFlowService.listFlow4Initiator(initiator, page, BorrowFlowConstants.BORROWFLOW_STATUS_OVER_PASS);
		writeJson(newPage);
	}
	
//	public void listBorrowedFail4Initiator(){
//		Page<BorrowFlow> page=null;
//		if(this.size==0)
//			 page=new Page<BorrowFlow>(10);
//		else
//			page=new Page<BorrowFlow>(this.size);
//		page.setPageNo(this.index+1);
//		
//		SystemUser initiator=this.userService.getUser();
//		
//		Page<BorrowFlow> newPage = this.borrowFlowService.listFlow4Initiator(initiator, page, BorrowFlowConstants.BORROWFLOW_STATUS_OVER_UNPASS);
//		writeJson(newPage);
//	}
	//过期的
	public void listExpired4Initiator(){
		Page<BorrowFlow> page=null;
		if(this.size==0)
			 page=new Page<BorrowFlow>(10);
		else
			page=new Page<BorrowFlow>(this.size);
		page.setPageNo(this.index+1);
		
		SystemUser initiator=this.userService.getUser();
		
		Page<BorrowFlow> newPage = this.borrowFlowService.listExpiredAndRejectFlow4Initiator(initiator, page);
		writeJson(newPage);
	}
	
	//待管理员处理
	public void listAllFlow4Admin(){
		
		Page<BorrowFlow> page=null;
		if(this.size==0)
			 page=new Page<BorrowFlow>(10);
		else
			page=new Page<BorrowFlow>(this.size);
		page.setPageNo(this.index+1);
		SystemUser admin=this.userService.getUser();
		Page<BorrowFlow> newPage = this.borrowFlowService.listAllFlow4Admin(admin, page);
		writeJson(newPage);
	}
	
//	public void listBuilding4Admin(){
//		
//		Page<BorrowFlow> page=null;
//		if(this.size==0)
//			 page=new Page<BorrowFlow>(10);
//		else
//			page=new Page<BorrowFlow>(this.size);
//		page.setPageNo(this.index+1);
//		SystemUser admin=this.userService.getUser();
//		Page<BorrowFlow> newPage = this.borrowFlowService.listFlow4Admin(admin, page,BorrowFlowConstants.BORROWFLOW_STATUS_BULIDING);
//		writeJson(newPage);
//	}
	
	//等待我处理审核借阅的
	public void listNeedBorrow4Borrower(){
		
		Page<BorrowFlow> page=null;
		if(this.size==0)
			 page=new Page<BorrowFlow>(10);
		else
			page=new Page<BorrowFlow>(this.size);
		page.setPageNo(this.index+1);
		SystemUser borrower=this.userService.getUser();
		Page<BorrowFlow> newPage = this.borrowFlowService.listNeededFlow4Borrower(borrower, page);
		writeJson(newPage);
	}
	//审核过借阅的
	public void listBorrowed4Borrower(){
		
		Page<BorrowFlow> page=null;
		if(this.size==0)
			 page=new Page<BorrowFlow>(10);
		else
			page=new Page<BorrowFlow>(this.size);
		page.setPageNo(this.index+1);
		SystemUser borrower=this.userService.getUser();
		Page<BorrowFlow> newPage = this.borrowFlowService.listBorrowedFlow4Borrower(borrower, page);
		writeJson(newPage);
	}
	
	
	
	
	
	private void writeJson(Page<BorrowFlow> page){
		PageDTO pageDTO=new PageDTO();
		pageDTO.setTotal(page.getTotalCount());
		pageDTO.setPagesize(page.getPageSize());
		pageDTO.setTotalPage(page.getTotalPages());
		pageDTO.setData(new ArrayList());
		for(BorrowFlow flow:page.getResult()){
			
			BorrowFlowDTO dto=new BorrowFlowDTO(flow);
			
			pageDTO.getData().add(dto);
		}
		
		JSONUtil.write(response, pageDTO);
		
	}
	
	public BorrowFlowService getBorrowFlowService() {
		return borrowFlowService;
	}
	@Autowired
	public void setBorrowFlowService(BorrowFlowService borrowFlowService) {
		this.borrowFlowService = borrowFlowService;
	}

	public UserService getUserService() {
		return userService;
	}
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;	
	}
	
	
	
	
	
	
	
	
	
	

}
