package edu.zju.cims201.GOF.web.privilege;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojo.AdminOperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.Empowerment;
import edu.zju.cims201.GOF.hibernate.pojo.OperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.EmpowermentDTO;
import edu.zju.cims201.GOF.rs.dto.OperationRightDTO;
import edu.zju.cims201.GOF.rs.dto.RoleDTO;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.rs.dto.UsersAndRolesDTO;
import edu.zju.cims201.GOF.service.privilege.PrivilegeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;


@Namespace("/privilege")


public class TreeEmpowermentAction extends ActionSupport implements ServletResponseAware{
	
	
	private long currentEmpowerment;
	
	private long nodeId;
	
	private long userId;
	
	private int adminTree;
	
	private long rightId;
	
	//private int adminTree;
	
	private String jsonUsers;
	private String jsonRoles;
	private String jsonOperations;
	
	
	
	private String empowermentName;
	private String empowermentDescription;
	
	
	

	
	
	private PrivilegeService privilegeService;
	
	private TreeService treeService;
	
	private UserService userService;
	
	private HttpServletResponse response;
	
	
	
	public String delete() throws Exception {
		if(this.currentEmpowerment==0)
			return null;
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
		this.privilegeService.deleteEmpowerment(empowerment,true);
		
		return null;
	}
	
	
	
	public String save() throws Exception {
		if(this.nodeId==0)
			return null;
		TreeNode belongNode=treeService.getTreeNode(this.nodeId);
		SystemUser creater=getUser();
		
		Empowerment empowerment=new Empowerment();
		empowerment.setBelongedNode(belongNode);
		empowerment.setCreater(creater);
		if(this.adminTree==0){
			empowerment.setIsAdmin(false);
		}else
			empowerment.setIsAdmin(true);
		empowerment.setName(this.empowermentName);
		empowerment.setEmpowermentDate(new Date());
		empowerment.setDescription(this.empowermentDescription);
		privilegeService.saveEmpowerment(empowerment);
		
		EmpowermentDTO empowermentDTO=new EmpowermentDTO();
		empowermentDTO.setId(empowerment.getId());
		empowermentDTO.setName(empowerment.getName());
		
		
		JSONUtil.write(response, empowermentDTO);
		
		
		
		return null;
	}
	
	private SystemUser getUser(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String email=securityContext.getAuthentication().getName();
		SystemUser user=userService.getUser(email);
		return user;
	}
	
	
	
	
	
	
	public String listUsersOutEmpowerment() throws Exception {
		
		if(this.nodeId==0||this.currentEmpowerment==0)
			return null;
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
		
		Set<SystemUser> exists=new HashSet<SystemUser>();
		
		Set<AdminPrivilegeTriple> adminTriples=empowerment.getAdminPrivilegeTriples();
		for(AdminPrivilegeTriple triple:adminTriples){
			exists.add(triple.getAdmin());
		}
		
		TreeNode roleTreeNode=treeService.getTreeNode(this.nodeId);
		List<SystemUser> users=userService.searchRoleUsers(null, roleTreeNode, true,exists);
		
		writeUserDTO(users);
		
		return null;
	}
	
	private void writeUserDTO(List<SystemUser> users) throws Exception {
		List<UserDTO> dtos=new ArrayList<UserDTO>();
		for(SystemUser user:users){
			UserDTO dto=new UserDTO();
			dto.setEmail(user.getEmail());
			dto.setName(user.getName());
			dto.setId(user.getId());
			dtos.add(dto);
		}
		
		
		UsersAndRolesDTO usersAndRolesDTO=new UsersAndRolesDTO();
		//usersAndRolesDTO.setRoleDtos(new ArrayList<RoleDTO>());
		usersAndRolesDTO.setUserDtos(dtos);
		
		
		JSONUtil.write(response, usersAndRolesDTO);
		
		
		
	}
	
	public String addRightsInEmpowerment()throws Exception{
		if(this.nodeId==0||this.currentEmpowerment==0)
			return null;
		TreeNode treeNode=this.treeService.getTreeNode(this.nodeId);
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
		JSONReader reader=new JSONReader();
		List<Long> rightsIDS=(List<Long>)reader.read(this.jsonOperations);
		Set<SystemUser> admins=new HashSet<SystemUser>();
		for(AdminPrivilegeTriple triple:empowerment.getAdminPrivilegeTriples()){
			admins.add(triple.getAdmin());
		}
		if(admins.size()==0){
			throw new Exception("添加权限前必须存在用户或者角色组！");
		}
		Set<AdminPrivilegeTriple> adminTriples=new HashSet<AdminPrivilegeTriple>();
		for(Long id:rightsIDS){
			OperationRight right=this.privilegeService.getOperationRight(id);
			for(SystemUser admin:admins){
				AdminPrivilegeTriple adminPrivilegeTriple=new AdminPrivilegeTriple();
				adminPrivilegeTriple.setEmpowerment(empowerment);
				
				try{
					adminPrivilegeTriple.setAdmin((AdminUser)admin);//可能报错
					adminPrivilegeTriple.setAdminOperationRight((AdminOperationRight)right);
				}catch (Exception e) {
					throw new  Exception("用户不是管理员或者权限非管理员权限！");
				}
				
				adminPrivilegeTriple.setTreeNode(treeNode);
				
				adminTriples.add(adminPrivilegeTriple);
				
			}		
		}
		
		this.privilegeService.addAdminWithRights2TreeNodes(adminTriples);
		
		return null;
		
	}
	
	public String addUsersRolesInEmpowerment()throws Exception{
		if(this.nodeId==0||this.currentEmpowerment==0)
			return null;
		TreeNode treeNode=this.treeService.getTreeNode(this.nodeId);
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
		
		JSONReader reader=new JSONReader();
		List<Long> userIDS=(List<Long>)reader.read(this.jsonUsers);
		
		
		//		管理员
		
		Set<SystemUser> users=new HashSet<SystemUser>();
		
		for(Long userid:userIDS){
			users.add(this.userService.getUser(userid));
		}
		
		Set<OperationRight> sumRights=new HashSet<OperationRight>();
		List<OperationRight> right1=privilegeService.getOperationRight(empowerment, AdminPrivilegeTriple.class);
		if(right1!=null)
			sumRights.addAll(right1);
		Set<AdminPrivilegeTriple> adminPrivilegeTriples=new HashSet<AdminPrivilegeTriple>();
		if(sumRights.size()==0){
			for(SystemUser user:users){
				AdminPrivilegeTriple triple=new AdminPrivilegeTriple();
				triple.setEmpowerment(empowerment);
				//triple.setUser(user);
				triple.setTreeNode(treeNode);
				//判断用户是否为admin
				if(this.userService.isAdmin(user)){
					//triple.setAdmin((AdminUser)user);
					Object proxyObj = user;
					Object  realEntity=null;
					if (user instanceof HibernateProxy) {  
					      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
					 } else{
						 realEntity=proxyObj;
					 }
					AdminUser admin=(AdminUser)realEntity;
					triple.setAdmin(admin);
				}else{
					triple.setAdmin(this.userService.changeUser2Admin(user));
				}
				
				adminPrivilegeTriples.add(triple);
			}
			privilegeService.addAdminWithRights2TreeNodes(adminPrivilegeTriples);
			
		}else{
			
			for(OperationRight right:sumRights){
				for(SystemUser user:users){
					AdminPrivilegeTriple triple=new AdminPrivilegeTriple();
					triple.setEmpowerment(empowerment);
					if(this.userService.isAdmin(user)){
						triple.setAdmin((AdminUser)user);
					}else{
						triple.setAdmin(this.userService.changeUser2Admin(user));
					}
					triple.setTreeNode(treeNode);
					triple.setAdminOperationRight((AdminOperationRight)right);
					adminPrivilegeTriples.add(triple);
				}
			}
			privilegeService.addAdminWithRights2TreeNodes(adminPrivilegeTriples);
			
		}
		
		return null;
	}
	
	
	public String listRights()throws Exception{
		if(this.nodeId==0||this.currentEmpowerment==0)
			return null;
		TreeNode treeNode=this.treeService.getTreeNode(this.nodeId);
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
	
		Set<OperationRight> selectedRights=new HashSet<OperationRight>();
		List<OperationRight> allRights=null;
		
		for(AdminPrivilegeTriple triple: empowerment.getAdminPrivilegeTriples()){
			selectedRights.add(triple.getAdminOperationRight());
		}
		allRights=privilegeService.listAllOperationRight(true);
		
		for(OperationRight right:selectedRights){
			allRights.remove(right);
		}
		//allRights.removeAll(allRights);
		writeRightDTO(new HashSet<OperationRight>(allRights));
		
		return null;
	}
	
	private void writeRightDTO(Set<OperationRight> allRights) throws IOException{
		List<OperationRightDTO> dtos=new ArrayList<OperationRightDTO>();
		for(OperationRight right:allRights){
			
			OperationRightDTO rightDTO=new OperationRightDTO();
			rightDTO.setId(right.getId());
			rightDTO.setName(right.getCode());
			dtos.add(rightDTO);
			
		}
		
		
		JSONUtil.write(response, dtos);
		

		
	}
	
	public String deleteUserInEmpowerment()throws Exception{
		if(this.currentEmpowerment==0||this.userId==0)
			return null;
		Empowerment empowerment=privilegeService.getEmpowerment(this.currentEmpowerment);
		SystemUser user=userService.getUser(this.userId);
		privilegeService.deletePrivilege(empowerment, user,true);
		return null;
	}
	
	public String deleteRightInEmpowerment()throws Exception{
		if(this.currentEmpowerment==0||this.rightId==0)
			return null;
		Empowerment empowerment=privilegeService.getEmpowerment(this.currentEmpowerment);
		OperationRight operationRight=privilegeService.getOperationRight(this.rightId);
		privilegeService.deletePrivilege(empowerment, operationRight,true);
		return null;
	}

	public long getCurrentEmpowerment() {
		return currentEmpowerment;
	}

	public void setCurrentEmpowerment(long currentEmpowerment) {
		this.currentEmpowerment = currentEmpowerment;
	}

	public String getJsonOperations() {
		return jsonOperations;
	}

	public void setJsonOperations(String jsonOperations) {
		this.jsonOperations = jsonOperations;
	}

	public String getJsonRoles() {
		return jsonRoles;
	}

	public void setJsonRoles(String jsonRoles) {
		this.jsonRoles = jsonRoles;
	}

	public String getJsonUsers() {
		return jsonUsers;
	}

	public void setJsonUsers(String jsonUsers) {
		this.jsonUsers = jsonUsers;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}
	@Autowired
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public long getRightId() {
		return rightId;
	}

	public void setRightId(long rightId) {
		this.rightId = rightId;
	}

	public TreeService getTreeService() {
		return treeService;
	}
	@Autowired
	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public UserService getUserService() {
		return userService;
	}
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setServletResponse(HttpServletResponse response) {
		
		this.response=response;
	
		
	}

	public String getEmpowermentDescription() {
		return empowermentDescription;
	}

	public void setEmpowermentDescription(String empowermentDescription) {
		this.empowermentDescription = empowermentDescription;
	}

	public String getEmpowermentName() {
		return empowermentName;
	}

	public void setEmpowermentName(String empowermentName) {
		this.empowermentName = empowermentName;
	}

	public int getAdminTree() {
		return adminTree;
	}

	public void setAdminTree(int adminTree) {
		this.adminTree = adminTree;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
