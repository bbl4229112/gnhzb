package edu.zju.cims201.GOF.service.message;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;

/**
 * 提供关于消息的服务接口，由具体的实现类来实现接口定义的方法
 * 
 * @author ljk
 */
@Transactional
public interface MessageService {
	/**
	 * 保存某条消息
	 * @param {@link Message} 需要存贮的消息
	 * @return 
	 * @author ljk
	 */
	public String save(Message message);
	
	 /**
	 * 通过知识id删除消息
	 * @param messageId 删除的消息的id
	 * @return 
	 * @author ljk
	 */
    public String delete(Long messageId);
    /**
	 * 通过知识id获得未处理消息的实例
	 * @param knowledgeId 需要获得的消息的id
	 * @return 具体消息的实例
	 * @author pl
	 */
    public List<Message> listUnAnsweredKnowledgeMessage(Long knowledgeId);
    
    /**
	 * 通过用户id获得未处理消息的实例
	 * @param userId 需要获得的消息的id
	 * @return 具体消息的实例
	 * @author pl
	 * 
	 */
    public List<Message> listUnAnsweredKnowledgeMessageByUserId(Long userId);

    /**
	 * 通过知识id获得消息的实例
	 * @param messageId 需要获得的消息的id
	 * @return 具体消息的实例
	 * @author ljk
	 */
    public Message getMessage(Long messageId);
    /**
	 * 获得用户的所有消息【除公告】
	 * @param receiverId 用户id
	 * @return 消息list
	 * @author ljk
	 */
    public List<Message> listMessages(Long receiverId);
    
    /**
	 * 通过用户id获得用户的所有收到未读消息列表
	 * @param 
	 * @return 消息list
	 * @author ljk
	 */
    public List<Message> listUnreadedMessages(Long receiverId);
    
    
    /**
	 * 通过用户id获得用户的所有收到消息列表
	 * @param 
	 * @return 消息list
	 * @author ljk
	 */
    public List<Message> listReceivedMessages(Long receiverId);
    
    /**
	 * 通过用户id和消息类型获得用户的所有收到消息列表
	 * @param 
	 * @return 消息list
	 * @author ljk
	 */
    public List<Message> listReceivedMessages(Long receiverId,String messageType);
    
    /**
	 * 通过用户id获得用户的所有发送消息列表
	 * @param 
	 * @return 消息list
	 * @author ljk
	 */
    public List<Message> listSendedMessages(Long senderId);
    
    /**
	 * 通过用户id和消息类型获得用户的所有发送消息列表
	 * @param 
	 * @return 消息list
	 * @author ljk
	 */
    public List<Message> listSendedMessages(Long senderId,String messageType);

    public String[] getMessagrProperty(Long messageId);

    
    public boolean messageExit(SystemUser receiver,String messageType,MetaKnowledge knowledge);
    

    /**
	 * 通过知识id和获得有关的所有发送消息列表
	 * @param 
	 * @return 消息list
	 * @author ljk
	 */
    public List<Message> listKnowledgeMessages(Long receiverId);
    /**
     * 发送消息
     * @param 
     * @return 
     * @author 
     */
    public void sendMessage(SystemUser sender,SystemUser receiver,String msg,String object);

	/**
	 * 根据知识id和用户id获取消息
	 * @author panlei
	 * @param id
	 * @param knowledgeId
	 * @return List
	 */
	public List<Message> listUnAnsweredKnowledgeMessageByUserIdAndKnowledgeId(
			Long id, Long knowledgeId);

	/**
	 * 根据知识id删除所有与之相关的消息
	 * @author panlei
	 * @param id
	 * 
	 * */
	public void deleteMessage(MetaKnowledge m);
    
}
