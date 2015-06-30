package edu.zju.cims201.GOF.service.approvalFlow;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.google.common.collect.Sets;
import com.sun.org.apache.commons.beanutils.BeanUtils;

import edu.zju.cims201.GOF.dao.knowledge.ApprovalFlowDao;
import edu.zju.cims201.GOF.dao.knowledge.FlowNodeDao;
import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.dao.privilege.AdminPrivilegeTripleDao;
import edu.zju.cims201.GOF.dao.privilege.RolePrivilegeTripleDao;
import edu.zju.cims201.GOF.dao.privilege.UserPrivilegeTripleDao;
import edu.zju.cims201.GOF.dao.tree.DomainTreeNodeDao;
import edu.zju.cims201.GOF.hibernate.pojo.AdminPrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.AdminUser;
import edu.zju.cims201.GOF.hibernate.pojo.ApprovalFlow;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.RolePrivilegeTriple;
import edu.zju.cims201.GOF.hibernate.pojo.RoleTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.*;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.service.privilege.SpringSecurityService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.web.privilege.PrivilegeConstants;

/**
 * i
 * 
 * @author hebi
 */
@Service
@Transactional 
public class ApprovalFlowServiceImpl implements ApprovalFlowService {
	
	private SessionFactory sessionFactory;
	private ApprovalFlowDao approvalFlowDao;
	private TreeService treeServiceImpl;
	private MetaKnowledgeDao knowledgeDao;
	
	private SpringSecurityService springSecurityService;
	
	private FlowNodeDao flowNodeDao;

	public String addApprovalFlowNode(ApprovalFlow added, FlowNode flowNode) {
		added.getFlowNodes().add(flowNode);
		added.setIndex((added.getIndex()+1));
		return null;
	}

	public ApprovalFlow approve(ApprovalFlow approvalFlow) {
		
		return null;
	}
	
	//新创建审批流，默认创建一个审批节点。

	public ApprovalFlow createApprovalFlow(SystemUser initiator, MetaKnowledge knowledge) {
		ApprovalFlow approvalFlow=new ApprovalFlow();
		approvalFlow.setInitiator(initiator);
		approvalFlow.setKnowledge(knowledge);
		FlowNode firstNode=new FlowNode();
		firstNode.setInitiator(initiator);
		firstNode.setAddTime(new Date());
		firstNode.setNodeStatus(Constants.approvalFlowNodeStart);
		firstNode.setFlow(approvalFlow);
		ArrayList<FlowNode> nodes=new ArrayList<FlowNode>();
		nodes.add(firstNode);
		approvalFlow.setFlowNodes(nodes);
		approvalFlow.setIndex(0);
		approvalFlow.setStartTime(new Date());
		approvalFlow.setStatus(Constants.approvalFlowStart);
		approvalFlowDao.save(approvalFlow);
		approvalFlowDao.flush();
		return approvalFlow;
	}

	public String endApprovalFlow(ApprovalFlow approvalFlow) {
		// TODO 自动生成方法存根
		return null;
	}
	
	public ApprovalFlow getApprovalFlow(MetaKnowledge knowledge) {	
		return approvalFlowDao.findUniqueBy("knowledge", knowledge);
	}

	public ApprovalFlow getApprovalFlow(Long approvalFlowID) {
		return approvalFlowDao.findUniqueBy("id", approvalFlowID);
	}

	public boolean isApprovalFlowModifiable(ApprovalFlow approvalFlow) {
		if(Constants.approvalEnd.equals(approvalFlow.getStatus())||Constants.approvalBlock.equals(approvalFlow.getStatus()))
			return false;
		return true;
	}
	public Page<MetaKnowledge> listApprovaledKnowledge(SystemUser user, Page<MetaKnowledge> page){
		String hql="select  knowledge from MetaKnowledge knowledge,ApprovalFlow flow,FlowNode node where flow.knowledge=knowledge and " +
				"node.flow=flow and node.approverORLender=? and (node.nodeStatus='"+Constants.approvalFlowNodeResult_Pass+"' or node.nodeStatus='"+
				Constants.approvalFlowNodeResult_PassButBlock+
						"' or node.nodeStatus='"+Constants.approvalFlowNodeResult_UnPass+"') and knowledge.isvisible=true";
		Page<MetaKnowledge> newpage=this.knowledgeDao.findPage(page,hql,user);
		
		return newpage;
	}

	

	public Page<MetaKnowledge> listNeededApprovalKnowledge(SystemUser user, Page<MetaKnowledge> page) {

		String hql="select  knowledge from MetaKnowledge knowledge,ApprovalFlow flow,FlowNode node where flow.knowledge=knowledge and " +
				"node.flow=flow and node.approverORLender=? and node.nodeStatus=? and knowledge.isvisible=true";
		Page<MetaKnowledge> newpage=this.knowledgeDao.findPage(page,hql,user,Constants.approvalFlowNodePending);

		return newpage;
	}
	
	public List<MetaKnowledge> listNeededApprovalKnowledge(SystemUser user) {
		String hql="select knowledge from MetaKnowledge knowledge,ApprovalFlow flow,FlowNode node where flow.knowledge=knowledge and " +
				"node.flow=flow and node.approverORLender=? and node.nodeStatus=? and knowledge.isvisible=true";
		//this.knowledgeDao.findPage(page,hql,user,Constants.approvalFlowNodePending);
		return this.knowledgeDao.find(hql, user,Constants.approvalFlowNodePending);
	}

	public Page<MetaKnowledge> listPendingApprovalKnowledge(SystemUser user, Page<MetaKnowledge> page) {
		String hql="select knowledge from MetaKnowledge knowledge,ApprovalFlow flow where flow.knowledge=knowledge and  flow.initiator=?" +
		" and flow.status=? and knowledge.isvisible=true ";
		
		Page<MetaKnowledge> newpage=this.knowledgeDao.findPage(page,hql,user,Constants.approvalFlowPending);
		
		return newpage;
	}

	public Set<TreeNode> listQualifiedNodes(SystemUser initiator) {
		
		

		Set<TreeNode> directNodes=initiator.getRoleNodes();
		Set<TreeNode> roots=new HashSet<TreeNode>();
		for(TreeNode node:directNodes){
			while(node.getParentId()!=null){
				node=this.treeServiceImpl.getTreeNode(node.getParentId());

			}
			roots.add(node);
		}
		
		return roots;
	}
	



	public String saveApprovalFlow(ApprovalFlow approvalFlow) {
		approvalFlowDao.save(approvalFlow);
		approvalFlowDao.flush();

		return null;
	}
	


	

	@Autowired
	public void setApprovalFlowDao(ApprovalFlowDao approvalFlowDao) {
		this.approvalFlowDao = approvalFlowDao;
	}


	public TreeService getTreeServiceImpl() {
		return treeServiceImpl;
	}
		


	public List<FlowNode> listAllApprovalTreeNode(MetaKnowledge knowledge) {
		// TODO Auto-generated method stub
		return null;

	}
	@Autowired
	public void setTreeServiceImpl(TreeService treeServiceImpl) {
		this.treeServiceImpl = treeServiceImpl;
	}

	public ApprovalFlowDao getApprovalFlowDao() {
		return approvalFlowDao;
	}

	public Page<MetaKnowledge> listApprovalEndOrBlockedKnowledge(SystemUser user, Page<MetaKnowledge> page) {
		
		String hql="select knowledge from MetaKnowledge knowledge,ApprovalFlow flow where flow.knowledge=knowledge and  flow.initiator=?" +

				" and( flow.status ='"+Constants.approvalEnd+"'or flow.status ='"+Constants.approvalBlock+"'  or flow.status ='"+Constants.approvalFlowUNPass+"' ) and knowledge.isvisible=true order by knowledge.uploadtime desc";

		
		Page<MetaKnowledge> newpage=this.knowledgeDao.findPage(page,hql,user);
	
		return newpage;
	}

	public Page<MetaKnowledge> listApprovalPassedKnowledge(SystemUser user, Page<MetaKnowledge> page) {
		
		String hql="select knowledge from MetaKnowledge knowledge,ApprovalFlow flow where flow.knowledge=knowledge and  flow.initiator=?" +
		" and (flow.status='"+Constants.approvalEnd+"' or flow.status= '"+Constants.approvalBlock+"'or flow.status='"+Constants.approvalFlowPass+"') and knowledge.isvisible=true order by knowledge.uploadtime desc";
		Page<MetaKnowledge> newpage=this.knowledgeDao.findPage(page,hql,user);
		return newpage;
	}

	public Page<MetaKnowledge> listUnApprovalKnowledge(SystemUser user, Page<MetaKnowledge> page) {
		
		
		String hql="select knowledge from MetaKnowledge knowledge where knowledge.status=? and knowledge.uploader=? and knowledge.isvisible=true order by knowledge.uploadtime desc";
		
		Page<MetaKnowledge> newpage=this.knowledgeDao.findPage(page,hql,"0",user);
		
		return newpage;
	}

	public MetaKnowledgeDao getKnowledgeDao() {
		return knowledgeDao;
	}
	@Autowired
	public void setKnowledgeDao(MetaKnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}

	public FlowNodeDao getFlowNodeDao() {
		return flowNodeDao;
	}
	@Autowired
	public void setFlowNodeDao(FlowNodeDao flowNodeDao) {
		this.flowNodeDao = flowNodeDao;
	}

	public SpringSecurityService getSpringSecurityService() {
		return springSecurityService;
	}
	@Autowired
	public void setSpringSecurityService(SpringSecurityService springSecurityService) {
		this.springSecurityService = springSecurityService;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void deleteApprovalFlow(MetaKnowledge k) {
		ApprovalFlow flow=this.getApprovalFlow(k);
		if(flow!=null){
			approvalFlowDao.delete(flow);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
