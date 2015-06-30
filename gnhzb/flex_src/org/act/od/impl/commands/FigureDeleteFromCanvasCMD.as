package org.act.od.impl.commands
{
	import mx.controls.Alert;
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.CooperateOperationEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	/**
	 * delete figures from canvas and figureEditorModel.
	 *  
	 * @author Mengsong
	 * 
	 */
	public class FigureDeleteFromCanvasCMD extends AODCommand{
		
		private var asymessage:AsyncMessage=new AsyncMessage();
		private var operationtype:String=new String();
		private var selectedFigures :Array = new Array();
		private var idArray_Delete:Array=new Array();
		private var canvasXML:XML = new XML();
		
		public function FigureDeleteFromCanvasCMD(){
			super();
		}
		
		
		/**
		 * 
		 * @param event {fileID}
		 * 
		 */
		override public function execute(event :OrDesignerEvent) :void{
			
			var delArr :Array = new Array();
			var delArr1 :Array = new Array();
			
			var fileID :String = event.data.fileID;
			this.canvasXML=event.data.canvasXML;
			
			var feVH :FigureEditorVH = 
				ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(fileID)) as FigureEditorVH;
			
			var feModel :FigureEditorModel = 
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().getFigureEditorModel(fileID);
			
			selectedFigures = feModel.selectedFigures;
			var figureID:Array = new Array();
			var rootFigure :IFigure = feModel.rootFigure;
			var j:int;
			
			
			//			OrDesignerModelLocator.getInstance().getOrchestraDesigner().SignModeArray = selectedFigures.concat();
			//			sign connection process
			for(var i:int = 0; i < selectedFigures.length; i++)
				if(AbstractFigure(selectedFigures[i]) is ConnectionFigure)
					figureID.push(AbstractFigure(selectedFigures[i]).outputAllInformation());
			
			for(i = 0 ; i<selectedFigures.length; i++){
				rootFigure.removechildWithConnection( AbstractFigure(selectedFigures[i]), delArr, delArr1);
				for(j=0;j<delArr.length;j++){
					if(selectedFigures.indexOf(delArr[j])==-1){
						selectedFigures.push(delArr[j]);
					}
				}
				for(j=0;j<delArr1.length;j++){
					if(selectedFigures.indexOf(delArr1[j])==-1){
						selectedFigures.push(delArr1[j]);
					}
				}
				IFigure(selectedFigures[i]).setSelected(false);
				feVH.removeFiguresFromCanvas( AbstractFigure(selectedFigures[i]) );
			}
			new FigureCanvasAppEvent(FigureCanvasAppEvent.Canvas_Children_Changed,
				{canvas :null}).dispatch();
			
			//			added by mengsong
			//			For cooperate
			//			cooperation state is true
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==true)
			{
				this.prepair4cooperation();
				this.doCooperation();
			}
		}
		private function prepair4cooperation():void
		{
			var i:int=0;
			this.operationtype="figuredelete";
			//			get the basic attributes of the figure except the Link FIgure
			for (i=0; i < this.selectedFigures.length; i++)
			{
				idArray_Delete.push(IFigure(this.selectedFigures[i]).getID());
			}
		}
		
		private function doCooperation():void
		{
			//			added by mengsong 
			//			For cooperate
			var activeFEModel:FigureEditorModel=OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
			if (activeFEModel != null)
			{
				asymessage.headers.delete_id=idArray_Delete.join(",");
				asymessage.headers.modify_type=operationtype;
				new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION, {message_move: asymessage}).dispatch();
			}
		}
	}
}