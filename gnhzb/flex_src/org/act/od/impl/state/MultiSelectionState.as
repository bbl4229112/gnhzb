package org.act.od.impl.state
{
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.core.UIComponent;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.model.AttributeViewModel;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	/**
	 * 
	 *  @author Likexin
	 * 
	 */	
	public class MultiSelectionState extends AbstractState{
		
		private var figureENModel :FigureEditorNavigatorModel;
		
		private var attributeViewModel :AttributeViewModel;
		
		private var feVH :FigureEditorVH;
		
		private var ox:Number=0;
		private var oy:Number=0;
		
		
		private var uic :UIComponent=new UIComponent();
		
		private var solidLength :Number=4;//every solid slide length
		private var dashedLength :Number=3;//every dashed slide length
		
		
		public function MultiSelectionState(){
			
			super();
			this.figureENModel = 
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			
			this.attributeViewModel = 
				OrDesignerModelLocator.getInstance().getAttributeViewModel();
			
			feVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(figureENModel.activeFigureEditorModel.fileID)) as FigureEditorVH;
			
			if(feVH == null)
				throw new Error("no binding figureEditorViewHelper with existed figure editor model");
			
		}
		
		
		override public function mouseDown(point:Point,event:MouseEvent):void{
			ox=point.x;
			oy=point.y;	
			feVH.addFigureToCanvas(this.uic);
		}
		
		
		override public function mouseUp(point:Point, event:MouseEvent):void{
			
			this.fcStateDomain.setFCActiveState(new SelectionState());
			
			feVH.removeFiguresFromCanvas(uic);
		}
		
		override public function mouseMove(point:Point, event:MouseEvent):void{
			figureENModel.activeFigureEditorModel.resetSelectedFigure();
			uic.graphics.clear();
			uic.x=point.x>ox?ox:point.x;
			uic.y=point.y>oy?oy:point.y;
			uic.width=Math.abs(point.x-ox);
			uic.height=Math.abs(point.y-oy);
			this.drawLine(uic,0,0,uic.width,0);
			this.drawLine(uic,0,0,0,uic.height);
			this.drawLine(uic,uic.width,0,uic.width,uic.height);
			this.drawLine(uic,0,uic.height,uic.width,uic.height);
			
			var arr:Array=new Array();
			var i:int;
			var rx:Number;
			var ry:Number;
			var selectedFigures:Array=figureENModel.activeFigureEditorModel.selectedFigures;
			figureENModel.activeFigureEditorModel.rootFigure.ifiguretoarray(arr);
			for(i=0;i<arr.length;i++){
				rx=IFigure(arr[i]).getrx();
				ry=IFigure(arr[i]).getry();
				if(rx>uic.x&&rx<uic.x+uic.width&&ry>uic.y&&ry<uic.y+uic.height){
					IFigure(arr[i]).ifiguretoarray(selectedFigures);
				}
			}
			for(i=0;i<selectedFigures.length;i++){
				IFigure(selectedFigures[i]).setSelected(true);
				IFigure(selectedFigures[i]).updateDraw();
			}
			
			feVH.addFigureToCanvas(this.uic);
		}
		
		
		public function createGapPoint(startPointX:Number,startPointY:Number,endPointX:Number,endPointY:Number):Array{
			var arr:Array=new Array();
			var i:int;
			var point:Point;
			var startPoint:Point=new Point();
			var endPoint:Point=new Point();
			startPoint.x=startPointX;
			startPoint.y=startPointY;
			endPoint.x=endPointX;
			endPoint.y=endPointY;
			var distance:Number;
			distance=Point.distance(startPoint,endPoint);
			var times:int=int(distance/(solidLength+dashedLength));
			var dx1:Number=(endPointX-startPointX)*solidLength/distance;
			var dx2:Number=(endPointX-startPointX)*dashedLength/distance;
			var dy1:Number=(endPointY-startPointY)*solidLength/distance;
			var dy2:Number=(endPointY-startPointY)*dashedLength/distance;
			for(i=0;i<times;i++){
				point=new Point(startPoint.x+i*(dx1+dx2),startPoint.y+i*(dy1+dy2));
				arr.push(point);
				point=new Point(startPoint.x+i*(dx1+dx2)+dx1,startPoint.y+i*(dy1+dy2)+dy1);
				arr.push(point);
			}
			arr.push(endPoint);
			return arr;
		}
		
		
		public function drawLine(sprt:Sprite,startPointX:Number,startPointY:Number,endPointX:Number,endPointY:Number):void{
			var arr:Array=createGapPoint(startPointX,startPointY,endPointX,endPointY);
			var i:int;
			for(i=0;i+1<arr.length;i=i+2){
				sprt.graphics.lineStyle(1,0x000000);
				sprt.graphics.moveTo(Point(arr[i]).x,Point(arr[i]).y);
				sprt.graphics.lineTo(Point(arr[i+1]).x,Point(arr[i+1]).y);
			}
		}
		override public function RollOut(event:MouseEvent):void{
			var activeFEModel :FigureEditorModel =  this.figureENModel.activeFigureEditorModel;
			if(activeFEModel != null){
				new FigureCanvasAppEvent(FigureCanvasAppEvent.Select_All,
					{fileID :activeFEModel.fileID} ).dispatch();
				OrDesignerModelLocator.getInstance().getFigureCanvasStateDomain().setFCActiveState(new SelectionState());
				feVH.removeFiguresFromCanvas(uic);
			}
		}
		
	}
}