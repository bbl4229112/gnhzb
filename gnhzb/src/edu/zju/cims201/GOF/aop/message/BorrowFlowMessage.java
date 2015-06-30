package edu.zju.cims201.GOF.aop.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.proxy.dwr.Util;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlow;
import edu.zju.cims201.GOF.hibernate.pojo.Flow;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowConstants;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.HibernatePorxy;
import edu.zju.cims201.GOF.web.knowledge.BorrowAction;
import edu.zju.cims201.GOF.web.message.dwr.ChatManager;


@Aspect @Service 
public class BorrowFlowMessage {
	
	private TreeService treeService;
	
	private MessageService messageService;
	
	private BorrowFlowService borrowFlowService;
	
	@Around("execution(*  edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowServiceImpl.startFlow(..)) ") 
	public Object  doAroundFinishBorrowFlowConstruction(ProceedingJoinPoint  pjp) throws Throwable {
		
		
		Object[] args = pjp.getArgs();
		Object proxy=args[0];
		
		Object proxyObj = proxy;
		Object  realEntity=null;
		if (proxyObj instanceof HibernateProxy) {  
		      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
		 } else{
			 realEntity=proxyObj;
		 }
		
	    BorrowFlow borrowFlow=(BorrowFlow)realEntity;
	    		Object ret=pjp.proceed();
		Long flowid=borrowFlow.getId();
		if(flowid==0)
			return ret;
		BorrowFlow borrowFlowAfter=this.borrowFlowService.getBorrowFlow(flowid);
		FlowNode currentNode=borrowFlowAfter.getFlowNodes().get(borrowFlowAfter.getIndex());
		MetaKnowledge knowledge=borrowFlowAfter.getKnowledge();
		sendBorrowMessage(currentNode,knowledge);
	//	System.out.println("sssssssssssss");
		return ret;
		
	}
	
	
	@Around("execution(*  edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowServiceImpl.borrow(..)) ") 
	public Object  doAroundBorrow(ProceedingJoinPoint  pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		String input=(String)args[1];
		Object ret=pjp.proceed();
		BorrowFlow result=(BorrowFlow)ret;
		MetaKnowledge knowledge=result.getKnowledge();
		Integer index=result.getIndex();
		FlowNode currentNode=result.getFlowNodes().get(index);
		if(input.equals(BorrowFlowConstants.BORROWFLOWNODE_STATUS_PASS)){
			int size=result.getFlowNodes().size();
			
			
			if(index<(size-1)&&(result.getFlowNodes().get(index-1).getNodeStatus().
					equals(BorrowFlowConstants.BORROWFLOWNODE_STATUS_PASS))){
				sendBorrowMessage(currentNode,knowledge);
				FlowNode beforeNode=result.getFlowNodes().get(index-1);
				sendDealMessage(beforeNode,knowledge);
			}
			if(index==(size-1)&&(currentNode.getNodeStatus().
					equals(BorrowFlowConstants.BORROWFLOWNODE_STATUS_PASS))){
				sendDealMessage(currentNode,knowledge);
			}
			
		}else if(input.equals(BorrowFlowConstants.BORROWFLOWNODE_STATUS_UNPASS)){
			sendDealMessage(currentNode,knowledge);
		}
		
		
		
		
		return ret;
		
	}
	
	
	
	@After("execution(* edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowServiceImpl.createBorrowFlow(..)) ") 
	public void doAfterCreateBorrowFlow(JoinPoint jp) {
		
		//System.out.println("-------message_createABorrowFlow");
		Object[] args = jp.getArgs();
		Object object0=args[0];
		Object object1=args[1];
		Object  realEntity=null;
		if (object0 instanceof HibernateProxy) {  
		      realEntity= ((HibernateProxy)object0).getHibernateLazyInitializer().getImplementation() ;    
		 } else{
			 realEntity=object0;
		 }
		SystemUser sender=(SystemUser)realEntity;
		if (object1 instanceof HibernateProxy) {  
		      realEntity= ((HibernateProxy)object1).getHibernateLazyInitializer().getImplementation() ;    
		 } else{
			 realEntity=object1;
		 }
		MetaKnowledge knowledge=(MetaKnowledge)realEntity;
	    
	   
	   
	    SystemUser uploader=knowledge.getUploader();
	    Set<SystemUser>  admins=getAdmins(uploader);
	    if(null!=admins){
	    	//System.out.println("adminsadminsadmins");
		    for (SystemUser admin : admins) {
	
					Message message = new Message();
					message.setIsRead(false);
					message.setKnowledge(knowledge);
					message.setSender(sender);
					message.setReceiver(admin);
					message.setMessageType("BorrowFlow");
					message.setSendTime(new Date());
					message.setContent("(" + sender.getEmail() + ")申请借阅");
					messageService.save(message);
					send(sender, admin, message.getContent(), knowledge
							.getTitlename());
				}
	    }
	         
		
	}
	
	
	
	@After("execution(* edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowServiceImpl.saveBorrowFlow4Modify(..)) ") 
	public void doAfterModifyFlowNode(JoinPoint jp) {
		Object[] args = jp.getArgs();
		Object object0=args[0];
		Object  realEntity=null;
		if (object0 instanceof HibernateProxy) {  
		      realEntity= ((HibernateProxy)object0).getHibernateLazyInitializer().getImplementation() ;    
		 } else{
			 realEntity=object0;
		 }
		BorrowFlow borrowFlow=(BorrowFlow)realEntity;
		
		
		
		
		FlowNode current=borrowFlow.getCurrentNode();
		MetaKnowledge knowledge=borrowFlow.getKnowledge();
		sendBorrowMessage(current,knowledge);
	}
	
	
	
	
	private void sendDealMessage(FlowNode flowNode,MetaKnowledge knowledge){
		SystemUser sender=flowNode.getApproverORLender();
		SystemUser receiver=flowNode.getInitiator();
		Message message=new Message();
        message.setIsRead(false);
        message.setKnowledge(knowledge);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessageType("BorrowFlow");
        message.setSendTime(new Date());
        String operation="";
        if(null!=flowNode.getNodeStatus()&&flowNode.getNodeStatus().equals("通过"))
        	operation="批准了你借阅";
        if(null!=flowNode.getNodeStatus()&&flowNode.getNodeStatus().equals("不通过"))
        	operation="拒绝了你借阅";
        message.setContent("("+sender.getEmail()+")"+operation);
        messageService.save(message);
    	send(sender,receiver,message.getContent(),knowledge.getTitlename());
	}
	
	private void sendBorrowMessage(FlowNode currentNode,MetaKnowledge knowledge){
		SystemUser sender=currentNode.getInitiator();
		SystemUser receiver=currentNode.getApproverORLender();
		Message message=new Message();
        message.setIsRead(false);
        message.setKnowledge(knowledge);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessageType("BorrowFlow");
        message.setSendTime(new Date());
        message.setContent("("+sender.getEmail()+")申请借阅");
        messageService.save(message);
    	send(sender,receiver,message.getContent(),knowledge.getTitlename());
	}
	
	
	public Set<SystemUser> getAdmins(SystemUser uploader){
		
		Set<TreeNode> nodes = uploader.getRoleNodes();
		for(TreeNode node:nodes){
			System.out.println("ttttt:"+node.getNodeName());
			System.out.println("ttttt:"+node.getId());
			Set<SystemUser>  admins=new HashSet<SystemUser>();
			TreeNode needNode=getOrgnization(node);
			if(needNode!=null){
				Set<AdminPrivilegeTriple> adminTriples = needNode.getAdminPrivilegeTriples();
				for(AdminPrivilegeTriple triple:adminTriples){
					admins.add(triple.getAdmin());
				}
				return admins;
			}
		}
		
		return null;
	}
	
	private TreeNode getOrgnization(TreeNode node){
		System.out.println(Constants.Borrow_ADMIN_ORG_TREENODEID);
		System.out.println(node.getParentId());
		for(Long pid=node.getParentId();pid!=null;){
			if(pid.equals(Constants.Borrow_ADMIN_ORG_TREENODEID)){
			System.out.println("组织树："+node.getNodeName());
				return node;
			}
			node=this.treeService.getTreeNode(pid);
			pid=node.getParentId();
		}
		
		return null;
	}
	
	/**
	 * 发送消息
	 * @param sender 发送者
	 * @param receiverid 接收者id
	 * @param msg 消息内容
	 * @param request
	 */
	private void send(SystemUser sender,SystemUser receiver,String msg,String object){
		Map<String , ScriptSession> scriptmap=ChatManager.scriptmap;

		ScriptSession receiverscriptSession=null;
		   for (Map.Entry<String,ScriptSession> entry:scriptmap.entrySet()) {
			   ScriptSession scriptSession=(ScriptSession) entry.getValue();
			   String userid=(String)scriptSession.getAttribute("userid");
	             System.out.println("scriptSession_userid="+userid);
	             if(userid.equals(String.valueOf(receiver.getId())))
	                {receiverscriptSession=scriptSession;break;}
          }
	        
	        if(null!=receiverscriptSession){
	        	
	        	Util util = new Util(receiverscriptSession);
	        	util.setValue("messageListBt", "新消息");
	    		util.addFunctionCall("remaindNewMessage");

	        	
	        }
	        else{System.out.println("用户不在线！");}
	
	}


	public TreeService getTreeService() {
		return treeService;
	}

	@Autowired
	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}



	public MessageService getMessageService() {
		return messageService;
	}


	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}



	public BorrowFlowService getBorrowFlowService() {
		return borrowFlowService;
	}


	@Autowired
	public void setBorrowFlowService(BorrowFlowService borrowFlowService) {
		this.borrowFlowService = borrowFlowService;
	}
	
	

}
