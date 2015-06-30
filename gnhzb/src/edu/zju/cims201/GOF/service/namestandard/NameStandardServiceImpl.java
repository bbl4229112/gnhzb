package edu.zju.cims201.GOF.service.namestandard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.namestandard.NameStandardDao;
import edu.zju.cims201.GOF.hibernate.pojoA.NameStandard;
@Service
@Transactional
public class NameStandardServiceImpl implements NameStandardService{

	private NameStandardDao nameStandardDao;
	public NameStandardDao getNameStandardDao() {
		return nameStandardDao;
	}
	@Autowired
	public void setNameStandardDao(NameStandardDao nameStandardDao) {
		this.nameStandardDao = nameStandardDao;
	}
	public void save(NameStandard ns) {
		// TODO Auto-generated method stub
		nameStandardDao.save(ns);
	}
	
	public List<NameStandard> getAll(){
		List<NameStandard> nsList =nameStandardDao.getAll();
		return nsList;
	}

}
