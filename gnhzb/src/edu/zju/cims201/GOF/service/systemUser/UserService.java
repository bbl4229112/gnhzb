package edu.zju.cims201.GOF.service.systemUser;

import java.util.List;
import java.util.Set;

import org.springside.modules.orm.Page;

import edu.zju.cims201.GOF.hibernate.pojo.AdminOperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.Empowerment;
import edu.zju.cims201.GOF.hibernate.pojo.OperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.RolePrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.UserPrivilegeTriple;





/**
 * i
 * 
 * @author hebi
 */

public interface UserService {
	
	public boolean isAdmin(SystemUser user);
	
	
	//	创建用户
	public String createUser(SystemUser user);
	
	//创建管理员
	public String createAdmin(AdminUser adminUser);
	
	public AdminUser changeUser2Admin(SystemUser user);
	
	public SystemUser changeAdmin2User(AdminUser admin);
	
	
	public List<SystemUser> listUsers(Set<Long> userids);
	 public List<SystemUser> getAllUsers();
	
	
	
	
	// 验证用户是否存在
	public boolean isUserExist(String userEmail);
	
	
	
	
	//根据姓名查找用户
	public Page<SystemUser> searchUsers(String name,Page<SystemUser> page);
	
	public List<SystemUser> searchUsers(String name);
	
	public Page<SystemUser> searchRoleUsers(String name, TreeNode roleTreeNode, boolean notIn,Page<SystemUser> page);
	
	public List<SystemUser> searchRoleUsers(String name, TreeNode roleTreeNode, boolean notIn, Set<SystemUser> exists);
	//通过springsecurityz在session中的存储直接获得当前的用户对象
	public SystemUser getUser() ;
	//根据email查找SystemUser
	public SystemUser getUser(String email);
	
	//根据id查找systemUser
	public SystemUser getUser(Long id);
	
	//根据name查找其中的一个用户
	public SystemUser getOneUser(String name);
	
	//根据name查找其中的一个用户
	public boolean isNameExist(String name);
	
	//更新user信息，成功返回标识
	public String updateUser(SystemUser user);
	
	//返回user单独的权限
	public Set<UserPrivilegeTriple> listUserPrivilegeTriples(SystemUser user);
	
	
	//	找出user所属的roleTreeNode
	public Set<TreeNode> listRoleTreeNodes(SystemUser user);
	
	
	//列出user对特定treeNode所具有的权限
	public Set<OperationRight> listOnlyUserRights(SystemUser user,TreeNode treeNode);
	
	
	
	//返回管理员管理的树节点及其权限三元组
	public Set<AdminPrivilegeTriple> listAdminPrivilegeTriple(AdminUser adminUser);
	
	//	列出管理员所管理的树节点,admin管理员，treeNodeClazz为节点类型
	public Set<TreeNode> listAdminNodes(AdminUser admin,Class<TreeNode> treeNodeClazz);
	
	//列出所有的用户
	public List<SystemUser> listUsers();
	
	//删除用户,如果尝试删除超级管理员将抛出异常
	public void deleteUser(Long id);
	public void deleteUser(String email);
	
	
	//返回管理员对特定树节点所具有的权限
	//public Set<AdminOperationRight> listAdminRights(AdminUser adminUser,TreeNode treeNode);
	
	//	返回user的所属角色三元组
	//public Page<RolePrivilegeTriple> listRolePrivilegeTriples(SystemUser user,Page<UserPrivilegeTriple> page);
}
