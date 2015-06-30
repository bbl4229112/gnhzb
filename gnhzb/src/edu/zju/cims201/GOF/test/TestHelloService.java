package edu.zju.cims201.GOF.test;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Stephen
 * 
 * 测试调用WebService
 */
public class TestHelloService {
    private static final Log log = LogFactory.getLog(TestHelloService.class);
    private static final String HELLO_SERVICE_ENDPOINT = "http://192.168.1.103:8080/axis/services/ConvertServices?wsdl";
   // private static final String HELLO_SERVICE_ENDPOINT = "http://localhost:8080/axis/services/HelloServices?wsdl";
    public static void main(String[] args) {
        TestHelloService tester = new TestHelloService();
        //tester.callSayHello();
        //tester.callSayHelloToPerson();
        tester.callGetFileName();
    }

    public void callSayHello() {
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(
                    HELLO_SERVICE_ENDPOINT));
            call.setOperationName(new QName("http://ws.cims201.zju.edu",
                    "sayHello"));
            call.setReturnType(org.apache.axis.Constants.XSD_STRING);
            try {
                String ret = (String) call.invoke(new Object[] {});
                System.out.println("The return value is:" + ret);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        log.error("call sayHello service error!");
    }

    public void callSayHelloToPerson() {
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(
                    HELLO_SERVICE_ENDPOINT));
            call.setOperationName(new QName("http://ws.cims201.zju.edu",
                    "sayHelloToPerson"));
            call.addParameter("name", org.apache.axis.Constants.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.setReturnType(org.apache.axis.Constants.XSD_STRING);
            try {
                String ret = (String) call.invoke(new Object[] { "sss" });
                System.out.println("The return value is:" + ret);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        log.error("call sayHello service error!");
    }
    
    public void callGetFileName() {
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(
                    HELLO_SERVICE_ENDPOINT));
            call.setOperationName(new QName("http://ws.cims201.zju.edu",
                    "convertFile"));
            call.addParameter("id", org.apache.axis.Constants.XSD_LONG,
                    javax.xml.rpc.ParameterMode.IN);
            call.setReturnType(org.apache.axis.Constants.XSD_STRING);
            try {
                String ret = (String) call.invoke(new Object[] {6l});
                System.out.println("文件名为：" + ret);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        log.error("call sayHello service error!");
    }
}
