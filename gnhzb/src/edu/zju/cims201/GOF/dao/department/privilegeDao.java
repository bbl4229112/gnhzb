package edu.zju.cims201.GOF.dao.department;



import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.pdm.Privilege;

/**
 *  项目管理dao
 * @author yetao
 *
 */
@Component
public class privilegeDao extends HibernateDao<Privilege, Long>{

}
