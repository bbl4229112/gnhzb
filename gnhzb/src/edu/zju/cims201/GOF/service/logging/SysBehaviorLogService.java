package edu.zju.cims201.GOF.service.logging;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlowContent;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;

public interface SysBehaviorLogService {
	
	public String logLogin(String userEmail);
	public Long countBehavior(Long userid,Long behaviorId);
	
	/**
	 * 保存某条行为记录
	 * @param {@link sysBehaviorLog} 
	 * @return 
	 * @author ljk
	 */
	public String save(SysBehaviorLog sysBehaviorLog);
	
	 /**
	 * 通过知识id删除记录
	 * @param sysBehaviorLogId 
	 * @return 
	 * @author ljk
	 */
    public String delete(Long sysBehaviorLogId);
    
    /**
	 * 通过id获得记录的实例
	 * @param sysBehaviorLogId
	 * @return 
	 * @author ljk
	 */
    public SysBehaviorLog getSysBehaviorLog(Long sysBehaviorLogId);
    /**
	 * 获得用户的所有记录
	 * @param sysBehaviorLogId
	 * @return sysBehaviorLog list
	 * @author ljk
	 */
    public List<SysBehaviorLog> listSysBehaviorLogs(Long sysBehaviorLogId);
    
    
    /**
     * 对借阅的知识访问次数进行叠加
     * @param content
     * @return
     * @author jixiang
     */
    public String increaseBorrowedTimes(BorrowFlowContent content);
    
    /**
     * 得到某篇知识以及浏览的次数
     * @param content
     * @return
     * @author jixiang
     */
    public Integer getBorrowedTimes(BorrowFlowContent content);
    
   
}
