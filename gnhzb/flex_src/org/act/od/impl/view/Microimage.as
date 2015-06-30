package org.act.od.impl.view
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	
	import mx.containers.Canvas;
	import mx.controls.Alert;
	import mx.core.UIComponent;
	import mx.events.ResizeEvent;
	import mx.graphics.ImageSnapshot;
	
	/**
	 * Microimage is used for lay out the canvas in the micro view.
	 * 
	 * @author Quyue
	 * 
	 */
	public class Microimage extends Canvas
	{		
		private static var microimage : Microimage;
		
		private var oheight:Number;
		private var owidth:Number;
		
		private var sourceCanvas:Canvas=null;		
		private var imagecanvas:Canvas=new Canvas();
		
		private var currentRectWidth:Number=0;		
		private var currentRectHeight:Number=0;		
		private var rate:Number=1;
		
		public function Microimage()
		{
			super();
			
			this.setStyle("backgroundColor","0xffffff");
			
			this.percentHeight=100;
			this.percentWidth=100;
			
			this.horizontalScrollPolicy='off';
			this.verticalScrollPolicy='off';
			
			this.addEventListener(ResizeEvent.RESIZE,resizeHandle);			
			this.addEventListener(MouseEvent.MOUSE_DOWN,mouseDownHandle);			
			this.addEventListener(MouseEvent.MOUSE_MOVE,mouseMoveHandle);			
		}
		
		private function mouseDownHandle(event:MouseEvent):void{
			if(!this.sourceCanvas){
				return;
			}
			
			var mousex:Number=Canvas(event.currentTarget).mouseX;
			var mousey:Number=Canvas(event.currentTarget).mouseY;
			
			var positionX:Number;
			var positionY:Number;
			
			positionX=(mousex-imagecanvas.x-currentRectWidth/2)/rate;
			if(positionX<0){
				positionX=0;
			}
			if(mousex-imagecanvas.x+currentRectWidth/2>imagecanvas.width*rate){
				positionX=(imagecanvas.width*rate-currentRectWidth)/rate;
			}
			
			positionY=(mousey-imagecanvas.y-currentRectHeight/2)/rate;
			if(positionY<0){
				positionY=0;
			}
			if(mousey-imagecanvas.y+currentRectHeight/2>imagecanvas.height*rate){
				positionY=(imagecanvas.height*rate-currentRectHeight)/rate;
			}
						
			if(this.sourceCanvas.verticalScrollBar){
				this.sourceCanvas.verticalScrollPosition=positionY;
			}
			
			if(this.sourceCanvas.horizontalScrollBar){
				this.sourceCanvas.horizontalScrollPosition=positionX;
			}
			
			this.updateShow();
			
		}
		
		private function mouseMoveHandle(event:MouseEvent):void{
			if(!this.sourceCanvas){
				return;
			}
			if(event.buttonDown){
				
				var mousex:Number=Canvas(event.currentTarget).mouseX;
				var mousey:Number=Canvas(event.currentTarget).mouseY;
				
				var positionX:Number;
				var positionY:Number;
				
				positionX=(mousex-imagecanvas.x-currentRectWidth/2)/rate;
				if(positionX<0){
					positionX=0;
				}
				if(mousex-imagecanvas.x+currentRectWidth/2>imagecanvas.width*rate){
					positionX=(imagecanvas.width*rate-currentRectWidth)/rate;
				}
				
				positionY=(mousey-imagecanvas.y-currentRectHeight/2)/rate;
				if(positionY<0){
					positionY=0;
				}
				if(mousey-imagecanvas.y+currentRectHeight/2>imagecanvas.height*rate){
					positionY=(imagecanvas.height*rate-currentRectHeight)/rate;
				}
				
				//              connect with figureCanvas
				if(this.sourceCanvas.verticalScrollBar){
					this.sourceCanvas.verticalScrollPosition=positionY;
				}
				
				if(this.sourceCanvas.horizontalScrollBar){
					this.sourceCanvas.horizontalScrollPosition=positionX;
				}
			}
			
			this.updateShow();
		}
		/**
		 * Singleton access to the Microimage is assured through the static getInstance()
		 * method, which is used to retrieve the only Microimage instance in a Cairngorm
		 * application.
		 *
		 * <p>Wherever there is a need to retreive the Resource instance, it is achieved
		 * using the following code:</p>
		 *
		 * <pre>
		 * var microimage:Microimage = Microimage.getInstance();
		 * </pre>
		 */
		public static function getInstance():Microimage{
			if(microimage==null){
				microimage=new Microimage();
			}
			return microimage;
		}
		
		public function updateShow(canvas:Canvas=null):void{
			
			if(canvas){
				this.sourceCanvas=canvas;
			}
			if(!this.sourceCanvas){
				this.removeAllChildren();
				return;
			}
			imagecanvas.removeAllChildren();
			imagecanvas.graphics.clear();
			
			
			var i:int;
			var rectx:Number=0;
			var recty:Number=0;
			var rectwidth:Number=sourceCanvas.width;
			var rectheight:Number=sourceCanvas.height;
			
			imagecanvas.height=sourceCanvas.height;
			imagecanvas.width=sourceCanvas.width;
			if(imagecanvas.height.toString()=="NaN"||imagecanvas.width.toString()=="NaN"){
				this.removeAllChildren();
				return;
			}
			
			
			if(sourceCanvas.horizontalScrollBar){
				imagecanvas.width+=sourceCanvas.horizontalScrollBar.maxScrollPosition;
				imagecanvas.height-=sourceCanvas.horizontalScrollBar.height;
				rectx=sourceCanvas.horizontalScrollPosition;
				rectheight-=sourceCanvas.horizontalScrollBar.height;
			}
			if(sourceCanvas.verticalScrollBar){
				imagecanvas.height+=sourceCanvas.verticalScrollBar.maxScrollPosition;
				imagecanvas.width-=sourceCanvas.verticalScrollBar.width;
				recty=sourceCanvas.verticalScrollPosition;
				rectwidth-=sourceCanvas.verticalScrollBar.width;
			}
			imagecanvas.graphics.beginFill(0xFFFF00,1);
			imagecanvas.graphics.drawRect(0,0,imagecanvas.width,imagecanvas.height);
			imagecanvas.graphics.endFill();
			
			var arr:Array;
			var tempbd:BitmapData;
			var tempbitmap:Bitmap;
			var tempsprt:Sprite;
			var tempuic:UIComponent;
			arr=sourceCanvas.getChildren();
			for(i=0;i<arr.length;i++){												
				try{
					tempbd=ImageSnapshot.captureBitmapData(arr[i]);					
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
				tempuic.x=DisplayObject(arr[i]).x;
				tempuic.y=DisplayObject(arr[i]).y;
				imagecanvas.addChild(tempuic);
				
				
			}
			
			
			this.removeAllChildren();
			
			
			
			rate=this.width/imagecanvas.width;
			rate=rate<this.height/imagecanvas.height?rate:this.height/imagecanvas.height;
			imagecanvas.scaleX=rate;
			imagecanvas.scaleY=rate;
			
			imagecanvas.x=(this.width-imagecanvas.width*rate)/2;
			imagecanvas.y=(this.height-imagecanvas.height*rate)/2;
			
			var rectuic:UIComponent=new UIComponent();
			rectuic.graphics.lineStyle(2,0xff0000);
			rectuic.x=imagecanvas.x+rectx*rate;
			rectuic.y=imagecanvas.y+recty*rate;
			
			
			try{
				rectuic.graphics.drawRect(0,0,rectwidth*rate,rectheight*rate);
			}
			catch(event:Error){
				
			}
			this.currentRectWidth=rectwidth*rate;
			this.currentRectHeight=rectheight*rate;
			
			
			
			this.addChild(imagecanvas);
			this.addChild(rectuic);
			
			
		}
		
		private function resizeHandle(event:ResizeEvent):void{
			this.updateShow();
		}
		/**
		 * Clear the view.
		 */
		public function clearShow():void{
			this.sourceCanvas=null;
			this.graphics.clear();
			this.removeAllChildren();
		}
		
		
	}
}