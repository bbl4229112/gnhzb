package org.act.od.impl.figure.bpmn
{
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.events.ResizeEvent;
	import mx.managers.PopUpManager;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.AttributeViewAppEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.figure.Activityow2Figure;
	import org.act.od.impl.figure.IFigure;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.FigureEditorNavigatorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.state.CreateConnectionStartState;
	import org.act.od.impl.view.FigureCanvas;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	import org.act.od.impl.vo.bpmn.PoolAttribute;
	import org.act.od.impl.vo.bpmn.PortType;
	
	
	public class Poolow2Figure extends Activityow2Figure
	{
		
		public static const FIGURE_TYPE : String = "Abstract_Pool";
		
		private var targetpositionmessageFlow : Number = 0;
		
		private var canvas : FigureCanvas;
		
		private var portTypes : Array = new Array;
		private var  portType1 : PortTypeFigure ;
		private var  portType2 : PortTypeFigure ;
		private var  portType3 : PortTypeFigure ;
		private var  portType4 : PortTypeFigure ;
		
		public var arryPortType : ArrayCollection;
		public var portType : PortType;
		
		// number of this pool in Canvas
		private var poolID: int = 0;
		
		// Window for select wsdl file
		private var fileWsdlUpload : Open;
		
		
		public function Poolow2Figure(processType:String=null)
		{
			this.getPopUpfileWsdlUpload();
			
			var figureENModel :FigureEditorNavigatorModel =	OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			var activeFEModel : FigureEditorModel = figureENModel.activeFigureEditorModel;
			var viewHelperKey : String  ;
			if(activeFEModel)
				viewHelperKey = FigureEditorVH.getViewHelperKey(activeFEModel.fileID);
			var feVH :FigureEditorVH;
			var figureCanvas : FigureCanvas;
			if(viewHelperKey!=null)
			{
				feVH = ViewLocator.getInstance().getViewHelper( viewHelperKey ) as FigureEditorVH;
				if(feVH!=null)
					figureCanvas = feVH.figureEditor.bpmnFigureCanvas;
				//var model : ToolPanelModel = OrDesignerModelLocator.getInstance().getToolPanelModel();
				if(figureCanvas!=null)
				{
					//figureCanvas.IncremNumPool();
					figureCanvas.addEventListener(ResizeEvent.RESIZE,Resize);
				}
			}
			
			super(processType);
			/**portTypes[0] = portType1;
			 portTypes[1] = portType2;
			 portTypes[2] = portType3;
			 portTypes[3] = portType4;*/
			
			this.attribute = new PoolAttribute();
			
			standardwidth=110;
			standardheight=38;
			width=standardwidth;
			height=standardheight;
			
			//Id of Poolow2Figure
			this.drawid=115;
			this.setpicture(BpmnFigureFactory.pool);
			
			//this.removeChild(this.picture);
			
			//this.setsize(0,figureCanvas.height-this.height);
			this.x = 0;
			this.percentWidth = 50;
			this.percentHeight = 20;
			if(figureCanvas!=null)
			{
				if(this.poolID==0) this.poolID = figureCanvas.NumPool;
				var z : int = 0;
				if(this.poolID==0) z = 1;
				this.y = figureCanvas.height + (this.poolID * this.height) - (z * this.height);
				this.width = figureCanvas.width-20;
				this.height = figureCanvas.height/5;
			}
			else
				this.y = 20;
			
			this.portType1 = BpmnFigureFactory.createFigure(120) as PortTypeFigure;
			this.portType2 = BpmnFigureFactory.createFigure(120) as PortTypeFigure;
			this.portType3 = BpmnFigureFactory.createFigure(120) as PortTypeFigure;
			this.portType4 = BpmnFigureFactory.createFigure(120) as PortTypeFigure;
			
			//this.initPortTypes();
			//this.initportTypesEvent();
			
			//BindingUtils.bindSetter(autosetsize,figureCanvas, "ok");
			//BindingUtils.bindProperty(this, "width", figureCanvas, "resizeX");
			//BindingUtils.bindProperty( this, "height", figureCanvas, "resizeY");
			
			
		}
		
		private function getPopUpfileWsdlUpload():void
		{
			// Window for select wsdl file
			fileWsdlUpload = Open(PopUpManager.createPopUp(OrDesignerModelLocator.getInstance().getOrchestraDesigner(),Open,true));
			PopUpManager.centerPopUp(fileWsdlUpload);
			fileWsdlUpload.addEventListener(CloseEvent.CLOSE, createPortTypeResult);
			
		}
		
		private function createPortTypeResult(event:CloseEvent):void
		{
			//var projectName:String=SaveFileWindow(event.currentTarget).getText();
//			this.portType = this.fileWsdlUpload.portType;
			//Alert.show("mon portType est : "+this.portType.LocalPart);
			this.initportTypes(this.portType);
			
		}
		
		private function initportTypes(portType : PortType ):void
		{
			var i:int = 0;
			var operations : Array = portType.getArrayOperations();
			for(i=0 ;i<operations.length; i++)
			{
				var positionX : Number;
				if(i==0)
					positionX = (120);
				else
					//positionX = PortTypeFigure(portTypes[i-1]).x + PortTypeFigure(portTypes[i-1]).figureName.length + 200;
					positionX = PortTypeFigure(portTypes[i-1]).x + PortTypeFigure(portTypes[i-1]).width + 200;
				//if(i!=0)
				//Alert.show("positionX "+i+"= "+ PortTypeFigure(portTypes[i-1]).x +" + "+ PortTypeFigure(portTypes[i-1]).figureName.length + " 62");
				
				var  newPortType : PortTypeFigure  = BpmnFigureFactory.createFigure(120) as PortTypeFigure;;
				newPortType.setposition(positionX, 10);
				newPortType.setNum(i+1);
				newPortType.figureName = operations[i].toString();
				
				if( ( positionX || (positionX + newPortType.width + 200) ) > this.width)
					this.width = this.width + newPortType.width + 100 + 300 ;
				
				this.addChild(newPortType);
				newPortType.updateDraw();
				newPortType.addEventListener(MouseEvent.DOUBLE_CLICK, portTypeMouseClic);
				
				portTypes[i] = newPortType;
			}
			
			/*var opActivity : Activityow2Figure = BpmnFigureFactory.createFigure(103) as Activityow2Figure;
			opActivity.setposition((this.width/6)*3/2,this.height/3);
			opActivity.figureName = "name";
			this.addChild(opActivity);
			opActivity.updateDraw();*/
		}
		
		private function updateDrawAllPortTypes():void
		{
			var i:int=0;
			for(i=0; i<this.portTypes.length; i++)
				PortTypeFigure(this.portTypes[i]).updateDraw();
		}
		
		private function portTypeMouseClic(event:MouseEvent):void
		{
			var messageflow : MessageFlowLinkFigure = BpmnFigureFactory.createFigure(119) as MessageFlowLinkFigure;
			OrDesignerModelLocator.getInstance().getFigureCanvasStateDomain().setFCActiveState(new CreateConnectionStartState());
			
			new FigureCanvasAppEvent(FigureCanvasAppEvent.Node_Create_Link,
				{linkType:"message_flow_link",startFigure :event.currentTarget} ).dispatch();
			
		}
				
		
		private function mouseDownHandle(event:MouseEvent):void
		{
			canvas.setFocus();
			
			if(canvas.verticalScrollBar){
				if(Canvas(event.currentTarget).mouseX>=canvas.verticalScrollBar.x
					&&Canvas(event.currentTarget).mouseX<=canvas.verticalScrollBar.x+canvas.verticalScrollBar.width
					&&Canvas(event.currentTarget).mouseY>=canvas.verticalScrollBar.y
					&&Canvas(event.currentTarget).mouseY<=canvas.verticalScrollBar.y+canvas.verticalScrollBar.height){
					return;
				}
			}
			if(canvas.horizontalScrollBar){
				if(Canvas(event.currentTarget).mouseX>=canvas.horizontalScrollBar.x
					&&Canvas(event.currentTarget).mouseX<=canvas.horizontalScrollBar.x+canvas.horizontalScrollBar.width
					&&Canvas(event.currentTarget).mouseY>=canvas.horizontalScrollBar.y
					&&Canvas(event.currentTarget).mouseY<=canvas.horizontalScrollBar.y+canvas.horizontalScrollBar.height){
					return;
				}
			}
			
			
			var ox :Number = FigureCanvas(event.currentTarget).mouseX;
			var oy :Number = FigureCanvas(event.currentTarget).mouseY;
			var point :Point = new Point(ox+canvas.horizontalScrollPosition,oy+canvas.verticalScrollPosition);
			
			//if selected, bubble the current figure's attributes event
			var tempFigure :IFigure = canvas.getFigureEditorModel().rootFigure.getupperfigure(point.x,point.y);
			new AttributeViewAppEvent(AttributeViewAppEvent.ATTRIBUTEVIEW_UPDATE,
				{selectedFigure:tempFigure} ).dispatch();
			
			
		}
		
		
		override public function drawpicture():void
		{
			//super.drawpicture();
			var figureENModel :FigureEditorNavigatorModel =	OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			var activeFEModel : FigureEditorModel = figureENModel.activeFigureEditorModel;
			var viewHelperKey : String  ;
			if(activeFEModel)
				viewHelperKey = FigureEditorVH.getViewHelperKey(activeFEModel.fileID);
			var feVH :FigureEditorVH;
			var figureCanvas : FigureCanvas;
			if(viewHelperKey!=null)
			{
				feVH = ViewLocator.getInstance().getViewHelper( viewHelperKey ) as FigureEditorVH;
				if(feVH!=null)
					figureCanvas = feVH.figureEditor.bpmnFigureCanvas;
				//var model : ToolPanelModel = OrDesignerModelLocator.getInstance().getToolPanelModel();
				
			}
			
			//this.setsize(0,figureCanvas.height-this.height);
			this.x = 0;
			if(figureCanvas!=null)
			{
				//this.y = figureCanvas.height-(this.poolID*this.height);
				var z : int = 0;
				//if(this.poolID==0)
				z = 1;
				this.y = figureCanvas.height + (this.poolID * this.height) - (z * this.height);
				if(this.width < figureCanvas.width)
					this.width = figureCanvas.width-20;
				this.height = 60;
			}
			this.drawclear();
			if( processType == FigureEditorModel.BPMN_PROCESS_TYPE)
			{
				sprt.graphics.lineStyle(this.defaultLineThickness*this.multiple,0x48618b,1);
				sprt.graphics.beginFill(0xe3e8f3,1);
			}
			else
			{
				sprt.graphics.beginFill(0xffffff,1);
				sprt.graphics.lineStyle(this.defaultLineThickness*this.multiple,0x000000,1);
			}
			//sprt.graphics.drawRect(0,figureCanvas.height-this.height,figureCanvas.width/2,this.height);
			sprt.graphics.drawRect(0,0,this.width,this.height);
			sprt.graphics.drawRect(0,0,60,this.height);
			sprt.graphics.endFill();
			
		}
		
		override public function setposition(x:Number,y:Number):void{
			var figureENModel :FigureEditorNavigatorModel =	OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			var activeFEModel : FigureEditorModel = figureENModel.activeFigureEditorModel;
			var feVH :FigureEditorVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(activeFEModel.fileID)) as FigureEditorVH;
			var figureCanvas : FigureCanvas = feVH.figureEditor.bpmnFigureCanvas;
			
			this.x = 0;
			this.y = figureCanvas.height-(this.poolID*this.height);
			
			this.rx=x+this.width/2;
			this.ry=y+this.height/2;
			
		}
		
		override public function isin(currentX:Number,currentY:Number):int
		{
			var ret:int=super.isin(currentX,currentY);
			if(ret>0){
				//return ret;
			}
			if(currentY>this.y && currentY<this.y+this.height)
			{
				if(currentX>this.x && currentX<this.x+(60) )
				{
					return 1;
				}
				else
				{
					var i : int = 0;
					for(i=0; i<this.portTypes.length; i++)
					{
						var portType : PortTypeFigure = portTypes[i] as PortTypeFigure;
						if(currentX >= portType.x && currentX < portType.x+portType.width)
						{
							return i+10;
						}
					}
					
				}
			}
			return 0;
		}
		
		public function Resize(event:ResizeEvent):void
		{
			var figureENModel :FigureEditorNavigatorModel =	OrDesignerModelLocator.getInstance().getFigureEditorNavigatorModel();
			var activeFEModel : FigureEditorModel = figureENModel.activeFigureEditorModel;
			var feVH :FigureEditorVH = ViewLocator.getInstance().getViewHelper(
				FigureEditorVH.getViewHelperKey(activeFEModel.fileID)) as FigureEditorVH;
			var figureCanvas : FigureCanvas = feVH.figureEditor.bpmnFigureCanvas;
			
			if(this.width < figureCanvas.width)
				this.width = figureCanvas.width - 20;
			//this.y = figureCanvas.height-(this.poolID*this.height);
			this.updateDraw();
			
			//this.initportTypes(this.portType);
			this.updateDrawAllPortTypes();
		}
		
		
		public function getPortTypeWithNumber(num : int):PortTypeFigure
		{
			return this.portTypes[num-1];
		}
		
		
		public function get PortTypes():Array
		{
			return portTypes
		}
		
		
		
	}
}