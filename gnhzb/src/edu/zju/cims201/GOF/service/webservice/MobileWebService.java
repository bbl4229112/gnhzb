package edu.zju.cims201.GOF.service.webservice;

public interface MobileWebService {

	
	/**
	 * 验证用户方法,返回用户
	 * @author 江丁丁 2013-6-28
	 */
	public String login_kms(String loginInfo); 
	
	/**
	 * 获取兴趣知识列表，返回封装成json
	 * @author 江丁丁 2013-6-29
	 */
	public String getInterestModelKnowledge(Long userId);
	
	/**
	 * 获取知识的详细信息
	 * @author 江丁丁 2013-7-1
	 */
	public String getKnowledgeDetail(String kId)throws Exception;
	
	/**
	 * 知识全文搜索
	 * @author 江丁丁 2013-7-3
	 */
	public String fullTextSearch(String searchInfo);
	
	/**
	 * 我的消息
	 * @author 江丁丁 2013-7-4
	 */
	public String myMessage(Long userId) throws Exception;
	
	/**
	 * 我的名片
	 * @author 江丁丁 2013-7-4
	 */
	public String myInfoCard(Long userId);
	
	/**
	 * 保存知识（上传、博文、问题）
	 * @author 江丁丁 2013-7-4
	 */
	public void simplesave(String kProperties); 
	
	/**
	 * 已订阅的兴趣
	 */
	public String listinterest(Long userId);
	
	/**
	 * 知识订阅-关键词订阅
	 * @author 江丁丁 2013-7-4
	 */
	public String savekeyword(String keywordname);
	/**
	 * 知识订阅-上传者订阅
	 * @author 江丁丁 2013-7-4
	 */
	public String saveuploader(String uploadername);
	
}
