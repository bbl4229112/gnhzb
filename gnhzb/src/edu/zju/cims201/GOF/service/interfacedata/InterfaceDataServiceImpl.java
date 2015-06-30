package edu.zju.cims201.GOF.service.interfacedata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.interfacedata.InterfaceDataDao;
import edu.zju.cims201.GOF.dao.interfacedata.InterfaceDataInstanceDao;
import edu.zju.cims201.GOF.dao.part.PartDao;
import edu.zju.cims201.GOF.hibernate.pojoA.InterfaceData;
import edu.zju.cims201.GOF.hibernate.pojoA.InterfaceDataInstance;
import edu.zju.cims201.GOF.hibernate.pojoA.InterfaceModule;
import edu.zju.cims201.GOF.hibernate.pojoA.Part;
import edu.zju.cims201.GOF.rs.dto.InterfaceDataDTO;
import edu.zju.cims201.GOF.util.JSONUtil;

@Service
@Transactional
public class InterfaceDataServiceImpl implements InterfaceDataService {
	private InterfaceDataDao interfaceDataDao;
	private InterfaceDataInstanceDao interfaceDataInstanceDao;
	private PartDao partDao;
	
	/**
	 * 添加接口数据
	 * @param moduleId 模块接口id
	 * @param interfaceInstanceId 本身实例id
	 * @param interfaceInstance2Id 关联实例id，得到的是多个实例id用“,”隔开的字符串
	 * @param interfaceType 接口类型
	 * @param interfaceElement 接口元素
	 * @param interfaceParams 接口参数
	 * @param interfaceNumber 参数数值
	 * @return 返回消息
	 */
	public String addInterfaceData(long interfaceModuleId, long interfaceInstanceId,
			String interfaceInstance2Id, String interfaceType,
			String interfaceElement, String interfaceParams,
			String interfaceNumber){
		
		InterfaceData iData = new InterfaceData();
		iData.setInterfaceElement(interfaceElement);
		Part p1 =new Part();
		p1.setId(interfaceInstanceId);
		iData.setInterfaceInstance(p1);
		InterfaceModule im =new InterfaceModule();
		im.setId(interfaceModuleId);
		iData.setInterfaceModule(im);
		iData.setInterfaceNumber(interfaceNumber);
		iData.setInterfaceParams(interfaceParams);
		iData.setInterfaceType(interfaceType);
		interfaceDataDao.save(iData);
		
		String[] idsStr = interfaceInstance2Id.split(",");
		int idsLength =idsStr.length;
		for(int i=0;i<idsLength;i++){
			long id =Long.parseLong(idsStr[i]);
			InterfaceDataInstance idi =new InterfaceDataInstance();
			idi.setInterfaceData(iData);
			Part p2 =new Part();
			p2.setId(id);
			idi.setInterfaceInstance2(p2);
			interfaceDataInstanceDao.save(idi);
		}
		
		return "添加成功！";
	}
	/**
	 * 根据模块接口显示实例与关联实例的所有数据
	 */
	public List<InterfaceDataDTO> getInterfaceDataByInterfaceModId(long interfaceModuleId)throws HibernateException{
		//保存父集合
		List<InterfaceDataDTO> idDTOs = new ArrayList<InterfaceDataDTO>();

		//List counts =interfaceDataDao.createQuery("select count(*) from InterfaceData iData where iData.interfaceModule.id =4 group by iData.interfaceInstance").list();
		//List counts =interfaceDataDao.createQuery("select iI.partNumber,count(*) from InterfaceData iData  inner join iData.interfaceInstance iI where iData.interfaceModule.id =4 group by iI.partNumber").list();
		List counts =interfaceDataDao.createQuery("select iData.interfaceInstance.id,count(*) from InterfaceData iData  where iData.interfaceModule.id =? group by iData.interfaceInstance.id",interfaceModuleId).list();
		Iterator it =counts.iterator();
		while(it.hasNext()){
			Object obj =(Object)it.next();
			String objStr = JSONUtil.write(obj);
			objStr =objStr.substring(1, objStr.length()-1);
			String[] strArr =objStr.split(",");
			//获得模块接口的实例1的id
			long interfaceInstanceId =Long.parseLong(strArr[0]);
			//模块接口中包含实例1的记录数
			int count =Integer.parseInt(strArr[1]);
			//根据实例1id查找 实例型号
			Part p = partDao.get(interfaceInstanceId);
			String interfaceInstanceNumber =p.getPartNumber();
			//获得模块接口中的实例1名称以及该实例对应的接口数据记录数
			String parentStr = interfaceInstanceNumber+"("+count+"条)";
			InterfaceDataDTO idDTO =new InterfaceDataDTO();
			idDTO.setInterfaceInstanceNumber(parentStr);
			idDTO.setInterfaceInstanceId(interfaceInstanceId);
			List<InterfaceData> iDatas =interfaceDataDao.find("from InterfaceData iData where iData.interfaceInstance.id=? and iData.interfaceModule.id = ?",interfaceInstanceId,interfaceModuleId);
			//保存子集合
			List<InterfaceDataDTO> idDTO2s = new ArrayList<InterfaceDataDTO>();
			//根据接口模块id和接口模块实例1id取得分组后的组内记录信息
			for(int i=0;i<iDatas.size();i++){
				InterfaceData iData =iDatas.get(i);
	
				List<InterfaceDataInstance> idis = interfaceDataInstanceDao.find("from InterfaceDataInstance idi where idi.interfaceData.id=?", iData.getId());
				String interfaceInstance2Ids ="";
				String interfaceInstance2Number="";
				for(int j =0;j<idis.size();j++){
					InterfaceDataInstance idi =idis.get(j);
					
					Part p2 =idi.getInterfaceInstance2();
					interfaceInstance2Ids+=p2.getId()+",";
					interfaceInstance2Number+=p2.getPartNumber()+",";
				}
				
				interfaceInstance2Ids =interfaceInstance2Ids.substring(0, interfaceInstance2Ids.length()-1);
				interfaceInstance2Number=interfaceInstance2Number.substring(0,interfaceInstance2Number.length()-1);
				
				InterfaceDataDTO idDTO2 =new InterfaceDataDTO();
				idDTO2.setId(iData.getId());
				idDTO2.setChildren(null);
				idDTO2.setInterfaceElement(iData.getInterfaceElement());
				
				
				idDTO2.setInterfaceInstance2Id(interfaceInstance2Ids);
				idDTO2.setInterfaceInstance2Number(interfaceInstance2Number);
				
				
				idDTO2.setInterfaceInstanceId(interfaceInstanceId);
				idDTO2.setInterfaceInstanceNumber(interfaceInstanceNumber);
				idDTO2.setInterfaceNumber(iData.getInterfaceNumber());
				idDTO2.setInterfaceParams(iData.getInterfaceParams());
				idDTO2.setInterfaceType(iData.getInterfaceType());
				
				
				idDTO2s.add(idDTO2);
				
			}	
			idDTO.setChildren(idDTO2s);
			idDTOs.add(idDTO);
		}
		return idDTOs;
		
	}
	
	public String deleteInterfaceData(long id){
		interfaceDataInstanceDao.batchExecute("delete from InterfaceDataInstance idi where idi.interfaceData.id=?", id);
		interfaceDataDao.delete(id);
		return "删除成功！";
	}
	
	
	public InterfaceDataDao getInterfaceDataDao() {
		return interfaceDataDao;
	}
	@Autowired
	public void setInterfaceDataDao(InterfaceDataDao interfaceDataDao) {
		this.interfaceDataDao = interfaceDataDao;
	}

	public InterfaceDataInstanceDao getInterfaceDataInstanceDao() {
		return interfaceDataInstanceDao;
	}
	@Autowired
	public void setInterfaceDataInstanceDao(
			InterfaceDataInstanceDao interfaceDataInstanceDao) {
		this.interfaceDataInstanceDao = interfaceDataInstanceDao;
	}

	public PartDao getPartDao() {
		return partDao;
	}
	@Autowired
	public void setPartDao(PartDao partDao) {
		this.partDao = partDao;
	}
	
}
