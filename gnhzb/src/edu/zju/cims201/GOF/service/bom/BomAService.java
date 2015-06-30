package edu.zju.cims201.GOF.service.bom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.bom.BomADao;
import edu.zju.cims201.GOF.dao.classificationtree.ClassificationTreeADao;
import edu.zju.cims201.GOF.dao.part.PartADao;
import edu.zju.cims201.GOF.hibernate.pojoA.BomA;
import edu.zju.cims201.GOF.hibernate.pojoA.BomDetailA;
import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTreeA;
import edu.zju.cims201.GOF.hibernate.pojoA.PartA;

@Transactional
@Service
public class BomAService{
	private BomADao bomADao;
	private ClassificationTreeADao classificationTreeADao;
	private PartADao partADao;
	
	/*
	 * 设计资源:用于服务
	 */
	 public void saveInstanceBoms(List<BomA> boms) throws Exception{
		 for(BomA b : boms){
			 BomA bExist = bomADao.findUniqueBy("uuid",b.getUuid());
			 if(bExist != null){
				 continue;
			 }
			 List<BomDetailA> detailList = b.getBomDetailList();
			 for(int i=0; i<detailList.size(); i++){
				 BomDetailA bDetail = detailList.get(i);
				 bDetail.setBom(b);
				 String moduleUuid = bDetail.getModuleUuid();
				 String partUuid = bDetail.getPartUuid();
				 ClassificationTreeA module = classificationTreeADao.findUniqueBy("uuid", moduleUuid);
				 PartA part = partADao.findUniqueBy("uuid", partUuid);
				 if(module==null || part ==null){
					 throw new Exception("实例BOM无法找到对应的零件或模块，同步出错!");
				 }else{
					 bDetail.setModuleClass(module);
					 bDetail.setPart(part);
				 }
			 }
			 //清除原有的id值
			 b.setId(0);
			 bomADao.save(b);
		 }
	 }

	public BomADao getBomADao() {
		return bomADao;
	}
	@Autowired
	public void setBomADao(BomADao bomADao) {
		this.bomADao = bomADao;
	}

	public ClassificationTreeADao getClassificationTreeADao() {
		return classificationTreeADao;
	}
	@Autowired
	public void setClassificationTreeADao(
			ClassificationTreeADao classificationTreeADao) {
		this.classificationTreeADao = classificationTreeADao;
	}

	public PartADao getPartADao() {
		return partADao;
	}
	@Autowired
	public void setPartADao(PartADao partADao) {
		this.partADao = partADao;
	}
	 
	 
	
}
