package edu.zju.cims201.GOF.service.privilege;

import java.sql.ResultSet;
import java.util.List;
import java.util.Set;


import edu.zju.cims201.GOF.hibernate.pojo.AdminOperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.Empowerment;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.OperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.RolePrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.UserPrivilegeTriple;
import edu.zju.cims201.GOF.rs.dto.TripleDTO;

/**
 * 提供关于用户和角色关于权限配置的相关服务，创建一个接口，由具体的实现类来实现接口中的方法
 * 
 * @author hebi
 */

public interface PrivilegeService {
	
	public Set<RolePrivilegeTriple> listRolePrivilegeTriples(TreeNode roleTreeNode);
	
	public Set<RolePrivilegeTriple> listRolePrivilegeTriples(Set<TreeNode> roleTreeNodes);
	
	
	//public List<Empowerment> listEmpowerments(TreeNode treeNode,Class tripleClazz);
	
	public List<Empowerment> listEmpowerments(TreeNode treeNode,Boolean isAdmin);
	
	public String deleteEmpowerment(Empowerment empowerment,boolean isBeyond2Role);
	
	public Empowerment getEmpowerment(Long empowermentID);
	
	public String saveEmpowerment(Empowerment empowerment);
	
	public int deletePrivilege(Empowerment empowerment,TreeNode role);
	
	public int deletePrivilege(Empowerment empowerment,SystemUser user,boolean isAdmin);
	
	public int deletePrivilege(Empowerment empowerment, OperationRight operationRight,boolean isAdmin);
	
	
	public List<OperationRight> getOperationRight(Empowerment empowerment,Class tripleClazz);
	
	
	public List<OperationRight> listAllOperationRight(boolean isAdmin);
	
	
	//public List<Empowerment> listEmpowerments(SystemUser user,Class tripleClazz);
	
	
	
	
	//增加用户操作权限
	public String addOperationRight(OperationRight operationRight);
	
	//增加管理员操作权限
	public String addAdminOperationRight(AdminOperationRight adminOperationRight);
	
	//查找用户权限
	public OperationRight getOperationRight(String rightName);
	
	public OperationRight getOperationRight(Long rightId);
	
	//查找管理员权限
	public AdminOperationRight getAdminOperationRight(String right);
	
	
	//修改用户权限
	public String modifyOperationRight(OperationRight operationRight);
	
	//修改管理员权限
	public String modifyAdminOperationRight(AdminOperationRight adminOperationRight);
	
	public String addUserWithRights2TreeNode(UserPrivilegeTriple userPrivilegeTriple);
	
	public String addUserWithRights2TreeNodes(Set<UserPrivilegeTriple> userPrivilegeTriples);
	
	
	
	/**
	 * 返回单独的用户对某个节点的权限列表
	 * @param TreeNode 该节点
	 * @param user 该用户
	 * @return 返回{@link OperationRight}列表
	 * @author hebi
	 */
	//public Set <OperationRight> listUserAndNodeRights(SystemUser user,TreeNode treeNode);
	//user服务中已经有了
	
	
	
	
	
	/**
	 * 返回角色组对某个节点的权限列表
	 * @param TreeNodeDTO 该节点
	 * @param roleTreeNode用户角色
	 * @return 返回{@link OperationRight}列表
	 * @author hebi
	 */
	public Set <OperationRight> listRoleAndNodeRights(RoleTreeNode roleTreeNode,TreeNode treeNode);
	
	
	
	/**
	 * 返回角色组对某个节点的权限列表
	 * @param TreeNodeDTO 该节点
	 * @param admin该节点管理员
	 * @return 返回{@link OperationRight}列表
	 * @author hebi
	 */
	public Set <AdminOperationRight> listAdminAndNodeRights(AdminUser admin,TreeNode treeNode);
	
	
	
		

	
	
	
	
	/**
	 * 对用户添加三元组权限
	 * @param userPrivilegeTriple 需要添加的用户-树节点-权限三元组
	 * 
	 * 
	 * @return 操作结果 -1不成功 1成功
	 * @author hebi
	 */
	
	public String  addAdminWithRights2TreeNode(AdminPrivilegeTriple adminPrivilegeTriple);
	
	public String  addAdminWithRights2TreeNodes(Set<AdminPrivilegeTriple> adminPrivilegeTriples);
	
	
	
	
	
	
	/**
	 * 对角色添加三元组权限
	 * @param rolePrivilegeTriple 需要角色-树节点三元组
	 * 
	 * 
	 * @return 操作结果 -1不成功 1成功
	 * @author hebi
	 */
	public String addRoleWithRights2TreeNode(RolePrivilegeTriple rolePrivilegeTriple);
	
	public String addRoleWithRights2TreeNodes(Set<RolePrivilegeTriple> rolePrivilegeTriples);
	
	
	/**
	 * 对用户修改三元组权限，用户-树节点-权限三元组
	 * @param userPrivilegeTriple 需要修改的三元组
	 * 
	 * @return 操作结果 -1不成功 1成功
	 * @author hebi
	 */
	public String modifyUserWithRightsOnTreeNode(UserPrivilegeTriple userPrivilegeTriple);
	
	
	
	
	/**
	 * 对角色修改三元组权限 需要角色-树节点-权限三元组
	 * @param rolePrivilegeTriple 需要修改的角色-树节点-权限三元组
	 * 
	 * @return 操作结果 -1不成功 1成功
	 * @author hebi
	 */
	public String modifyRoleWithRightsOnTreeNode(RolePrivilegeTriple rolePrivilegeTriple);
	
	
	
	
	/**
	 * 对用户删除三元组权限，用户-树节点-权限三元组
	 * @param userPrivilegeTriple 需要删除的用户-树节点-权限三元组
	 *
	 * @return 操作结果 -1不成功 1成功
	 * @author hebi
	 */
	public String deleteUserWithRightsOnTreeNode(UserPrivilegeTriple userPrivilegeTriple);

	

	/**
	 * 对角色删除三元组权限，角色-树节点-权限三元组
	 * @param rolePrivilegeTriple 需要删除的角色-树节点-权限三元组
	 * 
	 * @return 操作结果 -1不成功 1成功
	 * @author hebi
	 */
	public String deleteRoleWithRightsOnTreeNode(RolePrivilegeTriple rolePrivilegeTriple);

	
}
