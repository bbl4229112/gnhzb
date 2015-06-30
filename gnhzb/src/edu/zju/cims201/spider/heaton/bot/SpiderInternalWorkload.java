package edu.zju.cims201.spider.heaton.bot;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is used to maintain an internal,
 * memory based workload store for a spider. This
 * workload store will be used by default, if no
 * other is specified.
 *
 * Copyright 2001 by Jeff Heaton
 *
 * @author Jeff Heaton
 * @version 1.0
 */
public class SpiderInternalWorkload implements IWorkloadStorable {
	private Log log = LogFactory.getLog(this.getClass());
  /**
   * A list of complete workload items.
   */
  Hashtable _complete = new Hashtable();

  /**
   * A list of waiting workload items.
   */
  Vector _waiting = new Vector();

  /**
   * A list of running workload items.
   */
  Vector _running = new Vector();

  /**
   * Call this method to request a URL
   * to process. This method will return
   * a WAITING URL and mark it as RUNNING.
   *
   * @return The URL that was assigned.
   */
  synchronized public String[] assignWorkload()
  {
    if ( _waiting.size()<1 )
      return null;

    String[] w=new String[0];
    w[0]=(String)_waiting.firstElement();
    if ( w!=null ) {
      _waiting.remove(w);
      _running.addElement(w);
    }
    log.trace("Spider workload assigned:" + w);
    //Log.log(Log.LOG_LEVEL_TRACE,"Spider workload assigned:" + w);
    return w;
  }

  /**
   * Add a new URL to the workload, and
   * assign it a status of WAITING.
   *
   * @param url The URL to be added.
   */
  synchronized public void addWorkload(String url)
  {
    if ( getURLStatus(url) != IWorkloadStorable.UNKNOWN )
      return;
    _waiting.addElement(url);
    log.trace("Spider workload added:" + url);
    //Log.log(Log.LOG_LEVEL_TRACE,"Spider workload added:" + url);
  }
  synchronized public void addWorkload(String url,String parentUrl)
  {
    if ( getURLStatus(url) != IWorkloadStorable.UNKNOWN )
      return;
    _waiting.addElement(url);
    log.trace("Spider workload added:" + url);
    //Log.log(Log.LOG_LEVEL_TRACE,"Spider workload added:" + url);
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
    if ( _running.size()>0 ) {
      for ( Enumeration e = _running.elements() ; e.hasMoreElements() ; ) {
        String w = (String)e.nextElement();
        if ( w.equals(url) ) {
          _running.remove(w);
          if ( error ) {
        	  log.trace("Spider workload ended in error:" + url);
        	  //Log.log(Log.LOG_LEVEL_TRACE,"Spider workload ended in error:" + url);
            _complete.put(w,"e");
          } else {
        	  log.trace("Spider workload complete:" + url);
            //Log.log(Log.LOG_LEVEL_TRACE,"Spider workload complete:" + url);
            _complete.put(w,"c");
          }
          return;
        }
      }
    }
    log.error("Spider workload LOST:" + url);
    //Log.log(Log.LOG_LEVEL_ERROR,"Spider workload LOST:" + url);

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
    if ( _complete.get(url)!=null )
      return COMPLETE;

    if ( _waiting.size()>0 ) {
      for ( Enumeration e = _waiting.elements() ; e.hasMoreElements() ; ) {
        String w = (String)e.nextElement();
        if ( w.equals(url) )
          return WAITING;
      }
    }

    if ( _running.size()>0 ) {
      for ( Enumeration e = _running.elements() ; e.hasMoreElements() ; ) {
        String w = (String)e.nextElement();
        if ( w.equals(url) )
          return RUNNING;
      }
    }

    return UNKNOWN;
  }

  /**
   * Clear the contents of the workload store.
   */
  synchronized public void clear()
  {
    _waiting.clear();
    _complete.clear();
    _running.clear();
  }
}
