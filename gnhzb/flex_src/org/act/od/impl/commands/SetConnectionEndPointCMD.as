package org.act.od.impl.commands
{
	import flash.geom.ColorTransform;
	
	import mx.controls.Alert;
	import mx.core.UIComponent;
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.events.CooperateOperationEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.figure.bpmn.Poolow2Figure;
	import org.act.od.impl.figure.bpmn.PortTypeFigure;
	import org.act.od.impl.model.FigureCanvasStateDomain;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.model.ToolPanelModel;
	/**
	 * 
	 * @author Mengsong
	 * 
	 */
	public class SetConnectionEndPointCMD extends AODCommand{
		
		//model
		private var orDesModelLoc : OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
		private var figureEditorNavModel : FigureEditorNavigatorModel = orDesModelLoc.getFigureEditorNavigatorModel();
		private var fcStateDomain : FigureCanvasStateDomain= orDesModelLoc.getFigureCanvasStateDomain();
		private var toolPanelModel : ToolPanelModel = orDesModelLoc.getToolPanelModel();

		//		for cooperation
		private var asymessage:AsyncMessage=new AsyncMessage();
		private var operationtype:String=new String();
		private var createLinkXML:XML = new XML();
		
		//state information
		private var endFigure : IFigure;
		
		public function SetConnectionEndPointCMD(){
			super();
		}
		
		/**
		 * @param event{endFigure}
		 */
		override public function execute(event : OrDesignerEvent) : void {
			
			var endFigure : IFigure = event.data.endFigure as IFigure;
			var cf : ConnectionFigure = ConnectionFigure(toolPanelModel.selectedFigure);
			
			
			if(endFigure != null){
				
				//				!!!!!!!!!!!!!!!!!!
				//change the Link color to black
				var black:ColorTransform = new ColorTransform();
				black.color = 0xB6AFA9;
				cf.sprt.transform.colorTransform = black;
				
				this.endFigure = endFigure;
				cf.setEndFigure(endFigure);
				
				var portX : Number = 0;
				var portY : Number = 0;
				
				/**if(endFigure is PortTypeFigure)
				 {
				 //portX = Poolow2Figure(startFigure.getparent()).x;
				 var parent : Poolow2Figure = UIComponent(endFigure).parent as Poolow2Figure;
				 if (parent !=null)
				 portY = parent.y;
				 var endFigure2 : IFigure = endFigure;//new PortTypeFigure(FigureEditorModel.BPMN_PROCESS_TYPE);
				 PortTypeFigure(endFigure2).setpositionInPool(PortTypeFigure(endFigure).x+15,PortTypeFigure(endFigure).y+portY+50);
				 //Alert.show("x = "+PortTypeFigure(endFigure).x+" y = "+PortTypeFigure(endFigure).y+" + "+portY+" + 50");
				 //cf.setEndFigure(endFigure2);
				 }**/
				
				cf.autoSetAnchor();
				cf.setID(figureEditorNavModel.activeFigureEditorModel.idManager.getAvailabelId());
				figureEditorNavModel.activeFigureEditorModel.rootFigure.addchild(toolPanelModel.selectedFigure);
			}
//			translate link figure to xml file			
			createLinkXML = cf.outputAllInformation();
			
			//			For cooperate
			//			when cooperation state is true
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==true)
			{
				this.prepair4cooperation();
				this.doCooperation();
			}
			//			added by mengsong
			//			For cooperate
//			var activeFEModel :FigureEditorModel =  OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
//			if(activeFEModel != null){
//				new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION).dispatch();
//			}	
		}
		private function prepair4cooperation():void
		{
			this.operationtype="create";
		}
		
		private function doCooperation():void
		{
			//			added by mengsong 
			//			For cooperate
			var activeFEModel:FigureEditorModel=OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
			if (activeFEModel != null)
			{
				asymessage.headers.add_figurexml=createLinkXML;
				asymessage.headers.modify_type=operationtype;
				new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION, {message_move: asymessage}).dispatch();
			}
		}
	}
}

