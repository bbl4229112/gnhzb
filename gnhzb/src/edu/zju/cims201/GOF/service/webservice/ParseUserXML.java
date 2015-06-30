package edu.zju.cims201.GOF.service.webservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.lucene.index.CorruptIndexException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Version;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.knowledge.FullTextService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorListService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;

@Component
public class ParseUserXML {
	protected Logger logger = LoggerFactory.getLogger(getClass());
//	private HttpServlet servlet = (HttpServlet) MessageContext.getCurrentContext().getProperty(HTTPConstants.MC_HTTP_SERVLET);
//	
//	private ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servlet.getServletContext());
//	private UserService userservice=(UserService)context.getBean("userServiceImpl");
//	 private KtypeService ktypeservice=(KtypeService)context.getBean("ktypeServiceImpl");
//	 private CommonDao commondao=(CommonDao)context.getBean("commonDao");
//	 private KnowledgeService kservice=(KnowledgeService)context.getBean("knowledgeServiceImpl");
	private byte[] xmlString;
	private UserService userservice;
	private KtypeService ktypeservice;

    private KnowledgeService kservice;
	private CommonDao commondao;
	@Resource(name = "treeServiceImpl")
	private TreeService treeservice;
	@Resource(name = "fullTextServiceImpl")
	private FullTextService fulltextservice;
	@Resource(name="sysBehaviorLogServiceImpl")
	SysBehaviorLogService sysBehaviorLogService;
	@Resource(name="sysBehaviorListServiceImpl")
	SysBehaviorListService sysBehaviorListService;
	// public ParseUserXML(byte[] xmlString) {
	// this.xmlString = xmlString;
	// }

	@Transactional(readOnly = true)
	public void parseUser() throws Exception {
		// 获得解析器
		// ApplicationContext wac = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// UserService userService= (UserService)
		// wac.getBean("userServiceImpl");

		// System.out.print("解析");
		// 解析xml文件
		InputStream is = new ByteArrayInputStream(xmlString);

		 InputStreamReader is_GBK = null;
		 try {
		 is_GBK = new InputStreamReader(is, "GBK");
		 } catch (UnsupportedEncodingException e) {
		
		 e.printStackTrace();
		 }

		SAXBuilder sb = new SAXBuilder();
		Document doc = (Document) sb.build(is_GBK);

		// 获得xml根元素
		Element root = doc.getRootElement();

		List<Element> list = root.getChildren();
		for (Element e : list) {
			SystemUser user = new SystemUser();
			user.setEmail(e.getChildText("USRUID"));

			if (!userservice.isUserExist(user.getEmail()))// 判断用户是否已经存在
			{
				user.setName(e.getChildText("USRNAME"));
				user.setOrgname(e.getChildText("ORGNAME"));
				user.setSex(e.getChildText("GENDER"));
				user.setPassword("000");
				user.setIsVisible(true);// 统一设为可见

				if (user.getSex().toString().equals("M"))
					user.setPicturePath("fe74e496-7a47-441a-a53a-2c28d1846e97.gif");
				if (user.getSex().toString().equals("F"))
					user.setPicturePath("d910c60f-7e81-4071-bff1-8f2d80a83539.gif");

				if (null != e.getChildText("SECCLASS"))
					user.setSecurityLevel(Integer.parseInt(e
							.getChildText("SECCLASS")));
				userservice.createUser(user);
			}
		}

		// 获得根元素迭代器

	}
	@Transactional(readOnly = true)
	public void parseQualityKnowledge() throws Exception {
		// 获得解析器
		// ApplicationContext wac = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// UserService userService= (UserService)
		// wac.getBean("userServiceImpl");

		// System.out.print("解析");
		// 解析xml文件
		InputStream is = new ByteArrayInputStream(xmlString);
		String CARD_ID = "";
		String fileKeyword = "";
		String abstract_text = "";
		String titlename = "";
		String authorname = "";
		String uploadername = "";
		String uploaderemail = "";
		// InputStreamReader is_GBK = null;
		// try {
		// is_GBK = new InputStreamReader(is, "GBK");
		// } catch (UnsupportedEncodingException e) {
		//
		// e.printStackTrace();
		// }
		Ktype ktype= ktypeservice.getKtype("Qualityknowledge");
		List<KtypeProperty> ktypepropertylist = ktypeservice.listKtypeProperties(ktype.getId());

		SAXBuilder sb = new SAXBuilder();
		Document doc = (Document) sb.build(is);

		// 获得xml根元素
		Element root = doc.getRootElement();
        int i=0;
		List<Element> list = root.getChildren();
		for (Element e : list) {
			i=i+1;
			for (KtypeProperty kp : ktypepropertylist) {
				if (null != e.getChildText("CARD_ID")) {
					//唯一文档标示
					CARD_ID = e.getChildText("CARD_ID");
				}
				if (null != e.getChildText("SUMMARIZE")) {
					//故障现象概述 映射 摘要
					abstract_text = e.getChildText("SUMMARIZE");
				}
				if (null != e.getChildText("TROUBLE_MODENM")) {
					//故障模式 映射 关键词
					fileKeyword = e.getChildText("TROUBLE_MODENM");
				}
				if (null != e.getChildText("TROUBLE_NM")) {
					//质量问题名称 映射 标题
					titlename = e.getChildText("TROUBLE_NM");
				}
				if (null != e.getChildText("CREAT_SSO_CODE")) {
					//创建人email 单点登录账号 映射上传者邮箱
					uploaderemail = e.getChildText("CREAT_SSO_CODE");
				}
				if (null != e.getChildText("CREATERNM")) {
				   //创建人姓名 映射 创建人姓名
					uploadername = e.getChildText("CREATERNM");
				}
				if (null != e.getChildText("AUTHOR")) {
				  //填卡人  映射 作者
					authorname = e.getChildText("AUTHOR");
				}
			}

		
		  System.out.println("CARD_ID:"+CARD_ID);
		  System.out.println("abstract_text:"+abstract_text);
		  System.out.println("uploadername:"+uploadername);
		  System.out.println("fileKeyword:"+fileKeyword);
		  System.out.println("titlename:"+titlename);
          System.out.println("authorname:"+authorname);
          System.out.println("uploaderemail"+uploaderemail);
		Knowledge k;
		try {
			// 获得具体的知识类型的实例，用接口knowledg作为容器
			k = (Knowledge) Class.forName(
					"edu.zju.cims201.GOF.hibernate.pojo.Qualityknowledge")
					.newInstance();
		} catch (InstantiationException es) {

			es.printStackTrace();
			logger.warn("错误的知识类型", "");
			throw new ServiceException("错误的知识类型");
		} catch (IllegalAccessException es) {

			es.printStackTrace();
			logger.warn("非法操作", "");
			throw new ServiceException("非法操作");
		} catch (ClassNotFoundException es) {

			es.printStackTrace();
			logger.warn("没有该知识类型，没有找到对应的class{}",
					"edu.zju.cims201.GOF.hibernate.pojo.Qualityknowledge");
			throw new ServiceException("没有该知识类型，没有找到对应的class");
		}
		List templist = commondao.findByProperty(Class
				.forName("edu.zju.cims201.GOF.hibernate.pojo.Qualityknowledge"),
				"card_id", CARD_ID);
		if (null == templist || templist.isEmpty()) {
			// k = (Knowledge) templist.get(0);
			// k.setKnowledgesourcefilepath(globledocumentIID);
			// k.setFlashfilepath(globledocumentIID);
			// 添加关键词属性

			fileKeyword = fileKeyword.replaceAll("，", ",");
			fileKeyword = fileKeyword.replaceAll("  ", ",");
			fileKeyword = fileKeyword.replaceAll("、", ",");
			fileKeyword = fileKeyword.replaceAll("；", ",");
			fileKeyword = fileKeyword.replaceAll(";", ",");
			String[] keywords = fileKeyword.split(",");
			Set<Keyword> keywordlist = new HashSet<Keyword>();
			for (String keyword : keywords) {
				if (null != keyword && !keyword.equals("")) {
					Keyword keywordT = kservice.SearchAndSaveKeyword(keyword
							.trim());
					keywordlist.add(keywordT);
				}
			}
			k.setKeywords(keywordlist);
			// 添加知识类型属性
			Knowledgetype knowledgetype = kservice
					.SearchAndSaveKnowledgetype("质量管理");
			k.setKnowledgetype(knowledgetype);
			k.setTitlename(titlename);
			//添加知识域属性
			DomainTreeNode domain=(DomainTreeNode)treeservice.getTreeNode(new Long(Constants.QUALITYDOMAINID));
		   	k.setDomainnode(domain);

			// 添加作者属性
			List<Author> authorlist = new ArrayList<Author>();

			Author authorT = kservice.searchAndSaveAuthor(authorname);
			authorlist.add(authorT);
			k.setKauthors(authorlist);
			SystemUser u=new SystemUser();
			if(null!=uploaderemail&&!uploaderemail.trim().equals("")){
			if (userservice.isUserExist(uploaderemail)) {
				u=userservice.getUser(uploaderemail);
				//k.setUploader(u);

			} else {
				
				u.setEmail(uploaderemail);
				u.setPassword("000");
				u.setIsVisible(true);// 统一设为可见
				u.setSex("男");
				u.setPicturePath("fe74e496-7a47-441a-a53a-2c28d1846e97.gif");
				userservice.createUser(u);
				//k.setUploader(u);

			}
			}
			else
			{
				u=userservice.getUser(new Long(0));
				
			}
			 k.setUploader(u);

			Date uploadtimeT = new Date();
		

			k.setUploadtime(uploadtimeT);
			 
			k.setIsvisible(true);
		
		
			k.setKtype(ktype);
			k.setStatus("5");
			 k.setSecuritylevel("秘密");
			 k.setAbstracttext(abstract_text);
			// 添加知识状态属性
			Version version = new Version();
			version.setVersionTime(uploadtimeT);
    		version.setVersionNumber("1.0");
			k.setVersion(version);

			// 添加记录信息

			// 添加知识版本属性

			CommentRecord cr = new CommentRecord();
			cr.setCommentCount(new Long(0));
			cr.setViewCount(new Long(0));
			cr.setRate(new Float(0));
			cr.setDownloadCount(new Long(0));
			k.setCommentrecord(cr);

		//	for (Element e : list) {
				for (KtypeProperty kp : ktypepropertylist) {
					if (null != e.getChildText(kp.getProperty().getName().toUpperCase())) {
						String tempvalue=kp.getProperty().getName().toUpperCase();
						if(kp.getProperty().getPropertyType().equals("java.util.Date"))
						{Date datetemp =null;
							try{
						
							DateFormat format = new SimpleDateFormat("yyyy-MM-dd");        	
							datetemp = format.parse(e.getChildText(kp.getProperty().getName().toUpperCase())); 
							}catch(Exception es)
							{
								datetemp=new Date();
								
							}
							PropertyUtils.setProperty(k,
									kp.getProperty().getName(),datetemp);
						}
						else
						PropertyUtils.setProperty(k,
								kp.getProperty().getName(),
								e.getChildText(kp.getProperty().getName().toUpperCase()));

					}

				}

			//}
			
				System.out.println(" 保存第"+i+"次");
			kservice.save(k, null);
		 	//入库 添加到索引 并记录用户的添加知识的事件
			try {
				fulltextservice.indexKnowledge((MetaKnowledge)k);
			} catch (CorruptIndexException es) {
				// TODO Auto-generated catch block
				es.printStackTrace();
			} catch (IOException es) {
				// TODO Auto-generated catch block
				es.printStackTrace();
			}
			//记录审批行为行为到SysBehaviorLog表
	        SysBehaviorLog bLog = new SysBehaviorLog();
			bLog.setObjectid(k.getId());
			bLog.setObjectType("knowledge");
			bLog.setUser(u);
			
			Long SysBehaviorListId=2L;
			
			bLog.setActionTime(new Date());
			bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(2L));
			
			sysBehaviorLogService.save(bLog);
			
		}
		}

		

	}

	public byte[] getXmlString() {
		return xmlString;
	}

	public void setXmlString(byte[] xmlString) {
		this.xmlString = xmlString;
	}

	public UserService getUserservice() {
		return userservice;
	}

	@Autowired
	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}

	public KtypeService getKtypeservice() {
		return ktypeservice;
	}

	@Autowired
	public void setKtypeservice(KtypeService ktypeservice) {
		this.ktypeservice = ktypeservice;
	}

	public CommonDao getCommondao() {
		return commondao;
	}

	@Autowired
	public void setCommondao(CommonDao commondao) {
		this.commondao = commondao;
	}

	public KnowledgeService getKservice() {
		return kservice;
	}

	@Autowired
	public void setKservice(KnowledgeService kservice) {
		this.kservice = kservice;
	}
}
