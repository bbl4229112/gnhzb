package edu.zju.cims201.GOF.rs.dto;

import java.awt.List;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ObjectDTO {
	private Long id;
	private String name;
	private String showname;
	private String email;
    public ObjectDTO(){}	
    public ObjectDTO(Long id,String name){
    	
    this.id=id;
    this.name=name;
    this.showname=name;
    	
    }	
    public ObjectDTO(Long id,String name,String showname){
    	
        this.id=id;
        this.name=name;
        this.showname=showname;
        	
        }	
  public ObjectDTO(Long id,String name,String showname,String email){
    	
        this.id=id;
        this.name=name;
        this.showname=showname;
        this.email=email;	
        }
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
	public String getShowname() {
		return showname;
	}
	public void setShowname(String showname) {
		this.showname = showname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	
	
	

}
