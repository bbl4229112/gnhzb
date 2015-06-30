package edu.zju.cims201.GOF.rs.dto;

import edu.zju.cims201.GOF.hibernate.pojoA.Biaozhun;

public class BiaozhunDTO {
	private Integer id;
	private String biaoZhunCode;
	private String titleNameChi;
	private String titleNameEng;
	private String abstractString;
	private String biaozhunStatus;
	private String publishDate;
	private String implDate;
	private String beyondedIndustry;
	private String pdfname;
	
	public BiaozhunDTO(){
		
	}
	
	public BiaozhunDTO(Biaozhun biaozhun){
		
		if(biaozhun.getId()!=null){
			this.id=biaozhun.getId();
		}
		if(biaozhun.getBiaoZhunCode()!=null){
			this.biaoZhunCode=biaozhun.getBiaoZhunCode();
		}
		if(biaozhun.getTitleNameChi()!=null){
			this.titleNameChi =biaozhun.getTitleNameChi();
		}
		if(biaozhun.getTitleNameEng()!=null){
			this.titleNameEng= biaozhun.getTitleNameEng();
		}
		if(biaozhun.getAbstractString()!=null){
			this.abstractString=biaozhun.getAbstractString();
		}
		if(biaozhun.getBiaoZhunCode()!=null){
			this.biaozhunStatus=biaozhun.getBiaoZhunCode();
		}
		if(biaozhun.getPublishDate()!=null){
			this.publishDate=biaozhun.getPublishDate();
		}
		if(biaozhun.getImplDate()!=null){
			this.implDate=biaozhun.getImplDate();
		}
		if(biaozhun.getBeyondedIndustry()!=null){
			this.beyondedIndustry=biaozhun.getBeyondedIndustry();
		}
		if(biaozhun.getPdfname()!=null){
			String name=biaozhun.getPdfname();
			name=name.replace(" ", "");
			this.pdfname=name;
		}	
		
		
	}
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBiaoZhunCode() {
		return biaoZhunCode;
	}
	public void setBiaoZhunCode(String biaoZhunCode) {
		this.biaoZhunCode = biaoZhunCode;
	}
	public String getTitleNameChi() {
		return titleNameChi;
	}
	public void setTitleNameChi(String titleNameChi) {
		this.titleNameChi = titleNameChi;
	}
	public String getTitleNameEng() {
		return titleNameEng;
	}
	public void setTitleNameEng(String titleNameEng) {
		this.titleNameEng = titleNameEng;
	}
	public String getAbstractString() {
		return abstractString;
	}
	public void setAbstractString(String abstractString) {
		this.abstractString = abstractString;
	}
	public String getBiaozhunStatus() {
		return biaozhunStatus;
	}
	public void setBiaozhunStatus(String biaozhunStatus) {
		this.biaozhunStatus = biaozhunStatus;
	}

	
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getImplDate() {
		return implDate;
	}
	public void setImplDate(String implDate) {
		this.implDate = implDate;
	}
	public String getBeyondedIndustry() {
		return beyondedIndustry;
	}
	public void setBeyondedIndustry(String beyondedIndustry) {
		this.beyondedIndustry = beyondedIndustry;
	}

	public String getPdfname() {
		return pdfname;
	}

	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}
	
	
}
