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

import edu.zju.cims201.GOF.dao.common.DAO;
import edu.zju.cims201.GOF.dao.knowledge.CommentDao;
import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.test.BaseActionTest;

@Service
@Transactional
public class test2 {
	@Resource(name="commentDao")
	private CommentDao commentdao;
	 public void init() {
		 Comment result = commentdao.findUniqueBy("id", 2);
		 String aa=result.getContent();
		 System.out.println("aa="+aa);
		 

		 
	 }
	
	public static void main(String[] args) {
		
		System.out.println("hello world!");
		test test = new test();
		test.init();
	}
	
	
}