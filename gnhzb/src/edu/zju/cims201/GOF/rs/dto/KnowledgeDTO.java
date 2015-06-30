package edu.zju.cims201.GOF.rs.dto;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import edu.zju.cims201.GOF.service.systemFile.FileService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.hibernate.pojo.Attachment;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;
import edu.zju.cims201.GOF.hibernate.pojo.Version;



public class KnowledgeDTO implements Comparable<KnowledgeDTO> 
{	
	private Long id;
	private String identifier;
	private String securityLevel;
	private String status;
	private ObjectDTO uploader;
	private String abstractText;
	private String titleName;
	private String titleShowName;
	private String uploadTime;
	private String uploaddate;
	private String knowledgeSourceFilePath;
	private String flashFilePath;
	private Boolean isVisible;
	private CommentRecordDTO commentRecord;
	private ObjectDTO ktype;
	private Long borrowFlowId;
	private Long borrowFlowNodeId;
	private List<ObjectDTO> KAuthors;
	private ObjectDTO knowledgetype; 
	private List<ObjectDTO> categories;
	private ObjectDTO  domainNode;
	private List<ObjectDTO> keywords;
	private List<ObjectDTO>  attachments;
	private String content;
	//private String comments=new HashSet<Comment>();
	private List<KnowledgeDTO> citationKnowledges=new ArrayList<KnowledgeDTO> ();
	private List<UserKnowledgeTagDTO> userKnowledgeTags=new ArrayList<UserKnowledgeTagDTO>();
	
	private List<ObjectDTO> versions;
	private String questioncontent;
	private String questionsupplement;
	private Long questionstatus;
	private String isRead;
	private String isUserEqualUpload;
	
	private String uploaderName;
	private String knowledgeType;	
    public String getUploaderName() {
		return uploaderName;
	}
	public void setUploaderName(String uploaderName) {
		this.uploaderName = uploaderName;
	}
	public String getKnowledgeType() {
		return knowledgeType;
	}
	public void setKnowledgeType(String knowledgeType) {
		this.knowledgeType = knowledgeType;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}	
	public String getIsUserEqualUpload() {
		return isUserEqualUpload;
	}
	public void setIsUserEqualUpload(String isUserEqualUpload) {
		this.isUserEqualUpload = isUserEqualUpload;
	}
	public String getQuestionsupplement() {
		return questionsupplement;
	}
	public void setQuestionsupplement(String questionsupplement) {
		this.questionsupplement = questionsupplement;
	}
	public String getQuestioncontent() {
		return questioncontent;
	}
	public void setQuestioncontent(String questioncontent) {
		this.questioncontent = questioncontent;
	}
	public Long getQuestionstatus() {
		return questionstatus;
	}
	public void setQuestionstatus(Long questionstatus) {
		this.questionstatus = questionstatus;
	}
	
	public KnowledgeDTO()
    {
    	
    }
	public KnowledgeDTO(Knowledge k)
	{

		
		this.id=k.getId();
		//Ktype ktype=k.getKtype();
		ObjectDTO ktypedto=new ObjectDTO(k.getKtype().getId(),k.getKtype().getKtypeName());
		this.ktype=ktypedto;
		this.titleName=k.getTitlename();
		this.titleShowName=k.getTitlename();
		ObjectDTO uploaderdto=new ObjectDTO(k.getUploader().getId(),k.getUploader().getName(),"",k.getUploader().getEmail());
		this.uploader=uploaderdto;
		this.abstractText=k.getAbstracttext();
		List<ObjectDTO> authordtolist=new ArrayList<ObjectDTO>();
		List<Author> authorlist = k.getKauthors();
		for (Author author : authorlist) {
			 ObjectDTO authordto=new ObjectDTO(author.getId(),author.getAuthorName());		
			 authordtolist.add(authordto);
		}
		this.KAuthors=authordtolist;
		List<ObjectDTO> keywordsdtolist=new ArrayList<ObjectDTO>();
		Set<Keyword> keywordlist = k.getKeywords();
		for (Keyword keyword : keywordlist) {
			 ObjectDTO keyworddto=new ObjectDTO(keyword.getId(),keyword.getKeywordName());
			 keywordsdtolist.add(keyworddto);
		}
		this.keywords=keywordsdtolist;
		this.uploadTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(k.getUploadtime());
		this.uploaddate=new SimpleDateFormat("yyyy-MM-dd").format(k.getUploadtime());
		if(null!=k.getDomainnode())
		{ObjectDTO domainNodedto=new ObjectDTO(k.getDomainnode().getId(),k.getDomainnode().getNodeName());
		this.domainNode=domainNodedto;}
		if(null!=k.getKnowledgetype()){
			ObjectDTO knowledgetypedto=new ObjectDTO(k.getKnowledgetype().getId(),k.getKnowledgetype().getKnowledgeTypeName());
		this.knowledgetype=knowledgetypedto;}
		List<ObjectDTO> categoriesdtolist=new ArrayList<ObjectDTO>();
		Set<CategoryTreeNode> categorieslist = k.getCategories();
		for (CategoryTreeNode category : categorieslist) {
			 ObjectDTO categorydto=new ObjectDTO(category.getId(),category.getNodeName());			
			 categoriesdtolist.add(categorydto);
		}	
		this.categories=categoriesdtolist;
		
		List<ObjectDTO> attachdtolist=new ArrayList<ObjectDTO>();
		Set<Attachment> attachlist = k.getAttachments();
		for (Attachment attachment : attachlist) {
			 ObjectDTO attachmentdto=new ObjectDTO(attachment.getId(),attachment.getAttachmentName());			
			 attachdtolist.add(attachmentdto);
		}	
		this.attachments=attachdtolist;
		//引证文献
		List<KnowledgeDTO> citationKnowledges = new ArrayList<KnowledgeDTO>();
		List<MetaKnowledge> mks = k.getCitationknowledges();
		for(MetaKnowledge mk:mks){
			KnowledgeDTO mkdto = new KnowledgeDTO();
			mkdto.setId(mk.getId());
			mkdto.setTitleName(mk.getTitlename());
			List<ObjectDTO> mauthordtolist=new ArrayList<ObjectDTO>();
			List<Author> mauthorlist = mk.getKauthors();
			for (Author mauthor : mauthorlist) {
				 ObjectDTO mauthordto=new ObjectDTO(mauthor.getId(),mauthor.getAuthorName());		
				 mauthordtolist.add(mauthordto);
			}
			mkdto.setKAuthors(mauthordtolist);
			mkdto.setUploaddate(new SimpleDateFormat("yyyy-MM-dd").format(mk.getUploadtime()));
			citationKnowledges.add(mkdto);
		}
		this.citationKnowledges=citationKnowledges;

//		System.out.println(Constants.SOURCEFILE_PATH+"\\"+flashpath);
//		File f=new File(Constants.SOURCEFILE_PATH+"\\"+flashpath);
//		if(f.exists())
	    this.flashFilePath=k.getFlashfilepath();
//		else
//		this.flashFilePath="noflash";	
		//System.out.println("this.flashFilePath="+this.flashFilePath);
		CommentRecordDTO crdto=new CommentRecordDTO(k.getCommentrecord());
		
		//if(null!=cr)
		//cr.setKnowledge(null);
		this.commentRecord=crdto;
		this.knowledgeSourceFilePath=k.getKnowledgesourcefilepath();
		this.securityLevel=k.getSecuritylevel();
		this.status=k.getStatus();
		
	
	}

	//用于知识搜索后 将搜索结果专为dto并将检索条件高亮
	public KnowledgeDTO(Knowledge k,HashMap propertylist)
	{   
       
		this.id=k.getId();
		Ktype ktype=k.getKtype();
		ObjectDTO ktypedto=new ObjectDTO(k.getKtype().getId(),ktype.getKtypeName());
		this.ktype=ktypedto;
		//针对以标题查询知识，在检索结果中标红
		String titemp=k.getTitlename();
		if(null!=propertylist.get("titlename")&&null!=titemp&&!titemp.equals(""))
    	{	String tirex=propertylist.get("titlename").toString();
    	    String[] tirexs=tirex.split(" ");     
    	    for (String string : tirexs) {
    	    	
    	    	titemp=highlight(titemp, string);
			}
    	}	
		
		this.knowledgetype=new ObjectDTO(k.getKnowledgetype().getId(),k.getKnowledgetype().getKnowledgeTypeName());
		this.titleName=k.getTitlename();
		this.titleShowName=titemp;
		ObjectDTO uploaderdto=new ObjectDTO(k.getUploader().getId(),k.getUploader().getName());
		this.uploader=uploaderdto;
		
		//针对以摘要查询知识，在检索结果中标红
		String abtemp=k.getAbstracttext();
		if(null!=propertylist.get("abstracttext")&&null!=abtemp&&!abtemp.equals(""))
    	{	String 	abstractTextrex=propertylist.get("abstracttext").toString();
    	      
    	    String[] abstractTextrexs=abstractTextrex.split(" ");     
    	    for (String string : abstractTextrexs) {
    	    	abtemp=highlight(abtemp, string);
			}
    	}	
		this.abstractText=abtemp;
		
		
		List<ObjectDTO> authordtolist=new ArrayList<ObjectDTO>();
		List<Author> authorlist = k.getKauthors();
		for (Author author : authorlist) {
			
			String katemp=author.getAuthorName();
			//针对以作者名称查询知识，在检索结果中标红
			if(null!=propertylist.get("kauthors")&&null!=katemp&&!katemp.equals(""))
	    	{	String karex=propertylist.get("kauthors").toString();
	    	    String[] karexs=karex.split(" ");     
	    	   
	    	    for (String string : karexs) {
	    	    	katemp=highlight(katemp, string);
				}
	    	}
			
			//针对以作者id查询知识，在检索结果中标红
			
			if(null!=propertylist.get("kauthorid")&&null!=katemp&&!katemp.equals(""))
			{
				if((propertylist.get("kauthorid")).equals(author.getId()))
				{
					katemp=highlight(katemp, katemp);
					
				}
				
			}
			
			 ObjectDTO authordto=new ObjectDTO(author.getId(),author.getAuthorName(),katemp);		
			 authordtolist.add(authordto);
		}
		this.KAuthors=authordtolist;
		List<ObjectDTO> keywordsdtolist=new ArrayList<ObjectDTO>();
		Set<Keyword> keywordlist = k.getKeywords();
		for (Keyword keyword : keywordlist) {
			
			String kwtemp=keyword.getKeywordName();
			//针对以关键词查询知识，在检索结果中标红
			if(null!=propertylist.get("keywords")&&null!=kwtemp&&!kwtemp.equals(""))
	    	{	String kwrex=propertylist.get("keywords").toString();
	    	//System.out.println();
	    	    String[] kwrexs=kwrex.split(" ");     
	    	    for (String string : kwrexs) {
	    	    	kwtemp=highlight(kwtemp, string);
				}
	    	}
			//针对以关键词id查询知识，在检索结果中标红
			if(null!=propertylist.get("keywordid")&&null!=kwtemp&&!kwtemp.equals(""))
			{       
				
				if((propertylist.get("keywordid")).equals(keyword.getId()))
				{
					kwtemp=highlight(kwtemp, kwtemp);
					
				}
				
			}
			//System.out.println("kwtemp==="+kwtemp);
			 ObjectDTO keyworddto=new ObjectDTO(keyword.getId(),keyword.getKeywordName(),kwtemp);
			 keywordsdtolist.add(keyworddto);
		}
		this.keywords=keywordsdtolist;
		this.uploadTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(k.getUploadtime());
		this.uploaddate=new SimpleDateFormat("yyyy-MM-dd").format(k.getUploadtime());
		 //ObjectDTO domainNodedto=new ObjectDTO(k.getDomainnode().getId(),k.getDomainnode().getNodeName());
		//this.domainNode=domainNodedto;
		this.flashFilePath=k.getFlashfilepath();
        CommentRecordDTO crdto=new CommentRecordDTO(k.getCommentrecord());		
		this.commentRecord=crdto;
		this.securityLevel=k.getSecuritylevel();
		this.status=k.getStatus();
		//引证文献
		List<KnowledgeDTO> citationKnowledges = new ArrayList<KnowledgeDTO>();
		List<MetaKnowledge> mks = k.getCitationknowledges();
		for(MetaKnowledge mk:mks){
			KnowledgeDTO mkdto = new KnowledgeDTO();
			mkdto.setId(mk.getId());
			mkdto.setTitleName(mk.getTitlename());
			List<ObjectDTO> mauthordtolist=new ArrayList<ObjectDTO>();
			List<Author> mauthorlist = mk.getKauthors();
			for (Author mauthor : mauthorlist) {
				 ObjectDTO mauthordto=new ObjectDTO(mauthor.getId(),mauthor.getAuthorName());		
				 mauthordtolist.add(mauthordto);
			}
			mkdto.setKAuthors(mauthordtolist);
			mkdto.setUploaddate(new SimpleDateFormat("yyyy-MM-dd").format(mk.getUploadtime()));
			citationKnowledges.add(mkdto);
		}
		this.citationKnowledges=citationKnowledges;
	
	}

   private String highlight(String text,String rex )
   {
	   
	   text=text.replaceAll(rex, "<span class=\"knowledge_list_highlight\">"+rex+"</span>");
	   return text;
   }
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getIdentifier() {
		return identifier;
	}


	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public String getSecurityLevel() {
		return securityLevel;
	}


	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public ObjectDTO getUploader() {
		return uploader;
	}


	public void setUploader(ObjectDTO uploader) {
		this.uploader = uploader;
	}


	public String getAbstractText() {
		return abstractText;
	}


	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}


	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}


	public String getUploadTime() {
		return uploadTime;
	}


	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}


	public String getKnowledgeSourceFilePath() {
		return knowledgeSourceFilePath;
	}


	public void setKnowledgeSourceFilePath(String knowledgeSourceFilePath) {
		this.knowledgeSourceFilePath = knowledgeSourceFilePath;
	}


	public String getFlashFilePath() {
		return flashFilePath;
	}


	public void setFlashFilePath(String flashFilePath) {
		this.flashFilePath = flashFilePath;
	}


	public Boolean getIsVisible() {
		return isVisible;
	}


	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}


	public ObjectDTO getKtype() {
		return ktype;
	}


	public void setKtype(ObjectDTO ktype) {
		this.ktype = ktype;
	}


	public List<ObjectDTO> getKAuthors() {
		return KAuthors;
	}


	public void setKAuthors(List<ObjectDTO> authors) {
		KAuthors = authors;
	}


	public List<ObjectDTO> getCategories() {
		return categories;
	}


	public void setCategories(List<ObjectDTO> categories) {
		this.categories = categories;
	}


	public ObjectDTO getDomainNode() {
		return domainNode;
	}


	public void setDomainNode(ObjectDTO domainNode) {
		this.domainNode = domainNode;
	}


	public List<ObjectDTO> getKeywords() {
		return keywords;
	}


	public void setKeywords(List<ObjectDTO> keywords) {
		this.keywords = keywords;
	}


	public List<ObjectDTO> getAttachments() {
		return attachments;
	}


	public void setAttachments(List<ObjectDTO> attachments) {
		this.attachments = attachments;
	}
	
	public List<KnowledgeDTO> getCitationKnowledges() {
		return citationKnowledges;
	}


	public void setCitationKnowledges(List<KnowledgeDTO> citationKnowledges) {
		this.citationKnowledges = citationKnowledges;
	}


	public List<UserKnowledgeTagDTO> getUserKnowledgeTags() {
		return userKnowledgeTags;
	}


	public void setUserKnowledgeTags(List<UserKnowledgeTagDTO> userKnowledgeTags) {
		this.userKnowledgeTags = userKnowledgeTags;
	}


	public List<ObjectDTO> getVersions() {
		return versions;
	}
	public void setVersions(List<ObjectDTO> versions) {
		this.versions = versions;
	}
	public CommentRecordDTO getCommentRecord() {
		return commentRecord;
	}
	public void setCommentRecord(CommentRecordDTO commentRecord) {
		this.commentRecord = commentRecord;
	}
	public String getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitleShowName() {
		return titleShowName;
	}
	public void setTitleShowName(String titleShowName) {
		this.titleShowName = titleShowName;
	}
	public Long getBorrowFlowId() {
		return borrowFlowId;
	}
	public void setBorrowFlowId(Long borrowFlowId) {
		this.borrowFlowId = borrowFlowId;
	}
	public Long getBorrowFlowNodeId() {
		return borrowFlowNodeId;
	}
	public void setBorrowFlowNodeId(Long borrowFlowNodeId) {
		this.borrowFlowNodeId = borrowFlowNodeId;
	}
	public ObjectDTO getKnowledgetype() {
		return knowledgetype;
	}
	public void setKnowledgetype(ObjectDTO knowledgetype) {
		this.knowledgetype = knowledgetype;
	}
	
	public int compareTo(KnowledgeDTO o) {
		
		return this.id<o.getId()?1:-1;
	}

	
}
