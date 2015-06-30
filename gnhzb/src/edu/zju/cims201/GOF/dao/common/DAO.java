package edu.zju.cims201.GOF.dao.common;

import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
//import org.cims201.hibernate.User;
import org.hibernate.Query;

//import edu.zju.cims201.GOF.dao.common.QueryResult;


/**
 * 统一的DAO接口
 * @author jeffdong+hebi
 *
 */
public interface DAO {
	/**
	 *存储某一对象的一个实例
	  @param entity 需要操作的对象实例
	
	 */
	public void save(Object entity);
	
	/**
	 *存储或更新某一对象的一个实例
	  @param entity 需要操作的对象实例
	 * @author hebi
	 */
	public void saveOrUpdate(Object entity);
	

	/**
	 * 更新某一对象的一个实例
	  @param entity 需要操作的对象实例
	 * @author hebi
	 */
	public void update(Object entity);

	/**
	 * 根据主键id删除实体
	 * @param <T>
	 * @param entityClass 实体类型
	 * @param entityid  主键id
	 */
	public <T> void delete(Class<T> entityClass,Object entityid);
	
	/**
	 * 根据主键id数组删除多个实体
	 * @param <T>
	 * @param entityClass 实体类型
	 * @param entityids 主键id数组
	 */
	public <T> void delete(Class<T> entityClass,Object[] entityids);
	
	/**
	 * 通过传入对象删除某一对象的数据表中的某个实例
	 * @param entity 需要删除的对象实例
	 * @author hebi
	 */
	public void delete(Object entity) ;
 
	/**
	 * 根据主键id查找实体 
	 * @param <T>
	 * @param entityClass
	 * @param entityid
	 * @return 返回该实体类实例
	 */
	public <T> T findById(Class<T> entityClass,Object entityid);
	
    /**
     * 获取分页数据
     * @param <T>
     * @param session 
     * @param wherehql 条件查询语句 e.g. "o.id < ? "
     * @param entityClass  要查询的实体类
     * @param firstindex 从第firstindex条数据开始查询,不采用分页时填 -1
     * @param maxresult 每页显示数据,不采用分页时填 -1
     * @param queryParams 查询参数 
     * @param orderby 排序 支持多重排序
     * @return 返回{@link}QueryResult包装的该实体类实例列表
     */     
//	public <T> QueryResult<T> getScrollData(HttpSession session, String wherehql,Class<T> entityClass,int firstindex, int maxresult,Object[] queryParams,LinkedHashMap<String,String> orderby);
    /**
     * 获取分页数据（无Session版）
     * @param <T>
     * @param wherehql 条件查询语句 e.g. "o.id < ? "
     * @param entityClass  要查询的实体类
     * @param firstindex 从第firstindex条数据开始查询,不采用分页时填 -1
     * @param maxresult 每页显示数据,不采用分页时填 -1
     * @param queryParams 查询参数 
     * @param orderby 排序 支持多重排序
     * @return
     */     
//	public <T> QueryResult<T> getScrollData(String wherehql,Class<T> entityClass,int firstindex, int maxresult,Object[] queryParams,LinkedHashMap<String,String> orderby);
	
	//	public QueryResult createQueryz(String hql,int firstindex,int maxresult,Object[] queryParams,String orderby,String title); 
	/**
	 * 
	 * @param session 
	 * @param hql 查询语句 e.g. "from Metaknowledge o where o.id < 5 "
     * @param firstindex 从第firstindex条数据开始查询,不采用分页时填 -1
     * @param maxresult 每页显示数据,不采用分页时填 -1
     * @param queryParams 查询参数 
	 * @param orderby 排序
	 * @return
	 */
//	public QueryResult createQuery(HttpSession session,String hql,int firstindex,int maxresult,Object[] queryParams,String orderby);
	
	/**
	 * 通过类的属性查找
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return 返回list
	 */
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) ;
	
	/**
	 * 通过类的属性查找
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return 返回单一对象
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) ;
	
	
	

	/**
	 * createQuery（无Session版）
	 * @param hql 查询语句 e.g. "from Metaknowledge o where o.id < 5 "
     * @param firstindex 从第firstindex条数据开始查询,不采用分页时填 -1
     * @param maxresult 每页显示数据,不采用分页时填 -1
     * @param queryParams 查询参数 
	 * @param orderby 排序
	 * @return
	 */
//	public QueryResult createQuery(String hql,int firstindex,int maxresult,Object[] queryParams,String orderby);
	
//	public QueryResult createQuery2(String hql,int firstindex,int maxresult,Object[] queryParams,String orderby);
//	/**
//	 * 得到当前Session中的用户对象
//	 * @return
//	 */
//	public User getUser();
	
	/**
	 * 直接查询hql 语句，返回Query对象
	 * @param hql
	 * @return
	 */
//	public Query createQueryHQL(String hql,Object[] queryParams);
	
	
	/**
	 * 支持独立设置counttile 
	 * @param hql
	 * @param firstindex
	 * @param maxresult
	 * @param queryParams
	 * @param orderby
	 * @param title
	 * @param counttile
	 * @return
	 * added by jeffdong 2009年12月10日19:55:00
	 */
//	public  QueryResult createQueryCount(String hql,int firstindex,int maxresult,Object[] queryParams,String orderby,String title,String counttile) ;
	public void flush();
}