package org.act.od.impl.commands
{
	import mx.containers.Canvas;
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.FigureCanvas;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	/**
	 *Open or Close the compare mode while cooperating
	 * @author Mengsong
	 */
	public class CompareModeCMD extends AODCommand
	{
		public function CompareModeCMD()
		{
			super();
		}
		override public function execute(event :OrDesignerEvent) :void{
			var fileID:String = new String();
			fileID = OrDesignerModelLocator.getInstance().getOrchestraDesigner().FileID;
			var figureEditorVH:FigureEditorVH = ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(fileID)) as FigureEditorVH;
			
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().CompareModeState == true)
			{
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().CompareModeState = false;	
			}
			else{
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().CompareModeState = true;	
				if(figureEditorVH.figureEditor._figureCanvas.getChildByName("comparelayer"))
					figureEditorVH.figureEditor._figureCanvas.removeCompareLayer();
			}
		}	
	}
}
