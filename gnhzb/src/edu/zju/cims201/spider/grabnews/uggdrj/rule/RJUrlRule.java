package edu.zju.cims201.spider.grabnews.uggdrj.rule;

import java.util.regex.Pattern;

import edu.zju.cims201.spider.commontool.UrlRule;

public class RJUrlRule extends UrlRule{
	protected String exclusion_url = "css|js|jpg|png|gif|swf|pdf|doc|CSS|JS|JPG|PNG|GIF|SWF|PDF|DOC";

	protected String regExForHouzui = "\\.aspx|\\.asp|\\.css|\\.js|\\.jpg|\\.png|\\.gif|\\.swf|\\.pdf|\\.doc|\\.CSS|\\.JS|\\.JPG|\\.PNG|\\.GIF|\\.SWF|\\.PDF|\\.DOC";

	protected String egExuserfulurl = "http://www.uggd.com/news/rjnews/";
	protected String newspageurlpattern = "http://www.uggd.com/news/rjnews/\\d{4}-\\d{2}-\\d{2}/\\d{5}.html";
//	protected String newspageurlpattern = "http://www.uggd.com/news/rjnews/2012-06-26/12557.html";
	
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

