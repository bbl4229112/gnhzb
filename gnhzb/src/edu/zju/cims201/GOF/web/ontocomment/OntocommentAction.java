package edu.zju.cims201.GOF.web.ontocomment;

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


import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;

import org.stringtree.json.JSONWriter;



import edu.zju.cims201.GOF.hibernate.pojo.Comment;

import edu.zju.cims201.GOF.hibernate.pojo.OntoBuild;
import edu.zju.cims201.GOF.hibernate.pojo.OntoComment;


import edu.zju.cims201.GOF.rs.dto.OntoBuildDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.CommentDTO;
import edu.zju.cims201.GOF.rs.dto.VoteDTO;


import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.onto.OntoCommentService;
import edu.zju.cims201.GOF.service.systemUser.UserService;

import edu.zju.cims201.GOF.util.JSONUtil;
import edu.zju.cims201.GOF.web.CrudActionSupport;

/**
 * 评论管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数. 演示带分页的管理界面.
 * 
 * @author cwd
 */
// 定义URL映射对应/comment/comment.action
@Namespace("/comment")
// 定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results({ @Result(name = CrudActionSupport.RELOAD, location = "comment.action", type = "redirect") })
public class OntocommentAction extends CrudActionSupport<Comment> implements
		ServletResponseAware {

	private static final long serialVersionUID = 8683878162525847072L;
	@Resource(name = "ontoCommentServiceImpl")
	private OntoCommentService commentservice;

	@Resource(name = "userServiceImpl")
	private UserService userservice;
	@Resource(name = "messageServiceImpl")
	private MessageService messageService;

	// -- 页面属性 --//
	private Long id;
	private Long commentid;
	private Long ontobuildID;
	private Long ontoCommentID;

	public Long getOntoCommentID() {
		return ontoCommentID;
	}

	public void setOntoCommentID(Long ontoCommentID) {
		this.ontoCommentID = ontoCommentID;
	}

	public Long getOntobuildID() {
		return ontobuildID;
	}

	public void setOntobuildID(Long ontobuildID) {
		this.ontobuildID = ontobuildID;
	}

	private String content;
	private Float score;
	private Boolean isSupport;
	private Comment entity;

	private HttpServletResponse response;
	private int size;
	private int index;

	@Override
	public String delete() throws Exception {

		commentservice.deleteComments(ontoCommentID);
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

	// 列出相关知识的评论
	public String listComment() throws Exception {
		
		PageDTO pd = new PageDTO();

		OntoBuild ontobuild = commentservice.getOntoBuild(ontobuildID);
		pd.setKccounts(commentservice.getComments(ontobuild,content).size());
	
		List<CommentDTO> cdtos = new ArrayList<CommentDTO>();
		List<OntoComment> clist = commentservice.getComments(ontobuild,content);

		for (OntoComment comment : clist) {
			OntoComment commented = comment.getCommented();
			if (null == commented) {
				CommentDTO commentdto = new CommentDTO();
				Set<CommentDTO> commentdtos1 = new TreeSet<CommentDTO>();
				commentdto.setId(comment.getId());
				commentdto.setCommenterName(comment.getCommenter().getName());
				commentdto.setCommenterpicpath("thumbnail/"
						+ comment.getCommenter().getPicturePath());
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String commenttime = sdf.format(comment.getCommmentTime());
				commentdto.setCommmentTime(commenttime);
				commentdto.setContent(comment.getContent());
				commentdto.setIsBest(comment.getIsBest());
				commentdto.setHeat(comment.getHeat());
				commentdto.setRate(comment.getRate());
				commentdto.setKnowledgeid(comment.getOntobuild().getId());
				commentdto.setSupportVoteCount(comment.getSupportVoteCount());
				commentdto.setAgainstVoteCount(comment.getAgainstVoteCount());
				Set<OntoComment> coms = comment.getComments();
				if (null != coms) {
					for (OntoComment comment1 : coms) {
						CommentDTO commentdto1 = getCommentDTOs(comment1);
						commentdtos1.add(commentdto1);
						commentdto.setCommentdtos(commentdtos1);

					}
				}
				cdtos.add(commentdto);
			}
		}

		List<CommentDTO> subList = new ArrayList<CommentDTO>();
		for (int i = index * size; i < ((index + 1) * size < cdtos.size() ? (index + 1)
				* size
				: cdtos.size()); i++) {
			subList.add(cdtos.get(i));
		}
		pd.setData(subList);
		pd.setTotal(cdtos.size());
		int totalPage;
		if (size != 0) {
			if (cdtos.size() % size == 0) {
				totalPage = cdtos.size() / size;
			} else {
				totalPage = cdtos.size() / size + 1;
			}
			pd.setTotalPage(cdtos.size() / size + 1);
		}
		JSONWriter writer = new JSONWriter();
		String ktypestring = writer.write(pd);
		response.getWriter().print(ktypestring);

		return null;
	}

	protected CommentDTO getCommentDTOs(OntoComment comment) {

		CommentDTO commentdto = new CommentDTO();
		Set<CommentDTO> commentdtos = new HashSet<CommentDTO>();
		commentdto.setId(comment.getId());
		commentdto.setAgainstVoteCount(comment.getAgainstVoteCount());
		commentdto.setCommenterName(comment.getCommenter().getName());
		commentdto.setCommenterpicpath("thumbnail/"
				+ comment.getCommenter().getPicturePath());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String commenttime = sdf.format(comment.getCommmentTime());
		commentdto.setCommmentTime(commenttime);
		commentdto.setCommented(getCommentDTO(comment.getCommented()));
		commentdto.setCommenterpicpath("thumbnail/"
				+ comment.getCommenter().getPicturePath());
		commentdto.setContent(comment.getContent());
		commentdto.setIsBest(comment.getIsBest());
		commentdto.setHeat(comment.getHeat());
		commentdto.setRate(comment.getRate());
		commentdto.setKnowledgeid(comment.getOntobuild().getId());
		commentdto.setSupportVoteCount(comment.getSupportVoteCount());
		Set<OntoComment> comments = comment.getComments();
		if (null != comments) {

			for (OntoComment comment2 : comments) {
				CommentDTO commentdto2 = getCommentDTOs(comment2);
				commentdtos.add(commentdto2);
				commentdto.setCommentdtos(commentdtos);

			}

		}
		return commentdto;
	}



	protected CommentDTO getCommentDTO(OntoComment comment) {
		if (comment != null) {
			CommentDTO commentdto = new CommentDTO();
			commentdto.setId(comment.getId());
			commentdto.setAgainstVoteCount(comment.getAgainstVoteCount());
			commentdto.setCommenterpicpath("thumbnail/"
					+ comment.getCommenter().getPicturePath());
			commentdto.setCommenterName(comment.getCommenter().getName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String commenttime = sdf.format(comment.getCommmentTime());
			commentdto.setIsBest(comment.getIsBest());
			commentdto.setRate(comment.getRate());
			commentdto.setCommmentTime(commenttime);
			commentdto.setContent(comment.getContent());
			commentdto.setHeat(comment.getHeat());
			commentdto.setKnowledgeid(comment.getOntobuild().getId());
			commentdto.setSupportVoteCount(comment.getSupportVoteCount());
			OntoComment commented = comment.getCommented();
			if (null != commented) {
				CommentDTO subcommentdto = getCommentDTO(commented);
				commentdto.setCommented(subcommentdto);
			}

			return commentdto;
		}
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (entity == null) {
			entity = new Comment();
		}
	}

	public String searchandcreatontobuild() {
		if (null != content) {
			OntoBuildDTO ontodto=new OntoBuildDTO();
			OntoBuild ontobuild = commentservice.getOntoBuild(content);
			if (null == ontobuild) {
				ontobuild = new OntoBuild();
				ontobuild.setCreater(userservice.getUser());
				ontobuild.setCreattime(new Date());
				
				ontobuild.setOntoname(content);
				ontobuild.setHasExplain(false);
				commentservice.addOntoBuild(ontobuild);
				if (null != ontobuild && null != ontobuild.getId()) {
					ontodto.setCreattime(ontobuild.getCreattime().toLocaleString());
					ontodto.setId(ontobuild.getId());
					ontodto.setCreatername(userservice.getUser().getName());
					ontodto.setCreateremail(userservice.getUser().getEmail());
					ontodto.setOntocommentcount(0);
					ontodto.setOntocommentratecount(0);
					ontodto.setOntoname(content);
					JSONUtil.write(response, ontodto);

				} else
					JSONUtil.write(response, "error");
			} else {
				ontodto.setOntoname(content);
				ontodto.setCreattime(ontobuild.getCreattime().toLocaleString());
				ontodto.setId(ontobuild.getId());
				ontodto.setCreatername(ontobuild.getCreater().getName());
				ontodto.setCreateremail(ontobuild.getCreater().getEmail());
				List<OntoComment> ontocommentlist=commentservice.getComments(ontobuild, null);
				if(commentservice.getComments(ontobuild, null).isEmpty()){
					ontodto.setOntocommentratecount(0);
				ontodto.setOntocommentcount(0);
				}else
				{   Integer t=0;
					for(OntoComment ontocommenttemp:ontocommentlist)
				{
					t+=ontocommenttemp.getAgainstVoteCount();
				}
					ontodto.setOntocommentcount(ontocommentlist.size());
					ontodto.setOntocommentratecount(t);
				}
				ontodto.setHasExplain(ontobuild.isHasExplain());
				if(null!=ontobuild.getFinalExplainCreater()){
				ontodto.setFinalExplainCreatername(ontobuild.getFinalExplainCreater().getName());
				ontodto.setFinalExplainCreateremail(ontobuild.getFinalExplainCreater().getEmail());
				ontodto.setExplaintime(ontobuild.getExplaintime().toLocaleString());
				ontodto.setFinalExplain(ontobuild.getFinalExplain());
				}
			
				JSONUtil.write(response, ontodto);

			}
		} else
			JSONUtil.write(response, "error");
		return null;
	}

	@Override
	public String save() throws Exception {

		if (ontobuildID != null && commentid == null) {

			OntoBuild ontobuild = commentservice.getOntoBuild(ontobuildID);
			OntoComment comment = new OntoComment();
			comment.setCommenter(userservice.getUser());
			comment.setOntobuild(ontobuild);
			comment.setContent(content);
			comment.setCommmentTime(new Date());
			comment.setSupportVoteCount(0);
			comment.setAgainstVoteCount(0);
			comment.setRate(new Float(0));
			// if(knowledge.getKtype().getName().equals("Question")){
			comment.setIsBest(0);
			// }
			commentservice.addComment(comment);
			

		}
		if (ontobuildID != null && commentid != null) {

			//OntoBuild ontobuild = commentservice.getOntoBuild(ontobuildID);
			OntoComment comment = new OntoComment();
			comment.setCommenter(userservice.getUser());
			OntoComment commented = commentservice.getComment(commentid);
			
			comment.setCommented(commented);
			comment.setContent(content);
			comment.setCommmentTime(new Date());
			comment.setOntobuild(commented.getOntobuild());
			comment.setSupportVoteCount(0);
			comment.setRate(new Float(0));
			comment.setAgainstVoteCount(0);
			// if(knowledge.getKtype().getName().equals("Question")){
			comment.setIsBest(0);

			comment.setTail(1);
			commentservice.addComment(comment);
			OntoComment headCommnet =commentservice.getTailComment(commented);
			Integer ccount=headCommnet.getSupportVoteCount();
			ccount++;
			headCommnet.setSupportVoteCount(ccount);
			commentservice.updateComment(headCommnet);
			
			
		}
      //   JSONUtil.write(response, "保存成功！");
		return null;
	}
	public String savefinalexplain()
	{
		if (null!=ontobuildID&&null!=content) {
			OntoBuild	ontobuild=	commentservice.getOntoBuild(ontobuildID);
			ontobuild.setFinalExplainCreater(userservice.getUser());
			ontobuild.setFinalExplain(content);
			ontobuild.setHasExplain(true);
			ontobuild.setExplaintime(new Date());
			commentservice.addOntoBuild(ontobuild);
			OntoBuildDTO ontodto=new OntoBuildDTO();
			ontodto.setFinalExplainCreatername(ontobuild.getFinalExplainCreater().getName());
			ontodto.setFinalExplainCreateremail(ontobuild.getFinalExplainCreater().getEmail());
			ontodto.setExplaintime(ontobuild.getExplaintime().toLocaleString());
			ontodto.setFinalExplain(ontobuild.getFinalExplain());
			ontodto.setId(ontobuild.getId());
			JSONUtil.write(response, ontodto);
		}
		else
		{
		 JSONUtil.write(response, "error");
		}		
		return null;
	}



	

	public String rating() throws Exception {
		// 需传knowledgeID、score
		VoteDTO vd = new VoteDTO();
		if (commentservice
				.isRated(userservice.getUser().getId(), ontoCommentID)) {
			vd.setIsSupport(false);
			JSONWriter writer = new JSONWriter();
			String ktypestring = writer.write(vd);
			response.getWriter().print(ktypestring);
			addActionMessage("重复打分无效");
		} else {
			commentservice.rate(userservice.getUser().getId(), ontoCommentID,
					score);
			vd.setIsSupport(true);
			JSONWriter writer = new JSONWriter();
			String ktypestring = writer.write(vd);
			response.getWriter().print(ktypestring);
			averageRating();
			addActionMessage("打分成功");

		}
		return null;
	}

	public Float averageRating() throws Exception {
		// 需传knowledgeID
		// Long knowledgeID= new Long(9);
		OntoComment comment = commentservice.getComment(ontoCommentID);
		Float averagescore = commentservice.getAverageRating(comment);
		comment.setRate(averagescore);
		commentservice.updateComment(comment);
		addActionMessage("得到平均分成功");
		return averagescore;
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
		this.response = response;
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
