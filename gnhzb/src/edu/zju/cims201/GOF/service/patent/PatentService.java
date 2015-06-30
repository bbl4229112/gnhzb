package edu.zju.cims201.GOF.service.patent;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import edu.zju.cims201.GOF.hibernate.pojo.Patent;
import edu.zju.cims201.GOF.rs.dto.PageDTO;

public interface PatentService {

	/**
	 * 通过专利申请号查找专利列表
	 */
	public List findByAppCode(String patentid);

	/**
	 * 保存专利
	 * @param instance
	 */
	public void save(Patent instance);
	
	/**
	 * 解析中国专利
	 */
	public Patent parsercn(String content,String urlstr, String keyword)throws Exception;
	
	/**
	 * 解析美国专利
	 */
	public Patent parserus(String content,String urlstr)throws Exception;
	
	/**
	 * 解析美国专利（第二次）
	 */
	public Patent parserus(String content,String patentname, String patentid, String urlstr, String key_word)throws Exception;
	
	/**
	 * 下载美国专利
	 */
	public void downloadUSPatent(String url, String savepath, String poweredpagenumber) throws Exception;
	
	/**
	 * 查找存于数据库中的专利
	 */
	public PageDTO listPatentInDB(HashMap<String,String> propertyValues) throws Exception;
	
	/**
	 * 转换flash
	 * @param filename
	 * @param response
	 */
	public void flashconvert(String filename,HttpServletResponse response);

	
	
}
