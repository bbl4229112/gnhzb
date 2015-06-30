package edu.zju.cims201.GOF.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;
import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.logging.AddUserScore;
import edu.zju.cims201.GOF.service.logging.SysBehaviorListService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;
import edu.zju.cims201.GOF.service.systemFile.FileService;
import edu.zju.cims201.GOF.service.systemUser.UserService;


@Namespace("/knowledge")
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "fileupload.action", type = "redirect") })
public class ViewfileAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
	
	
	
	
	private String fileId;
	private Long id;
	private FileService fileService;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private UserService us;
	private String filename;
	@Resource(name="knowledgeServiceImpl")
	KnowledgeService ks;
	@Resource(name="sysBehaviorLogServiceImpl")
	SysBehaviorLogService sysBehaviorLogService;
	@Resource(name="sysBehaviorListServiceImpl")
	SysBehaviorListService sysBehaviorListService;
	@Resource(name="addUserScore")
	AddUserScore addUserScore;
	
	


	public String view(){
		if(null==this.fileId)
			return null;
		if (null != fileId && fileId.indexOf(".") != -1) {
			fileId = fileId.substring(0, fileId.lastIndexOf("."));
			}
		SystemFile file=this.fileService.getFile(fileId,".swf");
		if(null==file)
			file=this.fileService.getFile(fileId,".pdf");
		if(null==file)
			file=this.fileService.getFile(fileId,".PDF");
		if(null==file)
			return null;
		writeFile(file,response);
		return null;
	}
	
	private void writeFile(SystemFile file,HttpServletResponse response){
		
		Blob blob=file.getFileBinary();
		int len = 0;  
        byte[] buf = new byte[4096];
        ServletOutputStream out = null;
        InputStream in =null;
		try {
			out=this.response.getOutputStream();
			in = blob.getBinaryStream();
			while ((len = in.read(buf)) > 0) {  
                out.write(buf, 0, len);  
            } 
			out.flush();
			out.close();
			out=null;
			response.flushBuffer();
	        in.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public String docEdit(){
		if(null==this.filename)
			return null;
		if (null != filename && filename.indexOf(".") != -1) {
			filename = filename.substring(0, filename.lastIndexOf("."));
			}
		SystemFile file=this.fileService.getFile(filename,".doc");
		if(null==file)
			return null;
		writeFile(file,response);
		return null;
	}
	
	
	public String download(){
		
		if(null==this.id||0==this.id)
			return null;
		SystemFile file=this.fileService.getKnowledgeSourceFile(id);
		
		BufferedInputStream input = null;  
	    BufferedOutputStream output = null; 
		Blob blob=file.getFileBinary();
		
		try {
			InputStream in =blob.getBinaryStream();
			response.setHeader("Content-Type","application/octet-stream; charset=UTF-8");
			String fullName="知识原文件"+file.getFileType();
			MetaKnowledge k=fileService.getKnowledgebySourcefile(file.getFileName(), file.getFileType());
			if(null!=k&&null!=k.getKnowledgesourcefilepath()&&!k.getKnowledgesourcefilepath().trim().equals(""))
				fullName=k.getTitlename()+file.getFileType();
			fullName=new String(fullName.getBytes(), "ISO_8859_1");
		    response.setHeader("Content-Disposition","attachment; filename="+fullName);
		    input = new BufferedInputStream(in);  
            output = new BufferedOutputStream(response.getOutputStream());
            Streams.copy(input,output,true);
        	if(!us.getUser().getEmail().equals(k.getUploader().getEmail()))
    		{
            //用户行为记录和加分
          //记录审批行为行为到SysBehaviorLog表
  	      SysBehaviorLog bLog = new SysBehaviorLog();
  			bLog.setObjectid(k.getId());
  			bLog.setObjectType("knowledge");
  			bLog.setUser(us.getUser());
  			
  			Long SysBehaviorListId=16L;
  			
  			bLog.setActionTime(new Date());
  			bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(SysBehaviorListId));
  			
  			sysBehaviorLogService.save(bLog);

  			 SysBehaviorLog bLog2 = new SysBehaviorLog();
 		    bLog2.setObjectid(k.getId());
 		    bLog2.setObjectType("knowledge");
 		    bLog2.setUser(k.getUploader());
 			
 			Long SysBehaviorListId2=17L;
 			
 			bLog2.setActionTime(new Date());
 			bLog2.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(SysBehaviorListId2));
 			
 			sysBehaviorLogService.save(bLog2);
 			
  			//累加用户积分 下载加分
  			addUserScore.addUserScore(us.getUser(),SysBehaviorListId);
  			//被下载加分
  			addUserScore.addUserScore(k.getUploader(),SysBehaviorListId2);
  			//添加下载次数
  			CommentRecord cr= k.getCommentrecord();
  			cr.setDownloadCount(cr.getDownloadCount()+1);
  			k.setCommentrecord(cr);
  			ks.updateKnowledge(k);
            
    		}
            
            
            
            
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		return null;
		
	}
	
	
	
	
	

	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}
	public void setServletResponse(HttpServletResponse response) {
		
		this.response=response;
		
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}




	public FileService getFileService() {
		return fileService;
	}



	@Autowired
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public KnowledgeService getKs() {
		return ks;
	}

	@Autowired
	public void setKs(KnowledgeService ks) {
		this.ks = ks;
	}

	
	
	public UserService getUs() {
		return us;
	}

	@Autowired
	public void setUs(UserService us) {
		this.us = us;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	

	
	
	

}
