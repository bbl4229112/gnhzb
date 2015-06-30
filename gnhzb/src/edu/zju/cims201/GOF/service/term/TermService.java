package edu.zju.cims201.GOF.service.term;

import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.TermInterpretation;

/**
 * 提供关于术语的相关服务接口，由具体的实现类来实现相关的方法
 * 
 * @author cwd
 */
@Transactional
public interface TermService {
	/**
	 * 列出术语解释
	 * @param term 术语
	 * @param flag 用于判断解释的类型 0 为所有解释，1一般用户解释，2正式解释！
	 * @return 返回术语解释
	 * @author cwd
	 */
	@Transactional(readOnly = true)
	public TermInterpretation listTermInterpretation(TermService term,int flag);
	/**
	 * 添加术语解释
	 * @param user 用户
	 * @param interpretation 解释
	 * @param flag 用于判断解释的类型 0 为所有解释，1一般用户解释，2正式解释！
	 * @return 通过返回的String值判断是否操作成功
	 * @author cwd
	 */
	public String addTermInterpretation(SystemUser user,String interpretation,int flag);
	/**
	  * 删除术语解释
	 * @param user 用户
	 * @param interpretation 解释
	 * @param flag 用于判断解释的类型 0 为所有解释，1一般用户解释，2正式解释！
	 * @return 通过返回的String值判断是否操作成功
	 * @SystemUser cwd
	 */
	public String deleteTermInterpretation(SystemUser user,String interpretation,int flag);
	
	

}



