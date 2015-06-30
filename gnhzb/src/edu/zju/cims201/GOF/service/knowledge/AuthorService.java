package edu.zju.cims201.GOF.service.knowledge;

import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;

public interface AuthorService {

	/**
	 * @author panlei
	 * 根据知识删除Author
	 * 
	 * */
	public void deleteAuthorByKID(MetaKnowledge mt);
}
