package org.act.od.impl.state{
	
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.controls.Alert;
	import mx.containers.Canvas;
	
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.model.AttributeViewModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	/**
	 * 
	 *  @author Likexin
	 * 
	 */	
	
	public class MovingState extends AbstractState{
		
		private var fx:Number=0;
		private var fy:Number=0;
		
		private var attributeViewModel :AttributeViewModel;
		private var figureENModel :FigureEditorNavigatorModel;
		
		
		private var oldPosition :Array = new Array();
		private var canvasXML:XML = new XML();
		public function MovingState(){
			super();
			
			this.attributeViewModel = 
				OrDesignerModelLocator.getInstance().getAttributeViewModel();
			
			this.figureENModel = 
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			var selectedFigures :Array;
			selectedFigures = figureENModel.activeFigureEditorModel.selectedFigures;
			var leng :int = figureENModel.activeFigureEditorModel.selectedFigures.length;
			for(var i :int = 0; i < leng ; i++){
				oldPosition.push({x : IFigure(selectedFigures[i]).getx(),y : IFigure(selectedFigures[i]).gety()});
			}
		}
		
		/**
		 * 
		 * @param point
		 * @param event
		 * 
		 */
		override public function mouseDown(point:Point,event:MouseEvent):void{
			var tempArr:Array = figureENModel.activeFigureEditorModel.selectedFigures;
			
			var i:int;
			for(i=0;i<tempArr.length;i++){
				IFigure(tempArr[i]).setIsShowContextPanel(false);
			}
			fx = point.x;
			fy = point.y;
			
		}
		
		/**
		 * 
		 * @param point
		 * @param event
		 * 
		 */
		override public function mouseUp(point:Point,event:MouseEvent):void{
			var i:int;
			var tempx:int;
			var tempy:int;
			
			var tempArr:Array = new Array();
			tempArr = figureENModel.activeFigureEditorModel.selectedFigures;
			this.canvasXML = figureENModel.activeFigureEditorModel.rootFigure.outputAllInformation();
			if(tempArr.length==1){
				IFigure(tempArr[0]).setIsShowContextPanel(true);
				IFigure(tempArr[0]).updateDraw();
			}
			else{
				for(i=0;i<tempArr.length;i++){
					IFigure(tempArr[i]).setIsShowContextPanel(false);
					IFigure(tempArr[i]).updateDraw();
				}
			}
			
			tempx = (tempArr[0].x-this.oldPosition[0].x) as int;
			tempy = (tempArr[0].y-this.oldPosition[0].y) as int;
			//			if(int(tempArr[0].x-this.oldPosition[0].x)>0||int(tempArr[0].x-this.oldPosition[0].x)<0||
			//				int(tempArr[0].y-this.oldPosition[0].y)>0||int(tempArr[0].y-this.oldPosition[0].y)<0)
			if(tempx!=0||tempy!=0)
				new FigureCanvasAppEvent(FigureCanvasAppEvent.FIGURE_MOVE_IN_CANVAS,
					{movedFigures :tempArr, oldPosition : this.oldPosition,canvasXML : this.canvasXML}).dispatch();
			this.fcStateDomain.setFCActiveState(new SelectionState());
		}
		
		/**
		 * 
		 * @param point
		 * @param event
		 * 
		 */
		/*override public function mouseMove(point:Point,event:MouseEvent):void{  //Web端不需要动，20120907.
			
			var tempArr:Array;
			var tempIFigure:IFigure;
			var dx:Number;
			var dy:Number;
			var x:Number=point.x;
			var y:Number=point.y;
			
			tempArr = figureENModel.activeFigureEditorModel.selectedFigures;
			
			dx=x-fx;
			dy=y-fy;
			
			for(var i:int=0; i<tempArr.length; i++){
				
				tempIFigure=IFigure(tempArr[i]);
				if(tempIFigure is ConnectionFigure){
					continue;
				}
				
				tempIFigure.setposition(tempIFigure.getdrawx()+dx,tempIFigure.getdrawy()+dy);
				tempIFigure.updateDraw();
			}
			fx=x;
			fy=y;
			//			var can:Canvas = event.currentTarget as Canvas;
			//			//			var fi:AbstractFigure = can.getChildAt(0) as AbstractFigure;
			//			var feNavModel :FigureEditorNavigatorModel =
			//				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			//			var fem:FigureEditorModel = feNavModel.activeFigureEditorModel.rootModel;
			//			//			var xml:XML = fem._canvasXML;
			//			fem.updateCanvasXML();
		}*/
	}
}