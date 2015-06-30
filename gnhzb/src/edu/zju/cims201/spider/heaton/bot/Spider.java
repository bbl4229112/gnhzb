/**
 * The Spider class is the main organizational class for
 * spidering.  It delegates work to the SpiderWorker class.
 *
 * Copyright 2001 by Jeff Heaton
 *
 * @author Jeff Heaton
 * @version 1.0
 */
package edu.zju.cims201.spider.heaton.bot;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.zju.cims201.spider.commontool.Parser;
import edu.zju.cims201.spider.commontool.UrlRule;
import edu.zju.cims201.spider.commontool.ProxyUtil;

public class Spider extends Thread implements ISpiderReportable {
  private Log log = LogFactory.getLog(this.getClass());	
  protected IWorkloadStorable _workload;
  protected SpiderWorker _pool[];
  protected boolean _worldSpider;
  protected ISpiderReportable _manager;
  protected boolean _halted = false;
  protected SpiderDone _done = new SpiderDone();
  protected int _maxBodySize;
  protected String _pageEncode = "GB2312";

  /**
   * This constructor prepares the spider to begin.
   * Basic information required to begin is passed.
   * This constructor uses the internal workload manager.
   *
   * @param manager The object that this spider reports its findings to.
   * @param url The URL that the spider should begin at.
   * @param http The HTTP handler used by this spider.
   * @param poolsize The size of the thread pool.
   */
  public Spider(ISpiderReportable manager,String url,HTTP http,int poolSize)
  {
    this(manager,url,http,poolSize,new SpiderInternalWorkload());
  }

  /**
   * This constructor prepares the spider to begin.
   * Basic information required to begin is passed.
   * This constructor allows the user to specify a
   * customized workload manager.
   *
   * @param manager The object that this spider reports its findings to.
   * @param url The URL that the spider should begin at.
   * @param http The HTTP handler used by this spider.
   * @param poolsize The size of the thread pool.
   * @param w A customized workload manager.
   */
  public Spider(ISpiderReportable manager,String url,HTTP http,int poolSize,IWorkloadStorable w)
  {
	  ProxyUtil.useHttpProxy();
	_manager = manager;
    _worldSpider = false;
    _pageEncode = manager.getPageEncode();
    _pool = new SpiderWorker[poolSize];
    for ( int i=0;i<_pool.length;i++ ) {
      HTTP hc = http.copy();
      _pool[i] = new SpiderWorker( this,hc );
    }
    _workload = w;
    if ( url.length()>0 ) {
      _workload.clear();
      addWorkload(url,url);
    }
  }

  /**
   * Get the SpiderDone object used by this spider
   * to determine when it is done.
   *
   * @return Returns true if the spider is done.
   */
  public SpiderDone getSpiderDone()
  {
    return _done;
  }

  /**
   * The main loop of the spider. This can be called
   * directly, or the start method can be called to
   * run as a background thread. This method will not
   * return until there is no work remaining for the
   * spider.
   */
  public void run()
  {
    if ( _halted )
      return;
    for ( int i=0;i<_pool.length;i++ )
      _pool[i].start();

    try {
      _done.waitBegin();
      _done.waitDone();
      log.info("Spider has no work.");
      //Log.log(Log.LOG_LEVEL_NORMAL,"Spider has no work.");
      spiderComplete();

      for ( int i=0;i<_pool.length;i++ ) {
        _pool[i].interrupt();
        _pool[i].join();
        _pool[i] = null;
      }

    } catch ( Exception e ) {
    	log.error("Exception while starting spider", e);
      //Log.logException("Exception while starting spider", e);
    }

  }


  /**
   * This method is called to get a workload
   * from the workload manager. If no workload
   * is available, this method will block until
   * there is one.
   *
   * @return Returns the next URL to be spidered.
   */
/*  synchronized public String getWorkload()
  {
    try {
      for ( ;; ) {
        if ( _halted )
          return null;
        String w = _workload.assignWorkload();
        if ( w!=null )
          return w;
        wait();
      }
    } catch ( java.lang.InterruptedException e ) {
    }
    return null;
  }*/
  synchronized public String[] getWorkload()
  {
    try {
      for ( ;; ) {
        if ( _halted )
          return null;
        String[] w = _workload.assignWorkload();
        if ( w!=null )
          return w;
        wait();
      }
    } catch ( java.lang.InterruptedException e ) {
    }
    return null;
  }

  /**
   * Called to add a workload to the workload manager.
   * This method will release a thread that was waiting
   * for a workload. This method will do nothing if the
   * spider has been halted.
   *
   * @param url The URL to be added to the workload.
   */
  synchronized public void addWorkload(String url,String parentUrl)
  {
    if ( _halted )
      return;
      _workload.addWorkload(url,parentUrl);
      notify();    
  }
  synchronized public void addWorkload(String url)
  {
    if ( _halted )
      return;
      _workload.addWorkload(url);
      notify();    
  }
  /**
   * Called to specify this spider as either a world
   * or site spider. See getWorldSpider for more information
   * about what a world spider is.
   *
   * @param b True to be a world spider.
   */
  public void setWorldSpider(boolean b)
  {
    _worldSpider = b;
  }

  /**
   * Returns true if this is a world spider, a world
   * spider does not restrict itself to a single site
   * and will likely go on "forever".
   *
   * @return Returns true if the spider is done.
   */
  public boolean getWorldSpider()
  {
    return _worldSpider;
  }


  /**
   * Called when the spider finds an internal
   * link. An internal link shares the same
   * host address as the URL that started
   * the spider. This method hands the link off
   * to the manager and adds the URL to the workload
   * if necessary.
   *
   * @param url The URL that was found by the spider.
   * @return true - The spider should add this URL to the workload.
   * false - The spider should not add this URL to the workload.
   */
  synchronized public boolean foundInternalLink(String url,String parentUrl)
  {
    if ( _manager.foundInternalLink(url) )
      addWorkload(url,parentUrl);
    return true;
  }
  synchronized public boolean foundNewsLink(String url,String parentUrl)
  {
    if ( _manager.foundNewsLink(url,parentUrl) )
      addWorkload(url,parentUrl);
    return true;
  }
  synchronized public boolean foundMediaLink(String url,String parentUrl)
  {
    if ( _manager.foundMediaLink(url,parentUrl) )
      addWorkload(url,parentUrl);
    return true;
  }
  synchronized public boolean foundInternalLink(String url)
  {
    if ( _manager.foundInternalLink(url) )
      addWorkload(url);
    return true;
  }
  /**
   * Called when the spider finds an external
   * link. An external link does not share the
   * same host address as the URL that started
   * the spider. This method hands the link off
   * to the manager and adds the URL to the workload
   * if necessary. If this is a world spider, then
   * external links are treated as internal links.
   *
   * @param url The URL that was found by the spider.
   * @return true - The spider should add this URL to the workload.
   * false - The spider should not add this URL to the workload.
   */
  synchronized public boolean foundExternalLink(String url)
  {
    if ( _worldSpider ) {
      foundInternalLink(url);
      return true;
    }

    if ( _manager.foundExternalLink(url) )
      addWorkload(url);
    return true;
  }

  /**
   * Called when the spider finds a type of
   * link that does not point to another HTML
   * page(for example a mailto link). This method
   * hands the link off to the manager and adds
   * the URL to the workload if necessary.
   *
   * @param url The URL that was found by the spider.
   * @return true - The spider should add this URL to the workload.
   * false - The spider should not add this URL to the workload.
   */
  synchronized public boolean foundOtherLink(String url)
  {
    if ( _manager.foundOtherLink(url) )
      addWorkload(url);
    return true;
  }

  /**
   * Called to actually process a page. This is where the
   * work actually done by the spider is usually preformed.
   *
   * @param page The page contents.
   * @param error true - This page resulted in an HTTP error.
   * false - This page downloaded correctly.
   */
  synchronized public void processPage(HTTP page)
  {
    _manager.processPage(page);
  }
  synchronized public void processPage(HTTP page,String parenturl)
  {
    _manager.processPage(page,parenturl);
  }

  /**
   * This method is called by the spider to determine if
   * query strings should be removed. By default the spider
   * always chooses to remove query strings, so true is
   * returned.
   *
   * @return true - Query string should be removed.
   * false - Leave query strings as is.
   */
  synchronized public boolean getRemoveQuery()
  {
    return false;
  }

  /**
   * Called to request that a page be processed.
   * This page was just downloaded by the spider.
   * This messages passes this call on to its
   * manager.
   *
   * @param page The page contents.
   * @param error true - This page resulted in an HTTP error.
   * false - This page downloaded correctly.
   */
  synchronized public void completePage(HTTP page,boolean error)
  {
    _workload.completeWorkload(page.getURL(),error);
  }
  synchronized public void completePage(String url,boolean error)
  {
    _workload.completeWorkload(url,error);
  }
  /**
   * Called when the spider has no more work. This method
   * just passes this event on to its manager.
   */
  synchronized public void spiderComplete()
  {
    _manager.spiderComplete();
  }

  /**
   * Called to cause the spider to halt. The spider will not halt
   * immediately. Once the spider is halted the run method will
   * return.
   */
  synchronized public void halt()
  {
    _halted = true;
    //_workload.clear();
    notifyAll();
  }

  /**
   * Determines if the spider has been halted.
   *
   * @return Returns true if the spider has been halted.
   */
  public boolean isHalted()
  {
    return _halted;
  }

  /**
   * This method will set the maximum body size
   * that will be downloaded.
   *
   * @param i The maximum body size, or -1 for unlifted.
   */
  public void setMaxBody(int mx)
  {
    _maxBodySize = mx;
    for ( int i=0;i<_pool.length;i++ )
      _pool[i].getHTTP().setMaxBody(mx);
  }

  /**
   * This method will return the maximum body size
   * that will be downloaded.
   *
   * @return The maximum body size, or -1 for unlifted.
   */
  public int getMaxBody()
  {
    return _maxBodySize;
  }

  public void setPageEncode(String pageEncode){
	  this._pageEncode = this._manager.getPageEncode();
  }
  
  public String getPageEncode(){
	  return this._manager.getPageEncode();
  }

  public UrlRule getUrlRule() {
	  return this._manager.getUrlRule();
  }

  public boolean isMediaSource(String url) {
	  return this._manager.isMediaSource(url);
  }

  public ISpiderReportable getSpecifySpider(){
	  return this._manager;
  }

  public Parser getParser() {
	  return this._manager.getParser();
  }
  
}
