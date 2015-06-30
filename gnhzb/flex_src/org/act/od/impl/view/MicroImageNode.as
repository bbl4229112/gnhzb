package org.act.od.impl.view
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.controls.TextArea;
	import mx.core.UIComponent;
	import mx.events.ResizeEvent;
	import mx.graphics.ImageSnapshot;
	import mx.messaging.management.Attribute;
	
	import org.act.od.impl.figure.AbstractFigure;
	import org.act.od.impl.figure.MicroImageNodeArrowFigure;
	import org.act.od.impl.figure.NodeArrowFigure;
	import org.act.od.impl.figure.arrow.ArrowFactory;
	import org.act.od.impl.figure.arrow.IArrow;
	import org.act.od.impl.model.OrDesignerModelLocator;

	/**
	 * MicroImageNode is used for lay out the seleted node in the micro view.
	 * @author LiTao
	 * @date 20120806
	 */	
	public class MicroImageNode extends Canvas
	{
		private static var microImageNode:MicroImageNode;		
		private var imagecanvas:Canvas = new Canvas();	
		private var sourceFigure:AbstractFigure = null;
		
		public static var arrowInput:NodeArrowFigure;
		public static var arrowOutput:NodeArrowFigure;
		public static var arrowControl:NodeArrowFigure;
		public static var arrowMechanism:NodeArrowFigure;
		
		
		public function MicroImageNode()
		{
			super();			
			this.setStyle("backgroundColor","0xffffff");			
			this.percentHeight=100;
			this.percentWidth=100;			
			this.horizontalScrollPolicy='off';
			this.verticalScrollPolicy='off';		
		}
		
		/**
		 * Singleton access to the MicroImageNode is assured through the static getInstance()
		 * method, which is used to retrieve the only MicroImageNode instance in a Cairngorm
		 * application.
		 *
		 * <p>Wherever there is a need to retreive the Resource instance, it is achieved
		 * using the following code:</p>
		 *
		 * <pre>
		 * var microImage:MicroImageNode = MicroImageNode.getInstance();
		 * </pre>
		 */
		public static function getInstance():MicroImageNode{
			if(microImageNode==null){
				microImageNode=new MicroImageNode();
			}
			return microImageNode;
		}
		
		public function updateShow(selectedFigure:AbstractFigure):void{		
			if(selectedFigure)
				this.sourceFigure = selectedFigure;
			if(!sourceFigure) {
				this.removeAllChildren();
				return;
			}			
			imagecanvas.removeAllChildren();
			imagecanvas.graphics.clear();
						
			imagecanvas.percentWidth = 100;
			imagecanvas.percentHeight = 100;
			if(imagecanvas.height.toString()=="NaN"||imagecanvas.width.toString()=="NaN"){
				this.removeAllChildren();
				return;
			}
			
			var tempbd:BitmapData;
			var tempbitmap:Bitmap;
			var tempsprt:Sprite;
			var tempuic:UIComponent;
			
			tempbd=ImageSnapshot.captureBitmapData(sourceFigure);					
			tempbitmap=new Bitmap(tempbd.clone());
			tempbitmap.width=tempbd.width;
			tempbitmap.height=tempbd.height;			
						
			tempuic=new UIComponent();
			tempuic.width=tempbd.width;
			tempuic.height=tempbd.height;
			tempuic.x=imagecanvas.width/2-tempbd.width/2;
			tempuic.y=imagecanvas.height/2-tempbd.height/2;
			tempsprt=new Sprite();
			tempsprt.addChild(tempbitmap);			
			tempuic.addChild(tempsprt);
			imagecanvas.addChild(tempuic);
											
										
			imagecanvas.graphics.beginFill(0xFFFF00,1);
			imagecanvas.graphics.drawRect(0,0,imagecanvas.width,imagecanvas.height);
			imagecanvas.graphics.endFill();
										
			this.addChild(imagecanvas);
			
			arrowInput = new NodeArrowFigure("input");
			arrowInput.x = tempuic.x-50;
			arrowInput.y = tempuic.y+tempuic.height/2;
			arrowInput.addEventListener(MouseEvent.MOUSE_DOWN, arrowInputMouseDownHandler);
			imagecanvas.addChild(arrowInput);
			
			arrowOutput = new NodeArrowFigure("output");
			arrowOutput.x = tempuic.x+tempuic.width;
			arrowOutput.y = tempuic.y+tempuic.height/2;
			arrowOutput.addEventListener(MouseEvent.MOUSE_DOWN, arrowOutputMouseDownHandler);
			imagecanvas.addChild(arrowOutput);
			
			arrowControl = new NodeArrowFigure("control");
			arrowControl.x = tempuic.x+tempuic.width/2;
			arrowControl.y = tempuic.y-50;
//			arrowControl.addEventListener(MouseEvent.CLICK,);
			imagecanvas.addChild(arrowControl);
			
			arrowMechanism = new NodeArrowFigure("mechanism");
			arrowMechanism.x = tempuic.x+tempuic.width/2;
			arrowMechanism.y = tempuic.y+tempuic.height;
//			arrowMechanism.addEventListener(MouseEvent.CLICK,);
			imagecanvas.addChild(arrowMechanism);						
		}	
		
		private function arrowInputMouseDownHandler(event:MouseEvent):void {
			
			var _focusedCell:Object = new Object();			
			_focusedCell.rowIndex=0;			
			_focusedCell.columnIndex=1;	
			var atts:ArrayCollection = sourceFigure.getAttributeArray();
//			var inputsprt:Sprite=new Sprite();
//			inputsprt.addChild(sourceFigure);	
//			var inputUI:UIComponent = new UIComponent();
//			inputUI.addChild(inputsprt);
//			inputUI.setFocus();
//			sourceFigure.addEventListener(MouseEvent.MOUSE_DOWN,this.setFocus);
//			focusManager.setFocus(new AttributeView().getChildAt(0));
//			focusManager.setFocus(new AttributeView().att_value.getItemRendererFactory().newInstance().txt);
//			focusManager.setFocus(OrDesignerModelLocator.getInstance().getAttributeViewModel().attributes.getItemAt(0));
			for(var i:Number=0;i<atts.length;i++){
				trace(i+":"+atts.getItemAt(i).name+","+atts.getItemAt(i).value);
			}
			//此view就是一个DataGrid！20120813
			var view:AttributeView = new AttributeView();
			
//			view.editedItemPosition = _focusedCell;
			view.selectedIndex = 1;
//			var a:DisplayObject = view.getChildAt();
			trace("asdfasdfasdfasdfadsfasdfasdfasd");
		}
		private function arrowOutputMouseDownHandler(event:MouseEvent):void {
			var _focusedCell:Object = new Object();			
			_focusedCell.rowIndex=1;			
			_focusedCell.columnIndex=1;	
			
			//此view就是一个DataGrid！20120813
			var view:AttributeView = new AttributeView();  
			view.editedItemPosition = _focusedCell;
			
			trace("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			
			
			
		}
	}
}