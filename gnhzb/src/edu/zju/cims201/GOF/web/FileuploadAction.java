package edu.zju.cims201.GOF.web;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.JSONUtils;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.dao.HibernateUtils;

import edu.zju.cims201.GOF.hibernate.pojo.Attachment;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Version;
import edu.zju.cims201.GOF.service.ServiceException;

import edu.zju.cims201.GOF.service.interestmodel.InterestModelService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeServiceImpl;
import edu.zju.cims201.GOF.service.systemFile.FileService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.service.webservice.ConvertFlashWebService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数. 演示带分页的管理界面.
 * 
 * @author calvin
 */
// 定义URL映射对应/account/user.action
@Namespace("/knowledge")
// 定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "fileupload.action", type = "redirect") })
public class FileuploadAction extends ActionSupport
		implements ServletResponseAware {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "userServiceImpl")
	private UserService userservice;
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice;
	@Resource(name = "ktypeServiceImpl")
	private KtypeService ktypeservice;
	@Resource(name = "fileServiceImpl")
	private FileService fileservcie;
	@Resource(name = "convertFlashWebServiceImpl")
	private ConvertFlashWebService convertFlashWebService;
	private static final long serialVersionUID = 1L;
	@Resource(name = "treeServiceImpl")
	private TreeService treeservice;
	@Resource(name = "interestModelServiceImpl")
	private InterestModelService imservice;	
	private String isNewVersion;
	// -- 页面属性 --//
	private HttpServletResponse response;
	private List<File> file;// 对应前面的name
	// 使用列表保存多个上传文件的文件名
	private List<String> fileFileName;
	// 使用列表保存多个上传文件的MIME类型
	private List<String> fileContentType;
	// 保存上传文件的目录，相对于Web应用程序的根路径，在struts.xml文件中配置
	private String uploadDir;
	private String x, y, pwidth, pheight;
	
	private String filepathTemp;
	
	private String filename;
    //用于客户端上传	
	private String username;
	private String password;
	
	private String fileTitle; 
	private String fileKeyword;
	private String fileAbstract;
	private String securityLevel;
    private String knowledgeType;
    
    private String domainnode;
    private String categorynode;
    
    private Long versionid;

	public Long getVersionid() {
		return versionid;
	}

	public void setVersionid(Long versionid) {
		this.versionid = versionid;
	}

	private String versionNumber;

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public void uploadForModify(){
    	File file=null;
    	String targetFileName=null;
    	if(this.isNewVersion!=null&&this.isNewVersion.equals("false")){
    		if(this.filename==null)
    			return;
    		targetFileName=filename;
    	}else{
    		targetFileName = UUID.randomUUID().toString();
    	}
    	targetFileName+=".doc";
    	
    	if(filepathTemp!=null){
    		file=new File(filepathTemp);
    	}
    	Long fileid=fileservcie.save(file, targetFileName);	
    	
    }
    
    
    


	public void fileupload() {
		String fileoralname = "";
		String newFileName = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		String reInfo = "";// 上传成功返回的东西
		// String path = "c:/TEMP";
		// 附件存储路径
		String path = Constants.ATTACHMENT_PATH_TEMP;
		System.out.println(uploadDir);
		if (null != uploadDir && uploadDir.equals("source"))
			path = Constants.SOURCEFILE_PATH_TEMP;
		// String targetDirectory =path;
		// 附件系统保存名称 用uuid唯一标识
		String targetFileName = UUID.randomUUID().toString();

		for (int i = 0; i < file.size(); i++) {
			System.out.println("获得上传文件");
			long now = new Date().getTime();
			int index = fileFileName.get(i).lastIndexOf('.');
			// String path =
			// ServletActionContext.getServletContext().getRealPath(
			// uploadDir);

			File files = new File(path);
			if (!files.exists()) {
				files.mkdir();
			}
			if (index != -1) {
				newFileName = targetFileName
						+ fileFileName.get(i).substring(index);// 生成新文件名
				fileoralname = fileFileName.get(i);
			} else
				newFileName = targetFileName;
			fileservcie.save(file.get(i), newFileName);
			reInfo += newFileName + "上传成功！";
//××××××××××××××××××××××××××××采用数据库文件系统，替换原有系统文件，×××××××××××××××××××××××			
			bos = null;
			bis = null;
			try {
				if (file.get(i) != null) {
					FileInputStream fis = new FileInputStream(file.get(i)); // /////////
					bis = new BufferedInputStream(fis);
					FileOutputStream fos = new FileOutputStream(new File(files,
							newFileName));
					bos = new BufferedOutputStream(fos);
					byte[] buf = new byte[4096];
					int len = -1;
					while ((len = bis.read(buf)) != -1) {
						bos.write(buf, 0, len);
					}
				} else {
					System.out.println("没有获得上传文件！");

				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("传送失败！！");
			} finally {

				try {
					if (null != bis)
						bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					if (null != bos)
						bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//××××××××××××××××××××××××××××××××××××××		
			// //////////////////////////////
		}

		// 最后若没错误返回就这里返回了
		try {
			String msg = "{success:true,msg:'" + reInfo + "',filename:'"
					+ fileoralname + "',filepath:'" + newFileName + "'}";
			// System.out.println("msg="+msg);
			response.getWriter().write(msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sourcefileupload() {

		String fileoralname = "";
		String newFileName = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		String reInfo = "";// 上传成功返回的东西
		// String path = "c:/TEMP";
		// 附件存储路径
		String path = Constants.SOURCEFILE_PATH_TEMP;
		// String targetDirectory =path;
		// 附件系统保存名称 用uuid唯一标识
		String targetFileName = UUID.randomUUID().toString();
//		System.out.println("进来了？");
		for(int i = 0;i<file.size();i++){
			System.out.println(file.get(i));
			
		}
		for (int i = 0; i < file.size(); i++) {
			
			long now = new Date().getTime();
			int index = fileFileName.get(i).lastIndexOf('.');
			// String path =
			// ServletActionContext.getServletContext().getRealPath(
			// uploadDir);

			File files = new File(path);
			if (!files.exists()) {
				files.mkdir();// 创建个文件夹
				System.out.println("make a  dir");
			}
			if (index != -1) {
				newFileName = targetFileName
						+ fileFileName.get(i).substring(index);// 生成新文件名
				fileoralname = fileFileName.get(i);
			} else
				newFileName = targetFileName;
			reInfo += newFileName + "上传成功！";
			fileservcie.save(file.get(i), newFileName);
	
			//××××××××××××××××××××××××××××采用数据库文件系统，替换原有系统文件，××××××××××××××××××××××××	
			bos = null;
			bis = null;
			
			try {
				if (file.get(i) != null) {
					FileInputStream fis = new FileInputStream(file.get(i)); // /////////
					bis = new BufferedInputStream(fis);
					FileOutputStream fos = new FileOutputStream(new File(files,
							newFileName));
					bos = new BufferedOutputStream(fos);
					byte[] buf = new byte[4096];
					int len = -1;
					while ((len = bis.read(buf)) != -1) {
						bos.write(buf, 0, len);
					}
				} else {
					System.out.println("没有获得上传文件！");

				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("传送失败！！");
			} finally {

				try {
					if (null != bis)
						bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					if (null != bos)
						bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// //////////////////////////////
//××××××××××××××××××××××××××××××××××××××注释结束		
		}

		// 最后若没错误返回就这里返回了
		try {

			String msg = "{success:true,msg:'" + reInfo + "',filename:'"
					+ fileoralname + "',filepath:'" + newFileName + "'}";

			response.getWriter().write(msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	public void thumbnailfileupload() {
		System.out.println("thumbnailfileupload");
		String fileoralname = "";
		String newFileName = null;
		BufferedOutputStream bos = null;
		BufferedImage sourceImg = null;
		BufferedInputStream bis = null;
		String reInfo = "";// 上传成功返回的东西
		// String path = "c:/TEMP";
		// 附件存储路径
		String path = Constants.THUMBNAIL_PATH_TEMP;
		// String targetDirectory =path;
		// 附件系统保存名称 用uuid唯一标识
		String targetFileName = UUID.randomUUID().toString();
		int width = 0;
		int height = 0;
		for (int i = 0; i < file.size(); i++) {
			long now = new Date().getTime();
			int index = fileFileName.get(i).lastIndexOf('.');
			// String path =
			// ServletActionContext.getServletContext().getRealPath(
			// uploadDir);
			File dir = new File(path);
			System.out.println(path);
			if (!dir.exists()) {
				System.out.println("创建dir");
				dir.mkdir();// 创建个文件夹

			}
			if (index != -1) {
				newFileName = targetFileName
						+ fileFileName.get(i).substring(index);// 生成新文件名
				fileoralname = fileFileName.get(i);
			} else
				newFileName = targetFileName;
			reInfo += newFileName + "上传成功！";

			bos = null;
			bis = null;
			sourceImg = null;
			try {

				FileInputStream fis = new FileInputStream(file.get(i)); // /////////
				bis = new BufferedInputStream(fis);
				FileOutputStream fos = new FileOutputStream(new File(dir,
						newFileName));
				bos = new BufferedOutputStream(fos);
				byte[] buf = new byte[4096];
				int len = -1;
				while ((len = bis.read(buf)) != -1) {
					bos.write(buf, 0, len);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("传送失败！！");
			} finally {

				try {
					if (null != bis)
						bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					if (null != bos)
						bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 最后若没错误返回就这里返回了
		try {

			File imgfile = new File(Constants.THUMBNAIL_PATH_TEMP + "/"
					+ newFileName);
			sourceImg = javax.imageio.ImageIO.read(imgfile);
			width = sourceImg.getWidth();
			height = sourceImg.getHeight();
			String msg = "{success:true,width:" + width + ",height:" + height
					+ ",filepath:'" + newFileName + "'}";
			response.getWriter().write(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cutthumbnail()

	{
		InputStream source = null;
		ImageInputStream iis = null;
		ImageReader reader = null;
		FileOutputStream fos = null;
		try {

			// File file1=new File(Constants.THUMBNAIL_PATH_TEMP+"/"+filename);
			// //用file1取得图片名字
			String ext = filename.substring(filename.lastIndexOf(".") + 1);
			//System.out.println("啥"+ext);
			Iterator readers = ImageIO.getImageReadersByFormatName(ext);

			reader = (ImageReader) readers.next();

			source = new FileInputStream(Constants.THUMBNAIL_PATH_TEMP + "/"
					+ filename);

			iis = ImageIO.createImageInputStream(source);

			reader.setInput(iis, true);
			//System.out.println("已经到2");

			ImageReadParam param = reader.getDefaultReadParam();
//              System.out.println("??????"+x+"  "+y+" "+pwidth+" "+pheight);
//			Rectangle rect = new Rectangle(Integer.parseInt(x), Integer
//					.parseInt(y), Integer.parseInt(pwidth), Integer
//					.parseInt(pheight));
//
//			param.setSourceRegion(rect);

			BufferedImage bi = reader.read(0, param);
			fos = new FileOutputStream(Constants.THUMBNAIL_PATH + "/"
					+ filename);
			//System.out.println("已经到1");
			ImageIO.write(bi, ext, fos);

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {

			try {
				if (null != source)
					source.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (null != iis)
					iis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (null != fos)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (null != reader)
				reader.dispose();

		}
//		System.out.println("已经到3"+filename);
		SystemUser user = userservice.getUser();
	
		user.setPicturePath(filename);
		System.out.println("filename="+filename);
		userservice.createUser(user);
	
		JSONUtil.write(response, "操作成功！");
		
	
		
	}
	// 用于单点故障知识属性内容文件
	public void getdandianfileinfor() {
		
		List cdTreeNode=new ArrayList<String>();
		if(this.domainnode==null)
			return;
		cdTreeNode.add(this.domainnode);
		if(this.categorynode!=null){
			String[] cNodes=this.categorynode.split(",");
			for(String cnode:cNodes){
				cdTreeNode.add(cnode);
			}
		}

		if (null != filename) {

			// 首先判断是否符合抽取的文档 doc
			if (filename.indexOf(".") != -1) {
				String ext = filename.substring(filename.lastIndexOf(".") + 1);
				if (ext.equals("doc")||ext.equals("docx")) {
					int count=0;
					try {
				
							count = kservice.getdandiandtail(filename,cdTreeNode);
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					if ( 0!=count){
						JSONUtil.write(response, count);
					}
					else
						JSONUtil.write(response, "没有解析出结果");
				} else {
					JSONUtil.write(response, "文件格式不支持解析");
				}
			} else {
				JSONUtil.write(response, "文件格式不支持解析");
			}
		} else {
			throw new ServiceException("系统错误，没有得到文件名");

		}
	}
	// 用于抽取知识属性内容调用exe文件
	public void getfileinfor() {
		if (null != filename) {

			// 首先判断是否符合抽取的文档 doc
			if (filename.indexOf(".") != -1) {
				String ext = filename.substring(filename.lastIndexOf(".") + 1);
				if (ext.equals("doc")||ext.equals("docx")) {
					List<Property> propertyList = kservice.getkdtail(filename);
//					System.out.println("以下是单个上传的属性列表：");
//					for(int j=0;j<propertyList.size();j++) {
//						System.out.println("propertyList.get("+j+")----"+propertyList.get(j).getName()+":"+propertyList.get(j).getValue());
//					}
					if (null != propertyList){
						JSONUtil.write(response, propertyList);
					}
					else
						JSONUtil.write(response, "没有解析出结果");
				} else {
					JSONUtil.write(response, "文件格式不支持解析");
				}
			} else {
				JSONUtil.write(response, "文件格式不支持解析");
			}
		} else {
			throw new ServiceException("系统错误，没有得到文件名");

		}
//		ArrayList inforesults = new ArrayList();
//		String[] filenamearray  = filename.split(" ");
//		for(int i = 0;i<filenamearray.length;i++){
//			filename = filenamearray[i];
//			if (null != filename) {
//						List<Property> properytList = kservice.getkdtail(filename);
////						System.out.println("什么：-----"+properytList.get(3).getValue());
//						if (null != properytList){
//							inforesults.add(properytList);
////							JSONUtil.write(response, properytList);
//						}
//						else
//							inforesults.add("没有解析出结果");
////							JSONUtil.write(response, "没有解析出结果");
//					
//				
//			} else {
//				throw new ServiceException("系统错误，没有得到文件名");
//				
//			}
//		}
//		JSONUtil.write(response, inforesults);
	}
    public void flashconvert()
    {
    	if (null != filename) {
			if(null!=Constants.FLASHCONVERTMETHOD&&Constants.FLASHCONVERTMETHOD.equals("local")){
//*********************************通过本地转换flash****************************************8	
//			 首先判断是否符合抽取的文档 doc
			if (filename.indexOf(".") != -1) {
			
				
					String flashname=filename.substring(0,filename.lastIndexOf("."))+".swf";
					String sourceext=filename.substring(filename.lastIndexOf("."));
					System.out.println("flashname是："+flashname);
					int result = kservice.convertDOC2SWF(filename,flashname);
					if (result==3){
					//将转换好的flash存入到数据库
						
						fileservcie.convertFile( filename, flashname,sourceext);
						
						JSONUtil.write(response, "转换成功");
				
						
						
					}
					else
						JSONUtil.write(response, "可能文件格式不支持");
				
			} else {
				JSONUtil.write(response, "可能文件格式不支持");
			}
			}
//*******************************t通过webservice转换flash*****************************************************8		
			else{
			if (filename.indexOf(".") != -1) {
		    String docfilename = filename.substring(0, filename.lastIndexOf("."));
			String filetype = filename.substring(filename.lastIndexOf("."));
			try{	
	     	SystemFile sf=fileservcie.getFile(docfilename, filetype);
	     	if(null!=sf){
			String	flashfilename=convertFlashWebService.ConvertAndSaveFlash(sf.getId());
				
				if(null!=flashfilename&&!flashfilename.equals("fail"))
					JSONUtil.write(response, "转换成功");
				else
					JSONUtil.write(response, "可能文件格式不支持");}
	     	   else{
	     		  throw new ServiceException("系统错误，没有得到文件名");
	     		   
	     	   }
				}catch (Exception e) {
					e.printStackTrace();
				}	
			} else {
				JSONUtil.write(response, "可能文件格式不支持");
			}
			}
//*******************************t通过webservice转换flash结束*****************************************************8		
		
		} else {
			throw new ServiceException("系统错误，没有得到文件名");

		} 
    	
//		String[] filenamearray  = filename.split(" ");
//		ArrayList convertresultsstring = new ArrayList();
//		for(int i = 0;i<filenamearray.length;i++){
//			filename = filenamearray[i];
//			System.out.println("遍历文件名："+filename);
//			if (null != filename) {
//				if(null!=Constants.FLASHCONVERTMETHOD&&Constants.FLASHCONVERTMETHOD.equals("local")){
////*********************************通过本地转换flash****************************************8	
////			 首先判断是否符合抽取的文档 doc
//					if (filename.indexOf(".") != -1) {
//						
//						
//						String flashname=filename.substring(0,filename.lastIndexOf("."))+".swf";
//						String sourceext=filename.substring(filename.lastIndexOf("."));
//						//	System.out.println(flashname);
//						int result = kservice.convertDOC2SWF(filename,flashname);
//						if (result==3){
//							//将转换好的flash存入到数据库
//							
//							fileservcie.convertFile( filename, flashname,sourceext);
//							
//							convertresultsstring.add("转换成功");
//							
//							正在上传
//							
//						}
//						else
//							convertresultsstring.add("可能文件格式不支持"); 
//							
//						
//					} else {
//						convertresultsstring.add("可能文件格式不支持"); 
//					}
//				}
////*******************************t通过webservice转换flash*****************************************************8		
//				else{
//					if (filename.indexOf(".") != -1) {
//						String docfilename = filename.substring(0, filename.lastIndexOf("."));
//						String filetype = filename.substring(filename.lastIndexOf("."));
//						try{	
//							SystemFile sf=fileservcie.getFile(docfilename, filetype);
//							if(null!=sf){
//								String	flashfilename=convertFlashWebService.ConvertAndSaveFlash(sf.getId());
//								
//								if(null!=flashfilename&&!flashfilename.equals("fail"))
//									JSONUtil.write(response, "转换成功");
//								else
//									JSONUtil.write(response, "可能文件格式不支持");}
//							else{
//								throw new ServiceException("系统错误，没有得到文件名");
//								
//							}
//						}catch (Exception e) {
//							e.printStackTrace();
//						}	
//					} else {
//						JSONUtil.write(response, "可能文件格式不支持");
//					}
//				}
////*******************************t通过webservice转换flash结束*****************************************************8		
//				
//			} else {
//				throw new ServiceException("系统错误，没有得到文件名");
//				
//			} 
//		}
//		JSONUtil.write(response, convertresultsstring);
		
    }
    
    public String extractconvertsave (){
    	System.out.println("filename:"+filename);
    	System.out.println("domainnode:"+domainnode);
    	System.out.println("categorynode:"+categorynode);
    	String resultstring = new String();
    	String[] filenamearray  = filename.split(" ");
    	int length = filenamearray.length;
    	boolean nothasdomain=true;
    	if(domainnode != null || domainnode != ""){
    		nothasdomain = false;
    	}
    	if(filenamearray != null && !filenamearray.equals("")){
    		
    		for(int i = 0;i<length;i++){
    			try {
					filename = filenamearray[i];
					
					//pl 1.进行抽取知识
					List<Property> propertyList;
					if (null != filename) {
						propertyList = kservice.getkdtail(filename);
						//Pl 将属性的description转化为name，即中文转为英文
						if (propertyList != null){
							resultstring += "    解析成功!";
						}
						else{
							resultstring += "    没有解析出结果,非模板文件! 拒绝转换和保存!@#";
							continue;
						}
						
					} else {
						throw new ServiceException("系统错误，没有得到文件名");
						
					}
					for(int y = 0;y<propertyList.size();y++){
						System.out.println(propertyList.get(y).getName()+"------->"+propertyList.get(y).getValue());
						if(propertyList.get(y).getName()!="ktype"){
							Property newproperty = new Property();
							newproperty.setName(ktypeservice.getProperty(propertyList.get(y).getName()).getName());
							newproperty.setValue(propertyList.get(y).getValue());
							propertyList.set(y, newproperty);
						}
					}
					HashMap hs = new HashMap();
					int id = 2 ;
					List<Property> propertyValues = new ArrayList<Property>();
					for(int a = 0;a<propertyList.size();a++){
						if(propertyList.get(a).getName() == "ktype"){
							id = a;
						}else
							hs.put(propertyList.get(a).getName(), propertyList.get(a).getValue());
					}
					
					Ktype ktype ;
					System.out.println(propertyList.get(id).getValue().toString());
					ktype = ktypeservice.getKtypeByKtypeName(propertyList.get(id).getValue().toString());
					List<KtypeProperty> prolist = ktype.getKtypeproperties();
					if(hs.get("titlename") == null || hs.get("titlename") == "" || hs.get("titlename").equals("")){
						resultstring += "文件标题不能为空！请检查！";
						if(i != (length-1)){
							resultstring += "@#";
						}
						continue;
					}
					//pl 2.转换为flash
					
					if (null != filename) {
						if(null!=Constants.FLASHCONVERTMETHOD&&Constants.FLASHCONVERTMETHOD.equals("local")){
//*********************************通过本地转换flash****************************************8	
//			 首先判断是否符合抽取的文档 doc
							if (filename.indexOf(".") != -1) {
								
								
								String flashname=filename.substring(0,filename.lastIndexOf("."))+".swf";
								String sourceext=filename.substring(filename.lastIndexOf("."));
								//	System.out.println(flashname);
								int result = kservice.convertDOC2SWF(filename,flashname);
								if (result==3){
									//将转换好的flash存入到数据库
									
									fileservcie.convertFile( filename, flashname,sourceext);
									
									resultstring += "    转换成功!";
									
								}
								else
									resultstring += "    转换失败，可能文件格式不支持!"; 
								
								
							} else {
								resultstring += "    转换失败，可能文件格式不支持!"; 
							}
						}
//*******************************t通过webservice转换flash*****************************************************8		
						else{
							if (filename.indexOf(".") != -1) {
								String docfilename = filename.substring(0, filename.lastIndexOf("."));
								String filetype = filename.substring(filename.lastIndexOf("."));
								try{	
									SystemFile sf=fileservcie.getFile(docfilename, filetype);
									if(null!=sf){
										String	flashfilename=convertFlashWebService.ConvertAndSaveFlash(sf.getId());
										
										if(null!=flashfilename&&!flashfilename.equals("fail"))
											resultstring += "    转换成功!";
										else
											resultstring += "    转换失败，可能文件格式不支持!"; 
									}
									else{
										throw new ServiceException("系统错误，没有得到文件名");
										
									}
								}catch (Exception e) {
									e.printStackTrace();
								}	
							} else {
								resultstring += "    转换失败，可能文件格式不支持!"; 
							}
						}
//*******************************t通过webservice转换flash结束*****************************************************8		
						
					} else {
						throw new ServiceException("系统错误，没有得到文件名");
						
					}
					
					
//				for(int y = 0;y<propertyList.size();y++){
//					//pl 单独处理作者
//					if(propertyList.get(y).getName().equals("kauthors")){
//						String Authors_string = propertyList.get(y).getValue()
//						.toString();
//						Authors_string = Authors_string.replaceAll("  ", " ");
//						Authors_string = Authors_string.replaceAll("，", ",");
//						Authors_string = Authors_string.replaceAll("  ", ",");
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
//						Property authorsp = new Property();
//						authorsp.setName("kauthors");
//						authorsp.setValue(authorlist);
//						
//						propertyList.set(y,authorsp);
//					}else if(propertyList.get(y).getName().equals("keywords")){
//
//						String keyword_string = propertyList.get(y).getName();
//						keyword_string = keyword_string.replaceAll("  ", " ");
//						keyword_string = keyword_string.replaceAll("，", ",");
//						keyword_string = keyword_string.replaceAll(" ", ",");
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
//						Property keywordsp =new Property();
//						keywordsp.setName("keywords");
//						keywordsp.setValue(keywordlist);
//						propertyList.set(y,keywordsp);
//					}
//					if (isnotSet) {
//						if(p.getPropertyType().equals("java.util.Date")){
//						if(!"NaN-NaN-NaNTNaN:NaN:NaN".equals(hs.get(p.getName()).toString())){
//						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//											    
//						Date s_date = null;
//						try {
//							s_date = (Date)format.parse(hs.get(p.getName()).toString());
//							} catch (ParseException e) {
//							e.printStackTrace();
//							}
//							p.setValue(s_date);}
//							}
//							else{
//							p.setValue(hs.get(p.getName()));
//							}
//							propertyValues.add(p);
//							}
//					System.out.println(propertyList.get(y).getName()+"------->"+propertyList.get(y).getValue());
//				}
////				System.out.println("什么：-----"+properytList.get(3).getValue());
//			
//			//pl 3.知识属性保存
//					HashMap hs = new HashMap();
//					int id = 2 ;
//					List<Property> propertyValues = new ArrayList<Property>();
//					for(int a = 0;a<propertyList.size();a++){
//						if(propertyList.get(a).getName() == "ktype"){
//							id = a;
//						}else
//							hs.put(propertyList.get(a).getName(), propertyList.get(a).getValue());
//					}
//					
//					Ktype ktype ;
//					System.out.println(propertyList.get(id).getValue().toString());
//					ktype = ktypeservice.getKtypeByKtypeName(propertyList.get(id).getValue().toString());
//			//pl 添加知识模板属性
//			Property ktypeproperty = new Property();
//			ktypeproperty.setName("ktype");
//			ktypeproperty.setValue(ktype);
//			//pl 将第一个键值对ktype-->"学术论文"  改变为 ktype-->ktype(这里的ktype是Ktpye对象)
//			propertyList.set(id,ktypeproperty);
//			//pl 添加知识源文件路径属性
//			Property knowledgesourceproperty = new Property();
//			knowledgesourceproperty.setName("knowledgesourcefilepath");
//			knowledgesourceproperty.setValue(filename);
//			propertyList.add(knowledgesourceproperty);
//			//添加知识域属性
//			Property domainnodeproperty = new Property();
//			domainnodeproperty.setName("domainnode");
//			domainnodeproperty.setValue(domainnode);
//			propertyList.add(domainnodeproperty);
//			
//			//添加知识分类属性
//			Property categorynodeproperty = new Property();
//			categorynodeproperty.setName("categories");
//			String categories=categorynode;
//			categories=categories.replaceAll("，", ",");
//			categories=categories.replaceAll(" ", ",");
//			String[] category=categories.split(",");
//			Set<CategoryTreeNode> categorylist=new HashSet<CategoryTreeNode>();
//			for (int x = 0; x < category.length; x++) {
//				if(!category[x].equals("")){
//					 CategoryTreeNode cn = (CategoryTreeNode)treeservice.getTreeNode(new Long(category[x]));
//					 if(null!=cn)
//						 categorylist.add(cn);
//			}
//			}								
//			categorynodeproperty.setValue(categorylist);
//			
//			propertyList.add(categorynodeproperty);
//			
//			 转换flash失败
//			MetaKnowledge k = kservice.onebyonesave(ktype, propertyList);			
//			imservice.saveMessageAndSubscribeInfo(k);
					
//			hs.put("domainnode", treeservice.getTreeNode(new Long(hs.get("domainnode").toString())));
//			for (KtypeProperty kp : ktype.getKtypeproperties()) {
//				System.out.println(kp.getProperty().getName()+"------>"+kp.getProperty().getValue());
//			}
					
					for (KtypeProperty kp : ktype.getKtypeproperties()) {
						//   下面是要改的  getName改为getDescription
						Property p = kp.getProperty();
//				System.out.println("判断0：：：：："+p.getName());
//				System.out.println("判断1：：：：："+p.getName().equals("domainnode"));
//				System.out.println("判断2：：：：："+(null != domainnode));
						boolean isnotSet = true;
						boolean knowledgetypeexist = true;
						if (null != hs.get(p.getName())&&
								(!hs.get(p.getName()).equals(""))) {
							
							//单独处理知识类型
							if (p.getName().equals("knowledgetype")) {
								
								if(hs.get("knowledgetype") == null){
									hs.put("knowledgetype", hs.get("ktype"));
								}
								String knowledgetypename = hs.get("knowledgetype")
								.toString();
								
								if(knowledgetypename == null || knowledgetypename == ""){
									knowledgetypename = ktype.getKtypeName();
								}
								Knowledgetype knowledgetype = kservice.SearchAndSaveKnowledgetype(knowledgetypename);
								p.setValue(knowledgetype);
								isnotSet = false;
								propertyValues.add(p);
							}
							
							
							
							// 单独处理作者，因为在知识中作者是作为对象属性的
							if (p.getName().equals("kauthors")
									&& null != hs.get("kauthors")) {
								String Authors_string = hs.get("kauthors")
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
									&& null != hs.get("keywords")) {
								String keyword_string = hs.get("keywords")
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
//					System.out.println("判断1：：：：："+p.getName().equals("domainnode"));
//					System.out.println("判断2：：：：："+(null != domainnode));
//					if (p.getName() == "domainnode"&& (null != domainnode)) {
//						System.out.println("单独处理知识域进来了啊");
//						p.setValue(treeservice.getTreeNode(Long.parseLong(domainnode)));
//				   		propertyValues.add(p);
//						isnotSet = false;
//						nothasdomain=false;
//					}
							
							// 单独处理知识中的知识分类 categorys
							if (p.getName().equals("categories")
									&& null != categorynode) {
								String categories=categorynode;
								categories=categories.replaceAll("，", ",");
								categories=categories.replaceAll(" ", ",");
								String[] category=categories.split(",");
								Set<CategoryTreeNode> categorylist=new HashSet<CategoryTreeNode>();
								for (int x = 0; x < category.length; x++) {
									if(!category[x].equals("")){
										CategoryTreeNode cn = (CategoryTreeNode)treeservice.getTreeNode(Long.parseLong(category[x]));
										if(null!=cn)
											categorylist.add(cn);
									}
								}								
								p.setValue(categorylist);
								
								isnotSet = false;
								propertyValues.add(p);
							}
							
							if (isnotSet) {
								
								if(p.getPropertyType().equals("java.util.Date")){
									if(!"NaN-NaN-NaNTNaN:NaN:NaN".equals(hs.get(p.getName()).toString())){
										SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
										
										Date s_date = null;
										try {
											s_date = (Date)format.parse(hs.get(p.getName()).toString());
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										p.setValue(s_date);}
								}
								else{
									p.setValue(hs.get(p.getName()));
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
					// pl 添加知识域属性
					Property domainnodep = new Property();
					domainnodep.setName("domainnode");
					domainnodep.setValue(treeservice.getTreeNode(Long.parseLong(domainnode)));
					propertyValues.add(domainnodep);
					// 添加源文件路径属性
					Property knowledgesource = new Property();
					knowledgesource.setName("knowledgesourcefilepath");
					knowledgesource.setValue(filename);
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
					
					
					
					
					
					if(nothasdomain)
						throw new ServiceException("没有选择域！");	
					
//					try {
					MetaKnowledge k = kservice.save(ktype, propertyValues);			
					imservice.saveMessageAndSubscribeInfo(k);
					resultstring += "    保存成功!";
//					} catch (Exception e) {
//						System.out.println(e);
//						resultstring += "    由于系统原因，保存失败!";
//					}
					
					
					
					addActionMessage("保存知识属性成功");
					if(ktype.getId().equals(10000L)){
						return null;
					}
					if(i != (length-1)){
						resultstring += "@#";
					}
				} catch (NumberFormatException e) {
					resultstring += "该文件有错误!";
					if(i != (length-1)){
						resultstring += "@#";
					}
					e.printStackTrace();
				}
    		}
    	}else
    		throw new ServiceException("没有文件,请重新选择!");
    	JSONUtil.write(response, resultstring);
		return null;
    }
    public void clientupload() {
 	   //SystemUser  u=userservice.getUser();
    	Long fileid=new Long(0);
    	try {
    		username=	URLDecoder.decode(username,"GBK");
    		password=URLDecoder.decode(password,"GBK");
    	
		} catch (UnsupportedEncodingException e2) {
			
		}
    String checkuser="0";
     SystemUser u= userservice.getUser(username.trim());
     System.out.println(u.getPassword());
     if(u.getPassword().equals(password.trim()))
     {  
    	 checkuser="1";
    	 
     }
 		String fileoralname = "";
 		String newFileName = null;
 		BufferedOutputStream bos = null;
 		BufferedInputStream bis = null;
 		String reInfo = "";// 上传成功返回的东西
 		// String path = "c:/TEMP";
 		// 附件存储路径
 		String path = Constants.SOURCEFILE_PATH_TEMP;
 		System.out.println(uploadDir);
 		if (null != uploadDir && uploadDir.equals("source"))
 			path = Constants.SOURCEFILE_PATH_TEMP;
 		// String targetDirectory =path;
 		// 附件系统保存名称 用uuid唯一标识
 		String targetFileName = UUID.randomUUID().toString();
 		boolean fileuploadsuccess=false;
        if(null!=file&&file.size()>0){
        	
        	 try {
        		 System.out.println("fileTitle="+fileTitle);
			fileTitle=URLDecoder.decode(fileTitle,"GBK");
   			fileAbstract=URLDecoder.decode(fileAbstract,"GBK");
   			securityLevel=URLDecoder.decode(securityLevel,"GBK");
   			fileKeyword=URLDecoder.decode(fileKeyword,"GBK");
//   			System.out.println("fileTitle="+fileTitle);
//   			System.out.println("fileAbstract="+fileAbstract);
//   			System.out.println("securityLevel="+securityLevel);
//   			System.out.println("fileKeyword="+fileKeyword);
   	
     		} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	     fileuploadsuccess=true;
 		for (int i = 0; i < file.size(); i++) {
 	
 			System.out.println("获得上传文件");
 			long now = new Date().getTime();
 			int index = fileFileName.get(i).lastIndexOf('.');
 			File files = new File(path);
 			if (!files.exists()) {
 				files.mkdir();// 创建个文件夹
 			//	System.out.println("make a  dir");
 			}
 			if (index != -1) {
 				newFileName = targetFileName
 						+ fileFileName.get(i).substring(index);// 生成新文件名
 				fileoralname = fileFileName.get(i);
 			} else
 				newFileName = targetFileName;
 			reInfo += newFileName + "上传成功！";
 			System.out.println(reInfo);
			 fileid=fileservcie.save(file.get(i), newFileName);
			
//××××××××××××××××××××××××××××采用数据库文件系统，替换原有系统文件，被注释掉××××××××××××××××××××××××	
 			bos = null;
 			bis = null;
 			try {
 				if (file.get(i) != null) {
 					FileInputStream fis = new FileInputStream(file.get(i)); // /////////
 					bis = new BufferedInputStream(fis);
 					FileOutputStream fos = new FileOutputStream(new File(files,
 							newFileName));
 					bos = new BufferedOutputStream(fos);
 					byte[] buf = new byte[4096];
 					int len = -1;
 					while ((len = bis.read(buf)) != -1) {
 						bos.write(buf, 0, len);
 					}
 				} else {
 					System.out.println("没有获得上传文件！");

 				}
 			} catch (Exception e) {
 				e.printStackTrace();
 				throw new ServiceException("传送失败！！");
 			} finally {

 				try {
 					if (null != bis)
 						bis.close();
 				} catch (IOException e) {
 					e.printStackTrace();
 				}

 				try {
 					if (null != bos)
 						bos.close();
 				} catch (IOException e) {
 					e.printStackTrace();
 				}
 			}
 			// //////////////////////////////
//××××××××××××××××××××××××××××××××××××××注释结束				
 		}
 		
}
 		// 最后若没错误返回就这里返回了
 		try {
 
 			if(!fileuploadsuccess){
 		//		System.out.println("checkSystemUserTestsuccess");
 			response.getWriter().write(checkuser);
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		if(fileuploadsuccess){
 			Knowledge k  ;
 			try {
 				// 获得具体的知识类型的实例，用接口knowledg作为容器
 				k = (Knowledge) Class.forName("edu.zju.cims201.GOF.hibernate.pojo.Simpleknowledge").newInstance();
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
 				logger.warn("没有该知识类型，没有找到对应的class{}", "edu.zju.cims201.GOF.hibernate.pojo.Simpleknowledge");
 				throw new ServiceException("没有该知识类型，没有找到对应的class");
 			}
 			
 		
 		
 		k.setUploader(u);
 		k.setTitlename(fileTitle);
 		//添加关键词属性
 		fileKeyword = fileKeyword.replaceAll("：", ",");
 		fileKeyword = fileKeyword.replaceAll("  ", " ");
 		fileKeyword = fileKeyword.replaceAll("，", ",");
 		fileKeyword = fileKeyword.replaceAll(" ", ",");
 		fileKeyword = fileKeyword.replaceAll("、", ",");
 		fileKeyword = fileKeyword.replaceAll("；", ",");
		fileKeyword = fileKeyword.replaceAll(";", ",");
		String[] keywords = fileKeyword.split(",");
		Set<Keyword> keywordlist = new HashSet<Keyword>();
		for (String keyword : keywords) {
			if (null != keyword && !keyword.equals("")) {
				Keyword keywordT = kservice
						.SearchAndSaveKeyword(keyword.trim());
				// System.out.println("keyword==="+keywordT.toString());
				keywordlist.add(keywordT);
			}
		}
		k.setKeywords(keywordlist);
		//添加知识类别属性
		//单独处理知识类型
	
		           knowledgeType="在线知识";
			
					Knowledgetype knowledgetype = kservice.SearchAndSaveKnowledgetype(knowledgeType);
				
	             	k.setKnowledgetype(knowledgetype);
		
		
		
		
 		//添加作者属性
		List<Author> authorlist = new ArrayList<Author>();
		
			
				Author authorT = kservice
						.searchAndSaveAuthor(u.getName().trim());
				authorlist.add(authorT);
	     k.setKauthors(authorlist);
		
 		
 		//添加摘要属性
 		k.setAbstracttext(fileAbstract);
 		// 添加知识类型属性
	//	Property ktypep = new Property();
 	
 		Ktype ktype= ktypeservice.getKtype(new Long(2));
 		k.setKtype(ktype);

		// 添加源文件路径属性
 //		System.out.println("newFileName="+newFileName);
	k.setKnowledgesourcefilepath(newFileName);
		// 添加知识状态属性
	k.setStatus("0");
		// 添加知识版本属性
	Date uploadtimeT = new Date();
	k.setUploadtime(uploadtimeT);
		Version version = new Version();

  
		version.setVersionTime(uploadtimeT);
		
		String	versionNumber="1.0";
		version.setVersionNumber(versionNumber);
	     k.setVersion(version);
		
		//添加记录信息
	 	
		// 添加知识版本属性
	
		CommentRecord cr = new CommentRecord();
		cr.setCommentCount(new Long(0));
		cr.setViewCount(new Long(0));
		cr.setRate(new Float(0));
		cr.setDownloadCount(new Long(0));
       k.setCommentrecord(cr);
 	   k.setIsvisible(true);	
 	   kservice.save(k,fileid);
 		}
 		
 	}
    
    
    
    
    
    
    
    
    
    
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	public String getFileKeyword() {
		return fileKeyword;
	}

	public void setFileKeyword(String fileKeyword) {
		this.fileKeyword = fileKeyword;
	}

	public String getFileAbstract() {
		return fileAbstract;
	}

	public void setFileAbstract(String fileAbstract) {
		this.fileAbstract = fileAbstract;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getCategorynode() {
		return categorynode;
	}

	public void setCategorynode(String categorynode) {
		this.categorynode = categorynode;
	}

	public String getDomainnode() {
		return domainnode;
	}

	public void setDomainnode(String domainnode) {
		this.domainnode = domainnode;
	}
	public String getKnowledgeType() {
		return knowledgeType;
	}

	public void setKnowledgeType(String knowledgeType) {
		this.knowledgeType = knowledgeType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getPwidth() {
		return pwidth;
	}

	public void setPwidth(String pwidth) {
		this.pwidth = pwidth;
	}

	public String getPheight() {
		return pheight;
	}

	public void setPheight(String pheight) {
		this.pheight = pheight;
	}





	public String getFilepathTemp() {
		return filepathTemp;
	}





	public void setFilepathTemp(String filepathTemp) {
		this.filepathTemp = filepathTemp;
	}





	public String getIsNewVersion() {
		return isNewVersion;
	}





	public void setIsNewVersion(String isNewVersion) {
		this.isNewVersion = isNewVersion;
	}



	


	
	
	
	
	

}
