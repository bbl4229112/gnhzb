package edu.zju.cims201.GOF.service.flex;

import java.util.List;
import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.user.SystemUserDao;
import edu.zju.cims201.GOF.hibernate.pojo.KeepTreeNode;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.knowledge.keep.KeepService;
import edu.zju.cims201.GOF.service.statistic.StatisticService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
@Service
@Transactional (readOnly = true)
public class FlexServiceImpl implements FlexService {

	@Resource(name="statisticServiceImpl")
	StatisticService statisticService;
	@Resource(name="userServiceImpl")
	private UserService userservice;
	@Resource(name="keepServiceImpl")
	private KeepService keepservice;
	
	private SystemUserDao userDAO;
	
	
	
	public List<KeepTreeNode> listBookmark() {
		
		SystemUser currentUser = userservice.getUser();

		List<KeepTreeNode> list_keep = keepservice.listBookmark(currentUser);
		return list_keep;
	}
	public List<SystemUser> list(){
	
		List<SystemUser> list = userservice.listUsers();
		return list;
	}

	
	
	
	
	
	
	
	
	@Autowired
	public void setUserDAO(SystemUserDao userDAO) {
		this.userDAO = userDAO;
	}

	public SystemUserDao getUserDAO() {
		return userDAO;
	}
}
