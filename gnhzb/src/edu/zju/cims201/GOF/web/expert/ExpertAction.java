package edu.zju.cims201.GOF.web.expert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.stringtree.json.JSONWriter;

import edu.zju.cims201.GOF.hibernate.pojo.Expert;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.ExpertDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.expert.ExpertService;
import edu.zju.cims201.GOF.service.interestmodel.InterestModelService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;


/**
 * 名人专栏Action
 * @author jiangdingding 2013-6-5
 *
 */
@Namespace("/expert")
@Results({ @Result(name = CrudActionSupport.RELOAD, location = "expert.action", type = "redirect")})
public class ExpertAction extends CrudActionSupport<MetaKnowledge> implements ServletResponseAware{

	private static final long serialVersionUID = 1046248125123603836L;
	@Resource(name="interestModelServiceImpl")
	private InterestModelService imservice;
	@Resource(name = "ktypeServiceImpl")
	private KtypeService ktypeservice;
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice;
	@Resource(name = "treeServiceImpl")
	private TreeService treeservice;
	@Resource(name = "expertServiceImpl")
	private ExpertService expertService;
	
	
	private HttpServletResponse response;
	private String formvalue;
	private String key;


	private int size;
	private int index;
	
	
	/**
	 * 根据条件查询专家列表
	 * @return
	 * @throws Exception
	 */
	public String listExpertByDomain() throws Exception {
		Long domainnode = null;
		Long categorienode = null;
		
		HashMap<String, String> jsonMap = (HashMap<String, String>)JSONUtil.read(formvalue);
		
		String domainName = jsonMap.get("searchitem1");
		if(domainName!=null){
			String[] domainName_s = domainName.split(":");
			if(domainName_s.length > 1){		
				domainName =domainName_s[1];
			}else{
				domainName = null;
			}
		}
		
		String categroies = jsonMap.get("searchitem2");
		if(categroies!=null){
			String[] categroies_s = categroies.split(":");
			if(categroies_s.length > 1){		
				categroies =categroies_s[1];
			}else{
				categroies = null;
			}
		}
		
		//System.out.println("****categroies = "+categroies+"domainName="+domainName+"searchkey="+searchkey);
		
		if(!"".equals(domainName) && null!= domainName){
			TreeNode treeNode = treeservice.getTreeNodeByNodeName(domainName);
			if(treeNode!=null && treeNode.getId()!=null){
				domainnode = treeNode.getId();
			}
		}else{
			domainnode = null;
		}
		
		if(!"".equals(categroies) && null!= categroies){
			TreeNode treeNode = treeservice.getTreeNodeByNodeName(categroies);
			if(treeNode!=null && treeNode.getId()!=null){
				categorienode = treeNode.getId();
			}
		}else{
			categorienode = null;
		}
		
		Set<Expert> experts = null;
		Set<Expert> experts2 = null;
		Set<Expert> expertstemp = new HashSet<Expert>();
		if(categorienode!=null && domainnode!=null){
			experts = imservice.getTreeExpert(categorienode);
			experts2 = imservice.getTreeExpert(domainnode);
			
			for(Expert expert:experts){
				for(Expert expert2:experts2){
					if(expert.getId() == expert2.getId()){
						expertstemp.add(expert);
					}
				}
			}
			experts = expertstemp;
			
		}else if(categorienode!=null){
			experts = imservice.getTreeExpert(categorienode);
//			System.out.println("1****"+experts.size());
		}else if(domainnode!=null){
		
			experts = imservice.getTreeExpert(domainnode);
//			System.out.println("2********"+experts.size());
		}else{
			List list = new ArrayList(new HashSet()); 
			list = imservice.getAllExpert();
			experts = (Set<Expert>)new HashSet(list); 
//			System.out.println("3****"+experts.size());
		}
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
		
		List<ExpertDTO> subList = new ArrayList<ExpertDTO>();
		for (int i = index * size; i < ((index + 1) * size < edtos.size() ? (index + 1)
				* size
				: edtos.size()); i++) {
			subList.add(edtos.get(i));
		}
		PageDTO pdto = new PageDTO();
		pdto.setData(subList);
		pdto.setTotal(edtos.size());
		int totalPage;
		if(size != 0) {
			if (edtos.size() % size == 0) {
				totalPage = edtos.size() / size;
			} else {
				totalPage = edtos.size() / size + 1;
			}
			pdto.setTotalPage(edtos.size() / size + 1);
		}
		
		JSONUtil ju  = new JSONUtil();
		ju.write(response, pdto);
		return null;
	}
	
	/**
	 * 根据条件查询专家文章列表
	 * @return
	 * @throws Exception
	 */
	public String listArticleByDomain() throws Exception {
		Long domainnode = null;
		Long categorienode = null;
		
		HashMap<String, String> jsonMap = (HashMap<String, String>)JSONUtil.read(formvalue);
		
		String domainName = jsonMap.get("searchitem1");
		if(domainName!=null){
			String[] domainName_s = domainName.split(":");
			if(domainName_s.length > 1){		
				domainName =domainName_s[1];
			}else{
				domainName = null;
			}
		}
		
		String categroies = jsonMap.get("searchitem2");
		if(categroies!=null){
			String[] categroies_s = categroies.split(":");
			if(categroies_s.length > 1){		
				categroies =categroies_s[1];
			}else{
				categroies = null;
			}
		}
		
		if(!"".equals(domainName) && null!= domainName){
			TreeNode treeNode = treeservice.getTreeNodeByNodeName(domainName);
			if(treeNode!=null && treeNode.getId()!=null){
				domainnode = treeNode.getId();
			}
		}else{
			domainnode = null;
		}
		
		if(!"".equals(categroies) && null!= categroies){
			TreeNode treeNode = treeservice.getTreeNodeByNodeName(categroies);
			if(treeNode!=null && treeNode.getId()!=null){
				categorienode = treeNode.getId();
			}
		}else{
			categorienode = null;
		}
		
        HashMap propertyValues =new HashMap();
        
        Long ktypeId = ktypeservice.getKtype("Article").getId();
		propertyValues.put("size", size);
		propertyValues.put("index", index);
		propertyValues.put("selectedktype", ktypeId);
		
		List searchlist = new ArrayList();
		
		if(categroies !=null){
			HashMap search1 =new HashMap();
			search1.put("name","categories");
			search1.put("value",categorienode);
			
			search1.put("and_or","and");

			searchlist.add(search1);
		}
		
		if(domainnode!=null){
			HashMap search2 =new HashMap();
			search2.put("name","domainnode");
			search2.put("value",domainnode);
			search2.put("and_or","and");
			searchlist.add(search2);
		}
		PageDTO resultlist = new PageDTO();
		if(categroies ==null && domainnode ==null){
			//得到所有的文章列表
			resultlist = expertService.searchArticle("");
		}else{
			propertyValues.put("searchlist", searchlist);
			resultlist = kservice.searchKnowledge(propertyValues);
		}
		
		JSONUtil ju  = new JSONUtil();
		ju.write(response, resultlist);
		return null;
	}
	
	/**
	 * 通过关键词查找文章 
	 * @return
	 * @throws Exception
	 */
	public String articleKeySearch() throws Exception {
		PageDTO resultlist = expertService.searchArticle(key);
		JSONUtil ju  = new JSONUtil();
		ju.write(response, resultlist);
		return null;
	}
	
	/**
	 * 通过关键词查找专家
	 * @return
	 * @throws Exception
	 */
	public String expertKeySearch() throws Exception {
		PageDTO resultlist = expertService.searchExpert(key);
		JSONUtil ju  = new JSONUtil();
		ju.write(response, resultlist);
		return null;
	}
	
	
	
	public MetaKnowledge getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response=response;
		
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}




	public InterestModelService getImservice() {
		return imservice;
	}




	public void setImservice(InterestModelService imservice) {
		this.imservice = imservice;
	}




	public HttpServletResponse getResponse() {
		return response;
	}




	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}




	public int getSize() {
		return size;
	}




	public void setSize(int size) {
		this.size = size;
	}




	public int getIndex() {
		return index;
	}




	public void setIndex(int index) {
		this.index = index;
	}



	public TreeService getTreeservice() {
		return treeservice;
	}

	public void setTreeservice(TreeService treeservice) {
		this.treeservice = treeservice;
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

	public String getFormvalue() {
		return formvalue;
	}

	public void setFormvalue(String formvalue) {
		this.formvalue = formvalue;
	}

	public ExpertService getExpertService() {
		return expertService;
	}

	public void setExpertService(ExpertService expertService) {
		this.expertService = expertService;
	}
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
