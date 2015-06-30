package edu.zju.cims201.GOF.service.webservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


import edu.zju.cims201.GOF.rs.dto.AvidmKnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.service.webservice.qualityservice.DataService;
import edu.zju.cims201.GOF.service.webservice.qualityservice.DataServiceService;
import edu.zju.cims201.GOF.util.Constants;



@Component
public class AxisWebService {
	
	
	private static ApplicationContext cxt;
	private ParseUserXML parseUserXML;
	public AxisWebService() {
		super();
		cxt=new ClassPathXmlApplicationContext("applicationContext.xml");
		parseUserXML= (ParseUserXML) cxt.getBean("parseUserXML");
	}
	
	
	
	/**
	 * 得到用户
	 * @throws Exception
	 */	
  public  void userService() throws Exception {
	  String[] avidmhosts=Constants.AVIDMHOSTPROPERTY.split("#");
	  
		for(String avidmhosttemp:avidmhosts)
		{
			String[] avidmproperty=avidmhosttemp.split("&");
			if(avidmproperty.length>=2)
			{System.out.println("正在同步"+avidmproperty[0]+"的用户");
				String avidmip=avidmproperty[1];
			
		
	try {
		String namespace="http://"+avidmip+Constants.AVIDM_WEBSERVICE_NAMESPACE;
		String endpoint ="http://"+avidmip+Constants.AVIDM_WEBSERVICE_ENDPOINT;
//		String namespace="http://10.0.10.224:7101/avidm/services/CustomizeService";
//		String endpoint ="http://10.0.10.224:7101/avidm/services/CustomizeService?wsdl";
		// 接口方法名
		String operationName = "userInfoSync";
		//String operationName = "getCAServerIP";
		
		// 定义service对象
		Service service = new Service();
		// 创建一个call对象
		Call call = (Call) service.createCall();
		// 设置目标地址，即webservice路径
		call.setTargetEndpointAddress(endpoint);
		// 设置操作名称，即方法名称
		call.setOperationName(new QName(namespace, operationName));
	
		// 对于返回是字符串数组的返回类型只有这两种可行
		// call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_VECTOR);
		call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_BASE64BINARY);
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(namespace + "/" + operationName);
	
		byte[] re = (byte[]) call.invoke(new Object[] {});

		 String xmlString=new String(re);
         System.out.print(xmlString);
         parseUserXML.setXmlString(re);
         parseUserXML.parseUser();
		 
	    } catch (Exception ex) {
		ex.printStackTrace();
	}
			}
		}
	  
//*********************测试********************	  
//		byte[] re=null;
//        File file=new File("c:\\A1.xml");
//    	try {
//			InputStream in=new FileInputStream(file);
//			long si = file.length();
//			re=new byte[(int)si];
//	    	int i=in.read(re);
//	    	if(i!=si)
//	    		throw new Exception("chuc");
//		} catch (Exception e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
//		 String xmlString=new String(re);
//        // System.out.print(xmlString);
//         parseUserXML.setXmlString(re);
//         parseUserXML.parseQualityKnowledge();
       //*********************测试结束********************	 
			}

/**
 * 关键词检索
 * @param keyword 搜索的关键词
 * @param userid 用户名
 * @throws Exception
 */
  public List<AvidmKnowledgeDTO> searchService(String keyword,String userid,String avidmip)throws Exception
     {
	  List<AvidmKnowledgeDTO> list=new ArrayList();
	try {
		
		String namespace="http://"+avidmip+Constants.AVIDM_WEBSERVICE_NAMESPACE;
		String endpoint ="http://"+avidmip+Constants.AVIDM_WEBSERVICE_ENDPOINT;

		// 接口方法名
		String operationName = "seekFile";
		
		// 定义service对象
		Service service = new Service();
		// 创建一个call对象
		Call call = (Call) service.createCall();
		// 设置目标地址，即webservice路径
		call.setTargetEndpointAddress(endpoint);
		// 设置操作名称，即方法名称
		call.setOperationName(new QName(namespace, operationName));
		// 设置方法参数
		call.addParameter( "userid",XMLType.SOAP_STRING,javax.xml.rpc.ParameterMode.IN);
		call.addParameter( "keyWord",XMLType.SOAP_BASE64BINARY,javax.xml.rpc.ParameterMode.IN);

		// 对于返回是字符串数组的返回类型只有这两种可行
		// call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_VECTOR);
		call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_BASE64BINARY);
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(namespace + "/" + operationName);
		
		 byte[] re = (byte[]) call.invoke(new Object[] {userid,keyword.getBytes()  });
//******************************测试数据×××××××××××××××××××××××××××	  
//		byte[] re=null;
//        File file=new File("c:\\ssss.xml");
//    	try {
//			InputStream in=new FileInputStream(file);
//			long si = file.length();
//			re=new byte[(int)si];
//	    	int i=in.read(re);
//	    	if(i!=si)
//	    		throw new Exception("chuc");
//		} catch (Exception e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
//******************************测试数据结束×××××××××××××××××××××××××××	          
	     ParseSearchXML p=new ParseSearchXML(re,avidmip);
		 list=p.parse();
		 
		
      
	    } catch (Exception ex) {
		ex.printStackTrace();
	}
	    
	
//	  AvidmKnowledgeDTO dto=new  AvidmKnowledgeDTO();
//	  dto.setCreator("sssss");
//	  dto.setUpdatetime("2012 12 32 12:34:34");
//	  dto.setKtype("少时诵诗书");
//	  dto.setTitlename("生生世世是生生世世是搜索");
//	  list.add(dto);
      return list;
}
  
  




public ParseUserXML getParseUserXML() {
	return parseUserXML;
}
@Autowired
public void setParseUserXML(ParseUserXML parseUserXML) {
	this.parseUserXML = parseUserXML;
}

public static void main(String[] arg)
{
	 AxisWebService temp=new AxisWebService();
	 try {
		 temp.QualityService();
		//temp.userService();
		// temp.searchService("报告 知识", "administrator","10.0.10.224:7101");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}

/**
 * 得到质量管理系统数据
 * @throws Exception
 */	
public  void QualityService() throws Exception { 
try {

	String s = "";
//    try {
//        DataServiceService service = new DataServiceService(
//                new URL(Constants.QUALITY_WEBSERVICE_NAMESPACE),
//                new QName("http://service.application.javaplus.org/", "DataServiceService"));
//        DataService port = service.getDataServicePort();
//   
//        
//        s = port.getAllA1();
//        byte[] byteArray = s.getBytes("UTF-8"); 
	
	//******************************测试数据×××××××××××××××××××××××××××	  
	byte[] byteArray=null;
    File file=new File("c:\\A1.xml");
	try {
		InputStream in=new FileInputStream(file);
		long si = file.length();
		byteArray=new byte[(int)si];
    	int i=in.read(byteArray);
    	if(i!=si)
    		throw new Exception("chuc");
	} catch (Exception e) {
		// TODO 自动生成 catch 块
		e.printStackTrace();
	}
//******************************测试数据结束×××××××××××××××××××××××××××	  
	//String xmlString=new String(byteArray,"GBK");
   //  System.out.println("@@@@@@@@@@@@@@@@@@@@"+xmlString);

    // ParseUserXML t=new ParseUserXML();
     parseUserXML.setXmlString(byteArray);
     parseUserXML.parseQualityKnowledge();
    }catch(Exception e){
        e.printStackTrace();
    }

	
    //System.out.println(s);
	//String xmlString=new String(re);
    // System.out.print("@@@@@@@@@@@@@@@@@@@@"+xmlString);
    // parseUserXML.setXmlString(s);
   //  parseUserXML.parseQualityKnowledge();
	 
    
}
	
		}