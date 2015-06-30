package org.act.od.impl.events
{
	import org.act.od.framework.control.OrDesignerEvent;
	/**
	 * 
	 * @author Quyue
	 * 
	 */	
	public class AbstractPoolAppEvent extends OrDesignerEvent
	{
		
		
		//public static const FIGUREFILE_OPEN :String = "figurefile_open_FileNavigatorView";
		
		
		/**
		 * Constructor, takes the event name (type) and data object (defaults to null)
		 * and also defaults the standard Flex event properties bubbles and cancelable
		 * to true and false respectively.
		 */
		public function AbstractPoolAppEvent(type : String, dataParameter:Object = null ,
											 bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(type, dataParameter, bubbles, cancelable);
		}
	}
}