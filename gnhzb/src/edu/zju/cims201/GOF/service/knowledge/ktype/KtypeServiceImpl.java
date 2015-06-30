package edu.zju.cims201.GOF.service.knowledge.ktype;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.WordUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Query;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter.MatchType;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import org.w3c.dom.NodeList;

import edu.zju.cims201.GOF.dao.knowledge.KnowledgetypeDao;
import edu.zju.cims201.GOF.dao.knowledge.KtypeDao;
import edu.zju.cims201.GOF.dao.knowledge.KtypePropertyDao;
import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.dao.knowledge.PropertyDao;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.Vote;
import edu.zju.cims201.GOF.service.ServiceException;

import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.asm.BuildUtil;
import edu.zju.cims201.GOF.util.asm.POBuildUtil;

/**
 * 知识类型扩展的服务
 * 
 * @author cwd
 */

@Transactional(readOnly = true)
@Service
public class KtypeServiceImpl implements KtypeService {
	private static Logger logger = LoggerFactory
			.getLogger(KtypeServiceImpl.class);
	@Resource(name = "knowledgetypeDao")
	private KnowledgetypeDao knowledgetypeDao; 
	@Resource(name = "ktypeDao")
	private KtypeDao ktDao;
	@Resource(name = "ktypePropertyDao")
	private KtypePropertyDao ktpDao;
	@Resource(name = "propertyDao")
	private PropertyDao pDao;
	@Resource(name = "metaKnowledgeDao")
	private MetaKnowledgeDao knowledgeDao;
	
	@Resource(name = "dataSource")
	private BasicDataSource bds;

	@Transactional(readOnly = true)
	public String createKtype(Ktype ktype, List<KtypeProperty> KtypePropertites) {
		// TODO 自动生成方法存根
		String operateresult = "1";
		String name = WordUtils.capitalize(ktype.getName());
		List kl = ktDao.findBy("name", name);

		if (kl.size() > 0) {
			operateresult = "已经存在该知识类型";
			return operateresult;
		}
		ktype.setClassName("edu.zju.cims201.GOF.hibernate.pojo." + name);
		ktype.setName(name);
		ktype.setTableName("CALTKS_DK_" + name);
		ktDao.save(ktype);
		List<Property> extendProperties = new ArrayList<Property>();
		List<KtypeProperty> kps = new ArrayList<KtypeProperty>();
		for (KtypeProperty kp : KtypePropertites) {

			if (!kp.getProperty().getIsCommon())
				extendProperties.add(kp.getProperty());
			kp.setKtype(ktype);

			kps.add(kp);

		}

		ktype.setKtypeproperties(kps);
		ktDao.flush();
		// ktDao.save(ktype);
		try {
			operateresult = CreatClass(ktype, extendProperties);

		} catch (Exception e) {
			operateresult = "知识类型动态生成出错";
			logger.warn("知识类型动态生成出错", "");
			throw new ServiceException("知识类型动态生成出错");
		}
		return operateresult;
	}

	public String updateKtype(Ktype ktype, List<KtypeProperty> KtypePropertites) {
		
		String operateresult = "1";
		List<Property> extendProperties = new ArrayList<Property>();
		List<KtypeProperty> kps = new ArrayList<KtypeProperty>();
		for (KtypeProperty kp : KtypePropertites) {

			if (!kp.getProperty().getIsCommon())
				extendProperties.add(kp.getProperty());
			kp.setKtype(ktype);

			kps.add(kp);

		}
		ktype.setKtypeproperties(kps);
		ktDao.save(ktype);
		ktDao.flush();
		try {
			operateresult = UpdateClass(ktype, extendProperties);

		} catch (Exception e) {
			operateresult = "知识类型动态生成出错";
			logger.warn("知识类型动态生成出错", "");
			throw new ServiceException("知识类型动态生成出错");
		}
		return operateresult;
	}
	@Transactional
	private String UpdateClass(Ktype ktype, List<Property> extendProperties)
			throws UnsupportedEncodingException {
		String operateresult = "知识类型扩展操作成功！请重启系统服务，以对应载入配置类型";

		BuildPojo(ktype, extendProperties);
		dropKtypeHBM(ktype);
		BuildHbm(ktype, extendProperties);
		
		//无需重启即可使用了。重新加载class。用hibernate立即映射生成表中的列
		//1.hibernate动态映射入数据库
//		String path;
//		try {
//			path = URLDecoder.decode(this.getClass().getResource("/")
//					.getPath(), "UTF-8")
//					+ BuildUtil.transferClassName(ktype.getClassName());
//		} catch (UnsupportedEncodingException e1) {
//			logger.error("获得hbm.xml路径就出错");
//			throw new ServiceException("获得hbm.xml路径就出错");
//		}
//		path = path.substring(0, path.lastIndexOf("/"));
//		
//			try {
//				Configuration config = new AnnotationConfiguration() 
//					.configure(path + "/MetaKnowledge.hbm.xml"); 
//				File file = new File(path + "/MetaKnowledge.hbm.xml");
//				config.addFile(file);
//			} catch (MappingException e) {
//				e.printStackTrace();
//			} catch (HibernateException e) {
//				e.printStackTrace();
//			} 
		

		//2.加载修改的class
//		ApplicationContext cxt=new ClassPathXmlApplicationContext("applicationContext.xml");
//		ClassLoader cl = cxt.getClassLoader();
//		try {
//			cl.loadClass(ktype.getClassName());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
		return operateresult;
	}
	@Transactional
	public String createProperty(Property property) {
		try {
			pDao.save(property);
		} catch (RuntimeException e) {

			e.printStackTrace();
			logger.warn("创建新的知识类型属性出错！", "");
			throw new ServiceException("创建新的知识类型属性出错！");

		}
		return "1";
	}

	public Ktype getKtype(Long ktypeId) {

		Ktype ktype = ktDao.findUniqueBy("id", ktypeId);
		return ktype;
	}

	public Property getProperty(Long propertyId) {
		Property property = pDao.findUniqueBy("id", propertyId);

		return property;
	}
	public Property getProperty(String description) {
		Property property = pDao.findUniqueBy("description", description);

		return property;
	}

	public List<KtypeProperty> listKtypeProperties(Long ktypeID) {
		System.out.println("*****************"+ktDao.findUniqueBy("id", ktypeID).getName()); 
		List<KtypeProperty> kps = ktpDao.findBy("ktype", ktDao.findUniqueBy(
				"id", ktypeID));

		return kps;
	}
//	public List<KtypeProperty> listKtypeExpandedProperties() {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("isCommon", false);
//		String hql = "from Property o where o.isCommon = :isCommon order by id";
//	
//		List<KtypeProperty> Expandpropertites = ktpDao.createQuery(hql, params).list();
//		return propertites;
//	}
//	

	public List<Ktype> listKtypes(boolean withoutcommon) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (withoutcommon)
			{params.put("commonKTypeName1", "Metaknowledge");
			params.put("commonKTypeName2", "Avidmknowledge");
			params.put("commonKTypeName3", "Qualityknowledge");
			params.put("commonKTypeName4", Constants.HEADLINE1NAME);
			params.put("commonKTypeName5", Constants.HEADLINE2NAME);
			params.put("commonKTypeName6", Constants.HEADLINE6NAME);
			//params.put("commonKTypeName7", "Question");
			}
		else{
			params.put("commonKTypeName1", "null");
			params.put("commonKTypeName2", "null");
			params.put("commonKTypeName3", "null");
			params.put("commonKTypeName4", "null");
			params.put("commonKTypeName5", "null");
			params.put("commonKTypeName6", "null");
			//params.put("commonKTypeName7", "null");
		}
		List<Ktype> ktypes = ktDao.find(
				"from Ktype o where o.name != :commonKTypeName1 and o.name != :commonKTypeName2 and o.name!= :commonKTypeName3 and o.name!= :commonKTypeName4 and o.name!= :commonKTypeName5 and o.name!= :commonKTypeName6 order by o.id",
				params);
		//System.out.println("withoutcommon="+withoutcommon);
		return ktypes;
	}

	public boolean isKtypeExist(String propertyname, String propertyvalue) {
		if (null == propertyname || !propertyname.equals("ktypeName"))
			propertyname = "name";

		List<Ktype> ktypes = ktDao.findBy(propertyname, propertyvalue);
		if (ktypes.size() > 0)
			return true;
		return false;
	}

	public boolean isPropertyExist(String propertyname, String propertyvalue) {

		if (null == propertyname || !propertyname.equals("description"))
			propertyname = "name";

		List<Property> prs = pDao.findBy(propertyname, propertyvalue);
		if (prs.size() > 0)
			return true;
		return false;

	}

	public Page<Property> listCommonProperties(Page<Property> page) {
		// TODO 自动生成方法存根
		return null;
	}

	public List<Property> listCommonProperties() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCommon", true);
		String hql = "from Property o where o.isCommon = :isCommon order by id";

		List<Property> propertites = pDao.createQuery(hql, params).list();
		return propertites;
	}

	public Page<Property> listExpandedProperties(Page<Property> page) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCommon", false);
		String hql = "from Property o where o.isCommon = :isCommon ";

	//	System.out.println("list expand");
		page = pDao.findPage(page, hql, params);
		return page;
	}

	public List<Property> listExpandedProperties() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCommon", false);
		String hql = "from Property o where o.isCommon = :isCommon order by id";
	
		List<Property> propertites = pDao.createQuery(hql, params).list();
		return propertites;
	}

	public Page<Property> listKtypeProperties(Long ktypeID, Page<Property> page) {
		// TODO 自动生成方法存根
		return null;
	}

	public Page<Ktype> listKtypes(Page<Ktype> page) {
		page = ktDao.getAll(page);
		return page;
	}

	/**
	 * 创建一个新的知识类型类
	 * 
	 * @param ktpye
	 *            创建的知识类型
	 * @return operateresult -1为操作失败，1为操作成功
	 * @author hebi
	 * @throws UnsupportedEncodingException
	 */
	@Transactional
	private String CreatClass(Ktype ktype, List<Property> extendProperties)
			throws UnsupportedEncodingException {
		String operateresult = "知识类型扩展操作成功！请重启系统服务，以对应载入配置类型";

		BuildPojo(ktype, extendProperties);

		BuildHbm(ktype, extendProperties);

		return operateresult;
	}

	@Transactional
	private void BuildHbm(Ktype ktype, List<Property> extendProperties)
		 {
		File file;
		String path;
		try {
			path = URLDecoder.decode(this.getClass().getResource("/")
					.getPath(), "UTF-8")
					+ BuildUtil.transferClassName(ktype.getClassName());
		} catch (UnsupportedEncodingException e1) {
			logger.error("获得hbm.xml路径就出错");
			throw new ServiceException("获得hbm.xml路径就出错");
		}
		path = path.substring(0, path.lastIndexOf("/"));
		
		String backupfilename= backupHBM(path,"addktype_" );
	
		// 利用dom4j添加子节点
		try {
			SAXReader reader = new SAXReader();
			file = new File(path + "/MetaKnowledge.hbm.xml");
			if (null == file) {
				throw new ServiceException("没有得到hbm.xml");

			}
			
			
			Document document = reader.read(file);
			Element rootElm = document.getRootElement();
			Element memberElm = rootElm.element("class");
			//System.out.println("添加子类元素joined-subclass");
			Element subclassElm = memberElm.addElement("joined-subclass");
			subclassElm.addAttribute("name", ktype.getClassName());
			subclassElm.addAttribute("table", ktype.getTableName());
			Element subclasskeyElm = subclassElm.addElement("key");
			subclasskeyElm.addAttribute("column", "id");
			// List<KtypeProperty>
			// ktypeproperties=listKtypeProperties(ktype.getId());
			for (Property pr : extendProperties) {
               if(!pr.getName().equals("knowledgetype")){
			//	System.out.println("添加子类属性元素" + pr.getDescription());
				Element subclasspropertyElm = subclassElm
						.addElement("property");
				subclasspropertyElm.addAttribute("name", pr.getName());
				if (pr.getIsNotNull()) {
					subclasspropertyElm.addAttribute("not-null", "true");
				} else {
					subclasspropertyElm.addAttribute("not-null", "false");

				}
				if (pr.getPropertyType().equalsIgnoreCase("java.util.Date")) {
					subclasspropertyElm.addAttribute("type", "timestamp");
				} else {
					
					if(pr.getLength()>4000){
						subclasspropertyElm.addAttribute("length", pr.getLength()
								+ "");
						subclasspropertyElm.addAttribute("type", "clob");
						
					}else{
					subclasspropertyElm.addAttribute("length", pr.getLength()
							+ "");
					subclasspropertyElm.addAttribute("type", pr
							.getPropertyType());
				}
					}
               }
			}
			XMLWriter writer = new XMLWriter(new FileWriter(path
					+ "/MetaKnowledge.hbm.xml"));
			writer.write(document);
			writer.close();
			logger.info("修改添加知识类型修改知识配置文件成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改添加知识类型动态生成映射文件hbm.xml出错");
			// 还原原来的MetaKnowledge.hbm.xml文件
		    restoreHBM(backupfilename);
			throw new ServiceException("添加知识类型动态生成映射文件hbm.xml出错");
		}
		
	}
		

//	private String backupKtypHBM(String backuptype, String path) {
//
//		Date date = new Date();
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
//		String timestamp = format.format(date);
//		String backupname = "";
//		backupname = path + "/backup";
//		File backupdir = new File(backupname);
//		if (!backupdir.exists())
//			backupdir.mkdirs();// 如果目录不存在就创建
//
//		backupname = backupname + "/" + backuptype + "MetaKnowledge"
//				+ timestamp + ".hbm.xml.bak";
//		XMLWriter writer1 = new XMLWriter(new FileWriter(path
//				+ "/MetaKnowledge" + timestamp + ".hbm.xml.bak"));
//		writer1.write(document);
//		writer1.close();
//		return backupname;
//
//	}

	private static String backupHBM(String oldPath,String backuptype) {
		String newPath = "";
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String timestamp = format.format(date);
		try {
			 newPath =oldPath+"/backup";
			File backupdir = new File(newPath);
			// 如果目录不存在就创建
			if (!backupdir.exists())
				backupdir.mkdirs();
			
			    newPath+="/"+ backuptype + "MetaKnowledge"+ timestamp + ".hbm.xml.bak";
			    oldPath+="/MetaKnowledge.hbm.xml";

			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				
			}
			logger.info("备份知识配置文件到"+newPath+"成功");
			return newPath;
		} catch (Exception e) {
			logger.error("备份原有的知识配置文件到"+newPath+"出错");
			e.printStackTrace();
			throw new ServiceException("备份hbm.xml出错");
		}
	}
	
	private static void restoreHBM(String oldPath) {
		String newPath =oldPath.substring(0,oldPath.lastIndexOf("/backup/")+1)+"MetaKnowledge.hbm.xml";
		
		try {
			
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				
			}
			logger.info("还原原有的知识配置文件到"+newPath+"成功");
		} catch (Exception e) {
			logger.error("还原原有的知识配置文件"+oldPath+"出错");
			e.printStackTrace();
			throw new ServiceException("还原hbm.xml出错");
		}
	}

	private void dropKtypeHBM(Ktype ktype) {

		SAXReader reader = new SAXReader();
		String backupfilename = "";
	

			String path;
			try {
				path = URLDecoder.decode(this.getClass().getResource("/").getPath(), "UTF-8")+ BuildUtil.transferClassName(ktype.getClassName());
			} catch (UnsupportedEncodingException e1) {
				logger.error("获得hbm.xml路径就出错");
				throw new ServiceException("获得hbm.xml路径就出错");
			}
			path = path.substring(0, path.lastIndexOf("/"));
		
			backupfilename= backupHBM(path,"deletektype_" );
			restoreHBM(backupfilename);
		
			try {
			File file = new File(path + "/MetaKnowledge.hbm.xml");

			if (null == file) {
				throw new ServiceException("没有得到hbm.xml");
				
			}
			Document document = reader.read(file);
			Element rootElm = document.getRootElement();
			Element memberElm = rootElm.element("class");	
			List<Element> subclassElms = memberElm.elements("joined-subclass");
			for (Element subclassElm : subclassElms) {
				String ktypename = subclassElm.attribute("name").getValue();

				if (ktype.getClassName().trim().equals(ktypename.trim())) {
					
					memberElm.remove(subclassElm);

					break;
				}
			}
	
			XMLWriter writer = new XMLWriter(new FileWriter(path
					+ "/MetaKnowledge.hbm.xml"));
			writer.write(document);
			writer.close();
			logger.info("修改删除知识类型修改知识配置文件成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改删除知识类型动态生成映射文件hbm.xml出错");
			// 还原原来的MetaKnowledge.hbm.xml文件
		    restoreHBM(backupfilename);
			throw new ServiceException("知识类型动态生成映射文件hbm.xml出错");
		}
	}

	@Transactional
	private void BuildPojo(Ktype ktype, List<Property> extendProperties)
			throws UnsupportedEncodingException {
		String path = URLDecoder.decode(this.getClass().getResource("/")
				.getPath(), "UTF-8")
				+ BuildUtil.transferClassName(ktype.getClassName());
		System.out.println(path);

		POBuildUtil util = new POBuildUtil();
		util.build(ktype.getClassName(), URLDecoder.decode(this.getClass()
				.getResource("/").getPath(), "UTF-8")
				+ BuildUtil.transferClassName(ktype.getClassName()) + ".class",
				extendProperties);

	}

	public Property getExpandedProperties(Long id) {

		return pDao.findUniqueBy("id", id);
	}

	@Transactional
	public String deleteKtype(Long ktypeId) {
		// 首先判断是否可以删除，如果该知识类型中已经存在了知识，则不允许删除！
		
		Ktype kt = ktDao.findUniqueBy("id", ktypeId);
		
		List<MetaKnowledge> mks = knowledgeDao.findBy("ktype", kt);
		
		if (mks.size() == 0) {
			dropKtypeHBM(kt);
			List<KtypeProperty> ktps = (List<KtypeProperty>) ktpDao.findBy(
					"ktype.id", ktypeId);
			for (KtypeProperty ktp : ktps) {
				ktpDao.delete(ktp);
			}
			ktpDao.flush();
			ktDao.delete(kt);

			Connection con = null;
			try {
				
				con = bds.getConnection();
				con.setAutoCommit(false);
				con.createStatement().executeUpdate(
						"drop table "+kt.getTableName());
				//"drop table CALTKS_DK_PAPER"
			} catch (SQLException e) {

				e.printStackTrace();
			}
			
			
			ktDao.flush();
		
			return "1";
		}
		else
		{
			logger.error("本知识类型中还存在知识，不能直接删除本知识类型");
		
			throw new ServiceException("本知识类型中还存在知识，不能直接删除本知识类型");
			
		}
	
	}

	public String deleteProperty(Long propertyId) {
		// 首先判断是否可以删除，如果相关知识类型还存在该属性，则不允许删除！
		Property pr = pDao.get(propertyId);
		List<Ktype> kts = ktDao.getAll();
		for (Ktype kt : kts) {
			for (KtypeProperty kp : kt.getKtypeproperties()) {
				if (kp.getProperty().equals(pr)) {
					logger.error("由于存在相关知识类型还存在该属性，因此无法删除该属性");					
					throw new ServiceException("由于存在相关知识类型还存在该属性，因此无法删除该属性");
					//return "由于存在相关知识类型还存在该属性，因此无法删除该属性";
				}
			}
		}

		pDao.delete(propertyId);
		pDao.flush();
		return "1";
	}

	
//	public List<Ktype> getAllKtype() {
//		// TODO Auto-generated method stub
//		List<Ktype> list=ktDao.getAll();
//		return list;
//	}
	public List<Knowledgetype> listKnowledgetype(){
		// TODO Auto-generated method stub
		List<Knowledgetype> list=knowledgetypeDao.getAll();
		return list;
	}
	
	public Ktype getKtype(String ktypename) {
		Ktype ktype = ktDao.findUniqueBy("name", ktypename);
		return ktype;
	}
	
	public Ktype getKtypeByKtypeName(String ktypename) {
		Ktype ktype = ktDao.findUniqueBy("ktypeName", ktypename);
		return ktype;
	}

}
