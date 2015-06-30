package org.act.od.impl.commands
{
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	/**
	 * For receiving the messaging
	 *Dispatch the detail Command for modifying original xml file for cooperation
	 * Deleting figures
	 * @author Mengsong
	 *
	 */
	
	public class CoReceiveMessageFigureDeleteCMD extends AODCommand
	{
		public function CoReceiveMessageFigureDeleteCMD()
		{
			super();
		}
		override public function execute(event:OrDesignerEvent):void
		{
			//			init for Attributes of the Figures
			var deleteFigureXML:XML = new XML();
			var deleteFigureMessage:AsyncMessage = new AsyncMessage();
			var idArray_Delete:Array = new Array();
	
			//			get the value from the event
			deleteFigureXML = event.data.deleteFigureXML as XML;
			deleteFigureMessage = event.data.deleteFigureMessage as AsyncMessage;
			
			//			split the attributes from the ASYMessage
			idArray_Delete = deleteFigureMessage.headers.delete_id.split(",");
			//			do the procession of the  changing the CanvasXML from the message
			for(var j:int = 0;j<idArray_Delete.length;j++)
			{
				for (var i:int=0; i < deleteFigureXML.child("Figure").length(); i++)
						if (deleteFigureXML.Figure[i].@ID == idArray_Delete[j])
							{
								delete deleteFigureXML.Figure[i];
							}
			}
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperatexml=deleteFigureXML;
		}
	}
}