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
	
	public class DocumentFigure extends CaltksAbstractFigure
	{
		public function DocumentFigure(processType:String=null)
		{
			super(processType);
			drawid=160;
			standardwidth=90;
			standardheight=55;
			width=standardwidth;
			height=standardheight;
			this.lblNodeName.width=110-8;
			this.lblNodeName.height=17;
			this.lblNodeName.x=8;
			this.lblNodeName.y=11;
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
			sprt.graphics.moveTo(1,(height-2)*0.8+1);
			sprt.graphics.lineTo(1,1);
			sprt.graphics.lineTo(width-1,1);
			sprt.graphics.lineTo(width-1,(height-2)*0.8+1);
			sprt.graphics.curveTo(width/2+(width-2)/4,(height-2)*0.8+1-(height-2)*0.2,width/2,height-1-0.2*(height-2));
			sprt.graphics.curveTo(1+(width-2)/4,height-1,1,(height-2)*0.8+1);
			
		}
		
		
	}
}