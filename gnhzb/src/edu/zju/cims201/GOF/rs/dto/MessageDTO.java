package edu.zju.cims201.GOF.rs.dto;

import java.util.Date;

import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;

public class MessageDTO {
	private Long id;
	private String sender;
	private String receiver;
	private String content;
	private String knowledgeName;
	private Long isRead;
	private Long isAnswered;
	private String messageType;
	private String sendTime;
	private String linkedurl;//url链接地址
	private Long knowledgeid;
	public Long getKnowledgeid() {
		return knowledgeid;
	}
	public void setKnowledgeid(Long knowledgeid) {
		this.knowledgeid = knowledgeid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKnowledgeName() {
		return knowledgeName;
	}
	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getLinkedurl() {
		return linkedurl;
	}
	public void setLinkedurl(String linkedurl) {
		this.linkedurl = linkedurl;
	}
	public Long getIsRead() {
		return isRead;
	}
	public void setIsRead(Long isRead) {
		this.isRead = isRead;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public Long getIsAnswered() {
		return isAnswered;
	}
	public void setIsAnswered(Long isAnswered) {
		this.isAnswered = isAnswered;
	}
	

	
}
