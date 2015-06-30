package edu.zju.cims201.GOF.dao.task;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.pdm.LcaTask;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmTask;



@Component
public class PdmtaskDAO extends HibernateDao<PdmTask,Long> {

}
