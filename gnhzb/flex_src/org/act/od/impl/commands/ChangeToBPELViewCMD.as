package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.business.BpelCreator;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.FigureEditor;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	/**
	 * Show the flow chart in the BPEL view.
	 * 
	 * @ author Zhaoxq
	 * 
	 */
	public class ChangeToBPELViewCMD extends AODCommand
	{
		/**
		 *Constructor,
		 * call super class constructor.
		 *
		 */
		public function ChangeToBPELViewCMD(){
			super();
		}
		
		/**
		 * Execute commande to show the flow chart in the BPEL view.
		 * @param event
		 *
		 */
		override public function execute(event : OrDesignerEvent) :void{
			
			var figureEditorNavigatorModel :FigureEditorNavigatorModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			var activeFEModel :FigureEditorModel = figureEditorNavigatorModel.activeFigureEditorModel;
			var figureEditor : FigureEditor;
			var figureEditorVH :FigureEditorVH;
			var relBpelFileID :String;
			
			if(activeFEModel != null){
				activeFEModel.updateCanvasXML();
				figureEditorVH = ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(activeFEModel.fileID)) as FigureEditorVH;
				var bpelCreator :BpelCreator = new BpelCreator();
				var bpelText :String = bpelCreator.outBpelStirng(ProcessFigure(activeFEModel.rootFigure));
				figureEditorVH.setFEXMLContent(bpelText);
			}
		}
	}
}