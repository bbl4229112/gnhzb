package edu.zju.cims201.GOF.service.biaozhun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import edu.zju.cims201.GOF.dao.biaozhun.BiaozhunDao;
import edu.zju.cims201.GOF.hibernate.pojoA.Biaozhun;

@Service
@Transactional("transactionManagerBiaozhun") 
public class BiaozhunServiceImpl implements BiaozhunService {
	
	private BiaozhunDao biaozhunDao;

	public Page<Biaozhun> searchBiaozhun(String keyword,Page<Biaozhun> page) {
		String hql = "from Biaozhun o where o.titleNameChi like '%"+keyword+"%' order by o.id";
		Page<Biaozhun> newpage = biaozhunDao.findPage(page, hql);		
		return newpage;
	}
	
	public BiaozhunDao getBiaozhunDao() {
		return biaozhunDao;
	}
	@Autowired
	public void setBiaozhunDao(BiaozhunDao biaozhunDao) {
		this.biaozhunDao = biaozhunDao;
	}
	
	

}
