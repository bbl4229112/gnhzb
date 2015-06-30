package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.Date;

public class Message {
	
	
	
	private Long id;
	private SystemUser sender;
	private SystemUser receiver;
	private String content;
	private MetaKnowledge knowledge;
	private Boolean isRead;
	private String messageType;
	private Boolean isAnswered;
	//消息类型：notice,addComment,replyComment,vote,applyApproval,replyApproval,
	//        recommendKnowledge,subscription,applyBorrow,replyBorrow
	private Date sendTime;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public MetaKnowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(MetaKnowledge knowledge) {
		this.knowledge = knowledge;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public SystemUser getReceiver() {
		return receiver;
	}
	public void setReceiver(SystemUser receiver) {
		this.receiver = receiver;
	}
	public SystemUser getSender() {
		return sender;
	}
	public void setSender(SystemUser sender) {
		this.sender = sender;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Boolean getIsAnswered() {
		return isAnswered;
	}
	public void setIsAnswered(Boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

}
