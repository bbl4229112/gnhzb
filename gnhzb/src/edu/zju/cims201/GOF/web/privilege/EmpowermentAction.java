package edu.zju.cims201.GOF.web.privilege;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;

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
import edu.zju.cims201.GOF.rs.dto.EmpowermentDTO;
import edu.zju.cims201.GOF.rs.dto.OperationRightDTO;
import edu.zju.cims201.GOF.rs.dto.TreeNodeDTO;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.rs.dto.UsersAndRolesDTO;
import edu.zju.cims201.GOF.service.privilege.PrivilegeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;


@Namespace("/privilege")
//定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "empowerment.action", type = "redirect") })

public class EmpowermentAction extends CrudActionSupport<Empowerment> implements ServletResponseAware{
	
	
	
	private long currentEmpowerment;
	
	private long nodeId;
	
	private long userId;
	
	private long roleId;
	
	private long rightId;
	
	private int adminTree;
	
	private String jsonUsers;
	private String jsonRoles;
	private String jsonOperations;
	
	
	
	private String empowermentName;
	private String empowermentDescription;
	
	
	

	
	
	private PrivilegeService privilegeService;
	
	private TreeService treeService;
	
	private UserService userService;
	
	private HttpServletResponse response;
	
	
	@Override
	protected void prepareModel() throws Exception {
		
		
	}
	

	public Empowerment getModel() {
			
		return null;
		
	}
	

	@Override
	public String delete() throws Exception {
		if(this.currentEmpowerment==0)
			return null;
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
		this.privilegeService.deleteEmpowerment(empowerment,false);
		
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO 自动生成方法存根
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO 自动生成方法存根
		return null;
	}

	

	@Override
	public String save() throws Exception {
		if(this.nodeId==0)
			return null;
		TreeNode belongNode=treeService.getTreeNode(this.nodeId);
		SystemUser creater=getUser();
		
		Empowerment empowerment=new Empowerment();
		empowerment.setBelongedNode(belongNode);
		empowerment.setCreater(creater);
		empowerment.setName(this.empowermentName);
		if(this.adminTree==0){
			empowerment.setIsAdmin(false);
		}else
			empowerment.setIsAdmin(true);
		empowerment.setEmpowermentDate(new Date());
		empowerment.setDescription(this.empowermentDescription);
		privilegeService.saveEmpowerment(empowerment);
		
		EmpowermentDTO empowermentDTO=new EmpowermentDTO();
		empowermentDTO.setId(empowerment.getId());
		empowermentDTO.setName(empowerment.getName());
		UserDTO user =new UserDTO();
		user.setEmail(empowerment.getCreater().getEmail());
		user.setName(empowerment.getCreater().getName());
		empowermentDTO.setCreater(user);
		
		JSONUtil.write(response, empowermentDTO);
		
		
		
		return null;
	}
	
	
	public String listUsersOutEmpowerment() throws Exception {
		if(this.nodeId==0||this.currentEmpowerment==0)
			return null;
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
		
		Set<SystemUser> exists=new HashSet<SystemUser>();
		Set<UserPrivilegeTriple> userTriples=empowerment.getUserPrivilegeTriples();
		for(UserPrivilegeTriple triple:userTriples){
			exists.add(triple.getUser());
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
		//andRolesDTO.setRoleDtos(dtos);
		usersAndRolesDTO.setUserDtos(dtos);
		
		
		
		JSONUtil.write(response, dtos);
		
		
		
	}
	
	private SystemUser getUser(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String email=securityContext.getAuthentication().getName();
		SystemUser user=userService.getUser(email);
		return user;
	}
	
	public String addRightsInEmpowerment()throws Exception{
		if(this.nodeId==0||this.currentEmpowerment==0)
			return null;
		TreeNode treeNode=this.treeService.getTreeNode(this.nodeId);
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
		JSONReader reader=new JSONReader();
		List<Long> rightsIDS=(List<Long>)reader.read(this.jsonOperations);
		
		
		Set<SystemUser> users=new HashSet<SystemUser>();
		Set<TreeNode> nodes=new HashSet<TreeNode>();
		for(UserPrivilegeTriple triple:empowerment.getUserPrivilegeTriples()){
			users.add(triple.getUser());
		}
		for(RolePrivilegeTriple triple:empowerment.getRolePrivilegeTriples()){
			nodes.add(triple.getRoleTreeNode());
		}
		if(users.size()==0&&nodes.size()==0){
			throw new Exception("添加权限前必须存在用户或者角色组！");
		}
		
		Set<UserPrivilegeTriple> userTriples=new HashSet<UserPrivilegeTriple>();
		Set<RolePrivilegeTriple> roleTriples=new HashSet<RolePrivilegeTriple>();
		
		for(Long id:rightsIDS){
			OperationRight right=this.privilegeService.getOperationRight(id);
			for(SystemUser user:users){
				UserPrivilegeTriple userPrivilegeTriple=new UserPrivilegeTriple();
				userPrivilegeTriple.setEmpowerment(empowerment);
				userPrivilegeTriple.setUser(user);
				userPrivilegeTriple.setCdTreeNode(treeNode);
				userPrivilegeTriple.setOperationRight(right);
				userTriples.add(userPrivilegeTriple);
				
			}		
		}
		
		for(Long id:rightsIDS){
			OperationRight right=this.privilegeService.getOperationRight(id);
			for(TreeNode node:nodes){
				RolePrivilegeTriple rolePrivilegeTriple=new RolePrivilegeTriple();
				rolePrivilegeTriple.setEmpowerment(empowerment);
				rolePrivilegeTriple.setOperationRight(right);
				rolePrivilegeTriple.setRoleTreeNode(node);
				rolePrivilegeTriple.setCdTreeNode(treeNode);
				roleTriples.add(rolePrivilegeTriple);
			}		
		}
		
		this.privilegeService.addRoleWithRights2TreeNodes(roleTriples);
		this.privilegeService.addUserWithRights2TreeNodes(userTriples);
		
		
		
		
		return null;
		
	}
	
	
	
	public String addUsersRolesInEmpowerment()throws Exception{
		
		if(this.nodeId==0||this.currentEmpowerment==0)
			return null;
		TreeNode treeNode=this.treeService.getTreeNode(this.nodeId);
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
		
		JSONReader reader=new JSONReader();
		List<Long> roleIDS=(List<Long>)reader.read(this.jsonRoles);
		List<Long> userIDS=(List<Long>)reader.read(this.jsonUsers);
		
		Set<SystemUser> users=new HashSet<SystemUser>();
		Set<TreeNode> nodes=new HashSet<TreeNode>();
		for(Long userid:userIDS){
			users.add(this.userService.getUser(userid));
		}
		for(Long roleid:roleIDS){
			nodes.add(this.treeService.getTreeNode(roleid));
		}
		
		
		Set<OperationRight> sumRights=new HashSet<OperationRight>();
		List<OperationRight> right1=privilegeService.getOperationRight(empowerment, RolePrivilegeTriple.class);
		List<OperationRight> right2=privilegeService.getOperationRight(empowerment, UserPrivilegeTriple.class);
		sumRights.addAll(right1);
		sumRights.addAll(right2);
		Set<UserPrivilegeTriple> userPrivilegeTriples=null;
		Set<RolePrivilegeTriple> rolePrivilegeTriples=null;
		if(sumRights.size()==0){
			
			userPrivilegeTriples=new HashSet<UserPrivilegeTriple>();
			for(SystemUser user:users){
				UserPrivilegeTriple triple=new UserPrivilegeTriple();
				triple.setEmpowerment(empowerment);
				triple.setUser(user);
				triple.setCdTreeNode(treeNode);
				userPrivilegeTriples.add(triple);
			}
			privilegeService.addUserWithRights2TreeNodes(userPrivilegeTriples);
			
			rolePrivilegeTriples=new HashSet<RolePrivilegeTriple>();
			for(TreeNode node:nodes){
				RolePrivilegeTriple triple=new RolePrivilegeTriple();
				triple.setEmpowerment(empowerment);
				triple.setRoleTreeNode(node);
				triple.setCdTreeNode(treeNode);
				rolePrivilegeTriples.add(triple);
			}
			privilegeService.addRoleWithRights2TreeNodes(rolePrivilegeTriples);
			
		}else {
			rolePrivilegeTriples=new HashSet<RolePrivilegeTriple>();
			for(OperationRight right:sumRights){
				for(TreeNode node:nodes){
					RolePrivilegeTriple triple=new RolePrivilegeTriple();
					triple.setEmpowerment(empowerment);
					triple.setRoleTreeNode(node);
					triple.setCdTreeNode(treeNode);
					triple.setOperationRight(right);
					rolePrivilegeTriples.add(triple);
				}
			}
			privilegeService.addRoleWithRights2TreeNodes(rolePrivilegeTriples);
			
			
			userPrivilegeTriples=new HashSet<UserPrivilegeTriple>();
			for(OperationRight right:sumRights){
				for(SystemUser user:users){
					UserPrivilegeTriple triple=new UserPrivilegeTriple();
					triple.setEmpowerment(empowerment);
					triple.setUser(user);
					triple.setCdTreeNode(treeNode);
					triple.setOperationRight(right);
					userPrivilegeTriples.add(triple);
				}
			}
			privilegeService.addUserWithRights2TreeNodes(userPrivilegeTriples);
				
			
		}
		
		
		
		
		return null;
	}
	
	
	public String listRoleTreeNodesWithSelect()throws Exception{
		if(this.nodeId==0||this.currentEmpowerment==0)
			return null;
		Empowerment empowerment=privilegeService.getEmpowerment(this.currentEmpowerment);
		Set<RolePrivilegeTriple> roleTriple=empowerment.getRolePrivilegeTriples();
		Set<TreeNode> exist=new HashSet<TreeNode>();
		for(RolePrivilegeTriple triple:roleTriple){
			exist.add(triple.getRoleTreeNode());
		}
		
		List<TreeNode> roots=treeService.listRootTreeNodes(RoleTreeNode.class,false);
		
		listTreeNodes(roots,exist);
		
		
		
		
		return null;
	}
	
	
	private void listTreeNodes(List<TreeNode> list,Set<TreeNode> exist)throws Exception {
		JSONWriter writer = new JSONWriter();
		List<TreeNodeDTO> dtos=new ArrayList<TreeNodeDTO>();
		for(TreeNode treeNode:list){
			dtos.add(generateDTO(treeNode,exist));
			
		}
		Collections.sort(dtos);
		
		JSONUtil.write(response, dtos);
	
	
	}
	
	private TreeNodeDTO generateDTO(TreeNode treeNode,Set<TreeNode> exist){
		TreeNodeDTO treeNodeDTO=new TreeNodeDTO();
		treeNodeDTO.setId(treeNode.getId());
		treeNodeDTO.setName(treeNode.getNodeName());
		if(exist.contains(treeNode))
			treeNodeDTO.setChecked(1);
		Set<TreeNode> set=treeNode.getSubNodes();
		if(set.size()!=0){
			ArrayList<TreeNodeDTO> arrayList=new ArrayList<TreeNodeDTO>();
			for(TreeNode child:set){
				arrayList.add(generateDTO(child,exist));
			}
			Collections.sort(arrayList);
			treeNodeDTO.setChildren(arrayList);
		}
		
		
		return treeNodeDTO;
	}
	
	
	public String listRights()throws Exception{
		
		if(this.nodeId==0||this.currentEmpowerment==0)
			return null;
		TreeNode treeNode=this.treeService.getTreeNode(this.nodeId);
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
	
		Set<OperationRight> selectedRights=new HashSet<OperationRight>();
		List<OperationRight> allRights=null;
		
		for(RolePrivilegeTriple triple: empowerment.getRolePrivilegeTriples()){
			selectedRights.add(triple.getOperationRight());
		}
		
		for(UserPrivilegeTriple triple:empowerment.getUserPrivilegeTriples()){
			selectedRights.add(triple.getOperationRight());	
		}
	
		allRights=privilegeService.listAllOperationRight(false);
		
		for(OperationRight right:selectedRights){
			allRights.remove(right);
		}
 
		//allRights.removeAll(selectedRights);
		
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
	
	
	
	public String deleteRoleInEmpowerment()throws Exception{
		if(this.currentEmpowerment==0||this.roleId==0)
			return null;
		Empowerment empowerment=privilegeService.getEmpowerment(this.currentEmpowerment);
		TreeNode node=treeService.getTreeNode(this.roleId);
		
		privilegeService.deletePrivilege(empowerment, node);
		
		return null;
	}
	
	public String deleteUserInEmpowerment()throws Exception{
		if(this.currentEmpowerment==0||this.userId==0)
			return null;
		Empowerment empowerment=privilegeService.getEmpowerment(this.currentEmpowerment);
		SystemUser user=userService.getUser(this.userId);
		
		privilegeService.deletePrivilege(empowerment, user,false);
		
		
		
		
		
		return null;
	}
	
	public String deleteRightInEmpowerment()throws Exception{
		if(this.currentEmpowerment==0||this.rightId==0)
			return null;
		Empowerment empowerment=privilegeService.getEmpowerment(this.currentEmpowerment);
		OperationRight operationRight=privilegeService.getOperationRight(this.rightId);
		privilegeService.deletePrivilege(empowerment, operationRight,false);
	
		
		
		
		return null;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}


	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	@Autowired
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}


	


	public long getCurrentEmpowerment() {
		return currentEmpowerment;
	}

	public void setCurrentEmpowerment(long currentEmpowerment) {
		this.currentEmpowerment = currentEmpowerment;
	}

	public long getRightId() {
		return rightId;
	}


	public void setRightId(long rightId) {
		this.rightId = rightId;
	}


	public long getRoleId() {
		return roleId;
	}


	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
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

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
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

	

	

	public String getJsonOperations() {
		return jsonOperations;
	}

	public void setJsonOperations(String jsonOperations) {
		this.jsonOperations = jsonOperations;
	}

	public int getAdminTree() {
		return adminTree;
	}

	public void setAdminTree(int adminTree) {
		this.adminTree = adminTree;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	

}
