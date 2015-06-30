package edu.zju.cims201.GOF.service.approvalFlow;

import java.util.List;
import java.util.Set;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import edu.zju.cims201.GOF.hibernate.pojo.ApprovalFlow;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
//import edu.zju.cims201.GOF.entity.account.Role;
//import edu.zju.cims201.GOF.entity.account.SUser;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.QualifiedUsers;

/**
 * 提供关于知识的服务接口，由具体的实现类来实现接口定义的方法
 * 
 * @author hebi
 */

public interface ApprovalFlowService {
	
	/**
	 * 
	 * @param user
	 * @return 某用户待审批的所有知识
	 */
	public List<MetaKnowledge> listNeededApprovalKnowledge(SystemUser user) ;
	
	/**
	 * 
	 * @param initiator
	 * @return 有资格审批人所在的角色树节点
	 */
	public Set<TreeNode> listQualifiedNodes(SystemUser initiator);
	
	/**
	 * 创建审批流程
	 * @param initiator
	 * @param knowledge
	 * @return ApprovalFlow
	 */
	public ApprovalFlow createApprovalFlow(SystemUser initiator,MetaKnowledge knowledge);
	
	/**
	 * 查找特定知识的审批流
	 * @param knowledge
	 * @return ApprovalFlow
	 */
	public ApprovalFlow getApprovalFlow(MetaKnowledge knowledge);
	
	/**
	 * 查找特定的审批流
	 * @param approvalFlowID
	 * @return
	 */
	public ApprovalFlow getApprovalFlow(Long approvalFlowID);
	
	/**
	 * 对指定审批流增加审批节点
	 * @param added
	 * @param flowNode
	 * @return String 操作结果
	 */
	public String addApprovalFlowNode(ApprovalFlow added,FlowNode flowNode);
	
	
	public String endApprovalFlow(ApprovalFlow approvalFlow);
	
	
	/**
	 * 审批流是否可以更改
	 * @param approvalFlow
	 * @return
	 */
	public boolean isApprovalFlowModifiable(ApprovalFlow approvalFlow);
	
	/**
	 * 保存审批流，级联保存审批节点
	 * @param approvalFlow
	 * @return
	 */
	public String saveApprovalFlow(ApprovalFlow approvalFlow);
	
	
	/**
	 * 列出用户所有通过审批的知识
	 * @param user 用户
	 * @param page
	 * @return
	 */
	
	public Page<MetaKnowledge> listApprovalPassedKnowledge(SystemUser user, Page<MetaKnowledge> page);
	
	
	
	/**
	 * 列出用户所有走完审批流程的知识，包括中结，和走完流程的
	 * @param user
	 * @param page
	 * @return
	 */
	
	
	public Page<MetaKnowledge> listApprovalEndOrBlockedKnowledge(SystemUser user, Page<MetaKnowledge> page);
	
	/**
	 * 列出用户未发起审批的知识
	 * @param user
	 * @param page
	 * @return
	 */
	
	public Page<MetaKnowledge> listUnApprovalKnowledge(SystemUser user, Page<MetaKnowledge> page);
	
	
	/**
	 * 列出需要用户审批的知识
	 * @param user
	 * @param page
	 * @return
	 */
	public Page<MetaKnowledge> listNeededApprovalKnowledge(SystemUser user, Page<MetaKnowledge> page) ;
	
	/**
	 * 列出用户发起的，需要别人审批的知识
	 * @param user
	 * @param page
	 * @return
	 */
	public Page<MetaKnowledge> listPendingApprovalKnowledge(SystemUser user, Page<MetaKnowledge> page) ;
	
	
	public Page<MetaKnowledge> listApprovaledKnowledge(SystemUser user, Page<MetaKnowledge> page) ;
	
	
	/**
	 * 审批知识
	 * @param user
	 * @param page
	 * @return
	 */
	public ApprovalFlow approve(ApprovalFlow approvalFlow);
	/**
	 * 删除审批流
	 * @param metaknowledge
	 * @author 吉祥
	 */
	public void deleteApprovalFlow(MetaKnowledge k);
	
	
}
