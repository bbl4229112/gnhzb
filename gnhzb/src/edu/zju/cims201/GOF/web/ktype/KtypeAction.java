package edu.zju.cims201.GOF.web.ktype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;

import org.stringtree.json.JSONWriter;

import edu.zju.cims201.GOF.dao.knowledge.AuthorDao;
import edu.zju.cims201.GOF.dao.knowledge.KeepTreeNodeDao;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.rs.dto.KtypeDTO;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.PropertyDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.approvalFlow.ApprovalFlowService;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowService;
import edu.zju.cims201.GOF.service.knowledge.AuthorService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.comment.CommentService;
import edu.zju.cims201.GOF.service.knowledge.keep.KeepService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeDeleteService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 属性管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数. 演示带分页的管理界面.
 * 
 * @author cwd
 */
// 定义URL映射对应/ktype/ktype.action
@Namespace("/knowledge/ktype")
// 定义名为reload的result重定向到property.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "ktype.action", type = "redirect") })
public class KtypeAction extends CrudActionSupport<Ktype> implements
		ServletResponseAware {

	// CrudActionSupport里的参数
	private static final long serialVersionUID = 8683878162525847072L;
	@Resource(name = "ktypeServiceImpl")
	private KtypeService ktypeservice;
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService knowledgeservie;
	@Resource(name = "approvalFlowServiceImpl")
	private ApprovalFlowService approvalFlowService;
	@Resource(name = "borrowFlowServiceImpl")
	private BorrowFlowService borrowFlowService;
	@Resource(name = "messageServiceImpl")
	private MessageService messageService;
	@Resource(name = "keepServiceImpl")
	private KeepService keepService;
	
	
	
	//新加删除所有知识涉及的表记录
	@Resource(name = "ktypeDeleteServiceImpl")
	private KtypeDeleteService ktypedeleteservice;
	

	public KtypeDeleteService getKtypedeleteservice() {
		return ktypedeleteservice;
	}

	public void setKtypedeleteservice(KtypeDeleteService ktypedeleteservice) {
		this.ktypedeleteservice = ktypedeleteservice;
	}

	@Resource(name = "commentServiceImpl")
	private CommentService commentService;
	// /** 进行增删改操作后,以redirect方式重新打开action默认页的result名.*/
	// public static final String RELOAD = "reload";

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	// -- 页面属性 --//
	private Long id;
	private Ktype entity;
	private String json;
	private int size;
	private int index;
	private Long propertyId;
	private List<Property> commonproperties = new ArrayList<Property>();
	private List<Property> extendproperties = new ArrayList<Property>();
	private Long[] ktypepropertysid = new Long[] {};
	private String[] ktypepropertysshowname = new String[] {};
	private boolean[] ktypepropertyssearchable = new boolean[] {};
	private HttpServletResponse response;
	private boolean withoutCommon;
	// private List<Property> properties=new ArrayList<Property>();
	private Page<Ktype> page = new Page<Ktype>(5);// 每页5条记录

	public Ktype getModel() {
		// TODO Auto-generated method stub
		return entity;
	}

	public KtypeService getKtypeservice() {
		return ktypeservice;
	}

	public void setKtypeservice(KtypeService ktypeservice) {
		this.ktypeservice = ktypeservice;
	}
	// 潘雷加
	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	public Ktype getEntity() {
		return entity;
	}

	public void setEntity(Ktype entity) {
		this.entity = entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = ktypeservice.getKtype(id);
		} else {
			entity = new Ktype();
		}

	}

	@Override
	public String delete() throws Exception {
		

		return RELOAD;
	}
	public String deletektype() throws Exception {
		if(null!=id){
			if(id==1)
				throw new ServiceException("通用基本知识类型不能删除！");
			JSONUtil ju  = new JSONUtil();
			List<MetaKnowledge> mks=this.knowledgeservie.getKnowledgesByType(ktypeservice.getKtype(id));
			if(mks.size()!=0){
				
				try {
					for(MetaKnowledge k:mks){
						
						try {
							//删除知识涉及的审批流和借阅流
							this.approvalFlowService.deleteApprovalFlow(k);
							this.borrowFlowService.deleteBorrewFlow(k);
							//删除知识涉及的消息
							this.messageService.deleteMessage(k);
							
							//删除知识涉及的附件
							this.ktypedeleteservice.deleteAttachmentByK(k);
							
							//删除知识涉及的评论
							this.commentService.deleteCommentByKnowledge(k);
							
							
							//删除知识涉及的Flow
							this.ktypedeleteservice.deleteFlowByK(k);
							
							
							//删除知识涉及的收藏
							this.keepService.deleteAllKeepsByKnowledge(k);
							
							
							//删除知识涉及的KnowledgeRelated
//							this.ktypedeleteservice.deleteKnowledgeRelatedByK(k);
							
							//删除知识涉及的Rating
							this.ktypedeleteservice.deleteRatingByK(k);
							
							
							this.knowledgeservie.deleteKnowledgeDeep(k);
						} catch (Exception e) {
							e.printStackTrace();
							
							ju.write(response, "删除失败！");
							return "删除失败！";
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "系统错误";
				}
			}
//			this.ktypedeleteservice.deleteKTypePropertydByK(ktypeservice.getKtype(id));
			String ktypename = ktypeservice.getKtype(id).getKtypeName();
			ktypeservice.deleteKtype(id);
			ju.write(response, ktypename+"模板删除成功！");
			
		}
		return null;
	}
	
	@Override
	public String input() throws Exception {
//		commonproperties = ktypeservice.listCommonProperties();
//		extendproperties = ktypeservice.listExpandedProperties();
		return INPUT;
	}
	
	
	public String creatktypeinput() throws Exception {	
		commonproperties = ktypeservice.listCommonProperties();
		extendproperties = ktypeservice.listExpandedProperties();
		if(id != null){
			//如果是编辑模板，则要去掉common的和不为空的。因为common的不需要用，而因为这个知识模板已经上传了知识，不为空的话，那些知识的这个属性怎么办。
			Ktype ktype = ktypeservice.getKtype(id);
			List<KtypeProperty> list = ktype.getKtypeproperties();
			for(KtypeProperty kp : list){
				Property p = kp.getProperty();
				if(!p.getIsCommon()){
					extendproperties.remove(p);
				}
			}
			ArrayList<Property> extendproperties2 = new ArrayList<Property>();
			for(Property p2 : extendproperties){
				if(!p2.getIsNotNull()){
					extendproperties2.add(p2);
				}
			}
			extendproperties = extendproperties2;
		}
		List<Property> listproperties = new ArrayList<Property>();
		if(id == null){
			listproperties.addAll(commonproperties);
		}
		listproperties.addAll(extendproperties);
		
		JSONWriter writer = new JSONWriter();
		
		String propertystring = writer.write(listproperties);
		
		response.getWriter().print(propertystring);
		
		return null;
	}

	@Override
	public String list() throws Exception {

		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.ASC);
		}
		page = ktypeservice.listKtypes(page);
		return SUCCESS;

	}

	public String listAllktype() throws Exception {
		PageDTO pd = new PageDTO();
		JSONWriter writer = new JSONWriter();
		List<Ktype> ktypes = ktypeservice.listKtypes(withoutCommon);
		List<KtypeDTO> dots = new ArrayList<KtypeDTO>();
		for (Ktype ktype : ktypes) {
			KtypeDTO kdto = new KtypeDTO();
			kdto.setId(ktype.getId());
			kdto.setName(ktype.getName());
			kdto.setKtypeName(ktype.getKtypeName());
			kdto.setTemplatePath(ktype.getTemplatePath());
		
			kdto.setTemplateXMLPath(ktype.getTemplateXMLPath());
			// System.out.println("试试前"+ktype.getTemplateXMLPath());
			kdto.setClassName(ktype.getClassName());
			kdto.setTableName(ktype.getTableName());
			// kdto.setProperty(ktype.getKtypeproperties());
			// System.out.println("试试前"+ktype.getKtypeproperties());
			dots.add(kdto);
		}

		// System.out.println("试试"+dots);

		List<KtypeDTO> subList = new ArrayList<KtypeDTO>();

		for (int i = index * size; i < ((index + 1) * size < dots.size() ? (index + 1)
				* size
				: dots.size()); i++) {
			subList.add(dots.get(i));
		}
		System.out.println("dots size:" + dots.size());

		pd.setData(subList);
		pd.setTotal(dots.size());
		int totalPage;
		if (dots.size() % size == 0) {
			totalPage = dots.size() / size;
		} else {
			totalPage = dots.size() / size + 1;
		}

		pd.setTotalPage(dots.size() / size + 1);


		JSONWriter writer1 = new JSONWriter();
		String ktypestring = writer1.write(pd);
		response.getWriter().print(ktypestring);

		return null;

	}

	public String listAllktypeWithoutPage() throws Exception {
		JSONWriter writer = new JSONWriter();

		// long tBegin = System.currentTimeMillis();
		List<Ktype> ktypes = ktypeservice.listKtypes(withoutCommon);
		List<KtypeDTO> dots = new ArrayList<KtypeDTO>();

		for (Ktype ktype : ktypes) {
			KtypeDTO kdto = new KtypeDTO();
			kdto.setName(ktype.getName());
			kdto.setKtypeName(ktype.getKtypeName());
			kdto.setId(ktype.getId());
			dots.add(kdto);
		}
		String ktypestring = writer.write(dots);

		response.getWriter().print(ktypestring);
		return null;

	}

	public String listKtypeProperty() throws Exception {		
		JSONWriter writer = new JSONWriter();
		List<PropertyDTO> dtos = new ArrayList<PropertyDTO>();		
		if(id.equals(10000L)) {			
			entity = ktypeservice.getKtype("Question");	
		} else if(id.equals(20000L)){//江丁丁添加 2013-6-14 专家黄页
			entity = ktypeservice.getKtype("Article");	
		} else {			
			entity = ktypeservice.getKtype(id);				
		}
		for (KtypeProperty kps : entity.getKtypeproperties()) {
			if(kps != null){
				
				//System.out.println(kps.getShowname());
				Property property = kps.getProperty();
				PropertyDTO pdto = new PropertyDTO();
				pdto.setDescription(kps.getShowname());
				pdto.setName(property.getName());
				pdto.setIsVisible(property.getIsVisible());
				pdto.setPropertyType(property.getPropertyType());
				pdto.setVcomponent(kps.getVcomponent());
				pdto.setValuelist(kps.getListvalue());
				pdto.setSearchable(kps.isSearchable());
				pdto.setIsNotNull(property.getIsNotNull());
				pdto.setLength(property.getLength());
				pdto.setId(kps.getId());
				dtos.add(pdto);
			}
		}
		//Collections.sort(dtos);
		String jsondata = writer.write(dtos);
		//System.out.println(jsondata);
		response.getWriter().print(jsondata);

		return null;

	}

	//列出所有知识类型（不是知识模板）
	public String listKnowledgetype() throws Exception {
		JSONWriter writer = new JSONWriter();
		List<ObjectDTO> dtos = new ArrayList<ObjectDTO>();
		List<Knowledgetype>knowledgetypelist = ktypeservice.listKnowledgetype();
		for (Knowledgetype ktp : knowledgetypelist) {


			ObjectDTO ktypedto = new ObjectDTO(ktp.getId(),ktp.getKnowledgeTypeName());
			dtos.add(ktypedto);
		}
        JSONUtil.write(response, dtos);
		return null;

	}
	
	@Override
	public String save() throws Exception {

		JSONUtil ju  = new JSONUtil();
        HashMap formvalue =(HashMap)ju.read(json);
		

		if (null == formvalue.get("name")
				|| formvalue.get("name").toString().trim().equals("")
				|| null == formvalue.get("ktypename")
				|| formvalue.get("ktypename").toString().trim().equals("")) {
			response.getWriter().print("错误！请检查类型中文名或英文名！不允许为空！");
			return null;

		}
		String name = formvalue.get("name").toString().toLowerCase();
		name = WordUtils.capitalize(name);
		String ktypeName = formvalue.get("ktypename").toString().toUpperCase();

		if (ktypeservice.isKtypeExist("name", name)
				|| ktypeservice.isKtypeExist("ktypeName", ktypeName)) {
			response.getWriter().print("错误！ 该知识类型已经存在，请检查类型中文名或英文名！系统不允许重复！");
			return null;

		}

		Ktype ktype = new Ktype();
		ktype.setName(name);
		ktype.setKtypeName(ktypeName);

		List<HashMap> plist = (List<HashMap>)formvalue.get("propertylist");
	
		List<KtypeProperty>kplist=new ArrayList<KtypeProperty>();
		for (HashMap propertyhash : plist) {
			KtypeProperty kp = new KtypeProperty();
			 Property property = ktypeservice.getProperty(Long
					.valueOf(propertyhash.get("id").toString()));
			 kp.setProperty(property);
			 kp.setShowname(propertyhash.get("description").toString());

			
			
                if(null!=propertyhash.get("searchable")) 
                kp.setSearchable(propertyhash.get("searchable").toString().equals("true"));
                else
                kp.setSearchable(true);
				
				if (property.getIsCommon()) {
					if (property.getName().equals("securitylevel")) {
						kp.setVcomponent("combo");
						kp.setListvalue(Constants.SECURITY_LEVELS);
					} else if (property.getName().equals("abstracttext")) {
						kp.setVcomponent("textarea");

					} else if (property.getName().equals("uploadtime")) {
						kp.setVcomponent("date");

					} else if (property.getName().equals("domainnode")) {
						kp.setVcomponent("domaintree");

					} else if (property.getName().equals("categories")) {
						kp.setVcomponent("catagorytree");

					}else
						kp.setVcomponent("textfield");
				}
				else{
				if (null != propertyhash.get("vcomponent")) {

					kp.setVcomponent(propertyhash.get("vcomponent").toString());
				}
				if (null != propertyhash.get("listvalue")&&null!=propertyhash.get("vcomponent")&&propertyhash.get("vcomponent").toString().equals("combo")) {

					String listvalue = propertyhash.get("listvalue").toString();
					listvalue = listvalue.replaceAll("，", ",");
					kp.setListvalue(listvalue);
				}
				
				}
				kplist.add(kp);
			}
		ktypeservice.createKtype(ktype, kplist);
		response.getWriter().print("恭喜！知识创建成功，请重新启动服务，以完成配置！");
		return null;
	}

	//修改知识模板，增加属性
	public String editKTypeSave() throws Exception {

		JSONUtil ju  = new JSONUtil();
        HashMap formvalue =(HashMap)ju.read(json);
		Ktype ktype = ktypeservice.getKtype(id);

		List<HashMap> plist = (List<HashMap>)formvalue.get("propertylist");
	
		List<KtypeProperty>kplist=new ArrayList<KtypeProperty>();
		List<KtypeProperty> priviousprolist = ktype.getKtypeproperties();
		kplist.addAll(priviousprolist);
		for (HashMap propertyhash : plist) {
			KtypeProperty kp = new KtypeProperty();
			 Property property = ktypeservice.getProperty(Long
					.valueOf(propertyhash.get("id").toString()));
			 kp.setProperty(property);
			 kp.setShowname(propertyhash.get("description").toString());

			
			
                if(null!=propertyhash.get("searchable")) 
                kp.setSearchable(propertyhash.get("searchable").toString().equals("true"));
                else
                kp.setSearchable(true);
				
				if (property.getIsCommon()) {
					if (property.getName().equals("securitylevel")) {
						kp.setVcomponent("combo");
						kp.setListvalue(Constants.SECURITY_LEVELS);
					} else if (property.getName().equals("abstracttext")) {
						kp.setVcomponent("textarea");

					} else if (property.getName().equals("uploadtime")) {
						kp.setVcomponent("date");

					} else if (property.getName().equals("domainnode")) {
						kp.setVcomponent("domaintree");

					} else if (property.getName().equals("categories")) {
						kp.setVcomponent("catagorytree");

					}else
						kp.setVcomponent("textfield");
				}
				else{
				if (null != propertyhash.get("vcomponent")) {

					kp.setVcomponent(propertyhash.get("vcomponent").toString());
				}
				if (null != propertyhash.get("listvalue")&&null!=propertyhash.get("vcomponent")&&propertyhash.get("vcomponent").toString().equals("combo")) {

					String listvalue = propertyhash.get("listvalue").toString();
					listvalue = listvalue.replaceAll("，", ",");
					kp.setListvalue(listvalue);
				}
				
				}
				kplist.add(kp);
			}
		ktypeservice.updateKtype(ktype,kplist);
		response.getWriter().print("恭喜！知识创建成功，请重新启动服务，以完成配置！");
		return null;
	}
	
	
	public Page<Ktype> getPage() {
		return page;
	}

	public void setPage(Page<Ktype> page) {
		this.page = page;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Property> getCommonproperties() {
		return commonproperties;
	}

	public void setCommonproperties(List<Property> commonproperties) {
		this.commonproperties = commonproperties;
	}

	public List<Property> getExtendproperties() {
		return extendproperties;
	}

	public void setExtendproperties(List<Property> extendproperties) {
		this.extendproperties = extendproperties;
	}

	public Long[] getKtypepropertysid() {
		return ktypepropertysid;
	}

	public void setKtypepropertysid(Long[] ktypepropertysid) {
		this.ktypepropertysid = ktypepropertysid;
	}

	public String[] getKtypepropertysshowname() {
		return ktypepropertysshowname;
	}

	public void setKtypepropertysshowname(String[] ktypepropertysshowname) {
		this.ktypepropertysshowname = ktypepropertysshowname;
	}

	public boolean[] getKtypepropertyssearchable() {
		return ktypepropertyssearchable;
	}

	public void setKtypepropertyssearchable(boolean[] ktypepropertyssearchable) {
		this.ktypepropertyssearchable = ktypepropertyssearchable;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
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


	public Long getPropertyId() {
		return propertyId;
	}

	public boolean isWithoutCommon() {

		return withoutCommon;
	}

	public void setWithoutCommon(boolean withoutCommon) {
		this.withoutCommon = withoutCommon;
	}





}
