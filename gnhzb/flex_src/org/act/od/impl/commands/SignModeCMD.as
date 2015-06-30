package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	/**
	 *	transfor the state of the Sign Mode
	 * 
	 * @author mengsong
	 */
	public class SignModeCMD extends AODCommand
	{
		public function SignModeCMD()
		{
			super();
		}
		/**
		 * 
		 * @param event,SignModeState
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var fileID:String = new String();
			fileID = OrDesignerModelLocator.getInstance().getOrchestraDesigner().FileID;
			var figureEditorVH:FigureEditorVH = ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(fileID)) as FigureEditorVH;
			
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().SignModeState == true)
			{
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().SignModeState = false;	
			}
			else{
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().SignModeState = true;	
				if(figureEditorVH.figureEditor._figureCanvas.getChildByName("signlayer"))
					figureEditorVH.figureEditor._figureCanvas.removeSignLayer();
			}
		}
	}
}