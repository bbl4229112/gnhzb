package edu.zju.cims201.GOF.service.knowledge.ktype;


import java.util.List;
import java.util.Set;

import org.springside.modules.orm.Page;

import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;

/**
 * 提供知识类型配置的相关服务接口，有具体的实现类来实现相关的方法
 * 
 * @author hebi
 */

public interface KtypeService {
	
	
	//public List<Ktype> getAllKtype();
	
	/**
	 * 判断该知识类型是否存在
	 * @param propertyname 要判断的知识类型的属性名 ，一般只用在 判断 name(英文名) 和 ktypeName（中文名）,
	 * @return 
	 * @author hebi
	 */
	public boolean isKtypeExist(String propertyname,String propertyvalue);
	
	
	/**
	 * 获得所有通用知识类型的属性
	 * @return 
	 * @author hebi
	 */
	public Page<Property> listCommonProperties(Page<Property> page);
	public List<Property> listCommonProperties();
	
	
	
	/**
	 * 获得所有扩展知识类型的属性
	 * @return 
	 * @author hebi
	 */
	public Page<Property> listExpandedProperties(Page<Property> page);
	public List<Property> listExpandedProperties();
	
	public Property getExpandedProperties(Long id);
	
	
	/**
	 * 创建某一个知识类型
	 * @param Ktype 需要创建的知识类型
	 * @return 
	 * @author hebi
	 */
	public String createKtype(Ktype ktype,List<KtypeProperty>ktypepropertites);
	
	/**
	 * 更改某一个知识类型，作为编辑模板用
	 * @param Ktype 需要创建的知识类型
	 * @return String
	 * @author panlei
	 */
	public String updateKtype(Ktype ktype,List<KtypeProperty>ktypepropertites);
	
	/**
	 * 判断该知识属性是否已经存在
	 * @param propertyname  要判断的属性的属性名 ，一般只用在 判断 name(英文名) 和 ktypeName（中文名）,
	 * @param propertyvalue 要判断的属性的值
	 * @return 
	 * @author hebi
	 */
	public boolean isPropertyExist(String propertyname,String propertyvalue);
	
	
	/**
	 * 创建一个知识类型
	 * @param property 该知识属性
	 * @return 
	 * @author hebi
	 */
	public String createProperty(Property property);

	
	
	/**
	 * 列出所有的知识类型
	 * @return 知识类型列表set类型
	 * @author hebi
	 */


	public List<Ktype> listKtypes(boolean withoutcommon);

	
	/**
	 * 通过某一知识类型的id列出该知识类型的所有属性
	 * @param ktypeID 该知识类型的id
	 * @return 知识类型列表set类型
	 * @author hebi
	 */
	public Page<Property> listKtypeProperties(Long ktypeID,Page<Property> page);
	
	public List<KtypeProperty> listKtypeProperties(Long ktypeID);
	
	/**
	 * 通过知识类型id获得一个知识类型实例
	 * @param ktypeId 该知识类型id
	 * @return {@link Ktype}的一个实例
	 * @author hebi
	 */
	public Ktype getKtype(Long ktypeId);
	/**
	 * 通过知识类型名称获得一个知识类型实例
	 * @param ktypename 该知识类型id
	 * @return {@link Ktype}的一个实例
	 * @author hebi
	 */
	public Ktype getKtype(String ktypename);
	public Ktype getKtypeByKtypeName(String ktypename);

	
//	public void setKtype(Ktype ktype);
	
	
	/**
	 * 通过某知识属性id获得一个知识属性实例
	 * @param ktypeId 该知识属性id
	 * @return {@link Property} 的一个实例
	 * @author hebi
	 */
    public Property getProperty(Long propertyId);
    public Property getProperty(String description);
    public Page<Ktype> listKtypes(Page<Ktype> page); 
    
    public String deleteKtype(Long ktypeId);
    
    public String deleteProperty(Long propertyId);
	public List<Knowledgetype> listKnowledgetype();

}
