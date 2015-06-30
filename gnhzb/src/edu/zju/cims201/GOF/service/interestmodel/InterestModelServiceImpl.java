package edu.zju.cims201.GOF.service.interestmodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.common.CommonDao;
import edu.zju.cims201.GOF.dao.interestmodel.InterestModelDao;
import edu.zju.cims201.GOF.dao.knowledge.CommentDao;
import edu.zju.cims201.GOF.dao.knowledge.MetaKnowledgeDao;
import edu.zju.cims201.GOF.dao.tree.TreeNodeDao;
import edu.zju.cims201.GOF.dao.user.ExpertDao;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.CategoryTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Comment;
import edu.zju.cims201.GOF.hibernate.pojo.DomainTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.Expert;
import edu.zju.cims201.GOF.hibernate.pojo.InterestModel;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TreeNode;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.TreeNodeDTO;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.message.MessageService;
import edu.zju.cims201.GOF.service.tree.TreeService;
import edu.zju.cims201.GOF.util.Constants;

@Service
@Transactional
public class InterestModelServiceImpl implements InterestModelService {
	
	@Resource(name="interestModelDao")
	private InterestModelDao interestModeldao;
	@Resource(name="expertDao")
	private ExpertDao expertDao;
	@Resource(name="treeNodeDao")
	private TreeNodeDao treeNodeDao;
	@Resource(name = "metaKnowledgeDao")
	private MetaKnowledgeDao kdao;
	@Resource(name="commonDao")
	CommonDao dao;
	
	@Resource(name="knowledgeServiceImpl")
	private KnowledgeService knowledgeservice;
	@Resource(name="treeServiceImpl")
	private TreeService treeservice;
	@Resource(name="messageServiceImpl")
	private MessageService messageservice;

	
	public String addInterestModelItem(SystemUser user, String itemname,
			String itemtype,int isparent) {
		InterestModel interestmodel = new InterestModel();
		interestmodel.setUser(user);
		interestmodel.setInterestItem(itemname);
		interestmodel.setInterestItemType(itemtype);
		interestmodel.setCreatetime(new Date());
		interestmodel.setUnRead(0);
		interestmodel.setIsParent(isparent);
		interestModeldao.save(interestmodel);
		interestModeldao.flush();
		return null;
	}	
	
	private String generateSubTreeNodeIm(SystemUser user, TreeNode treenode,
			String itemtype,int isparent){
		
		addInterestModelItem(user,treenode.getId().toString(),itemtype,isparent);
		if(treenode.getSubNodes().size()>0) {
			for(TreeNode subtnd:treenode.getSubNodes()) {
				generateSubTreeNodeIm(user,subtnd,itemtype,isparent);
			}
				
		}	
		return null;
	}
	
	public String addInterestModelItems(SystemUser user, List<String> itemnames,
			String itemtype) {	
		for(String itemname: itemnames) {
			addInterestModelItem(user,itemname,itemtype,0);
			if(itemtype=="domain"||itemtype=="category") {
				long treeid = Long.parseLong(itemname);
				TreeNode treenode = treeservice.getTreeNode(treeid);
				if(treenode.getSubNodes().size()>0) {
					for(TreeNode tnd:treenode.getSubNodes()) {
						generateSubTreeNodeIm(user,tnd,itemtype,(int)treeid);				
					}					
				}
			}			
		}
		return null;	
	}

	public String deleteInterestModelItem(Long interestModelId) {
		interestModeldao.delete(interestModelId);
		return null;
	}
	
	private String deleteInterestModelItem(InterestModel interestModel) {
		interestModeldao.delete(interestModel);
		interestModeldao.flush();
		return null;
	}

	public List<MetaKnowledge> getInterestModelKnowledge(Long interestModelId) {
		InterestModel interestMdoel = interestModeldao.get(interestModelId);
		List<MetaKnowledge> knowledges= (List<MetaKnowledge>) interestMdoel.getKnowledges();
		return knowledges;	
	}
	
	//江丁丁 添加 兴趣十知识 2013-6-24
	public PageDTO getInterestModelKnowledge(SystemUser user) {
		
		List<InterestModel> list = this.listInterestModelItems(user);
		PageDTO resultlist = new PageDTO();
		List<KnowledgeDTO> klist = new ArrayList<KnowledgeDTO>();
		for(InterestModel im : list){
//			 Set<MetaKnowledge> knowledge= im.getKnowledges();
			HashMap propertyValues =new HashMap();
			List searchlist = new ArrayList();
			
			HashMap search =new HashMap();
			if(im.getInterestItemType().equals("keyword")){
				search.put("name","keywordid");
			}else if(im.getInterestItemType().equals("uploader")){
				search.put("name","uploaderid");
			}else if(im.getInterestItemType().equals("domain")){
				search.put("name","domainnodeid");
			}else if(im.getInterestItemType().equals("category")){
				search.put("name","categoriesid");
			}
			search.put("value",im.getInterestItem());
			search.put("and_or","and");
			searchlist.add(search);
			
			propertyValues.put("searchlist", searchlist);
			PageDTO pdto = knowledgeservice.searchKnowledge(propertyValues);
			List<KnowledgeDTO> knowledges = (List<KnowledgeDTO>)pdto.getData();
			
			 for (KnowledgeDTO k : knowledges){
				 klist.add(k);
			 }
		}
		//去除重复知识
		for (int i = 0; i < klist.size() - 1; i++) {
		    for (int j = klist.size() - 1; j > i; j--) {
		    	KnowledgeDTO kdto1 =  klist.get(i);
				KnowledgeDTO kdto2 = klist.get(j);
			     if (kdto2.getId() == kdto1.getId()) {
			      klist.remove(j);
			     }
		    }
	   }
		
		//重新排序
		Collections.sort(klist);
		
		List<KnowledgeDTO> ten_klist = new ArrayList<KnowledgeDTO>();
		//传十条兴趣知识（可根据页面情况修改）
		if(klist.size()>10){
			for(int i=0;i<10;i++){
				ten_klist.add(klist.get(i));
			}
			resultlist.setData(ten_klist);
		}else{
			resultlist.setData(klist);
		}

		return resultlist;	
	}
	

	public List<InterestModel> listInterestModelItems(SystemUser user) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("userid", user.getId());
		String hql = "from InterestModel o where o.user.id= :userid";
		Query query=interestModeldao.createQuery(hql, params);
		List<InterestModel> itemslist=(List<InterestModel>)query.list();
		return  itemslist;		
	}

	public String updateInterestModelItems(InterestModel interestModel) {
		interestModeldao.save(interestModel);
		return null;
	}

	public List<SystemUser> getsubscribers(String itemname,String itemtype) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("itemname", itemname);
		params.put("itemtype", itemtype);
		String hql = "select o.user from InterestModel o where o.interestItem= :itemname and o.interestItemType= :itemtype ";
		Query query = interestModeldao.createQuery(hql, params);
		List<SystemUser> subscribers = (ArrayList<SystemUser>)query.list();
		return subscribers;
	}
	
	
	public boolean isTreeInterestModelExist(SystemUser user,String interestItem,String interestItemType,int isParent) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("user", user);
		params.put("interestItem", interestItem);
		params.put("interestItemType", interestItemType);
		params.put("isParent", isParent);
		String hql = "from InterestModel o where o.user= :user and o.interestItem= :interestItem and " +
				"o.interestItemType= :interestItemType and o.isParent= :isParent";
		Query query = interestModeldao.createQuery(hql, params);
		List<InterestModel> ims = (ArrayList<InterestModel>)query.list();
		if(ims.size()>0){
			return true;
		}else{
			return false;
		}
	}
	public boolean isInterestModelExist(SystemUser user,String interestItem,String interestItemType) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("user", user);
		params.put("interestItem", interestItem);
		params.put("interestItemType", interestItemType);		
		String hql = "from InterestModel o where o.user= :user and o.interestItem= :interestItem and " +
		"o.interestItemType= :interestItemType";
		Query query = interestModeldao.createQuery(hql, params);
		List<InterestModel> ims = (ArrayList<InterestModel>)query.list();
		if(ims.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public InterestModel getInterestModel(Long id) {
		
		return interestModeldao.get(id);
	}
	
	

	public List<InterestModel> getInterestModels(SystemUser user, String interestItem,
			String interestItemType) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("user", user);
		params.put("interestItem", interestItem);
		params.put("interestItemType", interestItemType);
		String hql = "from InterestModel o where o.user= :user and o.interestItem= :interestItem and o.interestItemType= :interestItemType";
		Query query = interestModeldao.createQuery(hql, params);
		List<InterestModel> ims = (ArrayList<InterestModel>)query.list();
		
		return ims;
	}
	
	public InterestModel getCommonInterestModel(SystemUser user,String interestItem,
			String interestItemType,int isParent) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("user", user);
		params.put("interestItem", interestItem);
		params.put("interestItemType", interestItemType);
		params.put("isParent", isParent);
		String hql = "from InterestModel o where o.user= :user and o.interestItem= :interestItem and o.interestItemType= :interestItemType " +
				"and o.isParent=:isParent";
		Query query = interestModeldao.createQuery(hql, params);
		List<InterestModel> ims = (ArrayList<InterestModel>)query.list();
		InterestModel interestmodel = null;
		for(InterestModel im:ims) {
			interestmodel = im;
		}
		return interestmodel;
	}
	

	
	public boolean compareDate(Knowledge knowledge, InterestModel interestmodel) {
		Date knowledgedate = knowledge.getUploadtime();
		Date imdate = interestmodel.getCreatetime();

		java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Calendar c1=java.util.Calendar.getInstance();
		java.util.Calendar c2=java.util.Calendar.getInstance();
		c1.setTime(knowledgedate);
		c2.setTime(imdate);
		int result=c1.compareTo(c2);
		if(result>=0)
			return true;
		else 
			return false;
	}
	
	

	public String deleteAllSubNodeIm(long interestModelId) {	
	    deleteInterestModelItem(interestModelId);
	    
	    int treenodeid = Integer.parseInt(getInterestModel(interestModelId).getInterestItem());	    
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("isParent", treenodeid);		
		String hql = "from InterestModel o where o.isParent=:isParent";
		Query query = interestModeldao.createQuery(hql, params);
		List<InterestModel> ims = (ArrayList<InterestModel>)query.list();
		for(InterestModel im:ims) {
			deleteInterestModelItem(im);
		}
	
		return null;
	}

	//专家信息维护
	public String addExpertItems(SystemUser user,Set<TreeNode> treeNodes,Set<MetaKnowledge> unanswers) {		
		Expert expert = new Expert();
		expert.setUser(user);
		expert.setTreeNodes(treeNodes);
		expert.setUnanswers(unanswers);
		expertDao.save(expert);
		expertDao.flush();		
		return null;
	}

	public String deleteExpert(Long expertId) {
		expertDao.delete(expertId);
		expertDao.flush();
		return null;
	}
	
	public List<Expert> getAllExpert() {
		List<Expert> expertlist = expertDao.getAll();
		return expertlist;
	}
	
	public Set<Expert> getTreeExpert(Long treenodeid) {
		Set<Expert> treeExpertList = treeNodeDao.get(treenodeid).getExperts();
		return treeExpertList;
	}

	public Expert getExpert(Long expertid) {		
		return expertDao.get(expertid);
	}
	
	public Expert getOneExpert(String name){
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("name", name);
		String hql = "from Expert o where o.user.name=:name";
		Query query = expertDao.createQuery(hql, params);
		List<Expert> experts = query.list();
		Expert oneexpert = new Expert();
		for(Expert expert:experts) {
			oneexpert = expert;
		}
		
		return oneexpert;	
	}



	public String updateExpert(Expert expert) {
		expertDao.save(expert);
		expertDao.flush();
		return null;
	}



	public boolean isExpertExist(String name) {
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("name", name);		
		String hql = "from Expert o where o.user.name= :name";
		Query query = expertDao.createQuery(hql, params);
		List<Expert> expert = (ArrayList<Expert>)query.list();		
		if(expert.size()>0) 
		return true;		
		return false;

	}



	public List<Expert> searchExperts(String username) {
		List<Expert> expertlist = expertDao.getSession().createQuery(
				"from Expert o where o.user.name like '%"
						+ username
						+ "%'")
				.list();
		return expertlist;
		
	}

	public void saveMessageAndSubscribeInfo(Knowledge k){
		 MetaKnowledge knowledge;
		 if(k.getId()!=null){
			 knowledge = knowledgeservice.getMetaknowledge(k.getId());
		 }else{
			 knowledge = (MetaKnowledge)k;
		 }
		 SystemUser sender=null;
		 SystemUser receiver=null;
		 Date date=new Date();
		 sender=knowledge.getUploader();
		
		//如果知识类型是问题，则向专家发送求解消息----------------------------------------------暂时不要求
		if(knowledge.getKtype().getName().equals("Question"))
		 {
		    
		    
	        Iterator it=knowledge.getDomainnode().getExperts().iterator();
	    	while(it.hasNext())
		     {Message message=new Message();
		        message.setIsRead(false);	        
		        message.setIsAnswered(false);	        
		        message.setKnowledge(knowledge);
		        message.setSender(sender);
		        message.setMessageType("askForAnswer");
		        message.setSendTime(date);
		        message.setContent("向你发出了问题解答请求");
		        Expert expert=(Expert)it.next();
	            receiver=expert.getUser();
	           message.setReceiver(receiver);
		       dao.save(message);
		       messageservice.sendMessage(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息

		     }
		 }
		

		//不是‘问题’，则发送知识预定message
		else{
		//发送知识预定消息
	       List<SystemUser> subscribers=new ArrayList<SystemUser>();
		
				
		  
		       //根据关键词预定发送消息
		    	if(knowledge.getKeywords()!=null)
		    	{
		          Iterator it=knowledge.getKeywords().iterator();
		    	  while(it.hasNext())
			       {  Keyword keyword=(Keyword)it.next();		       
			        subscribers=getsubscribers(keyword.getId().toString(), "keyword");
			             if(subscribers!=null)
			                { Iterator it_=subscribers.iterator();
			  	    	       while(it_.hasNext())
					             { receiver=(SystemUser)it_.next();
					             
					              Message message=new Message();
								    message.setIsRead(false);
								    message.setIsAnswered(false);	
								    message.setKnowledge(knowledge);
								    message.setSender(sender);
								    message.setMessageType("subscribeknowledge");
								    message.setSendTime(date);
							        
					               message.setReceiver(receiver);
							       message.setContent("关键词【"+keyword.getKeywordName()+"】");
							       if(!messageservice.messageExit( receiver, message.getMessageType(), knowledge))
							       dao.save(message);
							       
							       messageservice.sendMessage(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息					      
							       
							       //未读的计数
							       if(isInterestModelExist(receiver, keyword.getId().toString(), "keyword")){						    	   
							    	   InterestModel im = getCommonInterestModel(receiver, keyword.getId().toString(), "keyword",0);
									   im.setUnRead(im.getUnRead()+1);
									   updateInterestModelItems(im);
							       }					      
					             }
			        	 
			                }
		         
			
			         }
			
		         }
		    	//根据作者预定发送消息
			    subscribers=getsubscribers(knowledge.getUploader().getId().toString(), "uploader");
			    //System.out.println("上传者id："+knowledge.getUploader().getId().toString());
	            if(subscribers!=null){
			       Iterator it_user=subscribers.iterator();
		    	   while(it_user.hasNext())
		    	    {
		    		   receiver=(SystemUser)it_user.next();
		    		   
		    		    Message message=new Message();
					    message.setIsRead(false);
					    message.setIsAnswered(false);	
					    message.setKnowledge(knowledge);
					    message.setSender(sender);
					    message.setMessageType("subscribeknowledge");
					    message.setSendTime(date);
		                message.setReceiver(receiver);
				        message.setContent("上传者【"+sender.getName()+"】");
				        
				        if(!messageservice.messageExit( receiver, message.getMessageType(), knowledge))
				        dao.save(message);
				       
				        messageservice.sendMessage(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息
				       //未读的计数
				       if(isInterestModelExist(receiver, knowledge.getUploader().getId().toString(), "uploader")){						    	   
				    	   InterestModel im = getCommonInterestModel(receiver, knowledge.getUploader().getId().toString(), "uploader",0);
						   im.setUnRead(im.getUnRead()+1);
						   updateInterestModelItems(im);
				       }

		    	    }
	            }
	            
		    	//根据关注的领域树节点发送消息
	            if(knowledge.getDomainnode()!=null){
	            	 subscribers=getsubscribers(knowledge.getDomainnode().getId().toString(), "domain");
	 			    //System.out.println("knowledge.getDomainnode().getId().toString()："+knowledge.getDomainnode().getId().toString());

	 			    if(subscribers!=null){
	 				       Iterator it_treeNode=subscribers.iterator();
	 			    	   while(it_treeNode.hasNext())
	 			    	    {
	 			    		   receiver=(SystemUser)it_treeNode.next();
	 			    		   
	 			    		   Message message=new Message();
	 						    message.setIsRead(false);
	 						    message.setIsAnswered(false);	
	 						    message.setKnowledge(knowledge);
	 						    message.setSender(sender);
	 						    message.setMessageType("subscribeknowledge");
	 						    message.setSendTime(date);
	 			               message.setReceiver(receiver);
	 					       message.setContent("领域【"+knowledge.getDomainnode().getNodeName()+"】");
	 					       
	 					       if(!messageservice.messageExit( receiver, message.getMessageType(), knowledge))
	 					       dao.save(message);
	 					       
	 					       messageservice.sendMessage(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息
	 					       //未读的计数
	 					       if(isInterestModelExist(receiver, knowledge.getDomainnode().getId().toString(), "domain")){					    	   
	 					    	   List<InterestModel> ims = getInterestModels(receiver, knowledge.getDomainnode().getId().toString(), "domain");
	 					    	   for(InterestModel im:ims){
	 								   im.setUnRead(im.getUnRead()+1);
	 								   updateInterestModelItems(im);
	 					    	   }
	 					    	 
	 					       }

	 			    	    }
	 		            }
	            	
	            }
			   		    	
			  //根据关注的多维分类树节点发送消息
	            if(knowledge.getCategories()!=null){
	            	 Iterator it=knowledge.getCategories().iterator();
	 			    while(it.hasNext())
	 			    {CategoryTreeNode ctNode=(CategoryTreeNode)it.next();
	 			    subscribers=getsubscribers(ctNode.getId().toString(), "category");
	 			   // System.out.println("ctNode.getId().toString()"+ctNode.getId().toString());

	 			    if(subscribers!=null){
	 				       Iterator it_treeNode=subscribers.iterator();
	 			    	   while(it_treeNode.hasNext())
	 			    	    {
	 			    		   receiver=(SystemUser)it_treeNode.next();		    		  
	 			    		   
	 			    		   Message message=new Message();
	 						    message.setIsRead(false);
	 						    message.setIsAnswered(false);	
	 						    message.setKnowledge(knowledge);
	 						    message.setSender(sender);
	 						    message.setMessageType("subscribeknowledge");
	 						    message.setSendTime(date);
	 			               message.setReceiver(receiver);
	 					       message.setContent("多维分类【"+ctNode.getNodeName()+"】");
	 					       
	 					       if(!messageservice.messageExit( receiver, message.getMessageType(), knowledge))
	 					       dao.save(message);
	 					       
	 					       messageservice.sendMessage(sender,receiver,message.getContent(),knowledge.getTitlename());//发送消息
	 					       //未读的计数
	 					       if(isInterestModelExist(receiver, ctNode.getId().toString(), "category")){	
	 					    	   
	 					    	   List<InterestModel> ims = getInterestModels(receiver, ctNode.getId().toString(), "category");
	 					    	   for(InterestModel im:ims){
	 								   im.setUnRead(im.getUnRead()+1);
	 								   updateInterestModelItems(im);
	 					    	   }

	 					       }

	 			    	    }
	 			    }	            	
	            }			   
			    }			   
			    }
	}

	

}
