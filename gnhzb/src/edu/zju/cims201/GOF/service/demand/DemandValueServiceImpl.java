package edu.zju.cims201.GOF.service.demand;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.demand.DemandValueDao;
import edu.zju.cims201.GOF.hibernate.pojoA.DemandManage;
import edu.zju.cims201.GOF.hibernate.pojoA.DemandValue;

@Service
@Transactional
public class DemandValueServiceImpl implements DemandValueService {
	private DemandValueDao demandValueDao;

	public String addDemandValue(long demandId, String demandValueName,
			String demandValueMemo){
		DemandValue dvExist =demandValueDao.findUniqueBy("demandValueName", demandValueName);
		if(dvExist!=null){
			return "需求值已存在，请重新添加";
		}
		DemandValue dv =new DemandValue();
		DemandManage dm =new DemandManage();
		dm.setId(demandId);
		dv.setDemandManage(dm);
		dv.setDemandValueMemo(demandValueMemo);
		dv.setDemandValueName(demandValueName);
		demandValueDao.save(dv);
		return "添加成功！";
		
	}
	
	public List<DemandValue> getValueByDemandId(long demandId){
		List<DemandValue> dvList = demandValueDao.find("from DemandValue dv where dv.demandManage.id=?", demandId);
		List<DemandValue> dvListRe = new ArrayList<DemandValue>();
		for(DemandValue dv :dvList){
			demandValueDao.getSession().evict(dv);
			dv.setDemandManage(null);
			dvListRe.add(dv);
		}
		return dvListRe;
	}
	/**
	 * 待完善，还要考虑order是否引用了demandValue
	 */
	public String deleteDemandValue(long id){
		demandValueDao.delete(id);
		return "删除成功！";
	}
	
	
	public String updateDemandValue(long id,long demandId, String demandValueName,
			String demandValueMemo){
		
/*		System.out.println(id);
		System.out.println(demandId);
		System.out.println(demandValueName);
		System.out.println(demandValueMemo);
		List<DemandValue> dvList=demandValueDao.find("from DemandValue dv where dv.demandValueName=? and dv.demandManage.id=?", demandValueName,demandId);
		if(dvList.size()>0){
			return "该需求值已经存在，请重新修改";
		}*/
		DemandValue dv =demandValueDao.get(id);
		dv.setDemandValueMemo(demandValueMemo);
		dv.setDemandValueName(demandValueName);
		demandValueDao.save(dv);
		return "修改成功！";
	}
	
	public DemandValueDao getDemandValueDao() {
		return demandValueDao;
	}
	@Autowired
	public void setDemandValueDao(DemandValueDao demandValueDao) {
		this.demandValueDao = demandValueDao;
	}
	
}
