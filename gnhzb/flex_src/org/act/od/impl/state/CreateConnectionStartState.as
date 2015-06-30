package org.act.od.impl.state{
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.managers.CursorManager;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.cursor.LinkCursor;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.GraphicalFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.model.ToolPanelModel;
	import org.act.od.impl.view.FigureEditor;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	
	/**
	 * CreateConnectionStartState
	 * 
	 *  @author Likexin
	 *
	 */
	public class CreateConnectionStartState extends AbstractState{
		
		
		
		private var toolPanelModel : ToolPanelModel;
		private var feNavModel :FigureEditorNavigatorModel;
		
		
		
		public function CreateConnectionStartState(){
			super();
			this.toolPanelModel = OrDesignerModelLocator.getInstance().getToolPanelModel();
			this.feNavModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			
		}
		
		
		override public function mouseDown(point:Point, event:MouseEvent):void{
			
			var feVH :FigureEditorVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(feNavModel.activeFigureEditorModel.fileID)) as FigureEditorVH;
			
			
			CursorManager.removeAllCursors();
			var ox : Number = point.x;
			var oy : Number = point.y;
			var startFigureT : IFigure = feNavModel.activeFigureEditorModel.rootFigure.getupperfigure(ox,oy);
			
			var connection : ConnectionFigure = ConnectionFigure(toolPanelModel.selectedFigure);
			
			
			var vl:ViewLocator;
			var fenVH:EditorNavigatorVH;
			if( startFigureT && (startFigureT is GraphicalFigure) ){
				new FigureCanvasAppEvent(FigureCanvasAppEvent.CREATE_CONNECTION_START,
					{startFigure :startFigureT} ).dispatch();
				
			}else{
				this.fcStateDomain.setFCActiveState(new SelectionState());
				
				
				vl=ViewLocator.getInstance();
				fenVH=EditorNavigatorVH(vl.getViewHelper(EditorNavigatorVH.VH_KEY));
				FigureEditor(fenVH.getSelectedChildOfNavigator()).toolPanel.resetAllSelections();
				
			}
		}
		
		override public function mouseMove(point:Point, event:MouseEvent):void{
			var feVH :FigureEditorVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(feNavModel.activeFigureEditorModel.fileID)) as FigureEditorVH;
			
			
			//add new figure to figure canvas(VIEW)
			if(feVH == null)
				throw new Error("no binding with existed figure editor model");
			else {
				CursorManager.setCursor(LinkCursor);
			}
		}
		override public function RollOver(event :MouseEvent) :void{
			
			var feVH :FigureEditorVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(feNavModel.activeFigureEditorModel.fileID)) as FigureEditorVH;
			
			
			//add new figure to figure canvas(VIEW)
			if(feVH == null)
				throw new Error("no binding with existed figure editor model");
			else {
				CursorManager.setCursor(LinkCursor);
			}
		}
		override public function RollOut(event :MouseEvent) :void{
			
			var feVH :FigureEditorVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(feNavModel.activeFigureEditorModel.fileID)) as FigureEditorVH;
			
			CursorManager.removeAllCursors();
			
			
		}
		
	}
}