package edu.zju.cims201.GOF.service.task;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import edu.zju.cims201.GOF.dao.task.LcataskDAO;
import edu.zju.cims201.GOF.dao.task.PdmtaskDAO;
import edu.zju.cims201.GOF.hibernate.pojo.ProcessUrl;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.Employee;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.LcaTask;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmTask;
import edu.zju.cims201.GOF.util.Constants;


@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    
    @Resource(name = "lcataskDAO")
    private LcataskDAO lcataskDAO;
    @Resource(name = "pdmtaskDAO")
    private PdmtaskDAO pdmtaskDAO;
    @Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	public Page<LcaTask> getLcatasks(Employee user, Page<LcaTask> page) {
		// TODO Auto-generated method stub
		/*String hql=" select task from Task task, SystemUser user where " +
				" user.id in elements(task.carrier)  and user.id ="+user.getId()+" and task.status !='"+Constants.TASK_STATUS_CONFIG+"' and task.status !='"+Constants.TASK_STATUS_END+"' order by task.creatTime desc";*/
		/*String hql=" select task from Task task where " +
				" task.carrier ="+user.getId()+" and task.status !='"+Constants.TASK_STATUS_CONFIG+"' and task.status !='"+Constants.TASK_STATUS_END+"'";
		return taskDAO.findPage(page, hql);*/
		String hql=" select task from LcaTask task where " +
			" task.status !='"+Constants.TASK_STATUS_CONFIG+"' and task.status !='"+Constants.TASK_STATUS_END+"'"+" and task.carrier.id ="+user.getId();
		return lcataskDAO.findPage(page, hql);
		
	}
	public Page<PdmTask> getPdmtasks(Employee user, Page<PdmTask> page) {
		// TODO Auto-generated method stub
		/*String hql=" select task from Task task, SystemUser user where " +
				" user.id in elements(task.carrier)  and user.id ="+user.getId()+" and task.status !='"+Constants.TASK_STATUS_CONFIG+"' and task.status !='"+Constants.TASK_STATUS_END+"' order by task.creatTime desc";*/
		/*String hql=" select task from Task task where " +
				" task.carrier ="+user.getId()+" and task.status !='"+Constants.TASK_STATUS_CONFIG+"' and task.status !='"+Constants.TASK_STATUS_END+"'";
		return taskDAO.findPage(page, hql);*/
		String hql=" select task from PdmTask task where " +
			" task.status !='"+Constants.TASK_STATUS_CONFIG+"' and task.status !='"+Constants.TASK_STATUS_END+"'"+"and task.carrier.id ="+user.getId();
		return pdmtaskDAO.findPage(page, hql);
		
		
	}



	public void saveLcaTask(LcaTask t) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(t);
		
	}
	public void savePdmTask(PdmTask t){
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(t);
		
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public void savefunctionurl(ProcessUrl pu) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(pu);
		
	}


	public PdmTask getPdmTask(long id) {
		// TODO Auto-generated method stub
		return (PdmTask)sessionFactory.getCurrentSession().get(PdmTask.class, id);
	}
	public LcaTask getLcaTask(long id) {
		// TODO Auto-generated method stub
		return (LcaTask)sessionFactory.getCurrentSession().get(LcaTask.class, id);
	}
	public LcataskDAO getLcataskDAO() {
		return lcataskDAO;
	}
	public void setLcataskDAO(LcataskDAO lcataskDAO) {
		this.lcataskDAO = lcataskDAO;
	}
	public PdmtaskDAO getPdmtaskDAO() {
		return pdmtaskDAO;
	}
	public void setPdmtaskDAO(PdmtaskDAO pdmtaskDAO) {
		this.pdmtaskDAO = pdmtaskDAO;
	}

	
}
