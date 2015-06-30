package edu.zju.cims201.GOF.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;

import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.privilege.PrivilegeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.web.knowledge.BorrowAction;

public class KnowledgeTest extends BaseActionTest {
	
	
	private BorrowAction borrowAction;
	private ApplicationContext ctx = null;  
	
	protected void setUp() throws Exception {
		super.setUp();
		ctx = getApplicationContext();  
		borrowAction=new BorrowAction();
		borrowAction.setKnowledgeService((KnowledgeService)ctx.getBean("knowledgeServiceImpl"));
		borrowAction.setBorrowFlowService((BorrowFlowService)ctx.getBean("borrowFlowServiceImpl"));
		borrowAction.setUserService((UserService)ctx.getBean("userServiceImpl"));
		 
	}
	
	public void testSave(){
	
//		borrowAction.setFlowNodeId(new Long(3));
//		borrowAction.setUserId(new Long(13));
//		borrowAction.addBorrower();
		borrowAction.setKnowledgeID(new Long(17));
		borrowAction.setTimes(100);
		borrowAction.createBorrowFlow();
//		
		
		
	}

}
