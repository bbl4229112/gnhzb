package edu.zju.cims201.GOF.service.platform;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.part.PartDao;
import edu.zju.cims201.GOF.dao.platform.PlatStructConfiRuleDao;
import edu.zju.cims201.GOF.dao.platform.PlatStructTreeDao;
import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTree;
import edu.zju.cims201.GOF.hibernate.pojoA.Part;
import edu.zju.cims201.GOF.hibernate.pojoA.PlatStructConfiRule;
import edu.zju.cims201.GOF.hibernate.pojoA.PlatStructTree;
import edu.zju.cims201.GOF.hibernate.pojoA.PlatformManage;
import edu.zju.cims201.GOF.rs.dto.PartDTO;
import edu.zju.cims201.GOF.rs.dto.PartRuleDTO;
import edu.zju.cims201.GOF.util.JSONUtil;
@Service
@Transactional
public class PlatStructConfiRuleServiceImpl implements PlatStructConfiRuleService {
	private PartDao partDao;
	private PlatStructConfiRuleDao platStructConfiRuleDao;
	private PlatStructTreeDao platStructTreeDao;
	
	
	public PlatStructTreeDao getPlatStructTreeDao() {
		return platStructTreeDao;
	}
	@Autowired
	public void setPlatStructTreeDao(PlatStructTreeDao platStructTreeDao) {
		this.platStructTreeDao = platStructTreeDao;
	}
	public PlatStructConfiRuleDao getPlatStructConfiRuleDao() {
		return platStructConfiRuleDao;
	}
	@Autowired
	public void setPlatStructConfiRuleDao(
			PlatStructConfiRuleDao platStructConfiRuleDao) {
		this.platStructConfiRuleDao = platStructConfiRuleDao;
	}
	public PartDao getPartDao() {
		return partDao;
	}
	@Autowired
	public void setPartDao(PartDao partDao) {
		this.partDao = partDao;
	}

	public List<PartRuleDTO> getAllRuleByClass(long platStructId, long moduleClassId){
		List<Part> partsList =partDao.find("from Part p where p.classificationTree.id=?",moduleClassId );
		List<PartRuleDTO>  partRuleList = new ArrayList<PartRuleDTO>();
		for(Part p : partsList){
			PartRuleDTO pr = new PartRuleDTO();
			long id =p.getId();
			String partName =p.getPartName();
			String partNumber = p.getPartNumber();
			pr.setId(id);
			pr.setPartname(partName);
			pr.setPartnumber(partNumber);
			StringBuffer and = new StringBuffer("");
			StringBuffer or = new StringBuffer("");
			StringBuffer not = new StringBuffer("");
			List<PlatStructConfiRule> pscrList=platStructConfiRuleDao.find("from PlatStructConfiRule pscr where pscr.platStruct.id=? and pscr.part.id=? and pscr.partSelected.id <> ?", platStructId,p.getId(),p.getId());
			for(PlatStructConfiRule pscr : pscrList){
				Part pSelected =partDao.get(pscr.getPartSelected().getId());
				if(pscr.getStatus()==1){
					and.append("&");
					and.append(pSelected.getPartNumber());
				}
				if(pscr.getStatus()==2){
					or.append("/");
					or.append(pSelected.getPartNumber());
				}
				if(pscr.getStatus()==3){
					not.append("-");
					not.append(pSelected.getPartNumber());
				}
			}
			pr.setAnd(and.toString());
			pr.setOr(or.toString());
			pr.setNot(not.toString());
			partRuleList.add(pr);
		}
		
		return partRuleList;
	}
	/**
	 * 根据平台id获取所有模块所属零部件信息（不包括选中模块）
	 */
	public String getAllPartsByPlatId(long platId, long moduleClassId){
		List<PlatStructTree> pstList = platStructTreeDao.find("from PlatStructTree pst where pst.plat.id=?", platId);
		StringBuffer partsList = new StringBuffer("[");
		for(PlatStructTree pst :pstList){
			long moduleId=0;
			String moduleName ="";
			if(pst.getModuleClass() != null){
				moduleId = pst.getModuleClass().getId();
				moduleName = pst.getModuleClass().getText();
				if(moduleId==moduleClassId ){
					continue;
				}
			}else{
				continue;
			}		
			List<Part> partList = partDao.find("from Part p where p.classificationTree.id = ?", moduleId);
			List<PartDTO> partDTOList = new ArrayList<PartDTO>();
			int partCount = partList.size();
			for(Part p :partList){
				PartDTO pDTO=new PartDTO();
				pDTO.setId(p.getId());
				pDTO.setPartName(p.getPartName());
				pDTO.setPartNumber(p.getPartNumber());
				pDTO.setDescription(p.getDescription());
				partDTOList.add(pDTO);
			}
			String head = "{'partNumber':'类别："+moduleName+"（"+partCount+"条数据）',children:";
			partsList.append(head);
			String children = JSONUtil.write(partDTOList);
			partsList.append(children);
			partsList.append("},");
		}
		partsList.deleteCharAt(partsList.length()-1);
		partsList.append("]");
		
		return partsList.toString();
	}
	
	public List<PartDTO> getSelectedParts(long platStructId,long moduleClassId, long partId, int status){
		List<PartDTO> partList =new ArrayList<PartDTO>();
		List<PlatStructConfiRule> pscrList = platStructConfiRuleDao.find("from PlatStructConfiRule pscr where pscr.platStruct.id=? and pscr.moduleClass.id=? and pscr.part.id=? and pscr.partSelected.id<>? and pscr.status=?", platStructId,moduleClassId,partId,partId,status);
		for(PlatStructConfiRule pscr : pscrList){
			Part partSelected = pscr.getPartSelected();
			PartDTO pDTO = new PartDTO();
			pDTO.setId(partSelected.getId());
			pDTO.setPartNumber(partSelected.getPartNumber());
			pDTO.setPartName(partSelected.getPartName());
			partList.add(pDTO);
		}
		return partList;
	}
	/**
	 * 为模块的零部件添加规则
	 */
	public String addPartRule(long platId, long platStructId,
			long moduleClassId, String classCode, String classText,
			long partId, long partSelectedId, String info, int status){
		List<PlatStructConfiRule> ruleExist = platStructConfiRuleDao.find("from PlatStructConfiRule rule where rule.platStruct.id=? and rule.moduleClass.id=? and rule.part.id=? and rule.partSelected.id=?", platStructId,moduleClassId,partId,partSelectedId);
		if(ruleExist.size()>0){
			return "添加失败，检查是否重复添加";
		}
		
		PlatformManage plat = new PlatformManage();
		plat.setId(platId);
		
		PlatStructTree psTree = new PlatStructTree();
		psTree.setId(platStructId);
		
		ClassificationTree ct = new ClassificationTree();
		ct.setId(moduleClassId);
		
		Part p =new Part();
		p.setId(partId);
		
		Part pSelected = new Part();
		pSelected.setId(partSelectedId);
		
		PlatStructConfiRule rule = new PlatStructConfiRule();
		rule.setClassCode(classCode);
		rule.setClassText(classText);
		rule.setInfo(info);
		rule.setModuleClass(ct);
		rule.setPart(p);
		rule.setPartSelected(pSelected);
		rule.setPlat(plat);
		rule.setPlatStruct(psTree);
		rule.setStatus(status);
		
		platStructConfiRuleDao.save(rule);
		return "添加成功！";
	}
	
	public String deletePartRule(long platStructId, long partId,
			long partSelectedId, int status){
		int count=platStructConfiRuleDao.batchExecute("delete from PlatStructConfiRule rule where rule.platStruct.id=? and rule.part.id=? and rule.partSelected.id=? and rule.status=?", platStructId,partId,partSelectedId,status);
		if(count==1){
			return "删除成功！";
		}
		return "未知错误，请与管理员联系";
	}
}
