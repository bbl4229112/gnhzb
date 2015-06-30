package edu.zju.cims201.GOF.service.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.status.StatusDao;
import edu.zju.cims201.GOF.hibernate.pojoA.Status;

@Service
@Transactional
public class StatusServiceImpl implements StatusService {
	private StatusDao statusDao;

	public List<Status> getAllStaus(){
		return statusDao.getAll();
	}
	
	
	
	public StatusDao getStatusDao() {
		return statusDao;
	}
	@Autowired
	public void setStatusDao(StatusDao statusDao) {
		this.statusDao = statusDao;
	}
	
}
