package edu.zju.cims201.GOF.service.knowledge;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.util.Streams;

import org.apache.lucene.index.CorruptIndexException;
import org.hibernate.Query;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;

import ParserWord.ParserDandianWord;
import ParserWord.ParserWordJacob;
import ParserWord.ParserWordPoi;

import edu.zju.cims201.GOF.hibernate.pojo.Attachment;
import edu.zju.cims201.GOF.hibernate.pojo.Author;

import edu.zju.cims201.GOF.hibernate.pojo.Comment;

import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.Rating;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorList;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Version;
import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.dao.knowledge.AttachmentDao;
import edu.zju.cims201.GOF.dao.knowledge.AuthorDao;
import edu.zju.cims201.GOF.dao.knowledge.KeywordDao;
import edu.zju.cims201.GOF.dao.knowledge.KnowledgeDao;
import edu.zju.cims201.GOF.dao.knowledge.KnowledgetypeDao;
import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.dao.knowledge.VersionDao;
import edu.zju.cims201.GOF.dao.logging.SysBehaviorLogDao;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeServiceImpl;
import edu.zju.cims201.GOF.service.logging.SysBehaviorListService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;
import edu.zju.cims201.GOF.service.systemFile.FileService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.service.webservice.ConvertFlashWebService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;

/**
 * 提供知识相关服务
 * 
 * author hebi
 */

@Transactional
@Service
public class KnowledgeServiceImpl implements KnowledgeService {
	private static Logger logger = LoggerFactory
			.getLogger(KtypeServiceImpl.class);
	// private Knowledge kg;
	@Resource(name = "metaKnowledgeDao")
	private MetaKnowledgeDao kdao;
	@Resource(name = "commonDao")
	private CommonDao commondao;
	@Resource(name = "knowledgeDao")
	private KnowledgeDao knowledgedao;
	@Resource(name = "authorDao")
	private AuthorDao authorDao;

	@Resource(name = "sysBehaviorLogDao")
	private SysBehaviorLogDao sysBehaviorLogDao;
	

	@Resource(name = "attachmentDao")
	private AttachmentDao attdao;
	 @Resource(name = "versionDao")
	 private VersionDao versiondao;
	@Resource(name = "keywordDao")
	private KeywordDao keywordDao;
	@Resource(name = "knowledgetypeDao")
	private KnowledgetypeDao knowledgetypeDao;
	@Resource(name = "userServiceImpl")
	private UserService userservice;
	@Resource(name = "ktypeServiceImpl")
	private KtypeService ktypeservice;
	@Resource(name = "convertFlashWebServiceImpl")
	private ConvertFlashWebService convertFlashWebService;
	@Resource(name = "fileServiceImpl")
	private FileService fileservcie;

	@Resource(name = "treeServiceImpl")
	private TreeService treeservice;
	@Resource(name = "fullTextServiceImpl")
	private FullTextService fulltextservice;
	public String delete(Long knowledgeID) {

		kdao.delete(knowledgeID);
		return "1";
	}

	public String getFlash(MetaKnowledge knowledge) {
		// TODO 自动生成方法存根
		return null;
	}

	public MetaKnowledge getMetaknowledge(Long knowledgeID) {
		MetaKnowledge k = kdao.findUniqueBy("id", knowledgeID);
		// System.out.println(k.getUploader().getEmail());
		return k;
	}

	public Object getProperty(Long knowledgeID, String propertyName) {
		MetaKnowledge knowledge = getMetaknowledge(knowledgeID);
		Ktype ktype = knowledge.getKtype();
		Knowledge kg = getExtendKnowledgeByKtype(ktype);
		kg = commondao.findById(kg.getClass(), knowledgeID);

		try {
			return PropertyUtils.getProperty(kg, propertyName);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.warn("非法操作", "");
			throw new ServiceException("非法操作");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			logger.warn("错误调用", "");
			throw new ServiceException("错误调用");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			logger.warn("没有该方法", "");
			throw new ServiceException("没有该方法");
		}
	}

	public List<Property> getKnowledgePropertyValue(Long knowledgeID) {
		MetaKnowledge knowledge = getMetaknowledge(knowledgeID);

		Ktype ktype = knowledge.getKtype();

		Knowledge kg = getExtendKnowledgeByKtype(ktype);
		kg = commondao.findById(kg.getClass(), knowledgeID);
		// TODO 测试一下在lazy=true的情况下是否可以直接得到属性列表

		List<KtypeProperty> ktypepropertites = ktypeservice
				.listKtypeProperties(ktype.getId());
		// 将需要显示的属性设置到显示列表中
		List<Property> propertyValues = new ArrayList<Property>();

		// 遍历将所有的属性和属性值摄入到属性值列表中
		for (KtypeProperty ktp : ktypepropertites) {
			Property property = ktp.getProperty();
			if (property.getIsVisible()) {
				property.setDescription(ktp.getShowname());
				try {
					property.setValue(PropertyUtils.getProperty(kg,
							property.getName()));
					propertyValues.add(property);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.warn("非法操作", "");
					throw new ServiceException("非法操作");
				} catch (InvocationTargetException e) {

					e.printStackTrace();
					logger.warn("错误调用", "");
					throw new ServiceException("错误调用");
				} catch (IllegalArgumentException e) {

					e.printStackTrace();
					logger.warn("错误调用", "");
					// throw new ServiceException("错误调用");
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					logger.warn("没有该方法", "");
					throw new ServiceException("没有该方法");
				}

			}
		}
		return propertyValues;
	}
	
	
	public List<KtypeProperty> getKtypePropertyValue(Long knowledgeID) {
		MetaKnowledge knowledge = getMetaknowledge(knowledgeID);
		
		Ktype ktype = knowledge.getKtype();
		
		Knowledge kg = getExtendKnowledgeByKtype(ktype);
		kg = commondao.findById(kg.getClass(), knowledgeID);
		// TODO 测试一下在lazy=true的情况下是否可以直接得到属性列表
		
		List<KtypeProperty> ktypepropertites = ktypeservice
		.listKtypeProperties(ktype.getId());
		// 将需要显示的属性设置到显示列表中
		List<KtypeProperty> ktValues = new ArrayList<KtypeProperty>();
		
		// 遍历将所有的属性和属性值摄入到属性值列表中
		for (KtypeProperty ktp : ktypepropertites) {
			Property property = ktp.getProperty();
			if (property.getIsVisible()) {
				property.setDescription(ktp.getShowname());
				try {
					property.setValue(PropertyUtils.getProperty(kg,
							property.getName()));
					ktp.setProperty(property);			
					ktValues.add(ktp);
					
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.warn("非法操作", "");
					throw new ServiceException("非法操作");
				} catch (InvocationTargetException e) {
					
					e.printStackTrace();
					logger.warn("错误调用", "");
					throw new ServiceException("错误调用");
				} catch (IllegalArgumentException e) {
					
					e.printStackTrace();
					logger.warn("错误调用", "");
					// throw new ServiceException("错误调用");
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					logger.warn("没有该方法", "");
					throw new ServiceException("没有该方法");
				}
				
			}
		}
		return ktValues;
	}

	public boolean isKnowledgeExist(String knowledgeTitle) {

		List<MetaKnowledge> klist = kdao.findBy("titleName", knowledgeTitle);
		if (klist.size() > 0)
			return true;
		// TODO 自动生成方法存根
		return false;
	}

	public String parseKnowledgeSource(String KnowledgeSource) {
		// TODO 自动生成方法存根
		return null;
	}

	@Transactional
	public String save(Knowledge knowledge, Long fileid) {
		
		String flashfilename = "";
		String docfilename = knowledge.getKnowledgesourcefilepath();
		if (null != Constants.FLASHCONVERTMETHOD
				&& Constants.FLASHCONVERTMETHOD.equals("local")) {
			// ****************************通过系统文件转换flash
			if (null != docfilename && docfilename.indexOf(".") != -1) {
				flashfilename = docfilename.substring(0,
						docfilename.lastIndexOf("."))
						+ ".swf";
				String ext = docfilename
						.substring(docfilename.lastIndexOf("."));
				int p = 100;
				if (null != ext && (ext.equals(".pdf") || ext.equals(".PDF")))
					p = 1200;
				System.out.println("ext==" + ext);
				System.out.println("p==" + p);
				int i = convertDOC2SWF(docfilename, flashfilename);

				if (i == -1) {
					throw new ServiceException("flash文件转换错误！");
				}
				knowledge.setFlashfilepath(flashfilename);
				File tempFilea = null;
				boolean fileexist = false;
				for (int t = 0; t < p; t++) {
					tempFilea = new File(Constants.SOURCEFILE_PATH_TEMP + "\\"
							+ flashfilename);

					// 用于知识客户端上传的时候等待 ，避免flash转换不成功或还没有保存flash就移动导致失败
					if (tempFilea.exists()) {
						fileexist = true;
						break;
					} else {
						try {
							Thread.currentThread().sleep(new Long(500));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("等待第" + t + "个500毫秒");
					}

				}
				// 如果是pdf则关闭pdf阅读程序

				try {

					if (fileexist) {
						String command = "tskill AcroRd32";

						System.out.println("command + \" :" + command);

						Process pro = Runtime.getRuntime().exec(command);
						pro.waitFor();
						fileservcie.save(tempFilea, flashfilename);

					} else {
						String command = "tskill FlashPrinter";
						System.out.println("command + \" :" + command);
						Process pro = Runtime.getRuntime().exec(command);
						pro.waitFor();
						command = "tskill WINWORD";
						System.out.println("command + \" :" + command);
						pro = Runtime.getRuntime().exec(command);
						pro.waitFor();
						command = "tskill AcroRd32";
						System.out.println("command + \" :" + command);
						pro = Runtime.getRuntime().exec(command);
						pro.waitFor();
						System.out.println("文件不存在");

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				moveTemp2Target(Constants.SOURCEFILE_PATH_TEMP,
						Constants.SOURCEFILE_PATH, flashfilename);
				moveTemp2Target(Constants.SOURCEFILE_PATH_TEMP,
						Constants.SOURCEFILE_PATH, docfilename);

			}
			// ××××××××××××××××××××××××××××××××××××××××××××通过系统文件转换flash结束
		} else {
			// ××××××××××××××××××××××××××××××××××××××××××××通过webservice转换flash
			if (null != docfilename && docfilename.indexOf(".") != -1) {
				try {
					flashfilename = convertFlashWebService
							.ConvertAndSaveFlash(fileid);

					if (null != flashfilename && !flashfilename.equals("fail"))
						knowledge.setFlashfilepath(flashfilename + ".swf");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// ××××××××××××××××××××××××××××××××××××××××××××通过webservice转换flash结束
		// System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		commondao.saveOrUpdate(knowledge);
		commondao.flush();
		return "1";
	}


	// @Transactional
	// public MetaKnowledge save(Ktype ktype, List<Property> PropertyValues) {
	// // System.out.println("执行save方法了");
	// // Ktype ktype = ktypeservice.getKtype(ktypeID);
	// Knowledge kg = getExtendKnowledgeByKtype(ktype);
	// String docfilename = "";
	// for (Property property : PropertyValues) {
	// try {
	// if (property.getName().equals("knowledgesourcefilepath")) {
	// docfilename = property.getValue().toString();
	//
	// }
	// PropertyUtils.setProperty(kg, property.getName(), property
	// .getValue());
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// logger.warn("非法操作", "");
	// throw new ServiceException("非法操作");
	// } catch (InvocationTargetException e) {
	//
	// e.printStackTrace();
	// logger.warn("错误调用", "");
	// throw new ServiceException("错误调用");
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// logger.warn("没有该方法", "");
	// throw new ServiceException("没有该方法");
	// }
	//
	// }
	// // 首先转换flash文件
	// // 判断flash是否可以转换
	// System.out.println("开始转换flash文件");
	// String ext = docfilename.substring(docfilename.lastIndexOf(".") + 1);
	// // System.out.println("ext===" + ext);
	// if (null == ext
	// || (!ext.equals("doc") && !ext.equals("docx")
	// && !ext.equals("ppt") && !ext.equals("pptx")
	// && !ext.equals("xls") && !ext.equals("xlsx"))) {
	// System.out.println("源文件格式不支持flash转换");
	//
	// } else {
	// String flashfilename = docfilename.substring(0, docfilename
	// .lastIndexOf("."))
	// + ".swf";
	//
	// int i = convertDOC2SWF(docfilename, flashfilename);
	// if (i == 3)
	// System.out.println("flash转换成功！ ");
	// if (i == -1) {
	// throw new ServiceException("flash文件转换错误！");
	// }
	// kg.setFlashfilepath(flashfilename);
	// }
	// // 转换成功的话 将flash 、源文件、附件 从temp文件中拷贝到存储文件中
	// // 将源文件从临时文件夹中转移到源文件文件夹中
	// System.out.println("源文件从临时文件夹拷贝到正式文件夹开始！");
	// if (docfilename != null) {
	// moveTemp2Target(Constants.SOURCEFILE_PATH_TEMP,Constants.SOURCEFILE_PATH,docfilename);
	// System.out.println("源文件从临时文件夹拷贝到正式文件夹成功！");
	// } else {
	// System.out.println("不存在源文件路径");
	// }
	// // 将附件文件从临时文件夹中转移到附件文件文件夹中
	// System.out.println("将附件拷贝到正式文件夹开始");
	// Set<Attachment> attachmentlist = kg.getAttachments();
	//
	// for (Iterator iterator = attachmentlist.iterator(); iterator.hasNext();)
	// {
	// Attachment kat = (Attachment) iterator.next();
	// System.out.println("移动附件" + kat.getAttachmentName());
	// //将kg对象设入到附件关联信息中的knowledge中
	// kat.setKnowledge(kg);
	// moveTemp2Target(Constants.ATTACHMENT_PATH_TEMP,Constants.ATTACHMENT_PATH,kat.getAttachmentPath());
	// }
	// kg.setAttachments(attachmentlist);
	// kg.getCommentrecord().setKnowledge(kg);
	// //处理将kg对象设入到commentrecord关联信息中的knowledge中
	//
	// //保存知识
	// commondao.save(kg);
	// commondao.flush();
	// // TODO 自动生成方法存根
	// return (MetaKnowledge) kg;
	// }

	@Transactional
	public MetaKnowledge save(Ktype ktype, List<Property> PropertyValues) {
		System.out.println(ktype);
		Knowledge kg = getExtendKnowledgeByKtype(ktype);
		String docfilename = "";
		for (Property property : PropertyValues) {
			try {
				if (property.getName().equals("knowledgesourcefilepath")) {
					docfilename = property.getValue().toString();

				}
				System.out.println(property.getName());
				System.out.println(property.getValue());
				PropertyUtils.setProperty(kg, property.getName(),
						property.getValue());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				logger.warn("非法操作", "");
				throw new ServiceException("非法操作");
			} catch (InvocationTargetException e) {

				e.printStackTrace();
				logger.warn("错误调用", "");
				throw new ServiceException("错误调用");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				logger.warn("没有该方法", "");
				throw new ServiceException("没有该方法");
			}

		}
		// 首先转换flash文件
		// 判断flash是否可以转换


		if(!ktype.getName().equals("Question")&& !ktype.getName().equals("Article"))//后半句 江丁丁添加 2013-6-15 

		{
		System.out.println("开始转换flash文件");
		String ext = docfilename.substring(docfilename.lastIndexOf(".") + 1);
	
	  
			String flashfilename = docfilename.substring(0, docfilename
					.lastIndexOf("."))

					+ ".swf";

			kg.setFlashfilepath(flashfilename);

			// 转换成功的话 将flash 、源文件、附件 从temp文件中拷贝到存储文件中
			// 将源文件从临时文件夹中转移到源文件文件夹中
			System.out.println("源文件从临时文件夹拷贝到正式文件夹开始！");
			if (docfilename != null) {
				moveTemp2Target(Constants.SOURCEFILE_PATH_TEMP,
						Constants.SWF_PATH, docfilename);
				System.out.println("源文件从临时文件夹拷贝到正式文件夹成功！");
			} else {
				System.out.println("不存在源文件路径");
			}
			System.out.println("flash文件从临时文件夹拷贝到正式文件夹开始！");
			if (flashfilename != null) {

				// 判断是否有flash文件 如果没有则不移动将flash名称更改为文件名称
				File tempFilea = new File(Constants.SOURCEFILE_PATH_TEMP + "\\"
						+ flashfilename);
				if (tempFilea.exists()) {

					moveTemp2Target(Constants.SOURCEFILE_PATH_TEMP,
							Constants.SOURCEFILE_PATH, flashfilename);
					System.out.println("flash文件从临时文件夹拷贝到正式文件夹成功！");
				}

				else {
					kg.setFlashfilepath(docfilename);
					System.out.println("flash文件没有找到，将flash名称更改为文件名称！");
				}

			} else {
				System.out.println("不存在flash文件路径");
			}
		}
		// 将附件文件从临时文件夹中转移到附件文件文件夹中
		System.out.println("将附件拷贝到正式文件夹开始");
		Set<Attachment> attachmentlist = kg.getAttachments();

		for (Iterator iterator = attachmentlist.iterator(); iterator.hasNext();) {
			Attachment kat = (Attachment) iterator.next();
			System.out.println("移动附件" + kat.getAttachmentName());
			// 将kg对象设入到附件关联信息中的knowledge中
			kat.setKnowledge(kg);
			moveTemp2Target(Constants.ATTACHMENT_PATH_TEMP,
					Constants.ATTACHMENT_PATH, kat.getAttachmentPath());
		}
		kg.setAttachments(attachmentlist);
		kg.getCommentrecord().setKnowledge(kg);
		// 处理将kg对象设入到commentrecord关联信息中的knowledge中

		// 保存知识
		commondao.save(kg);
		commondao.flush();
		// TODO 自动生成方法存根
		return (MetaKnowledge) kg;
	}
	//pl 批量上传时保存知识属性不需要转化
	public MetaKnowledge onebyonesave(Ktype ktype, List<Property> PropertyValues) {
		System.out.println(ktype);
		Ktype t = ktype;
		Knowledge kg = getExtendKnowledgeByKtype(t);
		String docfilename = "";
		for (Property property : PropertyValues) {
			try {
				if (property.getName().equals("knowledgesourcefilepath")) {
					docfilename = property.getValue().toString();

				}
				System.out.println(property.getName());
				System.out.println(property.getValue().equals(ktype));
				PropertyUtils.setProperty(kg, property.getName(),property.getValue());	
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				logger.warn("非法操作", "");
				throw new ServiceException("非法操作");
			} catch (InvocationTargetException e) {

				e.printStackTrace();
				logger.warn("错误调用", "");
				throw new ServiceException("错误调用");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				logger.warn("没有该方法", "");
				throw new ServiceException("没有该方法");
			}

		}
		
		
		kg.getCommentrecord().setKnowledge(kg);

		// 保存知识
		commondao.save(kg);
		commondao.flush();
		return (MetaKnowledge) kg;
	}
	@Transactional
	public MetaKnowledge updateFixedKnowledge(Knowledge kg, List<Property> PropertyValues) {

		String docfilename = "";
		for (Property property : PropertyValues) {
			try {
				if (property.getName().equals("knowledgesourcefilepath")) {
					docfilename = property.getValue().toString();

				}
				System.out.println(property.getName());
				System.out.println(property.getValue());
				PropertyUtils.setProperty(kg, property.getName(),
						property.getValue());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				logger.warn("非法操作", "");
				throw new ServiceException("非法操作");
			} catch (InvocationTargetException e) {

				e.printStackTrace();
				logger.warn("错误调用", "");
				throw new ServiceException("错误调用");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				logger.warn("没有该方法", "");
				throw new ServiceException("没有该方法");
			}

		}
		// 首先转换flash文件
		// 判断flash是否可以转换


		if(!kg.getKtype().getName().equals("Question"))

		{
		System.out.println("开始转换flash文件");
		String ext = docfilename.substring(docfilename.lastIndexOf(".") + 1);
	
	  
			String flashfilename = docfilename.substring(0, docfilename
					.lastIndexOf("."))

					+ ".swf";

			kg.setFlashfilepath(flashfilename);

			// 转换成功的话 将flash 、源文件、附件 从temp文件中拷贝到存储文件中
			// 将源文件从临时文件夹中转移到源文件文件夹中
			System.out.println("源文件从临时文件夹拷贝到正式文件夹开始！");
			if (docfilename != null) {
				moveTemp2Target(Constants.SOURCEFILE_PATH_TEMP,
						Constants.SWF_PATH, docfilename);
				System.out.println("源文件从临时文件夹拷贝到正式文件夹成功！");
			} else {
				System.out.println("不存在源文件路径");
			}
			System.out.println("flash文件从临时文件夹拷贝到正式文件夹开始！");
			if (flashfilename != null) {

				// 判断是否有flash文件 如果没有则不移动将flash名称更改为文件名称
				File tempFilea = new File(Constants.SOURCEFILE_PATH_TEMP + "\\"
						+ flashfilename);
				if (tempFilea.exists()) {

					moveTemp2Target(Constants.SOURCEFILE_PATH_TEMP,
							Constants.SOURCEFILE_PATH, flashfilename);
					System.out.println("flash文件从临时文件夹拷贝到正式文件夹成功！");
				}

				else {
					kg.setFlashfilepath(docfilename);
					System.out.println("flash文件没有找到，将flash名称更改为文件名称！");
				}

			} else {
				System.out.println("不存在flash文件路径");
			}
		}
		// 将附件文件从临时文件夹中转移到附件文件文件夹中
		System.out.println("将附件拷贝到正式文件夹开始");
		Set<Attachment> attachmentlist = kg.getAttachments();

		for (Iterator iterator = attachmentlist.iterator(); iterator.hasNext();) {
			Attachment kat = (Attachment) iterator.next();
			System.out.println("移动附件" + kat.getAttachmentName());
			// 将kg对象设入到附件关联信息中的knowledge中
			kat.setKnowledge(kg);
			moveTemp2Target(Constants.ATTACHMENT_PATH_TEMP,
					Constants.ATTACHMENT_PATH, kat.getAttachmentPath());
		}
		kg.setAttachments(attachmentlist);
		kg.getCommentrecord().setKnowledge(kg);
		// 处理将kg对象设入到commentrecord关联信息中的knowledge中

		// 保存知识
		commondao.save(kg);
		commondao.flush();
		// TODO 自动生成方法存根
		return (MetaKnowledge) kg;
	}

	public void moveTemp2Target(String tempdir, String targetdir,
			String filename) {
		try {
			File savedira = new File(targetdir);
			if (!savedira.exists())
				savedira.mkdirs();// 如果目录不存在就创建

			File tempFilea = null;
			boolean fileexist = false;
			for (int i = 0; i < 100; i++) {
				tempFilea = new File(tempdir + "\\" + filename);
				System.out.println(tempdir + "\\" + filename);
				// 用于知识客户端上传的时候等待 ，避免flash转换不成功或还没有保存flash就移动导致失败
				if (tempFilea.exists()) {
					fileexist = true;
					break;
				} else {
					try {
						Thread.currentThread().sleep(new Long(500));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("等待第一个500毫秒");
				}

			}

			if (fileexist) {
				BufferedInputStream ina;
				ina = new BufferedInputStream(new FileInputStream(tempFilea));
				BufferedOutputStream outa = new BufferedOutputStream(
						new FileOutputStream(new File(targetdir, filename)));

				Streams.copy(ina, outa, true);

				tempFilea.delete();// 删除临时文件
			} else {
				System.out.println("文件不存在");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("文件移动错误");
		}

	}

	public Knowledge getExtendKnowledgeByKtype(Ktype ktype) {
		Knowledge kg = null;
		try {
			// 获得具体的知识类型的实例，用接口knowledg作为容器
			kg = (Knowledge) Class.forName(ktype.getClassName()).newInstance();
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
			logger.warn("没有该知识类型，没有找到对应的class{}", ktype.getClass());
			throw new ServiceException("没有该知识类型，没有找到对应的class");
		}
		return kg;
	}

	public synchronized int convertDOC2SWF(String sourcePath, String fileName) {
		try {
			// 说明，在调用命令行的时候 在参数路径中包含空格的情况下，需要在命令行路径前后添加"",包成一个整体，这样就没有问题了
			String command = Constants.FLASHPAPER_PATH + "\\FlashPrinter.exe"
					+ " \"" + Constants.SOURCEFILE_PATH_TEMP + "\\"
					+ sourcePath + "\"" + " -o " + "\""
					+ Constants.SOURCEFILE_PATH_TEMP + "\\"// swf保存的路径
					+ fileName + "\"";
			System.out.println("command + \" :" + command);
			Process pro = Runtime.getRuntime().exec(command);
			int t = pro.waitFor();
			System.out.println(t);
			return t;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	public String searchKnowledgestring(String serachword) {
		PageDTO pagetable = searchKnowledge(serachword);
		String json = JSONUtil.write(pagetable);

		return json;
	}

	public PageDTO searchKnowledge(String searchword) {
		// JSONReader jr = new JSONReader();
		//
		// HashMap propertyValues = (HashMap) jr.read(searchword);
		JSONUtil ju = new JSONUtil();
		HashMap propertyValues = (HashMap) ju.read(searchword);
		return searchKnowledge(propertyValues);

	}

	public PageDTO searchKnowledge(HashMap searchpropertyValues) {
		HashMap searchwordtohilght = new HashMap();
		List<Object> queryParams = new ArrayList<Object>();
		Long ktypeId;

		if (null != searchpropertyValues.get("selectedktype"))
			ktypeId = Long.valueOf(searchpropertyValues.get("selectedktype")
					.toString());
		else {
			if (null != searchpropertyValues.get("selectedktypename")) {
				Ktype ktype = ktypeservice.getKtype(searchpropertyValues.get(
						"selectedktypename").toString());
				if (null != ktype) {
					ktypeId = ktype.getId();
				} else
					ktypeId = new Long(1);
			} else
				ktypeId = new Long(1);

		}
		List<HashMap> propertyList = (List<HashMap>) searchpropertyValues
				.get("searchlist");

		searchpropertyValues.remove("selectedktype");
		searchpropertyValues.remove("selectedktypename");
		Ktype ktype = ktypeservice.getKtype(ktypeId);
		// System.out.println();
		// 根据查询条件构建 hql的内容，为了避免构建是多 and 的问题，默认是 o.status!=0
		// ，保证未入库知识不会查出来，当然会出现出错时查出所有入库知识，问题待解决
		StringBuffer hql = new StringBuffer("");
		hql.append(" o.status !='0' and o.isvisible=true and (");
		// 由于一些级联关系信息，在构建hql时 添加from 中更多的表内容
		StringBuffer hqlfrom = new StringBuffer(" ");
		int i = 0;
		for (HashMap propertyValues : propertyList) {
			if (i == 0) {
				if (null != propertyValues.get("and_or")) {
					propertyValues.get("and_or");
					propertyValues.remove("and_or");
					propertyValues.put("and_or", " ");
				}
				if (null == propertyValues.get("value")
						|| propertyValues.get("value").toString().trim()
								.equals("")) {

					hql.append(" ( 1=1 ) ");
					// queryParams.add("%" + string + "%");
				}
			}
			i++;
			String key = propertyValues.get("name").toString();
			// System.out.println("key======"+key);

			Object value = new Object();

			String and_or = "";
			if (null != propertyValues.get("and_or"))
				and_or = propertyValues.get("and_or").toString();
			// 判断用户的输入属性，
			// 如果是日期类型的单独构建查询条件，因为对时间的查询往往是一个时间段内查询，因此不能和其他属性查询统一构建
			if (null != propertyValues.get("propertytype")
					&& propertyValues.get("propertytype").toString()
							.equalsIgnoreCase("date")) {
				if ((null != propertyValues.get("after") && !"".equals( propertyValues.get("after"))) || ( null != propertyValues.get("before")&&!"".equals( propertyValues.get("before")))
						) {
                     if(i==1)
                    hql.append(" and  ( ");
					hql.append(" " + and_or + " ( ");
				}
				


				if (null != propertyValues.get("after")
						|| null != propertyValues.get("before")) {
					value = propertyValues.get("after");
					// 按知识上传时间之后查询
					if (value != null && !"".equals(value)) {

						hql.append("  o." + key + ">=to_date('" + value
								+ " 00:00:00','yyyy-mm-dd hh24:mi:ss')");

					}
					if (null != propertyValues.get("after") && !"".equals( propertyValues.get("after")) &&null != propertyValues.get("before") 
							&&!"".equals( propertyValues.get("before"))) {

						hql.append(" and ");
					}

					value = propertyValues.get("before");
					// 按知识上传时间之前查询
					if (value != null && !"".equals(value)) {

						hql.append(" o." + key + " <=to_date('" + value
								+ " 23:59:59','yyyy-mm-dd hh24:mi:ss')");

					}
				}
				if ((null != propertyValues.get("after") && !"".equals( propertyValues.get("after"))) || ( null != propertyValues.get("before")&&!"".equals( propertyValues.get("before")))
				) {

					hql.append(" )");
		}
		
				hql.append(" )");
			}
			// 如果不是日期类型的，进行下列查询条件拼装
			// 其中如果中间有空格的时候 用空格分隔开来
			else {

				value = propertyValues.get("value");

				// System.out.println("value======"+value);

				if (null != value && !value.toString().trim().equals("")) {

					// 如果是在知识通用类型中定义的set或pojo对象属性，需要单独构建查询条件
					boolean isnotSet = true;
					// 如果是按照作者查询
					if (key.equals("kauthors")) {
						String[] values = value.toString()
								.replaceAll("  ", " ").trim().split(" ");
						if (hqlfrom.toString().indexOf(",Author author ") == -1)
							hqlfrom.append(" ,Author author ");
						for (String string : values) {

							hql.append(" " + and_or + " ");
							hql.append(" ( author.id in elements(o.kauthors )  and author.authorName like ? )");
							queryParams.add("%" + string + "%");

						}
						isnotSet = false;
						searchwordtohilght.put("kauthors", value);
					}
					// 如果是按照作者id
					if (key.equals("kauthorid")) {
						if (hqlfrom.toString().indexOf(",Author author ") == -1)
							hqlfrom.append(" ,Author author ");
						hql.append("  ( author.id in elements(o.kauthors )  and author.id = ? )");
						queryParams.add(new Long(value.toString()));
						searchwordtohilght.put("kauthorid",
								new Long(value.toString()));
						isnotSet = false;
					}

					if (key.equals("keywords")) {
						String[] values = value.toString()
								.replaceAll("  ", " ").trim().split(" ");
						if (hqlfrom.toString().indexOf(",Keyword keywords ") == -1)
							hqlfrom.append(" ,Keyword keywords ");
						for (String string : values) {
							hql.append(" " + and_or + " ");
							hql.append(" ( keywords.id in elements(o.keywords )  and keywords.keywordName like ? )");
							queryParams.add("%" + string + "%");
						}
						searchwordtohilght.put("keywords", value);
						isnotSet = false;
					}
					if (key.equals("keywordid")) {
						if (hqlfrom.toString().indexOf(",Keyword keywords ") == -1)
							hqlfrom.append(" ,Keyword keywords ");
						hql.append("  ( keywords.id in elements(o.keywords )  and keywords.id = ? )");
						queryParams.add(new Long(value.toString()));
						searchwordtohilght.put("keywordid",
								new Long(value.toString()));
						isnotSet = false;
					}

					if (key.equals("domainnodeisnull")) {
						hql.append(" " + and_or + " ");
						hql.append(" ( o.domainnode is null and o.uploader = ?)");
						// queryParams.add(new Long(value.toString()));

						queryParams.add(userservice.getUser());
						isnotSet = false;
					}
					if (key.equals("domainnodeisnotnull")) {
						hql.append(" " + and_or + " ");
						hql.append(" ( o.domainnode is not null and o.uploader = ?)");
						// queryParams.add(new Long(value.toString()));
						queryParams.add(userservice.getUser());
						isnotSet = false;
					}
					if (key.equals("domainnode")) {
						hql.append(" " + and_or + " ");
						hql.append(" ( o.domainnode.id = ? )");
						queryParams.add(new Long(value.toString()));

						isnotSet = false;
					}
					if (key.equals("domainnodeid")) {

						hql.append("  ( o.domainnode.id = ? )");
						queryParams.add(new Long(value.toString()));

						isnotSet = false;
					}

					// 根据大众标签查询，可能有问题

					if (key.equals("userknowledgetags")) {
						if (hqlfrom.toString().indexOf(",Tag tag") == -1)
							hqlfrom.append(" ,Tag tag ");
						if (hqlfrom.toString().indexOf(
								",UserKnowledgeTag uktag") == -1)
							hqlfrom.append(" ,UserKnowledgeTag uktag ");
						hql.append(" " + and_or + " ");
						hql.append(" ( uktag.id in elements(o.userknowledgetags)  and tag.id =uktag.tag.id and tag.tagName like ? )");

						queryParams.add("%" + value + "%");
						//

						isnotSet = false;
					}
					if (key.equals("tagid")) {

						if (hqlfrom.toString().indexOf(",Tag tag") == -1)
							hqlfrom.append(" ,Tag tag ");
						if (hqlfrom.toString().indexOf(
								",UserKnowledgeTag uktag") == -1)
							hqlfrom.append(" ,UserKnowledgeTag uktag ");

						hql.append(" ( uktag.id in elements(o.userknowledgetags)  and tag.id =uktag.tag.id and tag.id = ? )");
						queryParams.add(new Long(value.toString()));
						//
						isnotSet = false;
					}
					//
					if (key.equals("utagid")) {

						if (hqlfrom.toString().indexOf(
								",UserKnowledgeTag uktag") == -1)
							hqlfrom.append(" ,UserKnowledgeTag uktag ");

						hql.append(" ( uktag.id in elements(o.userknowledgetags) and uktag.tager= ? and uktag.tag.id = ? )");
						// TODO 得到用户的名字

						queryParams.add(userservice.getUser());
						queryParams.add(new Long(value.toString()));
						//
						isnotSet = false;
					}
					if (key.equals("knowledgetype")) {
						hql.append(" " + and_or + " ");
						hql.append(" ( o.knowledgetype.knowledgeTypeName = ? )");
						queryParams.add(value);
						searchwordtohilght.put("knowledgetype", value);
						isnotSet = false;
					}
					if (key.equals("knowledgetypeid")) {
						hql.append(" " + and_or + " ");
						hql.append(" ( o.knowledgetype.id = ? )");
						queryParams.add(value);
						searchwordtohilght.put("knowledgetypeid", value);
						isnotSet = false;
					}
					if (key.equals("uploader")) {
						hql.append(" " + and_or + " ");
						hql.append(" ( o.uploader.name like ? )");
						queryParams.add("%" + value + "%");
						searchwordtohilght.put("uploader", value);
						isnotSet = false;
					}
					if (key.equals("ktypeid")) {
						hql.append(" " + and_or + " ");
						hql.append(" ( o.ktype.id = ? )");
						queryParams.add(value);
						isnotSet = false;
					}
					if (key.equals("uploaderid")) {
						hql.append(" ( o.uploader.id = ? )");
						queryParams.add(new Long(value.toString()));
						isnotSet = false;
					}
					if (key.equals("categories")) {
						if (hqlfrom.toString().indexOf(
								",CategoryTreeNode categories") == -1)
							hqlfrom.append(" ,CategoryTreeNode categories ");
						hql.append(" " + and_or + " ");
						hql.append(" ( categories.id in elements(o.categories )  and categories.id = ? )");
						queryParams.add(new Long(value.toString()));

						isnotSet = false;
					}

					if (key.equals("categoriesid")) {
						if (hqlfrom.toString().indexOf(
								",CategoryTreeNode categories") == -1)
							hqlfrom.append(" ,CategoryTreeNode categories ");

						hql.append("  ( categories.id in elements(o.categories )  and categories.id = ? )");
						queryParams.add(new Long(value.toString()));

						isnotSet = false;
					}

					if (key.equals("versions")) {
						hql.append(" " + and_or + " ");
						hql.append(" ( o.versions.id = ? )");
						queryParams.add(value);
						isnotSet = false;
					}
					//问题的回答次数和解决情况
					if (key.equals("answernumbers")) {
						hql.append(" " + and_or + " ");
						hql.append(" ( o.commentrecord.commentCount >= ? )");
						queryParams.add(new Long(value.toString()));
						isnotSet = false;
					}
					if (key.equals("isSolved")) {
						hql.append(" " + and_or + " ");
						if(value.toString().equals("1")){
							hql.append(" ( o.questionstatus != ? )");							
						}else{
							hql.append(" ( o.questionstatus = ? )");							
						}
						queryParams.add(2L);						
						isnotSet = false;
					}
					
					//------需屏蔽的知识类型名字ktypenames
					if (key.equals("ktypenames")) {
						String[] values = value.toString()
								.replaceAll("  ", " ").trim().split(" ");
						for (String string : values) {
							hql.append(" " + and_or + " ");							
							hql.append(" o.ktype.name not like ? ");
							queryParams.add("%" + string + "%");
						}
						isnotSet = false;
					}					
					
					// 对不是set类型的属性进行直接查询 当然如果是有空格分开两个查询条件，则分开

					if (isnotSet) {
						value = value.toString().replaceAll("  ", " ");
						String[] values = value.toString().trim().split(" ");

						for (String string : values) {

							if (!string.trim().equals("")) {
								hql.append(" " + and_or + " ");
								hql.append(" ( o." + key + " like  ? ) ");
								queryParams.add("%" + string + "%");
							}
						}
						searchwordtohilght.put(key, value);
					}

				}

			}

		}
		if ((hql.toString().trim().lastIndexOf("and (") + 5) == hql.toString()
				.trim().length())
			throw new ServiceException("请不要搜索空数据");
		else
			hql.append(" )");
		Knowledge kg = getExtendKnowledgeByKtype(ktype);

		// JSONWriter jwriter = new JSONWriter();
		PageDTO page = new PageDTO();
		int index = 0;
		int size = Constants.rawPrepage;
		if (null != searchpropertyValues.get("index"))
			index = Integer.parseInt(searchpropertyValues.get("index")
					.toString());

		page.setFirstindex(index);
		if (null != searchpropertyValues.get("size"))
			size = Integer
					.parseInt(searchpropertyValues.get("size").toString());

		page.setPagesize(size);
		// 江丁丁添加  按上传时间排序  2013-7-1
		page.setOrderBy("order by o.id desc");
//       System.out.println(hql);
		PageDTO pagetable = searchKnowledge(hqlfrom.toString(), hql.toString(),
				kg.getClass(), page, queryParams.toArray(), searchwordtohilght);

		// String resultjason = jwriter.write(pagetable);
		return pagetable;
		// return null;
	}

	public PageDTO searchKnowledge(String hqlfrom, String hql,
			Class<? extends Knowledge> entityClass, PageDTO page,
			Object[] objects, HashMap searchpropertyValues) {
		String queryString = " select distinct o.id  from "
				+ entityClass.getName() + " o " + hqlfrom + " "
				+ ((hql == null || hql.equals("")) ? "" : "where " + hql)
				+ (page.getOrderBy() == null ? "" : page.getOrderBy());
//		 System.out.println(queryString);
		Query query = knowledgedao.createQuery(queryString, objects);
		if (page.getFirstindex() != -1 && page.getPagesize() != -1) {

			int index = page.getFirstindex()
					* (Integer.parseInt(page.getPagesize() + ""));
			query.setFirstResult(index);
			query.setMaxResults(Integer.parseInt(page.getPagesize() + ""));
		}
		Query countquery = knowledgedao.createQuery(
				"select count(distinct o.id) from "
						+ entityClass.getName()
						+ " o "
						+ hqlfrom
						+ " "
						+ ((hql == null || hql.equals("")) ? "" : "where "
								+ hql), objects);
		// System.out.println( query.list().size());
		List<Long> knowledgeidlist = query.list();
		page.setTotal(Integer.parseInt(countquery.uniqueResult().toString()));
		List<KnowledgeDTO> dtos = new ArrayList<KnowledgeDTO>();
		// HashSet<Long> idlist=new HashSet<Long>();
		for (Long kid : knowledgeidlist) {
			Knowledge k = knowledgedao.findUniqueBy("id", kid);
			KnowledgeDTO kdto = new KnowledgeDTO(k, searchpropertyValues);
			
			if(k.getKtype().getName().equals("Question"))
			{
			Long questionstatus=(Long)getProperty(kid, "questionstatus");
			if(questionstatus!=2L){
				kdto.setQuestionstatus(0L);
			}else{
				kdto.setQuestionstatus(1L);
			}			
			}
		
			dtos.add(kdto);
		}
				

		page.setData(dtos);

		return page;
	}
	
	public  Page kSearch(String knowledgeclassname,SystemUser uploader,boolean isDtreenodeNull,Page page) {
		String hql;
//		int size=Constants.rawPrepage;
		Page<MetaKnowledge> page1 = new Page<MetaKnowledge>();
//		page.setPageSize(size);
		//page.setAutoCount(true);	
	    
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("uploader", uploader);				
		if(isDtreenodeNull) {
			hql = "from "+knowledgeclassname+" o where o.uploader =:uploader and o.domainnode=null";
		} else {
			hql = "from "+knowledgeclassname+" o where o.uploader =:uploader and o.domainnode!=null";
		}
		page1 = kdao.findPage(page, hql, params);
		
		return page1;	
	}	

	@Transactional
	public Author searchAndSaveAuthor(String authorname) {
		if (isAuthorExist(authorname))
			return getAuthor(authorname);
		else
			return addAuthor(authorname);
	}

	@Transactional
	public Author addAuthor(String authorname) {
		Author author = new Author();
		author.setAuthorName(authorname);
		try {
			authorDao.save(author);
			authorDao.flush();
		} catch (Exception e) {
			logger.warn("知识作者存储错误", "");
			throw new ServiceException("知识作者存储错误");
		}

		return author;
	}

	public Author getAuthor(Long authorid) {
		Author author = authorDao.findUniqueBy("id", authorid);
		return author;
	}

	public Author getAuthor(String authorname) {
		Author author = authorDao.findUniqueBy("authorName", authorname);

		return author;

	}

	public Boolean isAuthorExist(String authorname) {

		Author author = authorDao.findUniqueBy("authorName", authorname);
		if (null == author)
			return false;
		return true;
	}

	public Keyword SearchAndSaveKeyword(String keyword) {
		if (isKeywordExist(keyword))
			return getKeyword(keyword);
		else
			return addKeyword(keyword);
	}

	public Keyword addKeyword(String keywordName) {
		Keyword keyword = new Keyword();
		keyword.setKeywordName(keywordName);
		try {
			keywordDao.save(keyword);
			keywordDao.flush();
		} catch (Exception e) {
			logger.warn("知识关键词存储错误", "");
			throw new ServiceException("知识关键词存储错误");
		}

		return keyword;
	}

	public Keyword getKeyword(Long keywordid) {
		Keyword keyword = keywordDao.findUniqueBy("id", keywordid);
		return keyword;
	}

	// 通过关键词名称返回关键词
	public Keyword getKeyword(String keywordname) {
		Keyword keyword = keywordDao.findUniqueBy("keywordName", keywordname);

		return keyword;

	}

	public Boolean isKeywordExist(String keywordname) {
		// System.out.println("keywordname===="+keywordname);
		Keyword keyword = keywordDao.findUniqueBy("keywordName", keywordname);
		if (null == keyword)
			return false;
		return true;
	}


	//处理知识类别

	public Knowledgetype SearchAndSaveKnowledgetype(String knowledgetypename) {
		if (isKnowledgetypeExist(knowledgetypename))
			return getKnowledgetype(knowledgetypename);
		else
			return addKnowledgetype(knowledgetypename);
	}




	public Knowledgetype addKnowledgetype(String knowledgetypename) {
		Knowledgetype knowledgetype = new Knowledgetype();
		knowledgetype.setKnowledgeTypeName(knowledgetypename);
		try {
			knowledgetypeDao.save(knowledgetype);
			knowledgetypeDao.flush();
		} catch (Exception e) {
			logger.warn("知识关键词存储错误", "");
			throw new ServiceException("知识关键词存储错误");
		}

		return knowledgetype;
	}

	public Knowledgetype getKnowledgetype(Long knowledgetypeid) {
		Knowledgetype knowledgetype = knowledgetypeDao.findUniqueBy("id",
				knowledgetypeid);
		return knowledgetype;
	}
	public Knowledgetype getKnowledgetype(String knowledgetypename) {
		Knowledgetype keyword = knowledgetypeDao.findUniqueBy(
				"knowledgeTypeName", knowledgetypename);

		return keyword;

	}
	
	public Boolean isKnowledgetypeExist(String knowledgetypename) {
		// System.out.println("keywordname===="+keywordname);
		Knowledgetype knowledgetype = knowledgetypeDao.findUniqueBy(
				"knowledgeTypeName", knowledgetypename);
		if (null == knowledgetype)
			return false;
		return true;
	}	

	public Attachment setAttachment(Attachment att) {
		attdao.save(att);
		attdao.flush();
		return att;

	}

	public Version setVersion(Version version) {

		return null;
	}
	

	public void saveVersion(Version version) {
		versiondao.save(version);
		versiondao.flush();
		
	}

	public Long getTotalNum() {
		// TODO Auto-generated method stub
		String hql = "select count(*) from MetaKnowledge m where m.status <> ? and  m.isvisible=true and m.status is not null and m.domainnode is not null  ";
		Long num = (Long) kdao.createQuery(hql, "0").uniqueResult();
		return num;

	}

	public void updateKnowledge(Knowledge k) {
		knowledgedao.save(k);
		knowledgedao.flush();
	}

	public void deleteKnowledge(Knowledge k) {
		k.setIsvisible(false);
		updateKnowledge(k);

	}

	public Long getTotalKnowledgeNum_user(Long userId) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from MetaKnowledge m where m.status <> 0 and m.status is not null and m.domainnode is not null and  m.isvisible=true and m.uploader.id= ?";
		Long num = (Long) kdao.createQuery(hql, userId).uniqueResult();
		return num;
	}

	public Double getUserAverageScore(Long userId) {
		// TODO Auto-generated method stub
		String hql = "select avg(cr.rate) from MetaKnowledge m, CommentRecord cr where m.uploader.id=? and cr.id= m.commentrecord.id and m.status <> 0 and m.status is not null and m.domainnode is not null and m.isvisible=true";
		Double score = (Double) kdao.createQuery(hql, userId).uniqueResult();
		return score;
	}

	public Long getTotalKnowledgeNum_ktype(Long ktypeId) {
		// TODO Auto-generated method stub
		String hql = "select count(m.id) from MetaKnowledge m where m.knowledgetype.id=? and  m.status <> '0' and m.status is not null and m.domainnode is not null  and m.isvisible=true";
		Long num = (Long) kdao.createQuery(hql, ktypeId).uniqueResult();
		return num;
	}

	public Long getTotalKnowledgeNum_ktype_user(Long ktypeId, Long userId) {
		// TODO Auto-generated method stub
		String hql = "select count(m.id) from MetaKnowledge m where m.knowledgetype.id=? and m.status <>? and m.uploader.id=? and m.status is not null and m.domainnode is not null   and m.isvisible=true";
		Long num = (Long) kdao.createQuery(hql, ktypeId, "0", userId)
				.uniqueResult();
		return num;
	}

	public Long getTotalKnowledgeNum_timeslot(Object[] o) {
		// TODO Auto-generated method stub

		Long num = (Long) kdao
				.createQuery(
						"select count(m.id) from MetaKnowledge m where m.status <> '0' and m.status is not null and m.domainnode is not null  and m.uploadtime between ? and  ? and m.isvisible=true",
						o).uniqueResult();
		return num;
	}

	public List<MetaKnowledge> sortKnowledge(String orderby, String pagerecord) {
		// TODO Auto-generated method stub
		int num = 10;
		try {
			num = Integer.parseInt(pagerecord.trim());

		} catch (Exception e) {
			e.printStackTrace();
		}

		String hql = " from MetaKnowledge m  where m.isvisible=true and m.status <> '0' and m.status is not null and m.domainnode is not null  "
				+ orderby;
		Query query = kdao.createQuery(hql);
		query.setMaxResults(num);
		List<MetaKnowledge> list = query.list();
		return list;
	}

	public Attachment getAttachment(Long id) {
		Attachment att = attdao.findUniqueBy("id", id);
		return att;
	}


    public List getkdtail(String docPath){
            //boolean ktypeql=true;
 		    List<String> valueList =new ArrayList<String>();
 		   String exeString;
 		    if(null!=Constants.GETFILEINFORMETHOD&&Constants.GETFILEINFORMETHOD.equals("exe"))
 		    		{
 		    	exeString = getStringFromExe(docPath);
 		    		}
 		    else
 		    exeString = getStringFromJacob(docPath);
 		    //解析得到知识类型
 		    String splite="@#@";
 		    if( exeString.indexOf("@#@")==-1)
 		    	splite= "#@#";
 		    int breakIndex = exeString.indexOf(splite);
 		    //确保有分割符
 		    if(breakIndex!=-1){	
 		    	String ktname = "";
 		   try{//pl 下面拿到传回来的字符串将第一个切割出来，拿到知识模板。因为拿回来的字符串经过处理，知识模板的内容是放在最前面的
 			    String ktypea = exeString.substring(0,breakIndex);
 			    String[] ktypeName = ktypea.split("->");
 			    System.out.println("知识模板："+ktypeName[1]);
 			    ktname = ktypeName[1];
 			   
 		   }
 		   catch(Exception e)
 		   {   System.out.println("返回结果中知识类型没有解析到:--");
 				System.out.println("文件没有得到解析结果");
 				return null;
 		   }
	    
 		    	   String[] varray = exeString.substring(breakIndex+3).split(splite);
 		    	   valueList = Arrays.asList(varray);
 		    	  Property p=new Property();
	    		   p.setName("ktype");
	    		   p.setValue(ktname);
	    		   List<Property> PropertyList=new ArrayList<Property>();
	    		   PropertyList.add(p);
 		    	   for (int i=0;i<valueList.size();i++) {
 		    		   
 		    		 //  System.out.println("第"+(i+1)+"个属性值："+valueList.get(i));
 		    		   String[] property =new String[2];
 		    		   property=valueList.get(i).split("->");
 		    		//  System.out.println("解析到的知识属性："+property[0]);
 		    		  String propertyvalue=valueList.get(i).toString().substring(valueList.get(i).toString().indexOf("->")+2);
 		    		 //  System.out.println("解析到的知识属性值："+property[1]);
 		    		   Property pp=new Property();
 		    		   pp.setName(property[0]);
 		    		   if(property.length==2&&null!=property[1])
 		    		   pp.setValue(property[1]);
 		    		   else
 		    		   pp.setValue("");   
 		    		  PropertyList.add(pp);
 				   }
 		   
 		    	 return PropertyList;
	    
 		    
 		    }
 		
 			System.out.println("文件没有得到解析结果");
 			return null;
 		}
    	
//    	 private synchronized String getStringFromExe(String docPath) {
//    			docPath="\""+Constants.SOURCEFILE_PATH_TEMP+"\\"+docPath+"\"";
//    			
//    			String exePath=Constants.EXE_PATH;    		
//    			Process p = null;
//    			StringBuffer sb = new StringBuffer();
//    			try {
//    				Runtime rn = Runtime.getRuntime();
//    				String str =  exePath + " " + docPath;   			
//    				p = rn.exec(str);    		
//    				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));			
//    				String inline;
//    				while (null != (inline = reader.readLine())) {
//    					sb.append(inline).append("\n");
//    				}
//    			
//>>>>>>> .r1224
//
//			} catch (Exception e) {
//				System.out.println("返回结果中知识类型没有解析到:--");
//				System.out.println("文件没有得到解析结果");
//				return null;
//			}
//
//			String[] varray = exeString.substring(breakIndex + 3).split(splite);
//			valueList = Arrays.asList(varray);
//			Property p = new Property();
//			p.setName("ktype");
//			p.setValue(ktname);
//			List<Property> PropertyList = new ArrayList<Property>();
//			PropertyList.add(p);
//			for (int i = 0; i < valueList.size(); i++) {
//
//				// System.out.println("第"+(i+1)+"个属性值："+valueList.get(i));
//				String[] property = new String[2];
//				property = valueList.get(i).split("->");
//				// System.out.println("解析到的知识属性："+property[0]);
//				String propertyvalue = valueList
//						.get(i)
//						.toString()
//						.substring(
//								valueList.get(i).toString().indexOf("->") + 2);
//				// System.out.println("解析到的知识属性值："+property[1]);
//				Property pp = new Property();
//				pp.setName(property[0]);
//				if (property.length == 2 && null != property[1])
//					pp.setValue(property[1]);
//				else
//					pp.setValue("");
//				PropertyList.add(pp);
//			}
//			// System.out.println("return mpl");
//			// return mpl;
//			return PropertyList;
//
//		}
//
//		System.out.println("文件没有得到解析结果");
//		return null;
//	}

	private synchronized String getStringFromExe(String docPath) {
		docPath = "\"" + Constants.SOURCEFILE_PATH_TEMP + "\\" + docPath + "\"";

		String exePath = Constants.EXE_PATH;
		// System.out.println("docPath="+docPath);
		Process p = null;
		StringBuffer sb = new StringBuffer();
		try {
			Runtime rn = Runtime.getRuntime();
			String str = exePath + " " + docPath;
			// String str = "ping localhost" ;
			p = rn.exec(str);
			// System.out.println("docPath="+docPath);
			// System.out.println("exePath="+exePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String inline;
			while (null != (inline = reader.readLine())) {
				sb.append(inline).append("\n");
			}

		} catch (Exception e) {
			System.out.println("解析程序运行错误！");
		}

		System.out.println(sb.toString());
		return sb.toString();
	}

	private synchronized String getStringFromJacob(String docPath) {
		docPath = "\"" + Constants.SOURCEFILE_PATH_TEMP + "\\" + docPath + "\"";

		List ktypePropertyList = new ArrayList();
		// ktypePropertyList.add("知识类型");
		ktypePropertyList = kdao
				.getSession()
				.createSQLQuery(
						"select distinct b.description from caltks_property b where b.description!='知识类型'")
				.list();
		ktypePropertyList.add(0, "知识类型");
		ParserWordJacob pwj = new ParserWordJacob(docPath, ktypePropertyList);
		String valuelist = pwj.GetBookMarkValue();

		// System.out.println("valuelist="+valuelist);

		return valuelist;
	}

	private synchronized int getdandiankFromPOI(String docPath,List cdNodes)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, InvocationTargetException,
			NoSuchMethodException {
		int count = 0;
		docPath = Constants.SOURCEFILE_PATH_TEMP + "\\" + docPath;
		String dnode=(String)cdNodes.get(0);

		ParserDandianWord pwdpoi = new ParserDandianWord();
		List<List<List<String>>> listFileInfo = pwdpoi.GetFileInfo(docPath);
		// 判断传入的文件类型，以此判断知识类型，获得相应类型ktype和知识实例knowledge k
	
		int type = 0;
		try {
			type = pwdpoi.getTableType(docPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("type==="+type);
		String ktypename = "";
		if (type == 1) {
			ktypename = Constants.HEADLINE1NAME;

		}
		if (type == 2) {
			ktypename = Constants.HEADLINE2NAME;

		}
		if (type == 6) {
			ktypename = Constants.HEADLINE6NAME;
		}
		Ktype ktype = ktypeservice.getKtype(ktypename);
		SystemUser u = userservice.getUser();
		if (null != ktype) {
			List<KtypeProperty> ktypepropertylist = ktypeservice.listKtypeProperties(ktype.getId());
			for (int i=0;i<listFileInfo.size();i++) {
				//区别附件3(1行表头) 5（2行表头） 和附件4（6行表头）的赋值方法			
				if(type==1||type==2){
					List<List<String>> rowlist = listFileInfo.get(i);
				for (List<String> collist : rowlist) {

					int colnum = 0;
					// 如果标题不为空 则保存该单点知识
					if (null != collist.get(0)
							&& !collist.get(0).toString().trim().equals("")) {
                          Knowledge k=SetCommonKnowledge(ktype, u,collist.get(0).toString().trim(), "秘密", "5",Constants.DANDIANNAME,"1.0",dnode);

						for (String propertyvalue : collist) {
							KtypeProperty kp = ktypepropertylist.get(colnum+18);
							k=setKnowledgeproperty(k,kp,propertyvalue);
							colnum++;
						}
						Set<CategoryTreeNode> categories=new HashSet<CategoryTreeNode>();
						for(int j=1;j<cdNodes.size();j++){
							String cnode=(String)cdNodes.get(j);
							CategoryTreeNode catenode=(CategoryTreeNode)this.treeservice.getTreeNode(Long.valueOf(cnode));
							categories.add(catenode);
						}
						k.setCategories(categories);
						save(k, null);
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
						count++;
					}
					
				}
				}
				if(type==6)
				{
					List<List<String>> headrowlist = listFileInfo.get(i);
					List<List<String>> rowlist = listFileInfo.get(i+1);
					for (List<String> collist : rowlist) {

						int colnum = 0;
						// 如果标题不为空 则保存该单点知识
						if (null != collist.get(0)
								&& !collist.get(0).toString().trim().equals("")&&null != headrowlist.get(0).get(0)
								&& !headrowlist.get(0).get(0).toString().trim().equals("")) {
							String versionname="1.0";
							if(null!=headrowlist.get(0).get(3))
								versionname=headrowlist.get(0).get(3).toString().trim();
	                          Knowledge k=SetCommonKnowledge(ktype, u,headrowlist.get(0).get(0).toString().trim()+collist.get(0).toString().trim(), "秘密", "5",Constants.DANDIANNAME,versionname,dnode);
	                          List<String> headcollist=headrowlist.get(0);
	                          //首先保存表头中的属性值
	                          for (String propertyvalue : headcollist) {
								KtypeProperty kp = ktypepropertylist.get(colnum+18);

								k=setKnowledgeproperty(k,kp,propertyvalue);
								colnum++;
							}
	                          //再保存列表中的属性值	                       
							for (String propertyvalue : collist) {
								KtypeProperty kp = ktypepropertylist.get(colnum+18);
								k=setKnowledgeproperty(k,kp,propertyvalue);	
								if(((colnum+18)<(ktypepropertylist.size()-1)))
								colnum++;
							}
							Set<CategoryTreeNode> categories=new HashSet<CategoryTreeNode>();
							for(int j=1;j<cdNodes.size();j++){
								String cnode=(String)cdNodes.get(j);
								CategoryTreeNode catenode=(CategoryTreeNode)this.treeservice.getTreeNode(Long.valueOf(cnode));
								categories.add(catenode);
							}
							k.setCategories(categories);
							save(k, null);
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
							count++;
						}
						
					}
					i++;
				}
			}
		}
		return count;
	}
    private Knowledge SetCommonKnowledge(Ktype ktype,SystemUser u,String titlename,String securitylevel,String status,String knowledgetypename,String versionname,String domainnodeid)throws InstantiationException, IllegalAccessException,
	ClassNotFoundException, InvocationTargetException,
	NoSuchMethodException 
    {
		Knowledge k = (Knowledge) Class.forName(ktype.getClassName()).newInstance();
		Knowledgetype knowledgetype = SearchAndSaveKnowledgetype(knowledgetypename);
		k.setKnowledgetype(knowledgetype);
		//System.out.println("titlename="+collist.get(0).toString().trim());
		k.setTitlename(titlename);
		// 添加知识域属性
		DomainTreeNode domain = (DomainTreeNode) treeservice
				.getTreeNode(new Long(domainnodeid));
		k.setDomainnode(domain);
		
		k.setUploader(u);
		List<Author> authorlist = new ArrayList<Author>();

		Author authorT = searchAndSaveAuthor(u.getName());
		authorlist.add(authorT);
		k.setKauthors(authorlist);
		Date uploadtimeT = new Date();

		k.setUploadtime(uploadtimeT);

		k.setIsvisible(true);

		k.setKtype(ktype);
		k.setStatus(status);
		k.setSecuritylevel(securitylevel);
		Version version = new Version();
		version.setVersionTime(uploadtimeT);
		version.setVersionNumber(versionname);
		k.setVersion(version);
		// 添加知识版本属性

		CommentRecord cr = new CommentRecord();
		cr.setCommentCount(new Long(0));
		cr.setViewCount(new Long(0));
		cr.setRate(new Float(0));
		cr.setDownloadCount(new Long(0));
		k.setCommentrecord(cr);
		return k;
 	
    }
    private Knowledge setKnowledgeproperty(Knowledge k,KtypeProperty kp,String propertyvalue) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
    	if (kp.getProperty().getPropertyType()
				.equals("java.util.Date")) {
			Date datetemp = null;
			try {
				DateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd");
				datetemp = format.parse(propertyvalue);
			} catch (Exception es) {
				datetemp = new Date();

			}
			PropertyUtils.setProperty(k, kp.getProperty()
					.getName(), datetemp);
		} else
			PropertyUtils.setProperty(k, kp.getProperty()
					.getName(), propertyvalue);	
    	
    	return k;
    }
	public String getStringFromPoi(String ktype, String docPath) {
		String valuelist = "";
		ParserWordPoi pwp = new ParserWordPoi(ktype, docPath);
		valuelist = pwp.ParserXML();
		return valuelist;

	}

	public List<MetaKnowledge> getnoStatusKnowledge() {
		String hql = " from MetaKnowledge m  where m.isvisible=true and m.status = '0' ";
		Query query = kdao.createQuery(hql);

		List<MetaKnowledge> knowledgelist = query.list();
		return knowledgelist;
	}

	public int getdandiandtail(String docPath,List cdnodes) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException,
			InvocationTargetException, NoSuchMethodException {

		return getdandiankFromPOI(docPath,cdnodes);

	}



    	 
		public List<String> searchKeywords(String keyword) {
			List<String> keywordlist = keywordDao.getSession().createQuery(
					"Select o.keywordName from Keyword o where o.keywordName like '%"
							+ keyword
							+ "%' group by o.keywordName order by count(o.keywordName) desc")
					.list();
			return keywordlist;

		}
		
		public List<String> searchAuthors(String author) {
			List<String> authorlist = authorDao.getSession().createQuery(
					"Select o.authorName from Author o where o.authorName like '%"
					+ author
					+ "%' group by o.authorName order by count(o.authorName) desc")
					.list();
			return authorlist;
			
		}
		
		public boolean isFirstView(SystemUser user,Long knowledgeid,SysBehaviorList sysBehaviorList,
				String objectType) {
			Map<String,Object> params = new HashMap<String ,Object>();
			params.put("user", user);
			params.put("objectType", "knowledge");
			params.put("sysBehaviorList",sysBehaviorList);
			params.put("objectid",knowledgeid);
			
			String hql = "from SysBehaviorLog o where o.user= :user and o.objectType=:objectType and o.sysBehaviorList=:sysBehaviorList and o.objectid=:objectid";
			List<SysBehaviorLog> loglist=(ArrayList<SysBehaviorLog>)sysBehaviorLogDao.createQuery(hql, params).list();
			if(loglist.size()>0)
				return true;	
			return false;
			}
		
		public List<MetaKnowledge> getReferenceDoc(String sugmessage) {
			String hql="select distinct o from MetaKnowledge o where o.titlename like '%"+sugmessage+"%' and o.status!='0'";
			List<MetaKnowledge> metakglist=kdao.getSession().createQuery(hql).list();			

			return metakglist;
		}
		
		
		public Version getVersion(Long id) {
		
			return 	versiondao.get(id);
		}
		
   	 
    	 //问题相关模块

		public List<MetaKnowledge> sortQuestion(String orderby, String pagerecord) {
    			int num=10;
    			try {
    				num = Integer.parseInt(pagerecord.trim());

    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			String hql;
    			if(orderby.substring(0,38).equals("from Question q where q.questionstatus")){
    				hql=""+orderby;
    			
    			}else if(orderby.substring(0,26).equals("where comment.commenter.id")||orderby.substring(0,22).equals("where comment.isBest=1")){    				
    				hql="select distinct m.id from MetaKnowledge m,Comment comment "+orderby;   			
//    				hql="select distinct m from MetaKnowledge m,Comment comment "+orderby;   			
    			}else {
    				hql=" from MetaKnowledge m "+orderby;
    			}
    			
    			System.out.println("hql== "+hql);
    			Query query;
    			
    			//规定几天之内为最新的问题
    			if(orderby.equals("where m.ktype.name='Question' and m.isvisible=true and m.uploadtime between ? and ? order by m.uploadtime desc")) {
    				Calendar now = Calendar.getInstance();		
        			Date date1=now.getTime();
        			now.add(Calendar.DATE,-119);
        			Date date=now.getTime();
        			Object[] params = new Object[0];
        		  	params = new Object[]{date,date1};
        		  	query=kdao.createQuery(hql, params);
        		  	
    			}else{
    			
    				query=kdao.createQuery(hql);
    				
    			}  
    			
    			//解决distinct的先查到他的id，然后根据id在得到他的对象
    			
    			if(orderby.equals("where m.ktype.name='Question' and m.isvisible=true order by m.commentrecord.commentCount desc"))
    			{query.setMaxResults(num);}
    			List<MetaKnowledge> questionlist = new ArrayList<MetaKnowledge>();
    			if(orderby.substring(0,26).equals("where comment.commenter.id")||orderby.substring(0,22).equals("where comment.isBest=1")){
    				List<Long> hqlids=query.list();
    				for(Long hqlkid:hqlids){
    					MetaKnowledge hqlk = getMetaknowledge(hqlkid);
    					questionlist.add(hqlk);
    				}

    			}else{
    				questionlist=query.list();
    			}			
    			return questionlist;
    		}
    	 
    	public List<MetaKnowledge> listAllQuestion() {
    		return kdao.getAll();
    	}
 
		public List<Keyword> listKeywords() {			
			return keywordDao.getAll();
		}

		public List<Author> listAllAuthors() {			
			return authorDao.getAll();
		}

		public void deleteKnowledgeDeep(Knowledge k) {
			this.knowledgedao.delete(k);
		}

		
		public List<MetaKnowledge> getKnowledgesByType(Ktype kt){
			List<MetaKnowledge> mks = this.kdao.findBy("ktype", kt);
			return mks;
		}

		public List<MetaKnowledge> getKnowledgeByTitle(String titleName) {

			String hql = "from MetaKnowledge o where o.titlename like '%" + titleName + "%' " ;
			Object[] values=null;
			Query query = kdao.createQuery(hql, values);
			List<MetaKnowledge> list = query.list();
			return list;
		}

		public List<MetaKnowledge> getPositionKnowledgeByDomain(Long domainId) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("domainid", domainId);
			params.put("ktypename", "岗位知识体系");
			String queryString = "from MetaKnowledge where domainnode.id =:domainid and ktype.ktypeName =:ktypename";
			Query query = kdao.createQuery(queryString, params);
			List<MetaKnowledge> list = query.list();
			return list;
		} 	

		    	 	




}

