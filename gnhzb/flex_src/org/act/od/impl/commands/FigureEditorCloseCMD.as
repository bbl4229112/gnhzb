package org.act.od.impl.commands
{
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.state.SelectionState;
	import org.act.od.impl.view.BpelEditor;
	import org.act.od.impl.view.EditorNavigatorChild;
	import org.act.od.impl.view.FigureEditor;
	import org.act.od.impl.view.SaveChangeWindow;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	/**
	 * 
	 * @author Zhaoxq
	 * 
	 */	
	
	
	public class FigureEditorCloseCMD extends AODCommand{
		
		private var closedFE : FigureEditor;
		private var filePath :String;
		public function FigureEditorCloseCMD(){
			super();
		}
		
		
		/**
		 * 
		 * @param event {closedFE}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var filename:String = event.data.filename.toString();
//			if(filename != null){
//				var len:int = filename.length;
//				if(len < 16){
//					if(){
//					
//					}
//				}
//			
//			}
			closedFE = event.data.closedFE;
			filePath = closedFE.filePath;
			//parameter		
			var orDesModelLoc :OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
			
			var feNavModel :FigureEditorNavigatorModel = orDesModelLoc.getFigureEditorNavigatorModel();
			
			var feNavVH :EditorNavigatorVH = 
				ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
			
			var saveChangeWindow :SaveChangeWindow = SaveChangeWindow(
				PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(), SaveChangeWindow, true));
			saveChangeWindow.setClosedFE(closedFE);
			saveChangeWindow.setFileName(filename);
			saveChangeWindow.addEventListener(CloseEvent.CLOSE, updateCanvasXML);
			PopUpManager.centerPopUp(saveChangeWindow);
			
			orDesModelLoc.getFigureCanvasStateDomain().setFCActiveState(new SelectionState());
			
			//as the last index figure editor page automatically become the active figure editor,
			//set the last figure editor model to be the active figure editor model
			var lastFeNavChild :EditorNavigatorChild = feNavVH.getSelectedChildOfNavigator();
			
			if(lastFeNavChild != null) {
				if(lastFeNavChild.type == EditorNavigatorChild.FIGURE_EDITOR_TYPE){
					feNavModel.activeFigureEditorModel = FigureEditor(lastFeNavChild).figureEditorModel;
					
				}else if( lastFeNavChild.type == EditorNavigatorChild.BPEL_EDITOR_TYPE){
					feNavModel.activeBpelEditorModel = BpelEditor(lastFeNavChild).bpelEditorModel;
					feNavModel.activeFigureEditorModel = null; 
				}
				
			}else {
				feNavModel.activeFigureEditorModel = null;
				
			}
			//reset attribute view's showing figure
			orDesModelLoc.getAttributeViewModel().editedFigure = null;
			orDesModelLoc.getAttributeViewModel().updateAttribute();
		}
		
		private function updateCanvasXML(event :CloseEvent):void{
//			closedFE.figureEditorModel.updateCanvasXML();
			closedFE.figureEditorModel.saveCanvasXML();	
			if(closedFE.figureEditorModel.fatherModel != null){
				closedFE.figureEditorModel.fatherModel.saveCanvasXML();
			}
		}
		
	}
}