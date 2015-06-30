package edu.zju.cims201.GOF.service.project;

import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmProject;



/**
 * 项目管理接口
 * @author yetao
 */
public interface ProjectService {
   
	//创建一个项目
	public void save(PdmProject p);
	public PdmProject getProject(long p);
	
}
