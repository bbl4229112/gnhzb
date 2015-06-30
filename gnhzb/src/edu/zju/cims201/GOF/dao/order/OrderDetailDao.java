package edu.zju.cims201.GOF.dao.order;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.OrderDetail;

@Repository
public class OrderDetailDao extends HibernateDao<OrderDetail, Long> {

}
