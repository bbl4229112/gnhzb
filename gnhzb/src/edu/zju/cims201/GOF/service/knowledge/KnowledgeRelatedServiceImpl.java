package edu.zju.cims201.GOF.service.knowledge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.knowledge.KnowledgeRelatedDao;
import edu.zju.cims201.GOF.hibernate.pojo.KnowledgeRelated;

@Service
@Transactional
public class KnowledgeRelatedServiceImpl implements KnowledgeRelatedService {

	@Resource(name="knowledgeRelatedDao")
	private KnowledgeRelatedDao knowledgeRelatedDao;

	public List<KnowledgeRelated> KnowledgeRelatedList(String xmlFigureId, String type) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("xmlFigureId" , xmlFigureId);
		params.put("type", type);
		String hql = "from KnowledgeRelated where xmlFigureId=:xmlFigureId and type=:type";
		Query query = knowledgeRelatedDao.createQuery(hql, params);
		List<KnowledgeRelated> list = query.list();
		return list;
	}

	public String addKnowledgeRelated(KnowledgeRelated knowledgeRelated) {
		knowledgeRelatedDao.save(knowledgeRelated);
		knowledgeRelatedDao.flush();
		return "1";
	}

	public String deleteKnowledgeRelated(String xmlFigureId, Long knowledgeId) {
		knowledgeRelatedDao.delete(this.getRecord(xmlFigureId, knowledgeId));
		knowledgeRelatedDao.flush();
		return "1";
	}
	
	public String deleteKnowledgeRelated(Long id) {
		knowledgeRelatedDao.delete(id);
		knowledgeRelatedDao.flush();
		return "1";
	}
	
	public boolean isKnowledgeRelatedExist(String xmlFigureId, Long knowledgeId, String type) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("xmlFigureId" , xmlFigureId);
		params.put("knowledgeId", knowledgeId);
		params.put("type", type);
		String hql = "from KnowledgeRelated where xmlFigureId=:xmlFigureId and knowledgeId=:knowledgeId and type=:type";
		Query query = knowledgeRelatedDao.createQuery(hql, params);
		List<KnowledgeRelated> list = query.list();
		if(list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public KnowledgeRelated getRecord(String xmlFigureId, Long knowledgeId) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("xmlFigureId" , xmlFigureId);
		params.put("knowledgeId", knowledgeId);
		String hql = "from KnowledgeRelated where xmlFigureId=:xmlFigureId and knowledgeId=:knowledgeId";
		Query query = knowledgeRelatedDao.createQuery(hql, params);
		List<KnowledgeRelated> list = query.list();
		KnowledgeRelated kr = list.get(0);		
		return kr;
	}

	public List<KnowledgeRelated> getPositionsByKnowledge(Long knowledgeId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("knowledgeId", knowledgeId);
		String hql = "from KnowledgeRelated where knowledgeId=:knowledgeId";
		Query query = knowledgeRelatedDao.createQuery(hql, params);
		List<KnowledgeRelated> kr = query.list();
		return kr;
	}	
	
	
}
