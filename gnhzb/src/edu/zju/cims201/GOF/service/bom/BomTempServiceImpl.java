package edu.zju.cims201.GOF.service.bom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.zju.cims201.GOF.dao.bom.BomTempDao;
import edu.zju.cims201.GOF.dao.platform.PlatStructConfiRuleDao;
import edu.zju.cims201.GOF.dao.platform.PlatStructTreeDao;
import edu.zju.cims201.GOF.hibernate.pojoA.BomTemp;
import edu.zju.cims201.GOF.hibernate.pojoA.OrderManage;
import edu.zju.cims201.GOF.hibernate.pojoA.Part;
import edu.zju.cims201.GOF.hibernate.pojoA.PlatStructConfiRule;
import edu.zju.cims201.GOF.hibernate.pojoA.PlatStructTree;
import edu.zju.cims201.GOF.hibernate.pojoA.PlatformManage;
import edu.zju.cims201.GOF.rs.dto.BomTempDTO;
import edu.zju.cims201.GOF.rs.dto.PartDTO;
import edu.zju.cims201.GOF.util.JSONUtil;

@Service
@Transactional
public class BomTempServiceImpl implements BomTempService{
	private BomTempDao bomTempDao;
	private PlatStructTreeDao platStructTreeDao;
	
	private PlatStructConfiRuleDao platStructConfiRuleDao;
	
	public PlatStructConfiRuleDao getPlatStructConfiRuleDao() {
		return platStructConfiRuleDao;
	}
	@Autowired
	public void setPlatStructConfiRuleDao(
			PlatStructConfiRuleDao platStructConfiRuleDao) {
		this.platStructConfiRuleDao = platStructConfiRuleDao;
	}
	public BomTempDao getBomTempDao(){
		return bomTempDao;
	}
	@Autowired
	public void setBomTempDao(BomTempDao bomTempDao) {
		this.bomTempDao = bomTempDao;
	}
	
	
	public PlatStructTreeDao getPlatStructTreeDao() {
		return platStructTreeDao;
	}
	@Autowired
	public void setPlatStructTreeDao(PlatStructTreeDao platStructTreeDao) {
		this.platStructTreeDao = platStructTreeDao;
	}
	public String addAll2BomTemp(long platId, long orderId){
		List<PlatStructConfiRule> partsRuleList = platStructConfiRuleDao.find("from PlatStructConfiRule pst where pst.plat.id=?", platId);
		for(PlatStructConfiRule rule :partsRuleList){
			BomTemp bt = new BomTemp();
			OrderManage order = new OrderManage();
			order.setId(orderId);
			bt.setClassCode(rule.getClassCode());
			bt.setClassText(rule.getClassText());
			bt.setModuleClass(rule.getModuleClass());
			bt.setPart(rule.getPart());
			bt.setInfo(rule.getInfo());
			bt.setPartSelected(rule.getPartSelected());
			bt.setPlat(rule.getPlat());
			bt.setPlatStruct(rule.getPlatStruct());
			bt.setQuantity(1);
			bt.setStatus(rule.getStatus());
			bt.setOrder(order);
			bt.setConfig(1);
			bomTempDao.save(bt);
			bomTempDao.flush();
		}
		
		return "数据载入成功";
	}
	
	public void deleteBomTempByPlatIdAndOrderId(long platId, long orderId){
		bomTempDao.batchExecute("delete from BomTemp bt where bt.plat.id=? and bt.order.id=?", platId,orderId);
	}
	
	public String getBomTempByPlatIdAndOrderId(long platId, long orderId){
		List<PlatStructTree> pstList = platStructTreeDao.find("from PlatStructTree pst where pst.plat.id=? and pst.moduleClass.id <> null", platId);
		@SuppressWarnings("unchecked")
		List<BomTemp> btList = bomTempDao.createQuery("select new BomTemp(bt.platStruct,bt.moduleClass,bt.part,bt.quantity,bt.order) from BomTemp bt where bt.plat.id = ? and bt.order.id = ? group by bt.platStruct,bt.moduleClass,bt.part,bt.quantity,bt.order", platId,orderId).list();
		StringBuffer btDTOListStr = new StringBuffer("[");
		for(PlatStructTree pst :pstList){
			long moduleId = pst.getModuleClass().getId();
			String moduleName = pst.getModuleClass().getText();
			List<BomTempDTO> btDTOList = new ArrayList<BomTempDTO>();
			int partCount =0;
			for(BomTemp bt : btList){
				if (moduleId == bt.getModuleClass().getId()){
					Part p = bt.getPart();
					BomTempDTO btDTO = new BomTempDTO();
					btDTO.setPartId(p.getId());
					btDTO.setPartName(p.getPartName());
					btDTO.setPartNumber(p.getPartNumber());
					btDTO.setQuantity(bt.getQuantity());
					btDTOList.add(btDTO);
					partCount++;
				}
			}
			String head = "{'partNumber':'类别："+moduleName+"（"+partCount+"条）',children:";
			btDTOListStr.append(head);
			String children = JSONUtil.write(btDTOList);
			btDTOListStr.append(children);
			btDTOListStr.append("},");
		}
		btDTOListStr.deleteCharAt(btDTOListStr.length()-1);
		btDTOListStr.append("]");
		
		return btDTOListStr.toString();
	}
	
	public List<PartDTO> getInstance2ChooseByPlatStructId(long platStructId, long orderId){
		@SuppressWarnings("unchecked")
		List<BomTemp> btList = bomTempDao.createQuery("select new BomTemp(bt.platStruct,bt.part,bt.quantity,bt.order) from BomTemp bt where bt.platStruct.id = ? and bt.order.id = ? group by bt.platStruct,bt.part,bt.quantity,bt.order", platStructId,orderId).list();
		List<PartDTO> parts = new ArrayList<PartDTO>();
		for(BomTemp bt :btList){
			PartDTO pDTO = new PartDTO();
			Part p =bt.getPart();
			pDTO.setId(p.getId());
			pDTO.setOrderId(orderId);
			pDTO.setPartName(p.getPartName());
			pDTO.setPartNumber(p.getPartNumber());
			pDTO.setPlatStructId(platStructId);
			pDTO.setCount(1);
			pDTO.setDescription(p.getDescription());
			parts.add(pDTO);
		}
		
		return parts;
	}
	
	
	
	public String configOrderByPartIds(long platStructId, long orderId,long[] partIdsArr){
		//查看平台树节点中是否有config=2的数据项，有则查看选中的数据项中是否包含了这些数据项
		List<BomTemp> configedList = bomTempDao.find("from BomTemp bt where bt.platStruct.id=? and bt.order.id=? and bt.config=2",platStructId,orderId);
		int configedListCount = configedList.size();
	
		//如果有confi=2的数据项，则将数据项的partId包装为数组
		if(configedListCount > 0){
			if(partIdsArr.length==1 && partIdsArr[0]==-1){
				return "有必选项未被选中，请重新选择";
			}
			Long[] configedPartIds=new Long[configedListCount];
			for(int i = 0; i<configedListCount; i++){
				configedPartIds[i] = configedList.get(i).getPart().getId();
				//查看配置状态为2的part是否在被选part中，默认为false,不在其中。
				boolean flag = false;
				for(int j =0; j<partIdsArr.length;j++){
					if(configedList.get(i).getPart().getId()==partIdsArr[j]){
						flag = true;
						break;
					}
				}
				if(flag = false){
					return "有必选项未被选中，请重新选择";
				}
			}
			
			//下面代码有问题！list和Arrays应该相反吧？
			/*List list=Arrays.asList(configedPartIds);
			for(int i=0;i<partIdsArr.length;i++){
				if(!list.contains(partIdsArr[i])){
					return "有必选项未被选中，请重新选择";
				}
			}*/
		}
		
		if(partIdsArr.length==1 && partIdsArr[0]==-1){
			//不执行
		}else{
			List<BomTemp> btList = bomTempDao.find("from BomTemp bt where bt.platStruct.id=? and bt.order.id=?",platStructId,orderId);
			//遍历选中零部件
			for(int i=0;i<partIdsArr.length;i++){
				//遍历平台结构树下选中的树节点所有零部件信息
				for(int j=0;j<btList.size();j++){
					BomTemp bt =btList.get(j);
					Part p =bt.getPart();
					Part pSelected = bt.getPartSelected();
					PlatformManage plat = bt.getPlat();
					//相等的话说明该零件是被选中的
					if(p.getId()==partIdsArr[i]){
						//状态为1表示必选
						if(bt.getStatus()==1){
							//零件id和被选零件id相同，说明选中为自身，将自身的状态设置为已配置
							if(p.getId()==pSelected.getId()){
								bt.setConfig(3);
							}else{
								//查找与之相关联的必选零部件是否存在
								BomTemp btExist = bomTempDao.findUnique("from BomTemp bt where bt.plat.id=? and bt.order.id = ? and bt.part.id =? and bt.partSelected.id = bt.part.id",plat.getId(),orderId,pSelected.getId());
								if(btExist==null){
									return "该零件的必选零件不存在，请重新配置";
								}
								//将与之相关联的必选零部件的config设置为2，表示未配置，但已经被关联
								bomTempDao.batchExecute("update BomTemp bt set bt.config=2 where bt.plat.id=? and bt.order.id=? and bt.part.id =? and bt.partSelected.id =bt.part.id",plat.getId(),orderId,pSelected.getId());
								//bomTempDao.findUnique("from BomTemp bt where bt.platStruct.id=? and bt.order.id=? and bt.part.id =? and bt.partSelected.id =bt.part.id",platStructId,orderId,pSelected.getId());
							}
						}
						if(bt.getStatus()==3){
							//删除与选择的零件相排斥的零件
							bomTempDao.batchExecute("delete BomTemp bt where bt.plat.id=? and bt.order.id=? and bt.part.id =?",plat.getId(),orderId,pSelected.getId());
						}
					}
					
					bomTempDao.save(bt);
				}
			} 
		}
		
		bomTempDao.batchExecute("delete BomTemp bt where bt.platStruct.id=? and bt.order.id=? and bt.config<>3", platStructId,orderId);
		if(partIdsArr.length==1 && partIdsArr[0]==-1){
			return "配置完成（不选）";
		}
		return "配置完成";
		
	}
	
	public void configPartCount(long platId, long orderId, long partId,int partCount){
		bomTempDao.batchExecute("update BomTemp bt set bt.quantity=? where bt.plat.id=? and bt.order.id=? and bt.part.id=? and bt.config=3",partCount,platId,orderId,partId);
	}

}
