package edu.zju.cims201.GOF.service.statistic.job;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.systemUser.UserService;



public class Statistic {
	
	
	
	UserService userService;
	private static ApplicationContext cxt;


	public  Statistic() {
		
	//	ServletContext sc = request.getSession().getServletContext();
	//	ApplicationContext cxt= WebApplicationContextUtils.getWebApplicationContext(sc);

		
		cxt=new ClassPathXmlApplicationContext("applicationContext.xml");
		userService = (UserService) cxt.getBean("userServiceImpl");
				
	}
	

	public void updateWeek(){
		
		
		List<SystemUser> list_user = userService.getAllUsers();
		for(SystemUser u:list_user){
			u.setLastweekpscore(u.getWeekpscore());
			u.setLasttwoweekpscore(u.getWeekpscore());
			u.setWeekpscore(0);
			u.setLastweekcscore(u.getWeekcscore());
			u.setLasttwoweekcscore(u.getWeekcscore());
			u.setWeekcscore(0);
			userService.updateUser(u);
			
		}
		

	} 
	

	public void updateMonth(){
		
		List<SystemUser> list_user = userService.getAllUsers();

		for(SystemUser u:list_user){
			u.setLastmonthpscore(u.getMonthpscore());			
			u.setMonthpscore(0);
			u.setLastmonthcscore(u.getMonthcscore());			
			u.setMonthcscore(0);
			
			userService.updateUser(u);
		}
		

	}  
	
	
	public void updateYear(){
		List<SystemUser> list_user = userService.getAllUsers();
		for(SystemUser u:list_user){
			u.setLastyearpscore(u.getYearpscore());			
			u.setYearpscore(0);
			u.setLastyearcscore(u.getYearcscore());			
			u.setYearcscore(0);
			
			userService.updateUser(u);
		}

	}  
    
}
