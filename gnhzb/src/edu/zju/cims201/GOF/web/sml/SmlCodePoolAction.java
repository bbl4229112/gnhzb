package edu.zju.cims201.GOF.web.sml;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.SmlCodePool;
import edu.zju.cims201.GOF.service.sml.SmlCodePoolService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class SmlCodePoolAction extends ActionSupport implements
		ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8201462305354552047L;
	HttpServletResponse response;
	PrintWriter out;
	
	private SmlCodePoolService smlCodePoolService;
	private long id;
	private String firstCode;
	private String codeName;
	private String information;
	
	public void getAllSmlCode() throws IOException{
		List<SmlCodePool> scp =smlCodePoolService.getAllSmlCode();
		String scpStr =JSONUtil.write(scp);
		out =response.getWriter();
		out.print(scpStr);
	}
	
	public void addSmlCode() throws IOException{
		String msg =smlCodePoolService.addSmlCode(firstCode,codeName,information);
		out = response.getWriter();
		out.print(msg);
	}
	
	public void modifySmlCode() throws IOException{
		String msg =smlCodePoolService.modifySmlCode(id,firstCode,codeName,information);
		out =response.getWriter();
		out.print(msg);
	}
	
	public void deleteSmlCode() throws IOException{
		String msg =smlCodePoolService.deleteSmlCode(id);
		out =response.getWriter();
		out.print(msg);
	}
	

	public SmlCodePoolService getSmlCodePoolService() {
		return smlCodePoolService;
	}

	public long getId() {
		return id;
	}

	public String getFirstCode() {
		return firstCode;
	}

	public String getCodeName() {
		return codeName;
	}

	public String getInformation() {
		return information;
	}
	@Autowired
	public void setSmlCodePoolService(SmlCodePoolService smlCodePoolService) {
		this.smlCodePoolService = smlCodePoolService;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public void setInformation(String information) {
		this.information = information;
	}
	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

}
