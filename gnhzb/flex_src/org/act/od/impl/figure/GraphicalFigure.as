package org.act.od.impl.figure
{
	import flash.events.ContextMenuEvent;
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuBuiltInItems;
	import flash.ui.ContextMenuItem;
	
	import mx.binding.utils.BindingUtils;
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.controls.Alert;
	import mx.controls.Label;
	import mx.messaging.messages.AsyncMessage;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.CooperateOperationEvent;
	import org.act.od.impl.events.DesignerToolBarAppEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.events.LabelNodeEvent;
	import org.act.od.impl.figure.ProcessFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.NodeContextPanel;
	import org.act.od.impl.view.bpmn.BpmnFigureData;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.vo.bpmn.BpmnDataComponent;
	import mx.core.Application;
	
	public class GraphicalFigure extends AbstractFigure
	{
		//		[Bindable]
		//		public var nodeName:String = "No Name";
		
		protected var startConnection:Array;
		protected var endConnection:Array;
		
		protected var nodeContextPanel:NodeContextPanel;
		protected var isShowContextPanel:Boolean=false;
		protected var lblNodeName:Label;
		
		//----------------------------------------------------
		
		protected var dataAttributes: ArrayCollection = new ArrayCollection();
		protected var gestionVar :ContextMenuItem;
		//---------------------------------------------
		private var canvasXML:XML = new XML();
		private var operationtype:String=new String();
		private var asymessage:AsyncMessage=new AsyncMessage();

		//	for new cooperation
		private var xArray_Move:Array=new Array();
		private var rxArray_Move:Array=new Array();
		private var widthArray_Move:Array = new Array();
		private var standardWidth_Move:Array = new Array();
		private var idArray_Move:Array=new Array();
		private var idArray_Delete:Array = new Array();
		private var figureName_Move:Array = new Array();
		
		private var xmlFileId:String = Application.application.parameters.xmlFileId + ""; //没有什么是不可能的。20120928
		private var xmlFileName:String = Application.application.parameters.xmlFileName;
		
		public function GraphicalFigure(processType:String=null)
		{
			super(processType);
			startConnection=new Array();
			endConnection=new Array();
			nodeContextPanel=new NodeContextPanel();
			nodeContextPanel.init();
			this.lblNodeName=new Label();
			
			
			BindingUtils.bindProperty(this.lblNodeName, "text", this, "figureName");
			BindingUtils.bindProperty(this.nodeContextPanel,"nodeName",this,"figureName");
			
			/* Add the option (rigth clic) of upadate Data*/
			
			if(processType==FigureEditorModel.BPMN_PROCESS_TYPE)
			{
				gestionVar  = new ContextMenuItem("Update Data");
				var contextMenu: ContextMenu = new ContextMenu();
				contextMenu.hideBuiltInItems();
				var defaultItems : ContextMenuBuiltInItems = contextMenu.builtInItems;
				defaultItems.print = false;
				contextMenu.customItems.push(gestionVar);
				this.contextMenu = contextMenu;
				/* Add rigth clic event */
				this.gestionVar.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, upateData);
			}
						
			this.lblNodeName.setStyle("verticalCenter", 0);
			this.lblNodeName.selectable = false;
			this.lblNodeName.setStyle("textAlign","center");
			this.lblNodeName.setStyle("fontWeight","bold");
			this.addEventListener("removeNode", handleRemoveNode);
			this.addEventListener("createLink",handleCreateLink);
			this.addEventListener(LabelNodeEvent.LABEL_NODE, handleLabelNode);
			
			//	this.addEventListener("createMessageFlowLink",handleCreateMessageFlowLink);
			//增加右键菜单“打开子流程”，20120830，20120907修改。
			if(this is Circuitow2Figure){         //只对可以建立下层模型的业务活动节点类型设置监听。20120925
				this.addEventListener(MouseEvent.ROLL_OVER,rollOverHandler);
				this.addEventListener(MouseEvent.ROLL_OUT,rollOutHandler);
				this.addEventListener(MouseEvent.DOUBLE_CLICK,doubleClickHandler);
			}
		}
		
		public function rollOverHandler(e:Event):void{
			var rightMenu:ContextMenu = new ContextMenu();
			var item1:ContextMenuItem = new ContextMenuItem("打开下层模型");
			var item2:ContextMenuItem = new ContextMenuItem("建立下层模型");
			item1.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT,function(){
				openDownModel(e);
			});
			item2.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT,function(){
				var feNavVH :EditorNavigatorVH = 
				ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
				var feNavModel :FigureEditorNavigatorModel =
				OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
				var num:int = feNavVH.getNum() as int;
				var graphicalFigure:GraphicalFigure = e.currentTarget as GraphicalFigure;
				//				var sonxml:XML = GraphicalFigure.sonXML;
				var sonmodel:FigureEditorModel = graphicalFigure.sonFigureEditorModel;
				if(sonmodel == null){
					var fileName:String = "\""+graphicalFigure.lblNodeName.text.toString()+"\"节点的下层模型";
					var fileType:String = "BPEL";
					var fileCategory:String = "BasicData";
					var fatherFileId:String = feNavVH.getSelectedChildOfNavigator().fileID;
					var fatherFigureId:int = graphicalFigure.ID;
					var fatherFigureName:String = graphicalFigure.figureName;
					var fathefigureeditormodel:FigureEditorModel = feNavModel.activeFigureEditorModel;
					fathefigureeditormodel.rootFigure.outputAllInformation();
					new DesignerToolBarAppEvent(DesignerToolBarAppEvent.NEW_FILE,
						{fileName:fileName , fileType:fileType ,fileCategory:fileCategory,fatherFigure:graphicalFigure,fathefigureeditormodel:fathefigureeditormodel,fatherFigureId:fatherFigureId}).dispatch();
					fathefigureeditormodel.rootModel.updateCanvasXML();
				}else{
					Alert.show("该节点已有下层模型!","提示");
				}
				
			});
			rightMenu.hideBuiltInItems();
//			rightMenu.customItems.push(item2);
			rightMenu.customItems.push(item1);
//			this.contextMenu = rightMenu;
		}
		public function doubleClickHandler(e:Event):void{
			openDownModel(e);
		}
		public function openDownModel(e:Event):void{
			var feNavVH :EditorNavigatorVH = 
				ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
			var graphicalFigure:GraphicalFigure = e.currentTarget as GraphicalFigure;
			if(graphicalFigure.sonFigureEditorModel != null){
				var fatherFigureEditorModel:FigureEditorModel = graphicalFigure.sonFigureEditorModel.fatherModel;
				
				var fileName:String = "\""+graphicalFigure.lblNodeName.text.toString()+"\"节点的下层模型";
				var fileType:String = "BPEL";
				var fileCategory:String = "BasicData";
				var fatherFileId:String = feNavVH.getSelectedChildOfNavigator().fileID;
				var fatherFigureId:int = graphicalFigure.ID;
				var fileID:String = graphicalFigure.sonFigureEditorModel.fileID;
				
				new FigureEditorNavigatorAppEvent(FigureEditorNavigatorAppEvent.ACTIVE_FIGUREEDITOR_PAGE,
					{ fileID:fileID,fileName:fileName,figureEditorModel:graphicalFigure.sonFigureEditorModel}
				).dispatch();
				
			}else{
				Alert.show("该节点没有下层模型!","提示");
			}
		}
		public function rollOutHandler(e:Event):void{
			//不写代码也无妨——不要方法也无妨。20120907
//			var rightMenu:ContextMenu = this.contextMenu;
//			var n:int =rightMenu.
//			for(var i:int = 0;i<n;i++){
//				orchestra_Designer.contextMenu.items.pop();
//			}
		}
		
		
		override public function setIsShowContextPanel(isShowContextPanel:Boolean):void{
			this.isShowContextPanel=isShowContextPanel;
		}
		
		override public function getIsShowContextPanel():Boolean{
			return this.isShowContextPanel;
		}
		
//		when change the figure's label text
		public function handleLabelNode(event : LabelNodeEvent):void{
			this.figureName=event.value;
			this.updateDraw();
			this.setFigureSizeAccordingTolblNodeName();
			this.updateDraw();
			//			this.updateDraw();
			this.hideContextPanel();
			new FigureCanvasAppEvent(FigureCanvasAppEvent.Canvas_Children_Changed,
				{canvas :null}).dispatch();
			this.cooperation();
		}
		
		protected function removeNodeContextPanel():void {
			this.nodeContextPanel.currentState = null;
			if(this.contains(this.nodeContextPanel)){
				this.removeChild(this.nodeContextPanel);
			}
		}
		
		override public function showContextPanel():void{
			if(!this.contains(this.nodeContextPanel)) {
				this.nodeContextPanel.resize.end();
				this.nodeContextPanel.y =((this.height / 2) - (this.nodeContextPanel.height / 2));
//				if(this is Poolow2Figure)
//					this.nodeContextPanel.x = 10;
//				else

//				modified by mengsong 2010-7-5 16:24:16
				this.nodeContextPanel.x =(this.width + 5);
				this.addChild(this.nodeContextPanel);
			}
		}
		
		override public function hideContextPanel():void{
			if(this.contains(this.nodeContextPanel)){
				this.removeChild(this.nodeContextPanel);
			}
			this.nodeContextPanel.resetAll(this.processType);
		}
//		when click the delete from the pop panel
		public function handleRemoveNode(event:Event):void{
			var headarr:Array=new Array();
			var tailarr:Array=new Array();
			if(this.contains(this.nodeContextPanel)){
				this.removeChild(this.nodeContextPanel);
			}
			if(this.getrootfigure()){
				this.getrootfigure().removechildWithConnection(this,headarr,tailarr);
			}
			if(this.parent){
				this.parent.removeChild(this);
			}
			this.cooperation4DeleteFigure(headarr,tailarr);
		}
		
		public function handleCreateLink(event:Event):void{
			var feNavModel:FigureEditorNavigatorModel=OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			if(feNavModel.activeFigureEditorModel==null){
				throw new Error("no ActiveFigureEditorModel!");
			}
			this.hideContextPanel();
			new FigureCanvasAppEvent(FigureCanvasAppEvent.Node_Create_Link,
				{fileID :feNavModel.activeFigureEditorModel.fileID ,startFigure :this} ).dispatch();
//			this.cooperation();
		}
		private function cooperation4DeleteFigure(headArray:Array,tailArray:Array):void
		{
			this.getXML();
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==true)
			{
				this.operationtype="figuredelete";
				//			get the basic attributes of the figure except the Link FIgure
				idArray_Delete.push(this.getID());
				if(headArray!=null)
				for(var i:int=0;i<headArray.length;i++)
					idArray_Delete.push(headArray[i].ID.toString());
				if(tailArray!=null)
				for(var j:int=0;j<tailArray.length;j++)
					idArray_Delete.push(tailArray[j].ID.toString());
				var activeFEModel:FigureEditorModel=OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel().activeFigureEditorModel;
				if (activeFEModel != null)
				{
					asymessage.headers.delete_id=idArray_Delete.join(",");
					asymessage.headers.modify_type=operationtype;
					new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION, {message_move: asymessage}).dispatch();
				}
			}
		}
		private function cooperation():void{
			//	For cooperate
			this.getXML();
			//			added by mengsong
			//			cooperation state is true
			if(OrDesignerModelLocator.getInstance().getOrchestraDesigner().cooperateState==true)
			{
				this.prepair4cooperation();
				this.doCooperation();
			}
		}		
		private function prepair4cooperation():void
		{
//			var i:int=0;
			this.operationtype="graphicifigure";
			//			get the basic attributes of the figure except the Link FIgure
			idArray_Move.push(this.getID());
			xArray_Move.push(this.getx());
			rxArray_Move.push(this.getrx());
			widthArray_Move.push(this.getwidth());
			standardWidth_Move.push(this.getstandardwidth());
			figureName_Move.push(this.figureName);
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
				asymessage.headers.modify_x=xArray_Move.join(",");
				asymessage.headers.modify_rx=rxArray_Move.join(",");
				asymessage.headers.modify_id=idArray_Move.join(",");
				asymessage.headers.modify_width=widthArray_Move.join(",");
				asymessage.headers.modify_standardWidth=standardWidth_Move.join(",");
				asymessage.headers.modify_type=operationtype;
				asymessage.headers.modify_figurename = figureName_Move.join(",");
				new CooperateOperationEvent(CooperateOperationEvent.COSENDMESSAGECOLLECTION, {message_move: asymessage}).dispatch();
			}
		}
		
		
		/**
		 *
		 * @param event
		 *
		 */
		public function handleCreateMessageFlowLink(event:Event):void{
			var feNavModel:FigureEditorNavigatorModel=OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			if(feNavModel.activeFigureEditorModel==null){
				throw new Error("no ActiveFigureEditorModel!");
			}
			this.hideContextPanel();
			new FigureCanvasAppEvent(FigureCanvasAppEvent.Node_Create_MessageFlow_Link,
				{fileID :feNavModel.activeFigureEditorModel.fileID ,startFigure :this ,linkType :"message_flow_link"} ).dispatch();
		}
		
		
		public function getStartConnection():Array{
			return startConnection;
		}
		
		public function getEndConnection():Array{
			return endConnection;
		}
		
		
		/**
		 *
		 * @param end : Destination point
		 * @param startOperation : If this Object is start Operation portType or end Operation PortType
		 * @return selected point in this  object (this compenent)
		 *
		 */
		public function getEdgePoint(end:Point, startOperation:Boolean=true):Point
		{
			return new Point(this.getrx(),this.getry());
		}
		
		public function updateConnection():void{
			var i:int;
			var temp_CF:ConnectionFigure;
			for(i=0;i<startConnection.length;i++){
				temp_CF=ConnectionFigure(this.startConnection[i]);
				temp_CF.autoSetAnchor();
			}
			for(i=0;i<endConnection.length;i++){
				temp_CF=ConnectionFigure(this.endConnection[i]);
				temp_CF.autoSetAnchor();
			}
		}
		
		override public function setposition(x:Number, y:Number):void{
			updateConnection();
		}
		
		override public function autosetsize(arrowX:Number, arrowY:Number, mode:Number):void{
			updateConnection();
		}
		
		override public function updateDraw():void{
			var i:int;
			for(i=0;i<startConnection.length;i++){
				ConnectionFigure(this.startConnection[i]).autoSetAnchor();
				ConnectionFigure(this.startConnection[i]).updateDraw();
			}
			for(i=0;i<endConnection.length;i++){
				ConnectionFigure(this.endConnection[i]).autoSetAnchor();
				ConnectionFigure(this.endConnection[i]).updateDraw();
			}
			super.updateDraw();
			if(!this.contains(this.lblNodeName)){
				this.addChild(this.lblNodeName);
			}
			this.lblNodeName.validateNow();
			if(this.lblNodeName.textWidth.toString()!="NaN"){
				lblNodeName.width=this.lblNodeName.textWidth+5;
				lblNodeName.height=this.lblNodeName.textHeight+3;
				this.setlblNodeNamePosition();
				if(doChangeSize()){
					this.setFigureSizeAccordingTolblNodeName();
				}
				if(this.lblNodeName.textWidth){
					autoSetStandardWidth();
				}
				if(this.lblNodeName.textHeight){
					autoSetStandardHeight();
				}
			}
			if(this.isShowContextPanel){
//				this.showContextPanel();    单击figure，不需要弹出panel了，20120908.
				if(xmlFileId != null  && xmlFileName != null ) {
					this.showContextPanel();   
					
				} else {
					this.hideContextPanel();
				}
			}
			else{
				this.hideContextPanel();
			}
		}
		
		
		protected function setlblNodeNamePosition():void
		{
			lblNodeName.x=this.width/2-this.lblNodeName.width/2;
			lblNodeName.y=this.height+4;
		}
		protected function doChangeSize():Boolean{
			return false;
		}
		public function setFigureSizeAccordingTolblNodeName():void{
			
		}
		protected function autoSetStandardWidth():void{
			
		}
		protected function autoSetStandardHeight():void{
			
		}
		
		protected function pointLine(x1:Number,y1:Number,x2:Number,y2:Number,px:Number,py:Number):Boolean{
			if(py<(y2-y1)/(x2-x1)*(px-x1)+y1){
				return true;
			}
			else{
				return false;
			}
		}
		
		override public function getContextPanel():Canvas{
			return this.nodeContextPanel;
		}
		
		override public function isin(currentX:Number, currentY:Number):int{
			if(this.contains(this.nodeContextPanel)){
				if(currentX>=this.x+this.nodeContextPanel.x&&currentX<=this.x+this.nodeContextPanel.x+this.nodeContextPanel.width&&currentY>=this.y+this.nodeContextPanel.y&&currentY<=this.y+this.nodeContextPanel.y+this.nodeContextPanel.height){
					return 2;
				}
			}
			return super.isin(currentX,currentY);
		}
		override public function drawclear():void{
			super.drawclear();
		}
		override public function outputAllInformation():XML{
			var info:XML=super.outputAllInformation();
			info.@figureName=this.figureName;
			
			return info;
		}
		
		override public function readInformationToFigure(info:XML,rootFigureEditorModel:FigureEditorModel,fatherFigureEditorModel:FigureEditorModel):void{
			super.readInformationToFigure(info,rootFigureEditorModel,fatherFigureEditorModel);
			this.figureName=String(info.@figureName);
		}
		
		override protected function OutputScale(mtp:Number):void{
			super.OutputScale(mtp);
			if(this.lblNodeName)
				this.lblNodeName.setStyle("fontSize",this.fontsize);
		}
		
		private  function  upateData(event:Event):void
		{
			var bpmnFigureData : BpmnFigureData = new  BpmnFigureData();
			bpmnFigureData.width=300;
			bpmnFigureData.height=300;
			var start:GraphicalFigure = new Startow2Figure();
			var dataCompnt : BpmnDataComponent = new BpmnDataComponent("int","0");
			start.addInDataAttributes(dataCompnt);
			dataCompnt = new BpmnDataComponent("int","5");
			start.addInDataAttributes(dataCompnt);
			dataCompnt = new BpmnDataComponent("string","Houssem");
			start.addInDataAttributes(dataCompnt);
			//bpmnFigureData.initDataGrid(start.getDataAttributes());
			//bpmnFigureData.save();
			this.addChild(bpmnFigureData);
			
		}
		
		
		/**
		 *
		 * @return dataAttributes
		 *
		 */
		protected function  addInDataAttributes(dataComponent:BpmnDataComponent):void
		{
			if(this.dataAttributes==null)
			{
				this.dataAttributes = new ArrayCollection();
			}
			this.dataAttributes.addItem(dataComponent);
			
		}
		
		/**
		 *
		 * Set dataAttributes
		 *
		 */
		public function  setDataAttributes(dataAttributes: ArrayCollection):void
		{
			this.dataAttributes = dataAttributes;
		}
		
		/**
		 *
		 * Get dataAttributes
		 *
		 */
		public function  getDataAttributes():ArrayCollection
		{
			return this.dataAttributes;
		}
		
		/**
		 *
		 * Get lblNodeName attribute
		 *
		 */
		public function  get LblNodeName():Label
		{
			return this.lblNodeName;
		}
		
		private function getXML():void
		{
			this.canvasXML = ProcessFigure(OrDesignerModelLocator.getInstance().figureEditorNavigatorModel.activeFigureEditorModel.rootFigure)
				.outputAllInformation();
		}
	}
}