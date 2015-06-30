package edu.zju.cims201.GOF.service.interestmodel;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.hibernate.pojo.Expert;
import edu.zju.cims201.GOF.hibernate.pojo.InterestModel;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.PageDTO;

/**
 * 提供关于兴趣模型相关的服务接口，通过具体实现类来实现相关方法
 * 
 * @author cwd
 */
@Transactional
public interface InterestModelService {
	/**
	 * 更新订阅的栏目
	 * @param interestModel 修改后的兴趣模型
	 * @return 通过返回的String值判断是否操作成功
	 * @author cwd
	 */
	public String updateInterestModelItems(InterestModel interestModel);
	/**
	 * 获取所有订阅栏目知识文档
	 * @param interestModelId 兴趣模型的id
	 * @return 返回所有订阅栏目知识文档
	 * @author cwd
	 */
	public List<MetaKnowledge> getInterestModelKnowledge(Long interestModelId);
	
	/**
	 * 根据用户查到相应兴趣模型，首页兴趣十知识
	 * @param 
	 * @return 
	 * @SystemUser 江丁丁jiangdingding添加   2013-6-22
	 */
	public PageDTO getInterestModelKnowledge(SystemUser user);
	
	/**
	 * 列出用户订阅的栏目内容
	 * @param user 用户	  
	 * @return 通过返回的String值判断是否操作成功
	 * @SystemUser cwd
	 */
	@Transactional(readOnly = true)
	public List<InterestModel> listInterestModelItems(SystemUser user);
	/**
	 * 添加用户订阅的多个栏目
	 * @param user 用户
	 * @param items 多个栏目
	 * @param itemtype 用整数值标定栏目类型
	 * @return 通过返回的String值判断是否操作成功
	 * @SystemUser cwd
	 */
	public String addInterestModelItems(SystemUser user,List<String> itemnames,String itemtype);
	/**
	 * 添加用户订阅的一个栏目
	 * @param user 用户
	 * @param item 一个栏目
	 * @param itemtype 用整数值标定栏目类型
	 * @return 通过返回的String值判断是否操作成功
	 * @SystemUser cwd
	 */
	public String addInterestModelItem(SystemUser user,String itemname,String itemtype,int isparent);
	/**
	 * 删除订阅栏目
	 * @param interestModelId 兴趣模型id	
	 * @return 通过返回的String值判断是否操作成功
	 * @SystemUser cwd
	 */
	public String deleteInterestModelItem(Long interestModelId);	
	/**
	 * 得到订阅者
	 * @param itemname 栏目名字
	 * @param itemtype 用整数值标定栏目类型
	 * @return 通过返回的String值判断是否操作成功
	 * @SystemUser cwd
	*/
	public List<SystemUser> getsubscribers( String itemname,String itemtype);
	/**
	 * 判断树兴趣模型是否已经存在
	 * @param 
	 * @return 
	 * @SystemUser cwd
	 */
	public boolean isTreeInterestModelExist(SystemUser user,String interestItem,String interestItemType,int isParent);
	/**
	 * 判断一般兴趣模型是否已经存在
	 * @param 
	 * @return 
	 * @SystemUser cwd
	 */
	public boolean isInterestModelExist(SystemUser user,String interestItem,String interestItemType);
	/**
	 * 根据id查到相应兴趣模型
	 * @param 
	 * @return 
	 * @SystemUser cwd
	 */
	public InterestModel getInterestModel(Long id);
	/**
	 * 根据用户，类型，类型id查到相应兴趣模型
	 * @param 
	 * @return 
	 * @SystemUser cwd
	 */
	public List<InterestModel> getInterestModels(SystemUser user,String interestItem,String interestItemType);
	
	
	/**
	 * 根据用户，类型，类型id查到相应兴趣模型
	 * @param 
	 * @return 
	 * @SystemUser cwd
	 */
	public InterestModel getCommonInterestModel(SystemUser user,String interestItem,String interestItemType,int isParent);

	 //比较知识的上传时间和兴趣模型的创建时间
    public boolean compareDate(Knowledge knowledge,InterestModel interestmodel);
    
    
    public String deleteAllSubNodeIm(long interestModelId);
	
	//专家信息维护
	public String addExpertItems(SystemUser user,Set<TreeNode> treeNodes,Set<MetaKnowledge> unanswers);
	
	public String deleteExpert(Long expertId);	
	
	public List<Expert> getAllExpert();
	
	public Set<Expert> getTreeExpert(Long treenodeid);
	
	public Expert getExpert(Long expertid);
	
	public String updateExpert(Expert expert);
	
	public boolean isExpertExist(String name);
	
	public Expert getOneExpert(String name);
	
	public List<Expert> searchExperts(String username);
	
	/**
	 * 通过上传入库的知识来加入订阅未读数据并保存Message
	 * @param 
	 * @return 
	 * @SystemUser cwd
	 */
	public void saveMessageAndSubscribeInfo(Knowledge k);



}

