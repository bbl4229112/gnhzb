package edu.zju.cims201.GOF.aop.message;


import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.stereotype.Service;

import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.hibernate.pojo.ApprovalFlow;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.interestmodel.InterestModelServiceImpl;
import edu.zju.cims201.GOF.service.logging.AddUserScore;
import edu.zju.cims201.GOF.service.logging.SysBehaviorListService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.web.message.dwr.ChatManager;


 
@Aspect @Service  
public class ApprovalFlowMessage {
	@Resource(name="messageServiceImpl")
	MessageService messageService; 
	@Resource(name="sysBehaviorLogServiceImpl")
	SysBehaviorLogService sysBehaviorLogService;
	@Resource(name="sysBehaviorListServiceImpl")
	SysBehaviorListService sysBehaviorListService;
	@Resource(name="addUserScore")
	AddUserScore addUserScore;
	@Resource(name="commonDao")
	CommonDao dao;
	@Resource(name="interestModelServiceImpl")
	InterestModelServiceImpl interestModelServiceImpl;
	
	@After("execution(* edu.zju.cims201.GOF.service.approvalFlow.ApprovalFlowServiceImpl.saveApprovalFlow(..)) ") 
    public void doAfterSaveApprovalFlow(JoinPoint jp) {      
		 Date date=new Date();
	
    Object[] args = jp.getArgs();
    ApprovalFlow approvalFlow=(ApprovalFlow)args[0];
    SystemUser sender=new SystemUser();
    SystemUser receiver=new SystemUser();
    FlowNode flowNode=approvalFlow.getFlowNodes().get(approvalFlow.getIndex());//得到当前的节点
    
    //关于审批事物的消息发送
    String approvalType="";	
    if(flowNode.getNodeStatus().equals(Constants.approvalFlowNodePending))//如果节点的审批状态为“待审批”，则sender为审批发起人
    {
    	sender=approvalFlow.getInitiator();
    receiver=flowNode.getApproverORLender();
    approvalType="向你发出了知识审批请求";	
    }
    else{                                                                //否则就是审批人发回审批结果，即sender为审批人
    sender=flowNode.getApproverORLender();
    receiver=approvalFlow.getInitiator();
    approvalType="处理了你的知识审批请求";
    }
      MetaKnowledge knowledge=approvalFlow.getKnowledge();
      
      Message message=new Message();
      message.setIsRead(false);
      message.setKnowledge(knowledge);
      message.setSender(sender);
      message.setReceiver(receiver);
      message.setMessageType("ApprovalFlow");
      message.setSendTime(new Date());//时间是发起人的时间还是审批处理的时间？？？？？

      message.setContent(approvalType);
      if(sender.getId()!=0){
      messageService.save(message);
      send(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息
    
      if(!flowNode.getNodeStatus().equals(Constants.approvalFlowNodePending))//如果节点的审批状态为“待审批”，则sender为审批发起人
      {
      //记录审批行为行为到SysBehaviorLog表
        SysBehaviorLog bLog = new SysBehaviorLog();
		bLog.setObjectid(approvalFlow.getKnowledge().getId());
		bLog.setObjectType("knowledge");
		bLog.setUser(sender);
		
		Long SysBehaviorListId=9L;
		
		bLog.setActionTime(flowNode.getApprovalORBorrowTime());
		bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(9L));
		
		sysBehaviorLogService.save(bLog);

		//累加用户积分
		addUserScore.addUserScore(sender,SysBehaviorListId);
      }
      }
      //关于知识入库  如果知识审批流的index=0，并且该flowNode的status为pass获passButBlock,则知识入库。此时给知识上传者记录知识上传事件，并为知识上传者加分
      if(approvalFlow.getIndex()==0&&
    		  (flowNode.getNodeStatus().equals(Constants.approvalFlowNodeResult_Pass)||flowNode.getNodeStatus().equals(Constants.approvalFlowNodeResult_PassButBlock)))
      {
    	  
    	SysBehaviorLog rbLog = new SysBehaviorLog();
  		rbLog.setObjectid(approvalFlow.getKnowledge().getId());
  		rbLog.setObjectType("knowledge");
  		rbLog.setUser(sender);
  		Long rSysBehaviorListId=2L;		
  		rbLog.setActionTime(flowNode.getApprovalORBorrowTime());
  		rbLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(rSysBehaviorListId));	
  		sysBehaviorLogService.save(rbLog);

  		//累加用户积分
  		addUserScore.addUserScore(receiver,rSysBehaviorListId);    
  		
  	  
    	SysBehaviorLog r2bLog = new SysBehaviorLog();
  		r2bLog.setObjectid(approvalFlow.getKnowledge().getId());
  		r2bLog.setObjectType("knowledge");
  		r2bLog.setUser(receiver);
  		
  		Long r2SysBehaviorListId=new Long(10);
  		
  		r2bLog.setActionTime(flowNode.getApprovalORBorrowTime());
  		r2bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(r2SysBehaviorListId));
  		
  		sysBehaviorLogService.save(r2bLog);

  		//累加用户积分
  		addUserScore.addUserScore(receiver,r2SysBehaviorListId);    
  		
  		
  		
//  		//发送知识预定消息
//        Set<SystemUser> subscribers=new HashSet<SystemUser>();
// 		sender=knowledge.getUploader();
// 			
// 	  
// 	       //根据关键词预定发送消息
// 	    	if(knowledge.getKeywords()!=null)
// 	    	{
// 	          Iterator it=knowledge.getKeywords().iterator();
// 	    	  while(it.hasNext())
// 		       {  Keyword keyword=(Keyword)it.next();
// 		        subscribers=interestModelServiceImpl.getsubscribers(keyword.getKeywordName(), Constants.INTEREST_MODEL_KEYWORD);
// 		             if(subscribers!=null)
// 		                { Iterator it_=subscribers.iterator();
// 		  	    	       while(it_.hasNext())
// 				             {SystemUser receiver2=(SystemUser)it_.next();
// 				             
// 				              Message bookmessage=new Message();
// 				             bookmessage.setIsRead(false);
// 				            bookmessage.setKnowledge(knowledge);
// 				           bookmessage.setSender(receiver);
// 				          bookmessage.setMessageType("subscribeknowledge");
// 				         bookmessage.setSendTime(date);
// 						        
// 				        bookmessage.setReceiver(receiver2);
// 				       bookmessage.setContent("你预定的关键词<a href='' >"+keyword.getKeywordName()+"</a>有新的知识入库<a href='' >"+knowledge.getTitlename()+"</a>");
//
// 						       dao.save(bookmessage);
// 						       
// 						       send(receiver,receiver2,"你预定的关键词"+keyword.getKeywordName()+"有新的知识入库",knowledge.getTitlename());//发送消息
//
// 				             }
// 		        	 
// 		                }
// 	         
// 		
// 		         }
// 		
// 	         }
// 	    	//根据作者预定发送消息
// 		    subscribers=interestModelServiceImpl.getsubscribers(knowledge.getUploader_String(), Constants.INTEREST_MODEL_USER);
//             if(subscribers!=null){
// 		       Iterator it_user=subscribers.iterator();
// 	    	   while(it_user.hasNext())
// 	    	    {
// 	    		 SystemUser  receiver2=(SystemUser)it_user.next();
// 	    		   
// 	    		   Message bookmessage=new Message();
// 	    		  bookmessage.setIsRead(false);
// 	    		 bookmessage.setKnowledge(knowledge);
// 	    		bookmessage.setSender(receiver);
// 	    		bookmessage.setMessageType("subscribeknowledge");
// 	    		bookmessage.setSendTime(date);
// 	    		bookmessage.setReceiver(receiver2);
// 	    		bookmessage.setContent("你关注的用户<a href='' >"+receiver.getName()+"</a>有新的知识发布<a href='' >"+knowledge.getTitlename()+"</a>");
//
// 			       dao.save(bookmessage);
// 			       
// 			       send(receiver,receiver2,"你关注的用户"+sender.getName()+"有新的知识发布",knowledge.getTitlename());//发送消息
//
// 	    	    }
//             }
//             
// 	    	//根据关注的领域树节点发送消息
// 		    subscribers=interestModelServiceImpl.getsubscribers(knowledge.getDomainNode_string(), Constants.INTEREST_MODEL_TREENODE);
//
// 		    if(subscribers!=null){
// 			       Iterator it_treeNode=subscribers.iterator();
// 		    	   while(it_treeNode.hasNext())
// 		    	    {
// 		    		 SystemUser  receiver2=(SystemUser)it_treeNode.next();
// 		    		   
// 		    		   Message bookmessage=new Message();
// 		    		  bookmessage.setIsRead(false);
// 		    		 bookmessage.setKnowledge(knowledge);
// 		    		bookmessage.setSender(receiver);
// 		    		bookmessage.setMessageType("subscribeknowledge");
// 		    		bookmessage.setSendTime(date);
// 		    		bookmessage.setReceiver(receiver2);
// 		    		bookmessage.setContent("你关注的领域<a href='' >"+knowledge.getDomainNode_string()+"</a>有新的知识发布<a href='' >"+knowledge.getTitlename()+"</a>");
//
// 				       dao.save(bookmessage);
// 				       
// 				       send(receiver,receiver2,"你关注的领域"+knowledge.getDomainNode_string()+"有新的知识发布",knowledge.getTitlename());//发送消息
//
// 		    	    }
// 	            }
// 	    	
// 		  //根据关注的多维分类树节点发送消息
// 		    subscribers=interestModelServiceImpl.getsubscribers(knowledge.getCategories_string(), Constants.INTEREST_MODEL_TREENODE);
//
// 		    if(subscribers!=null){
// 			       Iterator it_treeNode=subscribers.iterator();
// 		    	   while(it_treeNode.hasNext())
// 		    	    {
// 		    		SystemUser   receiver2=(SystemUser)it_treeNode.next();
// 		    		   
// 		    		   Message bookmessage=new Message();
// 		    		  bookmessage.setIsRead(false);
// 		    		 bookmessage.setKnowledge(knowledge);
// 		    		bookmessage.setSender(receiver);
// 		    		bookmessage.setMessageType("subscribeknowledge");
// 		    		bookmessage.setSendTime(date);
// 		    		bookmessage.setReceiver(receiver2);
// 		    		bookmessage.setContent("你关注的多维分类树节点<a href='' >"+knowledge.getCategories_string()+"</a>有新的知识发布<a href='' >"+knowledge.getTitlename()+"</a>");
//
// 				       dao.save(bookmessage);
// 				       
// 				       send(receiver,receiver2,"你关注的多维分类树节点"+knowledge.getCategories_string()+"有新的知识发布",knowledge.getTitlename());//发送消息
//
// 		    	    }
// 	            }
// 	    	
//  		
      }
      if(null!=approvalFlow.getIndex()&&approvalFlow.getIndex()!=0&&
    		  (flowNode.getNodeStatus().equals(Constants.approvalFlowNodeResult_Pass)||flowNode.getNodeStatus().equals(Constants.approvalFlowNodeResult_PassButBlock)))
      {
    	  
    	SysBehaviorLog rbLog = new SysBehaviorLog();
  		rbLog.setObjectid(approvalFlow.getKnowledge().getId());
  		rbLog.setObjectType("knowledge");
  		rbLog.setUser(receiver);
  		
  		Long rSysBehaviorListId=new Long(10+approvalFlow.getIndex());
  		
  		rbLog.setActionTime(flowNode.getApprovalORBorrowTime());
  		rbLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(rSysBehaviorListId));
  		
  		sysBehaviorLogService.save(rbLog);

  		//累加用户积分
  		addUserScore.addUserScore(receiver,rSysBehaviorListId);    
      }
      //关于知识入库  如果知识审批流的index=0，并且该flowNode的status为pass获passButBlock,则知识入库。此时发送消息给知识订阅者。此功能暂时不实现
      
      
      
      
    } 
	
	/**
	 * 发送消息
	 * @param sender 发送者
	 * @param receiverid 接收者id
	 * @param msg 消息内容
	 * @param request
	 */
	public void send(SystemUser sender,SystemUser receiver,String msg,String object){
		Map<String , ScriptSession> scriptmap=ChatManager.scriptmap;

		ScriptSession receiverscriptSession=null;
		   for (Map.Entry<String,ScriptSession> entry:scriptmap.entrySet()) {
			   ScriptSession scriptSession=(ScriptSession) entry.getValue();
			   String userid=(String)scriptSession.getAttribute("userid");
	             
	             if(userid.equals(String.valueOf(receiver.getId())))
	                {receiverscriptSession=scriptSession;break;}
          }
	        
	        if(null!=receiverscriptSession){
	        	
	        	Util util = new Util(receiverscriptSession);

//	        	util.setValue("messageListBt", "新审批消息");
	    		util.addFunctionCall("remaindNewMessage");

	        	
	        }
//	        else{System.out.println("用户不在线！");}
	
}

}