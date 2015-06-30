package edu.zju.cims201.GOF.web.interfacedata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.rs.dto.InterfaceModuleDTO;
import edu.zju.cims201.GOF.service.interfacedata.InterfaceModuleService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class InterfaceModuleAction extends ActionSupport implements
		ServletResponseAware {

	private static final long serialVersionUID = 2159157707033401428L;

	private HttpServletResponse response;
	private long id;
	private long modId;
	private long mod2Id;
	private String interfaceNumber;
	private String interfaceRelation;
	private String interfaceName;
	private String interfaceType;
	private InterfaceModuleService interfaceModuleService;
	
	PrintWriter out;
	
	public void addInterfaceModule() throws IOException{
		String msg =interfaceModuleService.addInterfaceModule(modId,mod2Id,interfaceNumber,interfaceRelation,interfaceName,interfaceType);
		out=response.getWriter();
		out.print(msg);
	}
	
	public void getInterfaceModulebyModId() throws IOException{
		List<InterfaceModuleDTO> imsDTO =interfaceModuleService.getInterfaceModulebyModId(modId);
		String msg =JSONUtil.write(imsDTO);
		System.out.println(msg);
		out=response.getWriter();
		out.print(msg);
	}
	
	public void deleteInterfaceModulebyId() throws IOException{
		String msg=interfaceModuleService.deleteInterfaceModulebyId(id);
		out=response.getWriter();
		out.print(msg);
	}
	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

	public long getModId() {
		return modId;
	}

	public String getInterfaceNumber() {
		return interfaceNumber;
	}

	public String getInterfaceRelation() {
		return interfaceRelation;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setModId(long modId) {
		this.modId = modId;
	}

	public void setInterfaceNumber(String interfaceNumber) {
		this.interfaceNumber = interfaceNumber;
	}

	public void setInterfaceRelation(String interfaceRelation) {
		this.interfaceRelation = interfaceRelation;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public InterfaceModuleService getInterfaceModuleService() {
		return interfaceModuleService;
	}
	@Autowired
	public void setInterfaceModuleService(
			InterfaceModuleService interfaceModuleService) {
		this.interfaceModuleService = interfaceModuleService;
	}

	public long getMod2Id() {
		return mod2Id;
	}

	public void setMod2Id(long mod2Id) {
		this.mod2Id = mod2Id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
