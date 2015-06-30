package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.messaging.events.MessageEvent;
	import mx.messaging.messages.AsyncMessage;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	import mx.states.*;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.state.*;
	import org.act.od.impl.viewhelper.DesignerToolBarVH;

	/**
	 * Operate the information for sending the message
	 * @author mengsong
	 */

	public class CoSendMessageCollectionCMD extends AODCommand
	{
		public function CoSendMessageCollectionCMD()
		{
			super();
		}
		public var message:AsyncMessage=new AsyncMessage();
		
		internal var operationType:String = new String();
		override public function execute(event:OrDesignerEvent):void
		{
			operationType =  event.data.message_move.headers.modify_type;
//			detect the type of the operationg:ADD,Delete,or Modify,then choose the init function
			var moveOperationArray:Array = OrDesignerModelLocator.getInstance().getOrchestraDesigner().moveOperationArray;
			var deleteOperationArray:Array = OrDesignerModelLocator.getInstance().getOrchestraDesigner().deleteOperationArray;
			var addOperationArray:Array = OrDesignerModelLocator.getInstance().getOrchestraDesigner().addOperationArray;
			//			whether is move operation
			if(moveOperationArray.indexOf(operationType)!=-1)
				init4Modify(event);
//			whether is delete operation
			else if(deleteOperationArray.indexOf(operationType)!=-1)
				init4Delete(event);
//			whether is add operation	
			else if(addOperationArray.indexOf(operationType)!=-1)
				init4Add(event);
			
			//Cooperation switch detection
			if (OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState == true)
			{
				generalCooperationVariableCollection();
				signModeCooperationVariableCollection();
				compareModeCooperationVariableCollection();
				getMD5();
				sendMessage();
			}
		}

		private function generalCooperationVariableCollection():void
		{
			var figureEditorNavigatorModel:FigureEditorNavigatorModel;
			figureEditorNavigatorModel=OrDesignerModelLocator.getInstance().figureEditorNavigatorModel;
			figureEditorNavigatorModel.activeFigureEditorModel.saveCanvasXML();
			var tempid:String=OrDesignerModelLocator.getInstance().getOrchestraDesigner().FileID;
			message.headers.fileID=OrDesignerModelLocator.getInstance().getOrchestraDesigner().FileID;
			message.headers.name=OrDesignerModelLocator.getInstance().getOrchestraDesigner().Name;
			message.headers.path=OrDesignerModelLocator.getInstance().getOrchestraDesigner().Path;
		}

		private function signModeCooperationVariableCollection():void
		{
			//sign mode cooperation
			var tempArray:Array=new Array();
			tempArray=OrDesignerModelLocator.getInstance().getOrchestraDesigner().SignModeArray;
			var tempXArray:Array=new Array();
			var tempYArray:Array=new Array();
			var tempWidthArray:Array=new Array();
			var tempHeightArray:Array=new Array();
			var figureID:Array=new Array();

			for (var i:int=0; i < tempArray.length; i++)
				if (AbstractFigure(tempArray[i]) is ConnectionFigure)
					figureID.push(AbstractFigure(tempArray[i]).outputAllInformation());

			for (i=0; i < tempArray.length; i++)
			{
				if ((tempArray[i] as IFigure) is ConnectionFigure)
					continue;
				tempXArray.push(AbstractFigure(tempArray[i]).getx());
				tempYArray.push(AbstractFigure(tempArray[i]).gety());
				tempWidthArray.push(AbstractFigure(tempArray[i]).getwidth());
				tempHeightArray.push(AbstractFigure(tempArray[i]).getheight());
			}
			message.headers.x=tempXArray.join(",");
			message.headers.y=tempYArray.join(",");
			message.headers.width=tempWidthArray.join(",");
			message.headers.height=tempHeightArray.join(",");
			message.body.figureID=figureID;
			message.headers.DrawConnectionOnlyState=OrDesignerModelLocator.getInstance().getOrchestraDesigner().DrawConnectionOnlyState;
		}

		private function compareModeCooperationVariableCollection():void
		{
			var tempCompareXML:XML=new XML();
			if (OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp[OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp.length - 1] != null)
				tempCompareXML=OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp[OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp.length - 2] as XML;
			message.headers.comparexml=tempCompareXML;
		}

		public function sendMessage():void
		{
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperationMessage=message;
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().sendMessage();
		}
		
		private function getMD5():void
		{
			var md5:TranslateToMD5CMD=new TranslateToMD5CMD();
			message.headers.MD5=md5.getMD5(operationType);
		}
		
//		init variables for Modify
		private function init4Modify(event:OrDesignerEvent):void
		{
			this.message = event.data.message_move as AsyncMessage;
		}
//		init variables for Delete
		private function init4Delete(event:OrDesignerEvent):void
		{
			this.message = event.data.message_move as AsyncMessage;
		}
//		init variables for Add
		private function init4Add(event:OrDesignerEvent):void
		{
			this.message = event.data.message_move as AsyncMessage;
		}
	}
}