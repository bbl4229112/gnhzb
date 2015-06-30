package edu.zju.cims201.GOF.service.interfacedata;

import java.util.List;

import org.hibernate.HibernateException;

import edu.zju.cims201.GOF.rs.dto.InterfaceDataDTO;

public interface InterfaceDataService {

	public String addInterfaceData(long interfaceModuleId, long interfaceInstanceId,
			String interfaceInstance2Id, String interfaceType,
			String interfaceElement, String interfaceParams,
			String interfaceNumber);

	public List<InterfaceDataDTO> getInterfaceDataByInterfaceModId(long interfaceModuleId) throws HibernateException;

	public String deleteInterfaceData(long id);

}
