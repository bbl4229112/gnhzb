package edu.zju.cims201.GOF.service.webservice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import edu.zju.cims201.GOF.rs.dto.AvidmKnowledgeDTO;


public class ParseSearchXML {
	

	private byte[] xmlString;
	private String avidmip;
	 public ParseSearchXML(byte[] xmlString,String avidmip){
	  this.xmlString= xmlString;
	  this.avidmip=avidmip;
	 }
	 public List<AvidmKnowledgeDTO> parse() throws Exception{
	  //获得解析器
	  //解析xml文件
	  InputStream is = new ByteArrayInputStream(xmlString);
	  
	  InputStreamReader is_GBK=null;
	   try {
		is_GBK = new InputStreamReader(is,"GBK");
	  } catch (UnsupportedEncodingException e) {
		
		e.printStackTrace();
	  } 
	  
	  SAXBuilder sb=new SAXBuilder();
	  Document doc=(Document)sb.build(is);

	  //获得xml根元素
	  Element root = doc.getRootElement();

	  List<AvidmKnowledgeDTO> list_a=new ArrayList<AvidmKnowledgeDTO>();

	  List<Element> list_root=root.getChildren();
	  for(Element e:list_root){
		  List<Element> list=e.getChildren();
		  AvidmKnowledgeDTO avidm_k=new AvidmKnowledgeDTO();
		  for(Element e_sub:list)
		  {if(null!=e_sub.getAttributeValue("documentIID"))
			    avidm_k.setDocumentIID(e_sub.getAttributeValue("documentIID"));
		  
		  if(null!=e_sub.getAttributeValue("fileIID"))
			    avidm_k.setFileIID(e_sub.getAttributeValue("fileIID"));
		  
		  if(null!=e_sub.getAttributeValue("creator"))
			    avidm_k.setCreator(e_sub.getAttributeValue("creator"));
		  
		  if(null!=e_sub.getAttributeValue("ktype"))
			    avidm_k.setKtype(e_sub.getAttributeValue("ktype"));
		  
		  if(null!=e_sub.getAttributeValue("updatetime"))
			    avidm_k.setUpdatetime(e_sub.getAttributeValue("updatetime"));
		  
		  if(null!=e_sub.getAttributeValue("fileOriginalName"))
			    avidm_k.setFileOriginalName(e_sub.getAttributeValue("fileOriginalName"));
		  
		  if(null!=e_sub.getAttributeValue("productIID"))
			    avidm_k.setProductID(e_sub.getAttributeValue("productIID"));
		  
		  if(null!=e_sub.getAttributeValue("Userid"))
			    avidm_k.setUserid(e_sub.getAttributeValue("Userid"));
		  if(null!=e_sub.getAttributeValue("UserName"))
			    avidm_k.setUserName(e_sub.getAttributeValue("UserName"));
		  if(null!=e_sub.getAttributeValue("versionIID"))
			    avidm_k.setVersionIID(e_sub.getAttributeValue("versionIID"));
		  
		  
		  if(null!=e_sub.getAttributeValue("documentType"))
			    avidm_k.setDocumentType(e_sub.getAttributeValue("documentType"));
		  if (null != e_sub.getAttributeValue("titlename")) {
			  avidm_k.setTitlename(e_sub.getAttributeValue("titlename"));
			}
			if (null != e_sub.getAttributeValue("keyword")) {
				avidm_k.setKeyword(e_sub.getAttributeValue("keyword"));
			}
			if (null != e_sub.getAttributeValue("abstract_text")) {
				avidm_k.setAbstract_text(e_sub.getAttributeValue("abstract_text"));
			}
			if (null != e_sub.getAttributeValue("security_level")) {
				avidm_k.setSecurity_level(e_sub.getAttributeValue("security_level"));
			}
			if (null != e_sub.getAttributeValue("version")) {
				avidm_k.setVersionname(e_sub.getAttributeValue("version"));
			}
		  }
		  avidm_k.setAvidmip(avidmip);
		  list_a.add(avidm_k);
		 
	  }
	
	return list_a;
	

	 }
	
}
