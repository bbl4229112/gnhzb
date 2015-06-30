package edu.zju.cims201.GOF.service.sml;



import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.SmlParameterPool;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.SmlParameterPoolDTO;




public interface SmlParameterPoolService {

	public String addSmlParameter(String smlName, String smlCode, long smlCodeId,
			String dataType, long moduleId, long unitId, double maxValue,
			double minValue, String defaultValue, String information);

	public PageDTO getAllSmlParameter(int index, int size);

	public String modifySmlParameter(long id, String smlName, String smlCode,
			long smlCodeId, String dataType, long moduleId, long unitId,
			double maxValue, double minValue, String defaultValue,
			String information);

	public String deleteSmlParameter(long id);

	public List<SmlParameterPoolDTO> getSmlParameterByModId(long moduleId);

}
