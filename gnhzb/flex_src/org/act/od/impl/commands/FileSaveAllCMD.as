package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	/**
	 * Save the all open file.
	 * 
	 * @author Quyue
	 * 
	 */ 
	public class FileSaveAllCMD extends AODCommand
	{
		private var figureEditorNavigatorVH :EditorNavigatorVH;
		private var figureEditorNavigatorModel :FigureEditorNavigatorModel;
		public function FileSaveAllCMD(){
			figureEditorNavigatorVH = ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
		}
		/**
		 * 
		 * @param event 
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
		}
		
	}
}