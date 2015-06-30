package edu.zju.cims201.GOF.service.knowledge.ktype;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.knowledge.AttachmentDao;
import edu.zju.cims201.GOF.dao.knowledge.AuthorDao;
import edu.zju.cims201.GOF.dao.knowledge.FlowDao;
import edu.zju.cims201.GOF.dao.knowledge.FlowNodeDao;
import edu.zju.cims201.GOF.dao.knowledge.KnowledgeRelatedDao;
import edu.zju.cims201.GOF.dao.knowledge.KtypePropertyDao;
import edu.zju.cims201.GOF.dao.knowledge.RatingDao;
import edu.zju.cims201.GOF.dao.user.ExpertDao;
import edu.zju.cims201.GOF.hibernate.pojo.Attachment;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.Flow;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.KnowledgeRelated;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Rating;
@Service
@Transactional
public class KtypeDeleteServiceImpl implements KtypeDeleteService {

	@Resource(name = "attachmentDao")
	private AttachmentDao  attachmentDao;
	@Resource(name = "flowDao")
	private FlowDao  flowDao;
	@Resource(name = "flowNodeDao")
	private FlowNodeDao  flowNodeDao;
	@Resource(name = "ratingDao")
	private RatingDao  ratingDao;
	@Resource(name = "ktypePropertyDao")
	private KtypePropertyDao  ktypePropertyDao;
	@Resource(name = "knowledgeRelatedDao")
	private KnowledgeRelatedDao  knowledgeRelatedDao;
	
	
	
	
	
	public void deleteAttachmentByK(MetaKnowledge mt) {
		List<Attachment> mks = this.attachmentDao.findBy("knowledge", mt);
		for(Attachment a : mks){
			this.attachmentDao.delete(a);			
		}
	}

	public void deleteExpertUnanswerByK(MetaKnowledge mt) {
		//下面绝对是有问题的
//		String queryString="from ExpertUnswer eu where eu.knowledge.id=?";
//		List<Object> list=this.expertDao.createQuery(queryString, mt.getId()).list();
		
	}

	public void deleteFlowByK(MetaKnowledge mt) {
		List<Flow> mks = this.flowDao.findBy("knowledge", mt);
		for(Flow a : mks){
			List<FlowNode> mks2 = this.flowNodeDao.findBy("flow", a);
			for(FlowNode fn : mks2){
				this.flowNodeDao.delete(fn);
			}
			this.flowDao.delete(a);			
		}
	}

	public void deleteRatingByK(MetaKnowledge mt) {
		List<Rating> mks = this.ratingDao.findBy("knowledge", mt);
		for(Rating a : mks){
			this.ratingDao.delete(a);			
		}
	}

	public void deleteKnowledgeRelatedByK(MetaKnowledge mt) {
		List<KnowledgeRelated> mks = this.knowledgeRelatedDao.findBy("knowledgeId", mt.getId());
		for(KnowledgeRelated a : mks){
			this.knowledgeRelatedDao.delete(a);			
		}
	}

	public void deleteKTypePropertydByK(Ktype kt) {
		List<KtypeProperty> mks = this.ktypePropertyDao.findBy("ktype", kt);
		for(KtypeProperty a : mks){
			this.ktypePropertyDao.delete(a);			
		}
	}

}
