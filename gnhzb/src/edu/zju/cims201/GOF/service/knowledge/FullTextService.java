package edu.zju.cims201.GOF.service.knowledge;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;



import org.apache.lucene.index.CorruptIndexException;

import edu.zju.cims201.GOF.hibernate.pojo.HotWord;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;

import edu.zju.cims201.GOF.rs.dto.PageDTO;

/**
 * 提供关于知识全文索引和检索的服务接口，由具体的实现类来实现接口定义的方法
 * 
 * @author hebi
 */

public interface FullTextService 

{
	
	
	
	/**
	 * 对所有入库知识进行全文的索引
	
	 * @author hebi
	 */
	public  void    indexAllKnowledge();
	/**
	 * 对一列知识进行全文的索引
	 * @param {@link MetaKnowledge} knowledgelist 知识的list
	 * @author hebi
	 */
	public  void    indexListKnowledge(List<MetaKnowledge> knowledgelist );
	/**
	 * 对单一知识进行全文的索引
	 * @param mg {@link MetaKnowledge}  知识
	 * @author hebi
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	 public   void   indexKnowledge(MetaKnowledge mg ) throws CorruptIndexException, IOException ;  
	 
	 

		
		
//		//对整个数据库和源文件创建索引，int为成功标识
//		public int createIndex();
		
		//对新入库的知识进行索引，int为成功标识
//		public int updateIndex(MetaKnowledge newKnowledge);
//		
//		//列出热词,为热词数量限制从配置文件中读取

		
		public List<String>  listHotWords();
		
		//根据关键词匹配热词
		
		
		public List<String>  searchHotWords(String keyword);
		
		
		//根据关键词全文匹配知识，分页未考虑，可能要加参数
		
		
		public PageDTO  searchKnowledge(String keyword,PageDTO page,String ktype );
		
		//添加热词，int为成功标识
		public int addHotWord(HotWord hotword);
		
		
		
		
		/**
		 * 获取知识的推荐相关知识
		 * @param Knowledge 与之相关的知识实例
		 * @return 被推荐知识的列表set类型
		 * @author hebi
		 */
		
		
		public PageDTO  listRecommendedKnowledges(MetaKnowledge knowledge, PageDTO page,String inktype);
		

		
		

	
}
