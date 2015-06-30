package edu.zju.cims201.GOF.service.draft;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.DraftType;

public interface DraftTypeService {
	public List<DraftType> getAllIsnotMaster();
	public List<DraftType> getAll();
	public String editModel(long id,String typeSuffix,String typeDes);
	public String addSelf(String typeName,String typeCode,String typeSuffix,String typeDes);
	public String editSelf(long id,String typeName,String typeCode,String typeSuffix,String typeDes);
	public String deleteSelf(long id);
}
