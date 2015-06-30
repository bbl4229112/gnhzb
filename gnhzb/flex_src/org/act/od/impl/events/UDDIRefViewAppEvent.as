package org.act.od.impl.events
{
	import org.act.od.framework.control.OrDesignerEvent;
	/**
	 * This class define the events raised by the UDDIRefView.
	 * 
	 * @ author Quyue
	 * 
	 */
	public class UDDIRefViewAppEvent extends OrDesignerEvent
	{
		/**
		 * Get a reference from server.
		 */
		public static const GET_REF_FROM_SERVER : String = "get_ref_from_server_UDDIRefView";
		/**
		 * Constructor, takes the event name (type) and data object (defaults to null)
		 * and also defaults the standard Flex event properties bubbles and cancelable
		 * to true and false respectively.
		 */ 
		public function UDDIRefViewAppEvent(type : String, dataParameter:Object = null ,
											bubbles : Boolean = false, cancelable : Boolean = false)
		{
			super(type, dataParameter, bubbles, cancelable);
		}
		
	}
}