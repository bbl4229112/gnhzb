package edu.zju.cims201.GOF.dao.classificationtree;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTreeA;

@Repository 
public class ClassificationTreeADao extends HibernateDao<ClassificationTreeA,Long>{
/*
	@Resource(name="sessionFactoryBiaozhun")
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}*/
}
