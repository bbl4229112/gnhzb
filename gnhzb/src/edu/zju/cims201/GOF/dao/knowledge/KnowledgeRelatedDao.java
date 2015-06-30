package edu.zju.cims201.GOF.dao.knowledge;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.KnowledgeRelated;

@Component
public class KnowledgeRelatedDao extends HibernateDao<KnowledgeRelated, Long> {

}
