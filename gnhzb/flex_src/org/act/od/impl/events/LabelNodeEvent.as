package org.act.od.impl.events
{
	import flash.display.InteractiveObject;
	import flash.events.MouseEvent;
	/**
	 * This class define the events raised by the LabelNode.
	 * 
	 * @ author Quyue
	 * 
	 */
	public class LabelNodeEvent extends MouseEvent
	{
		public static const LABEL_NODE:String = "labelNode";
		public var value:String;
		/**
		 * Constructor, takes the event name (type) and data object (defaults to null)
		 * and also defaults the standard Flex event properties bubbles and cancelable
		 * to true and false respectively.
		 */ 
		public function LabelNodeEvent(value:String) {
			super(LABEL_NODE);
			this.value = value;
		}
		
	}
}