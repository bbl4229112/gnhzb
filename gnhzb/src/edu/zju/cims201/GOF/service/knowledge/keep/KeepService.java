package edu.zju.cims201.GOF.service.knowledge.keep;

import java.util.List;
import edu.zju.cims201.GOF.hibernate.pojo.KeepTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.PageDTO;

/**
 * 提供关于收藏的服务接口，由具体的实现类来实现接口定义的方法
 * 
 * @author panlei
 */

public interface KeepService {
	
    /**
     * @author panlei
     * 根据id，拿到该id对应的收藏夹下所有的收藏，包括子收藏夹中的收藏，如果该id对应一个收藏，就列出该收藏即可。
     * 
     * */
    public List<KeepTreeNode> listAllKeeps(Long id,List<KeepTreeNode> list);
    /**
     * @author panlei
     * 根据知识id判断该知识是否被收藏
     * */
    public boolean keepExist(Long knowledgeId);
    /**
     * @author panlei
     * 根据knowledgeId拿到该知识对应的收藏
     * */
    public KeepTreeNode getKeepTreeNodeByKnowldedgeId(Long knowledgeId);
    /**
     * @author pl 
     * 列出当前用户所有收藏夹
     * */
    public List<KeepTreeNode> listBookmark(SystemUser user);
    /**
     * @author pl
     * 保存一个收藏
     * */
    public String save(KeepTreeNode ktn);
    /**
     * @author pl
     * 列出该收藏夹及其子收藏夹
     * */
    public KeepTreeNode getKeepTreeNode(Long nodeId);
    /**
     * @author panlei
     * 根据点击的收藏夹的id，以及是否是收藏，来查询该收藏夹中所有收藏
     * */
    public PageDTO searchKeepKnowledge(Long treenodeid,boolean b);
    /**
     * @author panlei
     * 根据收藏夹id拿到从数据库中拿到该收藏夹下所有的收藏
     * 
     * */
    public List<KeepTreeNode> getKeepTreeNodeListByBookmarkId(Long bookmarkid);
	/**
	 * @author panlei
	 * 根据收藏id删除某一条收藏
	 * @param treenodeid
	 */
	public void deleteKeep(Long treenodeid);
	/**
	 * 根据MetaKnowledge删除该知识涉及的所有收藏
	 * @author panlei
	 * @param metaknowledge
	 * 
	 * */
	public void deleteAllKeepsByKnowledge(MetaKnowledge mk);
	/**
	 * @author panlei
	 * 根据知识id，拿到该知识对应的收藏list
	 * @param knowledgeId
	 * @return
	 */
	public List<KeepTreeNode> getKeepTreeNodesByKnowledgeId(Long knowledgeId);
}
