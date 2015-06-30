package edu.zju.cims201.GOF.service.sml;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.sml.SmlUnitPoolDao;
import edu.zju.cims201.GOF.hibernate.pojoA.SmlUnitPool;

@Service
@Transactional
public class SmlUnitPoolServicImpl implements SmlUnitPoolService {
	private SmlUnitPoolDao smlUnitPoolDao;
	
	public List<SmlUnitPool> getAllSmlUnit(){
		List<SmlUnitPool> supList =smlUnitPoolDao.getAll();
		return supList;
	}
	public SmlUnitPoolDao getSmlUnitPoolDao() {
		return smlUnitPoolDao;
	}
	@Autowired
	public void setSmlUnitPoolDao(SmlUnitPoolDao smlUnitPoolDao) {
		this.smlUnitPoolDao = smlUnitPoolDao;
	}
	
	
}
