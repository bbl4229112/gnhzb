package edu.zju.cims201.GOF.service.knowledge.comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jms.Session;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.opensymphony.xwork2.config.Configuration;
import edu.zju.cims201.GOF.dao.knowledge.CommentDao;
import edu.zju.cims201.GOF.dao.knowledge.CommentRecordDao;
import edu.zju.cims201.GOF.dao.knowledge.KnowledgeDao;
import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.dao.knowledge.RatingDao;
import edu.zju.cims201.GOF.dao.knowledge.VoteDao;
import edu.zju.cims201.GOF.dao.user.SystemUserDao;
import edu.zju.cims201.GOF.hibernate.pojo.Attachment;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.CommentRecord;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.OntoComment;
import edu.zju.cims201.GOF.hibernate.pojo.Rating;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Vote;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	@Resource(name="commentDao")
	private CommentDao commentdao;
	@Resource(name="voteDao")
	private VoteDao votedao;
	@Resource(name="ratingDao")
	private RatingDao ratingdao;
	@Resource(name="systemUserDao")
	private SystemUserDao userdao;
	@Resource(name="metaKnowledgeDao")
	private MetaKnowledgeDao metaknowledgedao;
	@Resource(name="commentRecordDao")
	private CommentRecordDao commentrecorddao;


	public String addComment(Comment comment) {	
		comment.setTail(1);
		commentdao.save(comment);
		//改变所有父节点的tail值
		Comment fatherNode = comment.getCommented(); 
		while(fatherNode != null){
			fatherNode.setTail(0);
			commentdao.save(fatherNode);
			fatherNode = fatherNode.getCommented();
		}
		commentdao.flush();
		return "1";	
	}
		

	public String addVote(Vote vote) {
		//System.out.println("addvote+++++++++++++++++++++++++++++");
		int totalcount = 0;
		votedao.save(vote);
		votedao.flush();
		
		if(vote.getIsSupport()){
			commentdao.get(vote.getComment().getId()).setSupportVoteCount(commentdao.get(vote.getComment().getId()).getSupportVoteCount()+1);
		}else{
			commentdao.get(vote.getComment().getId()).setAgainstVoteCount(commentdao.get(vote.getComment().getId()).getAgainstVoteCount()+1);
		}

		
		if(commentdao.get(vote.getComment().getId()).getComments().size()>0) {
			totalcount = getHeatCounts(vote.getComment().getId())+1;
			
		}
		//System.out.println("shu"+totalcount);
		
		commentdao.get(vote.getComment().getId()).setHeat(commentdao.get(vote.getComment().getId()).getSupportVoteCount()+commentdao.get(vote.getComment().getId()).getAgainstVoteCount()+totalcount);
		commentdao.flush();
		return "1";
		
	}
		
	public Integer getHeatCounts(Long commentid) {
		int count=0;
		Set<Comment> cts = commentdao.get(commentid).getComments();
		
		Iterator it=cts.iterator();
		while(it.hasNext())
		 {count++;
		 Comment temp=(Comment)it.next();
		 if(temp.getComments()!=null)
			 count+=getHeatCounts(temp.getId());			
		 }
		return count;
	}
	
	public String deleteComments(Long commentid)
	{   Comment comment= getComment(commentid);
		if(!comment.getComments().isEmpty()&&null!=comment.getIsBest()&&comment.getIsBest()!=1)
		
		{
		 for(Comment tempcomment:comment.getComments())	
		 {
			 deleteComments(tempcomment.getId());
		 }
			
		}
		if(comment.getIsBest()==1){
			Comment headcomment=comment.getCommented();
			Set<Comment> commentset=headcomment.getComments();
			commentset.remove(comment);
			headcomment.setComments(commentset);
			commentdao.save(headcomment);
		
			comment.setCommented(null);
			comment.setTail(0);
			commentdao.save(comment);
			commentdao.flush();
		} 
		else
		deleteComment(comment.getId());
		return null;
	}

	public String deleteComment(Long commentid) {
		
		Comment tempcomment=commentdao.findUniqueBy("id", commentid);
		CommentRecord cr=tempcomment.getKnowledge().getCommentrecord();
		cr.setCommentCount(cr.getCommentCount()-1);
		commentrecorddao.save(cr);
		
		commentdao.delete(commentid);
		commentdao.flush();
		commentrecorddao.flush();
		return "1";
	}
	
	public String deleteComment(Comment comment) {
		
		return deleteComment(comment.getId());
	}
	

	public Float getAverageRating(MetaKnowledge knowledge) {
		int count = 0;
		Float totalscore = 0f;
		Float averagescore;
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("knowledge", knowledge.getId());
		String hql = "from Rating o where o.knowledge.id= :knowledge";
		List<Rating> ratinglists=(ArrayList<Rating>)ratingdao.createQuery(hql, params).list();
		System.out.println("得到的分数组"+ratinglists);
		for(Rating rating:ratinglists) {		
			totalscore += rating.getScore();
			count++;
		}
		if(count>0) {
			averagescore = totalscore / count;
			
		} else {
			return null;
		}		
		return averagescore;
	}

	public List<Comment> getComments(MetaKnowledge knowledge) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("knowledge", knowledge.getId());

		String hql = "from Comment o where o.knowledge.id= :knowledge order by o.commmentTime desc";
	//	String hql = "from Comment o where o.knowledge.id= :knowledge and o.tail = 1 order by o.commmentTime desc";
		Query query=commentdao.createQuery(hql, params);

		List<Comment> commentlist=(ArrayList<Comment>)query.list();

		return  commentlist;
	}

	public List<Comment> getHotComments(MetaKnowledge knowledge) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("knowledge", knowledge.getId());
		String hql = "from Comment o where o.knowledge.id= :knowledge  order by o.heat desc";
		Query query=commentdao.createQuery(hql, params);
		query.setFirstResult(0);
		query.setMaxResults(5);
		List<Comment> hotcommentlist=(ArrayList<Comment>)query.list();

		return hotcommentlist;		
	}

	public boolean isRated(Long userid, Long knowledgeID) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("user", userid);
		params.put("knowledge",knowledgeID);
		
		String hql = "from Rating o where o.rater.id= :user and o.knowledge.id= :knowledge ";
		List<Rating> ratinglist=(ArrayList<Rating>)ratingdao.createQuery(hql, params).list();
		if(ratinglist.size()>0)
			return true;	
		return false;
		
	}
	

	public boolean isVoted(Long userid, Long commentid) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("user", userid);
		params.put("comment",commentid);
		
		String hql = "from Vote o where o.user.id= :user and o.comment.id= :comment ";
		List<Vote> votelist=(ArrayList<Vote>)votedao.createQuery(hql, params).list();
		if(votelist.size()>0)
			return true;	
		return false;
		
	}
	public Comment getComment(Long commentid) {
		Comment result = commentdao.findUniqueBy("id", commentid);
		return result;
	}

	public String rate(Long userid, Long knowledgeID, Float score) {
		Rating rating = new Rating();
		SystemUser user = userdao.findUniqueBy("id", userid);
		MetaKnowledge knowledge = metaknowledgedao.findUniqueBy("id", knowledgeID);
		CommentRecord cr=knowledge.getCommentrecord();
		Long ratecount=cr.getRatecount();
		if(ratecount==null)
			ratecount=new Long(0);
			cr.setRatecount(ratecount+1);
		commentrecorddao.save(cr);
	
		rating.setKnowledge(knowledge);
		rating.setRater(user);
		rating.setRatingTime(new Date());
		rating.setScore(score);
		ratingdao.save(rating);
		ratingdao.flush();
		commentrecorddao.flush();
		return "1";
	}



	public String updateComment(Comment comment) {
		commentdao.save(comment);
		commentdao.flush();
		return "1";
	}
	
	//回答模块
	public List<Comment> getMyAnswers(SystemUser user) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("userid", user.getId());
		String hql = "from Comment o where o.commenter.id= :userid";
		Query query=commentdao.createQuery(hql, params);		
		List<Comment> myAnswers=(ArrayList<Comment>)query.list();

		return myAnswers;		
	}


	public void deleteCommentByKnowledge(MetaKnowledge mk) {
		List<Comment> mks = this.commentdao.findBy("knowledge", mk);
		for(Comment a : mks){
			this.commentdao.delete(a);			
		}
	}
	



}

