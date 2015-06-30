package edu.zju.cims201.GOF.service.knowledge.tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.dao.knowledge.TagDao;
import edu.zju.cims201.GOF.dao.knowledge.UserKnowledgeTagDao;

import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Tag;
import edu.zju.cims201.GOF.hibernate.pojo.UserKnowledgeTag;

import edu.zju.cims201.GOF.rs.dto.TagDTO;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.util.Constants;

@Service
@Transactional
public class TagServiceImpl implements TagService {

	@Resource(name = "metaKnowledgeDao")
	private MetaKnowledgeDao metaKnowledgedao;
	@Resource(name = "tagDao")
	private TagDao tagdao;
	@Resource(name = "userKnowledgeTagDao")
	private UserKnowledgeTagDao uktdao;


	public String addTag(Tag tag) {
		tagdao.save(tag);
        tagdao.flush();
		return "1";
	}
    
	public String addUserKnowledgeTag (UserKnowledgeTag ukt)
	{
		uktdao.save(ukt);
		uktdao.flush();
		return "1";
		
	}
	
	
	public String deleteTag2Knowledge(UserKnowledgeTag ukt)
	{
		uktdao.delete(ukt);
		return "1";
		
	}


	@Transactional(readOnly = true)
	public List<Tag> listPopularTags(Long  knowledge_id) {
		List<Tag> uktlist = new ArrayList<Tag>();		
		MetaKnowledge knowledge = metaKnowledgedao.findUniqueBy("id",knowledge_id);
        uktlist=listPopularTags(knowledge);
		return uktlist;
	}	
	
//
//	@Transactional(readOnly = true)
//	public List<Tag> listIndividualTags(Long knowledge_id, Long user_id) {
//		List<Tag> listit = new ArrayList<Tag>();
//		MetaKnowledge knowledge = metaKnowledgedao.findUniqueBy("id",knowledge_id);
//		Set<UserKnowledgeTag> userKnowledgeTags = knowledge.getUserKnowledgeTags();
//		for (UserKnowledgeTag userKnowledgeTag : userKnowledgeTags) {
//			if(userKnowledgeTag.getTager().getId().equals(user_id)) {
//				System.out.println("个人标签的名字是"+userKnowledgeTag.getTag().getTagName());
//				listit.add(userKnowledgeTag.getTag());					
//			}			
//		}		
//		return listit;
//		
//	}
	@Transactional(readOnly = true)
	public List<Tag> listPopularTags(MetaKnowledge knowledge) {
		if(null==knowledge)
			return null;
		List<Tag> populartaglist = new ArrayList<Tag>();
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("knowledge", knowledge);
		String hql = "select o from Tag o, UserKnowledgeTag ukt where ukt.knowledge= :knowledge and o.id= ukt.tag.id group by o.id, o.tagName order by count(o.id) desc";
		populartaglist=tagdao.createQuery(hql, params).setMaxResults(Constants.POPTAG_LIMIT).list();	

		return populartaglist;	
	}
	
	public List<Tag> recommentKTags(MetaKnowledge knowledge,String tagname) {
		if(null==knowledge||null==tagname||tagname.trim().equals(""))
			return null;
		List<Tag> recommenttaglist = new ArrayList<Tag>();
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("knowledge", knowledge);
		params.put("tagname", "%"+tagname+"%");
		String hql = "select o from Tag o, UserKnowledgeTag ukt where ukt.knowledge= :knowledge and o.id= ukt.tag.id and p.tagName like :tagname group by o.id, o.tagName order by count(o.id) desc";
		recommenttaglist=tagdao.createQuery(hql, params).setMaxResults(Constants.RECOMMENTTAG_LIMIT).list();	

		return recommenttaglist;	
	}
	
	
	@Transactional(readOnly = true)
	public List<Tag> listIndividualTags(MetaKnowledge knowledge, SystemUser user) {
		
		if(null==knowledge||null==user)
			return null;
		List<Tag> individualtaglist = new ArrayList<Tag>();	
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("knowledge", knowledge);
		params.put("tager", user);
		String hql = "Select o from Tag o, UserKnowledgeTag ukt where ukt.knowledge= :knowledge and ukt.tager= :tager and o.id= ukt.tag.id order by ukt.id";
		individualtaglist=tagdao.createQuery(hql, params).list();	
	   
		return individualtaglist;
	}
	//获得标签云的内容
	@Transactional(readOnly = true)
	public List<TagDTO> listTagCloud() {
		
		List<Object[]> populartaglist = new ArrayList<Object[]>();
		Map<String,Object> params = new HashMap<String ,Object>();
		List<TagDTO> taglist=new ArrayList<TagDTO>();
		//String hql = "select count(o.id), o.id, o.name from Tag o, UserKnowledgeTag ukt where  o.id= ukt.tag.id group by o.id, ukt.knowledge order by count(o.id) desc";
		String hql="select count(a.id),a.id ,a.tag_name from caltks.caltks_tag a, (select b.tagid tagid from caltks.caltks_user_knowledge_tag b group by b.tagid,b.knowledgeid) c where c.tagid=a.id group by a.id ,a.tag_name";
		populartaglist=tagdao.getSessionFactory().getCurrentSession().createSQLQuery(hql).setMaxResults(100).list();		
		for (Object[] tag : populartaglist) {
		TagDTO tagdto=new TagDTO();
		tagdto.setTagnum(Integer.parseInt(tag[0].toString()));
		tagdto.setId(new Long(tag[1].toString()));
		tagdto.setTagName(tag[2].toString());
	
		
		taglist.add(tagdto);
		}
		
		return taglist;	
	}
	
    //通过标签id获得标签对象
	@Transactional(readOnly = true)
	public Tag getTag(Long id) {
		return tagdao.findUniqueBy("id", id);
	}
	//通过标签名获得标签对象
	@Transactional(readOnly = true)
	public Tag getTag(String  tagName) {
		return tagdao.findUniqueBy("tagName", tagName);
	}

    //通过用户标签id获得用户标签
	@Transactional(readOnly = true)
	public UserKnowledgeTag getUserKnowledgeTag(Long id) {
	   return uktdao.findUniqueBy("id", id); 
	}
	
	  //通过用户,知识，和标签 获得用户标签
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public UserKnowledgeTag getUserKnowledgeTag(MetaKnowledge knowledge,Tag tag,SystemUser user) {
		
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("knowledge", knowledge);
		params.put("tager", user);
		params.put("tag", tag);
		String hql = "Select o from UserKnowledgeTag o where o.knowledge= :knowledge and o.tager= :tager and o.tag= :tag";
		List<UserKnowledgeTag> individualtaglist = new ArrayList<UserKnowledgeTag>();	
		individualtaglist=tagdao.createQuery(hql, params).list();		
		if(individualtaglist.isEmpty())
			return null;
		else 
			return individualtaglist.get(0);
			
//	   return uktdao.findUniqueBy("id", id); 
	}
	
	
	
	//通过标签名存储并获得一个标签，如果标签名已经存在则不再存储，直接和多
	public Tag SearchAndSaveTag(String tagname) {
		if (isTagExist(tagname))
			return getTag(tagname);
		else
			return addTag(tagname);
	}

	private Tag addTag(String tagName) {
		Tag tag = new Tag();
		tag.setTagName(tagName);
		try {
			tagdao.save(tag);
			tagdao.flush();
		} catch (Exception e) {
		
			throw new ServiceException("标签存储错误");
		}

		return tag;
	}
	@Transactional(readOnly = true)
	public Boolean isTagExist(String tagname) {
		
		Tag tag = tagdao.findUniqueBy("tagName", tagname);
		if (null == tag)
			return false;
		return true;
	}
	
	
	public void SearchAndSaveUserKnowledgeTag(UserKnowledgeTag ukt,String tagnames)
	{   MetaKnowledge k=ukt.getKnowledge();
	    SystemUser u=ukt.getTager();
	    List<UserKnowledgeTag> uktlist2delete=uktdao.findBy("tager", u);
	    for (UserKnowledgeTag userKnowledgeTag : uktlist2delete) {
	    	if(userKnowledgeTag.getKnowledge().getId()==k.getId())
	    	  uktdao.delete(userKnowledgeTag);
		}
	    uktdao.flush();
		if(null!=k||null!=u||null!=tagnames||!tagnames.trim().equals(""))
		{
			tagnames=tagnames.replaceAll("  ", " ")	;	
		tagnames=tagnames.replaceAll("，", ",")	;
		tagnames=tagnames.replaceAll(" ", ",")	;
		String[]tagname=tagnames.split(",");
		for (int i = 0; i < tagname.length; i++) {
			
		
		Tag t=new Tag();
			t=SearchAndSaveTag(tagname[i]);
		
	//	Map<String,Object> params = new HashMap<String ,Object>();
	//	params.put("knowledge", k);
	//	params.put("tager", u);
	//	params.put("tag", t);
	//	String hql = "from UserKnowledegTag o where o.knowledge= :knowledge and o.tager= :tager and o.tag= :tag";
		
		//List<UserKnowledgeTag> uktlist=uktdao.createQuery(hql, params).list();
	
		//if(uktlist.size()==0)
		//{
		UserKnowledgeTag uktnew=new UserKnowledgeTag();	
	    Date tagtime=new Date();
	    uktnew.setKnowledge(k);
	    uktnew.setTager(u);
	    uktnew.setTag(t);
	    uktnew.setTagTime(tagtime);
	    addUserKnowledgeTag(uktnew);
	
		//}
		}
		}
		
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

