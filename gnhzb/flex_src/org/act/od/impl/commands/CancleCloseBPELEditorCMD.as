package org.act.od.impl.commands
{
	import mx.controls.Alert;
	
	import org.act.od.framework.commands.SequenceCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.model.BpelEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.BpelEditor;
	
	/**
	 * Close the BPELEditor page and then cancel it.
	 * 
	 * @ author Zhaoxq
	 * 
	 */ 
	public class CancleCloseBPELEditorCMD extends SequenceCommand
	{   
		/**
		 * Constructor,
		 * call super class constructor.  
		 * 
		 */	
		public function CancleCloseBPELEditorCMD(){
			super();
		}
		/**
		 * Execute commande to close the BPELEditor page and then cancel it
		 * @param event {closedBPELEditor}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			var closedBPELEditor : BpelEditor = event.data.closedBPELEditor as BpelEditor;
			
			if(closedBPELEditor == null)
				Alert.show("closedFigureEditor null in CancleCloseBPELEditorCMD");
			else {
				var closedBEModel :BpelEditorModel = closedBPELEditor.bpelEditorModel
				
				//readd cancled-closing figure editor
				var viewLoc : ViewLocator =  ViewLocator.getInstance();
				var feNavModel :FigureEditorNavigatorModel = 
					OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
				//				var feNavVH : EditorNavigatorVH = 
				//					ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
				//				feNavVH.createNewBpelEditor(closedBEModel, closedBPELEditor.filePath, closedBPELEditor.label);
				//				var beVH :BpelEditorVH = viewLoc.getViewHelper(BpelEditorVH.getViewHelperKey(closedBEModel.fileID)) as BpelEditorVH;
				//				
				//				//reset current state and active figure editor model
				//				feNavModel.activeBpelEditorModel = closedBPELEditor.bpelEditorModel;
				//				
				//				feNavVH.SwithToGivenFileID(closedBEModel.fileID);
				
				//2. active the bpelEditorpage
				this.nextEvent = new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_BPELEDITOR_PAGE,
					{ fileID :closedBEModel.fileID, filePath :closedBPELEditor.filePath, 
						fileName :closedBPELEditor.label, bpelEditorModel:closedBEModel }
				);
				this.executeNextCommand();
			}
		}
	}
}