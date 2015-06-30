package edu.zju.cims201.GOF.service.knowledge;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojo.KnowledgeRelated;

@Transactional
public interface KnowledgeRelatedService {
	
	public KnowledgeRelated getRecord(String xmlFigureId, Long knowledgeI);
	
	public List<KnowledgeRelated> KnowledgeRelatedList(String xmlFigureId, String type);
	
	public String addKnowledgeRelated(KnowledgeRelated knowledgeRelated);
	
	public String deleteKnowledgeRelated(String xmlFigureId, Long knowledgeId);
	
	public String deleteKnowledgeRelated(Long id);
	
	public boolean isKnowledgeRelatedExist(String xmlFigureId, Long knowledgeId, String type);
	
	public List<KnowledgeRelated> getPositionsByKnowledge(Long knowledgeId);
}
