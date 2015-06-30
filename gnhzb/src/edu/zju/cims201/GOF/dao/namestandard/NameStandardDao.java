package edu.zju.cims201.GOF.dao.namestandard;


import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.NameStandard;
@Repository
public class NameStandardDao extends HibernateDao<NameStandard,Long>{
	//@Resource(name="sessionFactoryBiaozhun")
/*	public void setSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}*/
}
