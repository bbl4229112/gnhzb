package edu.zju.cims201.GOF.dao.user;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;


@Component
public class SystemUserDao extends HibernateDao<SystemUser,Long> {

}
