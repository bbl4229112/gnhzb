package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	/**
	 * Show the flow chart in the XML view.
	 * 
	 * @author Mengsong
	 * 
	 */ 
	public class ChangeToXMLViewCMD extends AODCommand{
		
		
		public function ChangeToXMLViewCMD(){
			super();
		}
		/**
		 * 
		 * @param event
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var feNavModel :FigureEditorNavigatorModel = 
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			
			var processFigure :ProcessFigure = ProcessFigure(feNavModel.activeFigureEditorModel.rootFigure);
			var activeFEModel :FigureEditorModel = feNavModel.activeFigureEditorModel;
			
			var feVH :FigureEditorVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(activeFEModel.fileID) ) as FigureEditorVH;
			
			if(feVH == null)
				throw new Error("no binding with existed figure editor model");
			else
				feVH.setFEXMLContent(processFigure.outputAllInformation().toXMLString());
			
		}
		
	}
}