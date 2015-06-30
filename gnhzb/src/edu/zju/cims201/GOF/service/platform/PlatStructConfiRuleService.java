package edu.zju.cims201.GOF.service.platform;

import java.util.List;

import edu.zju.cims201.GOF.rs.dto.PartDTO;
import edu.zju.cims201.GOF.rs.dto.PartRuleDTO;

public interface PlatStructConfiRuleService {

	public List<PartRuleDTO> getAllRuleByClass(long platStructId, long moduleClassId);

	public String getAllPartsByPlatId(long platId, long moduleClassId);

	public List<PartDTO> getSelectedParts(long platStructId,
			long moduleClassId, long partId, int status);

	public String addPartRule(long platId, long platStructId,
			long moduleClassId, String classCode, String classText,
			long partId, long partSelectedId, String info, int status);

	public String deletePartRule(long platStructId, long partId,
			long partSelectedId, int status);

}
