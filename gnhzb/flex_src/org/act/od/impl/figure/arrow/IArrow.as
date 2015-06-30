package org.act.od.impl.figure.arrow
{
	
	
	import flash.display.Sprite;
	
	public interface IArrow
	{
		function drawArrow(sprite:Sprite):void;
		function createVertexs():void;
		function setLength(length:Number):void;
		function setWidth(width:Number):void;
		function setLineStartPoint(x:Number,y:Number):void;
		function setLineEndPoint(x:Number,y:Number):void;
		function resetVertexs():void;
		function getArrowId():int;
		function outputAllInformation() :XML;
		function readInformationToFigure(info:XML):void;
		
		function setMultiple(multiple:Number):void;
		function getMultiple():Number;
	}
}