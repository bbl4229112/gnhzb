package edu.zju.cims201.GOF.dao.logging;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;

@Component
public class SysBehaviorLogDao extends HibernateDao<SysBehaviorLog, Long>{

}
