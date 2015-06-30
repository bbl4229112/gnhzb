package edu.zju.cims201.GOF.web.demand;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.DemandValue;
import edu.zju.cims201.GOF.service.demand.DemandValueService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class DemandValueAction extends ActionSupport implements
		ServletResponseAware {
	private HttpServletResponse response;
	PrintWriter out;
	
	private DemandValueService demandValueService;
	
	private long id;
	private long demandId;
	private String demandValueName;
	private String demandValueMemo;
	
	public void addDemandValue() throws IOException{
		String msg =demandValueService.addDemandValue(demandId,demandValueName,demandValueMemo);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void getValueByDemandId() throws IOException{
		List<DemandValue> dvList = demandValueService.getValueByDemandId(demandId);
		String listStr = JSONUtil.write(dvList);
		out = response.getWriter();
		out.print(listStr);
	}
	
	public void deleteDemandValue() throws IOException{
		String msg = demandValueService.deleteDemandValue(id);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void updateDemandValue() throws IOException{
		String msg = demandValueService.updateDemandValue(id,demandId,demandValueName,demandValueMemo);
		out = response.getWriter();
		out.print(msg);
	}
	
	public long getId() {
		return id;
	}

	public long getDemandId() {
		return demandId;
	}

	public String getDemandValueName() {
		return demandValueName;
	}

	public String getDemandValueMemo() {
		return demandValueMemo;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDemandId(long demandId) {
		this.demandId = demandId;
	}

	public void setDemandValueName(String demandValueName) {
		this.demandValueName = demandValueName;
	}

	public void setDemandValueMemo(String demandValueMemo) {
		this.demandValueMemo = demandValueMemo;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;
	}

	public DemandValueService getDemandValueService() {
		return demandValueService;
	}
	@Autowired
	public void setDemandValueService(DemandValueService demandValueService) {
		this.demandValueService = demandValueService;
	}
	
}
