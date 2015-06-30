package edu.zju.cims201.GOF.service.sml;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.sml.SmlCodePoolDao;
import edu.zju.cims201.GOF.dao.sml.SmlParameterPoolDao;
import edu.zju.cims201.GOF.hibernate.pojoA.SmlCodePool;
import edu.zju.cims201.GOF.hibernate.pojoA.SmlParameterPool;

@Service
@Transactional
public class SmlCodePoolServiceImpl implements SmlCodePoolService {
	private SmlCodePoolDao smlCodePoolDao;
	private SmlParameterPoolDao smlParameterPoolDao;
	
	public List<SmlCodePool> getAllSmlCode(){
		List<SmlCodePool> scpList = new ArrayList<SmlCodePool>();
		scpList = smlCodePoolDao.getAll();
		return scpList;
	}
	
	public String addSmlCode(String firstCode, String codeName,
			String information){
		SmlCodePool scpExist1 =smlCodePoolDao.findUniqueBy("firstCode", firstCode);
		if(null != scpExist1){
			return "分类码已存在，请重新添加";
		}
		SmlCodePool scpExist2 =smlCodePoolDao.findUniqueBy("codeName", codeName);
		if(null != scpExist2){
			return "名称已存在，请重新添加";
		}
		SmlCodePool scp =new SmlCodePool();
		scp.setCodeName(codeName);
		scp.setFirstCode(firstCode);
		scp.setInformation(information);
		smlCodePoolDao.save(scp);
		return "添加成功！";
	}
	/**
	 * 修改事物特性编码
	 */
	public String modifySmlCode(long id, String firstCode, String codeName,
			String information){
		//SmlCodePool scpExist1 =smlCodePoolDao.findUniqueBy("firstCode", firstCode);
		SmlCodePool scpExist1=smlCodePoolDao.findUnique("from SmlCodePool scp where scp.id <> ? and scp.firstCode=?",id,firstCode);
		if(null != scpExist1){
			return "分类码已存在，请重新修改";
		}
		//SmlCodePool scpExist2 =smlCodePoolDao.findUniqueBy("codeName", codeName);
		SmlCodePool scpExist2=smlCodePoolDao.findUnique("from SmlCodePool scp where scp.id <> ? and scp.codeName=?",id,codeName);
		if(null != scpExist2){
			return "名称已存在，请重新修改";
		}
		SmlCodePool scp = smlCodePoolDao.get(id);
		scp.setCodeName(codeName);
		scp.setFirstCode(firstCode);
		scp.setInformation(information);
		smlCodePoolDao.save(scp);
		return "修改成功！";
		
	}
	/**
	 * 删除事物特性编码（取消事物特性参数的分类）
	 */
	public String deleteSmlCode(long id){
		SmlParameterPool spp =smlParameterPoolDao.findUnique("from SmlParameterPool spp where spp.codeBelong.id=?", id);
		if(null!=spp){
			return "该编码 下已有事物特性参数，无法删除";
		}
		smlCodePoolDao.delete(id);
		return "删除成功";
	}

	public SmlCodePoolDao getSmlCodePoolDao() {
		return smlCodePoolDao;
	}
	@Autowired
	public void setSmlCodePoolDao(SmlCodePoolDao smlCodePoolDao) {
		this.smlCodePoolDao = smlCodePoolDao;
	}

	public SmlParameterPoolDao getSmlParameterPoolDao() {
		return smlParameterPoolDao;
	}
	@Autowired
	public void setSmlParameterPoolDao(SmlParameterPoolDao smlParameterPoolDao) {
		this.smlParameterPoolDao = smlParameterPoolDao;
	}
	
	
}
