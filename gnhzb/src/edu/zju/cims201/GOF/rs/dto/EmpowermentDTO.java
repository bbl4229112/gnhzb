package edu.zju.cims201.GOF.rs.dto;

public class EmpowermentDTO implements Comparable<EmpowermentDTO>{
	
	
	private Long id;
	
	private String name;
    private UserDTO creater;
	public UserDTO getCreater() {
		return creater;
	}

	public void setCreater(UserDTO creater) {
		this.creater = creater;
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

	public int compareTo(EmpowermentDTO o) {
		
		return this.id>o.id?1:-1;
	}
	
	
	
	

}
