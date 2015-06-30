package org.act.od.impl.figure.router
{
	
	
	import flash.geom.Point;
	
	import org.act.od.impl.viewhelper.FigureEditorVH;
	
	public interface IRouter
	{
		function createPath():void;
		function setSourcePoint(sSP:Point):void;
		function setTargetPoint(sTP:Point):void;
		function getPathPoint():Array;
		function getRouterId():int;
		function outputAllInformation() :XML;
		function readInformationToFigure(info:XML):void;
	}
}