package edu.zju.cims201.GOF.service.onto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;


import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.OntoBuild;
import edu.zju.cims201.GOF.hibernate.pojo.OntoComment;
import edu.zju.cims201.GOF.hibernate.pojo.Rating;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Vote;

/**
 * 提供关于知识评论相关的服务接口，通过具体实现类来实现相关方法
 * 
 * @author cwd
 */
public interface OntoCommentService {
	
	/**
	 * 用户获取相关知识文档的评论
	 * @param knowledge 知识文档
	
	 * @return 返回相关知识文档的评论
	 * @author cwd
	 */
	public List<OntoComment> getComments(OntoBuild ontobuild,String listtype);
	/**
	 * 用户获取相关知识文档的最热评论
	 * @param knowledge 知识文档
	
	 * @return 返回相关知识文档的最热评论
	 * @author cwd
	 */
	public List<OntoComment> getHotComments(OntoBuild ontobuild);
	/**
	 * 添加用户对相关知识文档的评论
	 * @param comment 评论
	 * @return 通过返回的String值判断是否操作成功
	 * @author cwd
	 */
	public OntoComment addComment(OntoComment comment);
	/**
	 * 更新
	 * @param comment 评论
	 * @return 通过返回的String值判断是否操作成功
	 * @author cwd
	 */
	public String updateComment(OntoComment comment);
	/**
	 * 判断是否对评论投票
	 * @param user 用户
	 * @param comment 评论
	 * @return boolean
	 * @author cwd
	 */
	public boolean isVoted(Long userid,Long commentid);
	/**
	 * 判断用户是否已对知识文档打分
	 * @param user 用户
	 * @param knowledge 知识文档
	 * @return boolean
	 * @author cwd
	 */
	public boolean isRated(Long userid,Long ontocommentID);
	/**
	 * 获取知识文档的平均评分
	 * @param knowledge 知识文档
	 * @return 返回相关知识文档的平均评分
	 * @author cwd
	 */
	public Float getAverageRating(OntoComment ontocommentId);
	
	public String rate(Long userid,Long ontocommentID,Float score);

	public OntoComment getComment(Long commentid);
	
	public String deleteComment(Long commentid);
	public String deleteComments(Long commentid);
	public OntoBuild getOntoBuild(Long ontobuildID);
	 public OntoBuild getOntoBuild(String ontobuildname);
    public OntoBuild addOntoBuild(OntoBuild ontobuild);
	public OntoComment getTailComment(OntoComment comment);
	//public String addVote(Vote vote);
	
	//回答模块
	/**
	 * 获取我的回答列表
	 * @param user 用户
	 * @return 用户的回答列表
	 * @author cwd
	 */
	public List<OntoComment> getMyAnswers(SystemUser user);
	
	
	

}


