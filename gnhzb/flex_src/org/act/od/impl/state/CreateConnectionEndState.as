package org.act.od.impl.state
{
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.ToolPanelAppEvent;
	import org.act.od.impl.figure.*;
	import org.act.od.impl.figure.bpmn.BpmnFigureFactory;
	import org.act.od.impl.figure.bpmn.MessageFlowLinkFigure;
	import org.act.od.impl.figure.bpmn.PortTypeFigure;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.model.ToolPanelModel;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;	
	
	/**
	 * CreateConnectionEndState
	 * 
	 *  @author Likexin
	 *
	 */
	public class CreateConnectionEndState extends AbstractState
	{
		
		private var toolPanelModel :ToolPanelModel;
		private var feNavModel :FigureEditorNavigatorModel;
		
		public function CreateConnectionEndState(){
			
			super();
			this.toolPanelModel = OrDesignerModelLocator.getInstance().getToolPanelModel();
			this.feNavModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
		}
		
		/**
		 *
		 * @param point
		 * @param event
		 *
		 */
		override public function mouseMove(point:Point,event:MouseEvent):void
		{
			var ox : Number = point.x;
			var oy : Number = point.y;
			var cf : ConnectionFigure = ConnectionFigure(toolPanelModel.selectedFigure);
			
			//repaint while dragging
			cf.drawclear();
			cf.setTailposition(ox,oy);
			
			var p:Point=new Point(ox,oy);
			
			if(cf.getStartFigure() is PortTypeFigure )
			{
				//cf.setposition(PortTypeFigure(cf.getStartFigure()).getEdgePointInPool(p).x,
				//	PortTypeFigure(cf.getStartFigure()).getEdgePointInPool(p).y);
				cf.setposition(PortTypeFigure(cf.getStartFigure()).getEdgePoint(p).x,
					PortTypeFigure(cf.getStartFigure()).getEdgePoint(p).y);
			}
			else
			{
				cf.setposition(GraphicalFigure(cf.getStartFigure()).getEdgePoint(p).x,
					GraphicalFigure(cf.getStartFigure()).getEdgePoint(p).y);
			}
			
			
			
			cf.drawpicture();
			
			//and the connection to canvas again, for rollout reason,
			var activeFEVHKey : String = FigureEditorVH.getViewHelperKey(feNavModel.activeFigureEditorModel.fileID);
			var activeFEVH : FigureEditorVH = ViewLocator.getInstance().getViewHelper(activeFEVHKey) as FigureEditorVH;
			activeFEVH.addFigureToCanvas( cf.getuic());
		}
		
		
		/**
		 *
		 * @param point
		 * @param event
		 *
		 */
		override public function mouseDown(point:Point,event:MouseEvent):void{
			
			var ox : Number = point.x;
			var oy : Number = point.y;
			var cf : ConnectionFigure = ConnectionFigure(toolPanelModel.selectedFigure);
			
			var endFigureT : IFigure = feNavModel.activeFigureEditorModel.rootFigure.getupperfigure(ox,oy);
			
			var activeFEVHKey : String = FigureEditorVH.getViewHelperKey(feNavModel.activeFigureEditorModel.fileID);
			var activeFEViewHelper : FigureEditorVH = ViewLocator.getInstance().getViewHelper(activeFEVHKey) as FigureEditorVH;
			
			if(endFigureT && (endFigureT is GraphicalFigure) && endFigureT != cf.getStartFigure()  ){
				
				
				
				if(endFigureT is PortTypeFigure)
				{
					if(cf is MessageFlowLinkFigure)
					{
						if(cf.getStartFigure() is PortTypeFigure )
						{
							cf.setStartFigure(null);
							activeFEViewHelper.removeFiguresFromCanvas(cf);
						}
						else
						{
							new FigureCanvasAppEvent(FigureCanvasAppEvent.CREATE_CONNECTION_END,
								{endFigure :endFigureT}).dispatch();
						}
						
					}
					else
					{
						cf.setStartFigure(null);
						activeFEViewHelper.removeFiguresFromCanvas(cf);
					}
				}
				else
				{
					
					if(cf is MessageFlowLinkFigure )
					{
						if(cf.getStartFigure() is PortTypeFigure)
						{
							new FigureCanvasAppEvent(FigureCanvasAppEvent.CREATE_CONNECTION_END,
								{endFigure :endFigureT}).dispatch();
						}
						else
						{
							cf.setStartFigure(null);
							activeFEViewHelper.removeFiguresFromCanvas(cf);
						}
						
					}
					else
					{
						if(cf.getStartFigure() is PortTypeFigure)
						{
							cf.setStartFigure(null);
							activeFEViewHelper.removeFiguresFromCanvas(cf);
						}
						else
						{
							new FigureCanvasAppEvent(FigureCanvasAppEvent.CREATE_CONNECTION_END,
								{endFigure :endFigureT}).dispatch();
						}
						
					}
				}
				
				
				
			}else{
				cf.setStartFigure(null);
				//				toolPanelModel.selectedFigure.drawclear();
				
				activeFEViewHelper.removeFiguresFromCanvas(cf);
			}
			
			var selectedFigureName:String="Link";
			var selectedFigId : int = FigureFactory.nametoid(selectedFigureName);
			if(cf is  MessageFlowLinkFigure )
			{
				selectedFigureName ="messageFlowLink";
				selectedFigId = BpmnFigureFactory.name2id(selectedFigureName);
			}
			
			
			
			new ToolPanelAppEvent( ToolPanelAppEvent.SELECT_FIGURE,
				{selectedFigureId :selectedFigId} ).dispatch();
			
			//				for rollback
			//				added by mengsong 2010-7-6 21:16:10
			var xml:XML = new XML;
			var figureEditorNavigatorModel :FigureEditorNavigatorModel;	
			var nowArrayID :Number = new Number();
			var ENVH:EditorNavigatorVH;
			ENVH =  ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY)
				as EditorNavigatorVH;
			nowArrayID = ENVH.IDNum;
			
			figureEditorNavigatorModel = OrDesignerModelLocator.getInstance()
				.figureEditorNavigatorModel;		
			figureEditorNavigatorModel.activeFigureEditorModel.saveCanvasXML();
			xml = ProcessFigure(figureEditorNavigatorModel.activeFigureEditorModel.rootFigure)
				.outputAllInformation();
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp
				.push(xml);
			OrDesignerModelLocator.getInstance().getOrchestraDesigner()
				.RollBack_TotalArray[nowArrayID]
				=OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp;
			
			
			
			//			this.fcStateDomain.setFCActiveState(new SelectionState());
		}
		
		
		/**
		 *
		 * @param event
		 *
		 */
		override public function RollOut(event:MouseEvent):void{
			
			var activeFEVHKey : String = FigureEditorVH.getViewHelperKey(feNavModel.activeFigureEditorModel.fileID);
			var activeFEViewHelper : FigureEditorVH = ViewLocator.getInstance().getViewHelper(activeFEVHKey) as FigureEditorVH;
			
			activeFEViewHelper.removeFiguresFromCanvas(DisplayObject(toolPanelModel.selectedFigure));
			
		}
	}
}