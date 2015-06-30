package edu.zju.cims201.GOF.dao.knowledge;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.OntoCommentRating;
import edu.zju.cims201.GOF.hibernate.pojo.Rating;



/**
 * 授权对象的泛型DAO.
 * 
 * @author cwd
 */
@Component
public class OntoCommentRatingDao extends HibernateDao<OntoCommentRating, Long> {
	
}
