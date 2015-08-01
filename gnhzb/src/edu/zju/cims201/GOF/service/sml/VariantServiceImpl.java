package edu.zju.cims201.GOF.service.sml;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.sml.VariantTaskDao;
import edu.zju.cims201.GOF.hibernate.pojoA.Part;
import edu.zju.cims201.GOF.hibernate.pojoA.VariantTask;
import edu.zju.cims201.GOF.rs.dto.VariantTaskDTO;
import edu.zju.cims201.GOF.util.JSONUtil;

@Service
@Transactional
public class VariantServiceImpl implements VariantService{
	private Log log = LogFactory.getLog(this.getClass());
	private VariantTaskDao  variantTaskDao;
	public String addTask(String taskName,long partId,String startDate,String endDate,String demo,String requirement) {
		// TODO Auto-generated method stub
		VariantTask vt = new VariantTask();
		vt.setTaskName(taskName);
		Part part = new Part();
		part.setId(partId);
		vt.setPart(part);
		
		ParsePosition p =null;
		SimpleDateFormat format = new SimpleDateFormat("\"yyyy-MM-dd'T'HH:mm:ss\"");
		Date start = null;
		Date end =null;
		
		try {
			start = format.parse(startDate);
			end = format.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error("addTask,日期格式转换错误");
			e.printStackTrace();
		}
		vt.setStartDate(start);
		vt.setEndDate(end);
		
		vt.setDemo(demo);
		vt.setRequirement(requirement);
		vt.setStatus("未完成");
		variantTaskDao.save(vt);
		log.info("addTask("+partId+","+endDate+","+demo+","+requirement+")：新建变型任务成功" );
		return "新建变型任务成功！";
	}
	
	public List<VariantTaskDTO> getAllVariantTask(){
		List<VariantTask> list = variantTaskDao.getAll();
		List<VariantTaskDTO> list2 = new ArrayList<VariantTaskDTO>();
		int length = list.size();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<length;i++){
			VariantTask vt = list.get(i);
			VariantTaskDTO vt2 = new VariantTaskDTO();
			vt2.setDemo(vt.getDemo());
			String endDate = sdf.format(vt.getEndDate());
			vt2.setEndDate(endDate);
			vt2.setId(vt.getId());
			vt2.setPartId(vt.getPart().getId());
			vt2.setPartName(vt.getPart().getPartName());
			vt2.setPartNumber(vt.getPart().getPartNumber());
			vt2.setRequirement(vt.getRequirement());
			String startDate = sdf.format(vt.getStartDate());
			vt2.setStartDate(startDate);
			vt2.setStatus(vt.getStatus());
			vt2.setTaskName(vt.getTaskName());
			list2.add(vt2);
			
		}
		log.info("getAllVariantTask():"+JSONUtil.write(list2));
		return list2;
	}
	
	@Autowired
	public void setVariantTaskDao(VariantTaskDao variantTaskDao) {
		this.variantTaskDao = variantTaskDao;
	}

	
}
