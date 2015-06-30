package edu.zju.cims201.GOF.dao.module;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.Material;


@Component
public class MaterialDao extends HibernateDao<Material, Long>{

}
