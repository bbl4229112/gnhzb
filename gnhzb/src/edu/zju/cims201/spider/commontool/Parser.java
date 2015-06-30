/**
 * 
 */
package edu.zju.cims201.spider.commontool;

import java.util.Date;

/**
 * @author alexadaman
 * 解析器接口类，实现对html文件的信息抽取
 */
public interface Parser {
	 /**
     * get the title of the html page.
     * @param noneed
     * @return the title of the html page 
     */
	public String getTitle();

	/**
	 * 
	 * @return keyWord 返回关键词
	 */
	public String getKeyWord();
    /**
     * 
     * @return summary 返回新闻正式内容
     */
	public String getSummary();
	/**
	 * 
	 * @return 知识的发布时间，为字符类型，用于知识检出时按时间排序 
	 */
	public String getPublicTime();
	
	public String getFromSource();
	
	/**
	 * 
	 * @return 知识的发布时间，为java.util.Date类型，用于知识检出时按时间排序
	 */
	public Date getPublicDate();
	/**
	 * 
	 * @return 新闻类别
	 */	
	public String getNewsType();
	
	public void setSource(String source);
}

