package edu.zju.cims201.GOF.service.biaozhun;

import org.springside.modules.orm.Page;

import edu.zju.cims201.GOF.hibernate.pojoA.Biaozhun;

public interface BiaozhunService {
	
	public Page<Biaozhun> searchBiaozhun(String keyword,Page<Biaozhun> page);
	
	

}
