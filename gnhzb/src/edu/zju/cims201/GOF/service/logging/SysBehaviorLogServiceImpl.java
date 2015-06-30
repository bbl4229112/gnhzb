package edu.zju.cims201.GOF.service.logging;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.logging.SysBehaviorLogDao;
import edu.zju.cims201.GOF.dao.logging.SysBorrowLogDao;
import edu.zju.cims201.GOF.hibernate.pojo.BorrowFlowContent;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorLog;
import edu.zju.cims201.GOF.hibernate.pojo.SysBorrowLog;
import edu.zju.cims201.GOF.service.systemUser.UserService;

@Service
@Transactional (readOnly = true)
public class SysBehaviorLogServiceImpl implements SysBehaviorLogService {

	@Resource(name="sysBehaviorLogDao")
	SysBehaviorLogDao sysBehaviorLogDao;
	@Resource(name="sysBehaviorListServiceImpl")
	SysBehaviorListService sysBehaviorListService;
	@Resource(name="sysBehaviorLogServiceImpl")
	SysBehaviorLogService sysBehaviorLogService;
	@Resource(name="addUserScore")
	AddUserScore addUserScore;
	@Resource(name="userServiceImpl")
	UserService userService;
	@Resource(name="sysBorrowLogDao")
	private SysBorrowLogDao sysBorrowLogDao;
	
	public String delete(Long sysBehaviorLogId) {
		sysBehaviorLogDao.delete(sysBehaviorLogId);
		sysBehaviorLogDao.flush();
		return "1";
	}

	
	public SysBehaviorLog getSysBehaviorLog(Long sysBehaviorLogId) {
		SysBehaviorLog sysBehaviorLog=sysBehaviorLogDao.get(sysBehaviorLogId);
		return sysBehaviorLog;
	}

	
	public List<SysBehaviorLog> listSysBehaviorLogs(Long sysBehaviorLogId) {
		
		String queryString="from SysBehaviorLog s where s.id=? order by s.actionTime desc";
		List<SysBehaviorLog> list=sysBehaviorLogDao.createQuery(queryString, sysBehaviorLogId).list();
		return list;
	}

	
	public String save(SysBehaviorLog sysBehaviorLog) {
		sysBehaviorLogDao.save(sysBehaviorLog);
		sysBehaviorLogDao.flush();
		return "1";
	}



	public String logLogin(String userEmail) {
		// TODO Auto-generated method stub
		   SysBehaviorLog bLog = new SysBehaviorLog();
			
			bLog.setActionTime(new Date());
			
			bLog.setSysBehaviorList(sysBehaviorListService.getSysBehaviorList(1L));
			bLog.setUser(userService.getUser(userEmail));
			bLog.setObjectType("login");
			sysBehaviorLogService.save(bLog);

			//累加用户积分
			addUserScore.addUserScore(userService.getUser(userEmail),1L);
		return "1";
	}


	
	public Long countBehavior(Long userid, Long behaviorId) {
		// TODO Auto-generated method stub
		String queryString="select count(*) from SysBehaviorLog s where s.user.id=? and s.sysBehaviorList.id=? ";
		Long countBehavior=(Long)sysBehaviorLogDao.createQuery(queryString,userid,behaviorId).uniqueResult();
		
		return countBehavior;
	}


	public String increaseBorrowedTimes(BorrowFlowContent content) {
		// TODO 自动生成方法存根
		SysBorrowLog sysBorrowLog=sysBorrowLogDao.findUniqueBy("content", content);
		if(sysBorrowLog==null){
			sysBorrowLog=new SysBorrowLog();
			sysBorrowLog.setTimes(0);
			sysBorrowLog.setContent(content);
		}
			
		Integer oldTimes=sysBorrowLog.getTimes();
		sysBorrowLog.setTimes(oldTimes+1);
		sysBorrowLogDao.save(sysBorrowLog);
		return null;
	}


	public Integer getBorrowedTimes(BorrowFlowContent content) {
		// TODO 自动生成方法存根
		SysBorrowLog sysBorrowLog=sysBorrowLogDao.findUniqueBy("content", content);
		if(sysBorrowLog==null){
			SysBorrowLog log=new SysBorrowLog();
			log.setContent(content);
			log.setTimes(0);
			sysBorrowLogDao.save(log);
			sysBorrowLogDao.flush();
			return log.getTimes();
		}
			
		
		return sysBorrowLog.getTimes();
	}

}
