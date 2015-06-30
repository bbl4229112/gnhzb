package org.act.od.impl.model{
	
	
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import org.act.od.impl.state.IState;
	import org.act.od.impl.state.SelectionState;
	
	/**
	 *  storage of primitives' state.
	 * 
	 * @ author Zhaoxq
	 * 
	 */
	public class FigureCanvasStateDomain{
		/**
		 * Current state.
		 */
		private var fcActiveState:IState;
		
		public function FigureCanvasStateDomain(){
			
		}
		/**
		 * Set the current state.
		 */
		public function setFCActiveState(state :IState) :void{
			this.fcActiveState = state;
			
		}
		/**
		 * Get the current state.
		 */
		public function getFCActiveState() :IState{
			return fcActiveState;
		}
		/**
		 * Do something when mouseMove in the current state.
		 */
		public function mouseMove(point:Point,event:MouseEvent):void{
			if(fcActiveState != null)
				fcActiveState.mouseMove(point,event);
			
		}
		/**
		 * Do something when mouseDown in the current state.
		 */
		public function mouseDown(point:Point,event:MouseEvent):void{
			if(fcActiveState != null)
				fcActiveState.mouseDown(point,event);
		}
		/**
		 * Do something when mouseUp in the current state.
		 */
		public function mouseUp(point:Point,event:MouseEvent):void{
			if(fcActiveState != null)
				fcActiveState.mouseUp(point,event);
		}
		/**
		 * Do something when mouseClick in the current state.
		 */
		public function mouseClick(point:Point,event:MouseEvent):void{
			if(fcActiveState != null)
				fcActiveState.mouseClick(point,event);
		}
		/**
		 * Do something when mouseDoubleClick in the current state.
		 */
		public function mouseDoubleClick(point:Point,event:MouseEvent):void{
			if(fcActiveState != null)
				fcActiveState.mouseDoubleClick(point,event);
		}
		/**
		 * Do something when keyDown in the current state.
		 */
		public function keyDown(event:KeyboardEvent):void{
			if(fcActiveState != null)
				fcActiveState.keyDown(event);
		}
		/**
		 * Do something when keyUp in the current state.
		 */
		public function keyUp(event:KeyboardEvent):void{
			if(fcActiveState != null)
				fcActiveState.keyUp(event);
		}
		/**
		 * Do something when RollOut in the current state.
		 */
		public function RollOut(event:MouseEvent):void{
			if(fcActiveState != null)
				fcActiveState.RollOut(event);
		}
		/**
		 * Do something when RollOver in the current state.
		 */
		public function RollOver(event:MouseEvent):void{
			if(fcActiveState != null)
				fcActiveState.RollOver(event);
		}
	}
}