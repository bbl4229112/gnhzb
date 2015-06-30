package org.act.od.impl.state{
	
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.managers.PopUpManager;
	import mx.messaging.messages.AsyncMessage;
	import mx.containers.Canvas;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.CooperateOperationEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.Endow2Figure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.figure.Startow2Figure;
	import org.act.od.impl.figure.bpmn.Poolow2Figure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.model.ToolPanelModel;
	import org.act.od.impl.view.FigureCreateFailedWarning;
	import org.act.od.impl.view.FigureEditor;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	/**
	 * process of drag creating
	 * 
	 *  @author Mengsong
	 *
	 */
	public class CreatingState extends AbstractState{
		
		private var ox:Number=0;
		private var oy:Number=0;
		
		private var figureENModel :FigureEditorNavigatorModel;
		
		private var toolPanelModel :ToolPanelModel;
		
		//		for cooperation
		private var asymessage:AsyncMessage=new AsyncMessage();
		private var operationtype:String=new String();
		private var selectedFigures :Array = new Array();
		//		private var idArray_Add:Array=new Array();
		private var figureXML_Add:XML = new XML();
		private var canvasXML:XML = new XML();
		
		public function CreatingState(){
			super();
			
			this.figureENModel =
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			this.toolPanelModel = OrDesignerModelLocator.getInstance().getToolPanelModel();
		}
		
		
		/**
		 *
		 * @param point
		 * @param event
		 *
		 */
		override public function mouseDown(point:Point,event:MouseEvent):void{
			
			ox=point.x;
			oy=point.y;
			
		}
		
		/**
		 *
		 * @param point
		 * @param event
		 *
		 */
		override public function mouseUp(point:Point,event:MouseEvent):void{
			
			this.fcStateDomain.setFCActiveState(new SelectionState());
			
			
			var i:int;
			var arr:Array;
			var fcfw :FigureCreateFailedWarning;
			var feVH :FigureEditorVH;
			
			feVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(figureENModel.activeFigureEditorModel.fileID)) as FigureEditorVH;
			
			var processType : String = feVH.figureEditor.figureEditorModel.processType;
			
			if(processType == FigureEditorModel.BPEL_PROCESS_TYPE)
			{
				if(this.toolPanelModel.selectedFigure is Startow2Figure){
					arr=new Array();
					this.figureENModel.activeFigureEditorModel.rootFigure.ifiguretoarray(arr);
					for(i=0;i<arr.length;i++){
						if(arr[i] is Startow2Figure){
							fcfw = PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(),
								FigureCreateFailedWarning, true) as FigureCreateFailedWarning;
							
							PopUpManager.centerPopUp(fcfw);
							
							feVH.removeFiguresFromCanvas( DisplayObject(toolPanelModel.selectedFigure) );
							return;
						}
						else{
							continue;
						}
					}
				}
				if(this.toolPanelModel.selectedFigure is Endow2Figure){
					arr=new Array();
					this.figureENModel.activeFigureEditorModel.rootFigure.ifiguretoarray(arr);
					for(i=0;i<arr.length;i++){
						if(arr[i] is Endow2Figure){
							fcfw = PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(),
								FigureCreateFailedWarning, true) as FigureCreateFailedWarning;
							
							PopUpManager.centerPopUp(fcfw);
							feVH = ViewLocator.getInstance().getViewHelper(
								FigureEditorVH.getViewHelperKey(figureENModel.activeFigureEditorModel.fileID)) as FigureEditorVH;
							feVH.removeFiguresFromCanvas( DisplayObject(toolPanelModel.selectedFigure) );
							return;
						}
						else{
							continue;
						}
					}
				}
			}
			
			this.toolPanelModel.selectedFigure.setID(figureENModel.activeFigureEditorModel.idManager.getAvailabelId());
			
			this.figureENModel.activeFigureEditorModel.rootFigure.addchild(this.toolPanelModel.selectedFigure);
			this.toolPanelModel.selectedFigure.updateDraw();
			
			figureXML_Add = this.toolPanelModel.selectedFigure.outputAllInformation();
			//increment number the Abstruct Pool in Canvas
			if(this.toolPanelModel.selectedFigure is Poolow2Figure)
				if(processType == FigureEditorModel.BPEL_PROCESS_TYPE)
					feVH.figureEditor.figureCanvas.IncremNumPool();
				else
					feVH.figureEditor.bpmnFigureCanvas.IncremNumPool();
			
			
			var vl:ViewLocator=ViewLocator.getInstance();
			var fenVH:EditorNavigatorVH=EditorNavigatorVH(vl.getViewHelper(EditorNavigatorVH.VH_KEY));
			//			FigureEditor(fenVH.getSelectedChildOfNavigator()).toolPanel.resetAllSelections();
			
			//			this.toolPanelModel.selectedFigure.updateDraw();
			
			//			For cooperate
			//			when cooperation state is true
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==true)
			{
				this.prepair4cooperation();
				this.doCooperation();
			}
		}
		
		private function prepair4cooperation():void
		{
			this.operationtype="create";
		}
		
		private function doCooperation():void
		{
			//			added by mengsong 
			//			For cooperate
			var activeFEModel:FigureEditorModel=OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
			if (activeFEModel != null)
			{
				asymessage.headers.add_figurexml=figureXML_Add;
				asymessage.headers.modify_type=operationtype;
				new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION, {message_move: asymessage}).dispatch();
			}
		}
		
		/**
		 * change the figure size
		 *
		 * @param point
		 * @param event
		 */
		override public function mouseMove(point:Point,event:MouseEvent):void{
			
			var x:Number=point.x;
			var y:Number=point.y;
			
			
			
			toolPanelModel.selectedFigure.setposition(ox,oy);
			toolPanelModel.selectedFigure.autosetsize(x,y,0);
			toolPanelModel.selectedFigure.updateDraw();
			
			//			var can:Canvas = event.currentTarget as Canvas;
			//			//			var fi:AbstractFigure = can.getChildAt(0) as AbstractFigure;
			//			var feNavModel :FigureEditorNavigatorModel =
			//				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			//			var fem:FigureEditorModel = feNavModel.activeFigureEditorModel.rootModel;
			//			//			var xml:XML = fem._canvasXML;
			//			fem.updateCanvasXML();
			
		}
		
		
	}
}