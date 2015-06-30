package edu.zju.cims201.GOF.service.expert;

import java.sql.Clob;
import java.util.HashMap;
import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojo.Expert;
import edu.zju.cims201.GOF.rs.dto.PageDTO;

public interface ExpertService {
	
	/**
	 * 通过关键词查找专家文章
	 */
	public PageDTO searchArticle(String key);
	
	/**
	 * 通过关键词查找专家文章
	 */
	public PageDTO searchExpert(String key);
	
	public List<Expert> searchExperts(String hql, Object[] objects);

}
