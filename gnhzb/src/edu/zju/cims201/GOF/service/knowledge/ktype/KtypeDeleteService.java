package edu.zju.cims201.GOF.service.knowledge.ktype;

import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
@Transactional
public interface KtypeDeleteService {

	/**
	 * @author panlei
	 * 根据知识删除Attachment
	 * 
	 * */
	public void deleteAttachmentByK(MetaKnowledge mt);
	/**
	 * @author panlei
	 * 根据知识删除ExpertUnanswer
	 * 
	 * */
	public void deleteExpertUnanswerByK(MetaKnowledge mt);
	/**
	 * @author panlei
	 * 根据知识删除Flow和FlowNode
	 * 
	 * */
	public void deleteFlowByK(MetaKnowledge mt);
	/**
	 * @author panlei
	 * 根据知识删除Rating
	 * 
	 * */
	public void deleteRatingByK(MetaKnowledge mt);
	/**
	 * @author panlei
	 * 根据知识删除KnowledgeRelated
	 * 
	 * */
	public void deleteKnowledgeRelatedByK(MetaKnowledge mt);
	/**
	 * @author panlei
	 * 根据知识删除知识模板属性
	 * 
	 * */
	public void deleteKTypePropertydByK(Ktype kt);
	
}
