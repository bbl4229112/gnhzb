package server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.OperationRight;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.TreeNodeDTO;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.privilege.PrivilegeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;

public class PositionFile {
	@Resource(name = "treeServiceImpl")
	private TreeService treeservice;
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice;
	@Resource(name = "userServiceImpl")
	private UserService userservice;
	@Resource(name = "privilegeServiceImpl")
	private PrivilegeService priviledgeservice;
	
	public String getTreeRoots() throws Exception {
		List<TreeNode> roots = new ArrayList<TreeNode>();
		SecurityContext securityContext = SecurityContextHolder.getContext();
		SystemUser user = this.userservice.getUser(securityContext.getAuthentication().getName());
		if(user == null)
			return null;
		if(user.getEmail().equals(Constants.SUPERADMIN)) {
			roots = treeservice.listRootTreeNodes(DomainTreeNode.class,false);
		} else {		
			OperationRight operationRight = this.priviledgeservice.getOperationRight("节点管理");
			if(operationRight==null)
				return null;
			String rightId=operationRight.getId().toString();
			String reg="(?<=(\\b(R|A|U)_\\d{0,1000}_))\\d+(?=_"+rightId+"\\b)";
			Pattern pattern = Pattern.compile(reg);
			List<String> nodeIds = getAuthoritiesTriplePart(pattern);			
			List<TreeNode> nodes = new ArrayList<TreeNode>();
			for(String id:nodeIds){
				TreeNode node = this.treeservice.getTreeNode(Long.valueOf(id));
				//node 可能为hibernate 代理对象，懒加载
				//导致 instanceof 不可用
				Object proxyObj = node;
				Object realEntity = null;
				if (proxyObj instanceof HibernateProxy) {  
				     realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
				} else {
					 realEntity=proxyObj;
				}
				if(realEntity instanceof DomainTreeNode){
					nodes.add((DomainTreeNode)realEntity);
				}			
			}
			roots = cleanTreeNodesRoot(nodes);
		}
		List<TreeNodeDTO> dtos = new ArrayList<TreeNodeDTO>();
		for(int i=0;i<roots.size();i++){
			TreeNodeDTO treeNodeDTO = new TreeNodeDTO();
			treeNodeDTO.setId(roots.get(i).getId());
			treeNodeDTO.setName(roots.get(i).getNodeName());
			treeNodeDTO.setOrderId(roots.get(i).getOrderId());
			if(roots.get(i).getParentId() == null) {

			} else {
				treeNodeDTO.setParentId(roots.get(i).getParentId());
			}
			dtos.add(treeNodeDTO);
		}
		
		List<TreeNode> list = this.treeservice.getAllTreeNodes();
		List<TreeNodeDTO> dtos2 = new ArrayList<TreeNodeDTO>();
		for(int i=0;i<list.size();i++) {
			TreeNodeDTO treeNodeDTO = new TreeNodeDTO();
			treeNodeDTO.setId(list.get(i).getId());
			treeNodeDTO.setName(list.get(i).getNodeName());
			treeNodeDTO.setOrderId(list.get(i).getOrderId());
			if(list.get(i).getParentId() == null) {

			} else {
				treeNodeDTO.setParentId(list.get(i).getParentId());
			}			
			Set<TreeNode> set = list.get(i).getSubNodes();
			if(set.size()!=0){
				ArrayList<TreeNodeDTO> arrayList=new ArrayList<TreeNodeDTO>();
				for(TreeNode child:set){
					arrayList.add(generateDTO(child,false,true));
				}
				treeNodeDTO.setChildren(arrayList);      //只是设置一下，后面用于判断。
			}
			dtos2.add(treeNodeDTO);
		}
		
		String treedata="<?xml version='1.0' encoding='utf-8'?>\n<node foddersortId='0' foddersortName='所有分类'>\n";
		for(TreeNodeDTO treenode:dtos) {
			treedata=treedata+"<node foddersortId='"+treenode.getId() +"' foddersortName='"+treenode.getName()+"'>\n";   
			treedata=CreateXmlNode(dtos2,treenode.getId(),treedata); 
			treedata=treedata+"</node>\n";   
		}   
		treedata=treedata+"</node>\n";   
		System.out.println(treedata);   
		return treedata;   
	}  
	
	public String CreateXmlNode(List<TreeNodeDTO> list,Long nodepid,String cratedata){   
		for (int a=0;a<list.size();a++) {   
			TreeNodeDTO cate=list.get(a);   			
//			if(cate.getTCategory()==null || cate.getTCategory().equals("")) {   
			if(cate.getParentId() == 0 ) {
				if(cate.getId() == nodepid) {
					List<MetaKnowledge> xmllist = kservice.getPositionKnowledgeByDomain(cate.getId());
					for(int i=0;i<xmllist.size();i++) {
						cratedata=cratedata+"<node foddersortId='"+xmllist.get(i).getId()+"'foddersortName='"+xmllist.get(i).getTitlename()+".xml' parentid='"+cate.getId()+"'/>\n"; 
					}
				}
			} else {
				if(cate.getParentId() == nodepid) {
					if(cate.getChildren()!=null) {   
						cratedata=cratedata+"<node foddersortId='"+cate.getId() +"' foddersortName='"+cate.getName()+"' parentid='"+cate.getParentId()+"'>\n";   
						cratedata=CreateXmlNode(list,cate.getId(),cratedata); 												
						cratedata=cratedata+"</node>\n";  
						List<MetaKnowledge> xmllist = kservice.getPositionKnowledgeByDomain(cate.getId());
						for(int i=0;i<xmllist.size();i++) {
							cratedata=cratedata+"<node foddersortId='"+xmllist.get(i).getId()+"'foddersortName='"+xmllist.get(i).getTitlename()+".xml' parentid='"+cate.getId()+"'/>\n";   						
						}						
					} else {   
						cratedata=cratedata+"<node foddersortId='"+cate.getId()+"'foddersortName='"+cate.getName()+"' parentid='"+cate.getParentId()+"'/>\n";  
						List<MetaKnowledge> xmllist = kservice.getPositionKnowledgeByDomain(cate.getId());
						for(int i=0;i<xmllist.size();i++) {
							cratedata=cratedata+"<node foddersortId='"+xmllist.get(i).getId()+"'foddersortName='"+xmllist.get(i).getTitlename()+".xml' parentid='"+cate.getId()+"'/>\n";   						
						}
					}   
				} else if(cate.getId() == nodepid){
					List<MetaKnowledge> xmllist = kservice.getPositionKnowledgeByDomain(cate.getId());
					for(int i=0;i<xmllist.size();i++) {
						cratedata=cratedata+"<node foddersortId='"+xmllist.get(i).getId()+"'foddersortName='"+xmllist.get(i).getTitlename()+".xml' parentid='"+cate.getId()+"'/>\n";   						
					}
				}
			}
		}
		return cratedata;   
	}
	
	private TreeNodeDTO generateDTO(TreeNode treeNode,boolean collapse,boolean isnotroot){		
		TreeNodeDTO treeNodeDTO=new TreeNodeDTO();
		treeNodeDTO.setId(treeNode.getId());
		treeNodeDTO.setName(treeNode.getNodeName());
		treeNodeDTO.setOrderId(treeNode.getOrderId());
		if(treeNode.getParentId() == null) {

		} else {			
			treeNodeDTO.setParentId(treeNode.getParentId());
		}		
		if(collapse&&isnotroot)
			treeNodeDTO.setExpanded(!collapse);
		Object proxyObj = treeNode;
		Object realEntity = null;
		if (proxyObj instanceof HibernateProxy) {  
		      realEntity= ((HibernateProxy)proxyObj).getHibernateLazyInitializer().getImplementation() ;    
		 } else{
			 realEntity=proxyObj;
		 }
		
//		if(realEntity instanceof RoleTreeNode){
//			treeNodeDTO.setIcon(Constants.roleTreeIcon);
//			
//		}else if(realEntity instanceof DomainTreeNode){
//			treeNodeDTO.setIcon(Constants.domianTreeIcon);
//			treeNodeDTO.setIndex("domainnodeid");
			
//		}else if(realEntity instanceof CategoryTreeNode){
//			treeNodeDTO.setIcon(Constants.categoryTreeIcon);
//			treeNodeDTO.setIndex("categoriesid");
//		}		
//		if(treeNodeDTO.getIcon()==null)
//			treeNodeDTO.setIcon("e-tree-folder");				
		Set<TreeNode> set = treeNode.getSubNodes();
		if(set.size()!=0){
			ArrayList<TreeNodeDTO> arrayList = new ArrayList<TreeNodeDTO>();
			for(TreeNode child:set){
				arrayList.add(generateDTO(child,collapse,true));
			}
//			Collections.sort(arrayList);
			treeNodeDTO.setChildren(arrayList);
		}else{
//			if(realEntity instanceof RoleTreeNode)
//				treeNodeDTO.setIcon(Constants.roleTreeChildIcon);
//			if(realEntity instanceof DomainTreeNode){
//				treeNodeDTO.setIcon(Constants.domianTreeChildIcon);				
//			}				
//			if(realEntity instanceof CategoryTreeNode)
//				treeNodeDTO.setIcon(Constants.categoryTreeChildIcon);
		}				
		return treeNodeDTO;
	}
	
	private List<String> getAuthoritiesTriplePart(Pattern pattern){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Collection<GrantedAuthority > authorities=securityContext.getAuthentication().getAuthorities();
		List<String> result = new ArrayList<String>();
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
	 * 清除子树，找出“根”节点
	 */
	private List<TreeNode> cleanTreeNodesRoot(List<TreeNode> nodes){		
		List<TreeNode> result=new ArrayList<TreeNode>();		
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
				TreeNode temp = nodesList.get(i);
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
		String pString = parent.getCode();
		String sString = son.getCode();
		if(sString.contains(pString))
			return true;
		return false;
	}
}
