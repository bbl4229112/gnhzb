package edu.zju.cims201.GOF.dao.demand;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.DemandManage;

@Repository
public class DemandManageDao extends HibernateDao<DemandManage, Long> {

}
