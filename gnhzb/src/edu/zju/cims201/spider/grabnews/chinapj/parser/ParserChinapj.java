package edu.zju.cims201.spider.grabnews.chinapj.parser;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParserChinapj implements edu.zju.cims201.spider.commontool.Parser{
    private String source;
    private Log log = LogFactory.getLog(this.getClass());
	public ParserChinapj(){
    	
    }
    public ParserChinapj(String source){
    	this.source = source;
    }
	public String getFromSource() {
		
		String fromsource = "";
		 if(this.source.indexOf("信息源自：")>0){
				String regEx = "信息源自：([^<]+)<";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(this.source);
			if(m.find()){
				fromsource =  m.group(1);
				fromsource = fromsource.trim();
			}
		}/*else if(this.source.indexOf("作者：")>0){
			String regEx = "作者：([^<]+)\\s*</div>";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(this.source);
			if(m.find()){
				fromsource =  m.group(1);
				fromsource = fromsource.trim();
			}
		}
*/ 
        if(StringUtils.isBlank(fromsource)){
			fromsource = "中国汽配网";
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
		String regEx = "(\\d{4})-(\\d{1,2})-(\\d{1,2})\\s(\\d{2}):(\\d{2}):(\\d{2})";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(source);
		if(m.find()){
			publicTime =  m.group(1)+"-"+m.group(2)+"-"+m.group(3)+" "+m.group(4)+":"+m.group(5)+":"+m.group(6);
			publicTime = publicTime.trim();
		}else{
			publicTime =  DateFormatUtils.format(Calendar.getInstance().getTime(),"yyyy-MM-dd HH:mm:ss");
		}		
		return publicTime;
	}

	public String getSummary() {
        String summary = "";
		
		//获得新闻正文内容
        String open = "<span class=\"hj14\"></span></div>";
        String close = "</span>";
        summary = StringUtils.substringBetween(this.source, open, close);
		
        /*String open = "<!---content--->";
		String close = "</table>";
		summary = StringUtils.substringBetween(this.source, open, close);*/
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
		title = StringUtils.substringBetween(this.source,"<title>", "</title>").trim();
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
		String regEx = "汽车部落</a> &gt; <([^>]+)>([^<]+)</a> &gt; 正文</td>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(source);
		if(m.find()){
			newstype =  m.group(2);
			newstype = newstype.trim();
			
			/*String regEx = "汽车部落</a> &gt; <([^>]+)>([^<]+)</a> &gt; 正文</td>";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(source);
			if(m.find()){
				newstype =  m.group(2);
				newstype = newstype.trim();
				*/
			
			}	
		
		/*else if(this.source.indexOf("资讯中心")>0){
			String regEx = "您的位置：<([^>]+)>中国服装网</a> <([^>]+)>资讯中心</a> <([^>]+)>([^<]+)</a> 正文";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(source);
			if(m.find()){
				newstype =  m.group(4);
				newstype = newstype.trim();
			}
		}
*/
		return newstype=null;
	}
	/**
	 * 传入待解析度html源代码source
	 */
	public void setSource(String source) {
		this.source = source;
		
	}   
	
}
