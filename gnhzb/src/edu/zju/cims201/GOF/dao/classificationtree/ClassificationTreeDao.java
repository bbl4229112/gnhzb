package edu.zju.cims201.GOF.dao.classificationtree;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTree;

@Repository 
public class ClassificationTreeDao extends HibernateDao<ClassificationTree,Long>{
/*
	@Resource(name="sessionFactoryBiaozhun")
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}*/
}
