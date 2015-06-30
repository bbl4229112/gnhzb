package org.act.od.impl.figure.router
{
	//	import editor.GraphicalViewer;
	
	import flash.geom.Point;
	
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	public class AbstractRouter implements IRouter
	{
		protected var sourcePoint:Point;
		protected var targetPoint:Point;
		protected var pathPoint:Array;
		protected var routerId:int;
		
		public function AbstractRouter()
		{
			pathPoint=new Array();
			sourcePoint=new Point();
			targetPoint=new Point();
		}
		
		public function createPath():void
		{
			var i:int;
			var l:int;
			l=this.pathPoint.length;
			for(i=0;i<l;i++){
				this.pathPoint.pop();
			}
		}
		public function setSourcePoint(sSP:Point):void{
			this.sourcePoint=sSP;
		}
		public function setTargetPoint(sTP:Point):void{
			this.targetPoint=sTP;
		}
		
		public function getPathPoint():Array{
			return pathPoint;
		}
		public function getRouterId():int{
			return this.routerId;
		}
		
		public function outputAllInformation():XML{
			var info:XML=new XML("<router></router>");
			info.@routerId=this.routerId;
			info.@sourcePointX=this.sourcePoint.x;
			info.@sourcePointY=this.sourcePoint.y;
			info.@targetPointX=this.targetPoint.x;
			info.@targetPointY=this.targetPoint.y;
			var path:XML=new XML("<pathpoints></pathpoints>");
			var newnode:XML;
			var i:int;
			for(i=0;i<this.pathPoint.length;i++){
				newnode=new XML("<point></point>");
				newnode.@sequenceId = i;
				newnode.@x = this.pathPoint[i].x;
				newnode.@y = this.pathPoint[i].y;
				path.appendChild(newnode);
			}
			info.appendChild(path);
			//			info.@pathPoint=this.pathPoint;
			return info;
		}
		
		public function readInformationToFigure(info:XML):void{
			this.routerId=int(info.@routerId);
			this.sourcePoint.x=Number(info.@sourcePointX);
			this.sourcePoint.y=Number(info.@sourcePointY);
			this.targetPoint.x=Number(info.@targetPointX);
			this.targetPoint.y=Number(info.@targetPointY);
			var i:int;
			var path:XMLList=new XMLList();
			var newnode:XMLList=new XMLList();
			var newpoint:Point;
			path=info.elements("pathpoints");
			newnode=XML(path).elements("point");
			for(i=0;i<newnode.length();i++){
				newpoint=new Point();
				newpoint.x=Number(XML(newnode[i]).elements("pointx").toString());
				newpoint.y=Number(XML(newnode[i]).elements("pointy").toString());
				pathPoint.push(newpoint);
			}
			//			this.pathPoint=Array(info.@pathPoint);
		}
		
	}
}