package edu.zju.cims201.GOF.hibernate.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;



public interface Knowledge {
	
	public CommentRecord getCommentrecord() ;

	public void setCommentrecord(CommentRecord commentrecord) ;

	public String getIdentifier() ;

	public void setIdentifier(String identifier);
	
	public Long getId() ;

	public void setId(Long id);

	public String getStatus() ;

	public void setStatus(String status) ;

	public SystemUser getUploader();

	public void setUploader(SystemUser uploader);

	public Set getAttachments() ;

	public void setAttachments(Set attachments);

	public Set getComments() ;

	public void setComments(Set comments);

	public Set<CategoryTreeNode> getCategories();

	public void setCategories(Set<CategoryTreeNode> categories);

	public Knowledgetype getKnowledgetype();
	public void setKnowledgetype(Knowledgetype knowledgetype);
	public String getSecuritylevel() ;

	public void setSecuritylevel(String securitylevel);

	public String getAbstracttext();

	public void setAbstracttext(String abstracttext);

	public String getTitlename();

	public void setTitlename(String titlename);

	public Date getUploadtime() ;

	public void setUploadtime(Date uploadtime);

	public String getKnowledgesourcefilepath();

	public void setKnowledgesourcefilepath(String knowledgesourcefilepath);

	public String getFlashfilepath();

	public void setFlashfilepath(String flashfilepath);

	public Boolean getIsvisible() ;

	public void setIsvisible(Boolean isvisible) ;

	public List<Author> getKauthors();

	public void setKauthors(List<Author> kauthors);
	
	public DomainTreeNode getDomainnode() ;

	public void setDomainnode(DomainTreeNode domainnode) ;

	public List<MetaKnowledge> getCitationknowledges();

	public void setCitationknowledges(List<MetaKnowledge> citationknowledges);
	
	public Set<UserKnowledgeTag> getUserknowledgetags() ;

	public void setUserknowledgetags(Set<UserKnowledgeTag> userknowledgetags) ;


	public Set<Keyword> getKeywords() ;

	public void setKeywords(Set<Keyword> keywords) ;

	

	public Version getVersion();

	public void setVersion(Version version);

	public Ktype getKtype();

	public void setKtype(Ktype ktype);


	
////	//针对部分属性中是set类型，不方便展示的问题，添加若干方法 ，便于支持知识展示
//	public String SetDomainNode_string(String DomainNode);
//
//	public String getCategories_string();
//
//	public String getDomainNode_string() ;
//
//	public String getKtype_string() ;
//
//	public String getUploader_String();
//
//	public void setCategories_string(String Categories_string);
//
//	public void setKtype_string(String ktype) ;
//
//	public void setUploader_string(String user);
//	
//	public String getKAuthors_string();

}
