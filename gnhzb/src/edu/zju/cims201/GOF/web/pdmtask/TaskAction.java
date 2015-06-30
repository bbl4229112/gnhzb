package edu.zju.cims201.GOF.web.pdmtask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONString;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.codehaus.jackson.map.ObjectMapper;
import org.springside.modules.orm.Page;


import EDU.oswego.cs.dl.util.concurrent.Takable;
import edu.zju.cims201.GOF.web.CrudActionSupport;
import edu.zju.cims201.GOF.hibernate.pojo.Function;
import edu.zju.cims201.GOF.hibernate.pojo.Node;
import edu.zju.cims201.GOF.hibernate.pojo.PdmModule;
import edu.zju.cims201.GOF.hibernate.pojo.PdmProcessTemplate;
import edu.zju.cims201.GOF.hibernate.pojo.ProcessTemplate;
import edu.zju.cims201.GOF.hibernate.pojo.ProcessUrl;
import edu.zju.cims201.GOF.hibernate.pojo.RelatedModel;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.Employee;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmProject;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.LcaTask;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmTask;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.TaskTreeNode;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.TaskDTO;
import edu.zju.cims201.GOF.service.department.DepartmentService;
import edu.zju.cims201.GOF.service.module.ModuleService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.task.TaskService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;



@Namespace("/pdmtask")
@Results({
		@Result(name = CrudActionSupport.RELOAD, location = "task.action", type = "redirect"),
		@Result(name = "taskmanage", location = "/WEB-INF/content/process/filelook.jsp"),
		@Result(name = "processmanage", location = "/WEB-INF/content/process/Processtest.jsp"),
		@Result(name = "processfile", location = "/WEB-INF/content/process/files.jsp") })
public class TaskAction extends CrudActionSupport<LcaTask> implements
		ServletResponseAware, ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name = "treeServiceImpl")
	private TreeService treeService;
	@Resource(name = "userServiceImpl")
	private UserService userServiceImpl;
	@Resource(name = "taskServiceImpl")
	private TaskService taskService;
	@Resource(name="moduleServiceImpl")
	private ModuleService moduleService;
	@Resource(name="departmentServiceImpl")
	private DepartmentService departmentService;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private int size;
	private int index;
	private String cellcollection;
	private String moduleid;
	private String taskid;
	PrintWriter out;
	private String tasktype;
	private String processtemplateid;


	public LcaTask getModel() {
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

  
	public String getMytasks() {
		SystemUser sysuser = userServiceImpl.getUser();
		System.out.println(sysuser.getId());
		Employee user=departmentService.getEmployeebyuserid(sysuser.getId());
		if(tasktype.equals("Pdm")){
			
			Page<PdmTask> page = null;
			if (this.getSize() == 0) {
				page = new Page<PdmTask>(20);
			} else {
				page = new Page<PdmTask>(this.getSize());
			}
			page.setPageNo(this.getIndex());
			Page<PdmTask> newpage = null;
			newpage = taskService.getPdmtasks(user, page);

			PageDTO pagedto = new PageDTO();
			pagedto.setTotal(newpage.getTotalCount());
			pagedto.setPagesize(newpage.getPageSize());
			pagedto.setTotalPage(newpage.getTotalPages());
			List<PdmTask> tasklist = newpage.getResult();
	        pagedto.setData(new ArrayList());
	        System.out.println("ddddddddddddddddddddddddddddddddd"+user.getId());
	        System.out.println("ddddddddddddddddddddddddddddddddd"+tasklist.size());
			for (PdmTask task : tasklist) {
				TaskDTO taskdto = new TaskDTO();
				taskdto.setName(task.getName());
				taskdto.setId(task.getId());
				taskdto.setStarttime(task.getStarttime().toString());
				taskdto.setEndtime(task.getEndtime().toString());
				taskdto.setCarriername(task.getCarrier().getUser().getName());
				taskdto.setProjectname(task.getPdmProject().getProjectname());
				taskdto.setStatus(task.getStatus());
				pagedto.getData().add(taskdto);
			}
			System.out.println(tasklist.size());
			JSONUtil.write(response, pagedto);
			return null;
		
		}else if(tasktype.equals("Lca")){
			Page<LcaTask> page = null;
			if (this.getSize() == 0) {
				page = new Page<LcaTask>(20);
			} else {
				page = new Page<LcaTask>(this.getSize());
			}
			page.setPageNo(this.getIndex());
			Page<LcaTask> newpage = null;
			newpage = taskService.getLcatasks(user, page);
	
			PageDTO pagedto = new PageDTO();
			pagedto.setTotal(newpage.getTotalCount());
			pagedto.setPagesize(newpage.getPageSize());
			pagedto.setTotalPage(newpage.getTotalPages());
			List<LcaTask> tasklist = newpage.getResult();
	        pagedto.setData(new ArrayList());
			// SystemUser user = userServiceImpl.getUser();
			// if(size==0){
			// size=20;
			// }
			// if(index==0){
			// index=1;
			// }
			// List<Task> tasklist=workFlowService.getMytask(user,index,size);
			// List<TaskDTO> taskdtoList=new ArrayList<TaskDTO>();
			for (LcaTask task : tasklist) {
				TaskDTO taskdto = new TaskDTO();
				taskdto.setName(task.getName());
				taskdto.setId(task.getId());
				taskdto.setStarttime(task.getStarttime().toString());
				taskdto.setEndtime(task.getEndtime().toString());
				taskdto.setCarriername(task.getCarrier().getUser().getName());
				taskdto.setProjectname(task.getPdmProject().getProjectname());
				taskdto.setStatus(task.getStatus());
				pagedto.getData().add(taskdto);
			}
			System.out.println(tasklist.size());
			JSONUtil.write(response, pagedto);
			return null;
		}else {
			return null;
		}
		
		
	}
	public void getkeywordidsbyprocess() throws IOException{
    	PdmProcessTemplate p=moduleService.getprocesstemplate(processtemplateid);
    	Set<RelatedModel> relatedmodels=p.getRelatedmodels();
    	Iterator it =relatedmodels.iterator();
    	List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
    	while(it.hasNext())
    	{   	
    		
				HashMap<String, Object> map = new HashMap<String, Object>();
    			RelatedModel r=(RelatedModel)it.next();
				map.put("id",   r.getDomainNodeId());
				TreeNode a=treeService.getTreeNode(r.getDomainNodeId());
				map.put("name", a.getNodeName());	
				list.add(map);
    	}
    	ObjectMapper objectMapper = new ObjectMapper();	
	    objectMapper.writeValue(response.getWriter(),list);
    }
    public void getTaskTreeCollectionbyTaskid() throws IOException{
    	PdmTask t=taskService.getPdmTask(Long.valueOf(taskid));
    	System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
    	PdmProcessTemplate p=t.getPdmprocessTemplate();
    	TaskTreeNode parentnode=p.getTasktreenode();
    	PdmProject pro=t.getPdmProject();
    	PdmModule parentmodule=p.getModule();
    	PdmModule superparentmodule=parentmodule.getParent();
    	List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
    	HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", parentnode.getId());
		map.put("name", p.getName());	
		map.put("des", parentnode.getNodeDescription());	
		map.put("url", parentnode.getUrl());
		map.put("processtemplateid", p.getId());
		map.put("code", parentnode.getCode());	
		map.put("children", getsubnodes(p,parentmodule.getLevelid(),String.valueOf(superparentmodule.getId())));	
		list.add(map);
    	ObjectMapper objectMapper = new ObjectMapper();	
	    objectMapper.writeValue(response.getWriter(),list);
    }
    public List getsubnodes(PdmProcessTemplate p,String parentlevelid,String supermoduleid){
    	List<HashMap<String, Object>> nodelist=new ArrayList<HashMap<String,Object>>();
    	PdmModule submodule =moduleService.getPdmModulebyparentandprocess(parentlevelid,
				p.getProcessid(),supermoduleid);
    	if(submodule!=null){
    		
    		List<PdmProcessTemplate> prots=moduleService.getprocesslists(submodule.getId());
        
        for(int i=0;i<prots.size();i++){
        		PdmProcessTemplate pt=prots.get(i);
        		TaskTreeNode subnode=pt.getTasktreenode();
        		HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", subnode.getId());
				map.put("name", pt.getName());
				map.put("input", pt.getInput());
				map.put("output", pt.getOutput());
				map.put("des", subnode.getNodeDescription());	
				map.put("url", subnode.getUrl());
				map.put("processtemplateid", pt.getId());
				map.put("code", subnode.getCode());	
				map.put("children", getsubnodes(pt,submodule.getLevelid(),supermoduleid));	
				nodelist.add(map);
        	}
        	return nodelist;
    	}else{
    		return null;
    	}
    	
    }
/*    public void taskAssignment() throws ParseException{
    	  List<HashMap> tasks =getJSONvalueList();
    	  int n=tasks.size();
    	  Task task=taskService.getTask(Long.valueOf(taskid));
    	  PdmModule pdm=new PdmModule();
		  pdm.setId(Integer.valueOf(moduleid));
    	  task.setPdmModule(pdm);
    	  PdmProject pro=task.getPdmProject();
    	  SimpleDateFormat a=new SimpleDateFormat("yyyy-MM-dd");
    	  for(int i=0;i<n;i++){
    		   Task t=new Task();
			   HashMap h=tasks.get(i);
			   String processid=String.valueOf(h.get("processid"));
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
					}
			  
		   }
    }*/
    public List<HashMap> getJSONvalueList()
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
	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}


	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}


	public TaskService getTaskService() {
		return taskService;
	}


	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
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


	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}



	public PrintWriter getOut() {
		return out;
	}


	public void setOut(PrintWriter out) {
		this.out = out;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getSize() {
		return size;
	}


	public ModuleService getModuleService() {
		return moduleService;
	}


	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public String getModuleid() {
		return moduleid;
	}


	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}


	public String getTasktype() {
		return tasktype;
	}


	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public String getCellcollection() {
		return cellcollection;
	}


	public String getTaskid() {
		return taskid;
	}


	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}


	public void setCellcollection(String cellcollection) {
		this.cellcollection = cellcollection;
	}


	public DepartmentService getDepartmentService() {
		return departmentService;
	}


	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}


	public TreeService getTreeService() {
		return treeService;
	}


	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}


	public String getProcesstemplateid() {
		return processtemplateid;
	}


	public void setProcesstemplateid(String processtemplateid) {
		this.processtemplateid = processtemplateid;
	}

	/**
	 * 得到关于具体某一个任务的具体信息
	 * 
	 * @author yet
	 */
	

}
