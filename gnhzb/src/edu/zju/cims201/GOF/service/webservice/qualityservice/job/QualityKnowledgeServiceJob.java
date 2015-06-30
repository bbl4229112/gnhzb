package edu.zju.cims201.GOF.service.webservice.qualityservice.job;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.webservice.AxisWebService;
@Component
public class QualityKnowledgeServiceJob implements Job {
	
	
	

	private static Log _log = LogFactory.getLog(QualityKnowledgeServiceJob.class);
	AxisWebService axisWebService;
	private static ApplicationContext cxt;
	public QualityKnowledgeServiceJob() {
		super();
		cxt=new ClassPathXmlApplicationContext("applicationContext.xml");
		axisWebService = (AxisWebService) cxt.getBean("axisWebService");
	
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String jobName = context.getJobDetail().getFullName();

		_log.info("--------------执行工作：" + jobName + "  时间：" + new Date());
		try {
			axisWebService.QualityService();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
