package edu.zju.cims201.GOF.service.knowledge.keep;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.hibernate.pojo.KeepTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;

import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;

import edu.zju.cims201.GOF.dao.knowledge.KeepTreeNodeDao;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.Constants;

/**
 * 提供收藏相关服务
 * 
 * author panlei
 */

@Transactional(readOnly = true)
@Service
public class KeepServiceImpl implements KeepService {
	@Resource(name = "keepTreeNodeDao")
	private KeepTreeNodeDao keepTreeNodeDao;
	
	@Resource(name = "userServiceImpl")
	private UserService userservice;

	
	public List<KeepTreeNode> listBookmark(SystemUser user) {
		Long id = user.getId();
		String queryString="";
		Class treeNodeClazz = KeepTreeNode.class;
			 queryString="from "+treeNodeClazz.getName()+" as node where node.orderId = "+id+" and  node.keepTreeNodeType != 'keep'";
		Object[] values=null;
		Query query = keepTreeNodeDao.createQuery(queryString, values);
		Long lo = (long)1;
		KeepTreeNode ktn = getKeepTreeNode(lo);
		List<KeepTreeNode> list = query.list();
		list.add(0, ktn);
		return list;
	}
	public String save(KeepTreeNode ktn) {
		ktn.setKeepTime(new Date());
		SystemUser user = userservice.getUser();
		ktn.setOrderId(user.getId());
		keepTreeNodeDao.save(ktn);
		keepTreeNodeDao.flush();
		return null;
	}
	public KeepTreeNode getKeepTreeNode(Long nodeId) {
		
		KeepTreeNode ktn = keepTreeNodeDao.findUniqueBy("id", nodeId);
		return ktn;
	}
	
	public PageDTO searchKeepKnowledge(Long treenodeid,boolean b) {
		if(treenodeid == 1){
			
		}
		PageDTO page = new PageDTO();
		
		int index = 0;
		int size = Constants.rawPrepage;
		page.setFirstindex(index);
		page.setPagesize(size);
		 
		//判断是否是收藏，如果是收藏，则将本收藏加入，如果不是，则列出收藏夹下所有收藏(夹)
		List<KeepTreeNode> keeptreenodelist = getKeepTreeNodeListByBookmarkId(treenodeid);
		List<KeepTreeNode> KeepTreeNodeList = getKeepTreeNodeListByBookmarkId((long)0);
		SystemUser user = userservice.getUser();
		Long userid = user.getId();
		if(b){
			KeepTreeNode ktn = getKeepTreeNode(treenodeid);
			KeepTreeNodeList.add(ktn);
			
		}else{
			int length = keeptreenodelist.size();
			for(int i = 0;i<length;i++){
				KeepTreeNode ktn = keeptreenodelist.get(i);
				if(ktn.getKeepTreeNodeType().equals("keep")){
					if(ktn.getOrderId().equals(userid)){
						KeepTreeNodeList.add(ktn);
					}
				}
				//panlei 下面else，是如果是子收藏夹，那么将子收藏夹下的收藏也加入进来
//				else {
//					KeepTreeNodeList = listAllKeeps(ktn.getId(), KeepTreeNodeList);	
//				}
			}
			
		}
		page.setTotalPage(KeepTreeNodeList.size());
		
		page.setData(KeepTreeNodeList);
		page.setTotal(KeepTreeNodeList.size());
		return page;
	}
	
	public List<KeepTreeNode> getKeepTreeNodeListByBookmarkId(Long bookmarkid) {
		return keepTreeNodeDao.createQuery(
				"from KeepTreeNode o where o.parentId = "+bookmarkid).list();
		
	}
	
	public KeepTreeNode getKeepTreeNodeByKnowldedgeId(Long knowledgeId) {
		
		KeepTreeNode ktn = keepTreeNodeDao.findUniqueBy("knowledgeId", knowledgeId);
		return ktn;
	}
	
	public boolean keepExist(Long knowledgeId) {
		boolean b = false;
		SystemUser user = userservice.getUser();
		Long userid = user.getId();
			List<KeepTreeNode> ktnlist = keepTreeNodeDao.findBy("knowledgeId", knowledgeId);
			
		if(ktnlist.size()>=1){
			for(int y = 0;y<ktnlist.size();y++){
				if(ktnlist.get(y).getOrderId().equals(userid)){
					b = true;
					break;
				}
			}
		}
		
		return b;
	}
	
	public List<KeepTreeNode> listAllKeeps(Long id,List<KeepTreeNode> list) {
		
		List<KeepTreeNode> templist = getKeepTreeNodeListByBookmarkId(id);
		int length = templist.size();
		
		for(int i = 0;i<length;i++){
			KeepTreeNode ktn = templist.get(i);
			if(ktn.getKeepTreeNodeType().equals("keep")){
				list.add(ktn);
			}else{
				listAllKeeps(ktn.getId(),list);
			}
			
		}
		
		
		return list;
	}
	
	public void deleteKeep(Long treenodeid) {
		keepTreeNodeDao.delete(getKeepTreeNode(treenodeid));
		keepTreeNodeDao.flush();
	}
	/* (non-Javadoc)
	 * @see edu.zju.cims201.GOF.service.knowledge.keep.KeepService#getKeepTreeNodesByKnowledgeId(java.lang.Long)
	 */
	public List<KeepTreeNode> getKeepTreeNodesByKnowledgeId(Long knowledgeId) {
		
		return keepTreeNodeDao.createQuery(
				"from KeepTreeNode o where o.knowledgeId = "+knowledgeId).list();
		
	}
	public void deleteAllKeepsByKnowledge(MetaKnowledge mk) {
		List<KeepTreeNode> kts = this.keepTreeNodeDao.findBy("knowledgeId", mk.getId());
		for(KeepTreeNode ktn : kts){
			this.keepTreeNodeDao.delete(ktn);			
		}
	}

}

