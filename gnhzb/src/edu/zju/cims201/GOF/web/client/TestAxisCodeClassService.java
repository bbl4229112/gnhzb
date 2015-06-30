package edu.zju.cims201.GOF.web.client;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
/*
 * 将web service的应用挂到收到挂到tomcat上的axis上，用的版本是axis2。
 */
public class TestAxisCodeClassService {
	public static void main(String[] args){
		try {
			String namespace="http://axis";
			String endpoint = "http://localhost:8080/axis2/services/MyService?wsdl";
			// 接口方法名
			String operationName = "sayHello";
			//String userid="administrator";
			//String keyword="测试";
			// 定义service对象
			Service service = new Service();
			// 创建一个call对象
			Call call = (Call) service.createCall();
			// 设置目标地址，即webservice路径
			call.setTargetEndpointAddress(endpoint);
			// 设置操作名称，即方法名称
			call.setOperationName(new QName(namespace, operationName));
			// 设置方法参数
			//call.addParameter( "userid",XMLType.SOAP_STRING,javax.xml.rpc.ParameterMode.IN);
			//call.addParameter( "keyWord",XMLType.SOAP_BASE64BINARY,javax.xml.rpc.ParameterMode.IN);

			// 对于返回是字符串数组的返回类型只有这两种可行
			// call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_VECTOR);
			//call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_BASE64BINARY);
			call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_STRING);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + "/" + operationName);
			//call.setSOAPActionURI("http://schemas.xmlsoap.org/wsdl/");
			//byte[] v=null;
			 String s = (String) call.invoke(new Object[] {});
			 //String xmlString=new String(s);
			
	        System.out.print(s);
	      
		    } catch (Exception ex) {
		    	ex.printStackTrace();
		    }
	}
}
