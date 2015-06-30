package edu.zju.cims201.GOF.web.namestandard;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.NameStandard;
import edu.zju.cims201.GOF.service.namestandard.NameStandardService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class NameStandardAction extends ActionSupport implements ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9037079230991112355L;
	private NameStandardService nameStandardService;
	private HttpServletResponse response;
	PrintWriter out;
	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}
	
	public NameStandardService getNameStandardService() {
		return nameStandardService;
	}

	@Autowired
	public void setNameStandardService(NameStandardService nameStandardService) {
		this.nameStandardService = nameStandardService;
	}

	
	
	
	public String getAll() throws IOException{
		List<NameStandard> nsList =nameStandardService.getAll();
		String snListStr =JSONUtil.write(nsList);
		out =response.getWriter();
		out.write(snListStr);
		return null;
	}

}
