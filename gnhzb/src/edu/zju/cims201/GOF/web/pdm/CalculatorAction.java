package edu.zju.cims201.GOF.web.pdm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.stringtree.json.JSONWriter;


import edu.zju.cims201.GOF.hibernate.pojo.Material;
import edu.zju.cims201.GOF.hibernate.pojo.MaterialEmission;
import edu.zju.cims201.GOF.service.module.MaterialService;
import edu.zju.cims201.GOF.web.CrudActionSupport;


@Namespace("/lca")
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "calculator.action", type = "redirect"),
@Result(name = "showChart", location = "/WEB-INF/content/lca/resultChart.jsp") })
public class CalculatorAction extends CrudActionSupport<Material> implements ServletResponseAware, ServletRequestAware{
	
	private static final long serialVersionUID = 8683878162525847072L;
	
	@Resource(name="materialServiceImpl")
	private MaterialService materialService;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private String stage;
	private String cId;
	private String id;
	private String name;
	private String description;
	private String data;
	private String componentId;
	private String materialId;
	private String listname;
	private String listvalue;
	
	//获取阶段名称获取材料
	public String getMaterialByStage() throws IOException{
		int stage_ = Integer.parseInt(stage);
		int id = Integer.parseInt(cId);
		List mlist = materialService.getMaterialByStage(stage_, id, 1);
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(mlist);
        response.getWriter().println(ktypestring);
//		JSONArray ja = JSONArray.fromObject(materialService.getMaterialByStage(stage_, id, 1));//systemuser暂定1
//		
//		response.getWriter().println(ja.toString());
		return null;
	}
	
	//新增材料
	public String addMaterial() throws IOException{
		int stage_ = Integer.parseInt(stage);
	
		
		Material m = new Material();
		m.setDescription(description);
		m.setName(name);
		m.setStage(stage_);
		
		m = materialService.addMaterial(m);
		
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(m);
        response.getWriter().println(ktypestring);
		
//		JSONObject jo = JSONObject.fromObject(m);
//		response.getWriter().println(jo.toString());
		return null;
	} 
	
	//更新材料
	public String updateMaterial() throws IOException{
		int id_ = Integer.parseInt(id);
		
		Material m = materialService.getMaterial(id_);
		m.setDescription(description);
		m.setName(name);

		m = materialService.updateMaterial(m);
		
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(m);
        response.getWriter().println(ktypestring);
//		JSONObject jo = JSONObject.fromObject(m);
//		response.getWriter().println(jo.toString());
		return null;
	} 
	
	//删除材料
	public String deleteMaterial(){
		int id_ = Integer.parseInt(id);

		materialService.deleteMaterial(id_);
		return null;
	} 
	
	//保存碳排放数�?
	public String saveCarbonEmission(){
		JSONArray ja = JSONArray.fromObject(data);
		List<MaterialEmission> meList = new ArrayList();
		for(int i=0;i<ja.size();i++){
			JSONObject jo = ja.getJSONObject(i);
			MaterialEmission me = new MaterialEmission();
			me.setComponentId(jo.getInt("componentId"));
			me.setEmission(Double.parseDouble(jo.getString("emission")));
			me.setMaterialId(jo.getInt("materialId"));
			me.setSum(Double.parseDouble(jo.getString("sum")));
//			me.setUserId(((SystemUser)request.getSession().getAttribute("user")).getId());
			meList.add(me);
		}
		materialService.saveCarbonEmission(meList);
		return null;
	} 
	
	//碳排放的统计结果
	public String carbonEmissionStatistics() throws IOException{
		int componentId_ = Integer.parseInt(componentId);
		int materialId_ = Integer.parseInt(materialId);
		List stList = materialService.carbonEmissionStatistic(componentId_, materialId_,1);//userid
		int sum = 0;
		for(int i=0; i<stList.size(); i++){
			Object[] st = (Object[])stList.get(i);
			sum += (Long)st[1];
		}
		
		for(int i=0; i<stList.size(); i++){
			Object[] st = (Object[])stList.get(i);
			double ddd = Double.valueOf(String.valueOf(st[1]))/sum;
			st[1] = ddd*100;
			
		}
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(stList);
        response.getWriter().println(ktypestring);
		
//		JSONArray ja = JSONArray.fromObject(stList);
//		response.getWriter().println(ja.toString());
		return null;
	}

	//返回统计分析结果图表�?
	public String showChart(){
		//指标�?
		List<String> listname = new ArrayList<String>();
		listname.add("PHASE1");
		listname.add("PHASE2");
		listname.add("PHASE3");
		listname.add("PHASE4");
		listname.add("PHASE5");
		listname.add("PHASE6");
		
		//指标�?
		List<Float> listvalue = new ArrayList<Float>();
		if(!"".equals(data)){			
			String[] arr = data.split(",");
			for(int i=0;i<arr.length;i++){			
				listvalue.add(new Float(arr[i]));
			}

		}else{
			for(int i=0;i<6;i++){			
				listvalue.add((float) 0);
			}
		}
		request.setAttribute("listvalue", listvalue);
		request.setAttribute("listname", listname);
		
	
		return "showChart";


	}

	public Material getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
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

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}

	public String getListvalue() {
		return listvalue;
	}

	public void setListvalue(String listvalue) {
		this.listvalue = listvalue;
	}
	
}
