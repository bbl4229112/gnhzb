package edu.zju.cims201.GOF.service.draft;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.draft.DraftTypeDao;
import edu.zju.cims201.GOF.hibernate.pojoA.DraftType;

@Service
@Transactional
public class DraftTypeServiceImpl implements DraftTypeService {
	private DraftTypeDao draftTypeDao;
	
	
	public List<DraftType> getAllIsnotMaster(){
		List<DraftType> dts =draftTypeDao.findBy("ismaster",0);
		return dts;
	}
	
	public List<DraftType> getAll(){
		List<DraftType> dts =draftTypeDao.getAll();
		return dts;
	}
	
	public String editModel(long id,String typeSuffix,String typeDes){
		DraftType dt=draftTypeDao.get(id);
		dt.setTypeSuffix(typeSuffix);
		dt.setTypeDes(typeDes);
		draftTypeDao.save(dt);
		return "修改成功！";
	}
	
	public String addSelf(String typeName,String typeCode,String typeSuffix,String typeDes){
		DraftType dt =new DraftType();
		dt.setIsmaster(0);
		dt.setTypeCode(typeCode);
		dt.setTypeDes(typeDes);
		dt.setTypeName(typeName);
		dt.setTypeSuffix(typeSuffix);
		draftTypeDao.save(dt);
		return "添加成功！";
	}
	
	public String editSelf(long id,String typeName,String typeCode,String typeSuffix,String typeDes){
		DraftType dt=draftTypeDao.get(id);
		dt.setTypeCode(typeCode);
		dt.setTypeDes(typeDes);
		dt.setTypeName(typeName);
		dt.setTypeSuffix(typeSuffix);
		draftTypeDao.save(dt);
		return "修改成功！";
	}
	
	public String deleteSelf(long id){
		draftTypeDao.delete(id);
		return "删除成功！";
	}
	
	

	public DraftTypeDao getDraftTypeDao(){
		return draftTypeDao;
	}
	@Autowired
	public void setDraftTypeDao(DraftTypeDao draftTypeDao) {
		this.draftTypeDao = draftTypeDao;
	}
}
