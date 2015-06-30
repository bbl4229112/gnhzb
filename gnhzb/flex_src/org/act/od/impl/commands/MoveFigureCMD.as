package org.act.od.impl.commands
{

	import mx.controls.Alert;
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.CooperateOperationEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.state.SelectionState;

	/**
	 *
	 * @author Mengsong
	 *
	 */


	public class MoveFigureCMD extends AODCommand
	{
		private var movedFigures:Array;
		private var oldPosition:Array;
		private var canvasXML:XML;

		//	for new cooperation
		private var xArray_Move:Array=new Array();
		private var yArray_Move:Array=new Array();
		private var rxArray_Move:Array=new Array();
		private var ryArray_Move:Array=new Array();
		private var idArray_Move:Array=new Array();

		private var asymessage:AsyncMessage=new AsyncMessage();
		private var operationtype:String=new String();

		/**
		 *
		 */
		public function MoveFigureCMD()
		{
			super();
		}

		/**
		 *
		 * @param event {movedFigures,oldPosition}
		 *
		 */

		/**
		 *
		 * @param event
		 */
		override public function execute(event:OrDesignerEvent):void
		{
			//resize current figure	
			this.movedFigures=event.data.movedFigures;
			this.movedFigures=this.movedFigures.concat();
			this.oldPosition=event.data.oldPosition;
			this.canvasXML=event.data.canvasXML;
			if (this.movedFigures == this.oldPosition)
				Alert.show("Same");

			var i:int=0;
			if (this.movedFigures.length == 1)
			{
				IFigure(this.movedFigures[0]).setIsShowContextPanel(true);
				IFigure(this.movedFigures[0]).updateDraw();
			}
//			when move multi figures
			else
			{
				for (i=0; i < this.movedFigures.length; i++)
				{
					IFigure(this.movedFigures[i]).setIsShowContextPanel(false);
					IFigure(this.movedFigures[i]).updateDraw();
				}
			}

			OrDesignerModelLocator.getInstance().getFigureCanvasStateDomain().setFCActiveState(new SelectionState());
//			cooperation state is true
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==true)
			{
				this.prepair4cooperation();
				this.doCooperation();
			}
		}

		override public function unExecute():void
		{
			for (var i:int=0; i < this.movedFigures.length; i++)
			{
				IFigure(this.movedFigures[i]).setposition(this.oldPosition[i].x, this.oldPosition[i].y);
			}
			for (i=0; i < movedFigures.length; i++)
			{
				IFigure(movedFigures[i]).updateDraw();
			}
		}

		override public function isReversible():Boolean
		{
			return true;
		}

		private function prepair4cooperation():void
		{
			var i:int=0;
			this.operationtype="move";
//			get the basic attributes of the figure except the Link FIgure
			for (i=0; i < this.movedFigures.length; i++)
			{
				idArray_Move.push(IFigure(this.movedFigures[i]).getID());
				xArray_Move.push(IFigure(this.movedFigures[i]).getx());
				yArray_Move.push(IFigure(this.movedFigures[i]).gety());
				rxArray_Move.push(IFigure(this.movedFigures[i]).getrx());
				ryArray_Move.push(IFigure(this.movedFigures[i]).getry());
			}
//			get the Attribute of the LinkFigure
			asymessage=OrDesignerModelLocator.getInstance().getOrchestraDesigner().getLinkAttribute(this.canvasXML, idArray_Move);
		}

		private function doCooperation():void
		{
			//			added by mengsong 
			//			For cooperate
			var activeFEModel:FigureEditorModel=OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
			if (activeFEModel != null)
			{
				asymessage.headers.modify_x=xArray_Move.join(",");
				asymessage.headers.modify_y=yArray_Move.join(",");
				asymessage.headers.modify_rx=rxArray_Move.join(",");
				asymessage.headers.modify_ry=ryArray_Move.join(",");
				asymessage.headers.modify_id=idArray_Move.join(",");
				asymessage.headers.modify_type=operationtype;

				new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION, {message_move: asymessage}).dispatch();
			}
		}
	}
}
