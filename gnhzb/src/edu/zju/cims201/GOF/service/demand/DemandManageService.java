package edu.zju.cims201.GOF.service.demand;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.DemandManage;

public interface DemandManageService {

	public String addDemand(String demandName, String demandMemo);

	public String deleteDemand(long id);

	public String updateDemand(long id, String demandName, String demandMemo);

	public List<DemandManage> getAll();

}
