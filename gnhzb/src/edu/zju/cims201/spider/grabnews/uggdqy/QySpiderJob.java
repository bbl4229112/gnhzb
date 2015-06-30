package edu.zju.cims201.spider.grabnews.uggdqy;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QySpiderJob implements Job {
	private static Log _log = LogFactory.getLog(QySpiderJob.class);
    public QySpiderJob(){
    	
    }
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String jobName = context.getJobDetail().getFullName();
		QySpider bzwspider = QySpider.getInstance();
		_log.info("执行工作：" + jobName + "  时间：" + new Date());
		bzwspider.init();

	}
	
}
