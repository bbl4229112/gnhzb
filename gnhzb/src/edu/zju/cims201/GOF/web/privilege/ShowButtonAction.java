package edu.zju.cims201.GOF.web.privilege;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springside.modules.orm.Page;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.hibernate.pojo.ApprovalFlow;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlow;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.ShowButtonDTO;
import edu.zju.cims201.GOF.service.approvalFlow.ApprovalFlowService;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.comment.CommentService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.onto.OntoCommentService;
import edu.zju.cims201.GOF.service.systemFile.FileService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.springsecurity.BorrwerChecker;
import edu.zju.cims201.GOF.springsecurity.SQLTriplesProvider;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;





@Namespace("/privilege")

public class ShowButtonAction extends ActionSupport implements  ServletResponseAware{

	
	private long knowledgeId;
	
	private String operationName;
	
	
	private SQLTriplesProvider triplesProvider;
	
	private KnowledgeService knowledgeService;
	
	private UserService userService;
	
	private ApprovalFlowService approvalFlowService;
	
	private BorrowFlowService borrowFlowService;
	
	
	private BorrwerChecker borrwerChecker;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "messageServiceImpl")
	private MessageService mService;
	private HttpServletResponse response;
	@Resource(name = "commonDao")
	private CommonDao commondao;
	@Resource(name = "ontoCommentServiceImpl")
	private OntoCommentService ontocommentservice;
	@Resource(name = "commentServiceImpl")
	private CommentService commentservice;
	 /**
	  * 用户自身能
	  * @author zju
	  * @return
	  */
	 
	 
	 public String whetherShowBestAnswerButton(){
		 
		 if(this.knowledgeId==0)
			 return null;
		 boolean result=true;
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		
		 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		 SystemUser user=this.userService.getUser();
		 if(!user.getEmail().equals(knowledge.getUploader().getEmail())){
			 result=false;
			 buttonDTO.setShow(result);
			 JSONUtil.write(response, buttonDTO);
			 return null;
		 }
		 
	
			
		buttonDTO.setShow(result);
		JSONUtil.write(response, buttonDTO);
		return null; 	 
		 
	 }
	 /**
	  * 根据知识权限决定是否显示
	  * @author zju
	  * @return
	  */
	 
	 
	 private String whetherShowKnowledgeButton(long Kid,String operationName){
		 
		 if(Kid==0||operationName==null||operationName.equals(""))
			 return null;
	
		 
		 SecurityContext securityContext = SecurityContextHolder.getContext();
		 Collection<GrantedAuthority> grantedAuthorities=securityContext.getAuthentication().getAuthorities();
		 
		 Collection<ConfigAttribute> needs=this.triplesProvider.getKnowledgeRelatedTriples(Kid, operationName);
		 
		 
		 boolean result=false;
		 if(needs!=null){
			 for(GrantedAuthority authority:grantedAuthorities){
				 for(ConfigAttribute needed:needs ){
					 if(authority.getAuthority().equals(needed.getAttribute())){
						 result=true;
						 break;
					 }
					 if(result)
						 break;
				 }
				 if(result)
					 break; 
					 
			 }
		 }
		 
		 if(!result){
			 if(operationName.trim().equals(PrivilegeConstants.USER_DOWNLOAD_KNOWLEDGE)){
				 SystemUser currentUser=this.userService.getUser();
				 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(Kid);
				
				
				 if(currentUser==null||knowledge==null)
					 result=false;
				 else{
					 //我这边没更新，borrwerChecker中有check4download方法，调用之
					// System.out.println("borrwerCheckerborrwerCheckerborrwerCheckerborrwerChecker");
					 if(this.borrwerChecker.check4download(currentUser, knowledge))
						 result=true; 
				
				}
				 
			 }
		 }
		 
		
		 
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		 buttonDTO.setShow(result);
		 JSONUtil.write(response, buttonDTO);
		 
		 
		 return null;
		 
	 }
	 
	 
	 /**
	  * 是否显示查看审批按钮
	  * @author zju
	  * @return
	  */
	 
	 public String whetherShowViewApprovalBtn(){
		 if(this.knowledgeId==0)
			 return null;
		 boolean result=false;
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		
		 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		 SystemUser user=this.userService.getUser();
		 if(!user.getEmail().equals(knowledge.getUploader().getEmail())){
			 buttonDTO.setShow(result);
			 JSONUtil.write(response, buttonDTO);
			 return null;
		 }
		 
		ApprovalFlow flow = this.approvalFlowService.getApprovalFlow(knowledge); 
		if(flow!=null){
			 result=true;
			 buttonDTO.setShow(result);
			 JSONUtil.write(response, buttonDTO);
			 return null;
		}
			
		buttonDTO.setShow(result);
		JSONUtil.write(response, buttonDTO);
		return null; 	 
		 
	 }
	 
	 /**
	  * 是否显示发起审批按钮
	  * @author zju
	  * @return
	  */
	 
	 public String whetherShowInitiateApprovalBtn(){
		 
		 if(this.knowledgeId==0)
			 return null;
		 boolean result=false;
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		 if(knowledge.getKtype().getName().equals("Avidmknowledge")||knowledge.getKtype().getName().equals("Qualityknowledge")||knowledge.getKtype().getName().equals(Constants.HEADLINE6NAME)||knowledge.getKtype().getName().equals(Constants.HEADLINE1NAME)||knowledge.getKtype().getName().equals(Constants.HEADLINE2NAME))
		 {
			 buttonDTO.setShow(result);
			 JSONUtil.write(response, buttonDTO);
			 return null;
			 
			 
		 }
		 if(null==knowledge.getDomainnode())
		 {

			 buttonDTO.setShow(result);
			 JSONUtil.write(response, buttonDTO);
			 return null;
		 
			 
		 }
		 SystemUser user=this.userService.getUser();
		 if(!user.getEmail().equals(knowledge.getUploader().getEmail())){
			 buttonDTO.setShow(result);
			 JSONUtil.write(response, buttonDTO);
			 return null;
		 }
		 ApprovalFlow flow = this.approvalFlowService.getApprovalFlow(knowledge); 
			if(flow==null){
				 result=true;
				 buttonDTO.setShow(result);
				 JSONUtil.write(response, buttonDTO);
				 return null;
			}
		buttonDTO.setShow(result);
		JSONUtil.write(response, buttonDTO);
		return null;  
		 
	 }
	 /**
	  * 是否显示修改域别按钮
	  * @author zju
	  * @return  
	  */
	 
	 public String whetherShowModifyDTreeBtn()
	 {  
		 if(this.knowledgeId==0)
			 return null;
		 boolean result=false;
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();		
		 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		 SystemUser user=this.userService.getUser();
		 if(user.getEmail().equals(knowledge.getUploader().getEmail())&&knowledge.getDomainnode()!=null)
		 {
			 result=true;
		 }
		 buttonDTO.setShow(result);
			JSONUtil.write(response, buttonDTO);
			return null;  
	 }
	 /**
	  * 是否显示申请修改域别按钮  只要登录进来就都可以显示。
	  * @author panlei
	  * @return  
	  */
	 
	 public String whetherShowapplyModifyDTreeBtn()
	 {  
		 if(this.knowledgeId==0)
			 return null;
		 boolean result=true;
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();		
		 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		 SystemUser user=this.userService.getUser();
		 
		 buttonDTO.setShow(result);
		 JSONUtil.write(response, buttonDTO);
		 return null;  
	 }
	 /**
	  * 是否显示审批改域按钮
	  * @author panlei
	  * @return
	  */
	 
	 public String whetherShowapprovalModifyDTreeBtn()
	 {  
		 if(this.knowledgeId==0)
			 return null;
		 boolean result=false;
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();		
		 MetaKnowledge knowledge=knowledgeService.getMetaknowledge(knowledgeId);
		 SystemUser user=this.userService.getUser();
		 List<Message> list = mService.listUnAnsweredKnowledgeMessageByUserIdAndKnowledgeId(user.getId(),(Long)knowledgeId);
		 
		 if(list.size() != 0)
		 {
			 result=true;
		 }
		 buttonDTO.setShow(result);
			JSONUtil.write(response, buttonDTO);
			return null;  
	 }
	 /**
	  * 是否显示修改类别按钮
	  * @author zju
	  * @return
	  */
	 
	 public String whetherShowModifyCTreeBtn()
	 {    //System.out.println("tetetetetetetet");
		 if(this.knowledgeId==0)
			 return null;
		 boolean result=false;
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();		
		 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		 SystemUser user=this.userService.getUser();
		 if(user.getEmail().equals(knowledge.getUploader().getEmail()))
		 {
			 result=true;
		 }
		 buttonDTO.setShow(result);
			JSONUtil.write(response, buttonDTO);
			return null;  
	 }
	 /**
	  * 是否显示审批按钮，审批人
	  * @author zju
	  * @return
	  */
	 
	 public String whetherShowDoApprovalBtn(){
		 if(this.knowledgeId==0)
			 return null;
		 boolean result=false;
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		
		 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		 SystemUser user=this.userService.getUser();
		 List<MetaKnowledge> list=this.approvalFlowService.listNeededApprovalKnowledge(user);
		 if(list.contains(knowledge))
			 result=true;
		 Long flowid=null;
//		 if(result){
//			 ApprovalFlow approvalFlow=this.approvalFlowService.getApprovalFlow(knowledge);
//			 flowid = approvalFlow.getId();
//		 }
//		 buttonDTO.setApprovalFlowId(flowid);
		 
		 buttonDTO.setShow(result);
			JSONUtil.write(response, buttonDTO);
			return null;  
			
	 }
	 
	 
	 /**
	  * 是否显示下载知识按钮
	  * @author zju
	  * @return
	  */
		 
	 
	 public String whetherShowDownloadKnowledgeBtn(){
		 if(this.knowledgeId==0)
			 return null; 
		 //判断知识是否有原文 如果没有原文也无法下载 返回null
			if(null==fileService.getKnowledgeSourceFile(knowledgeId))
				return null;
		 return this.whetherShowKnowledgeButton(knowledgeId, PrivilegeConstants.USER_DOWNLOAD_KNOWLEDGE);	 
		 
	 }
	 
	 
	 /**
	  * 是否显示只有超级管理员可以操作的按钮
	  * @return
	  */
	 
	 public String whetherShowSuperAdminBtn(){
		 
		 SecurityContext securityContext = SecurityContextHolder.getContext();
		 String userName=securityContext.getAuthentication().getName();
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		 if(userName.equals(Constants.SUPERADMIN))
			 buttonDTO.setShow(true);
		JSONUtil.write(response, buttonDTO);
		 
		 
		 return null;
		 
	 }
	 public String whetherShowshyufinalExplainButton()
	 {
		 SecurityContext securityContext = SecurityContextHolder.getContext();
		 String userName=securityContext.getAuthentication().getName();
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		 if(userName.equals(Constants.SUPERADMIN))
			 buttonDTO.setShow(true);
		 else
		 {  
			 if(userName.equals(ontocommentservice.getOntoBuild(knowledgeId).getCreater().getEmail()))
			 buttonDTO.setShow(true);
		 } 
		 
		JSONUtil.write(response, buttonDTO);
		 return null;
	 }
	 public String whetherShowontocommentdeleteButton()
	 {
		 SecurityContext securityContext = SecurityContextHolder.getContext();
		 String userName=securityContext.getAuthentication().getName();
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		 if(userName.equals(Constants.SUPERADMIN))
			 buttonDTO.setShow(true);
		 else
		 {  
			 if(userName.equals(ontocommentservice.getComment(knowledgeId).getCommenter().getEmail()))
			 buttonDTO.setShow(true);
		 } 
		 
		JSONUtil.write(response, buttonDTO);
		 return null;
	 }
	 public String whetherShowcommentdeleteButton()
	 { ShowButtonDTO buttonDTO=new ShowButtonDTO();
		 if(commentservice.getComment(knowledgeId).getIsBest()!=1){
		 SecurityContext securityContext = SecurityContextHolder.getContext();
		 String userName=securityContext.getAuthentication().getName();
		
		 if(userName.equals(Constants.SUPERADMIN))
			 buttonDTO.setShow(true);
		 else
		 {  
			 if(userName.equals(commentservice.getComment(knowledgeId).getCommenter().getEmail()))
			 buttonDTO.setShow(true);
		 } 
		 }
		JSONUtil.write(response, buttonDTO);
		 return null;
	 }

	 public String whetherShowSystemManagerBtn(){
		 
		 SecurityContext securityContext = SecurityContextHolder.getContext();
		 String userName=securityContext.getAuthentication().getName();
		 
		 SystemUser user=this.userService.getUser(userName);
		 boolean isAdmin=this.userService.isAdmin(user);
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		 buttonDTO.setShow(isAdmin);
		 JSONUtil.write(response, buttonDTO);
		 return null;
		 
	 }
	 
	 
	 public String whetherShowAdminBorrowFlowBtn(){
		 SystemUser currentUser=this.userService.getUser();
		 System.out.println(knowledgeId);
		 if(this.knowledgeId==0)
			 return null;
		 boolean ret=false;
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		 Page<BorrowFlow> page = this.borrowFlowService.listAllFlow4Admin(currentUser, knowledge,new Page<BorrowFlow>(1));
		 
		 List<BorrowFlow> list = page.getResult();
		 for(BorrowFlow borrowFlow:list){
			 System.out.println(borrowFlow.getKnowledge().getTitlename());
		 }
		 if(page!=null&&page.getTotalCount()>0){
			 ret=true;
		 }
		 buttonDTO.setShow(ret);
		 //System.out.println("sssssssssssssssssssssssssssssssssssssss:"+ret);
		 JSONUtil.write(response, buttonDTO);
		 return null;
	 }
	 
	 public String whetherShowDoBorrowingBtn(){
		 SystemUser currentUser=this.userService.getUser();
		 if(this.knowledgeId==0)
			 return null;
		 boolean ret=false;
		 ShowButtonDTO buttonDTO=new ShowButtonDTO();
		 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		 Page<BorrowFlow> page = this.borrowFlowService.listNeededFlow4Borrower(currentUser, new Page<BorrowFlow>(1));
		 int count=(int)page.getTotalCount();
		 if(count<=0){
			 JSONUtil.write(response, buttonDTO);
			 return null;
		 }
		 Page<BorrowFlow> allpage = this.borrowFlowService.listNeededFlow4Borrower(currentUser, new Page<BorrowFlow>(count));
		 if(page!=null&&page.getTotalCount()>0){
			 for(BorrowFlow bf:allpage.getResult()){
				 MetaKnowledge mk=bf.getKnowledge();
				 if(mk!=null&&mk.getId().equals(knowledge.getId())){
					 ret=true;
					 buttonDTO.setBorrowFlowId(bf.getId());
					 break;
				 }
			 }
			 
		 }
		 buttonDTO.setShow(ret);
		 JSONUtil.write(response, buttonDTO);
		 return null;
	 }
	 
	 
	 public String whetherShowAVIDMBtn()
	 {    SystemUser currentUser=this.userService.getUser();
		 String url="false";
		 if(this.knowledgeId==0)
			 return null;
		 MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeId);
		 if(null!=knowledge&&knowledge.getKtype().getName().equals("Avidmknowledge"))
		 {
			  try {
			Knowledge k=(Knowledge)commondao.findById(Class.forName("edu.zju.cims201.GOF.hibernate.pojo.Avidmknowledge"), knowledgeId);
			try {
				
				String   avidmdocumentiid=PropertyUtils.getProperty(k, "avidmdocumentiid").toString();
				String   avidmfileiid=PropertyUtils.getProperty(k, "avidmfileiid").toString();
				String   avidmversioniid=PropertyUtils.getProperty(k, "avidmversioniid").toString();
				String   avidmproductiid=PropertyUtils.getProperty(k, "avidmproductiid").toString();
				String   avidmhost=PropertyUtils.getProperty(k, "avidmhost").toString();
				String username=currentUser.getEmail();
				String[] avidmhosts=Constants.AVIDMHOSTPROPERTY.split("#");
				boolean havehost=false;
				for(String avidmhosttemp:avidmhosts)
				{    System.out.println(avidmhosttemp);
					String[] avidmproperty=avidmhosttemp.split("&");
					if(avidmproperty.length==4&&null!=avidmproperty[1]&&avidmproperty[0].equals(avidmhost))
					{
						avidmhost=avidmproperty[1];
						havehost=true;
						break;
						
					}
				}
				if(havehost)
				url="http://"+avidmhost+"/avidm/customize/knowledge/loginIntoAvidm.jsp?UserName="+username+"&documentIID="+avidmdocumentiid+"&productIID="+avidmproductiid+"&fileIID="+avidmfileiid+"&versionIID="+avidmversioniid;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			  
			  } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		 
		 }
		 
		 if(null!=knowledge&&knowledge.getKtype().getName().equals("Qualityknowledge"))
		 {
			  try {
			Knowledge k=(Knowledge)commondao.findById(Class.forName("edu.zju.cims201.GOF.hibernate.pojo.Qualityknowledge"), knowledgeId);
			try {
				String qualityhost=Constants.QUALITYHOST;
				String   card_id="";
				card_id=PropertyUtils.getProperty(k, "card_id").toString();
				String username=currentUser.getEmail();
		        url="http://"+qualityhost+"/AQB/AQCSSOLogin_A1Detail?iv-user="+username+"&iv-cardid="+card_id;
				//url="http://"+qualityhost+"/avidm/customize/knowledge/loginIntoAvidm.jsp?UserName="+username+"&documentIID="+avidmdocumentiid+"&productIID="+avidmproductiid+"&fileIID="+avidmfileiid+"&versionIID="+avidmversioniid;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			  
			  } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		 
		 }
		 
		 JSONUtil.write(response, url);
		 return null;
	 }

	


	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
		
	}


	public long getKnowledgeId() {
		return knowledgeId;
	}


	public void setKnowledgeId(long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}


	public String getOperationName() {
		return operationName;
	}


	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}


	public SQLTriplesProvider getTriplesProvider() {
		return triplesProvider;
	}

	@Autowired
	public void setTriplesProvider(SQLTriplesProvider triplesProvider) {
		this.triplesProvider = triplesProvider;
	}


	public KnowledgeService getKnowledgeService() {
		return knowledgeService;
	}

	@Autowired
	public void setKnowledgeService(KnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}


	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public ApprovalFlowService getApprovalFlowService() {
		return approvalFlowService;
	}

	@Autowired
	public void setApprovalFlowService(ApprovalFlowService approvalFlowService) {
		this.approvalFlowService = approvalFlowService;
	}

	public BorrowFlowService getBorrowFlowService() {
		return borrowFlowService;
	}
	@Autowired
	public void setBorrowFlowService(BorrowFlowService borrowFlowService) {
		this.borrowFlowService = borrowFlowService;
	}
	
	public BorrwerChecker getBorrwerChecker() {
		return borrwerChecker;
	}
	
	@Autowired
	public void setBorrwerChecker(BorrwerChecker borrwerChecker) {
		this.borrwerChecker = borrwerChecker;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	
	

}
