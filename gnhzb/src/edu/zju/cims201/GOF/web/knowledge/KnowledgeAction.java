package edu.zju.cims201.GOF.web.knowledge;

import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.BooleanClause;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;


import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.JavaScriptUtils;
import org.springside.modules.orm.Page;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;


import edu.zju.cims201.GOF.aop.message.KnowledgeMessage;
import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.Attachment;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Expert;
import edu.zju.cims201.GOF.hibernate.pojo.HotWord;
import edu.zju.cims201.GOF.hibernate.pojo.InterestModel;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;
import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Version;
import edu.zju.cims201.GOF.rs.dto.AvidmKnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.CommentDTO;
import edu.zju.cims201.GOF.rs.dto.CommentRecordDTO;
import edu.zju.cims201.GOF.rs.dto.ExpertDTO;
import edu.zju.cims201.GOF.rs.dto.InterestModelDTO;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.PropertyDTO;
import edu.zju.cims201.GOF.rs.dto.VersionDTO;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.interestmodel.InterestModelService;
import edu.zju.cims201.GOF.service.knowledge.FullTextService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.logging.AddUserScore;
import edu.zju.cims201.GOF.service.logging.SysBehaviorListService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.systemFile.FileService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.service.webservice.AxisWebService;
import edu.zju.cims201.GOF.service.webservice.ConvertFlashWebService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;
import java.text.DateFormat;  

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
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "knowledge.action", type = "redirect"),
			@Result(name = "questionview", location = "/aaa.jsp"),
			@Result(name = "searchlist", location = "/WEB-INF/content/klist.jsp")}
)
public class KnowledgeAction extends CrudActionSupport<MetaKnowledge> implements
		ServletResponseAware {

	// CrudActionSupport里的参数
	private static final long serialVersionUID = 8683878162525847072L;
	@Resource(name = "ktypeServiceImpl")
	private KtypeService ktypeservice;
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice;
	@Resource(name = "treeServiceImpl")
	private TreeService treeservice;
	@Resource(name = "userServiceImpl")
	private UserService userservice;
	@Resource(name = "fullTextServiceImpl")
	private FullTextService fulltextservice;
	@Resource(name = "fileServiceImpl")
	private FileService fileservcie;
	@Resource(name="axisWebService")
	private AxisWebService axisWebService;
	@Resource(name="sysBehaviorListServiceImpl")
	private SysBehaviorListService sysBehaviorListService;
	@Resource(name="sysBehaviorLogServiceImpl")
	private SysBehaviorLogService sysBehaviorLogService;
	@Resource(name="interestModelServiceImpl")
	private InterestModelService interestModelService;
	@Resource(name="addUserScore")
	AddUserScore addUserScore;
	@Resource(name = "convertFlashWebServiceImpl")
	private ConvertFlashWebService convertFlashWebService;	
	@Resource(name = "interestModelServiceImpl")
	private InterestModelService imservice;	
	@Resource(name="commonDao")
	CommonDao dao;
	@Resource(name="messageServiceImpl")
	private MessageService messageservice;

	// /** 进行增删改操作后,以redirect方式重新打开action默认页的result名.*/
	// public static final String RELOAD = "reload";
//#######################################################################################################
	//pl 做个知识类型字符串
	private String ktypenamestring;
	public String getKtypenamestring() {
		return ktypenamestring;
	}

	public void setKtypenamestring(String ktypenamestring) {
		this.ktypenamestring = ktypenamestring;
	}
	
//#######################################################################################################
	// -- 页面属性 --//
	private Long id;
	// 用于知识采集的知识类型属性
	private Long ktypeid;
	// 用于知识采集的知识源文件
	private File sourcefile;
	// 用于知识快速上传知识的路径
	private String sourcefilepath;
	// 用于知识采集的知识域
	private Long domainid;
	// 用于知识采集的属性内容jason格式
	private String formvalue;
	private String attachfile;
	private String questioncontent;
	private String questionsupplement;
	// 版本信息
	private Long versionid;
	private String versionNumber;
	//知识搜索需要的分页信息
	private int size;
	private int index;
	//全文检索需要的信息
	private String key;
	private MetaKnowledge entity;
	private HttpServletResponse response;
	//知识分类和域
	private String domainids;
	private String categoryids;
	//借阅的流程id
	private Long borrowFlowId;
	//avidm搜索
	private String avidmip;
	//引用文献ids
	private String citation;
	private String isNewVersion;
	private String json;
	
	//江丁丁添加 2013-6-16 博文的内容 
	private String articlecontent;
	
	//pl 申请改域说明文字############################################################################################
	private Long messageId;
	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	private String applytext;

	public String getApplytext() {
		return applytext;
	}

	public void setApplytext(String applytext) {
		this.applytext = applytext;
	}
	private String approvalresult;
	public String getApprovalresult() {
		return approvalresult;
	}

	public void setApprovalresult(String approvalresult) {
		this.approvalresult = approvalresult;
	}
	private Long receiverid;
	
	public Long getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(Long receiverid) {
		this.receiverid = receiverid;
	}

	//###############################################################################################################
	//private Long borrowFlowNodeId;
	//private PageDTO page = new Page<Property>(5);// 每页5条记录
	public MetaKnowledge getModel() {
		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			 entity = kservice.getMetaknowledge(id);
		} else {
			entity = new MetaKnowledge();
		}

	}


	@Override
	public String delete() throws Exception {
	
		MetaKnowledge k=kservice.getMetaknowledge(id);
	    if(null!=k.getStatus()&&k.getStatus().equals("0")){
		kservice.deleteKnowledge(k);
		JSONUtil.write(response, "删除成功！");
		}
	    else{
	    	
	    JSONUtil.write(response, "不能删除已入库知识！");
	    	
	    }
		return null;
	}
	
	public String hideKnowledge() throws Exception {
		
		MetaKnowledge k=kservice.getMetaknowledge(id);
		k.setIsvisible(false);
		kservice.updateKnowledge(k);
		JSONUtil.write(response, "删除成功！");
		
		return null;
	}

	@Override
	public String input() throws Exception {

		Long iid = entity.getId();
		return INPUT;
	}

	@Override
	public String list() throws Exception {

//		if (!page.isOrderBySetted()) {
//			page.setOrderBy("id");
//			page.setOrder(Page.ASC);
//		}
//
//		page = ktypeservice.listExpandedProperties(page);
		return SUCCESS;

	}

	public String upload() throws Exception {

		return "upload";
	}
	
	
	//知识展示
	public String view() throws Exception {
		
		MetaKnowledge k=kservice.getMetaknowledge(id);	
		System.out.println("-----------------------------"+k.getTitlename()+k.getKtype().getName());
		if(k.getKtype().getName().equals("Question"))
			{return "questionview";}

		return "view";
	}

	@Override
	public String save() throws Exception {		
		
		String realpathdir = Constants.SOURCEFILE_PATH_TEMP;		
		// 构建文件名称
		boolean nothasdomain=true;

		try {
            
			String targetDirectory = realpathdir;
			String targetFileName = UUID.randomUUID().toString() + ".doc";
			File target = new File(targetDirectory, targetFileName);
			
			FileUtils.copyFile(sourcefile, target);
			Long sourcefileid=fileservcie.save(target, targetFileName);
			//转换flash并将flash保存到数据库
			convertFlashWebService.ConvertAndSaveFlash(sourcefileid);
			List<Property> propertyValues = new ArrayList<Property>();
			
			Ktype ktype = ktypeservice.getKtype(ktypeid);
			HashMap hpropertyValues =getJSONvalue();

			for (KtypeProperty kp : ktype.getKtypeproperties()) {
				Property p = kp.getProperty();
				if (null != hpropertyValues.get(p.getName())&&
						 (!hpropertyValues.get(p.getName()).equals(""))) {
					boolean isnotSet = true;

					
					//单独处理知识类型
					if (null != hpropertyValues.get(p.getName())&&
							 (!hpropertyValues.get(p.getName()).equals("")&&p.getName().equals("knowledgetype"))) {	
					String knowledgetypename = hpropertyValues.get("knowledgetype")
					.toString();
					
							Knowledgetype knowledgetype = kservice.SearchAndSaveKnowledgetype(knowledgetypename);
						
					
					p.setValue(knowledgetype);
					isnotSet = false;
					propertyValues.add(p);
				
				}
					
                 	// 单独处理作者，因为在知识中作者是作为对象属性的
					if (p.getName().equals("kauthors")
							&& null != hpropertyValues.get("kauthors")) {
						String Authors_string = hpropertyValues.get("kauthors")
								.toString();
						Authors_string = Authors_string.replaceAll("  ", " ");
						Authors_string = Authors_string.replaceAll("，", ",");
						Authors_string = Authors_string.replaceAll("  ", ",");
						Authors_string = Authors_string.replaceAll("、", ",");
						Authors_string = Authors_string.replaceAll(";", ",");
						Authors_string = Authors_string.replaceAll("；", ",");
						String[] authors = Authors_string.split(",");
						List<Author> authorlist = new ArrayList<Author>();
						for (String author : authors) {
							if (!author.equals("")) {
								Author authorT = kservice
										.searchAndSaveAuthor(author.trim());
								authorlist.add(authorT);
							}
						}
						p.setValue(authorlist);
						isnotSet = false;
						propertyValues.add(p);
					}
					
				//	System.out.println("log————3");
					// 单独处理关键词，因为在知识中关键词是作为对象属性的
					if (p.getName().equals("keywords")
							&& null != hpropertyValues.get("keywords")) {
						String keyword_string = hpropertyValues.get("keywords")
								.toString();
						keyword_string = keyword_string.replaceAll("  ", " ");
						keyword_string = keyword_string.replaceAll("，", ",");
						keyword_string = keyword_string.replaceAll(" ", ",");
						keyword_string = keyword_string.replaceAll("、", ",");
						keyword_string = keyword_string.replaceAll("；", ",");
						keyword_string = keyword_string.replaceAll(";", ",");
						String[] keywords = keyword_string.split(",");
						Set<Keyword> keywordlist = new HashSet<Keyword>();
						for (String keyword : keywords) {
							if (null != keyword && !keyword.equals("")) {
								Keyword keywordT = kservice
										.SearchAndSaveKeyword(keyword.trim());
								// System.out.println("keyword==="+keywordT.toString());
								keywordlist.add(keywordT);
							}
						}
						p.setValue(keywordlist);
						isnotSet = false;
						propertyValues.add(p);
					}
					// 单独处理知识中的知识域
					if (p.getName().equals("domainnode")&& null != hpropertyValues.get("domainnode")
							) {
						
						p.setValue(treeservice.getTreeNode(new Long(hpropertyValues.get("domainnode").toString())));
				   		propertyValues.add(p);
						isnotSet = false;
						nothasdomain=false;
					}
				
					// 单独处理知识中的知识分类 categorys
					if (p.getName().equals("categories")
							&& null != hpropertyValues.get("categories")) {
						String categories=hpropertyValues.get("categories").toString();
						categories=categories.replaceAll("，", ",");
						categories=categories.replaceAll(" ", ",");
						String[] category=categories.split(",");
						Set<CategoryTreeNode> categorylist=new HashSet<CategoryTreeNode>();
						for (int i = 0; i < category.length; i++) {
							if(!category[i].equals("")){
								 CategoryTreeNode cn = (CategoryTreeNode)treeservice.getTreeNode(new Long(category[i]));
								 if(null!=cn)
									 categorylist.add(cn);
						}
						}								
						 p.setValue(categorylist);

						isnotSet = false;
						 propertyValues.add(p);
					}

					// 单独处理知识中的引证文献 categorys
//					if (p.getName().equals("citationknowledges")
//							&& null != hpropertyValues
//									.get("citationknowledges")) {
//						// p.setValue( treeservice.getTreeNode(new
//						// Long(hpropertyValues.get("domainNode").toString()));
//						// p.setValue( treeservice.getTreeNode(new Long("2")));
//
//						isnotSet = false;
//						// propertyValues.add(p);
//					}

					if (isnotSet) {

						if(p.getPropertyType().equals("java.util.Date")){
					     if(!"NaN-NaN-NaNTNaN:NaN:NaN".equals(hpropertyValues.get(p.getName()).toString())){
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					    
						  Date s_date =(Date)format.parse(hpropertyValues.get(p.getName()).toString());
					      p.setValue(s_date);}
						}
						else{
						p.setValue(hpropertyValues.get(p.getName()));
						}
						propertyValues.add(p);
					}
				}

			}
			// 添加uploadtime属性
			Property uploadtime = new Property();
			uploadtime.setName("uploadtime");
			
			Date uploadtimeT = new Date();
			uploadtime.setValue(uploadtimeT);
			propertyValues.add(uploadtime);

			// 添加uploader属性
			Property uploader = new Property();
			uploader.setName("uploader");

			uploader.setValue(userservice.getUser());
			
			propertyValues.add(uploader);
			
			//添加visible属性
			// 添加uploader属性
			Property isvisible = new Property();
			isvisible.setName("isvisible");
	
	
			isvisible.setValue(true);
			propertyValues.add(isvisible);
			
			
			
			
			
			// 添加知识类型属性
			Property ktypep = new Property();
			ktypep.setName("ktype");
			ktypep.setValue(ktype);
			propertyValues.add(ktypep);
			// 添加源文件路径属性
			Property knowledgesource = new Property();
			knowledgesource.setName("knowledgesourcefilepath");
			knowledgesource.setValue(targetFileName);
			propertyValues.add(knowledgesource);
			// 添加知识状态属性
			Property status = new Property();
			status.setName("status");
			status.setValue("0");

			if(ktype.getName().equals("Question"))                //如果知识类型是'问题'，则状态设置为7

			   {status.setValue("7");}
			propertyValues.add(status);
			// 添加知识版本属性
			Property versionp = new Property();
			Version version = new Version();
			version.setPid(versionid);
			version.setVersionTime(uploadtimeT);
			if(null==versionNumber||versionNumber.equals(""))
				versionNumber="1.0";
			version.setVersionNumber(versionNumber);
			versionp.setName("version");
			versionp.setValue(version);
			propertyValues.add(versionp);
			
			//添加记录信息
			
			// 添加知识版本属性
			Property commentrecord = new Property();
			CommentRecord cr = new CommentRecord();
			cr.setCommentCount(new Long(0));
			cr.setViewCount(new Long(0));
			cr.setRate(new Float(0));
			cr.setDownloadCount(new Long(0));
			commentrecord.setName("commentrecord");
			commentrecord.setValue(cr);
			propertyValues.add(commentrecord);
			
			//添加引证文献
			if(!citation.equals("")){
				Property citationknowledges = new Property();
				List<MetaKnowledge> mts = new ArrayList<MetaKnowledge>();
				String[] citationids = citation.split(";");
				for(String cid:citationids){
					Long clid = Long.parseLong(cid);
					MetaKnowledge metak = kservice.getMetaknowledge(clid);
					mts.add(metak);
				}
				citationknowledges.setName("citationknowledges");
				citationknowledges.setValue(mts);
				propertyValues.add(citationknowledges);
			}			
			// 添加附件属性 
			Set<Attachment> attachmentset = new HashSet<Attachment>();
			//System.out.println("log————5");
			
			if (null != attachfile && !attachfile.equals("")) {
			
				String[] attachnames = attachfile.split("@");
				for (int i = 0; i < attachnames.length; i++) {
					if (!attachnames[i].trim().equals("")) {
						String attpath=attachnames[i].substring(0,attachnames[i].indexOf("#"));
						String attname=attachnames[i].substring(attachnames[i].indexOf("#")+1);
						Attachment att = new Attachment();
						att.setAttachmentName(attname.trim());
						att.setAttachmentPath(attpath.trim());
						attachmentset.add(att);
						
					}
				}
				Property attachmentp = new Property();
				attachmentp.setName("attachments");
				attachmentp.setValue(attachmentset);
			
				propertyValues.add(attachmentp);
			}
			
		
          if(nothasdomain)
    	   throw new ServiceException("没有选择域！");	
			MetaKnowledge k = kservice.save(ktype, propertyValues);
			
	

		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ServiceException("文件上传失败！");

		}

		addActionMessage("保存知识属性成功");
		return "upload";
	}

	
	public String simpleupdate() throws Exception {
	
		// 构建文件名称
		boolean nothasdomain=true;
		boolean hassecurtylevel=false;
		boolean isNewV = isNewVersion.equals("1");
		
		try {
    
			List<Property> propertyValues = new ArrayList<Property>();
			MetaKnowledge knowledge = kservice.getMetaknowledge(id);
			Ktype ktype = knowledge.getKtype();
						
			//HashMap hpropertyValues =getJSONvalue();
			JSONReader jr = new JSONReader();
			HashMap modifyk = (HashMap) jr.read(json);

			for (KtypeProperty kp : ktype.getKtypeproperties()) {
				Property p = kp.getProperty();
				if (null != modifyk.get(p.getName())&&
						 (!modifyk.get(p.getName()).equals(""))) {
					boolean isnotSet = true;
					// 单独处理作者，因为在知识中作者是作为对象属性的
					if (p.getName().equals("kauthors")
							&& null != modifyk.get("kauthors")) {
						String Authors_string = modifyk.get("kauthors")
								.toString();
						Authors_string = Authors_string.replaceAll("，", ",");
						Authors_string = Authors_string.replaceAll("  ", ",");
						Authors_string = Authors_string.replaceAll("：", ",");
						Authors_string = Authors_string.replaceAll("、", ",");
						Authors_string = Authors_string.replaceAll(";", ",");
						Authors_string = Authors_string.replaceAll("；", ",");
						String[] authors = Authors_string.split(",");
						List<Author> authorlist = new ArrayList<Author>();
						for (String author : authors) {
							if (!author.equals("")) {
								Author authorT = kservice
										.searchAndSaveAuthor(author.trim());
								authorlist.add(authorT);
							}
						}
						p.setValue(authorlist);
						isnotSet = false;
						propertyValues.add(p);
					}
				
					//如果作者项为空，则默认当前上传者为作者
					if (p.getName().equals("kauthors")
							&& null == modifyk.get("kauthors")) {
						
						List<Author> authorlist = new ArrayList<Author>();
					
						
								Author authorT = kservice
										.searchAndSaveAuthor( userservice.getUser().getName());
								authorlist.add(authorT);
						
						p.setValue(authorlist);
						isnotSet = false;
						propertyValues.add(p);
					}
				//	System.out.println("log————3");
					// 单独处理关键词，因为在知识中关键词是作为对象属性的
					if (p.getName().equals("keywords")
							&& null != modifyk.get("keywords")) {
						String keyword_string = modifyk.get("keywords")
								.toString();
						keyword_string = keyword_string.replaceAll("：", ",");
						keyword_string = keyword_string.replaceAll("，", ",");
						keyword_string = keyword_string.replaceAll("  ", ",");
						keyword_string = keyword_string.replaceAll("、", ",");
						keyword_string = keyword_string.replaceAll("；", ",");
						keyword_string = keyword_string.replaceAll(";", ",");
						String[] keywords = keyword_string.split(",");
						Set<Keyword> keywordlist = new HashSet<Keyword>();
						for (String keyword : keywords) {
							if (null != keyword && !keyword.equals("")) {
								Keyword keywordT = kservice
										.SearchAndSaveKeyword(keyword.trim());
								// System.out.println("keyword==="+keywordT.toString());
								keywordlist.add(keywordT);
							}
						}
						p.setValue(keywordlist);
						isnotSet = false;
						propertyValues.add(p);
					}

					// 单独处理知识中的参考文献 categorys
					if (p.getName().equals("citationknowledges")) {
						// p.setValue( treeservice.getTreeNode(new
						// Long(hpropertyValues.get("domainNode").toString()));
						// p.setValue( treeservice.getTreeNode(new Long("2")));

						isnotSet = false;
						// propertyValues.add(p);
					}

					if (isnotSet) {
						if(p.getPropertyType().equals("java.util.Date")){
					      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					      if(!modifyk.get(p.getName()).toString().equals("NaN-NaN-NaNTNaN:NaN:NaN")){
						  Date s_date =(Date)format.parse(modifyk.get(p.getName()).toString());
					      p.setValue(s_date);
					      }
						}
						else{
					   //单独判断密级		
						//	System.out.println(p.getName());
						if(p.getName().equals("securitylevel"))	
						{
							hassecurtylevel=true;
						String securitylevel=modifyk.get(p.getName())+"";
						if(null==securitylevel||securitylevel.trim().equals("")||Constants.SECURITY_LEVELS.indexOf(securitylevel)==-1)
						{						
						securitylevel=Constants.DEFAULT_SECURITY_LEVELS;				
						}
						p.setValue(securitylevel);
						}
						else{
						p.setValue(modifyk.get(p.getName()));
						}
						}
						propertyValues.add(p);
					}
				}
				
				// 单独处理知识中的知识域
				if (p.getName().equals("domainnode")) {
					
					p.setValue(knowledge.getDomainnode());
			   		propertyValues.add(p);					
					nothasdomain=false;
				}
			
				// 单独处理知识中的知识分类 categorys
				if (p.getName().equals("categories")) {	
					if(isNewV){
						Set<CategoryTreeNode>  ctns=new HashSet<CategoryTreeNode>();
						for(CategoryTreeNode ctn:knowledge.getCategories()){
							ctns.add(ctn);
						}							
						 p.setValue(ctns);
						 propertyValues.add(p);
					}else{
						 p.setValue(knowledge.getCategories());
						 propertyValues.add(p);
					}
					
				}
				
				//单独处理知识类型
				if (p.getName().equals("knowledgetype")) {	
					p.setValue(knowledge.getKnowledgetype());				
					propertyValues.add(p);
			
				}
				

			}
			
			if(!hassecurtylevel){
				
				Property securitylevel = new Property();
				securitylevel.setName("securitylevel");			
				securitylevel.setValue(knowledge.getSecuritylevel());
				propertyValues.add(securitylevel);
			}
			
			// 添加uploadtime属性
			Property uploadtime = new Property();
			uploadtime.setName("uploadtime");			
			Date uploadtimeT = new Date();
			uploadtime.setValue(uploadtimeT);
			propertyValues.add(uploadtime);

			// 添加uploader属性
			Property uploader = new Property();
			uploader.setName("uploader");
			uploader.setValue(userservice.getUser());
			propertyValues.add(uploader);
			
//			if(ktype.getName().equals("Question"))//如果知识类型是'问题'，则初始化问题状态为0
//			{
//				Property questionstatus = new Property();
//				questionstatus.setName("questionstatus");
//				questionstatus.setValue(0L);
//				propertyValues.add(questionstatus);
//				Property questionsupplement = new Property();
//				questionsupplement.setName("questionsupplement");
//				questionsupplement.setValue("");
//				propertyValues.add(questionsupplement);				
//			}
			
			//添加visible属性
			Property isvisible = new Property();
			isvisible.setName("isvisible");
			isvisible.setValue(true);
			propertyValues.add(isvisible);
			
			// 添加知识模板属性
			Property ktypep = new Property();
			ktypep.setName("ktype");
			ktypep.setValue(ktype);
			propertyValues.add(ktypep);	
			// 添加知识状态属性
			Property status = new Property();
			status.setName("status");
			status.setValue(knowledge.getStatus());
			propertyValues.add(status);
			
			if(isNewV){
				// 添加源文件路径属性(改)（接受一个word源文件路径）
				Property knowledgesource = new Property();
				knowledgesource.setName("knowledgesourcefilepath");
				if(modifyk.get("knowledgesourcefilepath1").toString().equals("")){
					knowledgesource.setValue(knowledge.getKnowledgesourcefilepath());
				}else{
					knowledgesource.setValue(modifyk.get("knowledgesourcefilepath1").toString());
				}							
				propertyValues.add(knowledgesource);
				// 添加知识版本属性
				Property versionp = new Property();
				Version version = knowledge.getVersion();
				
				Version version2 = new Version();
				//version2.setPid(version.getId());
				version2.setVersionTime(uploadtimeT);								
				version2.setVersionNumber(genrateVersionNumberByParent(version));	
				version.getSubVersions().add(version2);
				//kservice.saveVersion(version);
				
				versionp.setName("version");
				versionp.setValue(version2);
				propertyValues.add(versionp);			
				//添加记录信息
				Property commentrecord = new Property();
				CommentRecord cr = new CommentRecord();
				cr.setCommentCount(new Long(0));
				cr.setViewCount(new Long(0));
				cr.setRate(new Float(0));
				cr.setDownloadCount(new Long(0));
				commentrecord.setName("commentrecord");
				commentrecord.setValue(cr);
				propertyValues.add(commentrecord);
				
				//添加引证文献
				Property citationknowledges = new Property();		
				citationknowledges.setName("citationknowledges");
				List<MetaKnowledge> citationks = new ArrayList<MetaKnowledge>();
				for(MetaKnowledge cmk:knowledge.getCitationknowledges()){
					citationks.add(cmk);
				}		
				citationknowledges.setValue(citationks);
				propertyValues.add(citationknowledges);
					
				// 添加附件属性 
				Property attachmentp = new Property();
				attachmentp.setName("attachments");
				Set atts = new HashSet<Attachment>();
				for(Object att:knowledge.getAttachments()){
					atts.add(att);
				}
				attachmentp.setValue(atts);		
				propertyValues.add(attachmentp);
			}else{
				// 添加源文件路径属性
				Property knowledgesource = new Property();
				knowledgesource.setName("knowledgesourcefilepath");
				if(modifyk.get("knowledgesourcefilepath1").toString().equals("")){
					knowledgesource.setValue(knowledge.getKnowledgesourcefilepath());
				}else{
					knowledgesource.setValue(modifyk.get("knowledgesourcefilepath1").toString());
				}
				propertyValues.add(knowledgesource);
				// 添加知识版本属性
				Property versionp = new Property();			
				versionp.setName("version");
				versionp.setValue(knowledge.getVersion());
				propertyValues.add(versionp);			
				//添加记录信息
				Property commentrecord = new Property();			
				commentrecord.setName("commentrecord");
				commentrecord.setValue(knowledge.getCommentrecord());
				propertyValues.add(commentrecord);
				//添加引证文献
				Property citationknowledges = new Property();		
				citationknowledges.setName("citationknowledges");
				citationknowledges.setValue(knowledge.getCitationknowledges());
				propertyValues.add(citationknowledges);
					
				// 添加附件属性 
				Property attachmentp = new Property();
				attachmentp.setName("attachments");
				attachmentp.setValue(knowledge.getAttachments());		
				propertyValues.add(attachmentp);
			}
			
			
//			//添加引证文献
//			Property citationknowledges = new Property();		
//			citationknowledges.setName("citationknowledges");
//			citationknowledges.setValue(knowledge.getCitationknowledges());
//			propertyValues.add(citationknowledges);
//				
//			// 添加附件属性 
//			Property attachmentp = new Property();
//			attachmentp.setName("attachments");
//			attachmentp.setValue(knowledge.getAttachments());		
//			propertyValues.add(attachmentp);
			
			if(isNewV){
				kservice.save(ktype, propertyValues);
				JSONUtil.write(response,"生成新版本");
			}else{
				kservice.updateFixedKnowledge(knowledge, propertyValues);
				JSONUtil.write(response,"更改成功");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ServiceException("文件上传失败！");
		}		
		return null;
	}
	
	private String genrateVersionNumberByParent(Version parent) throws Exception{		
		
		String parentCode=parent.getVersionNumber();
		List<Version> brothers=parent.getSubVersions();
		if(brothers.size()==0)
			return parentCode+".1";
		Set<Integer> brotherNOs=new HashSet<Integer>();
		for(Version brother:brothers){
			String brotherCode=brother.getVersionNumber();
			int beginIndex=brotherCode.lastIndexOf('.');
			String brotherNOString=brotherCode.substring(beginIndex+1);
			Integer brotherNO=Integer.valueOf(brotherNOString);
			brotherNOs.add(brotherNO);
		}
		int partCode=Collections.max(brotherNOs)+1;
		return parentCode+"."+partCode;
	}
	
//	public String batchsave() throws Exception {
//
//		String[] ktypenamearray = ktypenamestring.split("@");
//		int num = ktypenamestring.length();
//		for(int z = 0;z<num;z++){
//			
//		}
//		
//		// 构建文件名称formvalue
//		boolean nothasdomain=true;
//		boolean hassecurtylevel=false;
//		try {
//			HashMap hpropertyValues =getJSONvalue();
//			
//            ktypeid = hpropertyValues.
//
//			List<Property> propertyValues = new ArrayList<Property>();
//			Ktype ktype;
//			if(ktypeid.equals(10000L)) {
//				ktype = ktypeservice.getKtype("Question");
//			}else{
//				ktype = ktypeservice.getKtype(ktypeid);
//			}			
//			
//
//			for (KtypeProperty kp : ktype.getKtypeproperties()) {
//				Property p = kp.getProperty();
//				if (null != hpropertyValues.get(p.getName())&&
//						 (!hpropertyValues.get(p.getName()).equals(""))) {
//					boolean isnotSet = true;
//					// 单独处理作者，因为在知识中作者是作为对象属性的
//					if (p.getName().equals("kauthors")
//							&& null != hpropertyValues.get("kauthors")) {
//						String Authors_string = hpropertyValues.get("kauthors")
//								.toString();
//						Authors_string = Authors_string.replaceAll("，", ",");
//						Authors_string = Authors_string.replaceAll("  ", ",");
//						Authors_string = Authors_string.replaceAll("：", ",");
//						Authors_string = Authors_string.replaceAll("、", ",");
//						Authors_string = Authors_string.replaceAll(";", ",");
//						Authors_string = Authors_string.replaceAll("；", ",");
//						String[] authors = Authors_string.split(",");
//						List<Author> authorlist = new ArrayList<Author>();
//						for (String author : authors) {
//							if (!author.equals("")) {
//								Author authorT = kservice
//										.searchAndSaveAuthor(author.trim());
//								authorlist.add(authorT);
//							}
//						}
//						p.setValue(authorlist);
//						isnotSet = false;
//						propertyValues.add(p);
//					}
//					//单独处理知识类型
//					if (null != hpropertyValues.get(p.getName())&&
//							 (!hpropertyValues.get(p.getName()).equals("")&&p.getName().equals("knowledgetype"))) {
//					String knowledgetypename = hpropertyValues.get("knowledgetype")
//					.toString();
//						
//							Knowledgetype knowledgetype = kservice.SearchAndSaveKnowledgetype(knowledgetypename);
//						
//					
//					p.setValue(knowledgetype);
//					isnotSet = false;
//					propertyValues.add(p);
//				
//				}
//					//如果作者项为空，则默认当前上传者为作者
//					if (p.getName().equals("kauthors")
//							&& null == hpropertyValues.get("kauthors")) {
//						
//						List<Author> authorlist = new ArrayList<Author>();
//					
//						
//								Author authorT = kservice
//										.searchAndSaveAuthor( userservice.getUser().getName());
//								authorlist.add(authorT);
//						
//						p.setValue(authorlist);
//						isnotSet = false;
//						propertyValues.add(p);
//					}
//				//	System.out.println("log————3");
//					// 单独处理关键词，因为在知识中关键词是作为对象属性的
//					if (p.getName().equals("keywords")
//							&& null != hpropertyValues.get("keywords")) {
//						String keyword_string = hpropertyValues.get("keywords")
//								.toString();
//						keyword_string = keyword_string.replaceAll("：", ",");
//						keyword_string = keyword_string.replaceAll("，", ",");
//						keyword_string = keyword_string.replaceAll("  ", ",");
//						keyword_string = keyword_string.replaceAll("、", ",");
//						keyword_string = keyword_string.replaceAll("；", ",");
//						keyword_string = keyword_string.replaceAll(";", ",");
//						String[] keywords = keyword_string.split(",");
//						Set<Keyword> keywordlist = new HashSet<Keyword>();
//						for (String keyword : keywords) {
//							if (null != keyword && !keyword.equals("")) {
//								Keyword keywordT = kservice
//										.SearchAndSaveKeyword(keyword.trim());
//								// System.out.println("keyword==="+keywordT.toString());
//								keywordlist.add(keywordT);
//							}
//						}
//						p.setValue(keywordlist);
//						isnotSet = false;
//						propertyValues.add(p);
//					}
//					// 单独处理知识中的知识域
//					if (p.getName().equals("domainnode")&& null != hpropertyValues.get("domainnode")
//							) {
//						
//						p.setValue(treeservice.getTreeNode(new Long(hpropertyValues.get("domainnode").toString())));
//				   		propertyValues.add(p);
//						isnotSet = false;
//						nothasdomain=false;
//					}
//				
//					// 单独处理知识中的知识分类 categorys
//					if (p.getName().equals("categories")
//							&& null != hpropertyValues.get("categories")) {
//						String categories=hpropertyValues.get("categories").toString();
//						categories=categories.replaceAll("，", ",");
//						categories=categories.replaceAll(" ", ",");
//						String[] category=categories.split(",");
//						Set<CategoryTreeNode> categorylist=new HashSet<CategoryTreeNode>();
//						for (int i = 0; i < category.length; i++) {
//							if(!category[i].equals("")){
//								 CategoryTreeNode cn = (CategoryTreeNode)treeservice.getTreeNode(new Long(category[i]));
//								 if(null!=cn)
//									 categorylist.add(cn);
//						}
//						}								
//						 p.setValue(categorylist);
//
//						isnotSet = false;
//						 propertyValues.add(p);
//					}
//
//					// 单独处理知识中的参考文献 categorys
//					if (p.getName().equals("citationknowledges")
//							&& null != hpropertyValues
//									.get("citationknowledges")) {
//						// p.setValue( treeservice.getTreeNode(new
//						// Long(hpropertyValues.get("domainNode").toString()));
//						// p.setValue( treeservice.getTreeNode(new Long("2")));
//
//						isnotSet = false;
//						// propertyValues.add(p);
//					}
//
//					if (isnotSet) {
//						if(p.getPropertyType().equals("java.util.Date")){
//					      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//					      System.out.println(hpropertyValues.get(p.getName()).toString());
//					      if(!hpropertyValues.get(p.getName()).toString().equals("NaN-NaN-NaNTNaN:NaN:NaN")){
//						  Date s_date =(Date)format.parse(hpropertyValues.get(p.getName()).toString());
//					      p.setValue(s_date);
//					      }
//						}
//						else{
//					   //单独判断密级		
//						//	System.out.println(p.getName());
//						if(p.getName().equals("securitylevel"))	
//						{
//							hassecurtylevel=true;
//						String securitylevel=hpropertyValues.get(p.getName())+"";
//						if(null==securitylevel||securitylevel.trim().equals("")||Constants.SECURITY_LEVELS.indexOf(securitylevel)==-1)
//						{						
//						securitylevel=Constants.DEFAULT_SECURITY_LEVELS;				
//						}
//						p.setValue(securitylevel);
//						}
//						else{
//						p.setValue(hpropertyValues.get(p.getName()));
//						}
//						}
//						propertyValues.add(p);
//					}
//				}
//				
//				//对问题作者的单独处理
//				if (p.getName().equals("kauthors")&&ktype.getName().equals("Question")) {
//					
//					List<Author> authorlist = new ArrayList<Author>();
//				
//					
//							Author authorT = kservice
//									.searchAndSaveAuthor( userservice.getUser().getName());
//							authorlist.add(authorT);
//					
//					p.setValue(authorlist);
//					propertyValues.add(p);
//				}
//
//			}
//			
//			if(!hassecurtylevel){
//				
//				Property securitylevel = new Property();
//				securitylevel.setName("securitylevel");
//				
//				
//				securitylevel.setValue(Constants.DEFAULT_SECURITY_LEVELS);
//				propertyValues.add(securitylevel);
//			}
//			
//			
//			// 添加uploadtime属性
//			Property uploadtime = new Property();
//			uploadtime.setName("uploadtime");
//			
//			Date uploadtimeT = new Date();
//			uploadtime.setValue(uploadtimeT);
//			propertyValues.add(uploadtime);
//
//			// 添加uploader属性
//			Property uploader = new Property();
//			uploader.setName("uploader");
//	
//	
//			uploader.setValue(userservice.getUser());
//			propertyValues.add(uploader);
//			
//			if(ktype.getName().equals("Question"))//如果知识类型是'问题'，则初始化问题状态为0
//			{
//				Property questionstatus = new Property();
//				questionstatus.setName("questionstatus");
//				questionstatus.setValue(0L);
//				propertyValues.add(questionstatus);
//				Property questionsupplement = new Property();
//				questionsupplement.setName("questionsupplement");
//				questionsupplement.setValue("");
//				propertyValues.add(questionsupplement);				
//			}
//			
//			//添加visible属性
//			Property isvisible = new Property();
//			isvisible.setName("isvisible");
//			isvisible.setValue(true);
//			propertyValues.add(isvisible);
//			
//			// 添加知识模板属性
//			Property ktypep = new Property();
//			ktypep.setName("ktype");
//			ktypep.setValue(ktype);
//			propertyValues.add(ktypep);
//			// 添加源文件路径属性
//			Property knowledgesource = new Property();
//			knowledgesource.setName("knowledgesourcefilepath");
//			knowledgesource.setValue(sourcefilepath);
//			propertyValues.add(knowledgesource);
//			// 添加知识状态属性
//			Property status = new Property();
//			status.setName("status");
//			status.setValue("0");
//			if(ktype.getName().equals("Question"))                //如果知识类型是'问题'，则状态设置为7
//			   {status.setValue("7");}
//			propertyValues.add(status);
//			// 添加知识版本属性
//			Property versionp = new Property();
//			Version version = new Version();
//			version.setPid(versionid);
//			version.setVersionTime(uploadtimeT);
//			if(null==versionNumber||versionNumber.equals(""))
//				versionNumber="1.0";
//			version.setVersionNumber(versionNumber);
//			versionp.setName("version");
//			versionp.setValue(version);
//			propertyValues.add(versionp);
//			
//			//添加记录信息转换
//			
//			// 添加知识版本属性
//			Property commentrecord = new Property();
//			CommentRecord cr = new CommentRecord();
//			cr.setCommentCount(new Long(0));
//			cr.setViewCount(new Long(0));
//			cr.setRate(new Float(0));
//			cr.setDownloadCount(new Long(0));
//			commentrecord.setName("commentrecord");
//			commentrecord.setValue(cr);
//			propertyValues.add(commentrecord);
//			
//			//添加引证文献
//			if(!citation.equals("")){
//				Property citationknowledges = new Property();
//				List<MetaKnowledge> mts = new ArrayList<MetaKnowledge>();
//				String[] citationids = citation.split(";");
//				for(String cid:citationids){
//					Long clid = Long.parseLong(cid);
//					MetaKnowledge metak = kservice.getMetaknowledge(clid);
//					mts.add(metak);
//				}
//				citationknowledges.setName("citationknowledges");
//				citationknowledges.setValue(mts);
//				propertyValues.add(citationknowledges);
//			}	
//			// 添加附件属性 
//			Set<Attachment> attachmentset = new HashSet<Attachment>();
//			//System.out.println("log————5");
//			
//			if (null != attachfile && !attachfile.equals("")) {
//			
//				String[] attachnames = attachfile.split("@");
//				for (int i = 0; i < attachnames.length; i++) {
//					if (!attachnames[i].trim().equals("")) {
//						String attpath=attachnames[i].substring(0,attachnames[i].indexOf("#"));
//						String attname=attachnames[i].substring(attachnames[i].indexOf("#")+1);
//						Attachment att = new Attachment();
//						att.setAttachmentName(attname.trim());
//						att.setAttachmentPath(attpath.trim());
//						attachmentset.add(att);
//						
//					}
//				}
//				Property attachmentp = new Property();
//				attachmentp.setName("attachments");
//				attachmentp.setValue(attachmentset);
//			
//				propertyValues.add(attachmentp);
//			}
//			
//		
//          if(nothasdomain)
//    	   throw new ServiceException("没有选择域！");	
//			MetaKnowledge k = kservice.save(ktype, propertyValues);			
//			imservice.saveMessageAndSubscribeInfo(k);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//			throw new ServiceException("文件上传失败！");
//
//		}
//
//		addActionMessage("保存知识属性成功");
//		if(ktypeid.equals(10000L)){
//			return null;
//		}
//		return "batchupload";
//	
//	}
	public String simplesave() throws Exception {
		
		
		// 构建文件名称formvalue
		boolean nothasdomain=true;
		boolean hassecurtylevel=false;
		try {
          

			List<Property> propertyValues = new ArrayList<Property>();
			Ktype ktype;
			if(ktypeid.equals(10000L)) {
				ktype = ktypeservice.getKtype("Question");
			}else if(ktypeid.equals(20000L)){//江丁丁添加  2013-6-14  专家黄页
				ktype = ktypeservice.getKtype("Article");
			}else{
				ktype = ktypeservice.getKtype(ktypeid);
			}			
			HashMap hpropertyValues =getJSONvalue();

//			Set set = hpropertyValues.entrySet() ;
//			Iterator it = set.iterator();
//			while(it.hasNext()){
//			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
//			System.out.println(entry.getKey()+"--------"+entry.getValue());
//			} 

			

			for (KtypeProperty kp : ktype.getKtypeproperties()) {
				Property p = kp.getProperty();
				if (null != hpropertyValues.get(p.getName())&&
						 (!hpropertyValues.get(p.getName()).equals(""))) {
					boolean isnotSet = true;
					// 单独处理作者，因为在知识中作者是作为对象属性的
					if (p.getName().equals("kauthors")
							&& null != hpropertyValues.get("kauthors")) {
						String Authors_string = hpropertyValues.get("kauthors")
								.toString();
						Authors_string = Authors_string.replaceAll("，", ",");
						Authors_string = Authors_string.replaceAll("  ", ",");
						Authors_string = Authors_string.replaceAll("：", ",");
						Authors_string = Authors_string.replaceAll("、", ",");
						Authors_string = Authors_string.replaceAll(";", ",");
						Authors_string = Authors_string.replaceAll("；", ",");
						String[] authors = Authors_string.split(",");
						List<Author> authorlist = new ArrayList<Author>();
						for (String author : authors) {
							if (!author.equals("")) {
								Author authorT = kservice
										.searchAndSaveAuthor(author.trim());
								authorlist.add(authorT);
							}
						}
						p.setValue(authorlist);
						isnotSet = false;
						propertyValues.add(p);
					}
					//单独处理知识类型
					if (null != hpropertyValues.get(p.getName())&&
							 (!hpropertyValues.get(p.getName()).equals("")&&p.getName().equals("knowledgetype"))) {
					String knowledgetypename = hpropertyValues.get("knowledgetype")
					.toString();
						
							Knowledgetype knowledgetype = kservice.SearchAndSaveKnowledgetype(knowledgetypename);
						
					
					p.setValue(knowledgetype);
					isnotSet = false;
					propertyValues.add(p);
				
				}
					//如果作者项为空，则默认当前上传者为作者
					if (p.getName().equals("kauthors")
							&& null == hpropertyValues.get("kauthors")) {
						
						List<Author> authorlist = new ArrayList<Author>();
					
						
								Author authorT = kservice
										.searchAndSaveAuthor( userservice.getUser().getName());
								authorlist.add(authorT);
						
						p.setValue(authorlist);
						isnotSet = false;
						propertyValues.add(p);
					}
				//	System.out.println("log————3");
					// 单独处理关键词，因为在知识中关键词是作为对象属性的
					if (p.getName().equals("keywords")
							&& null != hpropertyValues.get("keywords")) {
						String keyword_string = hpropertyValues.get("keywords")
								.toString();
						keyword_string = keyword_string.replaceAll("：", ",");
						keyword_string = keyword_string.replaceAll("，", ",");
						keyword_string = keyword_string.replaceAll("  ", ",");
						keyword_string = keyword_string.replaceAll("、", ",");
						keyword_string = keyword_string.replaceAll("；", ",");
						keyword_string = keyword_string.replaceAll(";", ",");
						String[] keywords = keyword_string.split(",");
						Set<Keyword> keywordlist = new HashSet<Keyword>();
						for (String keyword : keywords) {
							if (null != keyword && !keyword.equals("")) {
								Keyword keywordT = kservice
										.SearchAndSaveKeyword(keyword.trim());
								// System.out.println("keyword==="+keywordT.toString());
								keywordlist.add(keywordT);
							}
						}
						p.setValue(keywordlist);
						isnotSet = false;
						propertyValues.add(p);
					}
					// 单独处理知识中的知识域
					if (p.getName().equals("domainnode")&& null != hpropertyValues.get("domainnode")
							) {
						
						p.setValue(treeservice.getTreeNode(new Long(hpropertyValues.get("domainnode").toString())));
				   		propertyValues.add(p);
						isnotSet = false;
						nothasdomain=false;
					}
				
					// 单独处理知识中的知识分类 categorys
					if (p.getName().equals("categories")
							&& null != hpropertyValues.get("categories")) {
						String categories=hpropertyValues.get("categories").toString();
						categories=categories.replaceAll("，", ",");
						categories=categories.replaceAll(" ", ",");
						String[] category=categories.split(",");
						Set<CategoryTreeNode> categorylist=new HashSet<CategoryTreeNode>();
						for (int i = 0; i < category.length; i++) {
							if(!category[i].equals("")){
								 CategoryTreeNode cn = (CategoryTreeNode)treeservice.getTreeNode(new Long(category[i]));
								 if(null!=cn)
									 categorylist.add(cn);
						}
						}								
						 p.setValue(categorylist);

						isnotSet = false;
						 propertyValues.add(p);
					}

					// 单独处理知识中的参考文献 categorys
					if (p.getName().equals("citationknowledges")
							&& null != hpropertyValues
									.get("citationknowledges")) {
						// p.setValue( treeservice.getTreeNode(new
						// Long(hpropertyValues.get("domainNode").toString()));
						// p.setValue( treeservice.getTreeNode(new Long("2")));

						isnotSet = false;
						// propertyValues.add(p);
					}

					if (isnotSet) {
						if(p.getPropertyType().equals("java.util.Date")){
					      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					      System.out.println(hpropertyValues.get(p.getName()).toString());
					      if(!hpropertyValues.get(p.getName()).toString().equals("NaN-NaN-NaNTNaN:NaN:NaN")){
						  Date s_date =(Date)format.parse(hpropertyValues.get(p.getName()).toString());
					      p.setValue(s_date);
					      }
						}
						else{
					   //单独判断密级		
						//	System.out.println(p.getName());
						if(p.getName().equals("securitylevel"))	
						{
							hassecurtylevel=true;
						String securitylevel=hpropertyValues.get(p.getName())+"";
						if(null==securitylevel||securitylevel.trim().equals("")||Constants.SECURITY_LEVELS.indexOf(securitylevel)==-1)
						{						
						securitylevel=Constants.DEFAULT_SECURITY_LEVELS;				
						}
						p.setValue(securitylevel);
						}
						else{
						p.setValue(hpropertyValues.get(p.getName()));
						}
						}
						propertyValues.add(p);
					}
				}
				
				//对问题作者的单独处理
				if (p.getName().equals("kauthors")&&ktype.getName().equals("Question")) {
					
					List<Author> authorlist = new ArrayList<Author>();
				
					
							Author authorT = kservice
									.searchAndSaveAuthor( userservice.getUser().getName());
							authorlist.add(authorT);
					
					p.setValue(authorlist);
					propertyValues.add(p);
				}

			}
			
			if(!hassecurtylevel){
				
				Property securitylevel = new Property();
				securitylevel.setName("securitylevel");
				
				
				securitylevel.setValue(Constants.DEFAULT_SECURITY_LEVELS);
				propertyValues.add(securitylevel);
			}
			
			
			// 添加uploadtime属性
			Property uploadtime = new Property();
			uploadtime.setName("uploadtime");
			
			Date uploadtimeT = new Date();
			uploadtime.setValue(uploadtimeT);
			propertyValues.add(uploadtime);

			// 添加uploader属性
			Property uploader = new Property();
			uploader.setName("uploader");
	
	
			uploader.setValue(userservice.getUser());
			propertyValues.add(uploader);
			
			if(ktype.getName().equals("Question"))//如果知识类型是'问题'，则初始化问题状态为0
			{
				Property questionstatus = new Property();
				questionstatus.setName("questionstatus");
				questionstatus.setValue(0L);
				propertyValues.add(questionstatus);
				Property questionsupplement = new Property();
				questionsupplement.setName("questionsupplement");
				questionsupplement.setValue("");
				propertyValues.add(questionsupplement);				
			}
			
			if(ktype.getName().equals("Article"))//如果知识类型是'文章' 江丁丁添加 2013-6-16
			{
//				java.sql.Clob articlecontent = new javax.sql.rowset.serial.SerialClob(articlecontent.toCharArray());
				Property content = new Property();
				content.setName("articlecontent");
				content.setValue(articlecontent);
				propertyValues.add(content);
			}
			
			//添加visible属性
			Property isvisible = new Property();
			isvisible.setName("isvisible");
			isvisible.setValue(true);
			propertyValues.add(isvisible);
			
			// 添加知识模板属性
			Property ktypep = new Property();
			ktypep.setName("ktype");
			ktypep.setValue(ktype);
			propertyValues.add(ktypep);
			// 添加源文件路径属性
			Property knowledgesource = new Property();
			knowledgesource.setName("knowledgesourcefilepath");
			knowledgesource.setValue(sourcefilepath);
			propertyValues.add(knowledgesource);
			// 添加知识状态属性
			Property status = new Property();
			status.setName("status");
			status.setValue("0");
			if(ktype.getName().equals("Question"))                //如果知识类型是'问题'，则状态设置为7
			   {status.setValue("7");}
			if(ktype.getName().equals("Article"))                //如果知识类型是'文章'，江丁丁添加 2013-6-18
			   {status.setValue("1");}
			propertyValues.add(status);
			// 添加知识版本属性
			Property versionp = new Property();
			Version version = new Version();
			version.setPid(versionid);
			version.setVersionTime(uploadtimeT);
			if(null==versionNumber||versionNumber.equals(""))
				versionNumber="1.0";
			version.setVersionNumber(versionNumber);
			versionp.setName("version");
			versionp.setValue(version);
			propertyValues.add(versionp);
			
			//添加记录信息
			
			// 添加知识版本属性
			Property commentrecord = new Property();
			CommentRecord cr = new CommentRecord();
			cr.setCommentCount(new Long(0));
			cr.setViewCount(new Long(0));
			cr.setRate(new Float(0));
			cr.setDownloadCount(new Long(0));
			commentrecord.setName("commentrecord");
			commentrecord.setValue(cr);
			propertyValues.add(commentrecord);
			
			//添加引证文献
			if(!citation.equals("")){
				Property citationknowledges = new Property();
				List<MetaKnowledge> mts = new ArrayList<MetaKnowledge>();
				String[] citationids = citation.split(";");
				for(String cid:citationids){
					Long clid = Long.parseLong(cid);
					MetaKnowledge metak = kservice.getMetaknowledge(clid);
					mts.add(metak);
				}
				citationknowledges.setName("citationknowledges");
				citationknowledges.setValue(mts);
				propertyValues.add(citationknowledges);
			}	
			// 添加附件属性 
			Set<Attachment> attachmentset = new HashSet<Attachment>();
			//System.out.println("log————5");
			
			if (null != attachfile && !attachfile.equals("")) {
			
				String[] attachnames = attachfile.split("@");
				for (int i = 0; i < attachnames.length; i++) {
					if (!attachnames[i].trim().equals("")) {
						String attpath=attachnames[i].substring(0,attachnames[i].indexOf("#"));
						String attname=attachnames[i].substring(attachnames[i].indexOf("#")+1);
						Attachment att = new Attachment();
						att.setAttachmentName(attname.trim());
						att.setAttachmentPath(attpath.trim());
						attachmentset.add(att);
						
					}
				}
				Property attachmentp = new Property();
				attachmentp.setName("attachments");
				attachmentp.setValue(attachmentset);
			
				propertyValues.add(attachmentp);
			}
			
		
          if(nothasdomain)
    	   throw new ServiceException("没有选择域！");	
			MetaKnowledge k = kservice.save(ktype, propertyValues);			
			imservice.saveMessageAndSubscribeInfo(k);

		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ServiceException("文件上传失败！");

		}

		addActionMessage("保存知识属性成功");
		if(ktypeid.equals(10000L) || ktypeid.equals(20000L)){//后半句  江丁丁添加 2013-6-16
			return null;
		}
		return "simpupload";
	}
	
	
	
	
	public String ksearchUI() {
		return "search";
	}

	public String ksearch() throws IOException {

		JSONUtil ju  = new JSONUtil();
        HashMap propertyValues =(HashMap)ju.read(formvalue);
        if(null==propertyValues)
        {
        	response.getWriter().print("jason格式不正确，请检查");	
        	return null;
        }
		propertyValues.put("size", size);
		propertyValues.put("index", index);		
		PageDTO resultlist = kservice.searchKnowledge(propertyValues);

		ju.write(response, resultlist);

		return null;
	}
	
	public String interestKSearch() throws IOException {
		
		JSONUtil ju  = new JSONUtil();		
		
		SystemUser user = userservice.getUser();
		HashMap propertyValues =(HashMap)ju.read(formvalue);
		if(null==propertyValues)
		{
			response.getWriter().print("jason格式不正确，请检查");	
			return null;
		}
		propertyValues.put("size", size);
		propertyValues.put("index", index);		
		PageDTO resultlist = kservice.searchKnowledge(propertyValues);
		List<KnowledgeDTO> kdtos = resultlist.getData();
		List<KnowledgeDTO> addkdtos = new ArrayList<KnowledgeDTO>();
		for(KnowledgeDTO kdto:kdtos){
			if(kservice.isFirstView(user, kdto.getId(), sysBehaviorListService.getSysBehaviorList(18L), "knowledge")){
				kdto.setIsRead("1");
			}else{
				kdto.setIsRead("0");
			}
			if(user.getId().equals(kdto.getUploader().getId())){
				kdto.setIsUserEqualUpload("1");
			}else{
				kdto.setIsUserEqualUpload("0");
			}
			addkdtos.add(kdto);
		}
		resultlist.setData(addkdtos);				
		ju.write(response, resultlist);
		return null;
	}
	
	public String qsearch() throws IOException {		
		JSONUtil ju  = new JSONUtil();
        HashMap propertyValues =(HashMap)ju.read(formvalue);
        if(null==propertyValues)
        {
        	response.getWriter().print("jason格式不正确，请检查");	
        	return null;
        }
        Long qKtypeId = ktypeservice.getKtype("Question").getId();
		propertyValues.put("size", size);
		propertyValues.put("index", index);
		propertyValues.put("selectedktype", qKtypeId);
		PageDTO resultlist = kservice.searchKnowledge(propertyValues);
		ju.write(response, resultlist);
		
		return null;
	}
	
	public String kNodtreenodeSearch() throws IOException {
		 JSONUtil ju  = new JSONUtil();
		 //测试的两个数据
		 SystemUser uploader = userservice.getUser();
		 String knowledgeclassname = formvalue;
		
		Page<MetaKnowledge> page=null;
		if(this.size==0)
			 page=new Page<MetaKnowledge>(10);
		else
			page=new Page<MetaKnowledge>(this.size);
		page.setPageNo(this.index+1);	
        Page<MetaKnowledge> newpage = kservice.kSearch(knowledgeclassname, uploader, true,page);

		 List<MetaKnowledge> knowledgelist = newpage.getResult();	
		 List<KnowledgeDTO> dtos = new ArrayList<KnowledgeDTO>();
		 for (Knowledge k : knowledgelist) {
			KnowledgeDTO kdto = new KnowledgeDTO(k);
			dtos.add(kdto);
		 }		 
		 
		 PageDTO pagedto = new PageDTO();
		 pagedto.setData(dtos);
		 pagedto.setTotal(page.getTotalCount());

		 ju.write(response, pagedto);
 
		 return null;
	}

	public String fulltextserach() throws IOException {
       System.out.println("key==="+key);
		key=key.replaceAll("  ", " ");
		PageDTO page=new PageDTO();		
		page.setFirstindex(index);
		SystemUser user=userservice.getUser();
		if(null==user)
			user=userservice.getUser(new Long(1));
		if(size!=0)
		page.setPagesize(size);
		else
			page.setPagesize(Constants.rawPrepage);
		
		page = fulltextservice.searchKnowledge(key, page, null);
		String[] keywords = key.split(" ");
		for (int k = 0; k < keywords.length; k++) {
			
		HotWord hk = new HotWord();
		hk.setWordName(keywords[k]);
		hk.setTime(new Date());
		hk.setInitiator(user);
		fulltextservice.addHotWord(hk);
		}
		JSONUtil.write(response,page);

		return null;
	}
	
	/**
	 * 门户主页搜索
	 * 江丁丁 2013-7-27 
	 */
	public String doorSerach() throws IOException {
	       System.out.println("key==="+key);
			key=key.replaceAll("  ", " ");
			PageDTO page=new PageDTO();		
			page.setFirstindex(index);
			SystemUser user=userservice.getUser();
			if(null==user)
				user=userservice.getUser(new Long(1));
			if(size!=0)
			page.setPagesize(size);
			else
				page.setPagesize(Constants.rawPrepage);
			
			page = fulltextservice.searchKnowledge(key, page, null);
			String[] keywords = key.split(" ");
			for (int k = 0; k < keywords.length; k++) {
				
			HotWord hk = new HotWord();
			hk.setWordName(keywords[k]);
			hk.setTime(new Date());
			hk.setInitiator(user);
			fulltextservice.addHotWord(hk);
			}
			JSONUtil.write(response,page);

			return "searchlist";
		}
	
	
public String listRcommentKnowledge() throws IOException {

	   MetaKnowledge knowledge=kservice.getMetaknowledge(id);
		PageDTO page=new PageDTO();
		
		page.setFirstindex(index);
		
		page.setPagesize(5);
	
		page = fulltextservice.listRecommendedKnowledges(knowledge, page,formvalue);
	   List <KnowledgeDTO> knowledgedtolist=page.getData();
	   if(null!=knowledgedtolist&&!knowledgedtolist.isEmpty())
	   for (KnowledgeDTO knowledgeDTO : knowledgedtolist) {

		 //  System.out.println(knowledgeDTO.getTitleName());
		   if(null!=kservice.getMetaknowledge(knowledgeDTO.getId())){

		   CommentRecord cr=kservice.getMetaknowledge(knowledgeDTO.getId()).getCommentrecord();
		   CommentRecordDTO crdto=new CommentRecordDTO(cr);
		 //  System.out.println(crdto.getRate()); 
		   knowledgeDTO.setCommentRecord(crdto);
		   knowledgeDTO.setUploadTime(kservice.getMetaknowledge(knowledgeDTO.getId()).getUploadtime().toString());
		   ObjectDTO uploaderdto = new ObjectDTO();
		   uploaderdto.setName(kservice.getMetaknowledge(knowledgeDTO.getId()).getUploader().getName());
		   knowledgeDTO.setUploader(uploaderdto);
		   
		   
         	}
	   }
	// System.out.println(((KnowledgeDTO)page.getData().get(0)).getCommentRecord().getRate());
		JSONUtil.write(response,page.getData());

		return null;
	}
	public String listHotword()throws IOException {
		List<String>  hotwords = fulltextservice.listHotWords();
		List<ObjectDTO> hotworddtos=new ArrayList();
		for (String string : hotwords) {
			ObjectDTO dto=new ObjectDTO();
			dto.setName(string);
			hotworddtos.add(dto);
		}
	   JSONUtil.write(response,hotworddtos);
		return null;
	}
	
	public String listKeywords() throws Exception {		
		List<Keyword> keywords = kservice.listKeywords();
		List<ObjectDTO> edtos = new ArrayList<ObjectDTO>();
		for(Keyword keyword:keywords) {
			ObjectDTO edto = new ObjectDTO();
			edto.setId(keyword.getId());
			edto.setName(keyword.getKeywordName());
			edtos.add(edto);
		}
		JSONWriter writer = new JSONWriter();
		String testring = writer.write(edtos);
		response.getWriter().print(testring);	
		return null;
	}
	
	public String listAuthors() throws Exception {
		List<Author> authors = kservice.listAllAuthors();
		List<ObjectDTO> edtos = new ArrayList<ObjectDTO>();
		for(Author author:authors) {
			ObjectDTO edto = new ObjectDTO();
			//edto.setId(author.getId());
			edto.setName(author.getAuthorName());
			edtos.add(edto);
		}
		JSONWriter writer = new JSONWriter();
		String testring = writer.write(edtos);
		response.getWriter().print(testring);	
		return null;
	}
	
	public String searchHotword()throws IOException {
		//String tempKey = new String(key.getBytes("ISO-8859-1"),"utf-8");
		//System.out.println("key="+key);
		List<String>  hotwords = fulltextservice.searchHotWords(key);
		
		List<ObjectDTO> hotworddtos=new ArrayList();
		for (String string : hotwords) {
			ObjectDTO dto=new ObjectDTO();
			dto.setName(string);
			hotworddtos.add(dto);
		}
	   JSONUtil.write(response,hotworddtos);
		return null;
	}
	
	public String searchKeywords() throws IOException {
		String keyname = key.trim();
		if(keyname.equals("")){
			List<ObjectDTO> keyworddtos=new ArrayList();
			JSONUtil.write(response,keyworddtos);			
		}else{
			List<String> keywords = kservice.searchKeywords(keyname);
			List<ObjectDTO> keyworddtos=new ArrayList();
			for (String keywordname: keywords) {
				ObjectDTO dto=new ObjectDTO();
				dto.setName(keywordname);
				keyworddtos.add(dto);
			}
		   JSONUtil.write(response,keyworddtos);
		}
		return null;
		
	}
	
	public String searchAuthors()throws IOException {
		
		List<String> authors = kservice.searchAuthors(key);
		List<ObjectDTO> authordtos=new ArrayList();
		for (String authorname: authors) {
			ObjectDTO dto=new ObjectDTO();
			dto.setName(authorname);
			authordtos.add(dto);
		}
	   JSONUtil.write(response,authordtos);
		
		return null;
	}
	
	
	public String indexall()throws IOException {
	  //  List<MeteKnoweldge> knowledgelist=kservice.
		fulltextservice.indexAllKnowledge();
		JSONUtil.write(response, "恭喜！索引成功！");
		return null;
	}
	
	
	public String showknowledge() throws Exception {
		MetaKnowledge knowledge = kservice.getMetaknowledge(id);
		if(knowledge.getIsvisible()){
			SecurityContext securityContext = SecurityContextHolder.getContext();
			
			if(securityContext.getAuthentication() != null){
				
				SystemUser user=userservice.getUser();
				//修改浏览记录 如果不是作者自己浏览知识，则知识浏览次数+1
				//暂时改
				if(!user.getEmail().equals(knowledge.getUploader().getEmail()))
				{
					
//		if(user.getEmail().equals(knowledge.getUploader().getEmail()))
//		{
					int viewcount=	Integer.parseInt(knowledge.getCommentrecord().getViewCount()+"")+1;
					knowledge.getCommentrecord().setViewCount(new Long(viewcount));
					kservice.updateKnowledge(knowledge);
					
					
					//记录审批行为行为到SysBehaviorLog表
					SysBehaviorLog bLog = new SysBehaviorLog();
					bLog.setObjectid(knowledge.getId());
					bLog.setObjectType("knowledge");
					bLog.setUser(user);
					
					Long SysBehaviorListId=3L;
					
					bLog.setActionTime(new Date());
					bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(3L));
					
					sysBehaviorLogService.save(bLog);
					
					
					SysBehaviorLog bLog2 = new SysBehaviorLog();
					bLog2.setObjectid(knowledge.getId());
					bLog2.setObjectType("knowledge");
					bLog2.setUser(knowledge.getUploader());
					
					Long SysBehaviorListId2=15L;
					
					bLog2.setActionTime(new Date());
					bLog2.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(SysBehaviorListId2));
					
					sysBehaviorLogService.save(bLog2);
					
					
					//累加用户积分 浏览加分
					addUserScore.addUserScore(user,SysBehaviorListId);
					//被浏览加分
					addUserScore.addUserScore(knowledge.getUploader(),SysBehaviorListId2);
					
					
				}else
				{
					System.out.println("自己浏览自己知识就不记录和加分了");
					
				}
			}

		
		KnowledgeDTO kdto = new KnowledgeDTO(knowledge);
        //判断知识flash地址
		String flashpath=knowledge.getFlashfilepath();
		if(null!=flashpath&&flashpath.indexOf(".")!=-1)
		{	String flashname=flashpath.substring(0,flashpath.lastIndexOf("."));
		  SystemFile sf= fileservcie.getFile(flashname, ".swf");
		  if(null==sf)
		  kdto.setFlashFilePath("noflash");
		  else
			  kdto.setFlashFilePath(knowledge.getFlashfilepath());
			  
		}
		else
			  kdto.setFlashFilePath("noflash");
		
		
		if(null!=borrowFlowId&&borrowFlowId!=0)
		kdto.setBorrowFlowId(borrowFlowId);
		
     

//		System.out.println("dddddddddd   "+kservice.getProperty(id, "questioncontent"));
		//如果知识类型是'问题'，则添加questioncontent属性到KnowledgeDTO
		if(knowledge.getKtype().getName().equals("Question"))
		{
		String questioncontent=(String)kservice.getProperty(id, "questioncontent");
		
		kdto.setQuestioncontent(questioncontent);
		}
		//如果查询的知识是新闻，那么就返回新闻知识中的新闻内容  潘雷 panlei 20130603
		if(knowledge.getKtype().getName().equals("News"))
		{
		String questioncontent=(String)kservice.getProperty(id, "newscontent");
		
		kdto.setQuestioncontent(questioncontent);
		}
		//如果查询的知识是博文，那么就返回博文知识中的博文内容  江丁丁 20130622
		if(knowledge.getKtype().getName().equals("Article"))
		{
		String articlecontent=(String)kservice.getProperty(id, "articlecontent");
		
		kdto.setQuestioncontent(articlecontent);
		}

		JSONUtil.write(response, kdto);
		}

		return null;

	}
	
	//TODO
	public String listReferences() throws Exception {
		String sugmessage = key.trim();
		List<KnowledgeDTO> kdtos = new ArrayList<KnowledgeDTO>();
		if(!sugmessage.equals("")){
			List<MetaKnowledge> ks = kservice.getReferenceDoc(sugmessage);			
			for(MetaKnowledge k:ks) {
				KnowledgeDTO kdto = new KnowledgeDTO();
				kdto.setId(k.getId());
				kdto.setTitleName(k.getTitlename());		
				kdtos.add(kdto);
			}
		}		
		JSONUtil.write(response,kdtos);
		return null;
	}
	
	
	public String saveImcounts() throws Exception {
		MetaKnowledge knowledge = kservice.getMetaknowledge(id);
		List<InterestModelDTO> imdtos = new ArrayList<InterestModelDTO>();
		if(knowledge.getIsvisible()){
			SystemUser user=userservice.getUser();
			//修改浏览记录 如果不是作者自己浏览知识，则知识浏览次数+1
			//暂时改
		if(!user.getEmail().equals(knowledge.getUploader().getEmail()))
		{
			 
//			if(user.getEmail().equals(knowledge.getUploader().getEmail()))
//			{	
				//记录用户是否浏览了某知识			
				if(!kservice.isFirstView(user, id, sysBehaviorListService.getSysBehaviorList(18L), "knowledge")){
					if(knowledge.getUploader()!=null){
						SystemUser uploader = knowledge.getUploader();
							if(interestModelService.isInterestModelExist(user,  uploader.getId().toString(), "uploader")){
								List<InterestModel> ims = interestModelService.getInterestModels(user, uploader.getId().toString(), "uploader");
								InterestModelDTO imdto = new InterestModelDTO();
								for(InterestModel im:ims){									
									if(interestModelService.compareDate(knowledge, im)&&im.getUnRead()>0){
										im.setUnRead(im.getUnRead()-1);
									}							
									interestModelService.updateInterestModelItems(im);
									imdto.setCounts(im.getUnRead());
									imdto.setInterestItemType(im.getInterestItemType());
									imdto.setInterestItemId(Long.parseLong(im.getInterestItem()));
									imdto.setInterestItemName(uploader.getName());
									imdtos.add(imdto);
								}
								
							}						
						
					}
					if(knowledge.getKeywords().size()>0){					
						for(Keyword keyword:knowledge.getKeywords()){
							if(interestModelService.isInterestModelExist(user, keyword.getId().toString(), "keyword")){
								List<InterestModel> ims = interestModelService.getInterestModels(user, keyword.getId().toString(), "keyword");
								InterestModelDTO imdto = new InterestModelDTO();
								for(InterestModel im:ims){									
									if(interestModelService.compareDate(knowledge, im)&&im.getUnRead()>0){
										im.setUnRead(im.getUnRead()-1);
									}							
									interestModelService.updateInterestModelItems(im);
									imdto.setCounts(im.getUnRead());
									imdto.setInterestItemType(im.getInterestItemType());
									imdto.setInterestItemId(Long.parseLong(im.getInterestItem()));
									imdto.setInterestItemName(keyword.getKeywordName());
									imdtos.add(imdto);
								}
							}						
						}
					}
					if(knowledge.getCategories().size()>0){	
						for(TreeNode category:knowledge.getCategories()){
							if(interestModelService.isInterestModelExist(user, category.getId().toString(), "category")){
								List<InterestModel> ims = interestModelService.getInterestModels(user, category.getId().toString(), "category");
								InterestModelDTO imdto = new InterestModelDTO();
								for(InterestModel im:ims){									
									if(interestModelService.compareDate(knowledge, im)&&im.getUnRead()>0){
										im.setUnRead(im.getUnRead()-1);
									}							
									interestModelService.updateInterestModelItems(im);
									imdto.setCounts(im.getUnRead());
									imdto.setInterestItemType(im.getInterestItemType());
									imdto.setInterestItemId(Long.parseLong(im.getInterestItem()));
									imdto.setInterestItemName(category.getNodeName());
									imdtos.add(imdto);
								}
							}						
						}
					}
					if(knowledge.getDomainnode()!=null){
						if(interestModelService.isInterestModelExist(user, knowledge.getDomainnode().getId().toString(), "domain")){
							List<InterestModel> ims = interestModelService.getInterestModels(user, knowledge.getDomainnode().getId().toString(), "domain");
							InterestModelDTO imdto = new InterestModelDTO();
							for(InterestModel im:ims){								
								if(interestModelService.compareDate(knowledge, im)&&im.getUnRead()>0){
									im.setUnRead(im.getUnRead()-1);
								}							
								interestModelService.updateInterestModelItems(im);
								imdto.setCounts(im.getUnRead());
								imdto.setInterestItemType(im.getInterestItemType());
								imdto.setInterestItemId(Long.parseLong(im.getInterestItem()));
								imdto.setInterestItemName(knowledge.getDomainnode().getNodeName());
								imdtos.add(imdto);
							}
						}										
					}
					
					SysBehaviorLog bLog3 = new SysBehaviorLog();
					bLog3.setObjectid(knowledge.getId());
					bLog3.setObjectType("knowledge");
					bLog3.setUser(user);
					
					Long SysBehaviorListId3=18L;
					
					bLog3.setActionTime(new Date());
					bLog3.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(SysBehaviorListId3));
					
					sysBehaviorLogService.save(bLog3);
				}	
			}
	
			
			JSONUtil.write(response, imdtos);

		}
		
		return null;
		
	}
	
	
	public void modifycdtree()  
	{
		MetaKnowledge knowledge = kservice.getMetaknowledge(id);
		if(null!=domainids&&!domainids.equals("")&&!domainids.equals("null"))
		{

			
		DomainTreeNode domain=(DomainTreeNode)treeservice.getTreeNode(new Long(domainids));
	   	knowledge.setDomainnode(domain);
	   	//入库 添加到索引 并记录用户的添加知识的事件
		try {
			fulltextservice.indexKnowledge(knowledge);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//记录审批行为行为到SysBehaviorLog表
        SysBehaviorLog bLog = new SysBehaviorLog();
		bLog.setObjectid(knowledge.getId());
		bLog.setObjectType("knowledge");
		bLog.setUser(userservice.getUser());
		
		Long SysBehaviorListId=2L;
		
		bLog.setActionTime(new Date());
		bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(2L));
		
		sysBehaviorLogService.save(bLog);
		
		}
		
		if(null!=categoryids&&!categoryids.equals("null"))
		{
			categoryids=categoryids.replaceAll("，", ",");
			categoryids=categoryids.replaceAll(" ", ",");
			String[] category=categoryids.split(",");
			Set<CategoryTreeNode> categorylist=new HashSet<CategoryTreeNode>();
			if(null!=category&&category.length>0)
			for (int i = 0; i < category.length; i++) {
				if(!category[i].equals("")){
					 CategoryTreeNode cn = (CategoryTreeNode)treeservice.getTreeNode(new Long(category[i]));
					 if(null!=cn)
						 categorylist.add(cn);
			}
			}								
			knowledge.setCategories(categorylist);
		}
		kservice.updateKnowledge(knowledge);
		if(null!=domainids&&!domainids.equals("")&&!domainids.equals("null"))
		{
			if(!knowledge.getStatus().equals("0"))
				imservice.saveMessageAndSubscribeInfo(knowledge);		
		}
		JSONUtil.write(response,"修改成功！");

	
	}
	
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
	 * @author panlei    
	 * 申请修改域方法，发送消息到域管理员
	 */
	public void applymodifycdtree(){

		MetaKnowledge knowledge = kservice.getMetaknowledge(id);
		String nodename = "";
		if(null!=domainids&&!domainids.equals("")&&!domainids.equals("null"))
		{
			DomainTreeNode domain=(DomainTreeNode)treeservice.getTreeNode(new Long(domainids));
			nodename = domain.getNodeName();
//			Set<AdminPrivilegeTriple> set = domain.getAdminPrivilegeTriples();
			
		}
			
		//记录审批行为行为到SysBehaviorLog表
        SysBehaviorLog bLog = new SysBehaviorLog();
		bLog.setObjectid(knowledge.getId());
		bLog.setObjectType("knowledge");
		bLog.setUser(userservice.getUser());
		
		Long SysBehaviorListId=2L;
		
		bLog.setActionTime(new Date());
		bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(2L));
		
		sysBehaviorLogService.save(bLog);
		
		
		Message message=new Message();
		SystemUser sender;
		SystemUser receiver;
		//pl 指定消息发送人(当前用户)和接收人(域管理员)
		sender = userservice.getUser();
		receiver = userservice.getUser(receiverid);
		
		Date date=new Date();
		String contentstring = "";
		if(nodename == ""){
			contentstring += "没有选择目的知识域。@";
		}else{
			contentstring += "选择\""+nodename+"\"为目的知识域。@";
		}
		contentstring += "申请说明如下："+applytext;
		message.setIsRead(false);	        
        message.setIsAnswered(false);	        
        message.setKnowledge(knowledge);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessageType("applymodifydomainnode");
        message.setSendTime(date);
        message.setContent("向你发出了改域申请。@"+contentstring);
        messageservice.save(message);
		messageservice.sendMessage(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息
		
		
		JSONUtil.write(response,"申请成功，正等待处理！");

	
	
	}
	/**
	 * @author pl    
	 * 审批改域，并变更知识域，发送消息到申请者
	 */
	public void approvalupdateknowledgectree()  
	{
		Message message = messageservice.getMessage(messageId);
		MetaKnowledge knowledge = message.getKnowledge();
		String contenttext = message.getContent();
		String nodename = contenttext.split("\"")[1];
//		String resultstring = "";
		if(approvalresult == "yes" || approvalresult.equals("yes")){
			if(null!=domainids&&!domainids.equals("")&&!domainids.equals("null"))
			{
				
			DomainTreeNode domain = (DomainTreeNode)treeservice.getTreeNodeByNodeName(nodename);
//			if(domain.equals(knowledge.getDomainnode())){
//				resultstring = "该知识已转到";
//			}
		   	knowledge.setDomainnode(domain);
		   	//入库 添加到索引 并记录用户的添加知识的事件
			try {
				fulltextservice.indexKnowledge(knowledge);
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//记录审批行为行为到SysBehaviorLog表
	        SysBehaviorLog bLog = new SysBehaviorLog();
			bLog.setObjectid(knowledge.getId());
			bLog.setObjectType("knowledge");
			bLog.setUser(userservice.getUser());
			
			Long SysBehaviorListId=2L;
			
			bLog.setActionTime(new Date());
			bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(2L));
			
			sysBehaviorLogService.save(bLog);
			
			}
			kservice.updateKnowledge(knowledge);
			if(null!=domainids&&!domainids.equals("")&&!domainids.equals("null"))
			{
				if(!knowledge.getStatus().equals("0"))
					imservice.saveMessageAndSubscribeInfo(knowledge);		
			}
		}
			//pl 将消息设为已回答 isAnswered=true
				message.setIsAnswered(true);
				messageservice.save(message);
			
			//pl 发送成功消息给申请者
			SystemUser sender;
			SystemUser receiver;
			//pl 指定消息发送人(当前用户)和接收人(超级管理员)
			sender = userservice.getUser();
			receiver = userservice.getUser(message.getSender().getId());
			
			Date date=new Date();
			String contentstring = "";
			if(approvalresult == "yes" || approvalresult.equals("yes")){
				contentstring += "同意你的改域申请。知识已转到\""+nodename+"\"。";
			}else{
				contentstring += "未同意你的改域申请。";
			}
			Message message2 = new Message();
			message2.setIsRead(false);	        
			message2.setIsAnswered(false);	        
			message2.setKnowledge(knowledge);
			message2.setSender(sender);
			message2.setReceiver(receiver);
			message2.setMessageType("applymodifydomainnoderesult");
			message2.setSendTime(date);
			message2.setContent(contentstring);
			messageservice.save(message2);
			messageservice.sendMessage(sender,receiver,message2.getContent(),knowledge.getTitlename());//发送消息
		if(approvalresult == "yes" || approvalresult.equals("yes")){
			JSONUtil.write(response,"改域成功！");
		}else{
			JSONUtil.write(response,"不同意申请改域");
		}
		
	}
	/**
	 * @author pl
	 * 根据知识读取该知识申请改域的申请信息
	 * */
	public void getApplyImformation(){
		MetaKnowledge knowledge = kservice.getMetaknowledge(id);
		List<Message> list = messageservice.listUnAnsweredKnowledgeMessage(id);
		Message message = null;
		for(int a = 0;a<list.size();a++){
			Message message2 = list.get(a);
			if(message2.getReceiver() == userservice.getUser()){
				message = message2;
			}
		}
		
		String applyimformation = "";
		
		if(message != null){
			
				String newcontent = "";
				message.setIsRead(true);
				
				messageservice.save(message);
				applyimformation += message.getSender().getName().toString();
				String content = message.getContent();
				String[] al = content.split("@");
				for(int q = 0;q<al.length;q++){
					newcontent += al[q];
				}
					applyimformation +=  newcontent;
		}
		JSONUtil.write(response,applyimformation+"@@@"+message.getId());
	}
	public void SearchAVIDM() throws Exception
	{
	List<AvidmKnowledgeDTO> resultlist=axisWebService.searchService(key, userservice.getUser().getEmail(),avidmip);	
	JSONUtil.write(response,resultlist);
	}
	public void shwoAVIDMHOST() throws Exception
	{
		String[] avidmhosts=Constants.AVIDMHOSTPROPERTY.split("#");
	   List<ObjectDTO> hostlist=new ArrayList<ObjectDTO>();
		for(String avidmhosttemp:avidmhosts)
		{   
			String[] avidmproperty=avidmhosttemp.split("&");
			if(avidmproperty.length>=2)
			{
			ObjectDTO host=	new ObjectDTO();
				host.setName(avidmproperty[1]);
				host.setShowname(avidmproperty[0]);
				hostlist.add(host);	
			}
		}	

	JSONUtil.write(response,hostlist);
	}

	
	//问题补充
	public String saveQuestionSupplement() throws Exception
	{
	
		MetaKnowledge k = kservice.getMetaknowledge(id);
		
		PropertyUtils.setProperty(k, "questionsupplement",questionsupplement);
		kservice.updateKnowledge(k);
		
		KnowledgeDTO kdto = new KnowledgeDTO();
		//String qs = questionsupplement.toString();
		kdto.setQuestionsupplement(questionsupplement);	
		JSONUtil.write(response,kdto);
		
		return null;
	}
	
	public String showquestionsupplement() throws Exception
	{
	
		MetaKnowledge k = kservice.getMetaknowledge(id);
		KnowledgeDTO kdto = new KnowledgeDTO();
		
		if(k.getKtype().getName().equals("Question"))
		{
		String questionsupplement=(String)kservice.getProperty(id, "questionsupplement");
		kdto.setQuestionsupplement(questionsupplement);	
		}		
		
		JSONUtil.write(response,kdto);
		
		return null;
	}
	
	

	
	//某知识的扩展知识类型属性列表
	public String expandKPropertyAndValueList() throws Exception {
		
		List<Property> kps = kservice.getKnowledgePropertyValue(id);
		MetaKnowledge k = kservice.getMetaknowledge(id);
	
		List<Property> expandkps = new ArrayList<Property>();
		for(Property kp:kps) {			
			if(kp.getIsCommon().equals(false)) {
				//System.out.println("+++++++++++"+PropertyUtils.getProperty(k, kp.getName()));
				Object tempvalue="";
				if(null!=PropertyUtils.getProperty(k, kp.getName())){
				if(kp.getPropertyType().equals("java.util.Date"))
					{
					      
							tempvalue=((Date)PropertyUtils.getProperty(k, kp.getName())).toLocaleString();
					
					}
				else{
				if(kp.getName().equals("knowledgetype"))
					
				{
					tempvalue=((Knowledgetype)PropertyUtils.getProperty(k, kp.getName())).getKnowledgeTypeName();
				}
				else
					
					tempvalue=PropertyUtils.getProperty(k, kp.getName()).toString();
				
				}
				}
				System.out.println(tempvalue);
				kp.setValue(tempvalue);
				expandkps.add(kp);			
			}
		
			
		}
	
		JSONWriter writer = new JSONWriter();
		String propertystring = writer.write(expandkps);
		response.getWriter().print(propertystring);

		return null;
	}
	
	//知识修改时能显示的知识属性及值列表
	public String kVisiblePropertyAndValueList() throws Exception {
		//List<Property> kps = kservice.getKnowledgePropertyValue(id);
		List<KtypeProperty> kps = kservice.getKtypePropertyValue(id);		
		MetaKnowledge k = kservice.getMetaknowledge(id);
		List<PropertyDTO> dtos = new ArrayList<PropertyDTO>();		

		//List<KtypeProperty> visblekps = new ArrayList<KtypeProperty>();
		for(KtypeProperty kp:kps) {			
			if(kp.getProperty().getIsVisible().equals(true)&&!kp.getProperty().getName().equals("domainnode")&&!kp.getProperty().getName().equals("categories")
					&&!kp.getProperty().getName().equals("knowledgetype")) {
				//System.out.println("+++++++++++"+PropertyUtils.getProperty(k, kp.getProperty().getName()));
				Object tempvalue="";
				if(null!=PropertyUtils.getProperty(k, kp.getProperty().getName())){
					if(kp.getProperty().getPropertyType().equals("java.util.Date"))
					{
						
						tempvalue=((Date)PropertyUtils.getProperty(k, kp.getProperty().getName())).toLocaleString();
						
					}
					else{
						if(kp.getProperty().getName().equals("knowledgetype"))
							
						{
							tempvalue=((Knowledgetype)PropertyUtils.getProperty(k, kp.getProperty().getName())).getKnowledgeTypeName();
						}
	
						else if(kp.getProperty().getName().equals("kauthors")){
							List<Author> authorlist = (List<Author>) PropertyUtils.getProperty(k, kp.getProperty().getName());						    
							String authorstring = "";
							for (Author author : authorlist) {
								if(authorstring.equals("")){
									authorstring += author.getAuthorName();	
								}else{
									authorstring += ";"+author.getAuthorName();	
								}
									
							}
							tempvalue = authorstring;
							
						}else if(kp.getProperty().getName().equals("keywords")){
							Set<Keyword> kewordlist = (Set<Keyword>) PropertyUtils.getProperty(k, kp.getProperty().getName());						    
							String keywordstring = "";
							for (Keyword keyword : kewordlist) {
								if(keywordstring.equals("")){
									keywordstring += keyword.getKeywordName();	
								}else{
									keywordstring += ";"+keyword.getKeywordName();	
								}
									
							}
							tempvalue = keywordstring;
						}	
						else
							
							tempvalue=PropertyUtils.getProperty(k, kp.getProperty().getName()).toString();
						
					}
				}
				
				kp.getProperty().setValue(tempvalue);
				
				Property property = kp.getProperty();
				PropertyDTO pdto = new PropertyDTO();
				pdto.setDescription(kp.getShowname());
				pdto.setName(property.getName());
				pdto.setIsVisible(property.getIsVisible());
				pdto.setPropertyType(property.getPropertyType());
				pdto.setVcomponent(kp.getVcomponent());
				pdto.setValuelist(kp.getListvalue());
				pdto.setSearchable(kp.isSearchable());
				pdto.setIsNotNull(property.getIsNotNull());
				pdto.setLength(property.getLength());
				pdto.setId(kp.getId());
				pdto.setValue(tempvalue);
				dtos.add(pdto);

				
				System.out.println(tempvalue);
			
			}
			
			
		}
		JSONUtil.write(response, dtos);

		
		return null;
	}
	
	public String listKVersions() throws IOException{
		MetaKnowledge k = kservice.getMetaknowledge(id);
		List<ObjectDTO> versionlist=new ArrayList<ObjectDTO>();
		Version kversion = k.getVersion();
		Version pversion = getParentVersion(kversion);
		VersionDTO vdto = getVersionDTO(pversion,kversion);
		JSONWriter writer = new JSONWriter();
		String versionstring = writer.write(vdto);
		response.getWriter().print(versionstring);
		return null;
		
	}
	
	private Version getParentVersion(Version version){	
		
		while(version.getPid()!=null){
			version = kservice.getVersion(version.getPid());
		}
		return version;
		
	}
	
	
	protected VersionDTO getVersionDTO(Version version,Version versionsource)
	{
		
			VersionDTO versiondto=new VersionDTO();
			List<VersionDTO> versiondtos = new ArrayList<VersionDTO>();			
			versiondto.setId(version.getId());		
			versiondto.setKnowledgeID(version.getKnowledge().getId());
			versiondto.setKnowledgeTitleName(version.getKnowledge().getTitlename());
			if(version.getId().equals(versionsource.getId())){
				versiondto.setVersionNumber("<span style=color:red;>【当前版本"+version.getVersionNumber()+"】"+"</span>");	
			}else{
				versiondto.setVersionNumber("【版本"+version.getVersionNumber()+"】");	
			}				
			List<Version> versions = version.getSubVersions();
	        if(null!=versions){	        	
	        	for(Version version2:versions) {
	        		if(version2!=null){
		        		VersionDTO versiondto2 = getVersionDTO(version2,versionsource);	        		
		        		versiondtos.add(versiondto2);
		        		Collections.sort(versiondtos);
		        		versiondto.setChildren(versiondtos);
	        		}
					
				}
	        } 				 				
		return versiondto;

	} 
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public File getSourcefile() {
		return sourcefile;
	}

	public void setSourcefile(File sourcefile) {
		this.sourcefile = sourcefile;
	}

	public String getFormvalue() {
		return formvalue;
	}

	public void setFormvalue(String formvalue) {
		this.formvalue = formvalue;
	}

	public Long getKtypeid() {
		return ktypeid;
	}

	public void setKtypeid(Long ktypeid) {
		this.ktypeid = ktypeid;
	}

	public Long getDomainid() {
		return domainid;
	}

	public void setDomainid(Long domainid) {
		this.domainid = domainid;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public void setKservice(KnowledgeService kservice) {
		this.kservice = kservice;
	}

	public String getAttachfile() {
		return attachfile;
	}

	public void setAttachfile(String attachfile) {
		this.attachfile = attachfile;
	}

	public Long getVersionid() {
		return versionid;
	}

	public void setVersionid(Long versionid) {
		this.versionid = versionid;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public HashMap getJSONvalue()
	{
		//JSONUtil jr  = new JSONUtil();
		HashMap hpropertyValues ;
		try{
			 hpropertyValues = (HashMap) JSONUtil.read(formvalue);}
		catch(Exception e ){
			System.out.println("jason 格式错误！");
			e.printStackTrace();
			return null;
		}
		return hpropertyValues;
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

	public String getSourcefilepath() {
		return sourcefilepath;
	}

	public void setSourcefilepath(String sourcefilepath) {
		this.sourcefilepath = sourcefilepath;
	}

	public String getDomainids() {
		return domainids;
	}

	public void setDomainids(String domainids) {
		this.domainids = domainids;
	}

	public String getCategoryids() {
		return categoryids;
	}

	public void setCategoryids(String categoryids) {
		this.categoryids = categoryids;
	}


	public Long getBorrowFlowId() {
		return borrowFlowId;
	}

	public void setBorrowFlowId(Long borrowFlowId) {
		this.borrowFlowId = borrowFlowId;
	}


	public String getQuestioncontent() {
		return questioncontent;
	}

	public void setQuestioncontent(String questioncontent) {
		this.questioncontent = questioncontent;
	}

	public String getAvidmip() {
		return avidmip;
	}

	public void setAvidmip(String avidmip) {
		this.avidmip = avidmip;
	}

	public String getQuestionsupplement() {
		return questionsupplement;
	}

	public void setQuestionsupplement(String questionsupplement) {
		this.questionsupplement = questionsupplement;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

 
	public String getIsNewVersion() {
		return isNewVersion;
	}

	public void setIsNewVersion(String isNewVersion) {
		this.isNewVersion = isNewVersion;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getArticlecontent() {
		return articlecontent;
	}

	public void setArticlecontent(String articlecontent) {
		this.articlecontent = articlecontent;
	}
	
	


}
