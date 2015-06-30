package edu.zju.cims201.GOF.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;

import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.hibernate.pojo.ApprovalFlow;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.approvalFlow.ApprovalFlowServiceImpl;
import edu.zju.cims201.GOF.service.knowledge.FullTextServiceImpl;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeServiceImpl;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeServiceImpl;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.web.user.UserAction;

@Service
public class ArrpovalDataTransfor extends BaseActionTest {
	
	
	
	private ApplicationContext ctx = null;  
	private KnowledgeServiceImpl knowledgeService;
    private UserService userService;  
    private UserAction userAction;  
    private ActionContext context;  
    private KtypeServiceImpl ktypeservice;
    private BasicDataSource bds;
    private ApprovalFlowServiceImpl approvalFlowservice;
    private FullTextServiceImpl fulltextservice;
	private MetaKnowledgeDao kdao;
    protected void setUp() throws Exception  
    {  
        super.setUp();  
        
        try{
        	
        ctx = getApplicationContext();  
        	
        	//从配置文件中获取业务层  
        userService=(UserService) ctx.getBean("userServiceImpl");  
        approvalFlowservice=(ApprovalFlowServiceImpl)ctx.getBean("approvalFlowServiceImpl");
		knowledgeService=(KnowledgeServiceImpl) ctx.getBean("knowledgeServiceImpl"); 
		kdao=(MetaKnowledgeDao) ctx.getBean("metaKnowledgeDao");
		fulltextservice=(FullTextServiceImpl) ctx.getBean("fullTextServiceImpl"); 
        	bds = (BasicDataSource) ctx.getBean("dataSource");  
        	
        	//action直接用new的方式构造  
        //userAction=new UserAction();  
        //context=ActionContext.getContext();  
        }catch(Exception e){
        	e.printStackTrace();
    }
        	
        }  
    
    
    
    public void testTransfor() throws SQLException{    	

    	try {
    	List<MetaKnowledge>	knowledgelist=knowledgeService.getnoStatusKnowledge();
    	SystemUser user=userService.getUser(new Long(1));
        for(MetaKnowledge k:knowledgelist)
        {
        	
        	ApprovalFlow flow=approvalFlowservice.createApprovalFlow(k.getUploader(),k);
        	//ApprovalFlow flow=approvalFlowservice.getApprovalFlow(k);
        	
        	//SystemUser approvalor=this.userService.getUser(this.approvalorId);
    		//ApprovalFlow flow=this.approvalFlowService.getApprovalFlow(this.approvalFlowId);
    		FlowNode currentNode=flow.getFlowNodes().get(flow.getIndex());
    		flow.getKnowledge().setStatus(String.valueOf(flow.getIndex()+1));
    		currentNode.setApproverORLender(user);
    		currentNode.setNodeStatus(Constants.approvalFlowNodeResult_Pass);
    		currentNode.setAddTime(new Date());
    		currentNode.setApprovalORBorrowTime(new Date());
    		flow.setStatus(Constants.approvalFlowPass);
    		approvalFlowservice.saveApprovalFlow(flow);
    		k.setStatus("1");
    		knowledgeService.updateKnowledge(k);
    	//	fulltextservice.indexKnowledge(k);
        }
			
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
    	
		
    }

}
