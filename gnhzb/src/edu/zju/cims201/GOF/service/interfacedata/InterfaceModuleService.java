package edu.zju.cims201.GOF.service.interfacedata;

import java.util.List;

import edu.zju.cims201.GOF.rs.dto.InterfaceModuleDTO;

public interface InterfaceModuleService {

	public String addInterfaceModule(long modId, long mod2Id, String interfaceNumber,
			String interfaceRelation, String interfaceName, String interfaceType);

	public List<InterfaceModuleDTO> getInterfaceModulebyModId(long modId);

	public String deleteInterfaceModulebyId(long id);

}
