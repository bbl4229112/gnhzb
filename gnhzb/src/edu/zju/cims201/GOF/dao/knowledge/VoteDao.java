package edu.zju.cims201.GOF.dao.knowledge;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.Vote;


/**
 * 授权对象的泛型DAO.
 * 
 * @author cwd
 */
@Component
public class VoteDao extends HibernateDao<Vote, Long> {
	
}
