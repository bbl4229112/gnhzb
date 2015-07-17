package edu.zju.cims201.GOF.web.user.role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.web.struts2.Struts2Utils;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;

import edu.zju.cims201.GOF.dao.HibernateUtils;

import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.UserPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.UserRoleAssociation;
import edu.zju.cims201.GOF.hibernate.pojo.UserRolePK;
import edu.zju.cims201.GOF.rs.dto.TreeNodeDTO;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.ServiceException;

import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 * 
 * @author calvin
 */
//定义URL映射对应/account/user.action
@Namespace("/user/role")
//定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "userole.action", type = "redirect") })
public class UseroleAction extends CrudActionSupport<TreeNode> implements ServletResponseAware{

	private static final long serialVersionUID =  8683878162525847073L;

	private HttpServletResponse response;
	
	private TreeService treeService;
	private UserService userService;
	
	
	private int size=5;
	private int index=0;
	
	private String searchName;
	
	private long id; 
	
	private long userid1;
	
	private long userid2;
	
	
	
	
	
	private String userIds;

	//-- ModelDriven 与 Preparable函数 --//
	

	public TreeNode getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		
		return null;
	}
	@Override
	public String input() throws Exception {
		SystemUser user=this.userService.getUser(new Long(1));
		//System.out.println(user.getEmail()+user.getPassword()+"sss");
		
		
		TreeNode node=this.treeService.getTreeNode(new Long(2));
	//	System.out.println(node.getNodeName()+"sss");
		return INPUT;
	}
	
	public String update() throws Exception {
		
		
		return null;
	}
	@Override
	public String save() throws Exception {
		
		
		return null;
	}

	@Override
	public String delete() throws Exception {
				
		return null;

	}
	
	
	
	/**
	 * 添加用户和角色树关系
	 * @return
	 * @throws Exception
	 */
	
	public String saveRoleUserRelation()throws Exception{
		if(this.id==0)
			return null;
		if(this.userIds==null||this.userIds.equals(""))
			return null;
		JSONReader reader=new JSONReader();
		List<Long> userids=(List<Long>)reader.read(userIds);
		RoleTreeNode roleTreeNode=(RoleTreeNode)treeService.getTreeNode(this.id);
		List<SystemUser> users=userService.listUsers(new HashSet<Long>(userids));
		for(SystemUser systemUser:users){
			//roleTreeNode.getUsers().add(systemUser);
			UserRolePK pk=new UserRolePK(systemUser.getId(),this.id);
			UserRoleAssociation association=new UserRoleAssociation(pk);
			association.setOrderId(systemUser.getId());
			roleTreeNode.getAssociations().add(association);
		}
		treeService.saveRoleTreeNode(roleTreeNode);
		
		
		return null;
	}
	
	
	/**
	 * 列出某角色树节点下的所有用户
	 * @return
	 * @throws Exception
	 */
	public String listUsersUnderRole() throws Exception{
		
		Page<UserRoleAssociation> pageAssociation=null;
		if(this.size==0)
			 pageAssociation=new Page<UserRoleAssociation>(10);
		else
			pageAssociation=new Page<UserRoleAssociation>(this.size);
		pageAssociation.setPageNo(this.index+1);
		TreeNode roleTreeNode=treeService.getTreeNode(this.id);
		if(roleTreeNode==null)
			return null;
		Page<UserRoleAssociation> newpage=treeService.listRTreeUsers((RoleTreeNode)roleTreeNode, false, pageAssociation);
		
		
		makePageDTO(newpage);
		
		
		return null;
		
	}
	
	/**
	 * 列出某节点下的所有用户，不包括当前用户（根据登录情况判断）
	 * @return
	 * @throws Exception
	 */
	
	public String listUsersUnderRoleWithoutSelf() throws Exception{
		//System.out.println("size==="+size);
		Page<UserRoleAssociation> pageAssociation=null;
		if(this.size==0)
			 pageAssociation=new Page<UserRoleAssociation>(10);
		else
			pageAssociation=new Page<UserRoleAssociation>(this.size);
		pageAssociation.setPageNo(this.index+1);
		Page<UserRoleAssociation> newpageAssociation=null;
		TreeNode roleTreeNode=treeService.getTreeNode(this.id);
		if(roleTreeNode==null)
			return null;
		
		Set<SystemUser> user=new HashSet<SystemUser>();
		user.add(this.userService.getUser());
		newpageAssociation=treeService.listRTreeUsers((RoleTreeNode)roleTreeNode, user, pageAssociation);
		
		
		makePageDTO(newpageAssociation);
		
		
		return null;
		
	}
	
	/**
	 * 列出某知识域节点下所有的域管理员(不包括本人)
	 * @return
	 * @throws Exception
	 */
	
	public String listDomainAdministrators() throws Exception{
		
		Set<AdminPrivilegeTriple> userPrivilegeTriples=null;
		Set<SystemUser> users=new HashSet<SystemUser>();
		TreeNode domainTreeNode=treeService.getTreeNode(this.id);
		userPrivilegeTriples = domainTreeNode.getAdminPrivilegeTriples();
		for(AdminPrivilegeTriple triple:userPrivilegeTriples){
			AdminUser adminuser = triple.getAdmin();
			if(!users.contains(adminuser)){
				users.add(adminuser);
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
		JSONUtil.write(response, userDtos);
		return null;
		
	}
	/**
	 * 根据searchname查询某个角色树节点下的所有用户，如果searchname为空默认列出角色树下的所有用户
	 * @return
	 * @throws Exception
	 */
	
	public String listRoleUsers() throws Exception{


		Page<SystemUser> page=null;
		if(this.size==0)
			 page=new Page<SystemUser>(10);
		else
			page=new Page<SystemUser>(this.size);
		page.setPageNo(this.index+1);
		Page<SystemUser> newpage=null;
		
		RoleTreeNode roleTreeNode=(RoleTreeNode)treeService.getTreeNode(this.id);
		if(roleTreeNode==null)
			return null;
		
		if(this.searchName==null||this.searchName.equals("")){
			newpage=userService.searchRoleUsers(null, roleTreeNode, false, page);	
		}
			
		else{
			newpage=userService.searchRoleUsers(this.searchName, roleTreeNode, false, page);
		}
		makeUserPageDTO(newpage);
		
		return null;
	}
	
	/**
	 * 删除角色和用户关系
	 * @return
	 * @throws Exception
	 */
	
	
	public String deleteRoleUserRelation() throws Exception{
		
		if(this.id==0)
			return null;
		if(this.userIds==null||this.userIds.equals(""))
			return null;
		JSONReader reader=new JSONReader();
		List<Long> userids=(List<Long>)reader.read(userIds);
		TreeNode roleTreeNode=treeService.getTreeNode(this.id);
		
		List<SystemUser> users = userService.listUsers(new HashSet<Long>(userids));
		treeService.deleteUserRelation((RoleTreeNode)roleTreeNode,new HashSet<SystemUser>(users));
		
		return null;
	}
	
	/**
	 * 查询不在角色树中的用户
	 * @return
	 * @throws Exception
	 */
	
	public String listNotInRoleUsers() throws Exception{
		
		Page<SystemUser> page=null;
		if(this.size==0)
			 page=new Page<SystemUser>(10);
		else
			page=new Page<SystemUser>(this.size);
		page.setPageNo(this.index+1);
		Page<SystemUser> newpage=null;
		
		RoleTreeNode roleTreeNode=(RoleTreeNode)treeService.getTreeNode(this.id);
		if(roleTreeNode==null)
			return null;
		
		if(this.searchName==null||this.searchName.equals(""))
			newpage=userService.searchRoleUsers(null, roleTreeNode, true, page);
		else
			newpage=userService.searchRoleUsers(this.searchName, roleTreeNode, true, page);
		
		makeUserPageDTO(newpage);
		
		return null;
	}
	
	private void makePageDTO(Page<UserRoleAssociation> page)throws Exception{
		PageDTO pagedto=new PageDTO();
		
		pagedto.setTotal(page.getTotalCount());
		pagedto.setPagesize(page.getPageSize());
		pagedto.setTotalPage(page.getTotalPages());
		//pagedto.
		//pagedto.setData(new ArrayList());
		ArrayList list = new ArrayList();
		for(UserRoleAssociation association:page.getResult()){
			if(association.getPk().getUserId()!=0){
				UserDTO dto=new UserDTO();
				SystemUser user = this.userService.getUser(new Long(association.getPk().getUserId()));
				dto.setEmail(user.getEmail());
				dto.setName(user.getName());
				dto.setId(user.getId());
				dto.setOrderId(association.getOrderId());
				list.add(dto);
			}	
		}
		Collections.sort(list);
		pagedto.setData(list);
		JSONUtil.write(response, pagedto);
		
	}
	
	
	private void makeUserPageDTO(Page<SystemUser> page)throws Exception{
		PageDTO pagedto=new PageDTO();
		
		pagedto.setTotal(page.getTotalCount());
		pagedto.setPagesize(page.getPageSize());
		pagedto.setTotalPage(page.getTotalPages());
		pagedto.setData(new ArrayList());
		List<SystemUser> list = page.getResult();
		for(SystemUser user:list){
			UserDTO dto=new UserDTO();
			dto.setEmail(user.getEmail());
			dto.setName(user.getName());
			dto.setId(user.getId());
			pagedto.getData().add(dto);
		}
		
		JSONUtil.write(response, pagedto);
		
	}
	
	
	public void swap(){
		
		if(this.userid1==0||this.userid2==0||this.id==0){
			return;
		}
		UserRoleAssociation association1=this.treeService.findbyID(userid1, id);
		UserRoleAssociation association2=this.treeService.findbyID(userid2, id);
		this.treeService.orderSwapAssociation(association1, association2);
		
		
	}
	


	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
		
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
	



	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public long getUserid1() {
		return userid1;
	}

	public void setUserid1(long userid1) {
		this.userid1 = userid1;
	}

	public long getUserid2() {
		return userid2;
	}

	public void setUserid2(long userid2) {
		this.userid2 = userid2;
	}


	
	
	
	
	
	
	

	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
