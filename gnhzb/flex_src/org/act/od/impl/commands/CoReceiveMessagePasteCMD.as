package org.act.od.impl.commands
{
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	public class CoReceiveMessagePasteCMD extends AODCommand
	{
		public function CoReceiveMessagePasteCMD()
		{
			super();
		}
		override public function execute(event:OrDesignerEvent):void
		{
			//			init for Attributes of the Figures
			var addFigureXML:XML = new XML();
			var addFigureMessage:AsyncMessage = new AsyncMessage();
			var createFigureArr:Array = new Array();
//			var createFigureXML:XML = new XML();
			
			//			get the value from the event
			addFigureXML = event.data.addFigureXML as XML;
			addFigureMessage = event.data.addFigureMessage as AsyncMessage;
			createFigureArr =addFigureMessage.headers.pasteArray_xml.split(",");
			for(var i:int =0;i<createFigureArr.length;i++)
			{
				addFigureXML.appendChild(createFigureArr[i]);
			}
//			createFigureXML = addFigureMessage.headers.add_figurexml;
			
//			createFigureArr = moveFigureMessage.headers.modify_id.split(",");
//			
//			addFigureXML.appendChild(createFigureXML);
//			
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperatexml=addFigureXML;
		}
	}
}