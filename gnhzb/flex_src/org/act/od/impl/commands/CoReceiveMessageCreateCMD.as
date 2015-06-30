package org.act.od.impl.commands
{
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	public class CoReceiveMessageCreateCMD extends AODCommand
	{
		public function CoReceiveMessageCreateCMD()
		{
			super();
		}
		override public function execute(event:OrDesignerEvent):void
		{
			//			init for Attributes of the Figures
			var addFigureXML:XML = new XML();
			var addFigureMessage:AsyncMessage = new AsyncMessage();
			var createFigureXML:XML = new XML();
			
			//			get the value from the event
			addFigureXML = event.data.addFigureXML as XML;
			addFigureMessage = event.data.addFigureMessage as AsyncMessage;
			createFigureXML = addFigureMessage.headers.add_figurexml;
			
			addFigureXML.appendChild(createFigureXML);
			
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperatexml=addFigureXML;
		}
	}
}