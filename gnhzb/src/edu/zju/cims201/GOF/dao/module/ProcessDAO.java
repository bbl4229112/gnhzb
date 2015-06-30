package edu.zju.cims201.GOF.dao.module;

import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.ProcessTemplate;



@Service
public class ProcessDAO extends HibernateDao<ProcessTemplate,Long> {

}
