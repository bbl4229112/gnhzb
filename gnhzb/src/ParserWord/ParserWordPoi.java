package ParserWord;

import java.io.File;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class ParserWordPoi {
	
	private String m_strWordDir;
	
	/**
     * @文件类型
    */
	private String m_strWordType;
	
	/**
     * @Construct
    */
	public ParserWordPoi(String ktype,String wordpath){
		m_strWordType = ktype;
		m_strWordDir = wordpath;
		
	}
	
	/**
     * @name SetWordType
     * @fun  设置类型
     * @author wangliwei
     * @data  2011-06-30
    */
	void SetWordType(String strWordType){
		m_strWordType = strWordType;
	}
	/**
     * @name SetWordType
     * @fun  设置类型
     * @author wangliwei
     * @data  2011-06-30
    */
	void SetWordDir(String strWordDir){
		m_strWordDir = strWordDir;
	}
	@Test
	public void test()
	{
		ParserXML();
	}
	
     
	/**
     * @name ParserXML
     * @fun  解析XML
     * @author wangliwei
     * @data  2011-06-30
    */
	public String ParserXML(){
		
		String strParseValue = "";
		try{
			 String strPath = URLDecoder.decode(this.getClass().getResource("/").getPath(), "UTF-8");
			 String strXMLpath = strPath + "/" + "WordType.xml";
			 File xmlfile = new File(strXMLpath);
			 if(xmlfile.exists() == true){
				//File inputXml = new File(fileName);
				SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(xmlfile);
				Element wordtypeList = document.getRootElement();
				List<Element> wordtypes =  wordtypeList.elements("wordtype");
				String strWordType = ""; //类型
				String strClassName = "";//类名
				for (Element employee : wordtypes){
					m_strWordType = "学术论文";
					strWordType = employee.attributeValue("name");
					strClassName = employee.attributeValue("class");
					if(strWordType == m_strWordType){
					    break;
					}
				} 
				
				InterfaceParserWord interParWord = (InterfaceParserWord)Class.forName(strClassName).newInstance();
			
				strParseValue = interParWord.ParserWord(m_strWordDir);
			}
			 
		}catch(Exception e){
			e.printStackTrace();
		}
		return strParseValue;
	}
	
	
	
	
	
	
	
	
	
	
	
	
		
}
