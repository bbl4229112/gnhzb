package edu.zju.cims201.GOF.web.sml;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.SmlUnitPool;
import edu.zju.cims201.GOF.service.sml.SmlUnitPoolService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class SmlUnitPoolAction extends ActionSupport implements
		ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6658069958441283159L;
	HttpServletResponse response;
	PrintWriter out;
	
	private SmlUnitPoolService smlUnitPoolService;
	
	
	
	public void getAllSmlUnit() throws IOException{
		List<SmlUnitPool> supList = smlUnitPoolService.getAllSmlUnit();
		String supListStr = JSONUtil.write(supList);
		out =response.getWriter();
		out.print(supListStr);
	}
	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response =arg0;
	}

	public SmlUnitPoolService getSmlUnitPoolService() {
		return smlUnitPoolService;
	}
	@Autowired
	public void setSmlUnitPoolService(SmlUnitPoolService smlUnitPoolService) {
		this.smlUnitPoolService = smlUnitPoolService;
	}

}
