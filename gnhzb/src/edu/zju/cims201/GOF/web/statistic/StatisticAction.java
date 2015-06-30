package edu.zju.cims201.GOF.web.statistic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.logging.SysBehaviorLogService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.web.CrudActionSupport;
import edu.zju.cims201.GOF.web.message.dwr.ChatManager;


@Namespace("/statistic")
//定义名为reload的result重定向到message.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "statistic.action", type = "redirect"),
	@Result(name = "typerank", location = "/WEB-INF/content/statistic/statistic-typerank.jsp")})
public class StatisticAction extends CrudActionSupport<Message> implements ServletResponseAware ,ServletRequestAware{
	


	@Resource(name="userServiceImpl")
	private UserService userService;
	@Resource(name="knowledgeServiceImpl")
	private KnowledgeService ks;
	@Resource(name="ktypeServiceImpl")
	private KtypeService ktypeService;
	@Resource(name="sysBehaviorLogServiceImpl")
	SysBehaviorLogService sysBehaviorLogService;
	
	private HttpServletRequest request;
	private String screenwidth;
	private String screenheight;
	private Map ktm = new HashMap();
	private List ktl = new ArrayList();
	private Map ktrendm = new HashMap();
	private List ktrendl = new ArrayList();
	private Map pktm = new HashMap();
	private List pktl = new ArrayList();
	private String totalUsers;
	//系统知识总量
	private String totalkg ;
	//系统当前在线人数
	private String onlineUsers;
	//当前用户上传知识量
	private String currentUserKg ;
	//当前用户登录次数
	private String currentUserlg;
	//当前用户发布知识平均质量
	private String currentUserkgs;
	 private SystemUser user;

	 
   /**
    * 按知识类型分类
    * @return
    * @throws Exception
    */
public String knowledgetyperank() throws Exception
	{
		 user = userService.getUser();
		
		

		//系统当前在线人数
		Integer numOfOnline = ChatManager.users.size();
		
		//系统知识总量Query qkg =kgs.createQueryHQL("select count(o.id) from Metaknowledge o where o.id>0 and o.kstatus.id>1 ",null);
		Long total_kg=ks.getTotalNum();

		//系统总注册用户
		List<SystemUser> list_users=userService.getAllUsers();
		//当前用户上传知识量
		Long user_total_kg=ks.getTotalKnowledgeNum_user(user.getId());
		//当前用户登录次数
//		Query quserlg =dao.createQueryHQL("select count(o.id) from SysBehaviorlog o where o.user.userid = "+user.getUserid()+" and o.objecttype='userlogin'", null);
		//当前用户发布知识平均质量
		Double userAverageScore=ks.getUserAverageScore(user.getId());
//		Query quserkgs =kgs.createQueryHQL("select avg(p.esimple) from Metaknowledge o, Kspointep p  where o.id = p.metaknowledge.id and  o.user.userid="+user.getUserid()+" and o.kstatus.id>1 ", null);
		
		//按知识类型分类统计
		List<Knowledgetype> list_knowledgetypes=ktypeService.listKnowledgetype();
		 ktm = new HashMap();
		 ktl = new ArrayList();

		for(Knowledgetype kt :list_knowledgetypes){			
			
			ktm.put(kt.getKnowledgeTypeName(),ks.getTotalKnowledgeNum_ktype(kt.getId()).toString());
			ktl.add(kt.getKnowledgeTypeName());	
		}
		

		
		//系统总注册用户
		 totalUsers = String.valueOf(userService.getAllUsers().size());
		//系统知识总量
		totalkg = ks.getTotalNum().toString();
		//系统当前在线人数
		 onlineUsers = String.valueOf(numOfOnline);
		//当前用户上传知识量
		 currentUserKg = String.valueOf(user_total_kg);
		//当前用户登录次数
		// currentUserlg =String.valueOf(sysBehaviorLogService.countBehavior(user.getId(), 1L));
		
		//当前用户发布知识平均质量
		 currentUserkgs ="";
		if(null!=userAverageScore){
			 currentUserkgs = String.valueOf(userAverageScore);
			 if(currentUserkgs.length()>4)
				 currentUserkgs=currentUserkgs.substring(0,4);
		}else{
			 currentUserkgs  = "0";
		}
		
				
		request.setAttribute("ktypewidth", 200);
		request.setAttribute("ktypeheight",190);
		request.setAttribute("ktl", ktl);
		request.setAttribute("ktm", ktm);

	
		request.setAttribute("totalUsers", totalUsers);
		request.setAttribute("totalkg", totalkg);
		request.setAttribute("onlineUsers", onlineUsers);
		request.setAttribute("currentUserKg", currentUserKg);
		//request.setAttribute("currentUserlg", currentUserlg);
		request.setAttribute("currentUserkgs", currentUserkgs);
		request.setAttribute("user", user);
		
		
		
		
		return "typerank";
	}


@Override	
public String list() throws Exception{
	
		 user = userService.getUser();


		//系统当前在线人数
		Integer numOfOnline = ChatManager.users.size();
		
		//系统知识总量Query qkg =kgs.createQueryHQL("select count(o.id) from Metaknowledge o where o.id>0 and o.kstatus.id>1 ",null);
		Long total_kg=ks.getTotalNum();

		//系统总注册用户
		List<SystemUser> list_users=userService.getAllUsers();
		//当前用户上传知识量
		Long user_total_kg=ks.getTotalKnowledgeNum_user(user.getId());
		//当前用户登录次数
//		Query quserlg =dao.createQueryHQL("select count(o.id) from SysBehaviorlog o where o.user.userid = "+user.getUserid()+" and o.objecttype='userlogin'", null);
		//当前用户发布知识平均质量
		Double userAverageScore=ks.getUserAverageScore(user.getId());
//		Query quserkgs =kgs.createQueryHQL("select avg(p.esimple) from Metaknowledge o, Kspointep p  where o.id = p.metaknowledge.id and  o.user.userid="+user.getUserid()+" and o.kstatus.id>1 ", null);
		
		//按知识类型分类统计
		List<Knowledgetype> list_knowledgetypes=ktypeService.listKnowledgetype();
		 ktm = new HashMap();
		 ktl = new ArrayList();

 		for(Knowledgetype kt :list_knowledgetypes){			
			
			ktm.put(kt.getKnowledgeTypeName(),ks.getTotalKnowledgeNum_ktype(kt.getId()).toString());
			ktl.add(kt.getKnowledgeTypeName());	
		}
 		
 		//个人上传知识按知识类型分类统计
		 pktm = new HashMap();
		 pktl = new ArrayList();

 		for(Knowledgetype kt :list_knowledgetypes){			
			pktm.put(kt.getKnowledgeTypeName(), ks.getTotalKnowledgeNum_ktype_user(kt.getId(), user.getId()).toString());
			pktl.add(kt.getKnowledgeTypeName());
			
		}
 		
 		
 		//系统知识上传走势
 		List hqltl = new ArrayList();
 		List monthl = new ArrayList();
 		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
 		String dateString1="";
        String dateString2="";    
	    
	    
	    for(int i=0;i<6;i++){
	    	Calendar cal = Calendar.getInstance();
	    	cal.add(Calendar.MONTH, -i);
	    	
	    	switch (cal.get(Calendar.MONTH)) {
			case 0:
				monthl.add("一月");
				break;
			case 1:
				monthl.add("二月");
				break;
			case 2:
				monthl.add("三月");
				break;
			case 3:
				monthl.add("四月");
				break;
			case 4:
				monthl.add("五月");
				break;
			case 5:
				monthl.add("六月");
				break;
			case 6:
				monthl.add("七月");
				break;
			case 7:
				monthl.add("八月");
				break;
			case 8:
				monthl.add("九月");
				break;
			case 9:
				monthl.add("十月");
				break;
			case 10:
				monthl.add("十一月");
				break;
			case 11:
				monthl.add("十二月");
				break;		

			default:
				break;
			}
	    	
			cal.set(Calendar.DATE, 1);	
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR, -12);
			cal.set(Calendar.SECOND, 0);
			 dateString1 = formatter.format(cal.getTime());
			 
			 Date date1= cal.getTime();
	
			
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DATE, 1);
			cal.add(Calendar.DATE, -1);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
		     dateString2 = formatter.format(cal.getTime());
		     
	
		     Date date2= cal.getTime();
		//    String hqltime =  " and o.uploadtime <= ? and o.uploadtime>=? ";
		     System.out.println("date1="+date1.toLocaleString());
		     System.out.println("date2="+date2.toLocaleString());
			Object[] qureyParams = new Object[]{date1,date2};		    
		    hqltl.add(qureyParams);
	    }
	    
		 ktrendm = new HashMap();
		 ktrendl = new ArrayList();

 		for(int i=5;i>=0;i--){	
 			
			Long num_timeslot=ks.getTotalKnowledgeNum_timeslot((Object[]) hqltl.get(i));
			if(null!=num_timeslot){
				ktrendm.put(monthl.get(i), num_timeslot.toString());
			}else{
				ktrendm.put(monthl.get(i), 0);
			}
			
			ktrendl.add(monthl.get(i));
			
		}
 		
 		
 		
 		//系统总注册用户
		 totalUsers = String.valueOf(userService.getAllUsers().size());
		//系统知识总量
		totalkg = ks.getTotalNum().toString();
		//系统当前在线人数
		 onlineUsers = String.valueOf(numOfOnline);
		//当前用户上传知识量
		 currentUserKg = String.valueOf(user_total_kg);
		//当前用户登录次数
		 currentUserlg =String.valueOf(sysBehaviorLogService.countBehavior(user.getId(), 1L));
		//当前用户发布知识平均质量
		 currentUserkgs ="";
		if(null!=userAverageScore){
			if(String.valueOf(userAverageScore).length()>4)
			 currentUserkgs = String.valueOf(userAverageScore).substring(0,4);
			else
			currentUserkgs = String.valueOf(userAverageScore);	
		}else{
			 currentUserkgs  = "0";
		}
		double d_type=(Integer.parseInt(screenwidth)-150)*0.4;
		int ktypewidth=(int)d_type;
		double d_trend=(Integer.parseInt(screenwidth)-150)*0.8;
		int ktrendwidth=(int)d_trend;
		request.setAttribute("ktypewidth", ktypewidth);
		request.setAttribute("ktypeheight",(int)(ktypewidth*0.8));
		request.setAttribute("ktrendwidth", ktrendwidth);
		request.setAttribute("ktrendheight",(int)(ktrendwidth*0.5));
		
		request.setAttribute("ktl", ktl);
		request.setAttribute("ktm", ktm);

		request.setAttribute("ktrendm", ktrendm);
		request.setAttribute("ktrendl", ktrendl);
		
		request.setAttribute("pktl", pktl);
		request.setAttribute("pktm", pktm);
		
//		System.out.println("ktl"+ktl.size()+"_ktm"+ktm.size()+"_ktrendm"+ktrendm.size()+"ktrendl"+ktrendl.size()+"pktl"+pktl.size()+"");

		
		request.setAttribute("totalUsers", totalUsers);
		request.setAttribute("totalkg", totalkg);
		request.setAttribute("onlineUsers", onlineUsers);
		request.setAttribute("currentUserKg", currentUserKg);
		request.setAttribute("currentUserlg", currentUserlg);
		request.setAttribute("currentUserkgs", currentUserkgs);
		request.setAttribute("user", user);
		

      return "list";
}
		
		

		@Override
		public String delete() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String input() throws Exception {
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

		
		public void setServletResponse(HttpServletResponse arg0) {
			// TODO Auto-generated method stub
			
		}

	
		public Message getModel() {
			// TODO Auto-generated method stub
			return null;
		}



	
		public void setServletRequest(HttpServletRequest arg1) {
			// TODO Auto-generated method stub
			this.request=arg1;
			
		}
		public void setRequest(HttpServletRequest request) {
			this.request = request;
		}



		public void setKtm(Map ktm) {
			this.ktm = ktm;
		}



		public void setKtl(List ktl) {
			this.ktl = ktl;
		}



		public void setKtrendm(Map ktrendm) {
			this.ktrendm = ktrendm;
		}



		public void setKtrendl(List ktrendl) {
			this.ktrendl = ktrendl;
		}



		public void setPktm(Map pktm) {
			this.pktm = pktm;
		}



		public void setPktl(List pktl) {
			this.pktl = pktl;
		}



		public void setTotalUsers(String totalUsers) {
			this.totalUsers = totalUsers;
		}



		public void setTotalkg(String totalkg) {
			this.totalkg = totalkg;
		}



		public void setOnlineUsers(String onlineUsers) {
			this.onlineUsers = onlineUsers;
		}



		public void setCurrentUserKg(String currentUserKg) {
			this.currentUserKg = currentUserKg;
		}



		public void setCurrentUserlg(String currentUserlg) {
			this.currentUserlg = currentUserlg;
		}



		public void setCurrentUserkgs(String currentUserkgs) {
			this.currentUserkgs = currentUserkgs;
		}



		public void setUser(SystemUser user) {
			this.user = user;
		}




		public String getScreenwidth() {
			return screenwidth;
		}


		public void setScreenwidth(String screenwidth) {
			this.screenwidth = screenwidth;
		}


		public String getScreenheight() {
			return screenheight;
		}


		public void setScreenheight(String screenheight) {
			this.screenheight = screenheight;
		}

		
		
		
		
	}
