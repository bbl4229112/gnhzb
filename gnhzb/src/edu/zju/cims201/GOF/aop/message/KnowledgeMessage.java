
package edu.zju.cims201.GOF.aop.message;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.stereotype.Service;

import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.Expert;
import edu.zju.cims201.GOF.hibernate.pojo.InterestModel;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.interestmodel.InterestModelServiceImpl;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeServiceImpl;
import edu.zju.cims201.GOF.service.message.MessageServiceImpl;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.web.message.dwr.ChatManager;

/**
 * @author ljk
 *
 */


@Aspect @Service 
public class KnowledgeMessage {

//	@Resource(name="commonDao")
//	CommonDao dao;
//	@Resource(name="interestModelServiceImpl")
//	InterestModelServiceImpl interestModelServiceImpl;
//	@Resource(name="messageServiceImpl")
//	MessageServiceImpl messageServiceImpl;
//	@Resource(name="knowledgeServiceImpl")
//	KnowledgeServiceImpl knowledgeServiceImpl;


//	@After("execution(* edu.zju.cims201.GOF.service.knowledge.FullTextServiceImpl.indexKnowledge(edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge))") 
//    public void doAfterIndexKnowledge(JoinPoint jp) {
//		 Object[] args = jp.getArgs();
//	     MetaKnowledge knowledge = (MetaKnowledge)args[0];
//		 saveMessage(knowledge);
//	}
//	
//	@After("execution(* edu.zju.cims201.GOF.service.knowledge.KnowledgeServiceImpl.save(edu.zju.cims201.GOF.hibernate.pojo.Knowledge,java.lang.Long))")
//	 public void doAfterSaveAvidmAndQualityKnowledge(JoinPoint jp) {
//		 Object[] args = jp.getArgs();
//		 System.out.println("++++++++++++++++++++++++++++开始");
//	     System.out.println("======================"+args[0]);
//	     Knowledge rknowledge = (Knowledge)args[0];
//	     
//	     MetaKnowledge knowledge = knowledgeServiceImpl.getMetaknowledge(rknowledge.getId());   
//		 System.out.println("-------message_saveKnowledge"+"ktype"+knowledge.getKtype().getName()); 
//		 if(knowledge.getKtype().getName().equals("Avidmknowledge")||knowledge.getKtype().getName().equals("Qualityknowledge")){
//			 saveMessage(knowledge);
//		 }		 
//	}
//	
//	public void saveMessageAndSubscribeInfo(Knowledge knowledge) {
//
//	 SystemUser sender=null;
//	 SystemUser receiver=null;
//	 Date date=new Date();
//	 sender=knowledge.getUploader();
//	
//	//如果知识类型是问题，则向专家发送求解消息----------------------------------------------暂时不要求
//	if(knowledge.getKtype().getName().equals("Question"))
//	 {
//	    
//	    
//        Iterator it=knowledge.getDomainnode().getExperts().iterator();
//    	while(it.hasNext())
//	     {Message message=new Message();
//	        message.setIsRead(false);	        
//	        message.setIsAnswered(false);	        
//	        message.setKnowledge(knowledge);
//	        message.setSender(sender);
//	        message.setMessageType("askForAnswer");
//	        message.setSendTime(date);
//	        message.setContent("向你发出了问题解答请求");
//	        Expert expert=(Expert)it.next();
//            receiver=expert.getUser();
//           message.setReceiver(receiver);
//	       dao.save(message);
//	       send(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息
//
//	     }
//	 }
//	
//
//	//不是‘问题’，则发送知识预定message
//	else{
//	//发送知识预定消息
//       List<SystemUser> subscribers=new ArrayList<SystemUser>();
//	
//			
//	  
//	       //根据关键词预定发送消息
//	    	if(knowledge.getKeywords()!=null)
//	    	{
//	          Iterator it=knowledge.getKeywords().iterator();
//	    	  while(it.hasNext())
//		       {  Keyword keyword=(Keyword)it.next();		       
//		        subscribers=interestModelServiceImpl.getsubscribers(keyword.getId().toString(), "keyword");
//		             if(subscribers!=null)
//		                { Iterator it_=subscribers.iterator();
//		  	    	       while(it_.hasNext())
//				             { receiver=(SystemUser)it_.next();
//				             
//				              Message message=new Message();
//							    message.setIsRead(false);
//							    message.setIsAnswered(false);	
//							    message.setKnowledge(knowledge);
//							    message.setSender(sender);
//							    message.setMessageType("subscribeknowledge");
//							    message.setSendTime(date);
//						        
//				               message.setReceiver(receiver);
//						       message.setContent("关键词【"+keyword.getKeywordName()+"】");
//						       if(!messageServiceImpl.messageExit( receiver, message.getMessageType(), knowledge))
//						       dao.save(message);
//						       
//						       send(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息					      
//						       
//						       //未读的计数
//						       if(interestModelServiceImpl.isInterestModelExist(receiver, keyword.getId().toString(), "keyword")){						    	   
//						    	   InterestModel im = interestModelServiceImpl.getCommonInterestModel(receiver, keyword.getId().toString(), "keyword",0);
////						    	   Set<Knowledge> ks = new HashSet<Knowledge>();
////						    	   ks.add(knowledge);
////						    	   im.setKnowledges(ks);
//								   im.setUnRead(im.getUnRead()+1);
//								   interestModelServiceImpl.updateInterestModelItems(im);
//						       }					      
//				             }
//		        	 
//		                }
//	         
//		
//		         }
//		
//	         }
//	    	//根据作者预定发送消息
//		    subscribers=interestModelServiceImpl.getsubscribers(knowledge.getUploader().getId().toString(), "author");
//		    System.out.println("上传者id："+knowledge.getUploader().getId().toString());
//            if(subscribers!=null){
//		       Iterator it_user=subscribers.iterator();
//	    	   while(it_user.hasNext())
//	    	    {
//	    		   receiver=(SystemUser)it_user.next();
//	    		   
//	    		    Message message=new Message();
//				    message.setIsRead(false);
//				    message.setIsAnswered(false);	
//				    message.setKnowledge(knowledge);
//				    message.setSender(sender);
//				    message.setMessageType("subscribeknowledge");
//				    message.setSendTime(date);
//	                message.setReceiver(receiver);
//			        message.setContent("上传者【"+sender.getName()+"】");
//			        
//			        if(!messageServiceImpl.messageExit( receiver, message.getMessageType(), knowledge))
//			        dao.save(message);
//			       
//			       send(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息
//			       //未读的计数
//			       if(interestModelServiceImpl.isInterestModelExist(receiver, knowledge.getUploader().getId().toString(), "author")){						    	   
//			    	   InterestModel im = interestModelServiceImpl.getCommonInterestModel(receiver, knowledge.getUploader().getId().toString(), "author",0);
////			    	   Set<Knowledge> ks = new HashSet<Knowledge>();
////			    	   ks.add(knowledge);
////			    	   im.setKnowledges(ks);
//					   im.setUnRead(im.getUnRead()+1);
//					   interestModelServiceImpl.updateInterestModelItems(im);
//			       }
//
//	    	    }
//            }
//            
//	    	//根据关注的领域树节点发送消息
//		    subscribers=interestModelServiceImpl.getsubscribers(knowledge.getDomainnode().getId().toString(), "domain");
//		    System.out.println("knowledge.getDomainnode().getId().toString()："+knowledge.getDomainnode().getId().toString());
//
//		    if(subscribers!=null){
//			       Iterator it_treeNode=subscribers.iterator();
//		    	   while(it_treeNode.hasNext())
//		    	    {
//		    		   receiver=(SystemUser)it_treeNode.next();
//		    		   
//		    		   Message message=new Message();
//					    message.setIsRead(false);
//					    message.setIsAnswered(false);	
//					    message.setKnowledge(knowledge);
//					    message.setSender(sender);
//					    message.setMessageType("subscribeknowledge");
//					    message.setSendTime(date);
//		               message.setReceiver(receiver);
//				       message.setContent("领域【"+knowledge.getDomainnode().getNodeName()+"】");
//				       
//				       if(!messageServiceImpl.messageExit( receiver, message.getMessageType(), knowledge))
//				       dao.save(message);
//				       
//				       send(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息
//				       //未读的计数
//				       if(interestModelServiceImpl.isInterestModelExist(receiver, knowledge.getDomainnode().getId().toString(), "domain")){					    	   
//				    	   List<InterestModel> ims = interestModelServiceImpl.getInterestModels(receiver, knowledge.getDomainnode().getId().toString(), "domain");
//				    	   for(InterestModel im:ims){
////				    		   Set<Knowledge> ks = new HashSet<Knowledge>();
////					    	   ks.add(knowledge);
////					    	   im.setKnowledges(ks);
//							   im.setUnRead(im.getUnRead()+1);
//							   interestModelServiceImpl.updateInterestModelItems(im);
//				    	   }
//				    	 
//				       }
//
//		    	    }
//	            }
//	    	
//		  //根据关注的多维分类树节点发送消息
//		    Iterator it=knowledge.getCategories().iterator();
//		    while(it.hasNext())
//		    {CategoryTreeNode ctNode=(CategoryTreeNode)it.next();
//		    subscribers=interestModelServiceImpl.getsubscribers(ctNode.getId().toString(), "category");
//		    System.out.println("ctNode.getId().toString()"+ctNode.getId().toString());
//
//		    if(subscribers!=null){
//			       Iterator it_treeNode=subscribers.iterator();
//		    	   while(it_treeNode.hasNext())
//		    	    {
//		    		   receiver=(SystemUser)it_treeNode.next();		    		  
//		    		   
//		    		   Message message=new Message();
//					    message.setIsRead(false);
//					    message.setIsAnswered(false);	
//					    message.setKnowledge(knowledge);
//					    message.setSender(sender);
//					    message.setMessageType("subscribeknowledge");
//					    message.setSendTime(date);
//		               message.setReceiver(receiver);
//				       message.setContent("多维分类【"+ctNode.getNodeName()+"】");
//				       
//				       if(!messageServiceImpl.messageExit( receiver, message.getMessageType(), knowledge))
//				       dao.save(message);
//				       
//				       send(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息
//				       //未读的计数
//				       if(interestModelServiceImpl.isInterestModelExist(receiver, ctNode.getId().toString(), "category")){	
//				    	   
//				    	   List<InterestModel> ims = interestModelServiceImpl.getInterestModels(receiver, ctNode.getId().toString(), "category");
//				    	   for(InterestModel im:ims){
////				    		   Set<Knowledge> ks = new HashSet<Knowledge>();
////					    	   ks.add(knowledge);
////					    	   im.setKnowledges(ks);
//							   im.setUnRead(im.getUnRead()+1);
//							   interestModelServiceImpl.updateInterestModelItems(im);
//				    	   }
//
//				       }
//
//		    	    }
//		    }
//		    }
//		   
//		    }
//	
//    
//    } 
//	
//	
//
//
//
///**
// * 发送消息
// * @param sender 发送者
// * @param receiverid 接收者id
// * @param msg 消息内容
// * @param request
// */
//public void send(SystemUser sender,SystemUser receiver,String msg,String object){
//	Map<String , ScriptSession> scriptmap=ChatManager.scriptmap;
//	System.out.println(scriptmap.size()+"receiverid"+receiver.getId());
//
//	ScriptSession receiverscriptSession=null;
//	   for (Map.Entry<String,ScriptSession> entry:scriptmap.entrySet()) {
//		   ScriptSession scriptSession=(ScriptSession) entry.getValue();
//		   String userid=(String)scriptSession.getAttribute("userid");
//             System.out.println("scriptSession_userid="+userid);
//             if(userid.equals(receiver.getId().toString()))
//                {receiverscriptSession=scriptSession;break;}
//      }
//        
//        if(null!=receiverscriptSession){
//        	
//        	String[] msgs=new String[2];
//        	msgs[0]=msg;
//        	
//        	Util util = new Util(receiverscriptSession);
//    		util.setValue("messageListBt", "新消息");
//    		util.addFunctionCall("remaindNewMessage");
//
//        	
//        }
//        else{System.out.println("用户不在线！");}
//
//}
	
	
	
	
	
	
	
	
}
