package edu.zju.cims201.GOF.dao.codeclass;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.CodeClassA;
@Repository 
public class CodeClassADao extends HibernateDao<CodeClassA,Long>{
	
/*
	@Resource(name="sessionFactoryBiaozhun")
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}*/
}
