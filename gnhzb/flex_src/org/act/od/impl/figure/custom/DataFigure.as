package org.act.od.impl.figure.custom
{
	import org.act.od.impl.figure.NoncontainerFigure;
	
	public class DataFigure extends CaltksAbstractFigure
	{
		public function DataFigure(processType:String=null)
		{
			super(processType);
			drawid=161;
			standardwidth=90;
			standardheight=40;
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
			sprt.graphics.moveTo((width-2)*0.2+1,1);
			sprt.graphics.lineTo(width-1,1);
			sprt.graphics.lineTo((width-2)*0.8+1,height-1);
			sprt.graphics.lineTo(1,height-1);
			sprt.graphics.lineTo((width-2)*0.2+1,1);
			
		}
	}
}