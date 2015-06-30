package org.act.od.impl.figure.arrow
{
	
	
	import flash.display.Sprite;
	import flash.geom.Point;
	
	import mx.binding.utils.BindingUtils;
	
	public class AbstractArrow implements IArrow
	{
		protected var arrowId:int;
		
		protected var length:Number;
		protected var width:Number;
		protected var lineStartPoint:Point;
		protected var lineEndPoint:Point;
		
		
		protected var headPoint:Point;
		protected var leftPoint:Point;
		protected var rightPoint:Point;
		
		[Bindable]
		public var multiple:Number=1;
		protected var defaultWidth:Number=7;
		protected var defaultLength:Number=12;
		
		public function AbstractArrow()
		{
			lineStartPoint=new Point();
			lineEndPoint=new Point();
			headPoint=new Point();
			leftPoint=new Point();
			rightPoint=new Point();
			setWidth(defaultWidth);
			setLength(defaultLength);
			BindingUtils.bindSetter(this.OutputScale,this,"multiple");
		}
		
		public function getArrowId():int{
			return this.arrowId;
		}
		
		public function setLineStartPoint(x:Number,y:Number):void{
			lineStartPoint.x=x;
			lineStartPoint.y=y;
		}
		
		public function setLineEndPoint(x:Number,y:Number):void{
			lineEndPoint.x=x;
			lineEndPoint.y=y;
		}
		
		public function drawArrow(sprite:Sprite):void{
			
		}
		
		public function createVertexs():void{
			var tempPoint:Point=new Point();
			var rate:Number;
			var templength:Number;
			if(Point.distance(lineStartPoint,lineEndPoint)<this.length||this.length==0||this.width==0){
				resetVertexs();
			}
			else{
				templength=Point.distance(lineEndPoint,lineStartPoint);
				rate=this.length/templength;
				tempPoint.x=rate*(lineStartPoint.x-lineEndPoint.x)+lineEndPoint.x;
				tempPoint.y=rate*(lineStartPoint.y-lineEndPoint.y)+lineEndPoint.y;
				rate=this.width/2/templength;
				leftPoint.x=tempPoint.x-rate*(lineStartPoint.y-lineEndPoint.y);
				rightPoint.x=tempPoint.x+rate*(lineStartPoint.y-lineEndPoint.y);
				leftPoint.y=tempPoint.y+rate*(lineStartPoint.x-lineEndPoint.x);
				rightPoint.y=tempPoint.y-rate*(lineStartPoint.x-lineEndPoint.x);
				headPoint.x=lineEndPoint.x;
				headPoint.y=lineEndPoint.y;
			}
		}
		
		public function resetVertexs():void{
			headPoint.x=0,headPoint.y=0;
			leftPoint.x=0,leftPoint.y=0;
			rightPoint.x=0,rightPoint.y=0;
		}
		
		public function setLength(length:Number):void{
			this.length=length;
		}
		
		public function setWidth(width:Number):void{
			this.width=width;
		}
		
		public function outputAllInformation():XML{
			var info:XML=new XML("<arrow></arrow>");
			info.@arrowId=this.arrowId;
			//			info.@length=this.length;
			//			info.@width=this.width;
			info.@lineStartPointX=this.lineStartPoint.x;
			info.@lineStartPointY=this.lineStartPoint.y;
			info.@lineEndPointX=this.lineEndPoint.x;
			info.@lineEndPointY=this.lineEndPoint.y;
			info.@headPointX=this.headPoint.x;
			info.@headPointY=this.headPoint.y;
			info.@leftPointX=this.leftPoint.x;
			info.@leftPointY=this.leftPoint.y;
			info.@rightPointX=this.rightPoint.x;
			info.@rightPointY=this.rightPoint.y;
			return info;
		}
		
		
		public function readInformationToFigure(info:XML):void{
			this.arrowId=int(info.@arrowId);
			//			this.length=Number(info.@length);
			//			this.width=Number(info.@width);
			this.lineStartPoint.x=Number(info.@lineStartPointX);
			this.lineStartPoint.y=Number(info.@lineStartPointY);
			this.lineEndPoint.x=Number(info.@lineEndPointX);
			this.lineEndPoint.y=Number(info.@lineEndPointY);
			this.headPoint.x=Number(info.@headPointX);
			this.headPoint.y=Number(info.@headPointY);
			this.leftPoint.x=Number(info.@leftPointX);
			this.leftPoint.y=Number(info.@leftPointY);
			this.rightPoint.x=Number(info.@rightPointX);
			this.rightPoint.y=Number(info.@rightPointY);
		}
		
		protected function OutputScale(mtp:Number):void{
			this.setWidth(this.defaultWidth*this.multiple);
			this.setLength(this.defaultLength*this.multiple);
		}
		public function setMultiple(mtp:Number):void{
			this.multiple=mtp;
		}
		public function getMultiple():Number{
			return this.multiple;
		}
		
	}
}