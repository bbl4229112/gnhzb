package edu.zju.cims201.GOF.web.message.dwr;


import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;



import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.stringtree.json.JSONWriter;

import com.sun.org.apache.commons.beanutils.BeanUtils;

import edu.zju.cims201.GOF.hibernate.pojo.InterestModel;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.rs.dto.MessageDTO;
import edu.zju.cims201.GOF.service.interestmodel.InterestModelService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.HibernatePorxy;
import edu.zju.cims201.GOF.util.JSONUtil;


//import com.leopard.web.util.SingletonSocket;

/**
 * 处理聊天相关
 * 
 * @author leopard
 * 
 */

@Component
public class ChatManager {
	@Resource(name="userServiceImpl")
	private  UserService userService;
	@Resource(name="messageServiceImpl")
	private  MessageService messageService;
	@Resource(name="interestModelServiceImpl")
	private  InterestModelService interestModelService;

	/** 保存当前在线用户列表 */
	public static Set<UserDTO> users = new HashSet<UserDTO>();
	/** 保存当前的scriptsession*/  //httpsession.id,scriptSession
	public static Map<String , ScriptSession> scriptmap = new HashMap<String , ScriptSession>();
	/** 保存当前的user和session对应*/ //user.id,scriptSession
	public static Map<String , ScriptSession> userScriptMap = new HashMap<String , ScriptSession>();

  public String listUnreadMessage(){
	  
//	  Integer index=Integer.parseInt(index_s);
//	  Integer size=Integer.parseInt(size_s);
//		PageDTO pd = new PageDTO();
	  List<MessageDTO> list_mDTO=new ArrayList<MessageDTO>();
	    String userName=SecurityContextHolder.getContext().getAuthentication().getName();//得到当前登录的用户名
		if(null!=userService.getUser(userName)){
			SystemUser user=(SystemUser)userService.getUser(userName);
			List<Message> list_message=messageService.listUnreadedMessages(user.getId());
			List<Message> sublist_message=new ArrayList<Message>();
			if(list_message.size()>10)
			{
				sublist_message=list_message.subList(0,10);
			}
			else{sublist_message=list_message;}
			for(Message m:sublist_message){
				MessageDTO mDTO=new MessageDTO();
				String[] properties=messageService.getMessagrProperty(m.getId()); 
				mDTO.setId(m.getId());
				mDTO.setSender(properties[0]);
				//mDTO.setMessageType(m.getMessageType());
				mDTO.setKnowledgeid(Long.valueOf(properties[1]));
				mDTO.setKnowledgeName(properties[2]);
				if("applymodifydomainnode".equals(m.getMessageType()) ){
					mDTO.setContent(m.getContent().split("@")[0]);
				}else{
					mDTO.setContent(m.getContent());}
				mDTO.setMessageType(properties[3]);
				
			 
				list_mDTO.add(mDTO);
				
			}
			
			JSONWriter jw = new JSONWriter();
	 		String str = jw.write(list_mDTO);
	 		
			ObjectDTO oDTO=new ObjectDTO();
			oDTO.setId(new Long(list_message.size()));
			oDTO.setName(str);
			str=jw.write(oDTO);

			
	 		return str;
			
		}
	  
	  return null;
  }
	
	/**
	 * 更新在线用户列表
	 * @param username 待添加到列表的用户名
	 * @param flag 是添加用户到列表,还是只获得当前列表
	 * @param request
	 * @return 用户userid
	 */
	public String updateUsersList(boolean flag, HttpServletRequest request) {
		Set<UserDTO> myOnlineUsers=new HashSet<UserDTO>();
		
		
      
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();//得到当前登录的用户名
		if(null!=userService.getUser(userName)){
		SystemUser user=(SystemUser)userService.getUser(userName);
		
		

		//首次进入登录页面则将用户id和scriptsession绑定，并把scriptsession加到scriptmap中
		if(!flag){
		String userInfo=user.getName()+"+"+user.getEmail()+"+"+user.getPicturePath();//用于赋全局变量
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPicturePath(user.getPicturePath());   
		Iterator it=users.iterator();
		Boolean exit=false;
		while(it.hasNext())
		{UserDTO userTemp=(UserDTO)it.next();
		 if(userTemp.getEmail().equals(userDTO.getEmail()))
			 {exit=true;break;}
		}
		if(!exit){
		users.add(userDTO);}
		
		this.setScriptSessionFlag(String.valueOf(userDTO.getId()));
		this.setUserScriptSession(userDTO);
		this.leopardScriptSessions(request);

		return userInfo;
		}
		
		//用户点击在线好友活着刷新在线好友
		if(flag)
		{

	          Iterator it=users.iterator();
	          while(it.hasNext())
	          {UserDTO temp=(UserDTO)it.next();
	                if(!temp.getName().equals(user.getName()))
	                  {	myOnlineUsers.add(temp);
	                  }
	            }
	         
//	  		  System.out.println("系统在线人数："+users.size());
//	  		  System.out.println("myOnlineUsers.size："+myOnlineUsers.size());
	  		  
	  		 ScriptSession scriptSession = WebContextFactory.get().getScriptSession();//获得当前用户的scriptSession
	  		 Util util = new Util(scriptSession);//更新用户页面
	  		 
	  		
	 		JSONWriter jw = new JSONWriter();
	 		String str = jw.write(myOnlineUsers);
	 		return str;
		
		}
            return user.getName();
		}

		
		
		return null;
	}
	public String markAsReaded(Integer messageId){
		if(null!=messageId)
		{Message m=messageService.getMessage(new Long(messageId));
		if(null!=m)
		{
		  m.setIsRead(true);
		  messageService.save(m);
 
		}		
		}		
		return null;
	}
	

	/**
	 * 验证当前scriptsession是否存在，若不存在，则罔scriptmap中添加 返回“0”
	 * 若存在，且httpsession对应的scriptsession相同，则不作任何改变  返回“1”
	 * 若存在但httpsession对应的scriptsession不相同，则替换  返回“2”
	 * @param request
	 * @return
	 */
	public void leopardScriptSessions(HttpServletRequest request){
		//获得scriptSession，把httpsession和scriptSession存到map中
		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		String result="";
		if(null!=scriptSession.getAttribute("userid"))
		{
				
			 for (Entry<String, ScriptSession> entry :scriptmap.entrySet())
	               {ScriptSession s=(ScriptSession)entry.getValue();
					 String xuserid = (String) s.getAttribute("userid");
					 if (xuserid != null && xuserid.equals((String)scriptSession.getAttribute("userid"))) {
						 
						    scriptmap.remove(entry.getKey());
							
							scriptmap.put(request.getSession().getId(), scriptSession);
						result="exit";break;
						}
					}
			 
			 if(!result.equals("exit"))
			  {
				 scriptmap.put(request.getSession().getId(), scriptSession);
			  }
		
		}
		

		
		
	}
	
	/**
	 * 绑定用户名和scriptsession
	 * @param user
	 */
	public void setUserScriptSession(UserDTO user){
		userScriptMap.put(String.valueOf(user.getId()),WebContextFactory.get().getScriptSession() );
	}

	/**
	 * 将用户id和页面脚本session绑定
	 * @param userid
	 */
	public void setScriptSessionFlag(String userid) {
		WebContextFactory.get().getScriptSession().setAttribute("userid", userid);
	}

	/**
	 * 根据用户id获得指定用户的页面脚本session
	 * @param userid
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ScriptSession getScriptSession(String userid, HttpServletRequest request) {
		ScriptSession scriptSessions = null;
		//this.leopardScriptSessions(request);
		HashSet sessionSet=new HashSet();
		sessionSet.addAll(scriptmap.values());
		Iterator it=sessionSet.iterator();
		while(it.hasNext())
		{ScriptSession s=(ScriptSession)it.next();
		 String xuserid = (String) s.getAttribute("userid");
		 if (xuserid != null && xuserid.equals(userid)) {
			scriptSessions = s;
			}
		}
		
		return scriptSessions;

	}
	
	public void cleanSession(String userid , HttpServletRequest request) {
		users.remove(0);
		scriptmap.remove(userid);
	}
	
	/**
	 * 发送消息
	 * @param sender 发送者
	 * @param receiverid 接收者id
	 * @param msg 消息内容
	 * @param request
	 */
	public void send(String sender,String receiverid,String msg,HttpServletRequest request){
		this.leopardScriptSessions(request);
		ScriptSession session = this.getScriptSession(receiverid, request);
		SystemUser sender_user=userService.getUser();

	if(session!=null){
		//session.addScript(script);
		Util util = new Util(session);
		//util.setStyle("showMessage", "display", "");
        DateFormat dateformat=new SimpleDateFormat("MM-dd HH:mm:ss");
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("<span style='font-size:10px;'><img src='/caltks/thumbnail/"+sender_user.getPicturePath()+"' width=30px height=30px></img><span style='color:rgb(223,60,0);font-size:11px;'>"+sender_user.getName()+"&nbsp&nbsp&nbsp&nbsp</span>"+dateformat.format(new Date())+"</span>");
        
        StringBuilder sb1 = new StringBuilder();
        sb1.append(msg);       
        
        
        SystemUser currentUser = userService.getUser();
        
        UserDTO ud = new UserDTO();
        ud.setId(currentUser.getId());
        ud.setEmail(currentUser.getEmail());
        ud.setName(currentUser.getName());
        
        List al = new ArrayList();
        
        al.add(sb.toString());
        al.add(sb1.toString());
        al.add(ud);
        
        
        util.setValue("cimsMessage", JSONUtil.write(al));
		util.addFunctionCall("dialog_callback");
	
		}

//	else{System.out.println("用户页面已关闭");}
		
	}

	
}
