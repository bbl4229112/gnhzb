package edu.zju.cims201.GOF.dao.knowledge;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.KeepTreeNode;



/**
 * 授权对象的泛型DAO.
 * 
 * @author pl
 */
@Component
public class KeepTreeNodeDao extends HibernateDao<KeepTreeNode, Long> {
	
}
