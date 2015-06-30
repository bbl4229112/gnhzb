package org.act.od.impl.commands
{
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.CooperateOperationEvent;
	
	
	/**
	 * For receiving the messaging
	 *	Choose the detail Delete Command for cooperation
	 * @author Mengsong
	 *
	 */
	public class CoReceiveMessageDeleteCMD extends AODCommand
	{
		public function CoReceiveMessageDeleteCMD()
		{
			super();
		}
		override public function execute(event:OrDesignerEvent):void
		{
			var deleteFigureXML:XML = new XML();
			var deleteMessage:AsyncMessage = new AsyncMessage();
			
			deleteFigureXML = event.data.deleteFigureXML;
			deleteMessage = event.data.deleteMessage as AsyncMessage;
			
			if(deleteMessage.headers.delete_type=="figuredelete")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEFIGUREDELETE,
					{
						deleteFigureXML:deleteFigureXML,
						deleteFigureMessage:deleteMessage
					}
				).dispatch();
			
			else if(deleteMessage.headers.delete_type=="linkdelete")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGELINKDELETE,
					{
						deleteFigureXML:deleteFigureXML,
						deleteFigureMessage:deleteMessage
					}
				).dispatch();
		}
	}
}