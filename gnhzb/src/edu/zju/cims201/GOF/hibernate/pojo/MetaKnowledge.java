package edu.zju.cims201.GOF.hibernate.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MetaKnowledge implements Knowledge, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String identifier;
	private String securitylevel;
	private String status;
	private SystemUser uploader;
	private String abstracttext;
	private String titlename;
	private Date uploadtime;
	private String knowledgesourcefilepath;
	private String flashfilepath;
	private Boolean isvisible;	
	private Ktype ktype;
	private  List<Author> kauthors=new ArrayList<Author> ();
	private Set<CategoryTreeNode>  categories=new HashSet<CategoryTreeNode>();
	private DomainTreeNode  domainnode;
	private Set<Keyword> keywords=new HashSet<Keyword>();
	private Set attachments=new HashSet<Attachment>();
	private Set comments=new HashSet<Comment>();
	private List<MetaKnowledge> citationknowledges=new ArrayList<MetaKnowledge> ();
	private Set <UserKnowledgeTag>userknowledgetags=new HashSet<UserKnowledgeTag>();
	private Version version;
	private CommentRecord commentrecord;
	private Knowledgetype knowledgetype;
	

	public Knowledgetype getKnowledgetype() {
		return knowledgetype;
	}

	public void setKnowledgetype(Knowledgetype knowledgetype) {
		this.knowledgetype = knowledgetype;
	}

	public CommentRecord getCommentrecord() {
		return commentrecord;
	}

	public void setCommentrecord(CommentRecord commentrecord) {
		this.commentrecord = commentrecord;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SystemUser getUploader() {
		return uploader;
	}

	public void setUploader(SystemUser uploader) {
		this.uploader = uploader;
	}

	public Set getAttachments() {
		return attachments;
	}

	public void setAttachments(Set attachments) {
		this.attachments = attachments;
	}

	public Set getComments() {
		return comments;
	}

	public void setComments(Set comments) {
		this.comments = comments;
	}

	public Set<CategoryTreeNode> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryTreeNode> categories) {
		this.categories = categories;
	}


	public String getSecuritylevel() {
		return securitylevel;
	}

	public void setSecuritylevel(String securitylevel) {
		this.securitylevel = securitylevel;
	}

	public String getAbstracttext() {
		return abstracttext;
	}

	public void setAbstracttext(String abstracttext) {
		this.abstracttext = abstracttext;
	}

	public String getTitlename() {
		return titlename;
	}

	public void setTitlename(String titlename) {
		this.titlename = titlename;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getKnowledgesourcefilepath() {
		return knowledgesourcefilepath;
	}

	public void setKnowledgesourcefilepath(String knowledgesourcefilepath) {
		this.knowledgesourcefilepath = knowledgesourcefilepath;
	}

	public String getFlashfilepath() {
		return flashfilepath;
	}

	public void setFlashfilepath(String flashfilepath) {
		this.flashfilepath = flashfilepath;
	}

	public Boolean getIsvisible() {
		return isvisible;
	}

	public void setIsvisible(Boolean isvisible) {
		this.isvisible = isvisible;
	}

	public List<Author> getKauthors() {
		return kauthors;
	}

	public void setKauthors(List<Author> kauthors) {
		this.kauthors = kauthors;
	}

	public DomainTreeNode getDomainnode() {
		return domainnode;
	}

	public void setDomainnode(DomainTreeNode domainnode) {
		this.domainnode = domainnode;
	}

	public List<MetaKnowledge> getCitationknowledges() {
		return citationknowledges;
	}

	public void setCitationknowledges(List<MetaKnowledge> citationknowledges) {
		this.citationknowledges = citationknowledges;
	}

	public Set<UserKnowledgeTag> getUserknowledgetags() {
		return userknowledgetags;
	}

	public void setUserknowledgetags(Set<UserKnowledgeTag> userknowledgetags) {
		this.userknowledgetags = userknowledgetags;
	}


	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public Ktype getKtype() {
		return ktype;
	}

	public void setKtype(Ktype ktype) {
		this.ktype = ktype;
	}

	public String SetDomainNode_string(String DomainNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCategories_string() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDomainNode_string() {
	String DomainNodename=setObjectDTO(getDomainnode().getId().toString(),getDomainnode().getNodeName());
	return DomainNodename;
	}

	public String getKtype_string() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUploader_String() {
	   String uploader="{id:"+getUploader().getId()+",name:"+getUploader().getName()+"}";
	   return uploader;
	}

	public void setCategories_string(String Categories_string) {
		// TODO Auto-generated method stub
		
	}

	public void setKtype_string(String ktype) {
		// TODO Auto-generated method stub
		
	}

	public void setUploader_string(String user) {
		// TODO Auto-generated method stub
		
	}

	public String getKAuthors_string() {
		String authors="";
	
		for (Author author:getKauthors()) {
			authors+=author.getAuthorName()+" ";
			
		} {
			
		}
			
		return authors;
	}

	public String setObjectDTO(String id,String name)
	{
		String objectjson="{id:"+id+",name:"+name+"}";
		return objectjson;

		
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MetaKnowledge other = (MetaKnowledge) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	


}
