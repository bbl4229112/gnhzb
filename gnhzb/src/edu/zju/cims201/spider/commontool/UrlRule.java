package edu.zju.cims201.spider.commontool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.util.Constants;

public class UrlRule {
	protected String mediaSource = "\\.jpg|\\.JPG|\\.png|\\.PNG|\\.gif|\\.GIF|\\.pdf|\\.PDF|\\.doc|\\.DOC|\\.bmp|\\.BMP|\\.xls|\\.XLS|\\.ppt|\\.PPT";

	private ApplicationContext context = new FileSystemXmlApplicationContext(new String[]{Constants.APPLICATIONCONTEXT});  
	private CommonDao commondao=(CommonDao)context.getBean("commonDao");
	public boolean isExist(String url){		
		List templist=new ArrayList();
		try {
			templist = commondao.findByProperty(Class
					.forName("edu.zju.cims201.GOF.hibernate.pojo.News"),
					"url", url);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(templist.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	public boolean isContain(String url,String parenturl){
//		String queryString = "from "+entityClass.getName()+" o where o."	+ propertyName + "= ?";		
		List templist=new ArrayList();
		try {
			templist = commondao.findByProperty(Class
					.forName("edu.zju.cims201.GOF.hibernate.pojo.News"),
					"url='"+url+"'and o.parenturl", parenturl);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(templist.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	
	public Knowledge findNewsByUrl(String url){
//		String queryString = "from "+entityClass.getName()+" o where o."	+ propertyName + "= ?";		
		List templist=new ArrayList();
		Knowledge k;
		try {
			k = (Knowledge) Class.forName(
			"edu.zju.cims201.GOF.hibernate.pojo.News")
			.newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			templist = commondao.findByProperty(Class
					.forName("edu.zju.cims201.GOF.hibernate.pojo.News"),
					"url", url);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != templist && !templist.isEmpty()){
			k = (Knowledge) templist.get(0);
			return k;
		}
		else{
			return null;
		}
			
	}
	
	public boolean isUrlAdd(String url){
		return false;
	}
	public boolean isNewsPage(String url) {
		return false;
	}
	public boolean isSecondPage(String url){
		return false;
	}
	public boolean isMediaSource(String url){
    	boolean isMediaSource = false;
    	isMediaSource = Pattern.compile(mediaSource).matcher(url).find();
    	return isMediaSource;
    }
}
