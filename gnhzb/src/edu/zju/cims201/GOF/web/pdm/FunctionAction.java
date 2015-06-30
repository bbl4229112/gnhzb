package edu.zju.cims201.GOF.web.pdm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.codehaus.jackson.map.ObjectMapper;

import edu.zju.cims201.GOF.web.CrudActionSupport;
import edu.zju.cims201.GOF.hibernate.pojo.Function;
import edu.zju.cims201.GOF.service.module.ModuleService;


@Namespace("/function")
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "function.action", type = "redirect")})
public class FunctionAction extends CrudActionSupport<Function> implements ServletResponseAware, ServletRequestAware {
private static final long serialVersionUID = 8683878162525847072L;
	
	@Resource(name="moduleServiceImpl")
	private ModuleService moduleService;
	
	
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	


	public String getfunctiondatartee() throws IOException{
		List flist =null;
		flist=moduleService.getfunctionlist();
		List<Map<String, Object>> fulist = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();	
		 if (flist!=null){
		for(int i=0;i<flist.size();i++){
			Function f=(Function)flist.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", f.getId());
 		    rootMap.put("name", f.getName());
		    rootMap.put("type", f.getTypee());
			rootMap.put("children", "");
			fulist.add(rootMap);		
			
		}
		objectMapper.writeValue(response.getWriter(), fulist);
		}
		return null;
	}
	public Function getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
		
	}
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
		
	}
	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}


}
