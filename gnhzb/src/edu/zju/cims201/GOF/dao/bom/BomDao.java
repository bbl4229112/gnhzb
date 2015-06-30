package edu.zju.cims201.GOF.dao.bom;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojoA.Bom;

@Repository
public class BomDao extends HibernateDao<Bom, Long>{

}
