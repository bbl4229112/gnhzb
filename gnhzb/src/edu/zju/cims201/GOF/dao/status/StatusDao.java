package edu.zju.cims201.GOF.dao.status;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.Status;
@Repository
public class StatusDao extends HibernateDao<Status, Long> {

}
