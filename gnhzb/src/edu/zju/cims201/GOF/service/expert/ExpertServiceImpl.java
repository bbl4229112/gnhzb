package edu.zju.cims201.GOF.service.expert;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.user.ExpertDao;
import edu.zju.cims201.GOF.hibernate.pojo.Expert;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.ExpertDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.util.Constants;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService{

	@Resource(name = "ktypeServiceImpl")
	private KtypeService ktypeservice;
	
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice;
	
	@Resource(name="expertDao")
	private ExpertDao expertDao;
	
	public List<Expert> searchExperts(String hql, Object[] objects) {
		List<Expert> expertlist = expertDao.createQuery(hql,objects).list();
		return expertlist;
	}

	
	
	public PageDTO searchExpert(String key) {
		StringBuffer hql = new StringBuffer("");
		StringBuffer hqlfrom = new StringBuffer(" ");
		List<Object> queryParams = new ArrayList<Object>();
		
		if (null!=key && !key.equals("")) {
			String[] values = key.replaceAll("  ", " ").trim().split(" ");
			hql.append("(");
			for (String string : values) {

				hql.append(" ( o.user.name like ? ) ");
				queryParams.add("%" + string + "%");
				
				if (hqlfrom.toString().indexOf(",TreeNode treeNodes ") == -1)
					hqlfrom.append(" ,TreeNode treeNodes ");
				hql.append("or");
				hql.append(" ( treeNodes.id in elements(o.treeNodes ) and treeNodes.nodeName like ? ) ");
				queryParams.add(string);
				
				}
			hql.append(")");
			}
		
		
		String queryString = " select distinct o  from Expert o " + hqlfrom + " "
				+ ((hql == null || hql.equals("")) ? "" : "where " + hql);
		System.out.println("检索hql="+ queryString);
		
		List<Expert> experts= this.searchExperts(queryString, queryParams.toArray());
		
		List<ExpertDTO> edtos = new ArrayList<ExpertDTO>();
		for(Expert expert:experts) {
			ExpertDTO edto = new ExpertDTO();
			edto.setId(expert.getId());
			edto.setUsername(expert.getUser().getName());
			edto.setUsersex(expert.getUser().getSex());
			edto.setPicturePath(expert.getUser().getPicturePath());
			edto.setEmail(expert.getUser().getEmail());
			edto.setIntroduction(expert.getUser().getIntroduction());
			
			Set<TreeNode> nodes = expert.getTreeNodes();
			
			if(nodes.size()!=0){
				
				Iterator<TreeNode> it = nodes.iterator();
				List<String> nodelist = new ArrayList<String>();
				while (it.hasNext())
				{			  
					TreeNode node =  (TreeNode) it.next();	
					String nodename = node.getNodeName();			
					nodelist.add(nodename);
				}
				edto.setTreenodename(nodelist);	
			}
			
			edtos.add(edto);
		}

		PageDTO pdto = new PageDTO();
		pdto.setData(edtos);
		pdto.setTotal(edtos.size());

		return pdto;
	}

	
	public PageDTO searchArticle(String key){
		
		// 根据查询条件构建 hql的内容
		StringBuffer hql = new StringBuffer("");
//		hql.append(" o.status !='0' and o.isvisible=true and (");
		hql.append(" o.status !='0' and o.isvisible=true ");
		// 由于一些级联关系信息，在构建hql时 添加from 中更多的表内容
		StringBuffer hqlfrom = new StringBuffer(" ");
		List<Object> queryParams = new ArrayList<Object>();
		HashMap searchwordtohilght = new HashMap();

		if (null!=key && !key.equals("")) {
			String[] values = key.replaceAll("  ", " ").trim().split(" ");
			hql.append(" and (");
			for (String string : values) {
				hql.append("  ( o.titlename like ? ) ");
				queryParams.add("%" + string + "%");
				
				hql.append("or");
				hql.append(" ( o.uploader.name like ? )");
				queryParams.add("%" + string + "%");
				
				if (hqlfrom.toString().indexOf(",Author kauthors ") == -1)
					hqlfrom.append(" ,Author kauthors ");
				hql.append("or");
				hql.append(" ( kauthors.id in elements(o.kauthors ) and kauthors.authorName like ? ) ");
				queryParams.add("%" + string + "%");
				
				hql.append("or");
				hql.append(" ( o.domainnode.nodeName like ? ) ");
				queryParams.add(string);
				
				hql.append("or");
				hqlfrom.append(" ,CategoryTreeNode categories ");
				hql.append(" ( categories.id in elements(o.categories ) and categories.nodeName like ? ) ");
				queryParams.add(string);
			}
			hql.append(")");
		}
		
		

//		hql.append(")");
		
		PageDTO page = new PageDTO();
		int index = 0;
		int size = Constants.rawPrepage;
		page.setFirstindex(index);
		page.setPagesize(size);
		page.setOrderBy("order by o.id desc");
		System.out.println("检索hql="+ hql.toString()+"queryParams="+queryParams+"hqlform="+hqlfrom.toString());
		
		Ktype ktype = ktypeservice.getKtypeByKtypeName("文章");
		Knowledge kg = kservice.getExtendKnowledgeByKtype(ktype);
		
		PageDTO pagetable = kservice.searchKnowledge(hqlfrom.toString(), hql.toString(),
				kg.getClass(), page, queryParams.toArray(), searchwordtohilght);

		return pagetable;
	}  
	

	public KtypeService getKtypeservice() {
		return ktypeservice;
	}

	public void setKtypeservice(KtypeService ktypeservice) {
		this.ktypeservice = ktypeservice;
	}

	public KnowledgeService getKservice() {
		return kservice;
	}

	public void setKservice(KnowledgeService kservice) {
		this.kservice = kservice;
	}



	public ExpertDao getExpertDao() {
		return expertDao;
	}



	public void setExpertDao(ExpertDao expertDao) {
		this.expertDao = expertDao;
	}



}
