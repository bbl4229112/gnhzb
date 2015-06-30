package edu.zju.cims201.GOF.web.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.stringtree.json.JSONWriter;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.OperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.CategoryRootDTO;
import edu.zju.cims201.GOF.rs.dto.TreeNodeDTO;
import edu.zju.cims201.GOF.service.privilege.PrivilegeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;


@Namespace("/tree")
public class PrivilegeTreeAction extends ActionSupport implements ServletResponseAware{
	
	private String treeType;
	
	private String operationName;
	
	private boolean disableInte;
	
	public boolean isDisableInte() {
		return disableInte;
	}

	public void setDisableInte(boolean disableInte) {
		this.disableInte = disableInte;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1046248125123603836L;
	private UserService userService;
	private TreeService treeService;
	
	private PrivilegeService privilegeService;
	private HttpServletResponse response;
	
	public String listAdminTreeNodes()throws Exception{
		

		return null;
	}
	
	private void listTreeNodes(List<TreeNode> list)throws Exception {
		
		List<TreeNodeDTO> dtos=new ArrayList<TreeNodeDTO>();
		for(TreeNode treeNode:list){
			if(disableInte)
			{
			if(!treeNode.getId().toString().equals(Constants.QUALITYDOMAINID.toString())&&!treeNode.getId().toString().equals(Constants.DANDIANDOMAINID.toString()))
			{
				dtos.add(generateDTO(treeNode));	
			}	
				
			}else
			{
			dtos.add(generateDTO(treeNode));
			}
		}
		Collections.sort(dtos);
		
		JSONUtil.write(response, dtos);
		
	}
	
	private TreeNodeDTO generateDTO(TreeNode treeNode){
		TreeNodeDTO treeNodeDTO=new TreeNodeDTO();
		treeNodeDTO.setId(treeNode.getId());
		treeNodeDTO.setName(treeNode.getNodeName());
		treeNodeDTO.setOrderId(treeNode.getOrderId());
		
		if(this.treeType.equals("roleTree")){
			treeNodeDTO.setIcon(Constants.roleTreeIcon);
			
				
		}else if(this.treeType.equals("domainTree")){
			treeNodeDTO.setIcon(Constants.domianTreeIcon);
				
		}else if(this.treeType.equals("categoryTree")){
			treeNodeDTO.setIcon(Constants.categoryTreeIcon);
				
		}
		if(treeNodeDTO.getIcon()==null)
			treeNodeDTO.setIcon("e-tree-folder");
		
		Set<TreeNode> set=treeNode.getSubNodes();
		if(set.size()!=0){
			ArrayList<TreeNodeDTO> arrayList=new ArrayList<TreeNodeDTO>();
			for(TreeNode child:set){
				arrayList.add(generateDTO(child));
			}
			Collections.sort(arrayList);
			treeNodeDTO.setChildren(arrayList);
		}else{
			if(this.treeType.equals("roleTree")){
				treeNodeDTO.setIcon(Constants.roleTreeChildIcon);
			}else if(this.treeType.equals("domainTree")){
				treeNodeDTO.setIcon(Constants.domianTreeChildIcon);
			}else if(this.treeType.equals("categoryTree")){
				treeNodeDTO.setIcon(Constants.categoryTreeChildIcon);
			}
			
		}
		
		
		return treeNodeDTO;
	}
	

	
	
	
	
	private Set<String> getAuthoritiesTriplePart(Pattern pattern){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Collection<GrantedAuthority > authorities=securityContext.getAuthentication().getAuthorities();
		Set<String> result=new HashSet<String>();
		for(GrantedAuthority ga:authorities){
			String temp=ga.getAuthority();
			Matcher matcher=pattern.matcher(temp);
			if(matcher.find()){
				result.add(matcher.group());
			}
		}
		return result;
		
		
		
		
	}
	
	
	/**
	 * 不遍历子节点
	 * @param nodes
	 */
	
	private void writeDirectNodes(List<TreeNode> nodes){
		
		
		List<CategoryRootDTO> list=new ArrayList<CategoryRootDTO>();
		for(TreeNode node:nodes){
			if(disableInte)
			{
			if(!node.getId().toString().equals(Constants.QUALITYDOMAINID.toString())&&!node.getId().toString().equals(Constants.DANDIANDOMAINID.toString()))
			{
				CategoryRootDTO rootDTO=new CategoryRootDTO();
				rootDTO.setName(node.getNodeName());
				rootDTO.setNodeId(node.getId());
				rootDTO.setIndex("cdTree");
				list.add(rootDTO);	
			}	
				
			}else
			{
				CategoryRootDTO rootDTO=new CategoryRootDTO();
				rootDTO.setName(node.getNodeName());
				rootDTO.setNodeId(node.getId());
				rootDTO.setIndex("cdTree");
				list.add(rootDTO);
			}
			
			
			
		}
		
		JSONUtil.write(response, list);
		
	}
	
	
	/**
	 * 读出有权限的根domain节点
	 * @return
	 * @throws Exception
	 */
	
	public String listTreeRoots() throws Exception{
		SecurityContext securityContext = SecurityContextHolder.getContext();
		SystemUser user=this.userService.getUser(securityContext.getAuthentication().getName());
		if(user==null)
			return null;
		if(user.getEmail().equals(Constants.SUPERADMIN)){
			if(null!=treeType&&treeType.equals("domainTree")){
			List<TreeNode> list=treeService.listRootTreeNodes(DomainTreeNode.class,disableInte);
			writeDirectNodes(list);
			}
			if(null!=treeType&&treeType.equals("categoryTree")){
			List<TreeNode> list=treeService.listRootTreeNodes(CategoryTreeNode.class,false);
			writeDirectNodes(list);
			}
		
			return null;
		}
		
		
		//		处理普通情况		
		if(this.operationName==null)
			return null;
		OperationRight operationRight=this.privilegeService.getOperationRight(this.operationName);
		if(operationRight==null)
			return null;
		String rightId=operationRight.getId().toString();
		String reg="(?<=(\\b(R|A|U)_\\d{0,1000}_))\\d+(?=_"+rightId+"\\b)";
		Pattern pattern=Pattern.compile(reg);
		Set<String> nodeIds=getAuthoritiesTriplePart(pattern);
		
		
		Set<TreeNode> nodes=new HashSet<TreeNode>();
		for(String id:nodeIds){
			TreeNode node=this.treeService.getTreeNode(Long.valueOf(id));
			//node 可能为hibernate 代理对象，懒加载
			//导致 instanceof 不可用
			Object proxyObj = node;
			Object  realEntity=null;
			if (proxyObj instanceof HibernateProxy) {  
			      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
			 } else{
				 realEntity=proxyObj;
			 }
			
			
			if(realEntity instanceof DomainTreeNode&&treeType.equals("domainTree")){
				
				nodes.add((DomainTreeNode)realEntity);
			}
	         if(realEntity instanceof CategoryTreeNode&&treeType.equals("categoryTree")){
				
				nodes.add((CategoryTreeNode)realEntity);
			}
		}
		
		Set<TreeNode> rootNodes=cleanTreeNodesRoot(nodes);
		writeDirectNodes(new ArrayList<TreeNode>(rootNodes));
		return null;
		
	}
	
	
	public String listPrivilegeTreeNodes() throws Exception{
		
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		SystemUser user=this.userService.getUser(securityContext.getAuthentication().getName());
		if(user==null)
			return null;
		//处理超级管理员
		if(user.getEmail().equals(Constants.SUPERADMIN)){
			List<TreeNode> nodes=new ArrayList<TreeNode>();
			if(this.treeType.equals("roleTree")){
				
				List<TreeNode> list=treeService.listRootTreeNodes(RoleTreeNode.class,false);
				nodes.addAll(list);		
				
			}else if(this.treeType.equals("domainTree")){		
				List<TreeNode> list=treeService.listRootTreeNodes(DomainTreeNode.class,disableInte);
				nodes.addAll(list);			
			}else if(this.treeType.equals("categoryTree")){			
				List<TreeNode> list=treeService.listRootTreeNodes(CategoryTreeNode.class,false);
				nodes.addAll(list);
					
			}
			listTreeNodes(nodes);
			return null;
			
		}
		
		//处理普通情况		
		if(this.operationName==null||this.treeType==null)
			return null;
		OperationRight operationRight=this.privilegeService.getOperationRight(this.operationName);
		if(operationRight==null)
			return null;
		String rightId=operationRight.getId().toString();
		String reg="(?<=(\\b(R|A|U)_\\d{0,1000}_))\\d+(?=_"+rightId+"\\b)";
		Pattern pattern=Pattern.compile(reg);
		Set<String> nodeIds=getAuthoritiesTriplePart(pattern);
		
		Set<TreeNode> nodes=new HashSet<TreeNode>();
		for(String id:nodeIds){
			TreeNode node=this.treeService.getTreeNode(Long.valueOf(id));
			//node 可能为hibernate 代理对象，懒加载
			//导致 instanceof 不可用
			Object proxyObj = node;
			Object  realEntity=null;
			if (proxyObj instanceof HibernateProxy) {  
			      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
			 } else{
				 realEntity=proxyObj;
			 }
			
			
			if(this.treeType.equals("roleTree")&&(realEntity instanceof RoleTreeNode)){
				nodes.add((RoleTreeNode)realEntity);
			}else if(this.treeType.equals("domainTree")&&(realEntity instanceof DomainTreeNode)){
				nodes.add((DomainTreeNode)realEntity);
			}else if(this.treeType.equals("categoryTree")&&(realEntity instanceof CategoryTreeNode)){
				nodes.add((CategoryTreeNode)realEntity);
			}
			
		}
		
		Set<TreeNode> rootNodes=cleanTreeNodesRoot(nodes);
		
		listTreeNodes(new ArrayList<TreeNode>(rootNodes));
		return null;
		
		
		
		
	}
	
	public String listCDTreeNodes() throws Exception{

			List<TreeNode> nodes=new ArrayList<TreeNode>();
			if(this.treeType.equals("domainTree")){		
				List<TreeNode> list=treeService.listRootTreeNodes(DomainTreeNode.class,disableInte);
				nodes.addAll(list);			
			}			
			if(this.treeType.equals("categoryTree")){			
				List<TreeNode> list=treeService.listRootTreeNodes(CategoryTreeNode.class,false);
				nodes.addAll(list);				
			}
			listTreeNodes(nodes);
			return null;
	
	}

	
	/**
	 * 清除子树，找出“根”节点
	 * @param nodes
	 * @return
	 */
	private Set<TreeNode> cleanTreeNodesRoot(Set<TreeNode> nodes){
		
		Set<TreeNode> result=new HashSet<TreeNode>();
		
		if(nodes.size()==0)
			return result;
		List<TreeNode> nodesList=new ArrayList<TreeNode>(nodes);
		
		while(nodesList.size()!=0){
			Set<TreeNode> willRemoved=new HashSet<TreeNode>();
			TreeNode parent=nodesList.get(0);
			for(int i=0;i<nodesList.size();i++){
				TreeNode temp=nodesList.get(i);
				boolean flag1=isSub(temp,parent);
				//boolean flag2=isSub(parent,temp);
				if(flag1)
					parent=temp;
					
			}
			for(int i=0;i<nodesList.size();i++){
				TreeNode temp=nodesList.get(i);
				if(isSub(parent,nodesList.get(i)))
					willRemoved.add(nodesList.get(i));
			}
			
			nodesList.removeAll(willRemoved);
			result.add(parent);
			nodesList.remove(parent);
		}
		
		return result;
	}
	
	private boolean isSub(TreeNode parent,TreeNode son){
		String pString=parent.getCode();
		String sString=son.getCode();
		if(sString.contains(pString))
			return true;
		return false;
	}
	

	
	
	
	public UserService getUserService() {
		return userService;
	}
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public TreeService getTreeService() {
		return treeService;
	}

	@Autowired
	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}

	public void setServletResponse(HttpServletResponse response) {
		
		this.response=response;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}
	@Autowired
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
