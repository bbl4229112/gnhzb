package edu.zju.cims201.GOF.service.systemFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.SystemFileDao;
import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.util.Constants;

@Service
@Transactional
public class FileServiceImpl implements FileService {

	private KnowledgeService ks;;
	private SystemFileDao fileDao;
    private MetaKnowledgeDao kdao;
	public KnowledgeService getKs() {
		return ks;
	}

	public MetaKnowledgeDao getKdao() {
		return kdao;
	}
	@Autowired
	public void setKdao(MetaKnowledgeDao kdao) {
		this.kdao = kdao;
	}

	@Autowired
	public void setKs(KnowledgeService ks) {
		this.ks = ks;
	}

	public Long save(File file, String fileName) {
		Blob blob = null;
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					new FileInputStream(file));
			long len = file.length();
			byte[] bytes = new byte[(int) len];
			bufferedInputStream.read(bytes);
			blob = Hibernate.createBlob(bytes, fileDao.getSession());
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		// String fileName=file.getName();
		String fileType = fileName.substring(fileName.indexOf("."), fileName.length());
		fileName = fileName.substring(0, fileName.indexOf("."));	
		SystemFile systemFile = fileDao.findUnique("from SystemFile sysf where sysf.fileName=? and sysf.fileType=?",
				fileName,fileType);
		
		if(systemFile==null){
			systemFile = new SystemFile();
			systemFile.setFileName(fileName);
			systemFile.setFileType(fileType);
			
		}

		systemFile.setFileBinary(blob);
		fileDao.save(systemFile);
		fileDao.flush();
		return systemFile.getId();
	}

	public SystemFile getFile(Long id) {
		SystemFile systemFile = fileDao.findUniqueBy("id", id);
		if (systemFile == null)
			return null;
		return systemFile;
	}

	public SystemFile getFile(String filename, String filetype) {
		SystemFile systemFile = fileDao
				.findUnique(
						"from SystemFile sysf where sysf.fileName=? and sysf.fileType=?",
						filename, filetype);
		
		if (systemFile == null){
			return null;
			}
		return systemFile;
	}

	public SystemFileDao getFileDao() {
		return fileDao;
	}

	@Autowired
	public void setFileDao(SystemFileDao fileDao) {
		this.fileDao = fileDao;
	}

	public SystemFile getKnowledgeSourceFile(Long id) {
		MetaKnowledge mk = ks.getMetaknowledge(id);
		String filename = mk.getKnowledgesourcefilepath();
		if (null != filename && filename.indexOf(".") != -1) {
			filename = filename.substring(0, filename.lastIndexOf("."));
			SystemFile systemFile = fileDao
					.findUnique(
							"from SystemFile sysf where sysf.fileName=? and sysf.fileType!=?",
							filename, ".swf");
			return systemFile;
		} else
			return null;
	}
    public String convertFile(String filename,String flashname,String sourceext){
    	
		String tempPath=Constants.SOURCEFILE_PATH_TEMP;	
		int p=100;
		if(null!=sourceext&&(sourceext.equals(".pdf")||sourceext.equals(".PDF")))
			p=1200;
//		System.out.println("ext=="+sourceext);
//		System.out.println("p=="+p);
			File tempFilea=null;
			boolean fileexist=false;
			for(int i=0;i<p;i++)
			{
			 tempFilea = new File(tempPath,flashname);
			 //用于知识客户端上传的时候等待 ，避免flash转换不成功或还没有保存flash就移动导致失败
				if(tempFilea.exists())
				{  fileexist=true;
					break;
				}
				else
				{
					try {
						Thread.currentThread().sleep(new Long(500)) ;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("等待第"+i+"个500毫秒");
				}
		 
				
			}
			try{
			if(fileexist){
				String command = "tskill AcroRd32";
				
			    System.out.println("command + \" :" + command);
			
				Process pro = Runtime.getRuntime().exec(command);
				 pro.waitFor();
				 save(tempFilea, flashname);
				 
				 }
			else
			{ 
//				tempFilea = new File(tempPath,filename);
//				save(tempFilea, filename);
				String command = "tskill FlashPrinter";
				System.out.println("command + \" :" + command);
				Process pro = Runtime.getRuntime().exec(command);
			    pro.waitFor();
				 command = "tskill WINWORD";
				System.out.println("command + \" :" + command);
				 pro = Runtime.getRuntime().exec(command);
			    pro.waitFor();
			    command="tskill AcroRd32";
			    System.out.println("command + \" :" + command);
			    pro = Runtime.getRuntime().exec(command);
			    pro.waitFor();	
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return null;
			
//		}else{
//			deleteFile(tempPath,filename);
//			deleteFile(tempPath,flashFileName);
//			return "fail";
//		}
			
	}

	public MetaKnowledge getKnowledgebySourcefile(String filename,
			String filetype) {
		MetaKnowledge k=kdao.findUnique(
				"from MetaKnowledge k where k.knowledgesourcefilepath=? ",
				filename+filetype);
	//	System.out.println(k.getKnowledgesourcefilepath());
		return k;
	}
}
