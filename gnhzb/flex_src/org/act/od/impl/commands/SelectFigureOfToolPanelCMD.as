package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.figure.*;
	import org.act.od.impl.figure.bpmn.BpmnFigureFactory;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.model.ToolPanelModel;
	import org.act.od.impl.state.CreateConnectionStartState;
	import org.act.od.impl.state.CreationState;
	import org.act.od.impl.view.bpmn.TabBpmnBpel;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	/**
	 * Selectd a figure in the toolPanel.
	 * 
	 * @author Mengsong
	 * 
	 */
	public class SelectFigureOfToolPanelCMD extends AODCommand{
		
		
		public function SelectFigureOfToolPanelCMD(){
			super();
		}
		
		/**
		 * @param event {selectedFigureId}
		 */
		override public function execute(event : OrDesignerEvent) :void{
			
			var orDesModelLoc : OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
			
			var model : ToolPanelModel = orDesModelLoc.getToolPanelModel();
			
			var activeFEModel : FigureEditorModel = orDesModelLoc.getFigureEditorNavigatorModel().activeFigureEditorModel;
			
			var selectedFigureId : int = event.data.selectedFigureId as int;
			
			//if selected figure not null, then change the tool panel model's selectedFigure attribute value
			if(selectedFigureId != -1) {
				
				var fileID:String;
				var figureEditorVH:FigureEditorVH;
				fileID = activeFEModel.fileID;
				figureEditorVH = ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(fileID)) as FigureEditorVH;
				
				var selectedFigure : IFigure ;
				
				if(figureEditorVH.figureEditor.figureEditorModel.processType == FigureEditorModel.BPEL_PROCESS_TYPE)
					selectedFigure = FigureFactory.createFigure(selectedFigureId);
				else
					if(figureEditorVH.figureEditor.figureEditorModel.processType == FigureEditorModel.BPMN_PROCESS_TYPE)
						selectedFigure = BpmnFigureFactory.createFigure(selectedFigureId);
				
				selectedFigure.setMultiple(activeFEModel._showingMultiple);
				
				model.selectedFigure = selectedFigure;
				
				//				for compare mode
				var tempfigureArray:Array = new Array();
				selectedFigure.ifiguretoarray(tempfigureArray);
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().SignModeArray = tempfigureArray.concat();
				
				if(selectedFigure is ConnectionFigure){
					orDesModelLoc.getFigureCanvasStateDomain().setFCActiveState(new CreateConnectionStartState());
					
				}else if(selectedFigure is GraphicalFigure){
					orDesModelLoc.getFigureCanvasStateDomain().setFCActiveState(new CreationState());
				}
				
			}
		}
	}
}