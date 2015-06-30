package edu.zju.cims201.GOF.service.knowledge;

import java.util.List;

import javax.annotation.Resource;

import edu.zju.cims201.GOF.dao.knowledge.AuthorDao;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.Message;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;

public class AuthorServiceImpl implements AuthorService {


	@Resource(name = "authorDao")
	private AuthorDao authorDao;
	public void deleteAuthorByKID(MetaKnowledge mt) {
		List<Author> mks = this.authorDao.findBy("knowledge", mt);
		for(Author a : mks){
			this.authorDao.delete(a);			
		}
	}

}
