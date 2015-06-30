package edu.zju.cims201.GOF.dao.privilege;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.UserPrivilegeTriple;

@Component
public class UserPrivilegeTripleDao extends HibernateDao<UserPrivilegeTriple,Long> {

}
