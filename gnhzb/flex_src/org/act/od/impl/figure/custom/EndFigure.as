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
	import org.act.od.impl.model.FigureEditorModel;
	import org.act.od.impl.model.OrDesignerModelLocator;
	import org.act.od.impl.view.OrchestraDesigner;
	public class EndFigure extends CaltksAbstractFigure
	{
		public function EndFigure(processType:String=null)
		{
			super(processType);
			drawid=159;
			standardwidth=50;
			standardheight=50;
			width=standardwidth;
			height=standardheight;
			this.lblNodeName.width=60-8;
			this.lblNodeName.height=40;
			this.lblNodeName.x=8;
			this.lblNodeName.y=10;
			this.addChild(this.lblNodeName);
		} 
		
		override public function drawpicture():void{
			super.drawpicture();
			sprt.graphics.lineStyle(this.defaultLineThickness*this.multiple,0x000000,1);
			sprt.graphics.beginFill(0xffffff,1);
			draw(0,0,this.width,this.height);
			sprt.graphics.endFill();
		}
		
		private function draw(x:Number,y:Number, width:Number, height:Number):void{
			var centreX:Number=x+width/2;
			var centreY:Number=y+height/2;
			var d:Number = width>height?height:width;
			sprt.graphics.drawCircle(centreX,centreY,d/2);
			
		}
		
		
	}
}