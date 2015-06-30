package edu.zju.cims201.GOF.web.privilege;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import org.stringtree.json.JSONWriter;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Empowerment;
import edu.zju.cims201.GOF.hibernate.pojo.OperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.RolePrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.UserPrivilegeTriple;
import edu.zju.cims201.GOF.rs.dto.EmpowermentDTO;
import edu.zju.cims201.GOF.rs.dto.OperationRightDTO;
import edu.zju.cims201.GOF.rs.dto.RoleDTO;
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
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "assignprivilege.action", type = "redirect") })
public class AssignprivilegeAction  extends ActionSupport implements ServletResponseAware{
	
	private HttpServletResponse response;
	
	private long nodeId;
	
	private long currentEmpowerment;
	
	private int adminTree;
	

	private String type;
	
	private TreeService treeService;
	
	private PrivilegeService privilegeService;
	
	private UserService userService;
	
	
	
	
	
	

	public String listEmpowerment() throws Exception{
		
		if(this.nodeId==0)
			return null;
		
		TreeNode node=treeService.getTreeNode(this.nodeId);
		SystemUser user=getUser();
		List<Empowerment> empowerments=null;
		
		
		
		if(this.adminTree==0){
			empowerments=privilegeService.listEmpowerments(node,false);
		}else{
			empowerments=privilegeService.listEmpowerments(node,true);
		}
	
		
		
		
		writeEmpowermentDTO(new HashSet<Empowerment>(empowerments));
		
		
		return null;
	}
	
	public String listUserAndRole() throws Exception{
		
		
		if(currentEmpowerment==0)
			return null;
		Empowerment empowerment=this.privilegeService.getEmpowerment(currentEmpowerment);
		
		
		Set<RolePrivilegeTriple> rolePrivilegeTriples=null;
		Set<UserPrivilegeTriple> userPrivilegeTriples=null;
		Set<AdminPrivilegeTriple> adminPrivilegeTriples=null;
		Set<TreeNode> roles=new HashSet<TreeNode>();
		Set<SystemUser> users=new HashSet<SystemUser>();
		if(this.adminTree==0){
			rolePrivilegeTriples=empowerment.getRolePrivilegeTriples();
			userPrivilegeTriples=empowerment.getUserPrivilegeTriples();
			for(RolePrivilegeTriple triple:rolePrivilegeTriples){
				roles.add(triple.getRoleTreeNode());
			}
			for(UserPrivilegeTriple triple:userPrivilegeTriples){
				users.add(triple.getUser());
			}
		}else{
			adminPrivilegeTriples=empowerment.getAdminPrivilegeTriples();
			for(AdminPrivilegeTriple triple:adminPrivilegeTriples){
				users.add(triple.getAdmin());
			}
		}
		List<UserDTO> userDtos=new ArrayList<UserDTO>();
		for(SystemUser user:users){
			UserDTO userDTO=new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setEmail(user.getEmail());
			userDTO.setName(user.getName());
			userDtos.add(userDTO);
		}
		Collections.sort(userDtos);
		
		List<RoleDTO> roleDtos=new ArrayList<RoleDTO>();
		for(TreeNode node:roles){
			RoleDTO roleDTO=new RoleDTO();
			roleDTO.setId(node.getId());
			roleDTO.setName(node.getNodeName());
			roleDtos.add(roleDTO);
		}
		Collections.sort(roleDtos);
		
		UsersAndRolesDTO andRolesDTO=new UsersAndRolesDTO();
		andRolesDTO.setRoleDtos(roleDtos);
		andRolesDTO.setUserDtos(userDtos);
		
		JSONUtil.write(response, andRolesDTO);
		
		
		
		
		
		
		
		
		return null;
	}
	
	
	public String listSelectedRights() throws Exception{
		
		if(this.currentEmpowerment==0)
			return null;
		Empowerment empowerment=this.privilegeService.getEmpowerment(this.currentEmpowerment);
		
		Set<OperationRight> rights=new HashSet<OperationRight>();
		if(this.adminTree==0){
			List<OperationRight> roleRight=this.privilegeService.getOperationRight(empowerment, RolePrivilegeTriple.class);
			List<OperationRight> userRight=this.privilegeService.getOperationRight(empowerment, UserPrivilegeTriple.class);
			
			if(roleRight!=null){
				for(OperationRight operationRight:roleRight){
					rights.add(operationRight);
				}
			}
			if(userRight!=null){
				for(OperationRight operationRight:userRight){
					rights.add(operationRight);
				}
			}
			
			
		}else{
			List<OperationRight> adminRight=this.privilegeService.getOperationRight(empowerment, AdminPrivilegeTriple.class);
			if(adminRight!=null)
				rights.addAll(adminRight);
		}
		
		
		writeOperationRightDTO(rights);
		return null;
	}
	
	
	
	private void writeOperationRightDTO(Set<OperationRight> rights) throws IOException{
		ArrayList<OperationRightDTO> dtos=new ArrayList<OperationRightDTO>();
		for(OperationRight right:rights){
			OperationRightDTO rightDTO=new OperationRightDTO();
			rightDTO.setId(right.getId());
			rightDTO.setName(right.getCode());
			dtos.add(rightDTO);
		}
		Collections.sort(dtos);
		
		JSONUtil.write(response, dtos);
		
		
	}
	
	
	private SystemUser getUser(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String email=securityContext.getAuthentication().getName();
		SystemUser user=userService.getUser(email);
		return user;
	}
	
	private void writeEmpowermentDTO(Set<Empowerment> empowerments) throws IOException{
		
		ArrayList<EmpowermentDTO> dtos=new ArrayList<EmpowermentDTO>();
		
		for(Empowerment empowerment:empowerments){
			UserDTO createrdot=new UserDTO();
			createrdot.setName(empowerment.getCreater().getName());
			createrdot.setEmail(empowerment.getCreater().getEmail());
			EmpowermentDTO empowermentDTO=new EmpowermentDTO();
			empowermentDTO.setId(empowerment.getId());
			empowermentDTO.setName(empowerment.getName());
			empowermentDTO.setCreater(createrdot);
			dtos.add(empowermentDTO);
			
			
		}
		Collections.sort(dtos);
		
		JSONUtil.write(response, dtos);
		
		
		
	}
	
	
	
	
	
	
	
	
	private void listTreeNodes(List<TreeNode> list)throws Exception {
		JSONWriter writer = new JSONWriter();
		List<TreeNodeDTO> dtos=new ArrayList<TreeNodeDTO>();
		for(TreeNode treeNode:list){
			dtos.add(generateDTO(treeNode));
			
		}
		String treeString=writer.write(dtos);
		response.getWriter().print(treeString);
		System.out.println(treeString);
	
	}
	private TreeNodeDTO generateDTO(TreeNode treeNode){
		TreeNodeDTO treeNodeDTO=new TreeNodeDTO();
		treeNodeDTO.setId(treeNode.getId());
		treeNodeDTO.setName(treeNode.getNodeName());
		Set<TreeNode> set=treeNode.getSubNodes();
		if(set.size()!=0){
			ArrayList<TreeNodeDTO> arrayList=new ArrayList<TreeNodeDTO>();
			for(TreeNode child:treeNode.getSubNodes()){
				arrayList.add(generateDTO(child));
			}
			treeNodeDTO.setChildren(arrayList);
		}
		
		
		return treeNodeDTO;
	}
	
	
	
	public TreeService getTreeService() {
		return treeService;
	}



	@Autowired
	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}
	
	
	




	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}



	@Autowired
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
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
	
	
	
	

	


	

	public int getAdminTree() {
		return adminTree;
	}

	public void setAdminTree(int adminTree) {
		this.adminTree = adminTree;
	}

	public long getCurrentEmpowerment() {
		return currentEmpowerment;
	}

	public void setCurrentEmpowerment(long currentEmpowerment) {
		this.currentEmpowerment = currentEmpowerment;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

	
	
	
	

}
