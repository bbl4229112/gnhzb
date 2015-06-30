package edu.zju.cims201.GOF.web.statistic;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.stringtree.json.JSONWriter;

import  edu.zju.cims201.GOF.dao.common.QueryResult;




import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.UserDTO;
import edu.zju.cims201.GOF.service.statistic.StatisticService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;

@Namespace("/userranking")
//定义名为reload的result重定向到message.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "userranking.action", type = "redirect")})
public class UserrankingAction  extends CrudActionSupport<Message> implements ServletRequestAware,ServletResponseAware{
	
	@Resource(name="statisticServiceImpl")
	StatisticService statisticService;
	@Resource(name="userServiceImpl")
	private UserService userservice;
	
	private String rankType;//totoalscore  contributionscore
	private String model_;//week lasttwoweek lastweek等
	private int size;
	private int index;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String rankAsTable;
	
	
	
	
	
	
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	//userParticipationRanking
	//返回当前用户总数，用于统计分析中的分页
	public void getUserCount()
	{String hql = " from SystemUser u where u.id<>1 ";
	  String hql_pre=" u , u.totoalscore ";
	  SystemUser currentUser = userservice.getUser();
	  QueryResult<SystemUser> qr=  statisticService.createQuery(hql, hql_pre, index, size, null,currentUser.getId());
	 Long totaluser= qr.getTotalrecord();
	 JSONUtil.write(response, totaluser);
	}
	
	public String list() throws Exception {
		PageDTO pageDTO=new PageDTO();
		SystemUser currentUser = userservice.getUser();
		String orderby ="";
		String rankName="";
        
        if(rankType.equals("contributionscore"))
          {
        	orderby=getOrderby(model_,"contributionscore");
            rankName="用户贡献度排行";
            }
        else{
        	 orderby = getOrderby(model_,"totoalscore");
             rankName="用户参与度排行";}
		String hql = " from SystemUser u where u.id<>1  order by "+orderby +" desc";
		String hql_pre=" u, "+orderby;

		QueryResult<SystemUser> qr=  statisticService.createQuery(hql, hql_pre, index, size, null,currentUser.getId());
		
		List<SystemUser> list_user=qr.getResultlist();
	//如果以列表形式，则返回json数据
		if(null!=rankAsTable&&rankAsTable.equals("yes"))
		{
			List<ObjectDTO> list_userDTO=new ArrayList<ObjectDTO>();
			for(int i=0;(list_user.size()>index+size)?i<index+size:i<list_user.size();i++)
			{   SystemUser u=(SystemUser)list_user.get(i);
				ObjectDTO o=new ObjectDTO();
				o.setId(new Long(u.getScoreTemp()));
				o.setName(u.getName());
				list_userDTO.add(o);
			}
			 PrintWriter out = this.response.getWriter();
				JSONWriter jw = new JSONWriter();
				String str = jw.write(list_userDTO);
				out.println(str);
			return null;
			
		}
	//以上 如果以列表形式，则返回json数据
		
	    pageDTO.setData(list_user);
	    pageDTO.setTotal(qr.getTotalrecord());

		Long totalPage;
		if(size != 0) {
			if(qr.getTotalrecord()%size == 0){
				totalPage = qr.getTotalrecord()/size;
			}else{
				totalPage = qr.getTotalrecord()/size+1;
			}
			pageDTO.setTotalPage(totalPage);
			
		}	
	    
	   
		//当前用户排名
		String currentRank =qr.getCurrentRank();

		request.setAttribute("currentRank", currentRank);
		request.setAttribute("rankName", rankName);
		request.setAttribute("isAdmin", false);	
		request.setAttribute("currentUser", currentUser);
		request.setAttribute("pageDTO", pageDTO);
		
		   
		
		return "list";
		

		
	}

	/**
	 * 返回门户首页中的用户排行。四个用户
	 * @author panlei
	 * 20130521
	 * */
	public String listTopFourUsers() throws Exception {
		PageDTO pageDTO=new PageDTO();
        String orderby=getOrderby(model_,"contributionscore");
		String hql = " from SystemUser u where u.id<>1  order by "+orderby +" desc";
		String hql_pre=" u, "+orderby;

		QueryResult<SystemUser> qr=  statisticService.createQuery(hql, hql_pre, index, size, null,1L);
		
		List<SystemUser> list_user=qr.getResultlist();
	
		List<UserDTO> list_userDTO=new ArrayList<UserDTO>();
		for(int i=0;(list_user.size()>index+size)?i<index+size:i<list_user.size();i++)
		{   SystemUser u=(SystemUser)list_user.get(i);
			UserDTO udto=new UserDTO();
			udto.setId(new Long(u.getScoreTemp()));
			udto.setName(u.getName());
			udto.setPicturePath(u.getPicturePath());
			udto.setEmail(u.getEmail());
			list_userDTO.add(udto);
		}
		PrintWriter out = this.response.getWriter();
		JSONWriter jw = new JSONWriter();
		String str = jw.write(list_userDTO);
		
		
		out.println(str);
		
		return null;
	}
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}


	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public Message getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/**
	 * 根据时间段设置排序字段
	 * @param model
	 * @param type 排行类型（贡献度or参与度）
	 * @return
	 */
	private String getOrderby(String model, String type) {
		
		String orderby =""; 
	    
		if(null!=type&&type.equals("totoalscore")){
			
		      if (null != model && model.equals("week")) {        orderby =" u.weekpscore ";}
		      if (null != model && model.equals("lasttwoweek")) { orderby =" u.lasttwoweekpscore ";		}
		      if (null != model && model.equals("lastweek")) {    orderby =" u.lastweekpscore ";} 
		      if (null != model && model.equals("month")) {       orderby =" u.monthpscore ";}
		      if (null != model && model.equals("lastmonth")) {   orderby =" u.lastmonthpscore ";			}
              if (null != model && model.equals("year")) {		  orderby =" u.yearpscore "; 			}
		      if (null != model && model.equals("lastyear")) {    orderby =" u.lastyearpscore ";	}
	       	  if (null != model && model.equals("all")) {         orderby =" u.totoalscore ";
					}
	      }
		
		else if(null!=type&&type.equals("contributionscore")){
			
			if (null != model && model.equals("week")) {          orderby =" u.weekcscore ";}
			if (null != model && model.equals("lasttwoweek")) {   orderby =" u.lasttwoweekcscore ";		}
			if (null != model && model.equals("lastweek")) {      orderby =" u.lastweekcscore ";   } 
			if (null != model && model.equals("month")) {         orderby =" u.monthcscore ";}
			if (null != model && model.equals("lastmonth")) {     orderby =" u.lastmonthcscore ";	}
            if (null != model && model.equals("year")) {		  orderby =" u.yearcscore "; 			}
			if (null != model && model.equals("lastyear")) {      orderby =" u.lastyearcscore ";   }
			if (null != model && model.equals("all")) {           orderby =" u.contributionscore "; }
			
		}

		return orderby;
	}

	


	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getModel_() {
		return model_;
	}

	public void setModel_(String model_) {
		this.model_ = model_;
	}

	public String getRankType() {
		return rankType;
	}

	public void setRankType(String rankType) {
		this.rankType = rankType;
	}

	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

	public String getRankAsTable() {
		return rankAsTable;
	}

	public void setRankAsTable(String rankAsTable) {
		this.rankAsTable = rankAsTable;
	}


	
	
}
