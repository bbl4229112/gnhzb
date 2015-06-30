package edu.zju.cims201.GOF.service.codeclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.codeclass.CodeClassADao;
import edu.zju.cims201.GOF.hibernate.pojoA.CodeClassA;

@Service
@Transactional
public class CodeClassAService {
	private CodeClassADao codeClassADao;
	/**
	 * 保存产品或零部件类（服务的方法）
	 * @param codeClass 保存产品或零部件类
	 */
	public void saveCodeClass(CodeClassA codeClass){
		codeClassADao.save(codeClass);
	}
	
	
	
	public CodeClassADao getCodeClassADao() {
		return codeClassADao;
	}
	@Autowired
	public void setCodeClassADao(CodeClassADao codeClassADao) {
		this.codeClassADao = codeClassADao;
	}
	
}
