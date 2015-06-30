package org.act.od.impl.state{
	
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.net.SharedObject;
	
	import mx.containers.Canvas;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.model.ToolPanelModel;
	import org.act.od.impl.view.FigureCanvas;
	import org.act.od.impl.view.LevelModel;
	import org.act.od.impl.view.OrchestraDesigner;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.DesignerToolBarVH;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import mx.graphics.ImageSnapshot;
	
	/**
	 * when click figure of the toolPanel, then switch to this state
	 * 
	 *  @author Likexin
	 * 
	 */
	public class CreationState extends AbstractState{
		
		private var toolPanelModel :ToolPanelModel;
		
		private var feNavModel :FigureEditorNavigatorModel;
		
		private var figureEditorNavigatorModel :FigureEditorNavigatorModel;
		public function CreationState(){
			super();
			this.toolPanelModel = OrDesignerModelLocator.getInstance().getToolPanelModel();
			this.feNavModel = 
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			
			
			figureEditorNavigatorModel = OrDesignerModelLocator.getInstance().figureEditorNavigatorModel;
		}
		
		/**
		 * 
		 * @param point
		 * @param event
		 * 
		 */
		override public function mouseDown(point :Point, event :MouseEvent) :void{
			this.fcStateDomain.setFCActiveState(new CreatingState());
			this.fcStateDomain.mouseDown(point,event);
			
		}
		
		
		
		/**
		 * 
		 * @param point
		 * @param event
		 * 
		 */
		//		override public function mouseUp(point :Point, event :MouseEvent) :void{
		
		//			this.fcStateDomain.setFCActiveState(new CreatingState());
		//			this.fcStateDomain.mouseDown(point,event);
		//			
		//			var orchestraDesigner:OrchestraDesigner = OrDesignerModelLocator.getInstance().getOrchestraDesigner();
		//			var level:LevelModel = orchestraDesigner.levelModel;
		//			
		//			var canvas:Canvas = level.canvas.getChildAt(level.canvas.numChildren - 1) as Canvas;
		//			
		//			var canvas2:Canvas = canvas.getChildAt(canvas.numChildren - 1) as Canvas;
		
		
		//		}
		
		/**
		 * 
		 * @param point
		 * @param event
		 * 
		 */
		override public function mouseMove(point :Point,event :MouseEvent):void{
			
			var x:Number=point.x;
			var y:Number=point.y;
			
			var absFigure:AbstractFigure = AbstractFigure(toolPanelModel.selectedFigure);
			
			var can:Canvas = event.currentTarget as Canvas;
			//			var fi:AbstractFigure = can.getChildAt(0) as AbstractFigure;
			var feNavModel :FigureEditorNavigatorModel =
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			var fem:FigureEditorModel = feNavModel.activeFigureEditorModel.rootModel;
			//			var xml:XML = fem._canvasXML;
			fem.updateCanvasXML();
			
			if(absFigure){ //redraw the figure
				//				absFigure.drawclear();
				absFigure.setposition(x,y);
				//				absFigure.drawpicture();
				absFigure.updateDraw();
			}
		}
		
		/**
		 * 
		 * @param event
		 * 
		 */
		//		 !!!!!!seem to be same as mouse move
		override public function RollOver(event :MouseEvent) :void{
			
			var feVH :FigureEditorVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(feNavModel.activeFigureEditorModel.fileID)) as FigureEditorVH;
			
			var absFigure:AbstractFigure = AbstractFigure(toolPanelModel.selectedFigure);
			
			//add new figure to figure canvas(VIEW)
			if(feVH == null)
				throw new Error("no binding with existed figure editor model");
			else {
				feVH.addFigureToCanvas(absFigure.getuic());
			}
		}
		
		
		/**
		 * 
		 * @param event
		 * 
		 */
		override public function RollOut(event :MouseEvent) :void{
			
			var feVH :FigureEditorVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(feNavModel.activeFigureEditorModel.fileID)) as FigureEditorVH;
			
			feVH.removeFiguresFromCanvas( DisplayObject(toolPanelModel.selectedFigure) );
			
			//if rollOut, then out of this state
			OrDesignerModelLocator.getInstance().getFigureCanvasStateDomain().setFCActiveState(new SelectionState());
			
		}
		
	}
}