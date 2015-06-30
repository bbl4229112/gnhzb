package edu.zju.cims201.GOF.service.systemUser;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;


import edu.zju.cims201.GOF.dao.tree.RoleTreeNodeDao;
import edu.zju.cims201.GOF.dao.user.AdminUserDao;
import edu.zju.cims201.GOF.dao.user.SystemUserDao;
import edu.zju.cims201.GOF.hibernate.pojo.AdminOperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.Empowerment;
import edu.zju.cims201.GOF.hibernate.pojo.OperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.RolePrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.UserPrivilegeTriple;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.util.Constants;



/**
 * i
 * 
 * @author hebi
 */

@Service
@Transactional (readOnly = true)
public class  UserServiceImpl implements UserService {
	
	
	
	private SystemUserDao userDAO;
	private AdminUserDao adminUserDao;
	
	private RoleTreeNodeDao roleTreeNodeDao;
	
	private SessionFactory sessionFactory;
	
	
	
	@Transactional
	public String createUser(SystemUser user) {
		
		userDAO.save(user);
		userDAO.flush();
		return null;
	}
	
	public String createAdmin(AdminUser adminUser) {
		
		adminUserDao.save(adminUser);
		adminUserDao.flush();
		return null;
	}
	
	
	public AdminUser changeUser2Admin(SystemUser user) {
		
		long id=user.getId();
		
		Session session=null;
		Connection connection = null;
		PreparedStatement ps=null;
		try {
			
			session=sessionFactory.openSession();
			connection=session.connection();
			ps=connection.prepareStatement("update caltks_system_user set is_admin='Y' where id=? ");
			ps.setLong(1, id);
			ps.executeUpdate();
			
		}catch (Exception e) {
			try {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
				
			} catch (Exception e1) {
				
			}
		}finally{
			try {
				connection.commit();
				//从cash中移除缓存对象
				userDAO.getSession().evict(user);
				connection.close();
				session.close();
			} catch (Exception e1) {
				
			}
		}
			
		AdminUser newUser=(AdminUser)this.getUser(id);
		return newUser;
	}
	
	public SystemUser changeAdmin2User(AdminUser admin) {
		long id=admin.getId();
		
		Session session=null;
		Connection connection = null;
		PreparedStatement ps=null;
		try {
			
			session=sessionFactory.openSession();
			connection=session.connection();
			ps=connection.prepareStatement("update caltks_system_user set is_admin='N' where id=? ");
			ps.setLong(1, id);
			ps.executeUpdate();
			
		}catch (Exception e) {
			try {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
				
			} catch (Exception e1) {
				
			}
		}finally{
			try {
				connection.commit();
				//从cash中移除缓存对象
				userDAO.getSession().evict(admin);
				connection.close();
				session.close();
			} catch (Exception e1) {
				
			}
		}
			
		SystemUser newUser=this.getUser(id);
		return newUser;
	}
	
	

	public SystemUser getUser(String email) {
		
			SystemUser user=userDAO.findUniqueBy("email", email);
			return user;	
	}
	public SystemUser getUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return getUser(securityContext.getAuthentication().getName());
			
     }
	
	
	public boolean isAdmin(SystemUser user) {
		
		Object proxyObj = user;
		Object  realEntity=null;
		if (proxyObj instanceof HibernateProxy) {  
		      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
		 } else{
			 realEntity=proxyObj;
		 }
		if(realEntity instanceof AdminUser)
			return true;
		return false;
	}

	public boolean isUserExist(String userEmail) {
		//System.out.println("isusrexist");
		SystemUser user=userDAO.findUniqueBy("email", userEmail);
		if(user==null) return false;
		else
			return true;
		
	}

	
	@Transactional
	public String updateUser(SystemUser user) {
		userDAO.save(user);
		userDAO.flush();
		return null;
	}
	
	

	

	public Set<TreeNode> listAdminNodes(AdminUser admin, Class<TreeNode> treeNodeClazz) {
		Set<AdminPrivilegeTriple> triples=admin.getAdminPrivilegeTriples();
		Set<TreeNode> result=new HashSet<TreeNode>();
		for(AdminPrivilegeTriple triple:triples){
			if(triple.getTreeNode().getClass().getName().equals(treeNodeClazz.getClass().getName())){
				result.add(triple.getTreeNode());
			}
		}
		return result;
	}

	public Set<AdminPrivilegeTriple> listAdminPrivilegeTriple(AdminUser adminUser) {
		
		return adminUser.getAdminPrivilegeTriples();
	}

	public Set<OperationRight> listOnlyUserRights(SystemUser user, TreeNode treeNode) {
		Set<UserPrivilegeTriple> userPrivilegeTriples=user.getUserPrivilegeTriples();
		Set<OperationRight> rights=new HashSet<OperationRight>();
		for(UserPrivilegeTriple triple :userPrivilegeTriples){
			if(triple.getCdTreeNode().getId()==treeNode.getId())
				rights.add(triple.getOperationRight());
		}
		return rights;
	}
	
	public SystemUser getUser(Long id) {
		
		return userDAO.findUniqueBy("id",id);
	}
	
	public SystemUser getOneUser(String name){
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("name", name);
		String hql = "from SystemUser o where o.name=:name";
		Query query = userDAO.createQuery(hql, params);
		List<SystemUser> users = query.list();
		SystemUser oneuser = new SystemUser();
		for(SystemUser user:users) {
			oneuser = user;
		}
		
		return oneuser;	
	}
	
	public boolean isNameExist(String name){
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("name", name);
		String hql = "from SystemUser o where o.name=:name";
		Query query = userDAO.createQuery(hql, params);
		List<SystemUser> users = query.list();		
		if(users.size()>0)
			return true;
		return false;
	}

	public List<SystemUser> listUsers(Set<Long> userids) {
		
		
		
		return userDAO.getSession().createCriteria(SystemUser.class).add(Restrictions.in("id", userids)).list();
	}
	

	public Set<TreeNode> listRoleTreeNodes(SystemUser user) {
		
		return user.getRoleNodes();
	}

	public Set<UserPrivilegeTriple> listUserPrivilegeTriples(SystemUser user) {
		
		return user.getUserPrivilegeTriples();
	}

	public Page<SystemUser> searchUsers(String name, Page<SystemUser> page) {
		
		String hql=null;
		if(name==null||name.replace(" ", "").equals(""))
			hql="from SystemUser as user order by user.id";
		else
			hql="from SystemUser as user where user.name like '%"+name+"%' or user.email like '%"+name+"%' order by user.id";
		//System.out.println(hql);
		
		return userDAO.findPage(page, hql);
	}
	
	public List<SystemUser> searchUsers(String username) {
		
		List<SystemUser> userlist = userDAO.getSession().createQuery(
				"from SystemUser o where o.name like '%"
						+ username
						+ "%'")
				.list();
		return userlist;
	}
	
	public List<SystemUser> listUsers() {
				
		String hql="from SystemUser as user order by user.id";
		List<SystemUser> users = userDAO.getSession().createQuery(hql).list();
		
		return users;
	}
	
	
	@Autowired
	public void setUserDAO(SystemUserDao userDAO) {
		this.userDAO = userDAO;
	}

	public SystemUserDao getUserDAO() {
		return userDAO;
	}

	public AdminUserDao getAdminUserDao() {
		return adminUserDao;
	}
	@Autowired
	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}

 
	public Page<SystemUser> searchRoleUsers(String name, TreeNode roleTreeNode, boolean notIn,Page<SystemUser> page) {
 		String hql=null;
		if(notIn){
			if(name==null||name.replace(" ", "").equals(""))
				hql="Select user from SystemUser as user ,RoleTreeNode as node  where node.id=? and user not in elements(node.users) and user.email!=? order by user.id";
			else
				hql="Select user from SystemUser as user ,RoleTreeNode as node  where node.id=? and (user.name like '%"
						+name+"%' or user.email like '%"+name+"%') and user not in elements(node.users) and user.email!=? order by user.id";
		}else{
			if(name==null||name.replace(" ", "").equals(""))
				hql="Select user from SystemUser as user ,RoleTreeNode as node  where node.id=? and user  in elements(node.users) and user.email!=? order by user.id";
			else
				hql="Select user from SystemUser as user ,RoleTreeNode as node  where node.id=? and (user.name like '%"
						+name+"%' or user.email like '%"+name+"%') and user  in elements(node.users) and user.email!=? order by user.id" ;
		}
		
		return userDAO.findPage(page, hql, roleTreeNode.getId(),Constants.SUPERADMIN);
	}

	public List<SystemUser> searchRoleUsers(String name, TreeNode roleTreeNode, boolean notIn, Set<SystemUser> exists) {
		String hql=null;
		
		if(name==null||name.replace(" ", "").equals(""))
			hql="Select node.users from RoleTreeNode as node where node.id=? " ;
			
		else
			hql="Select node.users from SystemUser as user, RoleTreeNode as node where node.id=? and " +
			"( user.name like '%"
			+name+"%' or user.email like '%"+name+"%') order by user.id";
		
		List<SystemUser> userInRole=roleTreeNodeDao.createQuery(hql, roleTreeNode.getId()).list();
		
		if(notIn){
			userInRole.removeAll(exists);
			
		}else{
			
		}
		
		return userInRole;
	}
	
	
	  
	@Transactional
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDAO.delete(id);
		userDAO.flush();
	}
	
	@Transactional
	public void deleteUser(String email) {
		//System.out.print("到Service了");
		SystemUser user = userDAO.findUniqueBy("email", email);
		
		userDAO.delete(user);
		userDAO.flush();
	}
	
	

	
	//判断是否超级管理员.
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	public RoleTreeNodeDao getRoleTreeNodeDao() {
		return roleTreeNodeDao;
	}
	@Autowired
	public void setRoleTreeNodeDao(RoleTreeNodeDao roleTreeNodeDao) {
		this.roleTreeNodeDao = roleTreeNodeDao;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public List<SystemUser> getAllUsers() {	
		return userDAO.getAll();
	}

	
	
	
	
	
	
	
	
	

	
}
