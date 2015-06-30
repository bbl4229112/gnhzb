package edu.zju.cims201.GOF.service.webservice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.SimpleFormatter;

import java.io.FileInputStream;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Version;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.interestmodel.InterestModelService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.Constants;

import javax.servlet.http.HttpServlet;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
public class ParseKnowledgeXML {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private HttpServlet servlet = (HttpServlet) MessageContext.getCurrentContext().getProperty(HTTPConstants.MC_HTTP_SERVLET);
	private ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servlet.getServletContext());
	//private FileService fileService=(FileService)context.getBean("fileServiceImpl");
	//@Resource(name = "userServiceImpl")
	private UserService userservice=(UserService)context.getBean("userServiceImpl");
	//@Resource(name = "ktypeServiceImpl")
	private KtypeService ktypeservice=(KtypeService)context.getBean("ktypeServiceImpl");
	//@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice=(KnowledgeService)context.getBean("knowledgeServiceImpl");
	//@Resource(name = "interestModelServiceImpl")
	private InterestModelService imservice=(InterestModelService)context.getBean("interestModelServiceImpl");
	//@Resource(name = "commonDao")
	private CommonDao commondao=(CommonDao)context.getBean("commonDao");
	
	private byte[] xmlString;

	public ParseKnowledgeXML(byte[] xmlString) {
		this.xmlString = xmlString;
	}
    public void parseQuality()throws Exception{
    	
    }
	public void parseAvidm() throws Exception {
		// 获得解析器
      //  System.out.println(new String(xmlString));
		// 解析xml文件
		InputStream is = new ByteArrayInputStream(xmlString);

		InputStreamReader is_GBK = null;
		try {
			is_GBK = new InputStreamReader(is, "GBK");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		SAXBuilder sb = new SAXBuilder();
		Document doc = (Document) sb.build(is);

		// 获得xml根元素
		String globledocumentIID = "";
		String avidmhost="";
		String documentIID = "";
		String fileIID = "";
		String productIID = "";
		String versionIID = "";
		String documentTypeName = "";
		String documentName = "";
		String userID = "";
		String securitylevel = "内部";
		String fileKeyword = "";
		String titlename = "";
		String abstract_text = "";
		String updatetime = "";
		String versionname="";
		Date updatedate = null;  
		Map<String, String> bookMarks = new HashMap<String, String>();

		Element root = doc.getRootElement();

		List<Element> list = root.getChildren();
		for (Element e : list) {
			if (null != e.getAttributeValue("documentIID")) {
				documentIID = e.getAttributeValue("documentIID");
			}

			if (null != e.getAttributeValue("fileIID")) {
				fileIID = e.getAttributeValue("fileIID");
			}

			if (null != e.getAttributeValue("productID")) {
				productIID = e.getAttributeValue("productID");
			}

			if (null != e.getAttributeValue("versionIID")) {
				versionIID = e.getAttributeValue("versionIID");
			}

			if (null != e.getAttributeValue("ktype")) {
				documentTypeName = e.getAttributeValue("ktype");
			}

			if (null != e.getAttributeValue("documentName")) {
				documentName = e.getAttributeValue("documentName");
			}

			if (null != e.getAttributeValue("userID")) {
				userID = e.getAttributeValue("userID");
			}
			if (null != e.getAttributeValue("titlename")) {
				titlename = e.getAttributeValue("titlename");
			}
			if (null != e.getAttributeValue("keyword")) {
				fileKeyword = e.getAttributeValue("keyword");
			}
			if (null != e.getAttributeValue("abstract_text")) {
				abstract_text = e.getAttributeValue("abstract_text");
			}
			if (null != e.getAttributeValue("security_level")) {
				securitylevel = e.getAttributeValue("security_level");
			}
			if (null != e.getAttributeValue("version")) {
				versionname = e.getAttributeValue("version");
			}if (null != e.getAttributeValue("globalIID")) {
				globledocumentIID = e.getAttributeValue("globalIID");
			}if (null != e.getAttributeValue("hostname")) {
				avidmhost = e.getAttributeValue("hostname");
			}
			if (null != e.getAttributeValue("updatetime")) {
				updatetime = e.getAttributeValue("updatetime");
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");        	
				updatedate = format.parse(updatetime);  // Thu Jan 18 00:00:00 CST 2007   
			
			}
			 String KEYWORD_BOOKMARK="";
			 String ABSTARCT_BOOKMARK="";
			 String[] avidmhosts=Constants.AVIDMHOSTPROPERTY.split("#");
			
				for(String avidmhosttemp:avidmhosts)
				{
					String[] avidmproperty=avidmhosttemp.split("&");
					if(avidmproperty.length==4&&null!=avidmproperty[1]&&avidmproperty[0].equals(avidmhost))
					{
						
						KEYWORD_BOOKMARK=avidmproperty[2];
						ABSTARCT_BOOKMARK=avidmproperty[3];
						break;
						
					}
				}
			 if(e.getName().equals("BOOKMARK"))
			 {bookMarks.put(e.getAttributeValue("name"), e.getText());
			 if(KEYWORD_BOOKMARK.equals(e.getAttributeValue("name")))
			 fileKeyword=e.getText();
			 if(ABSTARCT_BOOKMARK.equals(e.getAttributeValue("name")))
			 abstract_text=e.getText();
//			 if(SECURITYLEVEL_BOOKMARK.equals(e.getAttributeValue("name")))
//			 securitylevel=e.getText();
//			 System.out.println("name="+e.getAttributeValue("name")+",text="+e.getText());
			 }

		}
		// 保存avidm知识相关信息
		//userID="admin@webmaster.com";
		
		SystemUser u = userservice.getUser(userID.trim());
		if(null!=u){
		Knowledge k;
		try {
			// 获得具体的知识类型的实例，用接口knowledg作为容器
			k = (Knowledge) Class.forName(
					"edu.zju.cims201.GOF.hibernate.pojo.Avidmknowledge")
					.newInstance();
		} catch (InstantiationException e) {

			e.printStackTrace();
			logger.warn("错误的知识类型", "");
			throw new ServiceException("错误的知识类型");
		} catch (IllegalAccessException e) {

			e.printStackTrace();
			logger.warn("非法操作", "");
			throw new ServiceException("非法操作");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			logger.warn("没有该知识类型，没有找到对应的class{}",
					"edu.zju.cims201.GOF.hibernate.pojo.Avidmknowledge");
			throw new ServiceException("没有该知识类型，没有找到对应的class");
		}
		List templist = commondao.findByProperty(Class
				.forName("edu.zju.cims201.GOF.hibernate.pojo.Avidmknowledge"),
				"avidmglobledocumentid", globledocumentIID);
		if (null != templist && !templist.isEmpty())
			k = (Knowledge) templist.get(0);
		k.setKnowledgesourcefilepath(globledocumentIID);
		k.setFlashfilepath(globledocumentIID);
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
				Keyword keywordT = kservice
						.SearchAndSaveKeyword(keyword.trim());
				keywordlist.add(keywordT);
			}
		}
		k.setKeywords(keywordlist);
		//添加知识类型属性
		Knowledgetype knowledgetype = kservice.SearchAndSaveKnowledgetype("AVIDM");		
     	k.setKnowledgetype(knowledgetype);
		// 添加作者属性
		List<Author> authorlist = new ArrayList<Author>();

		Author authorT = kservice.searchAndSaveAuthor(u.getName());
		authorlist.add(authorT);
		k.setKauthors(authorlist);
		k.setUploader(u);
		k.setTitlename(documentName);
		Date uploadtimeT = new Date();
		if(null!=updatedate)
		k.setUploadtime(updatedate);
		else{
	
		k.setUploadtime(uploadtimeT);
		}
		k.setIsvisible(true);
		Ktype ktype = ktypeservice.getKtype("Avidmknowledge");
		k.setKtype(ktype);
		k.setStatus("5");
		k.setSecuritylevel(securitylevel);
		k.setAbstracttext(abstract_text);
		// 添加知识状态属性
		Version version = new Version();

		version.setVersionTime(uploadtimeT);

		//String versionNumber = versionname;
		version.setVersionNumber(versionname);
		k.setVersion(version);

		// 添加记录信息

		// 添加知识版本属性

		CommentRecord cr = new CommentRecord();
		cr.setCommentCount(new Long(0));
		cr.setViewCount(new Long(0));
		cr.setRate(new Float(0));
		cr.setDownloadCount(new Long(0));
		k.setCommentrecord(cr);
		System.out.println("documentTypeName="+documentTypeName);
		System.out.println("avidmfileiid="+fileIID);
		System.out.println("avidmglobledocumentid="+globledocumentIID);
		System.out.println("documentIID="+documentIID);
		System.out.println("avidmversioniid="+versionIID);
		PropertyUtils.setProperty(k, "avidmtype", documentTypeName);
		PropertyUtils.setProperty(k, "avidmfileiid", fileIID);
		PropertyUtils.setProperty(k, "avidmdocumentiid", documentIID);
		PropertyUtils.setProperty(k, "avidmproductiid", productIID);
		PropertyUtils.setProperty(k, "avidmversioniid", versionIID);
		PropertyUtils.setProperty(k, "avidmglobledocumentid", globledocumentIID);
		PropertyUtils.setProperty(k, "avidmhost", avidmhost);
		kservice.save(k,null);
		// 获得根元素迭代器

	}
	}
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

		List<Element> list = root.getChildren();
		for (Element e : list) {
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

		}
		  System.out.println("CARD_ID:"+CARD_ID);
		  System.out.println("abstract_text:"+abstract_text);
		  System.out.println("uploadername:"+uploadername);
		  System.out.println("fileKeyword:"+fileKeyword);
		  System.out.println("titlename:"+titlename);
          System.out.println("authorname:"+authorname);
		Knowledge k;
		try {
			// 获得具体的知识类型的实例，用接口knowledg作为容器
			k = (Knowledge) Class.forName(
					"edu.zju.cims201.GOF.hibernate.pojo.Avidmknowledge")
					.newInstance();
		} catch (InstantiationException e) {

			e.printStackTrace();
			logger.warn("错误的知识类型", "");
			throw new ServiceException("错误的知识类型");
		} catch (IllegalAccessException e) {

			e.printStackTrace();
			logger.warn("非法操作", "");
			throw new ServiceException("非法操作");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			logger.warn("没有该知识类型，没有找到对应的class{}",
					"edu.zju.cims201.GOF.hibernate.pojo.Avidmknowledge");
			throw new ServiceException("没有该知识类型，没有找到对应的class");
		}
		List templist = commondao.findByProperty(Class
				.forName("edu.zju.cims201.GOF.hibernate.pojo.Avidmknowledge"),
				"CARD_ID", CARD_ID);
		if (null != templist && !templist.isEmpty()) {
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
					.SearchAndSaveKnowledgetype("质量管理系统质量报告");
			k.setKnowledgetype(knowledgetype);
			k.setTitlename(titlename);

			// 添加作者属性
			List<Author> authorlist = new ArrayList<Author>();

			Author authorT = kservice.searchAndSaveAuthor(authorname);
			authorlist.add(authorT);
			k.setKauthors(authorlist);
			if (userservice.isUserExist(uploaderemail)) {
				k.setUploader(userservice.getUser(uploaderemail));

			} else {
				SystemUser u = new SystemUser();
				u.setEmail(uploaderemail);
				u.setPassword("000");
				u.setIsVisible(true);// 统一设为可见
				u.setSex("M");
				u.setPicturePath("fe74e496-7a47-441a-a53a-2c28d1846e97.gif");
				userservice.createUser(u);
				k.setUploader(u);

			}
			// k.setUploader(u);

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

			for (Element e : list) {
				for (KtypeProperty kp : ktypepropertylist) {
					if (null != e.getChildText(kp.getProperty().getName())) {

						PropertyUtils.setProperty(k,
								kp.getProperty().getName(),
								e.getChildText(kp.getProperty().getName().toUpperCase()));

					}

				}

			}
			kservice.save(k, null);
			imservice.saveMessageAndSubscribeInfo(k);
			// 获得根元素迭代器
		}

		// 获得根元素迭代器

	}
	
}
