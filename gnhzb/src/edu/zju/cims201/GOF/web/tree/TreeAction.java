package edu.zju.cims201.GOF.web.tree;

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
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.web.struts2.Struts2Utils;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;

import edu.zju.cims201.GOF.dao.HibernateUtils;

import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.CategoryRootDTO;
import edu.zju.cims201.GOF.rs.dto.TreeNodeDTO;
import edu.zju.cims201.GOF.service.ServiceException;

import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;
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
@Namespace("/tree")
//定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "tree.action", type = "redirect") })
public class TreeAction extends CrudActionSupport<TreeNode> implements ServletResponseAware{

	private static final long serialVersionUID = 8683878162525847045L;

	private TreeService treeServiceImpl;

	private String json;
	private String o;
	private String id;
	private String swapId;
	
	private boolean collapse;
	private long nodeId;
	
	private String direction;
	private String nodeDescription;
	private String name;
	private TreeNode entity;
	private HttpServletResponse response;
	
	private String type;
	

	//-- ModelDriven 与 Preparable函数 --//
	

	public TreeNode getModel() {
		return this.entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		if(this.id!=null){
			entity=treeServiceImpl.getTreeNode(this.id);
		}
		
		
		
	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
//		if(this.json.equals("domainRoot")){
//			
//			List<TreeNode> list = treeServiceImpl.listRootTreeNodes(DomainTreeNode.class);
//			listTreeNodes(list);
//		}else if(this.json.equals("roleRoot")){
//			List<TreeNode> list = treeServiceImpl.listRootTreeNodes(RoleTreeNode.class);
//			listTreeNodes(list);
//		}else if(this.json.equals("categoryRoot")){
//			List<TreeNode> list = treeServiceImpl.listRootTreeNodes(CategoryTreeNode.class);
//			listTreeNodes(list);
//		}
		return INPUT;
	}
	
	private void listTreeNodes(List<TreeNode> list)throws Exception {
		
		List<TreeNodeDTO> dtos=new ArrayList<TreeNodeDTO>();
		for(TreeNode treeNode:list){
		
			dtos.add(generateDTO(treeNode,collapse,false));
			
		}
		Collections.sort(dtos);
		
		JSONUtil.write(response, dtos);
		
	
	}
	
	private TreeNodeDTO generateDTO(TreeNode treeNode,boolean collapse,boolean isnotroot){
		
		TreeNodeDTO treeNodeDTO=new TreeNodeDTO();
		treeNodeDTO.setId(treeNode.getId());
		treeNodeDTO.setName(treeNode.getNodeName());
		treeNodeDTO.setOrderId(treeNode.getOrderId());
		if(collapse&&isnotroot)
			treeNodeDTO.setExpanded(!collapse);
		Object proxyObj = treeNode;
		Object  realEntity=null;
		if (proxyObj instanceof HibernateProxy) {  
		      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
		 } else{
			 realEntity=proxyObj;
		 }
		
		if(realEntity instanceof RoleTreeNode){
			treeNodeDTO.setIcon(Constants.roleTreeIcon);
			
		}else if(realEntity instanceof DomainTreeNode){
			treeNodeDTO.setIcon(Constants.domianTreeIcon);
			treeNodeDTO.setIndex("domainnodeid");
			
		}else if(realEntity instanceof CategoryTreeNode){
			treeNodeDTO.setIcon(Constants.categoryTreeIcon);
			treeNodeDTO.setIndex("categoriesid");
		}
		
		if(treeNodeDTO.getIcon()==null)
			treeNodeDTO.setIcon("e-tree-folder");
		
		
		Set<TreeNode> set=treeNode.getSubNodes();
		if(set.size()!=0){
			ArrayList<TreeNodeDTO> arrayList=new ArrayList<TreeNodeDTO>();
			for(TreeNode child:set){
				arrayList.add(generateDTO(child,collapse,true));
			}
			Collections.sort(arrayList);
			treeNodeDTO.setChildren(arrayList);
		}else{
			if(realEntity instanceof RoleTreeNode)
				treeNodeDTO.setIcon(Constants.roleTreeChildIcon);
			if(realEntity instanceof DomainTreeNode){
				treeNodeDTO.setIcon(Constants.domianTreeChildIcon);
				
			}
				
			if(realEntity instanceof CategoryTreeNode)
				treeNodeDTO.setIcon(Constants.categoryTreeChildIcon);
		}
		
		
		return treeNodeDTO;
	}
	
	
	public String listTreeRoots() throws Exception {
		
		
		List<TreeNode> roots=null;
		if(this.type.equals("categoryTree")){
			roots= this.treeServiceImpl.listRootTreeNodes(CategoryTreeNode.class,false);
		}else if(this.type.equals("domainTree")){
			roots= this.treeServiceImpl.listRootTreeNodes(DomainTreeNode.class,false);
		}
		
		
		List<CategoryRootDTO> dtos=new ArrayList<CategoryRootDTO>();
		for(TreeNode node:roots){
			CategoryRootDTO dto=new CategoryRootDTO();
			dto.setNodeId(node.getId());
			dto.setName(node.getNodeName());
			dto.setIndex("cdTree");
			dtos.add(dto);
		}
		
		JSONUtil.write(response, dtos);
		
		return null;
	}
	
	/**
	 * 根据一个节点写出其及子节点json
	 * @return
	 * @throws Exception
	 */
	public String listSubNode() throws Exception {
		if(this.nodeId==0)
			return null;
		TreeNode node=this.treeServiceImpl.getTreeNode(nodeId);
		ArrayList<TreeNode> list=new ArrayList<TreeNode>();
		list.add(node);
		listTreeNodes(list);
		return null;
	}
	
	public String listDandianSubNode() throws Exception {
		this.nodeId=Long.valueOf(Constants.DANDIANDOMAINID);
		listSubNode();
		return null;
	}
	


	@Override
	public String input() throws Exception {
		return INPUT;
	}
	
	public String update() throws Exception {
		
		if(id!=null){
			entity=treeServiceImpl.getTreeNode(Long.valueOf(this.id));
			entity.setNodeName(this.name);
			entity.setNodeDescription(this.nodeDescription);
			treeServiceImpl.updateNodeInfo(entity);
			TreeNodeDTO treeNodeDTO=generateDTO(entity,collapse,false);
			
			JSONUtil.write(response, treeNodeDTO);
			
		}
		return null;
	}
	

	@Override
	public String save() throws Exception {
		TreeNode addedNode=null;
		if(this.id!=null){//创建非根节点
			TreeNode parentNode=treeServiceImpl.getTreeNode(Long.valueOf(id));
			addedNode=parentNode.getClass().newInstance();
			addedNode.setNodeName(this.name);
			addedNode.setNodeDescription(this.nodeDescription);
			treeServiceImpl.saveNode(parentNode, addedNode);
			addedNode.setOrderId(addedNode.getId());
			addedNode.setParentId(parentNode.getId());
			treeServiceImpl.updateNodeInfo(addedNode);
		}else if(id==null&&json!=null){//创建根节点
			
			if(json.equals("domainTree"))
				addedNode=new DomainTreeNode();
			else if(json.equals("categoryTree"))
				addedNode=new CategoryTreeNode();
			else if(json.equals("roleTree"))
				addedNode=new RoleTreeNode();
			
			addedNode.setNodeName(this.name);
			addedNode.setNodeDescription(this.nodeDescription);
			treeServiceImpl.saveNode(null, addedNode);
			addedNode.setOrderId(addedNode.getId());
			treeServiceImpl.updateNodeInfo(addedNode);
		}
		TreeNodeDTO treeNodeDTO=generateDTO(addedNode,collapse,false);
		
		JSONUtil.write(response, treeNodeDTO);
		
		
		return null;
	}

	@Override
	public String delete() throws Exception {
		if(this.id!=null){
			entity=treeServiceImpl.getTreeNode(Long.valueOf(this.id));
		}
		if(isDeletable(entity)){

			treeServiceImpl.deleteTree(entity);
			//TreeNodeDTO treeNodeDTO=generateDTO(entity);
			JSONWriter writer = new JSONWriter();
			String domainTreeString=writer.write("success");
			response.getWriter().print(domainTreeString);
		}
		
		return null;

	}
	
	private boolean isDeletable(TreeNode treeNode) throws Exception{
		String message=null;
		JSONWriter writer = new JSONWriter();
		if(treeNode instanceof DomainTreeNode){
			DomainTreeNode domainTreeNode=(DomainTreeNode)treeNode;
			if(domainTreeNode.getKnowledges().size()!=0){
				message="不能删除节点:"+treeNode.getNodeName()+"下有知识";
				String messageString=writer.write(message);
				response.getWriter().print(messageString);
				return false;
			}
			if(domainTreeNode.getEmpowerments().size()!=0){
				message="不能删除节点:"+treeNode.getNodeName()+"已经被赋予权限";
				String messageString=writer.write(message);
				response.getWriter().print(messageString);
				return false;
			}
			Set<TreeNode> subNotes=domainTreeNode.getSubNodes();
			if(subNotes.size()!=0){
				for(TreeNode subNote:subNotes){
					boolean result=isDeletable(subNote);
					if(result==false)
						return result;
					
				}
			}
			
			return true;
		}
		
		else if(treeNode instanceof CategoryTreeNode){
			CategoryTreeNode categoryTreeNode=(CategoryTreeNode)treeNode;
			if(categoryTreeNode.getKnowledges().size()!=0){
				message="不能删除节点:"+treeNode.getNodeName()+"下有知识";
				String messageString=writer.write(message);
				response.getWriter().print(messageString);
				return false;
			}
			if(categoryTreeNode.getEmpowerments().size()!=0){
				message="不能删除节点:"+treeNode.getNodeName()+"已经被赋予权限";
				String messageString=writer.write(message);
				response.getWriter().print(messageString);
				return false;
			}
			Set<TreeNode> subNotes=categoryTreeNode.getSubNodes();
			if(subNotes.size()!=0){
				for(TreeNode subNote:subNotes){
					boolean result=isDeletable(subNote);
					if(result==false)
						return result;
					
				}
			}
			
			return true;
		}else{
			RoleTreeNode roleTreeNode=(RoleTreeNode)treeNode;
			if(roleTreeNode.getUsers().size()!=0){
				
				message="不能删除节点:"+treeNode.getNodeName()+"下有用户";
				String messageString=writer.write(message);
				response.getWriter().print(messageString);
				System.out.println(message);
				return false;
			}
			if(roleTreeNode.getEmpowerments().size()!=0){
				message="不能删除节点:"+treeNode.getNodeName()+"已经被赋予权限";
				String messageString=writer.write(message);
				response.getWriter().print(messageString);
				System.out.println(message);
				return false;
			}
			Set<TreeNode> subNotes=roleTreeNode.getSubNodes();
			if(subNotes.size()!=0){
				for(TreeNode subNote:subNotes){
					boolean result=isDeletable(subNote);
					if(result==false)
						return result;
					
				}
			}
			
			return true;
			
		}
	}
	
	
//	public String singleNode() throws Exception {
//		
//		DomainTreeNode domainTreeNode=new DomainTreeNode();
//		domainTreeNode.setId(new Date().getTime());
//		domainTreeNode.setNodeName("单个测试节点");
//		TreeNodeDTO treeNodeDTO=new TreeNodeDTO();
//
//		treeNodeDTO.setId(domainTreeNode.getId());
//
//		treeNodeDTO.setName(domainTreeNode.getNodeName());
//		JSONWriter writer = new JSONWriter();
//		String domainTreeString=writer.write(treeNodeDTO);
//		response.getWriter().print(domainTreeString);
//		
//		return null;
//	}
	
	public void order(){
		TreeNode swapNode=null;
		if(this.id!=null){
			entity=treeServiceImpl.getTreeNode(Long.valueOf(this.id));
		}
		if(this.swapId!=null){
			swapNode=treeServiceImpl.getTreeNode(Long.valueOf(this.swapId));
		}
		treeServiceImpl.orderSwap(entity, swapNode);
	}

	



	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setServletResponse(HttpServletResponse response) {
		
		this.response=response;
	}

	public TreeService getTreeServiceImpl() {
		return treeServiceImpl;
	}
	
	
	@Autowired
	public void setTreeServiceImpl(TreeService treeServiceImpl) {
		this.treeServiceImpl = treeServiceImpl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getNodeDescription() {
		return nodeDescription;
	}

	public void setNodeDescription(String nodeDescription) {
		this.nodeDescription = nodeDescription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public boolean isCollapse() {
		return collapse;
	}

	public void setCollapse(boolean collapse) {
		this.collapse = collapse;
	}

	public String getSwapId() {
		return swapId;
	}

	public void setSwapId(String swapId) {
		this.swapId = swapId;
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
}
