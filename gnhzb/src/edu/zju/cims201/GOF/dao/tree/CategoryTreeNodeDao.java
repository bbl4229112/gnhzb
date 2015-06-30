package edu.zju.cims201.GOF.dao.tree;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;

@Component
public class CategoryTreeNodeDao extends HibernateDao<CategoryTreeNode,Long> {

}
