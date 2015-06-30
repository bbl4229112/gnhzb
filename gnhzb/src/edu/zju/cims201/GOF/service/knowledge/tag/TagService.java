package edu.zju.cims201.GOF.service.knowledge.tag;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Tag;
import edu.zju.cims201.GOF.hibernate.pojo.UserKnowledgeTag;
import edu.zju.cims201.GOF.rs.dto.TagDTO;
import edu.zju.cims201.GOF.service.ServiceException;

/**
 * 提供关于标签的相关服务接口，由具体的实现类来实现相关的方法

 * 
 * @author cwd
 */

public interface TagService {
	/**
	 * 获得知识标签
	 * @param id 标签id
	 * @return {@link Tag} 返回用户需要的标签
	 * @author hebi
	 */
	public Tag getTag(Long id);
	/**
	 * 获得用户标签
	 * @param id 用户知识标签id
	 * @return {@link UserKnowledgeTag} 返回用户需要的用户知识标签
	 * @author hebi
	 */
	public UserKnowledgeTag getUserKnowledgeTag(Long id);
	/**
	 * 获得用户标签
	 * @param knowledge 知识
	 * @param tag 标签
	 * @param user 用户
	 * @return {@link UserKnowledgeTag} 返回用户需要的用户知识标签
	 * @author hebi
	 */
	public UserKnowledgeTag getUserKnowledgeTag(MetaKnowledge knowledge,Tag tag,SystemUser user) ;
	/**
	 * 添加知识标签
	 * @param tag 输入的标签
	 * @return 通过返回的String值判断是否操作成功
	 * @author cwd
	 */
	public String addTag(Tag tag);
	/**
	 * 添加用户标签
	 * @param {@link UserKnowledgeTag} ukt 输入的用户标签
	 * @return 通过返回的String值判断是否操作成功
	 * @author cwd
	 */
	
	public String addUserKnowledgeTag (UserKnowledgeTag ukt);
	/**
	 * 删除用户标签
	
	 * @param  {@link UserKnowledgeTag} ukt 用户标签
	 * @return 通过返回的String值判断是否操作成功
	 * @author cwd
	 */
	public String deleteTag2Knowledge(UserKnowledgeTag ukt);
//	/**
//	 * 删除标签与知识文档的关联
//	 * @param user_id 用户的id
//	 * @param knowledge_id 知识文档的id
//	 * @param tag_id 标签的id
//	 * @return 通过返回的String值判断是否操作成功
//	 * @author cwd
//	 */
//	public String deleteTag2Knowledge(Long user_id,Long knowledge_id,Long tag_id);
	/**
	 * 列出知识文档相关联的大众标签
	 * @param knowledge 知识文档
	 * @return {@link UserKnowledgeTag}  返回与知识文档相关联的大众标签
	 * @author cwd
	 */
	public List<Tag> listPopularTags(MetaKnowledge knowledge);
	/**
	 * 列出知识文档相关联的大众标签
	 * @param knowledge_id 知识文档的id
	 * @return 返回与知识文档相关联的大众标签
	 * @author cwd
	 */	
	public List<Tag> recommentKTags(MetaKnowledge knowledge,String tagname);
	/**
	 * 列出知识文档相关联的大众标签
	 * @param knowledge_id 知识文档的id
	 * @return 返回与知识文档相关联的大众标签
	 * @author cwd
	 */	
	public List<Tag> listPopularTags(Long  knowledge_id);
	/**
	 * 列出与知识文档相关的个人标签
	 * @param knowledge 知识文档
	 * @param user 用户
	 * @return  {@link Tag} 返回与知识文档相关的个人标签
	 * @author cwd
	 */	
	
	public List<Tag> listIndividualTags(MetaKnowledge knowledge,SystemUser user);
//	/**
//	 * 列出与知识文档相关的个人标签
//	 * @param knowledge_id 知识文档的id
//	 * @param user_id 用户的id
//	 * @return {@link UserKnowledgeTag}返回与知识文档相关的个人标签
//	 * @author cwd
//	 */		
//	public List<UserKnowledgeTag> listIndividualTags(Long knowledge_id, Long user_id);
//
//	
	/**
	 * 通过标签名获得标签对象
	 * @param tagName 标签名称
	 * @return {@link Tag} 返回用户需要的标签
	 * @author hebi
	 */	
	
	public Tag getTag(String  tagName) ;


	/**
	 * 通过标签名存储并获得一个标签，如果标签名已经存在则不再存储，直接获得
	 * @param tagName 标签名称
	 * @return {@link Tag} 返回用户需要的标签
	 * @author hebi
	 */	
	
	
	public Tag SearchAndSaveTag(String tagname);
    
	
	/**
	 * 通过一个用户标签对象查询是否已经存在这个用户标签，如果标签名已经存在则不再存储，直接获得，不存则则需要进行存储
	 * 要求传入对象中必须包换对象属性 {@link MetaKnowledge} 和 {@link SystemUser} tager,否则返回null 
	 * @param  {@link UserKnowledgeTag} 用户标签名称
	 *  @param  tagname  标签名称
	 * 
	 * @author hebi
	 */	
	
	
	public void SearchAndSaveUserKnowledgeTag(UserKnowledgeTag ukt ,String tagname);
	
	//获得标签云的内容

	public  List<TagDTO> listTagCloud();
	
}
