package edu.zju.cims201.GOF.dao.part;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.Part;
@Repository
public class PartDao extends HibernateDao<Part, Long> {
	//@Resource(name="sessionFactoryBiaozhun")
/*	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}*/
}
