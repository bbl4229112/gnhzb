package org.act.od.impl.commands
{
	import mx.core.UIComponent;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.bpmn.Poolow2Figure;
	import org.act.od.impl.figure.bpmn.PortTypeFigure;
	import org.act.od.impl.model.FigureCanvasStateDomain;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.model.ToolPanelModel;
	import org.act.od.impl.state.CreateConnectionEndState;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	/**
	 * 
	 *  @author Zhaoxq
	 * 
	 */	
	
	public class SetConnectionStartPointCMD extends AODCommand{
		
		
		public function SetConnectionStartPointCMD(){
			super();
		}
		
		/**
		 *
		 * @param event [startFigure]
		 *
		 */
		override public function execute(event : OrDesignerEvent) : void {
			
			//model
			var orDesModelLoc : OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
			var feNavModel : FigureEditorNavigatorModel = orDesModelLoc.getFigureEditorNavigatorModel();
			var fcStateDomain : FigureCanvasStateDomain = orDesModelLoc.getFigureCanvasStateDomain();
			var toolPanelModel : ToolPanelModel = orDesModelLoc.getToolPanelModel();
			
			//state information
			var startFigure : IFigure = event.data.startFigure as IFigure;
			
			
			
			var curConnection : ConnectionFigure = ConnectionFigure(toolPanelModel.selectedFigure);
			
			curConnection.setStartFigure(startFigure);
			
			var portX : Number = 0;
			var portY : Number = 0;
			/*if(startFigure is PortTypeFigure)
			{
			
			//portX = Poolow2Figure(startFigure.getparent()).x;
			var parent : Poolow2Figure = UIComponent(startFigure).parent as Poolow2Figure;
			if (parent !=null)
			portY = parent.y;
			var startFigure2 : IFigure = startFigure;// new PortTypeFigure(FigureEditorModel.BPMN_PROCESS_TYPE);
			PortTypeFigure(startFigure2).setpositionInPool(PortTypeFigure(startFigure).x+15,PortTypeFigure(startFigure).y+portY+15);
			curConnection.setStartFigure(startFigure2);
			}*/
			
			//curConnection.setposition(startFigure.getrx(),startFigure.getry()+portY);*
			//curConnection.setposition(1,1);
			
			//add connection figure to active figure canvas
			var activeFEVHKey : String = FigureEditorVH.getViewHelperKey(feNavModel.activeFigureEditorModel.fileID);
			var activeFEViewHelper : FigureEditorVH = ViewLocator.getInstance().getViewHelper(activeFEVHKey) as FigureEditorVH;
			if(activeFEViewHelper == null)
				throw new Error("activeFEViewHelper null");
			else
				activeFEViewHelper.addFigureToCanvas(curConnection);
			
			
			var i:int;
			for(i=0;i<feNavModel.activeFigureEditorModel.selectedFigures.length;i++){
				IFigure(feNavModel.activeFigureEditorModel.selectedFigures[i]).hideContextPanel();
			}
			fcStateDomain.setFCActiveState(new CreateConnectionEndState());
			
			//!!!can consider write putting this command into stack here, because if the start figure
			//is null, there's no need to put this command into command stack
		}
	}
}