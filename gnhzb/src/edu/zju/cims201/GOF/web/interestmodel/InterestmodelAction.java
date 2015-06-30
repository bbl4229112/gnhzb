package edu.zju.cims201.GOF.web.interestmodel;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;



import edu.zju.cims201.GOF.dao.HibernateUtils;

import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.InterestModel;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.CommentDTO;
import edu.zju.cims201.GOF.rs.dto.InterestModelDTO;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.KtypeDTO;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.TreeNodeDTO;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.service.ServiceException;

import edu.zju.cims201.GOF.service.interestmodel.InterestModelService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.service.webservice.AxisWebService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
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
@Namespace("/interestmodel")
//定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "interestmodel.action", type = "redirect") })

public class InterestmodelAction extends CrudActionSupport<InterestModel> implements ServletResponseAware{

	private static final long serialVersionUID = 8683878162525847072L;


	private InterestModelService imservice;
	
	private UserService userservice;
	private KnowledgeService knowledgeservice;
	private TreeService treeservice;
	
	
	//-- 页面属性 --//
	private Long id;
	
	private InterestModel entity;
	private String json;
	private String itemtype;
	private Long itemId;

	private HttpServletResponse response;
	private int size;
	private int index;
	private boolean collapse;


	

	//-- ModelDriven 与 Preparable函数 --//
	

	public InterestModel getModel() {
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		
//			if(json!=null){
//			
//			JSONReader reader=new JSONReader();
//			HashMap<String, String> jsonMap=(HashMap<String, String>)reader.read(json);
//			
//			InterestModel interestmodel = new InterestModel();
//			
//			interestmodel.setInterestItem(jsonMap.get("interestItem"));
//			interestmodel.setInterestItemType(jsonMap.get("interestItemType"));
//			interestmodel.setUser(jsonMap.get("user"));
//	
//			entity=interestmodel;
//		}	
	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	//江丁丁添加  2013-6-29 获取用户的兴趣知识
	public String listinterestknowledge() throws Exception {
		SystemUser user = userservice.getUser();
		PageDTO resultlist = imservice.getInterestModelKnowledge(user);
		
		JSONUtil ju  = new JSONUtil();
		ju.write(response, resultlist);
		
		return null;
	}
	
	public String listinterest() throws Exception {
		
		SystemUser user = userservice.getUser();
		List<InterestModel> interestlist = imservice.listInterestModelItems(user);
		List<InterestModelDTO> imDTOlist = new ArrayList<InterestModelDTO>();		
		if(interestlist.size()!=0){
			for(InterestModel im:interestlist) {			
				if(im.getInterestItemType().equals("keyword")) {
					long kewordid = Long.parseLong(im.getInterestItem());
					String kewordname = knowledgeservice.getKeyword(kewordid).getKeywordName();
					InterestModelDTO imdto = new InterestModelDTO();
					imdto.setId(im.getId());
					imdto.setInterestItemId(kewordid);
					imdto.setInterestItemName(kewordname);
					imdto.setInterestItemType(im.getInterestItemType());
					imdto.setCounts(im.getUnRead());
					imDTOlist.add(imdto);
				}
				if(im.getInterestItemType().equals("uploader")) {
					long uploaderid = Long.parseLong(im.getInterestItem());
					String uploadername = userservice.getUser(uploaderid).getName();
					InterestModelDTO imdto = new InterestModelDTO();
					imdto.setId(im.getId());
					imdto.setInterestItemId(uploaderid);
					imdto.setInterestItemName(uploadername);
					imdto.setInterestItemType(im.getInterestItemType());
					imdto.setCounts(im.getUnRead());
					imDTOlist.add(imdto);
				}
				if(im.getInterestItemType().equals("domain")) {
					if(im.getIsParent()==0){
						long domainid = Long.parseLong(im.getInterestItem());					
						TreeNode treeNode = treeservice.getTreeNode(domainid);			
				
						InterestModelDTO treeNodeImDTO=new InterestModelDTO();
						InterestModel treeim = imservice.getCommonInterestModel(user, treeNode.getId().toString(), "domain",0);
						treeNodeImDTO.setId(treeim.getId());
						treeNodeImDTO.setInterestItemId( treeNode.getId());
						treeNodeImDTO.setInterestItemName(treeNode.getNodeName());
						treeNodeImDTO.setInterestItemType(treeim.getInterestItemType());
						treeNodeImDTO.setCounts(treeim.getUnRead());
						Set<TreeNode> set=treeNode.getSubNodes();
						if(set.size()!=0){
							ArrayList<InterestModelDTO> arrayTreeList=new ArrayList<InterestModelDTO>();
							for(TreeNode treechild:set){
								arrayTreeList.add(generateTreeNodeImDTO(user,treechild,collapse,false,"domain",(int)domainid));
							}
							Collections.sort(arrayTreeList);
							treeNodeImDTO.setChildren(arrayTreeList);
						}
						
	
						imDTOlist.add(treeNodeImDTO);
					}
					
				}
				if(im.getInterestItemType().equals("category")) {
					if(im.getIsParent()==0){
						long categoryid = Long.parseLong(im.getInterestItem());
						TreeNode treeNode = treeservice.getTreeNode(categoryid);	
				
						InterestModelDTO treeNodeImDTO=new InterestModelDTO();
						InterestModel treeim = imservice.getCommonInterestModel(user, treeNode.getId().toString(), "category",0);
						treeNodeImDTO.setId(treeim.getId());
						treeNodeImDTO.setInterestItemId( treeNode.getId());
						treeNodeImDTO.setInterestItemName(treeNode.getNodeName());
						treeNodeImDTO.setInterestItemType(treeim.getInterestItemType());
						treeNodeImDTO.setCounts(treeim.getUnRead());
						Set<TreeNode> set=treeNode.getSubNodes();
						if(set.size()!=0){
							ArrayList<InterestModelDTO> arrayTreeList=new ArrayList<InterestModelDTO>();
							for(TreeNode treechild:set){
								arrayTreeList.add(generateTreeNodeImDTO(user,treechild,collapse,false,"category",(int)categoryid));
							}
							Collections.sort(arrayTreeList);
							treeNodeImDTO.setChildren(arrayTreeList);
						}			
						
						imDTOlist.add(treeNodeImDTO);
					}
					
				}
			}
		}
		JSONWriter writer = new JSONWriter();
		String imstring = writer.write(imDTOlist);
		response.getWriter().print(imstring);
		return null;
	}
	
	private InterestModelDTO generateTreeNodeImDTO(SystemUser user,TreeNode treeNode,boolean collapse,boolean isnotroot,
			String itemType,int isParent){
		
		InterestModelDTO timdto=new InterestModelDTO();
		InterestModel tim = imservice.getCommonInterestModel(user, treeNode.getId().toString(),itemType,isParent);
		timdto.setId(tim.getId());
		timdto.setInterestItemId( treeNode.getId());
		timdto.setInterestItemName(treeNode.getNodeName());
		timdto.setInterestItemType(tim.getInterestItemType());
		timdto.setCounts(tim.getUnRead());
					
		Set<TreeNode> set=treeNode.getSubNodes();
		if(set.size()!=0){
			ArrayList<InterestModelDTO> arrayList=new ArrayList<InterestModelDTO>();
			for(TreeNode child:set){
				arrayList.add(generateTreeNodeImDTO(user,child,collapse,isnotroot,itemType,isParent));
			}
			Collections.sort(arrayList);
			timdto.setChildren(arrayList);
		}
		
		
		return timdto;
	}
	
	@Override
	public String input() throws Exception {
		
		return INPUT;
	}

	
	@Override
	public String save() throws Exception {
		
		return null;
	}
	

	public String savekeyword() throws Exception {
		
		String keywordname = json;
		Keyword keyword = knowledgeservice.SearchAndSaveKeyword(keywordname);
	
		if(!(imservice.isInterestModelExist(userservice.getUser(), keyword.getId().toString(), "keyword"))){
			List<String> keywordids = new ArrayList<String>();
			keywordids.add(keyword.getId().toString());
			imservice.addInterestModelItems(userservice.getUser(), keywordids, "keyword");
			JSONUtil.write(response, "定制成功");
		}else{
			JSONUtil.write(response, "已定制");
		}
		
		return null;
	}
	
	public String saveuploader() throws Exception {
		System.out.println("+++++++++++++"+userservice.isNameExist(json));
		if(!userservice.isNameExist(json)){
			JSONUtil.write(response, "该上传者不存在,请重新输入");
		}else{
			SystemUser uploader = userservice.getOneUser(json);			
			if(!(imservice.isInterestModelExist(userservice.getUser(), uploader.getId().toString(), "uploader"))){
				List<String> uploadids = new ArrayList<String>();
				uploadids.add(uploader.getId().toString());
				imservice.addInterestModelItems(userservice.getUser(), uploadids, "uploader");
				JSONUtil.write(response, "定制成功");
			}else{
				JSONUtil.write(response, "已定制");
			}
			
		}		
		return null;
	}
	public String savedomainselect() throws Exception {
		List domainlist = new ArrayList();		
		JSONReader jr = new JSONReader();
		List im = (ArrayList) jr.read(json);
		for(Object kk:im) {
			String domain = kk.toString();
			//判断存的时候兴趣模型重复了
			if(!imservice.isTreeInterestModelExist(userservice.getUser(), domain, "domain",0)){
				domainlist.add(domain);
			} 
		
		}
		
	    imservice.addInterestModelItems(userservice.getUser(), domainlist, "domain");	    
	    
		return null;
	}	
	
	public String savecategoryselect() throws Exception {		
		List categorylist = new ArrayList();
		JSONReader jr = new JSONReader();
		List im = (ArrayList) jr.read(json);
		System.out.println("11111111"+im);
		for(Object kk:im) {
			String category = kk.toString();
			//判断存的时候兴趣模型重复了
			if(!(imservice.isTreeInterestModelExist(userservice.getUser(), category, "category",0))){
				categorylist.add(category);
			}
			
		}
		
		imservice.addInterestModelItems(userservice.getUser(), categorylist, "category");
		return null;
	}		
		

	@Override
	public String delete() throws Exception {
		//需前台传一个SystemUser的id
//		Long id = new Long(3);
//		System.out.println("已到Action了呵呵");
//		userServiceImpl.deleteUser(id);
		
		return null;
	}
	
	public String deleteUserInterest() throws Exception {
		imservice.deleteInterestModelItem(id);		
		return null;
	}
	
	public String deleteTreeInterest() throws Exception {
		imservice.deleteAllSubNodeIm(id);		
		return null;
	}
		

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public InterestModel getEntity() {
		return entity;
	}

	public void setEntity(InterestModel entity) {
		this.entity = entity;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}
		
	public boolean isCollapse() {
		return collapse;
	}

	public void setCollapse(boolean collapse) {
		this.collapse = collapse;
	}

	public InterestModelService getImservice() {
		return imservice;
	}
	@Autowired
	public void setImservice(InterestModelService imservice) {
		this.imservice = imservice;
	}

	public UserService getUserservice() {
		return userservice;
	}
    
	@Autowired
	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}

	public KnowledgeService getKnowledgeservice() {
		return knowledgeservice;
	}

	@Autowired
	public void setKnowledgeservice(KnowledgeService knowledgeservice) {
		this.knowledgeservice = knowledgeservice;
	}

	public TreeService getTreeservice() {
		return treeservice;
	}

	@Autowired
	public void setTreeservice(TreeService treeservice) {
		this.treeservice = treeservice;
	}
	
	
	
	
	
	
	
	

}
