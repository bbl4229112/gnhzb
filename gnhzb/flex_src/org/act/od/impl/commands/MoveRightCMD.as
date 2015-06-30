package org.act.od.impl.commands
{
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.commands.AODCommand;
	import org.act.od.framework.control.OrDesignerEvent;
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.ConnectionFigure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.CooperateOperationEvent;
	
	/**
	 * Move figures right.
	 * 
	 *  @author Mengsong
	 * 
	 */ 
	public class MoveRightCMD extends AODCommand
	{
		private var selectedFigures:Array;
		private var idArray_Move:Array=new Array();
		private var canvasXML:XML = new XML();
		
		private var asymessage:AsyncMessage=new AsyncMessage();
		private var operationtype:String=new String();
		
		public function MoveRightCMD(){
			super();
		}
		/**
		 * @param event {fileID}
		 */
		override public function execute(event:OrDesignerEvent):void{
			
			var fileID :String = event.data.fileID;
			this.canvasXML=event.data.canvasXML;
			var feModel :FigureEditorModel = 
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().getFigureEditorModel(fileID);
			
			selectedFigures = feModel.selectedFigures;
			
			var i:int;
			var conn:Array=new Array();
			for(i=0;i<selectedFigures.length;i++){
				IFigure(selectedFigures[i]).setxy(IFigure(selectedFigures[i]).getx()+5,IFigure(selectedFigures[i]).gety());
				if(selectedFigures[i] is ConnectionFigure){
					conn.push(selectedFigures[i]);
				}
			}
			for(i=0;i<conn.length;i++){
				ConnectionFigure(conn[i]).autoSetAnchor();
			}
			for(i=0;i<selectedFigures.length;i++){
				IFigure(selectedFigures[i]).updateDraw();
			}
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
			this.operationtype="moveright";
			//			get the basic attributes of the figure except the Link FIgure
			for (i=0; i < this.selectedFigures.length; i++)
			{
				idArray_Move.push(IFigure(this.selectedFigures[i]).getID());
			}
			//			get the Attribute of the LinkFigure
			asymessage=OrDesignerModelLocator.getInstance().getOrchestraDesigner().getLinkAttribute(this.canvasXML, idArray_Move);
		}
		
		private function doCooperation():void
		{
			//			added by mengsong 
			//			For cooperate
			var activeFEModel:FigureEditorModel=OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
			if (activeFEModel != null)
			{
				asymessage.headers.modify_id=idArray_Move.join(",");
				asymessage.headers.modify_type=operationtype;
				new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION, {message_move: asymessage}).dispatch();
			}
		}
	}
}