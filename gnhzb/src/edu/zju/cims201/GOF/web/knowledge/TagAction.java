package edu.zju.cims201.GOF.web.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springside.modules.orm.Page;
import org.stringtree.json.JSONWriter;


import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Tag;
import edu.zju.cims201.GOF.hibernate.pojo.UserKnowledgeTag;
import edu.zju.cims201.GOF.rs.dto.TagDTO;

import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.comment.CommentService;
import edu.zju.cims201.GOF.service.knowledge.tag.TagService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 评论管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数. 演示带分页的管理界面.
 * 
 * @author cwd
 */
// 定义URL映射对应/comment/comment.action
@Namespace("/knowledge/tag")
// 定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "tag.action", type = "redirect") }
          

)
public class TagAction extends CrudActionSupport<UserKnowledgeTag> implements ServletResponseAware{

	// CrudActionSupport里的参数
	private static final long serialVersionUID = 8683878162525847072L;
	@Resource(name = "tagServiceImpl")
	private TagService tservice;
	@Resource(name = "userServiceImpl")
	private UserService uservice;
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice;
    private HttpServletResponse response;
	// /** 进行增删改操作后,以redirect方式重新打开action默认页的result名.*/
	// public static final String RELOAD = "reload";

	// -- 页面属性 --//
	private Long id;
	private Long userid;

	private Long knowledgeID;
	private String tagname;
	private UserKnowledgeTag entity;
//	private Page<Comment> page = new Page<Comment>(5);// 每页5条记录

	@Override
	public String delete() throws Exception {

		tservice.deleteTag2Knowledge(entity);
		list();
		return null;
	}

	@Override
	public String input() throws Exception {

		return INPUT;
	}

	// 返回个人知识标签
	@Override
	public String list() throws Exception {

		//knowledgeID= new Long(1);
		MetaKnowledge knowledge = kservice.getMetaknowledge(knowledgeID);
	
		List<TagDTO>uktdtos=new ArrayList<TagDTO>();
		List<Tag> uktlist=tservice.listIndividualTags(knowledge, uservice.getUser());
		if(null!=uktlist)
		for (Tag tag : uktlist) {
			TagDTO tagdto=new TagDTO();
			tagdto.setId(tag.getId());
			tagdto.setTagName(tag.getTagName());
			uktdtos.add(tagdto);
		}
		List<TagDTO>ktdtos=new ArrayList<TagDTO>();
		List<Tag> ktlist=tservice.listPopularTags(knowledgeID);
		if(null!=ktlist)
		for (Tag tag : ktlist) {
			TagDTO tagdto=new TagDTO();
			tagdto.setId(tag.getId());
			tagdto.setTagName(tag.getTagName());
		
			ktdtos.add(tagdto);
		}

		List taglist=new ArrayList();
		taglist.add(uktdtos);
		taglist.add(ktdtos);
		
       JSONUtil.write(response,taglist);
		
		
		return null;

	}

	// 返回大众标签
	public String listALL() throws Exception {
		//knowledgeID= new Long(1);
		List<TagDTO>uktdtos=new ArrayList<TagDTO>();
		List<Tag> uktlist=tservice.listPopularTags(knowledgeID);
		for (Tag tag : uktlist) {
			TagDTO tagdto=new TagDTO();
			tagdto.setId(tag.getId());
			tagdto.setTagName(tag.getTagName());
			uktdtos.add(tagdto);
		}
	JSONUtil.write(response, uktdtos);
		
		return null;

	}
	// 推荐大众标签
	public String recommentTag() throws Exception {
		//knowledgeID= new Long(1);
		List<TagDTO>uktdtos=new ArrayList<TagDTO>();
		List<Tag> uktlist=tservice.recommentKTags(kservice.getMetaknowledge(knowledgeID), tagname);
		for (Tag tag : uktlist) {
			TagDTO tagdto=new TagDTO();
			tagdto.setId(tag.getId());
			tagdto.setTagName(tag.getTagName());
			uktdtos.add(tagdto);
		}
		JSONUtil.write(response, uktdtos);
		
		return null;

	}
	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {

			entity = new UserKnowledgeTag();
		} else {
			if(null!=knowledgeID){
			
			entity = tservice.getUserKnowledgeTag(kservice.getMetaknowledge(knowledgeID),tservice.getTag(id),uservice.getUser());
			}
			else
			entity=null;
		}

	}

	// 添加个人知识标签
	@Override
	public String save() throws Exception {
		MetaKnowledge k = kservice.getMetaknowledge(knowledgeID);
		
		//SecurityContext securityContext = SecurityContextHolder.getContext();
		//String userEmail=securityContext.getAuthentication().getName();
		
		SystemUser u =uservice.getUser();
		
		//SystemUser u = uservice.getUser(userEmail);
		UserKnowledgeTag ukt = new UserKnowledgeTag();
		ukt.setTager(u);
		ukt.setKnowledge(k);
		tservice.SearchAndSaveUserKnowledgeTag(ukt, tagname);
        list();
		return null;
	}

	public UserKnowledgeTag getModel() {
		// TODO Auto-generated method stub
		return entity;
	}

//	public Page<Comment> getPage() {
//		return page;
//	}
//
//	public void setPage(Page<Comment> page) {
//		this.page = page;
//	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getKnowledgeID() {
		return knowledgeID;
	}

	public void setKnowledgeID(Long knowledgeID) {
		this.knowledgeID = knowledgeID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
		
	}

	public TagService getTservice() {
		return tservice;
	}

	public void setTservice(TagService tservice) {
		this.tservice = tservice;
	}

	public UserService getUservice() {
		return uservice;
	}

	public void setUservice(UserService uservice) {
		this.uservice = uservice;
	}

	public KnowledgeService getKservice() {
		return kservice;
	}

	public void setKservice(KnowledgeService kservice) {
		this.kservice = kservice;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}



}
