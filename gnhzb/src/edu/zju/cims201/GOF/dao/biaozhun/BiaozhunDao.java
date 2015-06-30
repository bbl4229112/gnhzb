package edu.zju.cims201.GOF.dao.biaozhun;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import edu.zju.cims201.GOF.hibernate.pojoA.Biaozhun;

@Component
public class BiaozhunDao extends HibernateDao<Biaozhun, Long> {
	
	//@Autowired
	public void setSessionFactory(final @Qualifier("sessionFactoryA")SessionFactory sessionFactory) {
          this.sessionFactory = sessionFactory;
	}

}
