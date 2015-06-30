package edu.zju.cims201.GOF.service.tree;

import java.util.List;
import java.util.Set;

import org.springside.modules.orm.Page;


import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Tree;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.UserRoleAssociation;

/**
 * 提供关于树的相关服务，创建一个接口，由具体的实现类来实现接口中的方法
 * 
 * @author hebi
 */

public interface TreeService {
	
	
	
	
	
	public Set<TreeNode> listParentNodes(TreeNode treeNode);
	
	//根据树节点Id和树类型查找树节点
	public TreeNode getTreeNode(Long treeNodeId );
	
	//	根据树code和树类型查找树节点
	public TreeNode getTreeNode(String treeCode );
	
	//根据树类型查找树的根节点
	//disableInte 判断是否屏蔽不需要显示的域节点id
	public List<TreeNode> listRootTreeNodes(Class treeNodeClazz,boolean disableInte);
	
	
	public String saveRoleTreeNode(RoleTreeNode roleTreeNode);
	
	
	/**
	 * 获得某一节点的所有子节点
	 * @param TreeNodeId 该节点的ID
	 * @return 返回一棵树
	 * @author hebi
	 */
	public Set<TreeNode> listSubTreeNodes(TreeNode parentNode);
	
	
	
	
	/**
	 * 获得某一节点的所有子节点
	 * @param TreeNodeId 该节点的编码code
	 * @return 返回一棵树
	 * @author hebi
	 */
	
	public List<TreeNode> listDeepSubTreeNodes(String treeCode);
	
	
	
	
	/**
	 * 添加一个树节点
	 * @param ParentNodeID 该节点的父节点id
	 * @param ChildNode 该节点
	 * @return 
	 * @author hebi
	 */
	public String saveNode(TreeNode parentNode,TreeNode addedNode);
	
	
	/**
	 * 删除树节点及其所有子节点（如果有的话）
	 * @param deletedNode 需要被删除的节点
	 * 
	 * @return 删除成功标识
	 * @author hebi
	 */
	public String deleteTree(TreeNode rootNode);
	
	
	
	
	/**
	 * 更新一个树节点的相关信息不改变树结构
	 * @param treeNode 更改的节点
	 * 
	 * @return 
	 * @author hebi
	 */
	public String updateNodeInfo(TreeNode treeNode);
	
	
	
	
	/**
	 * 将树加入另一个树中
	 * @param parentTreeNode 待添加的父节点
	 * @param subRootTreeNode 子树
	 * @return 
	 * @author hebi
	 */
	public String addTree(TreeNode parentTreeNode,TreeNode subRootTreeNode);
	
	
	
	
	/**
	 * 添加知识与树节点关联//只有上传者添加
	 * @param treeNode 要加知识的树节点
	 * @param knowledge 要关联的知识
	 * @return 
	 * @author hebi
	 */
	public String addKnowledgeRelation(TreeNode treeNode, MetaKnowledge knowledge);
	
	
	
	/**
	 * 删除知识与树节点关联//只有上传者删除
	 * @param treeNode 要删除知识的树节点
	 * @param knowledge 要删除关联的知识
	 * @return 
	 * @author hebi
	 */
	public String deleteKnowledgeRelation(TreeNode treeNode,MetaKnowledge knowledge);
	
	
	
	
	
	/**
	 * //列出某一分类节点下面的所有知识
	 * @param CDtree 节点
	 * @param listSubTree 用于区别，当为true时，列出该节点下，包括子节点关联的知识；为false时，仅列出该节点下关联的知识
	 * @return set类型的知识列表
	 * @author hebi
	 */
	
	public Page<MetaKnowledge> listCDTreeKnowledges(TreeNode treeNode,boolean listSubTree,Page<MetaKnowledge> page);
	
	
	//给树节点添加人关联，treenode为roletree
	public String addUserRelation(RoleTreeNode roleTreeNode, SystemUser user);
	
	//给树节点删除人关联，treenode多数为roletree
	public String deleteUserRelation(RoleTreeNode roleTreeNode, Set<SystemUser> users);
	
	public Page<UserRoleAssociation> listRTreeUsers(RoleTreeNode roleTreeNode,boolean listSubTree,Page<UserRoleAssociation> page);
	
	public Page<UserRoleAssociation> listRTreeUsers(RoleTreeNode roleTreeNode,Set<SystemUser> without, Page<UserRoleAssociation> page);
	
	//	列出树节点管理员
	public Set<AdminUser> listAdmins(TreeNode treeNode);
	
	//列出用户所在的角色组 user服务中已经有了
	//public Set<RoleTreeNode> listBelongingRoles(SystemUser user);
	
	
	//列出管理员所管理的树节点,admin管理员，treeNodeClazz为节点类型,user服务中已经有了
	//public Set<TreeNode> listAdminNodes(AdminUser admin,Class<TreeNode> treeNodeClazz);
	
	
	//	列出知识所在的分类树节点
	public List<TreeNode> listBelongingCTreeNodes(MetaKnowledge knowledge);
	//节点排序，direction为1时，向上；direction为负时或0时，向下。
	public void orderSwap(TreeNode node1,TreeNode node2);
	
	public void orderSwapAssociation(UserRoleAssociation association1,UserRoleAssociation association2);
	
	public UserRoleAssociation findbyID(Long userid,Long roleid);

	/**
	 * 根绝节点名拿到该节点
	 * @param nodename
	 * @author panlei
	 * @return DomainTreeNode
	 */
	public TreeNode getTreeNodeByNodeName(String nodename);
	
	public List<TreeNode> getAllTreeNodes();
	
	
	
	
	
	
	
	
}
