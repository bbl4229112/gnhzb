package org.act.od.impl.events
{
	import org.act.od.framework.control.OrDesignerEvent;
	/**
	 * This class define the events raised by the DesignerToolBar.
	 * 
	 * @ author Quyue
	 * 
	 */
	public class FigureEditorAppEvent extends OrDesignerEvent{
		/**
		 * Show the figure file in xml format
		 */ 
		public static const CHANGE_To_XML_VIEW :String = "change_to_XML_view_FigureEditor";
		/**
		 * Build BPEL file form the figure file.
		 */ 
		public static const BPEL_CREAT :String = "bpel_creat_FigureEditor";
		/**
		 * Constructor, takes the event name (type) and data object (defaults to null)
		 * and also defaults the standard Flex event properties bubbles and cancelable
		 * to true and false respectively.
		 */ 
		public function FigureEditorAppEvent(type : String, dataParameter:Object = null ,
											 bubbles : Boolean = false, cancelable : Boolean = false){
			
			super(type, dataParameter, bubbles, cancelable);
		}
		
	}
}