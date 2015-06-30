package org.act.od.impl.commands
{
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.CooperateOperationEvent;
	
	
	/**
	 * For receiving the messaging
	 *	Choose the detail Add Command for cooperation
	 * @author Mengsong
	 *
	 */
	
	public class CoReceiveMessageAddCMD extends AODCommand
	{
		public function CoReceiveMessageAddCMD()
		{
			super();
		}
		override public function execute(event:OrDesignerEvent):void
		{
			var addFigureXML:XML = new XML();
			var addMessage:AsyncMessage = new AsyncMessage();
			
			addFigureXML = event.data.addFigureXML;
			addMessage = event.data.addMessage as AsyncMessage;
			
			if(addMessage.headers.add_type=="create")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGECREATE,
					{
						addFigureXML:addFigureXML,
						addFigureMessage:addMessage
					}
				).dispatch();
			else if(addMessage.headers.add_type=="paste")
				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEPASTE,
					{
						addFigureXML:addFigureXML,
						addFigureMessage:addMessage
					}
				).dispatch();
//			else if(addMessage.headers.delete_type=="linkdelete")
//				new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGELINKDELETE,
//					{
//						addFigureXML:addFigureXML,
//						addFigureMessage:addMessage
//					}
//				).dispatch();
		}
	}
}