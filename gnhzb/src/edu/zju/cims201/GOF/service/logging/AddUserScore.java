package edu.zju.cims201.GOF.service.logging;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.service.systemUser.UserService;

@Service("addUserScore")
@Transactional (readOnly = true)
public class AddUserScore {
	@Resource(name = "sysBehaviorLogServiceImpl")
	private SysBehaviorLogService sysBehaviorLogService;
	@Resource(name = "sysBehaviorListServiceImpl")
	private SysBehaviorListService sysBehaviorListService;
	@Resource(name="userServiceImpl")
	UserService userService;
	
	
	public void addUserScore(SystemUser user,Long sysBehaviorListId){
		
		
		int behaviorScore = (sysBehaviorListService.getSysBehaviorList(sysBehaviorListId)).getBehaviorpoint();
		boolean isContributionBehavior  = sysBehaviorListService.getSysBehaviorList(sysBehaviorListId).getContributionBehavior();
		
		// 将行为积分加入到用户的总积分
		
		
		
		setTotalScore(user,behaviorScore);		
		
		// 将行为积分加入到用户的贡献度积分
		
		if(isContributionBehavior){
			setContributionScore(user,behaviorScore);
		}
		//保存用户 
		userService.updateUser(user);
	
	}

	private void setContributionScore(SystemUser user, int behaviorScore) {
		
		if (user.getContributionscore() != null) {
			user.setContributionscore(user.getContributionscore()
					+behaviorScore);
		} else {
			user.setContributionscore(behaviorScore);
		}
		
		if (user.getWeekcscore() != null) {
			user.setWeekcscore(user.getWeekcscore()
					+behaviorScore);
		} else {
			user.setWeekcscore(behaviorScore);
		}
		
		if (user.getLastweekcscore() != null) {
			user.setLastweekcscore(user.getLastweekcscore()
					+behaviorScore);
		} else {
			user.setLastweekcscore(behaviorScore);
		}
		
		
		if (user.getMonthcscore() != null) {
			user.setMonthcscore(user.getMonthcscore()
					+behaviorScore);
		} else {
			user.setMonthcscore(behaviorScore);
		}
		
		
		if (user.getYearcscore() != null) {
			user.setYearcscore(user.getYearcscore()
					+behaviorScore);
		} else {
			user.setYearcscore(behaviorScore);
		}
		

		
	}

	private void setTotalScore(SystemUser user, int behaviorScore) {
		
		if (user.getTotoalscore()!= null) {
			
			user.setTotoalscore(user.getTotoalscore()
					+ behaviorScore);
			
		} else {
			user.setTotoalscore(behaviorScore);
		}
		
		
		if (user.getWeekpscore() != null) {
			user.setWeekpscore(user.getWeekpscore()
					+behaviorScore);
		} else {
			user.setWeekpscore(behaviorScore);
		}
		
		if (user.getLastweekpscore() != null) {
			user.setLastweekpscore(user.getLastweekpscore()
					+behaviorScore);
		} else {
			user.setLastweekpscore(behaviorScore);
		}
		
		if (user.getMonthpscore() != null) {
			user.setMonthpscore(user.getMonthpscore()
					+behaviorScore);
		} else {
			user.setMonthpscore(behaviorScore);
		}
		
		
		if (user.getYearpscore() != null) {
			user.setYearpscore(user.getYearpscore()
					+behaviorScore);
		} else {
			user.setYearpscore(behaviorScore);
		}
		
		
		
		
	}
}
