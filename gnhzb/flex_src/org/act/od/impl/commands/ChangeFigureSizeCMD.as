package org.act.od.impl.commands
{
	import flash.geom.Point;
	
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
	 * Change the size of the selected figure.
	 * 
	 * @ author Mengsong
	 * 
	 */
	public class ChangeFigureSizeCMD extends AODCommand{
		
//		private var movedFigures:Array;
//		private var oldPosition:Array;
		private var canvasXML:XML;
		private var resizedFigure :IFigure;
		//	for new cooperation
		private var xArray_Move:Array=new Array();
		private var yArray_Move:Array=new Array();
		private var rxArray_Move:Array=new Array();
		private var ryArray_Move:Array=new Array();
		private var widthArray_Move:Array = new Array();
		private var heightArray_Move:Array = new Array();
		private var standardWidth_Move:Array = new Array();
		private var idArray_Move:Array=new Array();
		
		private var asymessage:AsyncMessage=new AsyncMessage();
		private var operationtype:String=new String();

		public function ChangeFigureSizeCMD(){
			super();
		}
		
		/**
		 * 
		 * @param event {resizedFigure, direction, point}
		 * 
		 */
		override public function execute(event : OrDesignerEvent) :void{
			//resize current figure	
			resizedFigure  = event.data.resizedFigure as IFigure;
			this.canvasXML=event.data.canvasXML;
			var direction :int = event.data.direction as int;
			var point :Point = event.data.point as Point;
			
			resizedFigure.autosetsize(point.x,point.y,direction);
			resizedFigure.updateDraw();
			
			OrDesignerModelLocator.getInstance().getFigureCanvasStateDomain().setFCActiveState(new SelectionState());
			
			//			added by mengsong
			//			cooperation state is true
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==true)
			{
				this.prepair4cooperation();
				this.doCooperation();
			}
		}
		private function prepair4cooperation():void
		{
			var i:int=0;
			this.operationtype="changesize";
			//			get the basic attributes of the figure except the Link FIgure
				idArray_Move.push(this.resizedFigure.getID());
				xArray_Move.push(this.resizedFigure.getx());
				yArray_Move.push(this.resizedFigure.gety());
				rxArray_Move.push(this.resizedFigure.getrx());
				ryArray_Move.push(this.resizedFigure.getry());
				widthArray_Move.push(this.resizedFigure.getwidth());
				heightArray_Move.push(this.resizedFigure.getheight());
				standardWidth_Move.push(this.resizedFigure.getstandardwidth());
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
				asymessage.headers.modify_width=widthArray_Move.join(",");
				asymessage.headers.modify_height=heightArray_Move.join(",");
				asymessage.headers.modify_standardWidth=standardWidth_Move.join(",");
				asymessage.headers.modify_type=operationtype;
				
				new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION, {message_move: asymessage}).dispatch();
			}
		}
	}
}