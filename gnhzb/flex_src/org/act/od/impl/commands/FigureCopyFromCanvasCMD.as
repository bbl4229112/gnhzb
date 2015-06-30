package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	/**
	 * Copy a figure from the canvas and figureEditorModel.
	 * 
	 * @author Zhaoxq
	 * 
	 */ 
	public class FigureCopyFromCanvasCMD extends AODCommand{
		
		
		public function FigureCopyFromCanvasCMD(){
			super();
		}
		
		
		/**
		 * 
		 * @param event {fileID}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var fileID :String = event.data.fileID;
			
			var feNavModel :FigureEditorNavigatorModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			
			var feModel :FigureEditorModel = feNavModel.getFigureEditorModel(fileID);
			
			var selectedFigures :Array = feModel.selectedFigures;
			
			var xml :XML =<copy/>;
			
			for(var i:int =0; i<selectedFigures.length; i++){
				xml.appendChild( IFigure(selectedFigures[i]).outputAllInformation() );
			}
			
			feNavModel.xmlOfCopyFigures = xml;
			
		}
		
	}
}