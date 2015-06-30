package org.act.od.impl.state{
	
	import org.act.od.impl.model.*;
	
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	/**
	 * 
	 *  @author Likexin
	 * 
	 */	
	public interface IState{
		
		function mouseDoubleClick(point:Point, event:MouseEvent):void;
		
		function mouseClick(point:Point, event:MouseEvent):void;
		
		function mouseDown(point:Point, event:MouseEvent):void;
		
		function mouseUp(point:Point, event:MouseEvent):void;
		
		function mouseMove(point:Point, event:MouseEvent):void;
		
		function RollOut(event:MouseEvent):void;
		
		function RollOver(event:MouseEvent):void;
		
		function keyDown(event:KeyboardEvent):void;
		
		function keyUp(event:KeyboardEvent):void;
		
	}
}