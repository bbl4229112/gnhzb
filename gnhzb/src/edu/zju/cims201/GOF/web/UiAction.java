package edu.zju.cims201.GOF.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springside.modules.orm.Page;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;


import edu.zju.cims201.GOF.hibernate.pojo.Attachment;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Version;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.PropertyDTO;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.knowledge.FullTextService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 属性管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数. 
 * 
 * @author hebi
 */
// 定义URL映射对应/ktype/property.action
@Namespace("/knowledge")
// 定义名为reload的result重定向到property.action, 其他result则按照convention默认.
@Results( 
		{@Result(name = "kupload", location = "/WEB-INF/content/knowledge/knowledge-upload.jsp") ,
	     @Result(name = "simpkupload", location = "/WEB-INF/content/knowledge/knowledge-simpupload.jsp") ,
	     @Result(name = "batchupload", location = "/WEB-INF/content/knowledge/knowledge-batchupload.jsp") ,
	     @Result(name = "dandiankupload", location = "/WEB-INF/content/knowledge/knowledge-dandianupload.jsp") ,
	     @Result(name = "addquestion", location = "/WEB-INF/content/question/add-question.jsp") ,
		 @Result(name = "ksearch", location = "/WEB-INF/content/knowledge/knowledge-search.jsp") ,
		 @Result(name = "knowledgelist", location = "/WEB-INF/content/knowledge/knowledge-list.jsp"),
		 @Result(name = "knowledgedetail", location = "/WEB-INF/content/knowledge/knowledge-view.jsp"),
		 @Result(name = "knowledgecomment", location = "/WEB-INF/content/comment/comment-input.jsp"),
		 @Result(name = "kmap", location = "/WEB-INF/content/kmap/kmap.jsp"),
		 @Result(name = "indexall", location = "/WEB-INF/content/knowledge/knowledge-indexall.jsp"),
		 @Result(name = "inituser", location = "/WEB-INF/content/user/user-avidminit.jsp"),
		 @Result(name = "position", location = "/position.jsp"),
		 @Result(name = "addarticle", location = "/WEB-INF/content/expert/add-artical.jsp"),//江丁丁添加 
		 @Result(name = "lcahome", location = "/WEB-INF/content/lca/welcome.jsp")//杨文君添加 
		}
)
public class UiAction extends CrudActionSupport<MetaKnowledge> {
	/**
	 * 用于知识检索跳转时使用的变量
	 */
	private String url;
	private String formvalue;
	
	private int id;
	
	/**
	 * 评论跳转的时候使用的变量
	 */
	private int knowledgeID;
	
	// CrudActionSupport里的参数
	private static final long serialVersionUID = 8683878162525847072L;

	private MetaKnowledge entity;

	//北航
	private String keyword;
	
	//private PageDTO page = new Page<Property>(5);// 每页5条记录

	public MetaKnowledge getModel() {

		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		

	}

	@Override
	public String delete() throws Exception {

		return RELOAD;
	}

	@Override
	public String input() throws Exception {

		Long iid = entity.getId();
		return INPUT;
	}

	@Override
	public String list() throws Exception {


		return SUCCESS;

	}

	@Override
	public String save(){
		
		return null;
	}
	//@Action(value="/knowledge",results={@Result(name = "ksearch", location = "/WEB-INF/content/knowledge/knowledge-search.jsp")})
	public String ksearch() {
		return "ksearch";
	}
	public String addquestion(){
		return "addquestion";
	}
	
	//发布文章-名人专栏   江丁丁添加   2013-6-6
	public String addarticle(){
		return "addarticle";
	}
	
	//LCA欢迎界面   杨文君添加   2013-8-13
	public String lcahome(){
		return "lcahome";
	}
	
	//@Action(value="/knowledge",results={@Result(name = "kupload", location = "/WEB-INF/content/knowledge/knowledge-upload.jsp")})
	public String kupload() {
		return "kupload";
	}
	public String simpkupload() {
		return "simpkupload";
	}
	public String batchupload() {
		return "batchupload";
	}
	public String dandiankupload() {
		return "dandiankupload";
	}
	@Action(value="/knowledge/ktype/ktypeinput",results={@Result(name = "ktypeinput", location = "/WEB-INF/content/knowledge/ktype/ktype-input.jsp")})
	public String ktypeinput() {
		System.out.println("ktypeinput");
		return "ktypeinput";
	}
	

	public String fulltextserach() throws IOException {
		return null	;
	}

	//知识搜索的时候中转
	public String knowledgesearchlist() throws IOException {
		System.out.println(url);
		System.out.println(formvalue);
		return "knowledgelist";
	}
	
	//在展示知识详情的时候中转
	public String knowledgedetail() throws IOException {
		
		return "knowledgedetail";
	}
	
	public String position() throws IOException {
		return "position";
	}
	
	//在展示评论的时候中转
	public String knowledgecomment() throws IOException {
		
		return "knowledgecomment";
	}
	
	//显示profuse
	//在展示评论的时候中转
	public String kmap() throws IOException {
		
		return "kmap";
	}
	//构建索引
	public String indexall() throws IOException {
		
		return "indexall";
	}
	//从avidm初始化用户
	public String inituser() throws IOException {
		
		return "inituser";
	}
	
	//北航
	@Action(value="/ui",results={@Result(name = "contentpage", location = "/user.jsp")})
	public String clientsearch(){
		return "contentpage";		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFormvalue() {
		return formvalue;
	}

	public void setFormvalue(String formvalue) {
		this.formvalue = formvalue;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String testmyurl(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
		System.out.println("user name:"+auth.getName());
		return null;
	}

	public int getKnowledgeID() {
		return knowledgeID;
	}

	public void setKnowledgeID(int knowledgeID) {
		this.knowledgeID = knowledgeID;
	}

	public String getKeyword() {

		return keyword;
	}

	public void setKeyword(String keyword) {
		

		this.keyword = keyword;
	}
	
}
