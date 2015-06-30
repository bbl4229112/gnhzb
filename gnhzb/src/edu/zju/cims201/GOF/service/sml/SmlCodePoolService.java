package edu.zju.cims201.GOF.service.sml;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.SmlCodePool;

public interface SmlCodePoolService {

	public List<SmlCodePool> getAllSmlCode();

	public String addSmlCode(String firstCode, String codeName,
			String information);

	public String modifySmlCode(long id, String firstCode, String codeName,
			String information);

	public String deleteSmlCode(long id);

}
