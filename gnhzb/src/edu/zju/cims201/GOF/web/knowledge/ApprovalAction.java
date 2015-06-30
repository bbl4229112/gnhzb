package edu.zju.cims201.GOF.web.knowledge;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springside.modules.orm.Page;
import org.stringtree.json.JSONWriter;

import com.sun.org.apache.commons.beanutils.BeanUtils;

import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.hibernate.pojo.ApprovalFlow;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.FlowDTO;
import edu.zju.cims201.GOF.rs.dto.FlowNodeDTO;
import edu.zju.cims201.GOF.rs.dto.QualifiedUsers;
import edu.zju.cims201.GOF.rs.dto.TreeNodeDTO;
import edu.zju.cims201.GOF.service.approvalFlow.ApprovalFlowService;
import edu.zju.cims201.GOF.service.knowledge.FullTextService;
import edu.zju.cims201.GOF.service.knowledge.FullTextServiceImpl;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;



/**
 * 属性管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 * 
 * @author cwd
 */
//定义URL映射对应/ktype/property.action
@Namespace("/knowledge/approval")
//定义名为reload的result重定向到property.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "approval.action", type = "redirect") })

public class ApprovalAction extends CrudActionSupport<ApprovalFlow> implements ServletResponseAware{

	private TreeService treeServiceImpl;
	
	private ApprovalFlowService approvalFlowService;

	private KnowledgeService knowledgeService;
	
	private UserService userService;
    
	private FullTextService fulltextService;
	
	private long knowledgeId;
	
	private long approvalFlowId;
	
	private long pid;
	
	private long approvalorId;
	//pl 下面是我加的###############################################################
	private String knowledgeIdString;

	public String getKnowledgeIdString() {
		return knowledgeIdString;
	}

	public void setKnowledgeIdString(String knowledgeIdString) {
		this.knowledgeIdString = knowledgeIdString;
	}
	private String approvalFlowIdString;
	public String getApprovalFlowIdString() {
		return approvalFlowIdString;
	}

	public void setApprovalFlowIdString(String approvalFlowIdString) {
		this.approvalFlowIdString = approvalFlowIdString;
	}
	//pl 上面是我加的#################################################################
	private String opinion;
	
	private String approvalResult;
	
	private HttpServletResponse response;
	
	

	/**
	 * 返回有资格的审批人员
	 * @return
	 * @throws Exception
	 */
	public String getQualifiedRoleNodes() throws Exception {
		
		
		SystemUser initiator=getUser();
		if(initiator==null)
			return null;
		if(this.pid==0){
			Set<TreeNode> roots=this.approvalFlowService.listQualifiedNodes(initiator);
			listTreeNodesWithoutSub(new ArrayList<TreeNode>(roots));
		}else{
			TreeNode pnode = this.treeServiceImpl.getTreeNode(this.pid);
			listTreeNodesWithoutSub(new ArrayList<TreeNode>(pnode.getSubNodes()));
		}
		
		
		return null;
	}
	
	private void listTreeNodesWithoutSub(List<TreeNode> list)throws Exception {
		JSONWriter writer = new JSONWriter();
		List<TreeNodeDTO> dtos=new ArrayList<TreeNodeDTO>();
		for(TreeNode treeNode:list){
			TreeNodeDTO treeNodeDTO=new TreeNodeDTO();
			treeNodeDTO.setId(treeNode.getId());
			treeNodeDTO.setOrderId(treeNode.getOrderId());
			treeNodeDTO.setName(treeNode.getNodeName());
			treeNodeDTO.setExpanded(false);
			treeNodeDTO.set__viewicon(true);
			dtos.add(treeNodeDTO);
			
		}
		Collections.sort(dtos);
		String treeString=writer.write(dtos);
		response.getWriter().print(treeString);
		System.out.println(treeString);
	}
	
	private void listTreeNodes(List<TreeNode> list)throws Exception {
		JSONWriter writer = new JSONWriter();
		List<TreeNodeDTO> dtos=new ArrayList<TreeNodeDTO>();
		for(TreeNode treeNode:list){
			dtos.add(generateDTO(treeNode));
			
		}
		Collections.sort(dtos);
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
			for(TreeNode child:set){
				arrayList.add(generateDTO(child));
			}
			Collections.sort(arrayList);
			treeNodeDTO.setChildren(arrayList);
		}
		
		
		return treeNodeDTO;
	}
	
	
	public String showApprovalFlowStatus()throws Exception {
		if(this.knowledgeId==0)
			return null;
		
		MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		ApprovalFlow flow=this.approvalFlowService.getApprovalFlow(knowledge);
		if(flow==null)
			return null;
		
		writerFlowJson(flow);
		return null;
		
	}
	public String createApprovalFlowWithFirstNode()throws Exception {
		
			if(this.knowledgeId==0)
				return null;
			MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
			ApprovalFlow existFlow=this.approvalFlowService.getApprovalFlow(knowledge);
			if(existFlow!=null){
				
				writerFlowJson(existFlow);
				return null;
			}
			SystemUser initiator=getUser();
			ApprovalFlow flow=this.approvalFlowService.createApprovalFlow(initiator, knowledge);
			if(flow==null)
				return null;
			
			writerFlowJson(flow);
		
		
		return null;
		
	}
	public String createBatchApprovalFlowWithFirstNode()throws Exception {
		System.out.println("knowledgeIdString::"+knowledgeIdString);
		ArrayList al = new ArrayList();
		//pl 拿到js传过来的String
		String knowledgeidstring = knowledgeIdString;
		
		//pl 将拿到的String变为数组
		String[] knowledgeid = knowledgeidstring.split(" ");
		int num = 0;
		num = knowledgeid.length;
		long[] knowledgeidlong = new long[num];
		for(int i = 0;i<knowledgeid.length;i++){
			String s = knowledgeid[i];
			long l = Long.parseLong(s);
			knowledgeidlong[i] = l;
		}
		for(int a = 0;a<num;a++){
			ApprovalFlow flow;
			SystemUser initiator=getUser();
			knowledgeId = knowledgeidlong[a];
			
			if(this.knowledgeId==0)
				return null;
			MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
			ApprovalFlow existFlow=this.approvalFlowService.getApprovalFlow(knowledge);
			if(existFlow!=null){
				flow = existFlow;
			}else
				flow=this.approvalFlowService.createApprovalFlow(initiator, knowledge);
			if(flow==null)
				return null;
			
			FlowDTO flowDTO=new FlowDTO();
			flowDTO.setStatus(flow.getStatus());
			flowDTO.setId(flow.getId());
			List<FlowNodeDTO> nodeDTOs=new ArrayList<FlowNodeDTO>();
			List<FlowNode> nodes=flow.getFlowNodes();
			for(FlowNode node:nodes){
				FlowNodeDTO flowNodeDTO=new FlowNodeDTO();
				if(node.getApproverORLender()!=null){
					flowNodeDTO.setApproverORLenderName(node.getApproverORLender().getName());
				}
				flowNodeDTO.setNodeStatus(node.getNodeStatus());
				flowNodeDTO.setApprovalORBorrowOpinion(node.getApprovalORBorrowOpinion());
				nodeDTOs.add(flowNodeDTO);
			}
			flowDTO.setNodes(nodeDTOs);
		
			al.add(flowDTO);
		
		}
		JSONUtil.write(response, al);
		
		
		return null;
		
	}
	
	
	public String saveOrUpdateApprovalor() throws Exception {
		if(this.approvalFlowId==0||this.approvalorId==0)
			return null;
		SystemUser approvalor=this.userService.getUser(this.approvalorId);
		ApprovalFlow flow=this.approvalFlowService.getApprovalFlow(this.approvalFlowId);
		FlowNode currentNode=flow.getFlowNodes().get(flow.getIndex());
		currentNode.setApproverORLender(approvalor);
		currentNode.setNodeStatus(Constants.approvalFlowNodePending);
		flow.setStatus(Constants.approvalFlowPending);
		//可能还包括流状态
		this.approvalFlowService.saveApprovalFlow(flow);
		
		
		return null;
	}
	public String batchSaveOrUpdateApprovalor() throws Exception{
		ArrayList al = new ArrayList();
		String[] approvalFlowIdArray = approvalFlowIdString.split("@");
		for(int a = 0;a<approvalFlowIdArray.length;a++){
			approvalFlowId =  Long.parseLong(approvalFlowIdArray[a]);
			if(this.approvalFlowId==0||this.approvalorId==0)
				return null;
			SystemUser approvalor=this.userService.getUser(this.approvalorId);
			ApprovalFlow flow=this.approvalFlowService.getApprovalFlow(this.approvalFlowId);
			FlowNode currentNode=flow.getFlowNodes().get(flow.getIndex());
			currentNode.setApproverORLender(approvalor);
			currentNode.setNodeStatus(Constants.approvalFlowNodePending);
			flow.setStatus(Constants.approvalFlowPending);
			//可能还包括流状态
			this.approvalFlowService.saveApprovalFlow(flow);
			
			if(flow==null)
				return null;
			
			FlowDTO flowDTO=new FlowDTO();
			flowDTO.setStatus(flow.getStatus());
			flowDTO.setId(flow.getId());
			List<FlowNodeDTO> nodeDTOs=new ArrayList<FlowNodeDTO>();
			List<FlowNode> nodes=flow.getFlowNodes();
			for(FlowNode node:nodes){
				FlowNodeDTO flowNodeDTO=new FlowNodeDTO();
				if(node.getApproverORLender()!=null){
					flowNodeDTO.setApproverORLenderName(node.getApproverORLender().getName());
				}
				flowNodeDTO.setNodeStatus(node.getNodeStatus());
				flowNodeDTO.setApprovalORBorrowOpinion(node.getApprovalORBorrowOpinion());
				nodeDTOs.add(flowNodeDTO);
			}
			flowDTO.setNodes(nodeDTOs);
		
			al.add(flowDTO);
		}
		JSONUtil.write(response, al);
		return null;
	}
	public String approval() throws  Exception {
		
			
			//pl 定义一个String数组
			String[] knowledgeid = new String[]{};
			
			
			
			
			//pl 拿到js传过来的String
			String knowledgeidstring = knowledgeIdString;
			System.out.println(knowledgeidstring);
			
			knowledgeidstring = knowledgeidstring.substring(9);

			
			
			//pl 将拿到的String变为数组
			knowledgeid = knowledgeidstring.split(" ");
			int num = 0;
			num = knowledgeid.length;
			long[] knowledgeidlong = new long[num];
			
			//pl 将knowledgeid数组转变为long型的数组
			for(int i = 0;i<knowledgeid.length;i++){
//				System.out.println(knowledgeid[i]);
				String s = knowledgeid[i];
				long l = Long.parseLong(s);
				knowledgeidlong[i] = l;
			}
			
			
			
			System.out.println(opinion+":::::"+approvalResult);
			approvalResult = approvalResult.substring(9);
			opinion = opinion.substring(9);
			System.out.println(opinion+":::::"+approvalResult);
			for(int i = 0;i<knowledgeidlong.length;i++){
				knowledgeId = knowledgeidlong[i];
				
				if(knowledgeId==0||approvalResult==null)
	        		return null;
	        	MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
	        	if(knowledge==null)
	        		return null;
	        	ApprovalFlow flow=this.approvalFlowService.getApprovalFlow(knowledge);
	        	FlowNode currentNode=flow.getFlowNodes().get(flow.getIndex());
	        	SystemUser approvalor=getUser();
	        	if(approvalor.getId().equals(currentNode.getApproverORLender().getId())){
	        		currentNode.setApprovalORBorrowTime(new Date());
	        		currentNode.setApprovalORBorrowOpinion(opinion);
	        		currentNode.setNodeStatus(approvalResult);
	        		currentNode.setFlow(flow);
	        		if(approvalResult.equals(Constants.approvalFlowNodeResult_Pass)){
	        			if(flow.getFlowNodes().size()==Constants.approvalNodesLimit)
	        				flow.setStatus(Constants.approvalEnd);
	        			else
	        				flow.setStatus(Constants.approvalFlowPass);
	        			String knowledgeStatus=String.valueOf(flow.getIndex()+1);
	        			flow.getKnowledge().setStatus(knowledgeStatus);
	        			//如果第一级被审批通过则构建索引
	        			if(knowledgeStatus.equals("1"))
	        			{
	        				this.fulltextService.indexKnowledge(knowledge);
	        				
	        			}	
	        		}else if(approvalResult.equals(Constants.approvalFlowNodeResult_UnPass)){
	        			if(flow.getFlowNodes().size()==1){
	        				flow.setStatus(Constants.approvalFlowUNPass);
	        				flow.getKnowledge().setStatus("0");
	        			}
	        			
	        			flow.setStatus(Constants.approvalFlowUNPass);
	        		}else if(approvalResult.equals(Constants.approvalFlowNodeResult_PassButBlock)){
	        			flow.setStatus(Constants.approvalBlock);
	        			String knowledgeStatus=String.valueOf(flow.getIndex()+1);
	        			flow.getKnowledge().setStatus(knowledgeStatus);
	        		}
	        		
	        		
	        		this.approvalFlowService.saveApprovalFlow(flow);
	        		
	        		//writerFlowJson(this.approvalFlowService.getApprovalFlow(approvalFlowId));
	        	}
	        	
			}
			
		JSONUtil.write(response,num+"篇知识审批成功");
		return null;
	}
	
	public String createFlowNode()throws Exception {
		if(this.approvalFlowId==0)
			return null;
		ApprovalFlow flow=this.approvalFlowService.getApprovalFlow(this.approvalFlowId);
		if(flow.getFlowNodes().size()>=Constants.approvalNodesLimit)
			throw new Exception("最多进行"+Constants.approvalNodesLimit+"次审批");
		FlowNode newNode=new FlowNode();
		newNode.setAddTime(new Date());
		newNode.setInitiator(flow.getInitiator());
		newNode.setNodeStatus(Constants.approvalFlowNodeStart);
		newNode.setFlow(flow);
		
		this.approvalFlowService.addApprovalFlowNode(flow, newNode);
		writerFlowJson(this.approvalFlowService.getApprovalFlow(this.approvalFlowId));
		
		return null;
		
	}
	
	
	private void writerFlowNodeJson(FlowNode flowNode){
		
		FlowNodeDTO nodeDTO=new FlowNodeDTO();
		
		nodeDTO.setId(flowNode.getId());
		if(flowNode.getApproverORLender()!=null){
			nodeDTO.setApproverORLenderName(flowNode.getApproverORLender().getName());
		}
		if(flowNode.getInitiator()!=null){
			nodeDTO.setInitiatorId(flowNode.getInitiator().getId());
		}
		
		nodeDTO.setNodeStatus(flowNode.getNodeStatus());
		
		
		JSONUtil.write(response, nodeDTO);
		
		
	}
	
	

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
//		System.out.println("ssssssss");
		return INPUT;
	}

	@Override
	public String list() throws Exception {
		if(this.knowledgeId<=0){
			throw new Exception("未传入知识参数");
		}else{
			//approvalFlowService.getApproval
		}
		return RELOAD;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 保存审批申请
	 */
	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		/*SystemUser initiator = (SystemUser) request.getAttribute("systemUser");
		SystemUser qualifiedApprover = userService.getUser(userId);
		MetaKnowledge knowledge = metaKnowledgeDao.get(knowledgeId);
		
		approvalFlowService.createApprovalFlow(initiator, knowledge, qualifiedApprover);
		*/
		System.out.println("保存");
		
		return null;
	}
	
	public String createFirstApproval() throws Exception {
		SystemUser creater=getUser();
		if(this.knowledgeId==0||creater==null){
			return null;
		}
		MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(this.knowledgeId);
		if(this.approvalFlowService.getApprovalFlow(knowledge)!=null)
			return null;
		
		ApprovalFlow newApprovalFlow=this.approvalFlowService.createApprovalFlow(creater, knowledge);
		
		writerFlowJson(newApprovalFlow);
		
		
		return null;
	}
	
	/**
	 * 查找某个知识的审批状态
	 */
	public String listApprovalFlowStatus() throws Exception{
		if(this.knowledgeId==0){
			return null;
		}
		MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(this.knowledgeId);
		ApprovalFlow approvalFlow=this.approvalFlowService.getApprovalFlow(knowledge);
		if(approvalFlow==null)
			return null;
		writerFlowJson(approvalFlow);
		return null;
	}
	
	
	private SystemUser getUser(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String email=securityContext.getAuthentication().getName();
		SystemUser user=userService.getUser(email);
		return user;
	}
	
	private void writerFlowJson(ApprovalFlow flow) throws Exception{
			FlowDTO flowDTO=new FlowDTO();
			flowDTO.setStatus(flow.getStatus());
			flowDTO.setId(flow.getId());
			List<FlowNodeDTO> nodeDTOs=new ArrayList<FlowNodeDTO>();
			List<FlowNode> nodes=flow.getFlowNodes();
			for(FlowNode node:nodes){
				FlowNodeDTO flowNodeDTO=new FlowNodeDTO();
				if(node.getApproverORLender()!=null){
					flowNodeDTO.setApproverORLenderName(node.getApproverORLender().getName());
				}
				flowNodeDTO.setNodeStatus(node.getNodeStatus());
				flowNodeDTO.setApprovalORBorrowOpinion(node.getApprovalORBorrowOpinion());
				nodeDTOs.add(flowNodeDTO);
			}
			flowDTO.setNodes(nodeDTOs);
		
			
		
		JSONUtil.write(response, flowDTO);
		
	}
	
	public String update() throws Exception {
	
		
		
		return null;
	}


	public ApprovalFlow getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}


//	public long getKnowledgeId() {
//		return knowledgeId;
//	}
//
//	public void setKnowledgeId(long knowledgeId) {
//		this.knowledgeId = knowledgeId;
//	}


	
	

	public ApprovalFlowService getApprovalFlowService() {
		return approvalFlowService;
	}
	@Autowired
	public void setApprovalFlowService(ApprovalFlowService approvalFlowService) {
		this.approvalFlowService = approvalFlowService;
	}

	public KnowledgeService getKnowledgeService() {
		return knowledgeService;
	}
	@Autowired
	public void setKnowledgeService(KnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}

	public UserService getUserService() {
		return userService;
	}
	
	public FullTextService getFulltextService() {
		return fulltextService;
	}
	@Autowired
	public void setFulltextService(FullTextServiceImpl fulltextService) {
		this.fulltextService = fulltextService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public long getApprovalorId() {
		return approvalorId;
	}

	public void setApprovalorId(long approvalorId) {
		this.approvalorId = approvalorId;
	}

	public long getApprovalFlowId() {
		return approvalFlowId;
	}

	public void setApprovalFlowId(long approvalFlowId) {
		this.approvalFlowId = approvalFlowId;
	}

	public long getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public String getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public TreeService getTreeServiceImpl() {
		return treeServiceImpl;
	}
	@Autowired
	public void setTreeServiceImpl(TreeService treeServiceImpl) {
		this.treeServiceImpl = treeServiceImpl;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
