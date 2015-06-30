package edu.zju.cims201.GOF.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import junit.framework.TestCase;

public class BaseActionTest extends TestCase {
	
	 private ApplicationContext context = null;  
	  
	    protected void setUp() throws Exception  
	    {  
	        super.setUp();  
	        context = new FileSystemXmlApplicationContext(new String[]  
	        { "D:/java program files/tomcat/apache-tomcat-6.0.29/webapps/caltks/WEB-INF/classes/applicationContext.xml"});  
	    }  
	  
	    public ApplicationContext getApplicationContext()  
	    {  
	        return context;  
	    }  

}
