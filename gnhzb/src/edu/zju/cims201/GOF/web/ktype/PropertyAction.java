package edu.zju.cims201.GOF.web.ktype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;

import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.rs.dto.KtypeDTO;
import edu.zju.cims201.GOF.rs.dto.PropertyDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;



/**
 * 属性管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 * 
 * @author cwd
 */
//定义URL映射对应/ktype/property.action
@Namespace("/knowledge/property")
//定义名为reload的result重定向到property.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "property.action", type = "redirect") })

public class PropertyAction extends CrudActionSupport<Property> implements ServletResponseAware{

//	CrudActionSupport里的参数
	private static final long serialVersionUID = 8683878162525847072L;
	private List<Property> commonproperties1=new ArrayList<Property>();
	private List<Property> extendproperties1=new ArrayList<Property>();
	@Resource(name="ktypeServiceImpl")
	private KtypeService ktypeservice;
//	@Resource(name="userServiceImpl")
//	private UserService userservice;
//	@Resource(name="knowledgeServiceImpl")
//	private KnowledgeService knowledgeservice;
	
//	/** 进行增删改操作后,以redirect方式重新打开action默认页的result名.*/
//	public static final String RELOAD = "reload";

	//-- 页面属性 --//
	private Long id;
	private Property entity;

	private Page<Property> page = new Page<Property>(5);//每页5条记录
//	private Long checkedPropertyId; //页面中钩选的属性id
	
	
	
	private String json;
	private int index;
	private int size;
	

	private HttpServletResponse response;
	
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Property getModel() {
		// TODO Auto-generated method stub
		return entity;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = ktypeservice.getProperty(id);
		} else {
			entity = new Property();
		}
		
	}
	@Override
	public String delete() throws Exception {	
		
		return RELOAD;
	}
	public String deleteproperty() throws Exception {	
		
		if(null!=id){
			if(id<=17)
				throw new ServiceException("不得删除通用知识类型属性！");
			ktypeservice.deleteProperty(id);
			
		}
		return null;
	}
	
	@Override
	public String input() throws Exception {
		//commentservice.getComment(commentid)(entity);
//		ktypeservice.createProperty(entity);
		Long iid = entity.getId();
		return INPUT;
	}
	
	@Override
	public String list() throws Exception {
		
//	//	System.out.println("由delete展示出来了");
//	//	commentservice.getComments(knowledgeservice.getMetaknowledge(new Long(1)));
////  三种情况
//		if (!page.isOrderBySetted()) {
//			page.setOrderBy("id");
//			page.setOrder(Page.ASC);
//		}
////		page = ktypeservice.getComments(knowledgeservice.getMetaknowledge(new Long(1)),page);
//		page = ktypeservice.listExpandedProperties(page);
		return SUCCESS;

	}
	
	public String creatpropertyinput() throws Exception {
//		System.out.println("index:"+index);
//		System.out.println("size:"+size);
		PageDTO pd = new PageDTO();
		
		commonproperties1=ktypeservice.listCommonProperties();
		extendproperties1=ktypeservice.listExpandedProperties();
		
		List<Property> listproperties1 = new ArrayList<Property>();
		listproperties1.addAll(commonproperties1);
		listproperties1.addAll(extendproperties1);

		
		List<PropertyDTO> pots = new ArrayList<PropertyDTO>();
		for (Property property :listproperties1) {
			PropertyDTO pdto = new PropertyDTO();
			pdto.setId(property.getId());
			pdto.setName(property.getName());
			pdto.setDescription(property.getDescription());
			pdto.setIsNotNull(property.getIsNotNull());
			pdto.setIsVisible(property.getIsVisible());
			pdto.setIsCommon(property.getIsCommon());
			pdto.setPropertyType(property.getPropertyType());			
			pdto.setLength(property.getLength());
			pdto.setVcomponent(property.getVcomponent());
	
			pots.add(pdto);
		}
		
		
		List<PropertyDTO> subList = new ArrayList<PropertyDTO>();
		
		for(int i=index*size;i<((index+1)*size<pots.size()?(index+1)*size:pots.size());i++){
			subList.add(pots.get(i));
		}
		System.out.println("pots size:"+pots.size());
		
		pd.setData(subList);
		pd.setTotal(pots.size());
		int totalPage;
		if(pots.size()%size == 0){
			totalPage = pots.size()/size;
		}else{
			totalPage = pots.size()/size+1;
		}
		pd.setTotalPage(pots.size()/size+1);
		
		JSONWriter writer = new JSONWriter();
        String propertystring=writer.write(pd);  
       	response.getWriter().print(propertystring);
		
		
		return null;
	}
	

	

	@Override
	public String save() throws Exception {
		//commentservice.addComment(content, userid, knowledgeID);
		System.out.println(json);
		
		JSONReader jr = new JSONReader();
		HashMap mm = (HashMap) jr.read(json);
		
		
//	判断属性名称是否已经存在	
	
		if(null==mm.get("description")||null==mm.get("description"))
		{   
			//response.getWriter().print("错误！知识属性名称不能为空");
			  JSONUtil.write(response, "错误！知识属性名称不能为空");
			return null;
			
		}
		String propertyName=(String)mm.get("name");
		String description=(String)mm.get("description");
		propertyName=propertyName.toLowerCase();
		description=description.toUpperCase();
//判断知识是否已经存在		
		
		  if(ktypeservice.isPropertyExist("name",propertyName)||ktypeservice.isPropertyExist("description",description))
		{
			//  System.out.println("???????????错误！ 该知识属性已经存在,系统不允许重复！");
			  JSONUtil.write(response, "错误！ 该知识属性已经存在,系统不允许重复！");
	    	//response.getWriter().print("错误！ 该知识属性已经存在,系统不允许重复！");
			return null;
	    	
		}
		 
		Property p = new Property();
		p.setName(propertyName);
		p.setDescription(description);
		p.setIsNotNull(mm.get("isNotNull").toString().equals("true"));
		p.setIsVisible(mm.get("isVisible").toString().equals("true"));

		p.setPropertyType((String)mm.get("propertyType"));
		p.setIsCommon(false);
		
		p.setLength(Integer.valueOf((String)mm.get("length")));
	
		System.out.println(mm.get("vcomponent"));
		
		System.out.println(p);
		ktypeservice.createProperty(p);
		JSONUtil.write(response, "创建成功！");
		return null;
	}
	
	
	

	public Page<Property> getPage() {
		return page;
	}

	public void setPage(Page<Property> page) {
		this.page = page;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

//	public CommentService getCommentServiceImpl() {
//		return commentServiceImpl;
//	}
//	
//	@Autowired
//	public void setCommentServiceImpl(CommentService commentServiceImpl) {
//		this.commentServiceImpl = commentServiceImpl;
//	}


	
	
	
	

}
