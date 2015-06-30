package edu.zju.cims201.GOF.service.tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.dao.tree.CategoryTreeNodeDao;
import edu.zju.cims201.GOF.dao.tree.DomainTreeNodeDao;
import edu.zju.cims201.GOF.dao.tree.RoleTreeNodeDao;
import edu.zju.cims201.GOF.dao.tree.TreeNodeDao;
import edu.zju.cims201.GOF.dao.tree.UserRoleAssociationDao;
import edu.zju.cims201.GOF.dao.user.SystemUserDao;
import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Tree;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.UserRoleAssociation;
import edu.zju.cims201.GOF.util.Constants;

@Service
@Transactional (readOnly = true)
public class TreeServiceImpl implements TreeService {
	
	private DomainTreeNodeDao domainTreeNodeDao;
	private CategoryTreeNodeDao categoryTreeNodeDao;
	private RoleTreeNodeDao roleTreeNodeDao;
	private TreeNodeDao treeNodeDao;
	private TreeNodeCodeManager codeManager;
	private MetaKnowledgeDao metaKnowledgeDao;
	private SystemUserDao systemUserDao;
	private UserRoleAssociationDao associationDao;
	
	
	@Transactional
	public String addKnowledgeRelation(TreeNode treeNode, MetaKnowledge knowledge) {
		
		if(treeNode instanceof DomainTreeNode){
			DomainTreeNode domainTreeNode=(DomainTreeNode)treeNode;
			domainTreeNode.getKnowledges().add(knowledge);
			domainTreeNodeDao.save(domainTreeNode);
			domainTreeNodeDao.flush();
			
		}else if(treeNode instanceof CategoryTreeNode){
			CategoryTreeNode categoryTreeNode=(CategoryTreeNode)treeNode;
			categoryTreeNode.getKnowledges().add(knowledge);
			categoryTreeNodeDao.save(categoryTreeNode);
			categoryTreeNodeDao.flush();	
		}
		return null;
	}
	@Transactional
	public String addTree(TreeNode parentTreeNode, TreeNode subRootTreeNode) {
		if(!parentTreeNode.getClass().equals(subRootTreeNode.getClass()))
			return "-1";
		parentTreeNode.getSubNodes().add(subRootTreeNode);
		String subTreeNodeCode=null;
		try {
			subTreeNodeCode = codeManager.genrateCodeByParent(parentTreeNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		subRootTreeNode.setCode(subTreeNodeCode);
		treeNodeDao.save(parentTreeNode);
		codeManager.updateChildNodeCodes(subRootTreeNode,treeNodeDao);
		treeNodeDao.save(subRootTreeNode);
		treeNodeDao.flush();
		return null;
	}
	
	@Transactional
	public String addUserRelation(RoleTreeNode roleTreeNode, SystemUser user) {
		roleTreeNode.getUsers().add(user);
		roleTreeNodeDao.save(roleTreeNode);
		roleTreeNodeDao.flush();
		return null;
	}
	@Transactional
	public String deleteKnowledgeRelation(TreeNode treeNode, MetaKnowledge knowledge) {
		if(treeNode instanceof DomainTreeNode){
			DomainTreeNode domainTreeNode=(DomainTreeNode)treeNode;
			Set<MetaKnowledge> knowledges=domainTreeNode.getKnowledges();
			if(knowledges.contains(knowledge))
				knowledges.remove(knowledge);
			domainTreeNodeDao.save(domainTreeNode);
			domainTreeNodeDao.flush();
			
		}else if(treeNode instanceof CategoryTreeNode){
			CategoryTreeNode categoryTreeNode=(CategoryTreeNode)treeNode;
			Set<MetaKnowledge> knowledges=categoryTreeNode.getKnowledges();
			if(knowledges.contains(knowledge)){
				knowledges.remove(knowledge);
			}
				
			categoryTreeNodeDao.save(categoryTreeNode);
			categoryTreeNodeDao.flush();	
		}
		return null;
	}
	@Transactional
	public String deleteTree(TreeNode rootNode) {
		treeNodeDao.delete(rootNode);
		return null;
	}
	@Transactional
	public String deleteUserRelation(RoleTreeNode roleTreeNode, Set<SystemUser> dusers) {
		Set<SystemUser> users=roleTreeNode.getUsers();
		for(SystemUser user:dusers){
			if(users.contains(user))
				users.remove(user);
		}
		roleTreeNodeDao.save(roleTreeNode);
		roleTreeNodeDao.flush();
		return null;
	}

	public TreeNode getTreeNode(Long treeNodeId) {
		if(treeNodeId==null){
			return null;
		}
		return treeNodeDao.findUniqueBy("id", treeNodeId);
	}

	public TreeNode getTreeNode(String treeCode) {
		
		return treeNodeDao.findUniqueBy("code", treeCode);
	}

	public Set<AdminUser> listAdmins(TreeNode treeNode) {
		Set<AdminUser> admins=new HashSet<AdminUser>();
		Set<AdminPrivilegeTriple> adminPrivilegeTriples=treeNode.getAdminPrivilegeTriples();
		for(AdminPrivilegeTriple adminPrivilegeTriple:adminPrivilegeTriples){
			AdminUser admin=adminPrivilegeTriple.getAdmin();
			admins.add(admin);
		}
		
		return admins;
	}

	public List<TreeNode> listBelongingCTreeNodes(MetaKnowledge knowledge) {
		Criteria criteria=categoryTreeNodeDao.getSession().createCriteria(CategoryTreeNode.class).
			createCriteria("knowledges").add(Restrictions.idEq(knowledge.getId()));
		return criteria.list();
	}

	public Page<MetaKnowledge> listCDTreeKnowledges(TreeNode treeNode, boolean listSubTree, Page<MetaKnowledge> page) {
		if(!listSubTree){
			String queryString=" select knowledge  from "+treeNode.getClass().getName()+" as node,MetaKnowledge as knowledge where node.id=? and knowledge in elements(node.knowledges)";
			
			Page<MetaKnowledge> newPage=metaKnowledgeDao.findPage(page, queryString,treeNode.getId());
			return newPage;
		}
		List<TreeNode> deepSubNotes=this.listDeepSubTreeNodes(treeNode.getCode());
		String inString="'"+treeNode.getId()+"', ";
		for(TreeNode node:deepSubNotes){
			inString+="'"+node.getId()+"', ";
		}
		inString=inString.substring(0, inString.length()-2);
		String queryString=" select knowledge  from "+treeNode.getClass().getName()+" as node,MetaKnowledge as knowledge where node.id in ("+inString+") and knowledge in elements(node.knowledges)";
		Page<MetaKnowledge> newPage=metaKnowledgeDao.findPage(page, queryString);
		return newPage;
	
	}

	public List<TreeNode> listDeepSubTreeNodes(String treeCode) {
		String queryString="from "+TreeNode.class.getName()+" as node where node.code like '"+treeCode+"-%'";
		Query query = treeNodeDao.createQuery(queryString);
		return query.list();
	}

	public Page<UserRoleAssociation> listRTreeUsers(RoleTreeNode roleTreeNode, boolean listSubTree, Page<UserRoleAssociation> page) {
		if(!listSubTree){
			//String queryString=" select user  from  RoleTreeNode as node,SystemUser as user where node.id=? and user in elements(node.users)";
			//String queryString=" select association  from  RoleTreeNode as node,UserRoleAssociation as association  where node.id=? and association.user in elements(node.users)";
			String queryString=" select association  from UserRoleAssociation as association  where  association.pk.roleNodeId=? order by association.orderId" ;
			Page<UserRoleAssociation> newPage=associationDao.findPage(page, queryString,roleTreeNode.getId());
			return newPage;
		}
		List<TreeNode> deepSubNotes=this.listDeepSubTreeNodes(roleTreeNode.getCode());
		String inString="'"+roleTreeNode.getId()+"', ";
		for(TreeNode node:deepSubNotes){
			inString+="'"+node.getId()+"', ";
		}
		inString=inString.substring(0, inString.length()-2);
		//String queryString=" select user  from  RoleTreeNode as node,SystemUser as user where node.id in ("+inString+") and user in elements(node.users)";
		String queryString=" select association  from  UserRoleAssociation as association where association.pk.roleNodeId in ("+inString+") order by association.orderId";
		Page<UserRoleAssociation> newPage=associationDao.findPage(page, queryString);
		return newPage;
	}

	public List<TreeNode> listRootTreeNodes(Class treeNodeClazz,boolean disableInte) {
		String queryString="";
		if(disableInte)
			 queryString="from "+treeNodeClazz.getName()+" as node where node.parentId is null and node.id!="+Constants.DISABLEINTEDOMAINID+" and node.id!="+Constants.DANDIANDOMAINID;
		else
			queryString="from "+treeNodeClazz.getName()+" as node where node.parentId is null";
		
		Object[] values=null;
		Query query = treeNodeDao.createQuery(queryString, values);
		return query.list();
	}

	public Set<TreeNode> listSubTreeNodes(TreeNode parentNode) {
		Set<TreeNode> children=parentNode.getSubNodes();
		return children;
	}
	@Transactional
	public String saveNode(TreeNode parentNode, TreeNode addedNode) {
		if(addedNode.getCode()==null&&parentNode!=null){
			try {
				addedNode.setCode(codeManager.genrateCodeByParent(parentNode));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		if(parentNode==null){
			Class clazz=addedNode.getClass();
			addedNode.setCode(codeManager.genrateTreeRootCode(clazz));
			treeNodeDao.save(addedNode);
			treeNodeDao.flush();
			return "root node";
		}	
		if(!parentNode.getClass().equals(addedNode.getClass()))
			return "-1";
		Set<TreeNode> children=parentNode.getSubNodes();
		children.add(addedNode);
		treeNodeDao.save(parentNode);
		treeNodeDao.flush();
		return null;
	}
	@Transactional
	public String updateNodeInfo(TreeNode treeNode) {
		
		treeNodeDao.save(treeNode);
		treeNodeDao.flush();
		return null;
	}
	
	
	

	public CategoryTreeNodeDao getCategoryTreeNodeDao() {
		return categoryTreeNodeDao;
	}
	@Autowired
	public void setCategoryTreeNodeDao(CategoryTreeNodeDao categoryTreeNodeDao) {
		this.categoryTreeNodeDao = categoryTreeNodeDao;
	}

	public DomainTreeNodeDao getDomainTreeNodeDao() {
		return domainTreeNodeDao;
	}
	@Autowired
	public void setDomainTreeNodeDao(DomainTreeNodeDao domainTreeNodeDao) {
		this.domainTreeNodeDao = domainTreeNodeDao;
	}

	public RoleTreeNodeDao getRoleTreeNodeDao() {
		return roleTreeNodeDao;
	}
	
	@Autowired
	public void setRoleTreeNodeDao(RoleTreeNodeDao roleTreeNodeDao) {
		this.roleTreeNodeDao = roleTreeNodeDao;
	}

	public TreeNodeDao getTreeNodeDao() {
		return treeNodeDao;
	}
	@Autowired
	public void setTreeNodeDao(TreeNodeDao treeNodeDao) {
		this.treeNodeDao = treeNodeDao;
	}

	public TreeNodeCodeManager getCodeManager() {
		return codeManager;
	}
	@Autowired
	public void setCodeManager(TreeNodeCodeManager codeManager) {
		this.codeManager = codeManager;
	}

	public MetaKnowledgeDao getMetaKnowledgeDao() {
		return metaKnowledgeDao;
	}
	@Autowired
	public void setMetaKnowledgeDao(MetaKnowledgeDao metaKnowledgeDao) {
		this.metaKnowledgeDao = metaKnowledgeDao;
	}

	public SystemUserDao getSystemUserDao() {
		return systemUserDao;
	}
	@Autowired
	public void setSystemUserDao(SystemUserDao systemUserDao) {
		this.systemUserDao = systemUserDao;
	}
	

	public UserRoleAssociationDao getAssociationDao() {
		return associationDao;
	}
	@Autowired
	public void setAssociationDao(UserRoleAssociationDao associationDao) {
		this.associationDao = associationDao;
	}
	public Set<TreeNode> listParentNodes(TreeNode treeNode) {
		Set<TreeNode> set=new HashSet<TreeNode>();
		for(TreeNode parent=this.getTreeNode(treeNode.getParentId());parent!=null;parent=this.getTreeNode(parent.getParentId())){
			set.add(parent);
		}
		return set;
	}
	public String saveRoleTreeNode(RoleTreeNode roleTreeNode) {
		roleTreeNodeDao.save(roleTreeNode);
		roleTreeNodeDao.flush();
		return null;
	}
	public Page<UserRoleAssociation> listRTreeUsers(RoleTreeNode roleTreeNode,Set<SystemUser> without, Page<UserRoleAssociation> page) {
		
			String queryString=null;
			String inString="(";
			if(without!=null&&without.size()!=0){
				
				for(SystemUser user:without){
					inString+=user.getId()+",";
				}
				inString=inString.substring(0, inString.length()-1);
				inString+=")";
				//queryString=" select user  from  RoleTreeNode as node,SystemUser as user where node.id=? and user in elements(node.users) and user.id not in "+inString;
				//queryString=" select association  from  RoleTreeNode as node,UserRoleAssociation as association where node.id=? and association.user in elements(node.users) and association.user.id not in "+inString;
				queryString=" select association  from  UserRoleAssociation as association where association.pk.roleNodeId=? and association.pk.userId not in "+inString+" order by association.orderId ";
			}else{
				//queryString=" select user  from  RoleTreeNode as node,SystemUser as user where node.id=? and user in elements(node.users)  ";
				//queryString=" select association  from  RoleTreeNode as node,UserRoleAssociation as association where node.id=? and association.user in elements(node.users)  ";
				queryString=" select association  from  UserRoleAssociation as association where association.pk.roleNodeId=?  order by association.orderId";
			}
			Page<UserRoleAssociation> newPage=associationDao.findPage(page, queryString,roleTreeNode.getId());
			
			return newPage;
		
	}
	public void orderSwap(TreeNode node1,TreeNode node2){
		long order1=node1.getOrderId();
		long order2=node2.getOrderId();
		node1.setOrderId(order2);
		node2.setOrderId(order1);
		this.treeNodeDao.save(node1);
		this.treeNodeDao.save(node2);
		this.treeNodeDao.flush();
	}
	public void orderSwapAssociation(UserRoleAssociation association1,
			UserRoleAssociation association2) {
		Long order1=association1.getOrderId();
		Long order2=association2.getOrderId();
		association1.setOrderId(order2);
		association2.setOrderId(order1);
		this.associationDao.save(association1);
		this.associationDao.save(association2);
		this.associationDao.flush();
		
	}
	
	
	public UserRoleAssociation findbyID(Long userid, Long roleid) {
		String hql="from UserRoleAssociation association where association.pk.userId=? and  association.pk.roleNodeId=? ";
		UserRoleAssociation association = (UserRoleAssociation)this.associationDao.findUnique(hql, userid,roleid);
		return association;
	}
	
	public TreeNode getTreeNodeByNodeName(String nodename) {
		return  treeNodeDao.findUniqueBy("nodeName", nodename);
	}
		
	public List<TreeNode> getAllTreeNodes() {
		String hql = "from " + DomainTreeNode.class.getName();
		Map<String,Object> params = new HashMap<String,Object>();
		Query query = treeNodeDao.createQuery(hql, params);
		return query.list();
	}
	
	
	
	

	
	

}
