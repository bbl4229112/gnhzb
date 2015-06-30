package edu.zju.cims201.GOF.hibernate.pojo;


public class Workload {
	

	private Long id;
	private String url;
	private String status;
	private String parenturl;
	
	public Workload() {
	}
	
	public Workload(String url, String status, String parenturl) {
		this.url = url;
		this.status = status;
		this.parenturl = parenturl;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getParenturl() {
		return parenturl;
	}
	public void setParenturl(String parenturl) {
		this.parenturl = parenturl;
	}

	
	
	
	
	
	

}
