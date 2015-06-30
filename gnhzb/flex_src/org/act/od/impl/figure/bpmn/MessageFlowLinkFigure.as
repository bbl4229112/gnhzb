package org.act.od.impl.figure.bpmn
{
	
	import flash.display.CapsStyle;
	import flash.display.LineScaleMode;
	import flash.geom.Point;
	
	import org.act.od.impl.figure.ConnectionFigure;
	
	public class MessageFlowLinkFigure extends ConnectionFigure
	{
		public function MessageFlowLinkFigure(processType:String = null)
		{
			super(processType);
			drawid=119;
			standardwidth=30;
			standardheight=0;
			
			width=standardwidth;
			height=standardheight;
		}
		
		override public function drawpicture():void
		{
			super.drawpicture();
			var arr:Array=this.router.getPathPoint();
			var poi1:Point=new Point();
			var poi2:Point=new Point();
			var i:int;
			sprt.graphics.lineStyle(this.lineThickness,0x000000,1);
			//sprt.graphics.lineStyle(this.lineThickness, 0xFF0000, 0.25, false, LineScaleMode.NORMAL, CapsStyle.ROUND);
			for(i=1;i<arr.length;i++){
				poi1.x=Point(arr[i-1]).x;
				poi1.y=Point(arr[i-1]).y;
				poi2.x=Point(arr[i]).x;
				poi2.y=Point(arr[i]).y;
				
				var dashy:DashedLine = new DashedLine(sprt);
				//this.addChild(dashy);
				//DashedLine(sprt).moveTo(poi1.x-this.x,poi1.y-this.y);
				//DashedLine(sprt).lineTo(poi2.x-this.x,poi2.y-this.y);
				//sprt = dashy.stroke;
				dashy.moveTo(poi1.x-this.x,poi1.y-this.y);
				dashy.lineTo(poi2.x-this.x,poi2.y-this.y);
				
				/*sprt.graphics.moveTo(poi1.x-this.x,poi1.y-this.y);
				sprt.graphics.lineTo(poi2.x-this.x,poi2.y-this.y);*/
			}
			this.arrow.setLineStartPoint(this.headX-this.x,this.headY-this.y);
			this.arrow.setLineEndPoint(this.tailX-this.x,this.tailY-this.y);
			this.arrow.createVertexs();
			//Draw the arrow
			this.arrow.drawArrow(sprt);
			
			if(this.contains(sprt)){
				this.removeChild(sprt);
			}
			addChildAt(sprt,0);
		}
		
		override public function drawSelectedStyle():void{
			super.drawSelectedStyle();
		}
		
		override public function changedirection(currentX:Number,currentY:Number):int{
			return 0;
		}
		
		
		
		
		
	}
}