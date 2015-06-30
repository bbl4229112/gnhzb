package edu.zju.cims201.GOF.service.webservice;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;

import net.sf.json.JSONObject;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.stringtree.json.JSONWriter;

import com.sun.org.apache.commons.beanutils.BeanUtils;

import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.InterestModel;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Version;
import edu.zju.cims201.GOF.rs.dto.InterestModelDTO;
import edu.zju.cims201.GOF.rs.dto.MessageDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.interestmodel.InterestModelService;
import edu.zju.cims201.GOF.service.knowledge.FullTextService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;

public class MobileWebServiceImpl implements MobileWebService {

	private HttpServlet servlet = (HttpServlet) MessageContext.getCurrentContext().getProperty(HTTPConstants.MC_HTTP_SERVLET);
	private ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servlet.getServletContext());
	//service的注入
	private UserService userservice=(UserService)context.getBean("userServiceImpl");
	private InterestModelService imservice = (InterestModelService)context.getBean("interestModelServiceImpl");
	private KnowledgeService kservice = (KnowledgeService)context.getBean("knowledgeServiceImpl");
	private FullTextService fulltextservice = (FullTextService) context.getBean("fullTextServiceImpl");
	private MessageService messageService = (MessageService) context.getBean("messageServiceImpl");
	private KtypeService ktypeservice = (KtypeService) context.getBean("ktypeServiceImpl");
	private TreeService treeservice = (TreeService) context.getBean("treeServiceImpl");
	
	
	//知识搜索的页码和每页显示
	private int size = 20;
	private int index = 1;
	//知识保存
	private Long ktypeid;
	private SystemUser user;
	private boolean collapse;
	
	public String login_kms(String loginInfo) {
		//判断用户是否存在
		HashMap<String, String> jsonMap = (HashMap<String, String>)JSONUtil.read(loginInfo);
		String email = jsonMap.get("email");
		String password = jsonMap.get("password");
		
		if(userservice.isUserExist(email)){
			//验证密码
			SystemUser user = userservice.getUser(email);
			if(user.getPassword().equals(password)){
				UserDTO udto = new UserDTO();
				udto.setId(user.getId());
				udto.setName(user.getName());
				udto.setSex(user.getSex());
				udto.setEmail(user.getEmail());
				udto.setHobby(user.getHobby());
				udto.setIntroduction(user.getIntroduction());
				udto.setPicturePath(user.getPicturePath());
				String userJson = JSONUtil.write(udto);
				System.out.println("返回结果-用户："+userJson);
				return userJson;
			}else{
				//密码错误
				System.out.println("密码错误");
				return null;
			}
		}else{
			//用户名不存在
			System.out.println("用户不存在");
			return null;
		}
	}


	public String getInterestModelKnowledge(Long userId) {
		user = userservice.getUser(userId);
		PageDTO resultlist = imservice.getInterestModelKnowledge(user);
		
		String klist = JSONUtil.write(resultlist);
		System.out.println("返回结果-兴趣知识列表："+klist);
		return klist;
	}


	public String getKnowledgeDetail (String kId) throws Exception{
		Long id = Long.parseLong(kId);
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
					Knowledgetype ktype = (Knowledgetype) PropertyUtils.getProperty(k, kp.getName());
					tempvalue=ktype.getKnowledgeTypeName();
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
		
		String result = JSONUtil.write(expandkps);
		System.out.println("返回结果-一条知识的详细信息："+result);
		return result;
	}


	public String fullTextSearch(String searchInfo) {
		HashMap<String, String> jsonMap = (HashMap<String, String>)JSONUtil.read(searchInfo);
		String key = jsonMap.get("key");
		if(jsonMap.get("size")!=null){
			size = Integer.parseInt(jsonMap.get("size"));
		}
		if(jsonMap.get("index")!=null){
			index = Integer.parseInt(jsonMap.get("index"));
		}
		System.out.println("key==="+key);
		key=key.replaceAll("  ", " ");
		PageDTO page=new PageDTO();	
		page.setFirstindex(index);
		if(size!=0){
			page.setPagesize(size);
		}else{
			page.setPagesize(Constants.rawPrepage);
		}
		
		page = fulltextservice.searchKnowledge(key, page, null);

		String klist = JSONUtil.write(page);
		System.out.println("返回结果-全文搜索结果："+klist);
		return klist;
	}


	
	public String myMessage(Long userId) throws Exception{
		PageDTO pd = new PageDTO();
		List<Message> messageList=messageService.listMessages(userId);
		List<MessageDTO> messageListDTO=new ArrayList<MessageDTO>();
		for(Message m:messageList)
		  {MessageDTO mDTO=new MessageDTO();
		   BeanUtils.copyProperties(mDTO, m);
		   mDTO.setReceiver(m.getReceiver().getName());
		   if(m.getMessageType().equals("comment"))
			   mDTO.setMessageType("知识评论");
		   else if(m.getMessageType().equals("ApprovalFlow"))
			   mDTO.setMessageType("知识审批");
		   else if(m.getMessageType().equals("BorrowFlow"))
			   mDTO.setMessageType("借阅审批");
		   else if(m.getMessageType().equals("notice"))
			   mDTO.setMessageType("系统公告");
		   else if(m.getMessageType().equals("askForAnswer"))
			   mDTO.setMessageType("问题求解");
		   else if(m.getMessageType().equals("subscribeknowledge"))
			   mDTO.setMessageType("知识订阅");
		   else if(m.getMessageType().equals("recommendknowledge"))
			   mDTO.setMessageType("知识推荐");
		   else if(m.getMessageType().equals("applymodifydomainnode"))
				   mDTO.setMessageType("申请改域");
		   else if(m.getMessageType().equals("applymodifydomainnoderesult"))
			   mDTO.setMessageType("申请改域结果");
		   mDTO.setSendTime(m.getSendTime().toString().substring(0, 16));

		   //mDTO.setSendTime(m.getSendTime().toLocaleString());


		   mDTO.setSender(m.getSender().getName()+"("+m.getSender().getEmail()+")");
		  if(null!=m.getKnowledge())
				{mDTO.setKnowledgeName(m.getKnowledge().getTitlename());
				 mDTO.setKnowledgeid(m.getKnowledge().getId());
				
				}
		   mDTO.setLinkedurl("");
		   
		   if(m.getIsRead())
			   {mDTO.setIsRead(1L);}
		   else{mDTO.setIsRead(0L);}
		   
		   if(m.getMessageType().equals("askForAnswer")) {
			   if(m.getIsAnswered())
		        {mDTO.setIsAnswered(1L);}
		        else {mDTO.setIsAnswered(0L);} 
		   }		   
		   
		   messageListDTO.add(mDTO);
		  }
		
		
		List<MessageDTO> subList = new ArrayList<MessageDTO>();
		
		for(int i=index*size;i<((index+1)*size<messageListDTO.size()?(index+1)*size:messageListDTO.size());i++){
			subList.add(messageListDTO.get(i));
		}
		
		pd.setData(subList);
		pd.setTotal(messageListDTO.size());
		
		int totalPage;
		if(size != 0) {
			if(messageListDTO.size()%size == 0){
				totalPage = messageListDTO.size()/size;
			}else{
				totalPage = messageListDTO.size()/size+1;
			}
			pd.setTotalPage(messageListDTO.size()/size+1);
			
		}		
		
		String mlist = JSONUtil.write(pd);
		return mlist;
	}
	


	public String myInfoCard(Long userId) {
		user = userservice.getUser(userId);
		UserDTO udto = new UserDTO();
		udto.setId(user.getId());
		udto.setEmail(user.getEmail());
		udto.setPassword(user.getPassword());
		udto.setName(user.getName());
		udto.setSex(user.getSex());
		udto.setHobby(user.getHobby());
		udto.setIntroduction(user.getIntroduction());
		udto.setPicturePath(user.getPicturePath());
		
		String plist = JSONUtil.write(udto);
		return plist;
	}
	
	public String listinterest(Long userId){
		
		user = userservice.getUser(userId);
		List<InterestModel> interestlist = imservice.listInterestModelItems(user);
		List<InterestModelDTO> imDTOlist = new ArrayList<InterestModelDTO>();		
		if(interestlist.size()!=0){
			for(InterestModel im:interestlist) {			
				if(im.getInterestItemType().equals("keyword")) {
					long kewordid = Long.parseLong(im.getInterestItem());
					String kewordname = kservice.getKeyword(kewordid).getKeywordName();
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
			}
		}
		
		String result = JSONUtil.write(imDTOlist);
		System.out.println("返回结果-兴趣列表："+result);
		return result;
	}
	
	public String savekeyword(String keywordname){
		
		String issuccess = "1";
		String result = null;
		
		Keyword keyword = kservice.SearchAndSaveKeyword(keywordname);
	
		if(!(imservice.isInterestModelExist(userservice.getUser(), keyword.getId().toString(), "keyword"))){
			List<String> keywordids = new ArrayList<String>();
			keywordids.add(keyword.getId().toString());
			imservice.addInterestModelItems(userservice.getUser(), keywordids, "keyword");
			issuccess = "1";
			result = JSONUtil.write(issuccess);
			
		}else{
			issuccess = "0";
			result = JSONUtil.write(issuccess);
		}
		
		return result;
	}

	public String saveuploader(String uploadername){
		String issuccess = "1";
		String result = null;
		if(!userservice.isNameExist(uploadername)){
			result = JSONUtil.write("该上传者不存在,请重新输入");
		}else{
			SystemUser uploader = userservice.getOneUser(uploadername);			
			if(!(imservice.isInterestModelExist(userservice.getUser(), uploader.getId().toString(), "uploader"))){
				List<String> uploadids = new ArrayList<String>();
				uploadids.add(uploader.getId().toString());
				imservice.addInterestModelItems(userservice.getUser(), uploadids, "uploader");
				issuccess = "1";
				result = JSONUtil.write(issuccess);
			}else{
				issuccess = "0";
				result = JSONUtil.write(issuccess);
			}
			
		}	
		return result;
	}

	public void simplesave(String kProperties) {
		HashMap<String, String> jsonMap = (HashMap<String, String>)JSONUtil.read(kProperties);
		if(jsonMap.get("ktypeid")!=null){
			ktypeid = Long.parseLong(jsonMap.get("ktypeid"));
		}
		if(jsonMap.get("userName")!=null){
			user = userservice.getOneUser(jsonMap.get("userName"));
		}
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
			HashMap hpropertyValues =jsonMap;

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
										.searchAndSaveAuthor( user.getName());
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
									.searchAndSaveAuthor( user.getName());
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
	
	
			uploader.setValue(user);
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
				if(hpropertyValues.get("articlecontent")!=null){
					Property content = new Property();
					content.setName("articlecontent");
					content.setValue((String)hpropertyValues.get("articlecontent"));
					propertyValues.add(content);
				}
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
			if(hpropertyValues.get("sourcefilepath")!=null){
				Property knowledgesource = new Property();
				knowledgesource.setName("knowledgesourcefilepath");
				knowledgesource.setValue((String)hpropertyValues.get("sourcefilepath"));
				propertyValues.add(knowledgesource);
			}
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
			if(hpropertyValues.get("versionid")!=null){
				Property versionp = new Property();
				Version version = new Version();
				version.setPid(Long.parseLong((String) hpropertyValues.get("versionid")));
				version.setVersionTime(uploadtimeT);
				String versionNumber = (String) hpropertyValues.get("versionNumber");
				if(null==versionNumber||versionNumber.equals(""))
					versionNumber="1.0";
				version.setVersionNumber(versionNumber);
				versionp.setName("version");
				versionp.setValue(version);
				propertyValues.add(versionp);
			}
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
						
		
          if(nothasdomain)
    	   throw new ServiceException("没有选择域！");	
			MetaKnowledge k = kservice.save(ktype, propertyValues);			
			imservice.saveMessageAndSubscribeInfo(k);

		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ServiceException("文件上传失败！");

		}
	}


	
	
	
}
