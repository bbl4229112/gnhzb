package edu.zju.cims201.GOF.dao.message;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.Message;

@Component
public class MessageDao extends HibernateDao<Message, Long>{

}
