package edu.zju.cims201.GOF.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import edu.zju.cims201.GOF.dao.HibernateUtils;
import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;

import edu.zju.cims201.GOF.hibernate.pojo.Attachment;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.ServiceException;

import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 * 
 * @author calvin
 */
//定义URL映射对应/account/user.action
@Namespace("/knowledge")
//定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "fileupload.action", type = "redirect") })
public class SourcefiledownloadAction extends CrudActionSupport<MetaKnowledge> implements ServletResponseAware {

	@Resource(name="metaKnowledgeDao")
	private MetaKnowledgeDao dao;
	@Resource(name="userServiceImpl")
	private UserService us;
	@Resource(name="knowledgeServiceImpl")
	private KnowledgeService ks;
	private HttpServletResponse response;
	private Long id;
	
	public String download()throws Exception {
		
		//String kid=request.getParameter("kid");
		
		if(null!=id&&!"".equals(id)){//确认参数完整
			
			MetaKnowledge kg = ks.getMetaknowledge(id);
			File sfile = new File(Constants.SOURCEFILE_PATH+"\\"+kg.getKnowledgesourcefilepath());
			
			if(null!=sfile){//确认源文件存在
				
				String ext = kg.getKnowledgesourcefilepath().substring(kg.getKnowledgesourcefilepath().lastIndexOf("."));
				String filename = kg.getTitlename()+ext;
				System.out.println("下载文件："+filename);
			
		    //设置response
			response.reset();
		    response.setHeader("Pragma", "No-cache");
		    response.setHeader("Cache-Control", "no-cache");
		    
		    response.setDateHeader("Expires", 0);
		    response.setContentType("application/octet-stream;charset=UTF-8");
		    response.setHeader("Content-disposition","attachment;filename=\"" + new String(filename.getBytes(), "ISO_8859_1") + "\"");
		    response.setContentLength((int) sfile.length());
				
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sfile));
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
				
				Streams.copy(bis,bos,true);
				
			}
			
			//如果当前下载的用户不是知识上传者，则知识的下载次数+1
		if(!us.getUser().getEmail().equals(kg.getUploader().getEmail()))
				{
		
			int downloadcount=	Integer.parseInt(kg.getCommentrecord().getDownloadCount()+"")+1;
			kg.getCommentrecord().setDownloadCount(new Long(downloadcount));
			ks.updateKnowledge(kg);
			}
		}
		
		
		return null;
	}
	
	public String downloadattach()throws Exception {
		
		//String kid=request.getParameter("kid");
		
		if(null!=id&&!"".equals(id)){//确认参数完整
			
			Attachment att = ks.getAttachment(id);
			File sfile = new File(Constants.ATTACHMENT_PATH+"\\"+att.getAttachmentPath());
			
			if(null!=sfile){//确认源文件存在
				
			//	String ext = kg.getKnowledgesourcefilepath().substring(kg.getKnowledgesourcefilepath().lastIndexOf("."));
				String filename =att.getAttachmentName();
				System.out.println("下载文件："+filename);
			
		    //设置response
			response.reset();
		    response.setHeader("Pragma", "No-cache");
		    response.setHeader("Cache-Control", "no-cache");
		    
		    response.setDateHeader("Expires", 0);
		    response.setContentType("application/octet-stream;charset=UTF-8");
		    response.setHeader("Content-disposition","attachment;filename=\"" + new String(filename.getBytes(), "ISO_8859_1") + "\"");
		    response.setContentLength((int) sfile.length());
				
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sfile));
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
				
				Streams.copy(bis,bos,true);
				
			}
			
			
		}
		
		
		return null;
	}
	
	
	
	
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}
	public MetaKnowledge getModel() {
		// TODO Auto-generated method stub
		return null;
	}
   
	
	
	
}
