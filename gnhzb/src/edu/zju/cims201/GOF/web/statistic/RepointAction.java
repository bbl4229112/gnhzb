package edu.zju.cims201.GOF.web.statistic;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.Query;
import org.stringtree.json.JSONWriter;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.dao.logging.SysBehaviorListDao;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorList;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.SysBehaviorListDTO;
import edu.zju.cims201.GOF.service.logging.SysBehaviorListServiceImpl;
import edu.zju.cims201.GOF.service.statistic.StatisticService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.web.CrudActionSupport;




@Namespace("/")
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "repoint.action", type = "redirect")})
public class RepointAction extends ActionSupport implements ServletResponseAware {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name="userServiceImpl")
	UserService userService;
	@Resource(name="statisticServiceImpl")
	StatisticService statisticService;
	@Resource(name="sysBehaviorListDao")
	SysBehaviorListDao sysBehaviorListDao;
	@Resource(name="sysBehaviorListServiceImpl")
	SysBehaviorListServiceImpl sysBehaviorListService;
	
	private HttpServletResponse response;
	private String behaviorname;
	private Integer behaviorpoint;
	private Boolean contributionBehavior;
	private String behaviorcname;
	private Long id;
	
	

/**
 * 更新behavior类型属性
 * @return
 * @throws Exception
 */

	public String update()throws Exception{
		
		
		if(null!=id)
		{
			SysBehaviorList s=sysBehaviorListService.getSysBehaviorList(id);
			if(null!=behaviorname)s.setBehaviorname(behaviorname);
			
			if(null!=behaviorpoint)s.setBehaviorpoint(behaviorpoint);
			
			if(null!=contributionBehavior)s.setContributionBehavior(contributionBehavior);
			
			if(null!=behaviorcname)s.setBehaviorcname(behaviorcname);
			
			sysBehaviorListService.save(s);
			
		}
		
		
		
		return null;
	}
	
	/**
	 * list所有操作类型
	 * @return
	 * @throws Exception
	 */
   public String list()throws Exception{
		
		List<SysBehaviorList> list_behavior=sysBehaviorListService.listSysBehaviorLists();
		List<SysBehaviorListDTO> list_behaviorDTO=new ArrayList<SysBehaviorListDTO>();
		for(SysBehaviorList s:list_behavior)
		{SysBehaviorListDTO sd=new SysBehaviorListDTO();
		sd.setBehaviorcname(s.getBehaviorcname());
		sd.setBehaviorname(s.getBehaviorname());
		sd.setBehaviorpoint(s.getBehaviorpoint());
		sd.setContributionBehavior(s.getContributionBehavior());
		sd.setId(s.getId());
		list_behaviorDTO.add(sd);
			
		}
		
		
		 PrintWriter out = response.getWriter();
			JSONWriter jw = new JSONWriter();
			String str = jw.write(list_behaviorDTO);
			out.println(str);
		return null;
		
	}
	
	/**
	 * 重新计算分数
	 * @return
	 * @throws Exception
	 */
	public String repoint()throws Exception{
		
	response.setContentType("text/html;charset=utf-8");
	PrintWriter out = response.getWriter();
	
	try{
		List<SystemUser> userlist=userService.getAllUsers();
		if(null!=userlist)
		{
              //构建查询模式的数组，以便后续遍历
           String[] model=new String[]{"week","lasttwoweek","lastweek","month","lastmonth","year","lastyear","all"};
			for (int i = 0; i < userlist.size(); i++) {
				SystemUser user=(SystemUser)userlist.get(i);
				Long userid=user.getId();
				
				String hql="select sum( o.behaviorpoint ) from SysBehaviorList o ,SysBehaviorLog l ,SystemUser u  where"+ 
	                             " o.id=l.sysBehaviorList.id and l.user.id=u.id and u.id="+userid+" and l.actionTime between ? and ? ";
				String contr="";
				
		        //week 

			    Object[] hqltparam = new Object[]{};
			    contr =  " and o.contributionBehavior=1";

			     for (int j = 0; j < model.length; j++) {
			    	 
			    	  hqltparam=statisticService.getHqltime(model[j]);

						int totalscore=0;
				    	Query qr=sysBehaviorListDao.createQuery(hql, hqltparam);
				        if(null!=qr.uniqueResult())
				            	totalscore=Integer.parseInt(qr.uniqueResult().toString());
				
				        //计算用户参与度
				        if(model[j].equals("week"))
				        	user.setWeekpscore(totalscore);
				        if(model[j].equals("lasttwoweek"))
				        	user.setLasttwoweekpscore(totalscore);
				        if(model[j].equals("lastweek"))
				        	user.setLastweekpscore(totalscore);
				        if(model[j].equals("month"))
				        	user.setMonthpscore(totalscore);
				        if(model[j].equals("lastmonth"))
				        	user.setLastmonthpscore(totalscore);
				        if(model[j].equals("year"))
				        	user.setYearpscore(totalscore);
				        if(model[j].equals("lastyear"))
					     user.setLastyearpscore(totalscore);
					    if(model[j].equals("all"))
						  user.setTotoalscore(totalscore);
					    //计算用户贡献度
					    qr=sysBehaviorListDao.createQuery(hql+contr, hqltparam);
				        if(null!=qr.uniqueResult())
				            	totalscore=Integer.parseInt(qr.uniqueResult().toString());
				
				        if(model[j].equals("week"))
				        	user.setWeekcscore(totalscore);
				        if(model[j].equals("lasttwoweek"))
				        	user.setLasttwoweekcscore(totalscore);
				        if(model[j].equals("lastweek"))
				        	user.setLastweekcscore(totalscore);
				        if(model[j].equals("month"))
				        	user.setMonthcscore(totalscore);
				        if(model[j].equals("lastmonth"))
				        	user.setLastmonthcscore(totalscore);
				        if(model[j].equals("year"))
				        	user.setYearcscore(totalscore);
				        if(model[j].equals("lastyear"))
					     user.setLastyearcscore(totalscore);
					    if(model[j].equals("all"))
						  user.setContributionscore(totalscore);
				}
			   
						userService.updateUser(user);
						
			}
			//out.println("<table width='100%'><tr height='30px'><td></td></tr><tr><td align='center'><font style='size:15;color:red'>系统用户评分更新完毕！<font></td></tr><tr height='50px'><td></td></tr></table>");
		}
	}catch(Exception e){
		e.printStackTrace();
		//out.println("<table width='100%'><tr height='30px'><td></td></tr><tr><td align='center'><font style='size:15;color:red'>错误，请重新再试！<font></td></tr><tr height='50px'><td></td></tr></table>");
	}
	return null;
	
	}
	
	
	
	
	
	
	public void setServletResponse(HttpServletResponse arg0) {
       this.response=arg0;		
	}
	
	
	public String getBehaviorname() {
		return behaviorname;
	}

	public void setBehaviorname(String behaviorname) {
		this.behaviorname = behaviorname;
	}

	public Integer getBehaviorpoint() {
		return behaviorpoint;
	}

	public void setBehaviorpoint(Integer behaviorpoint) {
		this.behaviorpoint = behaviorpoint;
	}

	public Boolean getContributionBehavior() {
		return contributionBehavior;
	}

	public void setContributionBehavior(Boolean contributionBehavior) {
		this.contributionBehavior = contributionBehavior;
	}

	public String getBehaviorcname() {
		return behaviorcname;
	}

	public void setBehaviorcname(String behaviorcname) {
		this.behaviorcname = behaviorcname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
}
