package edu.zju.cims201.spider.grabnews.chinapj;
import java.sql.Connection;
import java.sql.PreparedStatement;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import edu.zju.cims201.GOF.service.knowledge.workload.WorkloadServiceImpl;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.spider.heaton.bot.IWorkloadStorable;
import edu.zju.cims201.GOF.hibernate.pojo.Workload;

/**
 * This class uses a JDBC database
 * to store a spider workload.
 *
 * @author Jeff Heaton
 * @version 1.0
 */
public class SpiderSQLWorkload implements IWorkloadStorable {
	private Log log = LogFactory.getLog(this.getClass());
	private ApplicationContext context = null; 
	private WorkloadServiceImpl workloadService;
	Connection _connection;
	  PreparedStatement _prepClear;
	  PreparedStatement _prepInit;
    PreparedStatement _prepAssign;
    PreparedStatement _prepGetStatus;
    PreparedStatement _prepSetStatus1;
    PreparedStatement _prepSetStatus2;
    PreparedStatement _prepSetStatus3;

   public SpiderSQLWorkload()
	  {
		  context = new FileSystemXmlApplicationContext(new String[]{Constants.APPLICATIONCONTEXT}); 
		  workloadService=(WorkloadServiceImpl) context.getBean("workloadServiceImpl"); 
//		 DBConnection dbmanager = new DBConnection();
//	    _connection = dbmanager.getDBConnection();
//	    _prepInit = _connection.prepareStatement("insert into workload(url) select url from categoryurl c where c.url like '%chinapj.com%'");
//	    _prepClear = _connection.prepareStatement("DELETE FROM workload WHERE url LIKE 'url'");
//	    _prepAssign = _connection.prepareStatement("SELECT URL,parenturl FROM workload WHERE Status = 'W' and parenturl like '%chinapj.com%';");
//	    _prepGetStatus = _connection.prepareStatement("SELECT Status FROM workload WHERE URL = ?;");
//	    _prepSetStatus1 = _connection.prepareStatement("SELECT count(*) as qty FROM workload WHERE URL = ?;");
//	    _prepSetStatus2 = _connection.prepareStatement("INSERT INTO workload(URL,Status,parentUrl) VALUES (?,?,?);");
//	    _prepSetStatus3 = _connection.prepareStatement("UPDATE workload SET Status = ? WHERE URL = ?;");
	  }
	  /**
	   * Call this method to request a URL
	   * to process. This method will return
	   * a WAITING URL and mark it as RUNNING.
	   *
	   * @return The URL that was assigned.
	   */
	  synchronized public String[] assignWorkload()
	  {
		Workload wl=workloadService.prepAssign("chinapj.com"); 
		if(wl==null){
			return null;
		}
		else{
			String[] url = new String[2];
			url[0]= wl.getUrl();
			url[1]= wl.getParenturl();
			setStatus(url[0],RUNNING);
			return url;
		}
//	    ResultSet rs = null;
//
//	    try {
//	      rs = _prepAssign.executeQuery();
//
//	      if ( !rs.next() )
//	        return null;
//	      String[] url = new String[2];
//	      url[0]= rs.getString("URL");
//	      url[1]= rs.getString("parenturl");
//	      setStatus(url[0],RUNNING);
//	      return url;
//	    } catch ( SQLException e ) {
//	        log.error("SQL Error: ",e );
//	    	//Log.logException("SQL Error: ",e );
//	    } finally {
//	      try {
//	        if ( rs!=null )
//	          rs.close();
//	      } catch ( Exception e ) {
//	      }
//	    }
//	    return null;
	  }
	  synchronized public void addWorkload(String url,String parentUrl)
	  {
	    if ( getURLStatus(url)!=UNKNOWN )
	      return;
	    setStatus(url,WAITING,parentUrl);

	  }
	  /**
	   * Add a new URL to the workload, and
	   * assign it a status of WAITING.
	   *
	   * @param url The URL to be added.
	   */
	  synchronized public void addWorkload(String url)
	  {
	    if ( getURLStatus(url)!=UNKNOWN )
	      return;
	    setStatus(url,WAITING);

	  }

	  /**
	   * Called to mark this URL as either
	   * COMPLETE or ERROR.
	   *
	   * @param url The URL to complete.
	   * @param error true - assign this workload a status of ERROR.
	   * false - assign this workload a status of COMPLETE.
	   */
	  synchronized public void completeWorkload(String url,boolean error)
	  {
//	    System.out.println("url="+url);
//	    System.out.println("error="+error);
	    
		  if ( error )
	      setStatus(url,ERROR);
	    else
	      setStatus(url,COMPLETE);

	  }




	  /**
	   * This is an internal method used to set the status
	   * of a given URL. This method will create a record
	   * for the URL of one does not currently exist.
	   *
	   * @param url The URL to set the status for.
	   * @param status What status to set.
	   */
	  protected void setStatus(String url,char status)
	  {
			if(workloadService.prepSetStatus1(url)==0)  {
				workloadService.prepSetStatus2(url, status, null);
			}else{
				workloadService.prepSetStatus3(status, url);
			}	  
//	    ResultSet rs = null;
//
//	    try {
//	      // first see if one exists
//	      _prepSetStatus1.setString(1,url);
//	      rs = _prepSetStatus1.executeQuery();
//	      rs.next();
//	      int count = rs.getInt("qty");
//
//	      if ( count<1 ) {// Create one
//	        _prepSetStatus2.setString(1,url);
//	        _prepSetStatus2.setString(2,(new Character(status)).toString());
//	        _prepSetStatus2.executeUpdate();
//	      } else {// Update it
//	        _prepSetStatus3.setString(1,(new Character(status)).toString());
//	        _prepSetStatus3.setString(2,url);
//	        _prepSetStatus3.executeUpdate();
//	      }
//	    } catch ( SQLException e ) {
//	        log.error("SQL Error: ",e );
//	    	//Log.logException("SQL Error: ",e );
//	    } finally {
//	      try {
//	        if ( rs!=null )
//	          rs.close();
//	      } catch ( Exception e ) {
//	      }
//	    }
	  }

	  protected void setStatus(String url,char status,String parentUrl)
	  {
		if(workloadService.prepSetStatus1(url)==0)  {
			workloadService.prepSetStatus2(url, status, parentUrl);
		}else{
			workloadService.prepSetStatus3(status, url);
		}		  
//	    ResultSet rs = null;
//
//	    try {
//	      // first see if one exists
//	      _prepSetStatus1.setString(1,url);
//	      rs = _prepSetStatus1.executeQuery();
//	      rs.next();
//	      int count = rs.getInt("qty");
//
//	      if ( count<1 ) {// Create one
//	        _prepSetStatus2.setString(1,url);
//	        _prepSetStatus2.setString(2,(new Character(status)).toString());
//	        _prepSetStatus2.setString(3,parentUrl);
//	        _prepSetStatus2.executeUpdate();
//	      } else {// Update it
//	        _prepSetStatus3.setString(1,(new Character(status)).toString());
//	        _prepSetStatus3.setString(2,url);
//	        _prepSetStatus3.executeUpdate();
//	      }
//	    } catch ( SQLException e ) {
//	    	log.error("SQL Error: ",e );
//	    	//Log.logException("SQL Error: ",e );
//	    } finally {
//	      try {
//	        if ( rs!=null )
//	          rs.close();
//	      } catch ( Exception e ) {
//	      }
//	    }
	  }
	  /**
	   * Get the status of a URL.
	   *
	   * @param url Returns either RUNNING, ERROR
	   * WAITING, or COMPLETE. If the URL
	   * does not exist in the database,
	   * the value of UNKNOWN is returned.
	   * @return Returns either RUNNING,ERROR,
	   * WAITING,COMPLETE or UNKNOWN.
	   */
	  synchronized public char getURLStatus(String url)
	  {
		return workloadService.prepGetStatus(url);
	  }

	  /**
	   * Clear the contents of the workload store.
	   */
	  synchronized public void clear()
	  {
		workloadService.prepClear("chinapj.com");  
		  
//	    try {
//	        
//	    	_prepClear.executeUpdate();
//	    	_prepInit.executeUpdate();
//	    } catch ( SQLException e ) {
//	    	log.error("SQL Error: ",e );
//	    	//Log.logException("SQL Error: ",e );
//	    }
	  }
}

