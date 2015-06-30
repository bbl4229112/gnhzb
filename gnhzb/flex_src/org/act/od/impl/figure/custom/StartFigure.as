package org.act.od.impl.figure.custom
{

	import flash.events.ContextMenuEvent;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	
	import mx.controls.Alert;
	import mx.core.ContextualClassFactory;
	
	import org.act.od.impl.figure.NoncontainerFigure;
	import org.act.od.impl.figure.FigureFactory;
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.OrchestraDesigner;
	public class StartFigure extends CaltksAbstractFigure
	{
		[Bindable]
		[Embed(source="/../assets/icon/container/palette.gif")]
		public var palette :Class;
		
		public function StartFigure(processType:String=null)
		{
			super(processType);
			drawid=158;
			standardwidth=50;
			standardheight=50;
			width=standardwidth;
			height=standardheight;
			this.lblNodeName.width=60-8;
			this.lblNodeName.height=40;
			this.lblNodeName.x=8;
			this.lblNodeName.y=10;
			this.addChild(this.lblNodeName);
//			this.setpicture(FigureFactory.circuit);
			
		} 
		
		
		override public function drawpicture():void{
			super.drawpicture();
//			setImageSizeAndPosition();
//			picture.maintainAspectRatio=false;
//			this.addChild(picture);
			sprt.graphics.lineStyle(this.defaultLineThickness*this.multiple,0x000000,1);
			sprt.graphics.beginFill(0xffffff,1);
			draw(0,0,this.width,this.height);
			sprt.graphics.endFill();
		}
//		override public function setImageSizeAndPosition():void{
//			picture.x = 7;
//			picture.y = 20;
//			picture.width = this.width/5;
//			picture.height = this.height/5;
//		}
		private function draw(x:Number,y:Number, width:Number, height:Number):void{
			var centreX:Number=x+width/2;
			var centreY:Number=y+height/2;
			var d:Number = width>height?height:width;
			sprt.graphics.drawCircle(centreX,centreY,d/2);
			
		}
		
	}
}