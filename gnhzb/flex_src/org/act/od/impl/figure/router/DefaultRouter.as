package org.act.od.impl.figure.router
{
	import flash.geom.Point;
	
	public class DefaultRouter extends AbstractRouter
	{
		public function DefaultRouter()
		{
			super();
			this.routerId=1;
		}
		
		override public function createPath():void{
			super.createPath();
			var point:Point;
			point=new Point();
			point.x=this.sourcePoint.x;
			point.y=this.sourcePoint.y;
			this.pathPoint.push(point);
			point=new Point();
			point.x=this.targetPoint.x;
			point.y=this.targetPoint.y;
			this.pathPoint.push(point);
		}
		
	}
}