package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.model.UDDIReferenceModel;
	
	/**
	 * Show the "UDDI configuration" window
	 * 
	 *  @author Likexin
	 * 
	 */
	public class UDDIConfigurationCMD extends AODCommand
	{
		private var address :String;
		private var name :String;
		private var businessKey :String;
		
		private var serviceLocation :String;
		
		private var find_service:HTTPService = new HTTPService();
		
		//model
		private var orDesModelLoc : OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
		public var model : UDDIReferenceModel = orDesModelLoc.getUDDIReferenceModel();
		
		public function UDDIConfigurationCMD(){
			super();
		}
		/**
		 * @param event {address,name}
		 */
		override public function execute(event:OrDesignerEvent):void{
			this.address = event.data.address;
			this.name = event.data.name;
			//			this.name = "Loan approval";
			var serviceName :XML =
				<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:uddi-org:api_v3">
				   <soapenv:Header/>
				   <soapenv:Body>
					  <urn:find_service>
						 <urn:name>{this.name}</urn:name>
					  </urn:find_service>
				   </soapenv:Body>
				</soapenv:Envelope>;
			
			find_service.url=this.address + "/services/inquiry";
			find_service.method="POST";
			find_service.contentType="text/xml; charset=utf-8";
			find_service.headers={SOAPAction: ""};
			find_service.addEventListener(ResultEvent.RESULT, findServiceResult);
			find_service.addEventListener(FaultEvent.FAULT, faultResult);
			find_service.cancel();
			find_service.send(serviceName);
		}
		/**
		 * Get businessKey form Juddiv3 server
		 */
		private function findServiceResult(event :ResultEvent):void{
			var message :String = event.message.body.toString();
			//Alert.show("message = "+message);
			var i :int = message.indexOf("<serviceInfo businessKey", 0);
			var m :String = message.substring(i,message.indexOf("</serviceInfo>",i)+14);
			var xml:XML = new XML(m);
			//Alert.show("m = "+m);
			this.businessKey = xml.@businessKey;
			
			var serviceAddress :XML =
				<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:uddi-org:api_v3">
				   <soapenv:Header/>
				   <soapenv:Body>
					  <urn:get_businessDetail>
						 <urn:businessKey>{this.businessKey}</urn:businessKey>
					  </urn:get_businessDetail>
				   </soapenv:Body>
				</soapenv:Envelope>;
			
			find_service.removeEventListener(ResultEvent.RESULT, findServiceResult);
			find_service.addEventListener(ResultEvent.RESULT, find_addressResult);
			find_service.cancel();
			find_service.send(serviceAddress);
		}
		/**
		 * Get discoveryURL from Juddiv3 server
		 */
		private function find_addressResult(event :ResultEvent):void
		{
			var message :String = event.message.body.toString();
			//Alert.show("message final = "+message);
			var i :int = message.indexOf("<discoveryURL useType", 0);
			var m :String = message.substring(i + 41, message.indexOf("</discoveryURL>",i));
			
			this.serviceLocation = m;
			
			var xml :XML =
				<servicesRef>
					<serviceRef>
						<ServiceName>{this.name}</ServiceName>
						<ServiceLocation>{m}</ServiceLocation>
					</serviceRef>
				</servicesRef>;
			
			this.model.setXMLContent(xml);
			
			//find wsdl file
			find_service.url = this.serviceLocation + "?wsdl";
			find_service.method="GET";
			find_service.resultFormat = HTTPService.RESULT_FORMAT_XML;
			find_service.removeEventListener(ResultEvent.RESULT, find_addressResult);
			find_service.addEventListener(ResultEvent.RESULT, findWsdlResult);
			find_service.cancel();
			find_service.send();
			
		}
		
		
		/**
		 * Get businessKey form Juddiv3 server
		 */
		private function findWsdlResult(event :ResultEvent):void
		{
			var wsdl :String = event.message.body.toString();
			if(wsdl != null)
			{
				var xmlWsdl : XML = new XML(wsdl) ;
				var wsdlChildren: XMLList = xmlWsdl.children();
				var xmllist : XML;
				if(wsdlChildren != null)
					for each(xmllist in wsdlChildren)
				{
					if(xmllist.localName().toString()=="portType")
					{
						this.model.xmlPortType = xmllist;
						//Alert.show("wsdlll = "+this.model.xmllist+"wsdlll");
					}
					
				}
				//Alert.show("portType = "+wsdlPortType.toXMLString());
			}
			
			
		}
		
		private function faultResult(event :FaultEvent):void{
			Alert.show(event.message.toString());
		}
	}
}