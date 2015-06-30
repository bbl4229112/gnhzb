package edu.zju.cims201.GOF.service.knowledge.workload;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;


import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Rating;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Vote;
import edu.zju.cims201.GOF.hibernate.pojo.Workload;

/**

 */
public interface WorkloadService {
	public String prepClear(String parturl);
	public char prepGetStatus(String url);
	public int prepSetStatus1(String url);
	public String prepSetStatus2(String url,char status,String parentUrl);
	public String prepSetStatus3(char status,String url);
	public Workload prepAssign(String parturl);
}


