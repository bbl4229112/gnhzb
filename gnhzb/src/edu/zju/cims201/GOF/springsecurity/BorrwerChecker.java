package edu.zju.cims201.GOF.springsecurity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlow;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlowContent;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowConstants;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;
import edu.zju.cims201.GOF.util.Constants;


@Component
public class BorrwerChecker {
	
	private SysBehaviorLogService sysBehaviorLogService;
	private BorrowFlowService borrowFlowService;
	
	public boolean check(SystemUser user,MetaKnowledge knowledge){
		
		BorrowFlow borrowFlow = this.borrowFlowService.getBorrowFlow(user, knowledge,BorrowFlowConstants.BORROWFLOW_STATUS_OVER_PASS);
		//System.out.println("ssssssssssssssssssssssssssssss"+borrowFlow.getStatus());
		if(borrowFlow==null)
			return false;
		BorrowFlowContent content = borrowFlow.getBorrowFlowContent();
		if(content==null)
			return false;
		
		if(!content.isLimited()){
			
			return true;
		}else{
			Integer times=content.getTimes();
			Integer logTimes=this.sysBehaviorLogService.getBorrowedTimes(content);
			//System.out.println("ssss"+times);
			Date time = content.getBorrowTime();
			Date currentTime=new Date();
			
			if(times!=0){
				if(logTimes<times){
					this.sysBehaviorLogService.increaseBorrowedTimes(content);
					return true;
				}else{
					borrowFlow.setStatus(BorrowFlowConstants.BORROWFLOW_STATUS_EXPIRED);
					this.borrowFlowService.saveBorrowFlow(borrowFlow);
					return false;
				}
			}
			
			if(time!=null){
//				System.out.println("time"+time);
//				System.out.println("currentTime"+currentTime);
				if(time.after(currentTime)){
					return true;	
				}else{
					System.out.println("currentTime"+currentTime);
					borrowFlow.setStatus(BorrowFlowConstants.BORROWFLOW_STATUS_EXPIRED);
					this.borrowFlowService.saveBorrowFlow(borrowFlow);
					return false;
				}
			}
			}
			
			return false;	
	}
	
	public boolean check4download(SystemUser user,MetaKnowledge knowledge){
		BorrowFlow borrowFlow = this.borrowFlowService.getBorrowFlow(user, knowledge,BorrowFlowConstants.BORROWFLOW_STATUS_OVER_PASS);
	
		if(knowledge.getUploader().getEmail().equals(user.getEmail())||user.getEmail().equals(Constants.SUPERADMIN))
			return true;
		
		if(borrowFlow==null)
			return false;
		BorrowFlowContent content = borrowFlow.getBorrowFlowContent();
		if(content==null)
			return false;
		if(!content.isDownload())
			return false;
		else
			return true;
		
	}
	
	public boolean check4lender(SystemUser user,MetaKnowledge knowledge){
		BorrowFlow borrowFlow = this.borrowFlowService.getBorrowFlow4Lender(user, knowledge,BorrowFlowConstants.BORROWFLOW_STATUS_BORROWING);
		if(borrowFlow==null)
			return false;
		FlowNode node = borrowFlow.getCurrentNode();
		if(node==null)
			return false;
		SystemUser lender=node.getApproverORLender();
		if(lender==null)
			return false;
		if(lender.getEmail().equals(user.getEmail()))
			return true;
		else 
			return false;
		
	}

	public BorrowFlowService getBorrowFlowService() {
		return borrowFlowService;
	}
	@Autowired
	public void setBorrowFlowService(BorrowFlowService borrowFlowService) {
		this.borrowFlowService = borrowFlowService;
	}

	public SysBehaviorLogService getSysBehaviorLogService() {
		return sysBehaviorLogService;
	}
	@Autowired
	public void setSysBehaviorLogService(SysBehaviorLogService sysBehaviorLogService) {
		this.sysBehaviorLogService = sysBehaviorLogService;
	}
	
	
	

}
