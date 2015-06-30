package edu.zju.cims201.GOF.web.knowledge;





import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.aop.message.BorrowFlowMessage;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlow;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlowContent;
import edu.zju.cims201.GOF.hibernate.pojo.Flow;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.rs.dto.BorrowFlowContentDTO;
import edu.zju.cims201.GOF.rs.dto.BorrowFlowDTO;
import edu.zju.cims201.GOF.rs.dto.FlowNodeDTO;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowConstants;
import edu.zju.cims201.GOF.service.borrowFlow.BorrowFlowService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.systemUser.UserService;

import edu.zju.cims201.GOF.service.tree.TreeService;

import edu.zju.cims201.GOF.util.HibernatePorxy;

import edu.zju.cims201.GOF.util.JSONUtil;


@Namespace("/knowledge/approval")
public class BorrowAction extends ActionSupport implements ServletResponseAware{
	
	private long knowledgeID;
	private String expireTime;
	
	private boolean download;
	
	private int times;
	
	private String limited;
	
	private long initId;
	
	private long flowId;
	
	private long flowNodeId;
	
	private long userId;
	
	private String json;
	
	private String result;
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService knowledgeService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "borrowFlowServiceImpl")
	private BorrowFlowService borrowFlowService;
	
	private BorrowFlowMessage borrowFlowMessage;
	
//	private UserService userService;
	
	private HttpServletResponse response;
	
	
	
	
	
	public String createBorrowFlow(){
		SystemUser currentUser=this.userService.getUser();
		//SystemUser currentUser=this.userService.getUser("daifeng@zju.edu.cn");
		if(this.knowledgeID==0){
	     System.out.println("没得到knowledge!!!!!!!!!!!!!!!!!!!!");
			return null;
		}
			
		MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(this.knowledgeID);
		BorrowFlowContent content=new BorrowFlowContent();
		content.setDownload(download);
		System.out.println("limited===++++++++++++++"+limited);
		if(this.limited!=null&&!this.limited.equals("nolimited")){
			System.out.println("limit:true");
			content.setLimited(true);
			if(this.expireTime!=null&&this.limited.equals("timemodel")){
				try {
	
					content.setBorrowTime(dateformateconverter(expireTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				content.setTimes(0);
			}else if(this.times!=0&&this.limited.equals("timesmodel")){
				//content.setBorrowTime(null);
				
				content.setTimes(times);
			}		
		}else if(this.limited!=null||this.limited.equals("nolimited")){
			System.out.println("limit:false");
			content.setLimited(false);
		}
			
		
		BorrowFlow borrowFlow = this.borrowFlowService.createBorrowFlow(currentUser, knowledge, content);
		
		BorrowFlowDTO borrowFlowDTO=new BorrowFlowDTO(borrowFlow);
		
		JSONUtil.write(response, borrowFlowDTO);
		
		return null;
		
		
	}
	private Date dateformateconverter(String datestring) throws ParseException
	{
		datestring=datestring.replaceAll("T", " ");
		datestring=datestring.replaceAll("\"", " ");
		// System.out.println("datestring=============="+datestring);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
        Date date = format.parse(datestring);	
        //System.out.println("date=============="+date);
        return date;
		
	}
	public String deleteLastBorrowFlowNode(){
		//System.out.println("flowNodeId========================"+flowNodeId);
		if(this.flowNodeId==0||this.flowId==0)
			return null;

		FlowNode flowNode = this.borrowFlowService.getFlowNode(flowNodeId);
		this.borrowFlowService.deleteBorrowFlowNode(flowNode);
		
		BorrowFlow borrowFlow = null;
		
		borrowFlow=this.borrowFlowService.getBorrowFlow(flowId);
		BorrowFlowDTO borrowFlowDTO=new BorrowFlowDTO(borrowFlow);
		
		//System.out.println(borrowFlowDTO.getBorrowAdmin().getEmail());
		JSONUtil.write(response, borrowFlowDTO);
		
		return null;
	}
	
	public void listAdmin4init(){
		
		if(knowledgeID==0)
			return;
		MetaKnowledge knowledge=this.knowledgeService.getMetaknowledge(knowledgeID);
		if(knowledge==null)
			return;
		SystemUser uploader=knowledge.getUploader();
		Set<SystemUser> admins = this.borrowFlowMessage.getAdmins(uploader);
		Set<ObjectDTO> dtos=new HashSet<ObjectDTO>();
		for(SystemUser admin:admins){
			ObjectDTO objectDTO=new ObjectDTO(admin.getId(),admin.getName(),null,admin.getEmail());
			dtos.add(objectDTO);
		}
		JSONUtil.write(response, dtos);
			
			
		
	}
	
	
	public String viewBorrowFlowContent(){
		BorrowFlow flow=null;
		if(this.flowId!=0){
			flow=this.borrowFlowService.getBorrowFlow(this.flowId);
		}else	
			return null;
		BorrowFlowContent content = flow.getBorrowFlowContent();
		BorrowFlowContentDTO contentDTO=new BorrowFlowContentDTO(content);
		JSONUtil.write(response, contentDTO);
		return null;
		
		
	}
	
	
	public String viewBorrowFlow(){
		BorrowFlow flow=null;
		MetaKnowledge knowledge=null;
		SystemUser initiator=null;
		if(this.flowId!=0){
			flow=this.borrowFlowService.getBorrowFlow(this.flowId);
		}else{
			if(this.initId==0||this.knowledgeID==0)
				return null;
			knowledge=this.knowledgeService.getMetaknowledge(this.knowledgeID);
			initiator=this.userService.getUser(this.initId);
			flow=this.borrowFlowService.getBorrowFlow(initiator, knowledge);
		}
		if(flow!=null){
			BorrowFlowDTO borrowFlowDTO=new BorrowFlowDTO(flow);
			
			JSONUtil.write(response, borrowFlowDTO);
			
			return null;
		}
		return null;
		
		
	}
	
	
	public String addBorrowFlowNode(){
		BorrowFlow flow=null;
		MetaKnowledge knowledge=null;
		SystemUser initiator=null;
		
		if(this.flowId!=0){
			flow=this.borrowFlowService.getBorrowFlow(this.flowId);
			initiator=flow.getInitiator();
		}	
		else{
			if(this.initId==0||this.knowledgeID==0)
				return null;
			knowledge=this.knowledgeService.getMetaknowledge(this.knowledgeID);
			initiator=this.userService.getUser(this.initId);
			flow=this.borrowFlowService.getBorrowFlow(initiator, knowledge);
		}
		FlowNode flowNode=new FlowNode();
		flowNode.setAddTime(new Date());
		flowNode.setFlow(flow);
		flowNode.setNodeStatus(BorrowFlowConstants.BORROWFLOWNODE_STATUS_BULIDING);
		flowNode.setInitiator(initiator);
		flow.getFlowNodes().add(flowNode);
		if(flow.getBorrowAdmin()==null){
			flow.setBorrowAdmin(this.userService.getUser());
		}
		this.borrowFlowService.saveBorrowFlow(flow);
		
		BorrowFlowDTO borrowFlowDTO=new BorrowFlowDTO(flow);
		
		JSONUtil.write(response, borrowFlowDTO);
		
		return null;
	}
	
	
	public String finishBorrowFlowConstruction(){
		BorrowFlow flow=null;
		System.out.println(flowId);
		if(this.flowId!=0)
			flow=this.borrowFlowService.getBorrowFlow(this.flowId);
		else 
			return null;
		System.out.println(flow);
		List<FlowNode> nodes = flow.getFlowNodes();
		if(nodes.size()==0)
			return null;
		for(FlowNode node: nodes){
			if(node.getApproverORLender()==null)
				return null;
		}
		
		this.borrowFlowService.startFlow(flow);
		
		BorrowFlowDTO borrowFlowDTO=new BorrowFlowDTO(flow);
		
		JSONUtil.write(response, borrowFlowDTO);
		
		return null;
		
	}
	
	public String addBorrower(){
		if(this.flowNodeId==0||this.userId==0)
			return null;
		FlowNode flowNode=borrowFlowService.getFlowNode(flowNodeId);

		SystemUser approver=this.userService.getUser(userId);
		flowNode.setApproverORLender(approver);
		flowNode.setNodeStatus(BorrowFlowConstants.BORROWFLOWNODE_STATUS_WATING_DEAL);
		this.borrowFlowService.saveBorrowFlow(flowNode.getFlow());
		Flow flow = flowNode.getFlow();
		HibernatePorxy<Flow> hp=new HibernatePorxy<Flow>();
		Flow real=hp.getRealEntity(flow);
		if (real  instanceof BorrowFlow) {
			BorrowFlow bf = (BorrowFlow) real;
			BorrowFlowDTO borrowFlowDTO=new BorrowFlowDTO(bf);
			JSONUtil.write(response, borrowFlowDTO);
			
			
		}
		
		
		return null;
	}
	
	public void modifiedBorrower(){
		if(this.flowNodeId==0||this.userId==0)
			return;
		FlowNode flowNode=borrowFlowService.getFlowNode(flowNodeId);
		if(!flowNode.getNodeStatus().equals(BorrowFlowConstants.BORROWFLOWNODE_STATUS_WATING_DEAL))
			return;
		SystemUser approver=this.userService.getUser(userId);
		flowNode.setApproverORLender(approver);
		flowNode.setNodeStatus(BorrowFlowConstants.BORROWFLOWNODE_STATUS_WATING_DEAL);
		this.borrowFlowService.saveBorrowFlow4Modify(flowNode.getFlow());
		Flow flow = flowNode.getFlow();
		HibernatePorxy<Flow> hp=new HibernatePorxy<Flow>();
		Flow real=hp.getRealEntity(flow);
		if (real  instanceof BorrowFlow) {
			BorrowFlow bf = (BorrowFlow) real;
			BorrowFlowDTO borrowFlowDTO=new BorrowFlowDTO(bf);
			JSONUtil.write(response, borrowFlowDTO);
			
			
		}
		
	}
	
	
	public String borrow(){
	
		BorrowFlow flow=null;
		if(this.flowId!=0)
			flow=this.borrowFlowService.getBorrowFlow(this.flowId);
		else
			return null;
		if(this.result==null)
			return null;
		this.borrowFlowService.borrow(flow, result);
		
		
		return viewBorrowFlow(flowId);
		
	}
	
	public String viewBorrowFlow(Long flowId){
		
		BorrowFlow flow=null;
		if(this.flowId!=0){
			flow=this.borrowFlowService.getBorrowFlow(this.flowId);
			
			BorrowFlowDTO borrowFlowDTO=new BorrowFlowDTO(flow);
			
			JSONUtil.write(response, borrowFlowDTO);
			return null;
		}
			
		else
			return null;
		
	}
	
	
	
	

	public long getKnowledgeID() {
		return knowledgeID;
	}

	public void setKnowledgeID(long knowledgeID) {
		this.knowledgeID = knowledgeID;
	}




	public BorrowFlowService getBorrowFlowService() {
		return borrowFlowService;
	}

//
//
//	@Autowired
//	public void setBorrowFlowService(BorrowFlowService borrowFlowService) {
//		this.borrowFlowService = borrowFlowService;
//	}




//	public UserService getUserService() {
//		return userService;
//	}
//
//
//
//	@Autowired
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}




//	public KnowledgeService getKnowledgeService() {
//		return knowledgeService;
//	}
//
//
//
//	@Autowired
//	public void setKnowledgeService(KnowledgeService knowledgeService) {
//		this.knowledgeService = knowledgeService;
//	}



	



	




	

	public String getLimited() {
		return limited;
	}

	public void setLimited(String limited) {
		this.limited = limited;
	}

	public int getTimes() {
		return times;
	}




	public void setTimes(int times) {
		this.times = times;
	}






	public long getFlowId() {
		return flowId;
	}






	public void setFlowId(long flowId) {
		this.flowId = flowId;
	}






	public long getInitId() {
		return initId;
	}






	public void setInitId(long initId) {
		this.initId = initId;
	}

	public long getFlowNodeId() {
		return flowNodeId;
	}

	public void setFlowNodeId(long flowNodeId) {
		this.flowNodeId = flowNodeId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isDownload() {
		return download;
	}

	public void setDownload(boolean download) {
		this.download = download;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
		
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public KnowledgeService getKnowledgeService() {
		return knowledgeService;
	}
	public void setKnowledgeService(KnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setBorrowFlowService(BorrowFlowService borrowFlowService) {
		this.borrowFlowService = borrowFlowService;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public BorrowFlowMessage getBorrowFlowMessage() {
		return borrowFlowMessage;
	}
	
	@Autowired
	public void setBorrowFlowMessage(BorrowFlowMessage borrowFlowMessage) {
		this.borrowFlowMessage = borrowFlowMessage;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
