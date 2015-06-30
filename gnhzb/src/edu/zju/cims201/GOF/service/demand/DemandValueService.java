package edu.zju.cims201.GOF.service.demand;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.DemandValue;

public interface DemandValueService {

	public String addDemandValue(long demandId, String demandValueName,
			String demandValueMemo);

	public List<DemandValue> getValueByDemandId(long demandId);

	public String deleteDemandValue(long id);

	public String updateDemandValue(long id, long demandId,String demandValueName,
			String demandValueMemo);

}
