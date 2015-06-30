package org.act.od.impl.figure
{
	import flash.geom.Point;
	
	public class NoncontainerFigure extends GraphicalFigure
	{
		
		
		public function NoncontainerFigure(processType:String=null)
		{
			super(processType);
			
		}
		
		
		override public function addchild(child:IFigure):void{
			
		}
		override public function addchildWithConnection(child:IFigure):void{
			
		}
		override public function removechildWithoutConnection(child:IFigure):void{
			
		}
		
		override public function removechildWithConnection(child:IFigure,headarr:Array,tailarr:Array):void{
		}
		
		protected function circlepoint(centerX:Number,centerY:Number,pointX:Number,pointY:Number,radius:Number):Point{
			var rate:Number;
			var ret:Point=new Point(centerX,centerY);
			var point:Point=new Point(pointX,pointY);
			rate=radius/Point.distance(point,ret);
			ret.x=(point.x-ret.x)*rate+ret.x;
			ret.y=(point.y-ret.y)*rate+ret.y;
			return ret;
		}
	}
}