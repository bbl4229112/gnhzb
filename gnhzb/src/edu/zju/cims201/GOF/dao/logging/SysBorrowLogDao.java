package edu.zju.cims201.GOF.dao.logging;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;
import edu.zju.cims201.GOF.hibernate.pojo.SysBorrowLog;

@Component
public class SysBorrowLogDao extends HibernateDao<SysBorrowLog, Long>{

}
