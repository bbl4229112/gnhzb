package edu.zju.cims201.GOF.service.webservice;




public class KnowledgeWebServiceImpl implements KnowledgeWebService {


	
	public byte[] addKnowledge(byte[] xmlString) {


		// TODO Auto-generated method stub
//System.out.println("接收web服务请求。。。。");
//System.out.println(new String(xmlString));

		
		ParseKnowledgeXML t=new ParseKnowledgeXML(xmlString);	
			try {
				//System.out.println(new String(xmlString));
				t.parseAvidm();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				return "error".getBytes();
			}
		
	
		return "ok".getBytes();
	}
	
	public byte[] addQualityKnowledge(byte[] xmlString) {


		// TODO Auto-generated method stub
//System.out.println("接收web服务请求。。。。");
//System.out.println(new String(xmlString));

		
		ParseKnowledgeXML t=new ParseKnowledgeXML(xmlString);	
			try {
				//System.out.println(new String(xmlString));
				t.parseQuality();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				return "error".getBytes();
			}
		
	
		return "ok".getBytes();
	}


}
