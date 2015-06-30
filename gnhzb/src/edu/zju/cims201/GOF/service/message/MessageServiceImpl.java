package edu.zju.cims201.GOF.service.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.proxy.dwr.Util;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.message.MessageDao;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.OperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.RolePrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.web.message.dwr.ChatManager;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Resource(name="messageDao")
	MessageDao mdao;
    
	
	public String delete(Long messageId) {
		// TODO Auto-generated method stub
		mdao.delete(messageId);
		mdao.flush();
		return "1";
	}

	
	public Message getMessage(Long messageId) {
		// TODO Auto-generated method stub
		Message k = mdao.findUniqueBy("id", messageId);
		return k;
	}

	
	public List<Message> listReceivedMessages(Long receiverId) {
	//	System.out.println("===========================");
		// TODO Auto-generated method stub
		String queryString="from Message m where m.receiver.id=? order by m.sendTime desc";
		List<Message> list=mdao.createQuery(queryString, receiverId).list();
		List<Message> mlist = new ArrayList<Message>();
		for(Message message:list){
			if(message.getMessageType().equals("askForAnswer")){
				if(message.getKnowledge().getIsvisible().equals(true)){
					mlist.add(message);
				}
			}else{
				mlist.add(message);
			}
		}		
		return mlist;
	}
	public List<Message> listKnowledgeMessages(Long receiverId) {
		// TODO Auto-generated method stub
		String queryString="from Message m where m.knowledge.id=? order by m.sendTime desc";
		List<Message> list=mdao.createQuery(queryString, receiverId).list();
		
		return list;
	}
	
	public List<Message> listReceivedMessages(Long receiverId,
			String messageType) {
		// TODO Auto-generated method stub
		List<Message> list;
		String	queryString;
		
		if(messageType.equals("notice"))
		   {queryString="from Message m where  m.messageType=? order by m.sendTime desc";
		   Query query=mdao.createQuery(queryString, messageType);
		   query.setMaxResults(Constants.NOTICE_LIMIT);
		    list=query.list();
		 }else if(messageType.equals("askForAnswer")){
			 queryString="from Message m where m.receiver.id=? and m.messageType=? and m.knowledge.isvisible=true order by m.sendTime desc";
			 list=mdao.createQuery(queryString, receiverId,messageType).list();
		 }else{queryString="from Message m where m.receiver.id=? and m.messageType=? order by m.sendTime desc";
		 list=mdao.createQuery(queryString, receiverId,messageType).list();
		   }

		return list;
	}

	
	public List<Message> listSendedMessages(Long senderId) {
		// TODO Auto-generated method stub
		String queryString="from Message m where m.sender.id=? order by m.sendTime desc";
		List<Message> list=mdao.createQuery(queryString, senderId).list();
		
		return list;
	}

	
	public List<Message> listSendedMessages(Long senderId, String messageType) {
		// TODO Auto-generated method stub
		String queryString="from Message m where m.sender.id=? and m.messgeType=? order by m.sendTime desc";
		List<Message> list=mdao.createQuery(queryString, senderId,messageType).list();
		
		return list;
	}

	
	@Transactional
	public String save(Message message) {
		// TODO Auto-generated method stub
		mdao.save(message);
		mdao.flush();
		return "1";
	}

	
	public List<Message> listUnreadedMessages(Long receiverId) {
		// TODO Auto-generated method stub
		String queryString="from Message m where m.receiver.id=? and m.isRead=false order by m.sendTime desc";
		List<Message> list=mdao.createQuery(queryString, receiverId).list();
		
		return list;
	}

	
	public List<Message> listMessages(Long receiverId) {
		// TODO Auto-generated method stub
		String queryString="from Message m where m.receiver.id=? and m.messageType<>'notice' order by m.sendTime desc";
		List<Message> list=mdao.createQuery(queryString, receiverId).list();
		List<Message> mlist = new ArrayList<Message>();
		for(Message message:list){
			if(message.getMessageType().equals("askForAnswer")){
				if(message.getKnowledge().getIsvisible().equals(true)){
					mlist.add(message);
				}
			}else{
				mlist.add(message);
			}
		}
		return mlist;
	}


	public String[] getMessagrProperty(Long messageId) {
		// TODO Auto-generated method stub
		Message m=getMessage(messageId);
		String[] properties=new String[4];
		properties[0]=m.getSender().getName();
		if(null!=m.getKnowledge())
		{properties[1]=String.valueOf(m.getKnowledge().getId());
		properties[2]=m.getKnowledge().getTitlename();
		properties[3]=m.getMessageType();
		}
		else {properties[1]="0";properties[2]="";properties[3]="";}
		return properties;
	}


	public boolean messageExit(SystemUser receiver, String messageType,MetaKnowledge knowledge) {


	
			Map<String,Object> params = new HashMap<String ,Object>();
			params.put("receiver", receiver);
			params.put("messageType", messageType);
			params.put("knowledge", knowledge);
			String hql = "from Message o where o.receiver= :receiver and o.messageType= :messageType and o.knowledge= :knowledge";
			Query query = mdao.createQuery(hql, params);
			List<Message> Messages = (ArrayList<Message>)query.list();
			if(Messages.size()!=0){
				return true;
			}else{
				return false;
			}
		
		
		
		
	}


	public void sendMessage(SystemUser sender, SystemUser receiver, String msg,
			String object) {
		Map<String , ScriptSession> scriptmap=ChatManager.scriptmap;
		System.out.println(scriptmap.size()+"receiverid"+receiver.getId());

		ScriptSession receiverscriptSession=null;
		   for (Map.Entry<String,ScriptSession> entry:scriptmap.entrySet()) {
			   ScriptSession scriptSession=(ScriptSession) entry.getValue();
			   String userid=(String)scriptSession.getAttribute("userid");
	             System.out.println("scriptSession_userid="+userid);
	             if(userid.equals(receiver.getId().toString()))
	                {receiverscriptSession=scriptSession;break;}
	      }
	        
	        if(null!=receiverscriptSession){
	        	
	        	String[] msgs=new String[2];
	        	msgs[0]=msg;
	        	
	        	Util util = new Util(receiverscriptSession);
	    		util.setValue("messageListBt", "新消息");
	    		util.addFunctionCall("remaindNewMessage");

	        	
	        }
	        else{System.out.println("用户不在线！");}
		
	}


	public List<Message> listUnAnsweredKnowledgeMessage(Long knowledgeId) {
		String queryString="from Message m where m.knowledge.id=? and m.isAnswered=false and m.messageType='applymodifydomainnode' order by m.sendTime desc";
		List<Message> list=mdao.createQuery(queryString, knowledgeId).list();
		
		return list;
	}


	public List<Message> listUnAnsweredKnowledgeMessageByUserId(Long userId) {
		
		String queryString="from Message m where m.receiver.id=? and m.isAnswered=false and m.messageType='applymodifydomainnode' order by m.sendTime desc";
		List<Message> list=mdao.createQuery(queryString, userId).list();
		
		return list;
	}


	//到了搜索不到这里的！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！是否显示审批改域按钮
	public List<Message> listUnAnsweredKnowledgeMessageByUserIdAndKnowledgeId(
			Long userId, Long knowledgeId) {
		String queryString="from Message m where m.receiver.id=? and m.knowledge.id=? and m.isAnswered=false and m.messageType='applymodifydomainnode' order by m.sendTime desc";
		List<Message> list=mdao.createQuery(queryString, userId,knowledgeId).list();
		
		return list;
	}


	public void deleteMessage(MetaKnowledge m) {
		
		List<Message> mks = this.mdao.findBy("knowledge", m);
		for(Message me : mks){
			this.mdao.delete(me);			
		}
		
	}




	
	

}
