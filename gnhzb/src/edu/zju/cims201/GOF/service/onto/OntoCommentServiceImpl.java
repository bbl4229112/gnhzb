package edu.zju.cims201.GOF.service.onto;

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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.knowledge.OntobuildsDao;
import edu.zju.cims201.GOF.dao.knowledge.OntoCommentDao;
import edu.zju.cims201.GOF.dao.knowledge.OntoCommentRatingDao;
import edu.zju.cims201.GOF.dao.knowledge.VoteDao;
import edu.zju.cims201.GOF.dao.user.SystemUserDao;

import edu.zju.cims201.GOF.hibernate.pojo.OntoBuild;
import edu.zju.cims201.GOF.hibernate.pojo.OntoComment;
import edu.zju.cims201.GOF.hibernate.pojo.OntoCommentRating;
import edu.zju.cims201.GOF.hibernate.pojo.Rating;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Vote;

@Service
@Transactional
public class OntoCommentServiceImpl implements OntoCommentService {
	@Resource(name="ontoCommentDao")
	private OntoCommentDao commentdao;
	@Resource(name="voteDao")
	private VoteDao votedao;
	@Resource(name="ontoCommentRatingDao")
	private OntoCommentRatingDao ratingdao;
	@Resource(name="systemUserDao")
	private SystemUserDao userdao;
	@Resource(name="ontobuildsDao")
	private OntobuildsDao ontobuilddao;
	


	public OntoComment addComment(OntoComment comment) {	
		comment.setTail(1);
		commentdao.save(comment);
		//改变所有父节点的tail值
		OntoComment fatherNode = comment.getCommented(); 
		while(fatherNode != null){
			fatherNode.setTail(0);
			commentdao.save(fatherNode);
			fatherNode = fatherNode.getCommented();
		}
		commentdao.flush();
		return comment;	
	}
		

//	public String addVote(Vote vote) {
//		//System.out.println("addvote+++++++++++++++++++++++++++++");
//		int totalcount = 0;
//		votedao.save(vote);
//		votedao.flush();
//		
//		if(vote.getIsSupport()){
//			commentdao.get(vote.getComment().getId()).setSupportVoteCount(commentdao.get(vote.getComment().getId()).getSupportVoteCount()+1);
//		}else{
//			commentdao.get(vote.getComment().getId()).setAgainstVoteCount(commentdao.get(vote.getComment().getId()).getAgainstVoteCount()+1);
//		}
//
//		
//		if(commentdao.get(vote.getComment().getId()).getComments().size()>0) {
//			totalcount = getHeatCounts(vote.getComment().getId())+1;
//			
//		}
//		//System.out.println("shu"+totalcount);
//		
//		commentdao.get(vote.getComment().getId()).setHeat(commentdao.get(vote.getComment().getId()).getSupportVoteCount()+commentdao.get(vote.getComment().getId()).getAgainstVoteCount()+totalcount);
//		commentdao.flush();
//		return "1";
//		
//	}
		
	public Integer getHeatCounts(Long commentid) {
		int count=0;
		Set<OntoComment> cts = commentdao.get(commentid).getComments();
		
		Iterator it=cts.iterator();
		while(it.hasNext())
		 {count++;
		 OntoComment temp=(OntoComment)it.next();
		 if(temp.getComments()!=null)
			 count+=getHeatCounts(temp.getId());			
		 }
		return count;
	}
	
	public String deleteComments(Long commentid)
	{   OntoComment comment= getComment(commentid);
		if(!comment.getComments().isEmpty())
		
		{
		 for(OntoComment tempcomment:comment.getComments())	
		 {
			 deleteComments(tempcomment.getId());
		 }
			
		}
		
		deleteComment(comment.getId());
		return null;
	}

	public String deleteComment(Long commentid) {
		OntoComment tempcomment=commentdao.findUniqueBy("id", commentid);
		List<OntoCommentRating> ratelist=ratingdao.findBy("ontocomment",tempcomment );
		if(!ratelist.isEmpty())
		{
			for(OntoCommentRating rate:ratelist)
			{
				ratingdao.delete(rate);
			}
		    	ratingdao.flush();
		}
		OntoComment tempcommented=tempcomment.getCommented();
	    if(null!=tempcommented){
	    	tempcommented.setSupportVoteCount(tempcommented.getSupportVoteCount()-1);
	        commentdao.save(tempcommented);
	    }
		commentdao.delete(commentid);
		commentdao.flush();
		return "1";
	}
	
	public String deleteComment(OntoComment comment) {
		
		return deleteComment(comment.getId());
	}
	

	public Float getAverageRating(OntoComment ontocomment) {
		int count = 0;
		Float totalscore = 0f;
		Float averagescore;
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("ontocomment", ontocomment.getId());
		String hql = "from OntoCommentRating o where o.ontocomment.id= :ontocomment";
		List<OntoCommentRating> ratinglists=(ArrayList<OntoCommentRating>)ratingdao.createQuery(hql, params).list();
		System.out.println("得到的分数组"+ratinglists);
		for(OntoCommentRating rating:ratinglists) {		
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

	public List<OntoComment> getComments(OntoBuild ontobuild,String listtype) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("ontobuild", ontobuild.getId());
        
		String orderby=" ";
		if(null!=listtype&&listtype.equals("shuyuscore"))
			orderby=" rate desc ,";
		if(null!=listtype&&listtype.equals("shuyucount"))
			orderby=" o.supportVoteCount desc ,";
		orderby+=" o.commmentTime desc";
		String hql = "from OntoComment o where o.ontobuild.id= :ontobuild order by "+ orderby;
			Query query=commentdao.createQuery(hql, params);

		List<OntoComment> commentlist=(ArrayList<OntoComment>)query.list();

		return  commentlist;
	}

	public List<OntoComment> getHotComments(OntoBuild ontobuild) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("ontobuild", ontobuild.getId());
		String hql = "from OntoComment o where ontobuild= :ontobuild  order by o.heat desc";
		Query query=commentdao.createQuery(hql, params);
		query.setFirstResult(0);
		query.setMaxResults(5);
		List<OntoComment> hotcommentlist=(ArrayList<OntoComment>)query.list();

		return hotcommentlist;		
	}
    public OntoBuild getOntoBuild(Long ontobuildid)
    {
    	return ontobuilddao.findUniqueBy("id", ontobuildid);
    }
    public OntoBuild getOntoBuild(String ontobuildname)
    {
    	return ontobuilddao.findUniqueBy("ontoname", ontobuildname);
    }
	public OntoBuild addOntoBuild(OntoBuild ontobuild) {	
		
		ontobuilddao.save(ontobuild);
		
		ontobuilddao.flush();
		return ontobuild;	
	}
    
    
	public boolean isRated(Long userid, Long ontocommentID) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("user", userid);
		params.put("ontocomment",ontocommentID);
		
		String hql = "from OntoCommentRating o where o.rater.id= :user and o.ontocomment.id= :ontocomment ";
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
	public OntoComment getComment(Long commentid) {
		OntoComment result = commentdao.findUniqueBy("id", commentid);
		return result;
	}
	public OntoComment getTailComment(OntoComment comment) {
		OntoComment result= new OntoComment();
		if(null!=comment.getCommented())
		{
			result=getTailComment(comment.getCommented());
		}
		else
		 result = comment;
		return result;
	}
	public String rate(Long userid, Long ontocommentID, Float score) {
		OntoCommentRating rating = new OntoCommentRating();
		SystemUser user = userdao.findUniqueBy("id", userid);
		OntoComment comment = commentdao.findUniqueBy("id", ontocommentID);
		Integer ratecount =comment.getAgainstVoteCount();
		ratecount++;
		comment.setAgainstVoteCount(ratecount);
		commentdao.save(comment);
		commentdao.flush();
		rating.setOntocomment(comment);
		rating.setRater(user);
		rating.setRatingTime(new Date());
		rating.setScore(score);
		ratingdao.save(rating);
		ratingdao.flush();
		
		return "1";
	}



	public String updateComment(OntoComment comment) {
		commentdao.save(comment);
		commentdao.flush();
		return "1";
	}
	
	//回答模块
	public List<OntoComment> getMyAnswers(SystemUser user) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("userid", user.getId());
		String hql = "from OntoComment o where o.commenter.id= :userid";
		Query query=commentdao.createQuery(hql, params);		
		List<OntoComment> myAnswers=(ArrayList<OntoComment>)query.list();

		return myAnswers;		
	}



	



}

