package edu.zju.cims201.GOF.service.statistic;

import org.springframework.stereotype.Service;
import  edu.zju.cims201.GOF.dao.common.QueryResult;


import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;

@Service
public interface StatisticService {
	public Object[] getHqltime(String model);
	public QueryResult<SystemUser> createQuery(String hql,String hql_pre, int firstindex, int maxresult,Object[] queryParams, Long userid) ;
		
}
