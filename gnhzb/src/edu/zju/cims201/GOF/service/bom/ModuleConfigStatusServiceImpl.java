package edu.zju.cims201.GOF.service.bom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.bom.ModuleConfigStatusDao;
import edu.zju.cims201.GOF.dao.order.OrderManageDao;
import edu.zju.cims201.GOF.dao.platform.PlatStructTreeDao;
import edu.zju.cims201.GOF.hibernate.pojoA.ModuleConfigStatus;
import edu.zju.cims201.GOF.hibernate.pojoA.OrderManage;
import edu.zju.cims201.GOF.hibernate.pojoA.PlatStructTree;

@Service
@Transactional
public class ModuleConfigStatusServiceImpl implements ModuleConfigStatusService {
	
	private ModuleConfigStatusDao moduleConfigStatusDao;
	private PlatStructTreeDao platStructTreeDao;
	private OrderManageDao orderManageDao;
	
	public ModuleConfigStatusDao getModuleConfigStatusDao() {
		return moduleConfigStatusDao;
	}
	@Autowired
	public void setModuleConfigStatusDao(ModuleConfigStatusDao moduleConfigStatusDao) {
		this.moduleConfigStatusDao = moduleConfigStatusDao;
	}

	public PlatStructTreeDao getPlatStructTreeDao() {
		return platStructTreeDao;
	}
	
	@Autowired
	public void setPlatStructTreeDao(PlatStructTreeDao platStructTreeDao) {
		this.platStructTreeDao = platStructTreeDao;
	}

	public OrderManageDao getOrderManageDao() {
		return orderManageDao;
	}
	@Autowired
	public void setOrderManageDao(OrderManageDao orderManageDao) {
		this.orderManageDao = orderManageDao;
	}
	public String initConfigStatus(long platId, long orderId){
		OrderManage order = orderManageDao.get(orderId);
		List<PlatStructTree> psts = platStructTreeDao.find("from PlatStructTree pst where pst.plat.id= ? ", platId);
		for(PlatStructTree pst :psts){
			if(pst.getParent()==null){
				continue;
			}
			long platStructTreeId = pst.getId();
			
			ModuleConfigStatus moduleConfigStatusExist = moduleConfigStatusDao.findUnique("from ModuleConfigStatus status where status.platStructTree.id=? and status.order.id=?", platStructTreeId,orderId);
			if(moduleConfigStatusExist == null){
				ModuleConfigStatus status = new ModuleConfigStatus();
				status.setOrder(order);
				status.setPlatStructTree(pst);
				status.setStatusName("待配置");
				moduleConfigStatusDao.save(status);
			}else{
				moduleConfigStatusDao.createQuery("update ModuleConfigStatus status set status.statusName=? where status.platStructTree.id=? and status.order.id=?", "待配置",platStructTreeId,orderId).executeUpdate();
			}
		}
		
		return "初始化成功";
	}
	
	public String moduleConfiged(long platStructId, long orderId){
		moduleConfigStatusDao.createQuery("update ModuleConfigStatus status  set status.statusName=? where status.platStructTree.id=? and status.order.id=?","已配置",platStructId,orderId).executeUpdate();
		return "已配置";
	}
	
}
