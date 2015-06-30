package org.act.od.impl.commands
{
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.BpelEditor;
	import org.act.od.impl.view.SaveChangeWindow;
	
	/**
	 * Close the selected BPELEditor page
	 * 
	 * @ author Zhaoxq
	 * 
	 */ 
	public class BPELEditorCloseCMD extends AODCommand{
		
		private var closedBE :BpelEditor;
		private var filePath :String;
		
		/**
		 * Constructor 
		 * Called constructor by super class.
		 */	
		public function BPELEditorCloseCMD(){
			super();
		}
		
		/**
		 * Create a PopUp windows to ask user to save BpelEditor's modifications.
		 * Close the BpelEditor
		 * @param event {closedBE}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			//parameter
			closedBE = event.data.closedBE;
			filePath = closedBE.filePath;
			
			var saveChangeWindow :SaveChangeWindow = SaveChangeWindow(PopUpManager.createPopUp(
				OrDesignerModelLocator.getInstance().getOrchestraDesigner(), SaveChangeWindow, true));
			
			saveChangeWindow.setFileName(filePath.substring(filePath.lastIndexOf("\\",filePath.length)+1, filePath.length));
			saveChangeWindow.setClosedBE(closedBE);
			saveChangeWindow.addEventListener(CloseEvent.CLOSE, updateBPEL);
			PopUpManager.centerPopUp(saveChangeWindow);
		}
		/**
		 * 
		 * @param event <code> CloseEvent </code>
		 * 
		 */	
		private function updateBPEL(event :CloseEvent):void{
			closedBE.bpelEditorModel.updateBpelContent(closedBE.getBpelContent());
		}
	}
}