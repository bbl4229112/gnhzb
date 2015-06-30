package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SystemUser {

	
	private Long id;
	private String name;
	private String email;
	private String password;
	private String introduction;
	private String hobby;
	private Boolean isVisible;
	private String sex;
	private String picturePath;
	private String orgname;//avidm部门
	private Integer securityLevel;
	
	
	private Set<Empowerment> empowerments;
	
	private Set<MetaKnowledge> uploadKnowledges=new HashSet<MetaKnowledge>();
	
	
	
	private Set<TreeNode> roleNodes=new HashSet<TreeNode>();
	private Set<Flow> initiatedFlows=new HashSet<Flow>();
	private Set<FlowNode> receivedFlowNodes=new HashSet<FlowNode>();
	private Set<Message> receivedMessages=new HashSet<Message>();
	private Set<InterestModel> interestModels=new HashSet<InterestModel>();
	
	//单独对单个人开了用户权限
	private Set<UserPrivilegeTriple> userPrivilegeTriples=new HashSet<UserPrivilegeTriple>();
	private Set<UserKnowledgeTag> userKnowledgeTags=new HashSet<UserKnowledgeTag>();
	

	private Set sysBehaviorlogs = new HashSet(0);//用户行为记录
	private Integer scoreTemp=0;//用于存放用户排行的临时分数，当按照weekcscore排序时，则scoreTemp=weekcscore
	private Integer totoalscore = 0;
	private Integer contributionscore = 0;
	private Integer weekcscore = 0;
	private Integer lastweekcscore = 0;
	private Integer monthcscore = 0;
	private Integer lastmonthcscore = 0;
	private Integer yearcscore = 0;
	private Integer lastyearcscore = 0;
	
	private Integer weekpscore = 0;
	private Integer lastweekpscore = 0;
	private Integer monthpscore = 0;
	private Integer lastmonthpscore = 0;
	private Integer yearpscore = 0;
	private Integer lastyearpscore = 0;
	
	private Integer lasttwoweekcscore = 0;
	private Integer lasttwoweekpscore = 0;
	
	
	
	
	public Integer getWeekcscore() {
		return weekcscore;
	}

	public void setWeekcscore(Integer weekcscore) {
		this.weekcscore = weekcscore;
	}

	public Integer getLastweekcscore() {
		return lastweekcscore;
	}

	public void setLastweekcscore(Integer lastweekcscore) {
		this.lastweekcscore = lastweekcscore;
	}

	public Integer getMonthcscore() {
		return monthcscore;
	}

	public void setMonthcscore(Integer monthcscore) {
		this.monthcscore = monthcscore;
	}

	public Integer getLastmonthcscore() {
		return lastmonthcscore;
	}

	public void setLastmonthcscore(Integer lastmonthcscore) {
		this.lastmonthcscore = lastmonthcscore;
	}

	public Integer getYearcscore() {
		return yearcscore;
	}

	public void setYearcscore(Integer yearcscore) {
		this.yearcscore = yearcscore;
	}

	public Integer getLastyearcscore() {
		return lastyearcscore;
	}

	public void setLastyearcscore(Integer lastyearcscore) {
		this.lastyearcscore = lastyearcscore;
	}

	public Integer getWeekpscore() {
		return weekpscore;
	}

	public void setWeekpscore(Integer weekpscore) {
		this.weekpscore = weekpscore;
	}

	public Integer getLastweekpscore() {
		return lastweekpscore;
	}

	public void setLastweekpscore(Integer lastweekpscore) {
		this.lastweekpscore = lastweekpscore;
	}

	public Integer getMonthpscore() {
		return monthpscore;
	}

	public void setMonthpscore(Integer monthpscore) {
		this.monthpscore = monthpscore;
	}

	public Integer getLastmonthpscore() {
		return lastmonthpscore;
	}

	public void setLastmonthpscore(Integer lastmonthpscore) {
		this.lastmonthpscore = lastmonthpscore;
	}

	public Integer getYearpscore() {
		return yearpscore;
	}

	public void setYearpscore(Integer yearpscore) {
		this.yearpscore = yearpscore;
	}

	public Integer getLastyearpscore() {
		return lastyearpscore;
	}

	public void setLastyearpscore(Integer lastyearpscore) {
		this.lastyearpscore = lastyearpscore;
	}

	public Integer getLasttwoweekcscore() {
		return lasttwoweekcscore;
	}

	public void setLasttwoweekcscore(Integer lasttwoweekcscore) {
		this.lasttwoweekcscore = lasttwoweekcscore;
	}

	public Integer getLasttwoweekpscore() {
		return lasttwoweekpscore;
	}

	public void setLasttwoweekpscore(Integer lasttwoweekpscore) {
		this.lasttwoweekpscore = lasttwoweekpscore;
	}

	public Set getSysBehaviorlogs() {
		return sysBehaviorlogs;
	}

	public void setSysBehaviorlogs(Set sysBehaviorlogs) {
		this.sysBehaviorlogs = sysBehaviorlogs;
	}



	public String getEmail() {
		return email;
	}
	
	public Set<UserKnowledgeTag> getUserKnowledgeTags() {
		return userKnowledgeTags;
	}

	public void setUserKnowledgeTags(Set<UserKnowledgeTag> userKnowledgeTags) {
		this.userKnowledgeTags = userKnowledgeTags;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Flow> getInitiatedFlows() {
		return initiatedFlows;
	}
	public void setInitiatedFlows(Set<Flow> initiatedFlows) {
		this.initiatedFlows = initiatedFlows;
	}
	public Set<InterestModel> getInterestModels() {
		return interestModels;
	}
	public void setInterestModels(Set<InterestModel> interestModels) {
		this.interestModels = interestModels;
	}
	public Set<FlowNode> getReceivedFlowNodes() {
		return receivedFlowNodes;
	}
	public void setReceivedFlowNodes(Set<FlowNode> receivedFlowNodes) {
		this.receivedFlowNodes = receivedFlowNodes;
	}
	
	public Set<Message> getReceivedMessages() {
		return receivedMessages;
	}
	public void setReceivedMessages(Set<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}
	public Set<TreeNode> getRoleNodes() {
		return roleNodes;
	}
	public void setRoleNodes(Set<TreeNode> roleNodes) {
		this.roleNodes = roleNodes;
	}
	public Set<MetaKnowledge> getUploadKnowledges() {
		return uploadKnowledges;
	}
	public void setUploadKnowledges(Set<MetaKnowledge> uploadKnowledges) {
		this.uploadKnowledges = uploadKnowledges;
	}
	public Set<UserPrivilegeTriple> getUserPrivilegeTriples() {
		return userPrivilegeTriples;
	}
	public void setUserPrivilegeTriples(
			Set<UserPrivilegeTriple> userPrivilegeTriples) {
		this.userPrivilegeTriples = userPrivilegeTriples;
	}
	
	
	

	public Set<Empowerment> getEmpowerments() {
		return empowerments;
	}

	public void setEmpowerments(Set<Empowerment> empowerments) {
		this.empowerments = empowerments;
	}
	
	

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SystemUser other = (SystemUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getTotoalscore() {
		return totoalscore;
	}

	public void setTotoalscore(Integer totoalscore) {
		this.totoalscore = totoalscore;
	}

	public Integer getContributionscore() {
		return contributionscore;
	}

	public void setContributionscore(Integer contributionscore) {
		this.contributionscore = contributionscore;
	}

	public Integer getScoreTemp() {
		return scoreTemp;
	}

	public void setScoreTemp(Integer scoreTemp) {
		this.scoreTemp = scoreTemp;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public Integer getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(Integer securityLevel) {
		this.securityLevel = securityLevel;
	}
	
	
	
	
	
	
	
	

}
