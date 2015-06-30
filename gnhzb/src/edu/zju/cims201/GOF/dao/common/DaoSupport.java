package edu.zju.cims201.GOF.dao.common;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
//import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.rs.dto.PageDTO;

//import edu.zju.cims201.GOF.dao.QueryResult;

@Transactional
/**
 * 抽象类
 * 
 * 实现Dao接口，不再单独根据pojo建立对应的dao文件，
 * 利用范式将操作的pojo和对应需要的类提供数据交互操作
 * 
 * @author hebi
 */
public abstract class DaoSupport implements DAO {
	
	@Resource(name="sessionFactory")
	protected SessionFactory sessionFactory;
	//protected EntityManager em;
	/**
	 *统一删除的方法1，通过id删除某一对象的实例
	 * @param entityClass 需要删除的对象的类，用于匹配泛型操作
	 * @param entityid 需要删除的对象的实例的id
	 *        
	 * @author hebi
	 */
	public <T> void delete(Class<T> entityClass,Object entityid) {
		delete(entityClass,new Object[]{entityid});
		
	}
	/**
	 *统一删除的方法2，通过id数组删除某一对象的多个对象
	 * @param entityClass 需要操作的对象类，用于匹配泛型操作
	 * @param entityids 需要删除的对象的实例的id的数组
	 * @author hebi
	 */

	public <T> void delete(Class<T> entityClass,Object[] entityids) {
		for(Object id :entityids)
			sessionFactory.getCurrentSession().delete
		    (sessionFactory.getCurrentSession().load(entityClass, (Integer) id));
		
	}
	/**
	 *统一删除的方法3，通过传入对象删除某一对象的数据表中的某个实例
	 * @param entity 需要删除的对象实例
	 * @author hebi
	 */
	public void delete(Object entity) {
		
		sessionFactory.getCurrentSession().delete(entity);
	}
	
	/**
	 *通过id查找某一对象的一个实例
	 * @param entityClass 需要操作的对象类，用于匹配泛型操作 
	 * @param entityid 需要删除的对象的实例的id的 
	 * @author hebi
	 * @return 返回该对象的泛型
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> T findById(Class<T> entityClass, Object entityid) {
		
		return (T) sessionFactory.getCurrentSession().get(entityClass, (Long) entityid);
	}

	/**
	 *存储某一对象的一个实例
	  @param entity 需要操作的对象实例
	 * @author hebi
	 */
	public void save(Object entity) {
		sessionFactory.getCurrentSession().persist(entity);
		
	}
	/**
	 *存储或更新某一对象的一个实例
	  @param entity 需要操作的对象实例
	 * @author hebi
	 */
	public void saveOrUpdate(Object entity) {
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
		
	}
	
	/**
	 * 更新某一对象的一个实例
	  @param entity 需要操作的对象实例
	 * @author hebi
	 */

	public void update(Object entity) {
		
		sessionFactory.getCurrentSession().merge(entity);
		
	}
	
     public void flush() {
		
		sessionFactory.getCurrentSession().flush();
		
	}
//	/**
//	 * 通过hql查询某一对象的多个实例	
//	 * @param session 操作的session 
//	 * @param entityClass 需要查询的对象的类，用于匹配泛型操作
//	 * @param wherehql hql语句
//	 * @param firstindex 用于分页传入的index 如果为-1则无视分页
//	 * @param maxresult 用于分页的每页的结果个数 如果为-1则无视分页
//	 * @param queryParams 查询的参数数组
//	 * @param orderby 用于查询的orderby
//	 * @param entity 需要操作的对象实例
//	 * @return 返回所有符合条件的查询结果列表{@link}QueryResult，包括结果集中结果的个数
//	 * @author hebi
//	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public PageDTO getScrollData(String hqlfrom , String hql,Class<? extends Knowledge> entityClass, PageDTO page,Object[] objects) {
		if(null==hqlfrom)
		hqlfrom=" ";
		//System.out.println("hqlfrom="+hqlfrom);
		System.out.println(" select distinct o from "+entityClass.getName() +" o "+hqlfrom+" "+((hql==null||hql.equals(""))? "":"where "+hql)+(page.getOrderBy()==null ? "": page.getOrderBy()));
		Query query =sessionFactory.getCurrentSession().createQuery(" select distinct o from "+entityClass.getName() +" o "+hqlfrom+" "+((hql==null||hql.equals(""))? "":"where "+hql)+(page.getOrderBy()==null ? "": page.getOrderBy()));
		setQueryParams(query,objects);
		int firstindex=page.getFirstindex();
		int maxresult= Integer.parseInt((String.valueOf(page.getPagesize())));
		if(firstindex!=-1 && maxresult!=-1){
		query.setFirstResult(firstindex);
		query.setMaxResults(maxresult);
		}
		page.setData(query.list());
	
		query =sessionFactory.getCurrentSession().createQuery("select count(o) from "+entityClass.getName() +" o "+hqlfrom+" "+((hql==null||hql.equals(""))? "":"where "+hql));
		
		setQueryParams(query,objects);
		page.setTotal( Integer.parseInt(query.uniqueResult().toString()));

		
		
		return page;
	}
//	@SuppressWarnings("unchecked")
//	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
//	public <T> QueryResult<T> getScrollData(String wherehql,Class<T> entityClass,
//			int firstindex, int maxresult,Object[] queryParams,LinkedHashMap<String,String> orderby) {
//		
//		QueryResult qr = new QueryResult<T>();
//		//String entityname = getEntityName(entityClass);        
//	   //	System.out.println("select o from "+entityClass.getName() +" o "+(wherehql==null? "":"where "+wherehql)+buildOrderby(orderby));		
//		Query query =sessionFactory.getCurrentSession().createQuery("select o from "+entityClass.getName() +" o "+(wherehql==null? "":"where "+wherehql)+buildOrderby(orderby));
//		setQueryParams(query,queryParams);
//		
//		if(firstindex!=-1 && maxresult!=-1){
//		query.setFirstResult(firstindex);
//		query.setMaxResults(maxresult);
//		}
//		
//		qr.setResultlist(query.list());
//		query =sessionFactory.getCurrentSession().createQuery("select count(o) from "+entityClass.getName() +" o "+(wherehql==null? "":"where "+wherehql));
//		
//		setQueryParams(query,queryParams);
//		qr.setTotalrecord(Integer.parseInt(query.uniqueResult().toString()));
//		
//		
//		return qr;
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
//	public  QueryResult createQuery(String hql,int firstindex,int maxresult,Object[] queryParams,String orderby) {
//		
//		QueryResult qr = new QueryResult();
//		       	
//		orderby = orderby==null? "":orderby;
//		Query query =sessionFactory.getCurrentSession().createQuery(hql+orderby);
//		setQueryParams(query,queryParams);
//		
//		if(firstindex!=-1 && maxresult!=-1){
//		query.setFirstResult(firstindex);
//		query.setMaxResults(maxresult);
//		}
//		
//		qr.setResultlist(query.list());
//		query =sessionFactory.getCurrentSession().createQuery("select count(*) "+hql);
//		
//		setQueryParams(query,queryParams);
//		qr.setTotalrecord(Integer.parseInt(query.uniqueResult().toString()));
//		
//		
//		return qr;
//	}	
//	
//	@SuppressWarnings("unchecked")
//	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
//	public  QueryResult createQuery(HttpSession session,String hql,int firstindex,int maxresult,Object[] queryParams,String orderby) {
//		
//		QueryResult qr = new QueryResult();
//		       
//	//	System.out.println(hql);
//		
//		orderby = orderby==null? "":orderby;
//		Query query =sessionFactory.getCurrentSession().createQuery(hql+orderby);
//		setQueryParams(query,queryParams);
//		
//		if(firstindex!=-1 && maxresult!=-1){
//		query.setFirstResult(firstindex);
//		query.setMaxResults(maxresult);
//		}
//		
//		qr.setResultlist(query.list());
//		query =sessionFactory.getCurrentSession().createQuery("select count(*) "+hql);
//		
//		setQueryParams(query,queryParams);
//		qr.setTotalrecord(Integer.parseInt(query.uniqueResult().toString()));
//		
//		
//		return qr;
//	}
	
	protected void setQueryParams(Query query, Object... queryParams){
	
			if (queryParams != null) {
				for (int i = 0; i < queryParams.length; i++) {
					query.setParameter(i, queryParams[i]);
				}
			}
		}		

	
	//order by o.key desc,o.key2 asc
	protected String buildOrderby(LinkedHashMap<String,String> orderby){
		StringBuffer orderbyq1= new StringBuffer("");
		if(orderby!=null&&orderby.size()>0){
			orderbyq1.append("order by ");
			for (String key:orderby.keySet()){
				orderbyq1.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
				
			}
			orderbyq1.deleteCharAt(orderbyq1.length()-1);
		}
		return orderbyq1.toString();
		
	}
	
	/**
	protected <T> String getEntityName(Class<T> entityClass){
		String entityname=entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if(entity.name()!=null&& !"".equals(entity.name())){
			entityname=entity.name();
		}
		
		return entityname;
	}
	
	**/
    
	

//	@SuppressWarnings("unchecked")
//	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
//	public  QueryResult createQueryz(String hql,int firstindex,int maxresult,Object[] queryParams,String orderby,String title) {
//		
//		QueryResult qr = new QueryResult();
//		       
////		System.out.println(hql);
//		
//		orderby = orderby==null? "":orderby;
//		Query query =sessionFactory.getCurrentSession().createQuery("select "+ title +" "+hql+orderby);
//		setQueryParams(query,queryParams);
//		
//		if(firstindex!=-1 && maxresult!=-1){
//		query.setFirstResult(firstindex);
//		query.setMaxResults(maxresult);
//		}
//		
//		qr.setResultlist(query.list());
//		if(null==orderby||orderby.indexOf("count")==-1){//按个数排序时 如（order by count(o)),多个count的查询语句会出现问题
//	//		System.out.println("sql===============select count("+title+") "+hql);
//		query =sessionFactory.getCurrentSession().createQuery("select count("+title+") "+hql);
//		
//		setQueryParams(query,queryParams);
//		qr.setTotalrecord(Integer.parseInt(query.uniqueResult().toString()));
//	   }
//		else{
//	//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
//		}
//		
//		return qr;
//	}	
	
//	@SuppressWarnings("unchecked")
//	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
//	public  QueryResult createQueryCount(String hql,int firstindex,int maxresult,Object[] queryParams,String orderby,String title,String counttile) {
//		
//		QueryResult qr = new QueryResult();
//		       
//	//	System.out.println(hql);
//		
//		orderby = orderby==null? "":orderby;
//		Query query =sessionFactory.getCurrentSession().createQuery("select "+ title +" "+hql+orderby);
//		setQueryParams(query,queryParams);
//		
//		if(firstindex!=-1 && maxresult!=-1){
//		query.setFirstResult(firstindex);
//		query.setMaxResults(maxresult);
//		}
////		 List<Knowledge> lt = new ArrayList<Knowledge>();
////		List querylist=query.list();
////		for (int i=0;i<querylist.size();i++) {
////			
////			  if(querylist.get(i).getClass().toString().equals("class org.cims201.metaknowledge.Metaknowledge"))
////			  {
////			      lt.add((Knowledge)querylist.get(i));
////			  }
////			  else
////			  {     
////				 Object[] obj = (Object[]) querylist.get(i);
////				 Knowledge knowledge = (Knowledge) obj[0];
////				 lt.add(knowledge);		
////			  }
////	 
////		}
//		
////		qr.setResultlist(lt);
//		
//		query =sessionFactory.getCurrentSession().createQuery("select count("+counttile+") "+hql);		
//		setQueryParams(query,queryParams);
//		
//		qr.setTotalrecord(Integer.parseInt(query.uniqueResult().toString()));
//		
//		return qr;
//	}	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
		//log.debug("finding "+entityClass.getName()+" instance with property: " + propertyName+ ", value: " + value);
		try {
			String queryString = "from "+entityClass.getName()+" o where o."	+ propertyName + "= ?";
			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
			
		} catch (RuntimeException re) {
			//log.error("find by property name failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
		//log.debug("finding "+entityClass.getName()+" instance with property: " + propertyName+ ", value: " + value);
		try {
			String queryString = "from "+entityClass.getName()+" o where o."	+ propertyName + "= ?";
			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return (T) queryObject.uniqueResult();
			
		} catch (RuntimeException re) {
			//log.error("find by property name failed", re);
			throw re;
		}
	}
	
	

	

//	public  QueryResult createQuery2(String hql,int firstindex,int maxresult,Object[] queryParams,String orderby) {
//		
//		QueryResult qr = new QueryResult();
//		       
//	//	System.out.println(hql);
//		
//		orderby = orderby==null? "":orderby;
//		Query query =sessionFactory.getCurrentSession().createQuery(hql+orderby);
//		setQueryParams(query,queryParams);
//		
//		if(firstindex!=-1 && maxresult!=-1){
//		query.setFirstResult(firstindex);
//		query.setMaxResults(maxresult);
//		}
//		
//		qr.setResultlist(query.list());
//		//query =sessionFactory.getCurrentSession().createQuery("select count(*) "+hql);
//		
//		//setQueryParams(query,queryParams);
//	//	qr.setTotalrecord(Integer.parseInt(query.uniqueResult().toString()));
//		
//		
//		return qr;
//	}	
//	@SuppressWarnings("unchecked")
//	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
//	public User getUser(){
//		
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();		
//		return findUniqueByProperty(User.class, "email", username);
//		
//		
//	}
	
//	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
//	public Query createQueryHQL(String hql,Map<String, ?> queryParams){
//		
//		Query query =sessionFactory.getCurrentSession().createQuery(hql);
//		setQueryParams(query,queryParams);
//		
//		
//		return query;
//		
//		
//		
//	}
	

}
