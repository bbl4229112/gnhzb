package edu.zju.cims201.GOF.service.privilege;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.ConnectionProviderFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.privilege.AdminOperationRightDao;
import edu.zju.cims201.GOF.dao.privilege.AdminPrivilegeTripleDao;
import edu.zju.cims201.GOF.dao.privilege.EmpowermentDao;
import edu.zju.cims201.GOF.dao.privilege.OperationRightDao;
import edu.zju.cims201.GOF.dao.privilege.RolePrivilegeTripleDao;
import edu.zju.cims201.GOF.dao.privilege.UserPrivilegeTripleDao;
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
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.HibernatePorxy;


@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {
	
	
	private RolePrivilegeTripleDao rolePrivilegeTripleDao;
	private UserPrivilegeTripleDao userPrivilegeTripleDao;
	private AdminPrivilegeTripleDao adminPrivilegeTripleDao;
	private AdminOperationRightDao adminOperationRightDao;
	private OperationRightDao operationRightDao;
	
	private EmpowermentDao empowermentDao;
	
	private TreeService treeService; 
	
	private UserService userService;

	



	@Transactional
	public String addAdminOperationRight(AdminOperationRight adminOperationRight) {
		adminOperationRightDao.save(adminOperationRight);
		return null;
	}
	@Transactional
	public String addAdminWithRights2TreeNode(AdminPrivilegeTriple adminPrivilegeTriple) {
		adminPrivilegeTripleDao.save(adminPrivilegeTriple);
		adminPrivilegeTripleDao.flush();
		return null;
	}
	@Transactional
	public String addAdminWithRights2TreeNodes(Set<AdminPrivilegeTriple> adminPrivilegeTriples) {
		for(AdminPrivilegeTriple adminPrivilegeTriple:adminPrivilegeTriples){
			adminPrivilegeTripleDao.save(adminPrivilegeTriple);
		}
		adminPrivilegeTripleDao.flush();
		return null;
	}
	@Transactional
	public String addOperationRight(OperationRight operationRight) {
		operationRightDao.save(operationRight);
		return null;
	}
	@Transactional
	public String addRoleWithRights2TreeNode(RolePrivilegeTriple rolePrivilegeTriple) {
		rolePrivilegeTripleDao.save(rolePrivilegeTriple);
		rolePrivilegeTripleDao.flush();
		return null;
	}
	@Transactional
	public String addRoleWithRights2TreeNodes(Set<RolePrivilegeTriple> rolePrivilegeTriples) {
		for(RolePrivilegeTriple rolePrivilegeTriple:rolePrivilegeTriples){
			rolePrivilegeTripleDao.save(rolePrivilegeTriple);
		}
		rolePrivilegeTripleDao.flush();
		return null;
	}
	
	
	@Transactional
	public String addUserWithRights2TreeNode(UserPrivilegeTriple userPrivilegeTriple) {
		userPrivilegeTripleDao.save(userPrivilegeTriple);
		userPrivilegeTripleDao.flush();
		return null;
	}
	
	@Transactional
	public String addUserWithRights2TreeNodes(Set<UserPrivilegeTriple> userPrivilegeTriples) {
		for(UserPrivilegeTriple userPrivilegeTriple:userPrivilegeTriples){
			userPrivilegeTripleDao.save(userPrivilegeTriple);
		}
		userPrivilegeTripleDao.flush();
		return null;
	}
	
	@Transactional
	public String deleteRoleWithRightsOnTreeNode(RolePrivilegeTriple rolePrivilegeTriple) {
		rolePrivilegeTripleDao.delete(rolePrivilegeTriple);
		rolePrivilegeTripleDao.flush();
		return null;
	}
	
	@Transactional
	public String deleteUserWithRightsOnTreeNode(UserPrivilegeTriple userPrivilegeTriple) {
		userPrivilegeTripleDao.delete(userPrivilegeTriple);
		userPrivilegeTripleDao.flush();
		return null;
	}
	public AdminOperationRight getAdminOperationRight(String right) {
		
		return adminOperationRightDao.findUniqueBy("code", right);
	}
	public OperationRight getOperationRight(String rightName) {
	
		return operationRightDao.findUniqueBy("code", rightName);
	}
	public Set<AdminOperationRight> listAdminAndNodeRights(AdminUser admin, TreeNode treeNode) {
		String queryString="from AdminPrivilegeTriple as triple where triple.admin=? and triple.TreeNode=?";
		List<AdminPrivilegeTriple> list=adminPrivilegeTripleDao.createQuery(queryString, admin,treeNode).list();
		Set<AdminOperationRight> set=new HashSet<AdminOperationRight>();
		for(AdminPrivilegeTriple adminPrivilegeTriple:list){
			set.add(adminPrivilegeTriple.getAdminOperationRight());
		}
		return set;
	}
	
	public Set<OperationRight> listRoleAndNodeRights(RoleTreeNode roleTreeNode, TreeNode treeNode) {
		String queryString="from RolePrivilegeTriple as triple where triple.cdTreeNode=? and triple.roleTreeNode=?";
		List<RolePrivilegeTriple> list=rolePrivilegeTripleDao.createQuery(queryString, treeNode,roleTreeNode).list();
		Set<OperationRight> set=new HashSet<OperationRight>();
		for(RolePrivilegeTriple rolePrivilegeTriple :list){
			set.add(rolePrivilegeTriple.getOperationRight());
		}
		return set;
	}
	
	
	
	@Transactional
	public String modifyAdminOperationRight(AdminOperationRight adminOperationRight) {
		adminOperationRightDao.save(adminOperationRight);
		return null;
	}
	
	@Transactional
	public String modifyOperationRight(OperationRight operationRight) {
		operationRightDao.save(operationRight);
		return null;
	}
	
	@Transactional
	public String modifyRoleWithRightsOnTreeNode(RolePrivilegeTriple rolePrivilegeTriple) {
		rolePrivilegeTripleDao.save(rolePrivilegeTriple);
		rolePrivilegeTripleDao.flush();
		return null;
	}
	
	@Transactional
	public String modifyUserWithRightsOnTreeNode(UserPrivilegeTriple userPrivilegeTriple) {
		userPrivilegeTripleDao.save(userPrivilegeTriple);
		userPrivilegeTripleDao.flush();
		return null;
	}
	
	
	public Set<RolePrivilegeTriple> listRolePrivilegeTriples(TreeNode roleTreeNode) {
		if(roleTreeNode==null)
			return null;
		return new HashSet<RolePrivilegeTriple> (rolePrivilegeTripleDao.findBy("roleTreeNode", roleTreeNode));
	}
	public Set<RolePrivilegeTriple> listRolePrivilegeTriples(Set<TreeNode> roleTreeNodes) {
		if(roleTreeNodes==null)
			return null;
		Set<RolePrivilegeTriple> set=new HashSet<RolePrivilegeTriple>();
		for(TreeNode node:roleTreeNodes){
			
			Set<RolePrivilegeTriple> temp=this.listRolePrivilegeTriples(node);
			for(RolePrivilegeTriple triple:temp){
				set.add(triple);
			}
			
		}
		
		return set;
	}
	
	
	
	public List<Empowerment> listEmpowerments(TreeNode treeNode,Boolean isAdmin) {
		String queryString=" from Empowerment empowerment  where empowerment.belongedNode=? and empowerment.isAdmin=?";
		
		return empowermentDao.createQuery(queryString, treeNode,isAdmin).list();
		
	}
	
	public Empowerment getEmpowerment(Long empowermentID) {
		
		return empowermentDao.findUniqueBy("id", empowermentID);
	}
	
	
	public String saveEmpowerment(Empowerment empowerment) {
		empowermentDao.save(empowerment);
		empowermentDao.flush();
		return null;
	}
	
	public List<OperationRight> getOperationRight(Empowerment empowerment,Class tripleClazz) {
		
		String queryHQL=null;
		if(tripleClazz.equals(RolePrivilegeTriple.class)){
			queryHQL="select triple.operationRight from RolePrivilegeTriple triple where triple.empowerment=?";
			return rolePrivilegeTripleDao.createQuery(queryHQL, empowerment).list();
		}else if(tripleClazz.equals(UserPrivilegeTriple.class)){
			queryHQL="select triple.operationRight from UserPrivilegeTriple triple where triple.empowerment=?";
			return userPrivilegeTripleDao.createQuery(queryHQL, empowerment).list();
		}else if(tripleClazz.equals(AdminPrivilegeTriple.class)){
			queryHQL="select triple.adminOperationRight from AdminPrivilegeTriple triple where triple.empowerment=?";
			return userPrivilegeTripleDao.createQuery(queryHQL, empowerment).list();
		}
		return null;
	}
	
	public int deletePrivilege(Empowerment empowerment, TreeNode role) {
		String hql="delete RolePrivilegeTriple triple where triple.empowerment=? and triple.roleTreeNode=?";
		int result=rolePrivilegeTripleDao.batchExecute(hql, empowerment,role);
		rolePrivilegeTripleDao.flush();
		return result;
	}
	
	public int deletePrivilege(Empowerment empowerment, SystemUser user, boolean isAdmin) {
		int result;
		if(!isAdmin){
			String hql="delete UserPrivilegeTriple triple where triple.empowerment=? and triple.user=?";
			result=userPrivilegeTripleDao.batchExecute(hql, empowerment,user);
			userPrivilegeTripleDao.flush();
		}else{
			AdminUser admin=(AdminUser)user;
			String hql="delete AdminPrivilegeTriple triple where triple.empowerment=? and triple.admin=?";
			result=adminPrivilegeTripleDao.batchExecute(hql, empowerment,admin);
			adminPrivilegeTripleDao.flush();
			if(admin.getAdminPrivilegeTriples().size()==0)
				this.userService.changeAdmin2User(admin);
			
			
			
		}
		
		return result;
	}
	
	public OperationRight getOperationRight(Long rightId) {
		
		return this.operationRightDao.findUniqueBy("id", rightId);
	}
	
	public int deletePrivilege(Empowerment empowerment, OperationRight operationRight,boolean isAdmin) {
		
		if(!isAdmin){
			String user_hql="delete UserPrivilegeTriple triple where triple.empowerment=? and triple.operationRight=?";
			int result1=userPrivilegeTripleDao.batchExecute(user_hql, empowerment,operationRight);
			userPrivilegeTripleDao.flush();
			
			String role_hql="delete RolePrivilegeTriple triple where triple.empowerment=? and triple.operationRight=?";
			int result2=userPrivilegeTripleDao.batchExecute(role_hql, empowerment,operationRight);
			userPrivilegeTripleDao.flush();
			
			return result1+result2;
		}else{
			
			String admin_hql="delete AdminPrivilegeTriple triple where triple.empowerment=? and triple.adminOperationRight=?";
			int result=userPrivilegeTripleDao.batchExecute(admin_hql, empowerment,(AdminOperationRight)operationRight);
			userPrivilegeTripleDao.flush();
			
			return result;
		}
		
		
	}
	
//	public List<Empowerment> listEmpowerments(SystemUser user, Class tripleClazz) {
//		
//		if(tripleClazz.equals(UserPrivilegeTriple.class)){
//			String queryString="select triple.empowerment from UserPrivilegeTriple triple where triple.user=?";
//			return userPrivilegeTripleDao.createQuery(queryString, user).list();
//		}else if(tripleClazz.equals(AdminPrivilegeTriple.class)){
//			String queryString="select triple.empowerment from AdminPrivilegeTriple triple where triple.cdTreeNode=?";
//			return userPrivilegeTripleDao.createQuery(queryString, user).list();
//		}
//		
//		return null;
//		
//		
//		
//	}
	
	
	
	
	
	
	
	
	
	
	public RolePrivilegeTripleDao getRolePrivilegeTripleDao() {
		return rolePrivilegeTripleDao;
	}
	@Autowired
	public void setRolePrivilegeTripleDao(
			RolePrivilegeTripleDao rolePrivilegeTripleDao) {
		this.rolePrivilegeTripleDao = rolePrivilegeTripleDao;
	}



	public UserPrivilegeTripleDao getUserPrivilegeTripleDao() {
		return userPrivilegeTripleDao;
	}


	@Autowired
	public void setUserPrivilegeTripleDao(
			UserPrivilegeTripleDao userPrivilegeTripleDao) {
		this.userPrivilegeTripleDao = userPrivilegeTripleDao;
	}



	public AdminPrivilegeTripleDao getAdminPrivilegeTripleDao() {
		return adminPrivilegeTripleDao;
	}


	@Autowired
	public void setAdminPrivilegeTripleDao(
			AdminPrivilegeTripleDao adminPrivilegeTripleDao) {
		this.adminPrivilegeTripleDao = adminPrivilegeTripleDao;
	}
	public AdminOperationRightDao getAdminOperationRightDao() {
		return adminOperationRightDao;
	}
	@Autowired
	public void setAdminOperationRightDao(
			AdminOperationRightDao adminOperationRightDao) {
		this.adminOperationRightDao = adminOperationRightDao;
	}
	public OperationRightDao getOperationRightDao() {
		return operationRightDao;
	}
	@Autowired
	public void setOperationRightDao(OperationRightDao operationRightDao) {
		this.operationRightDao = operationRightDao;
	}
	public TreeService getTreeService() {
		return treeService;
	}
	
	@Autowired
	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}
	
	
	
	public UserService getUserService() {
		return userService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public EmpowermentDao getEmpowermentDao() {
		return empowermentDao;
	}
	
	@Autowired
	public void setEmpowermentDao(EmpowermentDao empowermentDao) {
		this.empowermentDao = empowermentDao;
	}
	public List<OperationRight> listAllOperationRight(boolean isAdmin) {
		
		String hql="from OperationRight ";
		
		List<OperationRight> all=this.operationRightDao.createQuery(hql).list();
		
		List<OperationRight> results=new ArrayList<OperationRight>();
		
		
		for(OperationRight right:all){
			
			//hibernate 代理
			Object proxyObj = right;
			Object  realEntity=null;
			if (proxyObj instanceof HibernateProxy) {  
			      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
			 } else{
				 realEntity=proxyObj;
			 }
			
			if(isAdmin){
				if(realEntity instanceof AdminOperationRight)
					results.add((OperationRight)realEntity);
				
				
			}else {
				
				if(!(realEntity instanceof AdminOperationRight))
					results.add((OperationRight)realEntity);
				
			}
			
		}
		
		return results;
	}
	
	@Transactional
	public String deleteEmpowerment(Empowerment empowerment,boolean isBeyond2Role) {
		if(isBeyond2Role){
			
			Set<AdminPrivilegeTriple> triples=empowerment.getAdminPrivilegeTriples();
			Set<AdminUser> admins=new HashSet<AdminUser>();
			for(AdminPrivilegeTriple triple:triples){
				admins.add(triple.getAdmin());
			}
			
			this.empowermentDao.delete(empowerment);
			this.empowermentDao.flush();
			for(AdminUser admin:admins){
				if(admin.getAdminPrivilegeTriples().size()==0)
					this.userService.changeAdmin2User(admin);
			}
		}else{
			this.empowermentDao.delete(empowerment);
			this.empowermentDao.flush();
		}
		
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
