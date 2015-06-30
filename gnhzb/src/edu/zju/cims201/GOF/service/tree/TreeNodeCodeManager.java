package edu.zju.cims201.GOF.service.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.zju.cims201.GOF.dao.tree.TreeNodeDao;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;

@Component
public class TreeNodeCodeManager {
	
	
	private TreeService treeService;
	
	public String genrateTreeRootCode(Class<TreeNode> clazz){
		String treeType=null;
		if(clazz.equals(CategoryTreeNode.class))
			treeType="C";
		else if(clazz.equals(DomainTreeNode.class))
			treeType="D";
		else 
			treeType="R";
		List<TreeNode> rootlist = treeService.listRootTreeNodes(clazz,false);
		if(rootlist.size()==0)
			return treeType+"-1";
		Set<Integer> rootNOs=new HashSet<Integer>();
		for(TreeNode root:rootlist){
			String rootCode=root.getCode();
			int beginIndex=rootCode.lastIndexOf('-');
			String rootNOString=rootCode.substring(beginIndex+1);
			Integer rootNO=Integer.valueOf(rootNOString);
			rootNOs.add(rootNO);
		}
		int partCode=Collections.max(rootNOs)+1;
		return treeType+"-"+partCode;
	}
	
	
	public String genrateCodeByParent(TreeNode parent) throws Exception{
		//当parent为空表示父节点
		if(parent==null){
			throw new Exception("父节点为空");
		}
			
		String parentCode=parent.getCode();
		Set<TreeNode> brothers=parent.getSubNodes();
		if(brothers.size()==0)
			return parentCode+"-1";
		Set<Integer> brotherNOs=new HashSet<Integer>();
		for(TreeNode brother:brothers){
			String brotherCode=brother.getCode();
			int beginIndex=brotherCode.lastIndexOf('-');
			String brotherNOString=brotherCode.substring(beginIndex+1);
			Integer brotherNO=Integer.valueOf(brotherNOString);
			brotherNOs.add(brotherNO);
		}
		int partCode=Collections.max(brotherNOs)+1;
		return parentCode+"-"+partCode;
	}
	
	
	public void updateChildNodeCodes(TreeNode parent,TreeNodeDao treeNodeDao){
		Set<TreeNode> childNodes=parent.getSubNodes();
		if(childNodes.size()!=0){
			for(TreeNode node:childNodes){
				String code=null;
				try {
					code = this.genrateCodeByParent(parent);
				} catch (Exception e) {
					e.printStackTrace();
				}
				node.setCode(code);
				treeNodeDao.save(parent);
				updateChildNodeCodes(node,treeNodeDao);
			}
			
		}
	}


	public TreeService getTreeService() {
		return treeService;
	}

	@Autowired
	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}

}
