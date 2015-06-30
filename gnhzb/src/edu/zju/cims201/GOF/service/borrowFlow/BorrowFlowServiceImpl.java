package edu.zju.cims201.GOF.service.borrowFlow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;



import edu.zju.cims201.GOF.dao.knowledge.BorrowFlowDao;
import edu.zju.cims201.GOF.dao.knowledge.FlowNodeDao;
import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.ApprovalFlow;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlow;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlowContent;
import edu.zju.cims201.GOF.hibernate.pojo.Flow;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;

import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.HibernatePorxy;

/**
 * i
 * 
 * @author hebi
 */

@Service
@Transactional 
public class BorrowFlowServiceImpl implements BorrowFlowService {
	
	private BorrowFlowDao borrowFlowDao;
	
	private FlowNodeDao flowNodeDao;
	
	private UserService userService;
	
	private TreeService treeService;
	
	private MessageService messageService;
	

	public BorrowFlow addBorrowFlowNode(BorrowFlow addedFlow, FlowNode flowNode) {
		
		SystemUser currentUser=this.userService.getUser();
		//SystemUser currentUser=this.userService.getUser(new Long(6));
		if(addedFlow.getBorrowAdmin()==null)
			addedFlow.setBorrowAdmin(currentUser);
		else if(!addedFlow.getBorrowAdmin().getEmail().equals(currentUser.getEmail())){
			
			return null;
		}
	
		addedFlow.getFlowNodes().add(flowNode);
		this.borrowFlowDao.save(addedFlow);
		this.borrowFlowDao.flush();
		return addedFlow;
	}
	
	public FlowNode getFlowNode(Long flowNodeID){
		FlowNode node = this.flowNodeDao.findUniqueBy("id", flowNodeID);
		return node;
	}
	
	public String saveBorrowFlow4Modify(Flow borrowFlow) {
		saveBorrowFlow(borrowFlow);
		return null;
	}

	public BorrowFlow borrow(BorrowFlow borrowFlow, String result) {
		
		Integer index = borrowFlow.getIndex();
		FlowNode flowNode = borrowFlow.getFlowNodes().get(index);
		flowNode.setNodeStatus(result);
		flowNode.setApprovalORBorrowTime(new Date());
		if(result.equals(BorrowFlowConstants.BORROWFLOWNODE_STATUS_UNPASS)){
			flowNode.setNodeStatus(BorrowFlowConstants.BORROWFLOWNODE_STATUS_UNPASS);
			borrowFlow.setStatus(BorrowFlowConstants.BORROWFLOW_STATUS_OVER_UNPASS);
		}
		else if(result.equals(BorrowFlowConstants.BORROWFLOWNODE_STATUS_PASS)&&borrowFlow.getFlowNodes().size()==index+1){
			flowNode.setNodeStatus(BorrowFlowConstants.BORROWFLOWNODE_STATUS_PASS);
			borrowFlow.setStatus(BorrowFlowConstants.BORROWFLOW_STATUS_OVER_PASS);
			borrowFlow.setStartTime(new Date());
		}
		else if(result.equals(BorrowFlowConstants.BORROWFLOWNODE_STATUS_PASS)&&((index+1)<borrowFlow.getFlowNodes().size())){
			flowNode.setNodeStatus(BorrowFlowConstants.BORROWFLOWNODE_STATUS_PASS);
			borrowFlow.setIndex(++index);
			borrowFlow.setCurrentNode(borrowFlow.getFlowNodes().get(index));
		}
			
		
		this.borrowFlowDao.save(borrowFlow);
		this.borrowFlowDao.flush();
		return borrowFlow;
	}
	
	public BorrowFlow getBorrowFlow(SystemUser initiator, MetaKnowledge knowledge) {
		BorrowFlow borrowFlow = (BorrowFlow)this.borrowFlowDao.findUnique("select distinct bf from BorrowFlow bf where bf.initiator=? " +
				" and bf.knowledge=? and bf.status!=?", 
												initiator,knowledge,BorrowFlowConstants.BORROWFLOW_STATUS_EXPIRED);
		return borrowFlow;
	}
	
	public BorrowFlow getBorrowFlow(SystemUser initiator,
			MetaKnowledge knowledge, String status) {
		BorrowFlow borrowFlow = (BorrowFlow)this.borrowFlowDao.findUnique("select distinct bf from BorrowFlow bf where bf.initiator=? " +
				" and bf.knowledge=? and bf.status=?", 
												initiator,knowledge,status);
		return borrowFlow;
	}
	
	public BorrowFlow getBorrowFlow4Lender(SystemUser lender,MetaKnowledge knowledge,String status){
		BorrowFlow borrowFlow = (BorrowFlow)this.borrowFlowDao.findUnique("select distinct bf from BorrowFlow bf,FlowNode node where bf.currentNode=node " +
				"and node.approverORLender=? and bf.knowledge=? and bf.status=?", 
				lender,knowledge,status);
		
		return borrowFlow;
	}
	
	public BorrowFlow getBorrowFlow(Long flowId) {
		BorrowFlow borrowFlow = this.borrowFlowDao.findUniqueBy("id", flowId);
		return borrowFlow;
	}
	
	public void deleteBorrowFlowNode(FlowNode flowNode) {
		this.flowNodeDao.delete(flowNode);
		this.flowNodeDao.flush();
	}

	
	
	
	public Set<AdminUser> listBorrowFlowAdmin(MetaKnowledge knowledge) {
		SystemUser uploader=knowledge.getUploader();
		Set<TreeNode> roles = this.userService.listRoleTreeNodes(uploader);
		RoleTreeNode orgnizationNode=(RoleTreeNode)getOrgnizationNode(roles);
		Set<AdminUser> admins = this.treeService.listAdmins(orgnizationNode);
		return admins;
	}
	
	private TreeNode getOrgnizationNode(Set<TreeNode> roles ){
		for(TreeNode role:roles){	
			for(Long pid=role.getParentId();pid!=null;){
				TreeNode pNode=this.treeService.getTreeNode(pid);
				if(pNode.getId()==1)
					return pNode;
				pid=pNode.getParentId();
			}
		}
		return null;
	}

	public BorrowFlow createBorrowFlow(SystemUser initiator, MetaKnowledge knowledge,BorrowFlowContent content) {
		List<BorrowFlow> borrowFlows = this.borrowFlowDao.find("select distinct bf from BorrowFlow bf where bf.initiator=? " +
				" and bf.knowledge=? and bf.status!=? and bf.status!=?", 
				initiator,knowledge,BorrowFlowConstants.BORROWFLOW_STATUS_OVER_UNPASS,BorrowFlowConstants.BORROWFLOW_STATUS_EXPIRED);
		if(borrowFlows.size()!=0)
			return borrowFlows.get(0);
		BorrowFlow borrowFlow=new BorrowFlow();
		borrowFlow.setKnowledge(knowledge);
		borrowFlow.setInitiator(initiator);
		borrowFlow.setBorrowFlowContent(content);
		borrowFlow.setIndex(0);
		borrowFlow.setStartTime(new Date());
		borrowFlow.setStatus(BorrowFlowConstants.BORROWFLOW_STATUS_BULIDING);
		this.borrowFlowDao.save(borrowFlow);
		this.borrowFlowDao.flush();
		return borrowFlow;
	}
	
	
	public String addBorrower(FlowNode flowNode,SystemUser borrower) {
		
		flowNode.setApproverORLender(borrower);
		
		this.flowNodeDao.save(flowNode);
		this.borrowFlowDao.save((BorrowFlow)flowNode.getFlow());
		this.borrowFlowDao.flush();
		return null;
	}
	
	@Transactional
	public String saveBorrowFlow(Flow borrowFlow) {
		
		Object proxyObj = borrowFlow;
		Object  realEntity=null;
		if (borrowFlow instanceof HibernateProxy) {  
		      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
		 } else{
			 realEntity=proxyObj;
		 }
		this.borrowFlowDao.save((BorrowFlow)realEntity);
		this.borrowFlowDao.flush();
		return null;
	}
	
	
	public BorrowFlow getAdminBuildingBorrowFlow(SystemUser admin, MetaKnowledge knowledge) {
		BorrowFlow borrowFlow = (BorrowFlow)this.borrowFlowDao.findUnique("select distinct bf from BorrowFlow bf where bf.borrowAdmin=? " +
				"and bf.knowledge=? and bf.status=?", 
				admin,knowledge,BorrowFlowConstants.BORROWFLOW_STATUS_BULIDING);
		return borrowFlow;
	}
	
	
	public BorrowFlow getNeedDoBorrowingBorrowFlow(SystemUser user, MetaKnowledge knowledge) {
		
		
		List<FlowNode> nodes = this.flowNodeDao.find("select distinct node from FlowNode node where node.approverORLender=? " +
				" and node.nodeStatus=? ", user,BorrowFlowConstants.BORROWFLOWNODE_STATUS_WATING_DEAL);
		List<Flow> flows=new ArrayList<Flow>();
		for(FlowNode node:nodes){
			flows.add(node.getFlow());
		}
		for(Flow flow:flows){
			HibernatePorxy<Flow> hp=new HibernatePorxy<Flow>();
			Flow real=hp.getRealEntity(flow);
			if(real instanceof BorrowFlow){
				BorrowFlow bf=(BorrowFlow)real;
				long id=bf.getKnowledge().getId();
				if(id==knowledge.getId())
					return bf;
			}
			
		}
		
		return null;
	}
	
	//**********************************************************************************************************
	
	
	public Page<BorrowFlow> listFlow4Initiator(SystemUser initiator, Page<BorrowFlow> page, String status) {
		Page<BorrowFlow> flows = this.borrowFlowDao.findPage(page, "select distinct bf from BorrowFlow bf where bf.initiator=? " +
				" and bf.status=? ", initiator,status);
		return flows;
	}
	
	public Page<BorrowFlow> listBuildingAndBorrowingFlow4Initiator(SystemUser initiator, Page<BorrowFlow> page) {
		Page<BorrowFlow> flows = this.borrowFlowDao.findPage(page, "select distinct bf from BorrowFlow bf where bf.initiator=? " +
				" and ( bf.status=? or bf.status=? ) ", initiator,BorrowFlowConstants.BORROWFLOW_STATUS_BORROWING,
				BorrowFlowConstants.BORROWFLOW_STATUS_BULIDING);
		return flows;
	}

	public Page<BorrowFlow> listRelatedFlow4Initiator(SystemUser initiator, Page<BorrowFlow> page) {
		Page<BorrowFlow> flows = this.borrowFlowDao.findPage(page, "select distinct bf from BorrowFlow bf where bf.initiator=? ",initiator );
		return flows;
	}
	
	public Page<BorrowFlow> listExpiredAndRejectFlow4Initiator(
			SystemUser initiator, Page<BorrowFlow> page) {
		Page<BorrowFlow> flows = this.borrowFlowDao.findPage(page, "select distinct bf from BorrowFlow bf where bf.initiator=? " +
				" and (bf.status=? or bf.status=?) ", initiator,
				BorrowFlowConstants.BORROWFLOW_STATUS_EXPIRED, 
				BorrowFlowConstants.BORROWFLOW_STATUS_OVER_UNPASS);
		return flows;
	}


	
	public Page<BorrowFlow> listFlow4Admin(SystemUser admin, Page<BorrowFlow> page, String status) {
		Page<BorrowFlow> flows = this.borrowFlowDao.findPage(page, "select distinct bf from BorrowFlow bf where bf.borrowAdmin=? " +
				" and bf.status=? ", admin,status);
		return flows;
	}
	
	public Page<BorrowFlow> listUnBuildFlow4Admin(SystemUser admin, Page<BorrowFlow> page) {
		Page<BorrowFlow> flows = this.borrowFlowDao.findPage(page, "select distinct bf from BorrowFlow bf,Message message where message.messageType=? " +
				" and bf.knowledge=message.knowledge and bf.borrowAdmin is null", "BorrowFlow");
		
		return flows;
	}
	public Page<BorrowFlow> listAllFlow4Admin(SystemUser admin, Page<BorrowFlow> page) {
		System.out.println("************************");
		Page<BorrowFlow> flows = this.borrowFlowDao.findPage(page, "select distinct bf from BorrowFlow bf,Message message where ( message.messageType=? " +
				" and bf.knowledge=message.knowledge and bf.borrowAdmin is null and message.receiver=? and bf.status=?) or ( bf.borrowAdmin=? and bf.status=? )", "BorrowFlow",
				admin,BorrowFlowConstants.BORROWFLOW_STATUS_BULIDING,admin,BorrowFlowConstants.BORROWFLOW_STATUS_BULIDING);
		return flows;
	}
	
	public Page<BorrowFlow> listAllFlow4Admin(SystemUser admin,
			MetaKnowledge knowledge, Page<BorrowFlow> page) {
		Page<BorrowFlow> flows = this.borrowFlowDao.findPage(page, "select distinct bf from BorrowFlow bf,Message message where ( message.messageType=? " +
				" and bf.knowledge=message.knowledge and bf.borrowAdmin is null and message.receiver=? and bf.knowledge=?) or ( bf.borrowAdmin=? and bf.status=? ) " +
				" and bf.knowledge=?", "BorrowFlow",
				admin,admin,knowledge,BorrowFlowConstants.BORROWFLOW_STATUS_BULIDING,knowledge);
		return flows;
	}

	public String startFlow(BorrowFlow borrowFlow) {
		borrowFlow.setStatus(BorrowFlowConstants.BORROWFLOW_STATUS_BORROWING);
		borrowFlow.setCurrentNode(borrowFlow.getFlowNodes().get(0));
		saveBorrowFlow(borrowFlow);
		return null;
	}

	

	public Page<BorrowFlow> listNeededFlow4Borrower(SystemUser borrower, Page<BorrowFlow> page) {
		Page<BorrowFlow> flows = this.borrowFlowDao.findPage(page, "select distinct bf from BorrowFlow bf,FlowNode node where bf.currentNode=node " +
				" and node.approverORLender=? and node.nodeStatus=?", borrower,BorrowFlowConstants.BORROWFLOWNODE_STATUS_WATING_DEAL);
		return flows;
	}
	
	
	public Page<BorrowFlow> listBorrowedFlow4Borrower(SystemUser borrower, Page<BorrowFlow> page) {
		Page<BorrowFlow> flows = this.borrowFlowDao.findPage(page, "select distinct bf from BorrowFlow bf,FlowNode node where node.flow=bf " +
				" and node.approverORLender=? and ( node.nodeStatus=? or node.nodeStatus=? )", borrower,
				BorrowFlowConstants.BORROWFLOWNODE_STATUS_PASS,BorrowFlowConstants.BORROWFLOWNODE_STATUS_UNPASS);
		return flows;
	}

	


	
	public BorrowFlowDao getBorrowFlowDao() {
		return borrowFlowDao;
	}
	
	@Autowired
	public void setBorrowFlowDao(BorrowFlowDao borrowFlowDao) {
		this.borrowFlowDao = borrowFlowDao;
	}

	public UserService getUserService() {
		return userService;
	}
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public TreeService getTreeService() {
		return treeService;
	}
	@Autowired
	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}

	public FlowNodeDao getFlowNodeDao() {
		return flowNodeDao;
	}
	@Autowired
	public void setFlowNodeDao(FlowNodeDao flowNodeDao) {
		this.flowNodeDao = flowNodeDao;
	}

	public MessageService getMessageService() {
		return messageService;
	}
	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void deleteBorrewFlow(MetaKnowledge knowledge) {
		List<BorrowFlow> flows = this.borrowFlowDao.find( "select distinct bf from BorrowFlow bf where bf.knowledge=? ", knowledge);
				for(BorrowFlow bf:flows){
					this.borrowFlowDao.delete(bf);
				}
	}


	

	

	

	

	

	

	
	

	


	
	

	

	

	
	
	

	

	


	

	

	
	
	
	

	
	
	
	
	
	
	

	
	
	
	

	
	
	

	
}
