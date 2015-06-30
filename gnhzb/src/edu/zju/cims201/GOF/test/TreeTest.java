package edu.zju.cims201.GOF.test;

import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springside.modules.orm.Page;


import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;

public class TreeTest extends BaseActionTest {
	
	
	private TreeService treeService;
	private KnowledgeService knowledgeService;
	private UserService userService;
	private ApplicationContext ctx = null;  
	
	protected void setUp() throws Exception {
		super.setUp();
		
		ctx = getApplicationContext();  
		treeService=(TreeService) ctx.getBean("treeServiceImpl"); 
		knowledgeService=(KnowledgeService) ctx.getBean("knowledgeServiceImpl"); 
		userService=(UserService)ctx.getBean("userServiceImpl");
	}
	
	public void testSave(){
//		SystemUser user1=userService.getUser("jixiang@163.com");
//		SystemUser user2=userService.getUser("test2@163.com");
		//MetaKnowledge knowledge1 = knowledgeService.getMetaknowledge(new Long(1));
		//MetaKnowledge knowledge2 = knowledgeService.getMetaknowledge(new Long(2));
		DomainTreeNode parent=(DomainTreeNode)treeService.getTreeNode(new Long(4));
//		DomainTreeNode sub=(DomainTreeNode)treeService.getTreeNode(new Long(2));
//		Page<SystemUser> page=new Page<SystemUser>(5);
//		Set<MetaKnowledge> knowledges = categoryTreeNode.getKnowledges();
//		for(MetaKnowledge knowledge:knowledges){
//			System.out.println(knowledge.getTitleName());
//		}
//		String result=null;
//		result=treeService.addUserRelation(roleTreeNode, user1);
//		System.out.println(result);
//		result=treeService.addUserRelation(roleTreeNode, user2);
//		System.out.println(result);
//		Page<SystemUser> newpage=treeService.listRTreeUsers(roleTreeNode, false, page);
//		List<SystemUser> list = newpage.getResult();
//		System.out.println(newpage.getTotalCount());
//		for(SystemUser u:list){
//			System.out.println(u.getEmail());
//		}
//			
		
		
//		DomainTreeNode domainTreeNode=new DomainTreeNode();
//		domainTreeNode.setNodeName("D树子节点2");
		
		List<TreeNode> list = treeService.listDeepSubTreeNodes(parent.getCode());
		for(TreeNode n:list){
			System.out.println(n.getCode());
		}
		
	}

}
