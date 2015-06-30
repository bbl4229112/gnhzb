package edu.zju.cims201.GOF.test;

import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionContext;

import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.web.user.UserAction;

public class UserActionTest extends BaseActionTest {
	
	
	
	private ApplicationContext ctx = null;  
	  
    private UserService userService;  
    private UserAction userAction;  
    private ActionContext context;  
    protected void setUp() throws Exception  
    {  
        super.setUp();  
        ctx = getApplicationContext();  
        //从配置文件中获取业务层  
        userService=(UserService) ctx.getBean("userServiceImpl");  
          
        //action直接用new的方式构造  
        userAction=new UserAction();  
        context=ActionContext.getContext();  
    }  
    
    
    
    public void testSave(){
    	SystemUser user=new SystemUser();
    	user.setEmail("test3@163.com");
    	user.setHobby("testsss");
    	user.setName("吉祥");
    	userAction.setEntity(user);
    	userAction.setUserServiceImpl(userService);
    	
    	
    	try {
			String result=userAction.save();
			System.out.println(result);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		
    }

}
