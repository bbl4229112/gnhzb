package org.act.od.impl.state
{
	
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.net.SharedObject;
	
	import mx.controls.Alert;
	import mx.managers.CursorManager;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.*;
	import org.act.od.impl.model.*;
	import org.act.od.impl.view.OrchestraDesigner;
	import org.act.od.impl.viewhelper.DesignerToolBarVH;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	/**
	 *Translate the states of the figures
	 *  
	 * @author Likexin
	 * 
	 */	
	public class SelectionState extends AbstractState{
		
		private var feNavModel :FigureEditorNavigatorModel;
		
		private var  attrViewModel :AttributeViewModel;
		private var xml :XML = new XML;
		private var figureEditorVH:FigureEditorVH;
		
		//		the number of the limit steps,default 20 steps
		private static var LIMITNUM:Number = new Number(20);		
		
		[Bindable]
		[Embed(source="/../assets/icon/canvas/forbidden.png")]
		private var forbiddenicon:Class;
		private var forbiddeniconid:int=new int(0);
		
		public function SelectionState(){
			super();
			
			////			for token control
			//			var figureEditorNavigatorModel :FigureEditorNavigatorModel;
			//			figureEditorNavigatorModel = OrDesignerModelLocator.getInstance().figureEditorNavigatorModel;
			//			
			//			if(figureEditorNavigatorModel.activeFigureEditorModel!=null)
			//			Alert.show("Down");
			
			
			this.feNavModel = OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			this.attrViewModel = OrDesignerModelLocator.getInstance().getAttributeViewModel();
			this.RealTimeXmlTrans();
		}
		
		/**
		 *
		 * @param point
		 * @param event
		 *
		 */
		override public function mouseUp(point:Point,event:MouseEvent):void{
			CursorManager.removeCursor(forbiddeniconid);
		}
		
		/**
		 *
		 * @param point
		 * @param event
		 *
		 */
		override public function mouseDown(point:Point, event:MouseEvent):void{
			
			var ox:Number=point.x;
			var oy:Number=point.y;
			var i:int;
			var num:int;
			var j:int;
			
			var selectedFigures:Array = feNavModel.activeFigureEditorModel.selectedFigures;
			
			if( (OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==false)||
				(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==true)&&
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().TokenState == true)
			{
				//				CursorManager.removeCursor(forbiddeniconid);
				for(i=0; i<selectedFigures.length; i++){
					num = IFigure(selectedFigures[i]).changedirection(ox,oy);
					if(num!=0){
						for(j=0;j<selectedFigures.length;j++){
							IFigure(selectedFigures[j]).hideContextPanel();
						}
						this.fcStateDomain.setFCActiveState(new ChangeSizeState(IFigure(selectedFigures[i]), num));
						return;
					}
				}
				
				if(!event.ctrlKey){
					this.clickXY(point,event);
				}else{
					this.ctrlClickXY(point,event);
				}
				
				//				clear the graphics for sign mode
				//				figureEditorVH = ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(
				//					OrDesignerModelLocator.getInstance().getOrchestraDesigner().FileID)) as FigureEditorVH;
				//				figureEditorVH.figureEditor._figureCanvas.signlayer.removeAllChildren();
				//				figureEditorVH.figureEditor._figureCanvas.signlayer.graphics.clear();
				//				OrDesignerModelLocator.getInstance().getOrchestraDesigner().DrawConnectionOnlyState = false;
				//				
				//				//				clear the graphics for compare mode
				//				figureEditorVH.figureEditor._figureCanvas.comparelayer.removeAllChildren();	
				
			}
			else{
				forbiddeniconid = CursorManager.setCursor(forbiddenicon);
				//				Alert.show("Sorry,Please get the TOKEN");
			} 
		}
		
		private function clickXY(point:Point,event:MouseEvent):void{
			var x:Number=point.x;
			var y:Number=point.y;
			var selectedFigures:Array=feNavModel.activeFigureEditorModel.selectedFigures;
			var temp:IFigure = feNavModel.activeFigureEditorModel.rootFigure.getupperfigure(x,y);
			var i:int;
			
			if(temp!=null){
				
				//				reselecte the figure
				if(selectedFigures.indexOf(temp)==-1){
					feNavModel.activeFigureEditorModel.resetSelectedFigure();
				}
				
				temp.ifiguretoarray(selectedFigures);
				
				if(temp.isin(x,y)==1){
					this.fcStateDomain.setFCActiveState(new MovingState());
					this.fcStateDomain.mouseDown(point,event);
				}
				
			}else{
				feNavModel.activeFigureEditorModel.resetSelectedFigure();
				this.fcStateDomain.setFCActiveState(new MultiSelectionState());
				this.fcStateDomain.mouseDown(point,event);
			}
			
			//			test for compare mode	
			if(selectedFigures.length!=0){
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().SignModeArray = selectedFigures.concat();
			}
			
			
			
			for(i=0;i<selectedFigures.length;i++){
				AbstractFigure(selectedFigures[i]).setSelected(true);
				AbstractFigure(selectedFigures[i]).updateDraw();
			}
		}
		
		private function ctrlClickXY(point:Point,event:MouseEvent):void{
			var x:Number=point.x;
			var y:Number=point.y;
			var selectedFigures:Array=feNavModel.activeFigureEditorModel.selectedFigures;
			var temp:IFigure = feNavModel.activeFigureEditorModel.rootFigure.getupperfigure(x,y);
			
			if(temp!=null){
				
				if(selectedFigures.indexOf(temp)==-1){
					temp.ifiguretoarray(selectedFigures);
					temp.setSelected(true);
					
				}else{
					selectedFigures.splice(selectedFigures.indexOf(temp),1);
					temp.setSelected(false);
				}
				
				temp.updateDraw();
				
			}
		}
		
		
		
		/**
		 *
		 * @param event
		 *
		 */
		override public function keyDown(event:KeyboardEvent):void{
			
			
			
			
			// figure delete handle
			if(event.keyCode==46){//del
				if( feNavModel.activeFigureEditorModel == null ){
					throw new Error("no ActiveFigureEditorModel!");
				}
				
				new FigureCanvasAppEvent(FigureCanvasAppEvent.POP_FIGURE_DELETE_CONFIRM,
					{fileID :feNavModel.activeFigureEditorModel.fileID} ).dispatch();
				return;
			}
			
			
			// figure copy handle
			if(event.keyCode==71){//CG
				if(event.ctrlKey){
					if( feNavModel.activeFigureEditorModel == null ){
						throw new Error("no ActiveFigureEditorModel!");
					}
					new FigureCanvasAppEvent(FigureCanvasAppEvent.FIGURES_COPY,
						{fileID :feNavModel.activeFigureEditorModel.fileID} ).dispatch();
				}
				return;
			}
			
			
			//figure paste handle
			if(event.keyCode==85){//VU
				if(event.ctrlKey){
					if( feNavModel.activeFigureEditorModel == null ){
						throw new Error("no ActiveFigureEditorModel!");
					}
					new FigureCanvasAppEvent(FigureCanvasAppEvent.FIGURES_PASTE,
						{fileID :feNavModel.activeFigureEditorModel.fileID} ).dispatch();
				}
				return;
			}
			
			
			
			if(event.keyCode==37){//LEFT
				
				if( feNavModel.activeFigureEditorModel == null ){
					throw new Error("no ActiveFigureEditorModel!");
				}
				new FigureCanvasAppEvent(FigureCanvasAppEvent.MOVE_LEFT,
					{fileID :feNavModel.activeFigureEditorModel.fileID,
						canvasXML:this.xml} ).dispatch();
				
				
				return;
			}
			
			
			if(event.keyCode==38){//UP
				
				if( feNavModel.activeFigureEditorModel == null ){
					throw new Error("no ActiveFigureEditorModel!");
				}
				new FigureCanvasAppEvent(FigureCanvasAppEvent.MOVE_UP,
					{fileID :feNavModel.activeFigureEditorModel.fileID,
						canvasXML:this.xml} ).dispatch();
				
				
				return;
			}
			
			
			if(event.keyCode==39){//RIGHT
				
				if( feNavModel.activeFigureEditorModel == null ){
					throw new Error("no ActiveFigureEditorModel!");
				}
				new FigureCanvasAppEvent(FigureCanvasAppEvent.MOVE_RIGHT,
					{fileID :feNavModel.activeFigureEditorModel.fileID,
						canvasXML:this.xml} ).dispatch();
				
				
				return;
			}
			
			
			if(event.keyCode==40){//DOWN
				
				if( feNavModel.activeFigureEditorModel == null ){
					throw new Error("no ActiveFigureEditorModel!");
				}
				new FigureCanvasAppEvent(FigureCanvasAppEvent.MOVE_DOWN,
					{fileID :feNavModel.activeFigureEditorModel.fileID,
						canvasXML:this.xml} ).dispatch();
				
				
				
				return;
			}
			
			
		}
		
		
		//		added by mengsong 2010-7-5 21:47:44
		/**
		 *
		 * Get the realtime xml code
		 *
		 */
		public function RealTimeXmlTrans():void
		{
			
			var figureEditorNavigatorModel :FigureEditorNavigatorModel;
			var ENVH:EditorNavigatorVH;
			var tempOrXML:XML = new XML;
			
			//			Add 4 crash recovery
			var lso:SharedObject;
			var lso_array:Array = new Array;
			
			figureEditorNavigatorModel = OrDesignerModelLocator.getInstance().figureEditorNavigatorModel;
			
			if(figureEditorNavigatorModel.activeFigureEditorModel!=null)
			{
				var nowArrayID :Number = new Number();
				//				add for change icon by quyue
//				var DTVH:DesignerToolBarVH;
//				
//				DTVH=ViewLocator.getInstance().getViewHelper(DesignerToolBarVH.VH_KEY)
//					as DesignerToolBarVH;
				ENVH =  ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY)
					as EditorNavigatorVH;
				nowArrayID = ENVH.IDNum;
				
				figureEditorNavigatorModel.activeFigureEditorModel.saveCanvasXML();
				xml = ProcessFigure(figureEditorNavigatorModel.activeFigureEditorModel.rootFigure)
					.outputAllInformation();
				
				//				Added for crash recovery
				lso = SharedObject.getLocal("nowState");
				
				lso_array.push(xml);
				lso_array.push(OrDesignerModelLocator.getInstance().getOrchestraDesigner().FileID);
				lso_array.push(OrDesignerModelLocator.getInstance().getOrchestraDesigner().Name);
				lso_array.push(OrDesignerModelLocator.getInstance().getOrchestraDesigner().Path);
				lso.data.nowState = lso_array;
				
				var aaa:OrchestraDesigner = OrDesignerModelLocator.getInstance().getOrchestraDesigner();
				
				var bbb:Array = aaa.RollBack_arraytemp;
				trace();
				if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp.length==0)
				{
					
					if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_TotalArray
						.length == 1)
					{
						OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp
							.push(OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML);
						//						trace(":::::::::::::::::::::::::::::::::::");
						//						trace("length==1"+OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp);
						//						trace(":::::::::::::::::::::::::::::::::::");
					}
					else
					{
						OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp
							.push(ENVH.OriginalXML);
						//						trace("================================");	
						//						trace("length!=1"+ENVH.OriginalXML);
						//						trace("================================");							
					}
					
					OrDesignerModelLocator.getInstance().getOrchestraDesigner()
						.RollBack_TotalArray[nowArrayID]
						=OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp;
					//					trace("xml"+xml);
					//					trace("orignalxml"+OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML);		
					if(xml.children()!=OrDesignerModelLocator.getInstance().getOrchestraDesigner().OriginalXML.children())
					{
						OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp
							.push(xml);
						//						trace("++++++++++++++++++++++++++++++++++++");
						//						trace(OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp);
						//						trace("++++++++++++++++++++++++++++++++++++");	
						if(OrDesignerModelLocator.getInstance().getOrchestraDesigner()
							.RollBack_arraytemp.length == LIMITNUM + 2 )
							OrDesignerModelLocator.getInstance().getOrchestraDesigner()
								.RollBack_arraytemp.shift();
						
						OrDesignerModelLocator.getInstance().getOrchestraDesigner()
							.RollBack_TotalArray[nowArrayID]
							=OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp;
						
					}
				}
					
				else
				{
					tempOrXML = OrDesignerModelLocator.getInstance().getOrchestraDesigner()
						.RollBack_arraytemp[OrDesignerModelLocator.getInstance().getOrchestraDesigner()
							.RollBack_arraytemp.length - 1] as XML;
					
					if(tempOrXML!=xml){
						
						OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp
							.push(xml);
						//						trace("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						//						trace(OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp);
						//						trace("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");							
						if(OrDesignerModelLocator.getInstance().getOrchestraDesigner()
							.RollBack_arraytemp.length == LIMITNUM +2)
							OrDesignerModelLocator.getInstance().getOrchestraDesigner()
								.RollBack_arraytemp.shift();
						
						OrDesignerModelLocator.getInstance().getOrchestraDesigner()
							.RollBack_TotalArray[nowArrayID]
							=OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp;
						
					}
				}
				//				for change icon 
//				if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp.length==0 
//					|| OrDesignerModelLocator.getInstance().getOrchestraDesigner().RollBack_arraytemp.length==1)
//				{
//					DTVH.designerToolBar.rollbackButton.enabled = false;
//					DTVH.designerToolBar.rollbackButton.setStyle("icon",DTVH.designerToolBar.newrollback);
//					DTVH.designerToolBar.redoButton.enabled = false;
//					DTVH.designerToolBar.redoButton.setStyle("icon",DTVH.designerToolBar.newredo);
//				}
//				else
//				{
//					DTVH.designerToolBar.rollbackButton.enabled = true;
//					DTVH.designerToolBar.rollbackButton.setStyle("icon",DTVH.designerToolBar.rollback);
//					DTVH.designerToolBar.redoButton.setStyle("icon",DTVH.designerToolBar.redo);
//					//	DTVH.designerToolBar.ReDoButton.enabled = true;
//				}
				
				
			}
			
		}		
		
	}
}
