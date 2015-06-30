package edu.zju.cims201.GOF.dao.sml;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.SmlCodePool;
@Repository
public class SmlCodePoolDao extends HibernateDao<SmlCodePool, Long> {
	//@Resource(name="sessionFactoryBiaozhun")
/*	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}*/
}
