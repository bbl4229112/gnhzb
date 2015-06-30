package edu.zju.cims201.GOF.aop.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.stereotype.Service;

import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorList;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Vote;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.comment.CommentService;
import edu.zju.cims201.GOF.service.logging.AddUserScore;
import edu.zju.cims201.GOF.service.logging.SysBehaviorListService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.web.message.dwr.ChatManager;

@Aspect @Service 
public class CommentMessage {

	
	@Resource(name="messageServiceImpl")
	MessageService messageService;
	@Resource(name="sysBehaviorLogServiceImpl")
	SysBehaviorLogService sysBehaviorLogService;
	@Resource(name="sysBehaviorListServiceImpl")
	SysBehaviorListService sysBehaviorListService;
	@Resource(name="addUserScore")
	AddUserScore addUserScore;
	@Resource(name = "userServiceImpl")
	private UserService userservice;
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice;
	@Resource(name="commentServiceImpl")
	private CommentService commentservice;
	/**
	 * 拦截addComment动作，发送消息给被评论者
	 * @param jp
	 */
	@After("execution(* edu.zju.cims201.GOF.service.knowledge.comment.CommentServiceImpl.addComment(edu.zju.cims201.GOF.hibernate.pojo.Comment))") 
    public void doAfterComment(JoinPoint jp) {      
	
    Object[] args = jp.getArgs();
 
      Comment comment=(Comment)args[0];
      SystemUser sender=comment.getCommenter();
      SystemUser receiver=comment.getKnowledge().getUploader();
      Message message=new Message();
      message.setIsRead(false);
      message.setKnowledge(comment.getKnowledge());
      message.setSender(sender);
      message.setReceiver(receiver);//发送消息通知知识的上传者
      message.setMessageType("comment");
      message.setSendTime(comment.getCommmentTime());
      
      if(comment.getKnowledge().getKtype().getName().equals("Question")){
    	  message.setContent("回答了你的问题");
      }else{
    	  message.setContent("评论了你的知识");
      }     
      
      if(sender.getId()!=receiver.getId())//如果评论者就是知识上传者 则不发送通知给知识上传者
         { 
    	  messageService.save(message);
          send(sender,receiver,message.getContent(),comment.getKnowledge().getTitlename());//发送消息
         }
      
      if(comment.getCommented()!=null)//如果是对评论a的评论，则同时通知评论a的作者
         {receiver=comment.getCommented().getCommenter();
         Message message2=new Message();
         message2=message;
         message2.setReceiver(receiver);
         if(sender.getId()!=receiver.getId())//如果是对自己评论的回复，则不发送通知
            {
        	 message2.setContent("回复了你的评论");
             messageService.save(message2);
             
             send(sender,receiver,message.getContent(),comment.getKnowledge().getTitlename());//发送消息
            }

         //记录comment行为到SysBehaviorLog表
         SysBehaviorLog bLog = new SysBehaviorLog();
   		bLog.setObjectid(comment.getKnowledge().getId());
   		bLog.setActionTime(comment.getCommmentTime());
   		bLog.setObjectType("knowledge");
   		bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(5L));
   		bLog.setUser(comment.getCommenter());
   		sysBehaviorLogService.save(bLog);

   		//累加用户积分
   		addUserScore.addUserScore(comment.getCommenter(),5L);
         
         }
      else{
      //记录comment行为到SysBehaviorLog表
      SysBehaviorLog bLog = new SysBehaviorLog();
		bLog.setObjectid(comment.getKnowledge().getId());
		bLog.setActionTime(comment.getCommmentTime());
		bLog.setObjectType("knowledge");
		bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(4L));
		bLog.setUser(comment.getCommenter());
		sysBehaviorLogService.save(bLog);

		//累加用户积分
		addUserScore.addUserScore(comment.getCommenter(),4L);
      }
    } 
	

	@Before("execution(* edu.zju.cims201.GOF.service.knowledge.comment.CommentServiceImpl.deleteComments(..)) ") 
    public void doBeforedeleteComments(JoinPoint jp) {      
  // System.out.println("addvote+++++++++++++++++++++++++++++++++++++++++");
	
    Object[] args = jp.getArgs();//[user,comment,vote]
    
    Long commentid=(Long)args[0];
  
   Comment comment=commentservice.getComment(commentid);
  
      //记录vote行为到SysBehaviorLog表
        SysBehaviorLog bLog = new SysBehaviorLog();
		bLog.setObjectid(commentid);
		bLog.setActionTime(new Date());
		bLog.setObjectType("comment");
		bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(19L));
		bLog.setUser(comment.getCommenter());
		sysBehaviorLogService.save(bLog);

		//累加用户积分
		addUserScore.addUserScore(comment.getCommenter(),19L);
     
    
    } 
	
	@After("execution(* edu.zju.cims201.GOF.service.knowledge.comment.CommentServiceImpl.rate(..)) ") 
    public void doAfterRate(JoinPoint jp) {      
  // System.out.println("addvote+++++++++++++++++++++++++++++++++++++++++");
	
    Object[] args = jp.getArgs();//[user,comment,vote]
    
    Long userid=(Long)args[0];
    Long knowledgeID=(Long)args[1];
      SystemUser sender=userservice.getUser(userid);
    MetaKnowledge k=kservice.getMetaknowledge(knowledgeID);
      SystemUser receiver=k.getUploader();
      if(sender.getId()!=receiver.getId())
      {
       String isSupport=""; 
    
       
      Message message=new Message();
      message.setIsRead(false);
      message.setKnowledge(k);
      message.setSender(sender);
      message.setReceiver(receiver);
      message.setMessageType("vote");
      message.setSendTime(new Date());

      message.setContent("对您的知识评分");
    		  
      messageService.save(message);
      send(sender,receiver,message.getContent(),k.getTitlename());//发送消息

      //记录vote行为到SysBehaviorLog表
      SysBehaviorLog bLog = new SysBehaviorLog();
		bLog.setObjectid(k.getId());
		bLog.setActionTime(new Date());
		bLog.setObjectType("knowledge");
		bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(8L));
		bLog.setUser(sender);
		sysBehaviorLogService.save(bLog);

		//累加用户积分
		addUserScore.addUserScore(sender,8L);
      }
    
    } 
	
	@After("execution(* edu.zju.cims201.GOF.service.knowledge.comment.CommentServiceImpl.addVote(..)) ") 
    public void doAfterVote(JoinPoint jp) {      
//   System.out.println("addvote+++++++++++++++++++++++++++++++++++++++++");
	
    Object[] args = jp.getArgs();//[user,comment,vote]
    
    Vote vote=(Vote)args[0];
      SystemUser sender=vote.getUser();
      SystemUser receiver=vote.getComment().getCommenter();
      if(sender.getId()!=receiver.getId())
      {
       String isSupport=""; 
       if(vote.getIsSupport())
          {isSupport="支持了你的评论";}
       else{isSupport="反对了你的评论";}
       
      Message message=new Message();
      message.setIsRead(false);
      message.setKnowledge(vote.getComment().getKnowledge());
      message.setSender(sender);
      message.setReceiver(receiver);
      message.setMessageType("vote");
      message.setSendTime(vote.getVoteTime());

      message.setContent(isSupport);
    		  
      messageService.save(message);
      send(sender,receiver,message.getContent(),vote.getComment().getKnowledge().getTitlename());//发送消息

      //记录vote行为到SysBehaviorLog表
      SysBehaviorLog bLog = new SysBehaviorLog();
		bLog.setObjectid(vote.getComment().getKnowledge().getId());
		bLog.setActionTime(vote.getVoteTime());
		bLog.setObjectType("knowledge");
		bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(6L));
		bLog.setUser(vote.getUser());
		sysBehaviorLogService.save(bLog);

		//累加用户积分
		addUserScore.addUserScore(vote.getUser(),6L);
      }
    
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
	        	String[] msgs=new String[2];
	        	msgs[0]=msg;
	        	
	        	Util util = new Util(receiverscriptSession);
	    		util.setValue("messageListBt", "新消息");
	    		util.addFunctionCall("remaindNewMessage");

	        	
	        }
//	        else{System.out.println("用户不在线！");}

	}
}
