package edu.zju.cims201.GOF.service.knowledge;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.springside.modules.orm.Page;
import edu.zju.cims201.GOF.hibernate.pojo.Attachment;
import edu.zju.cims201.GOF.hibernate.pojo.Author;
import edu.zju.cims201.GOF.hibernate.pojo.Keyword;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SysBehaviorList;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;
import edu.zju.cims201.GOF.hibernate.pojo.Version;
//import edu.zju.cims201.GOF.entity.account.Role;
//import edu.zju.cims201.GOF.entity.account.SUser;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.rs.dto.PageDTO;

/**
 * 提供关于知识的服务接口，由具体的实现类来实现接口定义的方法
 * 
 * @author hebi
 */

public interface KnowledgeService {
	
	
	public Long getTotalNum();
	public Long getTotalKnowledgeNum_user(Long userId);
	public Double getUserAverageScore(Long userId);
	public Long getTotalKnowledgeNum_ktype(Long ktypeId);
	public Long getTotalKnowledgeNum_ktype_user(Long ktypeId,Long userId);
	public Long getTotalKnowledgeNum_timeslot(Object[] o);
	public List<MetaKnowledge> sortKnowledge(String orderby,String pagerecord);
	/**
	 * 通过解析知识文档获取知识内容
	 * @param KnowledgeSource 知识文档的存储路径
	 * @return 返回字符串形式的知识属性和值
	 * @author hebi
	 */
	public String parseKnowledgeSource(String KnowledgeSource);
	
	/**
	 * 判断知识是否存在
	 * @param knowledgeTitle 知识的名称
	 * @return boolean
	 * @author hebi
	 */
	public boolean isKnowledgeExist(String knowledgeTitle);
	
	
	/**
	 * 通过关键词搜索知识
	 * @param searchword 知识搜索的关键词
	 * @return 返回知识的set
	 * @author hebi
	 */	

	
	/**
	 * 保存某篇知识
	 * @param {@link Knowledge} 需要存贮的知识实例
	 * @return 
	 * @author hebi
	 */
	public String save(Knowledge k,Long fileid);
	/**
	 * 保存某篇知识
	 * @param  {@link Ktype} 知识的类型
	 * @param PropertyValues 知识的属性值
	 * @return 存储知识的id
	 * @author hebi
	 */
	public MetaKnowledge save(Ktype ktype,List<Property>PropertyValues);
	/**
	 * 保存某篇知识
	 * @param  {@link Ktype} 知识的类型
	 * @param PropertyValues 知识的属性值
	 * @return 存储知识的id
	 * @author hebi
	 */
	public MetaKnowledge onebyonesave(Ktype ktype,List<Property>PropertyValues);
	/**
	 * 更新某篇修改后的知识
	 * @param  
	 * @param 
	 * @return 存储知识的id
	 * @author hebi
	 */
	public MetaKnowledge updateFixedKnowledge(Knowledge kg, List<Property> PropertyValues);
	
//	/**
//	 * 快速保存某篇知识
//	 * @param  {@link Ktype} 知识的类型
//	 * @param PropertyValues 知识的属性值
//	 * @return 存储知识的id
//	 * @author hebi
//	 */
//	public MetaKnowledge simpsave(Ktype ktype,List<Property>PropertyValues);
	
	
	/**
	 * 获取知识的flash文件
	 * @param Knowledge 需要存贮的知识实例
	 * @return 知识flash文件的存储路径
	 * @author hebi
	 */
	public String getFlash(MetaKnowledge knowledge);
	

    /**
	 * 按照知识类型搜索相关知识
	 *  @param searchword 搜索知识的关键词等条件
	 * @return 知识搜索结果的列表jaso类型字符串
	 * @author hebi
	 */
    public  String searchKnowledgestring(String searchword);
    /**
	 * 按照知识类型搜索相关知识
	 *  @param searchword 搜索知识的关键词等条件
	 * @return {@link PageDTO} 知识搜索结果的列表PageDTO类型字符串
	 * @author hebi
	 */
    public  PageDTO searchKnowledge(String searchword);
    /**
	 * 按照知识类型搜索相关知识
	 *  @param propertyValues 搜索知识的关键词等条件的hashMap
	 * @return 知识搜索结果的列表jaso类型字符串
	 * @author hebi
	 */
    public  PageDTO searchKnowledge(HashMap propertyValues);
    /**
	 * 按照知识类型搜索入库和非入库知识
	 *  @param knowledgetype 知识类型
	 *  @param uploader 知识上传者
	 *  @param isDtreenodeNull 域节点是否为空
	 * @return 知识搜索结果的列表jaso类型字符串
	 * @author hebi
	 */
    public  Page kSearch(String knowledgeclassname,SystemUser uploader,boolean isDtreenodeNull,Page page);
    /**
	 * 通过知识id获得通用知识类型知识的实例
	 * @param knowledgeID 需要获得的知识的id
	 * @return 具体知识的实例
	 * @author hebi
	 */
    public MetaKnowledge getMetaknowledge(Long knowledgeID);
    
    
    /**
	 * 通过知识id获得扩展知识类型知识的实例
	 * @param entityClass 通用知识类型的类
	 * @param knowledgeID 需要获得的知识的id
	 * @return 具体知识的实例
	 * @author hebi
	 */	
    public List<Property> getKnowledgePropertyValue(Long knowledgeID);
    
    
    /**
	 * 通过知识id删除知识
	 * @param knowledgeID 删除的知识的id
	 * @return 
	 * @author hebi
	 */
    public String delete(Long knowledgeID);
	
    /**
	 * 判断知识关键词是否存在
	 * @param keyword 关键词
	 * @return 关键词是否已经存在
	 * @author hebi
	 */
    public Boolean isKeywordExist(String keyword);
    /**
	 * 添加一个关键词
	 * @param keyword 关键词
	 * @return 操作结果
	 * @author hebi
	 */
    public Keyword addKeyword(String keyword);
    /**
	 * 首先判断系统中是否有这个关键词，没有就在系统中创建一个返回这个关键词，有就返回这个关键词
	 * @param keyword 作者的名字
	 * @return {@link Keyword} 返回用户需要的关键词
	 * @author hebi
	 */
    public Keyword SearchAndSaveKeyword(String keyword);
    /**
	 * 通过知识分类的名字添加一个分类,首先判断系统中是否有这个类别，没有就在系统中创建一个，有就返回这个类别
	 * @param knowledgetypename 知识类别的名字
	 * @return {@link Knowledgetype} 返回用户需要的类别
	 * @author hebi
	 */
    public Knowledgetype SearchAndSaveKnowledgetype(String knowledgetypename);
    /**
	 * 通过作者id返回一个关键词
	 * @param keywordid 关键词的id
	 * @return {@link Keyword} 返回用户需要的关键词
	 * @author hebi
	 */
    
    public Keyword getKeyword(Long keywordid);        
    /**
	 * 列出所有的关键词	
	 * @author cwd
	 */
    public List<Keyword> listKeywords();        
    /**
     * 判断知识作者是否存在
     * @param authorname 作者的名字
     * @return 作者是否已经存在
     * @author hebi
     */
    public Boolean isAuthorExist(String authorname);
    /**
	 * 通过作者的名字添加一个作者
	 * @param authorname 作者的名字
	 * @return 操作结果
	 * @author hebi
	 */
    public Author addAuthor(String authorname);
    /**
	 * 通过作者的名字添加一个作者,首先判断系统中是否有这个作者，没有就在系统中创建一个，有就返回这个作者
	 * @param authorname 作者的名字
	 * @return {@link Author} 返回用户需要的作者
	 * @author hebi
	 */
    public Author searchAndSaveAuthor(String authorname); 
    /**
	 * 通过作者id返回一个作者
	 * @param authorid 作者的id
	 * @return {@link Author} 返回用户需要的作者
	 * @author hebi
	 */
    public Author getAuthor(Long authorid);
    /**
     * 列出所有的作者
     * @param 
     * @return 
     * @author cwd
     */
    public List<Author> listAllAuthors();
    /**
	 * 保存一个附件
	 * @param {@link Attachment} att 附件对象
	 * @return {@link Attachment} 返回保存的附件持久态对象
	 * @author hebi
	 */
    public Attachment setAttachment(Attachment att);
    
    /**
	 * 保存一个知识版本信息
	 * @param {@link Version} 版本信息对象
	 * @return {@link Version} 返回保存的版本持久态对象
	 * @author hebi
	 */
    public Version setVersion(Version version);
    /**
     * 根据id得到版本
     * @param 
     * @return
     * @author hebi
     */
    public Version getVersion(Long id);
    
    /**
	 * 修改知识
	 * @param {@link Knowledge} 需要修改的知识
	 * @author hebi
	 */
    public void updateKnowledge(Knowledge k);
    /**
	 * 删除知识，其实是将knowledge的可见属性 isvisable设置为false
	 * @param {@link Knowledge} 需要修改的知识
	 * @author hebi
	 */
    public void deleteKnowledge(Knowledge k);
    //根据附件id获取附件
    public Attachment getAttachment(Long id);
    //通过exe抽取知识属性
    public List getkdtail(String docPath);
    //通过poi抽取并保存单点故障知识,返回保存的知识的个数
     public int  getdandiandtail(String docPath,List cdnodes) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //flash转换
    public int convertDOC2SWF(String sourcePath, String fileName) ;
    /**
     * 根据知识id和属性名得到属性值
     * @param knowledgeID
     * @param propertyName
     * @return
     */
    public Object getProperty(Long knowledgeID,String propertyName);
    
    public List<String> searchKeywords(String keyword);
    
    public List<String> searchAuthors(String author);
    
    public void saveVersion(Version version);
    
    public List<KtypeProperty> getKtypePropertyValue(Long knowledgeID);
    
    //判断知识是否已被读过
    public boolean isFirstView(SystemUser user,Long knowledgeid,SysBehaviorList sysBehaviorList,
			String objectType);
   
    
    //问题相关模块
    /**
     * 对问题进行分类排行
     * @param orderby
     * @param pagerecord
     * @return
     */
    public List<MetaKnowledge> sortQuestion(String orderby, String pagerecord);
    /**
     * 列出所有问题   
     * @param 
     * @return
     */
    public List<MetaKnowledge> listAllQuestion();
    /**
     * 得到引证文献   
     * @param 
     * @return
     */
    public List<MetaKnowledge> getReferenceDoc(String sugmessage);
    // 删除知识类型所用            吉祥
    public void deleteKnowledgeDeep(Knowledge k);
    //根据知识类型id拿到所有知识
    public List<MetaKnowledge> getKnowledgesByType(Ktype k);
    
    public List<MetaKnowledge> getKnowledgeByTitle(String titleName);
    
    public List<MetaKnowledge> getPositionKnowledgeByDomain(Long domainId);
  //下面方法的接口   江丁丁
	public Knowledge getExtendKnowledgeByKtype(Ktype ktype);
	
	public PageDTO searchKnowledge(String hqlfrom, String hql,
			Class<? extends Knowledge> entityClass, PageDTO page,
			Object[] objects, HashMap searchpropertyValues);
	
	public void moveTemp2Target(String tempdir, String targetdir,String filename);
}
