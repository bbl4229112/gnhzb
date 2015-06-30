package edu.zju.cims201.GOF.service.webservice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.FileInputStream;

public class ParseXML{
 private String xmlString;
 public ParseXML(String xmlString){
  this.xmlString= xmlString;
 }
 public void parse() throws DocumentException{
  //获得解析器
  SAXReader sr = new SAXReader();
  
  
  //解析xml文件
  InputStream is = new ByteArrayInputStream(xmlString.getBytes());
  InputStreamReader utf8Is=null;
try {
	utf8Is = new InputStreamReader(is,"utf-8");
} catch (UnsupportedEncodingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
  
//  InputStream is=new StringBufferInputStream(xmlString);//传中文报错
  Document doc = sr.read(utf8Is);
  //获得xml根元素
  Element root = doc.getRootElement();
  
  System.out.println("rootName--"+root.getName());
  //获得根元素迭代器
  Iterator<Element> ir = root.elementIterator();
  //迭代根元素，看根元素下有哪些其他元素
  while (ir.hasNext()) {
   //第一个元素
   Element element = (Element) ir.next();

   //判断是哪个元素，方法应该没用错，错了就不好意思了
   if(element.getName().equals("userId")){
    //获取此元素属性值   
	  System.out.println( element.getStringValue());
    String userId = element.attributeValue("userId");
    System.out.println("userId====="+userId);}
   if(element.getName().equals("userName")){
	    //获取此元素属性值   
		  System.out.println( element.getStringValue());
	    String userName = element.attributeValue("userName");
	    System.out.println("userName====="+userName);}
   if(element.getName().equals("userEmail")){
	    //获取此元素属性值   
		  System.out.println( element.getStringValue());
	    String userEmail = element.attributeValue("userEmail");
	    System.out.println("userEmail====="+userEmail);}
   if(element.getName().equals("userAge")){
	    //获取此元素属性值   
		  System.out.println( element.getStringValue());
	    String userAge = element.attributeValue("userAge");
	    System.out.println("userAge====="+userAge);}
//    String userName = element.attributeValue("userName");
//    System.out.println("userId====="+userId);
//    System.out.println("userId====="+userId);
//    System.out.println("userId====="+userId);
//    System.out.println("userId====="+userId);
//    System.out.println("userId====="+userId);
//    System.out.println("userId====="+userId);
//    System.out.println("userId====="+userId);
//    System.out.println("userName====="+userName);
//    System.out.println("userName====="+userName);
//    System.out.println("userName====="+userName);
//    System.out.println("userName====="+userName);
//    System.out.println("userName====="+userName);
//  }
   } 
  }
 }
 