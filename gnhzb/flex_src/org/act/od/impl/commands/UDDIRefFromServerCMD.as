package org.act.od.impl.commands
{
	import mx.rpc.events.ResultEvent;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.UDDIRefViewAppEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.model.UDDIReferenceModel;
	/**
	 * 
	 *  @author Likexin
	 * 
	 */
	public class UDDIRefFromServerCMD extends AODCommand{
		
		//model
		private var orDesModelLoc : OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
		
		public var model : UDDIReferenceModel = orDesModelLoc.getUDDIReferenceModel();
		
		public function UDDIRefFromServerCMD(){
			super();
		}
		
		/**
		 * @param event{}
		 */
		override public function execute(event : OrDesignerEvent) :void{
			
			getRefFromServer();
		}
		
		private function getRefFromServer() :void{
			
			var i:int;
			var tempstr:String;
			test();
		}
		
		private function test() : void {
			
			var xml : XML = 
				<servicesRef>
					<serviceRef>
						<ServiceName>{"TestWS"}</ServiceName>
						<ServiceLocation>{"http://localhost/TestWS"}</ServiceLocation>
					</serviceRef>
					<serviceRef>
						<ServiceName>{"ResourceService"}</ServiceName>
						<ServiceLocation>{"http://localhost/ResourceService"}</ServiceLocation>
					</serviceRef>
				</servicesRef>;
			
			//			model.setXMLContent(xml);
		}
		
		private function getRefFromServerResult(event : ResultEvent):void{
			
			var str:String=event.result.toString();
			var OpId:String=str.substring(0,str.indexOf('|'));
			str=str.substring(str.indexOf('|')+1,str.length);
			var SerName:String=str.substring(0,str.indexOf('|'));
			str=str.substring(str.indexOf('|')+1,str.length);
			var OpName:String=str.substring(0,str.indexOf('|'));
			str=str.substring(str.indexOf('|')+1,str.length);
			var OpRef:String=str.substring(0,str.indexOf('|'));
			
			var newnode :XML = new XML();
			
			newnode =
				<serviceRef>
					<OpId>{OpId}</OpId>
					<SerName>{SerName}</SerName>
					<OpName>{OpName}</OpName>
					<OpRef>{OpRef}</OpRef>
				</serviceRef>;
			
			model.appendXMLContent(newnode);
		}
		
	}
}