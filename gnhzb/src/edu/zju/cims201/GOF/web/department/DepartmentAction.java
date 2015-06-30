package edu.zju.cims201.GOF.web.department;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.apache.struts2.interceptor.ServletResponseAware;
import org.codehaus.jackson.map.ObjectMapper;

import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.Department;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.Employee;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.OperationRoles;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.Privilege;
import edu.zju.cims201.GOF.hibernate.pojo.pdm.PrivilegeOperationRole;
import edu.zju.cims201.GOF.service.department.DepartmentService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 * 
 * @author calvin
 */
//定义URL映射对应/account/user.action
@Namespace("/department")
//定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "department.action", type = "redirect") })
public class DepartmentAction extends CrudActionSupport<Department> implements ServletResponseAware{

	private static final long serialVersionUID = 8683878162525847072L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	@Resource(name="departmentServiceImpl")
	private DepartmentService departmentService;
	@Resource(name="userServiceImpl")
	UserService userService;
	private String department;
	private String employee;
	private String id;
	private String privilege;
	private String role;
	private String rolearray;
	
    public void saveDepartment(){
    	HashMap projectmap=(HashMap)getJSONvalueObject(department);
    	Object object=projectmap.get("id");
    	String name=projectmap.get("name").toString();
    	if(object==null){
    		Department department=new Department();
    		department.setName(name);
    		departmentService.save(department);
    	}else{
    		System.out.println("是的 ");
    		Department department=departmentService.getDepartmentByid(object.toString());
    		department.setName(name);
    		departmentService.save(department);
    	};
    	
    }
   
    public void saveEmployee(){
    	HashMap projectmap=(HashMap)getJSONvalueObject(employee);
    	Object object=projectmap.get("id");
    	if(object==null){
    		
    		Employee e=new Employee();
        	String userid=projectmap.get("userid").toString();
        	String departmentid=projectmap.get("depid").toString();
        	String privilegeid=projectmap.get("priviid").toString();
        	SystemUser user=new SystemUser();
        	user.setId(Long.valueOf(userid));
        	e.setUser(user);
        	Department department=new Department();
        	department.setId(Long.valueOf(departmentid));
        	e.setDepartment(department);
        	Privilege p=new Privilege();
        	p.setId(Long.valueOf(privilegeid));
        	e.setPrivilege(p);
        	departmentService.saveEmployee(e);
    	}else{
    		Employee e=departmentService.geEmployeeByid(object.toString());
        	String departmentid=projectmap.get("depid").toString();
        	String privilegeid=projectmap.get("priviid").toString();
        	Department department=new Department();
        	department.setId(Long.valueOf(departmentid));
        	e.setDepartment(department);
        	Privilege p=new Privilege();
        	p.setId(Long.valueOf(privilegeid));
        	e.setPrivilege(p);
        	departmentService.saveEmployee(e);
    	}
    	
    	
    }
    public void savePrivilege(){
    	HashMap projectmap=(HashMap)getJSONvalueObject(privilege);
    	Object object=projectmap.get("id");
    	String name=projectmap.get("name").toString();
    	String code=projectmap.get("code").toString();
    	if(object==null){
    		Privilege privi=new Privilege();
    		privi.setName(name);
    		privi.setCode(code);
    		departmentService.savePrivilege(privi);
    	}else{
    		System.out.println("是的 ");
    		Privilege privi=departmentService.getPrivilegeByid(object.toString());
    		privi.setName(name);
    		privi.setCode(code);
    		departmentService.savePrivilege(privi);
    	};
    	
    }
    public void saveOperationRoles(){
    	HashMap projectmap=(HashMap)getJSONvalueObject(role);
    	Object object=projectmap.get("id");
    	String name=projectmap.get("name").toString();
    	if(object==null){
    		OperationRoles r=new OperationRoles();
    		r.setName(name);
    		departmentService.saveOperationRoles(r);
    	}else{
    		System.out.println("是的 ");
    		OperationRoles r=departmentService.getOperationRolesByid(object.toString());
    		r.setName(name);
    		departmentService.saveOperationRoles(r);
    	};
    }
    public void deleteDepartment(){
    	departmentService.delete(id);
    	
    }
    public void deleteEmployee(){
    	
    }
    public void deletePrivilege(){
     	departmentService.deletePrivilege(id);
    }
    public void deleteOperationRoles(){
    	departmentService.delete(id);
    	
    }
    
    public void getEmployeeByDepartment() throws IOException{
    	PrintWriter w=response.getWriter();
    	List list=departmentService.getEmployeeByDepartment(id);
    	ObjectMapper objectMapper=new ObjectMapper();
    	List<Map<String, Object>> elist = new ArrayList<Map<String, Object>>();
		 if (!(list==null)){
		for(int i=0;i<list.size();i++){
			Employee e=(Employee)list.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", e.getId());
 		    rootMap.put("name", e.getUser().getName());
			elist.add(rootMap);		
			
		}
		objectMapper.writeValue(w, elist);
    	
		 }
    }
    public void getDepartment() throws IOException{
    	PrintWriter w=response.getWriter();
    	List list=departmentService.getAllDepartments();
    	HashMap h=new HashMap();
    	h.put("name", "部门列表");
    	h.put("enableSelect", false);
    	h.put("children", list);
    	ObjectMapper objectMapper=new ObjectMapper();
    	objectMapper.writeValue(w, h);
    	
    	
    }
    public void getAllUsers()throws IOException{
    	PrintWriter w=response.getWriter();
    	List<SystemUser> userlist=userService.getAllUsers();
    	ObjectMapper objectMapper=new ObjectMapper();
    	List<Map<String, Object>> elist = new ArrayList<Map<String, Object>>();
		 if (!(userlist==null)){
		for(int i=0;i<userlist.size();i++){
			SystemUser p=(SystemUser)userlist.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", p.getId());
 		    rootMap.put("name",p.getName());
			elist.add(rootMap);		
			
		}
		objectMapper.writeValue(w, elist);
    	
		 }
    }
    public void getEmployee()throws IOException{
    	PrintWriter w=response.getWriter();
    	List list=departmentService.getEmployee();
    	ObjectMapper objectMapper=new ObjectMapper();
    	List<Map<String, Object>> elist = new ArrayList<Map<String, Object>>();
		 if (!(list==null)){
		for(int i=0;i<list.size();i++){
			Employee p=(Employee)list.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", p.getId());
 		    rootMap.put("name",p.getUser().getName());
 		    rootMap.put("emial", p.getUser().getEmail());
		    rootMap.put("hobby",p.getUser().getHobby());
		    rootMap.put("dep", p.getDepartment().getName());
		    rootMap.put("depid", p.getDepartment().getId());
 		    rootMap.put("sex",p.getUser().getSex());
 		    rootMap.put("privi", p.getPrivilege().getName());
 		    rootMap.put("priviid", p.getPrivilege().getId());
			elist.add(rootMap);		
			
		}
		objectMapper.writeValue(w, elist);
    	
		 }
    }
    public void getPrivilege()  throws IOException{
    	PrintWriter w=response.getWriter();
    	List list=departmentService.getPrivilege();
    	ObjectMapper objectMapper=new ObjectMapper();
    	List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
		 if (!(list==null)){
		for(int i=0;i<list.size();i++){
			Privilege p=(Privilege)list.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", p.getId());
 		    rootMap.put("name",p.getName());
 		    rootMap.put("code",p.getCode());
			plist.add(rootMap);		
			
		}
		objectMapper.writeValue(w, plist);
    	
		 }
    	
    }
    public void getOperationRoles() throws IOException{
    	PrintWriter w=response.getWriter();
    	List list=departmentService.getOperationRoles();
    	ObjectMapper objectMapper=new ObjectMapper();
    	List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
		 if (!(list==null)){
		for(int i=0;i<list.size();i++){
			OperationRoles r=(OperationRoles)list.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", r.getId());
 		    rootMap.put("name",r.getName());
			rlist.add(rootMap);		
			
		}
		objectMapper.writeValue(w, rlist);
    	
		 }
    	
    }
    public void addPrivilegeOperationRoles() throws IOException{
    	PrintWriter w=response.getWriter();
    	List<HashMap> roles =getJSONvalueList();
    	Privilege p=departmentService.getPrivilegeByid(id);
    	int n=roles.size();
    	 for(int i=0;i<n;i++){
    		 HashMap h=roles.get(i);
    		 System.out.println(Long.valueOf(n));
    		 String id=h.get("id").toString().trim();
    		 OperationRoles r=departmentService.getOperationRolesByid(id);
    		 PrivilegeOperationRole pr=new PrivilegeOperationRole();
    		 pr.setOperationRoles(r);
    		 pr.setPrivilege(p);
    		 departmentService.savePrivilegeOperationRoles(pr);
    	 }
    	
         
    	
    }
    public List<HashMap> getJSONvalueList()
   	{
   		//JSONUtil jr  = new JSONUtil();
   		List<HashMap> datas ;
   		try{
   			datas =  (List)JSONUtil.read(rolearray);}
   		catch(Exception e ){
   			System.out.println("jason解析错误");
   			e.printStackTrace();
   			return null;
   		}
   		return datas;
   	}
    public void deletePrivilegeOperationRoles() throws IOException{
    	PrintWriter w=response.getWriter();
    	List<HashMap> roles =getJSONvalueList();
    	int n=roles.size();
    	 for(int i=0;i<n;i++){
    		 HashMap h=roles.get(i);
    		 long roleid=Long.valueOf(h.get("id").toString());
    		 long priviid=Long.valueOf(id);
    		 departmentService.deleteoperationRolesbyprivilege(roleid,priviid);
    	 }
    	 
    	
    }
   
    public void getOwnPrivilegeOperationRoles() throws IOException{
    	PrintWriter w=response.getWriter();
    	List<OperationRoles> list=departmentService.getOperationRolesbyprivilege(id);
		HashMap<String,List> h=new HashMap<String, List>();
		List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
		StringBuffer strbuf = new StringBuffer();
		String ownid=null;
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
			OperationRoles r=list.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", r.getId());
		    strbuf.append(",").append(r.getId());
 		    rootMap.put("name",r.getName());
			rlist.add(rootMap);	
			}
			h.put("ownrole", rlist);
			ownid=strbuf.deleteCharAt( 0 ).toString();
		}else{
			h.put("ownrole", null);
		};
		List list2=departmentService.getUnOwnOperationRoles(ownid);
    	ObjectMapper objectMapper=new ObjectMapper();
    	List<Map<String, Object>> urlist = new ArrayList<Map<String, Object>>();
		 if (!(list2==null)){
		for(int i=0;i<list2.size();i++){
			OperationRoles r=(OperationRoles)list2.get(i);
		    Map<String, Object> rootMap = new HashMap<String, Object>();
		    rootMap.put("id", r.getId());
 		    rootMap.put("name",r.getName());
 		    urlist.add(rootMap);		
			
		}
		h.put("unownrole", urlist);
		objectMapper.writeValue(w, h);
    	
		 }
    	
    }


	public Department getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	 public Object getJSONvalueObject(String data){
			Object datas ;
			try{
				datas =  JSONUtil.read(data);}
			catch(Exception e ){
				System.out.println("jason解析错误");
				e.printStackTrace();
				return null;
			}
			return datas;
		}




	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
		
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





	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRolearray() {
		return rolearray;
	}

	public void setRolearray(String rolearray) {
		this.rolearray = rolearray;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
