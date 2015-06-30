package edu.zju.cims201.GOF.web.client;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTree;
import edu.zju.cims201.GOF.service.classificationtree.ClassificationTreeService;
import edu.zju.cims201.GOF.service.codeclass.CodeClassService;


public class SendbomAction extends ActionSupport{
	
//	private static ApplicationContext cxt;
	
	private  CodeClassService codeClassService;
	private ClassificationTreeService classificationTreeService;
	
/*	public SendBomAction(){
		cxt=new ClassPathXmlApplicationContext("applicationContext.xml");
		codeClassService = (CodeClassService) cxt.getBean("codeClassServiceImpl");
	}
	public static void main(String[] args){
		//List<CodeClass> list= service.findConstructed();
		
		SendBomAction sb = new SendBomAction();
		//List<CodeClass>list= codeClassService.findConstructed();
		List<CodeClass>list= sb.getList();
		System.out.println(list.size());
		
	}*/
	
	public void sendBom(){

		List<ClassificationTree> list2 = classificationTreeService.getClassStruct2();

		JSONArray jsArr = new JSONArray();
		
		//ClassificationTree中有属性自关联，配置JsonConfig，读取自关联
		//防止net.sf.json.JSONException: There is a cycle in the hierarchy!
		JsonConfig jsonConfig = new JsonConfig(); //建立配置文件
		jsonConfig.setIgnoreDefaultExcludes(false); //设置默认忽略
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 

		jsArr.addAll(list2,jsonConfig);

		String boms = jsArr.toString();

		try
        {
              // String wsdlUrl= "http://localhost:8080/gnhzb/services/moduleService?wsdl";
              // String nameSpaceUri= "http://localhost:8080/gnhzb/services/moduleService";
			 	String wsdlUrl= "http://10.11.112.74:8080/gdksp/services/moduleService?wsdl";
			 	String nameSpaceUri= "http://10.11.112.74:8080/gdksp/services/moduleService";  
			// 创建调用对象
               Service service = new Service();
               Call call = null;
               call = (Call) service.createCall();
               call.setOperationName(new QName(nameSpaceUri, "saveBom"));
               call.addParameter("boms", XMLType.SOAP_STRING, ParameterMode.IN);
               //call.setReturnType(org.apache.axis.Constants.SOAP_BOOLEAN);
               call.setReturnType(org.apache.axis.Constants.SOAP_BOOLEAN);
               call.setTargetEndpointAddress(new java.net.URL(wsdlUrl));
               Boolean ret = (Boolean) call.invoke(new Object[] {boms});

               System.out.println("return value is " + ret);
        }
        catch (Exception e)
        {
               e.printStackTrace();
        }
		
	}


	public CodeClassService getCodeClassService() {
		return codeClassService;
	}
	@Autowired
	public void setCodeClassService(CodeClassService codeClassService) {
		this.codeClassService = codeClassService;
	}

	public ClassificationTreeService getClassificationTreeService() {
		return classificationTreeService;
	}
	@Autowired
	public void setClassificationTreeService(
			ClassificationTreeService classificationTreeService) {
		this.classificationTreeService = classificationTreeService;
	}

}
