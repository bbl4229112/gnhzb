package edu.zju.cims201.GOF.service.sml;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.sml.SmlParameterPoolDao;
import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTree;
import edu.zju.cims201.GOF.hibernate.pojoA.SmlCodePool;
import edu.zju.cims201.GOF.hibernate.pojoA.SmlParameterPool;
import edu.zju.cims201.GOF.hibernate.pojoA.SmlUnitPool;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.SmlParameterPoolDTO;

@Service
@Transactional
public class SmlParameterPoolServiceImpl implements SmlParameterPoolService {
	private SmlParameterPoolDao smlParameterPoolDao;
	
	public String addSmlParameter(String smlName, String smlCode, long smlCodeId,
			String dataType, long moduleId, long unitId, double maxValue,
			double minValue, String defaultValue, String information){
		
		SmlParameterPool smlParameterPool = new SmlParameterPool();
		double defaultValue2 =0;
		SmlParameterPool sppExist1 =smlParameterPoolDao.findUnique("from SmlParameterPool spp where spp.smlName = ? and spp.moduleBelong.id=?", smlName,moduleId);
		if(null != sppExist1){
			return "事物特性名称已存在，请重新添加";
		}
		SmlParameterPool sppExist2 =smlParameterPoolDao.findUnique("from SmlParameterPool spp where spp.smlCode = ? and spp.moduleBelong.id=?", smlCode,moduleId);
		if(null != sppExist2){
			return "事物特性代码已存在，请重新添加";
		}
		
		if("".equals(defaultValue)){
			defaultValue ="0";
		}
		if("INTEGER".equals(dataType)){
			try {
				defaultValue2 = Integer.parseInt(defaultValue);
			}catch(NullPointerException  ne1) {
				ne1.printStackTrace();
			}catch(NumberFormatException ne2){
					return "默认值输入有误，请重新输入";
			}
			
			int minValueInt =(int)minValue;
			int maxValueInt =(int)maxValue;
			if(minValueInt!=minValue){
				return "最小值输入有误，请重新输入";
			}
			if(maxValueInt!=maxValue){
				return "最大值输入有误，请重新输入";
			}
			if(defaultValue2<minValue || defaultValue2>maxValue){
				return "默认值不能大于最大值或小于最小值";
			}
		}

		if("FLOAT".equals(dataType)){
			try {
				defaultValue2 = Double.parseDouble(defaultValue);
			}catch(NullPointerException  ne1) {
				ne1.printStackTrace();
			}catch(NumberFormatException ne2){
					return "默认值输入有误，请重新输入";
			}
			if(minValue>maxValue){
				return "最小值不能大于最大值，请重新添加";
			}
			if(defaultValue2<minValue||defaultValue2>maxValue){
				return "默认值不能大于最大值或者小于最小值，请重新输入";
			}
		}
		if("VARCHAR"==dataType){
			minValue=maxValue=0;
		}
		SmlCodePool scp = new SmlCodePool();
		scp.setId(smlCodeId);
		smlParameterPool.setCodeBelong(scp);
		
		smlParameterPool.setDataType(dataType);
		smlParameterPool.setDefaultValue(defaultValue);
		smlParameterPool.setInformation(information);
		smlParameterPool.setMaxValue(maxValue);
		smlParameterPool.setMinValue(minValue);
		
		ClassificationTree moduleBelong = new ClassificationTree();
		moduleBelong.setId(moduleId);
		smlParameterPool.setModuleBelong(moduleBelong);
		
		smlParameterPool.setSmlCode(smlCode);
		smlParameterPool.setSmlName(smlName);
		
		SmlUnitPool  sup =new SmlUnitPool();
		sup.setId(unitId);
		smlParameterPool.setUnit(sup);
		
		smlParameterPoolDao.save(smlParameterPool);
		
		return "添加成功！";
	}

	
	public String modifySmlParameter(long id, String smlName, String smlCode,
			long smlCodeId, String dataType, long moduleId, long unitId,
			double maxValue, double minValue, String defaultValue,
			String information){
		

		double defaultValue2 =0;
		SmlParameterPool sppExist1 =smlParameterPoolDao.findUnique("from SmlParameterPool spp where spp.id <> ? and spp.smlName = ? and spp.moduleBelong.id=?",id,smlName,moduleId);
		if(null != sppExist1){
			return "事物特性名称已存在，请重新修改";
		}
		SmlParameterPool sppExist2 =smlParameterPoolDao.findUnique("from SmlParameterPool spp where spp.id <> ?  and spp.smlCode = ? and spp.moduleBelong.id=?",id,smlCode,moduleId);
		if(null != sppExist2){
			return "事物特性代码已存在，请重新修改";
		}
		
		if("".equals(defaultValue)){
			defaultValue ="0";
		}
		if("INTEGER".equals(dataType)){
			try {
				defaultValue2 = Integer.parseInt(defaultValue);
			}catch(NullPointerException  ne1) {
				ne1.printStackTrace();
			}catch(NumberFormatException ne2){
					return "默认值输入有误，请重新修改";
			}


			int minValueInt =(int)minValue;
			int maxValueInt =(int)maxValue;
			if(minValueInt!=minValue){
				return "最小值输入有误，请重新修改";
			}
			if(maxValueInt!=maxValue){
				return "最大值输入有误，请重新修改";
			}
			if(defaultValue2<minValue || defaultValue2>maxValue){
				return "默认值不能大于最大值或小于最小值";
			}
		}

		if("FLOAT".equals(dataType)){
			try {
				defaultValue2 = Double.parseDouble(defaultValue);
			}catch(NullPointerException  ne1) {
				ne1.printStackTrace();
			}catch(NumberFormatException ne2){
					return "默认值输入有误，请重新修改";
			}
			if(minValue>maxValue){
				return "最小值不能大于最大值，请重新修改";
			}
			if(defaultValue2<minValue||defaultValue2>maxValue){
				return "默认值不能大于最大值或者小于最小值，请重新修改";
			}
		}
		if("VARCHAR"==dataType){
			minValue=maxValue=0;
		}
		SmlParameterPool smlParameterPool =smlParameterPoolDao.get(id); 
		SmlCodePool scp = new SmlCodePool();
		scp.setId(smlCodeId);
		smlParameterPool.setCodeBelong(scp);
		
		smlParameterPool.setDataType(dataType);
		smlParameterPool.setDefaultValue(defaultValue);
		smlParameterPool.setInformation(information);
		smlParameterPool.setMaxValue(maxValue);
		smlParameterPool.setMinValue(minValue);
		
		ClassificationTree moduleBelong = new ClassificationTree();
		moduleBelong.setId(moduleId);
		smlParameterPool.setModuleBelong(moduleBelong);
		
		smlParameterPool.setSmlCode(smlCode);
		smlParameterPool.setSmlName(smlName);
		
		SmlUnitPool  sup =new SmlUnitPool();
		sup.setId(unitId);
		smlParameterPool.setUnit(sup);
		
		smlParameterPoolDao.save(smlParameterPool);
		
		
		return "修改成功！";
	}
	
	public PageDTO getAllSmlParameter(int index, int size){
		 List<SmlParameterPoolDTO> sppDTOList = new ArrayList<SmlParameterPoolDTO>();
		 List<SmlParameterPool> sppList=smlParameterPoolDao.getAll();
		 for(SmlParameterPool spp:sppList){
			 SmlParameterPoolDTO sppDTO = new SmlParameterPoolDTO();
			 SmlCodePool  scp =spp.getCodeBelong();
			 ClassificationTree ct = spp.getModuleBelong();
			 SmlUnitPool sup = spp.getUnit();
			 
			 sppDTO.setCodeBelong(scp.getFirstCode());
			 sppDTO.setDataType(spp.getDataType());
			 sppDTO.setDefaultValue(spp.getDefaultValue());
			 sppDTO.setId(spp.getId());
			 sppDTO.setInformation(spp.getInformation());
			 sppDTO.setMaxValue(spp.getMaxValue());
			 sppDTO.setMinValue(spp.getMinValue());
			 sppDTO.setModuleBelong(ct.getText());
			 sppDTO.setModuleId(ct.getId());
			 sppDTO.setSmlCode(spp.getSmlCode());
			 sppDTO.setSmlCodeId(scp.getId());
			 sppDTO.setSmlName(spp.getSmlName());
			 sppDTO.setUnit(sup.getUnitCode());
			 sppDTO.setUnitId(sup.getId());
			 sppDTOList.add(sppDTO);
		 }
		 
		 List<SmlParameterPoolDTO> subSppDTOList = new ArrayList<SmlParameterPoolDTO>();
		 int total =sppDTOList.size();
			for(int i=index*size;i<((index+1)*size<total?(index+1)*size:total);i++){
				subSppDTOList.add(sppDTOList.get(i));
			}
			PageDTO pd =new PageDTO();
			pd.setPagesize(size);
			pd.setData(subSppDTOList);
			pd.setTotal(total);
			int totalPage =0;
			if(size!=0){
				if(total%size==0){
					totalPage = total/size;
				}else{
					totalPage = total/size+1;
				}
			}
			pd.setTotalPage(totalPage);
			return pd;
	}
	
	public String deleteSmlParameter(long id){
		smlParameterPoolDao.delete(id);
		return "删除成功！";
	}
	/**
	 * 根据大类id获取该大类所有的事物特性参数
	 * @param moduleId 大类模块id
	 */
	public List<SmlParameterPoolDTO>getSmlParameterByModId(long moduleId){
		List<SmlParameterPool> sppList=smlParameterPoolDao.find("from SmlParameterPool spp where spp.moduleBelong.id=?", moduleId);
		List<SmlParameterPoolDTO> sppDTOList =new ArrayList<SmlParameterPoolDTO>();

		for(SmlParameterPool spp:sppList){
			SmlParameterPoolDTO sppDTO = new SmlParameterPoolDTO();
			SmlCodePool  scp =spp.getCodeBelong();
			 ClassificationTree ct = spp.getModuleBelong();
			 SmlUnitPool sup = spp.getUnit();
			 
			 sppDTO.setCodeBelong(scp.getFirstCode());
			 sppDTO.setDataType(spp.getDataType());
			 sppDTO.setDefaultValue(spp.getDefaultValue());
			 sppDTO.setId(spp.getId());
			 sppDTO.setInformation(spp.getInformation());
			 sppDTO.setMaxValue(spp.getMaxValue());
			 sppDTO.setMinValue(spp.getMinValue());
			 sppDTO.setModuleBelong(ct.getText());
			 sppDTO.setModuleId(ct.getId());
			 sppDTO.setSmlCode(spp.getSmlCode());
			 sppDTO.setSmlCodeId(scp.getId());
			 sppDTO.setSmlName(spp.getSmlName());
			 sppDTO.setUnit(sup.getUnitCode());
			 sppDTO.setUnitId(sup.getId());
			 sppDTOList.add(sppDTO);
		}
		return sppDTOList;
	}
	
	
	public SmlParameterPoolDao getSmlParameterPoolDao() {
		return smlParameterPoolDao;
	}
	@Autowired
	public void setSmlParameterPoolDao(SmlParameterPoolDao smlParameterPoolDao) {
		this.smlParameterPoolDao = smlParameterPoolDao;
	}

}
