package edu.zju.cims201.spider.heaton.bot;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.zju.cims201.spider.commontool.NetHelper;
import edu.zju.cims201.spider.commontool.UrlRule;

/**
 * The SpiderWorker class performs the actual work of
 * spidering pages.  It is implemented as a thread
 * that is created by the spider class.
 *
 * Copyright 2001 by Jeff Heaton
 *
 * @author Jeff Heaton
 * @version 1.0
 */


public class SpiderWorker extends Thread {
  private Log log = LogFactory.getLog(this.getClass());
  protected UrlRule urlrule;
  /**
   * The URL that this spider worker
   * should be downloading.
   */
  //protected String _target;
  protected String [] _target;
  /**
   * The owner of this spider worker class,
   * should always be a Spider object.
   * This is the class that this spider
   * worker will send its data to.
   */
  protected Spider _owner;

  /**
   * Indicates if the spider is busy or not.
   * true = busy
   * false = idle
   */
  protected boolean _busy;

  /**
   * A descendant of the HTTP object that
   * this class should be using for HTTP
   * communication. This is usually the
   * HTTPSocket class.
   */
  protected HTTP _http;

  /**
   * Constructs a spider worker object.
   *
   * @param owner The owner of this object, usually
   * a Spider object.
   * @param http
   */
  public SpiderWorker(Spider owner,HTTP http)
  {
    _http = http;
    _owner = owner;
    urlrule = new UrlRule();
  }

  /**
   * Returns true of false to indicate if
   * the spider is busy or idle.
   *
   * @return true = busy
   * false = idle
   */
  public boolean isBusy()
  {
    return _busy;
  }

  /**
   * The run method causes this thread to go idle
   * and wait for a workload. Once a workload is
   * received, the processWorkload method is called
   * to handle the workload.
   */
  public void run()
  {
    for ( ;; ) {
      _target = _owner.getWorkload();
      if ( _target==null )
        return;
      _owner.getSpiderDone().workerBegin();
      processWorkload();
      _owner.getSpiderDone().workerEnd();
    }
  }
  
  protected void processWorkload()
  {

	  try {
		  _busy =  true;
		  log.info("Spidering " + _target);
		  
          //多媒体地址
		  if(_owner.isMediaSource(_target[0])){
			  NetHelper nethelper = new NetHelper();
			  nethelper.saveToFile(_target[0]);	
			  _http.send(_target[0],null,_owner.getPageEncode());
			  _owner.completePage(_http,true);
		  }
		  //知识类别地址
		  if(_target[1].equalsIgnoreCase("")||_target[0].equalsIgnoreCase(_target[1])){
			  _http.send(_target[0],null,_owner.getPageEncode());

			  HTMLParser parse = new HTMLParser();
			  parse._source = new StringBuffer(_http.getBody());			  
			  
	          //find all the links
			  while ( !parse.eof() ) {
				  char ch = parse.get();
				  if ( ch==0 ) {
					  HTMLTag tag = parse.getTag();
					  Attribute href = tag.get("HREF");
					  if ( href==null ){
						  href = tag.get("SRC");
						  if( href==null ){
							  continue;
						  }
					  }


					  URL target=null;
					  try {
						  target = new URL(new URL(_target[0]),href.getValue());
					  } catch ( MalformedURLException e ) {
						  log.trace("Spider found other link: " + href );
						  _owner.foundOtherLink(href.getValue());
						  continue;
					  }
					  boolean removequery = _owner.getRemoveQuery();
					  if ( removequery ){
						  target = URLUtility.stripQuery(target);
						  target = URLUtility.stripAnhcor(target);
					  }

                      if ( target.getHost().equalsIgnoreCase(
							  new URL(_target[0]).getHost()) ) {
                    	  //log.info("Spider found internal link: " + target.toString() );						   
						  _owner.foundNewsLink(target.toString(),_target[0]);
					  }				  
				  }
			  }
			  
			  _owner.completePage(_http,true);
		  }
		  //知识地址
		  if(_owner.getUrlRule().isNewsPage(_target[0])){
			  _http.send(_target[0],null,_owner.getPageEncode());

			  HTMLParser parse = new HTMLParser();
			  _owner.getParser().setSource(_http.getBody());
			  parse._source = new StringBuffer(_owner.getParser().getSummary());
			  _owner.processPage(_http,_target[1]);
			  
			  //find all the links
			  while ( !parse.eof() ) {
				  char ch = parse.get();
				  if ( ch==0 ) {
					  HTMLTag tag = parse.getTag();
					  Attribute href = tag.get("HREF");
					  if ( href==null ){
						  href = tag.get("SRC");
						  if( href==null ){
							  continue;
						  }
					  }

					  URL target=null;
					  try {
						  target = new URL(new URL(_target[0]),href.getValue());
					  } catch ( MalformedURLException e ) {
						  log.trace("Spider found other link: " + href );
						  continue;
					  }
					  boolean removequery = _owner.getRemoveQuery();
					  if ( removequery ){
						  target = URLUtility.stripQuery(target);
						  target = URLUtility.stripAnhcor(target);
					  }

					  _owner.foundMediaLink(target.toString(),_target[0]);
					  				  
				  }
			  }
		  }
		  _owner.completePage(_http,true);
		  
		  
	  } catch ( java.io.IOException e ) {
		  log.error("Error loading file("+ _target +"): " + e );
	  } catch ( Exception e ) {
		  log.error("Exception while processing file("+ _target +"): ", e );
	  } finally {
		  _owner.completePage(_http,false);
		  _busy = false;

	  }
	  
  }

  /**
   * Returns the HTTP descendant that this
   * object should use for all HTTP communication.
   *
   * @return An HTTP descendant object.
   */
  public HTTP getHTTP()
  {
    return _http;
  }
}

