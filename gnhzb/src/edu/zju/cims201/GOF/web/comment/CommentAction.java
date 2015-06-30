package edu.zju.cims201.GOF.web.comment;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.stringtree.json.JSONWriter;


import com.ibm.icu.text.DateFormat;
import com.opensymphony.xwork2.ActionContext;


import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Vote;



import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.CommentDTO;
import edu.zju.cims201.GOF.rs.dto.VoteDTO;

import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.knowledge.comment.CommentService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.HibernatePorxy;
import edu.zju.cims201.GOF.web.CrudActionSupport;



/**
 * 评论管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 * 
 * @author cwd
 */
//定义URL映射对应/comment/comment.action
@Namespace("/comment")
//定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "comment.action", type = "redirect") })

public class CommentAction extends CrudActionSupport<Comment> implements ServletResponseAware{


	private static final long serialVersionUID = 8683878162525847072L;
	@Resource(name="commentServiceImpl")
	private CommentService commentservice;
	@Resource(name="knowledgeServiceImpl")
	private KnowledgeService knowledgeservice;
	@Resource(name="userServiceImpl")
	private UserService userservice;
	@Resource(name="messageServiceImpl")
	private MessageService messageService;

	//-- 页面属性 --//
	private Long id;
	private Long commentid;
	private Long knowledgeID;
	private String content;
	private Float score;
	private Boolean isSupport;
	private Comment entity;
	
	private HttpServletResponse response;
	private int size;
	private int index;

	@Override
	public String delete() throws Exception {		

		commentservice.deleteComments(commentid);
		//System.out.println("删除一些操作！，并转向 list即 comment.aciton");
		return null;
	}
	
	@Override
	public String input() throws Exception {
		
		return INPUT;
	}
		
	@Override
	public String list() throws Exception {
	
		return SUCCESS;
	}
	
	//列出相关知识的评论
	public String listComment() throws Exception {

		PageDTO pd = new PageDTO();
	
	//	System.out.println("已经到ACTION");
		
		//Long knowledgeID= new Long(4);
	//	System.out.println("shibushi"+knowledgeservice.getMetaknowledge(knowledgeID));
		MetaKnowledge knowledge = knowledgeservice.getMetaknowledge(knowledgeID);
		pd.setKccounts(commentservice.getComments(knowledge).size());
		System.out.println(commentservice.getComments(knowledge).size());
		if(null==knowledge.getCommentrecord())
		{
			CommentRecord cr = new CommentRecord();
			cr.setCommentCount(new Long(0));
			cr.setViewCount(new Long(0));
			cr.setRate(new Float(0));
			cr.setDownloadCount(new Long(0));
			knowledge.setCommentrecord(cr);
		}
		else
		{knowledge.getCommentrecord().setCommentCount((long)(commentservice.getComments(knowledge).size()));}
		knowledgeservice.updateKnowledge(knowledge);
	
		List<CommentDTO>cdtos=new ArrayList<CommentDTO>();
		List<Comment> clist=commentservice.getComments(knowledge);

		for (Comment comment: clist) {
			Comment commented=comment.getCommented();			
			if(null==commented){
				CommentDTO commentdto=new CommentDTO();
				Set<CommentDTO> commentdtos1 = new TreeSet<CommentDTO>();
				commentdto.setId(comment.getId());
				commentdto.setCommenterName(comment.getCommenter().getName());
				commentdto.setCommenterpicpath("thumbnail/"+comment.getCommenter().getPicturePath());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
				String commenttime=sdf.format(comment.getCommmentTime());
				commentdto.setCommmentTime(commenttime);
				commentdto.setContent(comment.getContent());
				commentdto.setIsBest(comment.getIsBest());				
				commentdto.setHeat(comment.getHeat());
				commentdto.setKnowledgeid(comment.getKnowledge().getId());
				commentdto.setSupportVoteCount(comment.getSupportVoteCount());
				commentdto.setAgainstVoteCount(comment.getAgainstVoteCount());
				Set<Comment> coms = comment.getComments();
				if(null!=coms){					
					for(Comment comment1:coms) {
						CommentDTO commentdto1 = getCommentDTOs(comment1);
						commentdtos1.add(commentdto1);
						commentdto.setCommentdtos(commentdtos1);
						
					}
				}
				cdtos.add(commentdto);
			}		
		}

		
		List<CommentDTO> subList = new ArrayList<CommentDTO>();		
		for(int i=index*size;i<((index+1)*size<cdtos.size()?(index+1)*size:cdtos.size());i++){
			subList.add(cdtos.get(i));
		}				
		pd.setData(subList);
		pd.setTotal(cdtos.size());		
		int totalPage;
		if(size != 0) {
			if(cdtos.size()%size == 0){
				totalPage = cdtos.size()/size;
			}else{
				totalPage = cdtos.size()/size+1;
			}
			pd.setTotalPage(cdtos.size()/size+1);			
		}		
		JSONWriter writer = new JSONWriter();
        String ktypestring=writer.write(pd);  
       	response.getWriter().print(ktypestring);
		
	return null;
	}
	
	protected CommentDTO getCommentDTOs(Comment comment)
	{
		
			CommentDTO commentdto=new CommentDTO();
			Set<CommentDTO> commentdtos = new HashSet<CommentDTO>();
			commentdto.setId(comment.getId());
			commentdto.setAgainstVoteCount(comment.getAgainstVoteCount());
			commentdto.setCommenterName(comment.getCommenter().getName());
			commentdto.setCommenterpicpath("thumbnail/"+comment.getCommenter().getPicturePath());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
			String commenttime=sdf.format(comment.getCommmentTime());
			commentdto.setCommmentTime(commenttime);
			commentdto.setCommented(getCommentDTO(comment.getCommented()));
			commentdto.setCommenterpicpath("thumbnail/"+comment.getCommenter().getPicturePath());
			commentdto.setContent(comment.getContent());
			commentdto.setIsBest(comment.getIsBest());
			commentdto.setHeat(comment.getHeat());
			commentdto.setKnowledgeid(comment.getKnowledge().getId());
			commentdto.setSupportVoteCount(comment.getSupportVoteCount());
			Set<Comment> comments = comment.getComments();
	        if(null!=comments){
	        	
	        	for(Comment comment2:comments) {
	        		CommentDTO commentdto2 = getCommentDTOs(comment2);	        		
	        		commentdtos.add(commentdto2);
	        		commentdto.setCommentdtos(commentdtos);
					
				}

	        } 				
		return commentdto;
	} 
	//列出相关知识的评论
	public String listBestAnswer() throws Exception {
		MetaKnowledge knowledge = knowledgeservice.getMetaknowledge(knowledgeID);
	
	
		List<CommentDTO>bestAnswerList=new ArrayList<CommentDTO>();
		List<Comment> clist=commentservice.getComments(knowledge);
		
		
		for (Comment comment: clist) {
					if(comment.getIsBest()==1){
					CommentDTO commentdto=new CommentDTO();
					commentdto.setId(comment.getId());
					commentdto.setCommented(getCommentDTO(comment.getCommented()));
					commentdto.setCommenterName(comment.getCommenter().getName());
					commentdto.setCommenterpicpath("thumbnail/"+comment.getCommenter().getPicturePath());
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
					String commenttime=sdf.format(comment.getCommmentTime());
				    commentdto.setCommmentTime(commenttime);
					commentdto.setContent(comment.getContent());
					commentdto.setHeat(comment.getHeat());
					commentdto.setIsBest(comment.getIsBest());
					commentdto.setKnowledgeid(comment.getKnowledge().getId());
					commentdto.setSupportVoteCount(comment.getSupportVoteCount());
					commentdto.setAgainstVoteCount(comment.getAgainstVoteCount());
				
			
					bestAnswerList.add(commentdto);
					 }
			}

	
		JSONWriter writer = new JSONWriter();
        String bestAnswerList_string=writer.write(bestAnswerList);  
       	response.getWriter().print(bestAnswerList_string);
		
	return null;
	}

	//列出相关知识的最热评论
	public String listHotComment() throws Exception {
		

		MetaKnowledge knowledge = knowledgeservice.getMetaknowledge(knowledgeID);
				
		List<CommentDTO>cdtos=new ArrayList<CommentDTO>();
		List<Comment> hclist=commentservice.getHotComments(knowledge);
				
		for (Comment comment: hclist) {
			CommentDTO commentdto=new CommentDTO();
			commentdto.setId(comment.getId());
			commentdto.setCommenterName(comment.getCommenter().getName());
			commentdto.setCommenterpicpath("thumbnail/"+comment.getCommenter().getPicturePath());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String commenttime=sdf.format(comment.getCommmentTime());
		
		     commentdto.setCommmentTime(commenttime);
			commentdto.setContent(comment.getContent());
			commentdto.setHeat(comment.getHeat());
			commentdto.setKnowledgeid(comment.getKnowledge().getId());
			commentdto.setSupportVoteCount(comment.getSupportVoteCount());
			commentdto.setAgainstVoteCount(comment.getAgainstVoteCount());
				
			cdtos.add(commentdto);
		}
		
		JSONWriter jw=new JSONWriter();
		String json=jw.write(cdtos);
		response.getWriter().print(json);
		
		return null;
	}
	

//	protected CommentDTO getCommentDTO(Comment comment)
//	{
//		
//			CommentDTO commentdto=new CommentDTO();
//			commentdto.setId(comment.getId());
//			commentdto.setAgainstVoteCount(comment.getAgainstVoteCount());
//			commentdto.setCommenterName(comment.getCommenter().getName());
//			commentdto.setCommmentTime(comment.getCommmentTime().toString());
//			commentdto.setContent(comment.getContent());
//			commentdto.setHeat(comment.getHeat());
//			commentdto.setKnowledgeid(comment.getKnowledge().getId());
//			commentdto.setSupportVoteCount(comment.getSupportVoteCount());
//			Comment commented=comment.getCommented();
//	        if(null!=commented){
//	        	CommentDTO subcommentdto=getCommentDTO(commented);
//	        	commentdto.setCommented(subcommentdto);
//	        } 
//		
//		
//		return commentdto;
//	} 

	protected CommentDTO getCommentDTO(Comment comment)
	{
		if(comment!=null){
			CommentDTO commentdto=new CommentDTO();
			commentdto.setId(comment.getId());
			commentdto.setAgainstVoteCount(comment.getAgainstVoteCount());
			commentdto.setCommenterpicpath("thumbnail/"+comment.getCommenter().getPicturePath());
			commentdto.setCommenterName(comment.getCommenter().getName());
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String commenttime=sdf.format(comment.getCommmentTime());
			commentdto.setIsBest(comment.getIsBest());

		     commentdto.setCommmentTime(commenttime);
			commentdto.setContent(comment.getContent());
			commentdto.setHeat(comment.getHeat());
			commentdto.setKnowledgeid(comment.getKnowledge().getId());
			commentdto.setSupportVoteCount(comment.getSupportVoteCount());
			Comment commented=comment.getCommented();
	        if(null!=commented){
	        	CommentDTO subcommentdto=getCommentDTO(commented);
	        	commentdto.setCommented(subcommentdto);
	        } 
		
		
		return commentdto;}
		return null;
	} 

	
	@Override
	protected void prepareModel() throws Exception {
		if (entity == null) {
			entity = new Comment();
		}
	}
   
	
	
	@Override
	public String save() throws Exception {

		if(knowledgeID != null && commentid == null) {

			MetaKnowledge knowledge = knowledgeservice.getMetaknowledge(knowledgeID);
			Comment comment =new Comment();
			comment.setCommenter(userservice.getUser());
			comment.setKnowledge(knowledge);
			comment.setContent(content);
			comment.setCommmentTime(new Date());
			comment.setSupportVoteCount(0);
			comment.setAgainstVoteCount(0);	
			//if(knowledge.getKtype().getName().equals("Question")){
				comment.setIsBest(0);
			//}
			commentservice.addComment(comment);
			addActionMessage("保存评论成功");
		
			if(knowledge.getKtype().getName().equals("Question")&&(PropertyUtils.getProperty(knowledge, "questionstatus").equals(0L))){
				PropertyUtils.setProperty(knowledge, "questionstatus",1L);			
				knowledgeservice.updateKnowledge(knowledge);
			}
			
			if(knowledge.getKtype().getName().equals("Question")){
				List<Message> messageList=messageService.listMessages(userservice.getUser().getId());
				for(Message m:messageList)
				  {
					if(m.getMessageType().equals("askForAnswer")){
						if(m.getKnowledge().equals(knowledge)&&m.getIsAnswered().equals(false)&&
								(!m.getKnowledge().getUploader().getEmail().equals(userservice.getUser().getEmail()))){
							m.setIsAnswered(true);
							messageService.save(m);	 
						}						
					}
				  }
			}
						
		}
		if(knowledgeID != null && commentid != null) {
			
			MetaKnowledge knowledge = knowledgeservice.getMetaknowledge(knowledgeID);			
			Comment comment =new Comment();
			comment.setCommenter(userservice.getUser());
			Comment commented = commentservice.getComment(commentid);
			comment.setCommented(commented);
			comment.setContent(content);
			comment.setCommmentTime(new Date());
			comment.setKnowledge(commented.getKnowledge());
			comment.setSupportVoteCount(0);
			comment.setAgainstVoteCount(0);
			//if(knowledge.getKtype().getName().equals("Question")){
				comment.setIsBest(0);
			
			comment.setTail(1);
			commentservice.addComment(comment);
			
			addActionMessage("保存评论的评论成功");
			if(knowledge.getKtype().getName().equals("Question")){
				List<Message> messageList=messageService.listMessages(userservice.getUser().getId());				
				for(Message m:messageList)
				  {
					if(m.getMessageType().equals("askForAnswer")){
						if(m.getKnowledge().equals(knowledge)&&m.getIsAnswered().equals(false)&&
								(!m.getKnowledge().getUploader().getEmail().equals(userservice.getUser().getEmail()))){
							m.setIsAnswered(true);
							messageService.save(m);	 
						}						
					}
					
				  }
			}
		}
		
		return null;
	}
	public String bestAnswer() throws Exception {

		if( commentid != null) {

			Comment comment =commentservice.getComment(commentid);
			comment.setIsBest(1);
			commentservice.updateComment(comment);
			if(comment.getKnowledge().getKtype().getName().equals("Question")){
				MetaKnowledge k = comment.getKnowledge();
				HibernatePorxy hp= new HibernatePorxy<MetaKnowledge>();
				k=(MetaKnowledge)hp.getRealEntity(k);				
				PropertyUtils.setProperty(k, "questionstatus",2L);			
				knowledgeservice.updateKnowledge(k);
			}
			
			addActionMessage("保存评论成功");
		}

		
		return null;
	}

	

	public String addCommentVote() throws Exception {
		//需传commentid、isSupport
		VoteDTO vd = new VoteDTO();
		if(commentservice.isVoted(userservice.getUser().getId(),commentid)) {
			vd.setIsSupport(false);
			JSONWriter writer = new JSONWriter();
	        String ktypestring=writer.write(vd);  
	       	response.getWriter().print(ktypestring);
			addActionMessage("重复投票无效");
		}else{
			
			Vote vote = new Vote();
			
			vote.setUser(userservice.getUser());
			Comment comment = commentservice.getComment(commentid);
			vote.setComment(comment);
			vote.setIsSupport(isSupport);
			vote.setVoteTime(new Date());
			commentservice.addVote(vote);
			
			vd.setIsSupport(true);
			JSONWriter writer = new JSONWriter();
	        String ktypestring=writer.write(vd);  
	       	response.getWriter().print(ktypestring);
			addActionMessage("评论投票成功");
		}
		return null;
	}
	
	public String rating() throws Exception {
		//需传knowledgeID、score
		VoteDTO vd = new VoteDTO();
		if(commentservice.isRated(userservice.getUser().getId(), knowledgeID)) {
			vd.setIsSupport(false);
			JSONWriter writer = new JSONWriter();
	        String ktypestring=writer.write(vd);  
	       	response.getWriter().print(ktypestring);
			addActionMessage("重复打分无效");
		}else{		
			commentservice.rate(userservice.getUser().getId(), knowledgeID, score);
			vd.setIsSupport(true);
			JSONWriter writer = new JSONWriter();
	        String ktypestring=writer.write(vd);  
	       	response.getWriter().print(ktypestring);
	       	averageRating();
			addActionMessage("打分成功");
			
		}
		return null;
	}
	
	public Float averageRating() throws Exception {
		//需传knowledgeID
		//Long knowledgeID= new Long(9);
		MetaKnowledge knowledge = knowledgeservice.getMetaknowledge(knowledgeID);
		Float averagescore = commentservice.getAverageRating(knowledge);
		knowledge.getCommentrecord().setRate(averagescore);
		knowledgeservice.updateKnowledge(knowledge);
		addActionMessage("得到平均分成功");
		return averagescore;
	}
				
	public Long getKnowledgeID() {
		return knowledgeID;
	}

	public void setKnowledgeID(Long knowledgeID) {
		this.knowledgeID = knowledgeID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;		
	}
	
	public Long getCommentid() {
		return commentid;
	}

	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Comment getEntity() {
		return entity;
	}

	public void setEntity(Comment entity) {
		this.entity = entity;
	}

	public Comment getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public CommentService getCommentservice() {
		return commentservice;
	}

	public void setCommentservice(CommentService commentservice) {
		this.commentservice = commentservice;
	}

	public KnowledgeService getKnowledgeservice() {
		return knowledgeservice;
	}

	public void setKnowledgeservice(KnowledgeService knowledgeservice) {
		this.knowledgeservice = knowledgeservice;
	}

	public Boolean getIsSupport() {
		return isSupport;
	}

	public void setIsSupport(Boolean isSupport) {
		this.isSupport = isSupport;
	}

	public UserService getUserservice() {
		return userservice;
	}

	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}
	
	


}
