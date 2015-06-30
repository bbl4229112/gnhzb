package edu.zju.cims201.GOF.service.borrowFlow;

import java.util.Set;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.ApprovalFlow;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlow;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlowContent;
import edu.zju.cims201.GOF.hibernate.pojo.Flow;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.OperationRight;
//import edu.zju.cims201.GOF.entity.account.Role;
//import edu.zju.cims201.GOF.entity.account.SUser;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;

/**
 * 提供关于知识的服务接口，由具体的实现类来实现接口定义的方法
 * 
 * @author hebi
 */

public interface BorrowFlowService {
	
	
	public FlowNode getFlowNode(Long flowNodeID);
	
	public String addBorrower(FlowNode flowNode,SystemUser borrower);
	
	public String saveBorrowFlow(Flow borrowFlow);
	
	public String saveBorrowFlow4Modify(Flow borrowFlow);
	
	public String startFlow(BorrowFlow borrowFlow);

	public BorrowFlow getBorrowFlow(Long flowId);
	public BorrowFlow getBorrowFlow(SystemUser initiator,MetaKnowledge knowledge);
	
	public BorrowFlow getBorrowFlow(SystemUser initiator,MetaKnowledge knowledge,String status);
	
	public BorrowFlow getBorrowFlow4Lender(SystemUser lender,MetaKnowledge knowledge,String status);
	
	public BorrowFlow borrow(BorrowFlow borrowFlow, String result);
	
	
	public Set<AdminUser> listBorrowFlowAdmin(MetaKnowledge knowledge);
	
	public BorrowFlow getAdminBuildingBorrowFlow(SystemUser admin,MetaKnowledge knowledge);
	
	public BorrowFlow getNeedDoBorrowingBorrowFlow(SystemUser admin,MetaKnowledge knowledge);
	
	
	public BorrowFlow createBorrowFlow(SystemUser initiator,MetaKnowledge knowledge,BorrowFlowContent content);
	
	public void deleteBorrowFlowNode(FlowNode flowNode);
	
	
	
	public BorrowFlow addBorrowFlowNode(BorrowFlow addedFlow,FlowNode flowNode);
	
	
	public Page<BorrowFlow> listFlow4Initiator(SystemUser initiator,Page<BorrowFlow> page,String status);
	
	public Page<BorrowFlow> listExpiredAndRejectFlow4Initiator(SystemUser initiator,Page<BorrowFlow> page);
	
	public Page<BorrowFlow> listBuildingAndBorrowingFlow4Initiator(SystemUser initiator,Page<BorrowFlow> page);
	
	public Page<BorrowFlow> listRelatedFlow4Initiator(SystemUser initiator,Page<BorrowFlow> page);
	
	
	
	
	
	
	
	public Page<BorrowFlow> listUnBuildFlow4Admin(SystemUser admin,Page<BorrowFlow> page);
	public Page<BorrowFlow> listFlow4Admin(SystemUser admin,Page<BorrowFlow> page,String status);
	
	public Page<BorrowFlow> listAllFlow4Admin(SystemUser admin,Page<BorrowFlow> page);
	
	public Page<BorrowFlow> listAllFlow4Admin(SystemUser admin,MetaKnowledge knowledge,Page<BorrowFlow> page);
	
	public Page<BorrowFlow> listNeededFlow4Borrower(SystemUser admin,Page<BorrowFlow> page);
	
	public Page<BorrowFlow> listBorrowedFlow4Borrower(SystemUser admin,Page<BorrowFlow> page);
	//删除借阅流
	public void deleteBorrewFlow(MetaKnowledge knowledge);
	
	
	
	
	
}
