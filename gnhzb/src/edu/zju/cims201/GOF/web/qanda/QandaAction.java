package edu.zju.cims201.GOF.web.qanda;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;


import com.ibm.icu.text.DateFormat;
import com.opensymphony.xwork2.ActionContext;


import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.Expert;
import edu.zju.cims201.GOF.hibernate.pojo.InterestModel;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Vote;



import edu.zju.cims201.GOF.rs.dto.CommentRecordDTO;
import edu.zju.cims201.GOF.rs.dto.ExpertDTO;
import edu.zju.cims201.GOF.rs.dto.InterestModelDTO;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.CommentDTO;
import edu.zju.cims201.GOF.rs.dto.TreeNodeDTO;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.rs.dto.VoteDTO;

import edu.zju.cims201.GOF.service.interestmodel.InterestModelService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.comment.CommentService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;



/**
 * 专家和问答管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 * 
 * @author cwd
 */
//定义URL映射对应/comment/comment.action
@Namespace("/qanda")
//定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "qanda.action", type = "redirect") })

public class QandaAction extends CrudActionSupport<Expert> implements ServletResponseAware{
	private static final long serialVersionUID = 8683878162525847072L;


	private InterestModelService imservice;
	
	private UserService userservice;
	private KnowledgeService knowledgeservice;
	private TreeService treeservice;
	private CommentService commentservice;
	
	
	//-- 页面属性 --//
	private Long expertid;
	private Long nodeid;
		
	private String json;
	private String itemtype;

	private HttpServletResponse response;
	private int size;
	private int index;
	private String key;

	//-- ModelDriven 与 Preparable函数 --//
	@Override
	protected void prepareModel() throws Exception {
		
	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String listMyAnswers() throws Exception {
		List<Comment> answers = commentservice.getMyAnswers(userservice.getUser());
		List<CommentDTO> answerdtos = new ArrayList<CommentDTO>();
		for(Comment answer:answers) {
			CommentDTO answerdto = new CommentDTO();
			answerdto.setContent(answer.getContent());
			
			//comementdto 传哪些要素过去
			
			answerdtos.add(answerdto);
		}
		
		JSONWriter writer = new JSONWriter();
		String mastring = writer.write(answerdtos);
		response.getWriter().print(mastring);
		
		return null;
	}
	
	public String listtreeexperts() throws Exception {
		
		JSONReader jr = new JSONReader();
		Long teid = (Long) jr.read(json);
			
		TreeNodeDTO tndto = new TreeNodeDTO();			
		Set<Expert> treeexperts = imservice.getTreeExpert(teid);
		List<ExpertDTO> edtos = new ArrayList<ExpertDTO>();			
		Iterator it = treeexperts.iterator();
		while (it.hasNext())
		{			  
		   Expert expert =  (Expert) it.next();	
		   ExpertDTO expertdto = new ExpertDTO();
		   expertdto.setId(expert.getId());		   
		   expertdto.setUsername(expert.getUser().getName());
		   expertdto.setEmail(expert.getUser().getEmail());
		   expertdto.setIntroduction(expert.getUser().getIntroduction());
		   expertdto.setSex(expert.getUser().getSex());
		   expertdto.setPicturePath(expert.getUser().getPicturePath());		  
			
		   Set<TreeNode> nodes = expert.getTreeNodes();						
			if(nodes.size()!=0){			
			Iterator<TreeNode> it2 = nodes.iterator();
			List<String> nodelist = new ArrayList<String>();
			while (it2.hasNext())
			{			  
			   TreeNode node =  (TreeNode) it2.next();	
			   String nodename = node.getNodeName();			
			   nodelist.add(nodename);
			}
			expertdto.setTreenodename(nodelist);	
			}
			
		   edtos.add(expertdto);
		}		
		tndto.setName(treeservice.getTreeNode(teid).getNodeName());
		tndto.setChildren(edtos);	
	
		JSONWriter writer = new JSONWriter();
		String testring = writer.write(tndto);
		response.getWriter().print(testring);
		return null;
	}
	
	public String listAllExpert() throws Exception {
		PageDTO pd = new PageDTO();
		
	
		List<Expert> experts = imservice.getAllExpert();
		List<ExpertDTO> edtos = new ArrayList<ExpertDTO>();
		for(Expert expert:experts) {
			ExpertDTO edto = new ExpertDTO();
			edto.setId(expert.getId());
			edto.setUsername(expert.getUser().getName());
			edto.setUsersex(expert.getUser().getSex());
			edto.setUserprofess("专家");
			
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
		pd.setData(subList);
		pd.setTotal(edtos.size());
		int totalPage;
		if(size != 0) {
			if (edtos.size() % size == 0) {
				totalPage = edtos.size() / size;
			} else {
				totalPage = edtos.size() / size + 1;
			}
			pd.setTotalPage(edtos.size() / size + 1);
		}
	
		JSONWriter writer = new JSONWriter();
		String expertstring = writer.write(pd);
		response.getWriter().print(expertstring);
		return null;
	}
	public String searchExpert() throws Exception {	
		String expertname = key.trim();
		List<ExpertDTO> edtos = new ArrayList<ExpertDTO>();
		if(!expertname.equals("")){
			List<Expert> experts = imservice.searchExperts(expertname);			
			for(Expert expert:experts) {
				ExpertDTO edto = new ExpertDTO();
				edto.setId(expert.getId());
				edto.setUsername(expert.getUser().getName());
				
				edtos.add(edto);
			}
		}		
			JSONWriter writer = new JSONWriter();
			String expertstring = writer.write(edtos);
			response.getWriter().print(expertstring);
			return null;
			
		
	
	}
	
	public String listinterest() throws Exception {
		
		
		List<InterestModel> interestlist = imservice.listInterestModelItems(userservice.getUser());
		List<InterestModelDTO> imDTOlist = new ArrayList<InterestModelDTO>();
		System.out.println("+++++++"+interestlist.size());
		
		for(InterestModel im:interestlist) {			
			if(im.getInterestItemType().equals("keyword")) {
				long kewordid = Long.parseLong(im.getInterestItem());
				String kewordname = knowledgeservice.getKeyword(kewordid).getKeywordName();
				InterestModelDTO imdto = new InterestModelDTO();
				imdto.setInterestItemId(kewordid);
				imdto.setInterestItemName(kewordname);
				imdto.setInterestItemType(im.getInterestItemType());
				imDTOlist.add(imdto);
			}
			if(im.getInterestItemType().equals("author")) {
				long authorid = Long.parseLong(im.getInterestItem());
				String authorname = knowledgeservice.getAuthor(authorid).getAuthorName();
				InterestModelDTO imdto = new InterestModelDTO();
				imdto.setInterestItemId(authorid);
				imdto.setInterestItemName(authorname);
				imdto.setInterestItemType(im.getInterestItemType());
				imDTOlist.add(imdto);
			}
			if(im.getInterestItemType().equals("domain")) {
				long domainid = Long.parseLong(im.getInterestItem());
				String domainname = treeservice.getTreeNode(domainid).getNodeName();
				InterestModelDTO imdto = new InterestModelDTO();
				imdto.setInterestItemId(domainid);
				imdto.setInterestItemName(domainname);
				imdto.setInterestItemType(im.getInterestItemType());
				imDTOlist.add(imdto);
			}
			if(im.getInterestItemType().equals("category")) {
				long categoryid = Long.parseLong(im.getInterestItem());
				String categoryname = treeservice.getTreeNode(categoryid).getNodeName();
				InterestModelDTO imdto = new InterestModelDTO();
				imdto.setInterestItemId(categoryid);
				imdto.setInterestItemName(categoryname);
				imdto.setInterestItemType(im.getInterestItemType());
				imDTOlist.add(imdto);
			}
		}
		System.out.println("========"+imDTOlist.size());
		
		JSONWriter writer = new JSONWriter();
		String imstring = writer.write(imDTOlist);
		response.getWriter().print(imstring);
		return null;
	}
	
	public String setExpert() throws Exception {		
		JSONReader jr = new JSONReader();
		String username = json.trim();
		if(!userservice.isNameExist(username)){
			JSONUtil.write(response, "用户不存在");
		}else if(userservice.isNameExist(username)&&!imservice.isExpertExist(username)){
			imservice.addExpertItems(userservice.getOneUser(username), null, null);	
		}
	
		return null;	
	}
	
	public String deleteExpert() throws Exception {
		
		JSONReader jr = new JSONReader();
		Long expertid = (Long) jr.read(json);		
		imservice.deleteExpert(expertid);	
		
		return null;	
	}
	
	public String deleteTreeExpert() throws Exception {
				
		Expert expert = imservice.getExpert(expertid);
		Set<TreeNode> treeNodes = expert.getTreeNodes();
		TreeNode dtreenode = treeservice.getTreeNode(nodeid);
		treeNodes.remove(dtreenode);	
		expert.setTreeNodes(treeNodes);
	
		imservice.updateExpert(expert);	
		
		return null;	
	}
	
	public String setExpertNodes() throws Exception {
		String name = json.trim();
	    if(!imservice.isExpertExist(name)){
	    	JSONUtil.write(response, "专家不存在，请到【专家集维护】进行设置");
	    }else{
	    	Expert expert = imservice.getOneExpert(name);
			TreeNode treenode = treeservice.getTreeNode(nodeid);
			Set<TreeNode> treeNodes = new HashSet<TreeNode>();
			treeNodes.addAll(expert.getTreeNodes());		
			treeNodes.add(treenode);		
			expert.setTreeNodes(treeNodes);
			imservice.updateExpert(expert);	
				
			ExpertDTO expertdto = new ExpertDTO();
			expertdto.setId(expert.getId());		   
			expertdto.setUsername(expert.getUser().getName());
						
			JSONWriter writer = new JSONWriter();
			String expertstring = writer.write(expertdto);
			response.getWriter().print(expertstring);
	   }

		return null;	
	}
	


	@Override
	public String input() throws Exception {
		
		return INPUT;
	}
	
	public Expert getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String save() throws Exception {
		
		return null;
	}
	

	public String saveExpertTreeNode() throws Exception {		
		Set<TreeNode> treenodeset = new HashSet<TreeNode>();
		JSONReader jr = new JSONReader();
		List im = (ArrayList) jr.read(json);		
		for(Object kk:im) {
			String tree = kk.toString().substring(4,(kk.toString().length()-1));
			long treenodeid = Long.parseLong(tree);
			TreeNode treenode = (TreeNode) treeservice.getTreeNode(treenodeid);
			treenodeset.add(treenode);
		}
		
	    imservice.addExpertItems(userservice.getUser(),treenodeset,null);
		return null;
	}
	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
			
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;		
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public InterestModelService getImservice() {
		return imservice;
	}
	@Autowired
	public void setImservice(InterestModelService imservice) {
		this.imservice = imservice;
	}

	public UserService getUserservice() {
		return userservice;
	}
    
	@Autowired
	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}

	public KnowledgeService getKnowledgeservice() {
		return knowledgeservice;
	}

	@Autowired
	public void setKnowledgeservice(KnowledgeService knowledgeservice) {
		this.knowledgeservice = knowledgeservice;
	}

	public TreeService getTreeservice() {
		return treeservice;
	}

	@Autowired
	public void setTreeservice(TreeService treeservice) {
		this.treeservice = treeservice;
	}

	public CommentService getCommentservice() {
		return commentservice;
	}

	@Autowired
	public void setCommentservice(CommentService commentservice) {
		this.commentservice = commentservice;
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

	public Long getExpertid() {
		return expertid;
	}

	public void setExpertid(Long expertid) {
		this.expertid = expertid;
	}

	public Long getNodeid() {
		return nodeid;
	}

	public void setNodeid(Long nodeid) {
		this.nodeid = nodeid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
	

}
