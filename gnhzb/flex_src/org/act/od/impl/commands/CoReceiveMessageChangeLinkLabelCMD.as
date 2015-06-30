package org.act.od.impl.commands
{
	import mx.events.IndexChangedEvent;
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	public class CoReceiveMessageChangeLinkLabelCMD extends AODCommand
	{
		public function CoReceiveMessageChangeLinkLabelCMD()
		{
			super();
		}
		override public function execute(event:OrDesignerEvent):void
		{
			//			init for Attributes of the Figures
			var moveFigureMessage:AsyncMessage = new AsyncMessage();
			var moveFigureXML:XML = new XML();
			var labelwidth:String = new String();
			var labelheight:String = new String();
			var labelx:String = new String();
			var labely:String = new String();
			var labelID:String = new String();
			var figureName:String = new String();
			//			get the value from the event
			moveFigureMessage = event.data.moveFigureMessage;
			moveFigureXML = event.data.moveFigureXML as XML;
			
			labelID = moveFigureMessage.headers.linkID_Modify;
			labelwidth = moveFigureMessage.headers.labelwidth_Modify;
			labelheight = moveFigureMessage.headers.labelheight_Modify;
			labelx = moveFigureMessage.headers.labelx_Modify;
			labely = moveFigureMessage.headers.labely_Modify;
			figureName = moveFigureMessage.headers.linkname_Modify;
			
			
			//			do the procession of the  changing the CanvasXML from the message
			for (var i:int=0; i < moveFigureXML.child("Figure").length(); i++)
			{
				if(moveFigureXML.Figure[i].@type=="Link")
				{
						if(moveFigureXML.Figure[i].@ID==labelID)
						{
							moveFigureXML.Figure[i].@labelwidth=labelwidth;
							moveFigureXML.Figure[i].@labelheight=labelheight;
							moveFigureXML.Figure[i].@labelx=labelx;
							moveFigureXML.Figure[i].@labely=labely;
							moveFigureXML.Figure[i].@figureName=figureName;
							moveFigureXML.Figure[i].Attributes[0].Attribute[2] = figureName;
						}
				}
			}
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperatexml=moveFigureXML;
		}
	}
}