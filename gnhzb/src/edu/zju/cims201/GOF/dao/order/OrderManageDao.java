package edu.zju.cims201.GOF.dao.order;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.OrderManage;

@Repository
public class OrderManageDao extends HibernateDao<OrderManage, Long> {

}
