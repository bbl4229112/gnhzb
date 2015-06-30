package edu.zju.cims201.GOF.service.logging;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.logging.SysBehaviorListDao;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorList;
@Service
@Transactional (readOnly = true)
public class SysBehaviorListServiceImpl implements SysBehaviorListService {

	@Resource(name="sysBehaviorListDao")
	SysBehaviorListDao sysBehaviorListDao;
	

	public String delete(SysBehaviorList sysBehaviorList) {
		sysBehaviorListDao.delete(sysBehaviorList);
		sysBehaviorListDao.flush();
		return "1";
	}


	public SysBehaviorList getSysBehaviorList(Long sysBehaviorListId) {
	
		SysBehaviorList SysBehaviorList=sysBehaviorListDao.get(sysBehaviorListId);
		return SysBehaviorList;
	}

	
	public List<SysBehaviorList> listSysBehaviorLists() {
		List<SysBehaviorList> list=sysBehaviorListDao.getAll();
		return list;
	}

	
	public String save(SysBehaviorList sysBehaviorList) {
		sysBehaviorListDao.save(sysBehaviorList);
		sysBehaviorListDao.flush();
		return "1";
	}

}
