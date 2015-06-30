package org.act.od.impl.commands
{
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.ToolPanelAppEvent;
	import org.act.od.impl.figure.FigureFactory;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.bpmn.BpmnFigureFactory;
	import org.act.od.impl.model.FigureCanvasStateDomain;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	
	/**
	 * 
	 * @author Mengsong
	 * 
	 */	
	public class NodeCreateLinkCMD extends AODCommand
	{
		public function NodeCreateLinkCMD(){
			super();
		}
		/**
		 * @param event {startFigure}
		 */
		override public function execute(event:OrDesignerEvent):void{
			
			var orDesModelLoc : OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
			var feNavModel : FigureEditorNavigatorModel = orDesModelLoc.getFigureEditorNavigatorModel();
			var fcStateDomain : FigureCanvasStateDomain = orDesModelLoc.getFigureCanvasStateDomain();
			var selectedFigureName : String = "Link";
			var selectedFigId : int = FigureFactory.nametoid(selectedFigureName);
			
			//Select Link
			var linkType :String = event.data.linkType;
			if(linkType=="message_flow_link"){
				selectedFigureName = "messageFlowLink";
				selectedFigId = BpmnFigureFactory.name2id(selectedFigureName);
			}
			else
				selectedFigId = FigureFactory.nametoid(selectedFigureName);
			
			new ToolPanelAppEvent( ToolPanelAppEvent.SELECT_FIGURE,
				{selectedFigureId :selectedFigId} ).dispatch();
			
			
			//Select start Figure with selected Link
			var startFigure : IFigure = event.data.startFigure as IFigure;
			new FigureCanvasAppEvent(FigureCanvasAppEvent.CREATE_CONNECTION_START,
				{startFigure :startFigure} ).dispatch();
			
		}
		
	}
}