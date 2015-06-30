package org.act.od.impl.commands
{
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;

	/**
	 * For receiving the messaging
	 *Dispatch the detail Command for modifying original xml file for cooperation
	 * Cooperation for moving up
	 * @author Mengsong
	 *
	 */
	public class CoReceiveMessageMoveUpCMD extends AODCommand
	{
		public function CoReceiveMessageMoveUpCMD()
		{
			super();
		}
		override public function execute(event:OrDesignerEvent):void
		{
			//			init for Attributes of the Figures
			var moveFigureXML:XML = new XML();
			var moveFigureMessage:AsyncMessage = new AsyncMessage();
			var idArray_Move:Array = new Array();
			var xLinkArray_Move:Array = new Array();
			var yLinkArray_Move:Array = new Array();
			var rxLinkArray_Move:Array = new Array();
			var ryLinkArray_Move:Array = new Array();
			var widthLinkArray_Move:Array = new Array();
			var heightLinkArray_Move:Array = new Array();
			var tailXLinkArray_Move:Array = new Array();
			var tailYLinkArray_Move:Array = new Array();
			var headXLinkArray_Move:Array = new Array();
			var headYLinkArray_Move:Array = new Array();
			var IDLinkArray_Move:Array = new Array();
			var lineStartPointX_Move:Array = new Array();		
			var lineStartPointY_Move:Array = new Array();
			var lineEndPointX_Move:Array = new Array();
			var lineEndPointY_Move:Array = new Array();
			var headPointX_Move:Array = new Array();
			var headPointY_Move:Array = new Array();
			var leftPointX_Move:Array = new Array();
			var leftPointY_Move:Array = new Array();
			var rightPointX_Move:Array = new Array();
			var rightPointY_Move:Array = new Array();
			var sourcePointX_Move:Array = new Array();
			var sourcePointY_Move:Array = new Array();
			var targetPointX_Move:Array = new Array();
			var targetPointY_Move:Array = new Array();
			var sequence0X_Move:Array = new Array();
			var sequence0Y_Move:Array = new Array();
			var sequence1X_Move:Array = new Array();
			var sequence1Y_Move:Array = new Array();
			
			//			get the value from the event
			moveFigureXML = event.data.moveFigureXML as XML;
			moveFigureMessage = event.data.moveFigureMessage as AsyncMessage;
			
			idArray_Move = moveFigureMessage.headers.modify_id.split(",");
			xLinkArray_Move = moveFigureMessage.headers.modify_xLink.split(",");
			yLinkArray_Move = moveFigureMessage.headers.modify_yLink.split(",");
			rxLinkArray_Move = moveFigureMessage.headers.modify_rxLink.split(",");
			ryLinkArray_Move = moveFigureMessage.headers.modify_ryLink.split(",");
			widthLinkArray_Move = moveFigureMessage.headers.modify_widthLink.split(",");
			heightLinkArray_Move = moveFigureMessage.headers.modify_heigthLink.split(",");
			tailXLinkArray_Move = moveFigureMessage.headers.modify_tailxLink.split(",");
			tailYLinkArray_Move = moveFigureMessage.headers.modify_tailyLink.split(",");
			headXLinkArray_Move = moveFigureMessage.headers.modify_headxLink.split(",");
			headYLinkArray_Move = moveFigureMessage.headers.modify_headyLink.split(",");
			IDLinkArray_Move = moveFigureMessage.headers.modify_idLink.split(",");
			lineStartPointX_Move = moveFigureMessage.headers.modify_lineStartPointX.split(",");
			lineStartPointY_Move = moveFigureMessage.headers.modify_lineStartPointY.split(",");
			lineEndPointX_Move = moveFigureMessage.headers.modify_lineEndPointX.split(",");
			lineEndPointY_Move = moveFigureMessage.headers.modify_lineEndPointY.split(",");
			headPointX_Move = moveFigureMessage.headers.modify_headPointX.split(",");
			headPointY_Move = moveFigureMessage.headers.modify_headPointY.split(",");
			leftPointX_Move = moveFigureMessage.headers.modify_leftPointX.split(",");
			leftPointY_Move = moveFigureMessage.headers.modify_leftPointY.split(",");
			rightPointX_Move = moveFigureMessage.headers.modify_rightPointX.split(",");
			rightPointY_Move = moveFigureMessage.headers.modify_rightPointY.split(",");
			sourcePointX_Move = moveFigureMessage.headers.modify_sourcePointX.split(",");
			sourcePointY_Move = moveFigureMessage.headers.modify_sourcePointY.split(",");
			targetPointX_Move = moveFigureMessage.headers.modify_targetPointX.split(",");
			targetPointY_Move = moveFigureMessage.headers.modify_targetPointY.split(",");
			sequence0X_Move = moveFigureMessage.headers.modify_sequence0X.split(",");
			sequence0Y_Move = moveFigureMessage.headers.modify_sequence0Y.split(",");
			sequence1X_Move = moveFigureMessage.headers.modify_sequence1X.split(",");
			sequence1Y_Move = moveFigureMessage.headers.modify_sequence1Y.split(",");
			
			//			do the procession of the  changing the CanvasXML from the message
			for (var i:int=0; i < moveFigureXML.child("Figure").length(); i++)
			{
				if(moveFigureXML.Figure[i].@type=="Link")
				{
					for(var k:int = 0;k<IDLinkArray_Move.length;k++)
						if(moveFigureXML.Figure[i].@ID==IDLinkArray_Move[k])
						{
							moveFigureXML.Figure[i].@x=xLinkArray_Move[k];
							moveFigureXML.Figure[i].@y=yLinkArray_Move[k];
							moveFigureXML.Figure[i].@rx=rxLinkArray_Move[k];
							moveFigureXML.Figure[i].@ry=ryLinkArray_Move[k];
							moveFigureXML.Figure[i].@width=widthLinkArray_Move[k];
							moveFigureXML.Figure[i].@height=heightLinkArray_Move[k];
							moveFigureXML.Figure[i].@tailX=tailXLinkArray_Move[k];
							moveFigureXML.Figure[i].@tailY=tailYLinkArray_Move[k];
							moveFigureXML.Figure[i].@headX=headXLinkArray_Move[k];
							moveFigureXML.Figure[i].@headY=headYLinkArray_Move[k];
							moveFigureXML.Figure[i].@lineStartPointX=lineStartPointX_Move[k];
							moveFigureXML.Figure[i].@lineStartPointY=lineStartPointY_Move[k];
							moveFigureXML.Figure[i].@lineEndPointX=lineEndPointX_Move[k];
							moveFigureXML.Figure[i].@lineEndPointY=lineEndPointY_Move[k];
							moveFigureXML.Figure[i].@headPointX=headPointX_Move[k];
							moveFigureXML.Figure[i].@headPointY=headPointY_Move[k];
							moveFigureXML.Figure[i].@leftPointX=leftPointX_Move[k];
							moveFigureXML.Figure[i].@leftPointY=leftPointY_Move[k];
							moveFigureXML.Figure[i].@rightPointX=rightPointX_Move[k];
							moveFigureXML.Figure[i].@rightPointY=rightPointY_Move[k];
							moveFigureXML.Figure[i].@sourcePointX=sourcePointX_Move[k];
							moveFigureXML.Figure[i].@sourcePointY=sourcePointY_Move[k];
							moveFigureXML.Figure[i].@targetPointX=targetPointX_Move[k];
							moveFigureXML.Figure[i].@targetPointY=targetPointY_Move[k];
							
							moveFigureXML.Figure[i].router[0].pathpoints[0].point[0].@x=sequence0X_Move[k];
							moveFigureXML.Figure[i].router[0].pathpoints[0].point[0].@y=sequence0Y_Move[k];
							moveFigureXML.Figure[i].router[0].pathpoints[0].point[1].@x=sequence1X_Move[k];
							moveFigureXML.Figure[i].router[0].pathpoints[0].point[1].@y=sequence1Y_Move[k];
						}
				}
				else
				{
					for(var j:int = 0;j<idArray_Move.length;j++)
						if (moveFigureXML.Figure[i].@ID == idArray_Move[j])
						{	
							moveFigureXML.Figure[i].@y=(int(moveFigureXML.Figure[i].@y) - 5).toString();
							moveFigureXML.Figure[i].@ry=(int(moveFigureXML.Figure[i].@ry) - 5).toString();
						}
				}
			}
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperatexml=moveFigureXML;
		}
	}
}