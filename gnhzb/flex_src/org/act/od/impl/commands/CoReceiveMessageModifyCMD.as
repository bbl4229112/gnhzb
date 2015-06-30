package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.CooperateOperationEvent;

	/**
	 * For receiving the messaging
	 *Dispatch the detail Command for modifying original xml file for cooperation
	 * @author Mengsong
	 *
	 */
	public class CoReceiveMessageModifyCMD extends AODCommand
	{
		public function CoReceiveMessageModifyCMD()
		{
			super();
		}
		override public function execute(event:OrDesignerEvent):void
		{
			var modifyFigureXML:XML = new XML();
			var modifyMessage:AsyncMessage = new AsyncMessage();
			
			modifyFigureXML = event.data.modifyFigureXML;
			modifyMessage = event.data.modifyMessage as AsyncMessage;
			
			if(modifyMessage.headers.modify_type=="move")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEMOVEFIGURE,
					{
						moveFigureXML:modifyFigureXML,
						moveFigureMessage:modifyMessage
					}
				).dispatch();
			
			else if(modifyMessage.headers.modify_type=="moveleft")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEMOVELEFT,
					{
						moveFigureXML:modifyFigureXML,
						moveFigureMessage:modifyMessage
					}
				).dispatch();
			
			else if(modifyMessage.headers.modify_type=="moveright")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEMOVERIGHT,
					{
						moveFigureXML:modifyFigureXML,
						moveFigureMessage:modifyMessage
					}
				).dispatch();
			
			else if(modifyMessage.headers.modify_type=="moveup")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEMOVEUP,
					{
						moveFigureXML:modifyFigureXML,
						moveFigureMessage:modifyMessage
					}
				).dispatch();
			
			else if(modifyMessage.headers.modify_type=="movedown")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEMOVEDOWN,
					{
						moveFigureXML:modifyFigureXML,
						moveFigureMessage:modifyMessage
					}
				).dispatch();
			
			else if(modifyMessage.headers.modify_type=="changesize")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGECHANGESIZE,
					{
						moveFigureXML:modifyFigureXML,
						moveFigureMessage:modifyMessage
					}
				).dispatch();
				
			else if(modifyMessage.headers.modify_type=="graphicifigure")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEGRAPHICIFIGURE,
					{
						moveFigureXML:modifyFigureXML,
						moveFigureMessage:modifyMessage
					}
				).dispatch();
			else if(modifyMessage.headers.modify_type=="changelinklabel")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGECHANGELINKLABEL,
					{
						moveFigureXML:modifyFigureXML,
						moveFigureMessage:modifyMessage
					}
				).dispatch();
		}
	}
}
