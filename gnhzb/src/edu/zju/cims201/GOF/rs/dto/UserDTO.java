package edu.zju.cims201.GOF.rs.dto;

public class UserDTO implements Comparable<UserDTO>{
	private Long id;
	private String name;
	private String email;
	private String password;
	private String introduction;
	private String hobby;
	private Boolean isVisible;
	private String sex;
	private String picturePath;
	
	private Long orderId;
	
	private Integer administrativeRole;
	private Integer technicalRole;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	public Integer getAdministrativeRole() {
		return administrativeRole;
	}
	public void setAdministrativeRole(Integer administrativeRole) {
		this.administrativeRole = administrativeRole;
	}
	public Integer getTechnicalRole() {
		return technicalRole;
	}
	public void setTechnicalRole(Integer technicalRole) {
		this.technicalRole = technicalRole;
	}
	public int compareTo(UserDTO o) {
		if(this.orderId!=null&&o.getOrderId()!=null){
			return this.orderId>o.getOrderId()?1:-1;
		}
		return this.id>o.getId()?1:-1;
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
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
