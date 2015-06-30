package edu.zju.cims201.GOF.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;

import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;

import edu.zju.cims201.GOF.dao.SystemFileDao;
import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;
import edu.zju.cims201.GOF.service.privilege.PrivilegeService;
import edu.zju.cims201.GOF.service.systemFile.FileService;
import edu.zju.cims201.GOF.service.systemFile.FileServiceImpl;

public class FileTest extends BaseActionTest{
	
	private ApplicationContext ctx = null;  
	
	private FileService fileService;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		ctx = getApplicationContext();  
		fileService=(FileService) ctx.getBean("fileServiceImpl"); 
		
	}
	
	
	public void testSave(){
		
		File file =new File("C:\\creatgroup.swf");
		fileService.save(file,null);
		
	}

}
