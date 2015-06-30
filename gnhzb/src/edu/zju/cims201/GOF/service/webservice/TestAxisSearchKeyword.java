package edu.zju.cims201.GOF.service.webservice;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;



public class TestAxisSearchKeyword {
public static void main(String[] args) throws Exception {
	  
	try {
		String namespace="http://192.168.1.110:7001/avidm/services/CustomizeService";
		String endpoint = "http://192.168.1.110:7001/avidm/services/CustomizeService?wsdl";
		// 接口方法名
		String operationName = "seekFile";
		String userid="administrator";
		String keyword="测试";
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
		//call.setSOAPActionURI("http://schemas.xmlsoap.org/wsdl/");
		//byte[] v=null;
		 byte[] re = (byte[]) call.invoke(new Object[] {userid,keyword.getBytes()  });
		 String xmlString=new String(re);
		
        System.out.print(xmlString);
      
	    } catch (Exception ex) {
		ex.printStackTrace();
	}
}}