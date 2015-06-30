package org.act.od.impl.events
{
	import org.act.od.framework.control.OrDesignerEvent;
	/**
	 * This class define the events raised by the ToolPanel.
	 * 
	 * @ author Quyue
	 * 
	 */
	public class ToolPanelAppEvent extends OrDesignerEvent
	{
		/**
		 * Select a figure.
		 */
		public static const SELECT_FIGURE : String = "select_figure_ToolPanel";
		/**
		 * Initial the id of selected figure.
		 */
		public var selectedFigureId : int = -1;
		/**
		 * Constructor, takes the event name (type) and data object (defaults to null)
		 * and also defaults the standard Flex event properties bubbles and cancelable
		 * to true and false respectively.
		 */ 
		public function ToolPanelAppEvent(type:String, dataParamter:Object=null, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, dataParamter, bubbles, cancelable);
			
			this.selectedFigureId = dataParamter.selectedFigureId;
		}
		
	}
}