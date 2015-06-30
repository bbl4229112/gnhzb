package edu.zju.cims201.GOF.web.pdm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.codehaus.jackson.map.ObjectMapper;
import org.stringtree.json.JSONWriter;

import edu.zju.cims201.GOF.web.CrudActionSupport;
import edu.zju.cims201.GOF.hibernate.pojo.BaseModule;
import edu.zju.cims201.GOF.hibernate.pojo.Function;
import edu.zju.cims201.GOF.hibernate.pojo.Ioflow;
import edu.zju.cims201.GOF.hibernate.pojo.Material;
import edu.zju.cims201.GOF.hibernate.pojo.Node;
import edu.zju.cims201.GOF.hibernate.pojo.Nodecategory;
import edu.zju.cims201.GOF.hibernate.pojo.Nodetype;
import edu.zju.cims201.GOF.service.module.ModuleService;
import edu.zju.cims201.GOF.util.JSONUtil;


@Namespace("/node")
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "welcome.action", type = "redirect"),
		 @Result(name = "draw", location = "/WEB-INF/content/lca/filelookedu.zju.cims201.GOF2.jsp"),
		 @Result(name = "modulelook", location = "/WEB-INF/content/lca/modulelook.jsp"),
		 @Result(name = "nodemanage", location = "/WEB-INF/content/lca/nodemanage.jsp")})
public class NodeAction extends CrudActionSupport<Material> implements ServletResponseAware, ServletRequestAware {
private static final long serialVersionUID = 8683878162525847072L;
	
	@Resource(name="moduleServiceImpl")
	private ModuleService moduleService;
	
	
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private String parentId;
	private String name;;
	private String description;
	private String level;
	private String cId;
	private String data;
	private String projectid;
	private String processid;
	private String pdid;
	private String moduleid;
	private String nodename;
	private String nodetype;
	private String nodedrawtype;
	private String label;
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	private String pdname;
	private String mdname;
	private String processname;
	private String xmldata;
	private String mdnote;
	private String componentid;
	private String version;
	private String nodecategory;
	

	public String getComponentid() {
		return componentid;
	}
	public void setComponentid(String componentid) {
		this.componentid = componentid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	private String id;
	
	
	
	PrintWriter out;
	/*public String saveXml() throws Exception{
		//System.out.println("ss");
		String uuid = UUID.randomUUID().toString();
		byte[] xmldata =data.getBytes("UTF-8");
		BaseModule module=moduleService.getModule(componentid, null, version);
		BaseModule parent=module.getParent();
		String path=parent.getModuledir();
		System.out.println(parent.getModuledir());
		String xmlFileName=path+"//"+uuid+".xml";
		File xmlData = new File(xmlFileName);
		FileOutputStream outputStream = new FileOutputStream(xmlData);
		outputStream.write(xmldata);
	    module.setModuledir(path);
	    module.setModuleUUID(uuid);
	    module.setXmlFileName(uuid+".xml");
	    BaseModule m =moduleService.addModule(module);
	    if(m==null){
	    	System.out.println("失败");
	    }else{
	    	System.out.println("成功");
	    }
		  return null;
	  }*/
	public String getioflow() throws IOException {
		List<Ioflow> iolist = null;
			iolist =moduleService.getioflow(false,null, null,null,null);
			
			
			
//			JSONArray ja = JSONArray.fromObject(componentService.getNodesByParent(0));
//			response.getWriter().println(ja.toString());
			
		JSONWriter writer = new JSONWriter();
        String ioString=writer.write(iolist);
        response.getWriter().println(ioString);

		return null;
		
	}
	public String getmodelflow() throws IOException {
		List<Ioflow> iolist = null;
		if(componentid==null){
			iolist =moduleService.getioflow(true, processid,null,null,moduleid);
			
		}else{
		
			iolist =moduleService.getioflow(true, processid,componentid,version,null);
		}
			
			
			
//			JSONArray ja = JSONArray.fromObject(componentService.getNodesByParent(0));
//			response.getWriter().println(ja.toString());
			
		JSONWriter writer = new JSONWriter();
        String ioString=writer.write(iolist);
        response.getWriter().println(ioString);

		return null;
		
	}
	/*public String addversion() throws IOException {
		String uuid = UUID.randomUUID().toString();
		String path="D:\\module\\";
		File moduledir=new File(path+uuid);
		if(!moduledir.exists()){
			moduledir.mkdirs();
    	}
		List<BaseModule> modules=moduleService.getVersion(Integer.valueOf(pdid));
		BaseModule m=new BaseModule();
		Component component=componentService.getComponent(Integer.valueOf(pdid));
		m.setComponent(component);
		m.setName(mdname);
		m.setNote(mdnote);
		m.setCreatedate(new Date());
		m.setParent(null);
		m.setModuledir(path+uuid);
		int result=moduleService.addModule(m).getVersion();
		response.getWriter().println(result);
		return null;
		
	}*/
	public String nodemanage(){
		return "nodemanage";
	}
	/*public String adddata() throws IOException {
	   List<HashMap> datas =getJSONvalue();
	   int n=datas.size();
	   System.out.println(componentid+pdid+version);
	   BaseModule module =moduleService.getModule(componentid,pdid,version);
	   if (module==null){
		   BaseModule m =new BaseModule();
		   BaseModule parent =moduleService.getModule(null,pdid,version);
		   Component component=componentService.getComponent(Integer.valueOf(componentid));
		   m.setComponent(component);
		   m.setName(parent.getName());
		   m.setNote(parent.getNote());
		   m.setCreatedate(new Date());
		   m.setParent(parent);
		   m.setVersion(parent.getVersion());
		   moduleService.addModule(m);
		   BaseModule m2=moduleService.addModule(m);
		   ProcessTemplate process=new ProcessTemplate();
		   process.setProcessid(Integer.valueOf(processid));
		   process.setModule(m2);
		   process.setName(processname);
		   moduleService.addProcess(process);
		   ProcessTemplate p=moduleService.getprocess(processid, m2.getId());
		   for(int i=0;i<n;i++){
			   Modelflow a=new Modelflow();
			   HashMap h=datas.get(i);
			   String id=String.valueOf(h.get("id"));
			   Ioflow ioflow=moduleService.getioflow(id);
			   a.setFlow(ioflow);
			   a.setType("input");
			   a.setProcess(p);
			   moduleService.saveModelflow(a);
		   }
	   }else{
		   ProcessTemplate process=new ProcessTemplate();
		   process.setProcessid(Integer.valueOf(processid));
		   process.setModule(module);
		   process.setName(processname);
		   moduleService.addProcess(process);
		   ProcessTemplate p=moduleService.getprocess(processid, module.getId());
		   for(int i=0;i<n;i++){
			   Modelflow a=new Modelflow();
			   HashMap h=datas.get(i);
			   String id=String.valueOf(h.get("id"));
			   Ioflow ioflow=moduleService.getioflow(id);
			   a.setFlow(ioflow);
			   a.setType("input");
			   a.setProcess(p);
			   moduleService.saveModelflow(a);
		   }
		   
	   }

		return null;
		
	}*/
	public String getnodelist() throws Exception{
		List<Nodecategory> list=moduleService.getnodecategorylist();
		ObjectMapper objectMapper = new ObjectMapper();	
	    objectMapper.writeValue(response.getWriter(), list);
	    return null;
	}
	public String getnodeypetlist() throws Exception{
		List<Nodetype> list=moduleService.getnodetypelist();
		ObjectMapper objectMapper = new ObjectMapper();	
	    objectMapper.writeValue(response.getWriter(), list);
	    return null;
	}
	public String getNodeListByType() throws Exception{
		List<Node> nList=moduleService.getNodeListByType(nodetype,nodecategory);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();	
		for(Node n:nList){
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", n.getId());
 		    rootMap.put("name", n.getName());
 		    rootMap.put("img", n.getImg());
 		    rootMap.put("label", n.getLabel());
 		    rootMap.put("nodedrawtype", n.getNodedrawtype());
 		    rootMap.put("nodedrawtype", n.getNodedrawtype());
			list.add(rootMap);		
	}
		objectMapper.writeValue(response.getWriter(), list);
		return null;
	}
	public String getNodeCategory() throws Exception{
		List<Nodecategory> ncList=moduleService.getnodecategorylist();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();	
		for(Nodecategory n:ncList){
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", n.getId());
 		    rootMap.put("name", n.getName());
			list.add(rootMap);		
	}
		objectMapper.writeValue(response.getWriter(), list);
		return null;
	}
	
	
	/*//获得pdm类型的node，
	public String getnodelist() throws Exception{
		List<Node> nList=moduleService.getnodelist();
		List<Nodetype> ntList=moduleService.getnodetypelist();
		List<Nodecategory> nclist=moduleService.getnodecategorylist();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();	
		for(Nodecategory nc:nclist){
			    Map<String, Object> rootMap = new HashMap<String, Object>();
			    rootMap.put("id", nc.getId());
	 		    rootMap.put("name", nc.getName());
				rootMap.put("children", getnodecategorygroup(nc.getName(), nList,ntList));
				list.add(rootMap);		
		}
//		for(Node n:nList){
//			System.out.println(n.getNodetype().getName());
//		}
		objectMapper.writeValue(response.getWriter(), list);
		return null;
	}
	private List<Map<String, Object>> getnodecategorygroup(String name, List<Node> nList,List<Nodetype> ntList) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(Nodetype nt:ntList){
			    Map<String, Object> rootMap = new HashMap<String, Object>();
			    rootMap.put("id", nt.getId());
	 		    rootMap.put("name", nt.getName());
	 		    rootMap.put("img", nt.getName());
	 		    rootMap.put("nodecategory", nt.getName());
				rootMap.put("children", getnodetypegroup(nt.getName(),nList));
				list.add(rootMap);		
		}
		return list;
	}*/
	private List<Map<String, Object>> getnodetypegroup(String name, List<Node> nList) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int max=nList.size();
		for(int i=max-1;i>=0;i--){
			Node n=nList.get(i);
			if(n.getNodetype().getName()==name){
			    Map<String, Object> rootMap = new HashMap<String, Object>();
			    rootMap.put("id", n.getId());
	 		    rootMap.put("name", n.getName());
				list.add(rootMap);	
				nList.remove(i);
			}
		}
		return list;
	}
    
	public String addnode() throws IOException {
		   List<HashMap> functionlist =getJSONvalue();
		   int n=functionlist.size();
		   Node node=new Node();
		   Nodetype nt=new Nodetype();
		   nt.setId(Integer.valueOf(nodetype));
		   node.setNodetype(nt);
		   Nodecategory nc=new Nodecategory();
		   nc.setId(Integer.valueOf(nodecategory));
		   node.setNodecategory(nc);
		   node.setName(nodename);
		   node.setNodedrawtype(nodedrawtype);
		   node.setDescription(description);
		   node.setLabel(label);
		   Set<Function> functions=new HashSet<Function>();
				  
			   for(int i=0;i<n;i++){
				   Function f=new Function();
				   HashMap h=functionlist.get(i);
				   String id=String.valueOf(h.get("id"));
				   f.setId(Integer.valueOf(id));
				   functions.add(f);
			   }
		   node.setFunctions(functions);
		   moduleService.addnode(node);
            
			return null;
			
		}
	
	public List<HashMap> getJSONvalue()
	{
		//JSONUtil jr  = new JSONUtil();
		List<HashMap> datas ;
		try{
			datas =  (List)JSONUtil.read(data);}
		catch(Exception e ){
			System.out.println("jason解析错误");
			e.printStackTrace();
			return null;
		}
		return datas;
	}
	public String getdraw(){
			
			return "draw";
		}
	public String getmodulelook(){
		request.setAttribute("moduleid", moduleid);
		return "modulelook";
	}
	
	
	public void downloadXML() throws IOException {
		BaseModule m=moduleService.getModule(moduleid);
		String filename = m.getModuledir()
				+ File.separator + m.getXmlFileName();
		File file = new File(filename);
		response.reset();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");

		response.setDateHeader("Expires", 0);
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=\""
				+ new String(filename.getBytes(), "ISO_8859_1") + "\"");
		response.setContentLength((int) file.length());

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file));
		BufferedOutputStream bos = new BufferedOutputStream(
				response.getOutputStream());

		Streams.copy(bis, bos, true);

	}

	
	//根据名字查询对应的模�?
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


	public Material getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
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

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getParentId() {
		return parentId;
	}

	public String getPdid() {
		return pdid;
	}
	public void setPdid(String pdid) {
		this.pdid = pdid;
	}
	public String getPdname() {
		return pdname;
	}
	public void setPdname(String pdname) {
		this.pdname = pdname;
	}
	public ModuleService getModuleService() {
		return moduleService;
	}
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getMdname() {
		return mdname;
	}
	public void setMdname(String mdname) {
		this.mdname = mdname;
	}
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	public String getMdnote() {
		return mdnote;
	}
	public void setMdnote(String mdnote) {
		this.mdnote = mdnote;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getProcessid() {
		return processid;
	}
	public void setProcessid(String processid) {
		this.processid = processid;
	}
	public String getProcessname() {
		return processname;
	}
	public void setProcessname(String processname) {
		this.processname = processname;
	}
	public String getXmldata() {
		return xmldata;
	}
	public void setXmldata(String xmldata) {
		this.xmldata = xmldata;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;

	}
	public String getNodetype() {
		return nodetype;
	}
	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}
	public String getNodecategory() {
		return nodecategory;
	}
	public void setNodecategory(String nodecategory) {
		this.nodecategory = nodecategory;
	}
	public String getNodedrawtype() {
		return nodedrawtype;
	}
	public void setNodedrawtype(String nodedrawtype) {
		this.nodedrawtype = nodedrawtype;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
