package edu.zju.cims201.GOF.hibernate.pojo;

import java.sql.Blob;
import java.util.Date;

public class SystemFile {
	
	private Long id;
	private String fileName;
	private String fileType;
	private Blob fileBinary;
	private Date saveDate=new Date();
	
	
	public Blob getFileBinary() {
		return fileBinary;
	}
	public void setFileBinary(Blob fileBinary) {
		this.fileBinary = fileBinary;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
	
	

}
