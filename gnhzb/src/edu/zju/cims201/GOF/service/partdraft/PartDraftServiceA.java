package edu.zju.cims201.GOF.service.partdraft;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.partdraft.PartDraftADao;


@Transactional
@Service
public class PartDraftServiceA {
	
	private PartDraftADao partDraftADao;

	public PartDraftADao getPartDraftADao() {
		return partDraftADao;
	}
	@Autowired
	public void setPartDraftADao(PartDraftADao partDraftADao) {
		this.partDraftADao = partDraftADao;
	}
	
}
