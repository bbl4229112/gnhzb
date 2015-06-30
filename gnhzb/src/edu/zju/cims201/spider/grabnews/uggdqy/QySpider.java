/**
 * 
 */
package edu.zju.cims201.spider.grabnews.uggdqy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import edu.zju.cims201.spider.commontool.NetHelper;

import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Version;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.Constants;

import edu.zju.cims201.spider.commontool.Parser;
import edu.zju.cims201.spider.commontool.UrlRule;
import edu.zju.cims201.spider.commontool.FileToDisk;
import edu.zju.cims201.spider.grabnews.uggdqy.parser.ParserQy;
import edu.zju.cims201.spider.grabnews.uggdqy.rule.QyUrlRule;

import edu.zju.cims201.spider.heaton.bot.HTTP;
import edu.zju.cims201.spider.heaton.bot.HTTPSocket;
import edu.zju.cims201.spider.heaton.bot.ISpiderReportable;
import edu.zju.cims201.spider.heaton.bot.IWorkloadStorable;
import edu.zju.cims201.spider.heaton.bot.Spider;

/**
 * @author new
 * 
 */
public class QySpider extends Thread implements ISpiderReportable {
	private static final QySpider m_bzwspider = new QySpider();
	
	
	private Log log = LogFactory.getLog(this.getClass().getName());

	protected Spider _spider = null;

	protected static String _hostUrl = "http://www.uggd.com/news/qynews/";
	protected String pageEncode = "GB2312";
//	protected String dir = Constants.dir+"//meida";
//	protected FileToDisk filetodisk = new FileToDisk(dir
//			); 

	protected QyUrlRule rule = new QyUrlRule();
	protected ParserQy parser = new ParserQy();
	private static String spidername = "BzwSpider";

	private static String tablename = "workload";

	private static String urlpattern = "http://www.uggd.com/news/qynews/\\d{4}-\\d{2}-\\d{2}/\\d{5}.html";

//	private static String defaulturl = "http://www.bzw.com.cn/article/class_other.asp?id=89&page=";
	private static String defaulturl = "http://www.uggd.com/news/qynews/";  
	
	private ApplicationContext context = new FileSystemXmlApplicationContext(new String[]{Constants.APPLICATIONCONTEXT});  
	private KnowledgeService kservice=(KnowledgeService)context.getBean("knowledgeServiceImpl");
	private UserService userservice=(UserService)context.getBean("userServiceImpl");
	private KtypeService ktypeservice=(KtypeService)context.getBean("ktypeServiceImpl");
	private QySpider() {
		super();
	}
	public static QySpider getInstance() {
		return m_bzwspider;
	}
	/*
	 * （非 Javadoc）
	 * 
	 * @see com.heaton.bot.ISpiderReportable#completePage(com.heaton.bot.HTTP,
	 *      boolean)
	 */
	synchronized public void completePage(HTTP page, boolean error) {

	}
	public String getPageEncode() {
		return this.pageEncode;
	}
	public void setPageEncode(String pageEncode) {
		this.pageEncode = pageEncode;		
	}
	/**
	 * Method foundExternalLink(String url
	 * 
	 * @param url
	 *            链接地址
	 * @return boolean 返回是否属于定义的外链接类型。
	 */
	synchronized public boolean foundExternalLink(String url) {
		return false;
	}

	/**
	 * Method foundInternalLink(String url)
	 * 
	 * @param url
	 *            链接地址
	 * @return boolean 返回是否属于定义的内链接类型，例如只有后缀名是html、jsp、asp等才是内链接，而后缀是swf、jpg
	 *         的就不属于等。这样就可以过滤掉自己不需要的内容。而对于判断的规则从在rule包 里面的url规则类来判定。
	 */
	synchronized public boolean foundInternalLink(String url) {
		if (rule.isUrlAdd(url)) {
			log.info("Spider found internal link: " + url );
			return true;
		}
		return false;
	}

	/**
	 * Method foundOtherLink(String url)
	 * 
	 * @param url
	 *            链接地址
	 * @return boolean 返回是否属于定义的其他链接类型。
	 */
	synchronized public boolean foundOtherLink(String url) {
		return false;
	}

	/**
	 * Method getRemoveQuery()
	 * 
	 * @return boolean 是否去掉请求中带的查询字符串。例如<b>url</b>为http://www.google.com?lang=zh
	 *         是否把"?lang=zh"去掉
	 */
	synchronized public boolean getRemoveQuery() {
		return false;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.heaton.bot.ISpiderReportable#processPage(com.heaton.bot.HTTP)
	 */
	synchronized public void processPage(HTTP page) {
		// FileToDisk filetodisk = new
		// FileToDisk("E:\\workspace\\getsite\\download");
	}

	public void processPage(HTTP page, String parentUrl) {
		String url = page.getURL();
		if (rule.isNewsPage(url)&&!rule.isNewsPage(parentUrl)) {
			System.out.println("url=============================="+url);
			if (!rule.isExist(page.getURL())) {
				Date time;
				Date publicDate;
				String title, orignkeyword, body, fromSource, summary, publicTime,newstype;
				body = page.getBody();
			
				time = Calendar.getInstance().getTime();// DateFormatUtils.format(Calendar.getInstance().getTime(),"yyyy-MM-dd
														// HH:mm:ss");
                
				// filetodisk.processFile(page);
				parser.setSource(body);

				title = parser.getTitle().trim();
				orignkeyword = parser.getKeyWord();
				fromSource = parser.getFromSource().trim();
				NetHelper nethelp = new NetHelper();
				System.out.println("摘要="+parser.getSummary());
				System.out.println("url="+url);
				System.out.println("标题="+title);

				summary = nethelp.formatHtmlSource(parser.getSummary(),url);
				publicTime = parser.getPublicTime();
				publicDate = parser.getPublicDate();
//				newstype = parser.getNewsType();
				Knowledge k = null;
				try {
					k = (Knowledge) Class.forName("edu.zju.cims201.GOF.hibernate.pojo.News").newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				
				if(orignkeyword!=null&&!orignkeyword.equals("")){
					orignkeyword = orignkeyword.replaceAll("，", ",");
					orignkeyword = orignkeyword.replaceAll("  ", ",");
					orignkeyword = orignkeyword.replaceAll("、", ",");
					orignkeyword = orignkeyword.replaceAll("；", ",");
					orignkeyword = orignkeyword.replaceAll(";", ",");
					String[] keywords = orignkeyword.split(",");
					Set<Keyword> keywordlist = new HashSet<Keyword>();
					for (String keyword : keywords) {
						if (null != keyword && !keyword.equals("")) {
							Keyword keywordT = kservice
									.SearchAndSaveKeyword(keyword.trim());
							keywordlist.add(keywordT);
						}
					}
					k.setKeywords(keywordlist);
				}
				Knowledgetype knowledgetype = kservice.SearchAndSaveKnowledgetype("新闻");
				k.setKnowledgetype(knowledgetype);
				List<Author> authorlist = new ArrayList<Author>();
				Author authorT = kservice.searchAndSaveAuthor(fromSource);
				authorlist.add(authorT);
				k.setKauthors(authorlist);
				SystemUser u = userservice.getUser(new Long(1));
				k.setUploader(u);
				k.setTitlename(title);
				Date uploadtimeT = new Date();
				k.setUploadtime(uploadtimeT);
				k.setIsvisible(true);
				Ktype ktype = ktypeservice.getKtype(new Long(9));
				k.setKtype(ktype);
				k.setStatus("0");
				k.setSecuritylevel("非密");
				
				Version version = new Version();
				version.setVersionTime(uploadtimeT);
				version.setVersionNumber("1.0");
				k.setVersion(version);
				k.setStatus("1");
				
				CommentRecord cr = new CommentRecord();
				cr.setCommentCount(new Long(0));
				cr.setViewCount(new Long(0));
				cr.setRate(new Float(0));
				cr.setDownloadCount(new Long(0));
				k.setCommentrecord(cr);
				
				try {
					PropertyUtils.setProperty(k, "newsbody", body);
					PropertyUtils.setProperty(k, "url", url);
					PropertyUtils.setProperty(k, "parenturl", parentUrl);
					PropertyUtils.setProperty(k, "newscontent", summary);
					PropertyUtils.setProperty(k, "publictime", publicTime);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			
//				News news = new News(title, keyword, time, body, url, parentUrl, summary, publicTime, fromSource, publicDate, newstype);

//				System.out.println("parentUrl = " + parentUrl);
//				System.out.println("url = " + url); 
//				System.out.println("time = " + time); 
//				System.out.println("title = " + title);
//				System.out.println("keyword = " + keyword);
//				System.out.println("from = " + fromSource);
//				System.out.println("publicTime = " + publicTime);
//				System.out.println("summary = " + summary);
				//System.out.println("body = " + body);
				kservice.save(k,null);
				
				

			} else if (!rule.isContain(url, parentUrl)) {
				//如果已经存在这个URL,即这个新闻已经存在，但是这个新闻的parenturl不是这个，那么把这个parentUrl也加进去。
				Knowledge k;
				try {
					k = (Knowledge) Class.forName("edu.zju.cims201.GOF.hibernate.pojo.News").newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				k=rule.findNewsByUrl(url);
				String oldparenturl;
				try {
					oldparenturl = (String)PropertyUtils.getProperty(k, "parenturl");
					PropertyUtils.setProperty(k, "parenturl", oldparenturl+"|"+parentUrl);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}			
				kservice.save(k,null);
//				News news = (News) newsdao.findByUrl(url).get(0);
//				news.setParentUrl(news.getParentUrl() + "|" + parentUrl);
//				newsdao.save(news);
			}

			
			
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.heaton.bot.ISpiderReportable#spiderComplete()
	 */
	synchronized public void spiderComplete() {
		if (log.isInfoEnabled()) {
			log.info(_hostUrl + " done");
		}
		_spider = null;		
	}

	public void init() {
		IWorkloadStorable wl;
		try {
			wl = new edu.zju.cims201.spider.grabnews.uggdqy.SpiderSQLWorkload();
		} catch (Exception e) {
			log.error("Spider for bzw init() : " + e.getMessage());
			return;
		}
		_spider = new edu.zju.cims201.spider.heaton.bot.Spider(this, defaulturl, new HTTPSocket(), 2, wl);
		_spider.setMaxBody(200);
		_spider.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.setProperty("http.proxySet","true");
		//System.setProperty("http.proxyHost", "10.15.43.221");
		//System.setProperty("http.proxyPort", "141");
		System.out.println("hello world");
		QySpider spider = new QySpider();
		spider.init();
		System.out.println("over");
	}
	public boolean isMediaSource(String url) {
		return rule.isMediaSource(url);
	}
	public UrlRule getUrlRule() {
		return this.rule;
	}
	public boolean foundNewsLink(String url, String parenturl) {
		return rule.isNewsPage(url);
	}
	public boolean foundMediaLink(String url, String parenturl) {
		return rule.isMediaSource(url);
	}
	public Parser getParser() {
		return this.parser;
	}	
	
}