package edu.zju.cims201.GOF.service.statistic;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.dao.common.CommonDao;
import  edu.zju.cims201.GOF.dao.common.QueryResult;
import edu.zju.cims201.GOF.dao.user.SystemUserDao;
@Service
public class StatisticServiceImpl implements StatisticService {
	@Resource(name="systemUserDao") 
	SystemUserDao systemUserDao;

	
	public QueryResult<SystemUser> createQuery(String hql, String hql_pre,int firstindex, int maxresult,Object[] queryParams, Long userid) 
	{
		QueryResult<SystemUser> qr = new QueryResult<SystemUser>();
		
		Query query =systemUserDao.createQuery("select "+hql_pre+hql);
		List list_qr = query.list();
		List list_user = new ArrayList();;
		//当前用户排名
		String currentRank ="";
		for(int i= 0;i<list_qr.size();i++)
		   {  Object[] obj = (Object[]) list_qr.get(i);
		      SystemUser user = (SystemUser) obj[0];
		      Integer score = (Integer) obj[1];
		      user.setScoreTemp(score);
		      list_user.add(user);
		      
		      System.out.print(user.getScoreTemp()+"_");
			if(user.getId()==userid){
				currentRank =(i+1)+"/"+list_qr.size();
			}
		}
       qr.setCurrentRank(currentRank);
       qr.setTotalrecord(list_qr.size());
       
		

		Integer lastindex=100;
           if(list_user.size()>firstindex+maxresult)
        	   lastindex=firstindex+maxresult;
           else lastindex=list_user.size();
           
	     qr.setResultlist(list_user.subList(firstindex, lastindex));

			return qr;
		}
	
	/**
	 * 拼限制时间的hql串
	 * @deprecated by jeffdong 2009年12月26日22:54:07
	 * @param model
	 * @return
	 */
	public Object[] getHqltime(String model){
		Object[] hqltparam = new Object[]{};
		String hqltime=null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null != model && model.equals("week")) {
			Calendar now = Calendar.getInstance();
			int today = now.get(Calendar.DAY_OF_WEEK)-1;
			
			String dateString1 = formatter1.format(now.getTime());	
			Date date2 = now.getTime();
			now.add(Calendar.DATE,-today);
			Date date=now.getTime();
		    String dateString = formatter.format(date);	
    
		    hqltime =  " and b.actiontime <= '"+dateString1+"' and b.actiontime>='"+dateString+"' ";
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
		    hqltparam = new Object[]{ date2, date};
		    
		
		}
		if (null != model && model.equals("lasttwoweek")) {
			Calendar now = Calendar.getInstance();
			int today = now.get(Calendar.DAY_OF_WEEK)-1;
			
			
			String dateString1 = formatter.format(now.getTime());	
			Date date2 = now.getTime();
			now.add(Calendar.DATE,-(today + 7));
			Date date=now.getTime();
		    String dateString = formatter.format(date);
		    
		    hqltime =  " and b.actiontime <= '"+dateString1+"' and b.actiontime>='"+dateString+"' ";
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);

		    hqltparam = new Object[]{ date2, date};
		  			
		}
		if (null != model && model.equals("lastweek")) {
			Calendar now = Calendar.getInstance();
			int today = now.get(Calendar.DAY_OF_WEEK)-1;
	//		System.out.println("today::"+today);
			now.add(Calendar.DATE,-(today ));
			String dateString1 = formatter.format(now.getTime());	
			Date date2 = now.getTime();
			now = Calendar.getInstance();
			now.add(Calendar.DATE,-(today + 7));
			Date date=now.getTime();

		    String dateString = formatter.format(date);
		    
		    
		    hqltime =  " and b.actiontime <= '"+dateString1+"' and b.actiontime>='"+dateString+"' ";
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			date2.setHours(0);
			date2.setMinutes(0);
			date2.setSeconds(0);
		    hqltparam = new Object[]{ date2, date};
			
		} 
		if (null != model && model.equals("month")) {
			Calendar now = Calendar.getInstance();
			int today = now.get(Calendar.DAY_OF_MONTH)-1;
			
			
			String dateString1 = formatter.format(now.getTime());	
			Date date2 = now.getTime();
			now.add(Calendar.DATE,-today);
			Date date=now.getTime();
		    String dateString = formatter.format(date);
		    
		    hqltime =  " and b.actiontime <= '"+dateString1+"' and b.actiontime>='"+dateString+"' ";
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
	
		    hqltparam = new Object[]{ date2, date};
		    
			
		}
		if (null != model && model.equals("lastmonth")) {
			Calendar now = Calendar.getInstance();
			int today = now.get(Calendar.DAY_OF_MONTH)-1;
			
			
			//String dateString1 = formatter.format(now.getTime());		
			now.add(Calendar.DATE,-today);
			Date date=now.getTime();
		    String dateString1 = formatter.format(date);
		    
		    //now.add(Calendar.DATE,1);
		    now.add(Calendar.MONTH, -1);
		    Date date2 = now.getTime();
		    String dateString= formatter.format(now.getTime());	
		    
		    hqltime =  " and b.actiontime <= '"+dateString1+"' and b.actiontime>='"+dateString+"' ";
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			date2.setHours(0);
			date2.setMinutes(0);
			date2.setSeconds(0);
		    hqltparam = new Object[]{ date, date2};
		    
			
		}

		if (null != model && model.equals("year")) {
			Calendar now = Calendar.getInstance();
			int today = now.get(Calendar.DAY_OF_YEAR)-1;
			
			String dateString1 = formatter.format(now.getTime());		
			Date date2 = now.getTime();
			now.add(Calendar.DATE,-today);
			Date date=now.getTime();
		    String dateString = formatter.format(date);
		    
		    hqltime =  " and b.actiontime <= '"+dateString1+"' and b.actiontime>='"+dateString+"' ";
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);

		    hqltparam = new Object[]{ date2, date};
		    
			
		}
		if (null != model && model.equals("lastyear")) {
			Calendar now = Calendar.getInstance();
			int today = now.get(Calendar.DAY_OF_YEAR)-1;
			
			//String dateString1 = formatter.format(now.getTime());		
			now.add(Calendar.DATE,-today);
			Date date=now.getTime();
		    String dateString1 = formatter.format(date);
		  
		    now.add(Calendar.YEAR,-1);  
		    Date date2 = now.getTime();
		    String dateString= formatter.format(now.getTime());	
		    
		    hqltime =  " and b.actiontime <= '"+dateString1+"' and b.actiontime>='"+dateString+"' ";
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			date2.setHours(0);
			date2.setMinutes(0);
			date2.setSeconds(0);
		    hqltparam = new Object[]{ date, date2};
		    
			
		}
		if (null != model && model.equals("all")) {
			
			Calendar now = Calendar.getInstance();
			
			
			String dateString1 = formatter.format(now.getTime());	
			Date date2 = now.getTime();
			
			now.add(Calendar.YEAR,-40);
			Date date=now.getTime();
		    String dateString = formatter.format(date);
		    
		    hqltime =  " and b.actiontime <= '"+dateString1+"' and b.actiontime>='"+dateString+"' ";
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);

		    hqltparam = new Object[]{ date2, date};
		    
					
		}

		return new Object[]{ hqltparam[1], hqltparam[0]};
		
	}
//	}

}
