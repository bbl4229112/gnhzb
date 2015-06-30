package edu.zju.cims201.GOF.web.interfacedata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.rs.dto.InterfaceDataDTO;
import edu.zju.cims201.GOF.service.interfacedata.InterfaceDataService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class InterfaceDataAction extends ActionSupport implements ServletResponseAware{
	private static final long serialVersionUID = -472593912504425662L;
	private HttpServletResponse response;
	private InterfaceDataService interfaceDataService;
	//InterfaceData对象的id
	private long id;
	//模块接口的Id
	private long interfaceModuleId;
	private long interfaceInstanceId;
	private String interfaceInstance2Id;
	private String interfaceType;
	private String interfaceElement;
	private String interfaceParams;
	private String interfaceNumber;
	
	PrintWriter out;
	
	public void getInterfaceDataByInterfaceModId() throws IOException,HibernateException{
		List<InterfaceDataDTO> idDTOs =interfaceDataService.getInterfaceDataByInterfaceModId(interfaceModuleId);
		String idDTOsStr =JSONUtil.write(idDTOs);
		out = response.getWriter();
		out.print(idDTOsStr);
	}
	
	public void addInterfaceData() throws IOException{
		String msg = interfaceDataService.addInterfaceData(interfaceModuleId,interfaceInstanceId,interfaceInstance2Id,interfaceType,interfaceElement,interfaceParams,interfaceNumber);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void deleteInterfaceData() throws IOException{
		String msg=interfaceDataService.deleteInterfaceData(id);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}
	public InterfaceDataService getInterfaceDataService() {
		return interfaceDataService;
	}

	public long getInterfaceInstanceId() {
		return interfaceInstanceId;
	}
	public String getInterfaceInstance2Id() {
		return interfaceInstance2Id;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public String getInterfaceElement() {
		return interfaceElement;
	}
	public String getInterfaceParams() {
		return interfaceParams;
	}
	public String getInterfaceNumber() {
		return interfaceNumber;
	}
	@Autowired
	public void setInterfaceDataService(InterfaceDataService interfaceDataService) {
		this.interfaceDataService = interfaceDataService;
	}

	public void setInterfaceInstanceId(long interfaceInstanceId) {
		this.interfaceInstanceId = interfaceInstanceId;
	}
	public void setInterfaceInstance2Id(String interfaceInstance2Id) {
		this.interfaceInstance2Id = interfaceInstance2Id;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public void setInterfaceElement(String interfaceElement) {
		this.interfaceElement = interfaceElement;
	}
	public void setInterfaceParams(String interfaceParams) {
		this.interfaceParams = interfaceParams;
	}
	public void setInterfaceNumber(String interfaceNumber) {
		this.interfaceNumber = interfaceNumber;
	}

	public long getInterfaceModuleId() {
		return interfaceModuleId;
	}

	public void setInterfaceModuleId(long interfaceModuleId) {
		this.interfaceModuleId = interfaceModuleId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

}
