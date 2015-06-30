package org.act.od.impl.state
{
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import org.act.od.impl.model.FigureCanvasStateDomain;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	/**
	 * 
	 * @author Likexin
	 * 
	 */
	public class AbstractState implements IState{
		
		protected var fcStateDomain:FigureCanvasStateDomain;
		
		public function AbstractState(){
			this.fcStateDomain = OrDesignerModelLocator.getInstance().getFigureCanvasStateDomain();
		}
		
		
		public function mouseDoubleClick(point:Point,event:MouseEvent):void{
		}
		
		public function mouseClick(point:Point,event:MouseEvent):void{
		}
		
		public function mouseDown(point:Point,event:MouseEvent):void{
		}
		
		public function mouseUp(point:Point,event:MouseEvent):void{
			//			var feNavModel :FigureEditorNavigatorModel =
			//				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			//			var fem:FigureEditorModel = feNavModel.activeFigureEditorModel.rootModel;
			//			//			var xml:XML = fem._canvasXML;
			//			fem.updateCanvasXML();
			
		}
		public function mouseMove(point:Point,event:MouseEvent):void{
			//			var can:Canvas = event.currentTarget as Canvas;
			//			var fi:AbstractFigure = can.getChildAt(0) as AbstractFigure;
			var feNavModel :FigureEditorNavigatorModel =
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			var fem:FigureEditorModel = feNavModel.activeFigureEditorModel;
			//			var xml:XML = fem._canvasXML;
			//			fem.updateCanvasXML();
			var fm:FigureEditorModel = fem.fatherModel;
			faup(fm);
			//			trace(xml);
		}
		public function faup(fm:FigureEditorModel):void{
			
			if(fm != null){
				fm.updateCanvasXML();
				fm = fm.fatherModel;
				faup(fm);
			}
		}
		
		public function keyDown(event:KeyboardEvent):void{
		}
		
		public function keyUp(event:KeyboardEvent):void{
		}
		
		public function RollOut(event:MouseEvent):void{
		}
		
		public function RollOver(event:MouseEvent):void{
		}
		
	}
}