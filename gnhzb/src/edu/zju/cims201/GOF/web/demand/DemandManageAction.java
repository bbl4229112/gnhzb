package edu.zju.cims201.GOF.web.demand;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.DemandManage;
import edu.zju.cims201.GOF.service.demand.DemandManageService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class DemandManageAction extends ActionSupport implements
		ServletResponseAware {
	private HttpServletResponse response;
	private DemandManageService deamandManageService;
	PrintWriter out;
	
	private long id;
	private String demandName;
	private String demandMemo;
	
	public void getAll() throws IOException{
		List<DemandManage> list = deamandManageService.getAll();
		String listStr = JSONUtil.write(list);
		out =response.getWriter();
		out.print(listStr);
	}
	
	
	public void addDemand() throws IOException{
		String msg = deamandManageService.addDemand(demandName,demandMemo);
		out =response.getWriter();
		out.print(msg);
		
	}
	
	public void deleteDemand() throws IOException{
		String msg = deamandManageService.deleteDemand(id);
		out =response.getWriter();
		out.print(msg);
	}
	
	public void updateDemand() throws IOException{
		String msg =deamandManageService.updateDemand(id,demandName,demandMemo);
		out =response.getWriter();
		out.print(msg);
	}
	
	public long getId() {
		return id;
	}


	public String getDemandName() {
		return demandName;
	}


	public String getDemandMemo() {
		return demandMemo;
	}


	public void setId(long id) {
		this.id = id;
	}


	public void setDemandName(String demandName) {
		this.demandName = demandName;
	}


	public void setDemandMemo(String demandMemo) {
		this.demandMemo = demandMemo;
	}


	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;
	}



	public DemandManageService getDeamandManageService() {
		return deamandManageService;
	}


	@Autowired
	public void setDeamandManageService(DemandManageService deamandManageService) {
		this.deamandManageService = deamandManageService;
	}

}
