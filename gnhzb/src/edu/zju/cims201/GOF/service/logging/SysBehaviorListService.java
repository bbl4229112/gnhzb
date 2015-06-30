package edu.zju.cims201.GOF.service.logging;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorList;

public interface SysBehaviorListService {

	public String save(SysBehaviorList sysBehaviorList);
	
	public String delete(SysBehaviorList sysBehaviorList);
	
	public SysBehaviorList getSysBehaviorList(Long sysBehaviorListId);
	
	public List<SysBehaviorList> listSysBehaviorLists();
}
