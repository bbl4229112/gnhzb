package edu.zju.cims201.GOF.web.pdmproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


import edu.zju.cims201.GOF.web.CrudActionSupport;
import edu.zju.cims201.GOF.hibernate.pojo.CProject;
import edu.zju.cims201.GOF.hibernate.pojo.Function;
import edu.zju.cims201.GOF.hibernate.pojo.PdmModule;
import edu.zju.cims201.GOF.hibernate.pojo.PdmProcessTemplate;
import edu.zju.cims201.GOF.hibernate.pojo.ProcessTemplate;
import edu.zju.cims201.GOF.hibernate.pojo.ProcessUrl;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.Employee;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmProject;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.LcaTask;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.PdmTask;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.TaskTreeNode;
import edu.zju.cims201.GOF.service.department.DepartmentService;
import edu.zju.cims201.GOF.service.module.ModuleService;
import edu.zju.cims201.GOF.service.project.ProjectService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.service.task.TaskService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;


@Namespace("/project")
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "project.action", type = "redirect")})
public class ProjectAction extends CrudActionSupport<CProject> implements ServletResponseAware, ServletRequestAware {
private static final long serialVersionUID = 8683878162525847072L;
	@Resource(name="departmentServiceImpl")
	private DepartmentService departmentService;
	@Resource(name="projectServiceImpl")
	private ProjectService projectService;
	@Resource(name="taskServiceImpl")
	private TaskService taskService;
	@Resource(name="userServiceImpl")
	private UserService userservice;
	@Resource(name="moduleServiceImpl")
	private ModuleService moduleService;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String cellcollection;
	private String moduleid;
	private String projectdetail;
	private String projectname;
	private String projecttype;
	private String starttime;
	private String finishtime;
	private String processid;
	private String projectobjectdefine;
	
	
	
	PrintWriter out;
	
	public String getCellcollection() {
		return cellcollection;
	}

	public void setCellcollection(String cellcollection) {
		this.cellcollection = cellcollection;
	}

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getProjectdetail() {
		return projectdetail;
	}

	public void setProjectdetail(String projectdetail) {
		this.projectdetail = projectdetail;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getProjecttype() {
		return projecttype;
	}

	public void setProjecttype(String projecttype) {
		this.projecttype = projecttype;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}


	public String nodemanage(){
		return "nodemanage";
	}
	 public String saveproject() throws ParseException{
	     SystemUser sysuser=userservice.getUser();
	     Employee user=departmentService.getEmployeebyuserid(sysuser.getId());
	      if(projecttype.equals("Pdm")){
	    	  HashMap projectmap=(HashMap)getJSONvalueObject();
		      String projectname=projectmap.get("projectname").toString();
		      if((!"".equals(projectname))&&projectname!=null){
		          //开发任务管理者
		          //DomainTreeNode domain=domaintreeDao.get(this.domainId);
		    	  System.out.println("开始");
		    	  PdmProject pro=new PdmProject();
		    	  pro.setProjectname(projectname);
		    	 // pro.setProjectCode(projectCode);
		    	  pro.setProjectdetail(projectmap.get("projectnote").toString());
		    	  //pro.setDomainnode(domain);
		    	  pro.setCreater(user);
		    	  pro.setCreatTime(new Date());
		    	  pro.setFinishpercent("0");
		    	  SimpleDateFormat a=new SimpleDateFormat("yyyy-MM-dd");
		    	  //转换为标准时间格式
		    	  Date startTime=a.parse(projectmap.get("starttime").toString());
		          Date finishTime=a.parse(projectmap.get("finishtime").toString());
		    	  pro.setStartTime(startTime);
		    	  pro.setDeveloper(user);
		    	  pro.setApplier(user);
		    	  pro.setFinishTime(finishTime);
		    	  String moduleid=projectmap.get("moduleid").toString();
		    	  PdmModule pdm=new PdmModule();
				  pdm.setId(Integer.valueOf(moduleid));
				  
				  pro.setPdmModule(pdm);
		    	  int  day=(int) ((finishTime.getTime()-startTime.getTime())/(24*60*60*1000));
		    	  //开发任务
		    	  projectService.save(pro);
		    	  List<HashMap> tasks =getJSONvalueList();
		    	  int n=tasks.size();
		    	  PdmModule pm=moduleService.getPdmModulebyparentandprocess("level_stage",
		    				null,moduleid);
		    	  moduleid=String.valueOf(pm.getId());
		    	  for(int i=0;i<n;i++){
		    		   PdmTask t=new PdmTask();
		    		   System.out.println("继续");
					   HashMap h=tasks.get(i);
					   String processid=String.valueOf(h.get("id"));
					   PdmProcessTemplate p=(PdmProcessTemplate)moduleService.getNodebyModuleandProcess(moduleid, processid);
					   t.setPdmprocessTemplate(p);
					   t.setPdmModule(pdm);
					   long carrierid=Long.valueOf(h.get("processpersonid").toString());
					   Employee carrier=new Employee();
					   carrier.setId(carrierid);
					   t.setCarrier(carrier);
					   long checkid=Long.valueOf(h.get("processcheckpersonid").toString());
					   Employee checkperson=new Employee();
					   checkperson.setId(checkid);
					   t.setCheckperson(checkperson);
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
					   taskService.savePdmTask(t);
				   }
		    	
		    	 PrintWriter out;
				try {
					out = this.response.getWriter();
					out.print(pro.getId()+"");
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		  		
		      }
		      
		      return null;
	    	  
	      }else if (projecttype.equals("Lca")){
	    	  return null;
	      }else{
	    	  return null;
	      }
	      
	      
	    }
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
	 public Object getJSONvalueObject(){
			Object datas ;
			try{
				datas =  JSONUtil.read(projectobjectdefine);}
			catch(Exception e ){
				System.out.println("jason解析错误");
				e.printStackTrace();
				return null;
			}
			return datas;
		}


	public CProject getModel() {
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

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public ModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public UserService getUserservice() {
		return userservice;
	}

	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}

	public String getProjectobjectdefine() {
		return projectobjectdefine;
	}

	public void setProjectobjectdefine(String projectobjectdefine) {
		this.projectobjectdefine = projectobjectdefine;
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
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	
}
