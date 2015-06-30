package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.BpelEditorModel;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.Microimage;
	import org.act.od.impl.viewhelper.BpelEditorVH;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	
	
	/**
	 * Active the bpelEditor page. if not exist, then creat a new one
	 *  
	 * @author Likexin
	 * 
	 */
	public class ActiveBpelEditorPageCMD extends AODCommand{
		
		
		public function ActiveBpelEditorPageCMD(){
			super();
		}
		
		
		/**
		 * 
		 * @param event { fileID, filePath, fileName, bpelEditorModel }
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var viewLoc :ViewLocator =  ViewLocator.getInstance();
			
			var feNavVH :EditorNavigatorVH = 
				ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
			
			var feNavModel :FigureEditorNavigatorModel = 
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			
			var activeFEModel :FigureEditorModel = feNavModel.activeFigureEditorModel;
			
			var beVH :BpelEditorVH;
			
			var beModel :BpelEditorModel = event.data.bpelEditorModel as BpelEditorModel;
			//1. find if the bpelEditorPage not exist		
			if( !viewLoc.registrationExistsFor(BpelEditorVH.getViewHelperKey(event.data.fileID)) ){
				//2. creat new bpelEditor
				feNavVH.createNewBpelEditor(beModel, event.data.filePath, event.data.fileName);
			}
			
			beVH = viewLoc.getViewHelper(BpelEditorVH.getViewHelperKey(event.data.fileID)) as BpelEditorVH;
			//3. set the activeModel
			feNavModel.activeBpelEditorModel = beModel;
			
			//4. swith tab of bpelEditorNavigator
			feNavVH.SwithToGivenFileID(event.data.fileID);
		}
		
	}
}