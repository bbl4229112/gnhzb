package edu.zju.cims201.spider.grabnews.chinapj.rule;

import java.util.regex.Pattern;

import edu.zju.cims201.spider.commontool.UrlRule;

public class ChinapjUrlRule extends UrlRule{
	protected String exclusion_url = "css|js|jpg|png|gif|swf|pdf|doc|CSS|JS|JPG|PNG|GIF|SWF|PDF|DOC";

	protected String regExForHouzui = "\\.aspx|\\.asp|\\.css|\\.js|\\.jpg|\\.png|\\.gif|\\.swf|\\.pdf|\\.doc|\\.CSS|\\.JS|\\.JPG|\\.PNG|\\.GIF|\\.SWF|\\.PDF|\\.DOC|\\.htm";

	protected String egExuserfulurl = "http://www.chinapj.com/";
	//usefull
	protected String newspageurlpattern ="http://www\\.chinapj\\.com/news/shownews\\.asp\\?i\\w=\\d{1,}";
	
	public boolean isUrlAdd(String url) {
		boolean isUrlAdd = false;
		if (isNewsPage(url)||isMediaSource(url)) {
			isUrlAdd = true;
		}		
		return isUrlAdd;
	}
	
	public boolean isNewsPage(String url) {
		boolean isNewsPage = false;
		isNewsPage = Pattern.compile(newspageurlpattern).matcher(url).find();
		return isNewsPage;
	}
}
