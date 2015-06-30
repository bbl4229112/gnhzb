package edu.zju.cims201.GOF.service.kmap;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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
 * 提供关于知识地图的相关服务接口，由具体的实现类来实现相关的方法

 * 
 * @author hebi
 */

public interface KmapService {
	
	/**
	 * 根据用户的输入从本体文件中搜索本体术语名称，推荐给用户 

	 *  @param  onto 术语名称
	 * 
	 * @author hebi
	 */		
	public  List<TagDTO> getTagsuggest(String tagname,String owlFileName);
    
	public abstract Hashtable<String, String> getOrderrelation();

	public abstract void setOrderrelation(String tag);
	public Map<String,String> getM_edges() ;
	
}
