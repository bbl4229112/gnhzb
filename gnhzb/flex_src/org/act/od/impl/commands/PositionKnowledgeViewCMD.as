package org.act.od.impl.commands
{
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent; 
	import org.act.od.impl.events.DesignerToolBarAppEvent;
	import mx.controls.Alert;
	
	public class PositionKnowledgeViewCMD extends AODCommand
	{
		public var xmlFileId:String;
		public var xmlFileName:String;
		
		public function PositionKnowledgeViewCMD()
		{
			super();
		}
		
		override public function execute(event :OrDesignerEvent) :void{	
			xmlFileId = event.data.xmlFileId;
			xmlFileName = event.data.xmlFileName;
			
			var remote:RemoteObject = new RemoteObject();
			remote.destination = "knowledgeSource";
			remote.getFileByPath(this.xmlFileId);
			remote.addEventListener(ResultEvent.RESULT, fileOpenResultHandler);
		}
		
		protected function fileOpenResultHandler(event:ResultEvent):void {			
			var str:String=event.result.toString();
			var xml:XML=XML(str);
//			Alert.show(xml);
			new DesignerToolBarAppEvent(DesignerToolBarAppEvent.NEW_FILE,
				{fileName:this.xmlFileName, fileType:null ,fileCategory:null,sourceXML:xml,fileId:this.xmlFileId}).dispatch();
		}
	}
}