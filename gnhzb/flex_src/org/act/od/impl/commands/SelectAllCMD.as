package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	/**
	 * Selected all figures.
	 * 
	 * @author Mengsong
	 * 
	 */ 
	public class SelectAllCMD extends AODCommand
	{
		public function SelectAllCMD(){
			super();
		}
		/**
		 * @param event {fileID}
		 */
		override public function execute(event:OrDesignerEvent):void{
			
			var fileID :String = event.data.fileID;
			var feNavModel :FigureEditorNavigatorModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			
			var feModel :FigureEditorModel = feNavModel.getFigureEditorModel(fileID);
			
			var selectedfigure:Array=feModel.selectedFigures;
			feModel.rootFigure.ifiguretoarray(selectedfigure);
			
			var i:int;
			for(i=0;i<selectedfigure.length;i++){
				IFigure(selectedfigure[i]).setSelected(true);
				IFigure(selectedfigure[i]).updateDraw();
			}
			
		}
		
	}
}