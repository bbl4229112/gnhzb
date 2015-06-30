package edu.zju.cims201.GOF.rs.dto;

import java.util.List;

public class ExpertDTO {
	
	private Long id;
	private String username;
	private String usersex;
	private String userprofess;
	private String sex;
	private String email;
	private String picturePath;
	private String introduction;
	private List<String> treenodename;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsersex() {
		return usersex;
	}
	public void setUsersex(String usersex) {
		this.usersex = usersex;
	}
	public List<String> getTreenodename() {
		return treenodename;
	}
	public void setTreenodename(List<String> treenodename) {
		this.treenodename = treenodename;
	}
	public String getUserprofess() {
		return userprofess;
	}
	public void setUserprofess(String userprofess) {
		this.userprofess = userprofess;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	


}
