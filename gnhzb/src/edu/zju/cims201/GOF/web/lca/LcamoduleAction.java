package edu.zju.cims201.GOF.web.lca;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.util.Streams;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.codehaus.jackson.map.ObjectMapper;
import org.stringtree.json.JSONWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.hp.hpl.jena.graph.NodeCache;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.sun.corba.se.spi.copyobject.CopierManager;



import edu.zju.cims201.GOF.hibernate.pojo.Component;
import edu.zju.cims201.GOF.hibernate.pojo.Function;
import edu.zju.cims201.GOF.hibernate.pojo.Ioflow;
import edu.zju.cims201.GOF.hibernate.pojo.LcaModule;
import edu.zju.cims201.GOF.hibernate.pojo.LcaProcessTemplate;
import edu.zju.cims201.GOF.hibernate.pojo.Material;
import edu.zju.cims201.GOF.hibernate.pojo.Modelflow;
import edu.zju.cims201.GOF.hibernate.pojo.BaseModule;
import edu.zju.cims201.GOF.hibernate.pojo.Node;
import edu.zju.cims201.GOF.hibernate.pojo.Nodecategory;
import edu.zju.cims201.GOF.hibernate.pojo.Nodetype;
import edu.zju.cims201.GOF.hibernate.pojo.PdmModule;
import edu.zju.cims201.GOF.hibernate.pojo.ProcessTemplate;
import edu.zju.cims201.GOF.hibernate.pojo.ProcessUrl;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.Employee;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmProject;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.LcaTask;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.TaskTreeNode;
import edu.zju.cims201.GOF.service.lca.ComponentService;
import edu.zju.cims201.GOF.service.module.ModuleService;
import edu.zju.cims201.GOF.service.task.TaskService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;


@Namespace("/lca")
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "lcamodule.action", type = "redirect"),
		 @Result(name = "draw", location = "/WEB-INF/content/lca/filelookedu.zju.cims201.GOF2.jsp"),
		 @Result(name = "modulelook", location = "/WEB-INF/content/lca/modulelook.jsp"),
		 @Result(name = "nodemanage", location = "/WEB-INF/content/lca/nodemanage.jsp")})
public class LcamoduleAction extends CrudActionSupport<Material> implements ServletResponseAware, ServletRequestAware {
private static final long serialVersionUID = 8683878162525847072L;
	
	@Resource(name="componentServiceImpl")
	private ComponentService componentService;
	@Resource(name="moduleServiceImpl")
	private ModuleService moduleService;
	@Resource(name = "taskServiceImpl")
	private TaskService taskService;
	
	
	
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
	private String pdname;
	private String mdname;
	private String processname;
	private String xmldata;
	private String mdnote;
	private String componentid;
	private String components;
	private String version;
	private String nodecategory;
	private String modulename;
	private String modulenote;
	private String cellcollection;
	private String moduletype;
	private List<File> file;// 对应前面的name
	private List<String> fileFileName;
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
	
	public String fileupload(){
		System.out.println("开始上传");
		for(int i = 0;i<file.size();i++){
			System.out.println(file.get(i));
			int index = fileFileName.get(i).lastIndexOf('.');
			String newFileName = fileFileName.get(i).substring(index);// 生成新文件名
			System.out.println(newFileName);
			System.out.println(fileFileName);
			
		}
		/*try {

			String msg = "{success:true,msg:'" + reInfo + "',filename:'"
					+ fileoralname + "',filepath:'" + newFileName + "'}";

			response.getWriter().write(msg);

		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return null;
	}
	//获取所有的模块
	public String getComponentsList() throws IOException {
		List clist = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();	
		if("0".equals(parentId) || "null".equals(parentId) || parentId == null){
			clist =componentService.getNodesByParent(Integer.parseInt(id));
			Component parent=componentService.getComponent(Integer.valueOf(id));
			 Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", parent.getId());
 		    rootMap.put("name", parent.getName());
 		    rootMap.put("expanded", true);
 		    rootMap.put("__viewicon", true);
			for(int i=0;i<clist.size();i++){
				Component c=(Component)clist.get(i);
			    Map<String, Object> childmap = new HashMap<String, Object>();
			    childmap.put("id", c.getId());
			    childmap.put("name", c.getName());
			    childmap.put("expanded", false);
			    childmap.put("__viewicon", true);
			    childlist.add(childmap);		
				
			}
		
			rootMap.put("children", childlist);
			list.add(rootMap);
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(list);
        response.getWriter().println(ktypestring);
		return null;
			
		}else{
			clist =componentService.getNodesByParent(Integer.parseInt(parentId));
		    for(int i=0;i<clist.size();i++){
				Component c=(Component)clist.get(i);
			    Map<String, Object> childmap = new HashMap<String, Object>();
			    childmap.put("id", c.getId());
			    childmap.put("name", c.getName());
			    childmap.put("expanded", false);
			    childmap.put("__viewicon", true);
			    childlist.add(childmap);		
				
			}
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(childlist);
        response.getWriter().println(ktypestring);
		return null;
		
		
	}
	}
	public String getModuletreebytype() throws IOException{
		List mlist =null;
		mlist=moduleService.getModulelist(null,null,moduletype);
		List<Map<String, Object>> mtlist = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();	
		 if (!(mlist==null)){
		for(int i=0;i<mlist.size();i++){
			LcaModule m=(LcaModule)mlist.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", m.getId());
 		    rootMap.put("Moduledir", m.getModuledir());
		    rootMap.put("Createuserid", m.getCreateuserid());
		    rootMap.put("Createdate", m.getCreatedate().toString());
		    rootMap.put("ModuleUUID", m.getModuleUUID());
		    rootMap.put("Name", m.getName());
		    rootMap.put("Note", m.getNote());
		    rootMap.put("XmlFileName", m.getXmlFileName());
		    rootMap.put("Version", m.getVersion());
			rootMap.put("children", "");
			mtlist.add(rootMap);		
			
		}
		objectMapper.writeValue(response.getWriter(), mtlist);
		}
		return null;
	}
	public String getmoduleComponentsList() throws IOException {
		List clist = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();	
		if("0".equals(parentId) || "null".equals(parentId) || parentId == null){
			String ids="(";
			Map<String, Object> rootMap = new HashMap<String, Object>();
			Component parent=componentService.getComponent(Integer.valueOf(id));
			LcaModule parentModule=(LcaModule)moduleService.getModule(moduleid);
			Set<LcaModule> lcaModules=parentModule.getLcaModules();
			Iterator it=lcaModules.iterator();
		    rootMap.put("id", parent.getId());
		    rootMap.put("name", parent.getName());
		    rootMap.put("moduleid", moduleid);
		    rootMap.put("Version", parentModule.getVersion());
		    rootMap.put("expanded", true);
		    rootMap.put("__viewicon", true);
		    
			while(it.hasNext()){
				LcaModule m=(LcaModule)it.next();
				Component c=m.getComponent();
				if(c.getParentId()==0){
					Map<String, Object> childmap = new HashMap<String, Object>();
					childmap.put("id", m.getComponent().getId());
				    childmap.put("compname", m.getComponent().getName());
				    childmap.put("Version", parentModule.getVersion());
				    childmap.put("Createdate", m.getCreatedate().toString());
				    childmap.put("Note", m.getNote());
				    childmap.put("name",  m.getComponent().getName()+"已建模");
				    childmap.put("moduleid", m.getId());
				    childmap.put("expanded", false);
				    childmap.put("__viewicon", true);
				    childlist.add(childmap);
				}
				
			}
			if(childlist.size()==0){
				 Map<String, Object> childmap = new HashMap<String, Object>();
				    childmap.put("id", parent.getId());
				    childmap.put("name", parent.getName());
				    childmap.put("moduleid", null);
				    childmap.put("expanded", false);
				    childmap.put("__viewicon", true);
				    childlist.add(childmap);		
				}
				
			rootMap.put("children", childlist);
			list.add(rootMap);
			JSONWriter writer = new JSONWriter();
	        String ktypestring=writer.write(list);
	        response.getWriter().println(ktypestring);
			return null;
			
		}else{
				String ids="(";
				LcaModule parentmModule=(LcaModule)moduleService.getModule(moduleid);
				Set<LcaModule> lcaModules=parentmModule.getLcaModules();
				Iterator it=lcaModules.iterator();
				clist =componentService.getnomodulenodesByParent(Integer.parseInt(parentId),"()");
				if(clist==null||clist.size()==0){
					response.getWriter().println("[]");
					return null;
				}
				while(it.hasNext()){
					LcaModule m=(LcaModule)it.next();
					for(int i=0;i<clist.size();i++){
						Component c=(Component)clist.get(i);
						if(m.getComponent().getId()==c.getId()){
							ids=ids+m.getComponent().getId()+",";
							 Map<String, Object> childmap = new HashMap<String, Object>();
							    childmap.put("id", m.getComponent().getId());
							    childmap.put("name",  m.getComponent().getName()+"已建模");
							    childmap.put("Version", m.getVersion());
							    childmap.put("Createdate", m.getCreatedate().toString());
							    childmap.put("Note", m.getNote());
							    childmap.put("moduleid", m.getId());
							    childmap.put("expanded", false);
							    childmap.put("__viewicon", true);
							    childlist.add(childmap);
							    ids=ids+m.getComponent().getId()+",";
						}
					}
					/*LcaModule m=(LcaModule)it.next();
					ids=ids+m.getComponent().getId()+",";
					 Map<String, Object> childmap = new HashMap<String, Object>();
					    childmap.put("id", m.getComponent().getId());
					    childmap.put("name",  m.getComponent().getName()+"已建模");
					    childmap.put("moduleid", m.getId());
					    childmap.put("expanded", false);
					    childmap.put("__viewicon", true);
					    childlist.add(childmap);	*/	
				}
				if(childlist.size()>0){
				ids=ids.substring(0, ids.length()-1);
				System.out.println(ids);
				}
			ids+=")";
			clist =componentService.getnomodulenodesByParent(Integer.parseInt(parentId),ids);
		    for(int i=0;i<clist.size();i++){
				Component c=(Component)clist.get(i);
			    Map<String, Object> childmap = new HashMap<String, Object>();
			    childmap.put("id", c.getId());
			    childmap.put("name", c.getName());
			    childmap.put("moduleid", null);
			    childmap.put("expanded", false);
			    childmap.put("__viewicon", true);
			    childlist.add(childmap);		
				
			}
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(childlist);
        response.getWriter().println(ktypestring);
		return null;
		}
	}
	
/*	public String getModuletree() throws IOException{
		List mlist =null;
		mlist=moduleService.getModulelist(null,null);
		List<Map<String, Object>> mtlist = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();	
		 if (!(mlist==null)){
		for(int i=0;i<mlist.size();i++){
			BaseModule m=(BaseModule)mlist.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", m.getId());
 		    rootMap.put("Moduledir", m.getModuledir());
		    rootMap.put("Component", m.getComponent().getId());
		    rootMap.put("Createuserid", m.getCreateuserid());
		    rootMap.put("Createdate", m.getCreatedate().toString());
		    rootMap.put("ModuleUUID", m.getModuleUUID());
		    rootMap.put("Name", m.getName());
		    rootMap.put("Note", m.getNote());
		    rootMap.put("XmlFileName", m.getXmlFileName());
		    rootMap.put("Version", m.getVersion());
			rootMap.put("children", getChildren(m));
			mtlist.add(rootMap);		
			
		}
		objectMapper.writeValue(response.getWriter(), mtlist);
		}
		return null;
	}
	
	 private List getChildren(BaseModule m) {
		 List mlist =null;
		 List<Map<String, Object>> nextNode = new ArrayList<Map<String, Object>>();
		 mlist= moduleService.getModulelist(String.valueOf(m.getId()), null);
		 if (!(mlist==null)){
		 for(int i=0;i<mlist.size();i++){
				BaseModule mm=(BaseModule)mlist.get(i);
			    Map<String, Object> rootMap = new HashMap<String, Object>();
			    rootMap.put("id", mm.getId());
	 		    rootMap.put("Moduledir", mm.getModuledir());
			    rootMap.put("Component", mm.getComponent().getId());
			    rootMap.put("Createuserid", mm.getCreateuserid());
			    rootMap.put("Createdate", mm.getCreatedate().toString());
			    rootMap.put("ModuleUUID", mm.getModuleUUID());
			    rootMap.put("Name", mm.getName());
			    rootMap.put("Note", mm.getNote());
			    rootMap.put("XmlFileName", mm.getXmlFileName());
			    rootMap.put("Version", mm.getVersion());
				rootMap.put("children", getChildren(mm));
				nextNode.add(rootMap);		
				
			}
		 
		 
		 }
		 return nextNode;
		 
		 
	}*/
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
	public String getComponentList() throws IOException {
		List<Component> clist = null;
		if("0".equals(parentId) || "null".equals(parentId) || parentId == null){
			 clist =componentService.getNodesByParent(0);
			
			
			
//			JSONArray ja = JSONArray.fromObject(componentService.getNodesByParent(0));
//			response.getWriter().println(ja.toString());
			
		}else{
//			System.out.println("parentId="+parentId);
//			System.out.println("parentId2="+Integer.parseInt(parentId));
//			
			clist =componentService.getNodesByParent(Integer.parseInt(parentId));
//			JSONArray ja = JSONArray.fromObject(componentService.getNodesByParent(Integer.parseInt(parentId)));
//			response.getWriter().println(ja.toString());
		}
		for(Component c:clist){
			c.setIcon(null);
			c.setExpanded(true);
		}	
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(clist);
        response.getWriter().println(ktypestring);

		return null;
		
	}
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
	public String addVersionnandModule() throws IOException {
		String uuid = UUID.randomUUID().toString();
		String path="D:\\module\\";
		File moduledir=new File(path+uuid);
		if(!moduledir.exists()){
			moduledir.mkdirs();
    	}
		LcaModule m=new LcaModule();
		Component component=componentService.getComponent(Integer.valueOf(pdid));
		m.setComponent(component);
		m.setName(mdname);
		m.setNote(mdnote);
		m.setModuleUUID(uuid);
		m.setCreatedate(new Date());
		m.setParent(null);
		m.setModuledir(path+uuid);
		int result=moduleService.addModule(m).getVersion();
		response.getWriter().println(result);
		return null;
		
	}
	public String nodemanage(){
		return "nodemanage";
	}
	public String addmodule() throws Exception{
		LcaModule module =(LcaModule)moduleService.getModule(null,pdid,version);
		
		String parentuuid=module.getModuleUUID();
		String path="D:\\module\\";
		
		LcaModule m=null;
		System.out.println(moduletype);
		if(moduletype.equals("LCA")){
		    List<HashMap> componentslist =getcomponentsJSONvalue();
		    for (int c=0;c<componentslist.size();c++){
		    	HashMap h=componentslist.get(c);
		    	m=new LcaModule();
				m.setName(h.get("name").toString()+'_'+version);
				String uuid = UUID.randomUUID().toString();
				File moduledir=new File(path+parentuuid+"\\"+uuid);
				if(!moduledir.exists()){
					moduledir.mkdirs();
				}
				String stagexmlFileName=path+parentuuid+"\\"+uuid+"\\"+uuid+"_"+h.get("id").toString()+"_stage"+".xml";
				saveXml(stagexmlFileName);
				m.setNote(null);
				m.setCreatedate(new Date());
				m.setModuleUUID(uuid);
				m.setModuledir(path+parentuuid+"\\"+uuid);
				m.setStageXmlFileName(uuid+"_"+h.get("id").toString()+"_stage"+".xml");
				m.setVersion(Integer.valueOf(version));
				m.setParent(module);
				Component component=new Component();
				component.setId(Integer.valueOf(h.get("id").toString()));
				m.setComponent(component);
				moduleService.addModule(m);
		    }
		  
			
			
			System.out.println("lca.....................................");
			
		}
	  List<HashMap> stageslist =getJSONvalue();
  	  int n=stageslist.size();
  	 /* Task task=taskService.getTask(Long.valueOf(taskid));
  	  PdmModule pdm=new PdmModule();
		  pdm.setId(Integer.valueOf(moduleid));
  	  task.setPdmModule(pdm);
  	  PdmProject pro=task.getPdmProject();*/
  	  SimpleDateFormat a=new SimpleDateFormat("yyyy-MM-dd");
  	  for(int i=0;i<n;i++){
			   HashMap h1=stageslist.get(i);
			   List<HashMap> stagecomponents=(List)h1.get("components");
			   String stagename=h1.get("stagename").toString();
			   for(int j=0;j<stagecomponents.size();j++){
				   HashMap h2=stagecomponents.get(j);
				   LcaTask t=new LcaTask();
				   t.setProcessTemplate(null);
				   long carrierid=Long.valueOf(h2.get("processpersonid").toString());
				   Employee carrier=new Employee();
				   carrier.setId(carrierid);
				   t.setCarrier(carrier);
				   m=(LcaModule)moduleService.getModule(h2.get("id").toString(),null,version);
				   t.setLcaModule(m);
				  /* String starttime=String.valueOf(h2.get("starttime"));
				   String endtime=String.valueOf(h2.get("finishtime"));
				   Date taskstartTime=a.parse(null);
		           Date taskfinishTime=a.parse(endtime);
				   t.setStarttime(taskstartTime);
				   t.setEndtime(taskfinishTime);*/
				   t.setName(h2.get("name").toString()+stagename+"阶段建模");
				   t.setStatus(Constants.TASK_STATUS_TO_BE_ACTIVE);
				   t.setPdmProject(null);
				   t.setParentLcaTask(null);
				   taskService.saveLcaTask(t);
				 
				   
			   }
  	  }
			   /*String processid=String.valueOf(h.get("processid"));
			   ProcessTemplate p=moduleService.getNodebyModuleandProcess(moduleid, processid);
			   t.setProcessTemplate(p);
			   long carrierid=Long.valueOf(h.get("processcarrier").toString());
			   Employee carrier=new Employee();
			   carrier.setId(carrierid);
			   t.setCarrier(carrier);
			   String starttime=String.valueOf(h.get("starttime"));
			   String endtime=String.valueOf(h.get("finishtime"));
			   Date taskstartTime=a.parse(starttime);
	           Date taskfinishTime=a.parse(endtime);
			   t.setStarttime(taskstartTime);
			   t.setEndtime(taskfinishTime);
			   String processname=String.valueOf(h.get("processname"));
			   t.setName(processname);
			   t.setStatus(Constants.TASK_STATUS_TO_BE_ACTIVE);
			   t.setPdmProject(pro);
			   t.setParentTask(task);
			   taskService.saveTask(t);
			   String tasktreenodeid=String.valueOf(h.get("tasktreenodeid"));
			   TaskTreeNode node=new TaskTreeNode();
			   node.setId(Long.valueOf(tasktreenodeid));
			   ProcessUrl pu=new ProcessUrl();
			   pu.setTask(t);
		       pu.setNode(node);
			   taskService.savefunctionurl(pu);
			   Set<Function> functions= p.getNode().getFunctions();
				List<Function> functionlist=new ArrayList<Function>();
				 Iterator it=functions.iterator();
					while(it.hasNext()){
						Function f=(Function)it.next();
						String functiontype=f.getTypee();
						String typee=String.valueOf(h.get(functiontype));
						if(functiontype.equals("processurl")){
							ProcessUrl pu=new ProcessUrl();
							pu.setTask(t);
							pu.setUrl(typee);
							taskService.savefunctionurl(pu);
							
						}
					}*/
			  
		   
//		List<HashMap> processlist =getJSONvalue();
//		Set<ProcessTemplate> processTemplates=new HashSet<ProcessTemplate>();
//		 int n=processlist.size();
//		 System.out.println(n);
//		   for(int i=0;i<n;i++){
//			   LcaProcessTemplate p=new LcaProcessTemplate();
//			   HashMap h=processlist.get(i);
//			   String processid=String.valueOf(h.get("id"));
//			   p.setProcessid(Integer.valueOf(processid));
//			   Node node=new Node();
//			   String nodeid=String.valueOf(h.get("nodeid"));
//			   node.setId(Integer.valueOf(nodeid));
//			   p.setNode(node);
//			   p.setName(String.valueOf(h.get("processname")));
//			   String stage=String.valueOf(h.get("stage"));
//			   p.setStage(stage);
//			   processTemplates.add(p);
//		   }
//		   m.setProcessTemplates(processTemplates);
//		   moduleService.addModuleandprocess(m);
		
		return null;
	}
	public String saveXml( String xmlFileName) throws Exception{
		byte[] xmldatas =xmldata.getBytes("UTF-8");
		File xmlData = new File(xmlFileName);
		FileOutputStream outputStream = new FileOutputStream(xmlData);
		outputStream.write(xmldatas);
	    return null;
  }
	public String getModuletree() throws IOException{
		List mlist =null;
		if("0".equals(componentid) || "null".equals(componentid) || componentid == null){
		mlist=moduleService.getLcaModuleComponentlist();
		List<Map<String, Object>> mtlist = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();	
		 if (!(mlist==null)){
		for(int i=0;i<mlist.size();i++){
			Component c=(Component)mlist.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", c.getId());
 		    rootMap.put("name", c.getName());
 		    rootMap.put("expanded", false);
 		    rootMap.put("__viewicon", true);
			mtlist.add(rootMap);		
			
		}
		objectMapper.writeValue(response.getWriter(), mtlist);
		}
		return null;
		}else{
			mlist=moduleService.getmodulelistsbycomponnet(componentid);
			List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
			for(int i=0;i<mlist.size();i++){
				LcaModule m=(LcaModule)mlist.get(i);
			    Map<String, Object> childmap = new HashMap<String, Object>();
			    childmap.put("id", m.getId());
			    childmap.put("name", m.getName());
			    childmap.put("createuserid", m.getCreateuserid());
			    childmap.put("Createdate", m.getCreatedate().toString());
			    childmap.put("Version", m.getVersion());
			    childmap.put("componentid",componentid);
			    childmap.put("Note",m.getNote());
			    childmap.put("expanded", false);
			    childmap.put("__viewicon", true);
			    childlist.add(childmap);		
			}
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(childlist);
        response.getWriter().println(ktypestring);
		return null;
		}
	}
	public String getprocesscontent() throws IOException{
		LcaProcessTemplate pt=(LcaProcessTemplate)moduleService.getprocess(processid, Integer.valueOf(moduleid));
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("name", pt.getName());
		 map.put("stage", pt.getStage());
		 JSONWriter writer = new JSONWriter();
	     String ktypestring=writer.write(map);
	     response.getWriter().println(ktypestring);
		return null;
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
/*	public String getnodelist() throws Exception{
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
	}
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
	}*/

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
			datas =  (List)JSONUtil.read(cellcollection);}
		catch(Exception e ){
			System.out.println("jason解析错误");
			e.printStackTrace();
			return null;
		}
		return datas;
	}
	public List<HashMap> getcomponentsJSONvalue()
	{
		//JSONUtil jr  = new JSONUtil();
		List<HashMap> datas ;
		try{
			datas =  (List)JSONUtil.read(components);}
		catch(Exception e ){
			System.out.println("jason解析错误");
			e.printStackTrace();
			return null;
		}
		return datas;
	}
	//保存模块
	public String saveComponents() throws IOException{
		
		Component c = new Component();
		c.setDescription(description);
		c.setComponentlevel(2);
		c.setName(name);	
		c.setParentId(Integer.parseInt(parentId));			
		c = componentService.addComponent(c);
		
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(c);
        response.getWriter().println(ktypestring);
		
//		JSONObject jo = JSONObject.fromObject(c);
//		response.getWriter().println(jo.toString());
		return null;
	}
	public String getdraw(){
			
			return "draw";
		}
	public String getmodulelook(){
		request.setAttribute("moduleid", moduleid);
		return "modulelook";
	}
	
	
	public void downloadXML() throws IOException {
		LcaModule m=(LcaModule)moduleService.getModule(moduleid);
		String filename = m.getModuledir()
				+ File.separator + m.getStageXmlFileName();
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

	//删除模块
	public String deleteComponents(){
		int id = Integer.parseInt(cId);
		componentService.deleteComponent(id);
		return null;
	}
	
	//更新模块
	public String updateComponents() throws IOException{
		int id_ = Integer.parseInt(id);
		Component c = componentService.getComponent(id_);
		c.setName(name);
		c.setDescription(description);
		
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(c);
        response.getWriter().println(ktypestring);
//		JSONObject jo = JSONObject.fromObject(c);
//		response.getWriter().println(jo.toString());
		return null;
	}
	
	//根据名字查询对应的模块
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

	public String searchComponents() throws IOException{
		List clist = componentService.searchComponent(name);
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(clist);
        response.getWriter().println(ktypestring);
		
//		JSONArray ja = JSONArray.fromObject(componentService.searchComponent(name));
//		response.getWriter().println(ja.toString());
		return null;
	}
	
	//检验是否有该零部件
	public String checkComponent() throws IOException {
		
		int check = componentService.checkComponent(name);
		HashMap hm= new HashMap();
		hm.put("result", check);
		JSONWriter jw=new JSONWriter();
		String json=jw.write(hm);
		response.getWriter().print(json);
//		JSONObject jo = new JSONObject();
//		jo.put("result", componentService.checkComponent(name));
//		response.getWriter().println(jo.toString());
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

	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public String getModulenote() {
		return modulenote;
	}
	public void setModulenote(String modulenote) {
		this.modulenote = modulenote;
	}
	public String getCellcollection() {
		return cellcollection;
	}
	public void setCellcollection(String cellcollection) {
		this.cellcollection = cellcollection;
	}
	public String getModuletype() {
		return moduletype;
	}
	public void setModuletype(String moduletype) {
		this.moduletype = moduletype;
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
	public ComponentService getComponentService() {
		return componentService;
	}

	public void setComponentService(ComponentService componentService) {
		this.componentService = componentService;
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
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	public List<File> getFile() {
		return file;
	}
	public void setFile(List<File> file) {
		this.file = file;
	}
	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getComponents() {
		return components;
	}
	public void setComponents(String components) {
		this.components = components;
	}
	public TaskService getTaskService() {
		return taskService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
}
