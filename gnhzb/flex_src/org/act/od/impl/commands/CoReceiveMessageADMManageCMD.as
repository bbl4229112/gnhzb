package org.act.od.impl.commands
{
	import flash.events.AsyncErrorEvent;
	
	import mx.controls.Alert;
	import mx.messaging.events.MessageFaultEvent;
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.CooperateOperationEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;

	/**
	 * For receiving the messaging
	 *Dispatch the Add,Delete,or Modify  Command for the original xml file for cooperation
	 * @author Mengsong
	 *
	 */
	public class CoReceiveMessageADMManageCMD extends AODCommand
	{
		public function CoReceiveMessageADMManageCMD()
		{
			super();
		}
		private var figureXml:XML=new XML();
		private var message:AsyncMessage=new AsyncMessage();

		/**
		 *
		 * @param event { fileID, filePath, fileName, bpelEditorModel }
		 *
		 */
		override public function execute(event:OrDesignerEvent):void
		{
			figureXml=event.data.figureXML;
			message=event.data.CooperationMessage as AsyncMessage;
			var moveOperationArray:Array = OrDesignerModelLocator.getInstance().getOrchestraDesigner().moveOperationArray;
			var deleteOperationArray:Array = OrDesignerModelLocator.getInstance().getOrchestraDesigner().deleteOperationArray;
			var addOperationArray:Array = OrDesignerModelLocator.getInstance().getOrchestraDesigner().addOperationArray;
			var operationType:String = new String();
			var md5code:String = new String();
			operationType = message.headers.modify_type.toString();
			md5code = this.getMD5(operationType);
			if(md5code == message.headers.MD5)
			{
				//			whether is moveOperation
				if(moveOperationArray.indexOf(operationType)!=-1)
				{
					message.headers.modify_type = operationType;
					new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEMODIFY,
																	{
																		modifyFigureXML:this.figureXml,
																		modifyMessage:this.message
																	}
																	).dispatch();
				}
				//			whether is deleteOperation
				else if(deleteOperationArray.indexOf(operationType)!=-1)
				{
					message.headers.delete_type = operationType;
					new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEDELETE,
						{
							deleteFigureXML:this.figureXml,
							deleteMessage:this.message
						}
					).dispatch();
				}
				//			whether is deleteOperation
				else if(addOperationArray.indexOf(operationType)!=-1)
				{
					message.headers.add_type = operationType;
					new CooperateOperationEvent(CooperateOperationEvent.CORECEIVEMESSAGEADD,
						{
							addFigureXML:this.figureXml,
							addMessage:this.message
						}
					).dispatch();
				}
			}
			else
			{
//				reSend the message
				message.headers.errorCode = "1";
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().producer.send(message);
			}
		}
		private function getMD5(operationType:String):String
		{
			var md5:TranslateToMD5CMD = new TranslateToMD5CMD();
			return md5.getMD5(operationType);
		}
	}
}
