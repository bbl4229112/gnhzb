
package edu.zju.cims201.GOF.web.statistic;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import org.springframework.stereotype.Controller;
import org.stringtree.json.JSONWriter;

import edu.zju.cims201.GOF.hibernate.pojo.Expert;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.rs.dto.CommentRecordDTO;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.KtypeDTO;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.PropertyDTO;
import edu.zju.cims201.GOF.service.interestmodel.InterestModelService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.web.CrudActionSupport;



@Namespace("/statistic")
//定义名为reload的result重定向到message.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "khotlist.action", type = "redirect") })
public class KhotlistAction extends CrudActionSupport<Message> implements ServletResponseAware {
	
	
	private String listtype ; //排行类型（点击率，评论，评分，下载）
	private int index;
	private String size;
	private Long expertid;
	private String knowledgetype;//要列出的最热的知识的类型，比如列出最热的问题、新闻、标准、专利等。除了元知识
	

	//private String size ;//每页记录数
	private HttpServletResponse response;
	
	@Resource(name="knowledgeServiceImpl")
	KnowledgeService knowledgeService;
	@Resource(name="interestModelServiceImpl")
	InterestModelService imservice;
	@Resource(name="userServiceImpl")
	UserService userservice;


	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		//downloadCount commentCount viewCount rate
		String[] type={"rate","viewCount","commentCount", "downloadCount"  };
		StringBuffer orderby=new StringBuffer("order by m.commentrecord.");
		orderby.append(listtype).append(" desc ");
		for(int i=0;i<type.length;i++)
		{if(!type[i].equals(listtype))
		   {orderby.append(",m.commentrecord."+type[i]+" desc ");}
			
		}
		
		List<MetaKnowledge> list_meta=knowledgeService.sortKnowledge(orderby.toString(),size);
		List<KnowledgeDTO> list_metaDTO=new ArrayList<KnowledgeDTO>();
		for(MetaKnowledge m:list_meta)
		  {KnowledgeDTO mDTO=new KnowledgeDTO();
		  mDTO.setId(m.getId());
		  mDTO.setTitleName(m.getTitlename());
		  mDTO.setKnowledgetype(new ObjectDTO(m.getKnowledgetype().getId(),m.getKnowledgetype().getKnowledgeTypeName()));
		  List<ObjectDTO> keywords=new ArrayList<ObjectDTO>();
		  Iterator it=m.getKeywords().iterator();
		  while(it.hasNext())
		  {Keyword k=(Keyword)it.next();
				 ObjectDTO kDTO=new ObjectDTO(k.getId(),k.getKeywordName());
				 keywords.add(kDTO);
		  }
		  mDTO.setKeywords(keywords);
		  
		 ObjectDTO o=new ObjectDTO();
		  o.setName(m.getKtype().getKtypeName());
		  mDTO.setKtype(o);
		  
		  mDTO.setUploadTime(m.getUploadtime().toString().substring(0,19));
		  
		  ObjectDTO uploader=new ObjectDTO();
		  uploader.setName(m.getUploader().getName());
		  uploader.setId(m.getUploader().getId());
		  mDTO.setUploader(uploader);
		  
		  CommentRecordDTO commentRecord=new CommentRecordDTO();
		  commentRecord.setCommentCount(m.getCommentrecord().getCommentCount());
		  commentRecord.setDownloadCount(m.getCommentrecord().getDownloadCount());
		  commentRecord.setRate(m.getCommentrecord().getRate());
		  commentRecord.setViewCount(m.getCommentrecord().getViewCount());
		  mDTO.setCommentRecord(commentRecord);
		  list_metaDTO.add(mDTO);
			
		  }
		
		 PrintWriter out = response.getWriter();
			JSONWriter jw = new JSONWriter();
			String str = jw.write(list_metaDTO);
			out.println(str);

		
		return null;
	}
	/**
	 * 列出最新的七篇知识
	 * 
	 * */
	public String listLatestKnowledge() throws Exception {
		List<MetaKnowledge> list_meta=knowledgeService.sortKnowledge("order by m.uploadtime desc",size);
		List<KnowledgeDTO> list_metaDTO=new ArrayList<KnowledgeDTO>();
		for(MetaKnowledge m:list_meta)
		  {KnowledgeDTO mDTO=new KnowledgeDTO();
			  mDTO.setId(m.getId());
			  mDTO.setTitleName(m.getTitlename());
			  mDTO.setUploadTime(m.getUploadtime().toString());
			  ObjectDTO userdto = new ObjectDTO();
			  userdto.setName(m.getUploader().getName());
			  mDTO.setUploader(userdto);
			  
			  //mDTO.setUploadTime(m.getUploadtime().toString().substring(0,19));
			  
			  list_metaDTO.add(mDTO);
			
		  }
		
		 PrintWriter out = response.getWriter();
			JSONWriter jw = new JSONWriter();
			String str = jw.write(list_metaDTO);
			out.println(str);

		
		return null;
	}
	public String listHotQuestions() throws Exception{
		StringBuffer orderby=new StringBuffer("");
		
		orderby.append("where m.ktype.name='Question' and m.isvisible=true order by m.commentrecord.commentCount desc");
		
		List<MetaKnowledge> list_meta=knowledgeService.sortQuestion(orderby.toString(),size);
		List<KnowledgeDTO> mDTOS=new ArrayList<KnowledgeDTO>();
		for(MetaKnowledge m:list_meta)
		{
			KnowledgeDTO mDTO=new KnowledgeDTO();
			mDTO.setId(m.getId());
			mDTO.setTitleName(m.getTitlename());
			
			mDTO.setUploadTime(m.getUploadtime().toString().substring(0,19));
			
			ObjectDTO uploader=new ObjectDTO();
			uploader.setName(m.getUploader().getName());
			uploader.setId(m.getUploader().getId());
			mDTO.setUploader(uploader);
			
			CommentRecordDTO crdto = new CommentRecordDTO();
			crdto.setCommentCount(m.getCommentrecord().getCommentCount());
			crdto.setViewCount(m.getCommentrecord().getViewCount());
			mDTO.setCommentRecord(crdto);
			
			if(m.getKtype().getName().equals("Question"))
			{
				Long questionstatus=(Long)knowledgeService.getProperty(m.getId(), "questionstatus");
				if(questionstatus!=2L){
					mDTO.setQuestionstatus(0L);
				}else{
					mDTO.setQuestionstatus(1L);
				}			
			}
			
			mDTOS.add(mDTO);
		}				
		 PrintWriter out = response.getWriter();
			JSONWriter jw = new JSONWriter();
			String str = jw.write(mDTOS);
			out.println(str);
		return null;
	}
	//列出最热的问题、专利、新闻、标准甚至知识的方法进行封装，所以此类中其他的listHotNews、listHotQuestions都不需要了
	public String listHotDKKnowledge() throws Exception{
		StringBuffer orderby=new StringBuffer("");
		
		orderby.append("and m.ktype.name='"+knowledgetype+"' and m.isvisible=true order by m.");
		orderby.append(listtype);
		orderby.append(" desc");
		List<MetaKnowledge> list_meta=knowledgeService.sortKnowledge(orderby.toString(),size);
		List<KnowledgeDTO> mDTOS=new ArrayList<KnowledgeDTO>();
		for(MetaKnowledge m:list_meta)
		{
			KnowledgeDTO mDTO=new KnowledgeDTO();
			mDTO.setId(m.getId());
			mDTO.setTitleName(m.getTitlename());
			
			mDTO.setUploadTime(m.getUploadtime().toString().substring(0,19));
			
			ObjectDTO uploader=new ObjectDTO();
			uploader.setName(m.getUploader().getName());
			uploader.setId(m.getUploader().getId());
			mDTO.setUploader(uploader);
			
			CommentRecordDTO crdto = new CommentRecordDTO();
			crdto.setCommentCount(m.getCommentrecord().getCommentCount());
			crdto.setViewCount(m.getCommentrecord().getViewCount());
			mDTO.setCommentRecord(crdto);
			
			mDTOS.add(mDTO);
		}				
		 PrintWriter out = response.getWriter();
			JSONWriter jw = new JSONWriter();
			String str = jw.write(mDTOS);
			out.println(str);
		return null;
	}
	
	//列出最热的新闻
	public String listNews() throws Exception{
		StringBuffer orderby=new StringBuffer("");
		
		orderby.append("and m.ktype.name='News' and m.isvisible=true order by m.");
		orderby.append(listtype);
		orderby.append(" desc");
		List<MetaKnowledge> list_meta=knowledgeService.sortKnowledge(orderby.toString(),size);
		List<KnowledgeDTO> mDTOS=new ArrayList<KnowledgeDTO>();
		for(MetaKnowledge m:list_meta)
		{
			KnowledgeDTO mDTO=new KnowledgeDTO();
			mDTO.setId(m.getId());
			mDTO.setTitleName(m.getTitlename());
			
			mDTO.setUploadTime(m.getUploadtime().toString().substring(0,19));
			
			ObjectDTO uploader=new ObjectDTO();
			uploader.setName(m.getUploader().getName());
			uploader.setId(m.getUploader().getId());
			mDTO.setUploader(uploader);
			
			CommentRecordDTO crdto = new CommentRecordDTO();
			crdto.setCommentCount(m.getCommentrecord().getCommentCount());
			crdto.setViewCount(m.getCommentrecord().getViewCount());
			mDTO.setCommentRecord(crdto);
			
			mDTOS.add(mDTO);
		}				
		 PrintWriter out = response.getWriter();
			JSONWriter jw = new JSONWriter();
			String str = jw.write(mDTOS);
			out.println(str);
		return null;
	}
	
	//列出最热的专利 潘雷 panlei 20130604  
	public String listHotPatents() throws Exception{
		StringBuffer orderby=new StringBuffer("");
		
		orderby.append("and m.ktype.name='Patent' and m.isvisible=true order by m.");
		orderby.append(listtype);
		orderby.append(" desc");
		List<MetaKnowledge> list_meta=knowledgeService.sortKnowledge(orderby.toString(),size);
		List<KnowledgeDTO> mDTOS=new ArrayList<KnowledgeDTO>();
		for(MetaKnowledge m:list_meta)
		{
			KnowledgeDTO mDTO=new KnowledgeDTO();
			mDTO.setId(m.getId());
			mDTO.setTitleName(m.getTitlename());
			
			mDTO.setUploadTime(m.getUploadtime().toString().substring(0,19));
			
			ObjectDTO uploader=new ObjectDTO();
			uploader.setName(m.getUploader().getName());
			uploader.setId(m.getUploader().getId());
			mDTO.setUploader(uploader);
			
			CommentRecordDTO crdto = new CommentRecordDTO();
			crdto.setCommentCount(m.getCommentrecord().getCommentCount());
			crdto.setViewCount(m.getCommentrecord().getViewCount());
			mDTO.setCommentRecord(crdto);
			
			mDTOS.add(mDTO);
		}				
		 PrintWriter out = response.getWriter();
			JSONWriter jw = new JSONWriter();
			String str = jw.write(mDTOS);
			out.println(str);
		return null;
	}
	
	public String listQuestion() throws Exception {
		
		PageDTO pd = new PageDTO();
		
		String[] type={"uploadtime","commentCount","zeroanswer","waitanswer","existanswer","myanswer","acceptedanswer","myquestion"};
		StringBuffer orderby=new StringBuffer("");
		
		
		if(type[0].equals(listtype))
		{orderby.append("where m.ktype.name='Question' and m.isvisible=true and m.uploadtime between ? and ? order by m.uploadtime desc");}
		if(type[1].equals(listtype))
		{orderby.append("where m.ktype.name='Question' and m.isvisible=true order by m.commentrecord.commentCount desc");}
		if(type[2].equals(listtype))
		{orderby.append("from Question q where q.questionstatus=0 and q.isvisible=true order by q.uploadtime desc");}
				
		if(type[3].equals(listtype))
		{orderby.append("from Question q where q.questionstatus=1 or q.questionstatus=0 and q.isvisible=true order by q.uploadtime desc");}
		if(type[4].equals(listtype))
		{orderby.append("from Question q where q.questionstatus=2 and q.isvisible=true order by q.uploadtime desc");}
		if(type[5].equals(listtype))
		{orderby.append("where comment.commenter.id="+userservice.getUser().getId()+" and m.ktype.name='Question' " +
				"and m.isvisible=true and comment in elements(m.comments) )order by m.id desc");}
		if(type[6].equals(listtype))
		{orderby.append("where comment.commenter.id="+userservice.getUser().getId()+" and m.ktype.name='Question' " +
				"and m.isvisible=true and comment.isBest=1 and comment in elements(m.comments) order by m.id desc");}
		if(type[7].equals(listtype))
		{orderby.append("where m.uploader.id="+userservice.getUser().getId()+" and m.ktype.name='Question' and m.isvisible=true order by m.uploadtime desc");}
		
		
		List<MetaKnowledge> list_meta=knowledgeService.sortQuestion(orderby.toString(),size);
//		System.out.println("vvvvvvvvvvvvvv"+list_meta.size());
		List<KnowledgeDTO> mDTOS=new ArrayList<KnowledgeDTO>();
		for(MetaKnowledge m:list_meta)
		{
			KnowledgeDTO mDTO=new KnowledgeDTO();
			mDTO.setId(m.getId());
			mDTO.setTitleName(m.getTitlename());
			
			List<ObjectDTO> keywords=new ArrayList<ObjectDTO>();
			Iterator it=m.getKeywords().iterator();
			while(it.hasNext())
			{Keyword k=(Keyword)it.next();
			ObjectDTO kDTO=new ObjectDTO(k.getId(),k.getKeywordName());
			keywords.add(kDTO);
			}
			mDTO.setKeywords(keywords);
			
			mDTO.setUploadTime(m.getUploadtime().toString().substring(0,19));
			
			ObjectDTO uploader=new ObjectDTO();
			uploader.setName(m.getUploader().getName());
			uploader.setId(m.getUploader().getId());
			mDTO.setUploader(uploader);
			
			if(m.getKtype().getName().equals("Question"))
			{
			Long questionstatus=(Long)knowledgeService.getProperty(m.getId(), "questionstatus");
			if(questionstatus!=2L){
				mDTO.setQuestionstatus(0L);
			}else{
				mDTO.setQuestionstatus(1L);
			}			
			}
			
			CommentRecordDTO commentRecord=new CommentRecordDTO();
			commentRecord.setCommentCount(m.getCommentrecord().getCommentCount());
			mDTO.setCommentRecord(commentRecord);
			mDTOS.add(mDTO);
		
		}				
		
		List<KnowledgeDTO> subList = new ArrayList<KnowledgeDTO>();
		int isize = Integer.parseInt(size);
		for(int i=index*isize;i<((index+1)*isize<mDTOS.size()?(index+1)*isize:mDTOS.size());i++){
			subList.add(mDTOS.get(i));
		}
		pd.setData(subList);
		pd.setTotal(mDTOS.size());
		int totalPage;
		if(mDTOS.size()%isize == 0){
			totalPage = mDTOS.size()/isize;
		}else{
			totalPage = mDTOS.size()/isize+1;
		}
		pd.setTotalPage(mDTOS.size()/isize+1);		
		
		PrintWriter out = response.getWriter();
		JSONWriter jw = new JSONWriter();
		String str = jw.write(pd);
		out.println(str);		
		
		return null;
	}
	
	public String listExpertInfo() throws Exception {
		
		Expert expert = imservice.getExpert(new Long(expertid));
		PageDTO pd = new PageDTO();
		
		String[] type={"eknowledge","equestion","eanswer","ebestanswer","earticle"};//江丁丁修改 2013-6-20 添加earticle 
		StringBuffer orderby=new StringBuffer("");			
		
		if(type[0].equals(listtype))
		{orderby.append("where m.uploader.id="+expert.getUser().getId()+" and m.isvisible=true and m.ktype.name='Simpleknowledge' or m.ktype.name='Paper' order by m.uploadtime desc");}
		if(type[1].equals(listtype))
		{orderby.append("where m.uploader.id="+expert.getUser().getId()+" and m.ktype.name='Question' and m.isvisible=true order by m.uploadtime desc");}
		if(type[2].equals(listtype))
		{orderby.append("where comment.commenter.id="+expert.getUser().getId()+" and m.ktype.name='Question' " +
				"and m.isvisible=true and comment in elements(m.comments) order by m.id desc");}
		if(type[3].equals(listtype))
		{orderby.append("where comment.commenter.id="+expert.getUser().getId()+" and m.ktype.name='Question' " +
				"and m.isvisible=true and comment.isBest=1 and comment in elements(m.comments) order by m.id desc");}	
		
		//江丁丁添加 2013-6-20 添加earticle 
		if(type[4].equals(listtype))
		{orderby.append("where m.uploader.id="+expert.getUser().getId()+" and m.isvisible=true and m.ktype.name='Article' order by m.uploadtime desc");}
		
		List<MetaKnowledge> list_meta=knowledgeService.sortQuestion(orderby.toString(),size);
		List<KnowledgeDTO> mDTOS=new ArrayList<KnowledgeDTO>();
		for(MetaKnowledge m:list_meta)
		{KnowledgeDTO mDTO=new KnowledgeDTO();
		mDTO.setId(m.getId());
		mDTO.setTitleName(m.getTitlename());
		
		List<ObjectDTO> keywords=new ArrayList<ObjectDTO>();
		Iterator it=m.getKeywords().iterator();
		while(it.hasNext())
		{Keyword k=(Keyword)it.next();
		ObjectDTO kDTO=new ObjectDTO(k.getId(),k.getKeywordName());
		keywords.add(kDTO);
		}
		mDTO.setKeywords(keywords);
		
		mDTO.setUploadTime(m.getUploadtime().toString().substring(0,19));
		
		ObjectDTO uploader=new ObjectDTO();
		uploader.setName(m.getUploader().getName());
		uploader.setId(m.getUploader().getId());
		mDTO.setUploader(uploader);
		
		if(m.getKtype().getName().equals("Question"))
		{
		Long questionstatus=(Long)knowledgeService.getProperty(m.getId(), "questionstatus");
		if(questionstatus!=2L){
			mDTO.setQuestionstatus(0L);
		}else{
			mDTO.setQuestionstatus(1L);
		}			
		}
		if(m.getCommentrecord()!=null){
			CommentRecordDTO commentRecord=new CommentRecordDTO();
			commentRecord.setCommentCount(m.getCommentrecord().getCommentCount());
			mDTO.setCommentRecord(commentRecord);			
		}
		//System.out.println("m.getKtype().getName()====="+m.getKtype().getName());
		if(!m.getKtype().getName().equals("Question")){
			ObjectDTO knowledgetype=new ObjectDTO();
			knowledgetype.setName(m.getKnowledgetype().getKnowledgeTypeName());
			mDTO.setKnowledgetype(knowledgetype);
		}		
		mDTOS.add(mDTO);
		}	
		List<KnowledgeDTO> subList = new ArrayList<KnowledgeDTO>();
		int isize = Integer.parseInt(size);
		for(int i=index*isize;i<((index+1)*isize<mDTOS.size()?(index+1)*isize:mDTOS.size());i++){
			subList.add(mDTOS.get(i));
		}
		pd.setData(subList);
		pd.setTotal(mDTOS.size());
		int totalPage;
		if(mDTOS.size()%isize == 0){
			totalPage = mDTOS.size()/isize;
		}else{
			totalPage = mDTOS.size()/isize+1;
		}
		pd.setTotalPage(mDTOS.size()/isize+1);		
		
		PrintWriter out = response.getWriter();
		JSONWriter jw = new JSONWriter();
		String str = jw.write(pd);
		out.println(str);				
				
		return null;	
	}
	
	

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
		
	}


	public Message getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getListtype() {
		return listtype;
	}

	public void setListtype(String listtype) {
		this.listtype = listtype;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Long getExpertid() {
		return expertid;
	}

	public void setExpertid(Long expertid) {
		this.expertid = expertid;
	}
	public String getKnowledgetype() {
		return knowledgetype;
	}

	public void setKnowledgetype(String knowledgetype) {
		this.knowledgetype = knowledgetype;
	}

}

		