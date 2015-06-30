package edu.zju.cims201.GOF.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionContext;

import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeServiceImpl;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.web.user.UserAction;

public class CreatKTypeTest extends BaseActionTest {
	
	
	
	private ApplicationContext ctx = null;  
	  
    private KtypeService kts;  
    
    //private ActionContext context;  
    protected void setUp() throws Exception  
    {  
        super.setUp();  
        ctx = getApplicationContext();  
        //浠庨厤缃枃浠朵腑鑾峰彇涓氬姟灞�  
        kts=(KtypeService) ctx.getBean("ktypeServiceImpl");  
          
        
     //   context=ActionContext.getContext();  
    }  
    
    
    
    public void testSave(){
    	Ktype ktype=new Ktype();

    	List<Property> properties = new ArrayList<Property>();
    	//KtypeProperty ktypeproperty=new KtypeProperty();
    	properties=kts.listExpandedProperties();
    	ktype.setKtypeName("cwd12");
    	ktype.setName("cwd12");
   
    	try {
			//String result=kts.createKtype(ktype,properties);
			//String result=kts.test();
    		//System.out.println(result);
		} catch (Exception e) {
			// TODO 鑷姩鐢熸垚 catch 鍧�
			e.printStackTrace();
		}
		
    }

}
