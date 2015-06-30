package edu.zju.cims201.GOF.service.interfacedata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.interfacedata.InterfaceDataDao;
import edu.zju.cims201.GOF.dao.interfacedata.InterfaceDataInstanceDao;
import edu.zju.cims201.GOF.dao.interfacedata.InterfaceModuleDao;
import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTree;
import edu.zju.cims201.GOF.hibernate.pojoA.InterfaceData;
import edu.zju.cims201.GOF.hibernate.pojoA.InterfaceModule;
import edu.zju.cims201.GOF.rs.dto.InterfaceModuleDTO;

@Service
@Transactional
public class InterfaceModuleServiceImpl implements InterfaceModuleService {
	
	private InterfaceModuleDao interfaceModuleDao;
	private InterfaceDataDao interfaceDataDao;
	private InterfaceDataInstanceDao interfaceDataInstanceDao;
	
	public String addInterfaceModule(long modId, long mod2Id, String interfaceNumber,
			String interfaceRelation, String interfaceName, String interfaceType){
		InterfaceModule imExist= interfaceModuleDao.findUnique("from InterfaceModule im where im.module.id=? and im.module2.id=?", modId,mod2Id);
		if(null!=imExist){
			return "接口模块已存在，请重新选择！";
		}
		InterfaceModule iM =new InterfaceModule();
		ClassificationTree ct1 =new ClassificationTree();
		ClassificationTree ct2 =new ClassificationTree();
		ct1.setId(modId);
		ct2.setId(mod2Id);
		iM.setInterfaceName(interfaceName);
		iM.setInterfaceNumber(interfaceNumber);
		iM.setInterfaceRelation(interfaceRelation);
		iM.setInterfaceType(interfaceType);
		iM.setModule(ct1);
		iM.setModule2(ct2);
		interfaceModuleDao.save(iM);
		return "添加成功！";
	}
	
	public List<InterfaceModuleDTO> getInterfaceModulebyModId(long modId){
		List<InterfaceModule> ims =interfaceModuleDao.find("from InterfaceModule im where im.module.id=?",modId);
		List<InterfaceModuleDTO> imsDTO =new ArrayList<InterfaceModuleDTO>();
		int len =ims.size();
		for(int i=0;i<len;i++){
			InterfaceModule im =ims.get(i);
			long id =im.getId();
			long moduleId = im.getModule().getId();
			long module2Id = im.getModule2().getId();
			String interfaceName = im.getInterfaceName();
			String interfaceNumber = im.getInterfaceNumber();
			String interfaceRelation= im.getInterfaceRelation();
			String interfaceType =im.getInterfaceType();
			String module2Name = im.getModule2().getText();
			InterfaceModuleDTO imDTO = new InterfaceModuleDTO();
			imDTO.setId(id);
			imDTO.setModuleId(moduleId);
			imDTO.setModule2Id(module2Id);
			imDTO.setInterfaceName(interfaceName);
			imDTO.setInterfaceNumber(interfaceNumber);
			imDTO.setInterfaceRelation(interfaceRelation);
			imDTO.setInterfaceType(interfaceType);
			imDTO.setModule2Name(module2Name);
			imsDTO.add(imDTO);
		}
		return imsDTO;
	}
	
	public String deleteInterfaceModulebyId(long id){
		System.out.println("****************************************");
		System.out.println(id);
		List<InterfaceData> iDatas = interfaceDataDao.find("from InterfaceData iData where iData.interfaceModule.id=?", id);
		for(int i = 0;i<iDatas.size();i++){
			interfaceDataInstanceDao.batchExecute("delete from InterfaceDataInstance idi where idi.interfaceData.id=?", iDatas.get(i).getId());
			interfaceDataDao.delete(iDatas.get(i));
		}
		interfaceModuleDao.delete(id);
		return "删除成功！";
	}
	
	public InterfaceModuleDao getInterfaceModuleDao() {
		return interfaceModuleDao;
	}
	@Autowired
	public void setInterfaceModuleDao(InterfaceModuleDao interfaceModuleDao) {
		this.interfaceModuleDao = interfaceModuleDao;
	}

	public InterfaceDataDao getInterfaceDataDao() {
		return interfaceDataDao;
	}

	public InterfaceDataInstanceDao getInterfaceDataInstanceDao() {
		return interfaceDataInstanceDao;
	}
	@Autowired
	public void setInterfaceDataDao(InterfaceDataDao interfaceDataDao) {
		this.interfaceDataDao = interfaceDataDao;
	}
	@Autowired
	public void setInterfaceDataInstanceDao(
			InterfaceDataInstanceDao interfaceDataInstanceDao) {
		this.interfaceDataInstanceDao = interfaceDataInstanceDao;
	}
	
	
}
