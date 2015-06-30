package edu.zju.cims201.GOF.service.task;


import org.springside.modules.orm.Page;

import edu.zju.cims201.GOF.hibernate.pojo.Function;
import edu.zju.cims201.GOF.hibernate.pojo.ProcessUrl;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.Employee;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.LcaTask;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmTask;


public interface TaskService {
	public Page<LcaTask> getLcatasks(Employee user,Page<LcaTask> page);
	public Page<PdmTask> getPdmtasks(Employee user,Page<PdmTask> page);
	public void saveLcaTask(LcaTask t);
	public void savePdmTask(PdmTask t);
	public void savefunctionurl(ProcessUrl pu);
	public PdmTask getPdmTask(long id);
	public LcaTask getLcaTask(long id);
}
