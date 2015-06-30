package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	/**
	 * Zoom out the canvas
	 * 
	 *  @author Likexin
	 * 
	 */ 
	public class ZoomOutCMD extends AODCommand
	{
		public function ZoomOutCMD(){
			super();
		}
		/**
		 * @param event{fileID}
		 */ 
		override public function execute(event:OrDesignerEvent):void{
			
			var fileID :String = event.data.fileID;
			
			var feNavModel :FigureEditorNavigatorModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			
			var feModel :FigureEditorModel = feNavModel.getFigureEditorModel(fileID);
			
			feModel.showingMultiple/=1.2;
			
		}
		
	}
}