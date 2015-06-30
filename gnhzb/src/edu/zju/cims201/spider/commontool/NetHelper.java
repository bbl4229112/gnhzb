package edu.zju.cims201.spider.commontool;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import edu.zju.cims201.GOF.util.Constants;

public class NetHelper {
	protected String webappname = "";
	public NetHelper() {
		webappname = Constants.WEBAPPNAME;
	}

	private Log log = LogFactory.getLog(this.getClass().getName());

	public InputStream getMeidaSource(String url) {
		InputStream is = null;
		try {
			URL urlobject = new URL(url);
			is = urlobject.openStream();
		} catch (MalformedURLException e) {
			log.info("构造URL地址错误：" + url);
		} catch (IOException e) {
			log.info("URL IO读取错误");
		}
		return is;
	}

	public InputStream getMeidaSource(URL url) {
		InputStream is = null;
		try {
			is = url.openStream();
		} catch (MalformedURLException e) {
			log.info("构造URL地址错误：" + url);
		} catch (IOException e) {
			log.info("URL IO读取错误");
		}
		return is;
	}
    
	/**
	 * 默认为%web_app%/media
	 * @return String web应用的多媒体存放路径地址
	 */
	public String getRealMediaFolderPath(){
		String path = null;
		try{
			path = ClassLoaderUtil.class.getClassLoader().getResource("").toString();
			path = StringUtils.remove(path, "file:/");
			path = StringUtils.remove(path, "WEB-INF/classes/")+"media/";
		}catch(Exception e){
			
		}
		return path.replaceAll("%20", " ");
	}
	/**
	 * 
	 * @param mediaurl 多媒体文件url地址
	 * @return 返回多媒体文件的本地全路径名+文件名
	 */
	public String getMediaUrlToFileFullname(String mediaurl){
		String mediafileurl = this.getRealMediaFolderPath()+this.formatMediaUrlToFileName(mediaurl);
		return mediafileurl;
	}
	
	/**
	 * 格式化多媒体url地址，首先http:// 然后转换 / 成 -
	 * @param mediaurl 多媒体url地址
	 * @return 多媒体文件名
	 */
	public String formatMediaUrlToFileName(String mediaurl){
		mediaurl = StringUtils.remove(mediaurl, "http://");
		mediaurl = StringUtils.replace(mediaurl, "/", "-");
		return mediaurl;
	}
	/**
	 * 把地址为url的多媒体文件，保存到本机上
	 * @param url 多媒体文件url地址
	 * @param filepath 保存路径
	 * @param filename 保存文件名
	 */
	public void saveToFile(String url, String filepath, String filename){
		String filefullname = filepath+java.io.File.separator+filename;
		this.saveToFile(url, filefullname);
	}
	/**
	 * 把地址为url的多媒体文件，保存到本机上
	 * @param url 多媒体文件url地址
	 * @param filename 保存文件全路径名
	 */
	public void saveToFile(String url, String filefullname){
		try {
			byte[] buf = new byte[1024];
			int readLen = 0;
			FileOutputStream fos = new FileOutputStream(filefullname);
			InputStream is=new BufferedInputStream(getMeidaSource(url));
			while ((readLen=is.read(buf, 0, 1024))!=-1) {
				fos.write(buf, 0, readLen);
			}
			is.close();
			fos.close();
		}catch(IOException e){
			log.info("保存多媒体文件 IO异常");
			log.info("url = " +url);
			log.info("filepath = " +getMediaUrlToFileFullname(url));
		}
	}
	
	/**
	 * 把地址为url的多媒体文件，先传转换文件名后再保存到本机web应用的media文件夹下
	 * @param url 多媒体文件url地址
	 * @param filename 保存文件全路径名
	 */
	public void saveToFile(String url){
		try {
			byte[] buf = new byte[1024];
			int readLen = 0;
			String filepath = getMediaUrlToFileFullname(url);
			log.info("url = " +url);
			log.info("filepath = " +filepath);
			File file = new File(filepath);
			if(file.exists()){
				file.delete();
			}
			file = null;
			FileOutputStream fos = new FileOutputStream(filepath);
			InputStream is=new BufferedInputStream(getMeidaSource(url));
			while ((readLen=is.read(buf, 0, 1024))!=-1) {
				fos.write(buf, 0, readLen);
			}
			is.close();
			fos.close();			
		}catch(IOException e){
			log.info("保存多媒体文件 IO异常");
			log.info("url = " +url);
			log.info("filepath = " +getMediaUrlToFileFullname(url));
		}
	}
	/**
	 * 把html代码中所有链接地址转换成本地url地址。</br>
	 * 例如<b>http://www.sina.com/example.jpg</b> -> <b>/media/www.sina.com-example.jpg</b>
	 * @param source html代码
	 * @return 格式化后的html代码
	 */
	public String formatHtmlSource(String source,String parenturl){
		String src="src=";
		String href="href=";
		StringBuffer htmlsource = new StringBuffer();
		List medialinklist = new ArrayList();
		String mediaSource = "\\.jpg|\\.JPG|\\.png|\\.PNG|\\.gif|\\.GIF|\\.pdf|\\.PDF|\\.doc|\\.DOC|\\.bmp|\\.BMP|\\.xls|\\.XLS|\\.ppt|\\.PPT";
		Pattern p = Pattern.compile(mediaSource);
		int start = 0;
		int end = 0;
		//找出所有的src元素的位置，以及内容
		while(source.indexOf("src", start)>0){
			start = source.indexOf(src, start);
			end = source.indexOf("\"", start+5);
			String srccontent = source.substring(start+5, end);
			if(p.matcher(srccontent).find()){
				URL target=null;
				  try {
					  target = new URL(new URL(parenturl),srccontent);
				  } catch ( MalformedURLException e ) {
					  log.trace("Spider found other link: " + srccontent );
					  continue;
				}
				MediaLink medialink = new MediaLink(target.toString(),start+5,end-1);
				medialinklist.add(medialink);
			}
			start = end;
			
		}
//	    //找出所有的href元素的位置，以及内容
//		start = 0;
//		end = 0;
//		while(source.indexOf(href, start)>0){
//			start = source.indexOf(href, start);
//			end = source.indexOf("\"", start+6);
//			String srccontent = source.substring(start+6, end);
//			if(p.matcher(srccontent).find()){
//				URL target=null;
//				  try {
//					  target = new URL(new URL(parenturl),srccontent);
//				  } catch ( MalformedURLException e ) {
//					  log.trace("Spider found other link: " + srccontent );
//					  continue;
//				}
//				MediaLink medialink = new MediaLink(target.toString(),start+5,end-1);
//				medialinklist.add(medialink);
//			}
//			start = end;			
//		}
		start = 0;
		for(int i=0;i<medialinklist.size();i++){
			MediaLink medialink = (MediaLink)medialinklist.get(i);
			htmlsource.append(source.substring(start, medialink.start));
			htmlsource.append("/");
			htmlsource.append(this.getWebappname());
			htmlsource.append("/media/");
			htmlsource.append(this.formatMediaUrlToFileName(medialink.medialink));
			start = medialink.end+1;
			//System.out.println(medialink.start + " - "+ medialink.end + " : "+ medialink.medialink);
		}
		htmlsource.append(source.subSequence(start, source.length()));
		//System.out.println("htmlsource = " +htmlsource.toString());
		return htmlsource.toString();
	}

	public static void main(String[] args) {
		/*int[] temp = {20013,22269,26381,35013,32593,101,102,117,46,99,111,109,46,99,110};
		StringBuffer value=new StringBuffer();
		for(int i=0;i<temp.length;i++){
			value.append((char)(temp[i]));
			
		}
		System.out.println(value);*/
		/*Log log = LogFactory.getLog("NetHelper main()");	
		String name = "1_1_1_";
        String [] attachfileid = name.split("_");
        for(int i=0;i<attachfileid.length;i++){
        	System.out.println("attachfileid = "+attachfileid[i]);
        }*/
		edu.zju.cims201.spider.commontool.ProxyUtil.useHttpProxy();
		
		NetHelper nethelper = new NetHelper();
		String imgurl = "http://blog.sina.com.cn/images/default_icon.jpg";
		try {
			byte[] buf = new byte[1024];
			int readLen = 0;
			FileOutputStream fos = new FileOutputStream("D:/default_icon.jpg");
			InputStream is=new BufferedInputStream(nethelper.getMeidaSource(imgurl));
			while ((readLen=is.read(buf, 0, 1024))!=-1) {
				fos.write(buf, 0, readLen);
			}
			is.close();
			fos.close();
		}catch(IOException e){
			System.out.println("保存多媒体文件 IO异常");
		}
		
		/*String tags="士大夫";
		String[] taglist = tags.split("，");
		for(int i=0;i<taglist.length;i++){
			System.out.println("res = " +taglist[i]);
		}*/
		/*String strvalue = "fsf'dsfesee";
		System.out.println(strvalue.replaceAll("\'", "''"));*/
		
		/*DBConnection dbc = null;
		Connection conn = null;
		Statement stmt = null;
		try {
			dbc = new DBConnection();
			conn = dbc.getDBConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for(int i=0;i<entries.size();i++){
				SyndEntry entry = (SyndEntry)entries.get(i);
				String publicTime = DateFormatUtils.format(entry.getPublishedDate(),"yyyy-MM-dd HH:mm:ss");
				String sql = "INSERT INTO news(title,time,url,parentUrl,summary,publicTime,fromSource,publicDate,newsType) values('";
				sql+=entry.getTitle().replaceAll("\'", "''")+"','";
				sql+=DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+"','";
				sql+=entry.getLink()+"','";
				sql+=parenturl+"','";
				sql+=entry.getDescription().getValue().replaceAll("\'", "''")+"','";
				sql+=publicTime+"','";
				sql+=entry.getAuthor().replaceAll("\'", "''")+"','";
				sql+=publicTime+"','";
				sql+=categoryname+"')";
				stmt.addBatch(sql);
			}
			String sql_newstype = "insert into categorynews(categoryid,newsid)";
			sql_newstype += " select category.categoryid,news.id as newsid";
			sql_newstype += " from news,categoryurl,category";
			sql_newstype += " where (news.newstype = category.name and categoryurl.categoryid = category.categoryid and news.hasclassify is null)";
			String updatenews = "update news,categorynews set news.hasclassify = true where news.id = categorynews.newsId";
			stmt.addBatch(sql_newstype);
			stmt.addBatch(updatenews);
			stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					dbc.closeDBConnection(conn);
			} catch (SQLException ex) {
			}
		}*/
		/*String newsidanduserid = "84:";
		String[] infos = newsidanduserid.split(";");
		System.out.println(infos.length);*/
		
		/*String source = "document.write(FormatDateTime(#2005-11-5 13:14:00#,2))</script>";
		String regEx = "document.write\\(FormatDateTime\\(#([^#]+)#";
	       Pattern p = Pattern.compile(regEx);
	       Matcher m = p.matcher(source);
	       String publicTime = "";
	       if(m.find()){
	    	   
	    	   String str = m.group(1);
	    	   
	    	   if(str.indexOf(":")>0){
	    		   regEx = "(\\d{4})-(\\d{1,2})-(\\d{1,2})\\s*(\\d{1,2}):(\\d{1,2}):(\\d{1,2})";
	    	       p = Pattern.compile(regEx);
	    	       m = p.matcher(str);
	    	       if(m.find()){
	    	    	   String year,month,day,hour,min,sec;
		    	       year = m.group(1);
		    	       month = m.group(2);
		    	       day = m.group(3);
		    	       hour = m.group(4);
		    	       min = m.group(5);
		    	       sec = m.group(6);
		    	       
						if (month.length() < 2)
						{
							month = "0" + month;
						}
						if (day.length() < 2)
						{
							day = "0" + day;
						} 
						publicTime = year+"-"+month+"-"+day+ " " +hour+":"+min+":"+sec;
					    System.out.println("time="+publicTime);
	    	       }
	    	       
	    	       
	    	   }
	    	   else{
	    		   regEx = "(\\d{4})-(\\d{1,2})-(\\d{1,2})";
	    	       p = Pattern.compile(regEx);
	    	       m = p.matcher(str);
	    	       String month,day;
	    	       month = m.group(2);
	    	       day = m.group(3);
					if (month.length() < 2)
					{
						month = "0" + month;
					}
					if (day.length() < 2)
					{
						day = "0" + day;
					} 
					
					publicTime = m.group(1)+"-"+month+"-"+day +" 00:00:00";
				    System.out.println("time="+publicTime);
	    	   }
	    	   
	       }
	       else{
				publicTime = DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");    	   
	       }
	       System.out.println("time="+publicTime);*/
	}

	public String getWebappname() {
		return webappname;
	}

	public void setWebappname(String webappname) {
		this.webappname = webappname;
	}
	static final class MediaLink{
		int start = 0;
		int end = 0;
		String medialink = "";
		public MediaLink(String medialink, int start, int end){
			this.medialink = medialink;
			this.start = start;
			this.end = end;
		}		
	}
}
