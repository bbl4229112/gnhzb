package org.act.od.impl.figure.custom
{
	public class ReadyFigure extends CaltksAbstractFigure
	{
		public function ReadyFigure(processType:String=null)
		{
			super(processType);
			drawid=162;
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
			
			sprt.graphics.moveTo(width*0.25,0);
			sprt.graphics.lineTo(width*0.75,0);
			sprt.graphics.lineTo(width,centreY);
			sprt.graphics.lineTo(width*0.75,height);
			sprt.graphics.lineTo(width*0.25,height);
			sprt.graphics.lineTo(0,centreY);
			sprt.graphics.lineTo(width*0.25,0);
			
		}
	}
}