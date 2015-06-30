package edu.zju.cims201.GOF.web.message.sessionListener;

import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;   
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.session.HttpSessionEventPublisher;  
import org.springframework.web.context.WebApplicationContext;   
import org.springframework.web.context.support.WebApplicationContextUtils;   

import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.service.logging.AddUserScore;
import edu.zju.cims201.GOF.service.logging.SysBehaviorListService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.web.message.dwr.ChatManager;
import edu.zju.cims201.GOF.service.logging.AddUserScore;
import edu.zju.cims201.GOF.service.logging.SysBehaviorListService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
  

/**  
 * 扩展的HttpSessionEventPublisher  
 * 支持在线人数统计  
 *  
 */  
public class EnhancedHttpSessionEventPublisher extends HttpSessionEventPublisher {   



   
    public void sessionCreated(HttpSessionEvent event) { 
    
    	// 将用户加入到在线用户列表中   
    	//System.out.println("session 创建 拦截"+event.getSession().getId());
    	
    	HttpSession session = (HttpSession)event.getSession();
    
    	//保存session
        ApplicationConstants.SESSION_MAP.put(session.getId(),session);

      
        

    }   
  
   
    public void sessionDestroyed(HttpSessionEvent event) {   
        // 将用户从在线用户列表中移除
//    	 System.out.println("session 销毁 拦截"+event.getSession().getId());
    	 //session_map中移除session
    	 HttpSession session = (HttpSession)event.getSession();
    	 ApplicationConstants.SESSION_MAP.remove(session.getId());
//         System.out.println("session数量"+ApplicationConstants.SESSION_MAP.size());
        
         //从在线用户列表和scriptmap中移除该session，
         SecurityContext sc=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
         if(sc!=null){
         Authentication auth = sc.getAuthentication(); 
           if(null!=auth)
            {
        	 WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(event   
                     .getSession().getServletContext());   
        	 UserService userService= (UserService) wac.getBean("userServiceImpl"); 
        	  if(null!=userService.getUser(auth.getName()))
               {SystemUser userTemp=(SystemUser)userService.getUser(auth.getName());
               
        	  ChatManager.scriptmap.remove(session.getId());//从scriptmap中移除该session
        	      Iterator it=ChatManager.users.iterator();
        	        while(it.hasNext())
        	            {UserDTO temp=(UserDTO)it.next();
        	             if(userTemp.getEmail().equals(temp.getEmail()))
        	                { ChatManager.users.remove(temp);//从在线用户中移除
        	                  break;
        	                }
        	            }
        	   }
             }
         
           }

         super.sessionDestroyed(event);   

    }   

    /**  
     * 定义一个简单的内部枚举  
     */  
    private static enum Type {   
        SAVE, DELETE;   
    }   
  
}  

