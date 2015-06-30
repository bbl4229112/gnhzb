package edu.zju.cims201.GOF.dao.privilege;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.RolePrivilegeTriple;

@Component
public class RolePrivilegeTripleDao extends HibernateDao<RolePrivilegeTriple,Long>{

}
