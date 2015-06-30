package edu.zju.cims201.GOF.dao.platform;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.PlatformStatus;
@Repository
public class PlatformStatusDao extends HibernateDao<PlatformStatus, Long> {

}
