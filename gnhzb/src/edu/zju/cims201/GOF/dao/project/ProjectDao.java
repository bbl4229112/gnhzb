package edu.zju.cims201.GOF.dao.project;



import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmProject;



/**
 *  项目管理dao
 * @author yetao
 *
 */
@Component
public class ProjectDao extends HibernateDao<PdmProject, Long>{

}
