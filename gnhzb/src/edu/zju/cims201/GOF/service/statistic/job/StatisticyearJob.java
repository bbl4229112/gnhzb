package edu.zju.cims201.GOF.service.statistic.job;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class StatisticyearJob implements Job {
	private static Log _log = LogFactory.getLog(StatisticyearJob.class);

	public StatisticyearJob() {
		super();
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String jobName = context.getJobDetail().getFullName();
		Statistic statisitc = new Statistic();
		_log.info("--------------执行工作：" + jobName + "  时间：" + new Date());
		statisitc.updateYear();
	}

}
