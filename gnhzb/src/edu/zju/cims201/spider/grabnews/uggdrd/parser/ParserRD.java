package edu.zju.cims201.spider.grabnews.uggdrd.parser;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParserRD implements edu.zju.cims201.spider.commontool.Parser{
    private String source;
    private Log log = LogFactory.getLog(this.getClass());
	public ParserRD(){
    	
    }
    public ParserRD(String source){
    	this.source = source;
    }
public String getFromSource() {
		
		String fromsource = "";
		fromsource=StringUtils.substringBetween(this.source,"&nbsp;&nbsp;作者：", "</a>&nbsp;&nbsp;点击：").trim();
		fromsource=StringUtils.substringAfterLast(fromsource,">");	
//        if(this.source.indexOf("来源")>0){
//			String regEx = "来源：([^&]+)\\s*&";
//			Pattern p = Pattern.compile(regEx);
//			Matcher m = p.matcher(this.source);
//			if(m.find()){
//				fromsource =  m.group(1);
//				fromsource = fromsource.trim();
//			}
//		}else if(this.source.indexOf("作者：")>0){
//			String regEx = "作者：([^<]+)\\s*<";
//			Pattern p = Pattern.compile(regEx);
//			Matcher m = p.matcher(this.source);
//			if(m.find()){
//				fromsource =  m.group(1);
//				fromsource = fromsource.trim();
//			}
//		}

        if(StringUtils.isBlank(fromsource)){
			fromsource = "模具联盟网";
		}
		return fromsource;
	}
	
	public String getKeyWord() {
		String keyword="";
		/*String regEx = "<meta name=\"Keywords\" content=\"([^\"]+)\" />";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(source);
		if(m.find()){
			keyword =  m.group(1);
		}	
		keyword = StringUtils.replace(keyword, ",", " ");
		String title = this.getTitle();
		if(StringUtils.contains(keyword, title)){
			keyword = StringUtils.remove(keyword, title).trim();
		}
*/
 		return keyword;
	}

	public Date getPublicDate() {
		Date date = null;
		String[] pattern = { "yyyy-MM-dd HH:mm:ss" };
		try {
			date = DateUtils.parseDate(this.getPublicTime(),pattern);
		} catch (Exception e) {
			log.error("处理时间格式化错误");
		}
		return date;
	}

	public String getPublicTime() {
		String publicTime="";
		String regEx = "(\\d{4})-(\\d{1,2})-(\\d{1,2})";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(source);
		if(m.find()){
			publicTime =  m.group(1)+"-"+m.group(2)+"-"+m.group(3)+" 00:00:00";
			publicTime = publicTime.trim();
		}else{
			publicTime =  DateFormatUtils.format(Calendar.getInstance().getTime(),"yyyy-MM-dd HH:mm:ss");
		}
		return publicTime;
	}

	public String getSummary() {
        String summary = "";
//        if(this.source.indexOf("来源")>0){
//		//获得新闻正文内容
//        String open = "td valign=\"top\" class=\"wz_title4\" id=\"doZoom\">";
//		String close = "</table>";
//		summary = StringUtils.substringBetween(this.source, open, close);
//		}
//        else{
        String open = "<SPAN class=big title=切换到大字体>T</SPAN></a></div>";
    	String close = "<br /><p style='color:#808080;'>(编辑";
    	summary = StringUtils.substringBetween(this.source, open, close);       	
//        }	
		//去掉标签里面的样式
		/*String [] removestr = {" id=\"fontzoom\""," class=\"clearfix\""};
		for(int i=0;i<removestr.length;i++){
			summary = StringUtils.remove(summary, removestr[i]);
		}*/
		summary = summary.trim();
		return summary;
	}

	public String getTitle() {
		String title = "";
//		 if(this.source.indexOf("来源")>0){
//		title = StringUtils.substringBetween(this.source,"<span class='bt_title3'>", "</span>").trim();
//		 }
//		 else{
		title = StringUtils.substringBetween(this.source,"<div class=\"newshow_title\">", "</div>").trim(); 
		if(title.length()>255){
			title = StringUtils.substringBetween(this.source,"<div class=\"newshow_title\">", "/div>").trim(); 
		}
//		 }
		return title;
	}
	public String getAllTitle()
	{
		String title = "";
		title = StringUtils.substringBetween(this.source,"<title>", "</title>").trim();
		return title;
	}
	public String getNewsType()
	{
		String newstype = "";

			/*String regEx = "汽车部落</a> &gt; <([^>]+)>([^<]+)</a> &gt; 正文</td>";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(source);
			if(m.find()){
				newstype =  m.group(2);
				newstype = newstype.trim();
			}	
		
		else if(this.source.indexOf("资讯中心")>0){
			String regEx = "您的位置：<([^>]+)>中国服装网</a> <([^>]+)>资讯中心</a> <([^>]+)>([^<]+)</a> 正文";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(source);
			if(m.find()){
				newstype =  m.group(4);
				newstype = newstype.trim();
			}
		}
*/
		return newstype;
	}
	/**
	 * 传入待解析度html源代码source
	 */
	public void setSource(String source) {
		this.source = source;
		
	}   
	
}
