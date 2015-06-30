package edu.zju.cims201.spider.grabnews.uggdqy;
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

	  /**
	   * Create a new SQL workload store and
	   * connect to a database.
	   *
	   * @param driver The JDBC driver to use.
	   * @param source The driver source name.
	   * @exception java.sql.SQLException
	   * @exception java.lang.ClassNotFoundException
	   */
    public SpiderSQLWorkload()
	  {
		  context = new FileSystemXmlApplicationContext(new String[]{Constants.APPLICATIONCONTEXT}); 
		  workloadService=(WorkloadServiceImpl) context.getBean("workloadServiceImpl"); 
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
		Workload wl=workloadService.prepAssign("www.uggd.com"); 
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
	  }

	  protected void setStatus(String url,char status,String parentUrl)
	  {
		if(workloadService.prepSetStatus1(url)==0)  {
			workloadService.prepSetStatus2(url, status, parentUrl);
		}else{
			workloadService.prepSetStatus3(status, url);
		}		  

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
		workloadService.prepClear("www.uggd.com"); 
	  }
}

