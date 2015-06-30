package edu.zju.cims201.GOF.web.bom;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.service.bom.ModuleConfigStatusService;

public class ModuleConfigStatusAction extends ActionSupport implements
		ServletResponseAware {
	private HttpServletResponse response;
	PrintWriter out;
	private long orderId;
	private long platId;
	private long platStructId;
	private ModuleConfigStatusService moduleConfigStatusService;
	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getPlatId() {
		return platId;
	}

	public void setPlatId(long platId) {
		this.platId = platId;
	}
	
	public long getPlatStructId() {
		return platStructId;
	}

	public void setPlatStructId(long platStructId) {
		this.platStructId = platStructId;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;
	}

	public ModuleConfigStatusService getModuleConfigStatusService() {
		return moduleConfigStatusService;
	}

	@Autowired
	public void setModuleConfigStatusService(
			ModuleConfigStatusService moduleConfigStatusService) {
		this.moduleConfigStatusService = moduleConfigStatusService;
	}
	
	public void initConfigStatus() throws IOException{
		String msg =moduleConfigStatusService.initConfigStatus(platId,orderId);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void moduleConfiged() throws IOException{
		String msg =moduleConfigStatusService.moduleConfiged(platStructId,orderId);
		out = response.getWriter();
		out.print(msg);
	}
	
}
