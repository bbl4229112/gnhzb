package org.act.od.impl.view{
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.text.TextField;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.containers.Panel;
	import mx.controls.Alert;
	import mx.controls.Tree;
	import mx.core.Container;
	import mx.core.DragSource;
	import mx.core.IUIComponent;
	import mx.core.UIComponent;
	import mx.effects.Fade;
	import mx.effects.IEffectInstance;
	import mx.effects.Iris;
	import mx.effects.Parallel;
	import mx.effects.Rotate;
	import mx.effects.WipeDown;
	import mx.events.ChildExistenceChangedEvent;
	import mx.events.DragEvent;
	import mx.events.FlexEvent;
	import mx.events.ResizeEvent;
	import mx.events.ScrollEvent;
	import mx.events.ScrollEventDirection;
	import mx.graphics.ImageSnapshot;
	import mx.managers.DragManager;
	
	import org.act.od.framework.view.ViewLocator;
	import org.act.od.impl.events.AttributeViewAppEvent;
	import org.act.od.impl.events.FigureCanvasAppEvent;
	import org.act.od.impl.events.FigureEditorNavigatorAppEvent;
	import org.act.od.impl.events.KnowledgeViewAppEvent;
	import org.act.od.impl.figure.*;
	import org.act.od.impl.model.*;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.viewhelper.EditorNavigatorVH;
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	/**
	 * Canvas edit area
	 * 
	 * @author Quyue
	 *
	 */
	public class FigureCanvas extends Canvas{
		
		//		test for layer
		public var signlayer:Canvas = new Canvas();
		public var comparelayer:FigureCanvas;
	
		private static var grapWidth: Number = 25;
		
		private var _width :Number;
		
		private var _height :Number;
		
		//FigureEditorModel
		public var _editorModel :FigureEditorModel;
		
		private var orDesModelLoc :OrDesignerModelLocator = OrDesignerModelLocator.getInstance();
		
		private var processType :String = FigureEditorModel.BPEL_PROCESS_TYPE;
		
		// number of existing Abstruct Pool
		private var numPool : int = 0;
		
		[Bindable]
		public var resizeX : Number;
		[Bindable]
		public var resizeY : Number;
		
		
		//		[Bindable]
		//		private var aaa:TextField = new TextField();
		
		/**
		 * Create a new canvas by the figureEditorModel.
		 */
		public function FigureCanvas(figureEditorModel :FigureEditorModel, processType :String = "BPEL"){
			super();
			
			//			this.addChild(aaa);
			//			aaa.text = this.width as String;
			
			this.processType = processType;
			
			this.styleName="GraphicalViewerStyle";
			this.percentWidth = 100;
			this.percentHeight = 100;
			
			this.setFigureEditorModel(figureEditorModel);

			this.notifyLevelModelUpdate();
			this.doubleClickEnabled = true;
			this.initEventListener();

			this.graphics.lineStyle( 2, 0xff0000, 1);
			this.graphics.drawCircle(50,50,50);
			
			var obj:UIComponent = this.getChildByName("boundflag") as UIComponent;
			if(obj == null){
				obj = new UIComponent();
				this.addChild(obj);
				obj.x = 2000;
				obj.y = 500;
				obj.name = "boundflag";
			}
		}
		
		
		private function initEventListener() :void{
			
			//draw vertical or hierachical lines on the canvas
			this.addEventListener(FlexEvent.CREATION_COMPLETE, onInitializeHandle);
			
			this.addEventListener(ResizeEvent.RESIZE, repaintCanvasHandle);
			
			this.addEventListener(MouseEvent.MOUSE_MOVE, mouseMoveHandle);
			
			this.addEventListener(MouseEvent.MOUSE_DOWN, mouseDownHandle);
			
			this.addEventListener(MouseEvent.MOUSE_UP, mouseUpHandle);//这个方法不能去掉，去掉了点击别的figure时不会有环绕点。20120925
			
			this.addEventListener(KeyboardEvent.KEY_DOWN, keyDownHandle);
			
			this.addEventListener(MouseEvent.CLICK, mouseClickHandle);
			
			this.addEventListener(MouseEvent.ROLL_OUT, mouseRollOutHandle);
			
			this.addEventListener(MouseEvent.ROLL_OVER, mouseRollOverHandle);
			
			this.addEventListener(FlexEvent.SHOW, showEffectHandle);
			
			this.addEventListener(DragEvent.DRAG_ENTER, dragEnterHandle);
			
			this.addEventListener(DragEvent.DRAG_DROP, dragDropHandle);
			
			this.addEventListener(DragEvent.DRAG_OVER, dragOverHandle);
			
//			this.addEventListener(MouseEvent.DOUBLE_CLICK, doubleClickHandle);     不需要了，20120911.
			
			this.addEventListener(ResizeEvent.RESIZE,resizeHandle);
			
			this.addEventListener(FlexEvent.SHOW,showHandle);
			
			this.addEventListener(ScrollEvent.SCROLL,scrollHandle);
			
		}
		
		
		/**
		 * Update the Microimage show.
		 */
//		public function notifyMicroimageUpdate():void{
//			
//			new FigureCanvasAppEvent(FigureCanvasAppEvent.Canvas_Children_Changed,
//				{canvas :this}).dispatch();
//			
//		}
		
		/**
		 * 更新模型层次图
		 */
		public function notifyLevelModelUpdate():void{
			this._editorModel.updateCanvasXML();
			new FigureCanvasAppEvent(FigureCanvasAppEvent.Canvas_Children_Changed,
				{canvas :this}).dispatch();
			
		}
		
		/**
		 * Get the figureEditorModel
		 */
		public function getFigureEditorModel() :FigureEditorModel
		{
			return this._editorModel;
		}
		
		/**
		 * Set the figureEditorModel
		 */
		public function setFigureEditorModel(feModel :FigureEditorModel) :void {
			this._editorModel = feModel;
			feModel.setFigureCanvas(this);
		}
		/**
		 * Change the width of the canvas.
		 */
		public function changeWidth(newWidth :Number) :void {
			this.width = newWidth;
		}
		
		//		for subProcessFigure
		private function doubleClickHandle(event : MouseEvent) : void {
			this.setFocus();
			var ox :Number = FigureCanvas(event.currentTarget).mouseX;
			var oy :Number = FigureCanvas(event.currentTarget).mouseY;
			var point :Point = new Point(ox+this.horizontalScrollPosition,oy+this.verticalScrollPosition);
			
			var tempFigure :IFigure = this._editorModel.rootFigure.getupperfigure(point.x,point.y);
			if(tempFigure is SubProcessow2Figure) {
				var figureEditorVH :FigureEditorVH =
					ViewLocator.getInstance().getViewHelper(FigureEditorVH.getViewHelperKey(this._editorModel.fileID)) as FigureEditorVH;
				
				SubProcessow2Figure(tempFigure).filePath = figureEditorVH.filePath;
				new FigureCanvasAppEvent(FigureCanvasAppEvent.OPEN_SUBPROCESS,
					{subProcessFigure:tempFigure}).dispatch();
			}
		}
		//		drag from the navigator to the figureCanvas
		
		private function dragOverHandle(event :DragEvent):void{
			if(event.dragInitiator is Tree)
				DragManager.showFeedback(DragManager.COPY);
		}
		private function dragEnterHandle(event:DragEvent):void {
			var ds :DragSource = event.dragSource;
			if(ds.hasFormat("items"))
				DragManager.acceptDragDrop((event.target as IUIComponent));
			else if(ds.hasFormat("treeItems")) {
				DragManager.acceptDragDrop((event.target as IUIComponent));
			}
		}
		
		private function dragDropHandle(event:DragEvent):void {
			
			if(event.dragSource.hasFormat("items")) {
				var items:Array = event.dragSource.dataForFormat("items") as Array;
				
				for each(var xml:XML in items) {
					var serviceName:XMLList = xml.ServiceName;
					var serviceLoc:XMLList = xml.ServiceLocation;
					var new_atts:ArrayCollection = new ArrayCollection();
					new_atts.addItem({name: "Name: ", value: serviceName.text()});
					
					var drawId:int = FigureFactory.nametoid("Invoke");
					var invokeFigure:Invokeow2Figure = Invokeow2Figure(FigureFactory.createFigure(drawId));
					invokeFigure.setID(this._editorModel.idManager.getAvailabelId());
					invokeFigure.figureName = serviceName.text();
					invokeFigure.setAttribute(new_atts);
					invokeFigure.setMultiple(this._editorModel._showingMultiple);
					invokeFigure.setxy(event.localX,event.localY);
					
					this.addChild(invokeFigure);
					this._editorModel.rootFigure.addchild(invokeFigure);
					invokeFigure.updateDraw();
				}
			}
			else if(event.dragSource.hasFormat("treeItems")) {
				var treeItems : Array = event.dragSource.dataForFormat("treeItems") as Array;
				for each(var treeXml : XML in treeItems) {
					if(treeXml.@type != EditorNavigatorChild.FIGURE_EDITOR_TYPE)
						return;
					var idXML : XMLList = treeXml.@ID;
					var fileNameXNL : XMLList = treeXml.@name;
					var fileId : String = idXML.toString();
					var fileName : String = fileNameXNL.toString();
					
					var sbDrawId : int = FigureFactory.nametoid("SubProcess");
					var subProcessFigure : SubProcessow2Figure = SubProcessow2Figure(FigureFactory.createFigure(sbDrawId));
					subProcessFigure.setID(this._editorModel.idManager.getAvailabelId());
					subProcessFigure.setMultiple(this._editorModel._showingMultiple);
					subProcessFigure.setxy(event.localX,event.localY);
					subProcessFigure.setSubProcessFileName(fileName);
					subProcessFigure.setSubProcessFileID(fileId);
					
					this.addChild(subProcessFigure);
					this._editorModel.rootFigure.addchild(subProcessFigure);
					subProcessFigure.updateDraw();
				}
			}
			
//			this.notifyMicroimageUpdate();
//			this.notifyLevelModelUpdate();
		}
		
		/**
		 *
		 * @param event
		 *
		 */
		private function onInitializeHandle(event :Event): void{
			renderCanvas(this.processType);
			
			initSignLayer();
			initCompareLayer();
//			this.notifyLevelModelUpdate();
		}
		
		/**
		 *
		 * @param event
		 *
		 */
		private function repaintCanvasHandle(event :ResizeEvent) :void {
			renderCanvas(this.processType);
			
			this.resizeX = this.x;
			this.resizeY = this.y;
		}
		
		
		/**
		 * Delegate
		 * @param event
		 *
		 */
		private function mouseMoveHandle(event:MouseEvent):void{
			var ox :Number = FigureCanvas(event.currentTarget).mouseX;
			var oy :Number = FigureCanvas(event.currentTarget).mouseY;
			var point :Point = new Point( ox+this.horizontalScrollPosition, oy+this.verticalScrollPosition );
			orDesModelLoc.getFigureCanvasStateDomain().mouseMove(point,event);
			//			notifyLevelModelUpdate();
			//						this.notifyMicroimageUpdate();
			
		}
		
		
		/**
		 *
		 * @param event
		 *
		 */
		private function mouseDownHandle(event:MouseEvent):void{
			
			
			this.setFocus();
			
			if(this.verticalScrollBar){
				if(Canvas(event.currentTarget).mouseX>=this.verticalScrollBar.x
					&&Canvas(event.currentTarget).mouseX<=this.verticalScrollBar.x+this.verticalScrollBar.width
					&&Canvas(event.currentTarget).mouseY>=this.verticalScrollBar.y
					&&Canvas(event.currentTarget).mouseY<=this.verticalScrollBar.y+this.verticalScrollBar.height){
					return;
				}
			}
			if(this.horizontalScrollBar){
				if(Canvas(event.currentTarget).mouseX>=this.horizontalScrollBar.x
					&&Canvas(event.currentTarget).mouseX<=this.horizontalScrollBar.x+this.horizontalScrollBar.width
					&&Canvas(event.currentTarget).mouseY>=this.horizontalScrollBar.y
					&&Canvas(event.currentTarget).mouseY<=this.horizontalScrollBar.y+this.horizontalScrollBar.height){
					return;
				}
			}
			
			
			var ox :Number = FigureCanvas(event.currentTarget).mouseX;
			var oy :Number = FigureCanvas(event.currentTarget).mouseY;
			var point :Point = new Point(ox+this.horizontalScrollPosition,oy+this.verticalScrollPosition);
			
			//if selected, bubble the current figure's attributes event
			var tempFigure :IFigure = this._editorModel.rootFigure.getupperfigure(point.x,point.y);
			
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().showFigureAttribute();
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().showKnowledgeInput();
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().showKnowledgeRelated();
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().showKnowledgeOutput();
			OrDesignerModelLocator.getInstance().getOrchestraDesigner().getfigureAttributeNavigator().showBestPractice();
				
			new AttributeViewAppEvent(AttributeViewAppEvent.ATTRIBUTEVIEW_UPDATE,
				{selectedFigure:tempFigure} ).dispatch();
			new KnowledgeViewAppEvent(KnowledgeViewAppEvent.KNOWLEDGEINPUT_UPDATE,
				{selectedFigure:tempFigure}).dispatch();
			new KnowledgeViewAppEvent(KnowledgeViewAppEvent.KNOWLEDGEVIEW_UPDATE,
				{selectedFigure:tempFigure}).dispatch();
			new KnowledgeViewAppEvent(KnowledgeViewAppEvent.KNOWLEDGEOUTPUT_UPDATE,
				{selectedFigure:tempFigure}).dispatch();
			if(tempFigure == null) {
				OrDesignerModelLocator.getInstance().getOrchestraDesigner().bottomMdBox.height = 1;
			}			
//			new KnowledgeViewAppEvent(KnowledgeViewAppEvent.KNOWLEDGERELATED_POPUP,
//				{selectedFigure:tempFigure}).dispatch();
			
		/*
			//			by mengsong:......French...
			
			//ajouter pour la 3 eme fonctionnalitee
			if(tempFigure is GraphicalFigure )
			{
			var empGraphicalFigure : GraphicalFigure = GraphicalFigure(tempFigure);
			new AttributeViewAppEvent(AttributeViewAppEvent.DATA_ATTRIBUTEVIEW_UPDATE,
			{selectedFigure:tempFigure} ).dispatch();
			}*/
			
			//Delegate
			orDesModelLoc.getFigureCanvasStateDomain().mouseDown(point, event);
			
			
			//			this.notifyMicroimageUpdate();
			//			this.notifyLevelModelUpdate();
		}
		
		
		/**
		 * Delegate
		 * @param event
		 *
		 */
		private function mouseUpHandle(event:MouseEvent):void{
			var ox :Number = FigureCanvas(event.currentTarget).mouseX;
			var oy :Number = FigureCanvas(event.currentTarget).mouseY;
			var point :Point = new Point(ox+this.horizontalScrollPosition,oy + this.verticalScrollPosition);
			orDesModelLoc.getFigureCanvasStateDomain().mouseUp(point, event);
			
			
//			this.notifyMicroimageUpdate();
			this.notifyLevelModelUpdate();
		}
		
		
		/**
		 * Delegate
		 * @param event
		 *
		 */
		private function keyDownHandle(event:KeyboardEvent):void{
			orDesModelLoc.getFigureCanvasStateDomain().keyDown(event);
			
			//			this.notifyMicroimageUpdate();
			//			this.notifyLevelModelUpdate();
		}
		
		
		/**
		 * Delegate
		 * @param event
		 *
		 */
		private function mouseClickHandle(event:MouseEvent):void{
			var ox:Number = FigureCanvas(event.currentTarget).mouseX;
			var oy:Number = FigureCanvas(event.currentTarget).mouseY;
			var point:Point = new Point(ox+this.horizontalScrollPosition,oy+this.verticalScrollPosition);
			orDesModelLoc.getFigureCanvasStateDomain().mouseClick(point, event);
			
			
			//			this.notifyMicroimageUpdate();
			//			this.notifyLevelModelUpdate();
		}
		
		
		/**
		 * Delegate
		 * @param event
		 *
		 */
		private function mouseRollOutHandle(event:MouseEvent):void{
			orDesModelLoc.getFigureCanvasStateDomain().RollOut(event);
			
			//			this.notifyMicroimageUpdate();
		}
		
		/**
		 * Delegate
		 * @param event
		 *
		 */
		private function mouseRollOverHandle(event:MouseEvent):void{
			orDesModelLoc.getFigureCanvasStateDomain().RollOver(event);
			
			//			this.notifyMicroimageUpdate();
			//			this.notifyLevelModelUpdate();
		}
		
		
		private function resizeHandle(event:ResizeEvent):void{
			
			
			//			this.notifyMicroimageUpdate();
			//			this.notifyLevelModelUpdate();
		}
		
		
		private function showHandle(event:FlexEvent):void{
			
			//			this.notifyMicroimageUpdate();
			//			this.notifyLevelModelUpdate();
		}
		
		private function scrollHandle(event:ScrollEvent):void{
			
			switch(event.direction)
			{
				case ScrollEventDirection.VERTICAL:
					if(this.verticalScrollPosition<=0&&event.delta<0){
						return;
					}
					if(this.verticalScrollPosition>=this.verticalScrollBar.maxScrollPosition&&event.delta>0){
						return;
					}
					break;
				
				case ScrollEventDirection.HORIZONTAL:
					if(this.horizontalScrollPosition<=0&&event.delta<0){
						return;
					}
					if(this.horizontalScrollPosition>=this.horizontalScrollBar.maxScrollPosition&&event.delta>0){
						return;
					}
					break;
				
				default:
					break;
				
			}
			
			//			this.notifyMicroimageUpdate();
			//			this.notifyLevelModelUpdate();
		}
		
		/**
		 *
		 * @param event
		 *
		 */
		private function showEffectHandle(event:FlexEvent):void{
			var parallel:Parallel=new Parallel();
			var rotate:Rotate=new Rotate();
			rotate.angleFrom=180;
			rotate.angleTo=360;
			var fade:Fade=new Fade();
			fade.alphaFrom=0.0;
			fade.alphaTo=1.0;
			var iris:Iris=new Iris();
			iris.scaleXFrom=1;
			iris.scaleXTo=0;
			iris.scaleYFrom=1;
			iris.scaleYTo=0;
			var wipedown:WipeDown=new WipeDown();
			parallel.addChild(wipedown);
			parallel.addChild(iris);
			parallel.duration = 1600;
			parallel.repeatCount = 1;
			var instance:IEffectInstance = parallel.createInstance(this);
			instance.startEffect();
		}
		
		
		/**
		 * Update the selected figure by new attribute and redraw it.
		 */
		public function updateFigure() :void {
			
			var tempArr :Array = new Array();
			var linkarr :Array = new Array();
			
			this._editorModel.rootFigure.ifiguretoarray(tempArr);
			
			this.removeAllChildren();
			
			var len :int=tempArr.length;
			
			for(var i:int=0; i<len; i++){
				if(tempArr[i] is GraphicalFigure){
					IFigure(tempArr[i]).updateDraw();
					this.addChild(DisplayObject(tempArr[i]));
				}
				else if(tempArr[i] is ConnectionFigure){
					linkarr.push(tempArr[i]);
				}
			}
			
			len = linkarr.length;
			
			for(var j:int=0; j<len; j++){
				this.addChild(DisplayObject(linkarr[j]));
				IFigure(linkarr[j]).updateDraw();
			}
			
		}
		
		/**
		 * paint the gridding line
		 */
		private function renderCanvas(processType :String =  "BPEL" ) :void
		{
			this.graphics.clear();
			
			//fill background
			//			this.graphics.beginFill(0xFFFFFF);
			this.graphics.drawRect(0, 0, this.width, this.height);
			this.graphics.endFill();
			
			this.graphics.lineStyle(1, 0xD8D8DB);
			
			if(processType == FigureEditorModel.BPEL_PROCESS_TYPE)
			{
				//draw hirerachical line
				var i : int = 0;
				var totalLength: Number = 0;
				while(totalLength <= this.height){
					this.graphics.moveTo(0, grapWidth * i);
					this.graphics.lineTo(this.width, grapWidth * i);
					totalLength = grapWidth * i;
					i ++;
				}
				
				totalLength = 0;
				var j :int = 0;
				
				//draw vertical lines
				while(totalLength <= this.width){
					this.graphics.moveTo(grapWidth * j, 0);
					this.graphics.lineTo(grapWidth * j, this.height );
					totalLength = grapWidth * j;
					j++;
				}
			}
		}
		private function initCompareLayer():void{
			comparelayer = new FigureCanvas(_editorModel,"BPEL");
			comparelayer.alpha = 0.2;
			
			comparelayer.height = this.maxVerticalScrollPosition + this.height;
			comparelayer.width = this.maxHorizontalScrollPosition + this.width;
			comparelayer.horizontalScrollPolicy = "no";
			comparelayer.name = "comparelayer";
		}
		public function addCompareLayer():void{
			comparelayer = new FigureCanvas(OrDesignerModelLocator.getInstance().getOrchestraDesigner().figureEditorModel,"BPEL");
			comparelayer.alpha =0.2;
			
			comparelayer.height = this.maxVerticalScrollPosition + this.height;
			comparelayer.width = this.maxHorizontalScrollPosition + this.width;
			comparelayer.name = "comparelayer";
			if(this.getChildByName("comparelayer")==null)
				this.addChildAt(comparelayer,this.numChildren);
		}
		public function removeCompareLayer():void{
			//			comparelayer.graphics.clear();
			if(this.getChildByName("comparelayer")!=null)
				this.comparelayer.removeAllChildren();
		}
		private function initSignLayer():void{
			signlayer.alpha = 1;
			signlayer.percentHeight = 100;
			signlayer.percentWidth = 100;
			signlayer.graphics.lineStyle( 2, 0xff0000, 1);
			signlayer.name = "signlayer";
		}
		public function addSignLayer():void{
			if(this.getChildByName("signlayer")==null)
				this.addChildAt(signlayer,this.numChildren);
			//			Alert.show(this.numChildren.toString());
		}
		public function removeSignLayer():void{
			signlayer.graphics.clear();
			this.removeChild(signlayer);
		}
		public function get NumPool():int
		{
			return this.numPool;
		}
		
		public function IncremNumPool():void
		{
			this.numPool = this.numPool + 1;
		}
		
		public function createOneLevel(levelNum:int,isLast:Boolean):void{
			var feNavVH :EditorNavigatorVH = 
				ViewLocator.getInstance().getViewHelper(EditorNavigatorVH.VH_KEY) as EditorNavigatorVH;
			var orchestraDesigner:OrchestraDesigner = OrDesignerModelLocator.getInstance().getOrchestraDesigner();
			var level:LevelModel = orchestraDesigner.levelModel;
			level.draw(levelNum+1,isLast);
			var figureCanvas:Canvas = new Canvas();
			//			figureCanvas.graphics.clear();
			var figureeditormodel:FigureEditorModel = this._editorModel;
			var fileId:String = figureeditormodel.fileID;
			var fiarr:Array = this._editorModel.rootFigure.getchildren();
			var tempbd:BitmapData;
			var tempbitmap:Bitmap;
			var tempsprt:Sprite;
			var tempuic:UIComponent;
			var leftx:Number = 10000;
			var rightx:Number = 0;
			var topy:Number = 10000;
			var bottomy:Number = 0;
			for(var a:int = 0;a<fiarr.length;a++){
				var figure:LinkFigure = fiarr[a] as LinkFigure;
				if(figure == null){
					try{
						tempbd=ImageSnapshot.captureBitmapData(fiarr[a]);
					}
					catch(event:Error){
						continue;
					}
					tempbitmap=new Bitmap(tempbd.clone());
					tempbitmap.width=tempbd.width;
					tempbitmap.height=tempbd.height;
					
					
					tempsprt=new Sprite();
					tempsprt.addChild(tempbitmap);
					
					
					tempuic=new UIComponent();
					tempuic.width=tempbd.width;
					tempuic.height=tempbd.height;
					tempuic.addChild(tempsprt);
					tempuic.x=DisplayObject(fiarr[a]).x;
					tempuic.y=DisplayObject(fiarr[a]).y;
					
					figureCanvas.addChild(tempuic);
					figureCanvas.addEventListener(MouseEvent.CLICK,function(){
						feNavVH.SwithToGivenFileID(fileId);
					});
					leftx = leftx<tempuic.x?leftx:tempuic.x;
					rightx = rightx>(tempuic.x+tempuic.width)?rightx:(tempuic.x+tempuic.width);
					topy = topy<tempuic.y?topy:tempuic.y;
					bottomy = bottomy>(tempuic.y+tempuic.height)?bottomy:(tempuic.y+tempuic.height);
				}else{
					var arr:Array = figure.router.getPathPoint();
					var poi1:Point = new Point();
					var poi2:Point = new Point();
					figureCanvas.graphics.lineStyle(1,0x000000,1);
					for(var n:int = 1;n<arr.length;n++){
						poi1.x=Point(arr[n-1]).x;
						poi1.y=Point(arr[n-1]).y;
						poi2.x=Point(arr[n]).x;
						poi2.y=Point(arr[n]).y;
						figureCanvas.graphics.moveTo(poi1.x-this.x,poi1.y-this.y);
						figureCanvas.graphics.lineTo(poi2.x-this.x,poi2.y-this.y);
					}
				}
				
			}
			
			var canvas:Canvas = level.canvas.getChildAt(level.canvas.numChildren - 1) as Canvas;
			canvas.addChild(figureCanvas);
			canvas.setChildIndex(figureCanvas,canvas.numChildren-1);
			figureCanvas.x = 35;
			figureCanvas.y = 1;
			var rangex:Number = rightx;
			var rx:Number = (canvas.width-70)/rangex;
			var rangey:Number = bottomy;
			var ry:Number = (canvas.height-21)/(rangey);
			var r:Number = rx<ry?rx:ry;
			if(r < 1){
				figureCanvas.width = (canvas.width-70)/r;
				figureCanvas.height = (canvas.height-21)/r;
				figureCanvas.scaleX = r;
				figureCanvas.scaleY = r;
			}else{
				figureCanvas.width = (canvas.width-70)*5;
				figureCanvas.height = (canvas.height-21)*5;
				figureCanvas.scaleX = 0.2;
				figureCanvas.scaleY = 0.2;
			}
		}
		
	}
}