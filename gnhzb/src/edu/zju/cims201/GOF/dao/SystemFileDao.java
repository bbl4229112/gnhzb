package edu.zju.cims201.GOF.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;


@Component
public class SystemFileDao extends HibernateDao<SystemFile, Long>{

}
