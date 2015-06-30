package org.act.od.impl.events
{
	import org.act.od.framework.control.OrDesignerEvent;
	/**
	 * This class define the events raised by the FigureEditorNavigatorVH.
	 * 
	 * @ author Quyue
	 * 
	 */
	public class FigureEditorNavigatorAppEvent extends OrDesignerEvent
	{
		/**
		 * Close figureEditor page.
		 */
		public static const FIGURE_EDITOR_CLOSE :String = "figureEditor_close_FigureEditorNavigator";
		/**
		 * Close BPELEditor Page.
		 */
		public static const BPEL_EDITOR_CLOSE :String = "bpelEditor_close_FigureEditorNavigator";
		/**
		 * Switch tabNavigator's tab.
		 */
		public static const TABINDEX_CHANGE :String = "tabIndex_change_FigureEditorNavigator";
		/**
		 * Active figureEditor page.
		 */
		public static const ACTIVE_FIGUREEDITOR_PAGE :String = "active_figureEditor_page_FigureEditorNavigator";
		/**
		 * Active BPELEditor page.
		 */
		public static const ACTIVE_BPELEDITOR_PAGE :String = "active_bpelEditor_page_FigureEditorNavigator";
		/**
		 * Cancle close figureEditor page.
		 */
		public static const CANCLE_CLOSE_FIGURE_EDITOR :String = "cancle_close_figureEditor_FigureEditorNavigator";
		/**
		 * Cancle close BPELEditor page.
		 */
		public static const CANCLE_CLOSE_BPEL_EDITOR :String = "cancle_close_BPELEditor_FigureEditorNavigator";
		
		//		added by mengsong 2010-5-17 12:52:09
		/**
		 *double click the tab for fill the window
		 */
		public static const FILL_THE_WINDOWS :String = "fill_the_windows_FigureEditorNavigator";
		/**
		 *double click the tab again for  the default window
		 */		
		public static const DEFAULT_WINDOWS :String = "default_windows_FigureEditorNavigator";		
		/**
		 * Constructor, takes the event name (type) and data object (defaults to null)
		 * and also defaults the standard Flex event properties bubbles and cancelable
		 * to true and false respectively.
		 */
		public function FigureEditorNavigatorAppEvent(type : String, dataParameter:Object = null ,
													  bubbles : Boolean = false, cancelable : Boolean = false){
			
			super(type, dataParameter, bubbles, cancelable);
		}
		
	}
}