package edu.zju.cims201.GOF.web.client;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class HelloWorldClient {
	public static void main(String[] args) {
		 try
         {
                String wsdlUrl= "http://localhost:8080/springAxis/services/helloWorldService?wsdl";
                String nameSpaceUri= "http://localhost:8080/springAxis/services/helloWorldService";
                // 创建调用对象
                Service service = new Service();
                Call call = null;
                call = (Call) service.createCall();
                // 调用 getMessage
                System.out.println(">>>getMessage");
                call.setOperationName(new QName(nameSpaceUri, "sayHello"));
                call.setTargetEndpointAddress(new java.net.URL(wsdlUrl));
                String ret = (String) call.invoke(new Object[] {});
                System.out.println("return value is " + ret);
         }
         catch (Exception e)
         {
                e.printStackTrace();
         }
	}
}
