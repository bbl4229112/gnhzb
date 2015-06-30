package org.act.od.impl.events{
	
	import flash.events.MouseEvent;
	/**
	 * This class define the events raised by the LabelLink.
	 * 
	 * @ author Quyue
	 * 
	 */
	public class LabelLinkEvent extends MouseEvent{
		public static const LABEL_LINK:String = "labelLink";
		public var value:String;
		/**
		 * Constructor, takes the event name (type) and data object (defaults to null)
		 * and also defaults the standard Flex event properties bubbles and cancelable
		 * to true and false respectively.
		 */ 
		public function LabelLinkEvent(value:String) {
			super(LABEL_LINK);
			this.value = value;
		}
		
	}
}