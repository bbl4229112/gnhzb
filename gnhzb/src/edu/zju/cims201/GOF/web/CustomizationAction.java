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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springside.modules.orm.Page;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;

import com.sun.corba.se.impl.orbutil.closure.Constant;


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
import edu.zju.cims201.GOF.rs.dto.ShowButtonDTO;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.knowledge.FullTextService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 模块化组件定制Action.
 * 
 * 判断某组件是否存在于系统中，全部返回布尔类型的值 
 * 
 * @author 潘雷
 * 2013.05.01
 */
// 定义URL映射对应/ktype/property.action
@Namespace("/custom")
// 定义名为reload的result重定向到property.action, 其他result则按照convention默认.
@Results( 
		{
		 @Result(name = "smc", location = "/WEB-INF/content/custom/smc.jsp"),
		  @Result(name = "noright", location = "/WEB-INF/content/custom/noright.jsp")
		}
)
public class CustomizationAction extends CrudActionSupport<MetaKnowledge> implements ServletResponseAware {
	
	private HttpServletResponse response;
	private String json;
	@Resource(name = "userServiceImpl")
	private UserService userservice;
	
	/**
	 * 判断是否显示直接协同——在线好友聊天功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.01
	 * */
	public String whetherShowCooperationOnLine(){
		String propertyValue = Constants.COOPERATION_ON_LINE;
		if(propertyValue.equals("CooperationOnLine")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示知识地图功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kMAP(){
		String propertyValue = Constants.KNOWLEDGE_MAP;
		if(propertyValue.equals("KnowledgeMap")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示知识问答功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kQA(){
		String propertyValue = Constants.KNOWLEDGE_QUESTION_ANSWER;
		if(propertyValue.equals("KnowledgeQuestionAnswer")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示岗位知识功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kPosition(){
		String propertyValue = Constants.KNOWLEDGE_POSITION;
		if(propertyValue.equals("KnowledgePosition")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示知识订阅功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kRSS(){
		String propertyValue = Constants.KNOWLEDGE_RSS;
		if(propertyValue.equals("KnowledgeRSS")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示知识收藏功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kKeep(){
		String propertyValue = Constants.KNOWLEDGE_KEEP;
		if(propertyValue.equals("KnowledgeKeep")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示优质知识排行功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kValued(){
		String propertyValue = Constants.KNOWLEDGE_VALUED;
		if(propertyValue.equals("KnowledgeValued")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示总体趋势功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kTrend(){
		String propertyValue = Constants.KNOWLEDGE_TREND;
		if(propertyValue.equals("KnowledgeTrend")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示总体趋势功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kContribution(){
		String propertyValue = Constants.KNOWLEDGE_CONTRIBUTION;
		if(propertyValue.equals("KnowledgeContribution")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示多维搜索功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kMultiSearch(){
		String propertyValue = Constants.KNOWLEDGE_MULTI_SEARCH;
		if(propertyValue.equals("KnowledgeMultiSearch")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示知识借阅功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kBorrow(){
		String propertyValue = Constants.KNOWLEDGE_BORROW;
		if(propertyValue.equals("KnowledgeBorrow")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示统计积分配置功能组件
	 * 
	 * @author 潘雷
	 * 2013.05.04
	 * */
	public String kStatisticConfig(){
		String propertyValue = Constants.KNOWLEDGE_STATISTIC_CONFIG;
		if(propertyValue.equals("KnowledgeStatisticConfig")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	
	/**
	 * 判断是否显示专利协同功能组件
	 * 
	 * @author 江丁丁
	 * 2013.06.22
	 * */
	public String patent(){
		String propertyValue = Constants.PATENT;
		if(propertyValue.equals("Patent")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		 
	 }
	/**
	 * 判断是否显示专家黄页功能组件
	 * 
	 * @author 江丁丁
	 * 2013.06.22
	 * */
	public String expertArticle(){
		String propertyValue = Constants.EXPERT_ARTICLE;
		if(propertyValue.equals("ExpertArticle")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		
	}

	/**
	 * 判断是否显模块化评价功能组件
	 * 
	 * @author 江丁丁
	 * 2013.08.13
	 * */
	public String mde(){
		String propertyValue = Constants.MDE;
		if(propertyValue.equals("MDE")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		
	}
	/**
	 * 判断是否显示专家黄页功能组件
	 * 
	 * @author 江丁丁
	 * 2013.06.22
	 * */
	public String lca(){
		String propertyValue = Constants.LCA;
		if(propertyValue.equals("LCA")){
			
			JSONUtil.write(response, "true");
		}else{
			JSONUtil.write(response, "false");
			
		}
		return null; 	 
		
	}
	
	/**
	 * 判断是否显示系统配置组件的界面，本方法只留给我一个人使用
	 * 
	 * @author 潘雷
	 * 2013.05.01
	 * */
	public String wSMC(){
		SystemUser user = this.userservice.getUser();
		if(user.getEmail().equals("admin@systemmaster.com") && user.getPassword().equals("kssystemmaster")){
			
			JSONUtil.write(response, "systemmaster");
		}else{
			JSONUtil.write(response, "nothing");
			
		}
		return null; 	 
		 
	 }
	
	/**
	 * 跳转到配置组件页面
	 * 
	 * */
	public String smc() {
		SystemUser user = this.userservice.getUser();
		if(user.getEmail().equals("admin@systemmaster.com") && user.getPassword().equals("kssystemmaster")){
			return "smc";
		}else{
			return "noright";
		}
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

	public MetaKnowledge getModel() {
		return null;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
		
	}
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
}
