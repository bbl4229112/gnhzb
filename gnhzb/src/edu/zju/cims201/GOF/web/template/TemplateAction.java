package edu.zju.cims201.GOF.web.template;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.fileupload.util.Streams;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.Template;
import edu.zju.cims201.GOF.rs.dto.TemplateDTO;
import edu.zju.cims201.GOF.service.template.TemplateService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;
@Results({
	@Result(name = "showTemplatePlat", location = "/WEB-INF/content/template/createTemplate.jsp"),
	@Result(name = "showTemplateView", location = "/WEB-INF/content/template/templateView.jsp")
	
})
public class TemplateAction extends ActionSupport implements ServletResponseAware{

	private static final long serialVersionUID = 6934838216595375073L;
	private String data;
	private long templateId;

	private String templateName;
	private String templateDetail;
	private HttpServletResponse response;
	private TemplateService templateService;
	PrintWriter out;

	public String processProving() throws Exception{

  	  out = response.getWriter();
  	  boolean falg1 = false;
  	  boolean falg2 = false;
  	  boolean falg3 = false;
  	  boolean falg4 = false;
  	  boolean falg5 = true;
  	  boolean falg6 = true;
  	  String source = null;
  	  String target = null;
  	  int startNub =0;
  	  int endNub = 0;
  	  NodeList cells = analyticXML(data);
  	         for(int i=0;i<cells.getLength();i++){
  	        	 
  	        	 Element cellElement =  (Element) cells.item(i);
  	             String difference = cellElement.getAttribute("difference");
  	             if(difference.equals("1")||difference == "1"){
  	            	 ++startNub;
  	            	String startid = cellElement.getAttribute("id");
  	            	for(int j = 0; j<cells.getLength();j++){
  	            		Element cellElement1 =  (Element) cells.item(j);
  	            		
  	            	     source = cellElement1.getAttribute("source");
  	            	     target = cellElement1.getAttribute("target");
  	            		if(!source.equals("")&&source.equals(startid)){
  	            	    	falg1 = true;
  	            	    }
  	            		if(!target.equals("")&&target.equals(startid)){
  	            			falg5 =false;
  	            		}
  	            	
  	            	}
  	            	
  	             }if(difference.equals("2")||difference.equals("3")){
  	            	 String startid = cellElement.getAttribute("id");
  	            	 for(int j = 0; j<cells.getLength();j++){
   	            		Element cellElement1 =  (Element) cells.item(j);
   	            		
   	            	     source = cellElement1.getAttribute("source");
   	            	     target = cellElement1.getAttribute("target");
   	            		if(!source.equals("")&&source.equals(startid)){

   	            	    	falg2 = true;
   	            	    }
   	            		if(!target.equals("")&&target.equals(startid)){

   	            	    	falg3 = true;
   	            	    }
   	            	}
  	            	 
  	             }if(difference.equals("4")){
  	            	++endNub;
  	            	 String startid = cellElement.getAttribute("id");
  	            	 for(int j = 0; j<cells.getLength();j++){
   	            		Element cellElement1 =  (Element) cells.item(j);
   	            		
   	            	     source = cellElement1.getAttribute("source");
   	            	     target = cellElement1.getAttribute("target");
   	            		
   	            		if(!target.equals("")&&target.equals(startid)){
   	            			falg4 = true;
   	            		}
   	            		if(!source.equals("")&&source.equals(startid)){
   	            	    	falg6 = false;
   	            	    }
   	            		
   	            	}
  	             }
  	         } 
  	      if(startNub>1||endNub>1){
  	    	  falg1 =false;
  	      }
  	      if(falg1&&falg2&&falg3&&falg4&&falg5&&falg6){
  	    	  out.print("yes");
  	      }else{
  	    	  out.print("no");
  	      }
		return null;

	}
	private NodeList analyticXML(String data) throws Exception{
		//设置为utf-8 这样xml中有中文不会报错
   	 byte[] xmldata =data.getBytes("utf-8");

  	      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = dbf.newDocumentBuilder();
		  
		  InputStream iStream = new ByteArrayInputStream(xmldata);
		  Document md = db.parse(iStream);
		  NodeList cells = md.getElementsByTagName("mxCell");
		return cells;
    }
	
	public String SaveXml() throws Exception{
		String uuid =UUID.randomUUID().toString();
		byte[] xmldata = data.getBytes("utf-8");
		String path = Constants.TEMPLATE_PATH;
		File templatedir = new File(path+uuid);
		if(!templatedir.exists()){
			templatedir.mkdirs();
		}
		File xmlData = new File(path+uuid+"//"+uuid+".xml");
		FileOutputStream outputStream = new FileOutputStream(xmlData);
		outputStream.write(xmldata);
		outputStream.close();
		Template template  = new Template();
		template.setTemplateName(templateName);
		template.setTemplateDetail(templateDetail);
		Date date = new Date();
		template.setCreateTime(date);
		template.setTemplateUuid(uuid);
		template.setXmlFileName(uuid+".xml");
		template.setXmlFilePath(uuid+".xml");
		templateService.saveTemplate(template);
		
		System.out.println(templateName);
		System.out.println(templateDetail);
		System.out.println(data);
		out=response.getWriter();
		out.write("yes");
		out.print(path);
		return null;
	}
	
	
	public String findTemplates() throws Exception{
		List<Template> templates = templateService.findTemplates();
		List<TemplateDTO> templateList = new ArrayList<TemplateDTO>();
		for(int i =0;i<templates.size();i++){
			TemplateDTO templateDTO =new TemplateDTO();
			templateDTO.setId(templates.get(i).getId());
			templateDTO.setTemplateName(templates.get(i).getTemplateName());
			templateDTO.setTemplateDetail(templates.get(i).getTemplateDetail());
			templateDTO.setCreateTime(DateFormat.getDateInstance().format(templates.get(i).getCreateTime()));
			templateList.add(templateDTO);
		}
		String jsonString =JSONUtil.write(templateList);
		out =response.getWriter();
		out.print(jsonString);
		return null;
	}
	

	
	public String createTemplate() throws IOException  {
		
		return "showTemplatePlat";
	}
	
	public String templateView() throws IOException{
		return "showTemplateView";
	}
	
	public void downloadXML() throws IOException{
		String fileName =templateService.getTemplateFileName(this.templateId);
		System.out.println(fileName);
		File file =new File(fileName);
		response.reset();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=\""
				+ new String(fileName.getBytes(), "ISO_8859_1") + "\"");
		response.setContentLength((int) file.length());

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file));
		BufferedOutputStream bos = new BufferedOutputStream(
				response.getOutputStream());

		Streams.copy(bis, bos, true);
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data){
		this.data = data;
	}
	public long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDetail() {
		return templateDetail;
	}
	public void setTemplateDetail(String templateDetail) {
		this.templateDetail = templateDetail;
	}
	public TemplateService getTemplateService() {
		return templateService;
	}
	@Autowired
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

}
