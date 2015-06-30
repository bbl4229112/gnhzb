package edu.zju.cims201.GOF.web.message;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.stringtree.json.JSONWriter;

import com.sun.org.apache.commons.beanutils.BeanUtils;

import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.MessageDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.comment.CommentService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.web.CrudActionSupport;


//定义URL映射对应/message/message.action
@Namespace("/message")
//定义名为reload的result重定向到message.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "message.action", type = "redirect") })
public class MessageAction extends CrudActionSupport<Message> implements ServletResponseAware{
	@Resource(name="userServiceImpl")
	private UserService userService;
	@Resource(name="messageServiceImpl")
	private MessageService messageService;
	@Resource(name="commentServiceImpl")
	private CommentService commentService;
	@Resource(name="knowledgeServiceImpl")
	private KnowledgeService ks;
	private static final long serialVersionUID = 8683878162525847045L;

	private Message entity;
	private Long id;
	private String content;//消息内容
	private String messageType;//消息类型
	private Long isRead;
	private String sendTime;
	
	private HttpServletResponse response;
	
	private int size;
	private int index;
	
	
	/**
	 * 删除消息
	 * 
	 */
	
	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		messageService.delete(id);
		addActionMessage("消息删除成功！"+id);
		return null;
	}
	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		
	
		
		
		return null;
	}

	
	/**
	 * 所有消息列表【除了notice】
	 */
	@Override
	
	public String list() throws Exception {
		PageDTO pd = new PageDTO();
		SystemUser user=userService.getUser();
		List<Message> messageList=messageService.listMessages(user.getId());
		List<MessageDTO> messageListDTO=new ArrayList<MessageDTO>();
		for(Message m:messageList)
		  {MessageDTO mDTO=new MessageDTO();
		   BeanUtils.copyProperties(mDTO, m);
		   mDTO.setReceiver(m.getReceiver().getName());
		   if(m.getMessageType().equals("comment"))
			   mDTO.setMessageType("知识评论");
		   else if(m.getMessageType().equals("ApprovalFlow"))
			   mDTO.setMessageType("知识审批");
		   else if(m.getMessageType().equals("BorrowFlow"))
			   mDTO.setMessageType("借阅审批");
		   else if(m.getMessageType().equals("notice"))
			   mDTO.setMessageType("系统公告");
		   else if(m.getMessageType().equals("askForAnswer"))
			   mDTO.setMessageType("问题求解");
		   else if(m.getMessageType().equals("subscribeknowledge"))
			   mDTO.setMessageType("知识订阅");
		   else if(m.getMessageType().equals("recommendknowledge"))
			   mDTO.setMessageType("知识推荐");
		   else if(m.getMessageType().equals("applymodifydomainnode"))
				   mDTO.setMessageType("申请改域");
		   else if(m.getMessageType().equals("applymodifydomainnoderesult"))
			   mDTO.setMessageType("申请改域结果");
		   mDTO.setSendTime(m.getSendTime().toString().substring(0, 16));

		   //mDTO.setSendTime(m.getSendTime().toLocaleString());


		   mDTO.setSender(m.getSender().getName()+"("+m.getSender().getEmail()+")");
		  if(null!=m.getKnowledge())
				{mDTO.setKnowledgeName(m.getKnowledge().getTitlename());
				 mDTO.setKnowledgeid(m.getKnowledge().getId());
				
				}
		   mDTO.setLinkedurl("");
		   
		   if(m.getIsRead())
			   {mDTO.setIsRead(1L);}
		   else{mDTO.setIsRead(0L);}
		   
		   if(m.getMessageType().equals("askForAnswer")) {
			   if(m.getIsAnswered())
		        {mDTO.setIsAnswered(1L);}
		        else {mDTO.setIsAnswered(0L);} 
		   }		   
		   
		   messageListDTO.add(mDTO);
		  }
		
		
//System.out.println("size__"+size+"index--"+index);
		
		List<MessageDTO> subList = new ArrayList<MessageDTO>();
		
		for(int i=index*size;i<((index+1)*size<messageListDTO.size()?(index+1)*size:messageListDTO.size());i++){
			subList.add(messageListDTO.get(i));
		}
		
//		System.out.println("list_mDTO size:"+messageListDTO.size()+"_"+subList.size());
		
		pd.setData(subList);
		pd.setTotal(messageListDTO.size());
		
		int totalPage;
		if(size != 0) {
			if(messageListDTO.size()%size == 0){
				totalPage = messageListDTO.size()/size;
			}else{
				totalPage = messageListDTO.size()/size+1;
			}
			pd.setTotalPage(messageListDTO.size()/size+1);
			
		}		
		
        PrintWriter out = this.response.getWriter();
		JSONWriter jw = new JSONWriter();
		String str = jw.write(pd);
		out.println(str);
		
	   
		
		

		return null;
	}
	
	/**
	 * list用户未读消息列表listUnreadedMessagesForApprove
	 * @return
	 * @throws Exception
	 */
	
	public String listUnreadedMessages()throws Exception{
		PageDTO pd = new PageDTO();
		SystemUser user=userService.getUser();
		List<Message> messageList=messageService.listUnreadedMessages(user.getId());
		List<MessageDTO> messageListDTO=new ArrayList<MessageDTO>();
		for(Message m:messageList)
		  {MessageDTO mDTO=new MessageDTO();
		   BeanUtils.copyProperties(mDTO, m);
		   mDTO.setReceiver(m.getReceiver().getName());
		   mDTO.setSender(m.getSender().getName()+"("+m.getSender().getName()+")");
		  if(null!=m.getKnowledge())
				{mDTO.setKnowledgeName(m.getKnowledge().getTitlename());
				 mDTO.setKnowledgeid(m.getKnowledge().getId());
				}
		   mDTO.setLinkedurl("");
			
		   messageListDTO.add(mDTO);
		  }
		
		
//	System.out.println("size__"+size+"index--"+index);
		
		List<MessageDTO> subList = new ArrayList<MessageDTO>();
		
		for(int i=index*size;i<((index+1)*size<messageListDTO.size()?(index+1)*size:messageListDTO.size());i++){
			subList.add(messageListDTO.get(i));
		}
		
//		System.out.println("list_mDTO size:"+messageListDTO.size()+"--sublistsize"+subList.size());
		
		pd.setData(subList);
		pd.setTotal(messageListDTO.size());
		
		int totalPage;
		if(size != 0) {
			if(messageListDTO.size()%size == 0){
				totalPage = messageListDTO.size()/size;
			}else{
				totalPage = messageListDTO.size()/size+1;
			}
			pd.setTotalPage(messageListDTO.size()/size+1);
			
		}		
		
		
        PrintWriter out = this.response.getWriter();
		JSONWriter jw = new JSONWriter();
		String str = jw.write(subList);
		out.println(str);
		return null;
	}
	/**
	 * list审批变更知识域时的消息
	 * @return
	 * @throws Exception
	 */
	
	public String listUnreadedMessagesForApprove()throws Exception{
		PageDTO pd = new PageDTO();
		SystemUser user=userService.getUser();
		List<Message> messageList=messageService.listUnreadedMessages(user.getId());
		List<MessageDTO> messageListDTO=new ArrayList<MessageDTO>();
		for(Message m:messageList)
		  {MessageDTO mDTO=new MessageDTO();
		   BeanUtils.copyProperties(mDTO, m);
		   mDTO.setReceiver(m.getReceiver().getName());
		   mDTO.setSender(m.getSender().getName()+"("+m.getSender().getName()+")");
		  if(null!=m.getKnowledge())
				{mDTO.setKnowledgeName(m.getKnowledge().getTitlename());
				 mDTO.setKnowledgeid(m.getKnowledge().getId());
				}
		   mDTO.setLinkedurl("");
			
		   messageListDTO.add(mDTO);
		  }
		
		
//	System.out.println("size__"+size+"index--"+index);
		
		List<MessageDTO> subList = new ArrayList<MessageDTO>();
		
		for(int i=index*size;i<((index+1)*size<messageListDTO.size()?(index+1)*size:messageListDTO.size());i++){
			subList.add(messageListDTO.get(i));
		}
		
//		System.out.println("list_mDTO size:"+messageListDTO.size()+"--sublistsize"+subList.size());
		
		pd.setData(subList);
		pd.setTotal(messageListDTO.size());
		
		int totalPage;
		if(size != 0) {
			if(messageListDTO.size()%size == 0){
				totalPage = messageListDTO.size()/size;
			}else{
				totalPage = messageListDTO.size()/size+1;
			}
			pd.setTotalPage(messageListDTO.size()/size+1);
			
		}		
		
		
        PrintWriter out = this.response.getWriter();
		JSONWriter jw = new JSONWriter();
		String str = jw.write(subList);
		out.println(str);
		return null;
	}
	
	
	/**
	 * 根据消息类型列出所有消息，如果消息类型是notice【公告】则只返回消息的content和sendTime
	 * @return
	 * @throws Exception
	 */
	public String listTypeMessages()throws Exception{
		
		PageDTO pd = new PageDTO();
		SystemUser user=userService.getUser();
		List<Message> messageList=messageService.listReceivedMessages(user.getId(), messageType);
	//	System.out.println("********************************+"+messageList.size());
		List<MessageDTO> messageListDTO=new ArrayList<MessageDTO>();
		for(Message m:messageList)
		  {MessageDTO mDTO=new MessageDTO();
		  
		  //如果是公告 则只列消息内容和发送时间   
		  if(messageType.equals("notice")){
			  mDTO.setContent(m.getContent());
			  mDTO.setSendTime(m.getSendTime().toLocaleString());
		       }
		  else{
		      BeanUtils.copyProperties(mDTO, m);
		      mDTO.setReceiver(m.getReceiver().getName());
			  mDTO.setSendTime(m.getSendTime().toLocaleString());
			   if(m.getMessageType().equals("comment"))
				   mDTO.setMessageType("知识评论");
			   else if(m.getMessageType().equals("ApprovalFlow"))
				   mDTO.setMessageType("知识审批");
			   else if(m.getMessageType().equals("notice"))
				   mDTO.setMessageType("系统公告");
			   else if(m.getMessageType().equals("askForAnswer"))
				   mDTO.setMessageType("问题求解");
			   else if(m.getMessageType().equals("subscribeknowledge"))
				   mDTO.setMessageType("知识订阅");
			   else if(m.getMessageType().equals("recommendknowledge"))
				   mDTO.setMessageType("知识推荐");
		      
		      mDTO.setSender(m.getSender().getName()+"("+m.getSender().getEmail()+")");
		        if(null!=m.getKnowledge())
				   {mDTO.setKnowledgeName(m.getKnowledge().getTitlename());
				   mDTO.setKnowledgeid(m.getKnowledge().getId());}
		      
		        if(m.getIsRead())
		              {mDTO.setIsRead(1L);}
                 else {mDTO.setIsRead(0L);} 
		        
		        if(m.getMessageType().equals("askForAnswer")) {
					   if(m.getIsAnswered())
				        {mDTO.setIsAnswered(1L);}
				        else {mDTO.setIsAnswered(0L);} 
				   }       
		        
              mDTO.setLinkedurl("");
		       }
		  
		   messageListDTO.add(mDTO);
		  }
//		System.out.println("size__"+size+"index--"+index);
		
		List<MessageDTO> subList = new ArrayList<MessageDTO>();
		
		for(int i=index*size;i<((index+1)*size<messageListDTO.size()?(index+1)*size:messageListDTO.size());i++){
			subList.add(messageListDTO.get(i));
		}
		
	//	System.out.println("list_mDTO size:"+messageListDTO.size()+"_"+subList.size());
		
		pd.setData(subList);
		pd.setTotal(messageListDTO.size());
		
		int totalPage;
		if(size != 0) {
			if(messageListDTO.size()%size == 0){
				totalPage = messageListDTO.size()/size;
			}else{
				totalPage = messageListDTO.size()/size+1;
			}
			pd.setTotalPage(messageListDTO.size()/size+1);
			
		}		
		
	
        PrintWriter out = this.response.getWriter();
		JSONWriter jw = new JSONWriter();
		String str = jw.write(pd);
		out.println(str);
		return null;
	}
	
	

	
	
	/**
	 * 保存公告
	 * @return
	 * @throws Exception
	 */
	public String saveNotice() throws Exception{
		SystemUser sender=userService.getUser();
		Message message=new Message();
		message.setContent(content);
		message.setIsRead(true);
		message.setMessageType("notice");
		message.setSender(sender);
		message.setReceiver(sender);//这里把系统公告receiver设成发送者本人，实际不起作用
		message.setSendTime(new Date());
		messageService.save(message);
		addActionMessage("系统公告发布成功");
		
		return null;
	}
	
	public String markAsReaded()throws Exception{
		if(null!=id)
		{Message m=messageService.getMessage(id);
		m.setIsRead(true);
		messageService.save(m);	
		}		
		return null;
	}

	
	@Override
	protected void prepareModel() throws Exception {
		if(this.id!=null){
			entity=messageService.getMessage(this.id);
		}
	}
	@Override
	public String save() throws Exception {
		
		return null;
	}
	

	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}
	
	public Message getModel() {
		// TODO Auto-generated method stub
		return this.entity;
	}
	
	public String messageListInput() throws Exception {
		// TODO Auto-generated method stub
		return "list";
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Long getIsRead() {
		return isRead;
	}
	public void setIsRead(Long isRead) {
		this.isRead = isRead;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public Message getEntity() {
		return entity;
	}
	public void setEntity(Message entity) {
		this.entity = entity;
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

}