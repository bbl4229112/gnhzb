package org.act.od.impl.commands
{
	import mx.managers.PopUpManager;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.FiguresRemoveConfirm;
	
	/**
	 * Show the confirm window when delete figures.
	 * 
	 * @author Zhaoxq
	 * 
	 */ 
	public class FigureDeleteConfirmPopCMD extends AODCommand{
		
		
		public function FigureDeleteConfirmPopCMD(){
			super();
		}
		/**
		 *  
		 * @param event {fileID}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var figuresRemoveConfirm :FiguresRemoveConfirm = 
				PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(),
					FiguresRemoveConfirm, true) as FiguresRemoveConfirm;
			
			figuresRemoveConfirm.setFileID(event.data.fileID);
			
			PopUpManager.centerPopUp(figuresRemoveConfirm);
		}
		
	}
}