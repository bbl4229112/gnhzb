package edu.zju.cims201.GOF.service.sml;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.VariantTask;
import edu.zju.cims201.GOF.rs.dto.VariantTaskDTO;


public interface VariantService {
	public String addTask(String task,long partId,String startDate,String endDate,String demo,String requirement);

	public List<VariantTaskDTO> getAllVariantTask();

	public List<VariantTaskDTO> getVariantTaskById(long id);
}
