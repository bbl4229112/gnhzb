package edu.zju.cims201.GOF.hibernate.pojoA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class PartDraft {
	private long id;
	private String draftName;
	private String draftUrl;
	private String description;
	private String draftSuffix;
	private String typeName;
	private String fileName;
	private int ismaster;
	private Part part;
	private DraftType draftType;
	private String partUuid;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDraftName() {
		return draftName;
	}
	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}
	public String getDraftUrl() {
		return draftUrl;
	}
	public void setDraftUrl(String draftUrl) {
		this.draftUrl = draftUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDraftSuffix() {
		return draftSuffix;
	}
	public void setDraftSuffix(String draftSuffix) {
		this.draftSuffix = draftSuffix;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getIsmaster() {
		return ismaster;
	}
	public void setIsmaster(int ismaster) {
		this.ismaster = ismaster;
	}
	@ManyToOne
	@JoinColumn(name="part_id")
	public Part getPart() {
		return part;
	}
	public void setPart(Part part) {
		this.part = part;
	}
	@OneToOne
	@JoinColumn(name="draft_type_id")
	public DraftType getDraftType() {
		return draftType;
	}
	public void setDraftType(DraftType draftType) {
		this.draftType = draftType;
	}
	public String getPartUuid() {
		return partUuid;
	}
	public void setPartUuid(String partUuid) {
		this.partUuid = partUuid;
	}
	
	
}
