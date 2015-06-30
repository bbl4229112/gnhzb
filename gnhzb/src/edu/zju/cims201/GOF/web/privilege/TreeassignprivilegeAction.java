package edu.zju.cims201.GOF.web.privilege;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;


@Namespace("/privilege")
public class TreeassignprivilegeAction extends ActionSupport implements ServletResponseAware{
	
	private String type;	

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO 自动生成方法存根
		
	}
	
	public String input(){
		return INPUT;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
