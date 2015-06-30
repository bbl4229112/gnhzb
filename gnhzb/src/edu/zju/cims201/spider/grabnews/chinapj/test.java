/**
 * 
 */
package edu.zju.cims201.spider.grabnews.chinapj;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.dao.common.DAO;
import edu.zju.cims201.GOF.dao.knowledge.CommentDao;
import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeServiceImpl;
import edu.zju.cims201.GOF.test.BaseActionTest;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.spider.commontool.ClassLoaderUtil;


public class test{
	 private ApplicationContext context = null;  
	 
	 private MetaKnowledgeDao kdao;
	 private KnowledgeServiceImpl knowledgeService;
	 public void init() {
		 context = new FileSystemXmlApplicationContext(new String[]  
		                                              	        { Constants.APPLICATIONCONTEXT});  
		 //不能调，说没有这么bean
//		 CommentDao cdao=(CommentDao) context.getBean("CommentDao");
//		 Comment result = cdao.findUniqueBy("id", 2);
//		 String aa=result.getContent();
//		 System.out.println("aa="+aa);
		 
		 //能调不能用，能调这个bean,但不能用它的方法
//		 MetaKnowledgeDao kdao=(MetaKnowledgeDao) context.getBean("metaKnowledgeDao");
//		 MetaKnowledge result2 = kdao.findUniqueBy("id", 2);
//		 String aa=result2.getFlashfilepath();
//		 System.out.println("aa="+aa);
		 
		 //能用
		 CommonDao dao=(CommonDao)context.getBean("commonDao");
//		 DAO dao=(DAO) context.getBean("commonDAO");
		 Comment result = dao.findUniqueByProperty(Comment.class,"id",new Long(2));
		 String aa=result.getContent();
		 System.out.println("aa="+aa);
		
//		 knowledgeService=(KnowledgeServiceImpl) context.getBean("knowledgeServiceImpl"); 
//       MetaKnowledge result2 = knowledgeService.getMetaknowledge(new Long(2));
//  	 String aa=result2.getFlashfilepath();
//  	 System.out.println("aa="+aa);
		 
	 }
	
	public static void main(String[] args) {

		System.out.println("hello world!");
		test test = new test();
		test.init();
	}
	
	
}