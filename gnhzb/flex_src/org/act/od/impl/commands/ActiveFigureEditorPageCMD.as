package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	import org.act.od.impl.model.FigureEditorModel;
	
	/**
	 * Active the FigureEditor page, if not exist, creat a new one 
	 * @author Likexin
	 * 
	 */
	public class ActiveFigureEditorPageCMD extends AODCommand{
		
		
		public function ActiveFigureEditorPageCMD(){
			super();
		}
		
		
		/**
		 * 
		 * @param event { fileID, filePath, fileName, figureEditorModel }
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var viewLoc :ViewLocator =  ViewLocator.getInstance();
			
			var feNavVH :EditorNavigatorVH = 
				ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
			
			var feNavModel :FigureEditorNavigatorModel = 
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			
			var feVH :FigureEditorVH;
			
			//1. find if the figureEditorPage exist		
			var key:String = FigureEditorVH.getViewHelperKey(event.data.fileID);
			if( viewLoc.registrationExistsFor(key)){
				feVH = viewLoc.getViewHelper(FigureEditorVH.getViewHelperKey(event.data.fileID)) as FigureEditorVH;
				var feNavVH :EditorNavigatorVH = 
					ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
				feNavVH.SwithToGivenFileID(event.data.fileID);
				//				feVH.loadGraphFile();
				
			}else{ //2. creat new figureEditor
				feNavVH.createNewFigureEditor(event.data.figureEditorModel, event.data.filePath, event.data.fileName ,event.data.fileCategory);
				feVH = viewLoc.getViewHelper(FigureEditorVH.getViewHelperKey(event.data.fileID)) as FigureEditorVH;
				//				viewLoc.register(FigureEditorVH.getViewHelperKey(event.data.fileID),feVH);
			}
			
			//3. set the activeModel
			var fem:FigureEditorModel = event.data.figureEditorModel as FigureEditorModel;
			
			feNavModel.activeFigureEditorModel = fem;
			
			//4. swith tab of figureEditorNavigator
			
			
			//////////////////////问题就出在这里
			feNavModel.activeFigureEditorModel.saveCanvasXML(); 
			feNavVH.SwithToGivenFileID(event.data.fileID);
			
		}
		
	}
}