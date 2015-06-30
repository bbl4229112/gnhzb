package edu.zju.cims201.GOF.rs.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlow;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlowContent;
import edu.zju.cims201.GOF.hibernate.pojo.FlowNode;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;

public class BorrowFlowDTO {
	
	private Long id;
	private KnowledgeDTO knowledge;
	private UserDTO  initiator;
	private String startTime;
	private String status;
	private Integer index;
	private List<FlowNodeDTO> flowNodesDTO=new ArrayList<FlowNodeDTO>() ;
	private BorrowFlowContentDTO borrowFlowContentDTO;
	private UserDTO borrowAdmin;
	private Long currentnodeid;
	public BorrowFlowDTO(){
		
	}
	
	
	public BorrowFlowDTO(BorrowFlow borrowFlow){
		id=borrowFlow.getId();
		if(borrowFlow.getKnowledge()!=null){
			knowledge=new KnowledgeDTO(borrowFlow.getKnowledge());
//			knowledge.setId(borrowFlow.getKnowledge().getId());
//			knowledge.setTitleName(borrowFlow.getKnowledge().getTitlename());
//			ObjectDTO uploaderdto=new ObjectDTO(borrowFlow.getKnowledge().getUploader().getId(),borrowFlow.getKnowledge().getUploader().getName());
//			knowledge.setUploader(uploaderdto);
		}
		if(borrowFlow.getInitiator()!=null){
			initiator=new UserDTO();
			initiator.setId(borrowFlow.getInitiator().getId());
			initiator.setEmail(borrowFlow.getInitiator().getEmail());
			initiator.setName(borrowFlow.getInitiator().getName());
		}
		startTime=borrowFlow.getStartTime().toLocaleString();
		status=borrowFlow.getStatus();
		index=borrowFlow.getIndex();
		if(null!=borrowFlow.getCurrentNode())
		currentnodeid=borrowFlow.getCurrentNode().getId();
		if(borrowFlow.getBorrowFlowContent()!=null){
			borrowFlowContentDTO=new BorrowFlowContentDTO(borrowFlow.getBorrowFlowContent());
		}
		
		if(borrowFlow.getBorrowAdmin()!=null){
			borrowAdmin=new UserDTO();
			borrowAdmin.setId(borrowFlow.getBorrowAdmin().getId());
			borrowAdmin.setEmail(borrowFlow.getBorrowAdmin().getEmail());
			borrowAdmin.setName(borrowFlow.getBorrowAdmin().getName());
		}
		int size=borrowFlow.getFlowNodes().size();
		for(int i=0;i<size;i++){
			flowNodesDTO.add(new FlowNodeDTO(borrowFlow.getFlowNodes().get(i)));
		}
		
		
	}
	
	
	
	
	
	
	
	public UserDTO getBorrowAdmin() {
		return borrowAdmin;
	}
	public void setBorrowAdmin(UserDTO borrowAdmin) {
		this.borrowAdmin = borrowAdmin;
	}
	public BorrowFlowContentDTO getBorrowFlowContentDTO() {
		return borrowFlowContentDTO;
	}
	public void setBorrowFlowContentDTO(BorrowFlowContentDTO borrowFlowContentDTO) {
		this.borrowFlowContentDTO = borrowFlowContentDTO;
	}
	public List<FlowNodeDTO> getFlowNodesDTO() {
		return flowNodesDTO;
	}
	public void setFlowNodesDTO(List<FlowNodeDTO> flowNodesDTO) {
		this.flowNodesDTO = flowNodesDTO;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public UserDTO getInitiator() {
		return initiator;
	}
	public void setInitiator(UserDTO initiator) {
		this.initiator = initiator;
	}
	public KnowledgeDTO getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(KnowledgeDTO knowledge) {
		this.knowledge = knowledge;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}





	public Long getCurrentnodeid() {
		return currentnodeid;
	}


	public void setCurrentnodeid(Long currentnodeid) {
		this.currentnodeid = currentnodeid;
	}
	
	
	
	
	
	

}
