
package edu.zju.cims201.GOF.service.webservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;



public class TestAxisKnowledgeService {
public static void main(String[] args) throws Exception {
	  
	try {
		String namespace="http://webservice.service.GOF.cims201.zju.edu";
		String endpoint ="http://localhost/caltks/services/knowledgeWebService?wsdl";
		// 接口方法名
		String operationName = "addKnowledge";
		// 定义service对象
		Service service = new Service();
		// 创建一个call对象
		Call call = (Call) service.createCall();
		// 设置目标地址，即webservice路径
		call.setTargetEndpointAddress(endpoint);
		// 设置操作名称，即方法名称
		call.setOperationName(new QName(namespace, operationName));
		// 设置方法参数
		//call.addParameter(new QName(namespace));
		call.addParameter( "xmlString",XMLType.SOAP_BASE64BINARY,javax.xml.rpc.ParameterMode.IN);
		//***********************测试**********************************
	     String xmlString="<?xml version=\"1.0\" encoding=\"GBK\"?><DOC>   <FIlE Userid=\"administrator\" />    <FIlE UserName=\"系统管理\" />    <FIlE productIID=\"d59918e82c2b3d2e38741d3e5931790d\" />    <FIlE versionIID=\"c7c72f7d6705d5c73bb064f61400e648\" />    <FIlE version=\"A\" />    <FIlE documentIID=\"dec9009a79d379709d5b11bda94a6090\" />    <FIlE ktype=\"表格内容设计文件\" />    <FIlE ktypeid=\"4004\" />    <FIlE fileIID=\"838c8588edb33ac37f595b5a90ba7c32\" />    <FIlE titlename=\"知识测试\" />    <FIlE security_level=\"内部\" />    <FIlE updatetime=\"2011-07-23 11:30:55\" />    <FIlE abstract_text=\"\" />    <FIlE keyword=\"\" /></DOC>";
		//***********************测试结束**********************************
		//String xmlString="<?xml version=\"1.0\" encoding=\"GBK\"?><DOC> <FIlE documentIID=\"de5a1fb3d08cb4dc104b1ad268f4d623\"/> <FIlE fileIID=\"c1cf06e7e702ebd958816f788cb828a6\"/> <FIlE productIID=\"de350bff13ecdf855b995a4701546430\"/> <FIlE versionIID=\"0af7a97c1ba13c289715610fab92f854\"/> <BOOKMARK name=\"RaacCvMk主题词01\" /><BOOKMARK name=\"RaacCvMk产品代号01\"><![CDATA[XXXXXX]]></BOOKMARK> <BOOKMARK name=\"RaacCvMk文件编号01\"><![CDATA[Document-7797]]></BOOKMARK></DOC>";
		// 设置返回值类型
		// 对于返回是字符串数组的返回类型只有这两种可行
		// call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_VECTOR);
		call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_BASE64BINARY);
//		call.setUseSOAPAction(true);
		call.setSOAPActionURI(namespace + "/" + operationName);
		call.setSOAPActionURI("http://schemas.xmlsoap.org/wsdl/");
		//byte[] v=null;
		 byte[] re = (byte[]) call.invoke(new Object[] {xmlString.getBytes("GBK")});
//		 System.out.println(new String(re));
//		 String xmlString=new String(re);
//		
//        System.out.print(xmlString);
//        ParseUserXML p=new ParseUserXML(re);
//		 p.parse();
	    } catch (Exception ex) {
		ex.printStackTrace();
	}
}}