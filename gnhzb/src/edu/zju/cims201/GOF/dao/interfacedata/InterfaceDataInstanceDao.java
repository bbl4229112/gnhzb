package edu.zju.cims201.GOF.dao.interfacedata;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.InterfaceDataInstance;
@Repository
public class InterfaceDataInstanceDao extends HibernateDao<InterfaceDataInstance, Long> {
/*	@Resource(name="sessionFactoryBiaozhun")
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}*/
}
