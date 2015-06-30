package org.act.od.impl.state{
	
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	/**
	 * ChangeSizeState
	 * 
	 *  @author Mengsong
	 * 
	 */
	public class ChangeSizeState extends AbstractState {
		
		private var objFigure :IFigure;
		private var direction :int;
		private var canvasXML:XML;
		
		public function ChangeSizeState(obj :IFigure ,dir :int){
			super();
			this.objFigure=obj;
			this.direction=dir;
		}
		
		override public function mouseUp(point :Point, event :MouseEvent) :void{
			
			var resizedFigure : IFigure = this.objFigure;
			var direction : int = this.direction;
			var point : Point = point;
			
//			get the new canvasxml
			this.getXML();
			new FigureCanvasAppEvent(FigureCanvasAppEvent.CHANGE_FIGURE_SIZE_IN_CANVAS,
				{resizedFigure:resizedFigure, direction:direction, point:point,canvasXML:this.canvasXML}).dispatch();
		}
		
		override public function mouseMove(point:Point,event:MouseEvent):void{
			objFigure.autosetsize(point.x,point.y,direction);
			objFigure.updateDraw();
			objFigure.hideContextPanel();
		}
		private function getXML():void
		{
			this.canvasXML = ProcessFigure(OrDesignerModelLocator.getInstance().figureEditorNavigatorModel.activeFigureEditorModel.rootFigure)
				.outputAllInformation();
		}
		
	}
}