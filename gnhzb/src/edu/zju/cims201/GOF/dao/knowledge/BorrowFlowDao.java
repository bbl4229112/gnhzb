package edu.zju.cims201.GOF.dao.knowledge;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlow;

@Component
public class BorrowFlowDao extends HibernateDao<BorrowFlow, Long> {

}
