package edu.zju.cims201.GOF.service.knowledge.workload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.hibernate.pojo.Workload;
import edu.zju.cims201.GOF.dao.knowledge.WorkloadDao;

@Service
@Transactional
public class WorkloadServiceImpl implements WorkloadService {
	@Resource(name="workloadDao")
	private WorkloadDao workloaddao;
	
	public String prepClear(String parturl) {		
		String hql="from Workload o where o.url like '%"+parturl+"%'";
		List<Workload> workloadlist=(ArrayList<Workload>)workloaddao.createQuery(hql).list();		
		for(Workload workload:workloadlist) {		
			workloaddao.delete(workload);
			workloaddao.flush();
		}
		return "1";		
	}
	
	public char prepGetStatus(String url) {		
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("url", url);
		String hql = "from Workload o where o.url= :url";
		Query query=workloaddao.createQuery(hql, params);
		List<Workload> workloadlist=(ArrayList<Workload>)query.list();	
		if(workloadlist.size()==0)
			return 'U';
		else{
			Workload wl=workloadlist.get(0);
			return wl.getStatus().charAt(0);
		}
			
	}
	
	
	public int prepSetStatus1(String url) {		
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("url", url);
		String hql = "from Workload o where o.url= :url";
		Query query=workloaddao.createQuery(hql, params);
		List<Workload> workloadlist=(ArrayList<Workload>)query.list();	
		return workloadlist.size();
			
	}
	
	public String prepSetStatus2(String url,char status,String parentUrl) {	
		Workload wl=new Workload(url,(new Character(status)).toString(),parentUrl);
		workloaddao.save(wl);
		workloaddao.flush();
		return "1";	
	}

	public String prepSetStatus3(char status,String url) {	
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("url", url);
		String hql = "from Workload o where o.url= :url";
		Query query=workloaddao.createQuery(hql, params);
		List<Workload> workloadlist=(ArrayList<Workload>)query.list();	
		for(Workload workload:workloadlist) {		
			workload.setStatus((new Character(status)).toString());
			workloaddao.save(workload);
		}	
		return "1";	
	}
	
	public Workload prepAssign(String parturl) {		
		String hql="from Workload o where o.url like '%"+parturl+"%' and o.status='W'";
		List<Workload> workloadlist=(ArrayList<Workload>)workloaddao.createQuery(hql).list();		
		if(workloadlist.size()==0){
			return null;
		}
		else{
			return workloadlist.get(0);
		}
				
	}
	

	
	



}

